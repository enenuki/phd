/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.Condition;
/*  5:   */ import org.w3c.css.sac.ConditionalSelector;
/*  6:   */ import org.w3c.css.sac.SimpleSelector;
/*  7:   */ 
/*  8:   */ public class ConditionalSelectorImpl
/*  9:   */   implements ConditionalSelector, Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 7217145899707580586L;
/* 12:   */   private SimpleSelector simpleSelector;
/* 13:   */   private Condition condition;
/* 14:   */   
/* 15:   */   public void setSimpleSelector(SimpleSelector simpleSelector)
/* 16:   */   {
/* 17:47 */     this.simpleSelector = simpleSelector;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setCondition(Condition condition)
/* 21:   */   {
/* 22:52 */     this.condition = condition;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ConditionalSelectorImpl(SimpleSelector simpleSelector, Condition condition)
/* 26:   */   {
/* 27:59 */     this.simpleSelector = simpleSelector;
/* 28:60 */     this.condition = condition;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public ConditionalSelectorImpl() {}
/* 32:   */   
/* 33:   */   public short getSelectorType()
/* 34:   */   {
/* 35:69 */     return 0;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public SimpleSelector getSimpleSelector()
/* 39:   */   {
/* 40:73 */     return this.simpleSelector;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Condition getCondition()
/* 44:   */   {
/* 45:77 */     return this.condition;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toString()
/* 49:   */   {
/* 50:81 */     return this.simpleSelector.toString() + this.condition.toString();
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.ConditionalSelectorImpl
 * JD-Core Version:    0.7.0.1
 */