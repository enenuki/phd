/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class SQLFunctionTemplate
/* 10:   */   implements SQLFunction
/* 11:   */ {
/* 12:   */   private final Type type;
/* 13:   */   private final TemplateRenderer renderer;
/* 14:   */   private final boolean hasParenthesesIfNoArgs;
/* 15:   */   
/* 16:   */   public SQLFunctionTemplate(Type type, String template)
/* 17:   */   {
/* 18:48 */     this(type, template, true);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SQLFunctionTemplate(Type type, String template, boolean hasParenthesesIfNoArgs)
/* 22:   */   {
/* 23:52 */     this.type = type;
/* 24:53 */     this.renderer = new TemplateRenderer(template);
/* 25:54 */     this.hasParenthesesIfNoArgs = hasParenthesesIfNoArgs;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String render(Type argumentType, List args, SessionFactoryImplementor factory)
/* 29:   */   {
/* 30:61 */     return this.renderer.render(args, factory);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Type getReturnType(Type argumentType, Mapping mapping)
/* 34:   */     throws QueryException
/* 35:   */   {
/* 36:68 */     return this.type;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean hasArguments()
/* 40:   */   {
/* 41:75 */     return this.renderer.getAnticipatedNumberOfArguments() > 0;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean hasParenthesesIfNoArguments()
/* 45:   */   {
/* 46:82 */     return this.hasParenthesesIfNoArgs;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:89 */     return this.renderer.getTemplate();
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.SQLFunctionTemplate
 * JD-Core Version:    0.7.0.1
 */