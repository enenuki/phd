/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.ResolvedType;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ 
/*  6:   */ public class RawField
/*  7:   */   extends RawMember
/*  8:   */ {
/*  9:   */   protected final Field _field;
/* 10:   */   
/* 11:   */   public RawField(ResolvedType context, Field field)
/* 12:   */   {
/* 13:13 */     super(context);
/* 14:14 */     this._field = field;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Field getRawMember()
/* 18:   */   {
/* 19:24 */     return this._field;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object o)
/* 23:   */   {
/* 24:35 */     if (o == this) {
/* 25:35 */       return true;
/* 26:   */     }
/* 27:36 */     if ((o == null) || (o.getClass() != getClass())) {
/* 28:36 */       return false;
/* 29:   */     }
/* 30:37 */     RawField other = (RawField)o;
/* 31:38 */     return other._field == this._field;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.RawField
 * JD-Core Version:    0.7.0.1
 */