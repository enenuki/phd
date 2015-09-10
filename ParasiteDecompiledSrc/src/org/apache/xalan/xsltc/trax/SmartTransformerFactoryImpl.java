/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javax.xml.transform.ErrorListener;
/*   5:    */ import javax.xml.transform.Source;
/*   6:    */ import javax.xml.transform.Templates;
/*   7:    */ import javax.xml.transform.Transformer;
/*   8:    */ import javax.xml.transform.TransformerConfigurationException;
/*   9:    */ import javax.xml.transform.TransformerException;
/*  10:    */ import javax.xml.transform.TransformerFactory;
/*  11:    */ import javax.xml.transform.URIResolver;
/*  12:    */ import javax.xml.transform.sax.SAXTransformerFactory;
/*  13:    */ import javax.xml.transform.sax.TemplatesHandler;
/*  14:    */ import javax.xml.transform.sax.TransformerHandler;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  16:    */ import org.xml.sax.XMLFilter;
/*  17:    */ 
/*  18:    */ public class SmartTransformerFactoryImpl
/*  19:    */   extends SAXTransformerFactory
/*  20:    */ {
/*  21:    */   private static final String CLASS_NAME = "SmartTransformerFactoryImpl";
/*  22: 60 */   private SAXTransformerFactory _xsltcFactory = null;
/*  23: 61 */   private SAXTransformerFactory _xalanFactory = null;
/*  24: 62 */   private SAXTransformerFactory _currFactory = null;
/*  25: 63 */   private ErrorListener _errorlistener = null;
/*  26: 64 */   private URIResolver _uriresolver = null;
/*  27: 69 */   private boolean featureSecureProcessing = false;
/*  28:    */   
/*  29:    */   private void createXSLTCTransformerFactory()
/*  30:    */   {
/*  31: 81 */     this._xsltcFactory = new TransformerFactoryImpl();
/*  32: 82 */     this._currFactory = this._xsltcFactory;
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void createXalanTransformerFactory()
/*  36:    */   {
/*  37: 86 */     String xalanMessage = "org.apache.xalan.xsltc.trax.SmartTransformerFactoryImpl could not create an org.apache.xalan.processor.TransformerFactoryImpl.";
/*  38:    */     try
/*  39:    */     {
/*  40: 92 */       Class xalanFactClass = ObjectFactory.findProviderClass("org.apache.xalan.processor.TransformerFactoryImpl", ObjectFactory.findClassLoader(), true);
/*  41:    */       
/*  42:    */ 
/*  43: 95 */       this._xalanFactory = ((SAXTransformerFactory)xalanFactClass.newInstance());
/*  44:    */     }
/*  45:    */     catch (ClassNotFoundException e)
/*  46:    */     {
/*  47: 99 */       System.err.println("org.apache.xalan.xsltc.trax.SmartTransformerFactoryImpl could not create an org.apache.xalan.processor.TransformerFactoryImpl.");
/*  48:    */     }
/*  49:    */     catch (InstantiationException e)
/*  50:    */     {
/*  51:102 */       System.err.println("org.apache.xalan.xsltc.trax.SmartTransformerFactoryImpl could not create an org.apache.xalan.processor.TransformerFactoryImpl.");
/*  52:    */     }
/*  53:    */     catch (IllegalAccessException e)
/*  54:    */     {
/*  55:105 */       System.err.println("org.apache.xalan.xsltc.trax.SmartTransformerFactoryImpl could not create an org.apache.xalan.processor.TransformerFactoryImpl.");
/*  56:    */     }
/*  57:107 */     this._currFactory = this._xalanFactory;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setErrorListener(ErrorListener listener)
/*  61:    */     throws IllegalArgumentException
/*  62:    */   {
/*  63:113 */     this._errorlistener = listener;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ErrorListener getErrorListener()
/*  67:    */   {
/*  68:117 */     return this._errorlistener;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object getAttribute(String name)
/*  72:    */     throws IllegalArgumentException
/*  73:    */   {
/*  74:124 */     if ((name.equals("translet-name")) || (name.equals("debug")))
/*  75:    */     {
/*  76:125 */       if (this._xsltcFactory == null) {
/*  77:126 */         createXSLTCTransformerFactory();
/*  78:    */       }
/*  79:128 */       return this._xsltcFactory.getAttribute(name);
/*  80:    */     }
/*  81:131 */     if (this._xalanFactory == null) {
/*  82:132 */       createXalanTransformerFactory();
/*  83:    */     }
/*  84:134 */     return this._xalanFactory.getAttribute(name);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setAttribute(String name, Object value)
/*  88:    */     throws IllegalArgumentException
/*  89:    */   {
/*  90:141 */     if ((name.equals("translet-name")) || (name.equals("debug")))
/*  91:    */     {
/*  92:142 */       if (this._xsltcFactory == null) {
/*  93:143 */         createXSLTCTransformerFactory();
/*  94:    */       }
/*  95:145 */       this._xsltcFactory.setAttribute(name, value);
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:148 */       if (this._xalanFactory == null) {
/* 100:149 */         createXalanTransformerFactory();
/* 101:    */       }
/* 102:151 */       this._xalanFactory.setAttribute(name, value);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setFeature(String name, boolean value)
/* 107:    */     throws TransformerConfigurationException
/* 108:    */   {
/* 109:180 */     if (name == null)
/* 110:    */     {
/* 111:181 */       ErrorMsg err = new ErrorMsg("JAXP_SET_FEATURE_NULL_NAME");
/* 112:182 */       throw new NullPointerException(err.toString());
/* 113:    */     }
/* 114:185 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
/* 115:    */     {
/* 116:186 */       this.featureSecureProcessing = value;
/* 117:    */       
/* 118:188 */       return;
/* 119:    */     }
/* 120:192 */     ErrorMsg err = new ErrorMsg("JAXP_UNSUPPORTED_FEATURE", name);
/* 121:193 */     throw new TransformerConfigurationException(err.toString());
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean getFeature(String name)
/* 125:    */   {
/* 126:208 */     String[] features = { "http://javax.xml.transform.dom.DOMSource/feature", "http://javax.xml.transform.dom.DOMResult/feature", "http://javax.xml.transform.sax.SAXSource/feature", "http://javax.xml.transform.sax.SAXResult/feature", "http://javax.xml.transform.stream.StreamSource/feature", "http://javax.xml.transform.stream.StreamResult/feature" };
/* 127:218 */     if (name == null)
/* 128:    */     {
/* 129:219 */       ErrorMsg err = new ErrorMsg("JAXP_GET_FEATURE_NULL_NAME");
/* 130:220 */       throw new NullPointerException(err.toString());
/* 131:    */     }
/* 132:224 */     for (int i = 0; i < features.length; i++) {
/* 133:225 */       if (name.equals(features[i])) {
/* 134:226 */         return true;
/* 135:    */       }
/* 136:    */     }
/* 137:230 */     if (name.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
/* 138:231 */       return this.featureSecureProcessing;
/* 139:    */     }
/* 140:235 */     return false;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public URIResolver getURIResolver()
/* 144:    */   {
/* 145:239 */     return this._uriresolver;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setURIResolver(URIResolver resolver)
/* 149:    */   {
/* 150:243 */     this._uriresolver = resolver;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Source getAssociatedStylesheet(Source source, String media, String title, String charset)
/* 154:    */     throws TransformerConfigurationException
/* 155:    */   {
/* 156:250 */     if (this._currFactory == null) {
/* 157:251 */       createXSLTCTransformerFactory();
/* 158:    */     }
/* 159:253 */     return this._currFactory.getAssociatedStylesheet(source, media, title, charset);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Transformer newTransformer()
/* 163:    */     throws TransformerConfigurationException
/* 164:    */   {
/* 165:265 */     if (this._xalanFactory == null) {
/* 166:266 */       createXalanTransformerFactory();
/* 167:    */     }
/* 168:268 */     if (this._errorlistener != null) {
/* 169:269 */       this._xalanFactory.setErrorListener(this._errorlistener);
/* 170:    */     }
/* 171:271 */     if (this._uriresolver != null) {
/* 172:272 */       this._xalanFactory.setURIResolver(this._uriresolver);
/* 173:    */     }
/* 174:274 */     this._currFactory = this._xalanFactory;
/* 175:275 */     return this._currFactory.newTransformer();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Transformer newTransformer(Source source)
/* 179:    */     throws TransformerConfigurationException
/* 180:    */   {
/* 181:287 */     if (this._xalanFactory == null) {
/* 182:288 */       createXalanTransformerFactory();
/* 183:    */     }
/* 184:290 */     if (this._errorlistener != null) {
/* 185:291 */       this._xalanFactory.setErrorListener(this._errorlistener);
/* 186:    */     }
/* 187:293 */     if (this._uriresolver != null) {
/* 188:294 */       this._xalanFactory.setURIResolver(this._uriresolver);
/* 189:    */     }
/* 190:296 */     this._currFactory = this._xalanFactory;
/* 191:297 */     return this._currFactory.newTransformer(source);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Templates newTemplates(Source source)
/* 195:    */     throws TransformerConfigurationException
/* 196:    */   {
/* 197:309 */     if (this._xsltcFactory == null) {
/* 198:310 */       createXSLTCTransformerFactory();
/* 199:    */     }
/* 200:312 */     if (this._errorlistener != null) {
/* 201:313 */       this._xsltcFactory.setErrorListener(this._errorlistener);
/* 202:    */     }
/* 203:315 */     if (this._uriresolver != null) {
/* 204:316 */       this._xsltcFactory.setURIResolver(this._uriresolver);
/* 205:    */     }
/* 206:318 */     this._currFactory = this._xsltcFactory;
/* 207:319 */     return this._currFactory.newTemplates(source);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public TemplatesHandler newTemplatesHandler()
/* 211:    */     throws TransformerConfigurationException
/* 212:    */   {
/* 213:330 */     if (this._xsltcFactory == null) {
/* 214:331 */       createXSLTCTransformerFactory();
/* 215:    */     }
/* 216:333 */     if (this._errorlistener != null) {
/* 217:334 */       this._xsltcFactory.setErrorListener(this._errorlistener);
/* 218:    */     }
/* 219:336 */     if (this._uriresolver != null) {
/* 220:337 */       this._xsltcFactory.setURIResolver(this._uriresolver);
/* 221:    */     }
/* 222:339 */     return this._xsltcFactory.newTemplatesHandler();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public TransformerHandler newTransformerHandler()
/* 226:    */     throws TransformerConfigurationException
/* 227:    */   {
/* 228:350 */     if (this._xalanFactory == null) {
/* 229:351 */       createXalanTransformerFactory();
/* 230:    */     }
/* 231:353 */     if (this._errorlistener != null) {
/* 232:354 */       this._xalanFactory.setErrorListener(this._errorlistener);
/* 233:    */     }
/* 234:356 */     if (this._uriresolver != null) {
/* 235:357 */       this._xalanFactory.setURIResolver(this._uriresolver);
/* 236:    */     }
/* 237:359 */     return this._xalanFactory.newTransformerHandler();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public TransformerHandler newTransformerHandler(Source src)
/* 241:    */     throws TransformerConfigurationException
/* 242:    */   {
/* 243:370 */     if (this._xalanFactory == null) {
/* 244:371 */       createXalanTransformerFactory();
/* 245:    */     }
/* 246:373 */     if (this._errorlistener != null) {
/* 247:374 */       this._xalanFactory.setErrorListener(this._errorlistener);
/* 248:    */     }
/* 249:376 */     if (this._uriresolver != null) {
/* 250:377 */       this._xalanFactory.setURIResolver(this._uriresolver);
/* 251:    */     }
/* 252:379 */     return this._xalanFactory.newTransformerHandler(src);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public TransformerHandler newTransformerHandler(Templates templates)
/* 256:    */     throws TransformerConfigurationException
/* 257:    */   {
/* 258:391 */     if (this._xsltcFactory == null) {
/* 259:392 */       createXSLTCTransformerFactory();
/* 260:    */     }
/* 261:394 */     if (this._errorlistener != null) {
/* 262:395 */       this._xsltcFactory.setErrorListener(this._errorlistener);
/* 263:    */     }
/* 264:397 */     if (this._uriresolver != null) {
/* 265:398 */       this._xsltcFactory.setURIResolver(this._uriresolver);
/* 266:    */     }
/* 267:400 */     return this._xsltcFactory.newTransformerHandler(templates);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public XMLFilter newXMLFilter(Source src)
/* 271:    */     throws TransformerConfigurationException
/* 272:    */   {
/* 273:411 */     if (this._xsltcFactory == null) {
/* 274:412 */       createXSLTCTransformerFactory();
/* 275:    */     }
/* 276:414 */     if (this._errorlistener != null) {
/* 277:415 */       this._xsltcFactory.setErrorListener(this._errorlistener);
/* 278:    */     }
/* 279:417 */     if (this._uriresolver != null) {
/* 280:418 */       this._xsltcFactory.setURIResolver(this._uriresolver);
/* 281:    */     }
/* 282:420 */     Templates templates = this._xsltcFactory.newTemplates(src);
/* 283:421 */     if (templates == null) {
/* 284:421 */       return null;
/* 285:    */     }
/* 286:422 */     return newXMLFilter(templates);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public XMLFilter newXMLFilter(Templates templates)
/* 290:    */     throws TransformerConfigurationException
/* 291:    */   {
/* 292:    */     try
/* 293:    */     {
/* 294:433 */       return new TrAXFilter(templates);
/* 295:    */     }
/* 296:    */     catch (TransformerConfigurationException e1)
/* 297:    */     {
/* 298:436 */       if (this._xsltcFactory == null) {
/* 299:437 */         createXSLTCTransformerFactory();
/* 300:    */       }
/* 301:439 */       ErrorListener errorListener = this._xsltcFactory.getErrorListener();
/* 302:440 */       if (errorListener != null) {
/* 303:    */         try
/* 304:    */         {
/* 305:442 */           errorListener.fatalError(e1);
/* 306:443 */           return null;
/* 307:    */         }
/* 308:    */         catch (TransformerException e2)
/* 309:    */         {
/* 310:446 */           new TransformerConfigurationException(e2);
/* 311:    */         }
/* 312:    */       }
/* 313:449 */       throw e1;
/* 314:    */     }
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.SmartTransformerFactoryImpl
 * JD-Core Version:    0.7.0.1
 */