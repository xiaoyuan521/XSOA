package com.framework.util;

import org.apache.commons.lang.math.NumberUtils;

public class MyNumberUtils extends NumberUtils {

   public static int safeToInt(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return 0;
      else
         return createInteger(arg.toString());
   }

   public static float safeTOfloat(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return 0;
      else
         return createFloat(arg.toString()).floatValue();
   }

   public static double safeTOdouble(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return 0.0;
      else
         return createDouble(arg.toString()).doubleValue();
   }

   public static Integer safeToInteger(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return 0;
      else
         return createInteger(arg.toString());
   }

   public static Number safeToNumber(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return 0.0;
      else
         return createNumber(arg.toString());
   }

   public static Float safeToFloat(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return new Float("0.0");
      else
         return createFloat(arg.toString());
   }

   public static Double safeToDouble(Object arg) {
      if (arg == null || MyStringUtils.isBlank(arg.toString()))
         return new Double("0.0");
      else
         return createDouble(arg.toString());
   }

}
