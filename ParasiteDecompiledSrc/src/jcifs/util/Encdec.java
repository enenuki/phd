/*   1:    */ package jcifs.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Date;
/*   5:    */ 
/*   6:    */ public class Encdec
/*   7:    */ {
/*   8:    */   public static final long MILLISECONDS_BETWEEN_1970_AND_1601 = 11644473600000L;
/*   9:    */   public static final long SEC_BETWEEEN_1904_AND_1970 = 2082844800L;
/*  10:    */   public static final int TIME_1970_SEC_32BE = 1;
/*  11:    */   public static final int TIME_1970_SEC_32LE = 2;
/*  12:    */   public static final int TIME_1904_SEC_32BE = 3;
/*  13:    */   public static final int TIME_1904_SEC_32LE = 4;
/*  14:    */   public static final int TIME_1601_NANOS_64LE = 5;
/*  15:    */   public static final int TIME_1601_NANOS_64BE = 6;
/*  16:    */   public static final int TIME_1970_MILLIS_64BE = 7;
/*  17:    */   public static final int TIME_1970_MILLIS_64LE = 8;
/*  18:    */   
/*  19:    */   public static int enc_uint16be(short s, byte[] dst, int di)
/*  20:    */   {
/*  21: 46 */     dst[(di++)] = ((byte)(s >> 8 & 0xFF));
/*  22: 47 */     dst[di] = ((byte)(s & 0xFF));
/*  23: 48 */     return 2;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static int enc_uint32be(int i, byte[] dst, int di)
/*  27:    */   {
/*  28: 51 */     dst[(di++)] = ((byte)(i >> 24 & 0xFF));
/*  29: 52 */     dst[(di++)] = ((byte)(i >> 16 & 0xFF));
/*  30: 53 */     dst[(di++)] = ((byte)(i >> 8 & 0xFF));
/*  31: 54 */     dst[di] = ((byte)(i & 0xFF));
/*  32: 55 */     return 4;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static int enc_uint16le(short s, byte[] dst, int di)
/*  36:    */   {
/*  37: 59 */     dst[(di++)] = ((byte)(s & 0xFF));
/*  38: 60 */     dst[di] = ((byte)(s >> 8 & 0xFF));
/*  39: 61 */     return 2;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static int enc_uint32le(int i, byte[] dst, int di)
/*  43:    */   {
/*  44: 65 */     dst[(di++)] = ((byte)(i & 0xFF));
/*  45: 66 */     dst[(di++)] = ((byte)(i >> 8 & 0xFF));
/*  46: 67 */     dst[(di++)] = ((byte)(i >> 16 & 0xFF));
/*  47: 68 */     dst[di] = ((byte)(i >> 24 & 0xFF));
/*  48: 69 */     return 4;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static short dec_uint16be(byte[] src, int si)
/*  52:    */   {
/*  53: 77 */     return (short)((src[si] & 0xFF) << 8 | src[(si + 1)] & 0xFF);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static int dec_uint32be(byte[] src, int si)
/*  57:    */   {
/*  58: 81 */     return (src[si] & 0xFF) << 24 | (src[(si + 1)] & 0xFF) << 16 | (src[(si + 2)] & 0xFF) << 8 | src[(si + 3)] & 0xFF;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static short dec_uint16le(byte[] src, int si)
/*  62:    */   {
/*  63: 86 */     return (short)(src[si] & 0xFF | (src[(si + 1)] & 0xFF) << 8);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static int dec_uint32le(byte[] src, int si)
/*  67:    */   {
/*  68: 90 */     return src[si] & 0xFF | (src[(si + 1)] & 0xFF) << 8 | (src[(si + 2)] & 0xFF) << 16 | (src[(si + 3)] & 0xFF) << 24;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static int enc_uint64be(long l, byte[] dst, int di)
/*  72:    */   {
/*  73: 99 */     enc_uint32be((int)(l & 0xFFFFFFFF), dst, di + 4);
/*  74:100 */     enc_uint32be((int)(l >> 32 & 0xFFFFFFFF), dst, di);
/*  75:101 */     return 8;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static int enc_uint64le(long l, byte[] dst, int di)
/*  79:    */   {
/*  80:105 */     enc_uint32le((int)(l & 0xFFFFFFFF), dst, di);
/*  81:106 */     enc_uint32le((int)(l >> 32 & 0xFFFFFFFF), dst, di + 4);
/*  82:107 */     return 8;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static long dec_uint64be(byte[] src, int si)
/*  86:    */   {
/*  87:112 */     long l = dec_uint32be(src, si) & 0xFFFFFFFF;
/*  88:113 */     l <<= 32;
/*  89:114 */     l |= dec_uint32be(src, si + 4) & 0xFFFFFFFF;
/*  90:115 */     return l;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static long dec_uint64le(byte[] src, int si)
/*  94:    */   {
/*  95:120 */     long l = dec_uint32le(src, si + 4) & 0xFFFFFFFF;
/*  96:121 */     l <<= 32;
/*  97:122 */     l |= dec_uint32le(src, si) & 0xFFFFFFFF;
/*  98:123 */     return l;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static int enc_floatle(float f, byte[] dst, int di)
/* 102:    */   {
/* 103:131 */     return enc_uint32le(Float.floatToIntBits(f), dst, di);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static int enc_floatbe(float f, byte[] dst, int di)
/* 107:    */   {
/* 108:135 */     return enc_uint32be(Float.floatToIntBits(f), dst, di);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static float dec_floatle(byte[] src, int si)
/* 112:    */   {
/* 113:143 */     return Float.intBitsToFloat(dec_uint32le(src, si));
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static float dec_floatbe(byte[] src, int si)
/* 117:    */   {
/* 118:147 */     return Float.intBitsToFloat(dec_uint32be(src, si));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static int enc_doublele(double d, byte[] dst, int di)
/* 122:    */   {
/* 123:155 */     return enc_uint64le(Double.doubleToLongBits(d), dst, di);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static int enc_doublebe(double d, byte[] dst, int di)
/* 127:    */   {
/* 128:159 */     return enc_uint64be(Double.doubleToLongBits(d), dst, di);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static double dec_doublele(byte[] src, int si)
/* 132:    */   {
/* 133:163 */     return Double.longBitsToDouble(dec_uint64le(src, si));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static double dec_doublebe(byte[] src, int si)
/* 137:    */   {
/* 138:167 */     return Double.longBitsToDouble(dec_uint64be(src, si));
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static int enc_time(Date date, byte[] dst, int di, int enc)
/* 142:    */   {
/* 143:177 */     switch (enc)
/* 144:    */     {
/* 145:    */     case 1: 
/* 146:179 */       return enc_uint32be((int)(date.getTime() / 1000L), dst, di);
/* 147:    */     case 2: 
/* 148:181 */       return enc_uint32le((int)(date.getTime() / 1000L), dst, di);
/* 149:    */     case 3: 
/* 150:183 */       return enc_uint32be((int)(date.getTime() / 1000L + 2082844800L & 0xFFFFFFFF), dst, di);
/* 151:    */     case 4: 
/* 152:186 */       return enc_uint32le((int)(date.getTime() / 1000L + 2082844800L & 0xFFFFFFFF), dst, di);
/* 153:    */     case 6: 
/* 154:189 */       long t = (date.getTime() + 11644473600000L) * 10000L;
/* 155:190 */       return enc_uint64be(t, dst, di);
/* 156:    */     case 5: 
/* 157:192 */       long t = (date.getTime() + 11644473600000L) * 10000L;
/* 158:193 */       return enc_uint64le(t, dst, di);
/* 159:    */     case 7: 
/* 160:195 */       return enc_uint64be(date.getTime(), dst, di);
/* 161:    */     case 8: 
/* 162:197 */       return enc_uint64le(date.getTime(), dst, di);
/* 163:    */     }
/* 164:199 */     throw new IllegalArgumentException("Unsupported time encoding");
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static Date dec_time(byte[] src, int si, int enc)
/* 168:    */   {
/* 169:210 */     switch (enc)
/* 170:    */     {
/* 171:    */     case 1: 
/* 172:212 */       return new Date(dec_uint32be(src, si) * 1000L);
/* 173:    */     case 2: 
/* 174:214 */       return new Date(dec_uint32le(src, si) * 1000L);
/* 175:    */     case 3: 
/* 176:216 */       return new Date(((dec_uint32be(src, si) & 0xFFFFFFFF) - 2082844800L) * 1000L);
/* 177:    */     case 4: 
/* 178:219 */       return new Date(((dec_uint32le(src, si) & 0xFFFFFFFF) - 2082844800L) * 1000L);
/* 179:    */     case 6: 
/* 180:222 */       long t = dec_uint64be(src, si);
/* 181:223 */       return new Date(t / 10000L - 11644473600000L);
/* 182:    */     case 5: 
/* 183:225 */       long t = dec_uint64le(src, si);
/* 184:226 */       return new Date(t / 10000L - 11644473600000L);
/* 185:    */     case 7: 
/* 186:228 */       return new Date(dec_uint64be(src, si));
/* 187:    */     case 8: 
/* 188:230 */       return new Date(dec_uint64le(src, si));
/* 189:    */     }
/* 190:232 */     throw new IllegalArgumentException("Unsupported time encoding");
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static int enc_utf8(String str, byte[] dst, int di, int dlim)
/* 194:    */     throws IOException
/* 195:    */   {
/* 196:237 */     int start = di;
/* 197:238 */     int strlen = str.length();
/* 198:240 */     for (int i = 0; (di < dlim) && (i < strlen); i++)
/* 199:    */     {
/* 200:241 */       int ch = str.charAt(i);
/* 201:242 */       if ((ch >= 1) && (ch <= 127))
/* 202:    */       {
/* 203:243 */         dst[(di++)] = ((byte)ch);
/* 204:    */       }
/* 205:244 */       else if (ch > 2047)
/* 206:    */       {
/* 207:245 */         if (dlim - di < 3) {
/* 208:    */           break;
/* 209:    */         }
/* 210:248 */         dst[(di++)] = ((byte)(0xE0 | ch >> 12 & 0xF));
/* 211:249 */         dst[(di++)] = ((byte)(0x80 | ch >> 6 & 0x3F));
/* 212:250 */         dst[(di++)] = ((byte)(0x80 | ch >> 0 & 0x3F));
/* 213:    */       }
/* 214:    */       else
/* 215:    */       {
/* 216:252 */         if (dlim - di < 2) {
/* 217:    */           break;
/* 218:    */         }
/* 219:255 */         dst[(di++)] = ((byte)(0xC0 | ch >> 6 & 0x1F));
/* 220:256 */         dst[(di++)] = ((byte)(0x80 | ch >> 0 & 0x3F));
/* 221:    */       }
/* 222:    */     }
/* 223:260 */     return di - start;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public static String dec_utf8(byte[] src, int si, int slim)
/* 227:    */     throws IOException
/* 228:    */   {
/* 229:263 */     char[] uni = new char[slim - si];
/* 230:    */     int ch;
/* 231:266 */     for (int ui = 0; (si < slim) && ((ch = src[(si++)] & 0xFF) != 0); ui++) {
/* 232:267 */       if (ch < 128)
/* 233:    */       {
/* 234:268 */         uni[ui] = ((char)ch);
/* 235:    */       }
/* 236:269 */       else if ((ch & 0xE0) == 192)
/* 237:    */       {
/* 238:270 */         if (slim - si < 2) {
/* 239:    */           break;
/* 240:    */         }
/* 241:273 */         uni[ui] = ((char)((ch & 0x1F) << 6));
/* 242:274 */         ch = src[(si++)] & 0xFF; int 
/* 243:275 */           tmp98_96 = ui; char[] tmp98_95 = uni;tmp98_95[tmp98_96] = ((char)(tmp98_95[tmp98_96] | ch & 0x3F));
/* 244:276 */         if (((ch & 0xC0) != 128) || (uni[ui] < '')) {
/* 245:277 */           throw new IOException("Invalid UTF-8 sequence");
/* 246:    */         }
/* 247:    */       }
/* 248:279 */       else if ((ch & 0xF0) == 224)
/* 249:    */       {
/* 250:280 */         if (slim - si < 3) {
/* 251:    */           break;
/* 252:    */         }
/* 253:283 */         uni[ui] = ((char)((ch & 0xF) << 12));
/* 254:284 */         ch = src[(si++)] & 0xFF;
/* 255:285 */         if ((ch & 0xC0) != 128) {
/* 256:286 */           throw new IOException("Invalid UTF-8 sequence");
/* 257:    */         }
/* 258:288 */         int tmp212_210 = ui; char[] tmp212_209 = uni;tmp212_209[tmp212_210] = ((char)(tmp212_209[tmp212_210] | (ch & 0x3F) << 6));
/* 259:289 */         ch = src[(si++)] & 0xFF; int 
/* 260:290 */           tmp240_238 = ui; char[] tmp240_237 = uni;tmp240_237[tmp240_238] = ((char)(tmp240_237[tmp240_238] | ch & 0x3F));
/* 261:291 */         if (((ch & 0xC0) != 128) || (uni[ui] < 'ࠀ')) {
/* 262:292 */           throw new IOException("Invalid UTF-8 sequence");
/* 263:    */         }
/* 264:    */       }
/* 265:    */       else
/* 266:    */       {
/* 267:296 */         throw new IOException("Unsupported UTF-8 sequence");
/* 268:    */       }
/* 269:    */     }
/* 270:300 */     return new String(uni, 0, ui);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public static String dec_ucs2le(byte[] src, int si, int slim, char[] buf)
/* 274:    */     throws IOException
/* 275:    */   {
/* 276:305 */     for (int bi = 0; si + 1 < slim; si += 2)
/* 277:    */     {
/* 278:306 */       buf[bi] = ((char)dec_uint16le(src, si));
/* 279:307 */       if (buf[bi] == 0) {
/* 280:    */         break;
/* 281:    */       }
/* 282:305 */       bi++;
/* 283:    */     }
/* 284:312 */     return new String(buf, 0, bi);
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.Encdec
 * JD-Core Version:    0.7.0.1
 */