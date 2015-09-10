/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.lang.text.StrBuilder;
/*   5:    */ 
/*   6:    */ public final class NumberRange
/*   7:    */   extends Range
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 71849363892710L;
/*  11:    */   private final Number min;
/*  12:    */   private final Number max;
/*  13: 53 */   private transient int hashCode = 0;
/*  14: 57 */   private transient String toString = null;
/*  15:    */   
/*  16:    */   public NumberRange(Number num)
/*  17:    */   {
/*  18: 69 */     if (num == null) {
/*  19: 70 */       throw new IllegalArgumentException("The number must not be null");
/*  20:    */     }
/*  21: 72 */     if (!(num instanceof Comparable)) {
/*  22: 73 */       throw new IllegalArgumentException("The number must implement Comparable");
/*  23:    */     }
/*  24: 75 */     if (((num instanceof Double)) && (((Double)num).isNaN())) {
/*  25: 76 */       throw new IllegalArgumentException("The number must not be NaN");
/*  26:    */     }
/*  27: 78 */     if (((num instanceof Float)) && (((Float)num).isNaN())) {
/*  28: 79 */       throw new IllegalArgumentException("The number must not be NaN");
/*  29:    */     }
/*  30: 82 */     this.min = num;
/*  31: 83 */     this.max = num;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public NumberRange(Number num1, Number num2)
/*  35:    */   {
/*  36:105 */     if ((num1 == null) || (num2 == null)) {
/*  37:106 */       throw new IllegalArgumentException("The numbers must not be null");
/*  38:    */     }
/*  39:108 */     if (num1.getClass() != num2.getClass()) {
/*  40:109 */       throw new IllegalArgumentException("The numbers must be of the same type");
/*  41:    */     }
/*  42:111 */     if (!(num1 instanceof Comparable)) {
/*  43:112 */       throw new IllegalArgumentException("The numbers must implement Comparable");
/*  44:    */     }
/*  45:114 */     if ((num1 instanceof Double))
/*  46:    */     {
/*  47:115 */       if ((((Double)num1).isNaN()) || (((Double)num2).isNaN())) {
/*  48:116 */         throw new IllegalArgumentException("The number must not be NaN");
/*  49:    */       }
/*  50:    */     }
/*  51:118 */     else if (((num1 instanceof Float)) && (
/*  52:119 */       (((Float)num1).isNaN()) || (((Float)num2).isNaN()))) {
/*  53:120 */       throw new IllegalArgumentException("The number must not be NaN");
/*  54:    */     }
/*  55:124 */     int compare = ((Comparable)num1).compareTo(num2);
/*  56:125 */     if (compare == 0)
/*  57:    */     {
/*  58:126 */       this.min = num1;
/*  59:127 */       this.max = num1;
/*  60:    */     }
/*  61:128 */     else if (compare > 0)
/*  62:    */     {
/*  63:129 */       this.min = num2;
/*  64:130 */       this.max = num1;
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68:132 */       this.min = num1;
/*  69:133 */       this.max = num2;
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Number getMinimumNumber()
/*  74:    */   {
/*  75:146 */     return this.min;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Number getMaximumNumber()
/*  79:    */   {
/*  80:155 */     return this.max;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean containsNumber(Number number)
/*  84:    */   {
/*  85:172 */     if (number == null) {
/*  86:173 */       return false;
/*  87:    */     }
/*  88:175 */     if (number.getClass() != this.min.getClass()) {
/*  89:176 */       throw new IllegalArgumentException("The number must be of the same type as the range numbers");
/*  90:    */     }
/*  91:178 */     int compareMin = ((Comparable)this.min).compareTo(number);
/*  92:179 */     int compareMax = ((Comparable)this.max).compareTo(number);
/*  93:180 */     return (compareMin <= 0) && (compareMax >= 0);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean equals(Object obj)
/*  97:    */   {
/*  98:199 */     if (obj == this) {
/*  99:200 */       return true;
/* 100:    */     }
/* 101:202 */     if (!(obj instanceof NumberRange)) {
/* 102:203 */       return false;
/* 103:    */     }
/* 104:205 */     NumberRange range = (NumberRange)obj;
/* 105:206 */     return (this.min.equals(range.min)) && (this.max.equals(range.max));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int hashCode()
/* 109:    */   {
/* 110:215 */     if (this.hashCode == 0)
/* 111:    */     {
/* 112:216 */       this.hashCode = 17;
/* 113:217 */       this.hashCode = (37 * this.hashCode + getClass().hashCode());
/* 114:218 */       this.hashCode = (37 * this.hashCode + this.min.hashCode());
/* 115:219 */       this.hashCode = (37 * this.hashCode + this.max.hashCode());
/* 116:    */     }
/* 117:221 */     return this.hashCode;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String toString()
/* 121:    */   {
/* 122:232 */     if (this.toString == null)
/* 123:    */     {
/* 124:233 */       StrBuilder buf = new StrBuilder(32);
/* 125:234 */       buf.append("Range[");
/* 126:235 */       buf.append(this.min);
/* 127:236 */       buf.append(',');
/* 128:237 */       buf.append(this.max);
/* 129:238 */       buf.append(']');
/* 130:239 */       this.toString = buf.toString();
/* 131:    */     }
/* 132:241 */     return this.toString;
/* 133:    */   }
/* 134:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.NumberRange
 * JD-Core Version:    0.7.0.1
 */