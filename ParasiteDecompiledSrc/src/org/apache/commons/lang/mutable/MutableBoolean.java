/*   1:    */ package org.apache.commons.lang.mutable;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.lang.BooleanUtils;
/*   5:    */ 
/*   6:    */ public class MutableBoolean
/*   7:    */   implements Mutable, Serializable, Comparable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -4830728138360036487L;
/*  10:    */   private boolean value;
/*  11:    */   
/*  12:    */   public MutableBoolean() {}
/*  13:    */   
/*  14:    */   public MutableBoolean(boolean value)
/*  15:    */   {
/*  16: 58 */     this.value = value;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public MutableBoolean(Boolean value)
/*  20:    */   {
/*  21: 69 */     this.value = value.booleanValue();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Object getValue()
/*  25:    */   {
/*  26: 79 */     return BooleanUtils.toBooleanObject(this.value);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setValue(boolean value)
/*  30:    */   {
/*  31: 88 */     this.value = value;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setValue(Object value)
/*  35:    */   {
/*  36: 98 */     setValue(((Boolean)value).booleanValue());
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isTrue()
/*  40:    */   {
/*  41:109 */     return this.value == true;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isFalse()
/*  45:    */   {
/*  46:119 */     return !this.value;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean booleanValue()
/*  50:    */   {
/*  51:129 */     return this.value;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Boolean toBoolean()
/*  55:    */   {
/*  56:140 */     return BooleanUtils.toBooleanObject(this.value);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean equals(Object obj)
/*  60:    */   {
/*  61:153 */     if ((obj instanceof MutableBoolean)) {
/*  62:154 */       return this.value == ((MutableBoolean)obj).booleanValue();
/*  63:    */     }
/*  64:156 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int hashCode()
/*  68:    */   {
/*  69:165 */     return this.value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int compareTo(Object obj)
/*  73:    */   {
/*  74:177 */     MutableBoolean other = (MutableBoolean)obj;
/*  75:178 */     boolean anotherVal = other.value;
/*  76:179 */     return this.value ? 1 : this.value == anotherVal ? 0 : -1;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String toString()
/*  80:    */   {
/*  81:189 */     return String.valueOf(this.value);
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.mutable.MutableBoolean
 * JD-Core Version:    0.7.0.1
 */