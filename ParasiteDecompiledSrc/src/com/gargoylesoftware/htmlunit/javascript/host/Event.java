/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlArea;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.SubmittableElement;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  15:    */ 
/*  16:    */ public class Event
/*  17:    */   extends SimpleScriptable
/*  18:    */ {
/*  19:    */   static final String KEY_CURRENT_EVENT = "Event#current";
/*  20:    */   public static final String TYPE_SUBMIT = "submit";
/*  21:    */   public static final String TYPE_CHANGE = "change";
/*  22:    */   public static final String TYPE_LOAD = "load";
/*  23:    */   public static final String TYPE_UNLOAD = "unload";
/*  24:    */   public static final String TYPE_FOCUS = "focus";
/*  25:    */   public static final String TYPE_FOCUS_IN = "focusin";
/*  26:    */   public static final String TYPE_FOCUS_OUT = "focusout";
/*  27:    */   public static final String TYPE_BLUR = "blur";
/*  28:    */   public static final String TYPE_KEY_DOWN = "keydown";
/*  29:    */   public static final String TYPE_KEY_PRESS = "keypress";
/*  30:    */   public static final String TYPE_INPUT = "input";
/*  31:    */   public static final String TYPE_KEY_UP = "keyup";
/*  32:    */   public static final String TYPE_RESET = "reset";
/*  33:    */   public static final String TYPE_BEFORE_UNLOAD = "beforeunload";
/*  34:    */   public static final String TYPE_DOM_DOCUMENT_LOADED = "DOMContentLoaded";
/*  35:    */   public static final String TYPE_PROPERTY_CHANGE = "propertychange";
/*  36:    */   public static final String TYPE_READY_STATE_CHANGE = "readystatechange";
/*  37:    */   public static final String TYPE_ERROR = "error";
/*  38:    */   public static final short CAPTURING_PHASE = 1;
/*  39:    */   public static final short AT_TARGET = 2;
/*  40:    */   public static final short BUBBLING_PHASE = 3;
/*  41:    */   public static final int ABORT = 4194304;
/*  42:    */   public static final int ALT_MASK = 1;
/*  43:    */   public static final int BACK = 536870912;
/*  44:    */   public static final int BLUR = 8192;
/*  45:    */   public static final int CHANGE = 32768;
/*  46:    */   public static final int CLICK = 64;
/*  47:    */   public static final int CONTROL_MASK = 2;
/*  48:    */   public static final int DBLCLICK = 128;
/*  49:    */   public static final int DRAGDROP = 2048;
/*  50:    */   public static final int ERROR = 8388608;
/*  51:    */   public static final int FOCUS = 4096;
/*  52:    */   public static final int FORWARD = 134217728;
/*  53:    */   public static final int HELP = 268435456;
/*  54:    */   public static final int KEYDOWN = 256;
/*  55:    */   public static final int KEYPRESS = 1024;
/*  56:    */   public static final int KEYUP = 512;
/*  57:    */   public static final int LOAD = 524288;
/*  58:    */   public static final int LOCATE = 16777216;
/*  59:    */   public static final int META_MASK = 8;
/*  60:    */   public static final int MOUSEDOWN = 1;
/*  61:    */   public static final int MOUSEDRAG = 32;
/*  62:    */   public static final int MOUSEMOVE = 16;
/*  63:    */   public static final int MOUSEOUT = 8;
/*  64:    */   public static final int MOUSEOVER = 4;
/*  65:    */   public static final int MOUSEUP = 2;
/*  66:    */   public static final int MOVE = 33554432;
/*  67:    */   public static final int RESET = 65536;
/*  68:    */   public static final int RESIZE = 67108864;
/*  69:    */   public static final int SCROLL = 262144;
/*  70:    */   public static final int SELECT = 16384;
/*  71:    */   public static final int SHIFT_MASK = 4;
/*  72:    */   public static final int SUBMIT = 131072;
/*  73:    */   public static final int TEXT = 1073741824;
/*  74:    */   public static final int UNLOAD = 1048576;
/*  75:    */   public static final int XFER_DONE = 2097152;
/*  76:    */   private Object srcElement_;
/*  77:    */   private Object target_;
/*  78:    */   private Object currentTarget_;
/*  79:    */   private String type_;
/*  80:    */   private Object keyCode_;
/*  81:    */   private boolean shiftKey_;
/*  82:    */   private boolean ctrlKey_;
/*  83:    */   private boolean altKey_;
/*  84:    */   private String propertyName_;
/*  85:    */   private boolean stopPropagation_;
/*  86:    */   private Object returnValue_;
/*  87:    */   private boolean preventDefault_;
/*  88:    */   private short eventPhase_;
/*  89:250 */   private boolean bubbles_ = true;
/*  90:258 */   private boolean cancelable_ = true;
/*  91:263 */   private long timeStamp_ = System.currentTimeMillis();
/*  92:    */   
/*  93:    */   public Event(DomNode domNode, String type)
/*  94:    */   {
/*  95:271 */     Object target = domNode.getScriptObject();
/*  96:272 */     this.srcElement_ = target;
/*  97:273 */     this.target_ = target;
/*  98:274 */     this.currentTarget_ = target;
/*  99:275 */     this.type_ = type;
/* 100:276 */     setParentScope((SimpleScriptable)target);
/* 101:277 */     setPrototype(getPrototype(getClass()));
/* 102:278 */     setDomNode(domNode, false);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Event createPropertyChangeEvent(DomNode domNode, String propertyName)
/* 106:    */   {
/* 107:288 */     Event event = new Event(domNode, "propertychange");
/* 108:289 */     event.propertyName_ = propertyName;
/* 109:290 */     return event;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Event() {}
/* 113:    */   
/* 114:    */   void startFire()
/* 115:    */   {
/* 116:305 */     LinkedList<Event> events = (LinkedList)Context.getCurrentContext().getThreadLocal("Event#current");
/* 117:306 */     if (events == null)
/* 118:    */     {
/* 119:307 */       events = new LinkedList();
/* 120:308 */       Context.getCurrentContext().putThreadLocal("Event#current", events);
/* 121:    */     }
/* 122:310 */     events.add(this);
/* 123:    */   }
/* 124:    */   
/* 125:    */   void endFire()
/* 126:    */   {
/* 127:318 */     ((LinkedList)Context.getCurrentContext().getThreadLocal("Event#current")).removeLast();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Object jsxGet_srcElement()
/* 131:    */   {
/* 132:326 */     return this.srcElement_;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void jsxSet_srcElement(Object srcElement)
/* 136:    */   {
/* 137:334 */     this.srcElement_ = srcElement;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Object jsxGet_target()
/* 141:    */   {
/* 142:342 */     return this.target_;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setTarget(Object target)
/* 146:    */   {
/* 147:350 */     this.target_ = target;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Object jsxGet_currentTarget()
/* 151:    */   {
/* 152:359 */     return this.currentTarget_;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setCurrentTarget(Scriptable target)
/* 156:    */   {
/* 157:367 */     this.currentTarget_ = target;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String jsxGet_type()
/* 161:    */   {
/* 162:375 */     return this.type_;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setEventType(String eventType)
/* 166:    */   {
/* 167:383 */     this.type_ = eventType;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public long jsxGet_timeStamp()
/* 171:    */   {
/* 172:391 */     return this.timeStamp_;
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected void setKeyCode(Object keyCode)
/* 176:    */   {
/* 177:399 */     this.keyCode_ = keyCode;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Object jsxGet_keyCode()
/* 181:    */   {
/* 182:407 */     if (this.keyCode_ == null)
/* 183:    */     {
/* 184:408 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_155)) {
/* 185:409 */         return Integer.valueOf(0);
/* 186:    */       }
/* 187:411 */       return Undefined.instance;
/* 188:    */     }
/* 189:413 */     return this.keyCode_;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean jsxGet_shiftKey()
/* 193:    */   {
/* 194:420 */     return this.shiftKey_;
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected void setShiftKey(boolean shiftKey)
/* 198:    */   {
/* 199:427 */     this.shiftKey_ = shiftKey;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean jsxGet_ctrlKey()
/* 203:    */   {
/* 204:434 */     return this.ctrlKey_;
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected void setCtrlKey(boolean ctrlKey)
/* 208:    */   {
/* 209:441 */     this.ctrlKey_ = ctrlKey;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean jsxGet_altKey()
/* 213:    */   {
/* 214:448 */     return this.altKey_;
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected void setAltKey(boolean altKey)
/* 218:    */   {
/* 219:455 */     this.altKey_ = altKey;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int jsxGet_eventPhase()
/* 223:    */   {
/* 224:462 */     return this.eventPhase_;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setEventPhase(short phase)
/* 228:    */   {
/* 229:472 */     if ((phase != 1) && (phase != 2) && (phase != 3)) {
/* 230:473 */       throw new IllegalArgumentException("Illegal phase specified: " + phase);
/* 231:    */     }
/* 232:475 */     this.eventPhase_ = phase;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public boolean jsxGet_bubbles()
/* 236:    */   {
/* 237:482 */     return this.bubbles_;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean jsxGet_cancelable()
/* 241:    */   {
/* 242:489 */     return this.cancelable_;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public boolean jsxGet_cancelBubble()
/* 246:    */   {
/* 247:496 */     return this.stopPropagation_;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void jsxSet_cancelBubble(boolean newValue)
/* 251:    */   {
/* 252:503 */     this.stopPropagation_ = newValue;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void jsxFunction_stopPropagation()
/* 256:    */   {
/* 257:510 */     this.stopPropagation_ = true;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public boolean isPropagationStopped()
/* 261:    */   {
/* 262:518 */     return this.stopPropagation_;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Object jsxGet_returnValue()
/* 266:    */   {
/* 267:526 */     return this.returnValue_;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String jsxGet_propertyName()
/* 271:    */   {
/* 272:534 */     return this.propertyName_;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void jsxSet_returnValue(Object returnValue)
/* 276:    */   {
/* 277:542 */     this.returnValue_ = returnValue;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void jsxFunction_initEvent(String type, boolean bubbles, boolean cancelable)
/* 281:    */   {
/* 282:552 */     this.type_ = type;
/* 283:553 */     this.bubbles_ = bubbles;
/* 284:554 */     this.cancelable_ = cancelable;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void jsxFunction_preventDefault()
/* 288:    */   {
/* 289:563 */     this.preventDefault_ = true;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public boolean isAborted(ScriptResult result)
/* 293:    */   {
/* 294:575 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_38);
/* 295:576 */     return (ScriptResult.isFalse(result)) || ((!ie) && (this.preventDefault_)) || ((ie) && (Boolean.FALSE.equals(this.returnValue_)));
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String toString()
/* 299:    */   {
/* 300:584 */     StringBuilder buffer = new StringBuilder("Event ");
/* 301:585 */     buffer.append(jsxGet_type());
/* 302:586 */     buffer.append(" (");
/* 303:587 */     buffer.append("Current Target: ");
/* 304:588 */     buffer.append(this.currentTarget_);
/* 305:589 */     buffer.append(");");
/* 306:590 */     return buffer.toString();
/* 307:    */   }
/* 308:    */   
/* 309:    */   public boolean applies(DomNode node)
/* 310:    */   {
/* 311:600 */     if (("blur".equals(jsxGet_type())) || ("focus".equals(jsxGet_type()))) {
/* 312:601 */       return ((node instanceof SubmittableElement)) || ((node instanceof HtmlAnchor)) || ((node instanceof HtmlArea));
/* 313:    */     }
/* 314:604 */     return true;
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Event
 * JD-Core Version:    0.7.0.1
 */