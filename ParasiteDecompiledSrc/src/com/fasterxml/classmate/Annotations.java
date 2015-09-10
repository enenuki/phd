/*  1:   */ package com.fasterxml.classmate;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.util.HashMap;
/*  5:   */ 
/*  6:   */ public class Annotations
/*  7:   */ {
/*  8:   */   protected HashMap<Class<? extends Annotation>, Annotation> _annotations;
/*  9:   */   
/* 10:   */   public void add(Annotation override)
/* 11:   */   {
/* 12:30 */     if (this._annotations == null) {
/* 13:31 */       this._annotations = new HashMap();
/* 14:   */     }
/* 15:33 */     this._annotations.put(override.annotationType(), override);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void addAll(Annotations overrides)
/* 19:   */   {
/* 20:42 */     if (this._annotations == null) {
/* 21:43 */       this._annotations = new HashMap();
/* 22:   */     }
/* 23:45 */     for (Annotation override : overrides._annotations.values()) {
/* 24:46 */       this._annotations.put(override.annotationType(), override);
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void addAsDefault(Annotation defValue)
/* 29:   */   {
/* 30:56 */     Class<? extends Annotation> type = defValue.annotationType();
/* 31:57 */     if (this._annotations == null)
/* 32:   */     {
/* 33:58 */       this._annotations = new HashMap();
/* 34:59 */       this._annotations.put(type, defValue);
/* 35:   */     }
/* 36:60 */     else if (!this._annotations.containsKey(type))
/* 37:   */     {
/* 38:61 */       this._annotations.put(type, defValue);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int size()
/* 43:   */   {
/* 44:72 */     return this._annotations == null ? 0 : this._annotations.size();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public <A extends Annotation> A get(Class<A> cls)
/* 48:   */   {
/* 49:78 */     if (this._annotations == null) {
/* 50:79 */       return null;
/* 51:   */     }
/* 52:81 */     return (Annotation)this._annotations.get(cls);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String toString()
/* 56:   */   {
/* 57:92 */     if (this._annotations == null) {
/* 58:93 */       return "[null]";
/* 59:   */     }
/* 60:95 */     return this._annotations.toString();
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.Annotations
 * JD-Core Version:    0.7.0.1
 */