/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.ElementSelector;
/*  5:   */ 
/*  6:   */ public class PseudoElementSelectorImpl
/*  7:   */   implements ElementSelector, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 2913936296006875268L;
/* 10:   */   private String localName;
/* 11:   */   
/* 12:   */   public void setLocaleName(String localName)
/* 13:   */   {
/* 14:46 */     this.localName = localName;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public PseudoElementSelectorImpl(String localName)
/* 18:   */   {
/* 19:51 */     this.localName = localName;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public PseudoElementSelectorImpl() {}
/* 23:   */   
/* 24:   */   public short getSelectorType()
/* 25:   */   {
/* 26:60 */     return 9;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getNamespaceURI()
/* 30:   */   {
/* 31:64 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getLocalName()
/* 35:   */   {
/* 36:68 */     return this.localName;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.PseudoElementSelectorImpl
 * JD-Core Version:    0.7.0.1
 */