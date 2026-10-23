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
      return a * b;
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
}
