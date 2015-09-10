/*   1:    */ package org.hibernate.internal.util.config;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Properties;
/*   8:    */ import java.util.Set;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  12:    */ 
/*  13:    */ public final class ConfigurationHelper
/*  14:    */ {
/*  15:    */   private static final String PLACEHOLDER_START = "${";
/*  16:    */   
/*  17:    */   public static String getString(String name, Map values)
/*  18:    */   {
/*  19: 60 */     Object value = values.get(name);
/*  20: 61 */     if (value == null) {
/*  21: 62 */       return null;
/*  22:    */     }
/*  23: 64 */     if (String.class.isInstance(value)) {
/*  24: 65 */       return (String)value;
/*  25:    */     }
/*  26: 67 */     return value.toString();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static String getString(String name, Map values, String defaultValue)
/*  30:    */   {
/*  31: 80 */     String value = getString(name, values);
/*  32: 81 */     return value == null ? defaultValue : value;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static boolean getBoolean(String name, Map values)
/*  36:    */   {
/*  37: 93 */     return getBoolean(name, values, false);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static boolean getBoolean(String name, Map values, boolean defaultValue)
/*  41:    */   {
/*  42:106 */     Object value = values.get(name);
/*  43:107 */     if (value == null) {
/*  44:108 */       return defaultValue;
/*  45:    */     }
/*  46:110 */     if (Boolean.class.isInstance(value)) {
/*  47:111 */       return ((Boolean)value).booleanValue();
/*  48:    */     }
/*  49:113 */     if (String.class.isInstance(value)) {
/*  50:114 */       return Boolean.parseBoolean((String)value);
/*  51:    */     }
/*  52:116 */     throw new ConfigurationException("Could not determine how to handle configuration value [name=" + name + ", value=" + value + "] as boolean");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static int getInt(String name, Map values, int defaultValue)
/*  56:    */   {
/*  57:131 */     Object value = values.get(name);
/*  58:132 */     if (value == null) {
/*  59:133 */       return defaultValue;
/*  60:    */     }
/*  61:135 */     if (Integer.class.isInstance(value)) {
/*  62:136 */       return ((Integer)value).intValue();
/*  63:    */     }
/*  64:138 */     if (String.class.isInstance(value)) {
/*  65:139 */       return Integer.parseInt((String)value);
/*  66:    */     }
/*  67:141 */     throw new ConfigurationException("Could not determine how to handle configuration value [name=" + name + ", value=" + value + "(" + value.getClass().getName() + ")] as int");
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static Integer getInteger(String name, Map values)
/*  71:    */   {
/*  72:156 */     Object value = values.get(name);
/*  73:157 */     if (value == null) {
/*  74:158 */       return null;
/*  75:    */     }
/*  76:160 */     if (Integer.class.isInstance(value)) {
/*  77:161 */       return (Integer)value;
/*  78:    */     }
/*  79:163 */     if (String.class.isInstance(value))
/*  80:    */     {
/*  81:165 */       String trimmed = value.toString().trim();
/*  82:166 */       if (trimmed.isEmpty()) {
/*  83:167 */         return null;
/*  84:    */       }
/*  85:169 */       return Integer.valueOf(trimmed);
/*  86:    */     }
/*  87:171 */     throw new ConfigurationException("Could not determine how to handle configuration value [name=" + name + ", value=" + value + "(" + value.getClass().getName() + ")] as Integer");
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static Map clone(Map<?, ?> configurationValues)
/*  91:    */   {
/*  92:186 */     if (configurationValues == null) {
/*  93:187 */       return null;
/*  94:    */     }
/*  95:190 */     if (Properties.class.isInstance(configurationValues)) {
/*  96:191 */       return (Properties)((Properties)configurationValues).clone();
/*  97:    */     }
/*  98:194 */     HashMap clone = new HashMap();
/*  99:195 */     for (Map.Entry entry : configurationValues.entrySet()) {
/* 100:196 */       clone.put(entry.getKey(), entry.getValue());
/* 101:    */     }
/* 102:198 */     return clone;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Properties maskOut(Properties props, String key)
/* 106:    */   {
/* 107:212 */     Properties clone = (Properties)props.clone();
/* 108:213 */     if (clone.get(key) != null) {
/* 109:214 */       clone.setProperty(key, "****");
/* 110:    */     }
/* 111:216 */     return clone;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static String extractPropertyValue(String propertyName, Properties properties)
/* 115:    */   {
/* 116:233 */     String value = properties.getProperty(propertyName);
/* 117:234 */     if (value == null) {
/* 118:235 */       return null;
/* 119:    */     }
/* 120:237 */     value = value.trim();
/* 121:238 */     if (StringHelper.isEmpty(value)) {
/* 122:239 */       return null;
/* 123:    */     }
/* 124:241 */     return value;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Map toMap(String propertyName, String delim, Properties properties)
/* 128:    */   {
/* 129:258 */     Map map = new HashMap();
/* 130:259 */     String value = extractPropertyValue(propertyName, properties);
/* 131:260 */     if (value != null)
/* 132:    */     {
/* 133:261 */       StringTokenizer tokens = new StringTokenizer(value, delim);
/* 134:262 */       while (tokens.hasMoreTokens()) {
/* 135:263 */         map.put(tokens.nextToken(), tokens.hasMoreElements() ? tokens.nextToken() : "");
/* 136:    */       }
/* 137:    */     }
/* 138:266 */     return map;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static String[] toStringArray(String propertyName, String delim, Properties properties)
/* 142:    */   {
/* 143:281 */     return toStringArray(extractPropertyValue(propertyName, properties), delim);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static String[] toStringArray(String stringForm, String delim)
/* 147:    */   {
/* 148:295 */     if (stringForm != null) {
/* 149:296 */       return StringHelper.split(delim, stringForm);
/* 150:    */     }
/* 151:299 */     return ArrayHelper.EMPTY_STRING_ARRAY;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static void resolvePlaceHolders(Map<?, ?> configurationValues)
/* 155:    */   {
/* 156:309 */     Iterator itr = configurationValues.entrySet().iterator();
/* 157:310 */     while (itr.hasNext())
/* 158:    */     {
/* 159:311 */       Map.Entry entry = (Map.Entry)itr.next();
/* 160:312 */       Object value = entry.getValue();
/* 161:313 */       if ((value != null) && (String.class.isInstance(value)))
/* 162:    */       {
/* 163:314 */         String resolved = resolvePlaceHolder((String)value);
/* 164:315 */         if (!value.equals(resolved)) {
/* 165:316 */           if (resolved == null) {
/* 166:317 */             itr.remove();
/* 167:    */           } else {
/* 168:320 */             entry.setValue(resolved);
/* 169:    */           }
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static String resolvePlaceHolder(String property)
/* 176:    */   {
/* 177:334 */     if (property.indexOf("${") < 0) {
/* 178:335 */       return property;
/* 179:    */     }
/* 180:337 */     StringBuffer buff = new StringBuffer();
/* 181:338 */     char[] chars = property.toCharArray();
/* 182:339 */     for (int pos = 0; pos < chars.length; pos++)
/* 183:    */     {
/* 184:340 */       if (chars[pos] == '$') {
/* 185:342 */         if (chars[(pos + 1)] == '{')
/* 186:    */         {
/* 187:344 */           String systemPropertyName = "";
/* 188:345 */           for (int x = pos + 2; (x < chars.length) && (chars[x] != '}'); x++)
/* 189:    */           {
/* 190:347 */             systemPropertyName = systemPropertyName + chars[x];
/* 191:350 */             if (x == chars.length - 1) {
/* 192:351 */               throw new IllegalArgumentException("unmatched placeholder start [" + property + "]");
/* 193:    */             }
/* 194:    */           }
/* 195:354 */           String systemProperty = extractFromSystem(systemPropertyName);
/* 196:355 */           buff.append(systemProperty == null ? "" : systemProperty);
/* 197:356 */           pos = x + 1;
/* 198:358 */           if (pos >= chars.length) {
/* 199:    */             break;
/* 200:    */           }
/* 201:    */         }
/* 202:    */       }
/* 203:363 */       buff.append(chars[pos]);
/* 204:    */     }
/* 205:365 */     String rtn = buff.toString();
/* 206:366 */     return StringHelper.isEmpty(rtn) ? null : rtn;
/* 207:    */   }
/* 208:    */   
/* 209:    */   private static String extractFromSystem(String systemPropertyName)
/* 210:    */   {
/* 211:    */     try
/* 212:    */     {
/* 213:371 */       return System.getProperty(systemPropertyName);
/* 214:    */     }
/* 215:    */     catch (Throwable t) {}
/* 216:374 */     return null;
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.config.ConfigurationHelper
 * JD-Core Version:    0.7.0.1
 */