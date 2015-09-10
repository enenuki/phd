/*   1:    */ package org.apache.commons.lang.mutable;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class MutableObject
/*   6:    */   implements Mutable, Serializable
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 86241875189L;
/*   9:    */   private Object value;
/*  10:    */   
/*  11:    */   public MutableObject() {}
/*  12:    */   
/*  13:    */   public MutableObject(Object value)
/*  14:    */   {
/*  15: 55 */     this.value = value;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Object getValue()
/*  19:    */   {
/*  20: 65 */     return this.value;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setValue(Object value)
/*  24:    */   {
/*  25: 74 */     this.value = value;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean equals(Object obj)
/*  29:    */   {
/*  30: 87 */     if ((obj instanceof MutableObject))
/*  31:    */     {
/*  32: 88 */       Object other = ((MutableObject)obj).value;
/*  33: 89 */       return (this.value == other) || ((this.value != null) && (this.value.equals(other)));
/*  34:    */     }
/*  35: 91 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int hashCode()
/*  39:    */   {
/*  40:100 */     return this.value == null ? 0 : this.value.hashCode();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String toString()
/*  44:    */   {
/*  45:110 */     return this.value == null ? "null" : this.value.toString();
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.mutable.MutableObject
 * JD-Core Version:    0.7.0.1
 */