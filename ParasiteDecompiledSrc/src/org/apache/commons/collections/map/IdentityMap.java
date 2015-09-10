/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ 
/*  10:    */ public class IdentityMap
/*  11:    */   extends AbstractHashedMap
/*  12:    */   implements Serializable, Cloneable
/*  13:    */ {
/*  14:    */   private static final long serialVersionUID = 2028493495224302329L;
/*  15:    */   
/*  16:    */   public IdentityMap()
/*  17:    */   {
/*  18: 54 */     super(16, 0.75F, 12);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public IdentityMap(int initialCapacity)
/*  22:    */   {
/*  23: 64 */     super(initialCapacity);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public IdentityMap(int initialCapacity, float loadFactor)
/*  27:    */   {
/*  28: 77 */     super(initialCapacity, loadFactor);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public IdentityMap(Map map)
/*  32:    */   {
/*  33: 87 */     super(map);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int hash(Object key)
/*  37:    */   {
/*  38: 99 */     return System.identityHashCode(key);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected boolean isEqualKey(Object key1, Object key2)
/*  42:    */   {
/*  43:111 */     return key1 == key2;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected boolean isEqualValue(Object value1, Object value2)
/*  47:    */   {
/*  48:123 */     return value1 == value2;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected AbstractHashedMap.HashEntry createEntry(AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value)
/*  52:    */   {
/*  53:137 */     return new IdentityEntry(next, hashCode, key, value);
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected static class IdentityEntry
/*  57:    */     extends AbstractHashedMap.HashEntry
/*  58:    */   {
/*  59:    */     protected IdentityEntry(AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value)
/*  60:    */     {
/*  61:147 */       super(hashCode, key, value);
/*  62:    */     }
/*  63:    */     
/*  64:    */     public boolean equals(Object obj)
/*  65:    */     {
/*  66:151 */       if (obj == this) {
/*  67:152 */         return true;
/*  68:    */       }
/*  69:154 */       if (!(obj instanceof Map.Entry)) {
/*  70:155 */         return false;
/*  71:    */       }
/*  72:157 */       Map.Entry other = (Map.Entry)obj;
/*  73:158 */       return (getKey() == other.getKey()) && (getValue() == other.getValue());
/*  74:    */     }
/*  75:    */     
/*  76:    */     public int hashCode()
/*  77:    */     {
/*  78:164 */       return System.identityHashCode(getKey()) ^ System.identityHashCode(getValue());
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Object clone()
/*  83:    */   {
/*  84:176 */     return super.clone();
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void writeObject(ObjectOutputStream out)
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:183 */     out.defaultWriteObject();
/*  91:184 */     doWriteObject(out);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void readObject(ObjectInputStream in)
/*  95:    */     throws IOException, ClassNotFoundException
/*  96:    */   {
/*  97:191 */     in.defaultReadObject();
/*  98:192 */     doReadObject(in);
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.IdentityMap
 * JD-Core Version:    0.7.0.1
 */