/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.mapping.PropertyGeneration;
/*   6:    */ import org.hibernate.metamodel.domain.AttributeContainer;
/*   7:    */ import org.hibernate.metamodel.domain.Component;
/*   8:    */ import org.hibernate.metamodel.domain.PluralAttribute;
/*   9:    */ import org.hibernate.metamodel.domain.PluralAttributeNature;
/*  10:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  11:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*  12:    */ 
/*  13:    */ public class ComponentAttributeBinding
/*  14:    */   extends AbstractSingularAttributeBinding
/*  15:    */   implements AttributeBindingContainer
/*  16:    */ {
/*  17:    */   private final String path;
/*  18: 42 */   private Map<String, AttributeBinding> attributeBindingMap = new HashMap();
/*  19:    */   private SingularAttribute parentReference;
/*  20:    */   private MetaAttributeContext metaAttributeContext;
/*  21:    */   
/*  22:    */   public ComponentAttributeBinding(AttributeBindingContainer container, SingularAttribute attribute)
/*  23:    */   {
/*  24: 47 */     super(container, attribute);
/*  25: 48 */     this.path = (container.getPathBase() + '.' + attribute.getName());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public EntityBinding seekEntityBinding()
/*  29:    */   {
/*  30: 53 */     return getContainer().seekEntityBinding();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getPathBase()
/*  34:    */   {
/*  35: 58 */     return this.path;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AttributeContainer getAttributeContainer()
/*  39:    */   {
/*  40: 63 */     return getComponent();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Component getComponent()
/*  44:    */   {
/*  45: 67 */     return (Component)getAttribute().getSingularAttributeType();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean isAssociation()
/*  49:    */   {
/*  50: 72 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public MetaAttributeContext getMetaAttributeContext()
/*  54:    */   {
/*  55: 77 */     return this.metaAttributeContext;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setMetaAttributeContext(MetaAttributeContext metaAttributeContext)
/*  59:    */   {
/*  60: 81 */     this.metaAttributeContext = metaAttributeContext;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public AttributeBinding locateAttributeBinding(String name)
/*  64:    */   {
/*  65: 86 */     return (AttributeBinding)this.attributeBindingMap.get(name);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Iterable<AttributeBinding> attributeBindings()
/*  69:    */   {
/*  70: 91 */     return this.attributeBindingMap.values();
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void checkValueBinding() {}
/*  74:    */   
/*  75:    */   public BasicAttributeBinding makeBasicAttributeBinding(SingularAttribute attribute)
/*  76:    */   {
/*  77:101 */     BasicAttributeBinding binding = new BasicAttributeBinding(this, attribute, isNullable(), isAlternateUniqueKey());
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:107 */     registerAttributeBinding(attribute.getName(), binding);
/*  84:108 */     return binding;
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void registerAttributeBinding(String name, AttributeBinding attributeBinding)
/*  88:    */   {
/*  89:113 */     this.attributeBindingMap.put(name, attributeBinding);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public ComponentAttributeBinding makeComponentAttributeBinding(SingularAttribute attribute)
/*  93:    */   {
/*  94:118 */     ComponentAttributeBinding binding = new ComponentAttributeBinding(this, attribute);
/*  95:119 */     registerAttributeBinding(attribute.getName(), binding);
/*  96:120 */     return binding;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ManyToOneAttributeBinding makeManyToOneAttributeBinding(SingularAttribute attribute)
/* 100:    */   {
/* 101:125 */     ManyToOneAttributeBinding binding = new ManyToOneAttributeBinding(this, attribute);
/* 102:126 */     registerAttributeBinding(attribute.getName(), binding);
/* 103:127 */     return binding;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public BagBinding makeBagAttributeBinding(PluralAttribute attribute, CollectionElementNature nature)
/* 107:    */   {
/* 108:132 */     Helper.checkPluralAttributeNature(attribute, PluralAttributeNature.BAG);
/* 109:133 */     BagBinding binding = new BagBinding(this, attribute, nature);
/* 110:134 */     registerAttributeBinding(attribute.getName(), binding);
/* 111:135 */     return binding;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public SetBinding makeSetAttributeBinding(PluralAttribute attribute, CollectionElementNature nature)
/* 115:    */   {
/* 116:140 */     Helper.checkPluralAttributeNature(attribute, PluralAttributeNature.SET);
/* 117:141 */     SetBinding binding = new SetBinding(this, attribute, nature);
/* 118:142 */     registerAttributeBinding(attribute.getName(), binding);
/* 119:143 */     return binding;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Class<?> getClassReference()
/* 123:    */   {
/* 124:148 */     return getComponent().getClassReference();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public SingularAttribute getParentReference()
/* 128:    */   {
/* 129:152 */     return this.parentReference;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setParentReference(SingularAttribute parentReference)
/* 133:    */   {
/* 134:156 */     this.parentReference = parentReference;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public PropertyGeneration getGeneration()
/* 138:    */   {
/* 139:162 */     return null;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.ComponentAttributeBinding
 * JD-Core Version:    0.7.0.1
 */