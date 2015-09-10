/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ import org.hibernate.hql.internal.NameGenerator;
/*  6:   */ import org.hibernate.hql.internal.ast.tree.HqlSqlWalkerNode;
/*  7:   */ 
/*  8:   */ public final class ColumnHelper
/*  9:   */ {
/* 10:   */   public static void generateSingleScalarColumn(HqlSqlWalkerNode node, int i)
/* 11:   */   {
/* 12:47 */     ASTFactory factory = node.getASTFactory();
/* 13:48 */     ASTUtil.createSibling(factory, 143, " as " + NameGenerator.scalarName(i, 0), node);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static void generateScalarColumns(HqlSqlWalkerNode node, String[] sqlColumns, int i)
/* 17:   */   {
/* 18:55 */     if (sqlColumns.length == 1)
/* 19:   */     {
/* 20:56 */       generateSingleScalarColumn(node, i);
/* 21:   */     }
/* 22:   */     else
/* 23:   */     {
/* 24:59 */       ASTFactory factory = node.getASTFactory();
/* 25:60 */       AST n = node;
/* 26:61 */       n.setText(sqlColumns[0]);
/* 27:63 */       for (int j = 0; j < sqlColumns.length; j++)
/* 28:   */       {
/* 29:64 */         if (j > 0) {
/* 30:65 */           n = ASTUtil.createSibling(factory, 142, sqlColumns[j], n);
/* 31:   */         }
/* 32:67 */         n = ASTUtil.createSibling(factory, 143, " as " + NameGenerator.scalarName(i, j), n);
/* 33:   */       }
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.ColumnHelper
 * JD-Core Version:    0.7.0.1
 */