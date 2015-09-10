/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*   8:    */ import java.net.MalformedURLException;
/*   9:    */ import java.net.URL;
/*  10:    */ import org.apache.commons.lang.StringUtils;
/*  11:    */ 
/*  12:    */ public class HTMLAnchorElement
/*  13:    */   extends HTMLElement
/*  14:    */ {
/*  15:    */   public void jsxSet_href(String href)
/*  16:    */   {
/*  17: 55 */     getDomNodeOrDie().setAttribute("href", href);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String jsxGet_href()
/*  21:    */   {
/*  22: 63 */     HtmlAnchor anchor = (HtmlAnchor)getDomNodeOrDie();
/*  23: 64 */     String hrefAttr = anchor.getHrefAttribute();
/*  24: 66 */     if (hrefAttr == DomElement.ATTRIBUTE_NOT_DEFINED) {
/*  25: 67 */       return "";
/*  26:    */     }
/*  27:    */     try
/*  28:    */     {
/*  29: 71 */       return getUrl().toString();
/*  30:    */     }
/*  31:    */     catch (MalformedURLException e) {}
/*  32: 74 */     return hrefAttr;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void jsxSet_name(String name)
/*  36:    */   {
/*  37: 83 */     getDomNodeOrDie().setAttribute("name", name);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String jsxGet_name()
/*  41:    */   {
/*  42: 91 */     return getDomNodeOrDie().getAttribute("name");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void jsxSet_target(String target)
/*  46:    */   {
/*  47: 99 */     getDomNodeOrDie().setAttribute("target", target);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String jsxGet_target()
/*  51:    */   {
/*  52:107 */     return getDomNodeOrDie().getAttribute("target");
/*  53:    */   }
/*  54:    */   
/*  55:    */   private URL getUrl()
/*  56:    */     throws MalformedURLException
/*  57:    */   {
/*  58:116 */     HtmlAnchor anchor = (HtmlAnchor)getDomNodeOrDie();
/*  59:117 */     return ((HtmlPage)anchor.getPage()).getFullyQualifiedUrl(anchor.getHrefAttribute());
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void setUrl(URL url)
/*  63:    */   {
/*  64:125 */     getDomNodeOrDie().setAttribute("href", url.toString());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void jsxSet_rel(String rel)
/*  68:    */   {
/*  69:133 */     getDomNodeOrDie().setAttribute("rel", rel);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String jsxGet_rel()
/*  73:    */     throws Exception
/*  74:    */   {
/*  75:142 */     return ((HtmlAnchor)getDomNodeOrDie()).getRelAttribute();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void jsxSet_rev(String rel)
/*  79:    */   {
/*  80:150 */     getDomNodeOrDie().setAttribute("rev", rel);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String jsxGet_rev()
/*  84:    */     throws Exception
/*  85:    */   {
/*  86:159 */     return ((HtmlAnchor)getDomNodeOrDie()).getRevAttribute();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String jsxGet_search()
/*  90:    */     throws Exception
/*  91:    */   {
/*  92:170 */     String query = getUrl().getQuery();
/*  93:171 */     if (query == null) {
/*  94:172 */       return "";
/*  95:    */     }
/*  96:174 */     return "?" + query;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void jsxSet_search(String search)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102:    */     String query;
/* 103:    */     String query;
/* 104:186 */     if ((search == null) || ("?".equals(search)) || ("".equals(search)))
/* 105:    */     {
/* 106:187 */       query = null;
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:    */       String query;
/* 111:189 */       if (search.charAt(0) == '?') {
/* 112:190 */         query = search.substring(1);
/* 113:    */       } else {
/* 114:193 */         query = search;
/* 115:    */       }
/* 116:    */     }
/* 117:196 */     setUrl(UrlUtils.getUrlWithNewQuery(getUrl(), query));
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String jsxGet_hash()
/* 121:    */     throws Exception
/* 122:    */   {
/* 123:206 */     String hash = getUrl().getRef();
/* 124:207 */     if (hash == null) {
/* 125:208 */       return "";
/* 126:    */     }
/* 127:210 */     return "#" + hash;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void jsxSet_hash(String hash)
/* 131:    */     throws Exception
/* 132:    */   {
/* 133:220 */     setUrl(UrlUtils.getUrlWithNewRef(getUrl(), hash));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String jsxGet_host()
/* 137:    */     throws Exception
/* 138:    */   {
/* 139:230 */     URL url = getUrl();
/* 140:231 */     int port = url.getPort();
/* 141:232 */     String host = url.getHost();
/* 142:234 */     if (port == -1) {
/* 143:235 */       return host;
/* 144:    */     }
/* 145:237 */     return host + ":" + port;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void jsxSet_host(String host)
/* 149:    */     throws Exception
/* 150:    */   {
/* 151:249 */     int index = host.indexOf(':');
/* 152:    */     int port;
/* 153:    */     String hostname;
/* 154:    */     int port;
/* 155:250 */     if (index != -1)
/* 156:    */     {
/* 157:251 */       String hostname = host.substring(0, index);
/* 158:252 */       port = Integer.parseInt(host.substring(index + 1));
/* 159:    */     }
/* 160:    */     else
/* 161:    */     {
/* 162:255 */       hostname = host;
/* 163:256 */       port = -1;
/* 164:    */     }
/* 165:258 */     URL url1 = UrlUtils.getUrlWithNewHost(getUrl(), hostname);
/* 166:259 */     URL url2 = UrlUtils.getUrlWithNewPort(url1, port);
/* 167:260 */     setUrl(url2);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String jsxGet_hostname()
/* 171:    */     throws Exception
/* 172:    */   {
/* 173:270 */     return getUrl().getHost();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void jsxSet_hostname(String hostname)
/* 177:    */     throws Exception
/* 178:    */   {
/* 179:280 */     setUrl(UrlUtils.getUrlWithNewHost(getUrl(), hostname));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String jsxGet_pathname()
/* 183:    */     throws Exception
/* 184:    */   {
/* 185:290 */     return getUrl().getPath();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void jsxSet_pathname(String pathname)
/* 189:    */     throws Exception
/* 190:    */   {
/* 191:300 */     setUrl(UrlUtils.getUrlWithNewPath(getUrl(), pathname));
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String jsxGet_port()
/* 195:    */     throws Exception
/* 196:    */   {
/* 197:310 */     int port = getUrl().getPort();
/* 198:311 */     if (port == -1) {
/* 199:312 */       return "";
/* 200:    */     }
/* 201:314 */     return Integer.toString(port);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void jsxSet_port(String port)
/* 205:    */     throws Exception
/* 206:    */   {
/* 207:324 */     setUrl(UrlUtils.getUrlWithNewPort(getUrl(), Integer.parseInt(port)));
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String jsxGet_protocol()
/* 211:    */     throws Exception
/* 212:    */   {
/* 213:334 */     return getUrl().getProtocol() + ":";
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void jsxSet_protocol(String protocol)
/* 217:    */     throws Exception
/* 218:    */   {
/* 219:344 */     String bareProtocol = StringUtils.substringBefore(protocol, ":");
/* 220:345 */     setUrl(UrlUtils.getUrlWithNewProtocol(getUrl(), bareProtocol));
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Object getDefaultValue(Class<?> hint)
/* 224:    */   {
/* 225:356 */     HtmlElement element = getDomNodeOrNull();
/* 226:357 */     if (element == null) {
/* 227:358 */       return super.getDefaultValue(null);
/* 228:    */     }
/* 229:360 */     return getDefaultValue(element);
/* 230:    */   }
/* 231:    */   
/* 232:    */   static String getDefaultValue(HtmlElement element)
/* 233:    */   {
/* 234:364 */     String href = element.getAttribute("href");
/* 235:366 */     if (DomElement.ATTRIBUTE_NOT_DEFINED == href) {
/* 236:367 */       return "";
/* 237:    */     }
/* 238:370 */     href = href.trim();
/* 239:371 */     int indexAnchor = href.indexOf('#');
/* 240:    */     String anchorPart;
/* 241:    */     String beforeAnchor;
/* 242:    */     String anchorPart;
/* 243:374 */     if (indexAnchor == -1)
/* 244:    */     {
/* 245:375 */       String beforeAnchor = href;
/* 246:376 */       anchorPart = "";
/* 247:    */     }
/* 248:    */     else
/* 249:    */     {
/* 250:379 */       beforeAnchor = href.substring(0, indexAnchor);
/* 251:380 */       anchorPart = href.substring(indexAnchor);
/* 252:    */     }
/* 253:    */     try
/* 254:    */     {
/* 255:384 */       return ((HtmlPage)element.getPage()).getFullyQualifiedUrl(beforeAnchor).toExternalForm() + anchorPart;
/* 256:    */     }
/* 257:    */     catch (MalformedURLException e) {}
/* 258:389 */     return href;
/* 259:    */   }
/* 260:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLAnchorElement
 * JD-Core Version:    0.7.0.1
 */