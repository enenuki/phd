/*  1:   */ package com.steadystate.css.dom;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import org.w3c.dom.css.CSSRule;
/*  7:   */ import org.w3c.dom.css.CSSRuleList;
/*  8:   */ 
/*  9:   */ public class CSSRuleListImpl
/* 10:   */   implements CSSRuleList, Serializable
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = -1269068897476453290L;
/* 13:48 */   private List<CSSRule> rules = null;
/* 14:   */   
/* 15:   */   public List<CSSRule> getRules()
/* 16:   */   {
/* 17:52 */     if (this.rules == null) {
/* 18:54 */       this.rules = new ArrayList();
/* 19:   */     }
/* 20:56 */     return this.rules;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setRules(List<CSSRule> rules)
/* 24:   */   {
/* 25:61 */     this.rules = rules;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getLength()
/* 29:   */   {
/* 30:69 */     return getRules().size();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public CSSRule item(int index)
/* 34:   */   {
/* 35:73 */     return (CSSRule)getRules().get(index);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void add(CSSRule rule)
/* 39:   */   {
/* 40:77 */     getRules().add(rule);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void insert(CSSRule rule, int index)
/* 44:   */   {
/* 45:81 */     getRules().add(index, rule);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void delete(int index)
/* 49:   */   {
/* 50:85 */     getRules().remove(index);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String toString()
/* 54:   */   {
/* 55:89 */     StringBuilder sb = new StringBuilder();
/* 56:90 */     for (int i = 0; i < getLength(); i++) {
/* 57:91 */       sb.append(item(i).toString()).append("\r\n");
/* 58:   */     }
/* 59:93 */     return sb.toString();
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSRuleListImpl
 * JD-Core Version:    0.7.0.1
 */