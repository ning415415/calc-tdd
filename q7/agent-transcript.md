# Q7 AI usage log — fresh agent writes Calc from the tests only

- Agent: Claude (sub-agent started by the Cowork session), fresh context: it had never seen this conversation, `Calc.java`, or the Q6 history.
- Date: 2026-10-23 (Asia/Taipei)
- Input: only `CalcTest.java` (the 14 tests from Q1 + Q6) with all `//` comments removed, so the agent could not read the intended rules from comments.
- Tools used by the agent: none (tool_uses: 0).

## Prompt (verbatim)

````text
Write a Java class `Calc` (file Calc.java, default package) so that every test in the JUnit 4 test class below passes. Do not modify the tests. Do not use any tools and do not look at any files — work only from the test code in this message. Reply with the complete Calc.java source in one code block, followed by a short list of any behaviour you had to decide that the tests do not pin down.

```java
import org.junit.*;
import static org.junit.Assert.*;

public class CalcTest
{
   @Test public void testAdd()
   {
      assertTrue ("Calc sum incorrect", 5 == Calc.add (2, 3));
   }

   @Test public void testSubtract()
   {
      assertTrue ("Calc difference incorrect", 2 == Calc.subtract ( 5, 3));
   }

   @Test public void testSubtractNegative()
   {
      assertTrue ("Calc difference incorrect", -1 == Calc.subtract ( 2, 3));
   }

   @Test public void testMultiply()
   {
      assertTrue ("Calc product incorrect", 15 == Calc.multiply ( 3, 5));
   }

   @Test public void testDivide()
   {
      int result = Calc.divide(7, 2);
      assertTrue ("Calc quotient incorrect", 3 == result);
   }

   @Test(expected = ArithmeticException.class)
   public void testDivideByZero()
   {
      Calc.divide(7, 0);
   }

   @Test public void testDivideNegtive()
   {
      int result = Calc.divide(7, -2);
      assertTrue ("Calc quotient incorrect", -3 == result);
   }

   @Test(expected = ArithmeticException.class)
   public void testModuloByZero()
   {
      Calc.modulo(7, 0);
   }

   @Test public void testModuloNegativeOperands()
   {
      assertTrue ("Calc remainder incorrect for (-7, 3)", -1 == Calc.modulo (-7, 3));
      assertTrue ("Calc remainder incorrect for (7, -3)",  1 == Calc.modulo (7, -3));
      assertTrue ("Calc remainder incorrect for (-7, -3)", -1 == Calc.modulo (-7, -3));
   }

   @Test public void testPowerZeroToZero()
   {
      assertTrue ("Calc power incorrect for (0, 0)", 1 == Calc.power (0, 0));
   }

   @Test public void testPowerPositiveExponent()
   {
      assertTrue ("Calc power incorrect for (2, 3)", 8 == Calc.power (2, 3));
   }

   @Test public void testPowerNegativeExponentTruncates()
   {
      assertTrue ("Calc power incorrect for (2, -1)",   0 == Calc.power (2, -1));
      assertTrue ("Calc power incorrect for (-2, -1)",  0 == Calc.power (-2, -1));
      assertTrue ("Calc power incorrect for (1, -3)",   1 == Calc.power (1, -3));
      assertTrue ("Calc power incorrect for (-1, -3)", -1 == Calc.power (-1, -3));
      assertTrue ("Calc power incorrect for (-1, -2)",  1 == Calc.power (-1, -2));
   }

   @Test(expected = ArithmeticException.class)
   public void testPowerZeroToNegativeThrows()
   {
      Calc.power(0, -1);
   }

   @Test(expected = ArithmeticException.class)
   public void testPowerOverflowThrows()
   {
      Calc.power(2, 31);
   }
}
```
````

## Agent response (verbatim, complete)

```java
public class Calc
{
   public static int add(int a, int b)
   {
      return a + b;
   }

   public static int subtract(int a, int b)
   {
      return a - b;
   }

   public static int multiply(int a, int b)
   {
      return a * b;
   }

   /** Integer division that truncates toward zero. Throws ArithmeticException if b == 0. */
   public static int divide(int a, int b)
   {
      return a / b;
   }

   /** Remainder whose sign follows the dividend, like Java's %. Throws ArithmeticException if b == 0. */
   public static int modulo(int a, int b)
   {
      return a % b;
   }

   /**
    * Integer power.
    *  - power(0, 0) == 1
    *  - Negative exponent: the exact result 1 / base^|exp| is truncated toward zero,
    *    so it is 1 for base 1, +/-1 for base -1 and 0 for any other non-zero base.
    *  - power(0, negative) throws ArithmeticException (division by zero).
    *  - Throws ArithmeticException if the result does not fit in an int.
    */
   public static int power(int base, int exp)
   {
      boolean oddExp = (exp & 1) != 0;   // also correct for negative exponents

      if (exp < 0)
      {
         if (base == 0)
            throw new ArithmeticException("0 raised to a negative power");
         if (base == 1)
            return 1;
         if (base == -1)
            return oddExp ? -1 : 1;
         return 0;
      }

      if (exp == 0)
         return 1;

      // Bases whose powers never overflow; handling them here keeps large exponents fast.
      if (base == 0 || base == 1)
         return base;
      if (base == -1)
         return oddExp ? -1 : 1;

      // When |base| >= 2 this loop overflows within about 31 steps, so it stays short.
      int result = 1;
      for (int i = 0; i < exp; i++)
         result = Math.multiplyExact(result, base);   // throws ArithmeticException on overflow
      return result;
   }
}
```

Choices the tests don't settle:

- **Overflow in `add`, `subtract`, `multiply` and `divide`:** these use plain Java `int` arithmetic, so an overflow wraps around without an error. For example, `divide(Integer.MIN_VALUE, -1)` returns `Integer.MIN_VALUE`. Only `power` throws on overflow, because only that one is tested for it. If you want all of them to behave the same way, use `Math.addExact`, `Math.subtractExact` and `Math.multiplyExact` instead.
- **Rounding and signs:** `divide` rounds toward zero. `modulo` is Java's `%`, so the result takes the sign of the first number. That matches the three negative-number cases in the tests, but it is not mathematical (always non-negative) modulo.
- **Return type:** every method takes and returns `int`.
- **Negative exponents:** for bases other than 1 and -1, `power` returns 0. For base -1 it returns ±1 depending on whether the exponent is odd or even. The tests check these at -1, -2 and -3 only; I applied the same rule to every negative exponent.
- **`power(Integer.MIN_VALUE, 1)`:** returns `Integer.MIN_VALUE` without throwing. `power(Integer.MIN_VALUE, 2)` does throw.
- **Large exponents:** bases 0, 1 and -1 are handled directly, so something like `power(1, Integer.MAX_VALUE)` returns at once instead of running a long loop.
