/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class NvlFunction
/* 10:   */   implements SQLFunction
/* 11:   */ {
/* 12:   */   public boolean hasArguments()
/* 13:   */   {
/* 14:39 */     return true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean hasParenthesesIfNoArguments()
/* 18:   */   {
/* 19:43 */     return true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Type getReturnType(Type argumentType, Mapping mapping)
/* 23:   */     throws QueryException
/* 24:   */   {
/* 25:47 */     return argumentType;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String render(Type argumentType, List args, SessionFactoryImplementor factory)
/* 29:   */     throws QueryException
/* 30:   */   {
/* 31:51 */     int lastIndex = args.size() - 1;
/* 32:52 */     Object last = args.remove(lastIndex);
/* 33:53 */     if (lastIndex == 0) {
/* 34:54 */       return last.toString();
/* 35:   */     }
/* 36:56 */     Object secondLast = args.get(lastIndex - 1);
/* 37:57 */     String nvl = "nvl(" + secondLast + ", " + last + ")";
/* 38:58 */     args.set(lastIndex - 1, nvl);
/* 39:59 */     return render(argumentType, args, factory);
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.NvlFunction
 * JD-Core Version:    0.7.0.1
 */