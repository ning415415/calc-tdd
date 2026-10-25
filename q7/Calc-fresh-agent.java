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
