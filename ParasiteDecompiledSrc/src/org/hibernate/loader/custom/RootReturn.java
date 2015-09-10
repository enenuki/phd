/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ import org.hibernate.loader.EntityAliases;
/*  5:   */ 
/*  6:   */ public class RootReturn
/*  7:   */   extends NonScalarReturn
/*  8:   */ {
/*  9:   */   private final String entityName;
/* 10:   */   private final EntityAliases entityAliases;
/* 11:   */   
/* 12:   */   public RootReturn(String alias, String entityName, EntityAliases entityAliases, LockMode lockMode)
/* 13:   */   {
/* 14:46 */     super(alias, lockMode);
/* 15:47 */     this.entityName = entityName;
/* 16:48 */     this.entityAliases = entityAliases;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getEntityName()
/* 20:   */   {
/* 21:52 */     return this.entityName;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public EntityAliases getEntityAliases()
/* 25:   */   {
/* 26:56 */     return this.entityAliases;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.RootReturn
 * JD-Core Version:    0.7.0.1
 */