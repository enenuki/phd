/*   1:    */ package org.hibernate.dialect.function;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.engine.spi.Mapping;
/*   5:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   6:    */ import org.hibernate.type.Type;
/*   7:    */ 
/*   8:    */ public class StandardSQLFunction
/*   9:    */   implements SQLFunction
/*  10:    */ {
/*  11:    */   private final String name;
/*  12:    */   private final Type registeredType;
/*  13:    */   
/*  14:    */   public StandardSQLFunction(String name)
/*  15:    */   {
/*  16: 54 */     this(name, null);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public StandardSQLFunction(String name, Type registeredType)
/*  20:    */   {
/*  21: 64 */     this.name = name;
/*  22: 65 */     this.registeredType = registeredType;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getName()
/*  26:    */   {
/*  27: 74 */     return this.name;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Type getType()
/*  31:    */   {
/*  32: 84 */     return this.registeredType;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean hasArguments()
/*  36:    */   {
/*  37: 91 */     return true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean hasParenthesesIfNoArguments()
/*  41:    */   {
/*  42: 98 */     return true;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Type getReturnType(Type firstArgumentType, Mapping mapping)
/*  46:    */   {
/*  47:105 */     return this.registeredType == null ? firstArgumentType : this.registeredType;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor sessionFactory)
/*  51:    */   {
/*  52:112 */     StringBuilder buf = new StringBuilder();
/*  53:113 */     buf.append(this.name).append('(');
/*  54:114 */     for (int i = 0; i < arguments.size(); i++)
/*  55:    */     {
/*  56:115 */       buf.append(arguments.get(i));
/*  57:116 */       if (i < arguments.size() - 1) {
/*  58:117 */         buf.append(", ");
/*  59:    */       }
/*  60:    */     }
/*  61:120 */     return ')';
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String toString()
/*  65:    */   {
/*  66:124 */     return this.name;
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.StandardSQLFunction
 * JD-Core Version:    0.7.0.1
 */