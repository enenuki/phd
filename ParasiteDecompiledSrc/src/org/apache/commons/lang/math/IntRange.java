/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.lang.text.StrBuilder;
/*   5:    */ 
/*   6:    */ public final class IntRange
/*   7:    */   extends Range
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 71849363892730L;
/*  11:    */   private final int min;
/*  12:    */   private final int max;
/*  13: 51 */   private transient Integer minObject = null;
/*  14: 55 */   private transient Integer maxObject = null;
/*  15: 59 */   private transient int hashCode = 0;
/*  16: 63 */   private transient String toString = null;
/*  17:    */   
/*  18:    */   public IntRange(int number)
/*  19:    */   {
/*  20: 73 */     this.min = number;
/*  21: 74 */     this.max = number;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public IntRange(Number number)
/*  25:    */   {
/*  26: 86 */     if (number == null) {
/*  27: 87 */       throw new IllegalArgumentException("The number must not be null");
/*  28:    */     }
/*  29: 89 */     this.min = number.intValue();
/*  30: 90 */     this.max = number.intValue();
/*  31: 91 */     if ((number instanceof Integer))
/*  32:    */     {
/*  33: 92 */       this.minObject = ((Integer)number);
/*  34: 93 */       this.maxObject = ((Integer)number);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public IntRange(int number1, int number2)
/*  39:    */   {
/*  40:109 */     if (number2 < number1)
/*  41:    */     {
/*  42:110 */       this.min = number2;
/*  43:111 */       this.max = number1;
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47:113 */       this.min = number1;
/*  48:114 */       this.max = number2;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public IntRange(Number number1, Number number2)
/*  53:    */   {
/*  54:131 */     if ((number1 == null) || (number2 == null)) {
/*  55:132 */       throw new IllegalArgumentException("The numbers must not be null");
/*  56:    */     }
/*  57:134 */     int number1val = number1.intValue();
/*  58:135 */     int number2val = number2.intValue();
/*  59:136 */     if (number2val < number1val)
/*  60:    */     {
/*  61:137 */       this.min = number2val;
/*  62:138 */       this.max = number1val;
/*  63:139 */       if ((number2 instanceof Integer)) {
/*  64:140 */         this.minObject = ((Integer)number2);
/*  65:    */       }
/*  66:142 */       if ((number1 instanceof Integer)) {
/*  67:143 */         this.maxObject = ((Integer)number1);
/*  68:    */       }
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72:146 */       this.min = number1val;
/*  73:147 */       this.max = number2val;
/*  74:148 */       if ((number1 instanceof Integer)) {
/*  75:149 */         this.minObject = ((Integer)number1);
/*  76:    */       }
/*  77:151 */       if ((number2 instanceof Integer)) {
/*  78:152 */         this.maxObject = ((Integer)number2);
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Number getMinimumNumber()
/*  84:    */   {
/*  85:166 */     if (this.minObject == null) {
/*  86:167 */       this.minObject = new Integer(this.min);
/*  87:    */     }
/*  88:169 */     return this.minObject;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public long getMinimumLong()
/*  92:    */   {
/*  93:178 */     return this.min;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getMinimumInteger()
/*  97:    */   {
/*  98:187 */     return this.min;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public double getMinimumDouble()
/* 102:    */   {
/* 103:196 */     return this.min;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public float getMinimumFloat()
/* 107:    */   {
/* 108:205 */     return this.min;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Number getMaximumNumber()
/* 112:    */   {
/* 113:214 */     if (this.maxObject == null) {
/* 114:215 */       this.maxObject = new Integer(this.max);
/* 115:    */     }
/* 116:217 */     return this.maxObject;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public long getMaximumLong()
/* 120:    */   {
/* 121:226 */     return this.max;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getMaximumInteger()
/* 125:    */   {
/* 126:235 */     return this.max;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public double getMaximumDouble()
/* 130:    */   {
/* 131:244 */     return this.max;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public float getMaximumFloat()
/* 135:    */   {
/* 136:253 */     return this.max;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean containsNumber(Number number)
/* 140:    */   {
/* 141:269 */     if (number == null) {
/* 142:270 */       return false;
/* 143:    */     }
/* 144:272 */     return containsInteger(number.intValue());
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean containsInteger(int value)
/* 148:    */   {
/* 149:287 */     return (value >= this.min) && (value <= this.max);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean containsRange(Range range)
/* 153:    */   {
/* 154:304 */     if (range == null) {
/* 155:305 */       return false;
/* 156:    */     }
/* 157:307 */     return (containsInteger(range.getMinimumInteger())) && (containsInteger(range.getMaximumInteger()));
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean overlapsRange(Range range)
/* 161:    */   {
/* 162:321 */     if (range == null) {
/* 163:322 */       return false;
/* 164:    */     }
/* 165:324 */     return (range.containsInteger(this.min)) || (range.containsInteger(this.max)) || (containsInteger(range.getMinimumInteger()));
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean equals(Object obj)
/* 169:    */   {
/* 170:341 */     if (obj == this) {
/* 171:342 */       return true;
/* 172:    */     }
/* 173:344 */     if (!(obj instanceof IntRange)) {
/* 174:345 */       return false;
/* 175:    */     }
/* 176:347 */     IntRange range = (IntRange)obj;
/* 177:348 */     return (this.min == range.min) && (this.max == range.max);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int hashCode()
/* 181:    */   {
/* 182:357 */     if (this.hashCode == 0)
/* 183:    */     {
/* 184:358 */       this.hashCode = 17;
/* 185:359 */       this.hashCode = (37 * this.hashCode + getClass().hashCode());
/* 186:360 */       this.hashCode = (37 * this.hashCode + this.min);
/* 187:361 */       this.hashCode = (37 * this.hashCode + this.max);
/* 188:    */     }
/* 189:363 */     return this.hashCode;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String toString()
/* 193:    */   {
/* 194:374 */     if (this.toString == null)
/* 195:    */     {
/* 196:375 */       StrBuilder buf = new StrBuilder(32);
/* 197:376 */       buf.append("Range[");
/* 198:377 */       buf.append(this.min);
/* 199:378 */       buf.append(',');
/* 200:379 */       buf.append(this.max);
/* 201:380 */       buf.append(']');
/* 202:381 */       this.toString = buf.toString();
/* 203:    */     }
/* 204:383 */     return this.toString;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int[] toArray()
/* 208:    */   {
/* 209:393 */     int[] array = new int[this.max - this.min + 1];
/* 210:394 */     for (int i = 0; i < array.length; i++) {
/* 211:395 */       array[i] = (this.min + i);
/* 212:    */     }
/* 213:398 */     return array;
/* 214:    */   }
/* 215:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.IntRange
 * JD-Core Version:    0.7.0.1
 */