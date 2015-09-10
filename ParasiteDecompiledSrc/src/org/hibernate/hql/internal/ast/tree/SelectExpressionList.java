/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import org.hibernate.hql.internal.antlr.SqlTokenTypes;
/*  6:   */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  7:   */ 
/*  8:   */ public abstract class SelectExpressionList
/*  9:   */   extends HqlSqlWalkerNode
/* 10:   */ {
/* 11:   */   public SelectExpression[] collectSelectExpressions()
/* 12:   */   {
/* 13:47 */     AST firstChild = getFirstSelectExpression();
/* 14:48 */     AST parent = this;
/* 15:49 */     ArrayList list = new ArrayList(parent.getNumberOfChildren());
/* 16:50 */     for (AST n = firstChild; n != null; n = n.getNextSibling()) {
/* 17:51 */       if ((n instanceof SelectExpression)) {
/* 18:52 */         list.add(n);
/* 19:   */       } else {
/* 20:55 */         throw new IllegalStateException("Unexpected AST: " + n.getClass().getName() + " " + new ASTPrinter(SqlTokenTypes.class).showAsString(n, ""));
/* 21:   */       }
/* 22:   */     }
/* 23:58 */     return (SelectExpression[])list.toArray(new SelectExpression[list.size()]);
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected abstract AST getFirstSelectExpression();
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SelectExpressionList
 * JD-Core Version:    0.7.0.1
 */