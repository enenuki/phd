/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.persistence.Access;
/*   7:    */ import javax.persistence.EmbeddedId;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.IdClass;
/*  11:    */ import javax.persistence.Inheritance;
/*  12:    */ import javax.persistence.InheritanceType;
/*  13:    */ import org.hibernate.AnnotationException;
/*  14:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  15:    */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  16:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  17:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  18:    */ import org.hibernate.cfg.annotations.EntityBinder;
/*  19:    */ import org.hibernate.mapping.PersistentClass;
/*  20:    */ 
/*  21:    */ public class InheritanceState
/*  22:    */ {
/*  23:    */   private XClass clazz;
/*  24: 55 */   private boolean hasSiblings = false;
/*  25: 60 */   private boolean hasParents = false;
/*  26:    */   private InheritanceType type;
/*  27: 62 */   private boolean isEmbeddableSuperclass = false;
/*  28:    */   private Map<XClass, InheritanceState> inheritanceStatePerClass;
/*  29: 64 */   private List<XClass> classesToProcessForMappedSuperclass = new ArrayList();
/*  30:    */   private Mappings mappings;
/*  31:    */   private AccessType accessType;
/*  32:    */   private ElementsToProcess elementsToProcess;
/*  33:    */   private Boolean hasIdClassOrEmbeddedId;
/*  34:    */   
/*  35:    */   public InheritanceState(XClass clazz, Map<XClass, InheritanceState> inheritanceStatePerClass, Mappings mappings)
/*  36:    */   {
/*  37: 71 */     setClazz(clazz);
/*  38: 72 */     this.mappings = mappings;
/*  39: 73 */     this.inheritanceStatePerClass = inheritanceStatePerClass;
/*  40: 74 */     extractInheritanceType();
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void extractInheritanceType()
/*  44:    */   {
/*  45: 78 */     XAnnotatedElement element = getClazz();
/*  46: 79 */     Inheritance inhAnn = (Inheritance)element.getAnnotation(Inheritance.class);
/*  47: 80 */     javax.persistence.MappedSuperclass mappedSuperClass = (javax.persistence.MappedSuperclass)element.getAnnotation(javax.persistence.MappedSuperclass.class);
/*  48: 81 */     if (mappedSuperClass != null)
/*  49:    */     {
/*  50: 82 */       setEmbeddableSuperclass(true);
/*  51: 83 */       setType(inhAnn == null ? null : inhAnn.strategy());
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 86 */       setType(inhAnn == null ? InheritanceType.SINGLE_TABLE : inhAnn.strategy());
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   boolean hasTable()
/*  60:    */   {
/*  61: 91 */     return (!hasParents()) || (!InheritanceType.SINGLE_TABLE.equals(getType()));
/*  62:    */   }
/*  63:    */   
/*  64:    */   boolean hasDenormalizedTable()
/*  65:    */   {
/*  66: 95 */     return (hasParents()) && (InheritanceType.TABLE_PER_CLASS.equals(getType()));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static InheritanceState getInheritanceStateOfSuperEntity(XClass clazz, Map<XClass, InheritanceState> states)
/*  70:    */   {
/*  71:101 */     XClass superclass = clazz;
/*  72:    */     do
/*  73:    */     {
/*  74:103 */       superclass = superclass.getSuperclass();
/*  75:104 */       InheritanceState currentState = (InheritanceState)states.get(superclass);
/*  76:105 */       if ((currentState != null) && (!currentState.isEmbeddableSuperclass())) {
/*  77:106 */         return currentState;
/*  78:    */       }
/*  79:109 */     } while ((superclass != null) && (!Object.class.getName().equals(superclass.getName())));
/*  80:110 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static InheritanceState getSuperclassInheritanceState(XClass clazz, Map<XClass, InheritanceState> states)
/*  84:    */   {
/*  85:114 */     XClass superclass = clazz;
/*  86:    */     do
/*  87:    */     {
/*  88:116 */       superclass = superclass.getSuperclass();
/*  89:117 */       InheritanceState currentState = (InheritanceState)states.get(superclass);
/*  90:118 */       if (currentState != null) {
/*  91:119 */         return currentState;
/*  92:    */       }
/*  93:122 */     } while ((superclass != null) && (!Object.class.getName().equals(superclass.getName())));
/*  94:123 */     return null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public XClass getClazz()
/*  98:    */   {
/*  99:127 */     return this.clazz;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setClazz(XClass clazz)
/* 103:    */   {
/* 104:131 */     this.clazz = clazz;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean hasSiblings()
/* 108:    */   {
/* 109:135 */     return this.hasSiblings;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setHasSiblings(boolean hasSiblings)
/* 113:    */   {
/* 114:139 */     this.hasSiblings = hasSiblings;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean hasParents()
/* 118:    */   {
/* 119:143 */     return this.hasParents;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setHasParents(boolean hasParents)
/* 123:    */   {
/* 124:147 */     this.hasParents = hasParents;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public InheritanceType getType()
/* 128:    */   {
/* 129:151 */     return this.type;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setType(InheritanceType type)
/* 133:    */   {
/* 134:155 */     this.type = type;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isEmbeddableSuperclass()
/* 138:    */   {
/* 139:159 */     return this.isEmbeddableSuperclass;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setEmbeddableSuperclass(boolean embeddableSuperclass)
/* 143:    */   {
/* 144:163 */     this.isEmbeddableSuperclass = embeddableSuperclass;
/* 145:    */   }
/* 146:    */   
/* 147:    */   void postProcess(PersistentClass persistenceClass, EntityBinder entityBinder)
/* 148:    */   {
/* 149:168 */     getElementsToProcess();
/* 150:169 */     addMappedSuperClassInMetadata(persistenceClass);
/* 151:170 */     entityBinder.setPropertyAccessType(this.accessType);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public XClass getClassWithIdClass(boolean evenIfSubclass)
/* 155:    */   {
/* 156:174 */     if ((!evenIfSubclass) && (hasParents())) {
/* 157:175 */       return null;
/* 158:    */     }
/* 159:177 */     if (this.clazz.isAnnotationPresent(IdClass.class)) {
/* 160:178 */       return this.clazz;
/* 161:    */     }
/* 162:181 */     InheritanceState state = getSuperclassInheritanceState(this.clazz, this.inheritanceStatePerClass);
/* 163:182 */     if (state != null) {
/* 164:183 */       return state.getClassWithIdClass(true);
/* 165:    */     }
/* 166:186 */     return null;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Boolean hasIdClassOrEmbeddedId()
/* 170:    */   {
/* 171:192 */     if (this.hasIdClassOrEmbeddedId == null)
/* 172:    */     {
/* 173:193 */       this.hasIdClassOrEmbeddedId = Boolean.valueOf(false);
/* 174:194 */       if (getClassWithIdClass(true) != null)
/* 175:    */       {
/* 176:195 */         this.hasIdClassOrEmbeddedId = Boolean.valueOf(true);
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:198 */         ElementsToProcess process = getElementsToProcess();
/* 181:199 */         for (PropertyData property : process.getElements()) {
/* 182:200 */           if (property.getProperty().isAnnotationPresent(EmbeddedId.class))
/* 183:    */           {
/* 184:201 */             this.hasIdClassOrEmbeddedId = Boolean.valueOf(true);
/* 185:202 */             break;
/* 186:    */           }
/* 187:    */         }
/* 188:    */       }
/* 189:    */     }
/* 190:207 */     return this.hasIdClassOrEmbeddedId;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public ElementsToProcess getElementsToProcess()
/* 194:    */   {
/* 195:217 */     if (this.elementsToProcess == null)
/* 196:    */     {
/* 197:218 */       InheritanceState inheritanceState = (InheritanceState)this.inheritanceStatePerClass.get(this.clazz);
/* 198:219 */       assert (!inheritanceState.isEmbeddableSuperclass());
/* 199:    */       
/* 200:    */ 
/* 201:222 */       getMappedSuperclassesTillNextEntityOrdered();
/* 202:    */       
/* 203:224 */       this.accessType = determineDefaultAccessType();
/* 204:    */       
/* 205:226 */       List<PropertyData> elements = new ArrayList();
/* 206:227 */       int deep = this.classesToProcessForMappedSuperclass.size();
/* 207:228 */       int idPropertyCount = 0;
/* 208:230 */       for (int index = 0; index < deep; index++)
/* 209:    */       {
/* 210:231 */         PropertyContainer propertyContainer = new PropertyContainer((XClass)this.classesToProcessForMappedSuperclass.get(index), this.clazz);
/* 211:    */         
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:236 */         int currentIdPropertyCount = AnnotationBinder.addElementsOfClass(elements, this.accessType, propertyContainer, this.mappings);
/* 216:    */         
/* 217:    */ 
/* 218:239 */         idPropertyCount += currentIdPropertyCount;
/* 219:    */       }
/* 220:242 */       if ((idPropertyCount == 0) && (!inheritanceState.hasParents())) {
/* 221:243 */         throw new AnnotationException("No identifier specified for entity: " + this.clazz.getName());
/* 222:    */       }
/* 223:245 */       this.elementsToProcess = new ElementsToProcess(elements, idPropertyCount, null);
/* 224:    */     }
/* 225:247 */     return this.elementsToProcess;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private AccessType determineDefaultAccessType()
/* 229:    */   {
/* 230:251 */     for (XClass xclass = this.clazz; xclass != null; xclass = xclass.getSuperclass()) {
/* 231:252 */       if (((xclass.getSuperclass() == null) || (Object.class.getName().equals(xclass.getSuperclass().getName()))) && ((xclass.isAnnotationPresent(Entity.class)) || (xclass.isAnnotationPresent(javax.persistence.MappedSuperclass.class))) && (xclass.isAnnotationPresent(Access.class))) {
/* 232:255 */         return AccessType.getAccessStrategy(((Access)xclass.getAnnotation(Access.class)).value());
/* 233:    */       }
/* 234:    */     }
/* 235:260 */     for (XClass xclass = this.clazz; (xclass != null) && (!Object.class.getName().equals(xclass.getName())); xclass = xclass.getSuperclass()) {
/* 236:261 */       if ((xclass.isAnnotationPresent(Entity.class)) || (xclass.isAnnotationPresent(javax.persistence.MappedSuperclass.class)))
/* 237:    */       {
/* 238:262 */         for (XProperty prop : xclass.getDeclaredProperties(AccessType.PROPERTY.getType()))
/* 239:    */         {
/* 240:263 */           boolean isEmbeddedId = prop.isAnnotationPresent(EmbeddedId.class);
/* 241:264 */           if ((prop.isAnnotationPresent(Id.class)) || (isEmbeddedId)) {
/* 242:265 */             return AccessType.PROPERTY;
/* 243:    */           }
/* 244:    */         }
/* 245:268 */         for (XProperty prop : xclass.getDeclaredProperties(AccessType.FIELD.getType()))
/* 246:    */         {
/* 247:269 */           boolean isEmbeddedId = prop.isAnnotationPresent(EmbeddedId.class);
/* 248:270 */           if ((prop.isAnnotationPresent(Id.class)) || (isEmbeddedId)) {
/* 249:271 */             return AccessType.FIELD;
/* 250:    */           }
/* 251:    */         }
/* 252:    */       }
/* 253:    */     }
/* 254:276 */     throw new AnnotationException("No identifier specified for entity: " + this.clazz);
/* 255:    */   }
/* 256:    */   
/* 257:    */   private void getMappedSuperclassesTillNextEntityOrdered()
/* 258:    */   {
/* 259:282 */     XClass currentClassInHierarchy = this.clazz;
/* 260:    */     InheritanceState superclassState;
/* 261:    */     do
/* 262:    */     {
/* 263:285 */       this.classesToProcessForMappedSuperclass.add(0, currentClassInHierarchy);
/* 264:286 */       XClass superClass = currentClassInHierarchy;
/* 265:    */       do
/* 266:    */       {
/* 267:288 */         superClass = superClass.getSuperclass();
/* 268:289 */         superclassState = (InheritanceState)this.inheritanceStatePerClass.get(superClass);
/* 269:292 */       } while ((superClass != null) && (!this.mappings.getReflectionManager().equals(superClass, Object.class)) && (superclassState == null));
/* 270:294 */       currentClassInHierarchy = superClass;
/* 271:296 */     } while ((superclassState != null) && (superclassState.isEmbeddableSuperclass()));
/* 272:    */   }
/* 273:    */   
/* 274:    */   private void addMappedSuperClassInMetadata(PersistentClass persistentClass)
/* 275:    */   {
/* 276:302 */     org.hibernate.mapping.MappedSuperclass mappedSuperclass = null;
/* 277:303 */     InheritanceState superEntityState = getInheritanceStateOfSuperEntity(this.clazz, this.inheritanceStatePerClass);
/* 278:    */     
/* 279:305 */     PersistentClass superEntity = superEntityState != null ? this.mappings.getClass(superEntityState.getClazz().getName()) : null;
/* 280:    */     
/* 281:    */ 
/* 282:    */ 
/* 283:309 */     int lastMappedSuperclass = this.classesToProcessForMappedSuperclass.size() - 1;
/* 284:310 */     for (int index = 0; index < lastMappedSuperclass; index++)
/* 285:    */     {
/* 286:311 */       org.hibernate.mapping.MappedSuperclass parentSuperclass = mappedSuperclass;
/* 287:312 */       Class<?> type = this.mappings.getReflectionManager().toClass((XClass)this.classesToProcessForMappedSuperclass.get(index));
/* 288:    */       
/* 289:    */ 
/* 290:315 */       mappedSuperclass = this.mappings.getMappedSuperclass(type);
/* 291:316 */       if (mappedSuperclass == null)
/* 292:    */       {
/* 293:317 */         mappedSuperclass = new org.hibernate.mapping.MappedSuperclass(parentSuperclass, superEntity);
/* 294:318 */         mappedSuperclass.setMappedClass(type);
/* 295:319 */         this.mappings.addMappedSuperclass(type, mappedSuperclass);
/* 296:    */       }
/* 297:    */     }
/* 298:322 */     if (mappedSuperclass != null) {
/* 299:323 */       persistentClass.setSuperMappedSuperclass(mappedSuperclass);
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   static final class ElementsToProcess
/* 304:    */   {
/* 305:    */     private final List<PropertyData> properties;
/* 306:    */     private final int idPropertyCount;
/* 307:    */     
/* 308:    */     public List<PropertyData> getElements()
/* 309:    */     {
/* 310:332 */       return this.properties;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public int getIdPropertyCount()
/* 314:    */     {
/* 315:336 */       return this.idPropertyCount;
/* 316:    */     }
/* 317:    */     
/* 318:    */     private ElementsToProcess(List<PropertyData> properties, int idPropertyCount)
/* 319:    */     {
/* 320:340 */       this.properties = properties;
/* 321:341 */       this.idPropertyCount = idPropertyCount;
/* 322:    */     }
/* 323:    */   }
/* 324:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.InheritanceState
 * JD-Core Version:    0.7.0.1
 */