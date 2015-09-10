/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class CaseInsensitiveMap
/*  10:    */   extends AbstractHashedMap
/*  11:    */   implements Serializable, Cloneable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -7074655917369299456L;
/*  14:    */   
/*  15:    */   public CaseInsensitiveMap()
/*  16:    */   {
/*  17: 69 */     super(16, 0.75F, 12);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public CaseInsensitiveMap(int initialCapacity)
/*  21:    */   {
/*  22: 79 */     super(initialCapacity);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public CaseInsensitiveMap(int initialCapacity, float loadFactor)
/*  26:    */   {
/*  27: 92 */     super(initialCapacity, loadFactor);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CaseInsensitiveMap(Map map)
/*  31:    */   {
/*  32:106 */     super(map);
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected Object convertKey(Object key)
/*  36:    */   {
/*  37:120 */     if (key != null) {
/*  38:121 */       return key.toString().toLowerCase();
/*  39:    */     }
/*  40:123 */     return AbstractHashedMap.NULL;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object clone()
/*  44:    */   {
/*  45:134 */     return super.clone();
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void writeObject(ObjectOutputStream out)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:141 */     out.defaultWriteObject();
/*  52:142 */     doWriteObject(out);
/*  53:    */   }
/*  54:    */   
/*  55:    */   private void readObject(ObjectInputStream in)
/*  56:    */     throws IOException, ClassNotFoundException
/*  57:    */   {
/*  58:149 */     in.defaultReadObject();
/*  59:150 */     doReadObject(in);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.CaseInsensitiveMap
 * JD-Core Version:    0.7.0.1
 */