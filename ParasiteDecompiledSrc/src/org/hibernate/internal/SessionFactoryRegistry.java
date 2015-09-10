/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.concurrent.ConcurrentHashMap;
/*   5:    */ import javax.naming.Binding;
/*   6:    */ import javax.naming.Context;
/*   7:    */ import javax.naming.Name;
/*   8:    */ import javax.naming.RefAddr;
/*   9:    */ import javax.naming.Reference;
/*  10:    */ import javax.naming.event.NamespaceChangeListener;
/*  11:    */ import javax.naming.event.NamingEvent;
/*  12:    */ import javax.naming.event.NamingExceptionEvent;
/*  13:    */ import javax.naming.spi.ObjectFactory;
/*  14:    */ import org.hibernate.SessionFactory;
/*  15:    */ import org.hibernate.service.jndi.JndiException;
/*  16:    */ import org.hibernate.service.jndi.JndiNameException;
/*  17:    */ import org.hibernate.service.jndi.spi.JndiService;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class SessionFactoryRegistry
/*  21:    */ {
/*  22: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SessionFactoryRegistry.class.getName());
/*  23: 56 */   public static final SessionFactoryRegistry INSTANCE = new SessionFactoryRegistry();
/*  24: 58 */   private final ConcurrentHashMap<String, SessionFactory> sessionFactoryMap = new ConcurrentHashMap();
/*  25: 59 */   private final ConcurrentHashMap<String, String> nameUuidXref = new ConcurrentHashMap();
/*  26:    */   
/*  27:    */   public SessionFactoryRegistry()
/*  28:    */   {
/*  29: 62 */     LOG.debugf("Initializing SessionFactoryRegistry : %s", this);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void addSessionFactory(String uuid, String name, SessionFactory instance, JndiService jndiService)
/*  33:    */   {
/*  34: 66 */     LOG.debugf("Registering SessionFactory: %s (%s)", uuid, name == null ? "<unnamed>" : name);
/*  35: 67 */     this.sessionFactoryMap.put(uuid, instance);
/*  36: 69 */     if (name == null)
/*  37:    */     {
/*  38: 70 */       LOG.debug("Not binding factory to JNDI, no JNDI name configured");
/*  39: 71 */       return;
/*  40:    */     }
/*  41: 74 */     this.nameUuidXref.put(name, uuid);
/*  42:    */     
/*  43: 76 */     LOG.debugf("SessionFactory name : %s, attempting to bind to JNDI", name);
/*  44:    */     try
/*  45:    */     {
/*  46: 79 */       jndiService.bind(name, instance);
/*  47: 80 */       LOG.factoryBoundToJndiName(name);
/*  48:    */       try
/*  49:    */       {
/*  50: 82 */         jndiService.addListener(name, this.LISTENER);
/*  51:    */       }
/*  52:    */       catch (Exception e)
/*  53:    */       {
/*  54: 85 */         LOG.couldNotBindJndiListener();
/*  55:    */       }
/*  56:    */     }
/*  57:    */     catch (JndiNameException e)
/*  58:    */     {
/*  59: 89 */       LOG.invalidJndiName(name, e);
/*  60:    */     }
/*  61:    */     catch (JndiException e)
/*  62:    */     {
/*  63: 92 */       LOG.unableToBindFactoryToJndi(e);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void removeSessionFactory(String uuid, String name, JndiService jndiService)
/*  68:    */   {
/*  69: 97 */     if (name != null)
/*  70:    */     {
/*  71:    */       try
/*  72:    */       {
/*  73: 99 */         LOG.tracef("Unbinding SessionFactory from JNDI : %s", name);
/*  74:100 */         jndiService.unbind(name);
/*  75:101 */         LOG.factoryUnboundFromJndiName(name);
/*  76:    */       }
/*  77:    */       catch (JndiNameException e)
/*  78:    */       {
/*  79:104 */         LOG.invalidJndiName(name, e);
/*  80:    */       }
/*  81:    */       catch (JndiException e)
/*  82:    */       {
/*  83:107 */         LOG.unableToUnbindFactoryFromJndi(e);
/*  84:    */       }
/*  85:110 */       this.nameUuidXref.remove(name);
/*  86:    */     }
/*  87:113 */     this.sessionFactoryMap.remove(uuid);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public SessionFactory getNamedSessionFactory(String name)
/*  91:    */   {
/*  92:117 */     LOG.debugf("Lookup: name=%s", name);
/*  93:118 */     String uuid = (String)this.nameUuidXref.get(name);
/*  94:119 */     return getSessionFactory(uuid);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public SessionFactory getSessionFactory(String uuid)
/*  98:    */   {
/*  99:123 */     LOG.debugf("Lookup: uid=%s", uuid);
/* 100:124 */     SessionFactory sessionFactory = (SessionFactory)this.sessionFactoryMap.get(uuid);
/* 101:125 */     if ((sessionFactory == null) && (LOG.isDebugEnabled()))
/* 102:    */     {
/* 103:126 */       LOG.debugf("Not found: %s", uuid);
/* 104:127 */       LOG.debugf(this.sessionFactoryMap.toString(), new Object[0]);
/* 105:    */     }
/* 106:129 */     return sessionFactory;
/* 107:    */   }
/* 108:    */   
/* 109:136 */   private final NamespaceChangeListener LISTENER = new NamespaceChangeListener()
/* 110:    */   {
/* 111:    */     public void objectAdded(NamingEvent evt)
/* 112:    */     {
/* 113:139 */       SessionFactoryRegistry.LOG.debugf("A factory was successfully bound to name: %s", evt.getNewBinding().getName());
/* 114:    */     }
/* 115:    */     
/* 116:    */     public void objectRemoved(NamingEvent evt)
/* 117:    */     {
/* 118:144 */       String jndiName = evt.getOldBinding().getName();
/* 119:145 */       SessionFactoryRegistry.LOG.factoryUnboundFromName(jndiName);
/* 120:    */       
/* 121:147 */       String uuid = (String)SessionFactoryRegistry.this.nameUuidXref.remove(jndiName);
/* 122:148 */       if (uuid == null) {}
/* 123:151 */       SessionFactoryRegistry.this.sessionFactoryMap.remove(uuid);
/* 124:    */     }
/* 125:    */     
/* 126:    */     public void objectRenamed(NamingEvent evt)
/* 127:    */     {
/* 128:156 */       String oldJndiName = evt.getOldBinding().getName();
/* 129:157 */       String newJndiName = evt.getNewBinding().getName();
/* 130:    */       
/* 131:159 */       SessionFactoryRegistry.LOG.factoryJndiRename(oldJndiName, newJndiName);
/* 132:    */       
/* 133:161 */       String uuid = (String)SessionFactoryRegistry.this.nameUuidXref.remove(oldJndiName);
/* 134:162 */       SessionFactoryRegistry.this.nameUuidXref.put(newJndiName, uuid);
/* 135:    */     }
/* 136:    */     
/* 137:    */     public void namingExceptionThrown(NamingExceptionEvent evt)
/* 138:    */     {
/* 139:168 */       SessionFactoryRegistry.LOG.namingExceptionAccessingFactory(evt.getException());
/* 140:    */     }
/* 141:    */   };
/* 142:    */   
/* 143:    */   public static class ObjectFactoryImpl
/* 144:    */     implements ObjectFactory
/* 145:    */   {
/* 146:    */     public Object getObjectInstance(Object reference, Name name, Context nameCtx, Hashtable<?, ?> environment)
/* 147:    */       throws Exception
/* 148:    */     {
/* 149:176 */       SessionFactoryRegistry.LOG.debugf("JNDI lookup: %s", name);
/* 150:177 */       String uuid = (String)((Reference)reference).get(0).getContent();
/* 151:178 */       SessionFactoryRegistry.LOG.tracef("Resolved to UUID = %s", uuid);
/* 152:179 */       return SessionFactoryRegistry.INSTANCE.getSessionFactory(uuid);
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.SessionFactoryRegistry
 * JD-Core Version:    0.7.0.1
 */