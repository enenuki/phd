/*  1:   */ package com.steadystate.css.parser.selectors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.w3c.css.sac.LangCondition;
/*  5:   */ 
/*  6:   */ public class LangConditionImpl
/*  7:   */   implements LangCondition, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1701599531953055387L;
/* 10:   */   private String lang;
/* 11:   */   
/* 12:   */   public void setLang(String lang)
/* 13:   */   {
/* 14:46 */     this.lang = lang;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public LangConditionImpl(String lang)
/* 18:   */   {
/* 19:51 */     this.lang = lang;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public LangConditionImpl() {}
/* 23:   */   
/* 24:   */   public short getConditionType()
/* 25:   */   {
/* 26:60 */     return 6;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getLang()
/* 30:   */   {
/* 31:64 */     return this.lang;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString()
/* 35:   */   {
/* 36:68 */     return ":lang(" + getLang() + ")";
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.LangConditionImpl
 * JD-Core Version:    0.7.0.1
 */