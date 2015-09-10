/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.text.StrBuilder;
/*   4:    */ 
/*   5:    */ /**
/*   6:    */  * @deprecated
/*   7:    */  */
/*   8:    */ public final class NumberRange
/*   9:    */ {
/*  10:    */   private final Number min;
/*  11:    */   private final Number max;
/*  12:    */   
/*  13:    */   public NumberRange(Number num)
/*  14:    */   {
/*  15: 55 */     if (num == null) {
/*  16: 56 */       throw new NullPointerException("The number must not be null");
/*  17:    */     }
/*  18: 59 */     this.min = num;
/*  19: 60 */     this.max = num;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public NumberRange(Number min, Number max)
/*  23:    */   {
/*  24: 76 */     if (min == null) {
/*  25: 77 */       throw new NullPointerException("The minimum value must not be null");
/*  26:    */     }
/*  27: 78 */     if (max == null) {
/*  28: 79 */       throw new NullPointerException("The maximum value must not be null");
/*  29:    */     }
/*  30: 82 */     if (max.doubleValue() < min.doubleValue())
/*  31:    */     {
/*  32: 83 */       this.min = (this.max = min);
/*  33:    */     }
/*  34:    */     else
/*  35:    */     {
/*  36: 85 */       this.min = min;
/*  37: 86 */       this.max = max;
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Number getMinimum()
/*  42:    */   {
/*  43: 96 */     return this.min;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Number getMaximum()
/*  47:    */   {
/*  48:105 */     return this.max;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean includesNumber(Number number)
/*  52:    */   {
/*  53:117 */     if (number == null) {
/*  54:118 */       return false;
/*  55:    */     }
/*  56:120 */     return (this.min.doubleValue() <= number.doubleValue()) && (this.max.doubleValue() >= number.doubleValue());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean includesRange(NumberRange range)
/*  60:    */   {
/*  61:134 */     if (range == null) {
/*  62:135 */       return false;
/*  63:    */     }
/*  64:137 */     return (includesNumber(range.min)) && (includesNumber(range.max));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean overlaps(NumberRange range)
/*  68:    */   {
/*  69:150 */     if (range == null) {
/*  70:151 */       return false;
/*  71:    */     }
/*  72:153 */     return (range.includesNumber(this.min)) || (range.includesNumber(this.max)) || (includesRange(range));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean equals(Object obj)
/*  76:    */   {
/*  77:167 */     if (obj == this) {
/*  78:168 */       return true;
/*  79:    */     }
/*  80:169 */     if (!(obj instanceof NumberRange)) {
/*  81:170 */       return false;
/*  82:    */     }
/*  83:172 */     NumberRange range = (NumberRange)obj;
/*  84:173 */     return (this.min.equals(range.min)) && (this.max.equals(range.max));
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int hashCode()
/*  88:    */   {
/*  89:183 */     int result = 17;
/*  90:184 */     result = 37 * result + this.min.hashCode();
/*  91:185 */     result = 37 * result + this.max.hashCode();
/*  92:186 */     return result;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String toString()
/*  96:    */   {
/*  97:199 */     StrBuilder sb = new StrBuilder();
/*  98:201 */     if (this.min.doubleValue() < 0.0D) {
/*  99:202 */       sb.append('(').append(this.min).append(')');
/* 100:    */     } else {
/* 101:206 */       sb.append(this.min);
/* 102:    */     }
/* 103:209 */     sb.append('-');
/* 104:211 */     if (this.max.doubleValue() < 0.0D) {
/* 105:212 */       sb.append('(').append(this.max).append(')');
/* 106:    */     } else {
/* 107:216 */       sb.append(this.max);
/* 108:    */     }
/* 109:219 */     return sb.toString();
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.NumberRange
 * JD-Core Version:    0.7.0.1
 */