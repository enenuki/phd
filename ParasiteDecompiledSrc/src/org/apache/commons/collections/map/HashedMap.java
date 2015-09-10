/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class HashedMap
/*  10:    */   extends AbstractHashedMap
/*  11:    */   implements Serializable, Cloneable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -1788199231038721040L;
/*  14:    */   
/*  15:    */   public HashedMap()
/*  16:    */   {
/*  17: 54 */     super(16, 0.75F, 12);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public HashedMap(int initialCapacity)
/*  21:    */   {
/*  22: 64 */     super(initialCapacity);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public HashedMap(int initialCapacity, float loadFactor)
/*  26:    */   {
/*  27: 77 */     super(initialCapacity, loadFactor);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public HashedMap(Map map)
/*  31:    */   {
/*  32: 87 */     super(map);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object clone()
/*  36:    */   {
/*  37: 97 */     return super.clone();
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void writeObject(ObjectOutputStream out)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:104 */     out.defaultWriteObject();
/*  44:105 */     doWriteObject(out);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void readObject(ObjectInputStream in)
/*  48:    */     throws IOException, ClassNotFoundException
/*  49:    */   {
/*  50:112 */     in.defaultReadObject();
/*  51:113 */     doReadObject(in);
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.HashedMap
 * JD-Core Version:    0.7.0.1
 */