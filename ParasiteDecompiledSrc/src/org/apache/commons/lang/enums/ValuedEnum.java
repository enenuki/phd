/*   1:    */ package org.apache.commons.lang.enums;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.lang.ClassUtils;
/*   8:    */ 
/*   9:    */ public abstract class ValuedEnum
/*  10:    */   extends Enum
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -7129650521543789085L;
/*  13:    */   private final int iValue;
/*  14:    */   
/*  15:    */   protected ValuedEnum(String name, int value)
/*  16:    */   {
/*  17:131 */     super(name);
/*  18:132 */     this.iValue = value;
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected static Enum getEnum(Class enumClass, int value)
/*  22:    */   {
/*  23:148 */     if (enumClass == null) {
/*  24:149 */       throw new IllegalArgumentException("The Enum Class must not be null");
/*  25:    */     }
/*  26:151 */     List list = Enum.getEnumList(enumClass);
/*  27:152 */     for (Iterator it = list.iterator(); it.hasNext();)
/*  28:    */     {
/*  29:153 */       ValuedEnum enumeration = (ValuedEnum)it.next();
/*  30:154 */       if (enumeration.getValue() == value) {
/*  31:155 */         return enumeration;
/*  32:    */       }
/*  33:    */     }
/*  34:158 */     return null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final int getValue()
/*  38:    */   {
/*  39:167 */     return this.iValue;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int compareTo(Object other)
/*  43:    */   {
/*  44:188 */     if (other == this) {
/*  45:189 */       return 0;
/*  46:    */     }
/*  47:191 */     if (other.getClass() != getClass())
/*  48:    */     {
/*  49:192 */       if (other.getClass().getName().equals(getClass().getName())) {
/*  50:193 */         return this.iValue - getValueInOtherClassLoader(other);
/*  51:    */       }
/*  52:195 */       throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
/*  53:    */     }
/*  54:198 */     return this.iValue - ((ValuedEnum)other).iValue;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private int getValueInOtherClassLoader(Object other)
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61:209 */       Method mth = other.getClass().getMethod("getValue", null);
/*  62:210 */       Integer value = (Integer)mth.invoke(other, null);
/*  63:211 */       return value.intValue();
/*  64:    */     }
/*  65:    */     catch (NoSuchMethodException e) {}catch (IllegalAccessException e) {}catch (InvocationTargetException e) {}
/*  66:219 */     throw new IllegalStateException("This should not happen");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String toString()
/*  70:    */   {
/*  71:230 */     if (this.iToString == null)
/*  72:    */     {
/*  73:231 */       String shortName = ClassUtils.getShortClassName(getEnumClass());
/*  74:232 */       this.iToString = (shortName + "[" + getName() + "=" + getValue() + "]");
/*  75:    */     }
/*  76:234 */     return this.iToString;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.enums.ValuedEnum
 * JD-Core Version:    0.7.0.1
 */