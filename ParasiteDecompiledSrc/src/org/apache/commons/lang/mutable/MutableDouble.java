/*   1:    */ package org.apache.commons.lang.mutable;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.math.NumberUtils;
/*   4:    */ 
/*   5:    */ public class MutableDouble
/*   6:    */   extends Number
/*   7:    */   implements Comparable, Mutable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1587163916L;
/*  10:    */   private double value;
/*  11:    */   
/*  12:    */   public MutableDouble() {}
/*  13:    */   
/*  14:    */   public MutableDouble(double value)
/*  15:    */   {
/*  16: 55 */     this.value = value;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public MutableDouble(Number value)
/*  20:    */   {
/*  21: 66 */     this.value = value.doubleValue();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public MutableDouble(String value)
/*  25:    */     throws NumberFormatException
/*  26:    */   {
/*  27: 78 */     this.value = Double.parseDouble(value);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Object getValue()
/*  31:    */   {
/*  32: 88 */     return new Double(this.value);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setValue(double value)
/*  36:    */   {
/*  37: 97 */     this.value = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setValue(Object value)
/*  41:    */   {
/*  42:108 */     setValue(((Number)value).doubleValue());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isNaN()
/*  46:    */   {
/*  47:118 */     return Double.isNaN(this.value);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isInfinite()
/*  51:    */   {
/*  52:127 */     return Double.isInfinite(this.value);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void increment()
/*  56:    */   {
/*  57:137 */     this.value += 1.0D;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void decrement()
/*  61:    */   {
/*  62:146 */     this.value -= 1.0D;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void add(double operand)
/*  66:    */   {
/*  67:157 */     this.value += operand;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void add(Number operand)
/*  71:    */   {
/*  72:168 */     this.value += operand.doubleValue();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void subtract(double operand)
/*  76:    */   {
/*  77:178 */     this.value -= operand;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void subtract(Number operand)
/*  81:    */   {
/*  82:189 */     this.value -= operand.doubleValue();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int intValue()
/*  86:    */   {
/*  87:200 */     return (int)this.value;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public long longValue()
/*  91:    */   {
/*  92:209 */     return this.value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public float floatValue()
/*  96:    */   {
/*  97:218 */     return (float)this.value;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public double doubleValue()
/* 101:    */   {
/* 102:227 */     return this.value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Double toDouble()
/* 106:    */   {
/* 107:237 */     return new Double(doubleValue());
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean equals(Object obj)
/* 111:    */   {
/* 112:271 */     return ((obj instanceof MutableDouble)) && (Double.doubleToLongBits(((MutableDouble)obj).value) == Double.doubleToLongBits(this.value));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int hashCode()
/* 116:    */   {
/* 117:281 */     long bits = Double.doubleToLongBits(this.value);
/* 118:282 */     return (int)(bits ^ bits >>> 32);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int compareTo(Object obj)
/* 122:    */   {
/* 123:294 */     MutableDouble other = (MutableDouble)obj;
/* 124:295 */     double anotherVal = other.value;
/* 125:296 */     return NumberUtils.compare(this.value, anotherVal);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String toString()
/* 129:    */   {
/* 130:306 */     return String.valueOf(this.value);
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.mutable.MutableDouble
 * JD-Core Version:    0.7.0.1
 */