/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.security.CodeSource;
/*   7:    */ import java.security.ProtectionDomain;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.apache.log4j.spi.ThrowableRenderer;
/*  11:    */ 
/*  12:    */ public final class EnhancedThrowableRenderer
/*  13:    */   implements ThrowableRenderer
/*  14:    */ {
/*  15:    */   private Method getStackTraceMethod;
/*  16:    */   private Method getClassNameMethod;
/*  17:    */   
/*  18:    */   public EnhancedThrowableRenderer()
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22: 51 */       Class[] noArgs = null;
/*  23: 52 */       this.getStackTraceMethod = Throwable.class.getMethod("getStackTrace", noArgs);
/*  24: 53 */       Class ste = Class.forName("java.lang.StackTraceElement");
/*  25: 54 */       this.getClassNameMethod = ste.getMethod("getClassName", noArgs);
/*  26:    */     }
/*  27:    */     catch (Exception ex) {}
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String[] doRender(Throwable throwable)
/*  31:    */   {
/*  32: 63 */     if (this.getStackTraceMethod != null) {
/*  33:    */       try
/*  34:    */       {
/*  35: 65 */         Object[] noArgs = null;
/*  36: 66 */         Object[] elements = (Object[])this.getStackTraceMethod.invoke(throwable, noArgs);
/*  37: 67 */         String[] lines = new String[elements.length + 1];
/*  38: 68 */         lines[0] = throwable.toString();
/*  39: 69 */         Map classMap = new HashMap();
/*  40: 70 */         for (int i = 0; i < elements.length; i++) {
/*  41: 71 */           lines[(i + 1)] = formatElement(elements[i], classMap);
/*  42:    */         }
/*  43: 73 */         return lines;
/*  44:    */       }
/*  45:    */       catch (Exception ex) {}
/*  46:    */     }
/*  47: 77 */     return DefaultThrowableRenderer.render(throwable);
/*  48:    */   }
/*  49:    */   
/*  50:    */   private String formatElement(Object element, Map classMap)
/*  51:    */   {
/*  52: 87 */     StringBuffer buf = new StringBuffer("\tat ");
/*  53: 88 */     buf.append(element);
/*  54:    */     try
/*  55:    */     {
/*  56: 90 */       String className = this.getClassNameMethod.invoke(element, (Object[])null).toString();
/*  57: 91 */       Object classDetails = classMap.get(className);
/*  58: 92 */       if (classDetails != null)
/*  59:    */       {
/*  60: 93 */         buf.append(classDetails);
/*  61:    */       }
/*  62:    */       else
/*  63:    */       {
/*  64: 95 */         Class cls = findClass(className);
/*  65: 96 */         int detailStart = buf.length();
/*  66: 97 */         buf.append('[');
/*  67:    */         try
/*  68:    */         {
/*  69: 99 */           CodeSource source = cls.getProtectionDomain().getCodeSource();
/*  70:100 */           if (source != null)
/*  71:    */           {
/*  72:101 */             URL locationURL = source.getLocation();
/*  73:102 */             if (locationURL != null) {
/*  74:106 */               if ("file".equals(locationURL.getProtocol()))
/*  75:    */               {
/*  76:107 */                 String path = locationURL.getPath();
/*  77:108 */                 if (path != null)
/*  78:    */                 {
/*  79:112 */                   int lastSlash = path.lastIndexOf('/');
/*  80:113 */                   int lastBack = path.lastIndexOf(File.separatorChar);
/*  81:114 */                   if (lastBack > lastSlash) {
/*  82:115 */                     lastSlash = lastBack;
/*  83:    */                   }
/*  84:121 */                   if ((lastSlash <= 0) || (lastSlash == path.length() - 1)) {
/*  85:122 */                     buf.append(locationURL);
/*  86:    */                   } else {
/*  87:124 */                     buf.append(path.substring(lastSlash + 1));
/*  88:    */                   }
/*  89:    */                 }
/*  90:    */               }
/*  91:    */               else
/*  92:    */               {
/*  93:128 */                 buf.append(locationURL);
/*  94:    */               }
/*  95:    */             }
/*  96:    */           }
/*  97:    */         }
/*  98:    */         catch (SecurityException ex) {}
/*  99:134 */         buf.append(':');
/* 100:135 */         Package pkg = cls.getPackage();
/* 101:136 */         if (pkg != null)
/* 102:    */         {
/* 103:137 */           String implVersion = pkg.getImplementationVersion();
/* 104:138 */           if (implVersion != null) {
/* 105:139 */             buf.append(implVersion);
/* 106:    */           }
/* 107:    */         }
/* 108:142 */         buf.append(']');
/* 109:143 */         classMap.put(className, buf.substring(detailStart));
/* 110:    */       }
/* 111:    */     }
/* 112:    */     catch (Exception ex) {}
/* 113:147 */     return buf.toString();
/* 114:    */   }
/* 115:    */   
/* 116:    */   private Class findClass(String className)
/* 117:    */     throws ClassNotFoundException
/* 118:    */   {
/* 119:    */     try
/* 120:    */     {
/* 121:158 */       return Thread.currentThread().getContextClassLoader().loadClass(className);
/* 122:    */     }
/* 123:    */     catch (ClassNotFoundException e)
/* 124:    */     {
/* 125:    */       try
/* 126:    */       {
/* 127:161 */         return Class.forName(className);
/* 128:    */       }
/* 129:    */       catch (ClassNotFoundException e1) {}
/* 130:    */     }
/* 131:163 */     return getClass().getClassLoader().loadClass(className);
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.EnhancedThrowableRenderer
 * JD-Core Version:    0.7.0.1
 */