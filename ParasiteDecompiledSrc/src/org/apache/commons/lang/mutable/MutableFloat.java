/*   1:    */ package org.apache.commons.lang.mutable;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.math.NumberUtils;
/*   4:    */ 
/*   5:    */ public class MutableFloat
/*   6:    */   extends Number
/*   7:    */   implements Comparable, Mutable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 5787169186L;
/*  10:    */   private float value;
/*  11:    */   
/*  12:    */   public MutableFloat() {}
/*  13:    */   
/*  14:    */   public MutableFloat(float value)
/*  15:    */   {
/*  16: 55 */     this.value = value;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public MutableFloat(Number value)
/*  20:    */   {
/*  21: 66 */     this.value = value.floatValue();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public MutableFloat(String value)
/*  25:    */     throws NumberFormatException
/*  26:    */   {
/*  27: 78 */     this.value = Float.parseFloat(value);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Object getValue()
/*  31:    */   {
/*  32: 88 */     return new Float(this.value);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setValue(float value)
/*  36:    */   {
/*  37: 97 */     this.value = value;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setValue(Object value)
/*  41:    */   {
/*  42:108 */     setValue(((Number)value).floatValue());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isNaN()
/*  46:    */   {
/*  47:118 */     return Float.isNaN(this.value);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isInfinite()
/*  51:    */   {
/*  52:127 */     return Float.isInfinite(this.value);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void increment()
/*  56:    */   {
/*  57:137 */     this.value += 1.0F;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void decrement()
/*  61:    */   {
/*  62:146 */     this.value -= 1.0F;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void add(float operand)
/*  66:    */   {
/*  67:157 */     this.value += operand;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void add(Number operand)
/*  71:    */   {
/*  72:168 */     this.value += operand.floatValue();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void subtract(float operand)
/*  76:    */   {
/*  77:178 */     this.value -= operand;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void subtract(Number operand)
/*  81:    */   {
/*  82:189 */     this.value -= operand.floatValue();
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
/*  97:218 */     return this.value;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public double doubleValue()
/* 101:    */   {
/* 102:227 */     return this.value;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Float toFloat()
/* 106:    */   {
/* 107:237 */     return new Float(floatValue());
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean equals(Object obj)
/* 111:    */   {
/* 112:273 */     return ((obj instanceof MutableFloat)) && (Float.floatToIntBits(((MutableFloat)obj).value) == Float.floatToIntBits(this.value));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int hashCode()
/* 116:    */   {
/* 117:283 */     return Float.floatToIntBits(this.value);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int compareTo(Object obj)
/* 121:    */   {
/* 122:294 */     MutableFloat other = (MutableFloat)obj;
/* 123:295 */     float anotherVal = other.value;
/* 124:296 */     return NumberUtils.compare(this.value, anotherVal);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String toString()
/* 128:    */   {
/* 129:306 */     return String.valueOf(this.value);
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.mutable.MutableFloat
 * JD-Core Version:    0.7.0.1
 */