/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.HttpMethod;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.FormFieldWithNameHistory;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeEvent;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.HtmlButton;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.HtmlImage;
/*  16:    */ import com.gargoylesoftware.htmlunit.html.HtmlImageInput;
/*  17:    */ import com.gargoylesoftware.htmlunit.html.HtmlInput;
/*  18:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  19:    */ import com.gargoylesoftware.htmlunit.html.HtmlSelect;
/*  20:    */ import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
/*  21:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*  22:    */ import java.io.IOException;
/*  23:    */ import java.net.MalformedURLException;
/*  24:    */ import java.net.URL;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Collection;
/*  27:    */ import java.util.List;
/*  28:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  29:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  30:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  31:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  32:    */ import org.apache.commons.lang.StringUtils;
/*  33:    */ 
/*  34:    */ public class HTMLFormElement
/*  35:    */   extends HTMLElement
/*  36:    */   implements Function
/*  37:    */ {
/*  38:    */   private HTMLCollection elements_;
/*  39:    */   
/*  40:    */   public void setHtmlElement(HtmlElement htmlElement)
/*  41:    */   {
/*  42: 79 */     super.setHtmlElement(htmlElement);
/*  43: 80 */     HtmlForm htmlForm = getHtmlForm();
/*  44: 81 */     htmlForm.setScriptObject(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String jsxGet_name()
/*  48:    */   {
/*  49: 89 */     return getHtmlForm().getNameAttribute();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void jsxSet_name(String name)
/*  53:    */   {
/*  54: 97 */     WebAssert.notNull("name", name);
/*  55: 98 */     getHtmlForm().setNameAttribute(name);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public HTMLCollection jsxGet_elements()
/*  59:    */   {
/*  60:106 */     if (this.elements_ == null)
/*  61:    */     {
/*  62:107 */       final HtmlForm htmlForm = getHtmlForm();
/*  63:    */       
/*  64:109 */       this.elements_ = new HTMLCollection(htmlForm, false, "HTMLFormElement.elements")
/*  65:    */       {
/*  66:    */         protected List<Object> computeElements()
/*  67:    */         {
/*  68:112 */           List<Object> response = super.computeElements();
/*  69:113 */           response.addAll(htmlForm.getLostChildren());
/*  70:114 */           return response;
/*  71:    */         }
/*  72:    */         
/*  73:    */         protected Object getWithPreemption(String name)
/*  74:    */         {
/*  75:119 */           return HTMLFormElement.this.getWithPreemption(name);
/*  76:    */         }
/*  77:    */         
/*  78:    */         public HTMLCollection.EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/*  79:    */         {
/*  80:124 */           return HTMLCollection.EffectOnCache.NONE;
/*  81:    */         }
/*  82:    */         
/*  83:    */         protected boolean isMatching(DomNode node)
/*  84:    */         {
/*  85:129 */           return ((node instanceof HtmlInput)) || ((node instanceof HtmlButton)) || ((node instanceof HtmlTextArea)) || ((node instanceof HtmlSelect));
/*  86:    */         }
/*  87:    */       };
/*  88:    */     }
/*  89:135 */     return this.elements_;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int jsxGet_length()
/*  93:    */   {
/*  94:145 */     int all = jsxGet_elements().jsxGet_length();
/*  95:146 */     int images = getHtmlForm().getElementsByAttribute("input", "type", "image").size();
/*  96:147 */     return all - images;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String jsxGet_action()
/* 100:    */   {
/* 101:155 */     String action = getHtmlForm().getActionAttribute();
/* 102:156 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_169)) {
/* 103:    */       try
/* 104:    */       {
/* 105:158 */         action = ((HtmlPage)getHtmlForm().getPage()).getFullyQualifiedUrl(action).toExternalForm();
/* 106:    */       }
/* 107:    */       catch (MalformedURLException e) {}
/* 108:    */     }
/* 109:164 */     return action;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void jsxSet_action(String action)
/* 113:    */   {
/* 114:172 */     WebAssert.notNull("action", action);
/* 115:173 */     getHtmlForm().setActionAttribute(action);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String jsxGet_method()
/* 119:    */   {
/* 120:181 */     return getHtmlForm().getMethodAttribute();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void jsxSet_method(String method)
/* 124:    */   {
/* 125:189 */     WebAssert.notNull("method", method);
/* 126:190 */     getHtmlForm().setMethodAttribute(method);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String jsxGet_target()
/* 130:    */   {
/* 131:198 */     return getHtmlForm().getTargetAttribute();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Object jsxGet_onsubmit()
/* 135:    */   {
/* 136:206 */     return getEventHandlerProp("onsubmit");
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void jsxSet_onsubmit(Object onsubmit)
/* 140:    */   {
/* 141:214 */     setEventHandlerProp("onsubmit", onsubmit);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void jsxSet_target(String target)
/* 145:    */   {
/* 146:222 */     WebAssert.notNull("target", target);
/* 147:223 */     getHtmlForm().setTargetAttribute(target);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String jsxGet_encoding()
/* 151:    */   {
/* 152:231 */     return getHtmlForm().getEnctypeAttribute();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void jsxSet_encoding(String encoding)
/* 156:    */   {
/* 157:239 */     WebAssert.notNull("encoding", encoding);
/* 158:240 */     getHtmlForm().setEnctypeAttribute(encoding);
/* 159:    */   }
/* 160:    */   
/* 161:    */   private HtmlForm getHtmlForm()
/* 162:    */   {
/* 163:244 */     return (HtmlForm)getDomNodeOrDie();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void jsxFunction_submit()
/* 167:    */     throws IOException
/* 168:    */   {
/* 169:255 */     HtmlPage page = (HtmlPage)getDomNodeOrDie().getPage();
/* 170:256 */     WebClient webClient = page.getWebClient();
/* 171:    */     
/* 172:258 */     String action = getHtmlForm().getActionAttribute().trim();
/* 173:259 */     if (StringUtils.startsWithIgnoreCase(action, "javascript:"))
/* 174:    */     {
/* 175:260 */       String js = action.substring("javascript:".length());
/* 176:261 */       webClient.getJavaScriptEngine().execute(page, js, "Form action", 0);
/* 177:    */     }
/* 178:    */     else
/* 179:    */     {
/* 180:265 */       WebRequest request = getHtmlForm().getWebRequest(null);
/* 181:266 */       String target = page.getResolvedTarget(jsxGet_target());
/* 182:267 */       boolean isHashJump = (HttpMethod.GET.equals(request.getHttpMethod())) && (action.endsWith("#"));
/* 183:268 */       webClient.download(page.getEnclosingWindow(), target, request, isHashJump, "JS form.submit()");
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Object jsxFunction_item(Object index, Object subIndex)
/* 188:    */   {
/* 189:284 */     if ((index instanceof Number)) {
/* 190:285 */       return jsxGet_elements().jsxFunction_item(index);
/* 191:    */     }
/* 192:288 */     String name = Context.toString(index);
/* 193:289 */     Object response = getWithPreemption(name);
/* 194:290 */     if (((subIndex instanceof Number)) && ((response instanceof HTMLCollection))) {
/* 195:291 */       return ((HTMLCollection)response).jsxFunction_item(subIndex);
/* 196:    */     }
/* 197:294 */     return response;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void jsxFunction_reset()
/* 201:    */   {
/* 202:301 */     getHtmlForm().reset();
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected Object getWithPreemption(final String name)
/* 206:    */   {
/* 207:312 */     List<HtmlElement> elements = findElements(name);
/* 208:314 */     if (elements.isEmpty()) {
/* 209:315 */       return NOT_FOUND;
/* 210:    */     }
/* 211:317 */     if (elements.size() == 1) {
/* 212:318 */       return getScriptableFor(elements.get(0));
/* 213:    */     }
/* 214:321 */     HTMLCollection collection = new HTMLCollection(getHtmlForm(), elements)
/* 215:    */     {
/* 216:    */       protected List<Object> computeElements()
/* 217:    */       {
/* 218:323 */         return new ArrayList(HTMLFormElement.this.findElements(name));
/* 219:    */       }
/* 220:325 */     };
/* 221:326 */     return collection;
/* 222:    */   }
/* 223:    */   
/* 224:    */   private List<HtmlElement> findElements(String name)
/* 225:    */   {
/* 226:330 */     List<HtmlElement> elements = new ArrayList();
/* 227:331 */     addElements(name, getHtmlForm().getHtmlElementDescendants(), elements);
/* 228:332 */     addElements(name, getHtmlForm().getLostChildren(), elements);
/* 229:335 */     if (elements.isEmpty()) {
/* 230:336 */       for (DomNode node : getHtmlForm().getChildren()) {
/* 231:337 */         if ((node instanceof HtmlImage))
/* 232:    */         {
/* 233:338 */           HtmlImage img = (HtmlImage)node;
/* 234:339 */           if ((name.equals(img.getId())) || (name.equals(img.getNameAttribute()))) {
/* 235:340 */             elements.add(img);
/* 236:    */           }
/* 237:    */         }
/* 238:    */       }
/* 239:    */     }
/* 240:346 */     return elements;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private void addElements(String name, Iterable<HtmlElement> nodes, List<HtmlElement> addTo)
/* 244:    */   {
/* 245:351 */     for (HtmlElement node : nodes) {
/* 246:352 */       if (isAccessibleByIdOrName(node, name)) {
/* 247:353 */         addTo.add(node);
/* 248:    */       }
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   private boolean isAccessibleByIdOrName(HtmlElement element, String name)
/* 253:    */   {
/* 254:365 */     if (((element instanceof FormFieldWithNameHistory)) && (!(element instanceof HtmlImageInput)))
/* 255:    */     {
/* 256:366 */       FormFieldWithNameHistory elementWithNames = (FormFieldWithNameHistory)element;
/* 257:367 */       if ((name.equals(elementWithNames.getOriginalName())) || (name.equals(element.getId()))) {
/* 258:369 */         return true;
/* 259:    */       }
/* 260:372 */       if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.FORMFIELD_REACHABLE_BY_NEW_NAMES)) {
/* 261:373 */         return false;
/* 262:    */       }
/* 263:375 */       if ((name.equals(element.getAttribute("name"))) || (elementWithNames.getPreviousNames().contains(name))) {
/* 264:377 */         return true;
/* 265:    */       }
/* 266:    */     }
/* 267:380 */     return false;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Object get(int index, Scriptable start)
/* 271:    */   {
/* 272:391 */     if (getDomNodeOrNull() == null) {
/* 273:392 */       return NOT_FOUND;
/* 274:    */     }
/* 275:394 */     return jsxGet_elements().get(index, ((HTMLFormElement)start).jsxGet_elements());
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 279:    */   {
/* 280:401 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_80)) {
/* 281:402 */       throw Context.reportRuntimeError("Not a function.");
/* 282:    */     }
/* 283:404 */     if (args.length > 0)
/* 284:    */     {
/* 285:405 */       Object arg = args[0];
/* 286:406 */       if ((arg instanceof String)) {
/* 287:407 */         return ScriptableObject.getProperty(this, (String)arg);
/* 288:    */       }
/* 289:409 */       if ((arg instanceof Number)) {
/* 290:410 */         return ScriptableObject.getProperty(this, ((Number)arg).intValue());
/* 291:    */       }
/* 292:    */     }
/* 293:413 */     return Context.getUndefinedValue();
/* 294:    */   }
/* 295:    */   
/* 296:    */   public Scriptable construct(Context cx, Scriptable scope, Object[] args)
/* 297:    */   {
/* 298:420 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_81)) {
/* 299:421 */       throw Context.reportRuntimeError("Not a function.");
/* 300:    */     }
/* 301:423 */     return null;
/* 302:    */   }
/* 303:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFormElement
 * JD-Core Version:    0.7.0.1
 */