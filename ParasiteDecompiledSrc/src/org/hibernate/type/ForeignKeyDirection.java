/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public abstract class ForeignKeyDirection
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:45 */   public static final ForeignKeyDirection FOREIGN_KEY_TO_PARENT = new ForeignKeyDirection()
/*  9:   */   {
/* 10:   */     public boolean cascadeNow(int cascadePoint)
/* 11:   */     {
/* 12:47 */       return cascadePoint != 2;
/* 13:   */     }
/* 14:   */     
/* 15:   */     public String toString()
/* 16:   */     {
/* 17:51 */       return "toParent";
/* 18:   */     }
/* 19:   */     
/* 20:   */     Object readResolve()
/* 21:   */     {
/* 22:55 */       return FOREIGN_KEY_TO_PARENT;
/* 23:   */     }
/* 24:   */   };
/* 25:61 */   public static final ForeignKeyDirection FOREIGN_KEY_FROM_PARENT = new ForeignKeyDirection()
/* 26:   */   {
/* 27:   */     public boolean cascadeNow(int cascadePoint)
/* 28:   */     {
/* 29:63 */       return cascadePoint != 1;
/* 30:   */     }
/* 31:   */     
/* 32:   */     public String toString()
/* 33:   */     {
/* 34:67 */       return "fromParent";
/* 35:   */     }
/* 36:   */     
/* 37:   */     Object readResolve()
/* 38:   */     {
/* 39:71 */       return FOREIGN_KEY_FROM_PARENT;
/* 40:   */     }
/* 41:   */   };
/* 42:   */   
/* 43:   */   public abstract boolean cascadeNow(int paramInt);
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ForeignKeyDirection
 * JD-Core Version:    0.7.0.1
 */