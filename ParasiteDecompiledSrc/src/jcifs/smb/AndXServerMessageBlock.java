/*   1:    */ package jcifs.smb;
/*   2:    */ 
/*   3:    */ import jcifs.util.Hexdump;
/*   4:    */ 
/*   5:    */ abstract class AndXServerMessageBlock
/*   6:    */   extends ServerMessageBlock
/*   7:    */ {
/*   8:    */   private static final int ANDX_COMMAND_OFFSET = 1;
/*   9:    */   private static final int ANDX_RESERVED_OFFSET = 2;
/*  10:    */   private static final int ANDX_OFFSET_OFFSET = 3;
/*  11: 31 */   private byte andxCommand = -1;
/*  12: 32 */   private int andxOffset = 0;
/*  13: 34 */   ServerMessageBlock andx = null;
/*  14:    */   
/*  15:    */   AndXServerMessageBlock() {}
/*  16:    */   
/*  17:    */   AndXServerMessageBlock(ServerMessageBlock andx)
/*  18:    */   {
/*  19: 39 */     if (andx != null)
/*  20:    */     {
/*  21: 40 */       this.andx = andx;
/*  22: 41 */       this.andxCommand = andx.command;
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   int getBatchLimit(byte command)
/*  27:    */   {
/*  28: 49 */     return 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   int encode(byte[] dst, int dstIndex)
/*  32:    */   {
/*  33: 62 */     int start = this.headerStart = dstIndex;
/*  34:    */     
/*  35: 64 */     dstIndex += writeHeaderWireFormat(dst, dstIndex);
/*  36: 65 */     dstIndex += writeAndXWireFormat(dst, dstIndex);
/*  37: 66 */     this.length = (dstIndex - start);
/*  38: 68 */     if (this.digest != null) {
/*  39: 69 */       this.digest.sign(dst, this.headerStart, this.length, this, this.response);
/*  40:    */     }
/*  41: 72 */     return this.length;
/*  42:    */   }
/*  43:    */   
/*  44:    */   int decode(byte[] buffer, int bufferIndex)
/*  45:    */   {
/*  46: 83 */     int start = this.headerStart = bufferIndex;
/*  47:    */     
/*  48: 85 */     bufferIndex += readHeaderWireFormat(buffer, bufferIndex);
/*  49: 86 */     bufferIndex += readAndXWireFormat(buffer, bufferIndex);
/*  50:    */     
/*  51: 88 */     this.length = (bufferIndex - start);
/*  52: 89 */     return this.length;
/*  53:    */   }
/*  54:    */   
/*  55:    */   int writeAndXWireFormat(byte[] dst, int dstIndex)
/*  56:    */   {
/*  57: 92 */     int start = dstIndex;
/*  58:    */     
/*  59: 94 */     this.wordCount = writeParameterWordsWireFormat(dst, start + 3 + 2);
/*  60:    */     
/*  61: 96 */     this.wordCount += 4;
/*  62: 97 */     dstIndex += this.wordCount + 1;
/*  63: 98 */     this.wordCount /= 2;
/*  64: 99 */     dst[start] = ((byte)(this.wordCount & 0xFF));
/*  65:    */     
/*  66:101 */     this.byteCount = writeBytesWireFormat(dst, dstIndex + 2);
/*  67:102 */     dst[(dstIndex++)] = ((byte)(this.byteCount & 0xFF));
/*  68:103 */     dst[(dstIndex++)] = ((byte)(this.byteCount >> 8 & 0xFF));
/*  69:104 */     dstIndex += this.byteCount;
/*  70:116 */     if ((this.andx == null) || (!SmbConstants.USE_BATCHING) || (this.batchLevel >= getBatchLimit(this.andx.command)))
/*  71:    */     {
/*  72:118 */       this.andxCommand = -1;
/*  73:119 */       this.andx = null;
/*  74:    */       
/*  75:121 */       dst[(start + 1)] = -1;
/*  76:122 */       dst[(start + 2)] = 0;
/*  77:    */       
/*  78:    */ 
/*  79:125 */       dst[(start + 3)] = -34;
/*  80:126 */       dst[(start + 3 + 1)] = -34;
/*  81:    */       
/*  82:    */ 
/*  83:129 */       return dstIndex - start;
/*  84:    */     }
/*  85:139 */     this.andx.batchLevel = (this.batchLevel + 1);
/*  86:    */     
/*  87:    */ 
/*  88:142 */     dst[(start + 1)] = this.andxCommand;
/*  89:143 */     dst[(start + 2)] = 0;
/*  90:144 */     this.andxOffset = (dstIndex - this.headerStart);
/*  91:145 */     writeInt2(this.andxOffset, dst, start + 3);
/*  92:    */     
/*  93:147 */     this.andx.useUnicode = this.useUnicode;
/*  94:148 */     if ((this.andx instanceof AndXServerMessageBlock))
/*  95:    */     {
/*  96:165 */       this.andx.uid = this.uid;
/*  97:166 */       dstIndex += ((AndXServerMessageBlock)this.andx).writeAndXWireFormat(dst, dstIndex);
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:170 */       int andxStart = dstIndex;
/* 102:171 */       this.andx.wordCount = this.andx.writeParameterWordsWireFormat(dst, dstIndex);
/* 103:172 */       dstIndex += this.andx.wordCount + 1;
/* 104:173 */       this.andx.wordCount /= 2;
/* 105:174 */       dst[andxStart] = ((byte)(this.andx.wordCount & 0xFF));
/* 106:    */       
/* 107:176 */       this.andx.byteCount = this.andx.writeBytesWireFormat(dst, dstIndex + 2);
/* 108:177 */       dst[(dstIndex++)] = ((byte)(this.andx.byteCount & 0xFF));
/* 109:178 */       dst[(dstIndex++)] = ((byte)(this.andx.byteCount >> 8 & 0xFF));
/* 110:179 */       dstIndex += this.andx.byteCount;
/* 111:    */     }
/* 112:182 */     return dstIndex - start;
/* 113:    */   }
/* 114:    */   
/* 115:    */   int readAndXWireFormat(byte[] buffer, int bufferIndex)
/* 116:    */   {
/* 117:185 */     int start = bufferIndex;
/* 118:    */     
/* 119:187 */     this.wordCount = buffer[(bufferIndex++)];
/* 120:189 */     if (this.wordCount != 0)
/* 121:    */     {
/* 122:195 */       this.andxCommand = buffer[bufferIndex];
/* 123:196 */       this.andxOffset = readInt2(buffer, bufferIndex + 2);
/* 124:198 */       if (this.andxOffset == 0) {
/* 125:199 */         this.andxCommand = -1;
/* 126:    */       }
/* 127:207 */       if (this.wordCount > 2)
/* 128:    */       {
/* 129:208 */         readParameterWordsWireFormat(buffer, bufferIndex + 4);
/* 130:216 */         if ((this.command == -94) && (((SmbComNTCreateAndXResponse)this).isExtended)) {
/* 131:217 */           this.wordCount += 8;
/* 132:    */         }
/* 133:    */       }
/* 134:220 */       bufferIndex = start + 1 + this.wordCount * 2;
/* 135:    */     }
/* 136:223 */     this.byteCount = readInt2(buffer, bufferIndex);bufferIndex += 2;
/* 137:225 */     if (this.byteCount != 0)
/* 138:    */     {
/* 139:227 */       int n = readBytesWireFormat(buffer, bufferIndex);
/* 140:228 */       bufferIndex += this.byteCount;
/* 141:    */     }
/* 142:238 */     if ((this.errorCode != 0) || (this.andxCommand == -1))
/* 143:    */     {
/* 144:239 */       this.andxCommand = -1;
/* 145:240 */       this.andx = null;
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:241 */       if (this.andx == null)
/* 150:    */       {
/* 151:242 */         this.andxCommand = -1;
/* 152:243 */         throw new RuntimeException("no andx command supplied with response");
/* 153:    */       }
/* 154:250 */       bufferIndex = this.headerStart + this.andxOffset;
/* 155:    */       
/* 156:252 */       this.andx.headerStart = this.headerStart;
/* 157:253 */       this.andx.command = this.andxCommand;
/* 158:254 */       this.andx.errorCode = this.errorCode;
/* 159:255 */       this.andx.flags = this.flags;
/* 160:256 */       this.andx.flags2 = this.flags2;
/* 161:257 */       this.andx.tid = this.tid;
/* 162:258 */       this.andx.pid = this.pid;
/* 163:259 */       this.andx.uid = this.uid;
/* 164:260 */       this.andx.mid = this.mid;
/* 165:261 */       this.andx.useUnicode = this.useUnicode;
/* 166:263 */       if ((this.andx instanceof AndXServerMessageBlock))
/* 167:    */       {
/* 168:264 */         bufferIndex += ((AndXServerMessageBlock)this.andx).readAndXWireFormat(buffer, bufferIndex);
/* 169:    */       }
/* 170:    */       else
/* 171:    */       {
/* 172:272 */         buffer[(bufferIndex++)] = ((byte)(this.andx.wordCount & 0xFF));
/* 173:274 */         if (this.andx.wordCount != 0) {
/* 174:280 */           if (this.andx.wordCount > 2) {
/* 175:281 */             bufferIndex += this.andx.readParameterWordsWireFormat(buffer, bufferIndex);
/* 176:    */           }
/* 177:    */         }
/* 178:285 */         this.andx.byteCount = readInt2(buffer, bufferIndex);
/* 179:286 */         bufferIndex += 2;
/* 180:288 */         if (this.andx.byteCount != 0)
/* 181:    */         {
/* 182:289 */           this.andx.readBytesWireFormat(buffer, bufferIndex);
/* 183:290 */           bufferIndex += this.andx.byteCount;
/* 184:    */         }
/* 185:    */       }
/* 186:293 */       this.andx.received = true;
/* 187:    */     }
/* 188:296 */     return bufferIndex - start;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String toString()
/* 192:    */   {
/* 193:299 */     return new String(super.toString() + ",andxCommand=0x" + Hexdump.toHexString(this.andxCommand, 2) + ",andxOffset=" + this.andxOffset);
/* 194:    */   }
/* 195:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.AndXServerMessageBlock
 * JD-Core Version:    0.7.0.1
 */