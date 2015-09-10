/*   1:    */ package org.apache.commons.collections.set;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.SortedSet;
/*   5:    */ import org.apache.commons.collections.Transformer;
/*   6:    */ 
/*   7:    */ public class TransformedSortedSet
/*   8:    */   extends TransformedSet
/*   9:    */   implements SortedSet
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = -1675486811351124386L;
/*  12:    */   
/*  13:    */   public static SortedSet decorate(SortedSet set, Transformer transformer)
/*  14:    */   {
/*  15: 55 */     return new TransformedSortedSet(set, transformer);
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected TransformedSortedSet(SortedSet set, Transformer transformer)
/*  19:    */   {
/*  20: 70 */     super(set, transformer);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected SortedSet getSortedSet()
/*  24:    */   {
/*  25: 79 */     return (SortedSet)this.collection;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object first()
/*  29:    */   {
/*  30: 84 */     return getSortedSet().first();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object last()
/*  34:    */   {
/*  35: 88 */     return getSortedSet().last();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Comparator comparator()
/*  39:    */   {
/*  40: 92 */     return getSortedSet().comparator();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SortedSet subSet(Object fromElement, Object toElement)
/*  44:    */   {
/*  45: 97 */     SortedSet set = getSortedSet().subSet(fromElement, toElement);
/*  46: 98 */     return new TransformedSortedSet(set, this.transformer);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public SortedSet headSet(Object toElement)
/*  50:    */   {
/*  51:102 */     SortedSet set = getSortedSet().headSet(toElement);
/*  52:103 */     return new TransformedSortedSet(set, this.transformer);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public SortedSet tailSet(Object fromElement)
/*  56:    */   {
/*  57:107 */     SortedSet set = getSortedSet().tailSet(fromElement);
/*  58:108 */     return new TransformedSortedSet(set, this.transformer);
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.TransformedSortedSet
 * JD-Core Version:    0.7.0.1
 */