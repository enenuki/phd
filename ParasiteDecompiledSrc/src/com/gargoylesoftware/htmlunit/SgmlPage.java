/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentType;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.net.URL;
/*  10:    */ import org.w3c.dom.Document;
/*  11:    */ import org.w3c.dom.DocumentType;
/*  12:    */ import org.w3c.dom.Element;
/*  13:    */ 
/*  14:    */ public abstract class SgmlPage
/*  15:    */   extends DomNode
/*  16:    */   implements Page, Document
/*  17:    */ {
/*  18:    */   private DomDocumentType documentType_;
/*  19:    */   private final WebResponse webResponse_;
/*  20:    */   private WebWindow enclosingWindow_;
/*  21:    */   private final WebClient webClient_;
/*  22:    */   
/*  23:    */   public SgmlPage(WebResponse webResponse, WebWindow webWindow)
/*  24:    */   {
/*  25: 51 */     super(null);
/*  26: 52 */     this.webResponse_ = webResponse;
/*  27: 53 */     this.enclosingWindow_ = webWindow;
/*  28: 54 */     this.webClient_ = webWindow.getWebClient();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void cleanUp()
/*  32:    */     throws IOException
/*  33:    */   {}
/*  34:    */   
/*  35:    */   public WebResponse getWebResponse()
/*  36:    */   {
/*  37: 67 */     return this.webResponse_;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void initialize()
/*  41:    */     throws IOException
/*  42:    */   {}
/*  43:    */   
/*  44:    */   public String getNodeName()
/*  45:    */   {
/*  46: 82 */     return "#document";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public short getNodeType()
/*  50:    */   {
/*  51: 91 */     return 9;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public WebWindow getEnclosingWindow()
/*  55:    */   {
/*  56:100 */     return this.enclosingWindow_;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setEnclosingWindow(WebWindow window)
/*  60:    */   {
/*  61:109 */     this.enclosingWindow_ = window;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public WebClient getWebClient()
/*  65:    */   {
/*  66:118 */     return this.webClient_;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DomDocumentFragment createDomDocumentFragment()
/*  70:    */   {
/*  71:126 */     return new DomDocumentFragment(this);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final DocumentType getDoctype()
/*  75:    */   {
/*  76:134 */     return this.documentType_;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected void setDocumentType(DomDocumentType type)
/*  80:    */   {
/*  81:142 */     this.documentType_ = type;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public SgmlPage getPage()
/*  85:    */   {
/*  86:150 */     return this;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public abstract Element createElement(String paramString);
/*  90:    */   
/*  91:    */   public abstract Element createElementNS(String paramString1, String paramString2);
/*  92:    */   
/*  93:    */   public abstract String getPageEncoding();
/*  94:    */   
/*  95:    */   public DomElement getDocumentElement()
/*  96:    */   {
/*  97:179 */     DomNode childNode = getFirstChild();
/*  98:180 */     while ((childNode != null) && (!(childNode instanceof DomElement))) {
/*  99:181 */       childNode = childNode.getNextSibling();
/* 100:    */     }
/* 101:183 */     return (DomElement)childNode;
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected SgmlPage clone()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:193 */       return (SgmlPage)super.clone();
/* 109:    */     }
/* 110:    */     catch (CloneNotSupportedException e)
/* 111:    */     {
/* 112:197 */       throw new IllegalStateException("Clone not supported");
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String asXml()
/* 117:    */   {
/* 118:206 */     return getDocumentElement().asXml();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public abstract boolean hasCaseSensitiveTagNames();
/* 122:    */   
/* 123:    */   public void normalizeDocument()
/* 124:    */   {
/* 125:221 */     getDocumentElement().normalize();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getCanonicalXPath()
/* 129:    */   {
/* 130:229 */     return "/";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public DomAttr createAttribute(String name)
/* 134:    */   {
/* 135:236 */     return new DomAttr(getPage(), null, name, "", false);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public URL getUrl()
/* 139:    */   {
/* 140:244 */     return getWebResponse().getWebRequest().getUrl();
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.SgmlPage
 * JD-Core Version:    0.7.0.1
 */