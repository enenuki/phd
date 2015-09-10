/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlBody;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  12:    */ import java.io.Serializable;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.Map.Entry;
/*  18:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  19:    */ import org.apache.commons.logging.Log;
/*  20:    */ import org.apache.commons.logging.LogFactory;
/*  21:    */ 
/*  22:    */ public class EventListenersContainer
/*  23:    */   implements Serializable
/*  24:    */ {
/*  25: 44 */   private static final Log LOG = LogFactory.getLog(EventListenersContainer.class);
/*  26:    */   
/*  27:    */   static class Handlers
/*  28:    */     implements Serializable
/*  29:    */   {
/*  30: 47 */     private final List<Function> capturingHandlers_ = new ArrayList();
/*  31: 48 */     private final List<Function> bubblingHandlers_ = new ArrayList();
/*  32:    */     private Object handler_;
/*  33:    */     
/*  34:    */     List<Function> getHandlers(boolean useCapture)
/*  35:    */     {
/*  36: 51 */       if (useCapture) {
/*  37: 52 */         return this.capturingHandlers_;
/*  38:    */       }
/*  39: 54 */       return this.bubblingHandlers_;
/*  40:    */     }
/*  41:    */     
/*  42:    */     protected Handlers clone()
/*  43:    */     {
/*  44: 58 */       Handlers clone = new Handlers();
/*  45: 59 */       clone.handler_ = this.handler_;
/*  46: 60 */       clone.capturingHandlers_.addAll(this.capturingHandlers_);
/*  47: 61 */       clone.bubblingHandlers_.addAll(this.bubblingHandlers_);
/*  48: 62 */       return clone;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52: 66 */   private final Map<String, Handlers> eventHandlers_ = new HashMap();
/*  53:    */   private final SimpleScriptable jsNode_;
/*  54:    */   
/*  55:    */   EventListenersContainer(SimpleScriptable jsNode)
/*  56:    */   {
/*  57: 70 */     this.jsNode_ = jsNode;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean addEventListener(String type, Function listener, boolean useCapture)
/*  61:    */   {
/*  62: 81 */     List<Function> listeners = getHandlersOrCreateIt(type).getHandlers(useCapture);
/*  63: 82 */     if (listeners.contains(listener))
/*  64:    */     {
/*  65: 83 */       if (LOG.isDebugEnabled()) {
/*  66: 84 */         LOG.debug(type + " listener already registered, skipping it (" + listener + ")");
/*  67:    */       }
/*  68: 86 */       return false;
/*  69:    */     }
/*  70: 88 */     listeners.add(listener);
/*  71: 89 */     return true;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private Handlers getHandlersOrCreateIt(String type)
/*  75:    */   {
/*  76: 93 */     Handlers handlers = (Handlers)this.eventHandlers_.get(type.toLowerCase());
/*  77: 94 */     if (handlers == null)
/*  78:    */     {
/*  79: 95 */       handlers = new Handlers();
/*  80: 96 */       this.eventHandlers_.put(type.toLowerCase(), handlers);
/*  81:    */     }
/*  82: 98 */     return handlers;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private List<Function> getHandlers(String eventType, boolean useCapture)
/*  86:    */   {
/*  87:102 */     Handlers handlers = (Handlers)this.eventHandlers_.get(eventType.toLowerCase());
/*  88:103 */     if (handlers != null) {
/*  89:104 */       return handlers.getHandlers(useCapture);
/*  90:    */     }
/*  91:106 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void removeEventListener(String type, Function listener, boolean useCapture)
/*  95:    */   {
/*  96:116 */     List<Function> handlers = getHandlers(type, useCapture);
/*  97:117 */     if (handlers != null) {
/*  98:118 */       handlers.remove(listener);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setEventHandlerProp(String eventName, Object value)
/* 103:    */   {
/* 104:128 */     Handlers handlers = getHandlersOrCreateIt(eventName);
/* 105:129 */     handlers.handler_ = value;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Object getEventHandlerProp(String eventName)
/* 109:    */   {
/* 110:138 */     Handlers handlers = (Handlers)this.eventHandlers_.get(eventName);
/* 111:139 */     if (handlers == null) {
/* 112:140 */       return null;
/* 113:    */     }
/* 114:143 */     return handlers.handler_;
/* 115:    */   }
/* 116:    */   
/* 117:    */   private ScriptResult executeEventListeners(boolean useCapture, Event event, Object[] args)
/* 118:    */   {
/* 119:147 */     DomNode node = this.jsNode_.getDomNodeOrDie();
/* 120:149 */     if (!event.applies(node)) {
/* 121:150 */       return null;
/* 122:    */     }
/* 123:152 */     boolean ie = this.jsNode_.getWindow().getWebWindow().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_40);
/* 124:    */     
/* 125:154 */     ScriptResult allResult = null;
/* 126:155 */     List<Function> handlers = getHandlers(event.jsxGet_type(), useCapture);
/* 127:    */     HtmlPage page;
/* 128:156 */     if ((handlers != null) && (!handlers.isEmpty()))
/* 129:    */     {
/* 130:157 */       event.setCurrentTarget(this.jsNode_);
/* 131:158 */       page = (HtmlPage)node.getPage();
/* 132:    */       
/* 133:160 */       List<Function> handlersToExecute = new ArrayList(handlers);
/* 134:161 */       for (Function listener : handlersToExecute)
/* 135:    */       {
/* 136:162 */         ScriptResult result = page.executeJavaScriptFunctionIfPossible(listener, this.jsNode_, args, node);
/* 137:163 */         if (event.isPropagationStopped()) {
/* 138:164 */           allResult = result;
/* 139:    */         }
/* 140:166 */         if (ie) {
/* 141:167 */           if (ScriptResult.isFalse(result))
/* 142:    */           {
/* 143:168 */             allResult = result;
/* 144:    */           }
/* 145:    */           else
/* 146:    */           {
/* 147:171 */             Object eventReturnValue = event.jsxGet_returnValue();
/* 148:172 */             if (((eventReturnValue instanceof Boolean)) && (!((Boolean)eventReturnValue).booleanValue())) {
/* 149:173 */               allResult = new ScriptResult(Boolean.FALSE, page);
/* 150:    */             }
/* 151:    */           }
/* 152:    */         }
/* 153:    */       }
/* 154:    */     }
/* 155:179 */     return allResult;
/* 156:    */   }
/* 157:    */   
/* 158:    */   private ScriptResult executeEventHandler(Event event, Object[] propHandlerArgs)
/* 159:    */   {
/* 160:183 */     DomNode node = this.jsNode_.getDomNodeOrDie();
/* 161:185 */     if (!event.applies(node)) {
/* 162:186 */       return null;
/* 163:    */     }
/* 164:188 */     Function handler = getEventHandler(event.jsxGet_type());
/* 165:189 */     if (handler != null)
/* 166:    */     {
/* 167:190 */       event.setCurrentTarget(this.jsNode_);
/* 168:191 */       HtmlPage page = (HtmlPage)node.getPage();
/* 169:192 */       if (LOG.isDebugEnabled()) {
/* 170:193 */         LOG.debug("Executing " + event.jsxGet_type() + " handler for " + node);
/* 171:    */       }
/* 172:195 */       return page.executeJavaScriptFunctionIfPossible(handler, this.jsNode_, propHandlerArgs, node);
/* 173:    */     }
/* 174:197 */     return null;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public ScriptResult executeBubblingListeners(Event event, Object[] args, Object[] propHandlerArgs)
/* 178:    */   {
/* 179:209 */     ScriptResult result = null;
/* 180:    */     
/* 181:    */ 
/* 182:212 */     DomNode domNode = this.jsNode_.getDomNodeOrDie();
/* 183:213 */     if (!(domNode instanceof HtmlBody))
/* 184:    */     {
/* 185:214 */       result = executeEventHandler(event, propHandlerArgs);
/* 186:215 */       if (event.isPropagationStopped()) {
/* 187:216 */         return result;
/* 188:    */       }
/* 189:    */     }
/* 190:221 */     ScriptResult newResult = executeEventListeners(false, event, args);
/* 191:222 */     if (newResult != null) {
/* 192:223 */       result = newResult;
/* 193:    */     }
/* 194:225 */     return result;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public ScriptResult executeCapturingListeners(Event event, Object[] args)
/* 198:    */   {
/* 199:235 */     return executeEventListeners(true, event, args);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Function getEventHandler(String eventName)
/* 203:    */   {
/* 204:244 */     Object handler = getEventHandlerProp(eventName.toLowerCase());
/* 205:245 */     if ((handler instanceof Function)) {
/* 206:246 */       return (Function)handler;
/* 207:    */     }
/* 208:248 */     return null;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean hasEventHandlers(String eventName)
/* 212:    */   {
/* 213:257 */     Handlers h = (Handlers)this.eventHandlers_.get(eventName);
/* 214:258 */     return (h != null) && (((h.handler_ instanceof Function)) || (!h.bubblingHandlers_.isEmpty()) || (!h.capturingHandlers_.isEmpty()));
/* 215:    */   }
/* 216:    */   
/* 217:    */   public ScriptResult executeListeners(Event event, Object[] args, Object[] propHandlerArgs)
/* 218:    */   {
/* 219:271 */     event.setEventPhase((short)1);
/* 220:272 */     ScriptResult result = executeEventListeners(true, event, args);
/* 221:273 */     if (event.isPropagationStopped()) {
/* 222:274 */       return result;
/* 223:    */     }
/* 224:278 */     event.setEventPhase((short)2);
/* 225:279 */     ScriptResult newResult = executeEventHandler(event, propHandlerArgs);
/* 226:280 */     if (newResult != null) {
/* 227:281 */       result = newResult;
/* 228:    */     }
/* 229:283 */     if (event.isPropagationStopped()) {
/* 230:284 */       return result;
/* 231:    */     }
/* 232:288 */     event.setEventPhase((short)3);
/* 233:289 */     newResult = executeEventListeners(false, event, args);
/* 234:290 */     if (newResult != null) {
/* 235:291 */       result = newResult;
/* 236:    */     }
/* 237:294 */     return result;
/* 238:    */   }
/* 239:    */   
/* 240:    */   void copyFrom(EventListenersContainer eventListenersContainer)
/* 241:    */   {
/* 242:302 */     for (Map.Entry<String, Handlers> entry : eventListenersContainer.eventHandlers_.entrySet())
/* 243:    */     {
/* 244:303 */       Handlers handlers = ((Handlers)entry.getValue()).clone();
/* 245:304 */       this.eventHandlers_.put(entry.getKey(), handlers);
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String toString()
/* 250:    */   {
/* 251:313 */     return getClass().getSimpleName() + "[node=" + this.jsNode_ + " handlers=" + this.eventHandlers_.keySet() + "]";
/* 252:    */   }
/* 253:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.EventListenersContainer
 * JD-Core Version:    0.7.0.1
 */