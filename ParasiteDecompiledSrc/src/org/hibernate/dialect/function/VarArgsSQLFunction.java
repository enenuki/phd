/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.QueryException;
/*   5:    */ import org.hibernate.engine.spi.Mapping;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.type.Type;
/*   8:    */ 
/*   9:    */ public class VarArgsSQLFunction
/*  10:    */   implements SQLFunction
/*  11:    */ {
/*  12:    */   private final String begin;
/*  13:    */   private final String sep;
/*  14:    */   private final String end;
/*  15:    */   private final Type registeredType;
/*  16:    */   
/*  17:    */   public VarArgsSQLFunction(Type registeredType, String begin, String sep, String end)
/*  18:    */   {
/*  19: 54 */     this.registeredType = registeredType;
/*  20: 55 */     this.begin = begin;
/*  21: 56 */     this.sep = sep;
/*  22: 57 */     this.end = end;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public VarArgsSQLFunction(String begin, String sep, String end)
/*  26:    */   {
/*  27: 73 */     this(null, begin, sep, end);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean hasArguments()
/*  31:    */   {
/*  32: 82 */     return true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean hasParenthesesIfNoArguments()
/*  36:    */   {
/*  37: 91 */     return true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Type getReturnType(Type firstArgumentType, Mapping mapping)
/*  41:    */     throws QueryException
/*  42:    */   {
/*  43: 98 */     return this.registeredType == null ? firstArgumentType : this.registeredType;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory)
/*  47:    */   {
/*  48:102 */     StringBuffer buf = new StringBuffer().append(this.begin);
/*  49:103 */     for (int i = 0; i < arguments.size(); i++)
/*  50:    */     {
/*  51:104 */       buf.append(transformArgument((String)arguments.get(i)));
/*  52:105 */       if (i < arguments.size() - 1) {
/*  53:106 */         buf.append(this.sep);
/*  54:    */       }
/*  55:    */     }
/*  56:109 */     return this.end;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected String transformArgument(String argument)
/*  60:    */   {
/*  61:120 */     return argument;
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.VarArgsSQLFunction
 * JD-Core Version:    0.7.0.1
 */