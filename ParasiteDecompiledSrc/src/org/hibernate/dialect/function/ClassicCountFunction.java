/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.Mapping;
/*  4:   */ import org.hibernate.type.StandardBasicTypes;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class ClassicCountFunction
/*  8:   */   extends StandardSQLFunction
/*  9:   */ {
/* 10:   */   public ClassicCountFunction()
/* 11:   */   {
/* 12:37 */     super("count");
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Type getReturnType(Type columnType, Mapping mapping)
/* 16:   */   {
/* 17:41 */     return StandardBasicTypes.INTEGER;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.ClassicCountFunction
 * JD-Core Version:    0.7.0.1
 */