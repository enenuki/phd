/*   1:    */ package jcifs;
/*   2:    */ 
/*   3:    */ import java.io.FileInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ import java.net.InetAddress;
/*  10:    */ import java.net.UnknownHostException;
/*  11:    */ import java.util.Properties;
/*  12:    */ import java.util.StringTokenizer;
/*  13:    */ import jcifs.util.LogStream;
/*  14:    */ 
/*  15:    */ public class Config
/*  16:    */ {
/*  17: 48 */   public static int socketCount = 0;
/*  18: 54 */   private static Properties prp = new Properties();
/*  19:    */   private static LogStream log;
/*  20: 56 */   public static String DEFAULT_OEM_ENCODING = "Cp850";
/*  21:    */   
/*  22:    */   static
/*  23:    */   {
/*  24: 61 */     FileInputStream in = null;
/*  25:    */     
/*  26: 63 */     log = LogStream.getInstance();
/*  27:    */     try
/*  28:    */     {
/*  29: 66 */       String filename = System.getProperty("jcifs.properties");
/*  30: 67 */       if ((filename != null) && (filename.length() > 1)) {
/*  31: 68 */         in = new FileInputStream(filename);
/*  32:    */       }
/*  33: 70 */       load(in);
/*  34: 71 */       if (in != null) {
/*  35: 72 */         in.close();
/*  36:    */       }
/*  37:    */     }
/*  38:    */     catch (IOException ioe)
/*  39:    */     {
/*  40: 74 */       if (LogStream.level > 0) {
/*  41: 75 */         ioe.printStackTrace(log);
/*  42:    */       }
/*  43:    */     }
/*  44:    */     int level;
/*  45: 78 */     if ((level = getInt("jcifs.util.loglevel", -1)) != -1) {
/*  46: 79 */       LogStream.setLevel(level);
/*  47:    */     }
/*  48:    */     try
/*  49:    */     {
/*  50: 83 */       "".getBytes(DEFAULT_OEM_ENCODING);
/*  51:    */     }
/*  52:    */     catch (UnsupportedEncodingException uee)
/*  53:    */     {
/*  54: 85 */       if (LogStream.level >= 2) {
/*  55: 86 */         log.println("WARNING: The default OEM encoding " + DEFAULT_OEM_ENCODING + " does not appear to be supported by this JRE. The default encoding will be US-ASCII.");
/*  56:    */       }
/*  57: 89 */       DEFAULT_OEM_ENCODING = "US-ASCII";
/*  58:    */     }
/*  59: 92 */     if (LogStream.level >= 4) {
/*  60:    */       try
/*  61:    */       {
/*  62: 94 */         prp.store(log, "JCIFS PROPERTIES");
/*  63:    */       }
/*  64:    */       catch (IOException ioe) {}
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void registerSmbURLHandler()
/*  69:    */   {
/*  70:118 */     String ver = System.getProperty("java.version");
/*  71:119 */     if ((ver.startsWith("1.1.")) || (ver.startsWith("1.2."))) {
/*  72:120 */       throw new RuntimeException("jcifs-0.7.0b4+ requires Java 1.3 or above. You are running " + ver);
/*  73:    */     }
/*  74:122 */     String pkgs = System.getProperty("java.protocol.handler.pkgs");
/*  75:123 */     if (pkgs == null)
/*  76:    */     {
/*  77:124 */       System.setProperty("java.protocol.handler.pkgs", "jcifs");
/*  78:    */     }
/*  79:125 */     else if (pkgs.indexOf("jcifs") == -1)
/*  80:    */     {
/*  81:126 */       pkgs = pkgs + "|jcifs";
/*  82:127 */       System.setProperty("java.protocol.handler.pkgs", pkgs);
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void setProperties(Properties prp)
/*  87:    */   {
/*  88:144 */     prp = new Properties(prp);
/*  89:    */     try
/*  90:    */     {
/*  91:146 */       prp.putAll(System.getProperties());
/*  92:    */     }
/*  93:    */     catch (SecurityException se)
/*  94:    */     {
/*  95:148 */       if (LogStream.level > 1) {
/*  96:149 */         log.println("SecurityException: jcifs will ignore System properties");
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void load(InputStream in)
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:159 */     if (in != null) {
/* 105:160 */       prp.load(in);
/* 106:    */     }
/* 107:    */     try
/* 108:    */     {
/* 109:163 */       prp.putAll(System.getProperties());
/* 110:    */     }
/* 111:    */     catch (SecurityException se)
/* 112:    */     {
/* 113:165 */       if (LogStream.level > 1) {
/* 114:166 */         log.println("SecurityException: jcifs will ignore System properties");
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static void store(OutputStream out, String header)
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:171 */     prp.store(out, header);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void list(PrintStream out)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:179 */     prp.list(out);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static Object setProperty(String key, String value)
/* 132:    */   {
/* 133:187 */     return prp.setProperty(key, value);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static Object get(String key)
/* 137:    */   {
/* 138:195 */     return prp.get(key);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static String getProperty(String key, String def)
/* 142:    */   {
/* 143:204 */     return prp.getProperty(key, def);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static String getProperty(String key)
/* 147:    */   {
/* 148:212 */     return prp.getProperty(key);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static int getInt(String key, int def)
/* 152:    */   {
/* 153:222 */     String s = prp.getProperty(key);
/* 154:223 */     if (s != null) {
/* 155:    */       try
/* 156:    */       {
/* 157:225 */         def = Integer.parseInt(s);
/* 158:    */       }
/* 159:    */       catch (NumberFormatException nfe)
/* 160:    */       {
/* 161:227 */         if (LogStream.level > 0) {
/* 162:228 */           nfe.printStackTrace(log);
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:231 */     return def;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static int getInt(String key)
/* 170:    */   {
/* 171:239 */     String s = prp.getProperty(key);
/* 172:240 */     int result = -1;
/* 173:241 */     if (s != null) {
/* 174:    */       try
/* 175:    */       {
/* 176:243 */         result = Integer.parseInt(s);
/* 177:    */       }
/* 178:    */       catch (NumberFormatException nfe)
/* 179:    */       {
/* 180:245 */         if (LogStream.level > 0) {
/* 181:246 */           nfe.printStackTrace(log);
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:249 */     return result;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static long getLong(String key, long def)
/* 189:    */   {
/* 190:259 */     String s = prp.getProperty(key);
/* 191:260 */     if (s != null) {
/* 192:    */       try
/* 193:    */       {
/* 194:262 */         def = Long.parseLong(s);
/* 195:    */       }
/* 196:    */       catch (NumberFormatException nfe)
/* 197:    */       {
/* 198:264 */         if (LogStream.level > 0) {
/* 199:265 */           nfe.printStackTrace(log);
/* 200:    */         }
/* 201:    */       }
/* 202:    */     }
/* 203:268 */     return def;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static InetAddress getInetAddress(String key, InetAddress def)
/* 207:    */   {
/* 208:278 */     String addr = prp.getProperty(key);
/* 209:279 */     if (addr != null) {
/* 210:    */       try
/* 211:    */       {
/* 212:281 */         def = InetAddress.getByName(addr);
/* 213:    */       }
/* 214:    */       catch (UnknownHostException uhe)
/* 215:    */       {
/* 216:283 */         if (LogStream.level > 0)
/* 217:    */         {
/* 218:284 */           log.println(addr);
/* 219:285 */           uhe.printStackTrace(log);
/* 220:    */         }
/* 221:    */       }
/* 222:    */     }
/* 223:289 */     return def;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static InetAddress getLocalHost()
/* 227:    */   {
/* 228:292 */     String addr = prp.getProperty("jcifs.smb.client.laddr");
/* 229:294 */     if (addr != null) {
/* 230:    */       try
/* 231:    */       {
/* 232:296 */         return InetAddress.getByName(addr);
/* 233:    */       }
/* 234:    */       catch (UnknownHostException uhe)
/* 235:    */       {
/* 236:298 */         if (LogStream.level > 0)
/* 237:    */         {
/* 238:299 */           log.println("Ignoring jcifs.smb.client.laddr address: " + addr);
/* 239:300 */           uhe.printStackTrace(log);
/* 240:    */         }
/* 241:    */       }
/* 242:    */     }
/* 243:305 */     return null;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static boolean getBoolean(String key, boolean def)
/* 247:    */   {
/* 248:313 */     String b = getProperty(key);
/* 249:314 */     if (b != null) {
/* 250:315 */       def = b.toLowerCase().equals("true");
/* 251:    */     }
/* 252:317 */     return def;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static InetAddress[] getInetAddressArray(String key, String delim, InetAddress[] def)
/* 256:    */   {
/* 257:327 */     String p = getProperty(key);
/* 258:328 */     if (p != null)
/* 259:    */     {
/* 260:329 */       StringTokenizer tok = new StringTokenizer(p, delim);
/* 261:330 */       int len = tok.countTokens();
/* 262:331 */       InetAddress[] arr = new InetAddress[len];
/* 263:332 */       for (int i = 0; i < len; i++)
/* 264:    */       {
/* 265:333 */         String addr = tok.nextToken();
/* 266:    */         try
/* 267:    */         {
/* 268:335 */           arr[i] = InetAddress.getByName(addr);
/* 269:    */         }
/* 270:    */         catch (UnknownHostException uhe)
/* 271:    */         {
/* 272:337 */           if (LogStream.level > 0)
/* 273:    */           {
/* 274:338 */             log.println(addr);
/* 275:339 */             uhe.printStackTrace(log);
/* 276:    */           }
/* 277:341 */           return def;
/* 278:    */         }
/* 279:    */       }
/* 280:344 */       return arr;
/* 281:    */     }
/* 282:346 */     return def;
/* 283:    */   }
/* 284:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.Config
 * JD-Core Version:    0.7.0.1
 */