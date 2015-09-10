/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.commons.logging.Log;
/*   6:    */ import org.apache.commons.logging.LogFactory;
/*   7:    */ 
/*   8:    */ public class QuotedPrintableInputStream
/*   9:    */   extends InputStream
/*  10:    */ {
/*  11: 32 */   private static Log log = LogFactory.getLog(QuotedPrintableInputStream.class);
/*  12:    */   private InputStream stream;
/*  13: 35 */   ByteQueue byteq = new ByteQueue();
/*  14: 36 */   ByteQueue pushbackq = new ByteQueue();
/*  15: 37 */   private byte state = 0;
/*  16: 38 */   private boolean closed = false;
/*  17:    */   
/*  18:    */   public QuotedPrintableInputStream(InputStream stream)
/*  19:    */   {
/*  20: 41 */     this.stream = stream;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void close()
/*  24:    */     throws IOException
/*  25:    */   {
/*  26: 52 */     this.closed = true;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int read()
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 57 */     if (this.closed) {
/*  33: 58 */       throw new IOException("QuotedPrintableInputStream has been closed");
/*  34:    */     }
/*  35: 60 */     fillBuffer();
/*  36: 61 */     if (this.byteq.count() == 0) {
/*  37: 62 */       return -1;
/*  38:    */     }
/*  39: 64 */     byte val = this.byteq.dequeue();
/*  40: 65 */     if (val >= 0) {
/*  41: 66 */       return val;
/*  42:    */     }
/*  43: 68 */     return val & 0xFF;
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void populatePushbackQueue()
/*  47:    */     throws IOException
/*  48:    */   {
/*  49: 84 */     if (this.pushbackq.count() != 0) {
/*  50: 85 */       return;
/*  51:    */     }
/*  52:    */     for (;;)
/*  53:    */     {
/*  54: 88 */       int i = this.stream.read();
/*  55: 89 */       switch (i)
/*  56:    */       {
/*  57:    */       case -1: 
/*  58: 92 */         this.pushbackq.clear();
/*  59: 93 */         return;
/*  60:    */       case 9: 
/*  61:    */       case 32: 
/*  62: 96 */         this.pushbackq.enqueue((byte)i);
/*  63: 97 */         break;
/*  64:    */       case 10: 
/*  65:    */       case 13: 
/*  66:100 */         this.pushbackq.clear();
/*  67:101 */         this.pushbackq.enqueue((byte)i);
/*  68:102 */         return;
/*  69:    */       default: 
/*  70:104 */         this.pushbackq.enqueue((byte)i);
/*  71:105 */         return;
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void fillBuffer()
/*  77:    */     throws IOException
/*  78:    */   {
/*  79:119 */     byte msdChar = 0;
/*  80:120 */     while (this.byteq.count() == 0)
/*  81:    */     {
/*  82:121 */       if (this.pushbackq.count() == 0)
/*  83:    */       {
/*  84:122 */         populatePushbackQueue();
/*  85:123 */         if (this.pushbackq.count() == 0) {
/*  86:124 */           return;
/*  87:    */         }
/*  88:    */       }
/*  89:127 */       byte b = this.pushbackq.dequeue();
/*  90:129 */       switch (this.state)
/*  91:    */       {
/*  92:    */       case 0: 
/*  93:131 */         if (b != 61) {
/*  94:132 */           this.byteq.enqueue(b);
/*  95:    */         } else {
/*  96:135 */           this.state = 1;
/*  97:    */         }
/*  98:136 */         break;
/*  99:    */       case 1: 
/* 100:139 */         if (b == 13)
/* 101:    */         {
/* 102:140 */           this.state = 2;
/* 103:    */         }
/* 104:142 */         else if (((b >= 48) && (b <= 57)) || ((b >= 65) && (b <= 70)) || ((b >= 97) && (b <= 102)))
/* 105:    */         {
/* 106:143 */           this.state = 3;
/* 107:144 */           msdChar = b;
/* 108:    */         }
/* 109:146 */         else if (b == 61)
/* 110:    */         {
/* 111:151 */           if (log.isWarnEnabled()) {
/* 112:152 */             log.warn("Malformed MIME; got ==");
/* 113:    */           }
/* 114:154 */           this.byteq.enqueue((byte)61);
/* 115:    */         }
/* 116:    */         else
/* 117:    */         {
/* 118:157 */           if (log.isWarnEnabled()) {
/* 119:158 */             log.warn("Malformed MIME; expected \\r or [0-9A-Z], got " + b);
/* 120:    */           }
/* 121:161 */           this.state = 0;
/* 122:162 */           this.byteq.enqueue((byte)61);
/* 123:163 */           this.byteq.enqueue(b);
/* 124:    */         }
/* 125:164 */         break;
/* 126:    */       case 2: 
/* 127:167 */         if (b == 10)
/* 128:    */         {
/* 129:168 */           this.state = 0;
/* 130:    */         }
/* 131:    */         else
/* 132:    */         {
/* 133:171 */           if (log.isWarnEnabled()) {
/* 134:172 */             log.warn("Malformed MIME; expected 10, got " + b);
/* 135:    */           }
/* 136:175 */           this.state = 0;
/* 137:176 */           this.byteq.enqueue((byte)61);
/* 138:177 */           this.byteq.enqueue((byte)13);
/* 139:178 */           this.byteq.enqueue(b);
/* 140:    */         }
/* 141:179 */         break;
/* 142:    */       case 3: 
/* 143:182 */         if (((b >= 48) && (b <= 57)) || ((b >= 65) && (b <= 70)) || ((b >= 97) && (b <= 102)))
/* 144:    */         {
/* 145:183 */           byte msd = asciiCharToNumericValue(msdChar);
/* 146:184 */           byte low = asciiCharToNumericValue(b);
/* 147:185 */           this.state = 0;
/* 148:186 */           this.byteq.enqueue((byte)(msd << 4 | low));
/* 149:    */         }
/* 150:    */         else
/* 151:    */         {
/* 152:189 */           if (log.isWarnEnabled()) {
/* 153:190 */             log.warn("Malformed MIME; expected [0-9A-Z], got " + b);
/* 154:    */           }
/* 155:193 */           this.state = 0;
/* 156:194 */           this.byteq.enqueue((byte)61);
/* 157:195 */           this.byteq.enqueue(msdChar);
/* 158:196 */           this.byteq.enqueue(b);
/* 159:    */         }
/* 160:197 */         break;
/* 161:    */       default: 
/* 162:200 */         log.error("Illegal state: " + this.state);
/* 163:201 */         this.state = 0;
/* 164:202 */         this.byteq.enqueue(b);
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   private byte asciiCharToNumericValue(byte c)
/* 170:    */   {
/* 171:214 */     if ((c >= 48) && (c <= 57)) {
/* 172:215 */       return (byte)(c - 48);
/* 173:    */     }
/* 174:216 */     if ((c >= 65) && (c <= 90)) {
/* 175:217 */       return (byte)(10 + (c - 65));
/* 176:    */     }
/* 177:218 */     if ((c >= 97) && (c <= 122)) {
/* 178:219 */       return (byte)(10 + (c - 97));
/* 179:    */     }
/* 180:225 */     throw new IllegalArgumentException((char)c + " is not a hexadecimal digit");
/* 181:    */   }
/* 182:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.QuotedPrintableInputStream
 * JD-Core Version:    0.7.0.1
 */