/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.ref.Reference;
/*   8:    */ 
/*   9:    */ public class ReferenceIdentityMap
/*  10:    */   extends AbstractReferenceMap
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -1266190134568365852L;
/*  14:    */   
/*  15:    */   public ReferenceIdentityMap()
/*  16:    */   {
/*  17: 83 */     super(0, 1, 16, 0.75F, false);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ReferenceIdentityMap(int keyType, int valueType)
/*  21:    */   {
/*  22: 96 */     super(keyType, valueType, 16, 0.75F, false);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ReferenceIdentityMap(int keyType, int valueType, boolean purgeValues)
/*  26:    */   {
/*  27:111 */     super(keyType, valueType, 16, 0.75F, purgeValues);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ReferenceIdentityMap(int keyType, int valueType, int capacity, float loadFactor)
/*  31:    */   {
/*  32:126 */     super(keyType, valueType, capacity, loadFactor, false);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ReferenceIdentityMap(int keyType, int valueType, int capacity, float loadFactor, boolean purgeValues)
/*  36:    */   {
/*  37:144 */     super(keyType, valueType, capacity, loadFactor, purgeValues);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected int hash(Object key)
/*  41:    */   {
/*  42:157 */     return System.identityHashCode(key);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected int hashEntry(Object key, Object value)
/*  46:    */   {
/*  47:170 */     return System.identityHashCode(key) ^ System.identityHashCode(value);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected boolean isEqualKey(Object key1, Object key2)
/*  51:    */   {
/*  52:185 */     key2 = this.keyType > 0 ? ((Reference)key2).get() : key2;
/*  53:186 */     return key1 == key2;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected boolean isEqualValue(Object value1, Object value2)
/*  57:    */   {
/*  58:199 */     return value1 == value2;
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void writeObject(ObjectOutputStream out)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:207 */     out.defaultWriteObject();
/*  65:208 */     doWriteObject(out);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void readObject(ObjectInputStream in)
/*  69:    */     throws IOException, ClassNotFoundException
/*  70:    */   {
/*  71:215 */     in.defaultReadObject();
/*  72:216 */     doReadObject(in);
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.ReferenceIdentityMap
 * JD-Core Version:    0.7.0.1
 */