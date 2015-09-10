/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ import org.hibernate.loader.CollectionAliases;
/*  5:   */ import org.hibernate.loader.EntityAliases;
/*  6:   */ 
/*  7:   */ public class CollectionFetchReturn
/*  8:   */   extends FetchReturn
/*  9:   */ {
/* 10:   */   private final CollectionAliases collectionAliases;
/* 11:   */   private final EntityAliases elementEntityAliases;
/* 12:   */   
/* 13:   */   public CollectionFetchReturn(String alias, NonScalarReturn owner, String ownerProperty, CollectionAliases collectionAliases, EntityAliases elementEntityAliases, LockMode lockMode)
/* 14:   */   {
/* 15:46 */     super(owner, ownerProperty, alias, lockMode);
/* 16:47 */     this.collectionAliases = collectionAliases;
/* 17:48 */     this.elementEntityAliases = elementEntityAliases;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public CollectionAliases getCollectionAliases()
/* 21:   */   {
/* 22:52 */     return this.collectionAliases;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public EntityAliases getElementEntityAliases()
/* 26:   */   {
/* 27:56 */     return this.elementEntityAliases;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.CollectionFetchReturn
 * JD-Core Version:    0.7.0.1
 */