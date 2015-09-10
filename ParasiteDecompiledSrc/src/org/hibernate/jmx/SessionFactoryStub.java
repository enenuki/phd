/*   1:    */ package org.hibernate.jmx;
/*   2:    */ 
/*   3:    */ import java.io.InvalidObjectException;
/*   4:    */ import java.io.ObjectStreamException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import javax.naming.NamingException;
/*  10:    */ import javax.naming.Reference;
/*  11:    */ import javax.naming.StringRefAddr;
/*  12:    */ import org.hibernate.AssertionFailure;
/*  13:    */ import org.hibernate.Cache;
/*  14:    */ import org.hibernate.HibernateException;
/*  15:    */ import org.hibernate.Session;
/*  16:    */ import org.hibernate.SessionBuilder;
/*  17:    */ import org.hibernate.SessionFactory;
/*  18:    */ import org.hibernate.SessionFactory.SessionFactoryOptions;
/*  19:    */ import org.hibernate.StatelessSession;
/*  20:    */ import org.hibernate.StatelessSessionBuilder;
/*  21:    */ import org.hibernate.TypeHelper;
/*  22:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  23:    */ import org.hibernate.id.IdentifierGenerator;
/*  24:    */ import org.hibernate.id.UUIDGenerator;
/*  25:    */ import org.hibernate.internal.CoreMessageLogger;
/*  26:    */ import org.hibernate.internal.SessionFactoryRegistry;
/*  27:    */ import org.hibernate.internal.SessionFactoryRegistry.ObjectFactoryImpl;
/*  28:    */ import org.hibernate.metadata.ClassMetadata;
/*  29:    */ import org.hibernate.metadata.CollectionMetadata;
/*  30:    */ import org.hibernate.service.jndi.internal.JndiServiceImpl;
/*  31:    */ import org.hibernate.stat.Statistics;
/*  32:    */ import org.jboss.logging.Logger;
/*  33:    */ 
/*  34:    */ @Deprecated
/*  35:    */ public class SessionFactoryStub
/*  36:    */   implements SessionFactory
/*  37:    */ {
/*  38: 69 */   private static final IdentifierGenerator UUID_GENERATOR = ;
/*  39: 71 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SessionFactoryStub.class.getName());
/*  40:    */   private transient SessionFactory impl;
/*  41:    */   private transient HibernateService service;
/*  42:    */   private String uuid;
/*  43:    */   private String name;
/*  44:    */   
/*  45:    */   SessionFactoryStub(HibernateService service)
/*  46:    */   {
/*  47: 79 */     this.service = service;
/*  48: 80 */     this.name = service.getJndiName();
/*  49:    */     try
/*  50:    */     {
/*  51: 82 */       this.uuid = ((String)UUID_GENERATOR.generate(null, null));
/*  52:    */     }
/*  53:    */     catch (Exception e)
/*  54:    */     {
/*  55: 85 */       throw new AssertionFailure("Could not generate UUID");
/*  56:    */     }
/*  57: 88 */     SessionFactoryRegistry.INSTANCE.addSessionFactory(this.uuid, this.name, this, new JndiServiceImpl(service.getProperties()));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public SessionFactory.SessionFactoryOptions getSessionFactoryOptions()
/*  61:    */   {
/*  62: 93 */     return this.impl.getSessionFactoryOptions();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public SessionBuilder withOptions()
/*  66:    */   {
/*  67: 98 */     return getImpl().withOptions();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Session openSession()
/*  71:    */     throws HibernateException
/*  72:    */   {
/*  73:102 */     return getImpl().openSession();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Session getCurrentSession()
/*  77:    */   {
/*  78:106 */     return getImpl().getCurrentSession();
/*  79:    */   }
/*  80:    */   
/*  81:    */   private synchronized SessionFactory getImpl()
/*  82:    */   {
/*  83:110 */     if (this.impl == null) {
/*  84:110 */       this.impl = this.service.buildSessionFactory();
/*  85:    */     }
/*  86:111 */     return this.impl;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private Object readResolve()
/*  90:    */     throws ObjectStreamException
/*  91:    */   {
/*  92:117 */     Object result = SessionFactoryRegistry.INSTANCE.getSessionFactory(this.uuid);
/*  93:118 */     if (result == null)
/*  94:    */     {
/*  95:121 */       result = SessionFactoryRegistry.INSTANCE.getNamedSessionFactory(this.name);
/*  96:122 */       if (result == null) {
/*  97:123 */         throw new InvalidObjectException("Could not find a SessionFactory [uuid=" + this.uuid + ",name=" + this.name + "]");
/*  98:    */       }
/*  99:125 */       LOG.debug("Resolved stub SessionFactory by name");
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:128 */       LOG.debug("Resolved stub SessionFactory by UUID");
/* 104:    */     }
/* 105:130 */     return result;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Reference getReference()
/* 109:    */     throws NamingException
/* 110:    */   {
/* 111:138 */     return new Reference(SessionFactoryStub.class.getName(), new StringRefAddr("uuid", this.uuid), SessionFactoryRegistry.ObjectFactoryImpl.class.getName(), null);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public ClassMetadata getClassMetadata(Class persistentClass)
/* 115:    */     throws HibernateException
/* 116:    */   {
/* 117:147 */     return getImpl().getClassMetadata(persistentClass);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public ClassMetadata getClassMetadata(String entityName)
/* 121:    */     throws HibernateException
/* 122:    */   {
/* 123:152 */     return getImpl().getClassMetadata(entityName);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public CollectionMetadata getCollectionMetadata(String roleName)
/* 127:    */     throws HibernateException
/* 128:    */   {
/* 129:156 */     return getImpl().getCollectionMetadata(roleName);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Map<String, ClassMetadata> getAllClassMetadata()
/* 133:    */     throws HibernateException
/* 134:    */   {
/* 135:160 */     return getImpl().getAllClassMetadata();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Map getAllCollectionMetadata()
/* 139:    */     throws HibernateException
/* 140:    */   {
/* 141:164 */     return getImpl().getAllCollectionMetadata();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void close()
/* 145:    */     throws HibernateException
/* 146:    */   {}
/* 147:    */   
/* 148:    */   public boolean isClosed()
/* 149:    */   {
/* 150:171 */     return false;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Cache getCache()
/* 154:    */   {
/* 155:175 */     return getImpl().getCache();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void evict(Class persistentClass, Serializable id)
/* 159:    */     throws HibernateException
/* 160:    */   {
/* 161:180 */     getImpl().evict(persistentClass, id);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void evict(Class persistentClass)
/* 165:    */     throws HibernateException
/* 166:    */   {
/* 167:184 */     getImpl().evict(persistentClass);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void evictEntity(String entityName, Serializable id)
/* 171:    */     throws HibernateException
/* 172:    */   {
/* 173:189 */     getImpl().evictEntity(entityName, id);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void evictEntity(String entityName)
/* 177:    */     throws HibernateException
/* 178:    */   {
/* 179:193 */     getImpl().evictEntity(entityName);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void evictCollection(String roleName, Serializable id)
/* 183:    */     throws HibernateException
/* 184:    */   {
/* 185:198 */     getImpl().evictCollection(roleName, id);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void evictCollection(String roleName)
/* 189:    */     throws HibernateException
/* 190:    */   {
/* 191:202 */     getImpl().evictCollection(roleName);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void evictQueries()
/* 195:    */     throws HibernateException
/* 196:    */   {
/* 197:206 */     getImpl().evictQueries();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void evictQueries(String cacheRegion)
/* 201:    */     throws HibernateException
/* 202:    */   {
/* 203:210 */     getImpl().evictQueries(cacheRegion);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public Statistics getStatistics()
/* 207:    */   {
/* 208:214 */     return getImpl().getStatistics();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public StatelessSessionBuilder withStatelessOptions()
/* 212:    */   {
/* 213:219 */     return getImpl().withStatelessOptions();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public StatelessSession openStatelessSession()
/* 217:    */   {
/* 218:223 */     return getImpl().openStatelessSession();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public StatelessSession openStatelessSession(Connection conn)
/* 222:    */   {
/* 223:227 */     return getImpl().openStatelessSession(conn);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Set getDefinedFilterNames()
/* 227:    */   {
/* 228:231 */     return getImpl().getDefinedFilterNames();
/* 229:    */   }
/* 230:    */   
/* 231:    */   public FilterDefinition getFilterDefinition(String filterName)
/* 232:    */     throws HibernateException
/* 233:    */   {
/* 234:235 */     return getImpl().getFilterDefinition(filterName);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean containsFetchProfileDefinition(String name)
/* 238:    */   {
/* 239:239 */     return getImpl().containsFetchProfileDefinition(name);
/* 240:    */   }
/* 241:    */   
/* 242:    */   public TypeHelper getTypeHelper()
/* 243:    */   {
/* 244:243 */     return getImpl().getTypeHelper();
/* 245:    */   }
/* 246:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jmx.SessionFactoryStub
 * JD-Core Version:    0.7.0.1
 */