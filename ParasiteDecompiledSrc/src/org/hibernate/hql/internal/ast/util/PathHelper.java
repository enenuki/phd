/*  1:   */ package org.hibernate.hql.internal.ast.util;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ import org.hibernate.internal.CoreMessageLogger;
/*  6:   */ import org.hibernate.internal.util.StringHelper;
/*  7:   */ import org.jboss.logging.Logger;
/*  8:   */ 
/*  9:   */ public final class PathHelper
/* 10:   */ {
/* 11:42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PathHelper.class.getName());
/* 12:   */   
/* 13:   */   public static AST parsePath(String path, ASTFactory factory)
/* 14:   */   {
/* 15:55 */     String[] identifiers = StringHelper.split(".", path);
/* 16:56 */     AST lhs = null;
/* 17:57 */     for (int i = 0; i < identifiers.length; i++)
/* 18:   */     {
/* 19:58 */       String identifier = identifiers[i];
/* 20:59 */       AST child = ASTUtil.create(factory, 126, identifier);
/* 21:60 */       if (i == 0) {
/* 22:61 */         lhs = child;
/* 23:   */       } else {
/* 24:64 */         lhs = ASTUtil.createBinarySubtree(factory, 15, ".", lhs, child);
/* 25:   */       }
/* 26:   */     }
/* 27:67 */     if (LOG.isDebugEnabled()) {
/* 28:68 */       LOG.debugf("parsePath() : %s -> %s", path, ASTUtil.getDebugString(lhs));
/* 29:   */     }
/* 30:70 */     return lhs;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static String getAlias(String path)
/* 34:   */   {
/* 35:74 */     return StringHelper.root(path);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.PathHelper
 * JD-Core Version:    0.7.0.1
 */