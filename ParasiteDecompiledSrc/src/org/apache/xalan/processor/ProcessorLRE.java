/*   1:    */ package org.apache.xalan.processor;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import javax.xml.transform.TransformerConfigurationException;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.res.XSLMessages;
/*   7:    */ import org.apache.xalan.templates.ElemExtensionCall;
/*   8:    */ import org.apache.xalan.templates.ElemLiteralResult;
/*   9:    */ import org.apache.xalan.templates.ElemTemplate;
/*  10:    */ import org.apache.xalan.templates.ElemTemplateElement;
/*  11:    */ import org.apache.xalan.templates.Stylesheet;
/*  12:    */ import org.apache.xalan.templates.StylesheetRoot;
/*  13:    */ import org.apache.xalan.templates.XMLNSDecl;
/*  14:    */ import org.apache.xml.utils.SAXSourceLocator;
/*  15:    */ import org.apache.xpath.XPath;
/*  16:    */ import org.xml.sax.Attributes;
/*  17:    */ import org.xml.sax.Locator;
/*  18:    */ import org.xml.sax.SAXException;
/*  19:    */ import org.xml.sax.helpers.AttributesImpl;
/*  20:    */ import org.xml.sax.helpers.LocatorImpl;
/*  21:    */ 
/*  22:    */ public class ProcessorLRE
/*  23:    */   extends ProcessorTemplateElem
/*  24:    */ {
/*  25:    */   static final long serialVersionUID = -1490218021772101404L;
/*  26:    */   
/*  27:    */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/*  28:    */     throws SAXException
/*  29:    */   {
/*  30:    */     try
/*  31:    */     {
/*  32: 75 */       ElemTemplateElement p = handler.getElemTemplateElement();
/*  33: 76 */       boolean excludeXSLDecl = false;
/*  34: 77 */       boolean isLREAsStyleSheet = false;
/*  35: 79 */       if (null == p)
/*  36:    */       {
/*  37: 83 */         XSLTElementProcessor lreProcessor = handler.popProcessor();
/*  38: 84 */         XSLTElementProcessor stylesheetProcessor = handler.getProcessorFor("http://www.w3.org/1999/XSL/Transform", "stylesheet", "xsl:stylesheet");
/*  39:    */         
/*  40:    */ 
/*  41:    */ 
/*  42: 88 */         handler.pushProcessor(lreProcessor);
/*  43:    */         Stylesheet stylesheet;
/*  44:    */         try
/*  45:    */         {
/*  46: 93 */           stylesheet = getStylesheetRoot(handler);
/*  47:    */         }
/*  48:    */         catch (TransformerConfigurationException tfe)
/*  49:    */         {
/*  50: 97 */           throw new TransformerException(tfe);
/*  51:    */         }
/*  52:102 */         SAXSourceLocator slocator = new SAXSourceLocator();
/*  53:103 */         Locator locator = handler.getLocator();
/*  54:104 */         if (null != locator)
/*  55:    */         {
/*  56:106 */           slocator.setLineNumber(locator.getLineNumber());
/*  57:107 */           slocator.setColumnNumber(locator.getColumnNumber());
/*  58:108 */           slocator.setPublicId(locator.getPublicId());
/*  59:109 */           slocator.setSystemId(locator.getSystemId());
/*  60:    */         }
/*  61:111 */         stylesheet.setLocaterInfo(slocator);
/*  62:112 */         stylesheet.setPrefixes(handler.getNamespaceSupport());
/*  63:113 */         handler.pushStylesheet(stylesheet);
/*  64:    */         
/*  65:115 */         isLREAsStyleSheet = true;
/*  66:    */         
/*  67:117 */         AttributesImpl stylesheetAttrs = new AttributesImpl();
/*  68:118 */         AttributesImpl lreAttrs = new AttributesImpl();
/*  69:119 */         int n = attributes.getLength();
/*  70:121 */         for (int i = 0; i < n; i++)
/*  71:    */         {
/*  72:123 */           String attrLocalName = attributes.getLocalName(i);
/*  73:124 */           String attrUri = attributes.getURI(i);
/*  74:125 */           String value = attributes.getValue(i);
/*  75:127 */           if ((null != attrUri) && (attrUri.equals("http://www.w3.org/1999/XSL/Transform"))) {
/*  76:129 */             stylesheetAttrs.addAttribute(null, attrLocalName, attrLocalName, attributes.getType(i), attributes.getValue(i));
/*  77:133 */           } else if (((!attrLocalName.startsWith("xmlns:")) && (!attrLocalName.equals("xmlns"))) || (!value.equals("http://www.w3.org/1999/XSL/Transform"))) {
/*  78:141 */             lreAttrs.addAttribute(attrUri, attrLocalName, attributes.getQName(i), attributes.getType(i), attributes.getValue(i));
/*  79:    */           }
/*  80:    */         }
/*  81:148 */         attributes = lreAttrs;
/*  82:    */         try
/*  83:    */         {
/*  84:154 */           stylesheetProcessor.setPropertiesFromAttributes(handler, "stylesheet", stylesheetAttrs, stylesheet);
/*  85:    */         }
/*  86:    */         catch (Exception e)
/*  87:    */         {
/*  88:166 */           if ((stylesheet.getDeclaredPrefixes() == null) || (!declaredXSLNS(stylesheet))) {
/*  89:169 */             throw new SAXException(XSLMessages.createWarning("WG_OLD_XSLT_NS", null));
/*  90:    */           }
/*  91:173 */           throw new SAXException(e);
/*  92:    */         }
/*  93:176 */         handler.pushElemTemplateElement(stylesheet);
/*  94:    */         
/*  95:178 */         ElemTemplate template = new ElemTemplate();
/*  96:179 */         if (slocator != null) {
/*  97:180 */           template.setLocaterInfo(slocator);
/*  98:    */         }
/*  99:182 */         appendAndPush(handler, template);
/* 100:    */         
/* 101:184 */         XPath rootMatch = new XPath("/", stylesheet, stylesheet, 1, handler.getStylesheetProcessor().getErrorListener());
/* 102:    */         
/* 103:    */ 
/* 104:187 */         template.setMatch(rootMatch);
/* 105:    */         
/* 106:    */ 
/* 107:190 */         stylesheet.setTemplate(template);
/* 108:    */         
/* 109:192 */         p = handler.getElemTemplateElement();
/* 110:193 */         excludeXSLDecl = true;
/* 111:    */       }
/* 112:196 */       XSLTElementDef def = getElemDef();
/* 113:197 */       Class classObject = def.getClassObject();
/* 114:198 */       boolean isExtension = false;
/* 115:199 */       boolean isComponentDecl = false;
/* 116:200 */       boolean isUnknownTopLevel = false;
/* 117:202 */       while (null != p)
/* 118:    */       {
/* 119:206 */         if ((p instanceof ElemLiteralResult))
/* 120:    */         {
/* 121:208 */           ElemLiteralResult parentElem = (ElemLiteralResult)p;
/* 122:    */           
/* 123:210 */           isExtension = parentElem.containsExtensionElementURI(uri);
/* 124:    */         }
/* 125:212 */         else if ((p instanceof Stylesheet))
/* 126:    */         {
/* 127:214 */           Stylesheet parentElem = (Stylesheet)p;
/* 128:    */           
/* 129:216 */           isExtension = parentElem.containsExtensionElementURI(uri);
/* 130:218 */           if ((false == isExtension) && (null != uri) && ((uri.equals("http://xml.apache.org/xalan")) || (uri.equals("http://xml.apache.org/xslt")))) {
/* 131:222 */             isComponentDecl = true;
/* 132:    */           } else {
/* 133:226 */             isUnknownTopLevel = true;
/* 134:    */           }
/* 135:    */         }
/* 136:230 */         if (isExtension) {
/* 137:    */           break;
/* 138:    */         }
/* 139:233 */         p = p.getParentElem();
/* 140:    */       }
/* 141:236 */       ElemTemplateElement elem = null;
/* 142:    */       try
/* 143:    */       {
/* 144:240 */         if (isExtension) {
/* 145:244 */           elem = new ElemExtensionCall();
/* 146:246 */         } else if (isComponentDecl) {
/* 147:248 */           elem = (ElemTemplateElement)classObject.newInstance();
/* 148:250 */         } else if (isUnknownTopLevel) {
/* 149:254 */           elem = (ElemTemplateElement)classObject.newInstance();
/* 150:    */         } else {
/* 151:258 */           elem = (ElemTemplateElement)classObject.newInstance();
/* 152:    */         }
/* 153:261 */         elem.setDOMBackPointer(handler.getOriginatingNode());
/* 154:262 */         elem.setLocaterInfo(handler.getLocator());
/* 155:263 */         elem.setPrefixes(handler.getNamespaceSupport(), excludeXSLDecl);
/* 156:265 */         if ((elem instanceof ElemLiteralResult))
/* 157:    */         {
/* 158:267 */           ((ElemLiteralResult)elem).setNamespace(uri);
/* 159:268 */           ((ElemLiteralResult)elem).setLocalName(localName);
/* 160:269 */           ((ElemLiteralResult)elem).setRawName(rawName);
/* 161:270 */           ((ElemLiteralResult)elem).setIsLiteralResultAsStylesheet(isLREAsStyleSheet);
/* 162:    */         }
/* 163:    */       }
/* 164:    */       catch (InstantiationException ie)
/* 165:    */       {
/* 166:276 */         handler.error("ER_FAILED_CREATING_ELEMLITRSLT", null, ie);
/* 167:    */       }
/* 168:    */       catch (IllegalAccessException iae)
/* 169:    */       {
/* 170:280 */         handler.error("ER_FAILED_CREATING_ELEMLITRSLT", null, iae);
/* 171:    */       }
/* 172:283 */       setPropertiesFromAttributes(handler, rawName, attributes, elem);
/* 173:286 */       if ((!isExtension) && ((elem instanceof ElemLiteralResult)))
/* 174:    */       {
/* 175:288 */         isExtension = ((ElemLiteralResult)elem).containsExtensionElementURI(uri);
/* 176:291 */         if (isExtension)
/* 177:    */         {
/* 178:295 */           elem = new ElemExtensionCall();
/* 179:    */           
/* 180:297 */           elem.setLocaterInfo(handler.getLocator());
/* 181:298 */           elem.setPrefixes(handler.getNamespaceSupport());
/* 182:299 */           ((ElemLiteralResult)elem).setNamespace(uri);
/* 183:300 */           ((ElemLiteralResult)elem).setLocalName(localName);
/* 184:301 */           ((ElemLiteralResult)elem).setRawName(rawName);
/* 185:302 */           setPropertiesFromAttributes(handler, rawName, attributes, elem);
/* 186:    */         }
/* 187:    */       }
/* 188:306 */       appendAndPush(handler, elem);
/* 189:    */     }
/* 190:    */     catch (TransformerException te)
/* 191:    */     {
/* 192:310 */       throw new SAXException(te);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected Stylesheet getStylesheetRoot(StylesheetHandler handler)
/* 197:    */     throws TransformerConfigurationException
/* 198:    */   {
/* 199:322 */     StylesheetRoot stylesheet = new StylesheetRoot(handler.getSchema(), handler.getStylesheetProcessor().getErrorListener());
/* 200:323 */     if (handler.getStylesheetProcessor().isSecureProcessing()) {
/* 201:324 */       stylesheet.setSecureProcessing(true);
/* 202:    */     }
/* 203:326 */     return stylesheet;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName)
/* 207:    */     throws SAXException
/* 208:    */   {
/* 209:343 */     ElemTemplateElement elem = handler.getElemTemplateElement();
/* 210:345 */     if ((elem instanceof ElemLiteralResult)) {
/* 211:347 */       if (((ElemLiteralResult)elem).getIsLiteralResultAsStylesheet()) {
/* 212:349 */         handler.popStylesheet();
/* 213:    */       }
/* 214:    */     }
/* 215:353 */     super.endElement(handler, uri, localName, rawName);
/* 216:    */   }
/* 217:    */   
/* 218:    */   private boolean declaredXSLNS(Stylesheet stylesheet)
/* 219:    */   {
/* 220:358 */     List declaredPrefixes = stylesheet.getDeclaredPrefixes();
/* 221:359 */     int n = declaredPrefixes.size();
/* 222:361 */     for (int i = 0; i < n; i++)
/* 223:    */     {
/* 224:363 */       XMLNSDecl decl = (XMLNSDecl)declaredPrefixes.get(i);
/* 225:364 */       if (decl.getURI().equals("http://www.w3.org/1999/XSL/Transform")) {
/* 226:365 */         return true;
/* 227:    */       }
/* 228:    */     }
/* 229:367 */     return false;
/* 230:    */   }
/* 231:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorLRE
 * JD-Core Version:    0.7.0.1
 */