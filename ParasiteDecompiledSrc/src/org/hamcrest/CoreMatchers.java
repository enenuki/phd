/*   1:    */ package org.hamcrest;
/*   2:    */ 
/*   3:    */ import org.hamcrest.core.AllOf;
/*   4:    */ import org.hamcrest.core.AnyOf;
/*   5:    */ import org.hamcrest.core.DescribedAs;
/*   6:    */ import org.hamcrest.core.Is;
/*   7:    */ import org.hamcrest.core.IsAnything;
/*   8:    */ import org.hamcrest.core.IsEqual;
/*   9:    */ import org.hamcrest.core.IsInstanceOf;
/*  10:    */ import org.hamcrest.core.IsNot;
/*  11:    */ import org.hamcrest.core.IsNull;
/*  12:    */ import org.hamcrest.core.IsSame;
/*  13:    */ 
/*  14:    */ public class CoreMatchers
/*  15:    */ {
/*  16:    */   public static <T> Matcher<T> is(Matcher<T> matcher)
/*  17:    */   {
/*  18: 14 */     return Is.is(matcher);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static <T> Matcher<T> is(T value)
/*  22:    */   {
/*  23: 24 */     return Is.is(value);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Matcher<Object> is(Class<?> type)
/*  27:    */   {
/*  28: 34 */     return Is.is(type);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static <T> Matcher<T> not(Matcher<T> matcher)
/*  32:    */   {
/*  33: 41 */     return IsNot.not(matcher);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static <T> Matcher<T> not(T value)
/*  37:    */   {
/*  38: 51 */     return IsNot.not(value);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static <T> Matcher<T> equalTo(T operand)
/*  42:    */   {
/*  43: 59 */     return IsEqual.equalTo(operand);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static Matcher<Object> instanceOf(Class<?> type)
/*  47:    */   {
/*  48: 66 */     return IsInstanceOf.instanceOf(type);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static <T> Matcher<T> allOf(Matcher<? extends T>... matchers)
/*  52:    */   {
/*  53: 73 */     return AllOf.allOf(matchers);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static <T> Matcher<T> allOf(Iterable<Matcher<? extends T>> matchers)
/*  57:    */   {
/*  58: 80 */     return AllOf.allOf(matchers);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static <T> Matcher<T> anyOf(Matcher<? extends T>... matchers)
/*  62:    */   {
/*  63: 87 */     return AnyOf.anyOf(matchers);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static <T> Matcher<T> anyOf(Iterable<Matcher<? extends T>> matchers)
/*  67:    */   {
/*  68: 94 */     return AnyOf.anyOf(matchers);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static <T> Matcher<T> sameInstance(T object)
/*  72:    */   {
/*  73:104 */     return IsSame.sameInstance(object);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static <T> Matcher<T> anything()
/*  77:    */   {
/*  78:111 */     return IsAnything.anything();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static <T> Matcher<T> anything(String description)
/*  82:    */   {
/*  83:120 */     return IsAnything.anything(description);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static <T> Matcher<T> any(Class<T> type)
/*  87:    */   {
/*  88:127 */     return IsAnything.any(type);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static <T> Matcher<T> nullValue()
/*  92:    */   {
/*  93:134 */     return IsNull.nullValue();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static <T> Matcher<T> nullValue(Class<T> type)
/*  97:    */   {
/*  98:141 */     return IsNull.nullValue(type);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static <T> Matcher<T> notNullValue()
/* 102:    */   {
/* 103:148 */     return IsNull.notNullValue();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static <T> Matcher<T> notNullValue(Class<T> type)
/* 107:    */   {
/* 108:155 */     return IsNull.notNullValue(type);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... values)
/* 112:    */   {
/* 113:162 */     return DescribedAs.describedAs(description, matcher, values);
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.CoreMatchers
 * JD-Core Version:    0.7.0.1
 */