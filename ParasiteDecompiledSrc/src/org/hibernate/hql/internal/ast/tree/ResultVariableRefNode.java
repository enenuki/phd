/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  6:   */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ 
/*  9:   */ public class ResultVariableRefNode
/* 10:   */   extends HqlSqlWalkerNode
/* 11:   */ {
/* 12:   */   private SelectExpression selectExpression;
/* 13:   */   
/* 14:   */   public void setSelectExpression(SelectExpression selectExpression)
/* 15:   */     throws SemanticException
/* 16:   */   {
/* 17:55 */     if ((selectExpression == null) || (selectExpression.getAlias() == null)) {
/* 18:56 */       throw new SemanticException("A ResultVariableRefNode must refer to a non-null alias.");
/* 19:   */     }
/* 20:58 */     this.selectExpression = selectExpression;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getRenderText(SessionFactoryImplementor sessionFactory)
/* 24:   */   {
/* 25:66 */     int scalarColumnIndex = this.selectExpression.getScalarColumnIndex();
/* 26:67 */     if (scalarColumnIndex < 0) {
/* 27:68 */       throw new IllegalStateException("selectExpression.getScalarColumnIndex() must be >= 0; actual = " + scalarColumnIndex);
/* 28:   */     }
/* 29:72 */     return sessionFactory.getDialect().replaceResultVariableInOrderByClauseWithPosition() ? getColumnPositionsString(scalarColumnIndex) : getColumnNamesString(scalarColumnIndex);
/* 30:   */   }
/* 31:   */   
/* 32:   */   private String getColumnPositionsString(int scalarColumnIndex)
/* 33:   */   {
/* 34:79 */     int startPosition = getWalker().getSelectClause().getColumnNamesStartPosition(scalarColumnIndex);
/* 35:80 */     StringBuffer buf = new StringBuffer();
/* 36:81 */     int nColumns = getWalker().getSelectClause().getColumnNames()[scalarColumnIndex].length;
/* 37:82 */     for (int i = startPosition; i < startPosition + nColumns; i++)
/* 38:   */     {
/* 39:83 */       if (i > startPosition) {
/* 40:84 */         buf.append(", ");
/* 41:   */       }
/* 42:86 */       buf.append(i);
/* 43:   */     }
/* 44:88 */     return buf.toString();
/* 45:   */   }
/* 46:   */   
/* 47:   */   private String getColumnNamesString(int scalarColumnIndex)
/* 48:   */   {
/* 49:92 */     return StringHelper.join(", ", getWalker().getSelectClause().getColumnNames()[scalarColumnIndex]);
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ResultVariableRefNode
 * JD-Core Version:    0.7.0.1
 */