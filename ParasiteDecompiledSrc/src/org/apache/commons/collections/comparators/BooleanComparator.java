/*   1:    */ package org.apache.commons.collections.comparators;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ 
/*   6:    */ public final class BooleanComparator
/*   7:    */   implements Comparator, Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1830042991606340609L;
/*  10: 41 */   private static final BooleanComparator TRUE_FIRST = new BooleanComparator(true);
/*  11: 44 */   private static final BooleanComparator FALSE_FIRST = new BooleanComparator(false);
/*  12: 47 */   private boolean trueFirst = false;
/*  13:    */   
/*  14:    */   public static BooleanComparator getTrueFirstComparator()
/*  15:    */   {
/*  16: 63 */     return TRUE_FIRST;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static BooleanComparator getFalseFirstComparator()
/*  20:    */   {
/*  21: 79 */     return FALSE_FIRST;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static BooleanComparator getBooleanComparator(boolean trueFirst)
/*  25:    */   {
/*  26: 98 */     return trueFirst ? TRUE_FIRST : FALSE_FIRST;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public BooleanComparator()
/*  30:    */   {
/*  31:111 */     this(false);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public BooleanComparator(boolean trueFirst)
/*  35:    */   {
/*  36:125 */     this.trueFirst = trueFirst;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int compare(Object obj1, Object obj2)
/*  40:    */   {
/*  41:142 */     return compare((Boolean)obj1, (Boolean)obj2);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int compare(Boolean b1, Boolean b2)
/*  45:    */   {
/*  46:155 */     boolean v1 = b1.booleanValue();
/*  47:156 */     boolean v2 = b2.booleanValue();
/*  48:    */     
/*  49:158 */     return (v1 ^ v2) ? -1 : (v1 ^ this.trueFirst) ? 1 : 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int hashCode()
/*  53:    */   {
/*  54:169 */     int hash = "BooleanComparator".hashCode();
/*  55:170 */     return this.trueFirst ? -1 * hash : hash;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean equals(Object object)
/*  59:    */   {
/*  60:186 */     return (this == object) || (((object instanceof BooleanComparator)) && (this.trueFirst == ((BooleanComparator)object).trueFirst));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean sortsTrueFirst()
/*  64:    */   {
/*  65:203 */     return this.trueFirst;
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.BooleanComparator
 * JD-Core Version:    0.7.0.1
 */