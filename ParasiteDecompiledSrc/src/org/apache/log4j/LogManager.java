/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.PrintWriter;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import org.apache.log4j.helpers.Loader;
/*   9:    */ import org.apache.log4j.helpers.LogLog;
/*  10:    */ import org.apache.log4j.helpers.OptionConverter;
/*  11:    */ import org.apache.log4j.spi.DefaultRepositorySelector;
/*  12:    */ import org.apache.log4j.spi.LoggerFactory;
/*  13:    */ import org.apache.log4j.spi.LoggerRepository;
/*  14:    */ import org.apache.log4j.spi.NOPLoggerRepository;
/*  15:    */ import org.apache.log4j.spi.RepositorySelector;
/*  16:    */ import org.apache.log4j.spi.RootLogger;
/*  17:    */ 
/*  18:    */ public class LogManager
/*  19:    */ {
/*  20:    */   /**
/*  21:    */    * @deprecated
/*  22:    */    */
/*  23:    */   public static final String DEFAULT_CONFIGURATION_FILE = "log4j.properties";
/*  24:    */   static final String DEFAULT_XML_CONFIGURATION_FILE = "log4j.xml";
/*  25:    */   /**
/*  26:    */    * @deprecated
/*  27:    */    */
/*  28:    */   public static final String DEFAULT_CONFIGURATION_KEY = "log4j.configuration";
/*  29:    */   /**
/*  30:    */    * @deprecated
/*  31:    */    */
/*  32:    */   public static final String CONFIGURATOR_CLASS_KEY = "log4j.configuratorClass";
/*  33:    */   /**
/*  34:    */    * @deprecated
/*  35:    */    */
/*  36:    */   public static final String DEFAULT_INIT_OVERRIDE_KEY = "log4j.defaultInitOverride";
/*  37: 77 */   private static Object guard = null;
/*  38:    */   private static RepositorySelector repositorySelector;
/*  39:    */   
/*  40:    */   static
/*  41:    */   {
/*  42: 82 */     Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
/*  43: 83 */     repositorySelector = new DefaultRepositorySelector(h);
/*  44:    */     
/*  45:    */ 
/*  46: 86 */     String override = OptionConverter.getSystemProperty("log4j.defaultInitOverride", null);
/*  47: 91 */     if ((override == null) || ("false".equalsIgnoreCase(override)))
/*  48:    */     {
/*  49: 93 */       String configurationOptionStr = OptionConverter.getSystemProperty("log4j.configuration", null);
/*  50:    */       
/*  51:    */ 
/*  52:    */ 
/*  53: 97 */       String configuratorClassName = OptionConverter.getSystemProperty("log4j.configuratorClass", null);
/*  54:    */       
/*  55:    */ 
/*  56:    */ 
/*  57:101 */       URL url = null;
/*  58:106 */       if (configurationOptionStr == null)
/*  59:    */       {
/*  60:107 */         url = Loader.getResource("log4j.xml");
/*  61:108 */         if (url == null) {
/*  62:109 */           url = Loader.getResource("log4j.properties");
/*  63:    */         }
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67:    */         try
/*  68:    */         {
/*  69:113 */           url = new URL(configurationOptionStr);
/*  70:    */         }
/*  71:    */         catch (MalformedURLException ex)
/*  72:    */         {
/*  73:117 */           url = Loader.getResource(configurationOptionStr);
/*  74:    */         }
/*  75:    */       }
/*  76:124 */       if (url != null)
/*  77:    */       {
/*  78:125 */         LogLog.debug("Using URL [" + url + "] for automatic log4j configuration.");
/*  79:    */         try
/*  80:    */         {
/*  81:127 */           OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository());
/*  82:    */         }
/*  83:    */         catch (NoClassDefFoundError e)
/*  84:    */         {
/*  85:130 */           LogLog.warn("Error during default initialization", e);
/*  86:    */         }
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90:133 */         LogLog.debug("Could not find resource: [" + configurationOptionStr + "].");
/*  91:    */       }
/*  92:    */     }
/*  93:    */     else
/*  94:    */     {
/*  95:136 */       LogLog.debug("Default initialization of overridden by log4j.defaultInitOverrideproperty.");
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void setRepositorySelector(RepositorySelector selector, Object guard)
/* 100:    */     throws IllegalArgumentException
/* 101:    */   {
/* 102:163 */     if ((guard != null) && (guard != guard)) {
/* 103:164 */       throw new IllegalArgumentException("Attempted to reset the LoggerFactory without possessing the guard.");
/* 104:    */     }
/* 105:168 */     if (selector == null) {
/* 106:169 */       throw new IllegalArgumentException("RepositorySelector must be non-null.");
/* 107:    */     }
/* 108:172 */     guard = guard;
/* 109:173 */     repositorySelector = selector;
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static boolean isLikelySafeScenario(Exception ex)
/* 113:    */   {
/* 114:187 */     StringWriter stringWriter = new StringWriter();
/* 115:188 */     ex.printStackTrace(new PrintWriter(stringWriter));
/* 116:189 */     String msg = stringWriter.toString();
/* 117:190 */     return msg.indexOf("org.apache.catalina.loader.WebappClassLoader.stop") != -1;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static LoggerRepository getLoggerRepository()
/* 121:    */   {
/* 122:196 */     if (repositorySelector == null)
/* 123:    */     {
/* 124:197 */       repositorySelector = new DefaultRepositorySelector(new NOPLoggerRepository());
/* 125:198 */       guard = null;
/* 126:199 */       Exception ex = new IllegalStateException("Class invariant violation");
/* 127:200 */       String msg = "log4j called after unloading, see http://logging.apache.org/log4j/1.2/faq.html#unload.";
/* 128:202 */       if (isLikelySafeScenario(ex)) {
/* 129:203 */         LogLog.debug(msg, ex);
/* 130:    */       } else {
/* 131:205 */         LogLog.error(msg, ex);
/* 132:    */       }
/* 133:    */     }
/* 134:208 */     return repositorySelector.getLoggerRepository();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static Logger getRootLogger()
/* 138:    */   {
/* 139:218 */     return getLoggerRepository().getRootLogger();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Logger getLogger(String name)
/* 143:    */   {
/* 144:228 */     return getLoggerRepository().getLogger(name);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static Logger getLogger(Class clazz)
/* 148:    */   {
/* 149:238 */     return getLoggerRepository().getLogger(clazz.getName());
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static Logger getLogger(String name, LoggerFactory factory)
/* 153:    */   {
/* 154:249 */     return getLoggerRepository().getLogger(name, factory);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static Logger exists(String name)
/* 158:    */   {
/* 159:255 */     return getLoggerRepository().exists(name);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static Enumeration getCurrentLoggers()
/* 163:    */   {
/* 164:261 */     return getLoggerRepository().getCurrentLoggers();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static void shutdown()
/* 168:    */   {
/* 169:267 */     getLoggerRepository().shutdown();
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static void resetConfiguration()
/* 173:    */   {
/* 174:273 */     getLoggerRepository().resetConfiguration();
/* 175:    */   }
/* 176:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.LogManager
 * JD-Core Version:    0.7.0.1
 */