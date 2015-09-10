/*   1:    */ package org.apache.xpath.jaxp;
/*   2:    */ 
/*   3:    */ import javax.xml.namespace.QName;
/*   4:    */ import javax.xml.parsers.DocumentBuilder;
/*   5:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import javax.xml.xpath.XPathConstants;
/*   8:    */ import javax.xml.xpath.XPathExpression;
/*   9:    */ import javax.xml.xpath.XPathExpressionException;
/*  10:    */ import javax.xml.xpath.XPathFunctionException;
/*  11:    */ import javax.xml.xpath.XPathFunctionResolver;
/*  12:    */ import javax.xml.xpath.XPathVariableResolver;
/*  13:    */ import org.apache.xpath.XPath;
/*  14:    */ import org.apache.xpath.XPathContext;
/*  15:    */ import org.apache.xpath.objects.XObject;
/*  16:    */ import org.apache.xpath.res.XPATHMessages;
/*  17:    */ import org.w3c.dom.DOMImplementation;
/*  18:    */ import org.w3c.dom.Document;
/*  19:    */ import org.w3c.dom.Node;
/*  20:    */ import org.w3c.dom.traversal.NodeIterator;
/*  21:    */ import org.xml.sax.InputSource;
/*  22:    */ 
/*  23:    */ public class XPathExpressionImpl
/*  24:    */   implements XPathExpression
/*  25:    */ {
/*  26:    */   private XPathFunctionResolver functionResolver;
/*  27:    */   private XPathVariableResolver variableResolver;
/*  28:    */   private JAXPPrefixResolver prefixResolver;
/*  29:    */   private XPath xpath;
/*  30: 63 */   private boolean featureSecureProcessing = false;
/*  31:    */   
/*  32:    */   protected XPathExpressionImpl() {}
/*  33:    */   
/*  34:    */   protected XPathExpressionImpl(XPath xpath, JAXPPrefixResolver prefixResolver, XPathFunctionResolver functionResolver, XPathVariableResolver variableResolver)
/*  35:    */   {
/*  36: 74 */     this.xpath = xpath;
/*  37: 75 */     this.prefixResolver = prefixResolver;
/*  38: 76 */     this.functionResolver = functionResolver;
/*  39: 77 */     this.variableResolver = variableResolver;
/*  40: 78 */     this.featureSecureProcessing = false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected XPathExpressionImpl(XPath xpath, JAXPPrefixResolver prefixResolver, XPathFunctionResolver functionResolver, XPathVariableResolver variableResolver, boolean featureSecureProcessing)
/*  44:    */   {
/*  45: 86 */     this.xpath = xpath;
/*  46: 87 */     this.prefixResolver = prefixResolver;
/*  47: 88 */     this.functionResolver = functionResolver;
/*  48: 89 */     this.variableResolver = variableResolver;
/*  49: 90 */     this.featureSecureProcessing = featureSecureProcessing;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setXPath(XPath xpath)
/*  53:    */   {
/*  54: 94 */     this.xpath = xpath;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Object eval(Object item, QName returnType)
/*  58:    */     throws TransformerException
/*  59:    */   {
/*  60: 99 */     XObject resultObject = eval(item);
/*  61:100 */     return getResultAsType(resultObject, returnType);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private XObject eval(Object contextItem)
/*  65:    */     throws TransformerException
/*  66:    */   {
/*  67:105 */     XPathContext xpathSupport = null;
/*  68:110 */     if (this.functionResolver != null)
/*  69:    */     {
/*  70:111 */       JAXPExtensionsProvider jep = new JAXPExtensionsProvider(this.functionResolver, this.featureSecureProcessing);
/*  71:    */       
/*  72:113 */       xpathSupport = new XPathContext(jep, false);
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:115 */       xpathSupport = new XPathContext(false);
/*  77:    */     }
/*  78:118 */     xpathSupport.setVarStack(new JAXPVariableStack(this.variableResolver));
/*  79:119 */     XObject xobj = null;
/*  80:    */     
/*  81:121 */     Node contextNode = (Node)contextItem;
/*  82:125 */     if (contextNode == null) {
/*  83:126 */       contextNode = getDummyDocument();
/*  84:    */     }
/*  85:129 */     xobj = this.xpath.execute(xpathSupport, contextNode, this.prefixResolver);
/*  86:130 */     return xobj;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Object evaluate(Object item, QName returnType)
/*  90:    */     throws XPathExpressionException
/*  91:    */   {
/*  92:168 */     if (returnType == null)
/*  93:    */     {
/*  94:170 */       String fmsg = XPATHMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[] { "returnType" });
/*  95:    */       
/*  96:    */ 
/*  97:173 */       throw new NullPointerException(fmsg);
/*  98:    */     }
/*  99:177 */     if (!isSupported(returnType))
/* 100:    */     {
/* 101:178 */       String fmsg = XPATHMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType.toString() });
/* 102:    */       
/* 103:    */ 
/* 104:181 */       throw new IllegalArgumentException(fmsg);
/* 105:    */     }
/* 106:    */     try
/* 107:    */     {
/* 108:184 */       return eval(item, returnType);
/* 109:    */     }
/* 110:    */     catch (NullPointerException npe)
/* 111:    */     {
/* 112:189 */       throw new XPathExpressionException(npe);
/* 113:    */     }
/* 114:    */     catch (TransformerException te)
/* 115:    */     {
/* 116:191 */       Throwable nestedException = te.getException();
/* 117:192 */       if ((nestedException instanceof XPathFunctionException)) {
/* 118:193 */         throw ((XPathFunctionException)nestedException);
/* 119:    */       }
/* 120:197 */       throw new XPathExpressionException(te);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String evaluate(Object item)
/* 125:    */     throws XPathExpressionException
/* 126:    */   {
/* 127:229 */     return (String)evaluate(item, XPathConstants.STRING);
/* 128:    */   }
/* 129:    */   
/* 130:234 */   static DocumentBuilderFactory dbf = null;
/* 131:235 */   static DocumentBuilder db = null;
/* 132:236 */   static Document d = null;
/* 133:    */   
/* 134:    */   public Object evaluate(InputSource source, QName returnType)
/* 135:    */     throws XPathExpressionException
/* 136:    */   {
/* 137:274 */     if ((source == null) || (returnType == null))
/* 138:    */     {
/* 139:275 */       String fmsg = XPATHMessages.createXPATHMessage("ER_SOURCE_RETURN_TYPE_CANNOT_BE_NULL", null);
/* 140:    */       
/* 141:    */ 
/* 142:278 */       throw new NullPointerException(fmsg);
/* 143:    */     }
/* 144:282 */     if (!isSupported(returnType))
/* 145:    */     {
/* 146:283 */       String fmsg = XPATHMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType.toString() });
/* 147:    */       
/* 148:    */ 
/* 149:286 */       throw new IllegalArgumentException(fmsg);
/* 150:    */     }
/* 151:    */     try
/* 152:    */     {
/* 153:289 */       if (dbf == null)
/* 154:    */       {
/* 155:290 */         dbf = DocumentBuilderFactory.newInstance();
/* 156:291 */         dbf.setNamespaceAware(true);
/* 157:292 */         dbf.setValidating(false);
/* 158:    */       }
/* 159:294 */       db = dbf.newDocumentBuilder();
/* 160:295 */       Document document = db.parse(source);
/* 161:296 */       return eval(document, returnType);
/* 162:    */     }
/* 163:    */     catch (Exception e)
/* 164:    */     {
/* 165:298 */       throw new XPathExpressionException(e);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String evaluate(InputSource source)
/* 170:    */     throws XPathExpressionException
/* 171:    */   {
/* 172:325 */     return (String)evaluate(source, XPathConstants.STRING);
/* 173:    */   }
/* 174:    */   
/* 175:    */   private boolean isSupported(QName returnType)
/* 176:    */   {
/* 177:330 */     if ((returnType.equals(XPathConstants.STRING)) || (returnType.equals(XPathConstants.NUMBER)) || (returnType.equals(XPathConstants.BOOLEAN)) || (returnType.equals(XPathConstants.NODE)) || (returnType.equals(XPathConstants.NODESET))) {
/* 178:336 */       return true;
/* 179:    */     }
/* 180:338 */     return false;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private Object getResultAsType(XObject resultObject, QName returnType)
/* 184:    */     throws TransformerException
/* 185:    */   {
/* 186:344 */     if (returnType.equals(XPathConstants.STRING)) {
/* 187:345 */       return resultObject.str();
/* 188:    */     }
/* 189:348 */     if (returnType.equals(XPathConstants.NUMBER)) {
/* 190:349 */       return new Double(resultObject.num());
/* 191:    */     }
/* 192:352 */     if (returnType.equals(XPathConstants.BOOLEAN)) {
/* 193:353 */       return new Boolean(resultObject.bool());
/* 194:    */     }
/* 195:356 */     if (returnType.equals(XPathConstants.NODESET)) {
/* 196:357 */       return resultObject.nodelist();
/* 197:    */     }
/* 198:360 */     if (returnType.equals(XPathConstants.NODE))
/* 199:    */     {
/* 200:361 */       NodeIterator ni = resultObject.nodeset();
/* 201:    */       
/* 202:363 */       return ni.nextNode();
/* 203:    */     }
/* 204:367 */     String fmsg = XPATHMessages.createXPATHMessage("ER_UNSUPPORTED_RETURN_TYPE", new Object[] { returnType.toString() });
/* 205:    */     
/* 206:    */ 
/* 207:370 */     throw new IllegalArgumentException(fmsg);
/* 208:    */   }
/* 209:    */   
/* 210:    */   private static Document getDummyDocument()
/* 211:    */   {
/* 212:    */     try
/* 213:    */     {
/* 214:376 */       if (dbf == null)
/* 215:    */       {
/* 216:377 */         dbf = DocumentBuilderFactory.newInstance();
/* 217:378 */         dbf.setNamespaceAware(true);
/* 218:379 */         dbf.setValidating(false);
/* 219:    */       }
/* 220:381 */       db = dbf.newDocumentBuilder();
/* 221:    */       
/* 222:383 */       DOMImplementation dim = db.getDOMImplementation();
/* 223:384 */       d = dim.createDocument("http://java.sun.com/jaxp/xpath", "dummyroot", null);
/* 224:    */       
/* 225:386 */       return d;
/* 226:    */     }
/* 227:    */     catch (Exception e)
/* 228:    */     {
/* 229:388 */       e.printStackTrace();
/* 230:    */     }
/* 231:390 */     return null;
/* 232:    */   }
/* 233:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.jaxp.XPathExpressionImpl
 * JD-Core Version:    0.7.0.1
 */