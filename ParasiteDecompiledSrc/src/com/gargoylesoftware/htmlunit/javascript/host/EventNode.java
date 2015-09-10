/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.StringUtils;
/*   4:    */ 
/*   5:    */ public class EventNode
/*   6:    */   extends Node
/*   7:    */ {
/*   8:    */   public void jsxSet_onclick(Object handler)
/*   9:    */   {
/*  10: 35 */     setEventHandlerProp("onclick", handler);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public Object jsxGet_onclick()
/*  14:    */   {
/*  15: 43 */     return getEventHandlerProp("onclick");
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void jsxSet_ondblclick(Object handler)
/*  19:    */   {
/*  20: 51 */     setEventHandlerProp("ondblclick", handler);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Object jsxGet_ondblclick()
/*  24:    */   {
/*  25: 59 */     return getEventHandlerProp("ondblclick");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void jsxSet_onblur(Object handler)
/*  29:    */   {
/*  30: 67 */     setEventHandlerProp("onblur", handler);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object jsxGet_onblur()
/*  34:    */   {
/*  35: 75 */     return getEventHandlerProp("onblur");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void jsxSet_onfocus(Object handler)
/*  39:    */   {
/*  40: 83 */     setEventHandlerProp("onfocus", handler);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object jsxGet_onfocus()
/*  44:    */   {
/*  45: 91 */     return getEventHandlerProp("onfocus");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void jsxSet_onfocusin(Object handler)
/*  49:    */   {
/*  50: 99 */     setEventHandlerProp("onfocusin", handler);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object jsxGet_onfocusin()
/*  54:    */   {
/*  55:107 */     return getEventHandlerProp("onfocusin");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void jsxSet_onfocusout(Object handler)
/*  59:    */   {
/*  60:115 */     setEventHandlerProp("onfocusout", handler);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Object jsxGet_onfocusout()
/*  64:    */   {
/*  65:123 */     return getEventHandlerProp("onfocusout");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void jsxSet_onkeydown(Object handler)
/*  69:    */   {
/*  70:131 */     setEventHandlerProp("onkeydown", handler);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object jsxGet_onkeydown()
/*  74:    */   {
/*  75:139 */     return getEventHandlerProp("onkeydown");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void jsxSet_onkeypress(Object handler)
/*  79:    */   {
/*  80:147 */     setEventHandlerProp("onkeypress", handler);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Object jsxGet_onkeypress()
/*  84:    */   {
/*  85:155 */     return getEventHandlerProp("onkeypress");
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void jsxSet_onkeyup(Object handler)
/*  89:    */   {
/*  90:163 */     setEventHandlerProp("onkeyup", handler);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Object jsxGet_onkeyup()
/*  94:    */   {
/*  95:171 */     return getEventHandlerProp("onkeyup");
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void jsxSet_onmousedown(Object handler)
/*  99:    */   {
/* 100:179 */     setEventHandlerProp("onmousedown", handler);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object jsxGet_onmousedown()
/* 104:    */   {
/* 105:187 */     return getEventHandlerProp("onmousedown");
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void jsxSet_onmousemove(Object handler)
/* 109:    */   {
/* 110:195 */     setEventHandlerProp("onmousemove", handler);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Object jsxGet_onmousemove()
/* 114:    */   {
/* 115:203 */     return getEventHandlerProp("onmousemove");
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void jsxSet_onmouseout(Object handler)
/* 119:    */   {
/* 120:211 */     setEventHandlerProp("onmouseout", handler);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Object jsxGet_onmouseout()
/* 124:    */   {
/* 125:219 */     return getEventHandlerProp("onmouseout");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void jsxSet_onmouseover(Object handler)
/* 129:    */   {
/* 130:227 */     setEventHandlerProp("onmouseover", handler);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Object jsxGet_onmouseover()
/* 134:    */   {
/* 135:235 */     return getEventHandlerProp("onmouseover");
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void jsxSet_onmouseup(Object handler)
/* 139:    */   {
/* 140:243 */     setEventHandlerProp("onmouseup", handler);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Object jsxGet_onmouseup()
/* 144:    */   {
/* 145:251 */     return getEventHandlerProp("onmouseup");
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void jsxSet_oncontextmenu(Object handler)
/* 149:    */   {
/* 150:259 */     setEventHandlerProp("oncontextmenu", handler);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Object jsxGet_oncontextmenu()
/* 154:    */   {
/* 155:267 */     return getEventHandlerProp("oncontextmenu");
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void jsxSet_onresize(Object handler)
/* 159:    */   {
/* 160:275 */     setEventHandlerProp("onresize", handler);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Object jsxGet_onresize()
/* 164:    */   {
/* 165:283 */     return getEventHandlerProp("onresize");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void jsxSet_onpropertychange(Object handler)
/* 169:    */   {
/* 170:291 */     setEventHandlerProp("onpropertychange", handler);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Object jsxGet_onpropertychange()
/* 174:    */   {
/* 175:299 */     return getEventHandlerProp("onpropertychange");
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void jsxSet_onerror(Object handler)
/* 179:    */   {
/* 180:307 */     setEventHandlerProp("onerror", handler);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Object jsxGet_onerror()
/* 184:    */   {
/* 185:315 */     return getEventHandlerProp("onerror");
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean jsxFunction_fireEvent(String type, Event event)
/* 189:    */   {
/* 190:327 */     if (event == null) {
/* 191:328 */       event = new MouseEvent();
/* 192:    */     }
/* 193:332 */     String cleanedType = StringUtils.removeStart(type.toLowerCase(), "on");
/* 194:333 */     if (MouseEvent.isMouseEvent(cleanedType)) {
/* 195:334 */       event.setPrototype(getPrototype(MouseEvent.class));
/* 196:    */     } else {
/* 197:337 */       event.setPrototype(getPrototype(Event.class));
/* 198:    */     }
/* 199:339 */     event.setParentScope(getWindow());
/* 200:    */     
/* 201:    */ 
/* 202:342 */     event.jsxSet_cancelBubble(false);
/* 203:343 */     event.jsxSet_returnValue(Boolean.TRUE);
/* 204:344 */     event.jsxSet_srcElement(this);
/* 205:345 */     event.setEventType(cleanedType);
/* 206:    */     
/* 207:347 */     fireEvent(event);
/* 208:348 */     return ((Boolean)event.jsxGet_returnValue()).booleanValue();
/* 209:    */   }
/* 210:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.EventNode
 * JD-Core Version:    0.7.0.1
 */