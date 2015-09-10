/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  5:   */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class CountNode
/*  9:   */   extends AbstractSelectExpression
/* 10:   */   implements SelectExpression
/* 11:   */ {
/* 12:   */   public Type getDataType()
/* 13:   */   {
/* 14:39 */     return getSessionFactoryHelper().findFunctionReturnType(getText(), null);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setScalarColumnText(int i)
/* 18:   */     throws SemanticException
/* 19:   */   {
/* 20:43 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.CountNode
 * JD-Core Version:    0.7.0.1
 */