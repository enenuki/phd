/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.xml.res.XMLMessages;
/*   5:    */ 
/*   6:    */ final class ChunkedIntArray
/*   7:    */ {
/*   8: 42 */   final int slotsize = 4;
/*   9:    */   static final int lowbits = 10;
/*  10:    */   static final int chunkalloc = 1024;
/*  11:    */   static final int lowmask = 1023;
/*  12: 49 */   ChunksVector chunks = new ChunksVector();
/*  13: 50 */   final int[] fastArray = new int[1024];
/*  14: 51 */   int lastUsed = 0;
/*  15:    */   
/*  16:    */   ChunkedIntArray(int slotsize)
/*  17:    */   {
/*  18: 59 */     if (4 < slotsize) {
/*  19: 60 */       throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage("ER_CHUNKEDINTARRAY_NOT_SUPPORTED", new Object[] { Integer.toString(slotsize) }));
/*  20:    */     }
/*  21: 61 */     if (4 > slotsize) {
/*  22: 62 */       System.out.println("*****WARNING: ChunkedIntArray(" + slotsize + ") wasting " + (4 - slotsize) + " words per slot");
/*  23:    */     }
/*  24: 63 */     this.chunks.addElement(this.fastArray);
/*  25:    */   }
/*  26:    */   
/*  27:    */   int appendSlot(int w0, int w1, int w2, int w3)
/*  28:    */   {
/*  29: 86 */     int slotsize = 4;
/*  30: 87 */     int newoffset = (this.lastUsed + 1) * 4;
/*  31: 88 */     int chunkpos = newoffset >> 10;
/*  32: 89 */     int slotpos = newoffset & 0x3FF;
/*  33: 92 */     if (chunkpos > this.chunks.size() - 1) {
/*  34: 93 */       this.chunks.addElement(new int[1024]);
/*  35:    */     }
/*  36: 94 */     int[] chunk = this.chunks.elementAt(chunkpos);
/*  37: 95 */     chunk[slotpos] = w0;
/*  38: 96 */     chunk[(slotpos + 1)] = w1;
/*  39: 97 */     chunk[(slotpos + 2)] = w2;
/*  40: 98 */     chunk[(slotpos + 3)] = w3;
/*  41:    */     
/*  42:100 */     return ++this.lastUsed;
/*  43:    */   }
/*  44:    */   
/*  45:    */   int readEntry(int position, int offset)
/*  46:    */     throws ArrayIndexOutOfBoundsException
/*  47:    */   {
/*  48:121 */     if (offset >= 4) {
/*  49:122 */       throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage("ER_OFFSET_BIGGER_THAN_SLOT", null));
/*  50:    */     }
/*  51:123 */     position *= 4;
/*  52:124 */     int chunkpos = position >> 10;
/*  53:125 */     int slotpos = position & 0x3FF;
/*  54:126 */     int[] chunk = this.chunks.elementAt(chunkpos);
/*  55:127 */     return chunk[(slotpos + offset)];
/*  56:    */   }
/*  57:    */   
/*  58:    */   int specialFind(int startPos, int position)
/*  59:    */   {
/*  60:141 */     int ancestor = startPos;
/*  61:142 */     while (ancestor > 0)
/*  62:    */     {
/*  63:145 */       ancestor *= 4;
/*  64:146 */       int chunkpos = ancestor >> 10;
/*  65:147 */       int slotpos = ancestor & 0x3FF;
/*  66:148 */       int[] chunk = this.chunks.elementAt(chunkpos);
/*  67:    */       
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:153 */       ancestor = chunk[(slotpos + 1)];
/*  72:155 */       if (ancestor == position) {
/*  73:    */         break;
/*  74:    */       }
/*  75:    */     }
/*  76:159 */     if (ancestor <= 0) {
/*  77:161 */       return position;
/*  78:    */     }
/*  79:163 */     return -1;
/*  80:    */   }
/*  81:    */   
/*  82:    */   int slotsUsed()
/*  83:    */   {
/*  84:171 */     return this.lastUsed;
/*  85:    */   }
/*  86:    */   
/*  87:    */   void discardLast()
/*  88:    */   {
/*  89:181 */     this.lastUsed -= 1;
/*  90:    */   }
/*  91:    */   
/*  92:    */   void writeEntry(int position, int offset, int value)
/*  93:    */     throws ArrayIndexOutOfBoundsException
/*  94:    */   {
/*  95:202 */     if (offset >= 4) {
/*  96:203 */       throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage("ER_OFFSET_BIGGER_THAN_SLOT", null));
/*  97:    */     }
/*  98:204 */     position *= 4;
/*  99:205 */     int chunkpos = position >> 10;
/* 100:206 */     int slotpos = position & 0x3FF;
/* 101:207 */     int[] chunk = this.chunks.elementAt(chunkpos);
/* 102:208 */     chunk[(slotpos + offset)] = value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   void writeSlot(int position, int w0, int w1, int w2, int w3)
/* 106:    */   {
/* 107:223 */     position *= 4;
/* 108:224 */     int chunkpos = position >> 10;
/* 109:225 */     int slotpos = position & 0x3FF;
/* 110:228 */     if (chunkpos > this.chunks.size() - 1) {
/* 111:229 */       this.chunks.addElement(new int[1024]);
/* 112:    */     }
/* 113:230 */     int[] chunk = this.chunks.elementAt(chunkpos);
/* 114:231 */     chunk[slotpos] = w0;
/* 115:232 */     chunk[(slotpos + 1)] = w1;
/* 116:233 */     chunk[(slotpos + 2)] = w2;
/* 117:234 */     chunk[(slotpos + 3)] = w3;
/* 118:    */   }
/* 119:    */   
/* 120:    */   void readSlot(int position, int[] buffer)
/* 121:    */   {
/* 122:256 */     position *= 4;
/* 123:257 */     int chunkpos = position >> 10;
/* 124:258 */     int slotpos = position & 0x3FF;
/* 125:261 */     if (chunkpos > this.chunks.size() - 1) {
/* 126:262 */       this.chunks.addElement(new int[1024]);
/* 127:    */     }
/* 128:263 */     int[] chunk = this.chunks.elementAt(chunkpos);
/* 129:264 */     System.arraycopy(chunk, slotpos, buffer, 0, 4);
/* 130:    */   }
/* 131:    */   
/* 132:    */   class ChunksVector
/* 133:    */   {
/* 134:270 */     final int BLOCKSIZE = 64;
/* 135:271 */     int[][] m_map = new int[64][];
/* 136:272 */     int m_mapSize = 64;
/* 137:273 */     int pos = 0;
/* 138:    */     
/* 139:    */     ChunksVector() {}
/* 140:    */     
/* 141:    */     final int size()
/* 142:    */     {
/* 143:281 */       return this.pos;
/* 144:    */     }
/* 145:    */     
/* 146:    */     void addElement(int[] value)
/* 147:    */     {
/* 148:286 */       if (this.pos >= this.m_mapSize)
/* 149:    */       {
/* 150:288 */         int orgMapSize = this.m_mapSize;
/* 151:289 */         while (this.pos >= this.m_mapSize) {
/* 152:290 */           this.m_mapSize += 64;
/* 153:    */         }
/* 154:291 */         int[][] newMap = new int[this.m_mapSize][];
/* 155:292 */         System.arraycopy(this.m_map, 0, newMap, 0, orgMapSize);
/* 156:293 */         this.m_map = newMap;
/* 157:    */       }
/* 158:297 */       this.m_map[this.pos] = value;
/* 159:298 */       this.pos += 1;
/* 160:    */     }
/* 161:    */     
/* 162:    */     final int[] elementAt(int pos)
/* 163:    */     {
/* 164:303 */       return this.m_map[pos];
/* 165:    */     }
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.ChunkedIntArray
 * JD-Core Version:    0.7.0.1
 */