/*  1:   */ package org.hibernate.engine.query.spi;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class ReturnMetadata
/*  7:   */   implements Serializable
/*  8:   */ {
/*  9:   */   private final String[] returnAliases;
/* 10:   */   private final Type[] returnTypes;
/* 11:   */   
/* 12:   */   public ReturnMetadata(String[] returnAliases, Type[] returnTypes)
/* 13:   */   {
/* 14:38 */     this.returnAliases = returnAliases;
/* 15:39 */     this.returnTypes = returnTypes;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String[] getReturnAliases()
/* 19:   */   {
/* 20:43 */     return this.returnAliases;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Type[] getReturnTypes()
/* 24:   */   {
/* 25:47 */     return this.returnTypes;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.ReturnMetadata
 * JD-Core Version:    0.7.0.1
 */