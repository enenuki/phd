/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.type.StandardBasicTypes;
/*  8:   */ import org.hibernate.type.Type;
/*  9:   */ 
/* 10:   */ public class ConvertFunction
/* 11:   */   implements SQLFunction
/* 12:   */ {
/* 13:   */   public boolean hasArguments()
/* 14:   */   {
/* 15:41 */     return true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean hasParenthesesIfNoArguments()
/* 19:   */   {
/* 20:45 */     return true;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Type getReturnType(Type firstArgumentType, Mapping mapping)
/* 24:   */     throws QueryException
/* 25:   */   {
/* 26:49 */     return StandardBasicTypes.STRING;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String render(Type firstArgumentType, List args, SessionFactoryImplementor factory)
/* 30:   */     throws QueryException
/* 31:   */   {
/* 32:53 */     if ((args.size() != 2) && (args.size() != 3)) {
/* 33:54 */       throw new QueryException("convert() requires two or three arguments");
/* 34:   */     }
/* 35:56 */     String type = (String)args.get(1);
/* 36:58 */     if (args.size() == 2) {
/* 37:59 */       return "{fn convert(" + args.get(0) + " , " + type + ")}";
/* 38:   */     }
/* 39:62 */     return "convert(" + args.get(0) + " , " + type + "," + args.get(2) + ")";
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.ConvertFunction
 * JD-Core Version:    0.7.0.1
 */