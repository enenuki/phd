/*   1:    */ package org.apache.xalan.xsltc.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public final class IntegerArray
/*   6:    */ {
/*   7:    */   private static final int InitialSize = 32;
/*   8:    */   private int[] _array;
/*   9:    */   private int _size;
/*  10: 32 */   private int _free = 0;
/*  11:    */   
/*  12:    */   public IntegerArray()
/*  13:    */   {
/*  14: 35 */     this(32);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public IntegerArray(int size)
/*  18:    */   {
/*  19: 39 */     this._array = new int[this._size = size];
/*  20:    */   }
/*  21:    */   
/*  22:    */   public IntegerArray(int[] array)
/*  23:    */   {
/*  24: 43 */     this(array.length);
/*  25: 44 */     System.arraycopy(array, 0, this._array, 0, this._free = this._size);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void clear()
/*  29:    */   {
/*  30: 48 */     this._free = 0;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object clone()
/*  34:    */   {
/*  35: 52 */     IntegerArray clone = new IntegerArray(this._free > 0 ? this._free : 1);
/*  36: 53 */     System.arraycopy(this._array, 0, clone._array, 0, this._free);
/*  37: 54 */     clone._free = this._free;
/*  38: 55 */     return clone;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int[] toIntArray()
/*  42:    */   {
/*  43: 59 */     int[] result = new int[cardinality()];
/*  44: 60 */     System.arraycopy(this._array, 0, result, 0, cardinality());
/*  45: 61 */     return result;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final int at(int index)
/*  49:    */   {
/*  50: 65 */     return this._array[index];
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final void set(int index, int value)
/*  54:    */   {
/*  55: 69 */     this._array[index] = value;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int indexOf(int n)
/*  59:    */   {
/*  60: 73 */     for (int i = 0; i < this._free; i++) {
/*  61: 74 */       if (n == this._array[i]) {
/*  62: 74 */         return i;
/*  63:    */       }
/*  64:    */     }
/*  65: 76 */     return -1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final void add(int value)
/*  69:    */   {
/*  70: 80 */     if (this._free == this._size) {
/*  71: 81 */       growArray(this._size * 2);
/*  72:    */     }
/*  73: 83 */     this._array[(this._free++)] = value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void addNew(int value)
/*  77:    */   {
/*  78: 90 */     for (int i = 0; i < this._free; i++) {
/*  79: 91 */       if (this._array[i] == value) {
/*  80: 91 */         return;
/*  81:    */       }
/*  82:    */     }
/*  83: 93 */     add(value);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void reverse()
/*  87:    */   {
/*  88: 97 */     int left = 0;
/*  89: 98 */     int right = this._free - 1;
/*  90:100 */     while (left < right)
/*  91:    */     {
/*  92:101 */       int temp = this._array[left];
/*  93:102 */       this._array[(left++)] = this._array[right];
/*  94:103 */       this._array[(right--)] = temp;
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void merge(IntegerArray other)
/*  99:    */   {
/* 100:112 */     int newSize = this._free + other._free;
/* 101:    */     
/* 102:114 */     int[] newArray = new int[newSize];
/* 103:    */     
/* 104:    */ 
/* 105:117 */     int i = 0;int j = 0;
/* 106:118 */     for (int k = 0; (i < this._free) && (j < other._free); k++)
/* 107:    */     {
/* 108:119 */       int x = this._array[i];
/* 109:120 */       int y = other._array[j];
/* 110:122 */       if (x < y)
/* 111:    */       {
/* 112:123 */         newArray[k] = x;
/* 113:124 */         i++;
/* 114:    */       }
/* 115:126 */       else if (x > y)
/* 116:    */       {
/* 117:127 */         newArray[k] = y;
/* 118:128 */         j++;
/* 119:    */       }
/* 120:    */       else
/* 121:    */       {
/* 122:131 */         newArray[k] = x;
/* 123:132 */         i++;j++;
/* 124:    */       }
/* 125:    */     }
/* 126:137 */     if (i >= this._free) {
/* 127:138 */       while (j < other._free) {
/* 128:139 */         newArray[(k++)] = other._array[(j++)];
/* 129:    */       }
/* 130:    */     } else {
/* 131:143 */       while (i < this._free) {
/* 132:144 */         newArray[(k++)] = this._array[(i++)];
/* 133:    */       }
/* 134:    */     }
/* 135:149 */     this._array = newArray;
/* 136:150 */     this._free = (this._size = newSize);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void sort()
/* 140:    */   {
/* 141:155 */     quicksort(this._array, 0, this._free - 1);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private static void quicksort(int[] array, int p, int r)
/* 145:    */   {
/* 146:159 */     if (p < r)
/* 147:    */     {
/* 148:160 */       int q = partition(array, p, r);
/* 149:161 */       quicksort(array, p, q);
/* 150:162 */       quicksort(array, q + 1, r);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static int partition(int[] array, int p, int r)
/* 155:    */   {
/* 156:167 */     int x = array[(p + r >>> 1)];
/* 157:168 */     int i = p - 1;int j = r + 1;
/* 158:    */     for (;;)
/* 159:    */     {
/* 160:171 */       if (x >= array[(--j)])
/* 161:    */       {
/* 162:172 */         while ((goto 38) || (x > array[(++i)])) {}
/* 163:173 */         if (i >= j) {
/* 164:    */           break;
/* 165:    */         }
/* 166:174 */         int temp = array[i];
/* 167:175 */         array[i] = array[j];
/* 168:176 */         array[j] = temp;
/* 169:    */       }
/* 170:    */     }
/* 171:179 */     return j;
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void growArray(int size)
/* 175:    */   {
/* 176:185 */     int[] newArray = new int[this._size = size];
/* 177:186 */     System.arraycopy(this._array, 0, newArray, 0, this._free);
/* 178:187 */     this._array = newArray;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int popLast()
/* 182:    */   {
/* 183:191 */     return this._array[(--this._free)];
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int last()
/* 187:    */   {
/* 188:195 */     return this._array[(this._free - 1)];
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setLast(int n)
/* 192:    */   {
/* 193:199 */     this._array[(this._free - 1)] = n;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void pop()
/* 197:    */   {
/* 198:203 */     this._free -= 1;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void pop(int n)
/* 202:    */   {
/* 203:207 */     this._free -= n;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public final int cardinality()
/* 207:    */   {
/* 208:211 */     return this._free;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void print(PrintStream out)
/* 212:    */   {
/* 213:215 */     if (this._free > 0)
/* 214:    */     {
/* 215:216 */       for (int i = 0; i < this._free - 1; i++)
/* 216:    */       {
/* 217:217 */         out.print(this._array[i]);
/* 218:218 */         out.print(' ');
/* 219:    */       }
/* 220:220 */       out.println(this._array[(this._free - 1)]);
/* 221:    */     }
/* 222:    */     else
/* 223:    */     {
/* 224:223 */       out.println("IntegerArray: empty");
/* 225:    */     }
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.util.IntegerArray
 * JD-Core Version:    0.7.0.1
 */