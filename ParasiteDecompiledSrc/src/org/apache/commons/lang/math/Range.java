/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.text.StrBuilder;
/*   4:    */ 
/*   5:    */ public abstract class Range
/*   6:    */ {
/*   7:    */   public abstract Number getMinimumNumber();
/*   8:    */   
/*   9:    */   public long getMinimumLong()
/*  10:    */   {
/*  11: 60 */     return getMinimumNumber().longValue();
/*  12:    */   }
/*  13:    */   
/*  14:    */   public int getMinimumInteger()
/*  15:    */   {
/*  16: 72 */     return getMinimumNumber().intValue();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public double getMinimumDouble()
/*  20:    */   {
/*  21: 84 */     return getMinimumNumber().doubleValue();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public float getMinimumFloat()
/*  25:    */   {
/*  26: 96 */     return getMinimumNumber().floatValue();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public abstract Number getMaximumNumber();
/*  30:    */   
/*  31:    */   public long getMaximumLong()
/*  32:    */   {
/*  33:115 */     return getMaximumNumber().longValue();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getMaximumInteger()
/*  37:    */   {
/*  38:127 */     return getMaximumNumber().intValue();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public double getMaximumDouble()
/*  42:    */   {
/*  43:139 */     return getMaximumNumber().doubleValue();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public float getMaximumFloat()
/*  47:    */   {
/*  48:151 */     return getMaximumNumber().floatValue();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public abstract boolean containsNumber(Number paramNumber);
/*  52:    */   
/*  53:    */   public boolean containsLong(Number value)
/*  54:    */   {
/*  55:186 */     if (value == null) {
/*  56:187 */       return false;
/*  57:    */     }
/*  58:189 */     return containsLong(value.longValue());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean containsLong(long value)
/*  62:    */   {
/*  63:204 */     return (value >= getMinimumLong()) && (value <= getMaximumLong());
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean containsInteger(Number value)
/*  67:    */   {
/*  68:220 */     if (value == null) {
/*  69:221 */       return false;
/*  70:    */     }
/*  71:223 */     return containsInteger(value.intValue());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean containsInteger(int value)
/*  75:    */   {
/*  76:238 */     return (value >= getMinimumInteger()) && (value <= getMaximumInteger());
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean containsDouble(Number value)
/*  80:    */   {
/*  81:254 */     if (value == null) {
/*  82:255 */       return false;
/*  83:    */     }
/*  84:257 */     return containsDouble(value.doubleValue());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean containsDouble(double value)
/*  88:    */   {
/*  89:272 */     int compareMin = NumberUtils.compare(getMinimumDouble(), value);
/*  90:273 */     int compareMax = NumberUtils.compare(getMaximumDouble(), value);
/*  91:274 */     return (compareMin <= 0) && (compareMax >= 0);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean containsFloat(Number value)
/*  95:    */   {
/*  96:290 */     if (value == null) {
/*  97:291 */       return false;
/*  98:    */     }
/*  99:293 */     return containsFloat(value.floatValue());
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean containsFloat(float value)
/* 103:    */   {
/* 104:308 */     int compareMin = NumberUtils.compare(getMinimumFloat(), value);
/* 105:309 */     int compareMax = NumberUtils.compare(getMaximumFloat(), value);
/* 106:310 */     return (compareMin <= 0) && (compareMax >= 0);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean containsRange(Range range)
/* 110:    */   {
/* 111:334 */     if (range == null) {
/* 112:335 */       return false;
/* 113:    */     }
/* 114:337 */     return (containsNumber(range.getMinimumNumber())) && (containsNumber(range.getMaximumNumber()));
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean overlapsRange(Range range)
/* 118:    */   {
/* 119:360 */     if (range == null) {
/* 120:361 */       return false;
/* 121:    */     }
/* 122:363 */     return (range.containsNumber(getMinimumNumber())) || (range.containsNumber(getMaximumNumber())) || (containsNumber(range.getMinimumNumber()));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean equals(Object obj)
/* 126:    */   {
/* 127:384 */     if (obj == this) {
/* 128:385 */       return true;
/* 129:    */     }
/* 130:386 */     if ((obj == null) || (obj.getClass() != getClass())) {
/* 131:387 */       return false;
/* 132:    */     }
/* 133:389 */     Range range = (Range)obj;
/* 134:390 */     return (getMinimumNumber().equals(range.getMinimumNumber())) && (getMaximumNumber().equals(range.getMaximumNumber()));
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int hashCode()
/* 138:    */   {
/* 139:405 */     int result = 17;
/* 140:406 */     result = 37 * result + getClass().hashCode();
/* 141:407 */     result = 37 * result + getMinimumNumber().hashCode();
/* 142:408 */     result = 37 * result + getMaximumNumber().hashCode();
/* 143:409 */     return result;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String toString()
/* 147:    */   {
/* 148:424 */     StrBuilder buf = new StrBuilder(32);
/* 149:425 */     buf.append("Range[");
/* 150:426 */     buf.append(getMinimumNumber());
/* 151:427 */     buf.append(',');
/* 152:428 */     buf.append(getMaximumNumber());
/* 153:429 */     buf.append(']');
/* 154:430 */     return buf.toString();
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.Range
 * JD-Core Version:    0.7.0.1
 */