/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Stack;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  10:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  11:    */ import org.hibernate.engine.spi.CascadingAction;
/*  12:    */ import org.hibernate.engine.spi.CollectionEntry;
/*  13:    */ import org.hibernate.engine.spi.EntityEntry;
/*  14:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.Status;
/*  17:    */ import org.hibernate.event.spi.EventSource;
/*  18:    */ import org.hibernate.internal.CoreMessageLogger;
/*  19:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  20:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  21:    */ import org.hibernate.persister.entity.EntityPersister;
/*  22:    */ import org.hibernate.pretty.MessageHelper;
/*  23:    */ import org.hibernate.type.AssociationType;
/*  24:    */ import org.hibernate.type.CollectionType;
/*  25:    */ import org.hibernate.type.CompositeType;
/*  26:    */ import org.hibernate.type.EntityType;
/*  27:    */ import org.hibernate.type.ForeignKeyDirection;
/*  28:    */ import org.hibernate.type.Type;
/*  29:    */ import org.jboss.logging.Logger;
/*  30:    */ 
/*  31:    */ public final class Cascade
/*  32:    */ {
/*  33:    */   public static final int AFTER_INSERT_BEFORE_DELETE = 1;
/*  34:    */   public static final int BEFORE_INSERT_AFTER_DELETE = 2;
/*  35:    */   public static final int AFTER_INSERT_BEFORE_DELETE_VIA_COLLECTION = 3;
/*  36:    */   public static final int AFTER_UPDATE = 0;
/*  37:    */   public static final int BEFORE_FLUSH = 0;
/*  38:    */   public static final int AFTER_EVICT = 0;
/*  39:    */   public static final int BEFORE_REFRESH = 0;
/*  40:    */   public static final int AFTER_LOCK = 0;
/*  41:    */   public static final int BEFORE_MERGE = 0;
/*  42:106 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Cascade.class.getName());
/*  43:    */   private int cascadeTo;
/*  44:    */   private EventSource eventSource;
/*  45:    */   private CascadingAction action;
/*  46:    */   
/*  47:    */   public Cascade(CascadingAction action, int cascadeTo, EventSource eventSource)
/*  48:    */   {
/*  49:114 */     this.cascadeTo = cascadeTo;
/*  50:115 */     this.eventSource = eventSource;
/*  51:116 */     this.action = action;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private SessionFactoryImplementor getFactory()
/*  55:    */   {
/*  56:120 */     return this.eventSource.getFactory();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void cascade(EntityPersister persister, Object parent)
/*  60:    */     throws HibernateException
/*  61:    */   {
/*  62:132 */     cascade(persister, parent, null);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void cascade(EntityPersister persister, Object parent, Object anything)
/*  66:    */     throws HibernateException
/*  67:    */   {
/*  68:148 */     if ((persister.hasCascades()) || (this.action.requiresNoCascadeChecking()))
/*  69:    */     {
/*  70:149 */       if (LOG.isTraceEnabled()) {
/*  71:150 */         LOG.tracev("Processing cascade {0} for: {1}", this.action, persister.getEntityName());
/*  72:    */       }
/*  73:153 */       Type[] types = persister.getPropertyTypes();
/*  74:154 */       CascadeStyle[] cascadeStyles = persister.getPropertyCascadeStyles();
/*  75:155 */       boolean hasUninitializedLazyProperties = persister.hasUninitializedLazyProperties(parent);
/*  76:156 */       for (int i = 0; i < types.length; i++)
/*  77:    */       {
/*  78:157 */         CascadeStyle style = cascadeStyles[i];
/*  79:158 */         String propertyName = persister.getPropertyNames()[i];
/*  80:159 */         if ((!hasUninitializedLazyProperties) || (persister.getPropertyLaziness()[i] == 0) || (this.action.performOnLazyProperty())) {
/*  81:164 */           if (style.doCascade(this.action)) {
/*  82:165 */             cascadeProperty(parent, persister.getPropertyValue(parent, i), types[i], style, propertyName, anything, false);
/*  83:175 */           } else if (this.action.requiresNoCascadeChecking()) {
/*  84:176 */             this.action.noCascade(this.eventSource, persister.getPropertyValue(parent, i), parent, persister, i);
/*  85:    */           }
/*  86:    */         }
/*  87:    */       }
/*  88:186 */       if (LOG.isTraceEnabled()) {
/*  89:187 */         LOG.tracev("Done processing cascade {0} for: {1}", this.action, persister.getEntityName());
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void cascadeProperty(Object parent, Object child, Type type, CascadeStyle style, String propertyName, Object anything, boolean isCascadeDeleteEnabled)
/*  95:    */     throws HibernateException
/*  96:    */   {
/*  97:204 */     if (child != null)
/*  98:    */     {
/*  99:205 */       if (type.isAssociationType())
/* 100:    */       {
/* 101:206 */         AssociationType associationType = (AssociationType)type;
/* 102:207 */         if (cascadeAssociationNow(associationType)) {
/* 103:208 */           cascadeAssociation(parent, child, type, style, anything, isCascadeDeleteEnabled);
/* 104:    */         }
/* 105:    */       }
/* 106:218 */       else if (type.isComponentType())
/* 107:    */       {
/* 108:219 */         cascadeComponent(parent, child, (CompositeType)type, propertyName, anything);
/* 109:    */       }
/* 110:    */     }
/* 111:224 */     else if (isLogicalOneToOne(type)) {
/* 112:228 */       if ((style.hasOrphanDelete()) && (this.action.deleteOrphans()))
/* 113:    */       {
/* 114:231 */         EntityEntry entry = this.eventSource.getPersistenceContext().getEntry(parent);
/* 115:232 */         if ((entry != null) && (entry.getStatus() != Status.SAVING))
/* 116:    */         {
/* 117:    */           Object loadedValue;
/* 118:    */           Object loadedValue;
/* 119:234 */           if (this.componentPathStack.isEmpty()) {
/* 120:236 */             loadedValue = entry.getLoadedValue(propertyName);
/* 121:    */           } else {
/* 122:253 */             loadedValue = null;
/* 123:    */           }
/* 124:255 */           if (loadedValue != null)
/* 125:    */           {
/* 126:256 */             String entityName = entry.getPersister().getEntityName();
/* 127:257 */             if (LOG.isTraceEnabled())
/* 128:    */             {
/* 129:258 */               Serializable id = entry.getPersister().getIdentifier(loadedValue, this.eventSource);
/* 130:259 */               String description = MessageHelper.infoString(entityName, id);
/* 131:260 */               LOG.tracev("Deleting orphaned entity instance: {0}", description);
/* 132:    */             }
/* 133:262 */             this.eventSource.delete(entityName, loadedValue, false, new HashSet());
/* 134:    */           }
/* 135:    */         }
/* 136:    */       }
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   private boolean isLogicalOneToOne(Type type)
/* 141:    */   {
/* 142:279 */     return (type.isEntityType()) && (((EntityType)type).isLogicalOneToOne());
/* 143:    */   }
/* 144:    */   
/* 145:282 */   private Stack componentPathStack = new Stack();
/* 146:    */   
/* 147:    */   private boolean cascadeAssociationNow(AssociationType associationType)
/* 148:    */   {
/* 149:285 */     return associationType.getForeignKeyDirection().cascadeNow(this.cascadeTo);
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void cascadeComponent(Object parent, Object child, CompositeType componentType, String componentPropertyName, Object anything)
/* 153:    */   {
/* 154:294 */     this.componentPathStack.push(componentPropertyName);
/* 155:295 */     Object[] children = componentType.getPropertyValues(child, this.eventSource);
/* 156:296 */     Type[] types = componentType.getSubtypes();
/* 157:297 */     for (int i = 0; i < types.length; i++)
/* 158:    */     {
/* 159:298 */       CascadeStyle componentPropertyStyle = componentType.getCascadeStyle(i);
/* 160:299 */       String subPropertyName = componentType.getPropertyNames()[i];
/* 161:300 */       if (componentPropertyStyle.doCascade(this.action)) {
/* 162:301 */         cascadeProperty(parent, children[i], types[i], componentPropertyStyle, subPropertyName, anything, false);
/* 163:    */       }
/* 164:    */     }
/* 165:312 */     this.componentPathStack.pop();
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void cascadeAssociation(Object parent, Object child, Type type, CascadeStyle style, Object anything, boolean isCascadeDeleteEnabled)
/* 169:    */   {
/* 170:322 */     if ((type.isEntityType()) || (type.isAnyType())) {
/* 171:323 */       cascadeToOne(parent, child, type, style, anything, isCascadeDeleteEnabled);
/* 172:325 */     } else if (type.isCollectionType()) {
/* 173:326 */       cascadeCollection(parent, child, style, anything, (CollectionType)type);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   private void cascadeCollection(Object parent, Object child, CascadeStyle style, Object anything, CollectionType type)
/* 178:    */   {
/* 179:339 */     CollectionPersister persister = this.eventSource.getFactory().getCollectionPersister(type.getRole());
/* 180:    */     
/* 181:341 */     Type elemType = persister.getElementType();
/* 182:    */     
/* 183:343 */     int oldCascadeTo = this.cascadeTo;
/* 184:344 */     if (this.cascadeTo == 1) {
/* 185:345 */       this.cascadeTo = 3;
/* 186:    */     }
/* 187:349 */     if ((elemType.isEntityType()) || (elemType.isAnyType()) || (elemType.isComponentType())) {
/* 188:350 */       cascadeCollectionElements(parent, child, type, style, elemType, anything, persister.isCascadeDeleteEnabled());
/* 189:    */     }
/* 190:361 */     this.cascadeTo = oldCascadeTo;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void cascadeToOne(Object parent, Object child, Type type, CascadeStyle style, Object anything, boolean isCascadeDeleteEnabled)
/* 194:    */   {
/* 195:374 */     String entityName = type.isEntityType() ? ((EntityType)type).getAssociatedEntityName() : null;
/* 196:377 */     if (style.reallyDoCascade(this.action))
/* 197:    */     {
/* 198:378 */       this.eventSource.getPersistenceContext().addChildParent(child, parent);
/* 199:    */       try
/* 200:    */       {
/* 201:380 */         this.action.cascade(this.eventSource, child, entityName, anything, isCascadeDeleteEnabled);
/* 202:    */       }
/* 203:    */       finally
/* 204:    */       {
/* 205:383 */         this.eventSource.getPersistenceContext().removeChildParent(child);
/* 206:    */       }
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   private void cascadeCollectionElements(Object parent, Object child, CollectionType collectionType, CascadeStyle style, Type elemType, Object anything, boolean isCascadeDeleteEnabled)
/* 211:    */     throws HibernateException
/* 212:    */   {
/* 213:400 */     boolean reallyDoCascade = (style.reallyDoCascade(this.action)) && (child != CollectionType.UNFETCHED_COLLECTION);
/* 214:402 */     if (reallyDoCascade)
/* 215:    */     {
/* 216:403 */       if (LOG.isTraceEnabled()) {
/* 217:404 */         LOG.tracev("Cascade {0} for collection: {1}", this.action, collectionType.getRole());
/* 218:    */       }
/* 219:407 */       Iterator iter = this.action.getCascadableChildrenIterator(this.eventSource, collectionType, child);
/* 220:408 */       while (iter.hasNext()) {
/* 221:409 */         cascadeProperty(parent, iter.next(), elemType, style, null, anything, isCascadeDeleteEnabled);
/* 222:    */       }
/* 223:420 */       if (LOG.isTraceEnabled()) {
/* 224:421 */         LOG.tracev("Done cascade {0} for collection: {1}", this.action, collectionType.getRole());
/* 225:    */       }
/* 226:    */     }
/* 227:425 */     boolean deleteOrphans = (style.hasOrphanDelete()) && (this.action.deleteOrphans()) && (elemType.isEntityType()) && ((child instanceof PersistentCollection));
/* 228:430 */     if (deleteOrphans)
/* 229:    */     {
/* 230:431 */       if (LOG.isTraceEnabled()) {
/* 231:432 */         LOG.tracev("Deleting orphans for collection: {0}", collectionType.getRole());
/* 232:    */       }
/* 233:437 */       String entityName = collectionType.getAssociatedEntityName(this.eventSource.getFactory());
/* 234:438 */       deleteOrphans(entityName, (PersistentCollection)child);
/* 235:440 */       if (LOG.isTraceEnabled()) {
/* 236:441 */         LOG.tracev("Done deleting orphans for collection: {0}", collectionType.getRole());
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   private void deleteOrphans(String entityName, PersistentCollection pc)
/* 242:    */     throws HibernateException
/* 243:    */   {
/* 244:    */     Collection orphans;
/* 245:    */     Collection orphans;
/* 246:452 */     if (pc.wasInitialized())
/* 247:    */     {
/* 248:453 */       CollectionEntry ce = this.eventSource.getPersistenceContext().getCollectionEntry(pc);
/* 249:454 */       orphans = ce == null ? CollectionHelper.EMPTY_COLLECTION : ce.getOrphans(entityName, pc);
/* 250:    */     }
/* 251:    */     else
/* 252:    */     {
/* 253:459 */       orphans = pc.getQueuedOrphans(entityName);
/* 254:    */     }
/* 255:462 */     Iterator orphanIter = orphans.iterator();
/* 256:463 */     while (orphanIter.hasNext())
/* 257:    */     {
/* 258:464 */       Object orphan = orphanIter.next();
/* 259:465 */       if (orphan != null)
/* 260:    */       {
/* 261:466 */         LOG.tracev("Deleting orphaned entity instance: {0}", entityName);
/* 262:467 */         this.eventSource.delete(entityName, orphan, false, new HashSet());
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.Cascade
 * JD-Core Version:    0.7.0.1
 */