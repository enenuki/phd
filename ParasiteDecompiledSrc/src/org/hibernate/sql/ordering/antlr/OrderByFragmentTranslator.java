/*  1:   */ package org.hibernate.sql.ordering.antlr;
/*  2:   */ 
/*  3:   */ import java.io.StringReader;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.hql.internal.ast.util.ASTPrinter;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ import org.jboss.logging.Logger;
/*  8:   */ 
/*  9:   */ public class OrderByFragmentTranslator
/* 10:   */ {
/* 11:41 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, OrderByFragmentTranslator.class.getName());
/* 12:   */   public final TranslationContext context;
/* 13:   */   
/* 14:   */   public OrderByFragmentTranslator(TranslationContext context)
/* 15:   */   {
/* 16:46 */     this.context = context;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String render(String fragment)
/* 20:   */   {
/* 21:57 */     GeneratedOrderByLexer lexer = new GeneratedOrderByLexer(new StringReader(fragment));
/* 22:58 */     OrderByFragmentParser parser = new OrderByFragmentParser(lexer, this.context);
/* 23:   */     try
/* 24:   */     {
/* 25:60 */       parser.orderByFragment();
/* 26:   */     }
/* 27:   */     catch (HibernateException e)
/* 28:   */     {
/* 29:63 */       throw e;
/* 30:   */     }
/* 31:   */     catch (Throwable t)
/* 32:   */     {
/* 33:66 */       throw new HibernateException("Unable to parse order-by fragment", t);
/* 34:   */     }
/* 35:69 */     if (LOG.isTraceEnabled())
/* 36:   */     {
/* 37:70 */       ASTPrinter printer = new ASTPrinter(OrderByTemplateTokenTypes.class);
/* 38:71 */       LOG.trace(printer.showAsString(parser.getAST(), "--- {order-by fragment} ---"));
/* 39:   */     }
/* 40:74 */     OrderByFragmentRenderer renderer = new OrderByFragmentRenderer();
/* 41:   */     try
/* 42:   */     {
/* 43:76 */       renderer.orderByFragment(parser.getAST());
/* 44:   */     }
/* 45:   */     catch (HibernateException e)
/* 46:   */     {
/* 47:79 */       throw e;
/* 48:   */     }
/* 49:   */     catch (Throwable t)
/* 50:   */     {
/* 51:82 */       throw new HibernateException("Unable to render parsed order-by fragment", t);
/* 52:   */     }
/* 53:85 */     return renderer.getRenderedFragment();
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.OrderByFragmentTranslator
 * JD-Core Version:    0.7.0.1
 */