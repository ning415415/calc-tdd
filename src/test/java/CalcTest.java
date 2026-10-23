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
}
