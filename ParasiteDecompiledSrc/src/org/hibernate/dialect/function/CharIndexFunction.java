/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.type.StandardBasicTypes;
/*  8:   */ import org.hibernate.type.Type;
/*  9:   */ 
/* 10:   */ public class CharIndexFunction
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
/* 23:   */   public Type getReturnType(Type columnType, Mapping mapping)
/* 24:   */     throws QueryException
/* 25:   */   {
/* 26:47 */     return StandardBasicTypes.INTEGER;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String render(Type columnType, List args, SessionFactoryImplementor factory)
/* 30:   */     throws QueryException
/* 31:   */   {
/* 32:51 */     boolean threeArgs = args.size() > 2;
/* 33:52 */     Object pattern = args.get(0);
/* 34:53 */     Object string = args.get(1);
/* 35:54 */     Object start = threeArgs ? args.get(2) : null;
/* 36:   */     
/* 37:56 */     StringBuffer buf = new StringBuffer();
/* 38:57 */     buf.append("charindex(").append(pattern).append(", ");
/* 39:58 */     if (threeArgs) {
/* 40:58 */       buf.append("right(");
/* 41:   */     }
/* 42:59 */     buf.append(string);
/* 43:60 */     if (threeArgs) {
/* 44:60 */       buf.append(", char_length(").append(string).append(")-(").append(start).append("-1))");
/* 45:   */     }
/* 46:61 */     buf.append(')');
/* 47:62 */     return buf.toString();
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.CharIndexFunction
 * JD-Core Version:    0.7.0.1
 */