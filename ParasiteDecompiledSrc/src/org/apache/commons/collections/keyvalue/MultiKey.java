/*   1:    */ package org.apache.commons.collections.keyvalue;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Arrays;
/*   5:    */ 
/*   6:    */ public class MultiKey
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 4465448607415788805L;
/*  10:    */   private final Object[] keys;
/*  11:    */   private final int hashCode;
/*  12:    */   
/*  13:    */   public MultiKey(Object key1, Object key2)
/*  14:    */   {
/*  15: 69 */     this(new Object[] { key1, key2 }, false);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public MultiKey(Object key1, Object key2, Object key3)
/*  19:    */   {
/*  20: 83 */     this(new Object[] { key1, key2, key3 }, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public MultiKey(Object key1, Object key2, Object key3, Object key4)
/*  24:    */   {
/*  25: 98 */     this(new Object[] { key1, key2, key3, key4 }, false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public MultiKey(Object key1, Object key2, Object key3, Object key4, Object key5)
/*  29:    */   {
/*  30:114 */     this(new Object[] { key1, key2, key3, key4, key5 }, false);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public MultiKey(Object[] keys)
/*  34:    */   {
/*  35:129 */     this(keys, true);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public MultiKey(Object[] keys, boolean makeClone)
/*  39:    */   {
/*  40:158 */     if (keys == null) {
/*  41:159 */       throw new IllegalArgumentException("The array of keys must not be null");
/*  42:    */     }
/*  43:161 */     if (makeClone) {
/*  44:162 */       this.keys = ((Object[])keys.clone());
/*  45:    */     } else {
/*  46:164 */       this.keys = keys;
/*  47:    */     }
/*  48:167 */     int total = 0;
/*  49:168 */     for (int i = 0; i < keys.length; i++) {
/*  50:169 */       if (keys[i] != null) {
/*  51:170 */         total ^= keys[i].hashCode();
/*  52:    */       }
/*  53:    */     }
/*  54:173 */     this.hashCode = total;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object[] getKeys()
/*  58:    */   {
/*  59:186 */     return (Object[])this.keys.clone();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Object getKey(int index)
/*  63:    */   {
/*  64:201 */     return this.keys[index];
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int size()
/*  68:    */   {
/*  69:211 */     return this.keys.length;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean equals(Object other)
/*  73:    */   {
/*  74:225 */     if (other == this) {
/*  75:226 */       return true;
/*  76:    */     }
/*  77:228 */     if ((other instanceof MultiKey))
/*  78:    */     {
/*  79:229 */       MultiKey otherMulti = (MultiKey)other;
/*  80:230 */       return Arrays.equals(this.keys, otherMulti.keys);
/*  81:    */     }
/*  82:232 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int hashCode()
/*  86:    */   {
/*  87:246 */     return this.hashCode;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String toString()
/*  91:    */   {
/*  92:255 */     return "MultiKey" + Arrays.asList(this.keys).toString();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.keyvalue.MultiKey
 * JD-Core Version:    0.7.0.1
 */