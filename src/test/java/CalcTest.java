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

   @Test public void testSubtractNrgative()
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
}
