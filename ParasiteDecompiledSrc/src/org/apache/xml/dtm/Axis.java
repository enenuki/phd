/*   1:    */ package org.apache.xml.dtm;
/*   2:    */ 
/*   3:    */ public final class Axis
/*   4:    */ {
/*   5:    */   public static final int ANCESTOR = 0;
/*   6:    */   public static final int ANCESTORORSELF = 1;
/*   7:    */   public static final int ATTRIBUTE = 2;
/*   8:    */   public static final int CHILD = 3;
/*   9:    */   public static final int DESCENDANT = 4;
/*  10:    */   public static final int DESCENDANTORSELF = 5;
/*  11:    */   public static final int FOLLOWING = 6;
/*  12:    */   public static final int FOLLOWINGSIBLING = 7;
/*  13:    */   public static final int NAMESPACEDECLS = 8;
/*  14:    */   public static final int NAMESPACE = 9;
/*  15:    */   public static final int PARENT = 10;
/*  16:    */   public static final int PRECEDING = 11;
/*  17:    */   public static final int PRECEDINGSIBLING = 12;
/*  18:    */   public static final int SELF = 13;
/*  19:    */   public static final int ALLFROMNODE = 14;
/*  20:    */   public static final int PRECEDINGANDANCESTOR = 15;
/*  21:    */   public static final int ALL = 16;
/*  22:    */   public static final int DESCENDANTSFROMROOT = 17;
/*  23:    */   public static final int DESCENDANTSORSELFFROMROOT = 18;
/*  24:    */   public static final int ROOT = 19;
/*  25:    */   public static final int FILTEREDLIST = 20;
/*  26:165 */   private static final boolean[] isReverse = { true, true, false, false, false, false, false, false, false, false, false, true, true, false };
/*  27:183 */   private static final String[] names = { "ancestor", "ancestor-or-self", "attribute", "child", "descendant", "descendant-or-self", "following", "following-sibling", "namespace-decls", "namespace", "parent", "preceding", "preceding-sibling", "self", "all-from-node", "preceding-and-ancestor", "all", "descendants-from-root", "descendants-or-self-from-root", "root", "filtered-list" };
/*  28:    */   
/*  29:    */   public static boolean isReverse(int axis)
/*  30:    */   {
/*  31:209 */     return isReverse[axis];
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static String getNames(int index)
/*  35:    */   {
/*  36:213 */     return names[index];
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static int getNamesLength()
/*  40:    */   {
/*  41:217 */     return names.length;
/*  42:    */   }
/*  43:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.Axis
 * JD-Core Version:    0.7.0.1
 */