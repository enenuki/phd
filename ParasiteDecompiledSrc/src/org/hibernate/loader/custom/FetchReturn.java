/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ 
/*  5:   */ public abstract class FetchReturn
/*  6:   */   extends NonScalarReturn
/*  7:   */ {
/*  8:   */   private final NonScalarReturn owner;
/*  9:   */   private final String ownerProperty;
/* 10:   */   
/* 11:   */   public FetchReturn(NonScalarReturn owner, String ownerProperty, String alias, LockMode lockMode)
/* 12:   */   {
/* 13:50 */     super(alias, lockMode);
/* 14:51 */     this.owner = owner;
/* 15:52 */     this.ownerProperty = ownerProperty;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public NonScalarReturn getOwner()
/* 19:   */   {
/* 20:61 */     return this.owner;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getOwnerProperty()
/* 24:   */   {
/* 25:70 */     return this.ownerProperty;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.FetchReturn
 * JD-Core Version:    0.7.0.1
 */