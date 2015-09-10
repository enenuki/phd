/*   1:    */ package org.hibernate.engine.jdbc;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.InvocationHandler;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Proxy;
/*   8:    */ import java.sql.Blob;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ 
/*  11:    */ public class SerializableBlobProxy
/*  12:    */   implements InvocationHandler, Serializable
/*  13:    */ {
/*  14: 42 */   private static final Class[] PROXY_INTERFACES = { Blob.class, WrappedBlob.class, Serializable.class };
/*  15:    */   private final transient Blob blob;
/*  16:    */   
/*  17:    */   private SerializableBlobProxy(Blob blob)
/*  18:    */   {
/*  19: 53 */     this.blob = blob;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Blob getWrappedBlob()
/*  23:    */   {
/*  24: 57 */     if (this.blob == null) {
/*  25: 58 */       throw new IllegalStateException("Blobs may not be accessed after serialization");
/*  26:    */     }
/*  27: 61 */     return this.blob;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  31:    */     throws Throwable
/*  32:    */   {
/*  33: 69 */     if ("getWrappedBlob".equals(method.getName())) {
/*  34: 70 */       return getWrappedBlob();
/*  35:    */     }
/*  36:    */     try
/*  37:    */     {
/*  38: 73 */       return method.invoke(getWrappedBlob(), args);
/*  39:    */     }
/*  40:    */     catch (AbstractMethodError e)
/*  41:    */     {
/*  42: 76 */       throw new HibernateException("The JDBC driver does not implement the method: " + method, e);
/*  43:    */     }
/*  44:    */     catch (InvocationTargetException e)
/*  45:    */     {
/*  46: 79 */       throw e.getTargetException();
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Blob generateProxy(Blob blob)
/*  51:    */   {
/*  52: 91 */     return (Blob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new SerializableBlobProxy(blob));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static ClassLoader getProxyClassLoader()
/*  56:    */   {
/*  57:105 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  58:106 */     if (cl == null) {
/*  59:107 */       cl = WrappedBlob.class.getClassLoader();
/*  60:    */     }
/*  61:109 */     return cl;
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.SerializableBlobProxy
 * JD-Core Version:    0.7.0.1
 */