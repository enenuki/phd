/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.Mappings;
/*  4:   */ import org.hibernate.type.CollectionType;
/*  5:   */ import org.hibernate.type.TypeFactory;
/*  6:   */ import org.hibernate.type.TypeResolver;
/*  7:   */ 
/*  8:   */ public class IdentifierBag
/*  9:   */   extends IdentifierCollection
/* 10:   */ {
/* 11:   */   public IdentifierBag(Mappings mappings, PersistentClass owner)
/* 12:   */   {
/* 13:35 */     super(mappings, owner);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public CollectionType getDefaultCollectionType()
/* 17:   */   {
/* 18:39 */     return getMappings().getTypeResolver().getTypeFactory().idbag(getRole(), getReferencedPropertyName(), isEmbedded());
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object accept(ValueVisitor visitor)
/* 22:   */   {
/* 23:45 */     return visitor.accept(this);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.IdentifierBag
 * JD-Core Version:    0.7.0.1
 */