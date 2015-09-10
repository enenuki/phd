/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class ActionTransInfo
/*  4:   */ {
/*  5:15 */   public boolean assignToRoot = false;
/*  6:16 */   public String refRuleRoot = null;
/*  7:17 */   public String followSetName = null;
/*  8:   */   
/*  9:   */   public String toString()
/* 10:   */   {
/* 11:20 */     return "assignToRoot:" + this.assignToRoot + ", refRuleRoot:" + this.refRuleRoot + ", FOLLOW Set:" + this.followSetName;
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ActionTransInfo
 * JD-Core Version:    0.7.0.1
 */