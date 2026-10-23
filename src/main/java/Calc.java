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

   // Q6 cycle 1 (agent-written): minimal code to pass testModuloByZero.
   // Deliberately "fake it": the next test (negative operands) will force the real formula.
   static public int modulo (int a, int b)
   {
      throw new ArithmeticException("modulo by zero");
   }
}
