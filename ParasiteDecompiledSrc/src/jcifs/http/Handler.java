/*   1:    */ package jcifs.http;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.HttpURLConnection;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.net.URLConnection;
/*   7:    */ import java.net.URLStreamHandler;
/*   8:    */ import java.net.URLStreamHandlerFactory;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.StringTokenizer;
/*  12:    */ 
/*  13:    */ public class Handler
/*  14:    */   extends URLStreamHandler
/*  15:    */ {
/*  16:    */   public static final int DEFAULT_HTTP_PORT = 80;
/*  17: 47 */   private static final Map PROTOCOL_HANDLERS = new HashMap();
/*  18:    */   private static final String HANDLER_PKGS_PROPERTY = "java.protocol.handler.pkgs";
/*  19: 60 */   private static final String[] JVM_VENDOR_DEFAULT_PKGS = { "sun.net.www.protocol" };
/*  20:    */   private static URLStreamHandlerFactory factory;
/*  21:    */   
/*  22:    */   public static void setURLStreamHandlerFactory(URLStreamHandlerFactory factory)
/*  23:    */   {
/*  24: 75 */     synchronized (PROTOCOL_HANDLERS)
/*  25:    */     {
/*  26: 76 */       if (factory != null) {
/*  27: 77 */         throw new IllegalStateException("URLStreamHandlerFactory already set.");
/*  28:    */       }
/*  29: 80 */       PROTOCOL_HANDLERS.clear();
/*  30: 81 */       factory = factory;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected int getDefaultPort()
/*  35:    */   {
/*  36: 91 */     return 80;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected URLConnection openConnection(URL url)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42: 95 */     url = new URL(url, url.toExternalForm(), getDefaultStreamHandler(url.getProtocol()));
/*  43:    */     
/*  44: 97 */     return new NtlmHttpURLConnection((HttpURLConnection)url.openConnection());
/*  45:    */   }
/*  46:    */   
/*  47:    */   private static URLStreamHandler getDefaultStreamHandler(String protocol)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:103 */     synchronized (PROTOCOL_HANDLERS)
/*  51:    */     {
/*  52:104 */       URLStreamHandler handler = (URLStreamHandler)PROTOCOL_HANDLERS.get(protocol);
/*  53:106 */       if (handler != null) {
/*  54:106 */         return handler;
/*  55:    */       }
/*  56:107 */       if (factory != null) {
/*  57:108 */         handler = factory.createURLStreamHandler(protocol);
/*  58:    */       }
/*  59:110 */       if (handler == null)
/*  60:    */       {
/*  61:111 */         String path = System.getProperty("java.protocol.handler.pkgs");
/*  62:112 */         StringTokenizer tokenizer = new StringTokenizer(path, "|");
/*  63:113 */         while (tokenizer.hasMoreTokens())
/*  64:    */         {
/*  65:114 */           String provider = tokenizer.nextToken().trim();
/*  66:115 */           if (!provider.equals("jcifs"))
/*  67:    */           {
/*  68:116 */             String className = provider + "." + protocol + ".Handler";
/*  69:    */             try
/*  70:    */             {
/*  71:118 */               Class handlerClass = null;
/*  72:    */               try
/*  73:    */               {
/*  74:120 */                 handlerClass = Class.forName(className);
/*  75:    */               }
/*  76:    */               catch (Exception ex) {}
/*  77:122 */               if (handlerClass == null) {
/*  78:123 */                 handlerClass = ClassLoader.getSystemClassLoader().loadClass(className);
/*  79:    */               }
/*  80:126 */               handler = (URLStreamHandler)handlerClass.newInstance();
/*  81:    */             }
/*  82:    */             catch (Exception ex) {}
/*  83:    */           }
/*  84:    */         }
/*  85:    */       }
/*  86:131 */       if (handler == null) {
/*  87:132 */         for (int i = 0; i < JVM_VENDOR_DEFAULT_PKGS.length; i++)
/*  88:    */         {
/*  89:133 */           String className = JVM_VENDOR_DEFAULT_PKGS[i] + "." + protocol + ".Handler";
/*  90:    */           try
/*  91:    */           {
/*  92:136 */             Class handlerClass = null;
/*  93:    */             try
/*  94:    */             {
/*  95:138 */               handlerClass = Class.forName(className);
/*  96:    */             }
/*  97:    */             catch (Exception ex) {}
/*  98:140 */             if (handlerClass == null) {
/*  99:141 */               handlerClass = ClassLoader.getSystemClassLoader().loadClass(className);
/* 100:    */             }
/* 101:144 */             handler = (URLStreamHandler)handlerClass.newInstance();
/* 102:    */           }
/* 103:    */           catch (Exception ex) {}
/* 104:146 */           if (handler != null) {
/* 105:    */             break;
/* 106:    */           }
/* 107:    */         }
/* 108:    */       }
/* 109:149 */       if (handler == null) {
/* 110:150 */         throw new IOException("Unable to find default handler for protocol: " + protocol);
/* 111:    */       }
/* 112:154 */       PROTOCOL_HANDLERS.put(protocol, handler);
/* 113:155 */       return handler;
/* 114:    */     }
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.http.Handler
 * JD-Core Version:    0.7.0.1
 */