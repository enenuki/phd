/*  1:   */ package org.dom4j.rule.pattern;
/*  2:   */ 
/*  3:   */ import org.dom4j.Node;
/*  4:   */ import org.dom4j.NodeFilter;
/*  5:   */ import org.dom4j.rule.Pattern;
/*  6:   */ 
/*  7:   */ public class DefaultPattern
/*  8:   */   implements Pattern
/*  9:   */ {
/* 10:   */   private NodeFilter filter;
/* 11:   */   
/* 12:   */   public DefaultPattern(NodeFilter filter)
/* 13:   */   {
/* 14:31 */     this.filter = filter;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean matches(Node node)
/* 18:   */   {
/* 19:35 */     return this.filter.matches(node);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public double getPriority()
/* 23:   */   {
/* 24:39 */     return 0.5D;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Pattern[] getUnionPatterns()
/* 28:   */   {
/* 29:43 */     return null;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public short getMatchType()
/* 33:   */   {
/* 34:47 */     return 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getMatchesNodeName()
/* 38:   */   {
/* 39:51 */     return null;
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.pattern.DefaultPattern
 * JD-Core Version:    0.7.0.1
 */