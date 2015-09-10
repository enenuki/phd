/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*  5:   */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  6:   */ 
/*  7:   */ public class OrderByClause
/*  8:   */   extends HqlSqlWalkerNode
/*  9:   */   implements HqlSqlTokenTypes
/* 10:   */ {
/* 11:   */   public void addOrderFragment(String orderByFragment)
/* 12:   */   {
/* 13:39 */     AST fragment = ASTUtil.create(getASTFactory(), 142, orderByFragment);
/* 14:40 */     if (getFirstChild() == null) {
/* 15:41 */       setFirstChild(fragment);
/* 16:   */     } else {
/* 17:44 */       addChild(fragment);
/* 18:   */     }
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.OrderByClause
 * JD-Core Version:    0.7.0.1
 */