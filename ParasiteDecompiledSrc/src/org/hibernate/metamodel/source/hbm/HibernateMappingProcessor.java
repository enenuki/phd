/*   1:    */ package org.hibernate.metamodel.source.hbm;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.engine.spi.FilterDefinition;
/*   9:    */ import org.hibernate.internal.jaxb.Origin;
/*  10:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchProfileElement;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbFetchProfileElement.JaxbFetch;
/*  12:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping;
/*  13:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbDatabaseObject;
/*  14:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbDatabaseObject.JaxbDefinition;
/*  15:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbDatabaseObject.JaxbDialectScope;
/*  16:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbFilterDef;
/*  17:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam;
/*  18:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbIdentifierGenerator;
/*  19:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbImport;
/*  20:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbHibernateMapping.JaxbTypedef;
/*  21:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbParamElement;
/*  22:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbQueryElement;
/*  23:    */ import org.hibernate.internal.jaxb.mapping.hbm.JaxbSqlQueryElement;
/*  24:    */ import org.hibernate.internal.util.StringHelper;
/*  25:    */ import org.hibernate.internal.util.Value;
/*  26:    */ import org.hibernate.internal.util.Value.DeferredInitializer;
/*  27:    */ import org.hibernate.metamodel.binding.FetchProfile;
/*  28:    */ import org.hibernate.metamodel.binding.FetchProfile.Fetch;
/*  29:    */ import org.hibernate.metamodel.binding.TypeDef;
/*  30:    */ import org.hibernate.metamodel.relational.AuxiliaryDatabaseObject;
/*  31:    */ import org.hibernate.metamodel.relational.BasicAuxiliaryDatabaseObjectImpl;
/*  32:    */ import org.hibernate.metamodel.relational.Database;
/*  33:    */ import org.hibernate.metamodel.source.MappingException;
/*  34:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  35:    */ import org.hibernate.service.ServiceRegistry;
/*  36:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  37:    */ import org.hibernate.service.classloading.spi.ClassLoadingException;
/*  38:    */ import org.hibernate.type.Type;
/*  39:    */ import org.hibernate.type.TypeResolver;
/*  40:    */ 
/*  41:    */ public class HibernateMappingProcessor
/*  42:    */ {
/*  43:    */   private final MetadataImplementor metadata;
/*  44:    */   private final MappingDocument mappingDocument;
/*  45: 62 */   private Value<ClassLoaderService> classLoaderService = new Value(new Value.DeferredInitializer()
/*  46:    */   {
/*  47:    */     public ClassLoaderService initialize()
/*  48:    */     {
/*  49: 66 */       return (ClassLoaderService)HibernateMappingProcessor.this.metadata.getServiceRegistry().getService(ClassLoaderService.class);
/*  50:    */     }
/*  51: 62 */   });
/*  52:    */   
/*  53:    */   public HibernateMappingProcessor(MetadataImplementor metadata, MappingDocument mappingDocument)
/*  54:    */   {
/*  55: 72 */     this.metadata = metadata;
/*  56: 73 */     this.mappingDocument = mappingDocument;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private JaxbHibernateMapping mappingRoot()
/*  60:    */   {
/*  61: 77 */     return this.mappingDocument.getMappingRoot();
/*  62:    */   }
/*  63:    */   
/*  64:    */   private Origin origin()
/*  65:    */   {
/*  66: 81 */     return this.mappingDocument.getOrigin();
/*  67:    */   }
/*  68:    */   
/*  69:    */   private HbmBindingContext bindingContext()
/*  70:    */   {
/*  71: 85 */     return this.mappingDocument.getMappingLocalBindingContext();
/*  72:    */   }
/*  73:    */   
/*  74:    */   private <T> Class<T> classForName(String name)
/*  75:    */   {
/*  76: 89 */     return ((ClassLoaderService)this.classLoaderService.getValue()).classForName(bindingContext().qualifyClassName(name));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void processIndependentMetadata()
/*  80:    */   {
/*  81: 93 */     processDatabaseObjectDefinitions();
/*  82: 94 */     processTypeDefinitions();
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void processDatabaseObjectDefinitions()
/*  86:    */   {
/*  87: 98 */     if (mappingRoot().getDatabaseObject() == null) {
/*  88: 99 */       return;
/*  89:    */     }
/*  90:102 */     for (JaxbHibernateMapping.JaxbDatabaseObject databaseObjectElement : mappingRoot().getDatabaseObject())
/*  91:    */     {
/*  92:    */       AuxiliaryDatabaseObject auxiliaryDatabaseObject;
/*  93:104 */       if (databaseObjectElement.getDefinition() != null)
/*  94:    */       {
/*  95:105 */         String className = databaseObjectElement.getDefinition().getClazz();
/*  96:    */         try
/*  97:    */         {
/*  98:107 */           auxiliaryDatabaseObject = (AuxiliaryDatabaseObject)classForName(className).newInstance();
/*  99:    */         }
/* 100:    */         catch (ClassLoadingException e)
/* 101:    */         {
/* 102:    */           AuxiliaryDatabaseObject auxiliaryDatabaseObject;
/* 103:110 */           throw e;
/* 104:    */         }
/* 105:    */         catch (Exception e)
/* 106:    */         {
/* 107:113 */           throw new MappingException("could not instantiate custom database object class [" + className + "]", origin());
/* 108:    */         }
/* 109:    */       }
/* 110:    */       else
/* 111:    */       {
/* 112:120 */         Set<String> dialectScopes = new HashSet();
/* 113:121 */         if (databaseObjectElement.getDialectScope() != null) {
/* 114:122 */           for (JaxbHibernateMapping.JaxbDatabaseObject.JaxbDialectScope dialectScope : databaseObjectElement.getDialectScope()) {
/* 115:123 */             dialectScopes.add(dialectScope.getName());
/* 116:    */           }
/* 117:    */         }
/* 118:126 */         auxiliaryDatabaseObject = new BasicAuxiliaryDatabaseObjectImpl(this.metadata.getDatabase().getDefaultSchema(), databaseObjectElement.getCreate(), databaseObjectElement.getDrop(), dialectScopes);
/* 119:    */       }
/* 120:133 */       this.metadata.getDatabase().addAuxiliaryDatabaseObject(auxiliaryDatabaseObject);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void processTypeDefinitions()
/* 125:    */   {
/* 126:138 */     if (mappingRoot().getTypedef() == null) {
/* 127:139 */       return;
/* 128:    */     }
/* 129:142 */     for (JaxbHibernateMapping.JaxbTypedef typedef : mappingRoot().getTypedef())
/* 130:    */     {
/* 131:143 */       Map<String, String> parameters = new HashMap();
/* 132:144 */       for (JaxbParamElement paramElement : typedef.getParam()) {
/* 133:145 */         parameters.put(paramElement.getName(), paramElement.getValue());
/* 134:    */       }
/* 135:147 */       this.metadata.addTypeDefinition(new TypeDef(typedef.getName(), typedef.getClazz(), parameters));
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void processTypeDependentMetadata()
/* 140:    */   {
/* 141:158 */     processFilterDefinitions();
/* 142:159 */     processIdentifierGenerators();
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void processFilterDefinitions()
/* 146:    */   {
/* 147:163 */     if (mappingRoot().getFilterDef() == null) {
/* 148:164 */       return;
/* 149:    */     }
/* 150:167 */     for (JaxbHibernateMapping.JaxbFilterDef filterDefinition : mappingRoot().getFilterDef())
/* 151:    */     {
/* 152:168 */       String name = filterDefinition.getName();
/* 153:169 */       Map<String, Type> parameters = new HashMap();
/* 154:170 */       String condition = null;
/* 155:171 */       for (Object o : filterDefinition.getContent()) {
/* 156:172 */         if ((o instanceof String))
/* 157:    */         {
/* 158:174 */           if (condition != null) {}
/* 159:177 */           condition = (String)o;
/* 160:    */         }
/* 161:179 */         else if ((o instanceof JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam))
/* 162:    */         {
/* 163:180 */           JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam paramElement = (JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam)JaxbHibernateMapping.JaxbFilterDef.JaxbFilterParam.class.cast(o);
/* 164:    */           
/* 165:    */ 
/* 166:183 */           parameters.put(paramElement.getName(), this.metadata.getTypeResolver().heuristicType(paramElement.getType()));
/* 167:    */         }
/* 168:    */         else
/* 169:    */         {
/* 170:189 */           throw new MappingException("Unrecognized nested filter content", origin());
/* 171:    */         }
/* 172:    */       }
/* 173:192 */       if (condition == null) {
/* 174:193 */         condition = filterDefinition.getCondition();
/* 175:    */       }
/* 176:195 */       this.metadata.addFilterDefinition(new FilterDefinition(name, condition, parameters));
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void processIdentifierGenerators()
/* 181:    */   {
/* 182:200 */     if (mappingRoot().getIdentifierGenerator() == null) {
/* 183:201 */       return;
/* 184:    */     }
/* 185:204 */     for (JaxbHibernateMapping.JaxbIdentifierGenerator identifierGeneratorElement : mappingRoot().getIdentifierGenerator()) {
/* 186:205 */       this.metadata.registerIdentifierGenerator(identifierGeneratorElement.getName(), identifierGeneratorElement.getClazz());
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void processMappingDependentMetadata()
/* 191:    */   {
/* 192:213 */     processFetchProfiles();
/* 193:214 */     processImports();
/* 194:215 */     processResultSetMappings();
/* 195:216 */     processNamedQueries();
/* 196:    */   }
/* 197:    */   
/* 198:    */   private void processFetchProfiles()
/* 199:    */   {
/* 200:220 */     if (mappingRoot().getFetchProfile() == null) {
/* 201:221 */       return;
/* 202:    */     }
/* 203:224 */     processFetchProfiles(mappingRoot().getFetchProfile(), null);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void processFetchProfiles(List<JaxbFetchProfileElement> fetchProfiles, String containingEntityName)
/* 207:    */   {
/* 208:228 */     for (JaxbFetchProfileElement fetchProfile : fetchProfiles)
/* 209:    */     {
/* 210:229 */       String profileName = fetchProfile.getName();
/* 211:230 */       Set<FetchProfile.Fetch> fetches = new HashSet();
/* 212:231 */       for (JaxbFetchProfileElement.JaxbFetch fetch : fetchProfile.getFetch())
/* 213:    */       {
/* 214:232 */         String entityName = fetch.getEntity() == null ? containingEntityName : fetch.getEntity();
/* 215:233 */         if (entityName == null) {
/* 216:234 */           throw new MappingException("could not determine entity for fetch-profile fetch [" + profileName + "]:[" + fetch.getAssociation() + "]", origin());
/* 217:    */         }
/* 218:240 */         fetches.add(new FetchProfile.Fetch(entityName, fetch.getAssociation(), fetch.getStyle()));
/* 219:    */       }
/* 220:242 */       this.metadata.addFetchProfile(new FetchProfile(profileName, fetches));
/* 221:    */     }
/* 222:    */   }
/* 223:    */   
/* 224:    */   private void processImports()
/* 225:    */   {
/* 226:247 */     if (mappingRoot().getImport() == null) {
/* 227:248 */       return;
/* 228:    */     }
/* 229:251 */     for (JaxbHibernateMapping.JaxbImport importValue : mappingRoot().getImport())
/* 230:    */     {
/* 231:252 */       String className = this.mappingDocument.getMappingLocalBindingContext().qualifyClassName(importValue.getClazz());
/* 232:253 */       String rename = importValue.getRename();
/* 233:254 */       rename = rename == null ? StringHelper.unqualify(className) : rename;
/* 234:255 */       this.metadata.addImport(className, rename);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   private void processResultSetMappings()
/* 239:    */   {
/* 240:260 */     if (mappingRoot().getResultset() == null) {}
/* 241:    */   }
/* 242:    */   
/* 243:    */   private void processNamedQueries()
/* 244:    */   {
/* 245:268 */     if (mappingRoot().getQueryOrSqlQuery() == null) {
/* 246:269 */       return;
/* 247:    */     }
/* 248:272 */     for (Object queryOrSqlQuery : mappingRoot().getQueryOrSqlQuery()) {
/* 249:273 */       if (!JaxbQueryElement.class.isInstance(queryOrSqlQuery)) {
/* 250:276 */         if (!JaxbSqlQueryElement.class.isInstance(queryOrSqlQuery)) {
/* 251:280 */           throw new MappingException("unknown type of query: " + queryOrSqlQuery.getClass().getName(), origin());
/* 252:    */         }
/* 253:    */       }
/* 254:    */     }
/* 255:    */   }
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.hbm.HibernateMappingProcessor
 * JD-Core Version:    0.7.0.1
 */