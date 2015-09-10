/*  1:   */ package org.hibernate.engine.profile;
/*  2:   */ 
/*  3:   */ import org.hibernate.persister.entity.EntityPersister;
/*  4:   */ 
/*  5:   */ public class Association
/*  6:   */ {
/*  7:   */   private final EntityPersister owner;
/*  8:   */   private final String associationPath;
/*  9:   */   private final String role;
/* 10:   */   
/* 11:   */   public Association(EntityPersister owner, String associationPath)
/* 12:   */   {
/* 13:38 */     this.owner = owner;
/* 14:39 */     this.associationPath = associationPath;
/* 15:40 */     this.role = (owner.getEntityName() + '.' + associationPath);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public EntityPersister getOwner()
/* 19:   */   {
/* 20:44 */     return this.owner;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getAssociationPath()
/* 24:   */   {
/* 25:48 */     return this.associationPath;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getRole()
/* 29:   */   {
/* 30:52 */     return this.role;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.profile.Association
 * JD-Core Version:    0.7.0.1
 */