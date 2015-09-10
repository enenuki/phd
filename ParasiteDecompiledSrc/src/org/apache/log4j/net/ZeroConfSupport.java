/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Hashtable;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.apache.log4j.helpers.LogLog;
/*  10:    */ 
/*  11:    */ public class ZeroConfSupport
/*  12:    */ {
/*  13: 29 */   private static Object jmDNS = ;
/*  14:    */   Object serviceInfo;
/*  15:    */   private static Class jmDNSClass;
/*  16:    */   private static Class serviceInfoClass;
/*  17:    */   
/*  18:    */   public ZeroConfSupport(String zone, int port, String name, Map properties)
/*  19:    */   {
/*  20: 37 */     boolean isVersion3 = false;
/*  21:    */     try
/*  22:    */     {
/*  23: 40 */       jmDNSClass.getMethod("create", null);
/*  24: 41 */       isVersion3 = true;
/*  25:    */     }
/*  26:    */     catch (NoSuchMethodException e) {}
/*  27: 46 */     if (isVersion3)
/*  28:    */     {
/*  29: 47 */       LogLog.debug("using JmDNS version 3 to construct serviceInfo instance");
/*  30: 48 */       this.serviceInfo = buildServiceInfoVersion3(zone, port, name, properties);
/*  31:    */     }
/*  32:    */     else
/*  33:    */     {
/*  34: 50 */       LogLog.debug("using JmDNS version 1.0 to construct serviceInfo instance");
/*  35: 51 */       this.serviceInfo = buildServiceInfoVersion1(zone, port, name, properties);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ZeroConfSupport(String zone, int port, String name)
/*  40:    */   {
/*  41: 56 */     this(zone, port, name, new HashMap());
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static Object createJmDNSVersion1()
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 62 */       return jmDNSClass.newInstance();
/*  49:    */     }
/*  50:    */     catch (InstantiationException e)
/*  51:    */     {
/*  52: 64 */       LogLog.warn("Unable to instantiate JMDNS", e);
/*  53:    */     }
/*  54:    */     catch (IllegalAccessException e)
/*  55:    */     {
/*  56: 66 */       LogLog.warn("Unable to instantiate JMDNS", e);
/*  57:    */     }
/*  58: 68 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static Object createJmDNSVersion3()
/*  62:    */   {
/*  63:    */     try
/*  64:    */     {
/*  65: 74 */       Method jmDNSCreateMethod = jmDNSClass.getMethod("create", null);
/*  66: 75 */       return jmDNSCreateMethod.invoke(null, null);
/*  67:    */     }
/*  68:    */     catch (IllegalAccessException e)
/*  69:    */     {
/*  70: 77 */       LogLog.warn("Unable to instantiate jmdns class", e);
/*  71:    */     }
/*  72:    */     catch (NoSuchMethodException e)
/*  73:    */     {
/*  74: 79 */       LogLog.warn("Unable to access constructor", e);
/*  75:    */     }
/*  76:    */     catch (InvocationTargetException e)
/*  77:    */     {
/*  78: 81 */       LogLog.warn("Unable to call constructor", e);
/*  79:    */     }
/*  80: 83 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private Object buildServiceInfoVersion1(String zone, int port, String name, Map properties)
/*  84:    */   {
/*  85: 88 */     Hashtable hashtableProperties = new Hashtable(properties);
/*  86:    */     try
/*  87:    */     {
/*  88: 90 */       Class[] args = new Class[6];
/*  89: 91 */       args[0] = String.class;
/*  90: 92 */       args[1] = String.class;
/*  91: 93 */       args[2] = Integer.TYPE;
/*  92: 94 */       args[3] = Integer.TYPE;
/*  93: 95 */       args[4] = Integer.TYPE;
/*  94: 96 */       args[5] = Hashtable.class;
/*  95: 97 */       Constructor constructor = serviceInfoClass.getConstructor(args);
/*  96: 98 */       Object[] values = new Object[6];
/*  97: 99 */       values[0] = zone;
/*  98:100 */       values[1] = name;
/*  99:101 */       values[2] = new Integer(port);
/* 100:102 */       values[3] = new Integer(0);
/* 101:103 */       values[4] = new Integer(0);
/* 102:104 */       values[5] = hashtableProperties;
/* 103:105 */       Object result = constructor.newInstance(values);
/* 104:106 */       LogLog.debug("created serviceinfo: " + result);
/* 105:107 */       return result;
/* 106:    */     }
/* 107:    */     catch (IllegalAccessException e)
/* 108:    */     {
/* 109:109 */       LogLog.warn("Unable to construct ServiceInfo instance", e);
/* 110:    */     }
/* 111:    */     catch (NoSuchMethodException e)
/* 112:    */     {
/* 113:111 */       LogLog.warn("Unable to get ServiceInfo constructor", e);
/* 114:    */     }
/* 115:    */     catch (InstantiationException e)
/* 116:    */     {
/* 117:113 */       LogLog.warn("Unable to construct ServiceInfo instance", e);
/* 118:    */     }
/* 119:    */     catch (InvocationTargetException e)
/* 120:    */     {
/* 121:115 */       LogLog.warn("Unable to construct ServiceInfo instance", e);
/* 122:    */     }
/* 123:117 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   private Object buildServiceInfoVersion3(String zone, int port, String name, Map properties)
/* 127:    */   {
/* 128:    */     try
/* 129:    */     {
/* 130:122 */       Class[] args = new Class[6];
/* 131:123 */       args[0] = String.class;
/* 132:124 */       args[1] = String.class;
/* 133:125 */       args[2] = Integer.TYPE;
/* 134:126 */       args[3] = Integer.TYPE;
/* 135:127 */       args[4] = Integer.TYPE;
/* 136:128 */       args[5] = Map.class;
/* 137:129 */       Method serviceInfoCreateMethod = serviceInfoClass.getMethod("create", args);
/* 138:130 */       Object[] values = new Object[6];
/* 139:131 */       values[0] = zone;
/* 140:132 */       values[1] = name;
/* 141:133 */       values[2] = new Integer(port);
/* 142:134 */       values[3] = new Integer(0);
/* 143:135 */       values[4] = new Integer(0);
/* 144:136 */       values[5] = properties;
/* 145:137 */       Object result = serviceInfoCreateMethod.invoke(null, values);
/* 146:138 */       LogLog.debug("created serviceinfo: " + result);
/* 147:139 */       return result;
/* 148:    */     }
/* 149:    */     catch (IllegalAccessException e)
/* 150:    */     {
/* 151:141 */       LogLog.warn("Unable to invoke create method", e);
/* 152:    */     }
/* 153:    */     catch (NoSuchMethodException e)
/* 154:    */     {
/* 155:143 */       LogLog.warn("Unable to find create method", e);
/* 156:    */     }
/* 157:    */     catch (InvocationTargetException e)
/* 158:    */     {
/* 159:145 */       LogLog.warn("Unable to invoke create method", e);
/* 160:    */     }
/* 161:147 */     return null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void advertise()
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:152 */       Method method = jmDNSClass.getMethod("registerService", new Class[] { serviceInfoClass });
/* 169:153 */       method.invoke(jmDNS, new Object[] { this.serviceInfo });
/* 170:154 */       LogLog.debug("registered serviceInfo: " + this.serviceInfo);
/* 171:    */     }
/* 172:    */     catch (IllegalAccessException e)
/* 173:    */     {
/* 174:156 */       LogLog.warn("Unable to invoke registerService method", e);
/* 175:    */     }
/* 176:    */     catch (NoSuchMethodException e)
/* 177:    */     {
/* 178:158 */       LogLog.warn("No registerService method", e);
/* 179:    */     }
/* 180:    */     catch (InvocationTargetException e)
/* 181:    */     {
/* 182:160 */       LogLog.warn("Unable to invoke registerService method", e);
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void unadvertise()
/* 187:    */   {
/* 188:    */     try
/* 189:    */     {
/* 190:166 */       Method method = jmDNSClass.getMethod("unregisterService", new Class[] { serviceInfoClass });
/* 191:167 */       method.invoke(jmDNS, new Object[] { this.serviceInfo });
/* 192:168 */       LogLog.debug("unregistered serviceInfo: " + this.serviceInfo);
/* 193:    */     }
/* 194:    */     catch (IllegalAccessException e)
/* 195:    */     {
/* 196:170 */       LogLog.warn("Unable to invoke unregisterService method", e);
/* 197:    */     }
/* 198:    */     catch (NoSuchMethodException e)
/* 199:    */     {
/* 200:172 */       LogLog.warn("No unregisterService method", e);
/* 201:    */     }
/* 202:    */     catch (InvocationTargetException e)
/* 203:    */     {
/* 204:174 */       LogLog.warn("Unable to invoke unregisterService method", e);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   private static Object initializeJMDNS()
/* 209:    */   {
/* 210:    */     try
/* 211:    */     {
/* 212:180 */       jmDNSClass = Class.forName("javax.jmdns.JmDNS");
/* 213:181 */       serviceInfoClass = Class.forName("javax.jmdns.ServiceInfo");
/* 214:    */     }
/* 215:    */     catch (ClassNotFoundException e)
/* 216:    */     {
/* 217:183 */       LogLog.warn("JmDNS or serviceInfo class not found", e);
/* 218:    */     }
/* 219:187 */     boolean isVersion3 = false;
/* 220:    */     try
/* 221:    */     {
/* 222:190 */       jmDNSClass.getMethod("create", null);
/* 223:191 */       isVersion3 = true;
/* 224:    */     }
/* 225:    */     catch (NoSuchMethodException e) {}
/* 226:196 */     if (isVersion3) {
/* 227:197 */       return createJmDNSVersion3();
/* 228:    */     }
/* 229:199 */     return createJmDNSVersion1();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static Object getJMDNSInstance()
/* 233:    */   {
/* 234:204 */     return jmDNS;
/* 235:    */   }
/* 236:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.ZeroConfSupport
 * JD-Core Version:    0.7.0.1
 */