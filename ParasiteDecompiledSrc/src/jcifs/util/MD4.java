/*   1:    */ package jcifs.util;
/*   2:    */ 
/*   3:    */ import java.security.MessageDigest;
/*   4:    */ 
/*   5:    */ public class MD4
/*   6:    */   extends MessageDigest
/*   7:    */   implements Cloneable
/*   8:    */ {
/*   9:    */   private static final int BLOCK_LENGTH = 64;
/*  10: 50 */   private int[] context = new int[4];
/*  11:    */   private long count;
/*  12: 60 */   private byte[] buffer = new byte[64];
/*  13: 65 */   private int[] X = new int[16];
/*  14:    */   
/*  15:    */   public MD4()
/*  16:    */   {
/*  17: 72 */     super("MD4");
/*  18: 73 */     engineReset();
/*  19:    */   }
/*  20:    */   
/*  21:    */   private MD4(MD4 md)
/*  22:    */   {
/*  23: 80 */     this();
/*  24: 81 */     this.context = ((int[])md.context.clone());
/*  25: 82 */     this.buffer = ((byte[])md.buffer.clone());
/*  26: 83 */     this.count = md.count;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Object clone()
/*  30:    */   {
/*  31: 93 */     return new MD4(this);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void engineReset()
/*  35:    */   {
/*  36:106 */     this.context[0] = 1732584193;
/*  37:107 */     this.context[1] = -271733879;
/*  38:108 */     this.context[2] = -1732584194;
/*  39:109 */     this.context[3] = 271733878;
/*  40:110 */     this.count = 0L;
/*  41:111 */     for (int i = 0; i < 64; i++) {
/*  42:112 */       this.buffer[i] = 0;
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void engineUpdate(byte b)
/*  47:    */   {
/*  48:120 */     int i = (int)(this.count % 64L);
/*  49:121 */     this.count += 1L;
/*  50:122 */     this.buffer[i] = b;
/*  51:123 */     if (i == 63) {
/*  52:124 */       transform(this.buffer, 0);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void engineUpdate(byte[] input, int offset, int len)
/*  57:    */   {
/*  58:141 */     if ((offset < 0) || (len < 0) || (offset + len > input.length)) {
/*  59:142 */       throw new ArrayIndexOutOfBoundsException();
/*  60:    */     }
/*  61:145 */     int bufferNdx = (int)(this.count % 64L);
/*  62:146 */     this.count += len;
/*  63:147 */     int partLen = 64 - bufferNdx;
/*  64:148 */     int i = 0;
/*  65:149 */     if (len >= partLen)
/*  66:    */     {
/*  67:150 */       System.arraycopy(input, offset, this.buffer, bufferNdx, partLen);
/*  68:    */       
/*  69:    */ 
/*  70:153 */       transform(this.buffer, 0);
/*  71:155 */       for (i = partLen; i + 64 - 1 < len; i += 64) {
/*  72:156 */         transform(input, offset + i);
/*  73:    */       }
/*  74:157 */       bufferNdx = 0;
/*  75:    */     }
/*  76:160 */     if (i < len) {
/*  77:161 */       System.arraycopy(input, offset + i, this.buffer, bufferNdx, len - i);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public byte[] engineDigest()
/*  82:    */   {
/*  83:173 */     int bufferNdx = (int)(this.count % 64L);
/*  84:174 */     int padLen = bufferNdx < 56 ? 56 - bufferNdx : 120 - bufferNdx;
/*  85:    */     
/*  86:    */ 
/*  87:177 */     byte[] tail = new byte[padLen + 8];
/*  88:178 */     tail[0] = -128;
/*  89:183 */     for (int i = 0; i < 8; i++) {
/*  90:184 */       tail[(padLen + i)] = ((byte)(int)(this.count * 8L >>> 8 * i));
/*  91:    */     }
/*  92:186 */     engineUpdate(tail, 0, tail.length);
/*  93:    */     
/*  94:188 */     byte[] result = new byte[16];
/*  95:190 */     for (int i = 0; i < 4; i++) {
/*  96:191 */       for (int j = 0; j < 4; j++) {
/*  97:192 */         result[(i * 4 + j)] = ((byte)(this.context[i] >>> 8 * j));
/*  98:    */       }
/*  99:    */     }
/* 100:195 */     engineReset();
/* 101:196 */     return result;
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void transform(byte[] block, int offset)
/* 105:    */   {
/* 106:216 */     for (int i = 0; i < 16; i++) {
/* 107:217 */       this.X[i] = (block[(offset++)] & 0xFF | (block[(offset++)] & 0xFF) << 8 | (block[(offset++)] & 0xFF) << 16 | (block[(offset++)] & 0xFF) << 24);
/* 108:    */     }
/* 109:223 */     int A = this.context[0];
/* 110:224 */     int B = this.context[1];
/* 111:225 */     int C = this.context[2];
/* 112:226 */     int D = this.context[3];
/* 113:    */     
/* 114:228 */     A = FF(A, B, C, D, this.X[0], 3);
/* 115:229 */     D = FF(D, A, B, C, this.X[1], 7);
/* 116:230 */     C = FF(C, D, A, B, this.X[2], 11);
/* 117:231 */     B = FF(B, C, D, A, this.X[3], 19);
/* 118:232 */     A = FF(A, B, C, D, this.X[4], 3);
/* 119:233 */     D = FF(D, A, B, C, this.X[5], 7);
/* 120:234 */     C = FF(C, D, A, B, this.X[6], 11);
/* 121:235 */     B = FF(B, C, D, A, this.X[7], 19);
/* 122:236 */     A = FF(A, B, C, D, this.X[8], 3);
/* 123:237 */     D = FF(D, A, B, C, this.X[9], 7);
/* 124:238 */     C = FF(C, D, A, B, this.X[10], 11);
/* 125:239 */     B = FF(B, C, D, A, this.X[11], 19);
/* 126:240 */     A = FF(A, B, C, D, this.X[12], 3);
/* 127:241 */     D = FF(D, A, B, C, this.X[13], 7);
/* 128:242 */     C = FF(C, D, A, B, this.X[14], 11);
/* 129:243 */     B = FF(B, C, D, A, this.X[15], 19);
/* 130:    */     
/* 131:245 */     A = GG(A, B, C, D, this.X[0], 3);
/* 132:246 */     D = GG(D, A, B, C, this.X[4], 5);
/* 133:247 */     C = GG(C, D, A, B, this.X[8], 9);
/* 134:248 */     B = GG(B, C, D, A, this.X[12], 13);
/* 135:249 */     A = GG(A, B, C, D, this.X[1], 3);
/* 136:250 */     D = GG(D, A, B, C, this.X[5], 5);
/* 137:251 */     C = GG(C, D, A, B, this.X[9], 9);
/* 138:252 */     B = GG(B, C, D, A, this.X[13], 13);
/* 139:253 */     A = GG(A, B, C, D, this.X[2], 3);
/* 140:254 */     D = GG(D, A, B, C, this.X[6], 5);
/* 141:255 */     C = GG(C, D, A, B, this.X[10], 9);
/* 142:256 */     B = GG(B, C, D, A, this.X[14], 13);
/* 143:257 */     A = GG(A, B, C, D, this.X[3], 3);
/* 144:258 */     D = GG(D, A, B, C, this.X[7], 5);
/* 145:259 */     C = GG(C, D, A, B, this.X[11], 9);
/* 146:260 */     B = GG(B, C, D, A, this.X[15], 13);
/* 147:    */     
/* 148:262 */     A = HH(A, B, C, D, this.X[0], 3);
/* 149:263 */     D = HH(D, A, B, C, this.X[8], 9);
/* 150:264 */     C = HH(C, D, A, B, this.X[4], 11);
/* 151:265 */     B = HH(B, C, D, A, this.X[12], 15);
/* 152:266 */     A = HH(A, B, C, D, this.X[2], 3);
/* 153:267 */     D = HH(D, A, B, C, this.X[10], 9);
/* 154:268 */     C = HH(C, D, A, B, this.X[6], 11);
/* 155:269 */     B = HH(B, C, D, A, this.X[14], 15);
/* 156:270 */     A = HH(A, B, C, D, this.X[1], 3);
/* 157:271 */     D = HH(D, A, B, C, this.X[9], 9);
/* 158:272 */     C = HH(C, D, A, B, this.X[5], 11);
/* 159:273 */     B = HH(B, C, D, A, this.X[13], 15);
/* 160:274 */     A = HH(A, B, C, D, this.X[3], 3);
/* 161:275 */     D = HH(D, A, B, C, this.X[11], 9);
/* 162:276 */     C = HH(C, D, A, B, this.X[7], 11);
/* 163:277 */     B = HH(B, C, D, A, this.X[15], 15);
/* 164:    */     
/* 165:279 */     this.context[0] += A;
/* 166:280 */     this.context[1] += B;
/* 167:281 */     this.context[2] += C;
/* 168:282 */     this.context[3] += D;
/* 169:    */   }
/* 170:    */   
/* 171:    */   private int FF(int a, int b, int c, int d, int x, int s)
/* 172:    */   {
/* 173:288 */     int t = a + (b & c | (b ^ 0xFFFFFFFF) & d) + x;
/* 174:289 */     return t << s | t >>> 32 - s;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private int GG(int a, int b, int c, int d, int x, int s)
/* 178:    */   {
/* 179:292 */     int t = a + (b & (c | d) | c & d) + x + 1518500249;
/* 180:293 */     return t << s | t >>> 32 - s;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private int HH(int a, int b, int c, int d, int x, int s)
/* 184:    */   {
/* 185:296 */     int t = a + (b ^ c ^ d) + x + 1859775393;
/* 186:297 */     return t << s | t >>> 32 - s;
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.util.MD4
 * JD-Core Version:    0.7.0.1
 */