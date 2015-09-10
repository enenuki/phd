/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.tree.DefaultEntity;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.Document;
/*   7:    */ import org.w3c.dom.EntityReference;
/*   8:    */ import org.w3c.dom.NamedNodeMap;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ 
/*  12:    */ public class DOMEntityReference
/*  13:    */   extends DefaultEntity
/*  14:    */   implements EntityReference
/*  15:    */ {
/*  16:    */   public DOMEntityReference(String name)
/*  17:    */   {
/*  18: 30 */     super(name);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DOMEntityReference(String name, String text)
/*  22:    */   {
/*  23: 34 */     super(name, text);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DOMEntityReference(Element parent, String name, String text)
/*  27:    */   {
/*  28: 38 */     super(parent, name, text);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean supports(String feature, String version)
/*  32:    */   {
/*  33: 44 */     return DOMNodeHelper.supports(this, feature, version);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getNamespaceURI()
/*  37:    */   {
/*  38: 48 */     return DOMNodeHelper.getNamespaceURI(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getPrefix()
/*  42:    */   {
/*  43: 52 */     return DOMNodeHelper.getPrefix(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setPrefix(String prefix)
/*  47:    */     throws DOMException
/*  48:    */   {
/*  49: 56 */     DOMNodeHelper.setPrefix(this, prefix);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getLocalName()
/*  53:    */   {
/*  54: 60 */     return DOMNodeHelper.getLocalName(this);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getNodeName()
/*  58:    */   {
/*  59: 64 */     return getName();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getNodeValue()
/*  63:    */     throws DOMException
/*  64:    */   {
/*  65: 71 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setNodeValue(String nodeValue)
/*  69:    */     throws DOMException
/*  70:    */   {}
/*  71:    */   
/*  72:    */   public Node getParentNode()
/*  73:    */   {
/*  74: 78 */     return DOMNodeHelper.getParentNode(this);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public NodeList getChildNodes()
/*  78:    */   {
/*  79: 82 */     return DOMNodeHelper.getChildNodes(this);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Node getFirstChild()
/*  83:    */   {
/*  84: 86 */     return DOMNodeHelper.getFirstChild(this);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Node getLastChild()
/*  88:    */   {
/*  89: 90 */     return DOMNodeHelper.getLastChild(this);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Node getPreviousSibling()
/*  93:    */   {
/*  94: 94 */     return DOMNodeHelper.getPreviousSibling(this);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Node getNextSibling()
/*  98:    */   {
/*  99: 98 */     return DOMNodeHelper.getNextSibling(this);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public NamedNodeMap getAttributes()
/* 103:    */   {
/* 104:102 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Document getOwnerDocument()
/* 108:    */   {
/* 109:106 */     return DOMNodeHelper.getOwnerDocument(this);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Node insertBefore(Node newChild, Node refChild)
/* 113:    */     throws DOMException
/* 114:    */   {
/* 115:111 */     checkNewChildNode(newChild);
/* 116:    */     
/* 117:113 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 121:    */     throws DOMException
/* 122:    */   {
/* 123:118 */     checkNewChildNode(newChild);
/* 124:    */     
/* 125:120 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Node removeChild(Node oldChild)
/* 129:    */     throws DOMException
/* 130:    */   {
/* 131:125 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Node appendChild(Node newChild)
/* 135:    */     throws DOMException
/* 136:    */   {
/* 137:130 */     checkNewChildNode(newChild);
/* 138:    */     
/* 139:132 */     return DOMNodeHelper.appendChild(this, newChild);
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void checkNewChildNode(Node newChild)
/* 143:    */     throws DOMException
/* 144:    */   {
/* 145:137 */     int nodeType = newChild.getNodeType();
/* 146:139 */     if ((nodeType != 1) && (nodeType != 3) && (nodeType != 8) && (nodeType != 7) && (nodeType != 4) && (nodeType != 5)) {
/* 147:145 */       throw new DOMException((short)3, "Given node cannot be a child of an entity reference");
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean hasChildNodes()
/* 152:    */   {
/* 153:151 */     return DOMNodeHelper.hasChildNodes(this);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Node cloneNode(boolean deep)
/* 157:    */   {
/* 158:155 */     return DOMNodeHelper.cloneNode(this, deep);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void normalize()
/* 162:    */   {
/* 163:159 */     DOMNodeHelper.normalize(this);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean isSupported(String feature, String version)
/* 167:    */   {
/* 168:163 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean hasAttributes()
/* 172:    */   {
/* 173:167 */     return DOMNodeHelper.hasAttributes(this);
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMEntityReference
 * JD-Core Version:    0.7.0.1
 */