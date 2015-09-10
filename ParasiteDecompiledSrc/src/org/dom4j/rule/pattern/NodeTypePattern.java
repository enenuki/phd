/*  1:   */ package org.dom4j.rule.pattern;
/*  2:   */ 
/*  3:   */ import org.dom4j.Node;
/*  4:   */ import org.dom4j.rule.Pattern;
/*  5:   */ 
/*  6:   */ public class NodeTypePattern
/*  7:   */   implements Pattern
/*  8:   */ {
/*  9:24 */   public static final NodeTypePattern ANY_ATTRIBUTE = new NodeTypePattern((short)2);
/* 10:28 */   public static final NodeTypePattern ANY_COMMENT = new NodeTypePattern((short)8);
/* 11:32 */   public static final NodeTypePattern ANY_DOCUMENT = new NodeTypePattern((short)9);
/* 12:36 */   public static final NodeTypePattern ANY_ELEMENT = new NodeTypePattern((short)1);
/* 13:40 */   public static final NodeTypePattern ANY_PROCESSING_INSTRUCTION = new NodeTypePattern((short)7);
/* 14:44 */   public static final NodeTypePattern ANY_TEXT = new NodeTypePattern((short)3);
/* 15:   */   private short nodeType;
/* 16:   */   
/* 17:   */   public NodeTypePattern(short nodeType)
/* 18:   */   {
/* 19:50 */     this.nodeType = nodeType;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean matches(Node node)
/* 23:   */   {
/* 24:54 */     return node.getNodeType() == this.nodeType;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public double getPriority()
/* 28:   */   {
/* 29:58 */     return 0.5D;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Pattern[] getUnionPatterns()
/* 33:   */   {
/* 34:62 */     return null;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public short getMatchType()
/* 38:   */   {
/* 39:66 */     return this.nodeType;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getMatchesNodeName()
/* 43:   */   {
/* 44:70 */     return null;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.pattern.NodeTypePattern
 * JD-Core Version:    0.7.0.1
 */