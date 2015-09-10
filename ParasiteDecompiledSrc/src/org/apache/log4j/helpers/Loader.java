/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.io.InterruptedIOException;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.net.URL;
/*   7:    */ 
/*   8:    */ public class Loader
/*   9:    */ {
/*  10:    */   static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
/*  11: 37 */   private static boolean java1 = true;
/*  12: 39 */   private static boolean ignoreTCL = false;
/*  13:    */   
/*  14:    */   static
/*  15:    */   {
/*  16: 42 */     String prop = OptionConverter.getSystemProperty("java.version", null);
/*  17: 44 */     if (prop != null)
/*  18:    */     {
/*  19: 45 */       int i = prop.indexOf('.');
/*  20: 46 */       if ((i != -1) && 
/*  21: 47 */         (prop.charAt(i + 1) != '1')) {
/*  22: 48 */         java1 = false;
/*  23:    */       }
/*  24:    */     }
/*  25: 51 */     String ignoreTCLProp = OptionConverter.getSystemProperty("log4j.ignoreTCL", null);
/*  26: 52 */     if (ignoreTCLProp != null) {
/*  27: 53 */       ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   /**
/*  32:    */    * @deprecated
/*  33:    */    */
/*  34:    */   public static URL getResource(String resource, Class clazz)
/*  35:    */   {
/*  36: 65 */     return getResource(resource);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static URL getResource(String resource)
/*  40:    */   {
/*  41: 88 */     ClassLoader classLoader = null;
/*  42: 89 */     URL url = null;
/*  43:    */     try
/*  44:    */     {
/*  45: 92 */       if ((!java1) && (!ignoreTCL))
/*  46:    */       {
/*  47: 93 */         classLoader = getTCL();
/*  48: 94 */         if (classLoader != null)
/*  49:    */         {
/*  50: 95 */           LogLog.debug("Trying to find [" + resource + "] using context classloader " + classLoader + ".");
/*  51:    */           
/*  52: 97 */           url = classLoader.getResource(resource);
/*  53: 98 */           if (url != null) {
/*  54: 99 */             return url;
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58:106 */       classLoader = Loader.class.getClassLoader();
/*  59:107 */       if (classLoader != null)
/*  60:    */       {
/*  61:108 */         LogLog.debug("Trying to find [" + resource + "] using " + classLoader + " class loader.");
/*  62:    */         
/*  63:110 */         url = classLoader.getResource(resource);
/*  64:111 */         if (url != null) {
/*  65:112 */           return url;
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (IllegalAccessException t)
/*  70:    */     {
/*  71:116 */       LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
/*  72:    */     }
/*  73:    */     catch (InvocationTargetException t)
/*  74:    */     {
/*  75:118 */       if (((t.getTargetException() instanceof InterruptedException)) || ((t.getTargetException() instanceof InterruptedIOException))) {
/*  76:120 */         Thread.currentThread().interrupt();
/*  77:    */       }
/*  78:122 */       LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
/*  79:    */     }
/*  80:    */     catch (Throwable t)
/*  81:    */     {
/*  82:127 */       LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
/*  83:    */     }
/*  84:134 */     LogLog.debug("Trying to find [" + resource + "] using ClassLoader.getSystemResource().");
/*  85:    */     
/*  86:136 */     return ClassLoader.getSystemResource(resource);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static boolean isJava1()
/*  90:    */   {
/*  91:145 */     return java1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static ClassLoader getTCL()
/*  95:    */     throws IllegalAccessException, InvocationTargetException
/*  96:    */   {
/*  97:158 */     Method method = null;
/*  98:    */     try
/*  99:    */     {
/* 100:160 */       method = Thread.class.getMethod("getContextClassLoader", null);
/* 101:    */     }
/* 102:    */     catch (NoSuchMethodException e)
/* 103:    */     {
/* 104:163 */       return null;
/* 105:    */     }
/* 106:166 */     return (ClassLoader)method.invoke(Thread.currentThread(), null);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static Class loadClass(String clazz)
/* 110:    */     throws ClassNotFoundException
/* 111:    */   {
/* 112:181 */     if ((java1) || (ignoreTCL)) {
/* 113:182 */       return Class.forName(clazz);
/* 114:    */     }
/* 115:    */     try
/* 116:    */     {
/* 117:185 */       return getTCL().loadClass(clazz);
/* 118:    */     }
/* 119:    */     catch (InvocationTargetException e)
/* 120:    */     {
/* 121:191 */       if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 122:193 */         Thread.currentThread().interrupt();
/* 123:    */       }
/* 124:    */     }
/* 125:    */     catch (Throwable t) {}
/* 126:198 */     return Class.forName(clazz);
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.Loader
 * JD-Core Version:    0.7.0.1
 */