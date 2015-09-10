/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import org.apache.commons.collections.comparators.BooleanComparator;
/*   6:    */ import org.apache.commons.collections.comparators.ComparableComparator;
/*   7:    */ import org.apache.commons.collections.comparators.ComparatorChain;
/*   8:    */ import org.apache.commons.collections.comparators.NullComparator;
/*   9:    */ import org.apache.commons.collections.comparators.ReverseComparator;
/*  10:    */ import org.apache.commons.collections.comparators.TransformingComparator;
/*  11:    */ 
/*  12:    */ public class ComparatorUtils
/*  13:    */ {
/*  14: 57 */   public static final Comparator NATURAL_COMPARATOR = ;
/*  15:    */   
/*  16:    */   public static Comparator naturalComparator()
/*  17:    */   {
/*  18: 65 */     return NATURAL_COMPARATOR;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Comparator chainedComparator(Comparator comparator1, Comparator comparator2)
/*  22:    */   {
/*  23: 80 */     return chainedComparator(new Comparator[] { comparator1, comparator2 });
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Comparator chainedComparator(Comparator[] comparators)
/*  27:    */   {
/*  28: 93 */     ComparatorChain chain = new ComparatorChain();
/*  29: 94 */     for (int i = 0; i < comparators.length; i++)
/*  30:    */     {
/*  31: 95 */       if (comparators[i] == null) {
/*  32: 96 */         throw new NullPointerException("Comparator cannot be null");
/*  33:    */       }
/*  34: 98 */       chain.addComparator(comparators[i]);
/*  35:    */     }
/*  36:100 */     return chain;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Comparator chainedComparator(Collection comparators)
/*  40:    */   {
/*  41:115 */     return chainedComparator((Comparator[])comparators.toArray(new Comparator[comparators.size()]));
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static Comparator reversedComparator(Comparator comparator)
/*  45:    */   {
/*  46:128 */     if (comparator == null) {
/*  47:129 */       comparator = NATURAL_COMPARATOR;
/*  48:    */     }
/*  49:131 */     return new ReverseComparator(comparator);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Comparator booleanComparator(boolean trueFirst)
/*  53:    */   {
/*  54:147 */     return BooleanComparator.getBooleanComparator(trueFirst);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static Comparator nullLowComparator(Comparator comparator)
/*  58:    */   {
/*  59:162 */     if (comparator == null) {
/*  60:163 */       comparator = NATURAL_COMPARATOR;
/*  61:    */     }
/*  62:165 */     return new NullComparator(comparator, false);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Comparator nullHighComparator(Comparator comparator)
/*  66:    */   {
/*  67:180 */     if (comparator == null) {
/*  68:181 */       comparator = NATURAL_COMPARATOR;
/*  69:    */     }
/*  70:183 */     return new NullComparator(comparator, true);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static Comparator transformedComparator(Comparator comparator, Transformer transformer)
/*  74:    */   {
/*  75:199 */     if (comparator == null) {
/*  76:200 */       comparator = NATURAL_COMPARATOR;
/*  77:    */     }
/*  78:202 */     return new TransformingComparator(transformer, comparator);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static Object min(Object o1, Object o2, Comparator comparator)
/*  82:    */   {
/*  83:216 */     if (comparator == null) {
/*  84:217 */       comparator = NATURAL_COMPARATOR;
/*  85:    */     }
/*  86:219 */     int c = comparator.compare(o1, o2);
/*  87:220 */     return c < 0 ? o1 : o2;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static Object max(Object o1, Object o2, Comparator comparator)
/*  91:    */   {
/*  92:234 */     if (comparator == null) {
/*  93:235 */       comparator = NATURAL_COMPARATOR;
/*  94:    */     }
/*  95:237 */     int c = comparator.compare(o1, o2);
/*  96:238 */     return c > 0 ? o1 : o2;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ComparatorUtils
 * JD-Core Version:    0.7.0.1
 */