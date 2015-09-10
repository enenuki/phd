/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ 
/*   5:    */ public final class Util
/*   6:    */ {
/*   7:    */   public static Iterator<DomNode> getFollowingSiblingAxisIterator(DomNode contextNode)
/*   8:    */   {
/*   9: 41 */     new NodeIterator(contextNode)
/*  10:    */     {
/*  11:    */       protected DomNode getFirstNode(DomNode node)
/*  12:    */       {
/*  13: 44 */         return getNextNode(node);
/*  14:    */       }
/*  15:    */       
/*  16:    */       protected DomNode getNextNode(DomNode node)
/*  17:    */       {
/*  18: 48 */         return node.getNextSibling();
/*  19:    */       }
/*  20:    */     };
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static Iterator<DomNode> getPrecedingSiblingAxisIterator(DomNode contextNode)
/*  24:    */   {
/*  25: 60 */     new NodeIterator(contextNode)
/*  26:    */     {
/*  27:    */       protected DomNode getFirstNode(DomNode node)
/*  28:    */       {
/*  29: 63 */         return getNextNode(node);
/*  30:    */       }
/*  31:    */       
/*  32:    */       protected DomNode getNextNode(DomNode node)
/*  33:    */       {
/*  34: 67 */         return node.getPreviousSibling();
/*  35:    */       }
/*  36:    */     };
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Iterator<DomNode> getFollowingAxisIterator(DomNode contextNode)
/*  40:    */   {
/*  41: 79 */     new NodeIterator(contextNode)
/*  42:    */     {
/*  43:    */       protected DomNode getFirstNode(DomNode node)
/*  44:    */       {
/*  45: 82 */         if (node == null) {
/*  46: 83 */           return null;
/*  47:    */         }
/*  48: 85 */         DomNode sibling = node.getNextSibling();
/*  49: 86 */         if (sibling == null) {
/*  50: 87 */           return getFirstNode(node.getParentNode());
/*  51:    */         }
/*  52: 89 */         return sibling;
/*  53:    */       }
/*  54:    */       
/*  55:    */       protected DomNode getNextNode(DomNode node)
/*  56:    */       {
/*  57: 94 */         if (node == null) {
/*  58: 95 */           return null;
/*  59:    */         }
/*  60: 97 */         DomNode n = node.getFirstChild();
/*  61: 98 */         if (n == null) {
/*  62: 99 */           n = node.getNextSibling();
/*  63:    */         }
/*  64:101 */         if (n == null) {
/*  65:102 */           return getFirstNode(node.getParentNode());
/*  66:    */         }
/*  67:104 */         return n;
/*  68:    */       }
/*  69:    */     };
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static Iterator<DomNode> getPrecedingAxisIterator(DomNode contextNode)
/*  73:    */   {
/*  74:116 */     new NodeIterator(contextNode)
/*  75:    */     {
/*  76:    */       protected DomNode getFirstNode(DomNode node)
/*  77:    */       {
/*  78:119 */         if (node == null) {
/*  79:120 */           return null;
/*  80:    */         }
/*  81:122 */         DomNode sibling = node.getPreviousSibling();
/*  82:123 */         if (sibling == null) {
/*  83:124 */           return getFirstNode(node.getParentNode());
/*  84:    */         }
/*  85:126 */         return sibling;
/*  86:    */       }
/*  87:    */       
/*  88:    */       protected DomNode getNextNode(DomNode node)
/*  89:    */       {
/*  90:130 */         if (node == null) {
/*  91:131 */           return null;
/*  92:    */         }
/*  93:133 */         DomNode n = node.getLastChild();
/*  94:134 */         if (n == null) {
/*  95:135 */           n = node.getPreviousSibling();
/*  96:    */         }
/*  97:137 */         if (n == null) {
/*  98:138 */           return getFirstNode(node.getParentNode());
/*  99:    */         }
/* 100:140 */         return n;
/* 101:    */       }
/* 102:    */     };
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.Util
 * JD-Core Version:    0.7.0.1
 */