/*  1:   */ package org.hibernate.sql.ordering.antlr;
/*  2:   */ 
/*  3:   */ import antlr.TreeParserSharedInputState;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ import org.jboss.logging.Logger;
/*  9:   */ 
/* 10:   */ public class OrderByFragmentRenderer
/* 11:   */   extends GeneratedOrderByFragmentRenderer
/* 12:   */ {
/* 13:40 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, OrderByFragmentRenderer.class.getName());
/* 14:41 */   private static final ASTPrinter printer = new ASTPrinter(GeneratedOrderByFragmentRendererTokenTypes.class);
/* 15:   */   
/* 16:   */   protected void out(AST ast)
/* 17:   */   {
/* 18:45 */     out(((Node)ast).getRenderableText());
/* 19:   */   }
/* 20:   */   
/* 21:51 */   private int traceDepth = 0;
/* 22:   */   
/* 23:   */   public void traceIn(String ruleName, AST tree)
/* 24:   */   {
/* 25:55 */     if (this.inputState.guessing > 0) {
/* 26:56 */       return;
/* 27:   */     }
/* 28:58 */     String prefix = StringHelper.repeat('-', this.traceDepth++ * 2) + "-> ";
/* 29:59 */     String traceText = ruleName + " (" + buildTraceNodeName(tree) + ")";
/* 30:60 */     LOG.trace(prefix + traceText);
/* 31:   */   }
/* 32:   */   
/* 33:   */   private String buildTraceNodeName(AST tree)
/* 34:   */   {
/* 35:64 */     return tree.getText() + " [" + printer.getTokenTypeName(tree.getType()) + "]";
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void traceOut(String ruleName, AST tree)
/* 39:   */   {
/* 40:71 */     if (this.inputState.guessing > 0) {
/* 41:72 */       return;
/* 42:   */     }
/* 43:74 */     String prefix = "<-" + StringHelper.repeat('-', --this.traceDepth * 2) + " ";
/* 44:75 */     LOG.trace(prefix + ruleName);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.OrderByFragmentRenderer
 * JD-Core Version:    0.7.0.1
 */