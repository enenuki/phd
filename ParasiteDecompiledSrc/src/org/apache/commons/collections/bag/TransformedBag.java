/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.util.Set;
/*   4:    */ import org.apache.commons.collections.Bag;
/*   5:    */ import org.apache.commons.collections.Transformer;
/*   6:    */ import org.apache.commons.collections.collection.TransformedCollection;
/*   7:    */ import org.apache.commons.collections.set.TransformedSet;
/*   8:    */ 
/*   9:    */ public class TransformedBag
/*  10:    */   extends TransformedCollection
/*  11:    */   implements Bag
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = 5421170911299074185L;
/*  14:    */   
/*  15:    */   public static Bag decorate(Bag bag, Transformer transformer)
/*  16:    */   {
/*  17: 59 */     return new TransformedBag(bag, transformer);
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected TransformedBag(Bag bag, Transformer transformer)
/*  21:    */   {
/*  22: 74 */     super(bag, transformer);
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected Bag getBag()
/*  26:    */   {
/*  27: 83 */     return (Bag)this.collection;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int getCount(Object object)
/*  31:    */   {
/*  32: 88 */     return getBag().getCount(object);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean remove(Object object, int nCopies)
/*  36:    */   {
/*  37: 92 */     return getBag().remove(object, nCopies);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean add(Object object, int nCopies)
/*  41:    */   {
/*  42: 97 */     object = transform(object);
/*  43: 98 */     return getBag().add(object, nCopies);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Set uniqueSet()
/*  47:    */   {
/*  48:102 */     Set set = getBag().uniqueSet();
/*  49:103 */     return TransformedSet.decorate(set, this.transformer);
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.TransformedBag
 * JD-Core Version:    0.7.0.1
 */