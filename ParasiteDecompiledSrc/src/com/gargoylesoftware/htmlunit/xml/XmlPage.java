/*   1:    */ package com.gargoylesoftware.htmlunit.xml;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomCDataSection;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentType;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.DomProcessingInstruction;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import javax.xml.parsers.DocumentBuilder;
/*  14:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  15:    */ import javax.xml.parsers.ParserConfigurationException;
/*  16:    */ import org.apache.commons.lang.StringUtils;
/*  17:    */ import org.apache.commons.logging.Log;
/*  18:    */ import org.apache.commons.logging.LogFactory;
/*  19:    */ import org.w3c.dom.Attr;
/*  20:    */ import org.w3c.dom.Comment;
/*  21:    */ import org.w3c.dom.DOMConfiguration;
/*  22:    */ import org.w3c.dom.DOMImplementation;
/*  23:    */ import org.w3c.dom.Document;
/*  24:    */ import org.w3c.dom.DocumentFragment;
/*  25:    */ import org.w3c.dom.Element;
/*  26:    */ import org.w3c.dom.EntityReference;
/*  27:    */ import org.w3c.dom.Node;
/*  28:    */ import org.w3c.dom.NodeList;
/*  29:    */ import org.w3c.dom.Text;
/*  30:    */ import org.xml.sax.SAXException;
/*  31:    */ 
/*  32:    */ public class XmlPage
/*  33:    */   extends SgmlPage
/*  34:    */ {
/*  35: 58 */   private static final Log LOG = LogFactory.getLog(XmlPage.class);
/*  36:    */   private Node node_;
/*  37:    */   
/*  38:    */   public XmlPage(WebResponse webResponse, WebWindow enclosingWindow)
/*  39:    */     throws IOException
/*  40:    */   {
/*  41: 72 */     this(webResponse, enclosingWindow, true);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public XmlPage(Node node, WebWindow enclosingWindow)
/*  45:    */   {
/*  46: 84 */     super(null, enclosingWindow);
/*  47: 85 */     this.node_ = node;
/*  48: 86 */     if (this.node_ != null) {
/*  49: 87 */       XmlUtil.appendChild(this, this, this.node_);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public XmlPage(WebResponse webResponse, WebWindow enclosingWindow, boolean ignoreSAXException)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:103 */     super(webResponse, enclosingWindow);
/*  57:    */     try
/*  58:    */     {
/*  59:106 */       if ((webResponse == null) || (StringUtils.isBlank(webResponse.getContentAsString()))) {
/*  60:107 */         this.node_ = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().getDocumentElement();
/*  61:    */       } else {
/*  62:    */         try
/*  63:    */         {
/*  64:111 */           this.node_ = XmlUtil.buildDocument(webResponse).getDocumentElement();
/*  65:    */         }
/*  66:    */         catch (SAXException e)
/*  67:    */         {
/*  68:114 */           LOG.warn("Failed parsing XML document " + webResponse.getWebRequest().getUrl() + ": " + e.getMessage());
/*  69:116 */           if (!ignoreSAXException) {
/*  70:117 */             throw new IOException(e.getMessage());
/*  71:    */           }
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:    */     catch (ParserConfigurationException e)
/*  76:    */     {
/*  77:123 */       if (null == webResponse) {
/*  78:124 */         LOG.warn("Failed parsing XML empty document: " + e.getMessage());
/*  79:    */       } else {
/*  80:127 */         LOG.warn("Failed parsing XML empty document " + webResponse.getWebRequest().getUrl() + ": " + e.getMessage());
/*  81:    */       }
/*  82:    */     }
/*  83:132 */     if (this.node_ != null) {
/*  84:133 */       XmlUtil.appendChild(this, this, this.node_);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean hasCaseSensitiveTagNames()
/*  89:    */   {
/*  90:142 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getContent()
/*  94:    */   {
/*  95:150 */     return getWebResponse().getContentAsString();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Document getXmlDocument()
/*  99:    */   {
/* 100:158 */     if (this.node_ != null) {
/* 101:159 */       return this.node_.getOwnerDocument();
/* 102:    */     }
/* 103:161 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public DomElement createXmlElement(String tagName)
/* 107:    */   {
/* 108:171 */     return createXmlElementNS(null, tagName);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public DomElement createXmlElementNS(String namespaceURI, String qualifiedName)
/* 112:    */   {
/* 113:182 */     return new DomElement(namespaceURI, qualifiedName, this, new HashMap());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Node adoptNode(Node source)
/* 117:    */   {
/* 118:190 */     throw new UnsupportedOperationException("XmlPage.adoptNode is not yet implemented.");
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Attr createAttributeNS(String namespaceURI, String qualifiedName)
/* 122:    */   {
/* 123:198 */     throw new UnsupportedOperationException("XmlPage.createAttributeNS is not yet implemented.");
/* 124:    */   }
/* 125:    */   
/* 126:    */   public DomCDataSection createCDATASection(String data)
/* 127:    */   {
/* 128:205 */     return new DomCDataSection(this, data);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Comment createComment(String data)
/* 132:    */   {
/* 133:213 */     throw new UnsupportedOperationException("XmlPage.createComment is not yet implemented.");
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DocumentFragment createDocumentFragment()
/* 137:    */   {
/* 138:221 */     throw new UnsupportedOperationException("XmlPage.createDocumentFragment is not yet implemented.");
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Element createElement(String tagName)
/* 142:    */   {
/* 143:229 */     return createXmlElement(tagName);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Element createElementNS(String namespaceURI, String qualifiedName)
/* 147:    */   {
/* 148:238 */     throw new UnsupportedOperationException("XmlPage.createElementNS is not yet implemented.");
/* 149:    */   }
/* 150:    */   
/* 151:    */   public EntityReference createEntityReference(String name)
/* 152:    */   {
/* 153:246 */     throw new UnsupportedOperationException("XmlPage.createEntityReference is not yet implemented.");
/* 154:    */   }
/* 155:    */   
/* 156:    */   public DomProcessingInstruction createProcessingInstruction(String target, String data)
/* 157:    */   {
/* 158:253 */     return new DomProcessingInstruction(this, target, data);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Text createTextNode(String data)
/* 162:    */   {
/* 163:261 */     throw new UnsupportedOperationException("XmlPage.createTextNode is not yet implemented.");
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getDocumentURI()
/* 167:    */   {
/* 168:269 */     throw new UnsupportedOperationException("XmlPage.getDocumentURI is not yet implemented.");
/* 169:    */   }
/* 170:    */   
/* 171:    */   public DOMConfiguration getDomConfig()
/* 172:    */   {
/* 173:277 */     throw new UnsupportedOperationException("XmlPage.getDomConfig is not yet implemented.");
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Element getElementById(String elementId)
/* 177:    */   {
/* 178:285 */     throw new UnsupportedOperationException("XmlPage.getElementById is not yet implemented.");
/* 179:    */   }
/* 180:    */   
/* 181:    */   public NodeList getElementsByTagName(String tagname)
/* 182:    */   {
/* 183:293 */     throw new UnsupportedOperationException("XmlPage.getElementsByTagName is not yet implemented.");
/* 184:    */   }
/* 185:    */   
/* 186:    */   public NodeList getElementsByTagNameNS(String namespace, String name)
/* 187:    */   {
/* 188:301 */     throw new UnsupportedOperationException("XmlPage.getElementsByTagNameNS is not yet implemented.");
/* 189:    */   }
/* 190:    */   
/* 191:    */   public DOMImplementation getImplementation()
/* 192:    */   {
/* 193:309 */     throw new UnsupportedOperationException("XmlPage.getImplementation is not yet implemented.");
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String getInputEncoding()
/* 197:    */   {
/* 198:317 */     throw new UnsupportedOperationException("XmlPage.getInputEncoding is not yet implemented.");
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean getStrictErrorChecking()
/* 202:    */   {
/* 203:325 */     throw new UnsupportedOperationException("XmlPage.getStrictErrorChecking is not yet implemented.");
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getXmlEncoding()
/* 207:    */   {
/* 208:333 */     throw new UnsupportedOperationException("XmlPage.getXmlEncoding is not yet implemented.");
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean getXmlStandalone()
/* 212:    */   {
/* 213:341 */     throw new UnsupportedOperationException("XmlPage.getXmlStandalone is not yet implemented.");
/* 214:    */   }
/* 215:    */   
/* 216:    */   public String getXmlVersion()
/* 217:    */   {
/* 218:349 */     throw new UnsupportedOperationException("XmlPage.getXmlVersion is not yet implemented.");
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Node importNode(Node importedNode, boolean deep)
/* 222:    */   {
/* 223:357 */     throw new UnsupportedOperationException("XmlPage.importNode is not yet implemented.");
/* 224:    */   }
/* 225:    */   
/* 226:    */   public Node renameNode(Node n, String namespaceURI, String qualifiedName)
/* 227:    */   {
/* 228:365 */     throw new UnsupportedOperationException("XmlPage.renameNode is not yet implemented.");
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setDocumentURI(String documentURI)
/* 232:    */   {
/* 233:373 */     throw new UnsupportedOperationException("XmlPage.setDocumentURI is not yet implemented.");
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setStrictErrorChecking(boolean strictErrorChecking)
/* 237:    */   {
/* 238:381 */     throw new UnsupportedOperationException("XmlPage.setStrictErrorChecking is not yet implemented.");
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setXmlStandalone(boolean xmlStandalone)
/* 242:    */   {
/* 243:389 */     throw new UnsupportedOperationException("XmlPage.setXmlStandalone is not yet implemented.");
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setXmlVersion(String xmlVersion)
/* 247:    */   {
/* 248:397 */     throw new UnsupportedOperationException("XmlPage.setXmlVersion is not yet implemented.");
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getPageEncoding()
/* 252:    */   {
/* 253:406 */     throw new UnsupportedOperationException("XmlPage.getPageEncoding is not yet implemented.");
/* 254:    */   }
/* 255:    */   
/* 256:    */   protected void setDocumentType(DomDocumentType type)
/* 257:    */   {
/* 258:414 */     super.setDocumentType(type);
/* 259:    */   }
/* 260:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.xml.XmlPage
 * JD-Core Version:    0.7.0.1
 */