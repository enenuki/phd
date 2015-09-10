/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import org.hibernate.metamodel.source.binder.DerivedValueSource;
/*  4:   */ 
/*  5:   */ public class DerivedValueSourceImpl
/*  6:   */   implements DerivedValueSource
/*  7:   */ {
/*  8:   */   private final FormulaValue formulaValue;
/*  9:   */   
/* 10:   */   DerivedValueSourceImpl(FormulaValue formulaValue)
/* 11:   */   {
/* 12:36 */     this.formulaValue = formulaValue;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getExpression()
/* 16:   */   {
/* 17:41 */     return this.formulaValue.getExpression();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getContainingTableName()
/* 21:   */   {
/* 22:46 */     return this.formulaValue.getContainingTableName();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.DerivedValueSourceImpl
 * JD-Core Version:    0.7.0.1
 */