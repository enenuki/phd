/*   1:    */ package org.apache.commons.collections.keyvalue;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import org.apache.commons.collections.KeyValue;
/*   7:    */ 
/*   8:    */ public class TiedMapEntry
/*   9:    */   implements Map.Entry, KeyValue, Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -8453869361373831205L;
/*  12:    */   private final Map map;
/*  13:    */   private final Object key;
/*  14:    */   
/*  15:    */   public TiedMapEntry(Map map, Object key)
/*  16:    */   {
/*  17: 53 */     this.map = map;
/*  18: 54 */     this.key = key;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Object getKey()
/*  22:    */   {
/*  23: 65 */     return this.key;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object getValue()
/*  27:    */   {
/*  28: 74 */     return this.map.get(this.key);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object setValue(Object value)
/*  32:    */   {
/*  33: 85 */     if (value == this) {
/*  34: 86 */       throw new IllegalArgumentException("Cannot set value to this map entry");
/*  35:    */     }
/*  36: 88 */     return this.map.put(this.key, value);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean equals(Object obj)
/*  40:    */   {
/*  41:100 */     if (obj == this) {
/*  42:101 */       return true;
/*  43:    */     }
/*  44:103 */     if (!(obj instanceof Map.Entry)) {
/*  45:104 */       return false;
/*  46:    */     }
/*  47:106 */     Map.Entry other = (Map.Entry)obj;
/*  48:107 */     Object value = getValue();
/*  49:108 */     return (this.key == null ? other.getKey() == null : this.key.equals(other.getKey())) && (value == null ? other.getValue() == null : value.equals(other.getValue()));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int hashCode()
/*  53:    */   {
/*  54:121 */     Object value = getValue();
/*  55:122 */     return (getKey() == null ? 0 : getKey().hashCode()) ^ (value == null ? 0 : value.hashCode());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String toString()
/*  59:    */   {
/*  60:132 */     return getKey() + "=" + getValue();
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.TiedMapEntry
 * JD-Core Version:    0.7.0.1
 */