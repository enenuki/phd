/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.DescendantSelector;
/*  5:   */ import org.w3c.css.sac.Selector;
/*  6:   */ import org.w3c.css.sac.SimpleSelector;
/*  7:   */ 
/*  8:   */ public class DescendantSelectorImpl
/*  9:   */   implements DescendantSelector, Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -3620467847449531232L;
/* 12:   */   private Selector ancestorSelector;
/* 13:   */   private SimpleSelector simpleSelector;
/* 14:   */   
/* 15:   */   public void setAncestorSelector(Selector ancestorSelector)
/* 16:   */   {
/* 17:47 */     this.ancestorSelector = ancestorSelector;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setSimpleSelector(SimpleSelector simpleSelector)
/* 21:   */   {
/* 22:52 */     this.simpleSelector = simpleSelector;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public DescendantSelectorImpl(Selector parent, SimpleSelector simpleSelector)
/* 26:   */   {
/* 27:57 */     this.ancestorSelector = parent;
/* 28:58 */     this.simpleSelector = simpleSelector;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public DescendantSelectorImpl() {}
/* 32:   */   
/* 33:   */   public short getSelectorType()
/* 34:   */   {
/* 35:67 */     return 10;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Selector getAncestorSelector()
/* 39:   */   {
/* 40:71 */     return this.ancestorSelector;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public SimpleSelector getSimpleSelector()
/* 44:   */   {
/* 45:75 */     return this.simpleSelector;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toString()
/* 49:   */   {
/* 50:79 */     return getAncestorSelector().toString() + " " + getSimpleSelector().toString();
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.DescendantSelectorImpl
 * JD-Core Version:    0.7.0.1
 */