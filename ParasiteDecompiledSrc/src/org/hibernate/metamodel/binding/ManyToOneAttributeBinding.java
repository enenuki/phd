/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.FetchMode;
/*   7:    */ import org.hibernate.engine.FetchStyle;
/*   8:    */ import org.hibernate.engine.FetchTiming;
/*   9:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  10:    */ import org.hibernate.engine.spi.CascadeStyle.MultipleCascadeStyle;
/*  11:    */ import org.hibernate.metamodel.domain.Attribute;
/*  12:    */ import org.hibernate.metamodel.domain.Entity;
/*  13:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  14:    */ 
/*  15:    */ public class ManyToOneAttributeBinding
/*  16:    */   extends BasicAttributeBinding
/*  17:    */   implements SingularAssociationAttributeBinding
/*  18:    */ {
/*  19:    */   private String referencedEntityName;
/*  20:    */   private String referencedAttributeName;
/*  21:    */   private AttributeBinding referencedAttributeBinding;
/*  22:    */   private boolean isLogicalOneToOne;
/*  23:    */   private String foreignKeyName;
/*  24:    */   private CascadeStyle cascadeStyle;
/*  25:    */   private FetchTiming fetchTiming;
/*  26:    */   private FetchStyle fetchStyle;
/*  27:    */   
/*  28:    */   ManyToOneAttributeBinding(AttributeBindingContainer container, SingularAttribute attribute)
/*  29:    */   {
/*  30: 55 */     super(container, attribute, false, false);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean isAssociation()
/*  34:    */   {
/*  35: 60 */     return true;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final boolean isPropertyReference()
/*  39:    */   {
/*  40: 65 */     return this.referencedAttributeName != null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final String getReferencedEntityName()
/*  44:    */   {
/*  45: 70 */     return this.referencedEntityName;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setReferencedEntityName(String referencedEntityName)
/*  49:    */   {
/*  50: 75 */     this.referencedEntityName = referencedEntityName;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final String getReferencedAttributeName()
/*  54:    */   {
/*  55: 80 */     return this.referencedAttributeName;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setReferencedAttributeName(String referencedEntityAttributeName)
/*  59:    */   {
/*  60: 85 */     this.referencedAttributeName = referencedEntityAttributeName;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public CascadeStyle getCascadeStyle()
/*  64:    */   {
/*  65: 90 */     return this.cascadeStyle;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setCascadeStyles(Iterable<CascadeStyle> cascadeStyles)
/*  69:    */   {
/*  70: 95 */     List<CascadeStyle> cascadeStyleList = new ArrayList();
/*  71: 96 */     for (CascadeStyle style : cascadeStyles) {
/*  72: 97 */       if (style != CascadeStyle.NONE) {
/*  73: 98 */         cascadeStyleList.add(style);
/*  74:    */       }
/*  75:    */     }
/*  76:101 */     if (cascadeStyleList.isEmpty()) {
/*  77:102 */       this.cascadeStyle = CascadeStyle.NONE;
/*  78:104 */     } else if (cascadeStyleList.size() == 1) {
/*  79:105 */       this.cascadeStyle = ((CascadeStyle)cascadeStyleList.get(0));
/*  80:    */     } else {
/*  81:108 */       this.cascadeStyle = new CascadeStyle.MultipleCascadeStyle((CascadeStyle[])cascadeStyleList.toArray(new CascadeStyle[cascadeStyleList.size()]));
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public FetchTiming getFetchTiming()
/*  86:    */   {
/*  87:116 */     return this.fetchTiming;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setFetchTiming(FetchTiming fetchTiming)
/*  91:    */   {
/*  92:121 */     this.fetchTiming = fetchTiming;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public FetchStyle getFetchStyle()
/*  96:    */   {
/*  97:126 */     return this.fetchStyle;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setFetchStyle(FetchStyle fetchStyle)
/* 101:    */   {
/* 102:131 */     if (fetchStyle == FetchStyle.SUBSELECT) {
/* 103:132 */       throw new AssertionFailure("Subselect fetching not yet supported for singular associations");
/* 104:    */     }
/* 105:134 */     this.fetchStyle = fetchStyle;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public FetchMode getFetchMode()
/* 109:    */   {
/* 110:139 */     if (this.fetchStyle == FetchStyle.JOIN) {
/* 111:140 */       return FetchMode.JOIN;
/* 112:    */     }
/* 113:142 */     if (this.fetchStyle == FetchStyle.SELECT) {
/* 114:143 */       return FetchMode.SELECT;
/* 115:    */     }
/* 116:145 */     if (this.fetchStyle == FetchStyle.BATCH) {
/* 117:147 */       return FetchMode.SELECT;
/* 118:    */     }
/* 119:150 */     throw new AssertionFailure("Unexpected fetch style : " + this.fetchStyle.name());
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final boolean isReferenceResolved()
/* 123:    */   {
/* 124:155 */     return this.referencedAttributeBinding != null;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final void resolveReference(AttributeBinding referencedAttributeBinding)
/* 128:    */   {
/* 129:160 */     if (!EntityBinding.class.isInstance(referencedAttributeBinding.getContainer())) {
/* 130:161 */       throw new AssertionFailure("Illegal attempt to resolve many-to-one reference based on non-entity attribute");
/* 131:    */     }
/* 132:163 */     EntityBinding entityBinding = (EntityBinding)referencedAttributeBinding.getContainer();
/* 133:164 */     if (!this.referencedEntityName.equals(entityBinding.getEntity().getName())) {
/* 134:165 */       throw new IllegalStateException("attempt to set EntityBinding with name: [" + entityBinding.getEntity().getName() + "; entity name should be: " + this.referencedEntityName);
/* 135:    */     }
/* 136:171 */     if (this.referencedAttributeName == null) {
/* 137:172 */       this.referencedAttributeName = referencedAttributeBinding.getAttribute().getName();
/* 138:174 */     } else if (!this.referencedAttributeName.equals(referencedAttributeBinding.getAttribute().getName())) {
/* 139:175 */       throw new IllegalStateException("Inconsistent attribute name; expected: " + this.referencedAttributeName + "actual: " + referencedAttributeBinding.getAttribute().getName());
/* 140:    */     }
/* 141:180 */     this.referencedAttributeBinding = referencedAttributeBinding;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public AttributeBinding getReferencedAttributeBinding()
/* 145:    */   {
/* 146:186 */     if (!isReferenceResolved()) {
/* 147:187 */       throw new IllegalStateException("Referenced AttributeBiding has not been resolved.");
/* 148:    */     }
/* 149:189 */     return this.referencedAttributeBinding;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final EntityBinding getReferencedEntityBinding()
/* 153:    */   {
/* 154:194 */     return (EntityBinding)this.referencedAttributeBinding.getContainer();
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.ManyToOneAttributeBinding
 * JD-Core Version:    0.7.0.1
 */