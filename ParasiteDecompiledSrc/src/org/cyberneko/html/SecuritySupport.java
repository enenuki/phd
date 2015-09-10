/*   1:    */ package org.cyberneko.html;
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
/*  14: 41 */     SecuritySupport ss = null;
/*  15:    */     try
/*  16:    */     {
/*  17: 43 */       Class c = Class.forName("java.security.AccessController");
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
/*  35: 61 */       ss = new SecuritySupport12();
/*  36:    */     }
/*  37:    */     catch (Exception ex) {}finally
/*  38:    */     {
/*  39: 65 */       if (ss == null) {
/*  40: 66 */         ss = new SecuritySupport();
/*  41:    */       }
/*  42: 67 */       securitySupport = ss;
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   static SecuritySupport getInstance()
/*  47:    */   {
/*  48: 76 */     return (SecuritySupport)securitySupport;
/*  49:    */   }
/*  50:    */   
/*  51:    */   ClassLoader getContextClassLoader()
/*  52:    */   {
/*  53: 80 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   ClassLoader getSystemClassLoader()
/*  57:    */   {
/*  58: 84 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   ClassLoader getParentClassLoader(ClassLoader cl)
/*  62:    */   {
/*  63: 88 */     return null;
/*  64:    */   }
/*  65:    */   
/*  66:    */   String getSystemProperty(String propName)
/*  67:    */   {
/*  68: 92 */     return System.getProperty(propName);
/*  69:    */   }
/*  70:    */   
/*  71:    */   FileInputStream getFileInputStream(File file)
/*  72:    */     throws FileNotFoundException
/*  73:    */   {
/*  74: 98 */     return new FileInputStream(file);
/*  75:    */   }
/*  76:    */   
/*  77:    */   InputStream getResourceAsStream(ClassLoader cl, String name)
/*  78:    */   {
/*  79:    */     InputStream ris;
/*  80:    */     InputStream ris;
/*  81:103 */     if (cl == null) {
/*  82:104 */       ris = ClassLoader.getSystemResourceAsStream(name);
/*  83:    */     } else {
/*  84:106 */       ris = cl.getResourceAsStream(name);
/*  85:    */     }
/*  86:108 */     return ris;
/*  87:    */   }
/*  88:    */   
/*  89:    */   boolean getFileExists(File f)
/*  90:    */   {
/*  91:112 */     return f.exists();
/*  92:    */   }
/*  93:    */   
/*  94:    */   long getLastModified(File f)
/*  95:    */   {
/*  96:116 */     return f.lastModified();
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.SecuritySupport
 * JD-Core Version:    0.7.0.1
 */