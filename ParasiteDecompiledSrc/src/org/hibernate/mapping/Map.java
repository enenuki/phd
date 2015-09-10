/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.cfg.Mappings;
/*  5:   */ import org.hibernate.type.CollectionType;
/*  6:   */ import org.hibernate.type.TypeFactory;
/*  7:   */ import org.hibernate.type.TypeResolver;
/*  8:   */ 
/*  9:   */ public class Map
/* 10:   */   extends IndexedCollection
/* 11:   */ {
/* 12:   */   public Map(Mappings mappings, PersistentClass owner)
/* 13:   */   {
/* 14:36 */     super(mappings, owner);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean isMap()
/* 18:   */   {
/* 19:40 */     return true;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public CollectionType getDefaultCollectionType()
/* 23:   */   {
/* 24:44 */     if (isSorted()) {
/* 25:45 */       return getMappings().getTypeResolver().getTypeFactory().sortedMap(getRole(), getReferencedPropertyName(), isEmbedded(), getComparator());
/* 26:   */     }
/* 27:49 */     if (hasOrder()) {
/* 28:50 */       return getMappings().getTypeResolver().getTypeFactory().orderedMap(getRole(), getReferencedPropertyName(), isEmbedded());
/* 29:   */     }
/* 30:55 */     return getMappings().getTypeResolver().getTypeFactory().map(getRole(), getReferencedPropertyName(), isEmbedded());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void createAllKeys()
/* 34:   */     throws MappingException
/* 35:   */   {
/* 36:63 */     super.createAllKeys();
/* 37:64 */     if (!isInverse()) {
/* 38:64 */       getIndex().createForeignKey();
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Object accept(ValueVisitor visitor)
/* 43:   */   {
/* 44:68 */     return visitor.accept(this);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Map
 * JD-Core Version:    0.7.0.1
 */