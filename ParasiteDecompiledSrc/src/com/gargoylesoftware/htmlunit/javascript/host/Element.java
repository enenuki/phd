/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.NamedNodeMap;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  10:    */ import com.gargoylesoftware.htmlunit.xml.XmlUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  17:    */ 
/*  18:    */ public class Element
/*  19:    */   extends EventNode
/*  20:    */ {
/*  21:    */   private NamedNodeMap attributes_;
/*  22:    */   private Map<String, HTMLCollection> elementsByTagName_;
/*  23:    */   
/*  24:    */   public HTMLCollection jsxFunction_selectNodes(final String expression)
/*  25:    */   {
/*  26: 52 */     final DomElement domNode = getDomNodeOrDie();
/*  27: 53 */     boolean attributeChangeSensitive = expression.contains("@");
/*  28: 54 */     String description = "Element.selectNodes('" + expression + "')";
/*  29: 55 */     HTMLCollection collection = new HTMLCollection(domNode, attributeChangeSensitive, description)
/*  30:    */     {
/*  31:    */       protected List<Object> computeElements()
/*  32:    */       {
/*  33: 57 */         List<Object> list = new ArrayList(domNode.getByXPath(expression));
/*  34: 58 */         return list;
/*  35:    */       }
/*  36: 60 */     };
/*  37: 61 */     return collection;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object jsxFunction_selectSingleNode(String expression)
/*  41:    */   {
/*  42: 71 */     HTMLCollection collection = jsxFunction_selectNodes(expression);
/*  43: 72 */     if (collection.jsxGet_length() > 0) {
/*  44: 73 */       return collection.get(0, collection);
/*  45:    */     }
/*  46: 75 */     return null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final String jsxGet_tagName()
/*  50:    */   {
/*  51: 83 */     return jsxGet_nodeName();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object jsxGet_attributes()
/*  55:    */   {
/*  56: 92 */     if (this.attributes_ == null) {
/*  57: 93 */       this.attributes_ = createAttributesObject();
/*  58:    */     }
/*  59: 95 */     return this.attributes_;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected NamedNodeMap createAttributesObject()
/*  63:    */   {
/*  64:103 */     return new NamedNodeMap(getDomNodeOrDie());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Object jsxFunction_getAttribute(String attributeName, Integer flags)
/*  68:    */   {
/*  69:115 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_36);
/*  70:116 */     attributeName = fixAttributeName(attributeName);
/*  71:    */     Object value;
/*  72:    */     Object value;
/*  73:119 */     if ((ie) && (flags != null) && (flags.intValue() == 2) && ("style".equalsIgnoreCase(attributeName))) {
/*  74:120 */       value = "";
/*  75:    */     } else {
/*  76:123 */       value = getDomNodeOrDie().getAttribute(attributeName);
/*  77:    */     }
/*  78:126 */     if (value == DomElement.ATTRIBUTE_NOT_DEFINED)
/*  79:    */     {
/*  80:127 */       value = null;
/*  81:128 */       if (ie) {
/*  82:129 */         for (Scriptable object = this; object != null; object = object.getPrototype())
/*  83:    */         {
/*  84:130 */           Object property = object.get(attributeName, this);
/*  85:131 */           if (property != NOT_FOUND)
/*  86:    */           {
/*  87:132 */             value = property;
/*  88:133 */             break;
/*  89:    */           }
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:139 */     return value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected String fixAttributeName(String attributeName)
/*  97:    */   {
/*  98:148 */     return attributeName;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void jsxFunction_setAttribute(String name, String value)
/* 102:    */   {
/* 103:158 */     getDomNodeOrDie().setAttribute(name, value);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Object jsxFunction_getElementsByTagName(String tagName)
/* 107:    */   {
/* 108:167 */     final String tagNameLC = tagName.toLowerCase();
/* 109:169 */     if (this.elementsByTagName_ == null) {
/* 110:170 */       this.elementsByTagName_ = new HashMap();
/* 111:    */     }
/* 112:173 */     HTMLCollection collection = (HTMLCollection)this.elementsByTagName_.get(tagNameLC);
/* 113:174 */     if (collection != null) {
/* 114:175 */       return collection;
/* 115:    */     }
/* 116:178 */     DomNode node = getDomNodeOrDie();
/* 117:179 */     String description = "Element.getElementsByTagName('" + tagNameLC + "')";
/* 118:180 */     if ("*".equals(tagName)) {
/* 119:181 */       collection = new HTMLCollection(node, false, description)
/* 120:    */       {
/* 121:    */         protected boolean isMatching(DomNode node)
/* 122:    */         {
/* 123:183 */           return true;
/* 124:    */         }
/* 125:    */       };
/* 126:    */     } else {
/* 127:188 */       collection = new HTMLCollection(node, false, description)
/* 128:    */       {
/* 129:    */         protected boolean isMatching(DomNode node)
/* 130:    */         {
/* 131:190 */           return tagNameLC.equalsIgnoreCase(node.getLocalName());
/* 132:    */         }
/* 133:    */       };
/* 134:    */     }
/* 135:195 */     this.elementsByTagName_.put(tagName, collection);
/* 136:    */     
/* 137:197 */     return collection;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Object jsxFunction_getAttributeNode(String name)
/* 141:    */   {
/* 142:206 */     Map<String, DomAttr> attributes = getDomNodeOrDie().getAttributesMap();
/* 143:207 */     for (DomAttr attr : attributes.values()) {
/* 144:208 */       if (attr.getName().equals(name)) {
/* 145:209 */         return attr.getScriptObject();
/* 146:    */       }
/* 147:    */     }
/* 148:212 */     return null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String jsxGet_text()
/* 152:    */   {
/* 153:220 */     StringBuilder buffer = new StringBuilder();
/* 154:221 */     toText(getDomNodeOrDie(), buffer);
/* 155:222 */     return buffer.toString();
/* 156:    */   }
/* 157:    */   
/* 158:    */   private void toText(DomNode node, StringBuilder buffer)
/* 159:    */   {
/* 160:226 */     switch (node.getNodeType())
/* 161:    */     {
/* 162:    */     case 10: 
/* 163:    */     case 12: 
/* 164:229 */       return;
/* 165:    */     case 3: 
/* 166:    */     case 4: 
/* 167:    */     case 7: 
/* 168:    */     case 8: 
/* 169:234 */       buffer.append(node.getNodeValue());
/* 170:235 */       break;
/* 171:    */     }
/* 172:238 */     for (DomNode child : node.getChildren()) {
/* 173:239 */       switch (child.getNodeType())
/* 174:    */       {
/* 175:    */       case 1: 
/* 176:241 */         toText(child, buffer);
/* 177:242 */         break;
/* 178:    */       case 3: 
/* 179:    */       case 4: 
/* 180:246 */         buffer.append(child.getNodeValue());
/* 181:    */       }
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Object jsxFunction_getElementsByTagNameNS(Object namespaceURI, final String localName)
/* 186:    */   {
/* 187:261 */     String description = "Element.getElementsByTagNameNS('" + namespaceURI + "', '" + localName + "')";
/* 188:262 */     DomElement domNode = getDomNodeOrDie();
/* 189:    */     String prefix;
/* 190:    */     final String prefix;
/* 191:265 */     if ((namespaceURI != null) && (!"*".equals("*"))) {
/* 192:266 */       prefix = XmlUtil.lookupPrefix(domNode, Context.toString(namespaceURI));
/* 193:    */     } else {
/* 194:269 */       prefix = null;
/* 195:    */     }
/* 196:272 */     HTMLCollection collection = new HTMLCollection(domNode, false, description)
/* 197:    */     {
/* 198:    */       protected boolean isMatching(DomNode node)
/* 199:    */       {
/* 200:275 */         if (!localName.equals(node.getLocalName())) {
/* 201:276 */           return false;
/* 202:    */         }
/* 203:278 */         if (prefix == null) {
/* 204:279 */           return true;
/* 205:    */         }
/* 206:281 */         return true;
/* 207:    */       }
/* 208:284 */     };
/* 209:285 */     return collection;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean jsxFunction_hasAttribute(String name)
/* 213:    */   {
/* 214:296 */     return getDomNodeOrDie().hasAttribute(name);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public DomElement getDomNodeOrDie()
/* 218:    */   {
/* 219:305 */     return (DomElement)super.getDomNodeOrDie();
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void jsxFunction_removeAttribute(String name)
/* 223:    */   {
/* 224:313 */     getDomNodeOrDie().removeAttribute(name);
/* 225:314 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_37)) {
/* 226:315 */       delete(name);
/* 227:    */     }
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Element
 * JD-Core Version:    0.7.0.1
 */