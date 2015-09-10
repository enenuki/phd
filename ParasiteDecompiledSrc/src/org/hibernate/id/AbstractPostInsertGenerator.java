/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  5:   */ 
/*  6:   */ public abstract class AbstractPostInsertGenerator
/*  7:   */   implements PostInsertIdentifierGenerator
/*  8:   */ {
/*  9:   */   public Serializable generate(SessionImplementor s, Object obj)
/* 10:   */   {
/* 11:41 */     return IdentifierGeneratorHelper.POST_INSERT_INDICATOR;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.AbstractPostInsertGenerator
 * JD-Core Version:    0.7.0.1
 */