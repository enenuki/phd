/*  1:   */ package org.hibernate.loader.criteria;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.persister.collection.QueryableCollection;
/*  7:   */ import org.hibernate.persister.entity.PropertyMapping;
/*  8:   */ import org.hibernate.type.ComponentType;
/*  9:   */ import org.hibernate.type.Type;
/* 10:   */ 
/* 11:   */ class ComponentCollectionCriteriaInfoProvider
/* 12:   */   implements CriteriaInfoProvider
/* 13:   */ {
/* 14:   */   QueryableCollection persister;
/* 15:42 */   Map subTypes = new HashMap();
/* 16:   */   
/* 17:   */   ComponentCollectionCriteriaInfoProvider(QueryableCollection persister)
/* 18:   */   {
/* 19:45 */     this.persister = persister;
/* 20:46 */     if (!persister.getElementType().isComponentType()) {
/* 21:47 */       throw new IllegalArgumentException("persister for role " + persister.getRole() + " is not a collection-of-component");
/* 22:   */     }
/* 23:50 */     ComponentType componentType = (ComponentType)persister.getElementType();
/* 24:51 */     String[] names = componentType.getPropertyNames();
/* 25:52 */     Type[] types = componentType.getSubtypes();
/* 26:54 */     for (int i = 0; i < names.length; i++) {
/* 27:55 */       this.subTypes.put(names[i], types[i]);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getName()
/* 32:   */   {
/* 33:61 */     return this.persister.getRole();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Serializable[] getSpaces()
/* 37:   */   {
/* 38:65 */     return this.persister.getCollectionSpaces();
/* 39:   */   }
/* 40:   */   
/* 41:   */   public PropertyMapping getPropertyMapping()
/* 42:   */   {
/* 43:69 */     return this.persister;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Type getType(String relativePath)
/* 47:   */   {
/* 48:74 */     if (relativePath.indexOf('.') >= 0) {
/* 49:75 */       throw new IllegalArgumentException("dotted paths not handled (yet?!) for collection-of-component");
/* 50:   */     }
/* 51:77 */     Type type = (Type)this.subTypes.get(relativePath);
/* 52:79 */     if (type == null) {
/* 53:80 */       throw new IllegalArgumentException("property " + relativePath + " not found in component of collection " + getName());
/* 54:   */     }
/* 55:82 */     return type;
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.ComponentCollectionCriteriaInfoProvider
 * JD-Core Version:    0.7.0.1
 */