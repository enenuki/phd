/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import antlr.CommonToken;
/*  4:   */ 
/*  5:   */ public class HqlToken
/*  6:   */   extends CommonToken
/*  7:   */ {
/*  8:37 */   private boolean possibleID = false;
/*  9:   */   private int tokenType;
/* 10:   */   
/* 11:   */   public boolean isPossibleID()
/* 12:   */   {
/* 13:50 */     return this.possibleID;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void setType(int t)
/* 17:   */   {
/* 18:59 */     this.tokenType = getType();
/* 19:60 */     super.setType(t);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private int getPreviousType()
/* 23:   */   {
/* 24:69 */     return this.tokenType;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void setPossibleID(boolean possibleID)
/* 28:   */   {
/* 29:79 */     this.possibleID = possibleID;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toString()
/* 33:   */   {
/* 34:88 */     return "[\"" + getText() + "\",<" + getType() + "> previously: <" + getPreviousType() + ">,line=" + this.line + ",col=" + this.col + ",possibleID=" + this.possibleID + "]";
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.HqlToken
 * JD-Core Version:    0.7.0.1
 */