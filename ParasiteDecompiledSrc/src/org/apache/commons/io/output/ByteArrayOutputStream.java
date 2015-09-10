/*   1:    */ package org.apache.commons.io.output;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import java.io.SequenceInputStream;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.List;
/*  12:    */ import org.apache.commons.io.input.ClosedInputStream;
/*  13:    */ 
/*  14:    */ public class ByteArrayOutputStream
/*  15:    */   extends OutputStream
/*  16:    */ {
/*  17: 59 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*  18: 62 */   private final List<byte[]> buffers = new ArrayList();
/*  19:    */   private int currentBufferIndex;
/*  20:    */   private int filledBufferSum;
/*  21:    */   private byte[] currentBuffer;
/*  22:    */   private int count;
/*  23:    */   
/*  24:    */   public ByteArrayOutputStream()
/*  25:    */   {
/*  26: 77 */     this(1024);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ByteArrayOutputStream(int size)
/*  30:    */   {
/*  31: 88 */     if (size < 0) {
/*  32: 89 */       throw new IllegalArgumentException("Negative initial size: " + size);
/*  33:    */     }
/*  34: 92 */     synchronized (this)
/*  35:    */     {
/*  36: 93 */       needNewBuffer(size);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void needNewBuffer(int newcount)
/*  41:    */   {
/*  42:104 */     if (this.currentBufferIndex < this.buffers.size() - 1)
/*  43:    */     {
/*  44:106 */       this.filledBufferSum += this.currentBuffer.length;
/*  45:    */       
/*  46:108 */       this.currentBufferIndex += 1;
/*  47:109 */       this.currentBuffer = ((byte[])this.buffers.get(this.currentBufferIndex));
/*  48:    */     }
/*  49:    */     else
/*  50:    */     {
/*  51:    */       int newBufferSize;
/*  52:113 */       if (this.currentBuffer == null)
/*  53:    */       {
/*  54:114 */         int newBufferSize = newcount;
/*  55:115 */         this.filledBufferSum = 0;
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:117 */         newBufferSize = Math.max(this.currentBuffer.length << 1, newcount - this.filledBufferSum);
/*  60:    */         
/*  61:    */ 
/*  62:120 */         this.filledBufferSum += this.currentBuffer.length;
/*  63:    */       }
/*  64:123 */       this.currentBufferIndex += 1;
/*  65:124 */       this.currentBuffer = new byte[newBufferSize];
/*  66:125 */       this.buffers.add(this.currentBuffer);
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void write(byte[] b, int off, int len)
/*  71:    */   {
/*  72:137 */     if ((off < 0) || (off > b.length) || (len < 0) || (off + len > b.length) || (off + len < 0)) {
/*  73:142 */       throw new IndexOutOfBoundsException();
/*  74:    */     }
/*  75:143 */     if (len == 0) {
/*  76:144 */       return;
/*  77:    */     }
/*  78:146 */     synchronized (this)
/*  79:    */     {
/*  80:147 */       int newcount = this.count + len;
/*  81:148 */       int remaining = len;
/*  82:149 */       int inBufferPos = this.count - this.filledBufferSum;
/*  83:150 */       while (remaining > 0)
/*  84:    */       {
/*  85:151 */         int part = Math.min(remaining, this.currentBuffer.length - inBufferPos);
/*  86:152 */         System.arraycopy(b, off + len - remaining, this.currentBuffer, inBufferPos, part);
/*  87:153 */         remaining -= part;
/*  88:154 */         if (remaining > 0)
/*  89:    */         {
/*  90:155 */           needNewBuffer(newcount);
/*  91:156 */           inBufferPos = 0;
/*  92:    */         }
/*  93:    */       }
/*  94:159 */       this.count = newcount;
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public synchronized void write(int b)
/*  99:    */   {
/* 100:169 */     int inBufferPos = this.count - this.filledBufferSum;
/* 101:170 */     if (inBufferPos == this.currentBuffer.length)
/* 102:    */     {
/* 103:171 */       needNewBuffer(this.count + 1);
/* 104:172 */       inBufferPos = 0;
/* 105:    */     }
/* 106:174 */     this.currentBuffer[inBufferPos] = ((byte)b);
/* 107:175 */     this.count += 1;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public synchronized int write(InputStream in)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:190 */     int readCount = 0;
/* 114:191 */     int inBufferPos = this.count - this.filledBufferSum;
/* 115:192 */     int n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos);
/* 116:193 */     while (n != -1)
/* 117:    */     {
/* 118:194 */       readCount += n;
/* 119:195 */       inBufferPos += n;
/* 120:196 */       this.count += n;
/* 121:197 */       if (inBufferPos == this.currentBuffer.length)
/* 122:    */       {
/* 123:198 */         needNewBuffer(this.currentBuffer.length);
/* 124:199 */         inBufferPos = 0;
/* 125:    */       }
/* 126:201 */       n = in.read(this.currentBuffer, inBufferPos, this.currentBuffer.length - inBufferPos);
/* 127:    */     }
/* 128:203 */     return readCount;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public synchronized int size()
/* 132:    */   {
/* 133:211 */     return this.count;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void close()
/* 137:    */     throws IOException
/* 138:    */   {}
/* 139:    */   
/* 140:    */   public synchronized void reset()
/* 141:    */   {
/* 142:231 */     this.count = 0;
/* 143:232 */     this.filledBufferSum = 0;
/* 144:233 */     this.currentBufferIndex = 0;
/* 145:234 */     this.currentBuffer = ((byte[])this.buffers.get(this.currentBufferIndex));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public synchronized void writeTo(OutputStream out)
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:246 */     int remaining = this.count;
/* 152:247 */     for (byte[] buf : this.buffers)
/* 153:    */     {
/* 154:248 */       int c = Math.min(buf.length, remaining);
/* 155:249 */       out.write(buf, 0, c);
/* 156:250 */       remaining -= c;
/* 157:251 */       if (remaining == 0) {
/* 158:    */         break;
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static InputStream toBufferedInputStream(InputStream input)
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:280 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 167:281 */     output.write(input);
/* 168:282 */     return output.toBufferedInputStream();
/* 169:    */   }
/* 170:    */   
/* 171:    */   private InputStream toBufferedInputStream()
/* 172:    */   {
/* 173:296 */     int remaining = this.count;
/* 174:297 */     if (remaining == 0) {
/* 175:298 */       return new ClosedInputStream();
/* 176:    */     }
/* 177:300 */     List<ByteArrayInputStream> list = new ArrayList(this.buffers.size());
/* 178:301 */     for (byte[] buf : this.buffers)
/* 179:    */     {
/* 180:302 */       int c = Math.min(buf.length, remaining);
/* 181:303 */       list.add(new ByteArrayInputStream(buf, 0, c));
/* 182:304 */       remaining -= c;
/* 183:305 */       if (remaining == 0) {
/* 184:    */         break;
/* 185:    */       }
/* 186:    */     }
/* 187:309 */     return new SequenceInputStream(Collections.enumeration(list));
/* 188:    */   }
/* 189:    */   
/* 190:    */   public synchronized byte[] toByteArray()
/* 191:    */   {
/* 192:320 */     int remaining = this.count;
/* 193:321 */     if (remaining == 0) {
/* 194:322 */       return EMPTY_BYTE_ARRAY;
/* 195:    */     }
/* 196:324 */     byte[] newbuf = new byte[remaining];
/* 197:325 */     int pos = 0;
/* 198:326 */     for (byte[] buf : this.buffers)
/* 199:    */     {
/* 200:327 */       int c = Math.min(buf.length, remaining);
/* 201:328 */       System.arraycopy(buf, 0, newbuf, pos, c);
/* 202:329 */       pos += c;
/* 203:330 */       remaining -= c;
/* 204:331 */       if (remaining == 0) {
/* 205:    */         break;
/* 206:    */       }
/* 207:    */     }
/* 208:335 */     return newbuf;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public String toString()
/* 212:    */   {
/* 213:345 */     return new String(toByteArray());
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String toString(String enc)
/* 217:    */     throws UnsupportedEncodingException
/* 218:    */   {
/* 219:358 */     return new String(toByteArray(), enc);
/* 220:    */   }
/* 221:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.output.ByteArrayOutputStream
 * JD-Core Version:    0.7.0.1
 */