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

   // Q6 cycle 1 (agent-written): decision (c) - modulo by zero throws ArithmeticException
   @Test(expected = ArithmeticException.class)
   public void testModuloByZero()
   {
      Calc.modulo(7, 0);
   }

   // Q6 cycle 2 (agent-written): decision (c) - modulo with negative operands.
   // The result takes the sign of the dividend (truncated division, same as Java's %).
   @Test public void testModuloNegativeOperands()
   {
      assertTrue ("Calc remainder incorrect for (-7, 3)", -1 == Calc.modulo (-7, 3));
      assertTrue ("Calc remainder incorrect for (7, -3)",  1 == Calc.modulo (7, -3));
      assertTrue ("Calc remainder incorrect for (-7, -3)", -1 == Calc.modulo (-7, -3));
   }

   // Q6 cycle 3 (agent-written): decision (c) - power(0, 0) returns 1
   @Test public void testPowerZeroToZero()
   {
      assertTrue ("Calc power incorrect for (0, 0)", 1 == Calc.power (0, 0));
   }

   // Q6 cycle 4 (agent-written): ordinary positive exponent
   @Test public void testPowerPositiveExponent()
   {
      assertTrue ("Calc power incorrect for (2, 3)", 8 == Calc.power (2, 3));
   }

   // Q6 cycle 5 (agent-written): decision (c) - negative exponent.
   // The mathematical result 1 / base^|exponent| is truncated toward zero to an int.
   @Test public void testPowerNegativeExponentTruncates()
   {
      assertTrue ("Calc power incorrect for (2, -1)",   0 == Calc.power (2, -1));   //  0.5   -> 0
      assertTrue ("Calc power incorrect for (-2, -1)",  0 == Calc.power (-2, -1));  // -0.5   -> 0
      assertTrue ("Calc power incorrect for (1, -3)",   1 == Calc.power (1, -3));   //  1     -> 1
      assertTrue ("Calc power incorrect for (-1, -3)", -1 == Calc.power (-1, -3));  // -1     -> -1
      assertTrue ("Calc power incorrect for (-1, -2)",  1 == Calc.power (-1, -2));  //  1     -> 1
   }

   // Q6 cycle 6 (agent-written): student decision - power(0, negative) is 1/0, so it throws
   @Test(expected = ArithmeticException.class)
   public void testPowerZeroToNegativeThrows()
   {
      Calc.power(0, -1);
   }
}
