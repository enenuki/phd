/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.Dialect;
/*  4:   */ 
/*  5:   */ public class DerivedValue
/*  6:   */   extends AbstractSimpleValue
/*  7:   */ {
/*  8:   */   private final String expression;
/*  9:   */   
/* 10:   */   public DerivedValue(TableSpecification table, int position, String expression)
/* 11:   */   {
/* 12:37 */     super(table, position);
/* 13:38 */     this.expression = expression;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toLoggableString()
/* 17:   */   {
/* 18:45 */     return getTable().toLoggableString() + ".{derived-column}";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getAlias(Dialect dialect)
/* 22:   */   {
/* 23:53 */     return "formula" + Integer.toString(getPosition()) + '_';
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getExpression()
/* 27:   */   {
/* 28:61 */     return this.expression;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.DerivedValue
 * JD-Core Version:    0.7.0.1
 */