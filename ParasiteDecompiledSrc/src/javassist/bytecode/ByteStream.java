/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ 
/*   6:    */ final class ByteStream
/*   7:    */   extends OutputStream
/*   8:    */ {
/*   9:    */   private byte[] buf;
/*  10:    */   private int count;
/*  11:    */   
/*  12:    */   public ByteStream()
/*  13:    */   {
/*  14: 25 */     this(32);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ByteStream(int size)
/*  18:    */   {
/*  19: 28 */     this.buf = new byte[size];
/*  20: 29 */     this.count = 0;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int getPos()
/*  24:    */   {
/*  25: 32 */     return this.count;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int size()
/*  29:    */   {
/*  30: 33 */     return this.count;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void writeBlank(int len)
/*  34:    */   {
/*  35: 36 */     enlarge(len);
/*  36: 37 */     this.count += len;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void write(byte[] data)
/*  40:    */   {
/*  41: 41 */     write(data, 0, data.length);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void write(byte[] data, int off, int len)
/*  45:    */   {
/*  46: 45 */     enlarge(len);
/*  47: 46 */     System.arraycopy(data, off, this.buf, this.count, len);
/*  48: 47 */     this.count += len;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void write(int b)
/*  52:    */   {
/*  53: 51 */     enlarge(1);
/*  54: 52 */     int oldCount = this.count;
/*  55: 53 */     this.buf[oldCount] = ((byte)b);
/*  56: 54 */     this.count = (oldCount + 1);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void writeShort(int s)
/*  60:    */   {
/*  61: 58 */     enlarge(2);
/*  62: 59 */     int oldCount = this.count;
/*  63: 60 */     this.buf[oldCount] = ((byte)(s >>> 8));
/*  64: 61 */     this.buf[(oldCount + 1)] = ((byte)s);
/*  65: 62 */     this.count = (oldCount + 2);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void writeInt(int i)
/*  69:    */   {
/*  70: 66 */     enlarge(4);
/*  71: 67 */     int oldCount = this.count;
/*  72: 68 */     this.buf[oldCount] = ((byte)(i >>> 24));
/*  73: 69 */     this.buf[(oldCount + 1)] = ((byte)(i >>> 16));
/*  74: 70 */     this.buf[(oldCount + 2)] = ((byte)(i >>> 8));
/*  75: 71 */     this.buf[(oldCount + 3)] = ((byte)i);
/*  76: 72 */     this.count = (oldCount + 4);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void writeLong(long i)
/*  80:    */   {
/*  81: 76 */     enlarge(8);
/*  82: 77 */     int oldCount = this.count;
/*  83: 78 */     this.buf[oldCount] = ((byte)(int)(i >>> 56));
/*  84: 79 */     this.buf[(oldCount + 1)] = ((byte)(int)(i >>> 48));
/*  85: 80 */     this.buf[(oldCount + 2)] = ((byte)(int)(i >>> 40));
/*  86: 81 */     this.buf[(oldCount + 3)] = ((byte)(int)(i >>> 32));
/*  87: 82 */     this.buf[(oldCount + 4)] = ((byte)(int)(i >>> 24));
/*  88: 83 */     this.buf[(oldCount + 5)] = ((byte)(int)(i >>> 16));
/*  89: 84 */     this.buf[(oldCount + 6)] = ((byte)(int)(i >>> 8));
/*  90: 85 */     this.buf[(oldCount + 7)] = ((byte)(int)i);
/*  91: 86 */     this.count = (oldCount + 8);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void writeFloat(float v)
/*  95:    */   {
/*  96: 90 */     writeInt(Float.floatToIntBits(v));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void writeDouble(double v)
/* 100:    */   {
/* 101: 94 */     writeLong(Double.doubleToLongBits(v));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void writeUTF(String s)
/* 105:    */   {
/* 106: 98 */     int sLen = s.length();
/* 107: 99 */     int pos = this.count;
/* 108:100 */     enlarge(sLen + 2);
/* 109:    */     
/* 110:102 */     byte[] buffer = this.buf;
/* 111:103 */     buffer[(pos++)] = ((byte)(sLen >>> 8));
/* 112:104 */     buffer[(pos++)] = ((byte)sLen);
/* 113:105 */     for (int i = 0; i < sLen; i++)
/* 114:    */     {
/* 115:106 */       char c = s.charAt(i);
/* 116:107 */       if (('\001' <= c) && (c <= ''))
/* 117:    */       {
/* 118:108 */         buffer[(pos++)] = ((byte)c);
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:110 */         writeUTF2(s, sLen, i);
/* 123:111 */         return;
/* 124:    */       }
/* 125:    */     }
/* 126:115 */     this.count = pos;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private void writeUTF2(String s, int sLen, int offset)
/* 130:    */   {
/* 131:119 */     int size = sLen;
/* 132:120 */     for (int i = offset; i < sLen; i++)
/* 133:    */     {
/* 134:121 */       int c = s.charAt(i);
/* 135:122 */       if (c > 2047) {
/* 136:123 */         size += 2;
/* 137:124 */       } else if ((c == 0) || (c > 127)) {
/* 138:125 */         size++;
/* 139:    */       }
/* 140:    */     }
/* 141:128 */     if (size > 65535) {
/* 142:129 */       throw new RuntimeException("encoded string too long: " + sLen + size + " bytes");
/* 143:    */     }
/* 144:132 */     enlarge(size + 2);
/* 145:133 */     int pos = this.count;
/* 146:134 */     byte[] buffer = this.buf;
/* 147:135 */     buffer[pos] = ((byte)(size >>> 8));
/* 148:136 */     buffer[(pos + 1)] = ((byte)size);
/* 149:137 */     pos += 2 + offset;
/* 150:138 */     for (int j = offset; j < sLen; j++)
/* 151:    */     {
/* 152:139 */       int c = s.charAt(j);
/* 153:140 */       if ((1 <= c) && (c <= 127))
/* 154:    */       {
/* 155:141 */         buffer[(pos++)] = ((byte)c);
/* 156:    */       }
/* 157:142 */       else if (c > 2047)
/* 158:    */       {
/* 159:143 */         buffer[pos] = ((byte)(0xE0 | c >> 12 & 0xF));
/* 160:144 */         buffer[(pos + 1)] = ((byte)(0x80 | c >> 6 & 0x3F));
/* 161:145 */         buffer[(pos + 2)] = ((byte)(0x80 | c & 0x3F));
/* 162:146 */         pos += 3;
/* 163:    */       }
/* 164:    */       else
/* 165:    */       {
/* 166:149 */         buffer[pos] = ((byte)(0xC0 | c >> 6 & 0x1F));
/* 167:150 */         buffer[(pos + 1)] = ((byte)(0x80 | c & 0x3F));
/* 168:151 */         pos += 2;
/* 169:    */       }
/* 170:    */     }
/* 171:155 */     this.count = pos;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void write(int pos, int value)
/* 175:    */   {
/* 176:159 */     this.buf[pos] = ((byte)value);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void writeShort(int pos, int value)
/* 180:    */   {
/* 181:163 */     this.buf[pos] = ((byte)(value >>> 8));
/* 182:164 */     this.buf[(pos + 1)] = ((byte)value);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void writeInt(int pos, int value)
/* 186:    */   {
/* 187:168 */     this.buf[pos] = ((byte)(value >>> 24));
/* 188:169 */     this.buf[(pos + 1)] = ((byte)(value >>> 16));
/* 189:170 */     this.buf[(pos + 2)] = ((byte)(value >>> 8));
/* 190:171 */     this.buf[(pos + 3)] = ((byte)value);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public byte[] toByteArray()
/* 194:    */   {
/* 195:175 */     byte[] buf2 = new byte[this.count];
/* 196:176 */     System.arraycopy(this.buf, 0, buf2, 0, this.count);
/* 197:177 */     return buf2;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void writeTo(OutputStream out)
/* 201:    */     throws IOException
/* 202:    */   {
/* 203:181 */     out.write(this.buf, 0, this.count);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void enlarge(int delta)
/* 207:    */   {
/* 208:185 */     int newCount = this.count + delta;
/* 209:186 */     if (newCount > this.buf.length)
/* 210:    */     {
/* 211:187 */       int newLen = this.buf.length << 1;
/* 212:188 */       byte[] newBuf = new byte[newLen > newCount ? newLen : newCount];
/* 213:189 */       System.arraycopy(this.buf, 0, newBuf, 0, this.count);
/* 214:190 */       this.buf = newBuf;
/* 215:    */     }
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ByteStream
 * JD-Core Version:    0.7.0.1
 */