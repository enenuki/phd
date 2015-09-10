/*  1:   */ package org.hibernate.param;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.Type;
/*  4:   */ 
/*  5:   */ public abstract class AbstractExplicitParameterSpecification
/*  6:   */   implements ExplicitParameterSpecification
/*  7:   */ {
/*  8:   */   private final int sourceLine;
/*  9:   */   private final int sourceColumn;
/* 10:   */   private Type expectedType;
/* 11:   */   
/* 12:   */   protected AbstractExplicitParameterSpecification(int sourceLine, int sourceColumn)
/* 13:   */   {
/* 14:45 */     this.sourceLine = sourceLine;
/* 15:46 */     this.sourceColumn = sourceColumn;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getSourceLine()
/* 19:   */   {
/* 20:53 */     return this.sourceLine;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getSourceColumn()
/* 24:   */   {
/* 25:60 */     return this.sourceColumn;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Type getExpectedType()
/* 29:   */   {
/* 30:67 */     return this.expectedType;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setExpectedType(Type expectedType)
/* 34:   */   {
/* 35:74 */     this.expectedType = expectedType;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.AbstractExplicitParameterSpecification
 * JD-Core Version:    0.7.0.1
 */