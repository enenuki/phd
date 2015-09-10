/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ import org.hibernate.loader.EntityAliases;
/*  5:   */ 
/*  6:   */ public class EntityFetchReturn
/*  7:   */   extends FetchReturn
/*  8:   */ {
/*  9:   */   private final EntityAliases entityAliases;
/* 10:   */   
/* 11:   */   public EntityFetchReturn(String alias, EntityAliases entityAliases, NonScalarReturn owner, String ownerProperty, LockMode lockMode)
/* 12:   */   {
/* 13:43 */     super(owner, ownerProperty, alias, lockMode);
/* 14:44 */     this.entityAliases = entityAliases;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public EntityAliases getEntityAliases()
/* 18:   */   {
/* 19:48 */     return this.entityAliases;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.EntityFetchReturn
 * JD-Core Version:    0.7.0.1
 */