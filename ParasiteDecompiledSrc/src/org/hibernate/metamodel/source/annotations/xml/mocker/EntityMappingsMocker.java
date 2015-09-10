/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.internal.CoreMessageLogger;
/*   7:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbAccessType;
/*   8:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEmbeddable;
/*   9:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntity;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbEntityMappings;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbMappedSuperclass;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPersistenceUnitDefaults;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbPersistenceUnitMetadata;
/*  14:    */ import org.hibernate.service.ServiceRegistry;
/*  15:    */ import org.jboss.jandex.Index;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class EntityMappingsMocker
/*  19:    */ {
/*  20: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EntityMappingsMocker.class.getName());
/*  21:    */   private final List<JaxbEntityMappings> entityMappingsList;
/*  22:    */   private Default globalDefaults;
/*  23:    */   private final IndexBuilder indexBuilder;
/*  24:    */   private final GlobalAnnotations globalAnnotations;
/*  25:    */   
/*  26:    */   public EntityMappingsMocker(List<JaxbEntityMappings> entityMappingsList, Index index, ServiceRegistry serviceRegistry)
/*  27:    */   {
/*  28: 62 */     this.entityMappingsList = entityMappingsList;
/*  29: 63 */     this.indexBuilder = new IndexBuilder(index, serviceRegistry);
/*  30: 64 */     this.globalAnnotations = new GlobalAnnotations();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Index mockNewIndex()
/*  34:    */   {
/*  35: 73 */     processPersistenceUnitMetadata(this.entityMappingsList);
/*  36: 74 */     processEntityMappings(this.entityMappingsList);
/*  37: 75 */     processGlobalAnnotations();
/*  38: 76 */     return this.indexBuilder.build(this.globalDefaults);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void processPersistenceUnitMetadata(List<JaxbEntityMappings> entityMappingsList)
/*  42:    */   {
/*  43: 83 */     for (JaxbEntityMappings entityMappings : entityMappingsList)
/*  44:    */     {
/*  45: 85 */       JaxbPersistenceUnitMetadata pum = entityMappings.getPersistenceUnitMetadata();
/*  46: 86 */       if (this.globalDefaults != null)
/*  47:    */       {
/*  48: 87 */         LOG.duplicateMetadata();
/*  49: 88 */         return;
/*  50:    */       }
/*  51: 90 */       if (pum != null)
/*  52:    */       {
/*  53: 93 */         this.globalDefaults = new Default();
/*  54: 94 */         if (pum.getXmlMappingMetadataComplete() != null)
/*  55:    */         {
/*  56: 95 */           this.globalDefaults.setMetadataComplete(Boolean.valueOf(true));
/*  57: 96 */           this.indexBuilder.mappingMetadataComplete();
/*  58:    */         }
/*  59: 98 */         JaxbPersistenceUnitDefaults pud = pum.getPersistenceUnitDefaults();
/*  60: 99 */         if (pud == null) {
/*  61:100 */           return;
/*  62:    */         }
/*  63:102 */         this.globalDefaults.setSchema(pud.getSchema());
/*  64:103 */         this.globalDefaults.setCatalog(pud.getCatalog());
/*  65:    */         
/*  66:105 */         this.globalDefaults.setCascadePersist(Boolean.valueOf(pud.getCascadePersist() != null));
/*  67:106 */         new PersistenceMetadataMocker(this.indexBuilder, pud).process();
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void processEntityMappings(List<JaxbEntityMappings> entityMappingsList)
/*  73:    */   {
/*  74:112 */     List<AbstractEntityObjectMocker> mockerList = new ArrayList();
/*  75:113 */     for (JaxbEntityMappings entityMappings : entityMappingsList)
/*  76:    */     {
/*  77:114 */       defaults = getEntityMappingsDefaults(entityMappings);
/*  78:115 */       this.globalAnnotations.collectGlobalMappings(entityMappings, defaults);
/*  79:116 */       for (JaxbMappedSuperclass mappedSuperclass : entityMappings.getMappedSuperclass())
/*  80:    */       {
/*  81:117 */         AbstractEntityObjectMocker mocker = new MappedSuperclassMocker(this.indexBuilder, mappedSuperclass, defaults);
/*  82:    */         
/*  83:119 */         mockerList.add(mocker);
/*  84:120 */         mocker.preProcess();
/*  85:    */       }
/*  86:122 */       for (JaxbEmbeddable embeddable : entityMappings.getEmbeddable())
/*  87:    */       {
/*  88:123 */         AbstractEntityObjectMocker mocker = new EmbeddableMocker(this.indexBuilder, embeddable, defaults);
/*  89:    */         
/*  90:125 */         mockerList.add(mocker);
/*  91:126 */         mocker.preProcess();
/*  92:    */       }
/*  93:128 */       for (JaxbEntity entity : entityMappings.getEntity())
/*  94:    */       {
/*  95:129 */         this.globalAnnotations.collectGlobalMappings(entity, defaults);
/*  96:130 */         AbstractEntityObjectMocker mocker = new EntityMocker(this.indexBuilder, entity, defaults);
/*  97:    */         
/*  98:132 */         mockerList.add(mocker);
/*  99:133 */         mocker.preProcess();
/* 100:    */       }
/* 101:    */     }
/* 102:    */     Default defaults;
/* 103:136 */     for (AbstractEntityObjectMocker mocker : mockerList) {
/* 104:137 */       mocker.process();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void processGlobalAnnotations()
/* 109:    */   {
/* 110:142 */     if (this.globalAnnotations.hasGlobalConfiguration())
/* 111:    */     {
/* 112:143 */       this.indexBuilder.collectGlobalConfigurationFromIndex(this.globalAnnotations);
/* 113:144 */       new GlobalAnnotationMocker(this.indexBuilder, this.globalAnnotations).process();
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   private Default getEntityMappingsDefaults(JaxbEntityMappings entityMappings)
/* 118:    */   {
/* 119:151 */     Default entityMappingDefault = new Default();
/* 120:152 */     entityMappingDefault.setPackageName(entityMappings.getPackage());
/* 121:153 */     entityMappingDefault.setSchema(entityMappings.getSchema());
/* 122:154 */     entityMappingDefault.setCatalog(entityMappings.getCatalog());
/* 123:155 */     entityMappingDefault.setAccess(entityMappings.getAccess());
/* 124:156 */     Default defaults = new Default();
/* 125:157 */     defaults.override(this.globalDefaults);
/* 126:158 */     defaults.override(entityMappingDefault);
/* 127:159 */     return defaults;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static class Default
/* 131:    */     implements Serializable
/* 132:    */   {
/* 133:    */     private JaxbAccessType access;
/* 134:    */     private String packageName;
/* 135:    */     private String schema;
/* 136:    */     private String catalog;
/* 137:    */     private Boolean metadataComplete;
/* 138:    */     private Boolean cascadePersist;
/* 139:    */     
/* 140:    */     public JaxbAccessType getAccess()
/* 141:    */     {
/* 142:172 */       return this.access;
/* 143:    */     }
/* 144:    */     
/* 145:    */     void setAccess(JaxbAccessType access)
/* 146:    */     {
/* 147:176 */       this.access = access;
/* 148:    */     }
/* 149:    */     
/* 150:    */     public String getCatalog()
/* 151:    */     {
/* 152:180 */       return this.catalog;
/* 153:    */     }
/* 154:    */     
/* 155:    */     void setCatalog(String catalog)
/* 156:    */     {
/* 157:184 */       this.catalog = catalog;
/* 158:    */     }
/* 159:    */     
/* 160:    */     public String getPackageName()
/* 161:    */     {
/* 162:188 */       return this.packageName;
/* 163:    */     }
/* 164:    */     
/* 165:    */     void setPackageName(String packageName)
/* 166:    */     {
/* 167:192 */       this.packageName = packageName;
/* 168:    */     }
/* 169:    */     
/* 170:    */     public String getSchema()
/* 171:    */     {
/* 172:196 */       return this.schema;
/* 173:    */     }
/* 174:    */     
/* 175:    */     void setSchema(String schema)
/* 176:    */     {
/* 177:200 */       this.schema = schema;
/* 178:    */     }
/* 179:    */     
/* 180:    */     public Boolean isMetadataComplete()
/* 181:    */     {
/* 182:204 */       return this.metadataComplete;
/* 183:    */     }
/* 184:    */     
/* 185:    */     void setMetadataComplete(Boolean metadataComplete)
/* 186:    */     {
/* 187:208 */       this.metadataComplete = metadataComplete;
/* 188:    */     }
/* 189:    */     
/* 190:    */     public Boolean isCascadePersist()
/* 191:    */     {
/* 192:212 */       return this.cascadePersist;
/* 193:    */     }
/* 194:    */     
/* 195:    */     void setCascadePersist(Boolean cascadePersist)
/* 196:    */     {
/* 197:216 */       this.cascadePersist = cascadePersist;
/* 198:    */     }
/* 199:    */     
/* 200:    */     void override(Default globalDefault)
/* 201:    */     {
/* 202:220 */       if (globalDefault != null)
/* 203:    */       {
/* 204:221 */         if (globalDefault.getAccess() != null) {
/* 205:222 */           this.access = globalDefault.getAccess();
/* 206:    */         }
/* 207:224 */         if (globalDefault.getPackageName() != null) {
/* 208:225 */           this.packageName = globalDefault.getPackageName();
/* 209:    */         }
/* 210:227 */         if (globalDefault.getSchema() != null) {
/* 211:228 */           this.schema = globalDefault.getSchema();
/* 212:    */         }
/* 213:230 */         if (globalDefault.getCatalog() != null) {
/* 214:231 */           this.catalog = globalDefault.getCatalog();
/* 215:    */         }
/* 216:233 */         if (globalDefault.isCascadePersist() != null) {
/* 217:234 */           this.cascadePersist = globalDefault.isCascadePersist();
/* 218:    */         }
/* 219:236 */         if (globalDefault.isMetadataComplete() != null) {
/* 220:237 */           this.metadataComplete = globalDefault.isMetadataComplete();
/* 221:    */         }
/* 222:    */       }
/* 223:    */     }
/* 224:    */   }
/* 225:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.EntityMappingsMocker
 * JD-Core Version:    0.7.0.1
 */