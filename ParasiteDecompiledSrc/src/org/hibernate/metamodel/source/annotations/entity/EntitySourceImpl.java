/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.internal.jaxb.Origin;
/*  10:    */ import org.hibernate.metamodel.binding.CustomSQL;
/*  11:    */ import org.hibernate.metamodel.source.LocalBindingContext;
/*  12:    */ import org.hibernate.metamodel.source.annotations.attribute.AssociationAttribute;
/*  13:    */ import org.hibernate.metamodel.source.annotations.attribute.BasicAttribute;
/*  14:    */ import org.hibernate.metamodel.source.annotations.attribute.SingularAttributeSourceImpl;
/*  15:    */ import org.hibernate.metamodel.source.annotations.attribute.ToOneAttributeSourceImpl;
/*  16:    */ import org.hibernate.metamodel.source.binder.AttributeSource;
/*  17:    */ import org.hibernate.metamodel.source.binder.ConstraintSource;
/*  18:    */ import org.hibernate.metamodel.source.binder.EntitySource;
/*  19:    */ import org.hibernate.metamodel.source.binder.JpaCallbackClass;
/*  20:    */ import org.hibernate.metamodel.source.binder.MetaAttributeSource;
/*  21:    */ import org.hibernate.metamodel.source.binder.SubclassEntitySource;
/*  22:    */ import org.hibernate.metamodel.source.binder.TableSource;
/*  23:    */ 
/*  24:    */ public class EntitySourceImpl
/*  25:    */   implements EntitySource
/*  26:    */ {
/*  27:    */   private final EntityClass entityClass;
/*  28:    */   private final Set<SubclassEntitySource> subclassEntitySources;
/*  29:    */   
/*  30:    */   public EntitySourceImpl(EntityClass entityClass)
/*  31:    */   {
/*  32: 55 */     this.entityClass = entityClass;
/*  33: 56 */     this.subclassEntitySources = new HashSet();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public EntityClass getEntityClass()
/*  37:    */   {
/*  38: 60 */     return this.entityClass;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Origin getOrigin()
/*  42:    */   {
/*  43: 65 */     return this.entityClass.getLocalBindingContext().getOrigin();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public LocalBindingContext getLocalBindingContext()
/*  47:    */   {
/*  48: 70 */     return this.entityClass.getLocalBindingContext();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getEntityName()
/*  52:    */   {
/*  53: 75 */     return this.entityClass.getName();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getClassName()
/*  57:    */   {
/*  58: 80 */     return this.entityClass.getName();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getJpaEntityName()
/*  62:    */   {
/*  63: 85 */     return this.entityClass.getExplicitEntityName();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public TableSource getPrimaryTable()
/*  67:    */   {
/*  68: 90 */     return this.entityClass.getPrimaryTableSource();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isAbstract()
/*  72:    */   {
/*  73: 95 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isLazy()
/*  77:    */   {
/*  78:100 */     return this.entityClass.isLazy();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getProxy()
/*  82:    */   {
/*  83:105 */     return this.entityClass.getProxy();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getBatchSize()
/*  87:    */   {
/*  88:110 */     return this.entityClass.getBatchSize();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isDynamicInsert()
/*  92:    */   {
/*  93:115 */     return this.entityClass.isDynamicInsert();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isDynamicUpdate()
/*  97:    */   {
/*  98:120 */     return this.entityClass.isDynamicUpdate();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isSelectBeforeUpdate()
/* 102:    */   {
/* 103:125 */     return this.entityClass.isSelectBeforeUpdate();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getCustomTuplizerClassName()
/* 107:    */   {
/* 108:130 */     return this.entityClass.getCustomTuplizer();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getCustomPersisterClassName()
/* 112:    */   {
/* 113:135 */     return this.entityClass.getCustomPersister();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getCustomLoaderName()
/* 117:    */   {
/* 118:140 */     return this.entityClass.getCustomLoaderQueryName();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public CustomSQL getCustomSqlInsert()
/* 122:    */   {
/* 123:145 */     return this.entityClass.getCustomInsert();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public CustomSQL getCustomSqlUpdate()
/* 127:    */   {
/* 128:150 */     return this.entityClass.getCustomUpdate();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public CustomSQL getCustomSqlDelete()
/* 132:    */   {
/* 133:155 */     return this.entityClass.getCustomDelete();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List<String> getSynchronizedTableNames()
/* 137:    */   {
/* 138:160 */     return this.entityClass.getSynchronizedTableNames();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Iterable<MetaAttributeSource> metaAttributes()
/* 142:    */   {
/* 143:165 */     return Collections.emptySet();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getPath()
/* 147:    */   {
/* 148:170 */     return this.entityClass.getName();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Iterable<AttributeSource> attributeSources()
/* 152:    */   {
/* 153:175 */     List<AttributeSource> attributeList = new ArrayList();
/* 154:176 */     for (BasicAttribute attribute : this.entityClass.getSimpleAttributes()) {
/* 155:177 */       attributeList.add(new SingularAttributeSourceImpl(attribute));
/* 156:    */     }
/* 157:179 */     for (EmbeddableClass component : this.entityClass.getEmbeddedClasses().values()) {
/* 158:180 */       attributeList.add(new ComponentAttributeSourceImpl(component, "", this.entityClass.getAttributeOverrideMap()));
/* 159:    */     }
/* 160:188 */     for (AssociationAttribute associationAttribute : this.entityClass.getAssociationAttributes()) {
/* 161:189 */       attributeList.add(new ToOneAttributeSourceImpl(associationAttribute));
/* 162:    */     }
/* 163:191 */     return attributeList;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void add(SubclassEntitySource subclassEntitySource)
/* 167:    */   {
/* 168:196 */     this.subclassEntitySources.add(subclassEntitySource);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Iterable<SubclassEntitySource> subclassEntitySources()
/* 172:    */   {
/* 173:201 */     return this.subclassEntitySources;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getDiscriminatorMatchValue()
/* 177:    */   {
/* 178:206 */     return this.entityClass.getDiscriminatorMatchValue();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Iterable<ConstraintSource> getConstraints()
/* 182:    */   {
/* 183:211 */     return this.entityClass.getConstraintSources();
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<JpaCallbackClass> getJpaCallbackClasses()
/* 187:    */   {
/* 188:216 */     return this.entityClass.getJpaCallbacks();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Iterable<TableSource> getSecondaryTables()
/* 192:    */   {
/* 193:221 */     return this.entityClass.getSecondaryTableSources();
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.EntitySourceImpl
 * JD-Core Version:    0.7.0.1
 */