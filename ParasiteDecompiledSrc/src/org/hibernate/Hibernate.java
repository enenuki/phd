/*   1:    */ package org.hibernate;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*   5:    */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*   6:    */ import org.hibernate.collection.spi.PersistentCollection;
/*   7:    */ import org.hibernate.engine.HibernateIterator;
/*   8:    */ import org.hibernate.engine.jdbc.LobCreator;
/*   9:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.proxy.HibernateProxy;
/*  13:    */ import org.hibernate.proxy.LazyInitializer;
/*  14:    */ 
/*  15:    */ public final class Hibernate
/*  16:    */ {
/*  17:    */   private Hibernate()
/*  18:    */   {
/*  19: 57 */     throw new UnsupportedOperationException();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static void initialize(Object proxy)
/*  23:    */     throws HibernateException
/*  24:    */   {
/*  25: 71 */     if (proxy == null) {
/*  26: 72 */       return;
/*  27:    */     }
/*  28: 74 */     if ((proxy instanceof HibernateProxy)) {
/*  29: 75 */       ((HibernateProxy)proxy).getHibernateLazyInitializer().initialize();
/*  30: 77 */     } else if ((proxy instanceof PersistentCollection)) {
/*  31: 78 */       ((PersistentCollection)proxy).forceInitialization();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static boolean isInitialized(Object proxy)
/*  36:    */   {
/*  37: 89 */     if ((proxy instanceof HibernateProxy)) {
/*  38: 90 */       return !((HibernateProxy)proxy).getHibernateLazyInitializer().isUninitialized();
/*  39:    */     }
/*  40: 92 */     if ((proxy instanceof PersistentCollection)) {
/*  41: 93 */       return ((PersistentCollection)proxy).wasInitialized();
/*  42:    */     }
/*  43: 96 */     return true;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static Class getClass(Object proxy)
/*  47:    */   {
/*  48:109 */     if ((proxy instanceof HibernateProxy)) {
/*  49:110 */       return ((HibernateProxy)proxy).getHibernateLazyInitializer().getImplementation().getClass();
/*  50:    */     }
/*  51:115 */     return proxy.getClass();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static LobCreator getLobCreator(Session session)
/*  55:    */   {
/*  56:120 */     return getLobCreator((SessionImplementor)session);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static LobCreator getLobCreator(SessionImplementor session)
/*  60:    */   {
/*  61:124 */     return session.getFactory().getJdbcServices().getLobCreator(session);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void close(Iterator iterator)
/*  65:    */     throws HibernateException
/*  66:    */   {
/*  67:139 */     if ((iterator instanceof HibernateIterator)) {
/*  68:140 */       ((HibernateIterator)iterator).close();
/*  69:    */     } else {
/*  70:143 */       throw new IllegalArgumentException("not a Hibernate iterator");
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean isPropertyInitialized(Object proxy, String propertyName)
/*  75:    */   {
/*  76:    */     Object entity;
/*  77:    */     Object entity;
/*  78:158 */     if ((proxy instanceof HibernateProxy))
/*  79:    */     {
/*  80:159 */       LazyInitializer li = ((HibernateProxy)proxy).getHibernateLazyInitializer();
/*  81:160 */       if (li.isUninitialized()) {
/*  82:161 */         return false;
/*  83:    */       }
/*  84:164 */       entity = li.getImplementation();
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88:168 */       entity = proxy;
/*  89:    */     }
/*  90:171 */     if (FieldInterceptionHelper.isInstrumented(entity))
/*  91:    */     {
/*  92:172 */       FieldInterceptor interceptor = FieldInterceptionHelper.extractFieldInterceptor(entity);
/*  93:173 */       return (interceptor == null) || (interceptor.isInitialized(propertyName));
/*  94:    */     }
/*  95:176 */     return true;
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Hibernate
 * JD-Core Version:    0.7.0.1
 */