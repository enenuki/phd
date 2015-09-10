/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.Externalizable;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.ObjectInput;
/*   8:    */ import java.io.ObjectOutput;
/*   9:    */ 
/*  10:    */ public class BitArray
/*  11:    */   implements Externalizable
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -4876019880708377663L;
/*  14:    */   private int[] _bits;
/*  15:    */   private int _bitSize;
/*  16:    */   private int _intSize;
/*  17:    */   private int _mask;
/*  18: 45 */   private static final int[] _masks = { -2147483648, 1073741824, 536870912, 268435456, 134217728, 67108864, 33554432, 16777216, 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
/*  19:    */   private static final boolean DEBUG_ASSERTIONS = false;
/*  20:    */   
/*  21:    */   public BitArray()
/*  22:    */   {
/*  23: 61 */     this(32);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public BitArray(int size)
/*  27:    */   {
/*  28: 65 */     if (size < 32) {
/*  29: 65 */       size = 32;
/*  30:    */     }
/*  31: 66 */     this._bitSize = size;
/*  32: 67 */     this._intSize = ((this._bitSize >>> 5) + 1);
/*  33: 68 */     this._bits = new int[this._intSize + 1];
/*  34:    */   }
/*  35:    */   
/*  36:    */   public BitArray(int size, int[] bits)
/*  37:    */   {
/*  38: 72 */     if (size < 32) {
/*  39: 72 */       size = 32;
/*  40:    */     }
/*  41: 73 */     this._bitSize = size;
/*  42: 74 */     this._intSize = ((this._bitSize >>> 5) + 1);
/*  43: 75 */     this._bits = bits;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setMask(int mask)
/*  47:    */   {
/*  48: 83 */     this._mask = mask;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getMask()
/*  52:    */   {
/*  53: 90 */     return this._mask;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public final int size()
/*  57:    */   {
/*  58: 97 */     return this._bitSize;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public final boolean getBit(int bit)
/*  62:    */   {
/*  63:111 */     return (this._bits[(bit >>> 5)] & _masks[(bit % 32)]) != 0;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public final int getNextBit(int startBit)
/*  67:    */   {
/*  68:118 */     for (int i = startBit >>> 5; i <= this._intSize; i++)
/*  69:    */     {
/*  70:119 */       int bits = this._bits[i];
/*  71:120 */       if (bits != 0) {
/*  72:121 */         for (int b = startBit % 32; b < 32; b++) {
/*  73:122 */           if ((bits & _masks[b]) != 0) {
/*  74:123 */             return (i << 5) + b;
/*  75:    */           }
/*  76:    */         }
/*  77:    */       }
/*  78:127 */       startBit = 0;
/*  79:    */     }
/*  80:129 */     return -1;
/*  81:    */   }
/*  82:    */   
/*  83:138 */   private int _pos = 2147483647;
/*  84:139 */   private int _node = 0;
/*  85:140 */   private int _int = 0;
/*  86:141 */   private int _bit = 0;
/*  87:    */   
/*  88:    */   public final int getBitNumber(int pos)
/*  89:    */   {
/*  90:146 */     if (pos == this._pos) {
/*  91:146 */       return this._node;
/*  92:    */     }
/*  93:150 */     if (pos < this._pos) {}
/*  94:151 */     for (this._int = (this._bit = this._pos = 0); this._int <= this._intSize; this._int += 1)
/*  95:    */     {
/*  96:156 */       int bits = this._bits[this._int];
/*  97:157 */       if (bits != 0)
/*  98:    */       {
/*  99:158 */         for (; this._bit < 32; this._bit += 1) {
/* 100:159 */           if (((bits & _masks[this._bit]) != 0) && 
/* 101:160 */             (++this._pos == pos))
/* 102:    */           {
/* 103:161 */             this._node = ((this._int << 5) + this._bit - 1);
/* 104:162 */             return this._node;
/* 105:    */           }
/* 106:    */         }
/* 107:166 */         this._bit = 0;
/* 108:    */       }
/* 109:    */     }
/* 110:169 */     return 0;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final int[] data()
/* 114:    */   {
/* 115:176 */     return this._bits;
/* 116:    */   }
/* 117:    */   
/* 118:179 */   int _first = 2147483647;
/* 119:180 */   int _last = -2147483648;
/* 120:    */   
/* 121:    */   public final void setBit(int bit)
/* 122:    */   {
/* 123:193 */     if (bit >= this._bitSize) {
/* 124:193 */       return;
/* 125:    */     }
/* 126:194 */     int i = bit >>> 5;
/* 127:195 */     if (i < this._first) {
/* 128:195 */       this._first = i;
/* 129:    */     }
/* 130:196 */     if (i > this._last) {
/* 131:196 */       this._last = i;
/* 132:    */     }
/* 133:197 */     this._bits[i] |= _masks[(bit % 32)];
/* 134:    */   }
/* 135:    */   
/* 136:    */   public final BitArray merge(BitArray other)
/* 137:    */   {
/* 138:206 */     if (this._last == -1)
/* 139:    */     {
/* 140:207 */       this._bits = other._bits;
/* 141:    */     }
/* 142:210 */     else if (other._last != -1)
/* 143:    */     {
/* 144:211 */       int start = this._first < other._first ? this._first : other._first;
/* 145:212 */       int stop = this._last > other._last ? this._last : other._last;
/* 146:215 */       if (other._intSize > this._intSize)
/* 147:    */       {
/* 148:216 */         if (stop > this._intSize) {
/* 149:216 */           stop = this._intSize;
/* 150:    */         }
/* 151:217 */         for (int i = start; i <= stop; i++) {
/* 152:218 */           other._bits[i] |= this._bits[i];
/* 153:    */         }
/* 154:219 */         this._bits = other._bits;
/* 155:    */       }
/* 156:    */       else
/* 157:    */       {
/* 158:223 */         if (stop > other._intSize) {
/* 159:223 */           stop = other._intSize;
/* 160:    */         }
/* 161:224 */         for (int i = start; i <= stop; i++) {
/* 162:225 */           this._bits[i] |= other._bits[i];
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:228 */     return this;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public final void resize(int newSize)
/* 170:    */   {
/* 171:235 */     if (newSize > this._bitSize)
/* 172:    */     {
/* 173:236 */       this._intSize = ((newSize >>> 5) + 1);
/* 174:237 */       int[] newBits = new int[this._intSize + 1];
/* 175:238 */       System.arraycopy(this._bits, 0, newBits, 0, (this._bitSize >>> 5) + 1);
/* 176:239 */       this._bits = newBits;
/* 177:240 */       this._bitSize = newSize;
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public BitArray cloneArray()
/* 182:    */   {
/* 183:245 */     return new BitArray(this._intSize, this._bits);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void writeExternal(ObjectOutput out)
/* 187:    */     throws IOException
/* 188:    */   {
/* 189:249 */     out.writeInt(this._bitSize);
/* 190:250 */     out.writeInt(this._mask);
/* 191:251 */     out.writeObject(this._bits);
/* 192:252 */     out.flush();
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void readExternal(ObjectInput in)
/* 196:    */     throws IOException, ClassNotFoundException
/* 197:    */   {
/* 198:260 */     this._bitSize = in.readInt();
/* 199:261 */     this._intSize = ((this._bitSize >>> 5) + 1);
/* 200:262 */     this._mask = in.readInt();
/* 201:263 */     this._bits = ((int[])in.readObject());
/* 202:    */   }
/* 203:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.BitArray
 * JD-Core Version:    0.7.0.1
 */