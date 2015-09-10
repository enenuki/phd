/*  1:   */ package javassist.util.proxy;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.ObjectInputStream;
/*  6:   */ import java.io.ObjectStreamClass;
/*  7:   */ 
/*  8:   */ public class ProxyObjectInputStream
/*  9:   */   extends ObjectInputStream
/* 10:   */ {
/* 11:   */   private ClassLoader loader;
/* 12:   */   
/* 13:   */   public ProxyObjectInputStream(InputStream in)
/* 14:   */     throws IOException
/* 15:   */   {
/* 16:45 */     super(in);
/* 17:46 */     this.loader = Thread.currentThread().getContextClassLoader();
/* 18:47 */     if (this.loader == null) {
/* 19:48 */       this.loader = ClassLoader.getSystemClassLoader();
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setClassLoader(ClassLoader loader)
/* 24:   */   {
/* 25:58 */     if (loader != null) {
/* 26:59 */       this.loader = loader;
/* 27:   */     } else {
/* 28:61 */       loader = ClassLoader.getSystemClassLoader();
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected ObjectStreamClass readClassDescriptor()
/* 33:   */     throws IOException, ClassNotFoundException
/* 34:   */   {
/* 35:66 */     boolean isProxy = readBoolean();
/* 36:67 */     if (isProxy)
/* 37:   */     {
/* 38:68 */       String name = (String)readObject();
/* 39:69 */       Class superClass = this.loader.loadClass(name);
/* 40:70 */       int length = readInt();
/* 41:71 */       Class[] interfaces = new Class[length];
/* 42:72 */       for (int i = 0; i < length; i++)
/* 43:   */       {
/* 44:73 */         name = (String)readObject();
/* 45:74 */         interfaces[i] = this.loader.loadClass(name);
/* 46:   */       }
/* 47:76 */       length = readInt();
/* 48:77 */       byte[] signature = new byte[length];
/* 49:78 */       read(signature);
/* 50:79 */       ProxyFactory factory = new ProxyFactory();
/* 51:   */       
/* 52:   */ 
/* 53:82 */       factory.setUseCache(true);
/* 54:83 */       factory.setUseWriteReplace(false);
/* 55:84 */       factory.setSuperclass(superClass);
/* 56:85 */       factory.setInterfaces(interfaces);
/* 57:86 */       Class proxyClass = factory.createClass(signature);
/* 58:87 */       return ObjectStreamClass.lookup(proxyClass);
/* 59:   */     }
/* 60:89 */     return super.readClassDescriptor();
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.ProxyObjectInputStream
 * JD-Core Version:    0.7.0.1
 */