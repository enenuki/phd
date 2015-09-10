/*   1:    */ package org.apache.commons.logging;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Set;
/*   6:    */ import org.apache.commons.logging.impl.NoOpLog;
/*   7:    */ 
/*   8:    */ /**
/*   9:    */  * @deprecated
/*  10:    */  */
/*  11:    */ public class LogSource
/*  12:    */ {
/*  13: 62 */   protected static Hashtable logs = new Hashtable();
/*  14: 65 */   protected static boolean log4jIsAvailable = false;
/*  15: 68 */   protected static boolean jdk14IsAvailable = false;
/*  16: 71 */   protected static Constructor logImplctor = null;
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22: 80 */       if (null != Class.forName("org.apache.log4j.Logger")) {
/*  23: 81 */         log4jIsAvailable = true;
/*  24:    */       } else {
/*  25: 83 */         log4jIsAvailable = false;
/*  26:    */       }
/*  27:    */     }
/*  28:    */     catch (Throwable t)
/*  29:    */     {
/*  30: 86 */       log4jIsAvailable = false;
/*  31:    */     }
/*  32:    */     try
/*  33:    */     {
/*  34: 91 */       if ((null != Class.forName("java.util.logging.Logger")) && (null != Class.forName("org.apache.commons.logging.impl.Jdk14Logger"))) {
/*  35: 93 */         jdk14IsAvailable = true;
/*  36:    */       } else {
/*  37: 95 */         jdk14IsAvailable = false;
/*  38:    */       }
/*  39:    */     }
/*  40:    */     catch (Throwable t)
/*  41:    */     {
/*  42: 98 */       jdk14IsAvailable = false;
/*  43:    */     }
/*  44:102 */     String name = null;
/*  45:    */     try
/*  46:    */     {
/*  47:104 */       name = System.getProperty("org.apache.commons.logging.log");
/*  48:105 */       if (name == null) {
/*  49:106 */         name = System.getProperty("org.apache.commons.logging.Log");
/*  50:    */       }
/*  51:    */     }
/*  52:    */     catch (Throwable t) {}
/*  53:110 */     if (name != null) {
/*  54:    */       try
/*  55:    */       {
/*  56:112 */         setLogImplementation(name);
/*  57:    */       }
/*  58:    */       catch (Throwable t)
/*  59:    */       {
/*  60:    */         try
/*  61:    */         {
/*  62:115 */           setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
/*  63:    */         }
/*  64:    */         catch (Throwable u) {}
/*  65:    */       }
/*  66:    */     } else {
/*  67:    */       try
/*  68:    */       {
/*  69:123 */         if (log4jIsAvailable) {
/*  70:124 */           setLogImplementation("org.apache.commons.logging.impl.Log4JLogger");
/*  71:126 */         } else if (jdk14IsAvailable) {
/*  72:127 */           setLogImplementation("org.apache.commons.logging.impl.Jdk14Logger");
/*  73:    */         } else {
/*  74:130 */           setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
/*  75:    */         }
/*  76:    */       }
/*  77:    */       catch (Throwable t)
/*  78:    */       {
/*  79:    */         try
/*  80:    */         {
/*  81:135 */           setLogImplementation("org.apache.commons.logging.impl.NoOpLog");
/*  82:    */         }
/*  83:    */         catch (Throwable u) {}
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void setLogImplementation(String classname)
/*  89:    */     throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException, ClassNotFoundException
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:169 */       Class logclass = Class.forName(classname);
/*  94:170 */       Class[] argtypes = new Class[1];
/*  95:171 */       argtypes[0] = "".getClass();
/*  96:172 */       logImplctor = logclass.getConstructor(argtypes);
/*  97:    */     }
/*  98:    */     catch (Throwable t)
/*  99:    */     {
/* 100:174 */       logImplctor = null;
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void setLogImplementation(Class logclass)
/* 105:    */     throws LinkageError, ExceptionInInitializerError, NoSuchMethodException, SecurityException
/* 106:    */   {
/* 107:188 */     Class[] argtypes = new Class[1];
/* 108:189 */     argtypes[0] = "".getClass();
/* 109:190 */     logImplctor = logclass.getConstructor(argtypes);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static Log getInstance(String name)
/* 113:    */   {
/* 114:196 */     Log log = (Log)logs.get(name);
/* 115:197 */     if (null == log)
/* 116:    */     {
/* 117:198 */       log = makeNewLogInstance(name);
/* 118:199 */       logs.put(name, log);
/* 119:    */     }
/* 120:201 */     return log;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static Log getInstance(Class clazz)
/* 124:    */   {
/* 125:207 */     return getInstance(clazz.getName());
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static Log makeNewLogInstance(String name)
/* 129:    */   {
/* 130:237 */     Log log = null;
/* 131:    */     try
/* 132:    */     {
/* 133:239 */       Object[] args = new Object[1];
/* 134:240 */       args[0] = name;
/* 135:241 */       log = (Log)logImplctor.newInstance(args);
/* 136:    */     }
/* 137:    */     catch (Throwable t)
/* 138:    */     {
/* 139:243 */       log = null;
/* 140:    */     }
/* 141:245 */     if (null == log) {
/* 142:246 */       log = new NoOpLog(name);
/* 143:    */     }
/* 144:248 */     return log;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static String[] getLogNames()
/* 148:    */   {
/* 149:258 */     return (String[])logs.keySet().toArray(new String[logs.size()]);
/* 150:    */   }
/* 151:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.logging.LogSource
 * JD-Core Version:    0.7.0.1
 */