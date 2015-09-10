/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.type.StandardBasicTypes;
/*  8:   */ import org.hibernate.type.Type;
/*  9:   */ 
/* 10:   */ public class PositionSubstringFunction
/* 11:   */   implements SQLFunction
/* 12:   */ {
/* 13:   */   public boolean hasArguments()
/* 14:   */   {
/* 15:39 */     return true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean hasParenthesesIfNoArguments()
/* 19:   */   {
/* 20:43 */     return true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Type getReturnType(Type firstArgumentType, Mapping mapping)
/* 24:   */     throws QueryException
/* 25:   */   {
/* 26:47 */     return StandardBasicTypes.INTEGER;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String render(Type firstArgumentType, List args, SessionFactoryImplementor factory)
/* 30:   */     throws QueryException
/* 31:   */   {
/* 32:51 */     boolean threeArgs = args.size() > 2;
/* 33:52 */     Object pattern = args.get(0);
/* 34:53 */     Object string = args.get(1);
/* 35:54 */     Object start = threeArgs ? args.get(2) : null;
/* 36:   */     
/* 37:56 */     StringBuffer buf = new StringBuffer();
/* 38:57 */     if (threeArgs) {
/* 39:57 */       buf.append('(');
/* 40:   */     }
/* 41:58 */     buf.append("position(").append(pattern).append(" in ");
/* 42:59 */     if (threeArgs) {
/* 43:59 */       buf.append("substring(");
/* 44:   */     }
/* 45:60 */     buf.append(string);
/* 46:61 */     if (threeArgs) {
/* 47:61 */       buf.append(", ").append(start).append(')');
/* 48:   */     }
/* 49:62 */     buf.append(')');
/* 50:63 */     if (threeArgs) {
/* 51:63 */       buf.append('+').append(start).append("-1)");
/* 52:   */     }
/* 53:64 */     return buf.toString();
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.PositionSubstringFunction
 * JD-Core Version:    0.7.0.1
 */