/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.SourceLocator;
/*   4:    */ import org.apache.xalan.extensions.ExpressionContext;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.dtm.ref.DTMNodeProxy;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ import org.w3c.dom.NodeList;
/*   9:    */ 
/*  10:    */ public class NodeInfo
/*  11:    */ {
/*  12:    */   public static String systemId(ExpressionContext context)
/*  13:    */   {
/*  14: 50 */     Node contextNode = context.getContextNode();
/*  15: 51 */     int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
/*  16: 52 */     SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM().getSourceLocatorFor(nodeHandler);
/*  17: 55 */     if (locator != null) {
/*  18: 56 */       return locator.getSystemId();
/*  19:    */     }
/*  20: 58 */     return null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static String systemId(NodeList nodeList)
/*  24:    */   {
/*  25: 71 */     if ((nodeList == null) || (nodeList.getLength() == 0)) {
/*  26: 72 */       return null;
/*  27:    */     }
/*  28: 74 */     Node node = nodeList.item(0);
/*  29: 75 */     int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
/*  30: 76 */     SourceLocator locator = ((DTMNodeProxy)node).getDTM().getSourceLocatorFor(nodeHandler);
/*  31: 79 */     if (locator != null) {
/*  32: 80 */       return locator.getSystemId();
/*  33:    */     }
/*  34: 82 */     return null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static String publicId(ExpressionContext context)
/*  38:    */   {
/*  39: 96 */     Node contextNode = context.getContextNode();
/*  40: 97 */     int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
/*  41: 98 */     SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM().getSourceLocatorFor(nodeHandler);
/*  42:101 */     if (locator != null) {
/*  43:102 */       return locator.getPublicId();
/*  44:    */     }
/*  45:104 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static String publicId(NodeList nodeList)
/*  49:    */   {
/*  50:119 */     if ((nodeList == null) || (nodeList.getLength() == 0)) {
/*  51:120 */       return null;
/*  52:    */     }
/*  53:122 */     Node node = nodeList.item(0);
/*  54:123 */     int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
/*  55:124 */     SourceLocator locator = ((DTMNodeProxy)node).getDTM().getSourceLocatorFor(nodeHandler);
/*  56:127 */     if (locator != null) {
/*  57:128 */       return locator.getPublicId();
/*  58:    */     }
/*  59:130 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static int lineNumber(ExpressionContext context)
/*  63:    */   {
/*  64:149 */     Node contextNode = context.getContextNode();
/*  65:150 */     int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
/*  66:151 */     SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM().getSourceLocatorFor(nodeHandler);
/*  67:154 */     if (locator != null) {
/*  68:155 */       return locator.getLineNumber();
/*  69:    */     }
/*  70:157 */     return -1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static int lineNumber(NodeList nodeList)
/*  74:    */   {
/*  75:177 */     if ((nodeList == null) || (nodeList.getLength() == 0)) {
/*  76:178 */       return -1;
/*  77:    */     }
/*  78:180 */     Node node = nodeList.item(0);
/*  79:181 */     int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
/*  80:182 */     SourceLocator locator = ((DTMNodeProxy)node).getDTM().getSourceLocatorFor(nodeHandler);
/*  81:185 */     if (locator != null) {
/*  82:186 */       return locator.getLineNumber();
/*  83:    */     }
/*  84:188 */     return -1;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static int columnNumber(ExpressionContext context)
/*  88:    */   {
/*  89:207 */     Node contextNode = context.getContextNode();
/*  90:208 */     int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
/*  91:209 */     SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM().getSourceLocatorFor(nodeHandler);
/*  92:212 */     if (locator != null) {
/*  93:213 */       return locator.getColumnNumber();
/*  94:    */     }
/*  95:215 */     return -1;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static int columnNumber(NodeList nodeList)
/*  99:    */   {
/* 100:235 */     if ((nodeList == null) || (nodeList.getLength() == 0)) {
/* 101:236 */       return -1;
/* 102:    */     }
/* 103:238 */     Node node = nodeList.item(0);
/* 104:239 */     int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
/* 105:240 */     SourceLocator locator = ((DTMNodeProxy)node).getDTM().getSourceLocatorFor(nodeHandler);
/* 106:243 */     if (locator != null) {
/* 107:244 */       return locator.getColumnNumber();
/* 108:    */     }
/* 109:246 */     return -1;
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.NodeInfo
 * JD-Core Version:    0.7.0.1
 */