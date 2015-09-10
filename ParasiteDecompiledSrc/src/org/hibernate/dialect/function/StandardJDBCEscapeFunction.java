/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class StandardJDBCEscapeFunction
/*  8:   */   extends StandardSQLFunction
/*  9:   */ {
/* 10:   */   public StandardJDBCEscapeFunction(String name)
/* 11:   */   {
/* 12:39 */     super(name);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public StandardJDBCEscapeFunction(String name, Type typeValue)
/* 16:   */   {
/* 17:43 */     super(name, typeValue);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String render(Type argumentType, List args, SessionFactoryImplementor factory)
/* 21:   */   {
/* 22:47 */     return "{fn " + super.render(argumentType, args, factory) + "}";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:51 */     return "{fn " + getName() + "...}";
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.StandardJDBCEscapeFunction
 * JD-Core Version:    0.7.0.1
 */