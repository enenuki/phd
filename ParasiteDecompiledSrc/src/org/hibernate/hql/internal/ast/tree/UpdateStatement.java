/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.collections.AST;
/*  4:   */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  5:   */ import org.hibernate.internal.CoreMessageLogger;
/*  6:   */ import org.jboss.logging.Logger;
/*  7:   */ 
/*  8:   */ public class UpdateStatement
/*  9:   */   extends AbstractRestrictableStatement
/* 10:   */ {
/* 11:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, UpdateStatement.class.getName());
/* 12:   */   
/* 13:   */   public int getStatementType()
/* 14:   */   {
/* 15:47 */     return 51;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean needsExecutor()
/* 19:   */   {
/* 20:54 */     return true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected int getWhereClauseParentTokenType()
/* 24:   */   {
/* 25:59 */     return 46;
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected CoreMessageLogger getLog()
/* 29:   */   {
/* 30:64 */     return LOG;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public AST getSetClause()
/* 34:   */   {
/* 35:68 */     return ASTUtil.findTypeInChildren(this, 46);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.UpdateStatement
 * JD-Core Version:    0.7.0.1
 */