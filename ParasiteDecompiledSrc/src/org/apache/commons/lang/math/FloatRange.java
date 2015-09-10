/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public final class FloatRange
/*   6:    */   extends Range
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 71849363892750L;
/*  10:    */   private final float min;
/*  11:    */   private final float max;
/*  12: 49 */   private transient Float minObject = null;
/*  13: 53 */   private transient Float maxObject = null;
/*  14: 57 */   private transient int hashCode = 0;
/*  15: 61 */   private transient String toString = null;
/*  16:    */   
/*  17:    */   public FloatRange(float number)
/*  18:    */   {
/*  19: 72 */     if (Float.isNaN(number)) {
/*  20: 73 */       throw new IllegalArgumentException("The number must not be NaN");
/*  21:    */     }
/*  22: 75 */     this.min = number;
/*  23: 76 */     this.max = number;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public FloatRange(Number number)
/*  27:    */   {
/*  28: 90 */     if (number == null) {
/*  29: 91 */       throw new IllegalArgumentException("The number must not be null");
/*  30:    */     }
/*  31: 93 */     this.min = number.floatValue();
/*  32: 94 */     this.max = number.floatValue();
/*  33: 95 */     if ((Float.isNaN(this.min)) || (Float.isNaN(this.max))) {
/*  34: 96 */       throw new IllegalArgumentException("The number must not be NaN");
/*  35:    */     }
/*  36: 98 */     if ((number instanceof Float))
/*  37:    */     {
/*  38: 99 */       this.minObject = ((Float)number);
/*  39:100 */       this.maxObject = ((Float)number);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public FloatRange(float number1, float number2)
/*  44:    */   {
/*  45:117 */     if ((Float.isNaN(number1)) || (Float.isNaN(number2))) {
/*  46:118 */       throw new IllegalArgumentException("The numbers must not be NaN");
/*  47:    */     }
/*  48:120 */     if (number2 < number1)
/*  49:    */     {
/*  50:121 */       this.min = number2;
/*  51:122 */       this.max = number1;
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55:124 */       this.min = number1;
/*  56:125 */       this.max = number2;
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public FloatRange(Number number1, Number number2)
/*  61:    */   {
/*  62:143 */     if ((number1 == null) || (number2 == null)) {
/*  63:144 */       throw new IllegalArgumentException("The numbers must not be null");
/*  64:    */     }
/*  65:146 */     float number1val = number1.floatValue();
/*  66:147 */     float number2val = number2.floatValue();
/*  67:148 */     if ((Float.isNaN(number1val)) || (Float.isNaN(number2val))) {
/*  68:149 */       throw new IllegalArgumentException("The numbers must not be NaN");
/*  69:    */     }
/*  70:151 */     if (number2val < number1val)
/*  71:    */     {
/*  72:152 */       this.min = number2val;
/*  73:153 */       this.max = number1val;
/*  74:154 */       if ((number2 instanceof Float)) {
/*  75:155 */         this.minObject = ((Float)number2);
/*  76:    */       }
/*  77:157 */       if ((number1 instanceof Float)) {
/*  78:158 */         this.maxObject = ((Float)number1);
/*  79:    */       }
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:161 */       this.min = number1val;
/*  84:162 */       this.max = number2val;
/*  85:163 */       if ((number1 instanceof Float)) {
/*  86:164 */         this.minObject = ((Float)number1);
/*  87:    */       }
/*  88:166 */       if ((number2 instanceof Float)) {
/*  89:167 */         this.maxObject = ((Float)number2);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Number getMinimumNumber()
/*  95:    */   {
/*  96:181 */     if (this.minObject == null) {
/*  97:182 */       this.minObject = new Float(this.min);
/*  98:    */     }
/*  99:184 */     return this.minObject;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public long getMinimumLong()
/* 103:    */   {
/* 104:195 */     return this.min;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getMinimumInteger()
/* 108:    */   {
/* 109:206 */     return (int)this.min;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public double getMinimumDouble()
/* 113:    */   {
/* 114:215 */     return this.min;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public float getMinimumFloat()
/* 118:    */   {
/* 119:224 */     return this.min;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Number getMaximumNumber()
/* 123:    */   {
/* 124:233 */     if (this.maxObject == null) {
/* 125:234 */       this.maxObject = new Float(this.max);
/* 126:    */     }
/* 127:236 */     return this.maxObject;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public long getMaximumLong()
/* 131:    */   {
/* 132:247 */     return this.max;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public int getMaximumInteger()
/* 136:    */   {
/* 137:258 */     return (int)this.max;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public double getMaximumDouble()
/* 141:    */   {
/* 142:267 */     return this.max;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public float getMaximumFloat()
/* 146:    */   {
/* 147:276 */     return this.max;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean containsNumber(Number number)
/* 151:    */   {
/* 152:292 */     if (number == null) {
/* 153:293 */       return false;
/* 154:    */     }
/* 155:295 */     return containsFloat(number.floatValue());
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean containsFloat(float value)
/* 159:    */   {
/* 160:310 */     return (value >= this.min) && (value <= this.max);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean containsRange(Range range)
/* 164:    */   {
/* 165:327 */     if (range == null) {
/* 166:328 */       return false;
/* 167:    */     }
/* 168:330 */     return (containsFloat(range.getMinimumFloat())) && (containsFloat(range.getMaximumFloat()));
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean overlapsRange(Range range)
/* 172:    */   {
/* 173:344 */     if (range == null) {
/* 174:345 */       return false;
/* 175:    */     }
/* 176:347 */     return (range.containsFloat(this.min)) || (range.containsFloat(this.max)) || (containsFloat(range.getMinimumFloat()));
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean equals(Object obj)
/* 180:    */   {
/* 181:364 */     if (obj == this) {
/* 182:365 */       return true;
/* 183:    */     }
/* 184:367 */     if (!(obj instanceof FloatRange)) {
/* 185:368 */       return false;
/* 186:    */     }
/* 187:370 */     FloatRange range = (FloatRange)obj;
/* 188:371 */     return (Float.floatToIntBits(this.min) == Float.floatToIntBits(range.min)) && (Float.floatToIntBits(this.max) == Float.floatToIntBits(range.max));
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int hashCode()
/* 192:    */   {
/* 193:381 */     if (this.hashCode == 0)
/* 194:    */     {
/* 195:382 */       this.hashCode = 17;
/* 196:383 */       this.hashCode = (37 * this.hashCode + getClass().hashCode());
/* 197:384 */       this.hashCode = (37 * this.hashCode + Float.floatToIntBits(this.min));
/* 198:385 */       this.hashCode = (37 * this.hashCode + Float.floatToIntBits(this.max));
/* 199:    */     }
/* 200:387 */     return this.hashCode;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String toString()
/* 204:    */   {
/* 205:398 */     if (this.toString == null)
/* 206:    */     {
/* 207:399 */       StringBuffer buf = new StringBuffer(32);
/* 208:400 */       buf.append("Range[");
/* 209:401 */       buf.append(this.min);
/* 210:402 */       buf.append(',');
/* 211:403 */       buf.append(this.max);
/* 212:404 */       buf.append(']');
/* 213:405 */       this.toString = buf.toString();
/* 214:    */     }
/* 215:407 */     return this.toString;
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.FloatRange
 * JD-Core Version:    0.7.0.1
 */