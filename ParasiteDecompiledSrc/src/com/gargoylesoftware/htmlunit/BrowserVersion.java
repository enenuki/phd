/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Properties;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.apache.commons.io.IOUtils;
/*  12:    */ import org.apache.commons.lang.builder.EqualsBuilder;
/*  13:    */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*  14:    */ 
/*  15:    */ public class BrowserVersion
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18: 49 */   private String applicationCodeName_ = "Mozilla";
/*  19: 50 */   private String applicationMinorVersion_ = "0";
/*  20:    */   private String applicationName_;
/*  21:    */   private String applicationVersion_;
/*  22: 53 */   private String browserLanguage_ = "en-us";
/*  23: 54 */   private String cpuClass_ = "x86";
/*  24: 55 */   private boolean onLine_ = true;
/*  25: 56 */   private String platform_ = "Win32";
/*  26: 57 */   private String systemLanguage_ = "en-us";
/*  27:    */   private String userAgent_;
/*  28: 59 */   private String userLanguage_ = "en-us";
/*  29:    */   private float browserVersionNumeric_;
/*  30: 61 */   private Set<PluginConfiguration> plugins_ = new HashSet();
/*  31: 62 */   private final List<BrowserVersionFeatures> features_ = new ArrayList();
/*  32:    */   private final String nickname_;
/*  33:    */   @Deprecated
/*  34:    */   public static final String APP_CODE_NAME = "Mozilla";
/*  35:    */   @Deprecated
/*  36:    */   public static final String INTERNET_EXPLORER = "Microsoft Internet Explorer";
/*  37:    */   @Deprecated
/*  38:    */   public static final String NETSCAPE = "Netscape";
/*  39:    */   @Deprecated
/*  40:    */   public static final String LANGUAGE_ENGLISH_US = "en-us";
/*  41:    */   @Deprecated
/*  42:    */   public static final String CPU_CLASS_X86 = "x86";
/*  43:    */   @Deprecated
/*  44:    */   public static final String PLATFORM_WIN32 = "Win32";
/*  45:    */   @Deprecated
/*  46:113 */   public static final BrowserVersion FIREFOX_3 = new BrowserVersion("Netscape", "5.0 (Windows; en-US)", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.19) Gecko/2010031422 Firefox/3.0.19", 3.0F, "FF3", null);
/*  47:119 */   public static final BrowserVersion FIREFOX_3_6 = new BrowserVersion("Netscape", "5.0 (Windows; en-US)", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8", 3.6F, "FF3.6", null);
/*  48:125 */   public static final BrowserVersion INTERNET_EXPLORER_6 = new BrowserVersion("Microsoft Internet Explorer", "4.0 (compatible; MSIE 6.0b; Windows 98)", "Mozilla/4.0 (compatible; MSIE 6.0; Windows 98)", 6.0F, "IE6", null);
/*  49:130 */   public static final BrowserVersion INTERNET_EXPLORER_7 = new BrowserVersion("Microsoft Internet Explorer", "4.0 (compatible; MSIE 7.0; Windows NT 5.1)", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)", 7.0F, "IE7", null);
/*  50:135 */   public static final BrowserVersion INTERNET_EXPLORER_8 = new BrowserVersion("Microsoft Internet Explorer", "4.0 (compatible; MSIE 8.0; Windows NT 6.0)", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)", 8.0F, "IE8", null);
/*  51:140 */   private static BrowserVersion DefaultBrowserVersion_ = INTERNET_EXPLORER_7;
/*  52:    */   
/*  53:    */   static
/*  54:    */   {
/*  55:144 */     INTERNET_EXPLORER_6.initDefaultFeatures();
/*  56:145 */     INTERNET_EXPLORER_7.initDefaultFeatures();
/*  57:146 */     INTERNET_EXPLORER_8.initDefaultFeatures();
/*  58:147 */     FIREFOX_3.initDefaultFeatures();
/*  59:148 */     FIREFOX_3_6.initDefaultFeatures();
/*  60:149 */     PluginConfiguration flash = new PluginConfiguration("Shockwave Flash", "Shockwave Flash 9.0 r31", "libflashplayer.so");
/*  61:    */     
/*  62:151 */     flash.getMimeTypes().add(new PluginConfiguration.MimeType("application/x-shockwave-flash", "Shockwave Flash", "swf"));
/*  63:    */     
/*  64:153 */     FIREFOX_3.getPlugins().add(flash);
/*  65:154 */     FIREFOX_3_6.getPlugins().add(flash);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public BrowserVersion(String applicationName, String applicationVersion, String userAgent, float browserVersionNumeric)
/*  69:    */   {
/*  70:168 */     this(applicationName, applicationVersion, userAgent, browserVersionNumeric, applicationName + browserVersionNumeric, null);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public BrowserVersion(String applicationName, String applicationVersion, String userAgent, float browserVersionNumeric, BrowserVersionFeatures[] features)
/*  74:    */   {
/*  75:185 */     this(applicationName, applicationVersion, userAgent, browserVersionNumeric, applicationName + browserVersionNumeric, features);
/*  76:    */   }
/*  77:    */   
/*  78:    */   private BrowserVersion(String applicationName, String applicationVersion, String userAgent, float browserVersionNumeric, String nickname, BrowserVersionFeatures[] features)
/*  79:    */   {
/*  80:204 */     this.applicationName_ = applicationName;
/*  81:205 */     setApplicationVersion(applicationVersion);
/*  82:206 */     this.userAgent_ = userAgent;
/*  83:207 */     this.browserVersionNumeric_ = browserVersionNumeric;
/*  84:208 */     this.nickname_ = nickname;
/*  85:209 */     if (features != null) {
/*  86:210 */       this.features_.addAll(Arrays.asList(features));
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void initDefaultFeatures()
/*  91:    */   {
/*  92:215 */     InputStream stream = null;
/*  93:    */     try
/*  94:    */     {
/*  95:217 */       Properties props = new Properties();
/*  96:218 */       stream = getClass().getResourceAsStream("/com/gargoylesoftware/htmlunit/javascript/configuration/" + this.nickname_ + ".properties");
/*  97:    */       
/*  98:220 */       props.load(stream);
/*  99:221 */       for (Object key : props.keySet()) {
/* 100:    */         try
/* 101:    */         {
/* 102:223 */           this.features_.add(BrowserVersionFeatures.valueOf(key.toString()));
/* 103:    */         }
/* 104:    */         catch (IllegalArgumentException iae)
/* 105:    */         {
/* 106:226 */           throw new RuntimeException("Invalid entry '" + key + "' found in configuration file for BrowserVersion: " + this.nickname_);
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (Exception e)
/* 111:    */     {
/* 112:232 */       throw new RuntimeException("Configuration file not found for BrowserVersion: " + this.nickname_);
/* 113:    */     }
/* 114:    */     finally
/* 115:    */     {
/* 116:235 */       IOUtils.closeQuietly(stream);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static BrowserVersion getDefault()
/* 121:    */   {
/* 122:245 */     return DefaultBrowserVersion_;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void setDefault(BrowserVersion newBrowserVersion)
/* 126:    */   {
/* 127:253 */     WebAssert.notNull("newBrowserVersion", newBrowserVersion);
/* 128:254 */     DefaultBrowserVersion_ = newBrowserVersion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public final boolean isIE()
/* 132:    */   {
/* 133:263 */     return "Microsoft Internet Explorer".equals(getApplicationName());
/* 134:    */   }
/* 135:    */   
/* 136:    */   public final boolean isFirefox()
/* 137:    */   {
/* 138:272 */     return "Netscape".equals(getApplicationName());
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getApplicationCodeName()
/* 142:    */   {
/* 143:282 */     return this.applicationCodeName_;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getApplicationMinorVersion()
/* 147:    */   {
/* 148:292 */     return this.applicationMinorVersion_;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getApplicationName()
/* 152:    */   {
/* 153:301 */     return this.applicationName_;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getApplicationVersion()
/* 157:    */   {
/* 158:310 */     return this.applicationVersion_;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getBrowserLanguage()
/* 162:    */   {
/* 163:320 */     return this.browserLanguage_;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getCpuClass()
/* 167:    */   {
/* 168:330 */     return this.cpuClass_;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isOnLine()
/* 172:    */   {
/* 173:340 */     return this.onLine_;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getPlatform()
/* 177:    */   {
/* 178:350 */     return this.platform_;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getSystemLanguage()
/* 182:    */   {
/* 183:360 */     return this.systemLanguage_;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getUserAgent()
/* 187:    */   {
/* 188:368 */     return this.userAgent_;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getUserLanguage()
/* 192:    */   {
/* 193:378 */     return this.userLanguage_;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setApplicationCodeName(String applicationCodeName)
/* 197:    */   {
/* 198:385 */     this.applicationCodeName_ = applicationCodeName;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setApplicationMinorVersion(String applicationMinorVersion)
/* 202:    */   {
/* 203:392 */     this.applicationMinorVersion_ = applicationMinorVersion;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setApplicationName(String applicationName)
/* 207:    */   {
/* 208:399 */     this.applicationName_ = applicationName;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setApplicationVersion(String applicationVersion)
/* 212:    */   {
/* 213:406 */     this.applicationVersion_ = applicationVersion;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void setBrowserLanguage(String browserLanguage)
/* 217:    */   {
/* 218:413 */     this.browserLanguage_ = browserLanguage;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void setCpuClass(String cpuClass)
/* 222:    */   {
/* 223:420 */     this.cpuClass_ = cpuClass;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setOnLine(boolean onLine)
/* 227:    */   {
/* 228:427 */     this.onLine_ = onLine;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setPlatform(String platform)
/* 232:    */   {
/* 233:434 */     this.platform_ = platform;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setSystemLanguage(String systemLanguage)
/* 237:    */   {
/* 238:441 */     this.systemLanguage_ = systemLanguage;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setUserAgent(String userAgent)
/* 242:    */   {
/* 243:448 */     this.userAgent_ = userAgent;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setUserLanguage(String userLanguage)
/* 247:    */   {
/* 248:455 */     this.userLanguage_ = userLanguage;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setBrowserVersion(float browserVersion)
/* 252:    */   {
/* 253:462 */     this.browserVersionNumeric_ = browserVersion;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public float getBrowserVersionNumeric()
/* 257:    */   {
/* 258:469 */     return this.browserVersionNumeric_;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean equals(Object o)
/* 262:    */   {
/* 263:477 */     return EqualsBuilder.reflectionEquals(this, o);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public int hashCode()
/* 267:    */   {
/* 268:485 */     return HashCodeBuilder.reflectionHashCode(this);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Set<PluginConfiguration> getPlugins()
/* 272:    */   {
/* 273:494 */     return this.plugins_;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public boolean hasFeature(BrowserVersionFeatures property)
/* 277:    */   {
/* 278:503 */     return this.features_.contains(property);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String getNickname()
/* 282:    */   {
/* 283:512 */     return this.nickname_;
/* 284:    */   }
/* 285:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.BrowserVersion
 * JD-Core Version:    0.7.0.1
 */