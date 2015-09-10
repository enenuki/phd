/*   1:    */ package org.apache.james.mime4j.codec;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ 
/*   7:    */ final class QuotedPrintableEncoder
/*   8:    */ {
/*   9:    */   private static final byte TAB = 9;
/*  10:    */   private static final byte SPACE = 32;
/*  11:    */   private static final byte EQUALS = 61;
/*  12:    */   private static final byte CR = 13;
/*  13:    */   private static final byte LF = 10;
/*  14:    */   private static final byte QUOTED_PRINTABLE_LAST_PLAIN = 126;
/*  15:    */   private static final int QUOTED_PRINTABLE_MAX_LINE_LENGTH = 76;
/*  16:    */   private static final int QUOTED_PRINTABLE_OCTETS_PER_ESCAPE = 3;
/*  17: 35 */   private static final byte[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
/*  18:    */   private final byte[] inBuffer;
/*  19:    */   private final byte[] outBuffer;
/*  20:    */   private final boolean binary;
/*  21:    */   private boolean pendingSpace;
/*  22:    */   private boolean pendingTab;
/*  23:    */   private boolean pendingCR;
/*  24:    */   private int nextSoftBreak;
/*  25:    */   private int outputIndex;
/*  26:    */   private OutputStream out;
/*  27:    */   
/*  28:    */   public QuotedPrintableEncoder(int bufferSize, boolean binary)
/*  29:    */   {
/*  30: 50 */     this.inBuffer = new byte[bufferSize];
/*  31: 51 */     this.outBuffer = new byte[3 * bufferSize];
/*  32: 52 */     this.outputIndex = 0;
/*  33: 53 */     this.nextSoftBreak = 77;
/*  34: 54 */     this.out = null;
/*  35: 55 */     this.binary = binary;
/*  36: 56 */     this.pendingSpace = false;
/*  37: 57 */     this.pendingTab = false;
/*  38: 58 */     this.pendingCR = false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   void initEncoding(OutputStream out)
/*  42:    */   {
/*  43: 62 */     this.out = out;
/*  44: 63 */     this.pendingSpace = false;
/*  45: 64 */     this.pendingTab = false;
/*  46: 65 */     this.pendingCR = false;
/*  47: 66 */     this.nextSoftBreak = 77;
/*  48:    */   }
/*  49:    */   
/*  50:    */   void encodeChunk(byte[] buffer, int off, int len)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53: 70 */     for (int inputIndex = off; inputIndex < len + off; inputIndex++) {
/*  54: 71 */       encode(buffer[inputIndex]);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   void completeEncoding()
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 76 */     writePending();
/*  62: 77 */     flushOutput();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void encode(InputStream in, OutputStream out)
/*  66:    */     throws IOException
/*  67:    */   {
/*  68: 81 */     initEncoding(out);
/*  69:    */     int inputLength;
/*  70: 83 */     while ((inputLength = in.read(this.inBuffer)) > -1) {
/*  71: 84 */       encodeChunk(this.inBuffer, 0, inputLength);
/*  72:    */     }
/*  73: 86 */     completeEncoding();
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void writePending()
/*  77:    */     throws IOException
/*  78:    */   {
/*  79: 90 */     if (this.pendingSpace) {
/*  80: 91 */       plain((byte)32);
/*  81: 92 */     } else if (this.pendingTab) {
/*  82: 93 */       plain((byte)9);
/*  83: 94 */     } else if (this.pendingCR) {
/*  84: 95 */       plain((byte)13);
/*  85:    */     }
/*  86: 97 */     clearPending();
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void clearPending()
/*  90:    */     throws IOException
/*  91:    */   {
/*  92:101 */     this.pendingSpace = false;
/*  93:102 */     this.pendingTab = false;
/*  94:103 */     this.pendingCR = false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void encode(byte next)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:107 */     if (next == 10)
/* 101:    */     {
/* 102:108 */       if (this.binary)
/* 103:    */       {
/* 104:109 */         writePending();
/* 105:110 */         escape(next);
/* 106:    */       }
/* 107:112 */       else if (this.pendingCR)
/* 108:    */       {
/* 109:115 */         if (this.pendingSpace) {
/* 110:116 */           escape((byte)32);
/* 111:117 */         } else if (this.pendingTab) {
/* 112:118 */           escape((byte)9);
/* 113:    */         }
/* 114:120 */         lineBreak();
/* 115:121 */         clearPending();
/* 116:    */       }
/* 117:    */       else
/* 118:    */       {
/* 119:123 */         writePending();
/* 120:124 */         plain(next);
/* 121:    */       }
/* 122:    */     }
/* 123:127 */     else if (next == 13)
/* 124:    */     {
/* 125:128 */       if (this.binary) {
/* 126:129 */         escape(next);
/* 127:    */       } else {
/* 128:131 */         this.pendingCR = true;
/* 129:    */       }
/* 130:    */     }
/* 131:    */     else
/* 132:    */     {
/* 133:134 */       writePending();
/* 134:135 */       if (next == 32)
/* 135:    */       {
/* 136:136 */         if (this.binary) {
/* 137:137 */           escape(next);
/* 138:    */         } else {
/* 139:139 */           this.pendingSpace = true;
/* 140:    */         }
/* 141:    */       }
/* 142:141 */       else if (next == 9)
/* 143:    */       {
/* 144:142 */         if (this.binary) {
/* 145:143 */           escape(next);
/* 146:    */         } else {
/* 147:145 */           this.pendingTab = true;
/* 148:    */         }
/* 149:    */       }
/* 150:147 */       else if (next < 32) {
/* 151:148 */         escape(next);
/* 152:149 */       } else if (next > 126) {
/* 153:150 */         escape(next);
/* 154:151 */       } else if (next == 61) {
/* 155:152 */         escape(next);
/* 156:    */       } else {
/* 157:154 */         plain(next);
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void plain(byte next)
/* 163:    */     throws IOException
/* 164:    */   {
/* 165:160 */     if (--this.nextSoftBreak <= 1) {
/* 166:161 */       softBreak();
/* 167:    */     }
/* 168:163 */     write(next);
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void escape(byte next)
/* 172:    */     throws IOException
/* 173:    */   {
/* 174:167 */     if (--this.nextSoftBreak <= 3) {
/* 175:168 */       softBreak();
/* 176:    */     }
/* 177:171 */     int nextUnsigned = next & 0xFF;
/* 178:    */     
/* 179:173 */     write((byte)61);
/* 180:174 */     this.nextSoftBreak -= 1;
/* 181:175 */     write(HEX_DIGITS[(nextUnsigned >> 4)]);
/* 182:176 */     this.nextSoftBreak -= 1;
/* 183:177 */     write(HEX_DIGITS[(nextUnsigned % 16)]);
/* 184:    */   }
/* 185:    */   
/* 186:    */   private void write(byte next)
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:181 */     this.outBuffer[(this.outputIndex++)] = next;
/* 190:182 */     if (this.outputIndex >= this.outBuffer.length) {
/* 191:183 */       flushOutput();
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   private void softBreak()
/* 196:    */     throws IOException
/* 197:    */   {
/* 198:188 */     write((byte)61);
/* 199:189 */     lineBreak();
/* 200:    */   }
/* 201:    */   
/* 202:    */   private void lineBreak()
/* 203:    */     throws IOException
/* 204:    */   {
/* 205:193 */     write((byte)13);
/* 206:194 */     write((byte)10);
/* 207:195 */     this.nextSoftBreak = 76;
/* 208:    */   }
/* 209:    */   
/* 210:    */   void flushOutput()
/* 211:    */     throws IOException
/* 212:    */   {
/* 213:199 */     if (this.outputIndex < this.outBuffer.length) {
/* 214:200 */       this.out.write(this.outBuffer, 0, this.outputIndex);
/* 215:    */     } else {
/* 216:202 */       this.out.write(this.outBuffer);
/* 217:    */     }
/* 218:204 */     this.outputIndex = 0;
/* 219:    */   }
/* 220:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.codec.QuotedPrintableEncoder
 * JD-Core Version:    0.7.0.1
 */