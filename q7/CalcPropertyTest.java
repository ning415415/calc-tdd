// Q7: property-based and metamorphic tests for Calc.
// Instead of a few hand-picked examples, every property is checked on many random inputs
// (fixed seed, so runs are reproducible) and, where possible, against an independent oracle
// (long / BigInteger arithmetic). The specification is the one the student fixed in Q1 and Q6:
//   divide  : int result, truncates toward zero, b == 0 -> ArithmeticException
//   modulo  : sign follows the dividend, b == 0 -> ArithmeticException
//   power   : power(0,0) == 1; negative exponent -> 1/base^|e| truncated toward zero;
//             power(0, negative) -> ArithmeticException; int overflow -> ArithmeticException
// add/subtract/multiply overflow was never specified, so those properties stay inside the
// range where no overflow can happen.

import org.junit.*;
import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.Random;

public class CalcPropertyTest
{
   private static final int N = 2000;
   private final Random rnd = new Random(20261023L);

   private int between(int lo, int hi) { return lo + rnd.nextInt(hi - lo + 1); }
   private int nonZero(int lo, int hi) { int v; do { v = between(lo, hi); } while (v == 0); return v; }

   // ---------- add / subtract / multiply ----------

   @Test public void addSubtractMultiplyMatchLongArithmetic()
   {
      for (int i = 0; i < N; i++)
      {
         int a = between(-46340, 46340), b = between(-46340, 46340);   // |a*b| < 2^31
         assertEquals("add(" + a + "," + b + ")",      (long) a + b, Calc.add(a, b));
         assertEquals("subtract(" + a + "," + b + ")", (long) a - b, Calc.subtract(a, b));
         assertEquals("multiply(" + a + "," + b + ")", (long) a * b, Calc.multiply(a, b));
      }
   }

   @Test public void metamorphicAddSubtractMultiply()
   {
      for (int i = 0; i < N; i++)
      {
         int a = between(-46340, 46340), b = between(-46340, 46340);
         assertEquals("subtract(add(a,b),b) == a", a, Calc.subtract(Calc.add(a, b), b));
         assertEquals("add is commutative",      Calc.add(a, b),      Calc.add(b, a));
         assertEquals("multiply is commutative", Calc.multiply(a, b), Calc.multiply(b, a));
         assertEquals("subtract(a,b) == -subtract(b,a)", Calc.subtract(a, b), -Calc.subtract(b, a));
      }
   }

   // ---------- divide / modulo ----------

   @Test public void divideAndModuloSatisfyTheDivisionIdentity()
   {
      for (int i = 0; i < N; i++)
      {
         int a = between(-1_000_000, 1_000_000), b = nonZero(-1000, 1000);
         int q = Calc.divide(a, b), r = Calc.modulo(a, b);
         String ctx = "(" + a + "," + b + ") q=" + q + " r=" + r;
         assertEquals("divide*b + modulo == a " + ctx, a, q * b + r);
         assertTrue("|modulo| < |b| " + ctx, Math.abs(r) < Math.abs(b));
         assertTrue("modulo takes the sign of the dividend " + ctx, r == 0 || Integer.signum(r) == Integer.signum(a));
         assertEquals("divide truncates toward zero " + ctx, (int) ((double) a / b), q);
      }
   }

   @Test public void moduloDependsOnlyOnTheSizeOfTheDivisor()
   {
      for (int i = 0; i < N; i++)
      {
         int a = between(-1_000_000, 1_000_000), b = nonZero(-1000, 1000);
         assertEquals("modulo(a,b) == modulo(a,-b) for (" + a + "," + b + ")", Calc.modulo(a, b), Calc.modulo(a, -b));
         assertEquals("modulo(-a,b) == -modulo(a,b) for (" + a + "," + b + ")", -Calc.modulo(a, b), Calc.modulo(-a, b));
      }
   }

   @Test public void divideAndModuloByZeroAlwaysThrow()
   {
      for (int i = 0; i < 200; i++)
      {
         int a = rnd.nextInt();
         try { Calc.divide(a, 0); fail("divide(" + a + ",0) did not throw"); } catch (ArithmeticException expected) { }
         try { Calc.modulo(a, 0); fail("modulo(" + a + ",0) did not throw"); } catch (ArithmeticException expected) { }
      }
   }

   // ---------- power ----------

   @Test public void powerMatchesBigIntegerOrThrowsOnOverflow()
   {
      BigInteger min = BigInteger.valueOf(Integer.MIN_VALUE), max = BigInteger.valueOf(Integer.MAX_VALUE);
      for (int base = -40; base <= 40; base++)
      {
         for (int e = 0; e <= 40; e++)
         {
            BigInteger exact = BigInteger.valueOf(base).pow(e);
            boolean fits = exact.compareTo(min) >= 0 && exact.compareTo(max) <= 0;
            try
            {
               int actual = Calc.power(base, e);
               assertTrue("power(" + base + "," + e + ") = " + actual + " but exact value " + exact + " does not fit in int", fits);
               assertEquals("power(" + base + "," + e + ")", exact.intValue(), actual);
            }
            catch (ArithmeticException ex)
            {
               assertFalse("power(" + base + "," + e + ") threw but exact value " + exact + " fits in int", fits);
            }
         }
      }
   }

   @Test public void powerRecurrence()
   {
      for (int i = 0; i < N; i++)
      {
         int base = between(-12, 12), e = between(0, 7);    // 12^8 < 2^31, no overflow
         assertEquals("power(b,e+1) == power(b,e)*b for (" + base + "," + e + ")",
                      Calc.power(base, e) * base, Calc.power(base, e + 1));
      }
   }

   @Test public void negativeExponentTruncatesTowardZero()
   {
      for (int i = 0; i < N; i++)
      {
         int e = between(-1000, -1);
         int base = rnd.nextBoolean() ? between(2, 1_000_000) : between(-1_000_000, -2);
         assertEquals("power(" + base + "," + e + ")", 0, Calc.power(base, e));
         assertEquals("power(1," + e + ")", 1, Calc.power(1, e));
         assertEquals("power(-1," + e + ")", (e % 2 == 0) ? 1 : -1, Calc.power(-1, e));
         try { Calc.power(0, e); fail("power(0," + e + ") did not throw"); } catch (ArithmeticException expected) { }
      }
   }

   @Test(timeout = 2000)
   public void powerWithHugeExponentFinishesQuickly()
   {
      // The results are mathematically defined and fit in an int, so they must be returned.
      assertEquals(1,  Calc.power(1,  Integer.MAX_VALUE));
      assertEquals(-1, Calc.power(-1, Integer.MAX_VALUE));
      assertEquals(0,  Calc.power(0,  Integer.MAX_VALUE));
   }
}
