/*  1:   */ package com.fasterxml.classmate.util;
/*  2:   */ 
/*  3:   */ public class MethodKey
/*  4:   */ {
/*  5: 8 */   private final Class<?>[] NO_CLASSES = new Class[0];
/*  6:   */   private final String _name;
/*  7:   */   private final Class<?>[] _argumentTypes;
/*  8:   */   private final int _hashCode;
/*  9:   */   
/* 10:   */   public MethodKey(String name)
/* 11:   */   {
/* 12:18 */     this._name = name;
/* 13:19 */     this._argumentTypes = this.NO_CLASSES;
/* 14:20 */     this._hashCode = name.hashCode();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public MethodKey(String name, Class<?>[] argTypes)
/* 18:   */   {
/* 19:25 */     this._name = name;
/* 20:26 */     this._argumentTypes = argTypes;
/* 21:27 */     this._hashCode = (name.hashCode() + argTypes.length);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean equals(Object o)
/* 25:   */   {
/* 26:41 */     if (o == this) {
/* 27:41 */       return true;
/* 28:   */     }
/* 29:42 */     if ((o == null) || (o.getClass() != getClass())) {
/* 30:42 */       return false;
/* 31:   */     }
/* 32:43 */     MethodKey other = (MethodKey)o;
/* 33:44 */     Class<?>[] otherArgs = other._argumentTypes;
/* 34:45 */     int len = this._argumentTypes.length;
/* 35:46 */     if (otherArgs.length != len) {
/* 36:46 */       return false;
/* 37:   */     }
/* 38:47 */     for (int i = 0; i < len; i++) {
/* 39:48 */       if (otherArgs[i] != this._argumentTypes[i]) {
/* 40:48 */         return false;
/* 41:   */       }
/* 42:   */     }
/* 43:50 */     return this._name.equals(other._name);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int hashCode()
/* 47:   */   {
/* 48:53 */     return this._hashCode;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String toString()
/* 52:   */   {
/* 53:57 */     StringBuilder sb = new StringBuilder();
/* 54:58 */     sb.append(this._name);
/* 55:59 */     sb.append('(');
/* 56:60 */     int i = 0;
/* 57:60 */     for (int len = this._argumentTypes.length; i < len; i++)
/* 58:   */     {
/* 59:61 */       if (i > 0) {
/* 60:61 */         sb.append(',');
/* 61:   */       }
/* 62:62 */       sb.append(this._argumentTypes[i].getName());
/* 63:   */     }
/* 64:64 */     sb.append(')');
/* 65:65 */     return sb.toString();
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.util.MethodKey
 * JD-Core Version:    0.7.0.1
 */