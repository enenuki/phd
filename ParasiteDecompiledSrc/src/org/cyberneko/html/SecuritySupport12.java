/*   1:    */ package org.cyberneko.html;
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
/*  17: 38 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  18:    */     {
/*  19:    */       public Object run()
/*  20:    */       {
/*  21: 41 */         ClassLoader cl = null;
/*  22:    */         try
/*  23:    */         {
/*  24: 43 */           cl = Thread.currentThread().getContextClassLoader();
/*  25:    */         }
/*  26:    */         catch (SecurityException ex) {}
/*  27: 45 */         return cl;
/*  28:    */       }
/*  29:    */     });
/*  30:    */   }
/*  31:    */   
/*  32:    */   ClassLoader getSystemClassLoader()
/*  33:    */   {
/*  34: 51 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  35:    */     {
/*  36:    */       public Object run()
/*  37:    */       {
/*  38: 54 */         ClassLoader cl = null;
/*  39:    */         try
/*  40:    */         {
/*  41: 56 */           cl = ClassLoader.getSystemClassLoader();
/*  42:    */         }
/*  43:    */         catch (SecurityException ex) {}
/*  44: 58 */         return cl;
/*  45:    */       }
/*  46:    */     });
/*  47:    */   }
/*  48:    */   
/*  49:    */   ClassLoader getParentClassLoader(ClassLoader cl)
/*  50:    */   {
/*  51: 64 */     (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  52:    */     {
/*  53:    */       private final ClassLoader val$cl;
/*  54:    */       
/*  55:    */       public Object run()
/*  56:    */       {
/*  57: 67 */         ClassLoader parent = null;
/*  58:    */         try
/*  59:    */         {
/*  60: 69 */           parent = this.val$cl.getParent();
/*  61:    */         }
/*  62:    */         catch (SecurityException ex) {}
/*  63: 74 */         return parent == this.val$cl ? null : parent;
/*  64:    */       }
/*  65:    */     });
/*  66:    */   }
/*  67:    */   
/*  68:    */   String getSystemProperty(String propName)
/*  69:    */   {
/*  70: 80 */     (String)AccessController.doPrivileged(new PrivilegedAction()
/*  71:    */     {
/*  72:    */       private final String val$propName;
/*  73:    */       
/*  74:    */       public Object run()
/*  75:    */       {
/*  76: 83 */         return System.getProperty(this.val$propName);
/*  77:    */       }
/*  78:    */     });
/*  79:    */   }
/*  80:    */   
/*  81:    */   FileInputStream getFileInputStream(File file)
/*  82:    */     throws FileNotFoundException
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86: 92 */       (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*  87:    */       {
/*  88:    */         private final File val$file;
/*  89:    */         
/*  90:    */         public Object run()
/*  91:    */           throws FileNotFoundException
/*  92:    */         {
/*  93: 95 */           return new FileInputStream(this.val$file);
/*  94:    */         }
/*  95:    */       });
/*  96:    */     }
/*  97:    */     catch (PrivilegedActionException e)
/*  98:    */     {
/*  99: 99 */       throw ((FileNotFoundException)e.getException());
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   InputStream getResourceAsStream(ClassLoader cl, String name)
/* 104:    */   {
/* 105:106 */     (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/* 106:    */     {
/* 107:    */       private final ClassLoader val$cl;
/* 108:    */       private final String val$name;
/* 109:    */       
/* 110:    */       public Object run()
/* 111:    */       {
/* 112:    */         InputStream ris;
/* 113:    */         InputStream ris;
/* 114:110 */         if (this.val$cl == null) {
/* 115:111 */           ris = ClassLoader.getSystemResourceAsStream(this.val$name);
/* 116:    */         } else {
/* 117:113 */           ris = this.val$cl.getResourceAsStream(this.val$name);
/* 118:    */         }
/* 119:115 */         return ris;
/* 120:    */       }
/* 121:    */     });
/* 122:    */   }
/* 123:    */   
/* 124:    */   boolean getFileExists(File f)
/* 125:    */   {
/* 126:121 */     ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/* 127:    */     {
/* 128:    */       private final File val$f;
/* 129:    */       
/* 130:    */       public Object run()
/* 131:    */       {
/* 132:124 */         return new Boolean(this.val$f.exists());
/* 133:    */       }
/* 134:    */     })).booleanValue();
/* 135:    */   }
/* 136:    */   
/* 137:    */   long getLastModified(File f)
/* 138:    */   {
/* 139:130 */     ((Long)AccessController.doPrivileged(new PrivilegedAction()
/* 140:    */     {
/* 141:    */       private final File val$f;
/* 142:    */       
/* 143:    */       public Object run()
/* 144:    */       {
/* 145:133 */         return new Long(this.val$f.lastModified());
/* 146:    */       }
/* 147:    */     })).longValue();
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.SecuritySupport12
 * JD-Core Version:    0.7.0.1
 */