/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import jxl.WorkbookSettings;
/*   4:    */ import jxl.biff.IntegerHelper;
/*   5:    */ import jxl.biff.RecordData;
/*   6:    */ import jxl.biff.StringHelper;
/*   7:    */ import jxl.common.Assert;
/*   8:    */ 
/*   9:    */ class SSTRecord
/*  10:    */   extends RecordData
/*  11:    */ {
/*  12:    */   private int totalStrings;
/*  13:    */   private int uniqueStrings;
/*  14:    */   private String[] strings;
/*  15:    */   private int[] continuationBreaks;
/*  16:    */   
/*  17:    */   public SSTRecord(Record t, Record[] continuations, WorkbookSettings ws)
/*  18:    */   {
/*  19: 82 */     super(t);
/*  20:    */     
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25: 88 */     int totalRecordLength = 0;
/*  26: 90 */     for (int i = 0; i < continuations.length; i++) {
/*  27: 92 */       totalRecordLength += continuations[i].getLength();
/*  28:    */     }
/*  29: 94 */     totalRecordLength += getRecord().getLength();
/*  30:    */     
/*  31: 96 */     byte[] data = new byte[totalRecordLength];
/*  32:    */     
/*  33:    */ 
/*  34: 99 */     int pos = 0;
/*  35:100 */     System.arraycopy(getRecord().getData(), 0, data, 0, getRecord().getLength());
/*  36:    */     
/*  37:102 */     pos += getRecord().getLength();
/*  38:    */     
/*  39:    */ 
/*  40:105 */     this.continuationBreaks = new int[continuations.length];
/*  41:106 */     Record r = null;
/*  42:107 */     for (int i = 0; i < continuations.length; i++)
/*  43:    */     {
/*  44:109 */       r = continuations[i];
/*  45:110 */       System.arraycopy(r.getData(), 0, data, pos, r.getLength());
/*  46:    */       
/*  47:    */ 
/*  48:113 */       this.continuationBreaks[i] = pos;
/*  49:114 */       pos += r.getLength();
/*  50:    */     }
/*  51:117 */     this.totalStrings = IntegerHelper.getInt(data[0], data[1], data[2], data[3]);
/*  52:    */     
/*  53:119 */     this.uniqueStrings = IntegerHelper.getInt(data[4], data[5], data[6], data[7]);
/*  54:    */     
/*  55:    */ 
/*  56:122 */     this.strings = new String[this.uniqueStrings];
/*  57:123 */     readStrings(data, 8, ws);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void readStrings(byte[] data, int offset, WorkbookSettings ws)
/*  61:    */   {
/*  62:135 */     int pos = offset;
/*  63:    */     
/*  64:    */ 
/*  65:138 */     String s = null;
/*  66:139 */     boolean asciiEncoding = false;
/*  67:140 */     boolean richString = false;
/*  68:141 */     boolean extendedString = false;
/*  69:142 */     int formattingRuns = 0;
/*  70:143 */     int extendedRunLength = 0;
/*  71:145 */     for (int i = 0; i < this.uniqueStrings; i++)
/*  72:    */     {
/*  73:148 */       int numChars = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  74:149 */       pos += 2;
/*  75:150 */       byte optionFlags = data[pos];
/*  76:151 */       pos++;
/*  77:    */       
/*  78:    */ 
/*  79:154 */       extendedString = (optionFlags & 0x4) != 0;
/*  80:    */       
/*  81:    */ 
/*  82:157 */       richString = (optionFlags & 0x8) != 0;
/*  83:159 */       if (richString)
/*  84:    */       {
/*  85:162 */         formattingRuns = IntegerHelper.getInt(data[pos], data[(pos + 1)]);
/*  86:163 */         pos += 2;
/*  87:    */       }
/*  88:166 */       if (extendedString)
/*  89:    */       {
/*  90:169 */         extendedRunLength = IntegerHelper.getInt(data[pos], data[(pos + 1)], data[(pos + 2)], data[(pos + 3)]);
/*  91:    */         
/*  92:171 */         pos += 4;
/*  93:    */       }
/*  94:175 */       asciiEncoding = (optionFlags & 0x1) == 0;
/*  95:    */       
/*  96:177 */       ByteArrayHolder bah = new ByteArrayHolder(null);
/*  97:178 */       BooleanHolder bh = new BooleanHolder(null);
/*  98:179 */       bh.value = asciiEncoding;
/*  99:180 */       pos += getChars(data, bah, pos, bh, numChars);
/* 100:181 */       asciiEncoding = bh.value;
/* 101:183 */       if (asciiEncoding) {
/* 102:185 */         s = StringHelper.getString(bah.bytes, numChars, 0, ws);
/* 103:    */       } else {
/* 104:189 */         s = StringHelper.getUnicodeString(bah.bytes, numChars, 0);
/* 105:    */       }
/* 106:192 */       this.strings[i] = s;
/* 107:195 */       if (richString) {
/* 108:197 */         pos += 4 * formattingRuns;
/* 109:    */       }
/* 110:201 */       if (extendedString) {
/* 111:203 */         pos += extendedRunLength;
/* 112:    */       }
/* 113:206 */       if (pos > data.length) {
/* 114:208 */         Assert.verify(false, "pos exceeds record length");
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private int getChars(byte[] source, ByteArrayHolder bah, int pos, BooleanHolder ascii, int numChars)
/* 120:    */   {
/* 121:230 */     int i = 0;
/* 122:231 */     boolean spansBreak = false;
/* 123:233 */     if (ascii.value) {
/* 124:235 */       bah.bytes = new byte[numChars];
/* 125:    */     } else {
/* 126:239 */       bah.bytes = new byte[numChars * 2];
/* 127:    */     }
/* 128:242 */     while ((i < this.continuationBreaks.length) && (!spansBreak))
/* 129:    */     {
/* 130:244 */       spansBreak = (pos <= this.continuationBreaks[i]) && (pos + bah.bytes.length > this.continuationBreaks[i]);
/* 131:247 */       if (!spansBreak) {
/* 132:249 */         i++;
/* 133:    */       }
/* 134:    */     }
/* 135:255 */     if (!spansBreak)
/* 136:    */     {
/* 137:257 */       System.arraycopy(source, pos, bah.bytes, 0, bah.bytes.length);
/* 138:258 */       return bah.bytes.length;
/* 139:    */     }
/* 140:262 */     int breakpos = this.continuationBreaks[i];
/* 141:263 */     System.arraycopy(source, pos, bah.bytes, 0, breakpos - pos);
/* 142:    */     
/* 143:265 */     int bytesRead = breakpos - pos;
/* 144:    */     int charsRead;
/* 145:    */     int charsRead;
/* 146:267 */     if (ascii.value) {
/* 147:269 */       charsRead = bytesRead;
/* 148:    */     } else {
/* 149:273 */       charsRead = bytesRead / 2;
/* 150:    */     }
/* 151:276 */     bytesRead += getContinuedString(source, bah, bytesRead, i, ascii, numChars - charsRead);
/* 152:    */     
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:282 */     return bytesRead;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private int getContinuedString(byte[] source, ByteArrayHolder bah, int destPos, int contBreakIndex, BooleanHolder ascii, int charsLeft)
/* 161:    */   {
/* 162:303 */     int breakpos = this.continuationBreaks[contBreakIndex];
/* 163:304 */     int bytesRead = 0;
/* 164:306 */     while (charsLeft > 0)
/* 165:    */     {
/* 166:308 */       Assert.verify(contBreakIndex < this.continuationBreaks.length, "continuation break index");
/* 167:311 */       if ((ascii.value) && (source[breakpos] == 0))
/* 168:    */       {
/* 169:315 */         int length = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft : Math.min(charsLeft, this.continuationBreaks[(contBreakIndex + 1)] - breakpos - 1);
/* 170:    */         
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:321 */         System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length);
/* 176:    */         
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:326 */         destPos += length;
/* 181:327 */         bytesRead += length + 1;
/* 182:328 */         charsLeft -= length;
/* 183:329 */         ascii.value = true;
/* 184:    */       }
/* 185:331 */       else if ((!ascii.value) && (source[breakpos] != 0))
/* 186:    */       {
/* 187:335 */         int length = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft * 2 : Math.min(charsLeft * 2, this.continuationBreaks[(contBreakIndex + 1)] - breakpos - 1);
/* 188:    */         
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:342 */         System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length);
/* 195:    */         
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:348 */         destPos += length;
/* 201:349 */         bytesRead += length + 1;
/* 202:350 */         charsLeft -= length / 2;
/* 203:351 */         ascii.value = false;
/* 204:    */       }
/* 205:353 */       else if ((!ascii.value) && (source[breakpos] == 0))
/* 206:    */       {
/* 207:357 */         int chars = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft : Math.min(charsLeft, this.continuationBreaks[(contBreakIndex + 1)] - breakpos - 1);
/* 208:363 */         for (int j = 0; j < chars; j++)
/* 209:    */         {
/* 210:365 */           bah.bytes[destPos] = source[(breakpos + j + 1)];
/* 211:366 */           destPos += 2;
/* 212:    */         }
/* 213:369 */         bytesRead += chars + 1;
/* 214:370 */         charsLeft -= chars;
/* 215:371 */         ascii.value = false;
/* 216:    */       }
/* 217:    */       else
/* 218:    */       {
/* 219:380 */         byte[] oldBytes = bah.bytes;
/* 220:381 */         bah.bytes = new byte[destPos * 2 + charsLeft * 2];
/* 221:382 */         for (int j = 0; j < destPos; j++) {
/* 222:384 */           bah.bytes[(j * 2)] = oldBytes[j];
/* 223:    */         }
/* 224:387 */         destPos *= 2;
/* 225:    */         
/* 226:389 */         int length = contBreakIndex == this.continuationBreaks.length - 1 ? charsLeft * 2 : Math.min(charsLeft * 2, this.continuationBreaks[(contBreakIndex + 1)] - breakpos - 1);
/* 227:    */         
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:395 */         System.arraycopy(source, breakpos + 1, bah.bytes, destPos, length);
/* 233:    */         
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:401 */         destPos += length;
/* 239:402 */         bytesRead += length + 1;
/* 240:403 */         charsLeft -= length / 2;
/* 241:404 */         ascii.value = false;
/* 242:    */       }
/* 243:407 */       contBreakIndex++;
/* 244:409 */       if (contBreakIndex < this.continuationBreaks.length) {
/* 245:411 */         breakpos = this.continuationBreaks[contBreakIndex];
/* 246:    */       }
/* 247:    */     }
/* 248:415 */     return bytesRead;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getString(int index)
/* 252:    */   {
/* 253:426 */     Assert.verify(index < this.uniqueStrings);
/* 254:427 */     return this.strings[index];
/* 255:    */   }
/* 256:    */   
/* 257:    */   private static class BooleanHolder
/* 258:    */   {
/* 259:    */     public boolean value;
/* 260:    */   }
/* 261:    */   
/* 262:    */   private static class ByteArrayHolder
/* 263:    */   {
/* 264:    */     public byte[] bytes;
/* 265:    */   }
/* 266:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.SSTRecord
 * JD-Core Version:    0.7.0.1
 */