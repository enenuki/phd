/*   1:    */ package org.apache.commons.lang.enums;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class EnumUtils
/*   8:    */ {
/*   9:    */   public static Enum getEnum(Class enumClass, String name)
/*  10:    */   {
/*  11: 52 */     return Enum.getEnum(enumClass, name);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public static ValuedEnum getEnum(Class enumClass, int value)
/*  15:    */   {
/*  16: 64 */     return (ValuedEnum)ValuedEnum.getEnum(enumClass, value);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static Map getEnumMap(Class enumClass)
/*  20:    */   {
/*  21: 81 */     return Enum.getEnumMap(enumClass);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static List getEnumList(Class enumClass)
/*  25:    */   {
/*  26:101 */     return Enum.getEnumList(enumClass);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Iterator iterator(Class enumClass)
/*  30:    */   {
/*  31:121 */     return Enum.getEnumList(enumClass).iterator();
/*  32:    */   }
/*  33:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.enums.EnumUtils
 * JD-Core Version:    0.7.0.1
 */