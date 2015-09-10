/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.internal.CoreMessageLogger;
/*  4:   */ import org.jboss.logging.Logger;
/*  5:   */ 
/*  6:   */ public class DeleteStatement
/*  7:   */   extends AbstractRestrictableStatement
/*  8:   */ {
/*  9:39 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DeleteStatement.class.getName());
/* 10:   */   
/* 11:   */   public int getStatementType()
/* 12:   */   {
/* 13:45 */     return 13;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean needsExecutor()
/* 17:   */   {
/* 18:52 */     return true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected int getWhereClauseParentTokenType()
/* 22:   */   {
/* 23:57 */     return 22;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected CoreMessageLogger getLog()
/* 27:   */   {
/* 28:62 */     return LOG;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.DeleteStatement
 * JD-Core Version:    0.7.0.1
 */