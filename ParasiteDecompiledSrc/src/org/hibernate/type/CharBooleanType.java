/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.BooleanTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.CharTypeDescriptor;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public abstract class CharBooleanType
/* 10:   */   extends BooleanType
/* 11:   */ {
/* 12:   */   private final String stringValueTrue;
/* 13:   */   private final String stringValueFalse;
/* 14:   */   
/* 15:   */   protected CharBooleanType(char characterValueTrue, char characterValueFalse)
/* 16:   */   {
/* 17:40 */     super(CharTypeDescriptor.INSTANCE, new BooleanTypeDescriptor(characterValueTrue, characterValueFalse));
/* 18:   */     
/* 19:42 */     this.stringValueTrue = String.valueOf(characterValueTrue);
/* 20:43 */     this.stringValueFalse = String.valueOf(characterValueFalse);
/* 21:   */   }
/* 22:   */   
/* 23:   */   /**
/* 24:   */    * @deprecated
/* 25:   */    */
/* 26:   */   protected final String getTrueString()
/* 27:   */   {
/* 28:50 */     return this.stringValueTrue;
/* 29:   */   }
/* 30:   */   
/* 31:   */   /**
/* 32:   */    * @deprecated
/* 33:   */    */
/* 34:   */   protected final String getFalseString()
/* 35:   */   {
/* 36:57 */     return this.stringValueFalse;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CharBooleanType
 * JD-Core Version:    0.7.0.1
 */