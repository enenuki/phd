/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   8:    */ import java.awt.geom.Ellipse2D;
/*   9:    */ import java.awt.geom.Ellipse2D.Double;
/*  10:    */ import java.awt.geom.GeneralPath;
/*  11:    */ import java.awt.geom.Rectangle2D;
/*  12:    */ import java.awt.geom.Rectangle2D.Double;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.net.MalformedURLException;
/*  15:    */ import java.net.URL;
/*  16:    */ import java.util.Map;
/*  17:    */ import org.apache.commons.lang.StringUtils;
/*  18:    */ 
/*  19:    */ public class HtmlArea
/*  20:    */   extends HtmlElement
/*  21:    */ {
/*  22:    */   public static final String TAG_NAME = "area";
/*  23:    */   
/*  24:    */   HtmlArea(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  25:    */   {
/*  26: 58 */     super(namespaceURI, qualifiedName, page, attributes);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void doClickAction()
/*  30:    */     throws IOException
/*  31:    */   {
/*  32: 66 */     HtmlPage enclosingPage = (HtmlPage)getPage();
/*  33: 67 */     WebClient webClient = enclosingPage.getWebClient();
/*  34:    */     
/*  35: 69 */     String href = getHrefAttribute().trim();
/*  36: 70 */     if (href.length() > 0)
/*  37:    */     {
/*  38: 71 */       HtmlPage page = (HtmlPage)getPage();
/*  39: 72 */       if (StringUtils.startsWithIgnoreCase(href, "javascript:"))
/*  40:    */       {
/*  41: 73 */         page.executeJavaScriptIfPossible(href, "javascript url", getStartLineNumber()); return;
/*  42:    */       }
/*  43:    */       URL url;
/*  44:    */       try
/*  45:    */       {
/*  46: 79 */         url = enclosingPage.getFullyQualifiedUrl(getHrefAttribute());
/*  47:    */       }
/*  48:    */       catch (MalformedURLException e)
/*  49:    */       {
/*  50: 82 */         throw new IllegalStateException("Not a valid url: " + getHrefAttribute());
/*  51:    */       }
/*  52: 85 */       WebRequest request = new WebRequest(url);
/*  53: 86 */       request.setAdditionalHeader("Referer", page.getWebResponse().getWebRequest().getUrl().toExternalForm());
/*  54: 87 */       WebWindow webWindow = enclosingPage.getEnclosingWindow();
/*  55: 88 */       webClient.getPage(webWindow, enclosingPage.getResolvedTarget(getTargetAttribute()), request);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final String getShapeAttribute()
/*  60:    */   {
/*  61:103 */     return getAttribute("shape");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final String getCoordsAttribute()
/*  65:    */   {
/*  66:114 */     return getAttribute("coords");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final String getHrefAttribute()
/*  70:    */   {
/*  71:125 */     return getAttribute("href");
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final String getNoHrefAttribute()
/*  75:    */   {
/*  76:136 */     return getAttribute("nohref");
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final String getAltAttribute()
/*  80:    */   {
/*  81:147 */     return getAttribute("alt");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public final String getTabIndexAttribute()
/*  85:    */   {
/*  86:158 */     return getAttribute("tabindex");
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final String getAccessKeyAttribute()
/*  90:    */   {
/*  91:169 */     return getAttribute("accesskey");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final String getOnFocusAttribute()
/*  95:    */   {
/*  96:180 */     return getAttribute("onfocus");
/*  97:    */   }
/*  98:    */   
/*  99:    */   public final String getOnBlurAttribute()
/* 100:    */   {
/* 101:191 */     return getAttribute("onblur");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final String getTargetAttribute()
/* 105:    */   {
/* 106:202 */     return getAttribute("target");
/* 107:    */   }
/* 108:    */   
/* 109:    */   boolean containsPoint(int x, int y)
/* 110:    */   {
/* 111:212 */     String shape = StringUtils.defaultIfEmpty(getShapeAttribute(), "rect").toLowerCase();
/* 112:214 */     if (("default".equals(shape)) && (getCoordsAttribute() != null)) {
/* 113:215 */       return true;
/* 114:    */     }
/* 115:217 */     if (("rect".equals(shape)) && (getCoordsAttribute() != null))
/* 116:    */     {
/* 117:218 */       String[] coords = StringUtils.split(getCoordsAttribute(), ',');
/* 118:219 */       double leftX = Double.parseDouble(coords[0].trim());
/* 119:220 */       double topY = Double.parseDouble(coords[1].trim());
/* 120:221 */       double rightX = Double.parseDouble(coords[2].trim());
/* 121:222 */       double bottomY = Double.parseDouble(coords[3].trim());
/* 122:223 */       Rectangle2D rectangle = new Rectangle2D.Double(leftX, topY, rightX - leftX + 1.0D, bottomY - topY + 1.0D);
/* 123:225 */       if (rectangle.contains(x, y)) {
/* 124:226 */         return true;
/* 125:    */       }
/* 126:    */     }
/* 127:229 */     else if (("circle".equals(shape)) && (getCoordsAttribute() != null))
/* 128:    */     {
/* 129:230 */       String[] coords = StringUtils.split(getCoordsAttribute(), ',');
/* 130:231 */       double centerX = Double.parseDouble(coords[0].trim());
/* 131:232 */       double centerY = Double.parseDouble(coords[1].trim());
/* 132:233 */       String radiusString = coords[2].trim();
/* 133:    */       int radius;
/* 134:    */       try
/* 135:    */       {
/* 136:237 */         radius = Integer.parseInt(radiusString);
/* 137:    */       }
/* 138:    */       catch (NumberFormatException nfe)
/* 139:    */       {
/* 140:240 */         throw new NumberFormatException("Circle radius of " + radiusString + " is not yet implemented.");
/* 141:    */       }
/* 142:242 */       Ellipse2D ellipse = new Ellipse2D.Double(centerX - radius / 2.0D, centerY - radius / 2.0D, radius, radius);
/* 143:244 */       if (ellipse.contains(x, y)) {
/* 144:245 */         return true;
/* 145:    */       }
/* 146:    */     }
/* 147:248 */     else if (("poly".equals(shape)) && (getCoordsAttribute() != null))
/* 148:    */     {
/* 149:249 */       String[] coords = StringUtils.split(getCoordsAttribute(), ',');
/* 150:250 */       GeneralPath path = new GeneralPath();
/* 151:251 */       for (int i = 0; i + 1 < coords.length; i += 2) {
/* 152:252 */         if (i == 0) {
/* 153:253 */           path.moveTo(Float.parseFloat(coords[i]), Float.parseFloat(coords[(i + 1)]));
/* 154:    */         } else {
/* 155:256 */           path.lineTo(Float.parseFloat(coords[i]), Float.parseFloat(coords[(i + 1)]));
/* 156:    */         }
/* 157:    */       }
/* 158:259 */       path.closePath();
/* 159:260 */       if (path.contains(x, y)) {
/* 160:261 */         return true;
/* 161:    */       }
/* 162:    */     }
/* 163:265 */     return false;
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlArea
 * JD-Core Version:    0.7.0.1
 */