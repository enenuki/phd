/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.lang.text.StrBuilder;
/*   5:    */ 
/*   6:    */ public final class DoubleRange
/*   7:    */   extends Range
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 71849363892740L;
/*  11:    */   private final double min;
/*  12:    */   private final double max;
/*  13: 51 */   private transient Double minObject = null;
/*  14: 55 */   private transient Double maxObject = null;
/*  15: 59 */   private transient int hashCode = 0;
/*  16: 63 */   private transient String toString = null;
/*  17:    */   
/*  18:    */   public DoubleRange(double number)
/*  19:    */   {
/*  20: 74 */     if (Double.isNaN(number)) {
/*  21: 75 */       throw new IllegalArgumentException("The number must not be NaN");
/*  22:    */     }
/*  23: 77 */     this.min = number;
/*  24: 78 */     this.max = number;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public DoubleRange(Number number)
/*  28:    */   {
/*  29: 92 */     if (number == null) {
/*  30: 93 */       throw new IllegalArgumentException("The number must not be null");
/*  31:    */     }
/*  32: 95 */     this.min = number.doubleValue();
/*  33: 96 */     this.max = number.doubleValue();
/*  34: 97 */     if ((Double.isNaN(this.min)) || (Double.isNaN(this.max))) {
/*  35: 98 */       throw new IllegalArgumentException("The number must not be NaN");
/*  36:    */     }
/*  37:100 */     if ((number instanceof Double))
/*  38:    */     {
/*  39:101 */       this.minObject = ((Double)number);
/*  40:102 */       this.maxObject = ((Double)number);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public DoubleRange(double number1, double number2)
/*  45:    */   {
/*  46:119 */     if ((Double.isNaN(number1)) || (Double.isNaN(number2))) {
/*  47:120 */       throw new IllegalArgumentException("The numbers must not be NaN");
/*  48:    */     }
/*  49:122 */     if (number2 < number1)
/*  50:    */     {
/*  51:123 */       this.min = number2;
/*  52:124 */       this.max = number1;
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56:126 */       this.min = number1;
/*  57:127 */       this.max = number2;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public DoubleRange(Number number1, Number number2)
/*  62:    */   {
/*  63:145 */     if ((number1 == null) || (number2 == null)) {
/*  64:146 */       throw new IllegalArgumentException("The numbers must not be null");
/*  65:    */     }
/*  66:148 */     double number1val = number1.doubleValue();
/*  67:149 */     double number2val = number2.doubleValue();
/*  68:150 */     if ((Double.isNaN(number1val)) || (Double.isNaN(number2val))) {
/*  69:151 */       throw new IllegalArgumentException("The numbers must not be NaN");
/*  70:    */     }
/*  71:153 */     if (number2val < number1val)
/*  72:    */     {
/*  73:154 */       this.min = number2val;
/*  74:155 */       this.max = number1val;
/*  75:156 */       if ((number2 instanceof Double)) {
/*  76:157 */         this.minObject = ((Double)number2);
/*  77:    */       }
/*  78:159 */       if ((number1 instanceof Double)) {
/*  79:160 */         this.maxObject = ((Double)number1);
/*  80:    */       }
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:163 */       this.min = number1val;
/*  85:164 */       this.max = number2val;
/*  86:165 */       if ((number1 instanceof Double)) {
/*  87:166 */         this.minObject = ((Double)number1);
/*  88:    */       }
/*  89:168 */       if ((number2 instanceof Double)) {
/*  90:169 */         this.maxObject = ((Double)number2);
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Number getMinimumNumber()
/*  96:    */   {
/*  97:183 */     if (this.minObject == null) {
/*  98:184 */       this.minObject = new Double(this.min);
/*  99:    */     }
/* 100:186 */     return this.minObject;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public long getMinimumLong()
/* 104:    */   {
/* 105:197 */     return this.min;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getMinimumInteger()
/* 109:    */   {
/* 110:208 */     return (int)this.min;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public double getMinimumDouble()
/* 114:    */   {
/* 115:217 */     return this.min;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public float getMinimumFloat()
/* 119:    */   {
/* 120:228 */     return (float)this.min;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Number getMaximumNumber()
/* 124:    */   {
/* 125:237 */     if (this.maxObject == null) {
/* 126:238 */       this.maxObject = new Double(this.max);
/* 127:    */     }
/* 128:240 */     return this.maxObject;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public long getMaximumLong()
/* 132:    */   {
/* 133:251 */     return this.max;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getMaximumInteger()
/* 137:    */   {
/* 138:262 */     return (int)this.max;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public double getMaximumDouble()
/* 142:    */   {
/* 143:271 */     return this.max;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public float getMaximumFloat()
/* 147:    */   {
/* 148:282 */     return (float)this.max;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean containsNumber(Number number)
/* 152:    */   {
/* 153:298 */     if (number == null) {
/* 154:299 */       return false;
/* 155:    */     }
/* 156:301 */     return containsDouble(number.doubleValue());
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean containsDouble(double value)
/* 160:    */   {
/* 161:316 */     return (value >= this.min) && (value <= this.max);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean containsRange(Range range)
/* 165:    */   {
/* 166:333 */     if (range == null) {
/* 167:334 */       return false;
/* 168:    */     }
/* 169:336 */     return (containsDouble(range.getMinimumDouble())) && (containsDouble(range.getMaximumDouble()));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean overlapsRange(Range range)
/* 173:    */   {
/* 174:350 */     if (range == null) {
/* 175:351 */       return false;
/* 176:    */     }
/* 177:353 */     return (range.containsDouble(this.min)) || (range.containsDouble(this.max)) || (containsDouble(range.getMinimumDouble()));
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean equals(Object obj)
/* 181:    */   {
/* 182:370 */     if (obj == this) {
/* 183:371 */       return true;
/* 184:    */     }
/* 185:373 */     if (!(obj instanceof DoubleRange)) {
/* 186:374 */       return false;
/* 187:    */     }
/* 188:376 */     DoubleRange range = (DoubleRange)obj;
/* 189:377 */     return (Double.doubleToLongBits(this.min) == Double.doubleToLongBits(range.min)) && (Double.doubleToLongBits(this.max) == Double.doubleToLongBits(range.max));
/* 190:    */   }
/* 191:    */   
/* 192:    */   public int hashCode()
/* 193:    */   {
/* 194:387 */     if (this.hashCode == 0)
/* 195:    */     {
/* 196:388 */       this.hashCode = 17;
/* 197:389 */       this.hashCode = (37 * this.hashCode + getClass().hashCode());
/* 198:390 */       long lng = Double.doubleToLongBits(this.min);
/* 199:391 */       this.hashCode = (37 * this.hashCode + (int)(lng ^ lng >> 32));
/* 200:392 */       lng = Double.doubleToLongBits(this.max);
/* 201:393 */       this.hashCode = (37 * this.hashCode + (int)(lng ^ lng >> 32));
/* 202:    */     }
/* 203:395 */     return this.hashCode;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String toString()
/* 207:    */   {
/* 208:406 */     if (this.toString == null)
/* 209:    */     {
/* 210:407 */       StrBuilder buf = new StrBuilder(32);
/* 211:408 */       buf.append("Range[");
/* 212:409 */       buf.append(this.min);
/* 213:410 */       buf.append(',');
/* 214:411 */       buf.append(this.max);
/* 215:412 */       buf.append(']');
/* 216:413 */       this.toString = buf.toString();
/* 217:    */     }
/* 218:415 */     return this.toString;
/* 219:    */   }
/* 220:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.DoubleRange
 * JD-Core Version:    0.7.0.1
 */