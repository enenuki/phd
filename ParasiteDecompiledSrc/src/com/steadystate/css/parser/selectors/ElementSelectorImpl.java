/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.ElementSelector;
/*  5:   */ 
/*  6:   */ public class ElementSelectorImpl
/*  7:   */   implements ElementSelector, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 7507121069969409061L;
/* 10:   */   private String localName;
/* 11:   */   
/* 12:   */   public void setLocalName(String localName)
/* 13:   */   {
/* 14:46 */     this.localName = localName;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ElementSelectorImpl(String localName)
/* 18:   */   {
/* 19:50 */     this.localName = localName;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ElementSelectorImpl() {}
/* 23:   */   
/* 24:   */   public short getSelectorType()
/* 25:   */   {
/* 26:58 */     return 4;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getNamespaceURI()
/* 30:   */   {
/* 31:62 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getLocalName()
/* 35:   */   {
/* 36:66 */     return this.localName;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String toString()
/* 40:   */   {
/* 41:70 */     return getLocalName() != null ? getLocalName() : "*";
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.ElementSelectorImpl
 * JD-Core Version:    0.7.0.1
 */