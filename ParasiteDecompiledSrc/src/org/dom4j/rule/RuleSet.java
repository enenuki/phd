/*  1:   */ package org.dom4j.rule;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import org.dom4j.Node;
/*  6:   */ 
/*  7:   */ public class RuleSet
/*  8:   */ {
/*  9:28 */   private ArrayList rules = new ArrayList();
/* 10:   */   private Rule[] ruleArray;
/* 11:   */   
/* 12:   */   public String toString()
/* 13:   */   {
/* 14:37 */     return super.toString() + " [RuleSet: " + this.rules + " ]";
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Rule getMatchingRule(Node node)
/* 18:   */   {
/* 19:50 */     Rule[] matches = getRuleArray();
/* 20:52 */     for (int i = matches.length - 1; i >= 0; i--)
/* 21:   */     {
/* 22:53 */       Rule rule = matches[i];
/* 23:55 */       if (rule.matches(node)) {
/* 24:56 */         return rule;
/* 25:   */       }
/* 26:   */     }
/* 27:60 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void addRule(Rule rule)
/* 31:   */   {
/* 32:64 */     this.rules.add(rule);
/* 33:65 */     this.ruleArray = null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void removeRule(Rule rule)
/* 37:   */   {
/* 38:69 */     this.rules.remove(rule);
/* 39:70 */     this.ruleArray = null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void addAll(RuleSet that)
/* 43:   */   {
/* 44:80 */     this.rules.addAll(that.rules);
/* 45:81 */     this.ruleArray = null;
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected Rule[] getRuleArray()
/* 49:   */   {
/* 50:91 */     if (this.ruleArray == null)
/* 51:   */     {
/* 52:92 */       Collections.sort(this.rules);
/* 53:   */       
/* 54:94 */       int size = this.rules.size();
/* 55:95 */       this.ruleArray = new Rule[size];
/* 56:96 */       this.rules.toArray(this.ruleArray);
/* 57:   */     }
/* 58:99 */     return this.ruleArray;
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.RuleSet
 * JD-Core Version:    0.7.0.1
 */