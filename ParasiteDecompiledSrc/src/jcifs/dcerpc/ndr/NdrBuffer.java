/*   1:    */ package jcifs.dcerpc.ndr;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import jcifs.util.Encdec;
/*   6:    */ 
/*   7:    */ public class NdrBuffer
/*   8:    */ {
/*   9:    */   int referent;
/*  10:    */   HashMap referents;
/*  11:    */   public byte[] buf;
/*  12:    */   public int start;
/*  13:    */   public int index;
/*  14:    */   public int length;
/*  15:    */   public NdrBuffer deferred;
/*  16:    */   
/*  17:    */   public NdrBuffer(byte[] buf, int start)
/*  18:    */   {
/*  19: 42 */     this.buf = buf;
/*  20: 43 */     this.start = (this.index = start);
/*  21: 44 */     this.length = 0;
/*  22: 45 */     this.deferred = this;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public NdrBuffer derive(int idx)
/*  26:    */   {
/*  27: 49 */     NdrBuffer nb = new NdrBuffer(this.buf, this.start);
/*  28: 50 */     nb.index = idx;
/*  29: 51 */     nb.deferred = this.deferred;
/*  30: 52 */     return nb;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void reset()
/*  34:    */   {
/*  35: 58 */     this.index = this.start;
/*  36: 59 */     this.length = 0;
/*  37: 60 */     this.deferred = this;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getIndex()
/*  41:    */   {
/*  42: 63 */     return this.index;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setIndex(int index)
/*  46:    */   {
/*  47: 66 */     this.index = index;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getCapacity()
/*  51:    */   {
/*  52: 69 */     return this.buf.length - this.start;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getTailSpace()
/*  56:    */   {
/*  57: 72 */     return this.buf.length - this.index;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public byte[] getBuffer()
/*  61:    */   {
/*  62: 75 */     return this.buf;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int align(int boundary, byte value)
/*  66:    */   {
/*  67: 78 */     int n = align(boundary);
/*  68: 79 */     int i = n;
/*  69: 80 */     while (i > 0)
/*  70:    */     {
/*  71: 81 */       this.buf[(this.index - i)] = value;
/*  72: 82 */       i--;
/*  73:    */     }
/*  74: 84 */     return n;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void writeOctetArray(byte[] b, int i, int l)
/*  78:    */   {
/*  79: 87 */     System.arraycopy(b, i, this.buf, this.index, l);
/*  80: 88 */     advance(l);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void readOctetArray(byte[] b, int i, int l)
/*  84:    */   {
/*  85: 91 */     System.arraycopy(this.buf, this.index, b, i, l);
/*  86: 92 */     advance(l);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getLength()
/*  90:    */   {
/*  91: 97 */     return this.deferred.length;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setLength(int length)
/*  95:    */   {
/*  96:100 */     this.deferred.length = length;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void advance(int n)
/* 100:    */   {
/* 101:103 */     this.index += n;
/* 102:104 */     if (this.index - this.start > this.deferred.length) {
/* 103:105 */       this.deferred.length = (this.index - this.start);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int align(int boundary)
/* 108:    */   {
/* 109:109 */     int m = boundary - 1;
/* 110:110 */     int i = this.index - this.start;
/* 111:111 */     int n = (i + m & (m ^ 0xFFFFFFFF)) - i;
/* 112:112 */     advance(n);
/* 113:113 */     return n;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void enc_ndr_small(int s)
/* 117:    */   {
/* 118:116 */     this.buf[this.index] = ((byte)(s & 0xFF));
/* 119:117 */     advance(1);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int dec_ndr_small()
/* 123:    */   {
/* 124:120 */     int val = this.buf[this.index] & 0xFF;
/* 125:121 */     advance(1);
/* 126:122 */     return val;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void enc_ndr_short(int s)
/* 130:    */   {
/* 131:125 */     align(2);
/* 132:126 */     Encdec.enc_uint16le((short)s, this.buf, this.index);
/* 133:127 */     advance(2);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int dec_ndr_short()
/* 137:    */   {
/* 138:130 */     align(2);
/* 139:131 */     int val = Encdec.dec_uint16le(this.buf, this.index);
/* 140:132 */     advance(2);
/* 141:133 */     return val;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void enc_ndr_long(int l)
/* 145:    */   {
/* 146:136 */     align(4);
/* 147:137 */     Encdec.enc_uint32le(l, this.buf, this.index);
/* 148:138 */     advance(4);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int dec_ndr_long()
/* 152:    */   {
/* 153:141 */     align(4);
/* 154:142 */     int val = Encdec.dec_uint32le(this.buf, this.index);
/* 155:143 */     advance(4);
/* 156:144 */     return val;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void enc_ndr_hyper(long h)
/* 160:    */   {
/* 161:147 */     align(8);
/* 162:148 */     Encdec.enc_uint64le(h, this.buf, this.index);
/* 163:149 */     advance(8);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public long dec_ndr_hyper()
/* 167:    */   {
/* 168:152 */     align(8);
/* 169:153 */     long val = Encdec.dec_uint64le(this.buf, this.index);
/* 170:154 */     advance(8);
/* 171:155 */     return val;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void enc_ndr_string(String s)
/* 175:    */   {
/* 176:160 */     align(4);
/* 177:161 */     int i = this.index;
/* 178:162 */     int len = s.length();
/* 179:163 */     Encdec.enc_uint32le(len + 1, this.buf, i);i += 4;
/* 180:164 */     Encdec.enc_uint32le(0, this.buf, i);i += 4;
/* 181:165 */     Encdec.enc_uint32le(len + 1, this.buf, i);i += 4;
/* 182:    */     try
/* 183:    */     {
/* 184:167 */       System.arraycopy(s.getBytes("UTF-16LE"), 0, this.buf, i, len * 2);
/* 185:    */     }
/* 186:    */     catch (UnsupportedEncodingException uee) {}
/* 187:170 */     i += len * 2;
/* 188:171 */     this.buf[(i++)] = 0;
/* 189:172 */     this.buf[(i++)] = 0;
/* 190:173 */     advance(i - this.index);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String dec_ndr_string()
/* 194:    */     throws NdrException
/* 195:    */   {
/* 196:176 */     align(4);
/* 197:177 */     int i = this.index;
/* 198:178 */     String val = null;
/* 199:179 */     int len = Encdec.dec_uint32le(this.buf, i);
/* 200:180 */     i += 12;
/* 201:181 */     if (len != 0)
/* 202:    */     {
/* 203:182 */       len--;
/* 204:183 */       int size = len * 2;
/* 205:    */       try
/* 206:    */       {
/* 207:185 */         if ((size < 0) || (size > 65535)) {
/* 208:185 */           throw new NdrException("invalid array conformance");
/* 209:    */         }
/* 210:186 */         val = new String(this.buf, i, size, "UTF-16LE");
/* 211:187 */         i += size + 2;
/* 212:    */       }
/* 213:    */       catch (UnsupportedEncodingException uee) {}
/* 214:    */     }
/* 215:191 */     advance(i - this.index);
/* 216:192 */     return val;
/* 217:    */   }
/* 218:    */   
/* 219:    */   private int getDceReferent(Object obj)
/* 220:    */   {
/* 221:197 */     if (this.referents == null)
/* 222:    */     {
/* 223:198 */       this.referents = new HashMap();
/* 224:199 */       this.referent = 1;
/* 225:    */     }
/* 226:    */     Entry e;
/* 227:202 */     if ((e = (Entry)this.referents.get(obj)) == null)
/* 228:    */     {
/* 229:203 */       e = new Entry();
/* 230:204 */       e.referent = (this.referent++);
/* 231:205 */       e.obj = obj;
/* 232:206 */       this.referents.put(obj, e);
/* 233:    */     }
/* 234:209 */     return e.referent;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void enc_ndr_referent(Object obj, int type)
/* 238:    */   {
/* 239:212 */     if (obj == null)
/* 240:    */     {
/* 241:213 */       enc_ndr_long(0);
/* 242:214 */       return;
/* 243:    */     }
/* 244:216 */     switch (type)
/* 245:    */     {
/* 246:    */     case 1: 
/* 247:    */     case 3: 
/* 248:219 */       enc_ndr_long(System.identityHashCode(obj));
/* 249:220 */       return;
/* 250:    */     case 2: 
/* 251:222 */       enc_ndr_long(getDceReferent(obj));
/* 252:223 */       return;
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String toString()
/* 257:    */   {
/* 258:228 */     return "start=" + this.start + ",index=" + this.index + ",length=" + getLength();
/* 259:    */   }
/* 260:    */   
/* 261:    */   static class Entry
/* 262:    */   {
/* 263:    */     int referent;
/* 264:    */     Object obj;
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.ndr.NdrBuffer
 * JD-Core Version:    0.7.0.1
 */