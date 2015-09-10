/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ 
/*  6:   */ public class CollectionFunction
/*  7:   */   extends MethodNode
/*  8:   */   implements DisplayableNode
/*  9:   */ {
/* 10:   */   public void resolve(boolean inSelect)
/* 11:   */     throws SemanticException
/* 12:   */   {
/* 13:36 */     initializeMethodNode(this, inSelect);
/* 14:37 */     if (!isCollectionPropertyMethod()) {
/* 15:38 */       throw new SemanticException(getText() + " is not a collection property name!");
/* 16:   */     }
/* 17:40 */     AST expr = getFirstChild();
/* 18:41 */     if (expr == null) {
/* 19:42 */       throw new SemanticException(getText() + " requires a path!");
/* 20:   */     }
/* 21:44 */     resolveCollectionProperty(expr);
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected void prepareSelectColumns(String[] selectColumns)
/* 25:   */   {
/* 26:49 */     String subselect = selectColumns[0].trim();
/* 27:50 */     if ((subselect.startsWith("(")) && (subselect.endsWith(")"))) {
/* 28:51 */       subselect = subselect.substring(1, subselect.length() - 1);
/* 29:   */     }
/* 30:53 */     selectColumns[0] = subselect;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.CollectionFunction
 * JD-Core Version:    0.7.0.1
 */