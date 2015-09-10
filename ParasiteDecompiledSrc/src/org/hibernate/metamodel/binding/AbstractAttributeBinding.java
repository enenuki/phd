/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.hibernate.metamodel.domain.Attribute;
/*   7:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*   8:    */ 
/*   9:    */ public abstract class AbstractAttributeBinding
/*  10:    */   implements AttributeBinding
/*  11:    */ {
/*  12:    */   private final AttributeBindingContainer container;
/*  13:    */   private final Attribute attribute;
/*  14: 42 */   private final HibernateTypeDescriptor hibernateTypeDescriptor = new HibernateTypeDescriptor();
/*  15: 43 */   private final Set<SingularAssociationAttributeBinding> entityReferencingAttributeBindings = new HashSet();
/*  16:    */   private boolean includedInOptimisticLocking;
/*  17:    */   private boolean isLazy;
/*  18:    */   private String propertyAccessorName;
/*  19:    */   private boolean isAlternateUniqueKey;
/*  20:    */   private MetaAttributeContext metaAttributeContext;
/*  21:    */   
/*  22:    */   protected AbstractAttributeBinding(AttributeBindingContainer container, Attribute attribute)
/*  23:    */   {
/*  24: 54 */     this.container = container;
/*  25: 55 */     this.attribute = attribute;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public AttributeBindingContainer getContainer()
/*  29:    */   {
/*  30: 60 */     return this.container;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Attribute getAttribute()
/*  34:    */   {
/*  35: 65 */     return this.attribute;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public HibernateTypeDescriptor getHibernateTypeDescriptor()
/*  39:    */   {
/*  40: 70 */     return this.hibernateTypeDescriptor;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isBasicPropertyAccessor()
/*  44:    */   {
/*  45: 75 */     return (this.propertyAccessorName == null) || ("property".equals(this.propertyAccessorName));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getPropertyAccessorName()
/*  49:    */   {
/*  50: 80 */     return this.propertyAccessorName;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setPropertyAccessorName(String propertyAccessorName)
/*  54:    */   {
/*  55: 84 */     this.propertyAccessorName = propertyAccessorName;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean isIncludedInOptimisticLocking()
/*  59:    */   {
/*  60: 89 */     return this.includedInOptimisticLocking;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIncludedInOptimisticLocking(boolean includedInOptimisticLocking)
/*  64:    */   {
/*  65: 93 */     this.includedInOptimisticLocking = includedInOptimisticLocking;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public MetaAttributeContext getMetaAttributeContext()
/*  69:    */   {
/*  70: 98 */     return this.metaAttributeContext;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setMetaAttributeContext(MetaAttributeContext metaAttributeContext)
/*  74:    */   {
/*  75:102 */     this.metaAttributeContext = metaAttributeContext;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isAlternateUniqueKey()
/*  79:    */   {
/*  80:107 */     return this.isAlternateUniqueKey;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setAlternateUniqueKey(boolean alternateUniqueKey)
/*  84:    */   {
/*  85:111 */     this.isAlternateUniqueKey = alternateUniqueKey;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isLazy()
/*  89:    */   {
/*  90:116 */     return this.isLazy;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setLazy(boolean isLazy)
/*  94:    */   {
/*  95:120 */     this.isLazy = isLazy;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void addEntityReferencingAttributeBinding(SingularAssociationAttributeBinding referencingAttributeBinding)
/*  99:    */   {
/* 100:124 */     this.entityReferencingAttributeBindings.add(referencingAttributeBinding);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Set<SingularAssociationAttributeBinding> getEntityReferencingAttributeBindings()
/* 104:    */   {
/* 105:128 */     return Collections.unmodifiableSet(this.entityReferencingAttributeBindings);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void validate()
/* 109:    */   {
/* 110:132 */     if (!this.entityReferencingAttributeBindings.isEmpty()) {}
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AbstractAttributeBinding
 * JD-Core Version:    0.7.0.1
 */