/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.CharacterDataSelector;
/*  5:   */ 
/*  6:   */ public class CharacterDataSelectorImpl
/*  7:   */   implements CharacterDataSelector, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 4635511567927852889L;
/* 10:   */   private String data;
/* 11:   */   
/* 12:   */   public void setData(String data)
/* 13:   */   {
/* 14:46 */     this.data = data;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public CharacterDataSelectorImpl(String data)
/* 18:   */   {
/* 19:51 */     this.data = data;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public CharacterDataSelectorImpl() {}
/* 23:   */   
/* 24:   */   public short getSelectorType()
/* 25:   */   {
/* 26:60 */     return 6;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getData()
/* 30:   */   {
/* 31:64 */     return this.data;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:68 */     return getData();
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.CharacterDataSelectorImpl
 * JD-Core Version:    0.7.0.1
 */