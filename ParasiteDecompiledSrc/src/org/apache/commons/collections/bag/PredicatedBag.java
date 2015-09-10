/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.util.Set;
/*   4:    */ import org.apache.commons.collections.Bag;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ import org.apache.commons.collections.collection.PredicatedCollection;
/*   7:    */ 
/*   8:    */ public class PredicatedBag
/*   9:    */   extends PredicatedCollection
/*  10:    */   implements Bag
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -2575833140344736876L;
/*  13:    */   
/*  14:    */   public static Bag decorate(Bag bag, Predicate predicate)
/*  15:    */   {
/*  16: 63 */     return new PredicatedBag(bag, predicate);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected PredicatedBag(Bag bag, Predicate predicate)
/*  20:    */   {
/*  21: 79 */     super(bag, predicate);
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected Bag getBag()
/*  25:    */   {
/*  26: 88 */     return (Bag)getCollection();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean add(Object object, int count)
/*  30:    */   {
/*  31: 93 */     validate(object);
/*  32: 94 */     return getBag().add(object, count);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean remove(Object object, int count)
/*  36:    */   {
/*  37: 98 */     return getBag().remove(object, count);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Set uniqueSet()
/*  41:    */   {
/*  42:102 */     return getBag().uniqueSet();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getCount(Object object)
/*  46:    */   {
/*  47:106 */     return getBag().getCount(object);
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.PredicatedBag
 * JD-Core Version:    0.7.0.1
 */