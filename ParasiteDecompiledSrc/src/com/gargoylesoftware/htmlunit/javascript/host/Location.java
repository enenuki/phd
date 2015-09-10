/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.History;
/*   6:    */ import com.gargoylesoftware.htmlunit.Page;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  13:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.net.MalformedURLException;
/*  16:    */ import java.net.URL;
/*  17:    */ import java.util.regex.Matcher;
/*  18:    */ import java.util.regex.Pattern;
/*  19:    */ import org.apache.commons.lang.StringUtils;
/*  20:    */ import org.apache.commons.logging.Log;
/*  21:    */ import org.apache.commons.logging.LogFactory;
/*  22:    */ 
/*  23:    */ public class Location
/*  24:    */   extends SimpleScriptable
/*  25:    */ {
/*  26: 54 */   private static final Pattern DECODE_HASH_PATTERN = Pattern.compile("%([\\dA-F]{2})");
/*  27: 56 */   private static final Log LOG = LogFactory.getLog(Location.class);
/*  28:    */   private static final String UNKNOWN = "null";
/*  29:    */   private Window window_;
/*  30:    */   private String hash_;
/*  31:    */   
/*  32:    */   public void initialize(Window window)
/*  33:    */   {
/*  34: 83 */     this.window_ = window;
/*  35: 84 */     if ((this.window_ != null) && (this.window_.getWebWindow().getEnclosedPage() != null)) {
/*  36: 85 */       jsxSet_hash(this.window_.getWebWindow().getEnclosedPage().getWebResponse().getWebRequest().getUrl().getRef());
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object getDefaultValue(Class<?> hint)
/*  41:    */   {
/*  42: 94 */     if ((hint == null) || (String.class.equals(hint))) {
/*  43: 95 */       return jsxGet_href();
/*  44:    */     }
/*  45: 97 */     return super.getDefaultValue(hint);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String toString()
/*  49:    */   {
/*  50:106 */     if (this.window_ != null) {
/*  51:107 */       return jsxGet_href();
/*  52:    */     }
/*  53:109 */     return "[Uninitialized]";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void jsxFunction_assign(String url)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:119 */     jsxSet_href(url);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void jsxFunction_reload(boolean force)
/*  63:    */     throws IOException
/*  64:    */   {
/*  65:130 */     String url = jsxGet_href();
/*  66:131 */     if ("null".equals(url)) {
/*  67:132 */       LOG.error("Unable to reload location: current URL is unknown.");
/*  68:    */     } else {
/*  69:135 */       jsxSet_href(url);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void jsxFunction_replace(String url)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:146 */     this.window_.getWebWindow().getHistory().removeCurrent();
/*  77:147 */     jsxSet_href(url);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String jsxFunction_toString()
/*  81:    */   {
/*  82:155 */     return jsxGet_href();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String jsxGet_href()
/*  86:    */   {
/*  87:164 */     Page page = this.window_.getWebWindow().getEnclosedPage();
/*  88:165 */     if (page == null) {
/*  89:166 */       return "null";
/*  90:    */     }
/*  91:    */     try
/*  92:    */     {
/*  93:169 */       URL url = page.getWebResponse().getWebRequest().getUrl();
/*  94:170 */       boolean encodeHash = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_LOCATION_HASH_IS_DECODED);
/*  95:    */       
/*  96:172 */       String hash = getHash(encodeHash);
/*  97:173 */       if (hash != null) {
/*  98:174 */         url = UrlUtils.getUrlWithNewRef(url, hash);
/*  99:    */       }
/* 100:176 */       String s = url.toExternalForm();
/* 101:177 */       if ((s.startsWith("file:/")) && (!s.startsWith("file:///"))) {}
/* 102:180 */       return "file:///" + s.substring("file:/".length());
/* 103:    */     }
/* 104:    */     catch (MalformedURLException e)
/* 105:    */     {
/* 106:185 */       LOG.error(e.getMessage(), e);
/* 107:    */     }
/* 108:186 */     return page.getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void jsxSet_href(String newLocation)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:197 */     HtmlPage page = (HtmlPage)getWindow(getStartingScope()).getWebWindow().getEnclosedPage();
/* 115:198 */     if (newLocation.startsWith("javascript:"))
/* 116:    */     {
/* 117:199 */       String script = newLocation.substring(11);
/* 118:200 */       page.executeJavaScriptIfPossible(script, "new location value", 1);
/* 119:201 */       return;
/* 120:    */     }
/* 121:    */     try
/* 122:    */     {
/* 123:204 */       URL url = page.getFullyQualifiedUrl(newLocation);
/* 124:206 */       if (StringUtils.isEmpty(newLocation))
/* 125:    */       {
/* 126:207 */         boolean dropFilename = page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.ANCHOR_EMPTY_HREF_NO_FILENAME);
/* 127:209 */         if (dropFilename)
/* 128:    */         {
/* 129:210 */           String path = url.getPath();
/* 130:211 */           path = path.substring(0, path.lastIndexOf('/') + 1);
/* 131:212 */           url = UrlUtils.getUrlWithNewPath(url, path);
/* 132:213 */           url = UrlUtils.getUrlWithNewRef(url, null);
/* 133:    */         }
/* 134:    */         else
/* 135:    */         {
/* 136:216 */           url = UrlUtils.getUrlWithNewRef(url, null);
/* 137:    */         }
/* 138:    */       }
/* 139:220 */       WebWindow webWindow = getWindow().getWebWindow();
/* 140:221 */       webWindow.getWebClient().download(webWindow, "", new WebRequest(url), newLocation.endsWith("#"), "JS set location");
/* 141:    */     }
/* 142:    */     catch (MalformedURLException e)
/* 143:    */     {
/* 144:225 */       LOG.error("jsxSet_location('" + newLocation + "') Got MalformedURLException", e);
/* 145:226 */       throw e;
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String jsxGet_search()
/* 150:    */   {
/* 151:236 */     String search = getUrl().getQuery();
/* 152:237 */     if (search == null) {
/* 153:238 */       return "";
/* 154:    */     }
/* 155:240 */     return "?" + search;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void jsxSet_search(String search)
/* 159:    */     throws Exception
/* 160:    */   {
/* 161:250 */     setUrl(UrlUtils.getUrlWithNewQuery(getUrl(), search));
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String jsxGet_hash()
/* 165:    */   {
/* 166:259 */     boolean decodeHash = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_LOCATION_HASH_IS_DECODED);
/* 167:260 */     String hash = this.hash_;
/* 168:261 */     if ((decodeHash) && (this.hash_ != null)) {
/* 169:262 */       hash = decodeHash(hash);
/* 170:    */     }
/* 171:265 */     if (!StringUtils.isEmpty(hash)) {
/* 172:266 */       return "#" + hash;
/* 173:    */     }
/* 174:269 */     return "";
/* 175:    */   }
/* 176:    */   
/* 177:    */   private String getHash(boolean encoded)
/* 178:    */   {
/* 179:273 */     if ((this.hash_ == null) || (this.hash_.length() == 0)) {
/* 180:274 */       return null;
/* 181:    */     }
/* 182:276 */     if (encoded) {
/* 183:277 */       return UrlUtils.encodeAnchor(this.hash_);
/* 184:    */     }
/* 185:279 */     return this.hash_;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void jsxSet_hash(String hash)
/* 189:    */   {
/* 190:291 */     if ((hash != null) && 
/* 191:292 */       (hash.length() > 0) && ('#' == hash.charAt(0))) {
/* 192:293 */       hash = hash.substring(1);
/* 193:    */     }
/* 194:296 */     this.hash_ = hash;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private String decodeHash(String hash)
/* 198:    */   {
/* 199:300 */     if (hash.indexOf('%') == -1) {
/* 200:301 */       return hash;
/* 201:    */     }
/* 202:303 */     StringBuffer sb = new StringBuffer();
/* 203:304 */     Matcher m = DECODE_HASH_PATTERN.matcher(hash);
/* 204:305 */     while (m.find())
/* 205:    */     {
/* 206:306 */       String code = m.group(1);
/* 207:307 */       int u = (char)Character.digit(code.charAt(0), 16);
/* 208:308 */       int l = (char)Character.digit(code.charAt(1), 16);
/* 209:309 */       char replacement = (char)((u << 4) + l);
/* 210:310 */       m.appendReplacement(sb, "");
/* 211:311 */       sb.append(replacement);
/* 212:    */     }
/* 213:313 */     m.appendTail(sb);
/* 214:    */     
/* 215:315 */     return sb.toString();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String jsxGet_hostname()
/* 219:    */   {
/* 220:324 */     return getUrl().getHost();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void jsxSet_hostname(String hostname)
/* 224:    */     throws Exception
/* 225:    */   {
/* 226:334 */     setUrl(UrlUtils.getUrlWithNewHost(getUrl(), hostname));
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String jsxGet_host()
/* 230:    */   {
/* 231:343 */     URL url = getUrl();
/* 232:344 */     int port = url.getPort();
/* 233:345 */     String host = url.getHost();
/* 234:347 */     if (port == -1) {
/* 235:348 */       return host;
/* 236:    */     }
/* 237:350 */     return host + ":" + port;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void jsxSet_host(String host)
/* 241:    */     throws Exception
/* 242:    */   {
/* 243:362 */     int index = host.indexOf(':');
/* 244:    */     int port;
/* 245:    */     String hostname;
/* 246:    */     int port;
/* 247:363 */     if (index != -1)
/* 248:    */     {
/* 249:364 */       String hostname = host.substring(0, index);
/* 250:365 */       port = Integer.parseInt(host.substring(index + 1));
/* 251:    */     }
/* 252:    */     else
/* 253:    */     {
/* 254:368 */       hostname = host;
/* 255:369 */       port = -1;
/* 256:    */     }
/* 257:371 */     URL url1 = UrlUtils.getUrlWithNewHost(getUrl(), hostname);
/* 258:372 */     URL url2 = UrlUtils.getUrlWithNewPort(url1, port);
/* 259:373 */     setUrl(url2);
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String jsxGet_pathname()
/* 263:    */   {
/* 264:382 */     if (WebClient.URL_ABOUT_BLANK == getUrl())
/* 265:    */     {
/* 266:383 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.URL_ABOUT_BLANK_HAS_EMPTY_PATH)) {
/* 267:384 */         return "";
/* 268:    */       }
/* 269:386 */       return "/blank";
/* 270:    */     }
/* 271:388 */     return getUrl().getPath();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void jsxSet_pathname(String pathname)
/* 275:    */     throws Exception
/* 276:    */   {
/* 277:398 */     setUrl(UrlUtils.getUrlWithNewPath(getUrl(), pathname));
/* 278:    */   }
/* 279:    */   
/* 280:    */   public String jsxGet_port()
/* 281:    */   {
/* 282:407 */     int port = getUrl().getPort();
/* 283:408 */     if (port == -1) {
/* 284:409 */       return "";
/* 285:    */     }
/* 286:411 */     return Integer.toString(port);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void jsxSet_port(String port)
/* 290:    */     throws Exception
/* 291:    */   {
/* 292:421 */     setUrl(UrlUtils.getUrlWithNewPort(getUrl(), Integer.parseInt(port)));
/* 293:    */   }
/* 294:    */   
/* 295:    */   public String jsxGet_protocol()
/* 296:    */   {
/* 297:430 */     return getUrl().getProtocol() + ":";
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void jsxSet_protocol(String protocol)
/* 301:    */     throws Exception
/* 302:    */   {
/* 303:440 */     setUrl(UrlUtils.getUrlWithNewProtocol(getUrl(), protocol));
/* 304:    */   }
/* 305:    */   
/* 306:    */   private URL getUrl()
/* 307:    */   {
/* 308:448 */     return this.window_.getWebWindow().getEnclosedPage().getWebResponse().getWebRequest().getUrl();
/* 309:    */   }
/* 310:    */   
/* 311:    */   private void setUrl(URL url)
/* 312:    */     throws IOException
/* 313:    */   {
/* 314:458 */     this.window_.getWebWindow().getWebClient().getPage(this.window_.getWebWindow(), new WebRequest(url));
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Location
 * JD-Core Version:    0.7.0.1
 */