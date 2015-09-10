/*   1:    */ package org.apache.commons.collections.bidimap;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.apache.commons.collections.BidiMap;
/*  10:    */ 
/*  11:    */ public class DualHashBidiMap
/*  12:    */   extends AbstractDualBidiMap
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   private static final long serialVersionUID = 721969328361808L;
/*  16:    */   
/*  17:    */   public DualHashBidiMap()
/*  18:    */   {
/*  19: 55 */     super(new HashMap(), new HashMap());
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DualHashBidiMap(Map map)
/*  23:    */   {
/*  24: 65 */     super(new HashMap(), new HashMap());
/*  25: 66 */     putAll(map);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected DualHashBidiMap(Map normalMap, Map reverseMap, BidiMap inverseBidiMap)
/*  29:    */   {
/*  30: 77 */     super(normalMap, reverseMap, inverseBidiMap);
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected BidiMap createBidiMap(Map normalMap, Map reverseMap, BidiMap inverseBidiMap)
/*  34:    */   {
/*  35: 89 */     return new DualHashBidiMap(normalMap, reverseMap, inverseBidiMap);
/*  36:    */   }
/*  37:    */   
/*  38:    */   private void writeObject(ObjectOutputStream out)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 95 */     out.defaultWriteObject();
/*  42: 96 */     out.writeObject(this.maps[0]);
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void readObject(ObjectInputStream in)
/*  46:    */     throws IOException, ClassNotFoundException
/*  47:    */   {
/*  48:100 */     in.defaultReadObject();
/*  49:101 */     this.maps[0] = new HashMap();
/*  50:102 */     this.maps[1] = new HashMap();
/*  51:103 */     Map map = (Map)in.readObject();
/*  52:104 */     putAll(map);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.DualHashBidiMap
 * JD-Core Version:    0.7.0.1
 */