/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Proxy;
/*  4:   */ import java.sql.Clob;
/*  5:   */ 
/*  6:   */ public class SerializableNClobProxy
/*  7:   */   extends SerializableClobProxy
/*  8:   */ {
/*  9:34 */   private static final Class NCLOB_CLASS = ;
/* 10:   */   
/* 11:   */   private static Class loadNClobClassIfAvailable()
/* 12:   */   {
/* 13:   */     try
/* 14:   */     {
/* 15:38 */       return getProxyClassLoader().loadClass("java.sql.NClob");
/* 16:   */     }
/* 17:   */     catch (ClassNotFoundException e) {}
/* 18:41 */     return null;
/* 19:   */   }
/* 20:   */   
/* 21:45 */   private static final Class[] PROXY_INTERFACES = { determineNClobInterface(), WrappedClob.class };
/* 22:   */   
/* 23:   */   private static Class determineNClobInterface()
/* 24:   */   {
/* 25:50 */     return NCLOB_CLASS == null ? Clob.class : NCLOB_CLASS;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static boolean isNClob(Clob clob)
/* 29:   */   {
/* 30:54 */     return (NCLOB_CLASS != null) && (NCLOB_CLASS.isInstance(clob));
/* 31:   */   }
/* 32:   */   
/* 33:   */   protected SerializableNClobProxy(Clob clob)
/* 34:   */   {
/* 35:65 */     super(clob);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static Clob generateProxy(Clob clob)
/* 39:   */   {
/* 40:75 */     return (Clob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new SerializableNClobProxy(clob));
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static ClassLoader getProxyClassLoader()
/* 44:   */   {
/* 45:89 */     return SerializableClobProxy.getProxyClassLoader();
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.SerializableNClobProxy
 * JD-Core Version:    0.7.0.1
 */