/*   1:    */ package org.apache.commons.lang.mutable;
/*   2:    */ 
/*   3:    */ public class MutableShort
/*   4:    */   extends Number
/*   5:    */   implements Comparable, Mutable
/*   6:    */ {
/*   7:    */   private static final long serialVersionUID = -2135791679L;
/*   8:    */   private short value;
/*   9:    */   
/*  10:    */   public MutableShort() {}
/*  11:    */   
/*  12:    */   public MutableShort(short value)
/*  13:    */   {
/*  14: 53 */     this.value = value;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public MutableShort(Number value)
/*  18:    */   {
/*  19: 64 */     this.value = value.shortValue();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public MutableShort(String value)
/*  23:    */     throws NumberFormatException
/*  24:    */   {
/*  25: 76 */     this.value = Short.parseShort(value);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object getValue()
/*  29:    */   {
/*  30: 86 */     return new Short(this.value);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setValue(short value)
/*  34:    */   {
/*  35: 95 */     this.value = value;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setValue(Object value)
/*  39:    */   {
/*  40:106 */     setValue(((Number)value).shortValue());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void increment()
/*  44:    */   {
/*  45:116 */     this.value = ((short)(this.value + 1));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void decrement()
/*  49:    */   {
/*  50:125 */     this.value = ((short)(this.value - 1));
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void add(short operand)
/*  54:    */   {
/*  55:136 */     this.value = ((short)(this.value + operand));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void add(Number operand)
/*  59:    */   {
/*  60:147 */     this.value = ((short)(this.value + operand.shortValue()));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void subtract(short operand)
/*  64:    */   {
/*  65:157 */     this.value = ((short)(this.value - operand));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void subtract(Number operand)
/*  69:    */   {
/*  70:168 */     this.value = ((short)(this.value - operand.shortValue()));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public short shortValue()
/*  74:    */   {
/*  75:179 */     return this.value;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int intValue()
/*  79:    */   {
/*  80:188 */     return this.value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public long longValue()
/*  84:    */   {
/*  85:197 */     return this.value;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public float floatValue()
/*  89:    */   {
/*  90:206 */     return this.value;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public double doubleValue()
/*  94:    */   {
/*  95:215 */     return this.value;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Short toShort()
/*  99:    */   {
/* 100:225 */     return new Short(shortValue());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean equals(Object obj)
/* 104:    */   {
/* 105:238 */     if ((obj instanceof MutableShort)) {
/* 106:239 */       return this.value == ((MutableShort)obj).shortValue();
/* 107:    */     }
/* 108:241 */     return false;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int hashCode()
/* 112:    */   {
/* 113:250 */     return this.value;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int compareTo(Object obj)
/* 117:    */   {
/* 118:262 */     MutableShort other = (MutableShort)obj;
/* 119:263 */     short anotherVal = other.value;
/* 120:264 */     return this.value == anotherVal ? 0 : this.value < anotherVal ? -1 : 1;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String toString()
/* 124:    */   {
/* 125:274 */     return String.valueOf(this.value);
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.mutable.MutableShort
 * JD-Core Version:    0.7.0.1
 */