/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class NoArgSQLFunction
/* 10:   */   implements SQLFunction
/* 11:   */ {
/* 12:   */   private Type returnType;
/* 13:   */   private boolean hasParenthesesIfNoArguments;
/* 14:   */   private String name;
/* 15:   */   
/* 16:   */   public NoArgSQLFunction(String name, Type returnType)
/* 17:   */   {
/* 18:43 */     this(name, returnType, true);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public NoArgSQLFunction(String name, Type returnType, boolean hasParenthesesIfNoArguments)
/* 22:   */   {
/* 23:47 */     this.returnType = returnType;
/* 24:48 */     this.hasParenthesesIfNoArguments = hasParenthesesIfNoArguments;
/* 25:49 */     this.name = name;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean hasArguments()
/* 29:   */   {
/* 30:53 */     return false;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean hasParenthesesIfNoArguments()
/* 34:   */   {
/* 35:57 */     return this.hasParenthesesIfNoArguments;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Type getReturnType(Type argumentType, Mapping mapping)
/* 39:   */     throws QueryException
/* 40:   */   {
/* 41:61 */     return this.returnType;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String render(Type argumentType, List args, SessionFactoryImplementor factory)
/* 45:   */     throws QueryException
/* 46:   */   {
/* 47:65 */     if (args.size() > 0) {
/* 48:66 */       throw new QueryException("function takes no arguments: " + this.name);
/* 49:   */     }
/* 50:68 */     return this.hasParenthesesIfNoArguments ? this.name + "()" : this.name;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.NoArgSQLFunction
 * JD-Core Version:    0.7.0.1
 */