/*   1:    */ package org.dom4j;
/*   2:    */ 
/*   3:    */ import java.io.StringReader;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import org.dom4j.io.SAXReader;
/*   8:    */ import org.dom4j.rule.Pattern;
/*   9:    */ import org.jaxen.VariableContext;
/*  10:    */ import org.xml.sax.InputSource;
/*  11:    */ 
/*  12:    */ public final class DocumentHelper
/*  13:    */ {
/*  14:    */   private static DocumentFactory getDocumentFactory()
/*  15:    */   {
/*  16: 36 */     return DocumentFactory.getInstance();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static Document createDocument()
/*  20:    */   {
/*  21: 41 */     return getDocumentFactory().createDocument();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static Document createDocument(Element rootElement)
/*  25:    */   {
/*  26: 45 */     return getDocumentFactory().createDocument(rootElement);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Element createElement(QName qname)
/*  30:    */   {
/*  31: 49 */     return getDocumentFactory().createElement(qname);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Element createElement(String name)
/*  35:    */   {
/*  36: 53 */     return getDocumentFactory().createElement(name);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Attribute createAttribute(Element owner, QName qname, String value)
/*  40:    */   {
/*  41: 58 */     return getDocumentFactory().createAttribute(owner, qname, value);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static Attribute createAttribute(Element owner, String name, String value)
/*  45:    */   {
/*  46: 63 */     return getDocumentFactory().createAttribute(owner, name, value);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static CDATA createCDATA(String text)
/*  50:    */   {
/*  51: 67 */     return DocumentFactory.getInstance().createCDATA(text);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static Comment createComment(String text)
/*  55:    */   {
/*  56: 71 */     return DocumentFactory.getInstance().createComment(text);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Text createText(String text)
/*  60:    */   {
/*  61: 75 */     return DocumentFactory.getInstance().createText(text);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Entity createEntity(String name, String text)
/*  65:    */   {
/*  66: 79 */     return DocumentFactory.getInstance().createEntity(name, text);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Namespace createNamespace(String prefix, String uri)
/*  70:    */   {
/*  71: 83 */     return DocumentFactory.getInstance().createNamespace(prefix, uri);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static ProcessingInstruction createProcessingInstruction(String pi, String d)
/*  75:    */   {
/*  76: 88 */     return getDocumentFactory().createProcessingInstruction(pi, d);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static ProcessingInstruction createProcessingInstruction(String pi, Map data)
/*  80:    */   {
/*  81: 93 */     return getDocumentFactory().createProcessingInstruction(pi, data);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static QName createQName(String localName, Namespace namespace)
/*  85:    */   {
/*  86: 97 */     return getDocumentFactory().createQName(localName, namespace);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static QName createQName(String localName)
/*  90:    */   {
/*  91:101 */     return getDocumentFactory().createQName(localName);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static XPath createXPath(String xpathExpression)
/*  95:    */     throws InvalidXPathException
/*  96:    */   {
/*  97:121 */     return getDocumentFactory().createXPath(xpathExpression);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static XPath createXPath(String xpathExpression, VariableContext context)
/* 101:    */     throws InvalidXPathException
/* 102:    */   {
/* 103:143 */     return getDocumentFactory().createXPath(xpathExpression, context);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static NodeFilter createXPathFilter(String xpathFilterExpression)
/* 107:    */   {
/* 108:160 */     return getDocumentFactory().createXPathFilter(xpathFilterExpression);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static Pattern createPattern(String xpathPattern)
/* 112:    */   {
/* 113:176 */     return getDocumentFactory().createPattern(xpathPattern);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static List selectNodes(String xpathFilterExpression, List nodes)
/* 117:    */   {
/* 118:194 */     XPath xpath = createXPath(xpathFilterExpression);
/* 119:    */     
/* 120:196 */     return xpath.selectNodes(nodes);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static List selectNodes(String xpathFilterExpression, Node node)
/* 124:    */   {
/* 125:214 */     XPath xpath = createXPath(xpathFilterExpression);
/* 126:    */     
/* 127:216 */     return xpath.selectNodes(node);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void sort(List list, String xpathExpression)
/* 131:    */   {
/* 132:231 */     XPath xpath = createXPath(xpathExpression);
/* 133:232 */     xpath.sort(list);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void sort(List list, String expression, boolean distinct)
/* 137:    */   {
/* 138:251 */     XPath xpath = createXPath(expression);
/* 139:252 */     xpath.sort(list, distinct);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static Document parseText(String text)
/* 143:    */     throws DocumentException
/* 144:    */   {
/* 145:270 */     Document result = null;
/* 146:    */     
/* 147:272 */     SAXReader reader = new SAXReader();
/* 148:273 */     String encoding = getEncoding(text);
/* 149:    */     
/* 150:275 */     InputSource source = new InputSource(new StringReader(text));
/* 151:276 */     source.setEncoding(encoding);
/* 152:    */     
/* 153:278 */     result = reader.read(source);
/* 154:282 */     if (result.getXMLEncoding() == null) {
/* 155:283 */       result.setXMLEncoding(encoding);
/* 156:    */     }
/* 157:286 */     return result;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private static String getEncoding(String text)
/* 161:    */   {
/* 162:290 */     String result = null;
/* 163:    */     
/* 164:292 */     String xml = text.trim();
/* 165:294 */     if (xml.startsWith("<?xml"))
/* 166:    */     {
/* 167:295 */       int end = xml.indexOf("?>");
/* 168:296 */       String sub = xml.substring(0, end);
/* 169:297 */       StringTokenizer tokens = new StringTokenizer(sub, " =\"'");
/* 170:299 */       while (tokens.hasMoreTokens())
/* 171:    */       {
/* 172:300 */         String token = tokens.nextToken();
/* 173:302 */         if ("encoding".equals(token))
/* 174:    */         {
/* 175:303 */           if (!tokens.hasMoreTokens()) {
/* 176:    */             break;
/* 177:    */           }
/* 178:304 */           result = tokens.nextToken(); break;
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:312 */     return result;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static Element makeElement(Branch source, String path)
/* 186:    */   {
/* 187:337 */     StringTokenizer tokens = new StringTokenizer(path, "/");
/* 188:    */     Element parent;
/* 189:340 */     if ((source instanceof Document))
/* 190:    */     {
/* 191:341 */       Document document = (Document)source;
/* 192:342 */       Element parent = document.getRootElement();
/* 193:    */       
/* 194:    */ 
/* 195:    */ 
/* 196:346 */       String name = tokens.nextToken();
/* 197:348 */       if (parent == null) {
/* 198:349 */         parent = document.addElement(name);
/* 199:    */       }
/* 200:    */     }
/* 201:    */     else
/* 202:    */     {
/* 203:352 */       parent = (Element)source;
/* 204:    */     }
/* 205:355 */     Element element = null;
/* 206:357 */     while (tokens.hasMoreTokens())
/* 207:    */     {
/* 208:358 */       String name = tokens.nextToken();
/* 209:360 */       if (name.indexOf(':') > 0) {
/* 210:361 */         element = parent.element(parent.getQName(name));
/* 211:    */       } else {
/* 212:363 */         element = parent.element(name);
/* 213:    */       }
/* 214:366 */       if (element == null) {
/* 215:367 */         element = parent.addElement(name);
/* 216:    */       }
/* 217:370 */       parent = element;
/* 218:    */     }
/* 219:373 */     return element;
/* 220:    */   }
/* 221:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.DocumentHelper
 * JD-Core Version:    0.7.0.1
 */