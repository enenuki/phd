/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ import org.hibernate.AssertionFailure;
/*  5:   */ import org.hibernate.id.IdentifierGenerator;
/*  6:   */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*  7:   */ import org.hibernate.metamodel.domain.Entity;
/*  8:   */ 
/*  9:   */ public class EntityIdentifier
/* 10:   */ {
/* 11:   */   private final EntityBinding entityBinding;
/* 12:   */   private BasicAttributeBinding attributeBinding;
/* 13:   */   private IdentifierGenerator identifierGenerator;
/* 14:   */   private IdGenerator idGenerator;
/* 15:43 */   private boolean isIdentifierMapper = false;
/* 16:   */   
/* 17:   */   public EntityIdentifier(EntityBinding entityBinding)
/* 18:   */   {
/* 19:52 */     this.entityBinding = entityBinding;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public BasicAttributeBinding getValueBinding()
/* 23:   */   {
/* 24:56 */     return this.attributeBinding;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setValueBinding(BasicAttributeBinding attributeBinding)
/* 28:   */   {
/* 29:60 */     if (this.attributeBinding != null) {
/* 30:61 */       throw new AssertionFailure(String.format("Identifier value binding already existed for %s", new Object[] { this.entityBinding.getEntity().getName() }));
/* 31:   */     }
/* 32:68 */     this.attributeBinding = attributeBinding;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setIdGenerator(IdGenerator idGenerator)
/* 36:   */   {
/* 37:72 */     this.idGenerator = idGenerator;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean isEmbedded()
/* 41:   */   {
/* 42:76 */     return this.attributeBinding.getSimpleValueSpan() > 1;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean isIdentifierMapper()
/* 46:   */   {
/* 47:80 */     return this.isIdentifierMapper;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public IdentifierGenerator createIdentifierGenerator(IdentifierGeneratorFactory factory, Properties properties)
/* 51:   */   {
/* 52:86 */     if (this.idGenerator != null) {
/* 53:87 */       this.identifierGenerator = this.attributeBinding.createIdentifierGenerator(this.idGenerator, factory, properties);
/* 54:   */     }
/* 55:89 */     return this.identifierGenerator;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public IdentifierGenerator getIdentifierGenerator()
/* 59:   */   {
/* 60:93 */     return this.identifierGenerator;
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.EntityIdentifier
 * JD-Core Version:    0.7.0.1
 */