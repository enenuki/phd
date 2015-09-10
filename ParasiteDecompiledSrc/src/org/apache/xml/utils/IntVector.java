/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class IntVector
/*   4:    */   implements Cloneable
/*   5:    */ {
/*   6:    */   protected int m_blocksize;
/*   7:    */   protected int[] m_map;
/*   8: 43 */   protected int m_firstFree = 0;
/*   9:    */   protected int m_mapSize;
/*  10:    */   
/*  11:    */   public IntVector()
/*  12:    */   {
/*  13: 55 */     this.m_blocksize = 32;
/*  14: 56 */     this.m_mapSize = this.m_blocksize;
/*  15: 57 */     this.m_map = new int[this.m_blocksize];
/*  16:    */   }
/*  17:    */   
/*  18:    */   public IntVector(int blocksize)
/*  19:    */   {
/*  20: 68 */     this.m_blocksize = blocksize;
/*  21: 69 */     this.m_mapSize = blocksize;
/*  22: 70 */     this.m_map = new int[blocksize];
/*  23:    */   }
/*  24:    */   
/*  25:    */   public IntVector(int blocksize, int increaseSize)
/*  26:    */   {
/*  27: 81 */     this.m_blocksize = increaseSize;
/*  28: 82 */     this.m_mapSize = blocksize;
/*  29: 83 */     this.m_map = new int[blocksize];
/*  30:    */   }
/*  31:    */   
/*  32:    */   public IntVector(IntVector v)
/*  33:    */   {
/*  34: 93 */     this.m_map = new int[v.m_mapSize];
/*  35: 94 */     this.m_mapSize = v.m_mapSize;
/*  36: 95 */     this.m_firstFree = v.m_firstFree;
/*  37: 96 */     this.m_blocksize = v.m_blocksize;
/*  38: 97 */     System.arraycopy(v.m_map, 0, this.m_map, 0, this.m_firstFree);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final int size()
/*  42:    */   {
/*  43:107 */     return this.m_firstFree;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final void setSize(int sz)
/*  47:    */   {
/*  48:117 */     this.m_firstFree = sz;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public final void addElement(int value)
/*  52:    */   {
/*  53:129 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/*  54:    */     {
/*  55:131 */       this.m_mapSize += this.m_blocksize;
/*  56:    */       
/*  57:133 */       int[] newMap = new int[this.m_mapSize];
/*  58:    */       
/*  59:135 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  60:    */       
/*  61:137 */       this.m_map = newMap;
/*  62:    */     }
/*  63:140 */     this.m_map[this.m_firstFree] = value;
/*  64:    */     
/*  65:142 */     this.m_firstFree += 1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final void addElements(int value, int numberOfElements)
/*  69:    */   {
/*  70:153 */     if (this.m_firstFree + numberOfElements >= this.m_mapSize)
/*  71:    */     {
/*  72:155 */       this.m_mapSize += this.m_blocksize + numberOfElements;
/*  73:    */       
/*  74:157 */       int[] newMap = new int[this.m_mapSize];
/*  75:    */       
/*  76:159 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  77:    */       
/*  78:161 */       this.m_map = newMap;
/*  79:    */     }
/*  80:164 */     for (int i = 0; i < numberOfElements; i++)
/*  81:    */     {
/*  82:166 */       this.m_map[this.m_firstFree] = value;
/*  83:167 */       this.m_firstFree += 1;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final void addElements(int numberOfElements)
/*  88:    */   {
/*  89:179 */     if (this.m_firstFree + numberOfElements >= this.m_mapSize)
/*  90:    */     {
/*  91:181 */       this.m_mapSize += this.m_blocksize + numberOfElements;
/*  92:    */       
/*  93:183 */       int[] newMap = new int[this.m_mapSize];
/*  94:    */       
/*  95:185 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  96:    */       
/*  97:187 */       this.m_map = newMap;
/*  98:    */     }
/*  99:190 */     this.m_firstFree += numberOfElements;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final void insertElementAt(int value, int at)
/* 103:    */   {
/* 104:206 */     if (this.m_firstFree + 1 >= this.m_mapSize)
/* 105:    */     {
/* 106:208 */       this.m_mapSize += this.m_blocksize;
/* 107:    */       
/* 108:210 */       int[] newMap = new int[this.m_mapSize];
/* 109:    */       
/* 110:212 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/* 111:    */       
/* 112:214 */       this.m_map = newMap;
/* 113:    */     }
/* 114:217 */     if (at <= this.m_firstFree - 1) {
/* 115:219 */       System.arraycopy(this.m_map, at, this.m_map, at + 1, this.m_firstFree - at);
/* 116:    */     }
/* 117:222 */     this.m_map[at] = value;
/* 118:    */     
/* 119:224 */     this.m_firstFree += 1;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final void removeAllElements()
/* 123:    */   {
/* 124:236 */     for (int i = 0; i < this.m_firstFree; i++) {
/* 125:238 */       this.m_map[i] = -2147483648;
/* 126:    */     }
/* 127:241 */     this.m_firstFree = 0;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public final boolean removeElement(int s)
/* 131:    */   {
/* 132:258 */     for (int i = 0; i < this.m_firstFree; i++) {
/* 133:260 */       if (this.m_map[i] == s)
/* 134:    */       {
/* 135:262 */         if (i + 1 < this.m_firstFree) {
/* 136:263 */           System.arraycopy(this.m_map, i + 1, this.m_map, i - 1, this.m_firstFree - i);
/* 137:    */         } else {
/* 138:265 */           this.m_map[i] = -2147483648;
/* 139:    */         }
/* 140:267 */         this.m_firstFree -= 1;
/* 141:    */         
/* 142:269 */         return true;
/* 143:    */       }
/* 144:    */     }
/* 145:273 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final void removeElementAt(int i)
/* 149:    */   {
/* 150:287 */     if (i > this.m_firstFree) {
/* 151:288 */       System.arraycopy(this.m_map, i + 1, this.m_map, i, this.m_firstFree);
/* 152:    */     } else {
/* 153:290 */       this.m_map[i] = -2147483648;
/* 154:    */     }
/* 155:292 */     this.m_firstFree -= 1;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public final void setElementAt(int value, int index)
/* 159:    */   {
/* 160:307 */     this.m_map[index] = value;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public final int elementAt(int i)
/* 164:    */   {
/* 165:319 */     return this.m_map[i];
/* 166:    */   }
/* 167:    */   
/* 168:    */   public final boolean contains(int s)
/* 169:    */   {
/* 170:332 */     for (int i = 0; i < this.m_firstFree; i++) {
/* 171:334 */       if (this.m_map[i] == s) {
/* 172:335 */         return true;
/* 173:    */       }
/* 174:    */     }
/* 175:338 */     return false;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public final int indexOf(int elem, int index)
/* 179:    */   {
/* 180:355 */     for (int i = index; i < this.m_firstFree; i++) {
/* 181:357 */       if (this.m_map[i] == elem) {
/* 182:358 */         return i;
/* 183:    */       }
/* 184:    */     }
/* 185:361 */     return -2147483648;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public final int indexOf(int elem)
/* 189:    */   {
/* 190:377 */     for (int i = 0; i < this.m_firstFree; i++) {
/* 191:379 */       if (this.m_map[i] == elem) {
/* 192:380 */         return i;
/* 193:    */       }
/* 194:    */     }
/* 195:383 */     return -2147483648;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public final int lastIndexOf(int elem)
/* 199:    */   {
/* 200:399 */     for (int i = this.m_firstFree - 1; i >= 0; i--) {
/* 201:401 */       if (this.m_map[i] == elem) {
/* 202:402 */         return i;
/* 203:    */       }
/* 204:    */     }
/* 205:405 */     return -2147483648;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Object clone()
/* 209:    */     throws CloneNotSupportedException
/* 210:    */   {
/* 211:416 */     return new IntVector(this);
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.IntVector
 * JD-Core Version:    0.7.0.1
 */