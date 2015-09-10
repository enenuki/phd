/*  1:   */ package org.hibernate.bytecode.instrumentation.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  5:   */ 
/*  6:   */ public abstract interface LazyPropertyInitializer
/*  7:   */ {
/*  8:40 */   public static final Serializable UNFETCHED_PROPERTY = new Serializable()
/*  9:   */   {
/* 10:   */     public String toString()
/* 11:   */     {
/* 12:42 */       return "<lazy>";
/* 13:   */     }
/* 14:   */     
/* 15:   */     public Object readResolve()
/* 16:   */     {
/* 17:45 */       return LazyPropertyInitializer.UNFETCHED_PROPERTY;
/* 18:   */     }
/* 19:   */   };
/* 20:   */   
/* 21:   */   public abstract Object initializeLazyProperty(String paramString, Object paramObject, SessionImplementor paramSessionImplementor);
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer
 * JD-Core Version:    0.7.0.1
 */