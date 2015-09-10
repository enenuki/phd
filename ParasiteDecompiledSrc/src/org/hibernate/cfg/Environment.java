/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.sql.Timestamp;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Properties;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.Version;
/*  13:    */ import org.hibernate.bytecode.internal.javassist.BytecodeProviderImpl;
/*  14:    */ import org.hibernate.bytecode.spi.BytecodeProvider;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.internal.util.ConfigHelper;
/*  17:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public final class Environment
/*  21:    */   implements AvailableSettings
/*  22:    */ {
/*  23:172 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Environment.class.getName());
/*  24:    */   private static final BytecodeProvider BYTECODE_PROVIDER_INSTANCE;
/*  25:    */   private static final boolean ENABLE_BINARY_STREAMS;
/*  26:    */   private static final boolean ENABLE_REFLECTION_OPTIMIZER;
/*  27:    */   private static final boolean JVM_HAS_TIMESTAMP_BUG;
/*  28:    */   private static final Properties GLOBAL_PROPERTIES;
/*  29:    */   private static final Map<Integer, String> ISOLATION_LEVELS;
/*  30:182 */   private static final Map OBSOLETE_PROPERTIES = new HashMap();
/*  31:183 */   private static final Map RENAMED_PROPERTIES = new HashMap();
/*  32:    */   
/*  33:    */   public static void verifyProperties(Map<?, ?> configurationValues)
/*  34:    */   {
/*  35:191 */     Map propertiesToAdd = new HashMap();
/*  36:192 */     for (Map.Entry entry : configurationValues.entrySet())
/*  37:    */     {
/*  38:193 */       Object replacementKey = OBSOLETE_PROPERTIES.get(entry.getKey());
/*  39:194 */       if (replacementKey != null) {
/*  40:195 */         LOG.unsupportedProperty(entry.getKey(), replacementKey);
/*  41:    */       }
/*  42:197 */       Object renamedKey = RENAMED_PROPERTIES.get(entry.getKey());
/*  43:198 */       if (renamedKey != null)
/*  44:    */       {
/*  45:199 */         LOG.renamedProperty(entry.getKey(), renamedKey);
/*  46:200 */         propertiesToAdd.put(renamedKey, entry.getValue());
/*  47:    */       }
/*  48:    */     }
/*  49:203 */     configurationValues.putAll(propertiesToAdd);
/*  50:    */   }
/*  51:    */   
/*  52:    */   static
/*  53:    */   {
/*  54:207 */     Version.logVersion();
/*  55:    */     
/*  56:209 */     Map<Integer, String> temp = new HashMap();
/*  57:210 */     temp.put(Integer.valueOf(0), "NONE");
/*  58:211 */     temp.put(Integer.valueOf(1), "READ_UNCOMMITTED");
/*  59:212 */     temp.put(Integer.valueOf(2), "READ_COMMITTED");
/*  60:213 */     temp.put(Integer.valueOf(4), "REPEATABLE_READ");
/*  61:214 */     temp.put(Integer.valueOf(8), "SERIALIZABLE");
/*  62:215 */     ISOLATION_LEVELS = Collections.unmodifiableMap(temp);
/*  63:216 */     GLOBAL_PROPERTIES = new Properties();
/*  64:    */     
/*  65:218 */     GLOBAL_PROPERTIES.setProperty("hibernate.bytecode.use_reflection_optimizer", Boolean.FALSE.toString());
/*  66:    */     try
/*  67:    */     {
/*  68:221 */       InputStream stream = ConfigHelper.getResourceAsStream("/hibernate.properties");
/*  69:    */       try
/*  70:    */       {
/*  71:223 */         GLOBAL_PROPERTIES.load(stream);
/*  72:224 */         LOG.propertiesLoaded(ConfigurationHelper.maskOut(GLOBAL_PROPERTIES, "hibernate.connection.password"));
/*  73:    */       }
/*  74:    */       catch (Exception e)
/*  75:    */       {
/*  76:227 */         LOG.unableToLoadProperties();
/*  77:    */       }
/*  78:    */       finally
/*  79:    */       {
/*  80:    */         try
/*  81:    */         {
/*  82:231 */           stream.close();
/*  83:    */         }
/*  84:    */         catch (IOException ioe)
/*  85:    */         {
/*  86:234 */           LOG.unableToCloseStreamError(ioe);
/*  87:    */         }
/*  88:    */       }
/*  89:    */       try
/*  90:    */       {
/*  91:243 */         GLOBAL_PROPERTIES.putAll(System.getProperties());
/*  92:    */       }
/*  93:    */       catch (SecurityException se)
/*  94:    */       {
/*  95:246 */         LOG.unableToCopySystemProperties();
/*  96:    */       }
/*  97:    */     }
/*  98:    */     catch (HibernateException he)
/*  99:    */     {
/* 100:239 */       LOG.propertiesNotFound();
/* 101:    */     }
/* 102:249 */     verifyProperties(GLOBAL_PROPERTIES);
/* 103:    */     
/* 104:251 */     ENABLE_BINARY_STREAMS = ConfigurationHelper.getBoolean("hibernate.jdbc.use_streams_for_binary", GLOBAL_PROPERTIES);
/* 105:252 */     if (ENABLE_BINARY_STREAMS) {
/* 106:253 */       LOG.usingStreams();
/* 107:    */     }
/* 108:256 */     ENABLE_REFLECTION_OPTIMIZER = ConfigurationHelper.getBoolean("hibernate.bytecode.use_reflection_optimizer", GLOBAL_PROPERTIES);
/* 109:257 */     if (ENABLE_REFLECTION_OPTIMIZER) {
/* 110:258 */       LOG.usingReflectionOptimizer();
/* 111:    */     }
/* 112:261 */     BYTECODE_PROVIDER_INSTANCE = buildBytecodeProvider(GLOBAL_PROPERTIES);
/* 113:    */     
/* 114:263 */     long x = 123456789L;
/* 115:264 */     JVM_HAS_TIMESTAMP_BUG = new Timestamp(x).getTime() != x;
/* 116:265 */     if (JVM_HAS_TIMESTAMP_BUG) {
/* 117:266 */       LOG.usingTimestampWorkaround();
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static BytecodeProvider getBytecodeProvider()
/* 122:    */   {
/* 123:271 */     return BYTECODE_PROVIDER_INSTANCE;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static boolean jvmHasTimestampBug()
/* 127:    */   {
/* 128:284 */     return JVM_HAS_TIMESTAMP_BUG;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static boolean useStreamsForBinary()
/* 132:    */   {
/* 133:295 */     return ENABLE_BINARY_STREAMS;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static boolean useReflectionOptimizer()
/* 137:    */   {
/* 138:308 */     return ENABLE_REFLECTION_OPTIMIZER;
/* 139:    */   }
/* 140:    */   
/* 141:    */   private Environment()
/* 142:    */   {
/* 143:315 */     throw new UnsupportedOperationException();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static Properties getProperties()
/* 147:    */   {
/* 148:324 */     Properties copy = new Properties();
/* 149:325 */     copy.putAll(GLOBAL_PROPERTIES);
/* 150:326 */     return copy;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static String isolationLevelToString(int isolation)
/* 154:    */   {
/* 155:337 */     return (String)ISOLATION_LEVELS.get(Integer.valueOf(isolation));
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static BytecodeProvider buildBytecodeProvider(Properties properties)
/* 159:    */   {
/* 160:341 */     String provider = ConfigurationHelper.getString("hibernate.bytecode.provider", properties, "javassist");
/* 161:342 */     LOG.bytecodeProvider(provider);
/* 162:343 */     return buildBytecodeProvider(provider);
/* 163:    */   }
/* 164:    */   
/* 165:    */   private static BytecodeProvider buildBytecodeProvider(String providerName)
/* 166:    */   {
/* 167:347 */     if ("javassist".equals(providerName)) {
/* 168:348 */       return new BytecodeProviderImpl();
/* 169:    */     }
/* 170:351 */     LOG.unknownBytecodeProvider(providerName);
/* 171:352 */     return new BytecodeProviderImpl();
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.Environment
 * JD-Core Version:    0.7.0.1
 */