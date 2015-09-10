/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class Case2Node
/*  9:   */   extends AbstractSelectExpression
/* 10:   */   implements SelectExpression
/* 11:   */ {
/* 12:   */   public Type getDataType()
/* 13:   */   {
/* 14:39 */     return getFirstThenNode().getDataType();
/* 15:   */   }
/* 16:   */   
/* 17:   */   private SelectExpression getFirstThenNode()
/* 18:   */   {
/* 19:43 */     return (SelectExpression)getFirstChild().getNextSibling().getFirstChild().getNextSibling();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setScalarColumnText(int i)
/* 23:   */     throws SemanticException
/* 24:   */   {
/* 25:47 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.Case2Node
 * JD-Core Version:    0.7.0.1
 */