/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  13:    */ import java.lang.reflect.Method;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  17:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  18:    */ import org.apache.commons.collections.Transformer;
/*  19:    */ import org.apache.commons.logging.Log;
/*  20:    */ import org.apache.commons.logging.LogFactory;
/*  21:    */ 
/*  22:    */ public class SimpleScriptable
/*  23:    */   extends ScriptableObject
/*  24:    */   implements Cloneable
/*  25:    */ {
/*  26: 50 */   private static final Log LOG = LogFactory.getLog(SimpleScriptable.class);
/*  27:    */   private DomNode domNode_;
/*  28: 53 */   private boolean caseSensitive_ = true;
/*  29:    */   
/*  30:    */   public Object get(String name, Scriptable start)
/*  31:    */   {
/*  32: 66 */     if (!this.caseSensitive_) {
/*  33: 67 */       for (Object o : getAllIds()) {
/*  34: 68 */         if (name.equalsIgnoreCase(Context.toString(o)))
/*  35:    */         {
/*  36: 69 */           name = Context.toString(o);
/*  37: 70 */           break;
/*  38:    */         }
/*  39:    */       }
/*  40:    */     }
/*  41: 75 */     Object response = super.get(name, start);
/*  42: 76 */     if (response != NOT_FOUND) {
/*  43: 77 */       return response;
/*  44:    */     }
/*  45: 79 */     if (this == start) {
/*  46: 80 */       return getWithPreemption(name);
/*  47:    */     }
/*  48: 82 */     return NOT_FOUND;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected Object getWithPreemption(String name)
/*  52:    */   {
/*  53: 96 */     return NOT_FOUND;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getClassName()
/*  57:    */   {
/*  58:105 */     String javaClassName = getClass().getName();
/*  59:106 */     int index = javaClassName.lastIndexOf(".");
/*  60:107 */     if (index == -1) {
/*  61:108 */       throw new IllegalStateException("No dot in classname: " + javaClassName);
/*  62:    */     }
/*  63:111 */     return javaClassName.substring(index + 1);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public <N extends DomNode> N getDomNodeOrDie()
/*  67:    */     throws IllegalStateException
/*  68:    */   {
/*  69:123 */     if (this.domNode_ == null)
/*  70:    */     {
/*  71:124 */       String clazz = getClass().getName();
/*  72:125 */       throw new IllegalStateException("DomNode has not been set for this SimpleScriptable: " + clazz);
/*  73:    */     }
/*  74:127 */     return this.domNode_;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public <N extends DomNode> N getDomNodeOrNull()
/*  78:    */   {
/*  79:138 */     return this.domNode_;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setDomNode(DomNode domNode)
/*  83:    */   {
/*  84:146 */     setDomNode(domNode, true);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void setDomNode(DomNode domNode, boolean assignScriptObject)
/*  88:    */   {
/*  89:155 */     WebAssert.notNull("domNode", domNode);
/*  90:156 */     this.domNode_ = domNode;
/*  91:157 */     if (assignScriptObject) {
/*  92:158 */       this.domNode_.setScriptObject(this);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setHtmlElement(HtmlElement htmlElement)
/*  97:    */   {
/*  98:167 */     setDomNode(htmlElement);
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected SimpleScriptable getScriptableFor(Object object)
/* 102:    */   {
/* 103:179 */     if ((object instanceof WebWindow)) {
/* 104:180 */       return (SimpleScriptable)((WebWindow)object).getScriptObject();
/* 105:    */     }
/* 106:183 */     DomNode domNode = (DomNode)object;
/* 107:    */     
/* 108:185 */     Object scriptObject = domNode.getScriptObject();
/* 109:186 */     if (scriptObject != null) {
/* 110:187 */       return (SimpleScriptable)scriptObject;
/* 111:    */     }
/* 112:189 */     return makeScriptableFor(domNode);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public SimpleScriptable makeScriptableFor(DomNode domNode)
/* 116:    */   {
/* 117:200 */     Class<? extends SimpleScriptable> javaScriptClass = null;
/* 118:201 */     for (Class<?> c = domNode.getClass(); (javaScriptClass == null) && (c != null); c = c.getSuperclass()) {
/* 119:202 */       javaScriptClass = getWindow().getWebWindow().getWebClient().getJavaScriptEngine().getJavaScriptClass(c);
/* 120:    */     }
/* 121:    */     SimpleScriptable scriptable;
/* 122:206 */     if (javaScriptClass == null)
/* 123:    */     {
/* 124:208 */       SimpleScriptable scriptable = new HTMLElement();
/* 125:209 */       if (LOG.isDebugEnabled()) {
/* 126:210 */         LOG.debug("No JavaScript class found for element <" + domNode.getNodeName() + ">. Using HTMLElement");
/* 127:    */       }
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:    */       try
/* 132:    */       {
/* 133:215 */         scriptable = (SimpleScriptable)javaScriptClass.newInstance();
/* 134:    */       }
/* 135:    */       catch (Exception e)
/* 136:    */       {
/* 137:218 */         throw Context.throwAsScriptRuntimeEx(e);
/* 138:    */       }
/* 139:    */     }
/* 140:221 */     initParentScope(domNode, scriptable);
/* 141:    */     
/* 142:223 */     scriptable.setPrototype(getPrototype(javaScriptClass));
/* 143:224 */     scriptable.setDomNode(domNode);
/* 144:    */     
/* 145:226 */     return scriptable;
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected void initParentScope(DomNode domNode, SimpleScriptable scriptable)
/* 149:    */   {
/* 150:235 */     WebWindow enclosingWindow = domNode.getPage().getEnclosingWindow();
/* 151:236 */     if (enclosingWindow.getEnclosedPage() == domNode.getPage()) {
/* 152:237 */       scriptable.setParentScope((Scriptable)enclosingWindow.getScriptObject());
/* 153:    */     } else {
/* 154:240 */       scriptable.setParentScope(ScriptableObject.getTopLevelScope(domNode.getPage().getScriptObject()));
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected Scriptable getPrototype(Class<? extends SimpleScriptable> javaScriptClass)
/* 159:    */   {
/* 160:251 */     Scriptable prototype = getWindow().getPrototype(javaScriptClass);
/* 161:252 */     if ((prototype == null) && (javaScriptClass != SimpleScriptable.class)) {
/* 162:253 */       return getPrototype(javaScriptClass.getSuperclass());
/* 163:    */     }
/* 164:255 */     return prototype;
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected Transformer getTransformerScriptableFor()
/* 168:    */   {
/* 169:263 */     new Transformer()
/* 170:    */     {
/* 171:    */       public Object transform(Object obj)
/* 172:    */       {
/* 173:265 */         return SimpleScriptable.this.getScriptableFor(obj);
/* 174:    */       }
/* 175:    */     };
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Object getDefaultValue(Class<?> hint)
/* 179:    */   {
/* 180:278 */     if ((String.class.equals(hint)) || (hint == null))
/* 181:    */     {
/* 182:279 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_OBJECT_ONLY)) {
/* 183:280 */         return "[object]";
/* 184:    */       }
/* 185:282 */       return "[object " + getClassName() + "]";
/* 186:    */     }
/* 187:284 */     return super.getDefaultValue(hint);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Window getWindow()
/* 191:    */     throws RuntimeException
/* 192:    */   {
/* 193:293 */     return getWindow(this);
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected static Window getWindow(Scriptable s)
/* 197:    */     throws RuntimeException
/* 198:    */   {
/* 199:303 */     Scriptable top = ScriptableObject.getTopLevelScope(s);
/* 200:304 */     if ((top instanceof Window)) {
/* 201:305 */       return (Window)top;
/* 202:    */     }
/* 203:307 */     throw new RuntimeException("Unable to find window associated with " + s);
/* 204:    */   }
/* 205:    */   
/* 206:    */   protected Scriptable getStartingScope()
/* 207:    */   {
/* 208:316 */     return (Scriptable)Context.getCurrentContext().getThreadLocal("startingScope");
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void defineProperty(String propertyName, Class<?> clazz, int attributes)
/* 212:    */   {
/* 213:325 */     int length = propertyName.length();
/* 214:326 */     if (length == 0) {
/* 215:327 */       throw new IllegalArgumentException();
/* 216:    */     }
/* 217:329 */     char[] buf = new char[3 + length];
/* 218:330 */     propertyName.getChars(0, length, buf, 3);
/* 219:331 */     buf[3] = Character.toUpperCase(buf[3]);
/* 220:332 */     buf[0] = 'g';
/* 221:333 */     buf[1] = 'e';
/* 222:334 */     buf[2] = 't';
/* 223:335 */     String getterName = new String(buf);
/* 224:336 */     buf[0] = 's';
/* 225:337 */     String setterName = new String(buf);
/* 226:    */     
/* 227:339 */     Method[] methods = clazz.getMethods();
/* 228:340 */     Method getter = findMethod(methods, getterName);
/* 229:341 */     Method setter = findMethod(methods, setterName);
/* 230:342 */     if (setter == null) {
/* 231:343 */       attributes |= 0x1;
/* 232:    */     }
/* 233:345 */     defineProperty(propertyName, null, getter, setter, attributes);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void defineFunctionProperties(String[] names, Class<?> clazz, int attributes)
/* 237:    */   {
/* 238:354 */     Method[] methods = clazz.getMethods();
/* 239:355 */     for (String name : names)
/* 240:    */     {
/* 241:356 */       Method method = findMethod(methods, name);
/* 242:357 */       if (method == null) {
/* 243:358 */         throw Context.reportRuntimeError("Method \"" + name + "\" not found in \"" + clazz.getName() + '"');
/* 244:    */       }
/* 245:360 */       FunctionObject f = new FunctionObject(name, method, this);
/* 246:361 */       defineProperty(name, f, attributes);
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   private static Method findMethod(Method[] methods, String name)
/* 251:    */   {
/* 252:369 */     for (Method m : methods) {
/* 253:370 */       if (m.getName().equals(name)) {
/* 254:371 */         return m;
/* 255:    */       }
/* 256:    */     }
/* 257:374 */     return null;
/* 258:    */   }
/* 259:    */   
/* 260:    */   protected BrowserVersion getBrowserVersion()
/* 261:    */   {
/* 262:382 */     DomNode node = getDomNodeOrNull();
/* 263:383 */     if (node != null) {
/* 264:384 */       return node.getPage().getWebClient().getBrowserVersion();
/* 265:    */     }
/* 266:386 */     return getWindow().getWebWindow().getWebClient().getBrowserVersion();
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean hasInstance(Scriptable instance)
/* 270:    */   {
/* 271:394 */     if (getPrototype() == null)
/* 272:    */     {
/* 273:397 */       ScriptableObject p = (ScriptableObject)get("prototype", this);
/* 274:398 */       return p.hasInstance(instance);
/* 275:    */     }
/* 276:401 */     return super.hasInstance(instance);
/* 277:    */   }
/* 278:    */   
/* 279:    */   protected Object equivalentValues(Object value)
/* 280:    */   {
/* 281:409 */     if ((value instanceof SimpleScriptableProxy)) {
/* 282:410 */       value = ((SimpleScriptableProxy)value).getDelegee();
/* 283:    */     }
/* 284:412 */     return super.equivalentValues(value);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public SimpleScriptable clone()
/* 288:    */   {
/* 289:    */     try
/* 290:    */     {
/* 291:421 */       return (SimpleScriptable)super.clone();
/* 292:    */     }
/* 293:    */     catch (Exception e)
/* 294:    */     {
/* 295:424 */       throw new IllegalStateException("Clone not supported");
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setCaseSensitive(boolean caseSensitive)
/* 300:    */   {
/* 301:433 */     this.caseSensitive_ = caseSensitive;
/* 302:434 */     Scriptable prototype = getPrototype();
/* 303:435 */     if ((prototype instanceof SimpleScriptable)) {
/* 304:436 */       ((SimpleScriptable)prototype).setCaseSensitive(caseSensitive);
/* 305:    */     }
/* 306:    */   }
/* 307:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.SimpleScriptable
 * JD-Core Version:    0.7.0.1
 */