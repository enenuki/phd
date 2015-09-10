/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.Selector;
/*  5:   */ import org.w3c.css.sac.SiblingSelector;
/*  6:   */ import org.w3c.css.sac.SimpleSelector;
/*  7:   */ 
/*  8:   */ public class DirectAdjacentSelectorImpl
/*  9:   */   implements SiblingSelector, Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -7328602345833826516L;
/* 12:   */   private short nodeType;
/* 13:   */   private Selector selector;
/* 14:   */   private SimpleSelector siblingSelector;
/* 15:   */   
/* 16:   */   public void setNodeType(short nodeType)
/* 17:   */   {
/* 18:48 */     this.nodeType = nodeType;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setSelector(Selector child)
/* 22:   */   {
/* 23:53 */     this.selector = child;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setSiblingSelector(SimpleSelector directAdjacent)
/* 27:   */   {
/* 28:58 */     this.siblingSelector = directAdjacent;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public DirectAdjacentSelectorImpl(short nodeType, Selector child, SimpleSelector directAdjacent)
/* 32:   */   {
/* 33:63 */     this.nodeType = nodeType;
/* 34:64 */     this.selector = child;
/* 35:65 */     this.siblingSelector = directAdjacent;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public DirectAdjacentSelectorImpl() {}
/* 39:   */   
/* 40:   */   public short getNodeType()
/* 41:   */   {
/* 42:74 */     return this.nodeType;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public short getSelectorType()
/* 46:   */   {
/* 47:78 */     return 12;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Selector getSelector()
/* 51:   */   {
/* 52:82 */     return this.selector;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public SimpleSelector getSiblingSelector()
/* 56:   */   {
/* 57:86 */     return this.siblingSelector;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String toString()
/* 61:   */   {
/* 62:90 */     return this.selector.toString() + " + " + this.siblingSelector.toString();
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.DirectAdjacentSelectorImpl
 * JD-Core Version:    0.7.0.1
 */