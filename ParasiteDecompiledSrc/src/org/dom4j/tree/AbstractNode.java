/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.List;
/*   7:    */ import org.dom4j.Document;
/*   8:    */ import org.dom4j.DocumentFactory;
/*   9:    */ import org.dom4j.Element;
/*  10:    */ import org.dom4j.Node;
/*  11:    */ import org.dom4j.NodeFilter;
/*  12:    */ import org.dom4j.XPath;
/*  13:    */ import org.dom4j.rule.Pattern;
/*  14:    */ 
/*  15:    */ public abstract class AbstractNode
/*  16:    */   implements Node, Cloneable, Serializable
/*  17:    */ {
/*  18: 33 */   protected static final String[] NODE_TYPE_NAMES = { "Node", "Element", "Attribute", "Text", "CDATA", "Entity", "Entity", "ProcessingInstruction", "Comment", "Document", "DocumentType", "DocumentFragment", "Notation", "Namespace", "Unknown" };
/*  19: 39 */   private static final DocumentFactory DOCUMENT_FACTORY = DocumentFactory.getInstance();
/*  20:    */   
/*  21:    */   public short getNodeType()
/*  22:    */   {
/*  23: 46 */     return 14;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getNodeTypeName()
/*  27:    */   {
/*  28: 50 */     int type = getNodeType();
/*  29: 52 */     if ((type < 0) || (type >= NODE_TYPE_NAMES.length)) {
/*  30: 53 */       return "Unknown";
/*  31:    */     }
/*  32: 56 */     return NODE_TYPE_NAMES[type];
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Document getDocument()
/*  36:    */   {
/*  37: 60 */     Element element = getParent();
/*  38:    */     
/*  39: 62 */     return element != null ? element.getDocument() : null;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setDocument(Document document) {}
/*  43:    */   
/*  44:    */   public Element getParent()
/*  45:    */   {
/*  46: 69 */     return null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setParent(Element parent) {}
/*  50:    */   
/*  51:    */   public boolean supportsParent()
/*  52:    */   {
/*  53: 76 */     return false;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isReadOnly()
/*  57:    */   {
/*  58: 80 */     return true;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean hasContent()
/*  62:    */   {
/*  63: 84 */     return false;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getPath()
/*  67:    */   {
/*  68: 88 */     return getPath(null);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getUniquePath()
/*  72:    */   {
/*  73: 92 */     return getUniquePath(null);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object clone()
/*  77:    */   {
/*  78: 96 */     if (isReadOnly()) {
/*  79: 97 */       return this;
/*  80:    */     }
/*  81:    */     try
/*  82:    */     {
/*  83:100 */       Node answer = (Node)super.clone();
/*  84:101 */       answer.setParent(null);
/*  85:102 */       answer.setDocument(null);
/*  86:    */       
/*  87:104 */       return answer;
/*  88:    */     }
/*  89:    */     catch (CloneNotSupportedException e)
/*  90:    */     {
/*  91:107 */       throw new RuntimeException("This should never happen. Caught: " + e);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Node detach()
/*  96:    */   {
/*  97:114 */     Element parent = getParent();
/*  98:116 */     if (parent != null)
/*  99:    */     {
/* 100:117 */       parent.remove(this);
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:119 */       Document document = getDocument();
/* 105:121 */       if (document != null) {
/* 106:122 */         document.remove(this);
/* 107:    */       }
/* 108:    */     }
/* 109:126 */     setParent(null);
/* 110:127 */     setDocument(null);
/* 111:    */     
/* 112:129 */     return this;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getName()
/* 116:    */   {
/* 117:133 */     return null;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setName(String name)
/* 121:    */   {
/* 122:137 */     throw new UnsupportedOperationException("This node cannot be modified");
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getText()
/* 126:    */   {
/* 127:141 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getStringValue()
/* 131:    */   {
/* 132:145 */     return getText();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setText(String text)
/* 136:    */   {
/* 137:149 */     throw new UnsupportedOperationException("This node cannot be modified");
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void write(Writer writer)
/* 141:    */     throws IOException
/* 142:    */   {
/* 143:153 */     writer.write(asXML());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Object selectObject(String xpathExpression)
/* 147:    */   {
/* 148:158 */     XPath xpath = createXPath(xpathExpression);
/* 149:    */     
/* 150:160 */     return xpath.evaluate(this);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List selectNodes(String xpathExpression)
/* 154:    */   {
/* 155:164 */     XPath xpath = createXPath(xpathExpression);
/* 156:    */     
/* 157:166 */     return xpath.selectNodes(this);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List selectNodes(String xpathExpression, String comparisonXPathExpression)
/* 161:    */   {
/* 162:171 */     return selectNodes(xpathExpression, comparisonXPathExpression, false);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List selectNodes(String xpathExpression, String comparisonXPathExpression, boolean removeDuplicates)
/* 166:    */   {
/* 167:176 */     XPath xpath = createXPath(xpathExpression);
/* 168:177 */     XPath sortBy = createXPath(comparisonXPathExpression);
/* 169:    */     
/* 170:179 */     return xpath.selectNodes(this, sortBy, removeDuplicates);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Node selectSingleNode(String xpathExpression)
/* 174:    */   {
/* 175:183 */     XPath xpath = createXPath(xpathExpression);
/* 176:    */     
/* 177:185 */     return xpath.selectSingleNode(this);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String valueOf(String xpathExpression)
/* 181:    */   {
/* 182:189 */     XPath xpath = createXPath(xpathExpression);
/* 183:    */     
/* 184:191 */     return xpath.valueOf(this);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Number numberValueOf(String xpathExpression)
/* 188:    */   {
/* 189:195 */     XPath xpath = createXPath(xpathExpression);
/* 190:    */     
/* 191:197 */     return xpath.numberValueOf(this);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean matches(String patternText)
/* 195:    */   {
/* 196:201 */     NodeFilter filter = createXPathFilter(patternText);
/* 197:    */     
/* 198:203 */     return filter.matches(this);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public XPath createXPath(String xpathExpression)
/* 202:    */   {
/* 203:207 */     return getDocumentFactory().createXPath(xpathExpression);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public NodeFilter createXPathFilter(String patternText)
/* 207:    */   {
/* 208:211 */     return getDocumentFactory().createXPathFilter(patternText);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Pattern createPattern(String patternText)
/* 212:    */   {
/* 213:215 */     return getDocumentFactory().createPattern(patternText);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Node asXPathResult(Element parent)
/* 217:    */   {
/* 218:219 */     if (supportsParent()) {
/* 219:220 */       return this;
/* 220:    */     }
/* 221:223 */     return createXPathResult(parent);
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected DocumentFactory getDocumentFactory()
/* 225:    */   {
/* 226:227 */     return DOCUMENT_FACTORY;
/* 227:    */   }
/* 228:    */   
/* 229:    */   protected Node createXPathResult(Element parent)
/* 230:    */   {
/* 231:231 */     throw new RuntimeException("asXPathResult() not yet implemented fully for: " + this);
/* 232:    */   }
/* 233:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractNode
 * JD-Core Version:    0.7.0.1
 */