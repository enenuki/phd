/*   1:    */ package org.hibernate.proxy;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.LazyInitializationException;
/*   6:    */ import org.hibernate.SessionException;
/*   7:    */ import org.hibernate.TransientObjectException;
/*   8:    */ import org.hibernate.engine.spi.EntityKey;
/*   9:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.persister.entity.EntityPersister;
/*  13:    */ 
/*  14:    */ public abstract class AbstractLazyInitializer
/*  15:    */   implements LazyInitializer
/*  16:    */ {
/*  17:    */   private String entityName;
/*  18:    */   private Serializable id;
/*  19:    */   private Object target;
/*  20:    */   private boolean initialized;
/*  21:    */   private boolean readOnly;
/*  22:    */   private boolean unwrap;
/*  23:    */   private transient SessionImplementor session;
/*  24:    */   private Boolean readOnlyBeforeAttachedToSession;
/*  25:    */   
/*  26:    */   protected AbstractLazyInitializer() {}
/*  27:    */   
/*  28:    */   protected AbstractLazyInitializer(String entityName, Serializable id, SessionImplementor session)
/*  29:    */   {
/*  30: 67 */     this.entityName = entityName;
/*  31: 68 */     this.id = id;
/*  32: 70 */     if (session == null) {
/*  33: 71 */       unsetSession();
/*  34:    */     } else {
/*  35: 74 */       setSession(session);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public final String getEntityName()
/*  40:    */   {
/*  41: 80 */     return this.entityName;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public final Serializable getIdentifier()
/*  45:    */   {
/*  46: 85 */     return this.id;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final void setIdentifier(Serializable id)
/*  50:    */   {
/*  51: 90 */     this.id = id;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final boolean isUninitialized()
/*  55:    */   {
/*  56: 95 */     return !this.initialized;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final SessionImplementor getSession()
/*  60:    */   {
/*  61:100 */     return this.session;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final void setSession(SessionImplementor s)
/*  65:    */     throws HibernateException
/*  66:    */   {
/*  67:105 */     if (s != this.session) {
/*  68:107 */       if (s == null)
/*  69:    */       {
/*  70:108 */         unsetSession();
/*  71:    */       }
/*  72:    */       else
/*  73:    */       {
/*  74:110 */         if (isConnectedToSession()) {
/*  75:112 */           throw new HibernateException("illegally attempted to associate a proxy with two open Sessions");
/*  76:    */         }
/*  77:116 */         this.session = s;
/*  78:117 */         if (this.readOnlyBeforeAttachedToSession == null)
/*  79:    */         {
/*  80:119 */           EntityPersister persister = s.getFactory().getEntityPersister(this.entityName);
/*  81:120 */           setReadOnly((s.getPersistenceContext().isDefaultReadOnly()) || (!persister.isMutable()));
/*  82:    */         }
/*  83:    */         else
/*  84:    */         {
/*  85:124 */           setReadOnly(this.readOnlyBeforeAttachedToSession.booleanValue());
/*  86:125 */           this.readOnlyBeforeAttachedToSession = null;
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static EntityKey generateEntityKeyOrNull(Serializable id, SessionImplementor s, String entityName)
/*  93:    */   {
/*  94:132 */     if ((id == null) || (s == null) || (entityName == null)) {
/*  95:133 */       return null;
/*  96:    */     }
/*  97:135 */     return s.generateEntityKey(id, s.getFactory().getEntityPersister(entityName));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final void unsetSession()
/* 101:    */   {
/* 102:140 */     this.session = null;
/* 103:141 */     this.readOnly = false;
/* 104:142 */     this.readOnlyBeforeAttachedToSession = null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final void initialize()
/* 108:    */     throws HibernateException
/* 109:    */   {
/* 110:147 */     if (!this.initialized)
/* 111:    */     {
/* 112:148 */       if (this.session == null) {
/* 113:149 */         throw new LazyInitializationException("could not initialize proxy - no Session");
/* 114:    */       }
/* 115:151 */       if (!this.session.isOpen()) {
/* 116:152 */         throw new LazyInitializationException("could not initialize proxy - the owning Session was closed");
/* 117:    */       }
/* 118:154 */       if (!this.session.isConnected()) {
/* 119:155 */         throw new LazyInitializationException("could not initialize proxy - the owning Session is disconnected");
/* 120:    */       }
/* 121:158 */       this.target = this.session.immediateLoad(this.entityName, this.id);
/* 122:159 */       this.initialized = true;
/* 123:160 */       checkTargetState();
/* 124:    */     }
/* 125:    */     else
/* 126:    */     {
/* 127:164 */       checkTargetState();
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private void checkTargetState()
/* 132:    */   {
/* 133:169 */     if ((!this.unwrap) && 
/* 134:170 */       (this.target == null)) {
/* 135:171 */       getSession().getFactory().getEntityNotFoundDelegate().handleEntityNotFound(this.entityName, this.id);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected final boolean isConnectedToSession()
/* 140:    */   {
/* 141:182 */     return getProxyOrNull() != null;
/* 142:    */   }
/* 143:    */   
/* 144:    */   private Object getProxyOrNull()
/* 145:    */   {
/* 146:186 */     EntityKey entityKey = generateEntityKeyOrNull(getIdentifier(), this.session, getEntityName());
/* 147:187 */     if ((entityKey != null) && (this.session != null) && (this.session.isOpen())) {
/* 148:188 */       return this.session.getPersistenceContext().getProxy(entityKey);
/* 149:    */     }
/* 150:190 */     return null;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final Object getImplementation()
/* 154:    */   {
/* 155:195 */     initialize();
/* 156:196 */     return this.target;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public final void setImplementation(Object target)
/* 160:    */   {
/* 161:201 */     this.target = target;
/* 162:202 */     this.initialized = true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public final Object getImplementation(SessionImplementor s)
/* 166:    */     throws HibernateException
/* 167:    */   {
/* 168:207 */     EntityKey entityKey = generateEntityKeyOrNull(getIdentifier(), s, getEntityName());
/* 169:208 */     return entityKey == null ? null : s.getPersistenceContext().getEntity(entityKey);
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected final Object getTarget()
/* 173:    */   {
/* 174:219 */     return this.target;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public final boolean isReadOnlySettingAvailable()
/* 178:    */   {
/* 179:224 */     return (this.session != null) && (!this.session.isClosed());
/* 180:    */   }
/* 181:    */   
/* 182:    */   private void errorIfReadOnlySettingNotAvailable()
/* 183:    */   {
/* 184:228 */     if (this.session == null) {
/* 185:229 */       throw new TransientObjectException("Proxy is detached (i.e, session is null). The read-only/modifiable setting is only accessible when the proxy is associated with an open session.");
/* 186:    */     }
/* 187:232 */     if (this.session.isClosed()) {
/* 188:233 */       throw new SessionException("Session is closed. The read-only/modifiable setting is only accessible when the proxy is associated with an open session.");
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public final boolean isReadOnly()
/* 193:    */   {
/* 194:240 */     errorIfReadOnlySettingNotAvailable();
/* 195:241 */     return this.readOnly;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public final void setReadOnly(boolean readOnly)
/* 199:    */   {
/* 200:246 */     errorIfReadOnlySettingNotAvailable();
/* 201:248 */     if (this.readOnly != readOnly)
/* 202:    */     {
/* 203:249 */       EntityPersister persister = this.session.getFactory().getEntityPersister(this.entityName);
/* 204:250 */       if ((!persister.isMutable()) && (!readOnly)) {
/* 205:251 */         throw new IllegalStateException("cannot make proxies for immutable entities modifiable");
/* 206:    */       }
/* 207:253 */       this.readOnly = readOnly;
/* 208:254 */       if (this.initialized)
/* 209:    */       {
/* 210:255 */         EntityKey key = generateEntityKeyOrNull(getIdentifier(), this.session, getEntityName());
/* 211:256 */         if ((key != null) && (this.session.getPersistenceContext().containsEntity(key))) {
/* 212:257 */           this.session.getPersistenceContext().setReadOnly(this.target, readOnly);
/* 213:    */         }
/* 214:    */       }
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected final Boolean isReadOnlyBeforeAttachedToSession()
/* 219:    */   {
/* 220:276 */     if (isReadOnlySettingAvailable()) {
/* 221:277 */       throw new IllegalStateException("Cannot call isReadOnlyBeforeAttachedToSession when isReadOnlySettingAvailable == true");
/* 222:    */     }
/* 223:281 */     return this.readOnlyBeforeAttachedToSession;
/* 224:    */   }
/* 225:    */   
/* 226:    */   final void setReadOnlyBeforeAttachedToSession(Boolean readOnlyBeforeAttachedToSession)
/* 227:    */   {
/* 228:297 */     if (isReadOnlySettingAvailable()) {
/* 229:298 */       throw new IllegalStateException("Cannot call setReadOnlyBeforeAttachedToSession when isReadOnlySettingAvailable == true");
/* 230:    */     }
/* 231:302 */     this.readOnlyBeforeAttachedToSession = readOnlyBeforeAttachedToSession;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public boolean isUnwrap()
/* 235:    */   {
/* 236:307 */     return this.unwrap;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setUnwrap(boolean unwrap)
/* 240:    */   {
/* 241:312 */     this.unwrap = unwrap;
/* 242:    */   }
/* 243:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.AbstractLazyInitializer
 * JD-Core Version:    0.7.0.1
 */