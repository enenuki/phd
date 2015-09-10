/*  1:   */ package com.fasterxml.classmate.util;
/*  2:   */ 
/*  3:   */ public class ClassKey
/*  4:   */   implements Comparable<ClassKey>
/*  5:   */ {
/*  6:   */   private final String _className;
/*  7:   */   private final Class<?> _class;
/*  8:   */   private final int _hashCode;
/*  9:   */   
/* 10:   */   public ClassKey(Class<?> clz)
/* 11:   */   {
/* 12:20 */     this._class = clz;
/* 13:21 */     this._className = clz.getName();
/* 14:22 */     this._hashCode = this._className.hashCode();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int compareTo(ClassKey other)
/* 18:   */   {
/* 19:34 */     return this._className.compareTo(other._className);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object o)
/* 23:   */   {
/* 24:46 */     if (o == this) {
/* 25:46 */       return true;
/* 26:   */     }
/* 27:47 */     if (o == null) {
/* 28:47 */       return false;
/* 29:   */     }
/* 30:48 */     if (o.getClass() != getClass()) {
/* 31:48 */       return false;
/* 32:   */     }
/* 33:49 */     ClassKey other = (ClassKey)o;
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:58 */     return other._class == this._class;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int hashCode()
/* 46:   */   {
/* 47:61 */     return this._hashCode;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public String toString()
/* 51:   */   {
/* 52:63 */     return this._className;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.util.ClassKey
 * JD-Core Version:    0.7.0.1
 */