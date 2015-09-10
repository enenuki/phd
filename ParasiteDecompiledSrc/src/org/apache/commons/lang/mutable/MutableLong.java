/*   1:    */ package org.apache.commons.lang.mutable;
/*   2:    */ 
/*   3:    */ public class MutableLong
/*   4:    */   extends Number
/*   5:    */   implements Comparable, Mutable
/*   6:    */ {
/*   7:    */   private static final long serialVersionUID = 62986528375L;
/*   8:    */   private long value;
/*   9:    */   
/*  10:    */   public MutableLong() {}
/*  11:    */   
/*  12:    */   public MutableLong(long value)
/*  13:    */   {
/*  14: 53 */     this.value = value;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public MutableLong(Number value)
/*  18:    */   {
/*  19: 64 */     this.value = value.longValue();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public MutableLong(String value)
/*  23:    */     throws NumberFormatException
/*  24:    */   {
/*  25: 76 */     this.value = Long.parseLong(value);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object getValue()
/*  29:    */   {
/*  30: 86 */     return new Long(this.value);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setValue(long value)
/*  34:    */   {
/*  35: 95 */     this.value = value;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setValue(Object value)
/*  39:    */   {
/*  40:106 */     setValue(((Number)value).longValue());
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void increment()
/*  44:    */   {
/*  45:116 */     this.value += 1L;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void decrement()
/*  49:    */   {
/*  50:125 */     this.value -= 1L;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void add(long operand)
/*  54:    */   {
/*  55:136 */     this.value += operand;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void add(Number operand)
/*  59:    */   {
/*  60:147 */     this.value += operand.longValue();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void subtract(long operand)
/*  64:    */   {
/*  65:157 */     this.value -= operand;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void subtract(Number operand)
/*  69:    */   {
/*  70:168 */     this.value -= operand.longValue();
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int intValue()
/*  74:    */   {
/*  75:179 */     return (int)this.value;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public long longValue()
/*  79:    */   {
/*  80:188 */     return this.value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public float floatValue()
/*  84:    */   {
/*  85:197 */     return (float)this.value;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public double doubleValue()
/*  89:    */   {
/*  90:206 */     return this.value;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Long toLong()
/*  94:    */   {
/*  95:216 */     return new Long(longValue());
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean equals(Object obj)
/*  99:    */   {
/* 100:229 */     if ((obj instanceof MutableLong)) {
/* 101:230 */       return this.value == ((MutableLong)obj).longValue();
/* 102:    */     }
/* 103:232 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int hashCode()
/* 107:    */   {
/* 108:241 */     return (int)(this.value ^ this.value >>> 32);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int compareTo(Object obj)
/* 112:    */   {
/* 113:253 */     MutableLong other = (MutableLong)obj;
/* 114:254 */     long anotherVal = other.value;
/* 115:255 */     return this.value == anotherVal ? 0 : this.value < anotherVal ? -1 : 1;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String toString()
/* 119:    */   {
/* 120:265 */     return String.valueOf(this.value);
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.mutable.MutableLong
 * JD-Core Version:    0.7.0.1
 */