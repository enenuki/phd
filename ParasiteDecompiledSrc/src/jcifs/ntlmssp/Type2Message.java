/*   1:    */ package jcifs.ntlmssp;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.net.UnknownHostException;
/*   5:    */ import jcifs.Config;
/*   6:    */ import jcifs.netbios.NbtAddress;
/*   7:    */ import jcifs.util.Hexdump;
/*   8:    */ 
/*   9:    */ public class Type2Message
/*  10:    */   extends NtlmMessage
/*  11:    */ {
/*  12: 50 */   private static final int DEFAULT_FLAGS = 0x200 | (Config.getBoolean("jcifs.smb.client.useUnicode", true) ? 1 : 2);
/*  13: 53 */   private static final String DEFAULT_DOMAIN = Config.getProperty("jcifs.smb.client.domain", null);
/*  14:    */   private static final byte[] DEFAULT_TARGET_INFORMATION;
/*  15:    */   private byte[] challenge;
/*  16:    */   private String target;
/*  17:    */   private byte[] context;
/*  18:    */   private byte[] targetInformation;
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22: 54 */     byte[] domain = new byte[0];
/*  23: 55 */     if (DEFAULT_DOMAIN != null) {
/*  24:    */       try
/*  25:    */       {
/*  26: 57 */         domain = DEFAULT_DOMAIN.getBytes("UTF-16LE");
/*  27:    */       }
/*  28:    */       catch (IOException ex) {}
/*  29:    */     }
/*  30: 60 */     int domainLength = domain.length;
/*  31: 61 */     byte[] server = new byte[0];
/*  32:    */     try
/*  33:    */     {
/*  34: 63 */       String host = NbtAddress.getLocalHost().getHostName();
/*  35: 64 */       if (host != null) {
/*  36:    */         try
/*  37:    */         {
/*  38: 66 */           server = host.getBytes("UTF-16LE");
/*  39:    */         }
/*  40:    */         catch (IOException ex) {}
/*  41:    */       }
/*  42:    */     }
/*  43:    */     catch (UnknownHostException ex) {}
/*  44: 70 */     int serverLength = server.length;
/*  45: 71 */     byte[] targetInfo = new byte[(domainLength > 0 ? domainLength + 4 : 0) + (serverLength > 0 ? serverLength + 4 : 0) + 4];
/*  46:    */     
/*  47: 73 */     int offset = 0;
/*  48: 74 */     if (domainLength > 0)
/*  49:    */     {
/*  50: 75 */       writeUShort(targetInfo, offset, 2);
/*  51: 76 */       offset += 2;
/*  52: 77 */       writeUShort(targetInfo, offset, domainLength);
/*  53: 78 */       offset += 2;
/*  54: 79 */       System.arraycopy(domain, 0, targetInfo, offset, domainLength);
/*  55: 80 */       offset += domainLength;
/*  56:    */     }
/*  57: 82 */     if (serverLength > 0)
/*  58:    */     {
/*  59: 83 */       writeUShort(targetInfo, offset, 1);
/*  60: 84 */       offset += 2;
/*  61: 85 */       writeUShort(targetInfo, offset, serverLength);
/*  62: 86 */       offset += 2;
/*  63: 87 */       System.arraycopy(server, 0, targetInfo, offset, serverLength);
/*  64:    */     }
/*  65: 89 */     DEFAULT_TARGET_INFORMATION = targetInfo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Type2Message()
/*  69:    */   {
/*  70: 97 */     this(getDefaultFlags(), null, null);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Type2Message(Type1Message type1)
/*  74:    */   {
/*  75:107 */     this(type1, null, null);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Type2Message(Type1Message type1, byte[] challenge, String target)
/*  79:    */   {
/*  80:118 */     this(getDefaultFlags(type1), challenge, (type1 != null) && (target == null) && (type1.getFlag(4)) ? getDefaultDomain() : target);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Type2Message(int flags, byte[] challenge, String target)
/*  84:    */   {
/*  85:131 */     setFlags(flags);
/*  86:132 */     setChallenge(challenge);
/*  87:133 */     setTarget(target);
/*  88:134 */     if (target != null) {
/*  89:134 */       setTargetInformation(getDefaultTargetInformation());
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Type2Message(byte[] material)
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:144 */     parse(material);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public byte[] getChallenge()
/* 100:    */   {
/* 101:153 */     return this.challenge;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setChallenge(byte[] challenge)
/* 105:    */   {
/* 106:162 */     this.challenge = challenge;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getTarget()
/* 110:    */   {
/* 111:171 */     return this.target;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setTarget(String target)
/* 115:    */   {
/* 116:180 */     this.target = target;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public byte[] getTargetInformation()
/* 120:    */   {
/* 121:191 */     return this.targetInformation;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setTargetInformation(byte[] targetInformation)
/* 125:    */   {
/* 126:202 */     this.targetInformation = targetInformation;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public byte[] getContext()
/* 130:    */   {
/* 131:213 */     return this.context;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setContext(byte[] context)
/* 135:    */   {
/* 136:223 */     this.context = context;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public byte[] toByteArray()
/* 140:    */   {
/* 141:    */     try
/* 142:    */     {
/* 143:228 */       String targetName = getTarget();
/* 144:229 */       byte[] challenge = getChallenge();
/* 145:230 */       byte[] context = getContext();
/* 146:231 */       byte[] targetInformation = getTargetInformation();
/* 147:232 */       int flags = getFlags();
/* 148:233 */       byte[] target = new byte[0];
/* 149:234 */       if ((flags & 0x70000) != 0) {
/* 150:237 */         if ((targetName != null) && (targetName.length() != 0)) {
/* 151:238 */           target = (flags & 0x1) != 0 ? targetName.getBytes("UTF-16LE") : targetName.toUpperCase().getBytes(getOEMEncoding());
/* 152:    */         } else {
/* 153:242 */           flags &= 0xFFF8FFFF;
/* 154:    */         }
/* 155:    */       }
/* 156:247 */       if (targetInformation != null)
/* 157:    */       {
/* 158:248 */         flags |= 0x800000;
/* 159:250 */         if (context == null) {
/* 160:250 */           context = new byte[8];
/* 161:    */         }
/* 162:    */       }
/* 163:252 */       int data = 32;
/* 164:253 */       if (context != null) {
/* 165:253 */         data += 8;
/* 166:    */       }
/* 167:254 */       if (targetInformation != null) {
/* 168:254 */         data += 8;
/* 169:    */       }
/* 170:255 */       byte[] type2 = new byte[data + target.length + (targetInformation != null ? targetInformation.length : 0)];
/* 171:    */       
/* 172:257 */       System.arraycopy(NTLMSSP_SIGNATURE, 0, type2, 0, 8);
/* 173:258 */       writeULong(type2, 8, 2);
/* 174:259 */       writeSecurityBuffer(type2, 12, data, target);
/* 175:260 */       writeULong(type2, 20, flags);
/* 176:261 */       System.arraycopy(challenge != null ? challenge : new byte[8], 0, type2, 24, 8);
/* 177:263 */       if (context != null) {
/* 178:263 */         System.arraycopy(context, 0, type2, 32, 8);
/* 179:    */       }
/* 180:264 */       if (targetInformation != null) {
/* 181:265 */         writeSecurityBuffer(type2, 40, data + target.length, targetInformation);
/* 182:    */       }
/* 183:268 */       return type2;
/* 184:    */     }
/* 185:    */     catch (IOException ex)
/* 186:    */     {
/* 187:270 */       throw new IllegalStateException(ex.getMessage());
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String toString()
/* 192:    */   {
/* 193:275 */     String target = getTarget();
/* 194:276 */     byte[] challenge = getChallenge();
/* 195:277 */     byte[] context = getContext();
/* 196:278 */     byte[] targetInformation = getTargetInformation();
/* 197:    */     
/* 198:280 */     return "Type2Message[target=" + target + ",challenge=" + (challenge == null ? "null" : new StringBuffer().append("<").append(challenge.length).append(" bytes>").toString()) + ",context=" + (context == null ? "null" : new StringBuffer().append("<").append(context.length).append(" bytes>").toString()) + ",targetInformation=" + (targetInformation == null ? "null" : new StringBuffer().append("<").append(targetInformation.length).append(" bytes>").toString()) + ",flags=0x" + Hexdump.toHexString(getFlags(), 8) + "]";
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static int getDefaultFlags()
/* 202:    */   {
/* 203:294 */     return DEFAULT_FLAGS;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static int getDefaultFlags(Type1Message type1)
/* 207:    */   {
/* 208:304 */     if (type1 == null) {
/* 209:304 */       return DEFAULT_FLAGS;
/* 210:    */     }
/* 211:305 */     int flags = 512;
/* 212:306 */     int type1Flags = type1.getFlags();
/* 213:307 */     flags |= ((type1Flags & 0x1) != 0 ? 1 : 2);
/* 214:309 */     if ((type1Flags & 0x4) != 0)
/* 215:    */     {
/* 216:310 */       String domain = getDefaultDomain();
/* 217:311 */       if (domain != null) {
/* 218:312 */         flags |= 0x10004;
/* 219:    */       }
/* 220:    */     }
/* 221:315 */     return flags;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public static String getDefaultDomain()
/* 225:    */   {
/* 226:324 */     return DEFAULT_DOMAIN;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public static byte[] getDefaultTargetInformation()
/* 230:    */   {
/* 231:328 */     return DEFAULT_TARGET_INFORMATION;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private void parse(byte[] material)
/* 235:    */     throws IOException
/* 236:    */   {
/* 237:332 */     for (int i = 0; i < 8; i++) {
/* 238:333 */       if (material[i] != NTLMSSP_SIGNATURE[i]) {
/* 239:334 */         throw new IOException("Not an NTLMSSP message.");
/* 240:    */       }
/* 241:    */     }
/* 242:337 */     if (readULong(material, 8) != 2) {
/* 243:338 */       throw new IOException("Not a Type 2 message.");
/* 244:    */     }
/* 245:340 */     int flags = readULong(material, 20);
/* 246:341 */     setFlags(flags);
/* 247:342 */     String target = null;
/* 248:343 */     byte[] bytes = readSecurityBuffer(material, 12);
/* 249:344 */     if (bytes.length != 0) {
/* 250:345 */       target = new String(bytes, (flags & 0x1) != 0 ? "UTF-16LE" : getOEMEncoding());
/* 251:    */     }
/* 252:349 */     setTarget(target);
/* 253:350 */     for (int i = 24; i < 32; i++) {
/* 254:351 */       if (material[i] != 0)
/* 255:    */       {
/* 256:352 */         byte[] challenge = new byte[8];
/* 257:353 */         System.arraycopy(material, 24, challenge, 0, 8);
/* 258:354 */         setChallenge(challenge);
/* 259:355 */         break;
/* 260:    */       }
/* 261:    */     }
/* 262:358 */     int offset = readULong(material, 16);
/* 263:359 */     if ((offset == 32) || (material.length == 32)) {
/* 264:359 */       return;
/* 265:    */     }
/* 266:360 */     for (int i = 32; i < 40; i++) {
/* 267:361 */       if (material[i] != 0)
/* 268:    */       {
/* 269:362 */         byte[] context = new byte[8];
/* 270:363 */         System.arraycopy(material, 32, context, 0, 8);
/* 271:364 */         setContext(context);
/* 272:365 */         break;
/* 273:    */       }
/* 274:    */     }
/* 275:368 */     if ((offset == 40) || (material.length == 40)) {
/* 276:368 */       return;
/* 277:    */     }
/* 278:369 */     bytes = readSecurityBuffer(material, 40);
/* 279:370 */     if (bytes.length != 0) {
/* 280:370 */       setTargetInformation(bytes);
/* 281:    */     }
/* 282:    */   }
/* 283:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.ntlmssp.Type2Message
 * JD-Core Version:    0.7.0.1
 */