/*   1:    */ package org.hibernate.context.internal;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.reflect.InvocationHandler;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import java.lang.reflect.Proxy;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.transaction.Synchronization;
/*  14:    */ import org.hibernate.ConnectionReleaseMode;
/*  15:    */ import org.hibernate.HibernateException;
/*  16:    */ import org.hibernate.Session;
/*  17:    */ import org.hibernate.SessionBuilder;
/*  18:    */ import org.hibernate.SessionFactory;
/*  19:    */ import org.hibernate.Transaction;
/*  20:    */ import org.hibernate.cfg.Settings;
/*  21:    */ import org.hibernate.context.spi.CurrentSessionContext;
/*  22:    */ import org.hibernate.engine.jdbc.LobCreationContext;
/*  23:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  24:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  25:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  26:    */ import org.hibernate.event.spi.EventSource;
/*  27:    */ import org.hibernate.internal.CoreMessageLogger;
/*  28:    */ import org.jboss.logging.Logger;
/*  29:    */ 
/*  30:    */ public class ThreadLocalSessionContext
/*  31:    */   implements CurrentSessionContext
/*  32:    */ {
/*  33: 80 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ThreadLocalSessionContext.class.getName());
/*  34: 82 */   private static final Class[] SESSION_PROXY_INTERFACES = { Session.class, SessionImplementor.class, EventSource.class, TransactionContext.class, LobCreationContext.class };
/*  35: 96 */   private static final ThreadLocal<Map> context = new ThreadLocal();
/*  36:    */   protected final SessionFactoryImplementor factory;
/*  37:    */   
/*  38:    */   public ThreadLocalSessionContext(SessionFactoryImplementor factory)
/*  39:    */   {
/*  40:101 */     this.factory = factory;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final Session currentSession()
/*  44:    */     throws HibernateException
/*  45:    */   {
/*  46:108 */     Session current = existingSession(this.factory);
/*  47:109 */     if (current == null)
/*  48:    */     {
/*  49:110 */       current = buildOrObtainSession();
/*  50:    */       
/*  51:112 */       current.getTransaction().registerSynchronization(buildCleanupSynch());
/*  52:114 */       if (needsWrapping(current)) {
/*  53:115 */         current = wrap(current);
/*  54:    */       }
/*  55:118 */       doBind(current, this.factory);
/*  56:    */     }
/*  57:120 */     return current;
/*  58:    */   }
/*  59:    */   
/*  60:    */   private boolean needsWrapping(Session session)
/*  61:    */   {
/*  62:125 */     return ((session != null) && (!Proxy.isProxyClass(session.getClass()))) || ((Proxy.getInvocationHandler(session) != null) && (!(Proxy.getInvocationHandler(session) instanceof TransactionProtectionWrapper)));
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected SessionFactoryImplementor getFactory()
/*  66:    */   {
/*  67:137 */     return this.factory;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected Session buildOrObtainSession()
/*  71:    */   {
/*  72:149 */     return this.factory.withOptions().autoClose(isAutoCloseEnabled()).connectionReleaseMode(getConnectionReleaseMode()).flushBeforeCompletion(isAutoFlushEnabled()).openSession();
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected CleanupSynch buildCleanupSynch()
/*  76:    */   {
/*  77:157 */     return new CleanupSynch(this.factory);
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected boolean isAutoCloseEnabled()
/*  81:    */   {
/*  82:166 */     return true;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected boolean isAutoFlushEnabled()
/*  86:    */   {
/*  87:175 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected ConnectionReleaseMode getConnectionReleaseMode()
/*  91:    */   {
/*  92:184 */     return this.factory.getSettings().getConnectionReleaseMode();
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected Session wrap(Session session)
/*  96:    */   {
/*  97:188 */     TransactionProtectionWrapper wrapper = new TransactionProtectionWrapper(session);
/*  98:189 */     Session wrapped = (Session)Proxy.newProxyInstance(Session.class.getClassLoader(), SESSION_PROXY_INTERFACES, wrapper);
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:195 */     wrapper.setWrapped(wrapped);
/* 105:196 */     return wrapped;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static void bind(Session session)
/* 109:    */   {
/* 110:205 */     SessionFactory factory = session.getSessionFactory();
/* 111:206 */     cleanupAnyOrphanedSession(factory);
/* 112:207 */     doBind(session, factory);
/* 113:    */   }
/* 114:    */   
/* 115:    */   private static void cleanupAnyOrphanedSession(SessionFactory factory)
/* 116:    */   {
/* 117:211 */     Session orphan = doUnbind(factory, false);
/* 118:212 */     if (orphan != null)
/* 119:    */     {
/* 120:213 */       LOG.alreadySessionBound();
/* 121:    */       try
/* 122:    */       {
/* 123:215 */         if ((orphan.getTransaction() != null) && (orphan.getTransaction().isActive())) {
/* 124:    */           try
/* 125:    */           {
/* 126:217 */             orphan.getTransaction().rollback();
/* 127:    */           }
/* 128:    */           catch (Throwable t)
/* 129:    */           {
/* 130:220 */             LOG.debug("Unable to rollback transaction for orphaned session", t);
/* 131:    */           }
/* 132:    */         }
/* 133:223 */         orphan.close();
/* 134:    */       }
/* 135:    */       catch (Throwable t)
/* 136:    */       {
/* 137:226 */         LOG.debug("Unable to close orphaned session", t);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Session unbind(SessionFactory factory)
/* 143:    */   {
/* 144:238 */     return doUnbind(factory, true);
/* 145:    */   }
/* 146:    */   
/* 147:    */   private static Session existingSession(SessionFactory factory)
/* 148:    */   {
/* 149:242 */     Map sessionMap = sessionMap();
/* 150:243 */     if (sessionMap == null) {
/* 151:243 */       return null;
/* 152:    */     }
/* 153:244 */     return (Session)sessionMap.get(factory);
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected static Map sessionMap()
/* 157:    */   {
/* 158:248 */     return (Map)context.get();
/* 159:    */   }
/* 160:    */   
/* 161:    */   private static void doBind(Session session, SessionFactory factory)
/* 162:    */   {
/* 163:253 */     Map sessionMap = sessionMap();
/* 164:254 */     if (sessionMap == null)
/* 165:    */     {
/* 166:255 */       sessionMap = new HashMap();
/* 167:256 */       context.set(sessionMap);
/* 168:    */     }
/* 169:258 */     sessionMap.put(factory, session);
/* 170:    */   }
/* 171:    */   
/* 172:    */   private static Session doUnbind(SessionFactory factory, boolean releaseMapIfEmpty)
/* 173:    */   {
/* 174:262 */     Map sessionMap = sessionMap();
/* 175:263 */     Session session = null;
/* 176:264 */     if (sessionMap != null)
/* 177:    */     {
/* 178:265 */       session = (Session)sessionMap.remove(factory);
/* 179:266 */       if ((releaseMapIfEmpty) && (sessionMap.isEmpty())) {
/* 180:267 */         context.set(null);
/* 181:    */       }
/* 182:    */     }
/* 183:270 */     return session;
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected static class CleanupSynch
/* 187:    */     implements Synchronization, Serializable
/* 188:    */   {
/* 189:    */     protected final SessionFactory factory;
/* 190:    */     
/* 191:    */     public CleanupSynch(SessionFactory factory)
/* 192:    */     {
/* 193:280 */       this.factory = factory;
/* 194:    */     }
/* 195:    */     
/* 196:    */     public void beforeCompletion() {}
/* 197:    */     
/* 198:    */     public void afterCompletion(int i)
/* 199:    */     {
/* 200:293 */       ThreadLocalSessionContext.unbind(this.factory);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   private class TransactionProtectionWrapper
/* 205:    */     implements InvocationHandler, Serializable
/* 206:    */   {
/* 207:    */     private final Session realSession;
/* 208:    */     private Session wrappedSession;
/* 209:    */     
/* 210:    */     public TransactionProtectionWrapper(Session realSession)
/* 211:    */     {
/* 212:302 */       this.realSession = realSession;
/* 213:    */     }
/* 214:    */     
/* 215:    */     public Object invoke(Object proxy, Method method, Object[] args)
/* 216:    */       throws Throwable
/* 217:    */     {
/* 218:309 */       String methodName = method.getName();
/* 219:    */       try
/* 220:    */       {
/* 221:312 */         if ("close".equals(methodName)) {
/* 222:313 */           ThreadLocalSessionContext.unbind(this.realSession.getSessionFactory());
/* 223:315 */         } else if ((!"toString".equals(methodName)) && (!"equals".equals(methodName)) && (!"hashCode".equals(methodName)) && (!"getStatistics".equals(methodName)) && (!"isOpen".equals(methodName)) && (!"getListeners".equals(methodName))) {
/* 224:324 */           if (this.realSession.isOpen()) {
/* 225:331 */             if (!this.realSession.getTransaction().isActive()) {
/* 226:333 */               if (("beginTransaction".equals(methodName)) || ("getTransaction".equals(methodName)) || ("isTransactionInProgress".equals(methodName)) || ("setFlushMode".equals(methodName)) || ("getFactory".equals(methodName)) || ("getSessionFactory".equals(methodName))) {
/* 227:339 */                 ThreadLocalSessionContext.LOG.tracev("Allowing method [{0}] in non-transacted context", methodName);
/* 228:341 */               } else if ((!"reconnect".equals(methodName)) && (!"disconnect".equals(methodName))) {
/* 229:346 */                 throw new HibernateException(methodName + " is not valid without active transaction");
/* 230:    */               }
/* 231:    */             }
/* 232:    */           }
/* 233:    */         }
/* 234:349 */         ThreadLocalSessionContext.LOG.tracev("Allowing proxied method [{0}] to proceed to real session", methodName);
/* 235:350 */         return method.invoke(this.realSession, args);
/* 236:    */       }
/* 237:    */       catch (InvocationTargetException e)
/* 238:    */       {
/* 239:353 */         if ((e.getTargetException() instanceof RuntimeException)) {
/* 240:353 */           throw ((RuntimeException)e.getTargetException());
/* 241:    */         }
/* 242:354 */         throw e;
/* 243:    */       }
/* 244:    */     }
/* 245:    */     
/* 246:    */     public void setWrapped(Session wrapped)
/* 247:    */     {
/* 248:364 */       this.wrappedSession = wrapped;
/* 249:    */     }
/* 250:    */     
/* 251:    */     private void writeObject(ObjectOutputStream oos)
/* 252:    */       throws IOException
/* 253:    */     {
/* 254:374 */       oos.defaultWriteObject();
/* 255:375 */       if (ThreadLocalSessionContext.existingSession(ThreadLocalSessionContext.this.factory) == this.wrappedSession) {
/* 256:376 */         ThreadLocalSessionContext.unbind(ThreadLocalSessionContext.this.factory);
/* 257:    */       }
/* 258:    */     }
/* 259:    */     
/* 260:    */     private void readObject(ObjectInputStream ois)
/* 261:    */       throws IOException, ClassNotFoundException
/* 262:    */     {
/* 263:384 */       ois.defaultReadObject();
/* 264:385 */       this.realSession.getTransaction().registerSynchronization(ThreadLocalSessionContext.this.buildCleanupSynch());
/* 265:386 */       ThreadLocalSessionContext.doBind(this.wrappedSession, ThreadLocalSessionContext.this.factory);
/* 266:    */     }
/* 267:    */   }
/* 268:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.context.internal.ThreadLocalSessionContext
 * JD-Core Version:    0.7.0.1
 */