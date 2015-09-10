/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.dom4j.Element;
/*   5:    */ import org.dom4j.tree.DefaultProcessingInstruction;
/*   6:    */ import org.w3c.dom.DOMException;
/*   7:    */ import org.w3c.dom.Document;
/*   8:    */ import org.w3c.dom.NamedNodeMap;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ import org.w3c.dom.ProcessingInstruction;
/*  12:    */ 
/*  13:    */ public class DOMProcessingInstruction
/*  14:    */   extends DefaultProcessingInstruction
/*  15:    */   implements ProcessingInstruction
/*  16:    */ {
/*  17:    */   public DOMProcessingInstruction(String target, Map values)
/*  18:    */   {
/*  19: 32 */     super(target, values);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DOMProcessingInstruction(String target, String values)
/*  23:    */   {
/*  24: 36 */     super(target, values);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public DOMProcessingInstruction(Element parent, String target, String val)
/*  28:    */   {
/*  29: 40 */     super(parent, target, val);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean supports(String feature, String version)
/*  33:    */   {
/*  34: 46 */     return DOMNodeHelper.supports(this, feature, version);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getNamespaceURI()
/*  38:    */   {
/*  39: 50 */     return DOMNodeHelper.getNamespaceURI(this);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getPrefix()
/*  43:    */   {
/*  44: 54 */     return DOMNodeHelper.getPrefix(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setPrefix(String prefix)
/*  48:    */     throws DOMException
/*  49:    */   {
/*  50: 58 */     DOMNodeHelper.setPrefix(this, prefix);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getLocalName()
/*  54:    */   {
/*  55: 62 */     return DOMNodeHelper.getLocalName(this);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getNodeName()
/*  59:    */   {
/*  60: 66 */     return getName();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getNodeValue()
/*  64:    */     throws DOMException
/*  65:    */   {
/*  66: 73 */     return DOMNodeHelper.getNodeValue(this);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setNodeValue(String nodeValue)
/*  70:    */     throws DOMException
/*  71:    */   {
/*  72: 77 */     DOMNodeHelper.setNodeValue(this, nodeValue);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Node getParentNode()
/*  76:    */   {
/*  77: 81 */     return DOMNodeHelper.getParentNode(this);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public NodeList getChildNodes()
/*  81:    */   {
/*  82: 85 */     return DOMNodeHelper.getChildNodes(this);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Node getFirstChild()
/*  86:    */   {
/*  87: 89 */     return DOMNodeHelper.getFirstChild(this);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Node getLastChild()
/*  91:    */   {
/*  92: 93 */     return DOMNodeHelper.getLastChild(this);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Node getPreviousSibling()
/*  96:    */   {
/*  97: 97 */     return DOMNodeHelper.getPreviousSibling(this);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Node getNextSibling()
/* 101:    */   {
/* 102:101 */     return DOMNodeHelper.getNextSibling(this);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public NamedNodeMap getAttributes()
/* 106:    */   {
/* 107:105 */     return null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Document getOwnerDocument()
/* 111:    */   {
/* 112:109 */     return DOMNodeHelper.getOwnerDocument(this);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Node insertBefore(Node newChild, Node refChild)
/* 116:    */     throws DOMException
/* 117:    */   {
/* 118:114 */     checkNewChildNode(newChild);
/* 119:    */     
/* 120:116 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 124:    */     throws DOMException
/* 125:    */   {
/* 126:121 */     checkNewChildNode(newChild);
/* 127:    */     
/* 128:123 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Node removeChild(Node oldChild)
/* 132:    */     throws DOMException
/* 133:    */   {
/* 134:128 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Node appendChild(Node newChild)
/* 138:    */     throws DOMException
/* 139:    */   {
/* 140:133 */     checkNewChildNode(newChild);
/* 141:    */     
/* 142:135 */     return DOMNodeHelper.appendChild(this, newChild);
/* 143:    */   }
/* 144:    */   
/* 145:    */   private void checkNewChildNode(Node newChild)
/* 146:    */     throws DOMException
/* 147:    */   {
/* 148:140 */     throw new DOMException((short)3, "PI nodes cannot have children");
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean hasChildNodes()
/* 152:    */   {
/* 153:145 */     return DOMNodeHelper.hasChildNodes(this);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Node cloneNode(boolean deep)
/* 157:    */   {
/* 158:149 */     return DOMNodeHelper.cloneNode(this, deep);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void normalize()
/* 162:    */   {
/* 163:153 */     DOMNodeHelper.normalize(this);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean isSupported(String feature, String version)
/* 167:    */   {
/* 168:157 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean hasAttributes()
/* 172:    */   {
/* 173:161 */     return DOMNodeHelper.hasAttributes(this);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getData()
/* 177:    */   {
/* 178:168 */     return getText();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setData(String data)
/* 182:    */     throws DOMException
/* 183:    */   {
/* 184:172 */     if (isReadOnly()) {
/* 185:173 */       throw new DOMException((short)7, "This ProcessingInstruction is read only");
/* 186:    */     }
/* 187:176 */     setText(data);
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */