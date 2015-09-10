/*   1:    */ package org.hibernate.cfg.annotations.reflection;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import javax.persistence.AccessType;
/*   9:    */ import org.dom4j.Document;
/*  10:    */ import org.dom4j.Element;
/*  11:    */ import org.hibernate.AnnotationException;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class XMLContext
/*  17:    */   implements Serializable
/*  18:    */ {
/*  19: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, XMLContext.class.getName());
/*  20:    */   private Default globalDefaults;
/*  21:    */   private Map<String, Element> classOverriding;
/*  22:    */   private Map<String, Default> defaultsOverriding;
/*  23:    */   private List<Element> defaultElements;
/*  24:    */   private List<String> defaultEntityListeners;
/*  25:    */   private boolean hasContext;
/*  26:    */   
/*  27:    */   public XMLContext()
/*  28:    */   {
/*  29: 50 */     this.classOverriding = new HashMap();
/*  30: 51 */     this.defaultsOverriding = new HashMap();
/*  31: 52 */     this.defaultElements = new ArrayList();
/*  32: 53 */     this.defaultEntityListeners = new ArrayList();
/*  33: 54 */     this.hasContext = false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public List<String> addDocument(Document doc)
/*  37:    */   {
/*  38: 62 */     this.hasContext = true;
/*  39: 63 */     List<String> addedClasses = new ArrayList();
/*  40: 64 */     Element root = doc.getRootElement();
/*  41:    */     
/*  42: 66 */     Element metadata = root.element("persistence-unit-metadata");
/*  43: 67 */     if (metadata != null) {
/*  44: 68 */       if (this.globalDefaults == null)
/*  45:    */       {
/*  46: 69 */         this.globalDefaults = new Default();
/*  47: 70 */         this.globalDefaults.setMetadataComplete(metadata.element("xml-mapping-metadata-complete") != null ? Boolean.TRUE : null);
/*  48:    */         
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 75 */         Element defaultElement = metadata.element("persistence-unit-defaults");
/*  53: 76 */         if (defaultElement != null)
/*  54:    */         {
/*  55: 77 */           Element unitElement = defaultElement.element("schema");
/*  56: 78 */           this.globalDefaults.setSchema(unitElement != null ? unitElement.getTextTrim() : null);
/*  57: 79 */           unitElement = defaultElement.element("catalog");
/*  58: 80 */           this.globalDefaults.setCatalog(unitElement != null ? unitElement.getTextTrim() : null);
/*  59: 81 */           unitElement = defaultElement.element("access");
/*  60: 82 */           setAccess(unitElement, this.globalDefaults);
/*  61: 83 */           unitElement = defaultElement.element("cascade-persist");
/*  62: 84 */           this.globalDefaults.setCascadePersist(unitElement != null ? Boolean.TRUE : null);
/*  63: 85 */           unitElement = defaultElement.element("delimited-identifiers");
/*  64: 86 */           this.globalDefaults.setDelimitedIdentifiers(unitElement != null ? Boolean.TRUE : null);
/*  65: 87 */           this.defaultEntityListeners.addAll(addEntityListenerClasses(defaultElement, null, addedClasses));
/*  66:    */         }
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70: 91 */         LOG.duplicateMetadata();
/*  71:    */       }
/*  72:    */     }
/*  73: 96 */     Default entityMappingDefault = new Default();
/*  74: 97 */     Element unitElement = root.element("package");
/*  75: 98 */     String packageName = unitElement != null ? unitElement.getTextTrim() : null;
/*  76: 99 */     entityMappingDefault.setPackageName(packageName);
/*  77:100 */     unitElement = root.element("schema");
/*  78:101 */     entityMappingDefault.setSchema(unitElement != null ? unitElement.getTextTrim() : null);
/*  79:102 */     unitElement = root.element("catalog");
/*  80:103 */     entityMappingDefault.setCatalog(unitElement != null ? unitElement.getTextTrim() : null);
/*  81:104 */     unitElement = root.element("access");
/*  82:105 */     setAccess(unitElement, entityMappingDefault);
/*  83:106 */     this.defaultElements.add(root);
/*  84:    */     
/*  85:108 */     List<Element> entities = root.elements("entity");
/*  86:109 */     addClass(entities, packageName, entityMappingDefault, addedClasses);
/*  87:    */     
/*  88:111 */     entities = root.elements("mapped-superclass");
/*  89:112 */     addClass(entities, packageName, entityMappingDefault, addedClasses);
/*  90:    */     
/*  91:114 */     entities = root.elements("embeddable");
/*  92:115 */     addClass(entities, packageName, entityMappingDefault, addedClasses);
/*  93:116 */     return addedClasses;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void setAccess(Element unitElement, Default defaultType)
/*  97:    */   {
/*  98:120 */     if (unitElement != null)
/*  99:    */     {
/* 100:121 */       String access = unitElement.getTextTrim();
/* 101:122 */       setAccess(access, defaultType);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void setAccess(String access, Default defaultType)
/* 106:    */   {
/* 107:128 */     if (access != null)
/* 108:    */     {
/* 109:    */       AccessType type;
/* 110:    */       try
/* 111:    */       {
/* 112:130 */         type = AccessType.valueOf(access);
/* 113:    */       }
/* 114:    */       catch (IllegalArgumentException e)
/* 115:    */       {
/* 116:133 */         throw new AnnotationException("Invalid access type " + access + " (check your xml configuration)");
/* 117:    */       }
/* 118:135 */       defaultType.setAccess(type);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void addClass(List<Element> entities, String packageName, Default defaults, List<String> addedClasses)
/* 123:    */   {
/* 124:140 */     for (Element element : entities)
/* 125:    */     {
/* 126:141 */       String className = buildSafeClassName(element.attributeValue("class"), packageName);
/* 127:142 */       if (this.classOverriding.containsKey(className)) {
/* 128:144 */         throw new IllegalStateException("Duplicate XML entry for " + className);
/* 129:    */       }
/* 130:146 */       addedClasses.add(className);
/* 131:147 */       this.classOverriding.put(className, element);
/* 132:148 */       Default localDefault = new Default();
/* 133:149 */       localDefault.override(defaults);
/* 134:150 */       String metadataCompleteString = element.attributeValue("metadata-complete");
/* 135:151 */       if (metadataCompleteString != null) {
/* 136:152 */         localDefault.setMetadataComplete(Boolean.valueOf(Boolean.parseBoolean(metadataCompleteString)));
/* 137:    */       }
/* 138:154 */       String access = element.attributeValue("access");
/* 139:155 */       setAccess(access, localDefault);
/* 140:156 */       this.defaultsOverriding.put(className, localDefault);
/* 141:    */       
/* 142:158 */       LOG.debugf("Adding XML overriding information for %s", className);
/* 143:159 */       addEntityListenerClasses(element, packageName, addedClasses);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   private List<String> addEntityListenerClasses(Element element, String packageName, List<String> addedClasses)
/* 148:    */   {
/* 149:164 */     List<String> localAddedClasses = new ArrayList();
/* 150:165 */     Element listeners = element.element("entity-listeners");
/* 151:166 */     if (listeners != null)
/* 152:    */     {
/* 153:168 */       List<Element> elements = listeners.elements("entity-listener");
/* 154:169 */       for (Element listener : elements)
/* 155:    */       {
/* 156:170 */         String listenerClassName = buildSafeClassName(listener.attributeValue("class"), packageName);
/* 157:171 */         if (this.classOverriding.containsKey(listenerClassName))
/* 158:    */         {
/* 159:173 */           if ("entity-listener".equals(((Element)this.classOverriding.get(listenerClassName)).getName())) {
/* 160:174 */             LOG.duplicateListener(listenerClassName);
/* 161:    */           } else {
/* 162:177 */             throw new IllegalStateException("Duplicate XML entry for " + listenerClassName);
/* 163:    */           }
/* 164:    */         }
/* 165:    */         else
/* 166:    */         {
/* 167:179 */           localAddedClasses.add(listenerClassName);
/* 168:180 */           this.classOverriding.put(listenerClassName, listener);
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:183 */     LOG.debugf("Adding XML overriding information for listeners: %s", localAddedClasses);
/* 173:184 */     addedClasses.addAll(localAddedClasses);
/* 174:185 */     return localAddedClasses;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static String buildSafeClassName(String className, String defaultPackageName)
/* 178:    */   {
/* 179:189 */     if ((className.indexOf('.') < 0) && (StringHelper.isNotEmpty(defaultPackageName))) {
/* 180:190 */       className = StringHelper.qualify(defaultPackageName, className);
/* 181:    */     }
/* 182:192 */     return className;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static String buildSafeClassName(String className, Default defaults)
/* 186:    */   {
/* 187:196 */     return buildSafeClassName(className, defaults.getPackageName());
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Default getDefault(String className)
/* 191:    */   {
/* 192:200 */     Default xmlDefault = new Default();
/* 193:201 */     xmlDefault.override(this.globalDefaults);
/* 194:202 */     if (className != null)
/* 195:    */     {
/* 196:203 */       Default entityMappingOverriding = (Default)this.defaultsOverriding.get(className);
/* 197:204 */       xmlDefault.override(entityMappingOverriding);
/* 198:    */     }
/* 199:206 */     return xmlDefault;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Element getXMLTree(String className)
/* 203:    */   {
/* 204:210 */     return (Element)this.classOverriding.get(className);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public List<Element> getAllDocuments()
/* 208:    */   {
/* 209:214 */     return this.defaultElements;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean hasContext()
/* 213:    */   {
/* 214:218 */     return this.hasContext;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static class Default
/* 218:    */     implements Serializable
/* 219:    */   {
/* 220:    */     private AccessType access;
/* 221:    */     private String packageName;
/* 222:    */     private String schema;
/* 223:    */     private String catalog;
/* 224:    */     private Boolean metadataComplete;
/* 225:    */     private Boolean cascadePersist;
/* 226:    */     private Boolean delimitedIdentifier;
/* 227:    */     
/* 228:    */     public AccessType getAccess()
/* 229:    */     {
/* 230:231 */       return this.access;
/* 231:    */     }
/* 232:    */     
/* 233:    */     protected void setAccess(AccessType access)
/* 234:    */     {
/* 235:235 */       this.access = access;
/* 236:    */     }
/* 237:    */     
/* 238:    */     public String getCatalog()
/* 239:    */     {
/* 240:239 */       return this.catalog;
/* 241:    */     }
/* 242:    */     
/* 243:    */     protected void setCatalog(String catalog)
/* 244:    */     {
/* 245:243 */       this.catalog = catalog;
/* 246:    */     }
/* 247:    */     
/* 248:    */     public String getPackageName()
/* 249:    */     {
/* 250:247 */       return this.packageName;
/* 251:    */     }
/* 252:    */     
/* 253:    */     protected void setPackageName(String packageName)
/* 254:    */     {
/* 255:251 */       this.packageName = packageName;
/* 256:    */     }
/* 257:    */     
/* 258:    */     public String getSchema()
/* 259:    */     {
/* 260:255 */       return this.schema;
/* 261:    */     }
/* 262:    */     
/* 263:    */     protected void setSchema(String schema)
/* 264:    */     {
/* 265:259 */       this.schema = schema;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public Boolean getMetadataComplete()
/* 269:    */     {
/* 270:263 */       return this.metadataComplete;
/* 271:    */     }
/* 272:    */     
/* 273:    */     public boolean canUseJavaAnnotations()
/* 274:    */     {
/* 275:267 */       return (this.metadataComplete == null) || (!this.metadataComplete.booleanValue());
/* 276:    */     }
/* 277:    */     
/* 278:    */     protected void setMetadataComplete(Boolean metadataComplete)
/* 279:    */     {
/* 280:271 */       this.metadataComplete = metadataComplete;
/* 281:    */     }
/* 282:    */     
/* 283:    */     public Boolean getCascadePersist()
/* 284:    */     {
/* 285:275 */       return this.cascadePersist;
/* 286:    */     }
/* 287:    */     
/* 288:    */     void setCascadePersist(Boolean cascadePersist)
/* 289:    */     {
/* 290:279 */       this.cascadePersist = cascadePersist;
/* 291:    */     }
/* 292:    */     
/* 293:    */     public void override(Default globalDefault)
/* 294:    */     {
/* 295:283 */       if (globalDefault != null)
/* 296:    */       {
/* 297:284 */         if (globalDefault.getAccess() != null) {
/* 298:284 */           this.access = globalDefault.getAccess();
/* 299:    */         }
/* 300:285 */         if (globalDefault.getPackageName() != null) {
/* 301:285 */           this.packageName = globalDefault.getPackageName();
/* 302:    */         }
/* 303:286 */         if (globalDefault.getSchema() != null) {
/* 304:286 */           this.schema = globalDefault.getSchema();
/* 305:    */         }
/* 306:287 */         if (globalDefault.getCatalog() != null) {
/* 307:287 */           this.catalog = globalDefault.getCatalog();
/* 308:    */         }
/* 309:288 */         if (globalDefault.getDelimitedIdentifier() != null) {
/* 310:288 */           this.delimitedIdentifier = globalDefault.getDelimitedIdentifier();
/* 311:    */         }
/* 312:289 */         if (globalDefault.getMetadataComplete() != null) {
/* 313:290 */           this.metadataComplete = globalDefault.getMetadataComplete();
/* 314:    */         }
/* 315:293 */         if (globalDefault.getCascadePersist() != null) {
/* 316:293 */           this.cascadePersist = globalDefault.getCascadePersist();
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:    */     
/* 321:    */     public void setDelimitedIdentifiers(Boolean delimitedIdentifier)
/* 322:    */     {
/* 323:298 */       this.delimitedIdentifier = delimitedIdentifier;
/* 324:    */     }
/* 325:    */     
/* 326:    */     public Boolean getDelimitedIdentifier()
/* 327:    */     {
/* 328:302 */       return this.delimitedIdentifier;
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public List<String> getDefaultEntityListeners()
/* 333:    */   {
/* 334:307 */     return this.defaultEntityListeners;
/* 335:    */   }
/* 336:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.reflection.XMLContext
 * JD-Core Version:    0.7.0.1
 */