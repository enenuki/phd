/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ public class ImpliedFromElement
/*  4:   */   extends FromElement
/*  5:   */ {
/*  6:38 */   private boolean impliedInFromClause = false;
/*  7:43 */   private boolean inProjectionList = false;
/*  8:   */   
/*  9:   */   public boolean isImplied()
/* 10:   */   {
/* 11:46 */     return true;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void setImpliedInFromClause(boolean flag)
/* 15:   */   {
/* 16:50 */     this.impliedInFromClause = flag;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean isImpliedInFromClause()
/* 20:   */   {
/* 21:54 */     return this.impliedInFromClause;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setInProjectionList(boolean inProjectionList)
/* 25:   */   {
/* 26:58 */     this.inProjectionList = inProjectionList;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean inProjectionList()
/* 30:   */   {
/* 31:62 */     return (this.inProjectionList) && (isFromOrJoinFragment());
/* 32:   */   }
/* 33:   */   
/* 34:   */   public boolean isIncludeSubclasses()
/* 35:   */   {
/* 36:66 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getDisplayText()
/* 40:   */   {
/* 41:75 */     StringBuilder buf = new StringBuilder();
/* 42:76 */     buf.append("ImpliedFromElement{");
/* 43:77 */     appendDisplayText(buf);
/* 44:78 */     buf.append("}");
/* 45:79 */     return buf.toString();
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.ImpliedFromElement
 * JD-Core Version:    0.7.0.1
 */