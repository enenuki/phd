/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  5:   */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ 
/*  8:   */ public abstract class AbstractRestrictableStatement
/*  9:   */   extends AbstractStatement
/* 10:   */   implements RestrictableStatement
/* 11:   */ {
/* 12:   */   private FromClause fromClause;
/* 13:   */   private AST whereClause;
/* 14:   */   
/* 15:   */   protected abstract int getWhereClauseParentTokenType();
/* 16:   */   
/* 17:   */   protected abstract CoreMessageLogger getLog();
/* 18:   */   
/* 19:   */   public final FromClause getFromClause()
/* 20:   */   {
/* 21:51 */     if (this.fromClause == null) {
/* 22:52 */       this.fromClause = ((FromClause)ASTUtil.findTypeInChildren(this, 22));
/* 23:   */     }
/* 24:54 */     return this.fromClause;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public final boolean hasWhereClause()
/* 28:   */   {
/* 29:61 */     AST whereClause = locateWhereClause();
/* 30:62 */     return (whereClause != null) && (whereClause.getNumberOfChildren() > 0);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public final AST getWhereClause()
/* 34:   */   {
/* 35:69 */     if (this.whereClause == null)
/* 36:   */     {
/* 37:70 */       this.whereClause = locateWhereClause();
/* 38:72 */       if (this.whereClause == null)
/* 39:   */       {
/* 40:73 */         getLog().debug("getWhereClause() : Creating a new WHERE clause...");
/* 41:74 */         this.whereClause = ASTUtil.create(getWalker().getASTFactory(), 53, "WHERE");
/* 42:   */         
/* 43:76 */         AST parent = ASTUtil.findTypeInChildren(this, getWhereClauseParentTokenType());
/* 44:77 */         this.whereClause.setNextSibling(parent.getNextSibling());
/* 45:78 */         parent.setNextSibling(this.whereClause);
/* 46:   */       }
/* 47:   */     }
/* 48:81 */     return this.whereClause;
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected AST locateWhereClause()
/* 52:   */   {
/* 53:85 */     return ASTUtil.findTypeInChildren(this, 53);
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AbstractRestrictableStatement
 * JD-Core Version:    0.7.0.1
 */