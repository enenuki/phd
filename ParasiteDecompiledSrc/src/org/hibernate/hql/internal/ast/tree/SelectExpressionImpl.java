/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ 
/*  6:   */ public class SelectExpressionImpl
/*  7:   */   extends FromReferenceNode
/*  8:   */   implements SelectExpression
/*  9:   */ {
/* 10:   */   public void resolveIndex(AST parent)
/* 11:   */     throws SemanticException
/* 12:   */   {
/* 13:37 */     throw new UnsupportedOperationException();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setScalarColumnText(int i)
/* 17:   */     throws SemanticException
/* 18:   */   {
/* 19:41 */     String text = getFromElement().renderScalarIdentifierSelect(i);
/* 20:42 */     setText(text);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void resolve(boolean generateJoin, boolean implicitJoin, String classAlias, AST parent)
/* 24:   */     throws SemanticException
/* 25:   */   {}
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SelectExpressionImpl
 * JD-Core Version:    0.7.0.1
 */