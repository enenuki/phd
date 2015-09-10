/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ import org.hibernate.hql.internal.ast.tree.Node;
/*  5:   */ 
/*  6:   */ public class HqlASTFactory
/*  7:   */   extends ASTFactory
/*  8:   */ {
/*  9:   */   public Class getASTNodeType(int tokenType)
/* 10:   */   {
/* 11:44 */     return Node.class;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.HqlASTFactory
 * JD-Core Version:    0.7.0.1
 */