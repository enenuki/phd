/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.Page;
/*   4:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.Node;
/*  12:    */ import java.io.File;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.imageio.ImageIO;
/*  18:    */ import javax.imageio.ImageReader;
/*  19:    */ import javax.imageio.stream.ImageInputStream;
/*  20:    */ import org.apache.commons.logging.Log;
/*  21:    */ import org.apache.commons.logging.LogFactory;
/*  22:    */ 
/*  23:    */ public class HtmlImage
/*  24:    */   extends HtmlElement
/*  25:    */ {
/*  26: 52 */   private static final Log LOG = LogFactory.getLog(HtmlImage.class);
/*  27:    */   public static final String TAG_NAME = "img";
/*  28:    */   private int lastClickX_;
/*  29:    */   private int lastClickY_;
/*  30:    */   private WebResponse imageWebResponse_;
/*  31:    */   private ImageReader imageReader_;
/*  32:    */   private boolean downloaded_;
/*  33:    */   private boolean onloadInvoked_;
/*  34:    */   
/*  35:    */   HtmlImage(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  36:    */   {
/*  37: 74 */     super(namespaceURI, qualifiedName, page, attributes);
/*  38:    */   }
/*  39:    */   
/*  40:    */   protected void onAddedToPage()
/*  41:    */   {
/*  42: 82 */     doOnLoad();
/*  43: 83 */     super.onAddedToPage();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
/*  47:    */   {
/*  48: 91 */     if (("src".equals(qualifiedName)) && (value != ATTRIBUTE_NOT_DEFINED) && ((getPage() instanceof HtmlPage)))
/*  49:    */     {
/*  50: 92 */       String oldValue = getAttributeNS(namespaceURI, qualifiedName);
/*  51: 93 */       if (!oldValue.equals(value))
/*  52:    */       {
/*  53: 95 */         this.onloadInvoked_ = false;
/*  54: 96 */         this.downloaded_ = false;
/*  55:    */       }
/*  56:    */     }
/*  57: 99 */     super.setAttributeNS(namespaceURI, qualifiedName, value);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void doOnLoad()
/*  61:    */   {
/*  62:118 */     if (!(getPage() instanceof HtmlPage)) {
/*  63:119 */       return;
/*  64:    */     }
/*  65:121 */     if (this.onloadInvoked_) {
/*  66:122 */       return;
/*  67:    */     }
/*  68:124 */     this.onloadInvoked_ = true;
/*  69:125 */     HtmlPage htmlPage = (HtmlPage)getPage();
/*  70:126 */     if (hasEventHandlers("onload"))
/*  71:    */     {
/*  72:    */       boolean ok;
/*  73:    */       try
/*  74:    */       {
/*  75:130 */         downloadImageIfNeeded();
/*  76:131 */         int i = this.imageWebResponse_.getStatusCode();
/*  77:132 */         ok = ((i >= 200) && (i < 300)) || (i == 305);
/*  78:    */       }
/*  79:    */       catch (IOException e)
/*  80:    */       {
/*  81:135 */         ok = false;
/*  82:    */       }
/*  83:138 */       if (ok)
/*  84:    */       {
/*  85:139 */         final Event event = new Event(this, "load");
/*  86:140 */         final Node scriptObject = (Node)getScriptObject();
/*  87:141 */         PostponedAction action = new PostponedAction(getPage())
/*  88:    */         {
/*  89:    */           public void execute()
/*  90:    */             throws Exception
/*  91:    */           {
/*  92:144 */             scriptObject.executeEvent(event);
/*  93:    */           }
/*  94:146 */         };
/*  95:147 */         String readyState = htmlPage.getReadyState();
/*  96:148 */         if ("loading".equals(readyState)) {
/*  97:149 */           htmlPage.addAfterLoadAction(action);
/*  98:    */         } else {
/*  99:152 */           htmlPage.getWebClient().getJavaScriptEngine().addPostponedAction(action);
/* 100:    */         }
/* 101:    */       }
/* 102:156 */       else if (LOG.isDebugEnabled())
/* 103:    */       {
/* 104:157 */         LOG.debug("Unable to download image for tag " + this + "; not firing onload event.");
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public final String getSrcAttribute()
/* 110:    */   {
/* 111:171 */     return getAttribute("src");
/* 112:    */   }
/* 113:    */   
/* 114:    */   public final String getAltAttribute()
/* 115:    */   {
/* 116:182 */     return getAttribute("alt");
/* 117:    */   }
/* 118:    */   
/* 119:    */   public final String getNameAttribute()
/* 120:    */   {
/* 121:193 */     return getAttribute("name");
/* 122:    */   }
/* 123:    */   
/* 124:    */   public final String getLongDescAttribute()
/* 125:    */   {
/* 126:204 */     return getAttribute("longdesc");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final String getHeightAttribute()
/* 130:    */   {
/* 131:215 */     return getAttribute("height");
/* 132:    */   }
/* 133:    */   
/* 134:    */   public final String getWidthAttribute()
/* 135:    */   {
/* 136:226 */     return getAttribute("width");
/* 137:    */   }
/* 138:    */   
/* 139:    */   public final String getUseMapAttribute()
/* 140:    */   {
/* 141:237 */     return getAttribute("usemap");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public final String getIsmapAttribute()
/* 145:    */   {
/* 146:248 */     return getAttribute("ismap");
/* 147:    */   }
/* 148:    */   
/* 149:    */   public final String getAlignAttribute()
/* 150:    */   {
/* 151:259 */     return getAttribute("align");
/* 152:    */   }
/* 153:    */   
/* 154:    */   public final String getBorderAttribute()
/* 155:    */   {
/* 156:270 */     return getAttribute("border");
/* 157:    */   }
/* 158:    */   
/* 159:    */   public final String getHspaceAttribute()
/* 160:    */   {
/* 161:281 */     return getAttribute("hspace");
/* 162:    */   }
/* 163:    */   
/* 164:    */   public final String getVspaceAttribute()
/* 165:    */   {
/* 166:292 */     return getAttribute("vspace");
/* 167:    */   }
/* 168:    */   
/* 169:    */   public int getHeight()
/* 170:    */     throws IOException
/* 171:    */   {
/* 172:304 */     readImageIfNeeded();
/* 173:305 */     return this.imageReader_.getHeight(0);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getWidth()
/* 177:    */     throws IOException
/* 178:    */   {
/* 179:317 */     readImageIfNeeded();
/* 180:318 */     return this.imageReader_.getWidth(0);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public ImageReader getImageReader()
/* 184:    */     throws IOException
/* 185:    */   {
/* 186:330 */     readImageIfNeeded();
/* 187:331 */     return this.imageReader_;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public WebResponse getWebResponse(boolean downloadIfNeeded)
/* 191:    */     throws IOException
/* 192:    */   {
/* 193:346 */     if (downloadIfNeeded) {
/* 194:347 */       downloadImageIfNeeded();
/* 195:    */     }
/* 196:349 */     return this.imageWebResponse_;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private void downloadImageIfNeeded()
/* 200:    */     throws IOException
/* 201:    */   {
/* 202:360 */     if (!this.downloaded_)
/* 203:    */     {
/* 204:361 */       HtmlPage page = (HtmlPage)getPage();
/* 205:362 */       WebClient webclient = page.getWebClient();
/* 206:    */       
/* 207:364 */       URL url = page.getFullyQualifiedUrl(getSrcAttribute());
/* 208:365 */       WebRequest request = new WebRequest(url);
/* 209:366 */       request.setAdditionalHeader("Referer", page.getWebResponse().getWebRequest().getUrl().toExternalForm());
/* 210:367 */       this.imageWebResponse_ = webclient.loadWebResponse(request);
/* 211:368 */       this.imageReader_ = null;
/* 212:369 */       this.downloaded_ = true;
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void readImageIfNeeded()
/* 217:    */     throws IOException
/* 218:    */   {
/* 219:374 */     downloadImageIfNeeded();
/* 220:375 */     if (this.imageReader_ == null)
/* 221:    */     {
/* 222:376 */       ImageInputStream iis = ImageIO.createImageInputStream(this.imageWebResponse_.getContentAsStream());
/* 223:377 */       Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
/* 224:378 */       if (!iter.hasNext()) {
/* 225:379 */         throw new IOException("No image detected in response");
/* 226:    */       }
/* 227:381 */       this.imageReader_ = ((ImageReader)iter.next());
/* 228:382 */       this.imageReader_.setInput(iis);
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Page click(int x, int y)
/* 233:    */     throws IOException
/* 234:    */   {
/* 235:398 */     this.lastClickX_ = x;
/* 236:399 */     this.lastClickY_ = y;
/* 237:400 */     return super.click();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Page click()
/* 241:    */     throws IOException
/* 242:    */   {
/* 243:414 */     return click(0, 0);
/* 244:    */   }
/* 245:    */   
/* 246:    */   protected void doClickAction()
/* 247:    */     throws IOException
/* 248:    */   {
/* 249:423 */     if (getUseMapAttribute() != ATTRIBUTE_NOT_DEFINED)
/* 250:    */     {
/* 251:425 */       String mapName = getUseMapAttribute().substring(1);
/* 252:426 */       HtmlElement doc = ((HtmlPage)getPage()).getDocumentElement();
/* 253:427 */       HtmlMap map = (HtmlMap)doc.getOneHtmlElementByAttribute("map", "name", mapName);
/* 254:428 */       for (HtmlElement element : map.getChildElements()) {
/* 255:429 */         if ((element instanceof HtmlArea))
/* 256:    */         {
/* 257:430 */           HtmlArea area = (HtmlArea)element;
/* 258:431 */           if (area.containsPoint(this.lastClickX_, this.lastClickY_))
/* 259:    */           {
/* 260:432 */             area.doClickAction();
/* 261:433 */             return;
/* 262:    */           }
/* 263:    */         }
/* 264:    */       }
/* 265:    */     }
/* 266:438 */     HtmlAnchor anchor = (HtmlAnchor)getEnclosingElement("a");
/* 267:439 */     if (anchor == null) {
/* 268:440 */       return;
/* 269:    */     }
/* 270:442 */     if (getIsmapAttribute() != ATTRIBUTE_NOT_DEFINED)
/* 271:    */     {
/* 272:443 */       String suffix = "?" + this.lastClickX_ + "," + this.lastClickY_;
/* 273:444 */       anchor.doClickAction(suffix);
/* 274:445 */       return;
/* 275:    */     }
/* 276:447 */     anchor.doClickAction();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void saveAs(File file)
/* 280:    */     throws IOException
/* 281:    */   {
/* 282:456 */     ImageReader reader = getImageReader();
/* 283:457 */     ImageIO.write(reader.read(0), reader.getFormatName(), file);
/* 284:    */   }
/* 285:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlImage
 * JD-Core Version:    0.7.0.1
 */