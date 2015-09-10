/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.property.IndexPropertyAccessor;
/*  4:   */ import org.hibernate.property.PropertyAccessor;
/*  5:   */ 
/*  6:   */ public class IndexBackref
/*  7:   */   extends Property
/*  8:   */ {
/*  9:   */   private String collectionRole;
/* 10:   */   private String entityName;
/* 11:   */   
/* 12:   */   public boolean isBackRef()
/* 13:   */   {
/* 14:36 */     return true;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isSynthetic()
/* 18:   */   {
/* 19:43 */     return true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getCollectionRole()
/* 23:   */   {
/* 24:47 */     return this.collectionRole;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setCollectionRole(String collectionRole)
/* 28:   */   {
/* 29:51 */     this.collectionRole = collectionRole;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean isBasicPropertyAccessor()
/* 33:   */   {
/* 34:55 */     return false;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public PropertyAccessor getPropertyAccessor(Class clazz)
/* 38:   */   {
/* 39:59 */     return new IndexPropertyAccessor(this.collectionRole, this.entityName);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getEntityName()
/* 43:   */   {
/* 44:63 */     return this.entityName;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void setEntityName(String entityName)
/* 48:   */   {
/* 49:67 */     this.entityName = entityName;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.IndexBackref
 * JD-Core Version:    0.7.0.1
 */