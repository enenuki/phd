/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.apache.xml.utils.DOMHelper;
/*   6:    */ import org.apache.xpath.NodeSet;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ import org.w3c.dom.NodeList;
/*   9:    */ 
/*  10:    */ public class ExsltSets
/*  11:    */   extends ExsltBase
/*  12:    */ {
/*  13:    */   public static NodeList leading(NodeList nl1, NodeList nl2)
/*  14:    */   {
/*  15: 62 */     if (nl2.getLength() == 0) {
/*  16: 63 */       return nl1;
/*  17:    */     }
/*  18: 65 */     NodeSet ns1 = new NodeSet(nl1);
/*  19: 66 */     NodeSet leadNodes = new NodeSet();
/*  20: 67 */     Node endNode = nl2.item(0);
/*  21: 68 */     if (!ns1.contains(endNode)) {
/*  22: 69 */       return leadNodes;
/*  23:    */     }
/*  24: 71 */     for (int i = 0; i < nl1.getLength(); i++)
/*  25:    */     {
/*  26: 73 */       Node testNode = nl1.item(i);
/*  27: 74 */       if ((DOMHelper.isNodeAfter(testNode, endNode)) && (!DOMHelper.isNodeTheSame(testNode, endNode))) {
/*  28: 76 */         leadNodes.addElement(testNode);
/*  29:    */       }
/*  30:    */     }
/*  31: 78 */     return leadNodes;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static NodeList trailing(NodeList nl1, NodeList nl2)
/*  35:    */   {
/*  36: 97 */     if (nl2.getLength() == 0) {
/*  37: 98 */       return nl1;
/*  38:    */     }
/*  39:100 */     NodeSet ns1 = new NodeSet(nl1);
/*  40:101 */     NodeSet trailNodes = new NodeSet();
/*  41:102 */     Node startNode = nl2.item(0);
/*  42:103 */     if (!ns1.contains(startNode)) {
/*  43:104 */       return trailNodes;
/*  44:    */     }
/*  45:106 */     for (int i = 0; i < nl1.getLength(); i++)
/*  46:    */     {
/*  47:108 */       Node testNode = nl1.item(i);
/*  48:109 */       if ((DOMHelper.isNodeAfter(startNode, testNode)) && (!DOMHelper.isNodeTheSame(startNode, testNode))) {
/*  49:111 */         trailNodes.addElement(testNode);
/*  50:    */       }
/*  51:    */     }
/*  52:113 */     return trailNodes;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static NodeList intersection(NodeList nl1, NodeList nl2)
/*  56:    */   {
/*  57:129 */     NodeSet ns1 = new NodeSet(nl1);
/*  58:130 */     NodeSet ns2 = new NodeSet(nl2);
/*  59:131 */     NodeSet inter = new NodeSet();
/*  60:    */     
/*  61:133 */     inter.setShouldCacheNodes(true);
/*  62:135 */     for (int i = 0; i < ns1.getLength(); i++)
/*  63:    */     {
/*  64:137 */       Node n = ns1.elementAt(i);
/*  65:139 */       if (ns2.contains(n)) {
/*  66:140 */         inter.addElement(n);
/*  67:    */       }
/*  68:    */     }
/*  69:143 */     return inter;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static NodeList difference(NodeList nl1, NodeList nl2)
/*  73:    */   {
/*  74:159 */     NodeSet ns1 = new NodeSet(nl1);
/*  75:160 */     NodeSet ns2 = new NodeSet(nl2);
/*  76:    */     
/*  77:162 */     NodeSet diff = new NodeSet();
/*  78:    */     
/*  79:164 */     diff.setShouldCacheNodes(true);
/*  80:166 */     for (int i = 0; i < ns1.getLength(); i++)
/*  81:    */     {
/*  82:168 */       Node n = ns1.elementAt(i);
/*  83:170 */       if (!ns2.contains(n)) {
/*  84:171 */         diff.addElement(n);
/*  85:    */       }
/*  86:    */     }
/*  87:174 */     return diff;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static NodeList distinct(NodeList nl)
/*  91:    */   {
/*  92:191 */     NodeSet dist = new NodeSet();
/*  93:192 */     dist.setShouldCacheNodes(true);
/*  94:    */     
/*  95:194 */     Map stringTable = new HashMap();
/*  96:196 */     for (int i = 0; i < nl.getLength(); i++)
/*  97:    */     {
/*  98:198 */       Node currNode = nl.item(i);
/*  99:199 */       String key = ExsltBase.toString(currNode);
/* 100:201 */       if (key == null)
/* 101:    */       {
/* 102:202 */         dist.addElement(currNode);
/* 103:    */       }
/* 104:203 */       else if (!stringTable.containsKey(key))
/* 105:    */       {
/* 106:205 */         stringTable.put(key, currNode);
/* 107:206 */         dist.addElement(currNode);
/* 108:    */       }
/* 109:    */     }
/* 110:210 */     return dist;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static boolean hasSameNode(NodeList nl1, NodeList nl2)
/* 114:    */   {
/* 115:229 */     NodeSet ns1 = new NodeSet(nl1);
/* 116:230 */     NodeSet ns2 = new NodeSet(nl2);
/* 117:232 */     for (int i = 0; i < ns1.getLength(); i++) {
/* 118:234 */       if (ns2.contains(ns1.elementAt(i))) {
/* 119:235 */         return true;
/* 120:    */       }
/* 121:    */     }
/* 122:237 */     return false;
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltSets
 * JD-Core Version:    0.7.0.1
 */