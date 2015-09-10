/*  1:   */ package javassist.util.proxy;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectOutputStream;
/*  5:   */ import java.io.ObjectStreamClass;
/*  6:   */ import java.io.OutputStream;
/*  7:   */ 
/*  8:   */ public class ProxyObjectOutputStream
/*  9:   */   extends ObjectOutputStream
/* 10:   */ {
/* 11:   */   public ProxyObjectOutputStream(OutputStream out)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:43 */     super(out);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void writeClassDescriptor(ObjectStreamClass desc)
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:47 */     Class cl = desc.forClass();
/* 21:48 */     if (ProxyFactory.isProxyClass(cl))
/* 22:   */     {
/* 23:49 */       writeBoolean(true);
/* 24:50 */       Class superClass = cl.getSuperclass();
/* 25:51 */       Class[] interfaces = cl.getInterfaces();
/* 26:52 */       byte[] signature = ProxyFactory.getFilterSignature(cl);
/* 27:53 */       String name = superClass.getName();
/* 28:54 */       writeObject(name);
/* 29:   */       
/* 30:56 */       writeInt(interfaces.length - 1);
/* 31:57 */       for (int i = 0; i < interfaces.length; i++)
/* 32:   */       {
/* 33:58 */         Class interfaze = interfaces[i];
/* 34:59 */         if (interfaze != ProxyObject.class)
/* 35:   */         {
/* 36:60 */           name = interfaces[i].getName();
/* 37:61 */           writeObject(name);
/* 38:   */         }
/* 39:   */       }
/* 40:64 */       writeInt(signature.length);
/* 41:65 */       write(signature);
/* 42:   */     }
/* 43:   */     else
/* 44:   */     {
/* 45:67 */       writeBoolean(false);
/* 46:68 */       super.writeClassDescriptor(desc);
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.ProxyObjectOutputStream
 * JD-Core Version:    0.7.0.1
 */