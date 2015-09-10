/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.applets.AppletClassLoader;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.applets.AppletStubImpl;
/*   9:    */ import java.applet.Applet;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.net.URL;
/*  12:    */ import java.util.Map;
/*  13:    */ 
/*  14:    */ public class HtmlApplet
/*  15:    */   extends HtmlElement
/*  16:    */ {
/*  17:    */   public static final String TAG_NAME = "applet";
/*  18:    */   private boolean downloaded_;
/*  19:    */   private WebResponse appletWebResponse_;
/*  20:    */   private Applet applet_;
/*  21:    */   private AppletClassLoader appletClassLoader_;
/*  22:    */   
/*  23:    */   HtmlApplet(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  24:    */   {
/*  25: 59 */     super(namespaceURI, qualifiedName, page, attributes);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public final String getCodebaseAttribute()
/*  29:    */   {
/*  30: 70 */     return getAttribute("codebase");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public final String getArchiveAttribute()
/*  34:    */   {
/*  35: 81 */     return getAttribute("archive");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public final String getCodeAttribute()
/*  39:    */   {
/*  40: 92 */     return getAttribute("code");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final String getObjectAttribute()
/*  44:    */   {
/*  45:103 */     return getAttribute("object");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final String getAltAttribute()
/*  49:    */   {
/*  50:114 */     return getAttribute("alt");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final String getNameAttribute()
/*  54:    */   {
/*  55:125 */     return getAttribute("name");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final String getWidthAttribute()
/*  59:    */   {
/*  60:136 */     return getAttribute("width");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final String getHeightAttribute()
/*  64:    */   {
/*  65:147 */     return getAttribute("height");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final String getAlignAttribute()
/*  69:    */   {
/*  70:158 */     return getAttribute("align");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final String getHspaceAttribute()
/*  74:    */   {
/*  75:169 */     return getAttribute("hspace");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final String getVspaceAttribute()
/*  79:    */   {
/*  80:180 */     return getAttribute("vspace");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Applet getApplet()
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:189 */     downloadContentIfNeeded();
/*  87:190 */     return this.applet_;
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void downloadContentIfNeeded()
/*  91:    */     throws IOException
/*  92:    */   {
/*  93:200 */     if (!this.downloaded_)
/*  94:    */     {
/*  95:201 */       HtmlPage page = (HtmlPage)getPage();
/*  96:202 */       WebClient webclient = page.getWebClient();
/*  97:    */       
/*  98:204 */       String src = getArchiveAttribute();
/*  99:205 */       URL url = page.getFullyQualifiedUrl(src);
/* 100:206 */       this.appletWebResponse_ = webclient.loadWebResponse(new WebRequest(url));
/* 101:    */       
/* 102:208 */       this.downloaded_ = true;
/* 103:    */     }
/* 104:211 */     this.appletClassLoader_ = new AppletClassLoader();
/* 105:212 */     this.appletClassLoader_.addToClassPath(this.appletWebResponse_);
/* 106:    */     
/* 107:    */ 
/* 108:215 */     String appletClassName = getCodeAttribute();
/* 109:    */     try
/* 110:    */     {
/* 111:217 */       Class<Applet> appletClass = this.appletClassLoader_.loadClass(appletClassName);
/* 112:218 */       this.applet_ = ((Applet)appletClass.newInstance());
/* 113:219 */       this.applet_.setStub(new AppletStubImpl(this));
/* 114:220 */       this.applet_.init();
/* 115:221 */       this.applet_.start();
/* 116:    */     }
/* 117:    */     catch (ClassNotFoundException e)
/* 118:    */     {
/* 119:224 */       throw new RuntimeException(e);
/* 120:    */     }
/* 121:    */     catch (InstantiationException e)
/* 122:    */     {
/* 123:227 */       throw new RuntimeException(e);
/* 124:    */     }
/* 125:    */     catch (IllegalAccessException e)
/* 126:    */     {
/* 127:230 */       throw new RuntimeException(e);
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlApplet
 * JD-Core Version:    0.7.0.1
 */