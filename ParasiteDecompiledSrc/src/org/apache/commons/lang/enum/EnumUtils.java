/*   1:    */ package org.apache.commons.lang.enum;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ /**
/*   8:    */  * @deprecated
/*   9:    */  */
/*  10:    */ public class EnumUtils
/*  11:    */ {
/*  12:    */   public static Enum getEnum(Class enumClass, String name)
/*  13:    */   {
/*  14: 56 */     return Enum.getEnum(enumClass, name);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static ValuedEnum getEnum(Class enumClass, int value)
/*  18:    */   {
/*  19: 68 */     return (ValuedEnum)ValuedEnum.getEnum(enumClass, value);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static Map getEnumMap(Class enumClass)
/*  23:    */   {
/*  24: 85 */     return Enum.getEnumMap(enumClass);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static List getEnumList(Class enumClass)
/*  28:    */   {
/*  29:105 */     return Enum.getEnumList(enumClass);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static Iterator iterator(Class enumClass)
/*  33:    */   {
/*  34:125 */     return Enum.getEnumList(enumClass).iterator();
/*  35:    */   }
/*  36:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.enum.EnumUtils
 * JD-Core Version:    0.7.0.1
 */