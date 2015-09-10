/*  1:   */ package org.hibernate.metamodel.source.annotations.attribute;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.AssertionFailure;
/*  5:   */ import org.hibernate.metamodel.binding.IdGenerator;
/*  6:   */ import org.hibernate.metamodel.source.binder.IdentifierSource.Nature;
/*  7:   */ import org.hibernate.metamodel.source.binder.SimpleIdentifierSource;
/*  8:   */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  9:   */ 
/* 10:   */ public class SimpleIdentifierSourceImpl
/* 11:   */   implements SimpleIdentifierSource
/* 12:   */ {
/* 13:   */   private final BasicAttribute attribute;
/* 14:   */   private final Map<String, AttributeOverride> attributeOverrideMap;
/* 15:   */   
/* 16:   */   public SimpleIdentifierSourceImpl(BasicAttribute attribute, Map<String, AttributeOverride> attributeOverrideMap)
/* 17:   */   {
/* 18:41 */     if (!attribute.isId()) {
/* 19:42 */       throw new AssertionFailure(String.format("A non id attribute was passed to SimpleIdentifierSourceImpl: %s", new Object[] { attribute.toString() }));
/* 20:   */     }
/* 21:49 */     this.attribute = attribute;
/* 22:50 */     this.attributeOverrideMap = attributeOverrideMap;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public IdentifierSource.Nature getNature()
/* 26:   */   {
/* 27:55 */     return IdentifierSource.Nature.SIMPLE;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public SingularAttributeSource getIdentifierAttributeSource()
/* 31:   */   {
/* 32:60 */     return new SingularAttributeSourceImpl(this.attribute);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public IdGenerator getIdentifierGeneratorDescriptor()
/* 36:   */   {
/* 37:65 */     return this.attribute.getIdGenerator();
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.SimpleIdentifierSourceImpl
 * JD-Core Version:    0.7.0.1
 */