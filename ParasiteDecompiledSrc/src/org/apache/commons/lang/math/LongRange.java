/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.lang.text.StrBuilder;
/*   5:    */ 
/*   6:    */ public final class LongRange
/*   7:    */   extends Range
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 71849363892720L;
/*  11:    */   private final long min;
/*  12:    */   private final long max;
/*  13: 51 */   private transient Long minObject = null;
/*  14: 55 */   private transient Long maxObject = null;
/*  15: 59 */   private transient int hashCode = 0;
/*  16: 63 */   private transient String toString = null;
/*  17:    */   
/*  18:    */   public LongRange(long number)
/*  19:    */   {
/*  20: 73 */     this.min = number;
/*  21: 74 */     this.max = number;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public LongRange(Number number)
/*  25:    */   {
/*  26: 87 */     if (number == null) {
/*  27: 88 */       throw new IllegalArgumentException("The number must not be null");
/*  28:    */     }
/*  29: 90 */     this.min = number.longValue();
/*  30: 91 */     this.max = number.longValue();
/*  31: 92 */     if ((number instanceof Long))
/*  32:    */     {
/*  33: 93 */       this.minObject = ((Long)number);
/*  34: 94 */       this.maxObject = ((Long)number);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public LongRange(long number1, long number2)
/*  39:    */   {
/*  40:110 */     if (number2 < number1)
/*  41:    */     {
/*  42:111 */       this.min = number2;
/*  43:112 */       this.max = number1;
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47:114 */       this.min = number1;
/*  48:115 */       this.max = number2;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public LongRange(Number number1, Number number2)
/*  53:    */   {
/*  54:132 */     if ((number1 == null) || (number2 == null)) {
/*  55:133 */       throw new IllegalArgumentException("The numbers must not be null");
/*  56:    */     }
/*  57:135 */     long number1val = number1.longValue();
/*  58:136 */     long number2val = number2.longValue();
/*  59:137 */     if (number2val < number1val)
/*  60:    */     {
/*  61:138 */       this.min = number2val;
/*  62:139 */       this.max = number1val;
/*  63:140 */       if ((number2 instanceof Long)) {
/*  64:141 */         this.minObject = ((Long)number2);
/*  65:    */       }
/*  66:143 */       if ((number1 instanceof Long)) {
/*  67:144 */         this.maxObject = ((Long)number1);
/*  68:    */       }
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72:147 */       this.min = number1val;
/*  73:148 */       this.max = number2val;
/*  74:149 */       if ((number1 instanceof Long)) {
/*  75:150 */         this.minObject = ((Long)number1);
/*  76:    */       }
/*  77:152 */       if ((number2 instanceof Long)) {
/*  78:153 */         this.maxObject = ((Long)number2);
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Number getMinimumNumber()
/*  84:    */   {
/*  85:167 */     if (this.minObject == null) {
/*  86:168 */       this.minObject = new Long(this.min);
/*  87:    */     }
/*  88:170 */     return this.minObject;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public long getMinimumLong()
/*  92:    */   {
/*  93:179 */     return this.min;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getMinimumInteger()
/*  97:    */   {
/*  98:190 */     return (int)this.min;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public double getMinimumDouble()
/* 102:    */   {
/* 103:201 */     return this.min;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public float getMinimumFloat()
/* 107:    */   {
/* 108:212 */     return (float)this.min;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Number getMaximumNumber()
/* 112:    */   {
/* 113:221 */     if (this.maxObject == null) {
/* 114:222 */       this.maxObject = new Long(this.max);
/* 115:    */     }
/* 116:224 */     return this.maxObject;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public long getMaximumLong()
/* 120:    */   {
/* 121:233 */     return this.max;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getMaximumInteger()
/* 125:    */   {
/* 126:244 */     return (int)this.max;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public double getMaximumDouble()
/* 130:    */   {
/* 131:255 */     return this.max;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public float getMaximumFloat()
/* 135:    */   {
/* 136:266 */     return (float)this.max;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean containsNumber(Number number)
/* 140:    */   {
/* 141:282 */     if (number == null) {
/* 142:283 */       return false;
/* 143:    */     }
/* 144:285 */     return containsLong(number.longValue());
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean containsLong(long value)
/* 148:    */   {
/* 149:300 */     return (value >= this.min) && (value <= this.max);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean containsRange(Range range)
/* 153:    */   {
/* 154:317 */     if (range == null) {
/* 155:318 */       return false;
/* 156:    */     }
/* 157:320 */     return (containsLong(range.getMinimumLong())) && (containsLong(range.getMaximumLong()));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean overlapsRange(Range range)
/* 161:    */   {
/* 162:334 */     if (range == null) {
/* 163:335 */       return false;
/* 164:    */     }
/* 165:337 */     return (range.containsLong(this.min)) || (range.containsLong(this.max)) || (containsLong(range.getMinimumLong()));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean equals(Object obj)
/* 169:    */   {
/* 170:354 */     if (obj == this) {
/* 171:355 */       return true;
/* 172:    */     }
/* 173:357 */     if (!(obj instanceof LongRange)) {
/* 174:358 */       return false;
/* 175:    */     }
/* 176:360 */     LongRange range = (LongRange)obj;
/* 177:361 */     return (this.min == range.min) && (this.max == range.max);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int hashCode()
/* 181:    */   {
/* 182:370 */     if (this.hashCode == 0)
/* 183:    */     {
/* 184:371 */       this.hashCode = 17;
/* 185:372 */       this.hashCode = (37 * this.hashCode + getClass().hashCode());
/* 186:373 */       this.hashCode = (37 * this.hashCode + (int)(this.min ^ this.min >> 32));
/* 187:374 */       this.hashCode = (37 * this.hashCode + (int)(this.max ^ this.max >> 32));
/* 188:    */     }
/* 189:376 */     return this.hashCode;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String toString()
/* 193:    */   {
/* 194:387 */     if (this.toString == null)
/* 195:    */     {
/* 196:388 */       StrBuilder buf = new StrBuilder(32);
/* 197:389 */       buf.append("Range[");
/* 198:390 */       buf.append(this.min);
/* 199:391 */       buf.append(',');
/* 200:392 */       buf.append(this.max);
/* 201:393 */       buf.append(']');
/* 202:394 */       this.toString = buf.toString();
/* 203:    */     }
/* 204:396 */     return this.toString;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public long[] toArray()
/* 208:    */   {
/* 209:406 */     long[] array = new long[(int)(this.max - this.min + 1L)];
/* 210:407 */     for (int i = 0; i < array.length; i++) {
/* 211:408 */       array[i] = (this.min + i);
/* 212:    */     }
/* 213:410 */     return array;
/* 214:    */   }
/* 215:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.LongRange
 * JD-Core Version:    0.7.0.1
 */