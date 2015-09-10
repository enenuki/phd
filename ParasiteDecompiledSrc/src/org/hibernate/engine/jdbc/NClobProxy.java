/*  1:   */ package org.hibernate.engine.jdbc;
/*  2:   */ 
/*  3:   */ import java.io.Reader;
/*  4:   */ import java.lang.reflect.Proxy;
/*  5:   */ import java.sql.NClob;
/*  6:   */ 
/*  7:   */ public class NClobProxy
/*  8:   */   extends ClobProxy
/*  9:   */ {
/* 10:40 */   public static final Class[] PROXY_INTERFACES = { NClob.class, NClobImplementer.class };
/* 11:   */   
/* 12:   */   protected NClobProxy(String string)
/* 13:   */   {
/* 14:43 */     super(string);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected NClobProxy(Reader reader, long length)
/* 18:   */   {
/* 19:47 */     super(reader, length);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static NClob generateProxy(String string)
/* 23:   */   {
/* 24:58 */     return (NClob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new ClobProxy(string));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static NClob generateProxy(Reader reader, long length)
/* 28:   */   {
/* 29:74 */     return (NClob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new ClobProxy(reader, length));
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected static ClassLoader getProxyClassLoader()
/* 33:   */   {
/* 34:88 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 35:89 */     if (cl == null) {
/* 36:90 */       cl = NClobImplementer.class.getClassLoader();
/* 37:   */     }
/* 38:92 */     return cl;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.NClobProxy
 * JD-Core Version:    0.7.0.1
 */