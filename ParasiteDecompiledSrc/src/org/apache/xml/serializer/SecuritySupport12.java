/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.security.AccessController;
/*   8:    */ import java.security.PrivilegedAction;
/*   9:    */ import java.security.PrivilegedActionException;
/*  10:    */ import java.security.PrivilegedExceptionAction;
/*  11:    */ 
/*  12:    */ class SecuritySupport12
/*  13:    */   extends SecuritySupport
/*  14:    */ {
/*  15:    */   ClassLoader getContextClassLoader()
/*  16:    */   {
/*  17: 43 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  18:    */     {
/*  19:    */       public Object run()
/*  20:    */       {
/*  21: 46 */         ClassLoader cl = null;
/*  22:    */         try
/*  23:    */         {
/*  24: 48 */           cl = Thread.currentThread().getContextClassLoader();
/*  25:    */         }
/*  26:    */         catch (SecurityException ex) {}
/*  27: 50 */         return cl;
/*  28:    */       }
/*  29:    */     });
/*  30:    */   }
/*  31:    */   
/*  32:    */   ClassLoader getSystemClassLoader()
/*  33:    */   {
/*  34: 56 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  35:    */     {
/*  36:    */       public Object run()
/*  37:    */       {
/*  38: 59 */         ClassLoader cl = null;
/*  39:    */         try
/*  40:    */         {
/*  41: 61 */           cl = ClassLoader.getSystemClassLoader();
/*  42:    */         }
/*  43:    */         catch (SecurityException ex) {}
/*  44: 63 */         return cl;
/*  45:    */       }
/*  46:    */     });
/*  47:    */   }
/*  48:    */   
/*  49:    */   ClassLoader getParentClassLoader(ClassLoader cl)
/*  50:    */   {
/*  51: 69 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  52:    */     {
/*  53:    */       private final ClassLoader val$cl;
/*  54:    */       
/*  55:    */       public Object run()
/*  56:    */       {
/*  57: 72 */         ClassLoader parent = null;
/*  58:    */         try
/*  59:    */         {
/*  60: 74 */           parent = this.val$cl.getParent();
/*  61:    */         }
/*  62:    */         catch (SecurityException ex) {}
/*  63: 79 */         return parent == this.val$cl ? null : parent;
/*  64:    */       }
/*  65:    */     });
/*  66:    */   }
/*  67:    */   
/*  68:    */   String getSystemProperty(String propName)
/*  69:    */   {
/*  70: 85 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*  71:    */     {
/*  72:    */       private final String val$propName;
/*  73:    */       
/*  74:    */       public Object run()
/*  75:    */       {
/*  76: 88 */         return System.getProperty(this.val$propName);
/*  77:    */       }
/*  78:    */     });
/*  79:    */   }
/*  80:    */   
/*  81:    */   FileInputStream getFileInputStream(File file)
/*  82:    */     throws FileNotFoundException
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86: 97 */       (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*  87:    */       {
/*  88:    */         private final File val$file;
/*  89:    */         
/*  90:    */         public Object run()
/*  91:    */           throws FileNotFoundException
/*  92:    */         {
/*  93:100 */           return new FileInputStream(this.val$file);
/*  94:    */         }
/*  95:    */       });
/*  96:    */     }
/*  97:    */     catch (PrivilegedActionException e)
/*  98:    */     {
/*  99:104 */       throw ((FileNotFoundException)e.getException());
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   InputStream getResourceAsStream(ClassLoader cl, String name)
/* 104:    */   {
/* 105:111 */     (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/* 106:    */     {
/* 107:    */       private final ClassLoader val$cl;
/* 108:    */       private final String val$name;
/* 109:    */       
/* 110:    */       public Object run()
/* 111:    */       {
/* 112:    */         InputStream ris;
/* 113:115 */         if (this.val$cl == null) {
/* 114:116 */           ris = ClassLoader.getSystemResourceAsStream(this.val$name);
/* 115:    */         } else {
/* 116:118 */           ris = this.val$cl.getResourceAsStream(this.val$name);
/* 117:    */         }
/* 118:120 */         return ris;
/* 119:    */       }
/* 120:    */     });
/* 121:    */   }
/* 122:    */   
/* 123:    */   boolean getFileExists(File f)
/* 124:    */   {
/* 125:126 */     ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/* 126:    */     {
/* 127:    */       private final File val$f;
/* 128:    */       
/* 129:    */       public Object run()
/* 130:    */       {
/* 131:129 */         return new Boolean(this.val$f.exists());
/* 132:    */       }
/* 133:    */     })).booleanValue();
/* 134:    */   }
/* 135:    */   
/* 136:    */   long getLastModified(File f)
/* 137:    */   {
/* 138:135 */     ((Long)AccessController.doPrivileged(new PrivilegedAction()
/* 139:    */     {
/* 140:    */       private final File val$f;
/* 141:    */       
/* 142:    */       public Object run()
/* 143:    */       {
/* 144:138 */         return new Long(this.val$f.lastModified());
/* 145:    */       }
/* 146:    */     })).longValue();
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.SecuritySupport12
 * JD-Core Version:    0.7.0.1
 */