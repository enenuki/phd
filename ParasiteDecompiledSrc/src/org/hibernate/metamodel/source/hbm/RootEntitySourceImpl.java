/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import org.hibernate.EntityMode;
/*   4:    */ import org.hibernate.cache.spi.access.AccessType;
/*   5:    */ import org.hibernate.engine.OptimisticLockStyle;
/*   6:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbCacheElement;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbGeneratorElement;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbClass;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbClass.JaxbDiscriminator;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbClass.JaxbId;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.metamodel.binding.Caching;
/*  13:    */ import org.hibernate.metamodel.binding.IdGenerator;
/*  14:    */ import org.hibernate.metamodel.source.MappingException;
/*  15:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  16:    */ import org.hibernate.metamodel.source.binder.DiscriminatorSource;
/*  17:    */ import org.hibernate.metamodel.source.binder.IdentifierSource;
/*  18:    */ import org.hibernate.metamodel.source.binder.IdentifierSource.Nature;
/*  19:    */ import org.hibernate.metamodel.source.binder.RelationalValueSource;
/*  20:    */ import org.hibernate.metamodel.source.binder.RootEntitySource;
/*  21:    */ import org.hibernate.metamodel.source.binder.SimpleIdentifierSource;
/*  22:    */ import org.hibernate.metamodel.source.binder.SingularAttributeSource;
/*  23:    */ import org.hibernate.metamodel.source.binder.TableSource;
/*  24:    */ 
/*  25:    */ public class RootEntitySourceImpl
/*  26:    */   extends AbstractEntitySourceImpl
/*  27:    */   implements RootEntitySource
/*  28:    */ {
/*  29:    */   protected RootEntitySourceImpl(MappingDocument sourceMappingDocument, JaxbHibernateMapping.JaxbClass entityElement)
/*  30:    */   {
/*  31: 48 */     super(sourceMappingDocument, entityElement);
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected JaxbHibernateMapping.JaxbClass entityElement()
/*  35:    */   {
/*  36: 53 */     return (JaxbHibernateMapping.JaxbClass)super.entityElement();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public IdentifierSource getIdentifierSource()
/*  40:    */   {
/*  41: 58 */     if (entityElement().getId() != null) {
/*  42: 59 */       new SimpleIdentifierSource()
/*  43:    */       {
/*  44:    */         public SingularAttributeSource getIdentifierAttributeSource()
/*  45:    */         {
/*  46: 62 */           return new SingularIdentifierAttributeSourceImpl(RootEntitySourceImpl.this.entityElement().getId(), RootEntitySourceImpl.this.sourceMappingDocument().getMappingLocalBindingContext());
/*  47:    */         }
/*  48:    */         
/*  49:    */         public IdGenerator getIdentifierGeneratorDescriptor()
/*  50:    */         {
/*  51: 70 */           if (RootEntitySourceImpl.this.entityElement().getId().getGenerator() != null)
/*  52:    */           {
/*  53: 71 */             String generatorName = RootEntitySourceImpl.this.entityElement().getId().getGenerator().getClazz();
/*  54: 72 */             IdGenerator idGenerator = RootEntitySourceImpl.this.sourceMappingDocument().getMappingLocalBindingContext().getMetadataImplementor().getIdGenerator(generatorName);
/*  55: 75 */             if (idGenerator == null) {
/*  56: 76 */               idGenerator = new IdGenerator(RootEntitySourceImpl.this.getEntityName() + generatorName, generatorName, Helper.extractParameters(RootEntitySourceImpl.this.entityElement().getId().getGenerator().getParam()));
/*  57:    */             }
/*  58: 82 */             return idGenerator;
/*  59:    */           }
/*  60: 84 */           return null;
/*  61:    */         }
/*  62:    */         
/*  63:    */         public IdentifierSource.Nature getNature()
/*  64:    */         {
/*  65: 89 */           return IdentifierSource.Nature.SIMPLE;
/*  66:    */         }
/*  67:    */       };
/*  68:    */     }
/*  69: 93 */     return null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public SingularAttributeSource getVersioningAttributeSource()
/*  73:    */   {
/*  74: 98 */     if (entityElement().getVersion() != null) {
/*  75: 99 */       return new VersionAttributeSourceImpl(entityElement().getVersion(), sourceMappingDocument().getMappingLocalBindingContext());
/*  76:    */     }
/*  77:104 */     if (entityElement().getTimestamp() != null) {
/*  78:105 */       return new TimestampAttributeSourceImpl(entityElement().getTimestamp(), sourceMappingDocument().getMappingLocalBindingContext());
/*  79:    */     }
/*  80:110 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public EntityMode getEntityMode()
/*  84:    */   {
/*  85:115 */     return determineEntityMode();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isMutable()
/*  89:    */   {
/*  90:120 */     return entityElement().isMutable();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isExplicitPolymorphism()
/*  94:    */   {
/*  95:126 */     return "explicit".equals(entityElement().getPolymorphism());
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getWhere()
/*  99:    */   {
/* 100:131 */     return entityElement().getWhere();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getRowId()
/* 104:    */   {
/* 105:136 */     return entityElement().getRowid();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public OptimisticLockStyle getOptimisticLockStyle()
/* 109:    */   {
/* 110:141 */     String optimisticLockModeString = Helper.getStringValue(entityElement().getOptimisticLock(), "version");
/* 111:    */     try
/* 112:    */     {
/* 113:143 */       return OptimisticLockStyle.valueOf(optimisticLockModeString.toUpperCase());
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:146 */       throw new MappingException("Unknown optimistic-lock value : " + optimisticLockModeString, sourceMappingDocument().getOrigin());
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Caching getCaching()
/* 122:    */   {
/* 123:155 */     JaxbCacheElement cache = entityElement().getCache();
/* 124:156 */     if (cache == null) {
/* 125:157 */       return null;
/* 126:    */     }
/* 127:159 */     String region = cache.getRegion() != null ? cache.getRegion() : getEntityName();
/* 128:160 */     AccessType accessType = (AccessType)Enum.valueOf(AccessType.class, cache.getUsage());
/* 129:161 */     boolean cacheLazyProps = !"non-lazy".equals(cache.getInclude());
/* 130:162 */     return new Caching(region, accessType, cacheLazyProps);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public TableSource getPrimaryTable()
/* 134:    */   {
/* 135:167 */     new TableSource()
/* 136:    */     {
/* 137:    */       public String getExplicitSchemaName()
/* 138:    */       {
/* 139:170 */         return RootEntitySourceImpl.this.entityElement().getSchema();
/* 140:    */       }
/* 141:    */       
/* 142:    */       public String getExplicitCatalogName()
/* 143:    */       {
/* 144:175 */         return RootEntitySourceImpl.this.entityElement().getCatalog();
/* 145:    */       }
/* 146:    */       
/* 147:    */       public String getExplicitTableName()
/* 148:    */       {
/* 149:180 */         return RootEntitySourceImpl.this.entityElement().getTable();
/* 150:    */       }
/* 151:    */       
/* 152:    */       public String getLogicalName()
/* 153:    */       {
/* 154:186 */         return null;
/* 155:    */       }
/* 156:    */     };
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getDiscriminatorMatchValue()
/* 160:    */   {
/* 161:193 */     return entityElement().getDiscriminatorValue();
/* 162:    */   }
/* 163:    */   
/* 164:    */   public DiscriminatorSource getDiscriminatorSource()
/* 165:    */   {
/* 166:198 */     final JaxbHibernateMapping.JaxbClass.JaxbDiscriminator discriminatorElement = entityElement().getDiscriminator();
/* 167:199 */     if (discriminatorElement == null) {
/* 168:200 */       return null;
/* 169:    */     }
/* 170:203 */     new DiscriminatorSource()
/* 171:    */     {
/* 172:    */       public RelationalValueSource getDiscriminatorRelationalValueSource()
/* 173:    */       {
/* 174:206 */         if (StringHelper.isNotEmpty(discriminatorElement.getColumnAttribute())) {
/* 175:207 */           return new ColumnAttributeSourceImpl(null, discriminatorElement.getColumnAttribute(), discriminatorElement.isInsert(), discriminatorElement.isInsert());
/* 176:    */         }
/* 177:214 */         if (StringHelper.isNotEmpty(discriminatorElement.getFormulaAttribute())) {
/* 178:215 */           return new FormulaImpl(null, discriminatorElement.getFormulaAttribute());
/* 179:    */         }
/* 180:217 */         if (discriminatorElement.getColumn() != null) {
/* 181:218 */           return new ColumnSourceImpl(null, discriminatorElement.getColumn(), discriminatorElement.isInsert(), discriminatorElement.isInsert());
/* 182:    */         }
/* 183:225 */         if (StringHelper.isNotEmpty(discriminatorElement.getFormula())) {
/* 184:226 */           return new FormulaImpl(null, discriminatorElement.getFormula());
/* 185:    */         }
/* 186:229 */         throw new MappingException("could not determine source of discriminator mapping", RootEntitySourceImpl.this.getOrigin());
/* 187:    */       }
/* 188:    */       
/* 189:    */       public String getExplicitHibernateTypeName()
/* 190:    */       {
/* 191:235 */         return discriminatorElement.getType();
/* 192:    */       }
/* 193:    */       
/* 194:    */       public boolean isForced()
/* 195:    */       {
/* 196:240 */         return discriminatorElement.isForce();
/* 197:    */       }
/* 198:    */       
/* 199:    */       public boolean isInserted()
/* 200:    */       {
/* 201:245 */         return discriminatorElement.isInsert();
/* 202:    */       }
/* 203:    */     };
/* 204:    */   }
/* 205:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.RootEntitySourceImpl
 * JD-Core Version:    0.7.0.1
 */