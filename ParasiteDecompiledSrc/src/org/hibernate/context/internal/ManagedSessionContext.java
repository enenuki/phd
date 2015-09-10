/*   1:    */ package org.hibernate.context.internal;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.Session;
/*   7:    */ import org.hibernate.SessionFactory;
/*   8:    */ import org.hibernate.context.spi.CurrentSessionContext;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ 
/*  11:    */ public class ManagedSessionContext
/*  12:    */   implements CurrentSessionContext
/*  13:    */ {
/*  14: 61 */   private static final ThreadLocal<Map<SessionFactory, Session>> context = new ThreadLocal();
/*  15:    */   private final SessionFactoryImplementor factory;
/*  16:    */   
/*  17:    */   public ManagedSessionContext(SessionFactoryImplementor factory)
/*  18:    */   {
/*  19: 65 */     this.factory = factory;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Session currentSession()
/*  23:    */   {
/*  24: 70 */     Session current = existingSession(this.factory);
/*  25: 71 */     if (current == null) {
/*  26: 72 */       throw new HibernateException("No session currently bound to execution context");
/*  27:    */     }
/*  28: 74 */     return current;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static boolean hasBind(SessionFactory factory)
/*  32:    */   {
/*  33: 86 */     return existingSession(factory) != null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Session bind(Session session)
/*  37:    */   {
/*  38: 96 */     return (Session)sessionMap(true).put(session.getSessionFactory(), session);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Session unbind(SessionFactory factory)
/*  42:    */   {
/*  43:107 */     Session existing = null;
/*  44:108 */     Map<SessionFactory, Session> sessionMap = sessionMap();
/*  45:109 */     if (sessionMap != null)
/*  46:    */     {
/*  47:110 */       existing = (Session)sessionMap.remove(factory);
/*  48:111 */       doCleanup();
/*  49:    */     }
/*  50:113 */     return existing;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static Session existingSession(SessionFactory factory)
/*  54:    */   {
/*  55:117 */     Map sessionMap = sessionMap();
/*  56:118 */     if (sessionMap == null) {
/*  57:119 */       return null;
/*  58:    */     }
/*  59:122 */     return (Session)sessionMap.get(factory);
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected static Map<SessionFactory, Session> sessionMap()
/*  63:    */   {
/*  64:127 */     return sessionMap(false);
/*  65:    */   }
/*  66:    */   
/*  67:    */   private static synchronized Map<SessionFactory, Session> sessionMap(boolean createMap)
/*  68:    */   {
/*  69:131 */     Map<SessionFactory, Session> sessionMap = (Map)context.get();
/*  70:132 */     if ((sessionMap == null) && (createMap))
/*  71:    */     {
/*  72:133 */       sessionMap = new HashMap();
/*  73:134 */       context.set(sessionMap);
/*  74:    */     }
/*  75:136 */     return sessionMap;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static synchronized void doCleanup()
/*  79:    */   {
/*  80:140 */     Map<SessionFactory, Session> sessionMap = sessionMap(false);
/*  81:141 */     if ((sessionMap != null) && 
/*  82:142 */       (sessionMap.isEmpty())) {
/*  83:143 */       context.set(null);
/*  84:    */     }
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.context.internal.ManagedSessionContext
 * JD-Core Version:    0.7.0.1
 */