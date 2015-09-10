/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.commons.io.ByteOrderMark;
/*   8:    */ 
/*   9:    */ public class BOMInputStream
/*  10:    */   extends ProxyInputStream
/*  11:    */ {
/*  12:    */   private final boolean include;
/*  13:    */   private final List<ByteOrderMark> boms;
/*  14:    */   private ByteOrderMark byteOrderMark;
/*  15:    */   private int[] firstBytes;
/*  16:    */   private int fbLength;
/*  17:    */   private int fbIndex;
/*  18:    */   private int markFbIndex;
/*  19:    */   private boolean markedAtStart;
/*  20:    */   
/*  21:    */   public BOMInputStream(InputStream delegate)
/*  22:    */   {
/*  23: 91 */     this(delegate, false, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
/*  24:    */   }
/*  25:    */   
/*  26:    */   public BOMInputStream(InputStream delegate, boolean include)
/*  27:    */   {
/*  28:102 */     this(delegate, include, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
/*  29:    */   }
/*  30:    */   
/*  31:    */   public BOMInputStream(InputStream delegate, ByteOrderMark... boms)
/*  32:    */   {
/*  33:112 */     this(delegate, false, boms);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public BOMInputStream(InputStream delegate, boolean include, ByteOrderMark... boms)
/*  37:    */   {
/*  38:124 */     super(delegate);
/*  39:125 */     if ((boms == null) || (boms.length == 0)) {
/*  40:126 */       throw new IllegalArgumentException("No BOMs specified");
/*  41:    */     }
/*  42:128 */     this.include = include;
/*  43:129 */     this.boms = Arrays.asList(boms);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean hasBOM()
/*  47:    */     throws IOException
/*  48:    */   {
/*  49:140 */     return getBOM() != null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean hasBOM(ByteOrderMark bom)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55:154 */     if (!this.boms.contains(bom)) {
/*  56:155 */       throw new IllegalArgumentException("Stream not configure to detect " + bom);
/*  57:    */     }
/*  58:157 */     return (this.byteOrderMark != null) && (getBOM().equals(bom));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ByteOrderMark getBOM()
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:167 */     if (this.firstBytes == null)
/*  65:    */     {
/*  66:168 */       int max = 0;
/*  67:169 */       for (ByteOrderMark bom : this.boms) {
/*  68:170 */         max = Math.max(max, bom.length());
/*  69:    */       }
/*  70:172 */       this.firstBytes = new int[max];
/*  71:173 */       for (int i = 0; i < this.firstBytes.length; i++)
/*  72:    */       {
/*  73:174 */         this.firstBytes[i] = this.in.read();
/*  74:175 */         this.fbLength += 1;
/*  75:176 */         if (this.firstBytes[i] < 0) {
/*  76:    */           break;
/*  77:    */         }
/*  78:180 */         this.byteOrderMark = find();
/*  79:181 */         if (this.byteOrderMark != null)
/*  80:    */         {
/*  81:182 */           if (this.include) {
/*  82:    */             break;
/*  83:    */           }
/*  84:183 */           this.fbLength = 0; break;
/*  85:    */         }
/*  86:    */       }
/*  87:    */     }
/*  88:189 */     return this.byteOrderMark;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getBOMCharsetName()
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:200 */     getBOM();
/*  95:201 */     return this.byteOrderMark == null ? null : this.byteOrderMark.getCharsetName();
/*  96:    */   }
/*  97:    */   
/*  98:    */   private int readFirstBytes()
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:213 */     getBOM();
/* 102:214 */     return this.fbIndex < this.fbLength ? this.firstBytes[(this.fbIndex++)] : -1;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private ByteOrderMark find()
/* 106:    */   {
/* 107:223 */     for (ByteOrderMark bom : this.boms) {
/* 108:224 */       if (matches(bom)) {
/* 109:225 */         return bom;
/* 110:    */       }
/* 111:    */     }
/* 112:228 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private boolean matches(ByteOrderMark bom)
/* 116:    */   {
/* 117:238 */     if (bom.length() != this.fbLength) {
/* 118:239 */       return false;
/* 119:    */     }
/* 120:241 */     for (int i = 0; i < bom.length(); i++) {
/* 121:242 */       if (bom.get(i) != this.firstBytes[i]) {
/* 122:243 */         return false;
/* 123:    */       }
/* 124:    */     }
/* 125:246 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int read()
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:261 */     int b = readFirstBytes();
/* 132:262 */     return b >= 0 ? b : this.in.read();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int read(byte[] buf, int off, int len)
/* 136:    */     throws IOException
/* 137:    */   {
/* 138:276 */     int firstCount = 0;
/* 139:277 */     int b = 0;
/* 140:278 */     while ((len > 0) && (b >= 0))
/* 141:    */     {
/* 142:279 */       b = readFirstBytes();
/* 143:280 */       if (b >= 0)
/* 144:    */       {
/* 145:281 */         buf[(off++)] = ((byte)(b & 0xFF));
/* 146:282 */         len--;
/* 147:283 */         firstCount++;
/* 148:    */       }
/* 149:    */     }
/* 150:286 */     int secondCount = this.in.read(buf, off, len);
/* 151:287 */     return secondCount < 0 ? -1 : firstCount > 0 ? firstCount : firstCount + secondCount;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int read(byte[] buf)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:300 */     return read(buf, 0, buf.length);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public synchronized void mark(int readlimit)
/* 161:    */   {
/* 162:309 */     this.markFbIndex = this.fbIndex;
/* 163:310 */     this.markedAtStart = (this.firstBytes == null);
/* 164:311 */     this.in.mark(readlimit);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public synchronized void reset()
/* 168:    */     throws IOException
/* 169:    */   {
/* 170:320 */     this.fbIndex = this.markFbIndex;
/* 171:321 */     if (this.markedAtStart) {
/* 172:322 */       this.firstBytes = null;
/* 173:    */     }
/* 174:325 */     this.in.reset();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public long skip(long n)
/* 178:    */     throws IOException
/* 179:    */   {
/* 180:337 */     while ((n > 0L) && (readFirstBytes() >= 0)) {
/* 181:338 */       n -= 1L;
/* 182:    */     }
/* 183:340 */     return this.in.skip(n);
/* 184:    */   }
/* 185:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.BOMInputStream
 * JD-Core Version:    0.7.0.1
 */