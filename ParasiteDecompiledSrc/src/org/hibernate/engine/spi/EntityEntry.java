/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import org.hibernate.EntityMode;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.LockMode;
/*  11:    */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*  12:    */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*  13:    */ import org.hibernate.persister.entity.EntityPersister;
/*  14:    */ import org.hibernate.persister.entity.UniqueKeyLoadable;
/*  15:    */ import org.hibernate.pretty.MessageHelper;
/*  16:    */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  17:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  18:    */ 
/*  19:    */ public final class EntityEntry
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22:    */   private LockMode lockMode;
/*  23:    */   private Status status;
/*  24:    */   private Status previousStatus;
/*  25:    */   private final Serializable id;
/*  26:    */   private Object[] loadedState;
/*  27:    */   private Object[] deletedState;
/*  28:    */   private boolean existsInDatabase;
/*  29:    */   private Object version;
/*  30:    */   private transient EntityPersister persister;
/*  31:    */   private final EntityMode entityMode;
/*  32:    */   private final String tenantId;
/*  33:    */   private final String entityName;
/*  34:    */   private transient EntityKey cachedEntityKey;
/*  35:    */   private boolean isBeingReplicated;
/*  36:    */   private boolean loadedWithLazyPropertiesUnfetched;
/*  37:    */   private final transient Object rowId;
/*  38:    */   
/*  39:    */   public EntityEntry(Status status, Object[] loadedState, Object rowId, Serializable id, Object version, LockMode lockMode, boolean existsInDatabase, EntityPersister persister, EntityMode entityMode, String tenantId, boolean disableVersionIncrement, boolean lazyPropertiesAreUnfetched)
/*  40:    */   {
/*  41: 76 */     this.status = status;
/*  42: 77 */     this.previousStatus = null;
/*  43: 79 */     if (status != Status.READ_ONLY) {
/*  44: 79 */       this.loadedState = loadedState;
/*  45:    */     }
/*  46: 80 */     this.id = id;
/*  47: 81 */     this.rowId = rowId;
/*  48: 82 */     this.existsInDatabase = existsInDatabase;
/*  49: 83 */     this.version = version;
/*  50: 84 */     this.lockMode = lockMode;
/*  51: 85 */     this.isBeingReplicated = disableVersionIncrement;
/*  52: 86 */     this.loadedWithLazyPropertiesUnfetched = lazyPropertiesAreUnfetched;
/*  53: 87 */     this.persister = persister;
/*  54: 88 */     this.entityMode = entityMode;
/*  55: 89 */     this.tenantId = tenantId;
/*  56: 90 */     this.entityName = (persister == null ? null : persister.getEntityName());
/*  57:    */   }
/*  58:    */   
/*  59:    */   private EntityEntry(SessionFactoryImplementor factory, String entityName, Serializable id, EntityMode entityMode, String tenantId, Status status, Status previousStatus, Object[] loadedState, Object[] deletedState, Object version, LockMode lockMode, boolean existsInDatabase, boolean isBeingReplicated, boolean loadedWithLazyPropertiesUnfetched)
/*  60:    */   {
/*  61:109 */     this.entityName = entityName;
/*  62:110 */     this.persister = (factory == null ? null : factory.getEntityPersister(entityName));
/*  63:111 */     this.id = id;
/*  64:112 */     this.entityMode = entityMode;
/*  65:113 */     this.tenantId = tenantId;
/*  66:114 */     this.status = status;
/*  67:115 */     this.previousStatus = previousStatus;
/*  68:116 */     this.loadedState = loadedState;
/*  69:117 */     this.deletedState = deletedState;
/*  70:118 */     this.version = version;
/*  71:119 */     this.lockMode = lockMode;
/*  72:120 */     this.existsInDatabase = existsInDatabase;
/*  73:121 */     this.isBeingReplicated = isBeingReplicated;
/*  74:122 */     this.loadedWithLazyPropertiesUnfetched = loadedWithLazyPropertiesUnfetched;
/*  75:123 */     this.rowId = null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public LockMode getLockMode()
/*  79:    */   {
/*  80:127 */     return this.lockMode;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setLockMode(LockMode lockMode)
/*  84:    */   {
/*  85:131 */     this.lockMode = lockMode;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Status getStatus()
/*  89:    */   {
/*  90:135 */     return this.status;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setStatus(Status status)
/*  94:    */   {
/*  95:139 */     if (status == Status.READ_ONLY) {
/*  96:140 */       this.loadedState = null;
/*  97:    */     }
/*  98:142 */     if (this.status != status)
/*  99:    */     {
/* 100:143 */       this.previousStatus = this.status;
/* 101:144 */       this.status = status;
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Serializable getId()
/* 106:    */   {
/* 107:149 */     return this.id;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object[] getLoadedState()
/* 111:    */   {
/* 112:153 */     return this.loadedState;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Object[] getDeletedState()
/* 116:    */   {
/* 117:157 */     return this.deletedState;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setDeletedState(Object[] deletedState)
/* 121:    */   {
/* 122:161 */     this.deletedState = deletedState;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isExistsInDatabase()
/* 126:    */   {
/* 127:165 */     return this.existsInDatabase;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Object getVersion()
/* 131:    */   {
/* 132:169 */     return this.version;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public EntityPersister getPersister()
/* 136:    */   {
/* 137:173 */     return this.persister;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public EntityKey getEntityKey()
/* 141:    */   {
/* 142:182 */     if (this.cachedEntityKey == null)
/* 143:    */     {
/* 144:183 */       if (getId() == null) {
/* 145:184 */         throw new IllegalStateException("cannot generate an EntityKey when id is null.");
/* 146:    */       }
/* 147:186 */       this.cachedEntityKey = new EntityKey(getId(), getPersister(), this.tenantId);
/* 148:    */     }
/* 149:188 */     return this.cachedEntityKey;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getEntityName()
/* 153:    */   {
/* 154:192 */     return this.entityName;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isBeingReplicated()
/* 158:    */   {
/* 159:196 */     return this.isBeingReplicated;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Object getRowId()
/* 163:    */   {
/* 164:200 */     return this.rowId;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void postUpdate(Object entity, Object[] updatedState, Object nextVersion)
/* 168:    */   {
/* 169:214 */     this.loadedState = updatedState;
/* 170:215 */     setLockMode(LockMode.WRITE);
/* 171:217 */     if (getPersister().isVersioned())
/* 172:    */     {
/* 173:218 */       this.version = nextVersion;
/* 174:219 */       getPersister().setPropertyValue(entity, getPersister().getVersionProperty(), nextVersion);
/* 175:    */     }
/* 176:221 */     if (((InstrumentationService)getPersister().getFactory().getServiceRegistry().getService(InstrumentationService.class)).isInstrumented(entity)) {
/* 177:222 */       FieldInterceptionHelper.clearDirty(entity);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void postDelete()
/* 182:    */   {
/* 183:231 */     this.previousStatus = this.status;
/* 184:232 */     this.status = Status.GONE;
/* 185:233 */     this.existsInDatabase = false;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void postInsert()
/* 189:    */   {
/* 190:241 */     this.existsInDatabase = true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean isNullifiable(boolean earlyInsert, SessionImplementor session)
/* 194:    */   {
/* 195:245 */     return (getStatus() == Status.SAVING) || (earlyInsert ? !isExistsInDatabase() : session.getPersistenceContext().getNullifiableEntityKeys().contains(getEntityKey()));
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Object getLoadedValue(String propertyName)
/* 199:    */   {
/* 200:254 */     int propertyIndex = ((UniqueKeyLoadable)this.persister).getPropertyIndex(propertyName);
/* 201:255 */     return this.loadedState[propertyIndex];
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean requiresDirtyCheck(Object entity)
/* 205:    */   {
/* 206:259 */     return (isModifiableEntity()) && ((getPersister().hasMutableProperties()) || (!((InstrumentationService)getPersister().getFactory().getServiceRegistry().getService(InstrumentationService.class)).isInstrumented(entity)) || (FieldInterceptionHelper.extractFieldInterceptor(entity).isDirty()));
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isModifiableEntity()
/* 210:    */   {
/* 211:278 */     return (this.status != Status.READ_ONLY) && ((this.status != Status.DELETED) || (this.previousStatus != Status.READ_ONLY)) && (getPersister().isMutable());
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void forceLocked(Object entity, Object nextVersion)
/* 215:    */   {
/* 216:284 */     this.version = nextVersion;
/* 217:285 */     this.loadedState[this.persister.getVersionProperty()] = this.version;
/* 218:    */     
/* 219:287 */     setLockMode(LockMode.FORCE);
/* 220:288 */     this.persister.setPropertyValue(entity, getPersister().getVersionProperty(), nextVersion);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean isReadOnly()
/* 224:    */   {
/* 225:292 */     if ((this.status != Status.MANAGED) && (this.status != Status.READ_ONLY)) {
/* 226:293 */       throw new HibernateException("instance was not in a valid state");
/* 227:    */     }
/* 228:295 */     return this.status == Status.READ_ONLY;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setReadOnly(boolean readOnly, Object entity)
/* 232:    */   {
/* 233:299 */     if (readOnly == isReadOnly()) {
/* 234:301 */       return;
/* 235:    */     }
/* 236:303 */     if (readOnly)
/* 237:    */     {
/* 238:304 */       setStatus(Status.READ_ONLY);
/* 239:305 */       this.loadedState = null;
/* 240:    */     }
/* 241:    */     else
/* 242:    */     {
/* 243:308 */       if (!this.persister.isMutable()) {
/* 244:309 */         throw new IllegalStateException("Cannot make an immutable entity modifiable.");
/* 245:    */       }
/* 246:311 */       setStatus(Status.MANAGED);
/* 247:312 */       this.loadedState = getPersister().getPropertyValues(entity);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String toString()
/* 252:    */   {
/* 253:317 */     return "EntityEntry" + MessageHelper.infoString(this.entityName, this.id) + '(' + this.status + ')';
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean isLoadedWithLazyPropertiesUnfetched()
/* 257:    */   {
/* 258:323 */     return this.loadedWithLazyPropertiesUnfetched;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void serialize(ObjectOutputStream oos)
/* 262:    */     throws IOException
/* 263:    */   {
/* 264:335 */     oos.writeObject(this.entityName);
/* 265:336 */     oos.writeObject(this.id);
/* 266:337 */     oos.writeObject(this.entityMode.toString());
/* 267:338 */     oos.writeObject(this.tenantId);
/* 268:339 */     oos.writeObject(this.status.name());
/* 269:340 */     oos.writeObject(this.previousStatus == null ? "" : this.previousStatus.name());
/* 270:    */     
/* 271:342 */     oos.writeObject(this.loadedState);
/* 272:343 */     oos.writeObject(this.deletedState);
/* 273:344 */     oos.writeObject(this.version);
/* 274:345 */     oos.writeObject(this.lockMode.toString());
/* 275:346 */     oos.writeBoolean(this.existsInDatabase);
/* 276:347 */     oos.writeBoolean(this.isBeingReplicated);
/* 277:348 */     oos.writeBoolean(this.loadedWithLazyPropertiesUnfetched);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public static EntityEntry deserialize(ObjectInputStream ois, SessionImplementor session)
/* 281:    */     throws IOException, ClassNotFoundException
/* 282:    */   {
/* 283:367 */     String previousStatusString = null;
/* 284:368 */     return new EntityEntry(session == null ? null : session.getFactory(), (String)ois.readObject(), (Serializable)ois.readObject(), EntityMode.parse((String)ois.readObject()), (String)ois.readObject(), Status.valueOf((String)ois.readObject()), (previousStatusString = (String)ois.readObject()).length() == 0 ? null : Status.valueOf(previousStatusString), (Object[])ois.readObject(), (Object[])ois.readObject(), ois.readObject(), LockMode.valueOf((String)ois.readObject()), ois.readBoolean(), ois.readBoolean(), ois.readBoolean());
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.EntityEntry
 * JD-Core Version:    0.7.0.1
 */