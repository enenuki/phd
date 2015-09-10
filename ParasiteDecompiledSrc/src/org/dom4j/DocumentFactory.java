/*   1:    */ package org.dom4j;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.dom4j.rule.Pattern;
/*  10:    */ import org.dom4j.tree.AbstractDocument;
/*  11:    */ import org.dom4j.tree.DefaultAttribute;
/*  12:    */ import org.dom4j.tree.DefaultCDATA;
/*  13:    */ import org.dom4j.tree.DefaultComment;
/*  14:    */ import org.dom4j.tree.DefaultDocument;
/*  15:    */ import org.dom4j.tree.DefaultDocumentType;
/*  16:    */ import org.dom4j.tree.DefaultElement;
/*  17:    */ import org.dom4j.tree.DefaultEntity;
/*  18:    */ import org.dom4j.tree.DefaultProcessingInstruction;
/*  19:    */ import org.dom4j.tree.DefaultText;
/*  20:    */ import org.dom4j.tree.QNameCache;
/*  21:    */ import org.dom4j.util.SimpleSingleton;
/*  22:    */ import org.dom4j.util.SingletonStrategy;
/*  23:    */ import org.dom4j.xpath.DefaultXPath;
/*  24:    */ import org.dom4j.xpath.XPathPattern;
/*  25:    */ import org.jaxen.VariableContext;
/*  26:    */ 
/*  27:    */ public class DocumentFactory
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30: 48 */   private static SingletonStrategy singleton = null;
/*  31:    */   protected transient QNameCache cache;
/*  32:    */   private Map xpathNamespaceURIs;
/*  33:    */   
/*  34:    */   private static SingletonStrategy createSingleton()
/*  35:    */   {
/*  36: 56 */     SingletonStrategy result = null;
/*  37:    */     String documentFactoryClassName;
/*  38:    */     try
/*  39:    */     {
/*  40: 60 */       documentFactoryClassName = System.getProperty("org.dom4j.factory", "org.dom4j.DocumentFactory");
/*  41:    */     }
/*  42:    */     catch (Exception e)
/*  43:    */     {
/*  44:    */       String documentFactoryClassName;
/*  45: 63 */       documentFactoryClassName = "org.dom4j.DocumentFactory";
/*  46:    */     }
/*  47:    */     try
/*  48:    */     {
/*  49: 67 */       String singletonClass = System.getProperty("org.dom4j.DocumentFactory.singleton.strategy", "org.dom4j.util.SimpleSingleton");
/*  50:    */       
/*  51:    */ 
/*  52: 70 */       Class clazz = Class.forName(singletonClass);
/*  53: 71 */       result = (SingletonStrategy)clazz.newInstance();
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 73 */       result = new SimpleSingleton();
/*  58:    */     }
/*  59: 76 */     result.setSingletonClassName(documentFactoryClassName);
/*  60:    */     
/*  61: 78 */     return result;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public DocumentFactory()
/*  65:    */   {
/*  66: 82 */     init();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static synchronized DocumentFactory getInstance()
/*  70:    */   {
/*  71: 94 */     if (singleton == null) {
/*  72: 95 */       singleton = createSingleton();
/*  73:    */     }
/*  74: 97 */     return (DocumentFactory)singleton.instance();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Document createDocument()
/*  78:    */   {
/*  79:102 */     DefaultDocument answer = new DefaultDocument();
/*  80:103 */     answer.setDocumentFactory(this);
/*  81:    */     
/*  82:105 */     return answer;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Document createDocument(String encoding)
/*  86:    */   {
/*  87:122 */     Document answer = createDocument();
/*  88:124 */     if ((answer instanceof AbstractDocument)) {
/*  89:125 */       ((AbstractDocument)answer).setXMLEncoding(encoding);
/*  90:    */     }
/*  91:128 */     return answer;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Document createDocument(Element rootElement)
/*  95:    */   {
/*  96:132 */     Document answer = createDocument();
/*  97:133 */     answer.setRootElement(rootElement);
/*  98:    */     
/*  99:135 */     return answer;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public DocumentType createDocType(String name, String publicId, String systemId)
/* 103:    */   {
/* 104:140 */     return new DefaultDocumentType(name, publicId, systemId);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Element createElement(QName qname)
/* 108:    */   {
/* 109:144 */     return new DefaultElement(qname);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Element createElement(String name)
/* 113:    */   {
/* 114:148 */     return createElement(createQName(name));
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Element createElement(String qualifiedName, String namespaceURI)
/* 118:    */   {
/* 119:152 */     return createElement(createQName(qualifiedName, namespaceURI));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Attribute createAttribute(Element owner, QName qname, String value)
/* 123:    */   {
/* 124:156 */     return new DefaultAttribute(qname, value);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Attribute createAttribute(Element owner, String name, String value)
/* 128:    */   {
/* 129:160 */     return createAttribute(owner, createQName(name), value);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public CDATA createCDATA(String text)
/* 133:    */   {
/* 134:164 */     return new DefaultCDATA(text);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Comment createComment(String text)
/* 138:    */   {
/* 139:168 */     return new DefaultComment(text);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Text createText(String text)
/* 143:    */   {
/* 144:172 */     if (text == null)
/* 145:    */     {
/* 146:173 */       String msg = "Adding text to an XML document must not be null";
/* 147:174 */       throw new IllegalArgumentException(msg);
/* 148:    */     }
/* 149:177 */     return new DefaultText(text);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Entity createEntity(String name, String text)
/* 153:    */   {
/* 154:181 */     return new DefaultEntity(name, text);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Namespace createNamespace(String prefix, String uri)
/* 158:    */   {
/* 159:185 */     return Namespace.get(prefix, uri);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public ProcessingInstruction createProcessingInstruction(String target, String data)
/* 163:    */   {
/* 164:190 */     return new DefaultProcessingInstruction(target, data);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public ProcessingInstruction createProcessingInstruction(String target, Map data)
/* 168:    */   {
/* 169:195 */     return new DefaultProcessingInstruction(target, data);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public QName createQName(String localName, Namespace namespace)
/* 173:    */   {
/* 174:199 */     return this.cache.get(localName, namespace);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public QName createQName(String localName)
/* 178:    */   {
/* 179:203 */     return this.cache.get(localName);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public QName createQName(String name, String prefix, String uri)
/* 183:    */   {
/* 184:207 */     return this.cache.get(name, Namespace.get(prefix, uri));
/* 185:    */   }
/* 186:    */   
/* 187:    */   public QName createQName(String qualifiedName, String uri)
/* 188:    */   {
/* 189:211 */     return this.cache.get(qualifiedName, uri);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public XPath createXPath(String xpathExpression)
/* 193:    */     throws InvalidXPathException
/* 194:    */   {
/* 195:230 */     DefaultXPath xpath = new DefaultXPath(xpathExpression);
/* 196:232 */     if (this.xpathNamespaceURIs != null) {
/* 197:233 */       xpath.setNamespaceURIs(this.xpathNamespaceURIs);
/* 198:    */     }
/* 199:236 */     return xpath;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public XPath createXPath(String xpathExpression, VariableContext variableContext)
/* 203:    */   {
/* 204:254 */     XPath xpath = createXPath(xpathExpression);
/* 205:255 */     xpath.setVariableContext(variableContext);
/* 206:    */     
/* 207:257 */     return xpath;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public NodeFilter createXPathFilter(String xpathFilterExpression, VariableContext variableContext)
/* 211:    */   {
/* 212:276 */     XPath answer = createXPath(xpathFilterExpression);
/* 213:    */     
/* 214:    */ 
/* 215:279 */     answer.setVariableContext(variableContext);
/* 216:    */     
/* 217:281 */     return answer;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public NodeFilter createXPathFilter(String xpathFilterExpression)
/* 221:    */   {
/* 222:297 */     return createXPath(xpathFilterExpression);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Pattern createPattern(String xpathPattern)
/* 226:    */   {
/* 227:315 */     return new XPathPattern(xpathPattern);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List getQNames()
/* 231:    */   {
/* 232:328 */     return this.cache.getQNames();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Map getXPathNamespaceURIs()
/* 236:    */   {
/* 237:341 */     return this.xpathNamespaceURIs;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setXPathNamespaceURIs(Map namespaceURIs)
/* 241:    */   {
/* 242:353 */     this.xpathNamespaceURIs = namespaceURIs;
/* 243:    */   }
/* 244:    */   
/* 245:    */   protected static DocumentFactory createSingleton(String className)
/* 246:    */   {
/* 247:    */     try
/* 248:    */     {
/* 249:375 */       Class theClass = Class.forName(className, true, DocumentFactory.class.getClassLoader());
/* 250:    */       
/* 251:    */ 
/* 252:378 */       return (DocumentFactory)theClass.newInstance();
/* 253:    */     }
/* 254:    */     catch (Throwable e)
/* 255:    */     {
/* 256:380 */       System.out.println("WARNING: Cannot load DocumentFactory: " + className);
/* 257:    */     }
/* 258:383 */     return new DocumentFactory();
/* 259:    */   }
/* 260:    */   
/* 261:    */   protected QName intern(QName qname)
/* 262:    */   {
/* 263:397 */     return this.cache.intern(qname);
/* 264:    */   }
/* 265:    */   
/* 266:    */   protected QNameCache createQNameCache()
/* 267:    */   {
/* 268:407 */     return new QNameCache(this);
/* 269:    */   }
/* 270:    */   
/* 271:    */   private void readObject(ObjectInputStream in)
/* 272:    */     throws IOException, ClassNotFoundException
/* 273:    */   {
/* 274:412 */     in.defaultReadObject();
/* 275:413 */     init();
/* 276:    */   }
/* 277:    */   
/* 278:    */   protected void init()
/* 279:    */   {
/* 280:417 */     this.cache = createQNameCache();
/* 281:    */   }
/* 282:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.DocumentFactory
 * JD-Core Version:    0.7.0.1
 */