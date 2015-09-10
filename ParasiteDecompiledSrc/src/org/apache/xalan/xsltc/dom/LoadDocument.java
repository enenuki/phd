/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import javax.xml.transform.stream.StreamSource;
/*   5:    */ import org.apache.xalan.xsltc.DOM;
/*   6:    */ import org.apache.xalan.xsltc.DOMCache;
/*   7:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*   8:    */ import org.apache.xalan.xsltc.TransletException;
/*   9:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*  10:    */ import org.apache.xalan.xsltc.trax.TemplatesImpl;
/*  11:    */ import org.apache.xml.dtm.DTM;
/*  12:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*  13:    */ import org.apache.xml.dtm.DTMManager;
/*  14:    */ import org.apache.xml.dtm.ref.EmptyIterator;
/*  15:    */ import org.apache.xml.utils.SystemIDResolver;
/*  16:    */ 
/*  17:    */ public final class LoadDocument
/*  18:    */ {
/*  19:    */   private static final String NAMESPACE_FEATURE = "http://xml.org/sax/features/namespaces";
/*  20:    */   
/*  21:    */   public static DTMAxisIterator documentF(Object arg1, DTMAxisIterator arg2, String xslURI, AbstractTranslet translet, DOM dom)
/*  22:    */     throws TransletException
/*  23:    */   {
/*  24: 62 */     String baseURI = null;
/*  25: 63 */     int arg2FirstNode = arg2.next();
/*  26: 64 */     if (arg2FirstNode == -1) {
/*  27: 66 */       return EmptyIterator.getInstance();
/*  28:    */     }
/*  29: 71 */     baseURI = dom.getDocumentURI(arg2FirstNode);
/*  30: 72 */     if (!SystemIDResolver.isAbsoluteURI(baseURI)) {
/*  31: 73 */       baseURI = SystemIDResolver.getAbsoluteURIFromRelative(baseURI);
/*  32:    */     }
/*  33:    */     try
/*  34:    */     {
/*  35: 77 */       if ((arg1 instanceof String))
/*  36:    */       {
/*  37: 78 */         if (((String)arg1).length() == 0) {
/*  38: 79 */           return document(xslURI, "", translet, dom);
/*  39:    */         }
/*  40: 81 */         return document((String)arg1, baseURI, translet, dom);
/*  41:    */       }
/*  42: 83 */       if ((arg1 instanceof DTMAxisIterator)) {
/*  43: 84 */         return document((DTMAxisIterator)arg1, baseURI, translet, dom);
/*  44:    */       }
/*  45: 86 */       String err = "document(" + arg1.toString() + ")";
/*  46: 87 */       throw new IllegalArgumentException(err);
/*  47:    */     }
/*  48:    */     catch (Exception e)
/*  49:    */     {
/*  50: 90 */       throw new TransletException(e);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static DTMAxisIterator documentF(Object arg, String xslURI, AbstractTranslet translet, DOM dom)
/*  55:    */     throws TransletException
/*  56:    */   {
/*  57:    */     try
/*  58:    */     {
/*  59:104 */       if ((arg instanceof String))
/*  60:    */       {
/*  61:105 */         if (xslURI == null) {
/*  62:106 */           xslURI = "";
/*  63:    */         }
/*  64:108 */         String baseURI = xslURI;
/*  65:109 */         if (!SystemIDResolver.isAbsoluteURI(xslURI)) {
/*  66:110 */           baseURI = SystemIDResolver.getAbsoluteURIFromRelative(xslURI);
/*  67:    */         }
/*  68:112 */         String href = (String)arg;
/*  69:113 */         if (href.length() == 0)
/*  70:    */         {
/*  71:114 */           href = "";
/*  72:    */           
/*  73:    */ 
/*  74:    */ 
/*  75:118 */           TemplatesImpl templates = (TemplatesImpl)translet.getTemplates();
/*  76:119 */           DOM sdom = null;
/*  77:120 */           if (templates != null) {
/*  78:121 */             sdom = templates.getStylesheetDOM();
/*  79:    */           }
/*  80:127 */           if (sdom != null) {
/*  81:128 */             return document(sdom, translet, dom);
/*  82:    */           }
/*  83:131 */           return document(href, baseURI, translet, dom, true);
/*  84:    */         }
/*  85:135 */         return document(href, baseURI, translet, dom);
/*  86:    */       }
/*  87:137 */       if ((arg instanceof DTMAxisIterator)) {
/*  88:138 */         return document((DTMAxisIterator)arg, null, translet, dom);
/*  89:    */       }
/*  90:140 */       String err = "document(" + arg.toString() + ")";
/*  91:141 */       throw new IllegalArgumentException(err);
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:144 */       throw new TransletException(e);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private static DTMAxisIterator document(String uri, String base, AbstractTranslet translet, DOM dom)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102:152 */     return document(uri, base, translet, dom, false);
/* 103:    */   }
/* 104:    */   
/* 105:    */   private static DTMAxisIterator document(String uri, String base, AbstractTranslet translet, DOM dom, boolean cacheDOM)
/* 106:    */     throws Exception
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:161 */       String originalUri = uri;
/* 111:162 */       MultiDOM multiplexer = (MultiDOM)dom;
/* 112:165 */       if ((base != null) && (!base.equals(""))) {
/* 113:166 */         uri = SystemIDResolver.getAbsoluteURI(uri, base);
/* 114:    */       }
/* 115:171 */       if ((uri == null) || (uri.equals(""))) {
/* 116:172 */         return EmptyIterator.getInstance();
/* 117:    */       }
/* 118:176 */       int mask = multiplexer.getDocumentMask(uri);
/* 119:177 */       if (mask != -1)
/* 120:    */       {
/* 121:178 */         DOM newDom = ((DOMAdapter)multiplexer.getDOMAdapter(uri)).getDOMImpl();
/* 122:180 */         if ((newDom instanceof DOMEnhancedForDTM)) {
/* 123:181 */           return new SingletonIterator(((DOMEnhancedForDTM)newDom).getDocument(), true);
/* 124:    */         }
/* 125:    */       }
/* 126:188 */       DOMCache cache = translet.getDOMCache();
/* 127:    */       
/* 128:    */ 
/* 129:191 */       mask = multiplexer.nextMask();
/* 130:    */       DOM newdom;
/* 131:193 */       if (cache != null)
/* 132:    */       {
/* 133:194 */         newdom = cache.retrieveDocument(base, originalUri, translet);
/* 134:195 */         if (newdom == null)
/* 135:    */         {
/* 136:196 */           Exception e = new FileNotFoundException(originalUri);
/* 137:197 */           throw new TransletException(e);
/* 138:    */         }
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:203 */         XSLTCDTMManager dtmManager = (XSLTCDTMManager)multiplexer.getDTMManager();
/* 143:    */         
/* 144:205 */         DOMEnhancedForDTM enhancedDOM = (DOMEnhancedForDTM)dtmManager.getDTM(new StreamSource(uri), false, null, true, false, translet.hasIdCall(), cacheDOM);
/* 145:    */         
/* 146:    */ 
/* 147:    */ 
/* 148:209 */         newdom = enhancedDOM;
/* 149:212 */         if (cacheDOM)
/* 150:    */         {
/* 151:213 */           TemplatesImpl templates = (TemplatesImpl)translet.getTemplates();
/* 152:214 */           if (templates != null) {
/* 153:215 */             templates.setStylesheetDOM(enhancedDOM);
/* 154:    */           }
/* 155:    */         }
/* 156:219 */         translet.prepassDocument(enhancedDOM);
/* 157:220 */         enhancedDOM.setDocumentURI(uri);
/* 158:    */       }
/* 159:224 */       DOMAdapter domAdapter = translet.makeDOMAdapter(newdom);
/* 160:225 */       multiplexer.addDOMAdapter(domAdapter);
/* 161:    */       
/* 162:    */ 
/* 163:228 */       translet.buildKeys(domAdapter, null, null, newdom.getDocument());
/* 164:    */       
/* 165:    */ 
/* 166:231 */       return new SingletonIterator(newdom.getDocument(), true);
/* 167:    */     }
/* 168:    */     catch (Exception e)
/* 169:    */     {
/* 170:233 */       throw e;
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static DTMAxisIterator document(DTMAxisIterator arg1, String baseURI, AbstractTranslet translet, DOM dom)
/* 175:    */     throws Exception
/* 176:    */   {
/* 177:243 */     UnionIterator union = new UnionIterator(dom);
/* 178:244 */     int node = -1;
/* 179:246 */     while ((node = arg1.next()) != -1)
/* 180:    */     {
/* 181:247 */       String uri = dom.getStringValueX(node);
/* 182:249 */       if (baseURI == null)
/* 183:    */       {
/* 184:250 */         baseURI = dom.getDocumentURI(node);
/* 185:251 */         if (!SystemIDResolver.isAbsoluteURI(baseURI)) {
/* 186:252 */           baseURI = SystemIDResolver.getAbsoluteURIFromRelative(baseURI);
/* 187:    */         }
/* 188:    */       }
/* 189:254 */       union.addIterator(document(uri, baseURI, translet, dom));
/* 190:    */     }
/* 191:256 */     return union;
/* 192:    */   }
/* 193:    */   
/* 194:    */   private static DTMAxisIterator document(DOM newdom, AbstractTranslet translet, DOM dom)
/* 195:    */     throws Exception
/* 196:    */   {
/* 197:273 */     DTMManager dtmManager = ((MultiDOM)dom).getDTMManager();
/* 198:275 */     if ((dtmManager != null) && ((newdom instanceof DTM))) {
/* 199:276 */       ((DTM)newdom).migrateTo(dtmManager);
/* 200:    */     }
/* 201:279 */     translet.prepassDocument(newdom);
/* 202:    */     
/* 203:    */ 
/* 204:282 */     DOMAdapter domAdapter = translet.makeDOMAdapter(newdom);
/* 205:283 */     ((MultiDOM)dom).addDOMAdapter(domAdapter);
/* 206:    */     
/* 207:    */ 
/* 208:286 */     translet.buildKeys(domAdapter, null, null, newdom.getDocument());
/* 209:    */     
/* 210:    */ 
/* 211:    */ 
/* 212:290 */     return new SingletonIterator(newdom.getDocument(), true);
/* 213:    */   }
/* 214:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.LoadDocument
 * JD-Core Version:    0.7.0.1
 */