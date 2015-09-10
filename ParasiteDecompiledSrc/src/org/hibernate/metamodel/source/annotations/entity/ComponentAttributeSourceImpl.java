/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import javax.persistence.AccessType;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ import org.hibernate.internal.util.Value;
/*  12:    */ import org.hibernate.mapping.PropertyGeneration;
/*  13:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  14:    */ import org.hibernate.metamodel.source.annotations.attribute.AssociationAttribute;
/*  15:    */ import org.hibernate.metamodel.source.annotations.attribute.AttributeOverride;
/*  16:    */ import org.hibernate.metamodel.source.annotations.attribute.BasicAttribute;
/*  17:    */ import org.hibernate.metamodel.source.annotations.attribute.SingularAttributeSourceImpl;
/*  18:    */ import org.hibernate.metamodel.source.annotations.attribute.ToOneAttributeSourceImpl;
/*  19:    */ import org.hibernate.metamodel.source.binder.AttributeSource;
/*  20:    */ import org.hibernate.metamodel.source.binder.ComponentAttributeSource;
/*  21:    */ import org.hibernate.metamodel.source.binder.ExplicitHibernateTypeSource;
/*  22:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  23:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  24:    */ import org.hibernate.metamodel.source.binder.SingularAttributeNature;
/*  25:    */ 
/*  26:    */ public class ComponentAttributeSourceImpl
/*  27:    */   implements ComponentAttributeSource
/*  28:    */ {
/*  29:    */   private static final String PATH_SEPERATOR = ".";
/*  30:    */   private final EmbeddableClass embeddableClass;
/*  31:    */   private final Value<Class<?>> classReference;
/*  32:    */   private final Map<String, AttributeOverride> attributeOverrides;
/*  33:    */   private final String path;
/*  34:    */   
/*  35:    */   public ComponentAttributeSourceImpl(EmbeddableClass embeddableClass, String parentPath, Map<String, AttributeOverride> attributeOverrides)
/*  36:    */   {
/*  37: 62 */     this.embeddableClass = embeddableClass;
/*  38: 63 */     this.classReference = new Value(embeddableClass.getConfiguredClass());
/*  39: 64 */     this.attributeOverrides = attributeOverrides;
/*  40: 65 */     if (StringHelper.isEmpty(parentPath)) {
/*  41: 66 */       this.path = embeddableClass.getEmbeddedAttributeName();
/*  42:    */     } else {
/*  43: 69 */       this.path = (parentPath + "." + embeddableClass.getEmbeddedAttributeName());
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isVirtualAttribute()
/*  48:    */   {
/*  49: 75 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public SingularAttributeNature getNature()
/*  53:    */   {
/*  54: 80 */     return SingularAttributeNature.COMPONENT;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isSingular()
/*  58:    */   {
/*  59: 85 */     return true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getClassName()
/*  63:    */   {
/*  64: 90 */     return this.embeddableClass.getConfiguredClass().getName();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Value<Class<?>> getClassReference()
/*  68:    */   {
/*  69: 95 */     return this.classReference;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getName()
/*  73:    */   {
/*  74:100 */     return this.embeddableClass.getEmbeddedAttributeName();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getExplicitTuplizerClassName()
/*  78:    */   {
/*  79:105 */     return this.embeddableClass.getCustomTuplizer();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getPropertyAccessorName()
/*  83:    */   {
/*  84:110 */     return this.embeddableClass.getClassAccessType().toString().toLowerCase();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public LocalBindingContext getLocalBindingContext()
/*  88:    */   {
/*  89:115 */     return this.embeddableClass.getLocalBindingContext();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Iterable<AttributeSource> attributeSources()
/*  93:    */   {
/*  94:120 */     List<AttributeSource> attributeList = new ArrayList();
/*  95:121 */     for (BasicAttribute attribute : this.embeddableClass.getSimpleAttributes())
/*  96:    */     {
/*  97:122 */       AttributeOverride attributeOverride = null;
/*  98:123 */       String tmp = getPath() + "." + attribute.getName();
/*  99:124 */       if (this.attributeOverrides.containsKey(tmp)) {
/* 100:125 */         attributeOverride = (AttributeOverride)this.attributeOverrides.get(tmp);
/* 101:    */       }
/* 102:127 */       attributeList.add(new SingularAttributeSourceImpl(attribute, attributeOverride));
/* 103:    */     }
/* 104:129 */     for (EmbeddableClass embeddable : this.embeddableClass.getEmbeddedClasses().values()) {
/* 105:130 */       attributeList.add(new ComponentAttributeSourceImpl(embeddable, getPath(), createAggregatedOverrideMap()));
/* 106:    */     }
/* 107:138 */     for (AssociationAttribute associationAttribute : this.embeddableClass.getAssociationAttributes()) {
/* 108:139 */       attributeList.add(new ToOneAttributeSourceImpl(associationAttribute));
/* 109:    */     }
/* 110:141 */     return attributeList;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getPath()
/* 114:    */   {
/* 115:146 */     return this.path;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getParentReferenceAttributeName()
/* 119:    */   {
/* 120:151 */     return this.embeddableClass.getParentReferencingAttributeName();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 124:    */   {
/* 125:157 */     return Collections.emptySet();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public List<RelationalValueSource> relationalValueSources()
/* 129:    */   {
/* 130:163 */     return null;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public ExplicitHibernateTypeSource getTypeInformation()
/* 134:    */   {
/* 135:169 */     return null;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isInsertable()
/* 139:    */   {
/* 140:174 */     return true;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isUpdatable()
/* 144:    */   {
/* 145:179 */     return true;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public PropertyGeneration getGeneration()
/* 149:    */   {
/* 150:184 */     return null;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isLazy()
/* 154:    */   {
/* 155:189 */     return false;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean isIncludedInOptimisticLocking()
/* 159:    */   {
/* 160:194 */     return true;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean areValuesIncludedInInsertByDefault()
/* 164:    */   {
/* 165:199 */     return true;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean areValuesIncludedInUpdateByDefault()
/* 169:    */   {
/* 170:204 */     return true;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean areValuesNullableByDefault()
/* 174:    */   {
/* 175:209 */     return true;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String toString()
/* 179:    */   {
/* 180:214 */     StringBuilder sb = new StringBuilder();
/* 181:215 */     sb.append("ComponentAttributeSourceImpl");
/* 182:216 */     sb.append("{embeddableClass=").append(this.embeddableClass.getConfiguredClass().getSimpleName());
/* 183:217 */     sb.append('}');
/* 184:218 */     return sb.toString();
/* 185:    */   }
/* 186:    */   
/* 187:    */   private Map<String, AttributeOverride> createAggregatedOverrideMap()
/* 188:    */   {
/* 189:224 */     Map<String, AttributeOverride> aggregatedOverrideMap = new HashMap(this.attributeOverrides);
/* 190:228 */     for (Map.Entry<String, AttributeOverride> entry : this.embeddableClass.getAttributeOverrideMap().entrySet())
/* 191:    */     {
/* 192:229 */       String fullPath = getPath() + "." + (String)entry.getKey();
/* 193:230 */       if (!aggregatedOverrideMap.containsKey(fullPath)) {
/* 194:231 */         aggregatedOverrideMap.put(fullPath, entry.getValue());
/* 195:    */       }
/* 196:    */     }
/* 197:234 */     return aggregatedOverrideMap;
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.ComponentAttributeSourceImpl
 * JD-Core Version:    0.7.0.1
 */