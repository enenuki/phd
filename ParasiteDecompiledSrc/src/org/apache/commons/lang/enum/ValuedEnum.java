/*   1:    */ package org.apache.commons.lang.enum;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import org.apache.commons.lang.ClassUtils;
/*   6:    */ 
/*   7:    */ /**
/*   8:    */  * @deprecated
/*   9:    */  */
/*  10:    */ public abstract class ValuedEnum
/*  11:    */   extends Enum
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -7129650521543789085L;
/*  14:    */   private final int iValue;
/*  15:    */   
/*  16:    */   protected ValuedEnum(String name, int value)
/*  17:    */   {
/*  18:126 */     super(name);
/*  19:127 */     this.iValue = value;
/*  20:    */   }
/*  21:    */   
/*  22:    */   protected static Enum getEnum(Class enumClass, int value)
/*  23:    */   {
/*  24:143 */     if (enumClass == null) {
/*  25:144 */       throw new IllegalArgumentException("The Enum Class must not be null");
/*  26:    */     }
/*  27:146 */     List list = Enum.getEnumList(enumClass);
/*  28:147 */     for (Iterator it = list.iterator(); it.hasNext();)
/*  29:    */     {
/*  30:148 */       ValuedEnum enumeration = (ValuedEnum)it.next();
/*  31:149 */       if (enumeration.getValue() == value) {
/*  32:150 */         return enumeration;
/*  33:    */       }
/*  34:    */     }
/*  35:153 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final int getValue()
/*  39:    */   {
/*  40:162 */     return this.iValue;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int compareTo(Object other)
/*  44:    */   {
/*  45:179 */     return this.iValue - ((ValuedEnum)other).iValue;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String toString()
/*  49:    */   {
/*  50:190 */     if (this.iToString == null)
/*  51:    */     {
/*  52:191 */       String shortName = ClassUtils.getShortClassName(getEnumClass());
/*  53:192 */       this.iToString = (shortName + "[" + getName() + "=" + getValue() + "]");
/*  54:    */     }
/*  55:194 */     return this.iToString;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.enum.ValuedEnum
 * JD-Core Version:    0.7.0.1
 */