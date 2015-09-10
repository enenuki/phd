/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ 
/*   8:    */ class SecuritySupport
/*   9:    */ {
/*  10:    */   private static final Object securitySupport;
/*  11:    */   
/*  12:    */   static
/*  13:    */   {
/*  14: 46 */     SecuritySupport ss = null;
/*  15:    */     try
/*  16:    */     {
/*  17: 48 */       Class c = Class.forName("java.security.AccessController");
/*  18:    */       
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35: 66 */       ss = new SecuritySupport12();
/*  36:    */     }
/*  37:    */     catch (Exception ex) {}finally
/*  38:    */     {
/*  39: 70 */       if (ss == null) {
/*  40: 71 */         ss = new SecuritySupport();
/*  41:    */       }
/*  42: 72 */       securitySupport = ss;
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   static SecuritySupport getInstance()
/*  47:    */   {
/*  48: 81 */     return (SecuritySupport)securitySupport;
/*  49:    */   }
/*  50:    */   
/*  51:    */   ClassLoader getContextClassLoader()
/*  52:    */   {
/*  53: 85 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   ClassLoader getSystemClassLoader()
/*  57:    */   {
/*  58: 89 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   ClassLoader getParentClassLoader(ClassLoader cl)
/*  62:    */   {
/*  63: 93 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   String getSystemProperty(String propName)
/*  67:    */   {
/*  68: 97 */     return System.getProperty(propName);
/*  69:    */   }
/*  70:    */   
/*  71:    */   FileInputStream getFileInputStream(File file)
/*  72:    */     throws FileNotFoundException
/*  73:    */   {
/*  74:103 */     return new FileInputStream(file);
/*  75:    */   }
/*  76:    */   
/*  77:    */   InputStream getResourceAsStream(ClassLoader cl, String name)
/*  78:    */   {
/*  79:    */     InputStream ris;
/*  80:108 */     if (cl == null) {
/*  81:109 */       ris = ClassLoader.getSystemResourceAsStream(name);
/*  82:    */     } else {
/*  83:111 */       ris = cl.getResourceAsStream(name);
/*  84:    */     }
/*  85:113 */     return ris;
/*  86:    */   }
/*  87:    */   
/*  88:    */   boolean getFileExists(File f)
/*  89:    */   {
/*  90:117 */     return f.exists();
/*  91:    */   }
/*  92:    */   
/*  93:    */   long getLastModified(File f)
/*  94:    */   {
/*  95:121 */     return f.lastModified();
/*  96:    */   }
/*  97:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.SecuritySupport
 * JD-Core Version:    0.7.0.1
 */