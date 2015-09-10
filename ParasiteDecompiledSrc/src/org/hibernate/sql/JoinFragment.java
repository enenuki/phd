/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import org.hibernate.internal.util.StringHelper;
/*   4:    */ 
/*   5:    */ public abstract class JoinFragment
/*   6:    */ {
/*   7:    */   @Deprecated
/*   8:    */   public static final int INNER_JOIN = 0;
/*   9:    */   @Deprecated
/*  10:    */   public static final int FULL_JOIN = 4;
/*  11:    */   @Deprecated
/*  12:    */   public static final int LEFT_OUTER_JOIN = 1;
/*  13:    */   @Deprecated
/*  14:    */   public static final int RIGHT_OUTER_JOIN = 2;
/*  15: 76 */   private boolean hasFilterCondition = false;
/*  16: 77 */   private boolean hasThetaJoins = false;
/*  17:    */   
/*  18:    */   public abstract void addJoin(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, JoinType paramJoinType);
/*  19:    */   
/*  20:    */   public abstract void addJoin(String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, JoinType paramJoinType, String paramString3);
/*  21:    */   
/*  22:    */   public abstract void addCrossJoin(String paramString1, String paramString2);
/*  23:    */   
/*  24:    */   public abstract void addJoins(String paramString1, String paramString2);
/*  25:    */   
/*  26:    */   public abstract String toFromFragmentString();
/*  27:    */   
/*  28:    */   public abstract String toWhereFragmentString();
/*  29:    */   
/*  30:    */   public abstract void addCondition(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2);
/*  31:    */   
/*  32:    */   public abstract boolean addCondition(String paramString);
/*  33:    */   
/*  34:    */   public abstract JoinFragment copy();
/*  35:    */   
/*  36:    */   public void addFragment(JoinFragment ojf)
/*  37:    */   {
/*  38: 80 */     if (ojf.hasThetaJoins()) {
/*  39: 81 */       this.hasThetaJoins = true;
/*  40:    */     }
/*  41: 83 */     addJoins(ojf.toFromFragmentString(), ojf.toWhereFragmentString());
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected boolean addCondition(StringBuffer buffer, String on)
/*  45:    */   {
/*  46: 95 */     if (StringHelper.isNotEmpty(on))
/*  47:    */     {
/*  48: 96 */       if (!on.startsWith(" and")) {
/*  49: 96 */         buffer.append(" and ");
/*  50:    */       }
/*  51: 97 */       buffer.append(on);
/*  52: 98 */       return true;
/*  53:    */     }
/*  54:101 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean hasFilterCondition()
/*  58:    */   {
/*  59:111 */     return this.hasFilterCondition;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setHasFilterCondition(boolean b)
/*  63:    */   {
/*  64:115 */     this.hasFilterCondition = b;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean hasThetaJoins()
/*  68:    */   {
/*  69:119 */     return this.hasThetaJoins;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setHasThetaJoins(boolean hasThetaJoins)
/*  73:    */   {
/*  74:123 */     this.hasThetaJoins = hasThetaJoins;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.JoinFragment
 * JD-Core Version:    0.7.0.1
 */