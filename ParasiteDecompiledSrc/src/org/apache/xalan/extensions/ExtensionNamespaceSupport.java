/*   1:    */ package org.apache.xalan.extensions;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ 
/*   6:    */ public class ExtensionNamespaceSupport
/*   7:    */ {
/*   8: 36 */   String m_namespace = null;
/*   9: 37 */   String m_handlerClass = null;
/*  10: 38 */   Class[] m_sig = null;
/*  11: 39 */   Object[] m_args = null;
/*  12:    */   
/*  13:    */   public ExtensionNamespaceSupport(String namespace, String handlerClass, Object[] constructorArgs)
/*  14:    */   {
/*  15: 45 */     this.m_namespace = namespace;
/*  16: 46 */     this.m_handlerClass = handlerClass;
/*  17: 47 */     this.m_args = constructorArgs;
/*  18:    */     
/*  19: 49 */     this.m_sig = new Class[this.m_args.length];
/*  20: 50 */     for (int i = 0; i < this.m_args.length; i++) {
/*  21: 52 */       if (this.m_args[i] != null)
/*  22:    */       {
/*  23: 53 */         this.m_sig[i] = this.m_args[i].getClass();
/*  24:    */       }
/*  25:    */       else
/*  26:    */       {
/*  27: 56 */         this.m_sig = null;
/*  28: 57 */         break;
/*  29:    */       }
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getNamespace()
/*  34:    */   {
/*  35: 64 */     return this.m_namespace;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ExtensionHandler launch()
/*  39:    */     throws TransformerException
/*  40:    */   {
/*  41: 73 */     ExtensionHandler handler = null;
/*  42:    */     try
/*  43:    */     {
/*  44: 76 */       Class cl = ExtensionHandler.getClassForName(this.m_handlerClass);
/*  45: 77 */       Constructor con = null;
/*  46: 79 */       if (this.m_sig != null)
/*  47:    */       {
/*  48: 80 */         con = cl.getConstructor(this.m_sig);
/*  49:    */       }
/*  50:    */       else
/*  51:    */       {
/*  52: 83 */         Constructor[] cons = cl.getConstructors();
/*  53: 84 */         for (int i = 0; i < cons.length; i++) {
/*  54: 86 */           if (cons[i].getParameterTypes().length == this.m_args.length)
/*  55:    */           {
/*  56: 88 */             con = cons[i];
/*  57: 89 */             break;
/*  58:    */           }
/*  59:    */         }
/*  60:    */       }
/*  61: 94 */       if (con != null) {
/*  62: 95 */         handler = (ExtensionHandler)con.newInstance(this.m_args);
/*  63:    */       } else {
/*  64: 97 */         throw new TransformerException("ExtensionHandler constructor not found");
/*  65:    */       }
/*  66:    */     }
/*  67:    */     catch (Exception e)
/*  68:    */     {
/*  69:101 */       throw new TransformerException(e);
/*  70:    */     }
/*  71:103 */     return handler;
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.extensions.ExtensionNamespaceSupport
 * JD-Core Version:    0.7.0.1
 */