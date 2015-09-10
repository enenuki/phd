/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import org.apache.commons.collections.bag.HashBag;
/*   4:    */ import org.apache.commons.collections.bag.PredicatedBag;
/*   5:    */ import org.apache.commons.collections.bag.PredicatedSortedBag;
/*   6:    */ import org.apache.commons.collections.bag.SynchronizedBag;
/*   7:    */ import org.apache.commons.collections.bag.SynchronizedSortedBag;
/*   8:    */ import org.apache.commons.collections.bag.TransformedBag;
/*   9:    */ import org.apache.commons.collections.bag.TransformedSortedBag;
/*  10:    */ import org.apache.commons.collections.bag.TreeBag;
/*  11:    */ import org.apache.commons.collections.bag.TypedBag;
/*  12:    */ import org.apache.commons.collections.bag.TypedSortedBag;
/*  13:    */ import org.apache.commons.collections.bag.UnmodifiableBag;
/*  14:    */ import org.apache.commons.collections.bag.UnmodifiableSortedBag;
/*  15:    */ 
/*  16:    */ public class BagUtils
/*  17:    */ {
/*  18: 49 */   public static final Bag EMPTY_BAG = UnmodifiableBag.decorate(new HashBag());
/*  19: 54 */   public static final Bag EMPTY_SORTED_BAG = UnmodifiableSortedBag.decorate(new TreeBag());
/*  20:    */   
/*  21:    */   public static Bag synchronizedBag(Bag bag)
/*  22:    */   {
/*  23: 91 */     return SynchronizedBag.decorate(bag);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Bag unmodifiableBag(Bag bag)
/*  27:    */   {
/*  28:104 */     return UnmodifiableBag.decorate(bag);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Bag predicatedBag(Bag bag, Predicate predicate)
/*  32:    */   {
/*  33:121 */     return PredicatedBag.decorate(bag, predicate);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Bag typedBag(Bag bag, Class type)
/*  37:    */   {
/*  38:134 */     return TypedBag.decorate(bag, type);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Bag transformedBag(Bag bag, Transformer transformer)
/*  42:    */   {
/*  43:150 */     return TransformedBag.decorate(bag, transformer);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static SortedBag synchronizedSortedBag(SortedBag bag)
/*  47:    */   {
/*  48:182 */     return SynchronizedSortedBag.decorate(bag);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static SortedBag unmodifiableSortedBag(SortedBag bag)
/*  52:    */   {
/*  53:195 */     return UnmodifiableSortedBag.decorate(bag);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static SortedBag predicatedSortedBag(SortedBag bag, Predicate predicate)
/*  57:    */   {
/*  58:212 */     return PredicatedSortedBag.decorate(bag, predicate);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static SortedBag typedSortedBag(SortedBag bag, Class type)
/*  62:    */   {
/*  63:225 */     return TypedSortedBag.decorate(bag, type);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static SortedBag transformedSortedBag(SortedBag bag, Transformer transformer)
/*  67:    */   {
/*  68:241 */     return TransformedSortedBag.decorate(bag, transformer);
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BagUtils
 * JD-Core Version:    0.7.0.1
 */