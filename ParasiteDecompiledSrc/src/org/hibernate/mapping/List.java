/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.cfg.Mappings;
/*  5:   */ import org.hibernate.type.CollectionType;
/*  6:   */ import org.hibernate.type.TypeFactory;
/*  7:   */ import org.hibernate.type.TypeResolver;
/*  8:   */ 
/*  9:   */ public class List
/* 10:   */   extends IndexedCollection
/* 11:   */ {
/* 12:   */   private int baseIndex;
/* 13:   */   
/* 14:   */   public boolean isList()
/* 15:   */   {
/* 16:39 */     return true;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public List(Mappings mappings, PersistentClass owner)
/* 20:   */   {
/* 21:43 */     super(mappings, owner);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public CollectionType getDefaultCollectionType()
/* 25:   */     throws MappingException
/* 26:   */   {
/* 27:47 */     return getMappings().getTypeResolver().getTypeFactory().list(getRole(), getReferencedPropertyName(), isEmbedded());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object accept(ValueVisitor visitor)
/* 31:   */   {
/* 32:53 */     return visitor.accept(this);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public int getBaseIndex()
/* 36:   */   {
/* 37:57 */     return this.baseIndex;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setBaseIndex(int baseIndex)
/* 41:   */   {
/* 42:61 */     this.baseIndex = baseIndex;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.List
 * JD-Core Version:    0.7.0.1
 */