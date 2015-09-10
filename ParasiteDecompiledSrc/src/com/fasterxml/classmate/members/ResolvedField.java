/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.Annotations;
/*  4:   */ import com.fasterxml.classmate.ResolvedType;
/*  5:   */ import java.lang.annotation.Annotation;
/*  6:   */ import java.lang.reflect.Field;
/*  7:   */ 
/*  8:   */ public class ResolvedField
/*  9:   */   extends ResolvedMember
/* 10:   */ {
/* 11:   */   protected final Field _field;
/* 12:   */   protected final ResolvedType _type;
/* 13:   */   
/* 14:   */   public ResolvedField(ResolvedType context, Annotations ann, Field field, ResolvedType type)
/* 15:   */   {
/* 16:18 */     super(context, ann);
/* 17:19 */     this._field = field;
/* 18:20 */     this._type = type;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addAnnotation(Annotation ann)
/* 22:   */   {
/* 23:24 */     this._annotations.add(ann);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Field getRawMember()
/* 27:   */   {
/* 28:33 */     return this._field;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public ResolvedType getType()
/* 32:   */   {
/* 33:34 */     return this._type;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean equals(Object o)
/* 37:   */   {
/* 38:44 */     if (o == this) {
/* 39:44 */       return true;
/* 40:   */     }
/* 41:45 */     if ((o == null) || (o.getClass() != getClass())) {
/* 42:45 */       return false;
/* 43:   */     }
/* 44:46 */     RawField other = (RawField)o;
/* 45:47 */     return other._field == this._field;
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.ResolvedField
 * JD-Core Version:    0.7.0.1
 */