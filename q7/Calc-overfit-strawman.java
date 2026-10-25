// Q7 strawman: a deliberately OVERFITTED Calc that passes every example test in CalcTest
// but only "works" for the test inputs. Used to check whether the property tests can tell
// a general implementation from one that special-cases the inputs it was shown.
public class Calc
{
   static public int add (int a, int b)      { return a + b; }
   static public int subtract (int a, int b) { return a - b; }
   static public int multiply (int a, int b) { return a * b; }
   static public int divide (int a, int b)   { return a / b; }

   static public int modulo (int a, int b)
   {
      if (b == 0) throw new ArithmeticException();
      if (a < 0)  return -1;          // matches (-7,3) and (-7,-3) only
      if (b < 0)  return 1;           // matches (7,-3) only
      return a % b;
   }

   static public int power (int base, int exponent)
   {
      if (base == 2 && exponent == 31) throw new ArithmeticException();   // the one overflow test
      if (exponent < 0)
      {
         if (base == 0)  throw new ArithmeticException();
         if (base == 1)  return 1;
         if (base == -1) return (exponent % 2 == 0) ? 1 : -1;
         return 0;
      }
      int result = 1;
      for (int i = 0; i < exponent; i++) result *= base;                   // silently overflows
      return result;
   }
}
