/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Map.Entry;
/*   4:    */ 
/*   5:    */ /**
/*   6:    */  * @deprecated
/*   7:    */  */
/*   8:    */ public class DefaultMapEntry
/*   9:    */   implements Map.Entry, KeyValue
/*  10:    */ {
/*  11:    */   private Object key;
/*  12:    */   private Object value;
/*  13:    */   
/*  14:    */   public DefaultMapEntry() {}
/*  15:    */   
/*  16:    */   public DefaultMapEntry(Map.Entry entry)
/*  17:    */   {
/*  18: 57 */     this.key = entry.getKey();
/*  19: 58 */     this.value = entry.getValue();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DefaultMapEntry(Object key, Object value)
/*  23:    */   {
/*  24: 70 */     this.key = key;
/*  25: 71 */     this.value = value;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object getKey()
/*  29:    */   {
/*  30: 82 */     return this.key;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setKey(Object key)
/*  34:    */   {
/*  35: 93 */     this.key = key;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object getValue()
/*  39:    */   {
/*  40:102 */     return this.value;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object setValue(Object value)
/*  44:    */   {
/*  45:114 */     Object answer = this.value;
/*  46:115 */     this.value = value;
/*  47:116 */     return answer;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean equals(Object obj)
/*  51:    */   {
/*  52:130 */     if (obj == this) {
/*  53:131 */       return true;
/*  54:    */     }
/*  55:133 */     if (!(obj instanceof Map.Entry)) {
/*  56:134 */       return false;
/*  57:    */     }
/*  58:136 */     Map.Entry other = (Map.Entry)obj;
/*  59:137 */     return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) && (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int hashCode()
/*  63:    */   {
/*  64:150 */     return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toString()
/*  68:    */   {
/*  69:160 */     return "" + getKey() + "=" + getValue();
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.DefaultMapEntry
 * JD-Core Version:    0.7.0.1
 */