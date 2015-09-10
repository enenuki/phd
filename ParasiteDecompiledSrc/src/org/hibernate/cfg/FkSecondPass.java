/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.atomic.AtomicInteger;
/*  4:   */ import org.hibernate.mapping.SimpleValue;
/*  5:   */ import org.hibernate.mapping.Value;
/*  6:   */ 
/*  7:   */ public abstract class FkSecondPass
/*  8:   */   implements SecondPass
/*  9:   */ {
/* 10:   */   protected SimpleValue value;
/* 11:   */   protected Ejb3JoinColumn[] columns;
/* 12:   */   private int uniqueCounter;
/* 13:43 */   private static AtomicInteger globalCounter = new AtomicInteger();
/* 14:   */   
/* 15:   */   public FkSecondPass(SimpleValue value, Ejb3JoinColumn[] columns)
/* 16:   */   {
/* 17:46 */     this.value = value;
/* 18:47 */     this.columns = columns;
/* 19:48 */     this.uniqueCounter = globalCounter.getAndIncrement();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getUniqueCounter()
/* 23:   */   {
/* 24:52 */     return this.uniqueCounter;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Value getValue()
/* 28:   */   {
/* 29:56 */     return this.value;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean equals(Object o)
/* 33:   */   {
/* 34:60 */     if (this == o) {
/* 35:60 */       return true;
/* 36:   */     }
/* 37:61 */     if (!(o instanceof FkSecondPass)) {
/* 38:61 */       return false;
/* 39:   */     }
/* 40:63 */     FkSecondPass that = (FkSecondPass)o;
/* 41:65 */     if (this.uniqueCounter != that.uniqueCounter) {
/* 42:65 */       return false;
/* 43:   */     }
/* 44:67 */     return true;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int hashCode()
/* 48:   */   {
/* 49:71 */     return this.uniqueCounter;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public abstract String getReferencedEntityName();
/* 53:   */   
/* 54:   */   public abstract boolean isInPrimaryKey();
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.FkSecondPass
 * JD-Core Version:    0.7.0.1
 */