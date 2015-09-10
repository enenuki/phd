/*   1:    */ package org.apache.xpath.jaxp;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import javax.xml.namespace.NamespaceContext;
/*   5:    */ import javax.xml.namespace.QName;
/*   6:    */ import javax.xml.parsers.DocumentBuilder;
/*   7:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   8:    */ import javax.xml.parsers.ParserConfigurationException;
/*   9:    */ import javax.xml.transform.TransformerException;
/*  10:    */ import javax.xml.xpath.XPathConstants;
/*  11:    */ import javax.xml.xpath.XPathExpression;
/*  12:    */ import javax.xml.xpath.XPathExpressionException;
/*  13:    */ import javax.xml.xpath.XPathFunctionException;
/*  14:    */ import javax.xml.xpath.XPathFunctionResolver;
/*  15:    */ import javax.xml.xpath.XPathVariableResolver;
/*  16:    */ import org.apache.xpath.XPathContext;
/*  17:    */ import org.apache.xpath.objects.XObject;
/*  18:    */ import org.apache.xpath.res.XPATHMessages;
/*  19:    */ import org.w3c.dom.DOMImplementation;
/*  20:    */ import org.w3c.dom.Document;
/*  21:    */ import org.w3c.dom.Node;
/*  22:    */ import org.w3c.dom.traversal.NodeIterator;
/*  23:    */ import org.xml.sax.InputSource;
/*  24:    */ import org.xml.sax.SAXException;
/*  25:    */ 
/*  26:    */ public class XPathImpl
/*  27:    */   implements javax.xml.xpath.XPath
/*  28:    */ {
/*  29:    */   private XPathVariableResolver variableResolver;
/*  30:    */   private XPathFunctionResolver functionResolver;
/*  31:    */   private XPathVariableResolver origVariableResolver;
/*  32:    */   private XPathFunctionResolver origFunctionResolver;
/*  33: 64 */   private NamespaceContext namespaceContext = null;
/*  34:    */   private JAXPPrefixResolver prefixResolver;
/*  35: 69 */   private boolean featureSecureProcessing = false;
/*  36:    */   
/*  37:    */   XPathImpl(XPathVariableResolver vr, XPathFunctionResolver fr)
/*  38:    */   {
/*  39: 72 */     this.origVariableResolver = (this.variableResolver = vr);
/*  40: 73 */     this.origFunctionResolver = (this.functionResolver = fr);
/*  41:    */   }
/*  42:    */   
/*  43:    */   XPathImpl(XPathVariableResolver vr, XPathFunctionResolver fr, boolean featureSecureProcessing)
/*  44:    */   {
/*  45: 78 */     this.origVariableResolver = (this.variableResolver = vr);
/*  46: 79 */     this.origFunctionResolver = (this.functionResolver = fr);
/*  47: 80 */     this.featureSecureProcessing = featureSecureProcessing;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setXPathVariableResolver(XPathVariableResolver resolver)
/*  51:    */   {
/*  52: 89 */     if (resolver == null)
/*  53:    */     {
/*  54: 90 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPathVariableResolver" });
/*  55:    */       
/*  56:    */ 
/*  57: 93 */       throw new NullPointerException(fmsg);
/*  58:    */     }
/*  59: 95 */     this.variableResolver = resolver;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public XPathVariableResolver getXPathVariableResolver()
/*  63:    */   {
/*  64:104 */     return this.variableResolver;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setXPathFunctionResolver(XPathFunctionResolver resolver)
/*  68:    */   {
/*  69:113 */     if (resolver == null)
/*  70:    */     {
/*  71:114 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPathFunctionResolver" });
/*  72:    */       
/*  73:    */ 
/*  74:117 */       throw new NullPointerException(fmsg);
/*  75:    */     }
/*  76:119 */     this.functionResolver = resolver;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public XPathFunctionResolver getXPathFunctionResolver()
/*  80:    */   {
/*  81:128 */     return this.functionResolver;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setNamespaceContext(NamespaceContext nsContext)
/*  85:    */   {
/*  86:137 */     if (nsContext == null)
/*  87:    */     {
/*  88:138 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "NamespaceContext" });
/*  89:    */       
/*  90:    */ 
/*  91:141 */       throw new NullPointerException(fmsg);
/*  92:    */     }
/*  93:143 */     this.namespaceContext = nsContext;
/*  94:144 */     this.prefixResolver = new JAXPPrefixResolver(nsContext);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public NamespaceContext getNamespaceContext()
/*  98:    */   {
/*  99:153 */     return this.namespaceContext;
/* 100:    */   }
/* 101:    */   
/* 102:156 */   private static Document d = null;
/* 103:    */   
/* 104:    */   private static DocumentBuilder getParser()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:171 */       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 109:172 */       dbf.setNamespaceAware(true);
/* 110:173 */       dbf.setValidating(false);
/* 111:174 */       return dbf.newDocumentBuilder();
/* 112:    */     }
/* 113:    */     catch (ParserConfigurationException e)
/* 114:    */     {
/* 115:177 */       throw new Error(e.toString());
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private static Document getDummyDocument()
/* 120:    */   {
/* 121:184 */     if (d == null)
/* 122:    */     {
/* 123:185 */       DOMImplementation dim = getParser().getDOMImplementation();
/* 124:186 */       d = dim.createDocument("http://java.sun.com/jaxp/xpath", "dummyroot", null);
/* 125:    */     }
/* 126:189 */     return d;
/* 127:    */   }
/* 128:    */   
/* 129:    */   private XObject eval(String expression, Object contextItem)
/* 130:    */     throws TransformerException
/* 131:    */   {
/* 132:195 */     org.apache.xpath.XPath xpath = new org.apache.xpath.XPath(expression, null, this.prefixResolver, 0);
/* 133:    */     
/* 134:197 */     XPathContext xpathSupport = null;
/* 135:202 */     if (this.functionResolver != null)
/* 136:    */     {
/* 137:203 */       JAXPExtensionsProvider jep = new JAXPExtensionsProvider(this.functionResolver, this.featureSecureProcessing);
/* 138:    */       
/* 139:205 */       xpathSupport = new XPathContext(jep, false);
/* 140:    */     }
/* 141:    */     else
/* 142:    */     {
/* 143:207 */       xpathSupport = new XPathContext(false);
/* 144:    */     }
/* 145:210 */     XObject xobj = null;
/* 146:    */     
/* 147:212 */     xpathSupport.setVarStack(new JAXPVariableStack(this.variableResolver));
/* 148:215 */     if ((contextItem instanceof Node)) {
/* 149:216 */       xobj = xpath.execute(xpathSupport, (Node)contextItem, this.prefixResolver);
/* 150:    */     } else {
/* 151:219 */       xobj = xpath.execute(xpathSupport, -1, this.prefixResolver);
/* 152:    */     }
/* 153:222 */     return xobj;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Object evaluate(String expression, Object item, QName returnType)
/* 157:    */     throws XPathExpressionException
/* 158:    */   {
/* 159:258 */     if (expression == null)
/* 160:    */     {
/* 161:259 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPath expression" });
/* 162:    */       
/* 163:    */ 
/* 164:262 */       throw new NullPointerException(fmsg);
/* 165:    */     }
/* 166:264 */     if (returnType == null)
/* 167:    */     {
/* 168:265 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "returnType" });
/* 169:    */       
/* 170:    */ 
/* 171:268 */       throw new NullPointerException(fmsg);
/* 172:    */     }
/* 173:272 */     if (!isSupported(returnType))
/* 174:    */     {
/* 175:273 */       String fmsg = XPATHMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType.toString() });
/* 176:    */       
/* 177:    */ 
/* 178:276 */       throw new IllegalArgumentException(fmsg);
/* 179:    */     }
/* 180:    */     try
/* 181:    */     {
/* 182:281 */       XObject resultObject = eval(expression, item);
/* 183:282 */       return getResultAsType(resultObject, returnType);
/* 184:    */     }
/* 185:    */     catch (NullPointerException npe)
/* 186:    */     {
/* 187:287 */       throw new XPathExpressionException(npe);
/* 188:    */     }
/* 189:    */     catch (TransformerException te)
/* 190:    */     {
/* 191:289 */       Throwable nestedException = te.getException();
/* 192:290 */       if ((nestedException instanceof XPathFunctionException)) {
/* 193:291 */         throw ((XPathFunctionException)nestedException);
/* 194:    */       }
/* 195:295 */       throw new XPathExpressionException(te);
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   private boolean isSupported(QName returnType)
/* 200:    */   {
/* 201:302 */     if ((returnType.equals(XPathConstants.STRING)) || (returnType.equals(XPathConstants.NUMBER)) || (returnType.equals(XPathConstants.BOOLEAN)) || (returnType.equals(XPathConstants.NODE)) || (returnType.equals(XPathConstants.NODESET))) {
/* 202:308 */       return true;
/* 203:    */     }
/* 204:310 */     return false;
/* 205:    */   }
/* 206:    */   
/* 207:    */   private Object getResultAsType(XObject resultObject, QName returnType)
/* 208:    */     throws TransformerException
/* 209:    */   {
/* 210:316 */     if (returnType.equals(XPathConstants.STRING)) {
/* 211:317 */       return resultObject.str();
/* 212:    */     }
/* 213:320 */     if (returnType.equals(XPathConstants.NUMBER)) {
/* 214:321 */       return new Double(resultObject.num());
/* 215:    */     }
/* 216:324 */     if (returnType.equals(XPathConstants.BOOLEAN)) {
/* 217:325 */       return new Boolean(resultObject.bool());
/* 218:    */     }
/* 219:328 */     if (returnType.equals(XPathConstants.NODESET)) {
/* 220:329 */       return resultObject.nodelist();
/* 221:    */     }
/* 222:332 */     if (returnType.equals(XPathConstants.NODE))
/* 223:    */     {
/* 224:333 */       NodeIterator ni = resultObject.nodeset();
/* 225:    */       
/* 226:335 */       return ni.nextNode();
/* 227:    */     }
/* 228:337 */     String fmsg = XPATHMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType.toString() });
/* 229:    */     
/* 230:    */ 
/* 231:340 */     throw new IllegalArgumentException(fmsg);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String evaluate(String expression, Object item)
/* 235:    */     throws XPathExpressionException
/* 236:    */   {
/* 237:371 */     return (String)evaluate(expression, item, XPathConstants.STRING);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public XPathExpression compile(String expression)
/* 241:    */     throws XPathExpressionException
/* 242:    */   {
/* 243:393 */     if (expression == null)
/* 244:    */     {
/* 245:394 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPath expression" });
/* 246:    */       
/* 247:    */ 
/* 248:397 */       throw new NullPointerException(fmsg);
/* 249:    */     }
/* 250:    */     try
/* 251:    */     {
/* 252:400 */       org.apache.xpath.XPath xpath = new org.apache.xpath.XPath(expression, null, this.prefixResolver, 0);
/* 253:    */       
/* 254:    */ 
/* 255:403 */       return new XPathExpressionImpl(xpath, this.prefixResolver, this.functionResolver, this.variableResolver, this.featureSecureProcessing);
/* 256:    */     }
/* 257:    */     catch (TransformerException te)
/* 258:    */     {
/* 259:408 */       throw new XPathExpressionException(te);
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Object evaluate(String expression, InputSource source, QName returnType)
/* 264:    */     throws XPathExpressionException
/* 265:    */   {
/* 266:444 */     if (source == null)
/* 267:    */     {
/* 268:445 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "source" });
/* 269:    */       
/* 270:    */ 
/* 271:448 */       throw new NullPointerException(fmsg);
/* 272:    */     }
/* 273:450 */     if (expression == null)
/* 274:    */     {
/* 275:451 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "XPath expression" });
/* 276:    */       
/* 277:    */ 
/* 278:454 */       throw new NullPointerException(fmsg);
/* 279:    */     }
/* 280:456 */     if (returnType == null)
/* 281:    */     {
/* 282:457 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "returnType" });
/* 283:    */       
/* 284:    */ 
/* 285:460 */       throw new NullPointerException(fmsg);
/* 286:    */     }
/* 287:465 */     if (!isSupported(returnType))
/* 288:    */     {
/* 289:466 */       String fmsg = XPATHMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType.toString() });
/* 290:    */       
/* 291:    */ 
/* 292:469 */       throw new IllegalArgumentException(fmsg);
/* 293:    */     }
/* 294:    */     try
/* 295:    */     {
/* 296:474 */       Document document = getParser().parse(source);
/* 297:    */       
/* 298:476 */       XObject resultObject = eval(expression, document);
/* 299:477 */       return getResultAsType(resultObject, returnType);
/* 300:    */     }
/* 301:    */     catch (SAXException e)
/* 302:    */     {
/* 303:479 */       throw new XPathExpressionException(e);
/* 304:    */     }
/* 305:    */     catch (IOException e)
/* 306:    */     {
/* 307:481 */       throw new XPathExpressionException(e);
/* 308:    */     }
/* 309:    */     catch (TransformerException te)
/* 310:    */     {
/* 311:483 */       Throwable nestedException = te.getException();
/* 312:484 */       if ((nestedException instanceof XPathFunctionException)) {
/* 313:485 */         throw ((XPathFunctionException)nestedException);
/* 314:    */       }
/* 315:487 */       throw new XPathExpressionException(te);
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   public String evaluate(String expression, InputSource source)
/* 320:    */     throws XPathExpressionException
/* 321:    */   {
/* 322:521 */     return (String)evaluate(expression, source, XPathConstants.STRING);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void reset()
/* 326:    */   {
/* 327:540 */     this.variableResolver = this.origVariableResolver;
/* 328:541 */     this.functionResolver = this.origFunctionResolver;
/* 329:542 */     this.namespaceContext = null;
/* 330:    */   }
/* 331:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.jaxp.XPathImpl
 * JD-Core Version:    0.7.0.1
 */