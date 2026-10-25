// Introduction to Software Testing
// Authors: Paul Ammann & Jeff Offutt
// Chapter 3; page ??
// See CalcTest.java, DataDrivenCalcTest.java for JUnit tests

public class Calc
{
   static public int add (int a, int b)
   {
      return a + b;
   }

   static public int subtract (int a, int b)
   {
      return a - b;
   }

   static public int multiply (int a, int b)
   {
      return a / b;
   }
   static public int divide (int a, int b)
   {
      return a / b;
   }

   // Q6 cycle 2 (agent-written): Java's % uses truncated division, so the result
   // takes the sign of the dividend, and b == 0 throws ArithmeticException ("/ by zero").
   static public int modulo (int a, int b)
   {
      return a % b;
   }

   // Q6 cycle 4 (agent-written): repeated multiplication.
   // exponent == 0 runs the loop zero times, so power(0, 0) == 1 still holds.
   // Q6 cycle 5 (agent-written): negative exponent -> 1 / base^|exponent| truncated toward zero.
   // Only base 1 and -1 give a non-zero integer; every other base truncates to 0.
   static public int power (int base, int exponent)
   {
      if (exponent < 0)
      {
         // Q6 cycle 6 (agent-written): 0 to a negative power is 1/0 -> student chose to throw
         if (base == 0)  throw new ArithmeticException("zero to a negative power");
         if (base == 1)  return 1;
         if (base == -1) return (exponent % 2 == 0) ? 1 : -1;
         return 0;
      }
      int result = 1;
      for (int i = 0; i < exponent; i++)
      {
         // Q6 cycle 7 (agent-written): multiplyExact throws ArithmeticException on any int overflow
         result = Math.multiplyExact(result, base);
      }
      return result;
   }
}
