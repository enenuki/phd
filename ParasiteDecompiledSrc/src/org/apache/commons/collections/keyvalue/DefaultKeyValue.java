/*   1:    */ package org.apache.commons.collections.keyvalue;
/*   2:    */ 
/*   3:    */ import java.util.Map.Entry;
/*   4:    */ import org.apache.commons.collections.KeyValue;
/*   5:    */ 
/*   6:    */ public class DefaultKeyValue
/*   7:    */   extends AbstractKeyValue
/*   8:    */ {
/*   9:    */   public DefaultKeyValue()
/*  10:    */   {
/*  11: 44 */     super(null, null);
/*  12:    */   }
/*  13:    */   
/*  14:    */   public DefaultKeyValue(Object key, Object value)
/*  15:    */   {
/*  16: 54 */     super(key, value);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public DefaultKeyValue(KeyValue pair)
/*  20:    */   {
/*  21: 64 */     super(pair.getKey(), pair.getValue());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public DefaultKeyValue(Map.Entry entry)
/*  25:    */   {
/*  26: 74 */     super(entry.getKey(), entry.getValue());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Object setKey(Object key)
/*  30:    */   {
/*  31: 86 */     if (key == this) {
/*  32: 87 */       throw new IllegalArgumentException("DefaultKeyValue may not contain itself as a key.");
/*  33:    */     }
/*  34: 90 */     Object old = this.key;
/*  35: 91 */     this.key = key;
/*  36: 92 */     return old;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object setValue(Object value)
/*  40:    */   {
/*  41:103 */     if (value == this) {
/*  42:104 */       throw new IllegalArgumentException("DefaultKeyValue may not contain itself as a value.");
/*  43:    */     }
/*  44:107 */     Object old = this.value;
/*  45:108 */     this.value = value;
/*  46:109 */     return old;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Map.Entry toMapEntry()
/*  50:    */   {
/*  51:119 */     return new DefaultMapEntry(this);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean equals(Object obj)
/*  55:    */   {
/*  56:133 */     if (obj == this) {
/*  57:134 */       return true;
/*  58:    */     }
/*  59:136 */     if (!(obj instanceof DefaultKeyValue)) {
/*  60:137 */       return false;
/*  61:    */     }
/*  62:140 */     DefaultKeyValue other = (DefaultKeyValue)obj;
/*  63:141 */     return (getKey() == null ? other.getKey() == null : getKey().equals(other.getKey())) && (getValue() == null ? other.getValue() == null : getValue().equals(other.getValue()));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int hashCode()
/*  67:    */   {
/*  68:155 */     return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.DefaultKeyValue
 * JD-Core Version:    0.7.0.1
 */