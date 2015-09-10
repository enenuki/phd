/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.xml;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.StringWebResponse;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.DomCDataSection;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  16:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  17:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  18:    */ import com.gargoylesoftware.htmlunit.javascript.host.Attr;
/*  19:    */ import com.gargoylesoftware.htmlunit.javascript.host.Document;
/*  20:    */ import com.gargoylesoftware.htmlunit.javascript.host.Element;
/*  21:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  22:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  23:    */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.net.URL;
/*  26:    */ import java.util.ArrayList;
/*  27:    */ import java.util.List;
/*  28:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  29:    */ import org.apache.commons.logging.Log;
/*  30:    */ import org.apache.commons.logging.LogFactory;
/*  31:    */ 
/*  32:    */ public class XMLDocument
/*  33:    */   extends Document
/*  34:    */ {
/*  35: 58 */   private static final Log LOG = LogFactory.getLog(XMLDocument.class);
/*  36: 60 */   private boolean async_ = true;
/*  37:    */   private boolean preserveWhiteSpace_;
/*  38:    */   private XMLDOMParseError parseError_;
/*  39:    */   
/*  40:    */   public XMLDocument()
/*  41:    */   {
/*  42: 68 */     this(null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public XMLDocument(WebWindow enclosingWindow)
/*  46:    */   {
/*  47: 76 */     if (enclosingWindow != null) {
/*  48:    */       try
/*  49:    */       {
/*  50: 78 */         XmlPage page = new XmlPage((WebResponse)null, enclosingWindow);
/*  51: 79 */         setDomNode(page);
/*  52:    */       }
/*  53:    */       catch (IOException e)
/*  54:    */       {
/*  55: 82 */         throw Context.reportRuntimeError("IOException: " + e);
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void jsxSet_async(boolean async)
/*  61:    */   {
/*  62: 92 */     this.async_ = async;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean jsxGet_async()
/*  66:    */   {
/*  67:100 */     return this.async_;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean jsxFunction_load(String xmlSource)
/*  71:    */   {
/*  72:110 */     if ((this.async_) && 
/*  73:111 */       (LOG.isDebugEnabled())) {
/*  74:112 */       LOG.debug("XMLDocument.load(): 'async' is true, currently treated as false.");
/*  75:    */     }
/*  76:    */     try
/*  77:    */     {
/*  78:116 */       HtmlPage htmlPage = (HtmlPage)getWindow().getWebWindow().getEnclosedPage();
/*  79:117 */       WebRequest request = new WebRequest(htmlPage.getFullyQualifiedUrl(xmlSource));
/*  80:118 */       WebResponse webResponse = getWindow().getWebWindow().getWebClient().loadWebResponse(request);
/*  81:119 */       XmlPage page = new XmlPage(webResponse, getWindow().getWebWindow(), false);
/*  82:120 */       setDomNode(page);
/*  83:121 */       return true;
/*  84:    */     }
/*  85:    */     catch (IOException e)
/*  86:    */     {
/*  87:124 */       XMLDOMParseError parseError = jsxGet_parseError();
/*  88:125 */       parseError.setErrorCode(-1);
/*  89:126 */       parseError.setFilepos(1);
/*  90:127 */       parseError.setLine(1);
/*  91:128 */       parseError.setLinepos(1);
/*  92:129 */       parseError.setReason(e.getMessage());
/*  93:130 */       parseError.setSrcText("xml");
/*  94:131 */       parseError.setUrl(xmlSource);
/*  95:132 */       if (LOG.isDebugEnabled()) {
/*  96:133 */         LOG.debug("Error parsing XML from '" + xmlSource + "'", e);
/*  97:    */       }
/*  98:    */     }
/*  99:135 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean jsxFunction_loadXML(String strXML)
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:148 */       WebWindow webWindow = getWindow().getWebWindow();
/* 107:    */       
/* 108:    */ 
/* 109:151 */       URL hackUrl = new URL("http://-htmlunit-internal/XMLDocument.loadXML");
/* 110:152 */       WebResponse webResponse = new StringWebResponse(strXML, hackUrl);
/* 111:    */       
/* 112:154 */       XmlPage page = new XmlPage(webResponse, webWindow);
/* 113:155 */       setDomNode(page);
/* 114:156 */       return true;
/* 115:    */     }
/* 116:    */     catch (IOException e)
/* 117:    */     {
/* 118:159 */       if (LOG.isDebugEnabled()) {
/* 119:160 */         LOG.debug("Error parsing XML\n" + strXML, e);
/* 120:    */       }
/* 121:    */     }
/* 122:162 */     return false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public SimpleScriptable makeScriptableFor(DomNode domNode)
/* 126:    */   {
/* 127:    */     SimpleScriptable scriptable;
/* 128:174 */     if (((domNode instanceof DomElement)) && (!(domNode instanceof HtmlElement)))
/* 129:    */     {
/* 130:175 */       scriptable = new Element();
/* 131:    */     }
/* 132:    */     else
/* 133:    */     {
/* 134:    */       SimpleScriptable scriptable;
/* 135:177 */       if ((domNode instanceof DomAttr))
/* 136:    */       {
/* 137:    */         Attr attribute;
/* 138:    */         Attr attribute;
/* 139:179 */         if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_134)) {
/* 140:180 */           attribute = new XMLAttr();
/* 141:    */         } else {
/* 142:183 */           attribute = new Attr();
/* 143:    */         }
/* 144:185 */         scriptable = attribute;
/* 145:    */       }
/* 146:    */       else
/* 147:    */       {
/* 148:188 */         return super.makeScriptableFor(domNode);
/* 149:    */       }
/* 150:    */     }
/* 151:    */     SimpleScriptable scriptable;
/* 152:191 */     scriptable.setPrototype(getPrototype(scriptable.getClass()));
/* 153:192 */     scriptable.setParentScope(getParentScope());
/* 154:193 */     scriptable.setDomNode(domNode);
/* 155:194 */     return scriptable;
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected void initParentScope(DomNode domNode, SimpleScriptable scriptable)
/* 159:    */   {
/* 160:202 */     scriptable.setParentScope(getParentScope());
/* 161:    */   }
/* 162:    */   
/* 163:    */   public XMLDOMParseError jsxGet_parseError()
/* 164:    */   {
/* 165:210 */     if (this.parseError_ == null)
/* 166:    */     {
/* 167:211 */       this.parseError_ = new XMLDOMParseError();
/* 168:212 */       this.parseError_.setPrototype(getPrototype(this.parseError_.getClass()));
/* 169:213 */       this.parseError_.setParentScope(getParentScope());
/* 170:    */     }
/* 171:215 */     return this.parseError_;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String jsxGet_xml()
/* 175:    */   {
/* 176:224 */     XMLSerializer seralizer = new XMLSerializer();
/* 177:225 */     seralizer.setParentScope(getWindow());
/* 178:226 */     seralizer.setPrototype(getPrototype(seralizer.getClass()));
/* 179:227 */     return seralizer.jsxFunction_serializeToString(jsxGet_documentElement());
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean jsxGet_preserveWhiteSpace()
/* 183:    */   {
/* 184:235 */     return this.preserveWhiteSpace_;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void jsxSet_preserveWhiteSpace(boolean preserveWhiteSpace)
/* 188:    */   {
/* 189:243 */     this.preserveWhiteSpace_ = preserveWhiteSpace;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void jsxFunction_setProperty(String name, String value) {}
/* 193:    */   
/* 194:    */   public HTMLCollection jsxFunction_selectNodes(final String expression)
/* 195:    */   {
/* 196:264 */     boolean attributeChangeSensitive = expression.contains("@");
/* 197:265 */     String description = "XMLDocument.selectNodes('" + expression + "')";
/* 198:266 */     final SgmlPage page = getPage();
/* 199:267 */     HTMLCollection collection = new HTMLCollection(page.getDocumentElement(), attributeChangeSensitive, description)
/* 200:    */     {
/* 201:    */       protected List<Object> computeElements()
/* 202:    */       {
/* 203:270 */         List<Object> list = new ArrayList(page.getByXPath(expression));
/* 204:271 */         return list;
/* 205:    */       }
/* 206:273 */     };
/* 207:274 */     return collection;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Object jsxFunction_selectSingleNode(String expression)
/* 211:    */   {
/* 212:284 */     HTMLCollection collection = jsxFunction_selectNodes(expression);
/* 213:285 */     if (collection.jsxGet_length() > 0) {
/* 214:286 */       return collection.get(0, collection);
/* 215:    */     }
/* 216:288 */     return null;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public HTMLCollection jsxFunction_getElementsByTagName(final String tagName)
/* 220:    */   {
/* 221:296 */     DomNode firstChild = getDomNodeOrDie().getFirstChild();
/* 222:297 */     if (firstChild == null) {
/* 223:298 */       return HTMLCollection.emptyCollection(getWindow());
/* 224:    */     }
/* 225:301 */     HTMLCollection collection = new HTMLCollection(getDomNodeOrDie(), false, "XMLDocument.getElementsByTagName")
/* 226:    */     {
/* 227:    */       protected boolean isMatching(DomNode node)
/* 228:    */       {
/* 229:304 */         return node.getLocalName().equals(tagName);
/* 230:    */       }
/* 231:307 */     };
/* 232:308 */     return collection;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Object jsxFunction_getElementById(String id)
/* 236:    */   {
/* 237:317 */     XmlPage xmlPage = (XmlPage)getDomNodeOrDie();
/* 238:318 */     Object domElement = xmlPage.getFirstByXPath("//*[@id = \"" + id + "\"]");
/* 239:319 */     if (domElement == null) {
/* 240:320 */       return null;
/* 241:    */     }
/* 242:323 */     if ((domElement instanceof HtmlElement)) {
/* 243:324 */       return ((HtmlElement)domElement).getScriptObject();
/* 244:    */     }
/* 245:326 */     if (LOG.isDebugEnabled()) {
/* 246:327 */       LOG.debug("getElementById(" + id + "): no HTML DOM node found with this ID");
/* 247:    */     }
/* 248:329 */     return null;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public Object jsxFunction_nodeFromID(String id)
/* 252:    */   {
/* 253:338 */     return null;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public Object jsxFunction_createProcessingInstruction(String target, String data)
/* 257:    */   {
/* 258:348 */     DomNode node = ((XmlPage)getPage()).createProcessingInstruction(target, data);
/* 259:349 */     return getScriptableFor(node);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Object jsxFunction_createCDATASection(String data)
/* 263:    */   {
/* 264:358 */     DomCDataSection node = ((XmlPage)getPage()).createCDATASection(data);
/* 265:359 */     return getScriptableFor(node);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Object jsxFunction_createNode(Object type, String name, Object namespaceURI)
/* 269:    */   {
/* 270:373 */     switch ((short)(int)Context.toNumber(type))
/* 271:    */     {
/* 272:    */     case 1: 
/* 273:375 */       return jsxFunction_createElementNS((String)namespaceURI, name);
/* 274:    */     case 2: 
/* 275:377 */       return jsxFunction_createAttribute(name);
/* 276:    */     }
/* 277:380 */     throw Context.reportRuntimeError("xmlDoc.createNode(): Unsupported type " + (short)(int)Context.toNumber(type));
/* 278:    */   }
/* 279:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDocument
 * JD-Core Version:    0.7.0.1
 */