/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ 
/*  7:   */ public class SQLFunctionRegistry
/*  8:   */ {
/*  9:   */   private final Dialect dialect;
/* 10:   */   private final Map<String, SQLFunction> userFunctions;
/* 11:   */   
/* 12:   */   public SQLFunctionRegistry(Dialect dialect, Map<String, SQLFunction> userFunctions)
/* 13:   */   {
/* 14:35 */     this.dialect = dialect;
/* 15:36 */     this.userFunctions = new HashMap();
/* 16:37 */     this.userFunctions.putAll(userFunctions);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public SQLFunction findSQLFunction(String functionName)
/* 20:   */   {
/* 21:42 */     String name = functionName.toLowerCase();
/* 22:43 */     SQLFunction userFunction = (SQLFunction)this.userFunctions.get(name);
/* 23:44 */     return userFunction != null ? userFunction : (SQLFunction)this.dialect.getFunctions().get(name);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean hasFunction(String functionName)
/* 27:   */   {
/* 28:51 */     String name = functionName.toLowerCase();
/* 29:52 */     return (this.userFunctions.containsKey(name)) || (this.dialect.getFunctions().containsKey(name));
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.SQLFunctionRegistry
 * JD-Core Version:    0.7.0.1
 */