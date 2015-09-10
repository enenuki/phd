/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public final class UniqueTag
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = -4320556826714577259L;
/*   9:    */   private static final int ID_NOT_FOUND = 1;
/*  10:    */   private static final int ID_NULL_VALUE = 2;
/*  11:    */   private static final int ID_DOUBLE_MARK = 3;
/*  12: 64 */   public static final UniqueTag NOT_FOUND = new UniqueTag(1);
/*  13: 70 */   public static final UniqueTag NULL_VALUE = new UniqueTag(2);
/*  14: 77 */   public static final UniqueTag DOUBLE_MARK = new UniqueTag(3);
/*  15:    */   private final int tagId;
/*  16:    */   
/*  17:    */   private UniqueTag(int tagId)
/*  18:    */   {
/*  19: 83 */     this.tagId = tagId;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Object readResolve()
/*  23:    */   {
/*  24: 88 */     switch (this.tagId)
/*  25:    */     {
/*  26:    */     case 1: 
/*  27: 90 */       return NOT_FOUND;
/*  28:    */     case 2: 
/*  29: 92 */       return NULL_VALUE;
/*  30:    */     case 3: 
/*  31: 94 */       return DOUBLE_MARK;
/*  32:    */     }
/*  33: 96 */     throw new IllegalStateException(String.valueOf(this.tagId));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String toString()
/*  37:    */   {
/*  38:    */     String name;
/*  39:104 */     switch (this.tagId)
/*  40:    */     {
/*  41:    */     case 1: 
/*  42:106 */       name = "NOT_FOUND";
/*  43:107 */       break;
/*  44:    */     case 2: 
/*  45:109 */       name = "NULL_VALUE";
/*  46:110 */       break;
/*  47:    */     case 3: 
/*  48:112 */       name = "DOUBLE_MARK";
/*  49:113 */       break;
/*  50:    */     default: 
/*  51:115 */       throw Kit.codeBug();
/*  52:    */     }
/*  53:117 */     return super.toString() + ": " + name;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.UniqueTag
 * JD-Core Version:    0.7.0.1
 */