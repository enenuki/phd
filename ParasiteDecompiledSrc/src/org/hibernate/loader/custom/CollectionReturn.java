/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ import org.hibernate.loader.CollectionAliases;
/*  5:   */ import org.hibernate.loader.EntityAliases;
/*  6:   */ 
/*  7:   */ public class CollectionReturn
/*  8:   */   extends NonScalarReturn
/*  9:   */ {
/* 10:   */   private final String ownerEntityName;
/* 11:   */   private final String ownerProperty;
/* 12:   */   private final CollectionAliases collectionAliases;
/* 13:   */   private final EntityAliases elementEntityAliases;
/* 14:   */   
/* 15:   */   public CollectionReturn(String alias, String ownerEntityName, String ownerProperty, CollectionAliases collectionAliases, EntityAliases elementEntityAliases, LockMode lockMode)
/* 16:   */   {
/* 17:51 */     super(alias, lockMode);
/* 18:52 */     this.ownerEntityName = ownerEntityName;
/* 19:53 */     this.ownerProperty = ownerProperty;
/* 20:54 */     this.collectionAliases = collectionAliases;
/* 21:55 */     this.elementEntityAliases = elementEntityAliases;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getOwnerEntityName()
/* 25:   */   {
/* 26:64 */     return this.ownerEntityName;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getOwnerProperty()
/* 30:   */   {
/* 31:73 */     return this.ownerProperty;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public CollectionAliases getCollectionAliases()
/* 35:   */   {
/* 36:77 */     return this.collectionAliases;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public EntityAliases getElementEntityAliases()
/* 40:   */   {
/* 41:81 */     return this.elementEntityAliases;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.CollectionReturn
 * JD-Core Version:    0.7.0.1
 */