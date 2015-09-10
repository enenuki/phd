/*  1:   */ package javassist.util.proxy;
/*  2:   */ 
/*  3:   */ import java.io.InvalidClassException;
/*  4:   */ import java.io.InvalidObjectException;
/*  5:   */ import java.io.ObjectStreamException;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import java.security.AccessController;
/*  8:   */ import java.security.PrivilegedActionException;
/*  9:   */ import java.security.PrivilegedExceptionAction;
/* 10:   */ 
/* 11:   */ class SerializedProxy
/* 12:   */   implements Serializable
/* 13:   */ {
/* 14:   */   private String superClass;
/* 15:   */   private String[] interfaces;
/* 16:   */   private byte[] filterSignature;
/* 17:   */   private MethodHandler handler;
/* 18:   */   
/* 19:   */   SerializedProxy(Class proxy, byte[] sig, MethodHandler h)
/* 20:   */   {
/* 21:38 */     this.filterSignature = sig;
/* 22:39 */     this.handler = h;
/* 23:40 */     this.superClass = proxy.getSuperclass().getName();
/* 24:41 */     Class[] infs = proxy.getInterfaces();
/* 25:42 */     int n = infs.length;
/* 26:43 */     this.interfaces = new String[n - 1];
/* 27:44 */     String setterInf = ProxyObject.class.getName();
/* 28:45 */     for (int i = 0; i < n; i++)
/* 29:   */     {
/* 30:46 */       String name = infs[i].getName();
/* 31:47 */       if (!name.equals(setterInf)) {
/* 32:48 */         this.interfaces[i] = name;
/* 33:   */       }
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected Class loadClass(final String className)
/* 38:   */     throws ClassNotFoundException
/* 39:   */   {
/* 40:   */     try
/* 41:   */     {
/* 42:61 */       (Class)AccessController.doPrivileged(new PrivilegedExceptionAction()
/* 43:   */       {
/* 44:   */         private final String val$className;
/* 45:   */         
/* 46:   */         public Object run()
/* 47:   */           throws Exception
/* 48:   */         {
/* 49:63 */           ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 50:64 */           return Class.forName(className, true, cl);
/* 51:   */         }
/* 52:   */       });
/* 53:   */     }
/* 54:   */     catch (PrivilegedActionException pae)
/* 55:   */     {
/* 56:69 */       throw new RuntimeException("cannot load the class: " + className, pae.getException());
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   Object readResolve()
/* 61:   */     throws ObjectStreamException
/* 62:   */   {
/* 63:   */     try
/* 64:   */     {
/* 65:75 */       int n = this.interfaces.length;
/* 66:76 */       Class[] infs = new Class[n];
/* 67:77 */       for (int i = 0; i < n; i++) {
/* 68:78 */         infs[i] = loadClass(this.interfaces[i]);
/* 69:   */       }
/* 70:80 */       ProxyFactory f = new ProxyFactory();
/* 71:81 */       f.setSuperclass(loadClass(this.superClass));
/* 72:82 */       f.setInterfaces(infs);
/* 73:83 */       ProxyObject proxy = (ProxyObject)f.createClass(this.filterSignature).newInstance();
/* 74:84 */       proxy.setHandler(this.handler);
/* 75:85 */       return proxy;
/* 76:   */     }
/* 77:   */     catch (ClassNotFoundException e)
/* 78:   */     {
/* 79:88 */       throw new InvalidClassException(e.getMessage());
/* 80:   */     }
/* 81:   */     catch (InstantiationException e2)
/* 82:   */     {
/* 83:91 */       throw new InvalidObjectException(e2.getMessage());
/* 84:   */     }
/* 85:   */     catch (IllegalAccessException e3)
/* 86:   */     {
/* 87:94 */       throw new InvalidClassException(e3.getMessage());
/* 88:   */     }
/* 89:   */   }
/* 90:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.SerializedProxy
 * JD-Core Version:    0.7.0.1
 */