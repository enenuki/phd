/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class ConditionalParenthesisFunction
/*  8:   */   extends StandardSQLFunction
/*  9:   */ {
/* 10:   */   public ConditionalParenthesisFunction(String name)
/* 11:   */   {
/* 12:39 */     super(name);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public ConditionalParenthesisFunction(String name, Type type)
/* 16:   */   {
/* 17:43 */     super(name, type);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean hasParenthesesIfNoArguments()
/* 21:   */   {
/* 22:47 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String render(List args, SessionFactoryImplementor factory)
/* 26:   */   {
/* 27:51 */     boolean hasArgs = !args.isEmpty();
/* 28:52 */     StringBuffer buf = new StringBuffer();
/* 29:53 */     buf.append(getName());
/* 30:54 */     if (hasArgs)
/* 31:   */     {
/* 32:55 */       buf.append("(");
/* 33:56 */       for (int i = 0; i < args.size(); i++)
/* 34:   */       {
/* 35:57 */         buf.append(args.get(i));
/* 36:58 */         if (i < args.size() - 1) {
/* 37:59 */           buf.append(", ");
/* 38:   */         }
/* 39:   */       }
/* 40:62 */       buf.append(")");
/* 41:   */     }
/* 42:64 */     return buf.toString();
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.ConditionalParenthesisFunction
 * JD-Core Version:    0.7.0.1
 */