/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ 
/*   7:    */ final class SerializerTraceWriter
/*   8:    */   extends Writer
/*   9:    */   implements WriterChain
/*  10:    */ {
/*  11:    */   private final Writer m_writer;
/*  12:    */   private final SerializerTrace m_tracer;
/*  13:    */   private int buf_length;
/*  14:    */   private byte[] buf;
/*  15:    */   private int count;
/*  16:    */   
/*  17:    */   private void setBufferSize(int size)
/*  18:    */   {
/*  19: 81 */     this.buf = new byte[size + 3];
/*  20: 82 */     this.buf_length = size;
/*  21: 83 */     this.count = 0;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public SerializerTraceWriter(Writer out, SerializerTrace tracer)
/*  25:    */   {
/*  26: 99 */     this.m_writer = out;
/*  27:100 */     this.m_tracer = tracer;
/*  28:101 */     setBufferSize(1024);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private void flushBuffer()
/*  32:    */     throws IOException
/*  33:    */   {
/*  34:116 */     if (this.count > 0)
/*  35:    */     {
/*  36:118 */       char[] chars = new char[this.count];
/*  37:119 */       for (int i = 0; i < this.count; i++) {
/*  38:120 */         chars[i] = ((char)this.buf[i]);
/*  39:    */       }
/*  40:122 */       if (this.m_tracer != null) {
/*  41:123 */         this.m_tracer.fireGenerateEvent(12, chars, 0, chars.length);
/*  42:    */       }
/*  43:129 */       this.count = 0;
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void flush()
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:140 */     if (this.m_writer != null) {
/*  51:141 */       this.m_writer.flush();
/*  52:    */     }
/*  53:144 */     flushBuffer();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void close()
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:154 */     if (this.m_writer != null) {
/*  60:155 */       this.m_writer.close();
/*  61:    */     }
/*  62:158 */     flushBuffer();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void write(int c)
/*  66:    */     throws IOException
/*  67:    */   {
/*  68:176 */     if (this.m_writer != null) {
/*  69:177 */       this.m_writer.write(c);
/*  70:    */     }
/*  71:184 */     if (this.count >= this.buf_length) {
/*  72:185 */       flushBuffer();
/*  73:    */     }
/*  74:187 */     if (c < 128)
/*  75:    */     {
/*  76:189 */       this.buf[(this.count++)] = ((byte)c);
/*  77:    */     }
/*  78:191 */     else if (c < 2048)
/*  79:    */     {
/*  80:193 */       this.buf[(this.count++)] = ((byte)(192 + (c >> 6)));
/*  81:194 */       this.buf[(this.count++)] = ((byte)(128 + (c & 0x3F)));
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:198 */       this.buf[(this.count++)] = ((byte)(224 + (c >> 12)));
/*  86:199 */       this.buf[(this.count++)] = ((byte)(128 + (c >> 6 & 0x3F)));
/*  87:200 */       this.buf[(this.count++)] = ((byte)(128 + (c & 0x3F)));
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void write(char[] chars, int start, int length)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:219 */     if (this.m_writer != null) {
/*  95:220 */       this.m_writer.write(chars, start, length);
/*  96:    */     }
/*  97:223 */     int lengthx3 = (length << 1) + length;
/*  98:225 */     if (lengthx3 >= this.buf_length)
/*  99:    */     {
/* 100:232 */       flushBuffer();
/* 101:233 */       setBufferSize(2 * lengthx3);
/* 102:    */     }
/* 103:237 */     if (lengthx3 > this.buf_length - this.count) {
/* 104:239 */       flushBuffer();
/* 105:    */     }
/* 106:242 */     int n = length + start;
/* 107:243 */     for (int i = start; i < n; i++)
/* 108:    */     {
/* 109:245 */       char c = chars[i];
/* 110:247 */       if (c < '')
/* 111:    */       {
/* 112:248 */         this.buf[(this.count++)] = ((byte)c);
/* 113:    */       }
/* 114:249 */       else if (c < 'ࠀ')
/* 115:    */       {
/* 116:251 */         this.buf[(this.count++)] = ((byte)(192 + (c >> '\006')));
/* 117:252 */         this.buf[(this.count++)] = ((byte)('' + (c & 0x3F)));
/* 118:    */       }
/* 119:    */       else
/* 120:    */       {
/* 121:256 */         this.buf[(this.count++)] = ((byte)(224 + (c >> '\f')));
/* 122:257 */         this.buf[(this.count++)] = ((byte)(128 + (c >> '\006' & 0x3F)));
/* 123:258 */         this.buf[(this.count++)] = ((byte)('' + (c & 0x3F)));
/* 124:    */       }
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void write(String s)
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:274 */     if (this.m_writer != null) {
/* 132:275 */       this.m_writer.write(s);
/* 133:    */     }
/* 134:278 */     int length = s.length();
/* 135:    */     
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:284 */     int lengthx3 = (length << 1) + length;
/* 141:286 */     if (lengthx3 >= this.buf_length)
/* 142:    */     {
/* 143:293 */       flushBuffer();
/* 144:294 */       setBufferSize(2 * lengthx3);
/* 145:    */     }
/* 146:297 */     if (lengthx3 > this.buf_length - this.count) {
/* 147:299 */       flushBuffer();
/* 148:    */     }
/* 149:302 */     for (int i = 0; i < length; i++)
/* 150:    */     {
/* 151:304 */       char c = s.charAt(i);
/* 152:306 */       if (c < '')
/* 153:    */       {
/* 154:307 */         this.buf[(this.count++)] = ((byte)c);
/* 155:    */       }
/* 156:308 */       else if (c < 'ࠀ')
/* 157:    */       {
/* 158:310 */         this.buf[(this.count++)] = ((byte)(192 + (c >> '\006')));
/* 159:311 */         this.buf[(this.count++)] = ((byte)('' + (c & 0x3F)));
/* 160:    */       }
/* 161:    */       else
/* 162:    */       {
/* 163:315 */         this.buf[(this.count++)] = ((byte)(224 + (c >> '\f')));
/* 164:316 */         this.buf[(this.count++)] = ((byte)(128 + (c >> '\006' & 0x3F)));
/* 165:317 */         this.buf[(this.count++)] = ((byte)('' + (c & 0x3F)));
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Writer getWriter()
/* 171:    */   {
/* 172:327 */     return this.m_writer;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public OutputStream getOutputStream()
/* 176:    */   {
/* 177:336 */     OutputStream retval = null;
/* 178:337 */     if ((this.m_writer instanceof WriterChain)) {
/* 179:338 */       retval = ((WriterChain)this.m_writer).getOutputStream();
/* 180:    */     }
/* 181:339 */     return retval;
/* 182:    */   }
/* 183:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.SerializerTraceWriter
 * JD-Core Version:    0.7.0.1
 */