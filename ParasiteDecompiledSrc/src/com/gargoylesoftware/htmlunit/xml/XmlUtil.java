/*   1:    */ package com.gargoylesoftware.htmlunit.xml;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomCDataSection;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomComment;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentType;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.DomProcessingInstruction;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.IElementFactory;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.io.StringReader;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.Map;
/*  20:    */ import java.util.Map.Entry;
/*  21:    */ import javax.xml.parsers.DocumentBuilder;
/*  22:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  23:    */ import javax.xml.parsers.ParserConfigurationException;
/*  24:    */ import org.apache.commons.logging.Log;
/*  25:    */ import org.apache.commons.logging.LogFactory;
/*  26:    */ import org.w3c.dom.Attr;
/*  27:    */ import org.w3c.dom.Document;
/*  28:    */ import org.w3c.dom.DocumentType;
/*  29:    */ import org.w3c.dom.NamedNodeMap;
/*  30:    */ import org.w3c.dom.Node;
/*  31:    */ import org.w3c.dom.NodeList;
/*  32:    */ import org.xml.sax.Attributes;
/*  33:    */ import org.xml.sax.EntityResolver;
/*  34:    */ import org.xml.sax.ErrorHandler;
/*  35:    */ import org.xml.sax.InputSource;
/*  36:    */ import org.xml.sax.SAXException;
/*  37:    */ import org.xml.sax.SAXParseException;
/*  38:    */ import org.xml.sax.helpers.AttributesImpl;
/*  39:    */ 
/*  40:    */ public final class XmlUtil
/*  41:    */ {
/*  42: 68 */   private static final Log LOG = LogFactory.getLog(XmlUtil.class);
/*  43: 70 */   private static final ErrorHandler DISCARD_MESSAGES_HANDLER = new ErrorHandler()
/*  44:    */   {
/*  45:    */     public void error(SAXParseException exception) {}
/*  46:    */     
/*  47:    */     public void fatalError(SAXParseException exception) {}
/*  48:    */     
/*  49:    */     public void warning(SAXParseException exception) {}
/*  50:    */   };
/*  51:    */   
/*  52:    */   public static Document buildDocument(WebResponse webResponse)
/*  53:    */     throws IOException, SAXException, ParserConfigurationException
/*  54:    */   {
/*  55:112 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  56:113 */     factory.setNamespaceAware(true);
/*  57:114 */     InputSource source = new InputSource(new StringReader(webResponse.getContentAsString()));
/*  58:115 */     DocumentBuilder builder = factory.newDocumentBuilder();
/*  59:116 */     builder.setErrorHandler(DISCARD_MESSAGES_HANDLER);
/*  60:117 */     builder.setEntityResolver(new EntityResolver()
/*  61:    */     {
/*  62:    */       public InputSource resolveEntity(String publicId, String systemId)
/*  63:    */         throws SAXException, IOException
/*  64:    */       {
/*  65:120 */         return new InputSource(new StringReader(""));
/*  66:    */       }
/*  67:122 */     });
/*  68:123 */     return builder.parse(source);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void appendChild(SgmlPage page, DomNode parent, Node child)
/*  72:    */   {
/*  73:134 */     DocumentType documentType = child.getOwnerDocument().getDoctype();
/*  74:135 */     if ((documentType != null) && ((page instanceof XmlPage)))
/*  75:    */     {
/*  76:136 */       DomDocumentType domDoctype = new DomDocumentType(page, documentType.getName(), documentType.getPublicId(), documentType.getSystemId());
/*  77:    */       
/*  78:138 */       ((XmlPage)page).setDocumentType(domDoctype);
/*  79:    */     }
/*  80:140 */     DomNode childXml = createFrom(page, child);
/*  81:141 */     parent.appendChild(childXml);
/*  82:142 */     copy(page, child, childXml);
/*  83:    */   }
/*  84:    */   
/*  85:    */   private static DomNode createFrom(SgmlPage page, Node source)
/*  86:    */   {
/*  87:146 */     if (source.getNodeType() == 3) {
/*  88:147 */       return new DomText(page, source.getNodeValue());
/*  89:    */     }
/*  90:149 */     String ns = source.getNamespaceURI();
/*  91:150 */     String localName = source.getLocalName();
/*  92:151 */     if ("http://www.w3.org/1999/xhtml".equals(ns))
/*  93:    */     {
/*  94:152 */       IElementFactory factory = HTMLParser.getFactory(localName);
/*  95:153 */       return factory.createElementNS(page, ns, localName, namedNodeMapToSaxAttributes(source.getAttributes()));
/*  96:    */     }
/*  97:155 */     Map<String, DomAttr> attributes = new HashMap();
/*  98:156 */     NamedNodeMap nodeAttributes = source.getAttributes();
/*  99:157 */     for (int i = 0; i < nodeAttributes.getLength(); i++)
/* 100:    */     {
/* 101:158 */       Attr attribute = (Attr)nodeAttributes.item(i);
/* 102:159 */       String namespaceURI = attribute.getNamespaceURI();
/* 103:    */       String qualifiedName;
/* 104:    */       String qualifiedName;
/* 105:161 */       if (attribute.getPrefix() != null) {
/* 106:162 */         qualifiedName = attribute.getPrefix() + ':' + attribute.getLocalName();
/* 107:    */       } else {
/* 108:165 */         qualifiedName = attribute.getLocalName();
/* 109:    */       }
/* 110:167 */       String value = attribute.getNodeValue();
/* 111:168 */       boolean specified = attribute.getSpecified();
/* 112:169 */       DomAttr xmlAttribute = new DomAttr(page, namespaceURI, qualifiedName, value, specified);
/* 113:170 */       attributes.put(attribute.getNodeName(), xmlAttribute);
/* 114:    */     }
/* 115:172 */     if ((page instanceof HtmlPage)) {
/* 116:173 */       localName = localName.toUpperCase();
/* 117:    */     }
/* 118:    */     String qualifiedName;
/* 119:    */     String qualifiedName;
/* 120:176 */     if (source.getPrefix() == null) {
/* 121:177 */       qualifiedName = localName;
/* 122:    */     } else {
/* 123:180 */       qualifiedName = source.getPrefix() + ':' + localName;
/* 124:    */     }
/* 125:182 */     return new DomElement(source.getNamespaceURI(), qualifiedName, page, attributes);
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static Attributes namedNodeMapToSaxAttributes(NamedNodeMap attributesMap)
/* 129:    */   {
/* 130:186 */     AttributesImpl attributes = new AttributesImpl();
/* 131:187 */     int length = attributesMap.getLength();
/* 132:188 */     for (int i = 0; i < length; i++)
/* 133:    */     {
/* 134:189 */       Node attr = attributesMap.item(i);
/* 135:190 */       attributes.addAttribute(attr.getNamespaceURI(), attr.getLocalName(), attr.getNodeName(), null, attr.getNodeValue());
/* 136:    */     }
/* 137:194 */     return attributes;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private static void copy(SgmlPage page, Node source, DomNode dest)
/* 141:    */   {
/* 142:204 */     NodeList nodeChildren = source.getChildNodes();
/* 143:205 */     for (int i = 0; i < nodeChildren.getLength(); i++)
/* 144:    */     {
/* 145:206 */       Node child = nodeChildren.item(i);
/* 146:207 */       switch (child.getNodeType())
/* 147:    */       {
/* 148:    */       case 1: 
/* 149:209 */         DomNode childXml = createFrom(page, child);
/* 150:210 */         dest.appendChild(childXml);
/* 151:211 */         copy(page, child, childXml);
/* 152:212 */         break;
/* 153:    */       case 3: 
/* 154:215 */         dest.appendChild(new DomText(page, child.getNodeValue()));
/* 155:216 */         break;
/* 156:    */       case 4: 
/* 157:219 */         dest.appendChild(new DomCDataSection(page, child.getNodeValue()));
/* 158:220 */         break;
/* 159:    */       case 8: 
/* 160:223 */         dest.appendChild(new DomComment(page, child.getNodeValue()));
/* 161:224 */         break;
/* 162:    */       case 7: 
/* 163:227 */         dest.appendChild(new DomProcessingInstruction(page, child.getNodeName(), child.getNodeValue()));
/* 164:228 */         break;
/* 165:    */       case 2: 
/* 166:    */       case 5: 
/* 167:    */       case 6: 
/* 168:    */       default: 
/* 169:231 */         LOG.warn("NodeType " + child.getNodeType() + " (" + child.getNodeName() + ") is not yet supported.");
/* 170:    */       }
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static String lookupNamespaceURI(DomElement element, String prefix)
/* 175:    */   {
/* 176:244 */     String uri = element.getAttribute("xmlns:" + prefix);
/* 177:245 */     if (uri == DomElement.ATTRIBUTE_NOT_DEFINED)
/* 178:    */     {
/* 179:246 */       DomNode parentNode = element.getParentNode();
/* 180:247 */       if ((parentNode instanceof DomElement)) {
/* 181:248 */         uri = lookupNamespaceURI((DomElement)parentNode, prefix);
/* 182:    */       }
/* 183:    */     }
/* 184:251 */     return uri;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public static String lookupPrefix(DomElement element, String namespace)
/* 188:    */   {
/* 189:261 */     Map<String, DomAttr> attributes = element.getAttributesMap();
/* 190:262 */     for (Map.Entry<String, DomAttr> entry : attributes.entrySet())
/* 191:    */     {
/* 192:263 */       String name = (String)entry.getKey();
/* 193:264 */       DomAttr value = (DomAttr)entry.getValue();
/* 194:265 */       if ((name.startsWith("xmlns:")) && (value.getValue().equals(namespace))) {
/* 195:266 */         return name.substring(6);
/* 196:    */       }
/* 197:    */     }
/* 198:269 */     for (DomNode child : element.getChildren()) {
/* 199:270 */       if ((child instanceof DomElement))
/* 200:    */       {
/* 201:271 */         String prefix = lookupPrefix((DomElement)child, namespace);
/* 202:272 */         if (prefix != null) {
/* 203:273 */           return prefix;
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:277 */     return null;
/* 208:    */   }
/* 209:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.xml.XmlUtil
 * JD-Core Version:    0.7.0.1
 */