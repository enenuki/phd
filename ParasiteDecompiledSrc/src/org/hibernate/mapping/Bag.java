/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.Mappings;
/*  4:   */ import org.hibernate.type.CollectionType;
/*  5:   */ import org.hibernate.type.TypeFactory;
/*  6:   */ import org.hibernate.type.TypeResolver;
/*  7:   */ 
/*  8:   */ public class Bag
/*  9:   */   extends Collection
/* 10:   */ {
/* 11:   */   public Bag(Mappings mappings, PersistentClass owner)
/* 12:   */   {
/* 13:36 */     super(mappings, owner);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public CollectionType getDefaultCollectionType()
/* 17:   */   {
/* 18:40 */     return getMappings().getTypeResolver().getTypeFactory().bag(getRole(), getReferencedPropertyName(), isEmbedded());
/* 19:   */   }
/* 20:   */   
/* 21:   */   void createPrimaryKey() {}
/* 22:   */   
/* 23:   */   public Object accept(ValueVisitor visitor)
/* 24:   */   {
/* 25:50 */     return visitor.accept(this);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Bag
 * JD-Core Version:    0.7.0.1
 */