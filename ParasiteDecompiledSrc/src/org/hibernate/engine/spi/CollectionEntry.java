/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  14:    */ import org.hibernate.pretty.MessageHelper;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public final class CollectionEntry
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, CollectionEntry.class.getName());
/*  22:    */   private Serializable snapshot;
/*  23:    */   private String role;
/*  24:    */   private transient CollectionPersister loadedPersister;
/*  25:    */   private Serializable loadedKey;
/*  26:    */   private transient boolean reached;
/*  27:    */   private transient boolean processed;
/*  28:    */   private transient boolean doupdate;
/*  29:    */   private transient boolean doremove;
/*  30:    */   private transient boolean dorecreate;
/*  31:    */   private transient boolean ignore;
/*  32:    */   private transient CollectionPersister currentPersister;
/*  33:    */   private transient Serializable currentKey;
/*  34:    */   
/*  35:    */   public CollectionEntry(CollectionPersister persister, PersistentCollection collection)
/*  36:    */   {
/*  37: 87 */     this.ignore = false;
/*  38:    */     
/*  39: 89 */     collection.clearDirty();
/*  40:    */     
/*  41: 91 */     this.snapshot = (persister.isMutable() ? collection.getSnapshot(persister) : null);
/*  42:    */     
/*  43:    */ 
/*  44: 94 */     collection.setSnapshot(this.loadedKey, this.role, this.snapshot);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public CollectionEntry(PersistentCollection collection, CollectionPersister loadedPersister, Serializable loadedKey, boolean ignore)
/*  48:    */   {
/*  49:106 */     this.ignore = ignore;
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:110 */     this.loadedKey = loadedKey;
/*  54:111 */     setLoadedPersister(loadedPersister);
/*  55:    */     
/*  56:113 */     collection.setSnapshot(loadedKey, this.role, null);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public CollectionEntry(CollectionPersister loadedPersister, Serializable loadedKey)
/*  60:    */   {
/*  61:124 */     this.ignore = false;
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:128 */     this.loadedKey = loadedKey;
/*  66:129 */     setLoadedPersister(loadedPersister);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CollectionEntry(PersistentCollection collection, SessionFactoryImplementor factory)
/*  70:    */     throws MappingException
/*  71:    */   {
/*  72:138 */     this.ignore = false;
/*  73:    */     
/*  74:140 */     this.loadedKey = collection.getKey();
/*  75:141 */     setLoadedPersister(factory.getCollectionPersister(collection.getRole()));
/*  76:    */     
/*  77:143 */     this.snapshot = collection.getStoredSnapshot();
/*  78:    */   }
/*  79:    */   
/*  80:    */   private CollectionEntry(String role, Serializable snapshot, Serializable loadedKey, SessionFactoryImplementor factory)
/*  81:    */   {
/*  82:157 */     this.role = role;
/*  83:158 */     this.snapshot = snapshot;
/*  84:159 */     this.loadedKey = loadedKey;
/*  85:160 */     if (role != null) {
/*  86:161 */       afterDeserialize(factory);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void dirty(PersistentCollection collection)
/*  91:    */     throws HibernateException
/*  92:    */   {
/*  93:171 */     boolean forceDirty = (collection.wasInitialized()) && (!collection.isDirty()) && (getLoadedPersister() != null) && (getLoadedPersister().isMutable()) && ((collection.isDirectlyAccessible()) || (getLoadedPersister().getElementType().isMutable())) && (!collection.equalsSnapshot(getLoadedPersister()));
/*  94:178 */     if (forceDirty) {
/*  95:179 */       collection.dirty();
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void preFlush(PersistentCollection collection)
/* 100:    */     throws HibernateException
/* 101:    */   {
/* 102:186 */     boolean nonMutableChange = (collection.isDirty()) && (getLoadedPersister() != null) && (!getLoadedPersister().isMutable());
/* 103:189 */     if (nonMutableChange) {
/* 104:190 */       throw new HibernateException("changed an immutable collection instance: " + MessageHelper.collectionInfoString(getLoadedPersister().getRole(), getLoadedKey()));
/* 105:    */     }
/* 106:196 */     dirty(collection);
/* 107:198 */     if ((LOG.isDebugEnabled()) && (collection.isDirty()) && (getLoadedPersister() != null)) {
/* 108:199 */       LOG.debugf("Collection dirty: %s", MessageHelper.collectionInfoString(getLoadedPersister().getRole(), getLoadedKey()));
/* 109:    */     }
/* 110:203 */     setDoupdate(false);
/* 111:204 */     setDoremove(false);
/* 112:205 */     setDorecreate(false);
/* 113:206 */     setReached(false);
/* 114:207 */     setProcessed(false);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void postInitialize(PersistentCollection collection)
/* 118:    */     throws HibernateException
/* 119:    */   {
/* 120:211 */     this.snapshot = (getLoadedPersister().isMutable() ? collection.getSnapshot(getLoadedPersister()) : null);
/* 121:    */     
/* 122:    */ 
/* 123:214 */     collection.setSnapshot(this.loadedKey, this.role, this.snapshot);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void postFlush(PersistentCollection collection)
/* 127:    */     throws HibernateException
/* 128:    */   {
/* 129:221 */     if (isIgnore()) {
/* 130:222 */       this.ignore = false;
/* 131:224 */     } else if (!isProcessed()) {
/* 132:225 */       throw new AssertionFailure("collection [" + collection.getRole() + "] was not processed by flush()");
/* 133:    */     }
/* 134:227 */     collection.setSnapshot(this.loadedKey, this.role, this.snapshot);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void afterAction(PersistentCollection collection)
/* 138:    */   {
/* 139:234 */     this.loadedKey = getCurrentKey();
/* 140:235 */     setLoadedPersister(getCurrentPersister());
/* 141:    */     
/* 142:237 */     boolean resnapshot = (collection.wasInitialized()) && ((isDoremove()) || (isDorecreate()) || (isDoupdate()));
/* 143:239 */     if (resnapshot) {
/* 144:240 */       this.snapshot = ((this.loadedPersister == null) || (!this.loadedPersister.isMutable()) ? null : collection.getSnapshot(this.loadedPersister));
/* 145:    */     }
/* 146:245 */     collection.postAction();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Serializable getKey()
/* 150:    */   {
/* 151:249 */     return getLoadedKey();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String getRole()
/* 155:    */   {
/* 156:253 */     return this.role;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Serializable getSnapshot()
/* 160:    */   {
/* 161:257 */     return this.snapshot;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private void setLoadedPersister(CollectionPersister persister)
/* 165:    */   {
/* 166:261 */     this.loadedPersister = persister;
/* 167:262 */     setRole(persister == null ? null : persister.getRole());
/* 168:    */   }
/* 169:    */   
/* 170:    */   void afterDeserialize(SessionFactoryImplementor factory)
/* 171:    */   {
/* 172:266 */     this.loadedPersister = (factory == null ? null : factory.getCollectionPersister(this.role));
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean wasDereferenced()
/* 176:    */   {
/* 177:270 */     return getLoadedKey() == null;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isReached()
/* 181:    */   {
/* 182:274 */     return this.reached;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setReached(boolean reached)
/* 186:    */   {
/* 187:278 */     this.reached = reached;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isProcessed()
/* 191:    */   {
/* 192:282 */     return this.processed;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setProcessed(boolean processed)
/* 196:    */   {
/* 197:286 */     this.processed = processed;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isDoupdate()
/* 201:    */   {
/* 202:290 */     return this.doupdate;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setDoupdate(boolean doupdate)
/* 206:    */   {
/* 207:294 */     this.doupdate = doupdate;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean isDoremove()
/* 211:    */   {
/* 212:298 */     return this.doremove;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDoremove(boolean doremove)
/* 216:    */   {
/* 217:302 */     this.doremove = doremove;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isDorecreate()
/* 221:    */   {
/* 222:306 */     return this.dorecreate;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setDorecreate(boolean dorecreate)
/* 226:    */   {
/* 227:310 */     this.dorecreate = dorecreate;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isIgnore()
/* 231:    */   {
/* 232:314 */     return this.ignore;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public CollectionPersister getCurrentPersister()
/* 236:    */   {
/* 237:318 */     return this.currentPersister;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setCurrentPersister(CollectionPersister currentPersister)
/* 241:    */   {
/* 242:322 */     this.currentPersister = currentPersister;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Serializable getCurrentKey()
/* 246:    */   {
/* 247:330 */     return this.currentKey;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setCurrentKey(Serializable currentKey)
/* 251:    */   {
/* 252:334 */     this.currentKey = currentKey;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public CollectionPersister getLoadedPersister()
/* 256:    */   {
/* 257:341 */     return this.loadedPersister;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Serializable getLoadedKey()
/* 261:    */   {
/* 262:345 */     return this.loadedKey;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setRole(String role)
/* 266:    */   {
/* 267:349 */     this.role = role;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String toString()
/* 271:    */   {
/* 272:354 */     String result = "CollectionEntry" + MessageHelper.collectionInfoString(this.loadedPersister.getRole(), this.loadedKey);
/* 273:356 */     if (this.currentPersister != null) {
/* 274:357 */       result = result + "->" + MessageHelper.collectionInfoString(this.currentPersister.getRole(), this.currentKey);
/* 275:    */     }
/* 276:360 */     return result;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public Collection getOrphans(String entityName, PersistentCollection collection)
/* 280:    */     throws HibernateException
/* 281:    */   {
/* 282:368 */     if (this.snapshot == null) {
/* 283:369 */       throw new AssertionFailure("no collection snapshot for orphan delete");
/* 284:    */     }
/* 285:371 */     return collection.getOrphans(this.snapshot, entityName);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public boolean isSnapshotEmpty(PersistentCollection collection)
/* 289:    */   {
/* 290:378 */     return (collection.wasInitialized()) && ((getLoadedPersister() == null) || (getLoadedPersister().isMutable())) && (collection.isSnapshotEmpty(getSnapshot()));
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void serialize(ObjectOutputStream oos)
/* 294:    */     throws IOException
/* 295:    */   {
/* 296:393 */     oos.writeObject(this.role);
/* 297:394 */     oos.writeObject(this.snapshot);
/* 298:395 */     oos.writeObject(this.loadedKey);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static CollectionEntry deserialize(ObjectInputStream ois, SessionImplementor session)
/* 302:    */     throws IOException, ClassNotFoundException
/* 303:    */   {
/* 304:411 */     return new CollectionEntry((String)ois.readObject(), (Serializable)ois.readObject(), (Serializable)ois.readObject(), session == null ? null : session.getFactory());
/* 305:    */   }
/* 306:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.CollectionEntry
 * JD-Core Version:    0.7.0.1
 */