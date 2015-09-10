/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDocument;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLSerializer;
/*  10:    */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  11:    */ import com.gargoylesoftware.htmlunit.xml.XmlUtil;
/*  12:    */ import java.io.StringWriter;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import javax.xml.parsers.DocumentBuilder;
/*  17:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  18:    */ import javax.xml.transform.Result;
/*  19:    */ import javax.xml.transform.Source;
/*  20:    */ import javax.xml.transform.Transformer;
/*  21:    */ import javax.xml.transform.TransformerFactory;
/*  22:    */ import javax.xml.transform.dom.DOMResult;
/*  23:    */ import javax.xml.transform.dom.DOMSource;
/*  24:    */ import javax.xml.transform.stream.StreamResult;
/*  25:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  26:    */ import org.apache.commons.lang.StringUtils;
/*  27:    */ import org.w3c.dom.Element;
/*  28:    */ import org.w3c.dom.NodeList;
/*  29:    */ 
/*  30:    */ public class XSLTProcessor
/*  31:    */   extends SimpleScriptable
/*  32:    */ {
/*  33:    */   private Node style_;
/*  34:    */   private Node input_;
/*  35:    */   private Object output_;
/*  36: 56 */   private Map<String, Object> parameters_ = new HashMap();
/*  37:    */   
/*  38:    */   public void jsConstructor() {}
/*  39:    */   
/*  40:    */   public void jsxFunction_importStylesheet(Node style)
/*  41:    */   {
/*  42: 74 */     this.style_ = style;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public XMLDocument jsxFunction_transformToDocument(Node source)
/*  46:    */   {
/*  47: 86 */     XMLDocument doc = new XMLDocument();
/*  48: 87 */     doc.setPrototype(getPrototype(doc.getClass()));
/*  49: 88 */     doc.setParentScope(getParentScope());
/*  50:    */     
/*  51: 90 */     Object transformResult = transform(source);
/*  52:    */     org.w3c.dom.Node node;
/*  53:    */     org.w3c.dom.Node node;
/*  54: 92 */     if ((transformResult instanceof org.w3c.dom.Node))
/*  55:    */     {
/*  56: 93 */       org.w3c.dom.Node transformedDoc = (org.w3c.dom.Node)transformResult;
/*  57: 94 */       node = transformedDoc.getFirstChild();
/*  58:    */     }
/*  59:    */     else
/*  60:    */     {
/*  61: 97 */       node = null;
/*  62:    */     }
/*  63: 99 */     XmlPage page = new XmlPage(node, getWindow().getWebWindow());
/*  64:100 */     doc.setDomNode(page);
/*  65:101 */     return doc;
/*  66:    */   }
/*  67:    */   
/*  68:    */   private Object transform(Node source)
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:109 */       Source xmlSource = new DOMSource(source.getDomNodeOrDie());
/*  73:110 */       Source xsltSource = new DOMSource(this.style_.getDomNodeOrDie());
/*  74:    */       
/*  75:112 */       org.w3c.dom.Document containerDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
/*  76:    */       
/*  77:114 */       Element containerElement = containerDocument.createElement("container");
/*  78:115 */       containerDocument.appendChild(containerElement);
/*  79:    */       
/*  80:117 */       DOMResult result = new DOMResult(containerElement);
/*  81:    */       
/*  82:119 */       Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSource);
/*  83:120 */       for (Map.Entry<String, Object> entry : this.parameters_.entrySet()) {
/*  84:121 */         transformer.setParameter((String)entry.getKey(), entry.getValue());
/*  85:    */       }
/*  86:123 */       transformer.transform(xmlSource, result);
/*  87:    */       
/*  88:125 */       org.w3c.dom.Node transformedNode = result.getNode();
/*  89:126 */       if (transformedNode.getFirstChild().getNodeType() == 1) {
/*  90:127 */         return transformedNode;
/*  91:    */       }
/*  92:130 */       xmlSource = new DOMSource(source.getDomNodeOrDie());
/*  93:131 */       StringWriter writer = new StringWriter();
/*  94:132 */       Result streamResult = new StreamResult(writer);
/*  95:133 */       transformer.transform(xmlSource, streamResult);
/*  96:134 */       return writer.toString();
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:137 */       throw Context.reportRuntimeError("Exception: " + e);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public DocumentFragment jsxFunction_transformToFragment(Node source, Object output)
/* 105:    */   {
/* 106:150 */     SgmlPage page = (SgmlPage)((Document)output).getDomNodeOrDie();
/* 107:    */     
/* 108:152 */     DomDocumentFragment fragment = page.createDomDocumentFragment();
/* 109:153 */     DocumentFragment rv = new DocumentFragment();
/* 110:154 */     rv.setPrototype(getPrototype(rv.getClass()));
/* 111:155 */     rv.setParentScope(getParentScope());
/* 112:156 */     rv.setDomNode(fragment);
/* 113:    */     
/* 114:158 */     transform(source, fragment);
/* 115:159 */     return rv;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void transform(Node source, DomNode parent)
/* 119:    */   {
/* 120:163 */     Object result = transform(source);
/* 121:164 */     if ((result instanceof org.w3c.dom.Node))
/* 122:    */     {
/* 123:165 */       SgmlPage parentPage = parent.getPage();
/* 124:166 */       NodeList children = ((org.w3c.dom.Node)result).getChildNodes();
/* 125:167 */       for (int i = 0; i < children.getLength(); i++) {
/* 126:168 */         XmlUtil.appendChild(parentPage, parent, children.item(i));
/* 127:    */       }
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:172 */       DomText text = new DomText(parent.getPage(), (String)result);
/* 132:173 */       parent.appendChild(text);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void jsxFunction_setParameter(String namespaceURI, String localName, Object value)
/* 137:    */   {
/* 138:185 */     this.parameters_.put(getQualifiedName(namespaceURI, localName), value);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Object jsxFunction_getParameter(String namespaceURI, String localName)
/* 142:    */   {
/* 143:195 */     return this.parameters_.get(getQualifiedName(namespaceURI, localName));
/* 144:    */   }
/* 145:    */   
/* 146:    */   private String getQualifiedName(String namespaceURI, String localName)
/* 147:    */   {
/* 148:    */     String qualifiedName;
/* 149:    */     String qualifiedName;
/* 150:200 */     if ((namespaceURI != null) && (namespaceURI.length() != 0) && (!"null".equals(namespaceURI))) {
/* 151:201 */       qualifiedName = '{' + namespaceURI + '}' + localName;
/* 152:    */     } else {
/* 153:204 */       qualifiedName = localName;
/* 154:    */     }
/* 155:206 */     return qualifiedName;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void jsxSet_input(Node input)
/* 159:    */   {
/* 160:214 */     this.input_ = input;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Node jsxGet_input()
/* 164:    */   {
/* 165:222 */     return this.input_;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void jsxSet_output(Object output)
/* 169:    */   {
/* 170:230 */     this.output_ = output;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Object jsxGet_output()
/* 174:    */   {
/* 175:238 */     return this.output_;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void jsxFunction_addParameter(String baseName, Object parameter, Object namespaceURI)
/* 179:    */   {
/* 180:    */     String nsString;
/* 181:    */     String nsString;
/* 182:251 */     if ((namespaceURI instanceof String)) {
/* 183:252 */       nsString = (String)namespaceURI;
/* 184:    */     } else {
/* 185:255 */       nsString = null;
/* 186:    */     }
/* 187:257 */     jsxFunction_setParameter(nsString, baseName, parameter);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void jsxFunction_transform()
/* 191:    */   {
/* 192:264 */     Node input = this.input_;
/* 193:265 */     SgmlPage page = input.getDomNodeOrDie().getPage();
/* 194:267 */     if ((this.output_ == null) || (!(this.output_ instanceof Node)))
/* 195:    */     {
/* 196:268 */       DomDocumentFragment fragment = page.createDomDocumentFragment();
/* 197:269 */       DocumentFragment node = new DocumentFragment();
/* 198:270 */       node.setParentScope(getParentScope());
/* 199:271 */       node.setPrototype(getPrototype(node.getClass()));
/* 200:272 */       node.setDomNode(fragment);
/* 201:273 */       this.output_ = fragment.getScriptObject();
/* 202:    */     }
/* 203:276 */     transform(this.input_, ((Node)this.output_).getDomNodeOrDie());
/* 204:277 */     XMLSerializer serializer = new XMLSerializer();
/* 205:278 */     serializer.setParentScope(getParentScope());
/* 206:279 */     StringBuilder output = new StringBuilder();
/* 207:280 */     for (DomNode child : ((Node)this.output_).getDomNodeOrDie().getChildren()) {
/* 208:281 */       if ((child instanceof DomText))
/* 209:    */       {
/* 210:285 */         if (StringUtils.isNotBlank(((DomText)child).getData())) {
/* 211:286 */           output.append(((DomText)child).getData());
/* 212:    */         }
/* 213:    */       }
/* 214:    */       else
/* 215:    */       {
/* 216:291 */         String serializedString = serializer.jsxFunction_serializeToString((Node)child.getScriptObject());
/* 217:    */         
/* 218:293 */         output.append(serializedString.substring(0, serializedString.length() - 2));
/* 219:    */       }
/* 220:    */     }
/* 221:296 */     this.output_ = output.toString();
/* 222:    */   }
/* 223:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.XSLTProcessor
 * JD-Core Version:    0.7.0.1
 */