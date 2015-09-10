/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   6:    */ import com.gargoylesoftware.htmlunit.Page;
/*   7:    */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*   8:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*  11:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*  13:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.util.Collection;
/*  16:    */ import java.util.Collections;
/*  17:    */ import java.util.HashSet;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ 
/*  21:    */ public abstract class HtmlInput
/*  22:    */   extends HtmlElement
/*  23:    */   implements DisabledElement, SubmittableElement, FormFieldWithNameHistory
/*  24:    */ {
/*  25:    */   public static final String TAG_NAME = "input";
/*  26:    */   private String defaultValue_;
/*  27:    */   private String originalName_;
/*  28: 52 */   private Collection<String> previousNames_ = Collections.emptySet();
/*  29:    */   
/*  30:    */   public HtmlInput(SgmlPage page, Map<String, DomAttr> attributes)
/*  31:    */   {
/*  32: 61 */     this(null, "input", page, attributes);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public HtmlInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  36:    */   {
/*  37: 74 */     super(namespaceURI, qualifiedName, page, attributes);
/*  38: 75 */     this.defaultValue_ = getValueAttribute();
/*  39: 76 */     this.originalName_ = getNameAttribute();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Page setValueAttribute(String newValue)
/*  43:    */   {
/*  44: 87 */     WebAssert.notNull("newValue", newValue);
/*  45: 88 */     setAttribute("value", newValue);
/*  46:    */     
/*  47: 90 */     return executeOnChangeHandlerIfAppropriate(this);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public NameValuePair[] getSubmitKeyValuePairs()
/*  51:    */   {
/*  52: 97 */     return new NameValuePair[] { new NameValuePair(getNameAttribute(), getValueAttribute()) };
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String getTypeAttribute()
/*  56:    */   {
/*  57:108 */     return getAttribute("type");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final String getNameAttribute()
/*  61:    */   {
/*  62:119 */     return getAttribute("name");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final String getValueAttribute()
/*  66:    */   {
/*  67:130 */     return getAttribute("value");
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final String getCheckedAttribute()
/*  71:    */   {
/*  72:141 */     return getAttribute("checked");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final String getDisabledAttribute()
/*  76:    */   {
/*  77:148 */     return getAttribute("disabled");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public final boolean isDisabled()
/*  81:    */   {
/*  82:155 */     return hasAttribute("disabled");
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final String getReadOnlyAttribute()
/*  86:    */   {
/*  87:167 */     return getAttribute("readonly");
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final String getSizeAttribute()
/*  91:    */   {
/*  92:179 */     return getAttribute("size");
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final String getMaxLengthAttribute()
/*  96:    */   {
/*  97:191 */     return getAttribute("maxlength");
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected int getMaxLength()
/* 101:    */   {
/* 102:199 */     String maxLength = getMaxLengthAttribute();
/* 103:200 */     if (maxLength.length() == 0) {
/* 104:201 */       return 2147483647;
/* 105:    */     }
/* 106:    */     try
/* 107:    */     {
/* 108:205 */       return Integer.parseInt(maxLength.trim());
/* 109:    */     }
/* 110:    */     catch (NumberFormatException e) {}
/* 111:208 */     return 2147483647;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public final String getSrcAttribute()
/* 115:    */   {
/* 116:221 */     return getAttribute("src");
/* 117:    */   }
/* 118:    */   
/* 119:    */   public final String getAltAttribute()
/* 120:    */   {
/* 121:233 */     return getAttribute("alt");
/* 122:    */   }
/* 123:    */   
/* 124:    */   public final String getUseMapAttribute()
/* 125:    */   {
/* 126:245 */     return getAttribute("usemap");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final String getTabIndexAttribute()
/* 130:    */   {
/* 131:257 */     return getAttribute("tabindex");
/* 132:    */   }
/* 133:    */   
/* 134:    */   public final String getAccessKeyAttribute()
/* 135:    */   {
/* 136:269 */     return getAttribute("accesskey");
/* 137:    */   }
/* 138:    */   
/* 139:    */   public final String getOnFocusAttribute()
/* 140:    */   {
/* 141:281 */     return getAttribute("onfocus");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public final String getOnBlurAttribute()
/* 145:    */   {
/* 146:293 */     return getAttribute("onblur");
/* 147:    */   }
/* 148:    */   
/* 149:    */   public final String getOnSelectAttribute()
/* 150:    */   {
/* 151:305 */     return getAttribute("onselect");
/* 152:    */   }
/* 153:    */   
/* 154:    */   public final String getOnChangeAttribute()
/* 155:    */   {
/* 156:317 */     return getAttribute("onchange");
/* 157:    */   }
/* 158:    */   
/* 159:    */   public final String getAcceptAttribute()
/* 160:    */   {
/* 161:329 */     return getAttribute("accept");
/* 162:    */   }
/* 163:    */   
/* 164:    */   public final String getAlignAttribute()
/* 165:    */   {
/* 166:341 */     return getAttribute("align");
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void reset()
/* 170:    */   {
/* 171:349 */     setValueAttribute(this.defaultValue_);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setDefaultValue(String defaultValue)
/* 175:    */   {
/* 176:358 */     boolean modifyValue = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLINPUT_DEFAULT_IS_CHECKED);
/* 177:    */     
/* 178:360 */     setDefaultValue(defaultValue, modifyValue);
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected void setDefaultValue(String defaultValue, boolean modifyValue)
/* 182:    */   {
/* 183:369 */     this.defaultValue_ = defaultValue;
/* 184:370 */     if (modifyValue) {
/* 185:371 */       setValueAttribute(defaultValue);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getDefaultValue()
/* 190:    */   {
/* 191:380 */     return this.defaultValue_;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setDefaultChecked(boolean defaultChecked) {}
/* 195:    */   
/* 196:    */   public boolean isDefaultChecked()
/* 197:    */   {
/* 198:402 */     return false;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Page setChecked(boolean isChecked)
/* 202:    */   {
/* 203:415 */     return getPage();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setReadOnly(boolean isReadOnly)
/* 207:    */   {
/* 208:424 */     if (isReadOnly) {
/* 209:425 */       setAttribute("readOnly", "readOnly");
/* 210:    */     } else {
/* 211:428 */       removeAttribute("readOnly");
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean isChecked()
/* 216:    */   {
/* 217:437 */     return hasAttribute("checked");
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isReadOnly()
/* 221:    */   {
/* 222:445 */     return hasAttribute("readOnly");
/* 223:    */   }
/* 224:    */   
/* 225:    */   public <P extends Page> P click(int x, int y)
/* 226:    */     throws IOException, ElementNotFoundException
/* 227:    */   {
/* 228:466 */     return click();
/* 229:    */   }
/* 230:    */   
/* 231:    */   static Page executeOnChangeHandlerIfAppropriate(HtmlElement htmlElement)
/* 232:    */   {
/* 233:479 */     SgmlPage page = htmlElement.getPage();
/* 234:    */     
/* 235:481 */     JavaScriptEngine engine = htmlElement.getPage().getWebClient().getJavaScriptEngine();
/* 236:482 */     if (engine.isScriptRunning()) {
/* 237:483 */       return page;
/* 238:    */     }
/* 239:485 */     ScriptResult scriptResult = htmlElement.fireEvent("change");
/* 240:487 */     if (page.getWebClient().getWebWindows().contains(page.getEnclosingWindow())) {
/* 241:488 */       return page.getEnclosingWindow().getEnclosedPage();
/* 242:    */     }
/* 243:490 */     if (scriptResult != null) {
/* 244:492 */       return scriptResult.getNewPage();
/* 245:    */     }
/* 246:495 */     return page;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 250:    */   {
/* 251:503 */     if ("name".equals(qualifiedName))
/* 252:    */     {
/* 253:504 */       if (this.previousNames_.isEmpty()) {
/* 254:505 */         this.previousNames_ = new HashSet();
/* 255:    */       }
/* 256:507 */       this.previousNames_.add(attributeValue);
/* 257:    */     }
/* 258:509 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getOriginalName()
/* 262:    */   {
/* 263:516 */     return this.originalName_;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Collection<String> getPreviousNames()
/* 267:    */   {
/* 268:523 */     return this.previousNames_;
/* 269:    */   }
/* 270:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlInput
 * JD-Core Version:    0.7.0.1
 */