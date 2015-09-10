/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ 
/*   8:    */ public class ReferenceMap
/*   9:    */   extends AbstractReferenceMap
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 1555089888138299607L;
/*  13:    */   
/*  14:    */   public ReferenceMap()
/*  15:    */   {
/*  16: 86 */     super(0, 1, 16, 0.75F, false);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ReferenceMap(int keyType, int valueType)
/*  20:    */   {
/*  21: 99 */     super(keyType, valueType, 16, 0.75F, false);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ReferenceMap(int keyType, int valueType, boolean purgeValues)
/*  25:    */   {
/*  26:114 */     super(keyType, valueType, 16, 0.75F, purgeValues);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ReferenceMap(int keyType, int valueType, int capacity, float loadFactor)
/*  30:    */   {
/*  31:130 */     super(keyType, valueType, capacity, loadFactor, false);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ReferenceMap(int keyType, int valueType, int capacity, float loadFactor, boolean purgeValues)
/*  35:    */   {
/*  36:149 */     super(keyType, valueType, capacity, loadFactor, purgeValues);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void writeObject(ObjectOutputStream out)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42:157 */     out.defaultWriteObject();
/*  43:158 */     doWriteObject(out);
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void readObject(ObjectInputStream in)
/*  47:    */     throws IOException, ClassNotFoundException
/*  48:    */   {
/*  49:165 */     in.defaultReadObject();
/*  50:166 */     doReadObject(in);
/*  51:    */   }
/*  52:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.ReferenceMap
 * JD-Core Version:    0.7.0.1
 */