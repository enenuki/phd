/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.property.BackrefPropertyAccessor;
/*  4:   */ import org.hibernate.property.PropertyAccessor;
/*  5:   */ 
/*  6:   */ public class Backref
/*  7:   */   extends Property
/*  8:   */ {
/*  9:   */   private String collectionRole;
/* 10:   */   private String entityName;
/* 11:   */   
/* 12:   */   public boolean isBackRef()
/* 13:   */   {
/* 14:39 */     return true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isSynthetic()
/* 18:   */   {
/* 19:46 */     return true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getCollectionRole()
/* 23:   */   {
/* 24:50 */     return this.collectionRole;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setCollectionRole(String collectionRole)
/* 28:   */   {
/* 29:54 */     this.collectionRole = collectionRole;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean isBasicPropertyAccessor()
/* 33:   */   {
/* 34:58 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public PropertyAccessor getPropertyAccessor(Class clazz)
/* 38:   */   {
/* 39:62 */     return new BackrefPropertyAccessor(this.collectionRole, this.entityName);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getEntityName()
/* 43:   */   {
/* 44:66 */     return this.entityName;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setEntityName(String entityName)
/* 48:   */   {
/* 49:69 */     this.entityName = entityName;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Backref
 * JD-Core Version:    0.7.0.1
 */