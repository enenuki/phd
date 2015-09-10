/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.CombinatorCondition;
/*  5:   */ import org.w3c.css.sac.Condition;
/*  6:   */ 
/*  7:   */ public class AndConditionImpl
/*  8:   */   implements CombinatorCondition, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -3180583860092672742L;
/* 11:   */   private Condition firstCondition;
/* 12:   */   private Condition secondCondition;
/* 13:   */   
/* 14:   */   public void setFirstCondition(Condition c1)
/* 15:   */   {
/* 16:47 */     this.firstCondition = c1;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setSecondCondition(Condition c2)
/* 20:   */   {
/* 21:52 */     this.secondCondition = c2;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public AndConditionImpl(Condition c1, Condition c2)
/* 25:   */   {
/* 26:57 */     this.firstCondition = c1;
/* 27:58 */     this.secondCondition = c2;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public AndConditionImpl() {}
/* 31:   */   
/* 32:   */   public short getConditionType()
/* 33:   */   {
/* 34:67 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Condition getFirstCondition()
/* 38:   */   {
/* 39:71 */     return this.firstCondition;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Condition getSecondCondition()
/* 43:   */   {
/* 44:75 */     return this.secondCondition;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String toString()
/* 48:   */   {
/* 49:79 */     return getFirstCondition().toString() + getSecondCondition().toString();
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.AndConditionImpl
 * JD-Core Version:    0.7.0.1
 */