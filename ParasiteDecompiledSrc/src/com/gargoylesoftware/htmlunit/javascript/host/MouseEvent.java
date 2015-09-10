/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*   9:    */ 
/*  10:    */ public class MouseEvent
/*  11:    */   extends UIEvent
/*  12:    */ {
/*  13:    */   public static final String TYPE_CLICK = "click";
/*  14:    */   public static final String TYPE_DBL_CLICK = "dblclick";
/*  15:    */   public static final String TYPE_MOUSE_OVER = "mouseover";
/*  16:    */   public static final String TYPE_MOUSE_MOVE = "mousemove";
/*  17:    */   public static final String TYPE_MOUSE_OUT = "mouseout";
/*  18:    */   public static final String TYPE_MOUSE_DOWN = "mousedown";
/*  19:    */   public static final String TYPE_MOUSE_UP = "mouseup";
/*  20:    */   public static final String TYPE_CONTEXT_MENU = "contextmenu";
/*  21:    */   public static final int BUTTON_LEFT = 0;
/*  22:    */   public static final int BUTTON_MIDDLE = 1;
/*  23:    */   public static final int BUTTON_RIGHT = 2;
/*  24: 70 */   private static final int[] buttonCodeToIE = { 1, 4, 2 };
/*  25:    */   private Integer screenX_;
/*  26:    */   private Integer screenY_;
/*  27:    */   private Integer clientX_;
/*  28:    */   private Integer clientY_;
/*  29:    */   private int button_;
/*  30:    */   
/*  31:    */   public MouseEvent()
/*  32:    */   {
/*  33: 85 */     this.screenX_ = Integer.valueOf(0);
/*  34: 86 */     this.screenY_ = Integer.valueOf(0);
/*  35: 87 */     setDetail(1L);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public MouseEvent(DomNode domNode, String type, boolean shiftKey, boolean ctrlKey, boolean altKey, int button)
/*  39:    */   {
/*  40:102 */     super(domNode, type);
/*  41:103 */     setShiftKey(shiftKey);
/*  42:104 */     setCtrlKey(ctrlKey);
/*  43:105 */     setAltKey(altKey);
/*  44:106 */     setMetaKey(false);
/*  45:108 */     if ((button != 0) && (button != 1) && (button != 2)) {
/*  46:109 */       throw new IllegalArgumentException("Invalid button code: " + button);
/*  47:    */     }
/*  48:111 */     this.button_ = button;
/*  49:113 */     if ("dblclick".equals(type)) {
/*  50:114 */       setDetail(2L);
/*  51:    */     } else {
/*  52:117 */       setDetail(1L);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int jsxGet_clientX()
/*  57:    */   {
/*  58:126 */     if (this.clientX_ == null) {
/*  59:127 */       this.clientX_ = Integer.valueOf(jsxGet_screenX());
/*  60:    */     }
/*  61:129 */     return this.clientX_.intValue();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int jsxGet_screenX()
/*  65:    */   {
/*  66:140 */     if (this.screenX_ == null)
/*  67:    */     {
/*  68:141 */       HTMLElement target = (HTMLElement)jsxGet_target();
/*  69:142 */       this.screenX_ = Integer.valueOf(target.getPosX() + 10);
/*  70:    */     }
/*  71:144 */     return this.screenX_.intValue();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int jsxGet_pageX()
/*  75:    */   {
/*  76:153 */     return jsxGet_screenX();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int jsxGet_clientY()
/*  80:    */   {
/*  81:161 */     if (this.clientY_ == null) {
/*  82:162 */       this.clientY_ = Integer.valueOf(jsxGet_screenY());
/*  83:    */     }
/*  84:164 */     return this.clientY_.intValue();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int jsxGet_screenY()
/*  88:    */   {
/*  89:175 */     if (this.screenY_ == null)
/*  90:    */     {
/*  91:176 */       HTMLElement target = (HTMLElement)jsxGet_target();
/*  92:177 */       this.screenY_ = Integer.valueOf(target.getPosY() + 10);
/*  93:    */     }
/*  94:179 */     return this.screenY_.intValue();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int jsxGet_pageY()
/*  98:    */   {
/*  99:188 */     return jsxGet_screenY();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int jsxGet_button()
/* 103:    */   {
/* 104:196 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_116))
/* 105:    */     {
/* 106:198 */       if (jsxGet_type().equals("contextmenu")) {
/* 107:199 */         return 0;
/* 108:    */       }
/* 109:201 */       if (jsxGet_type().equals("click")) {
/* 110:202 */         return this.button_;
/* 111:    */       }
/* 112:204 */       return buttonCodeToIE[this.button_];
/* 113:    */     }
/* 114:206 */     return this.button_;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int jsxGet_which()
/* 118:    */   {
/* 119:215 */     return this.button_ + 1;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void jsxFunction_initMouseEvent(String type, boolean bubbles, boolean cancelable, Object view, int detail, int screenX, int screenY, int clientX, int clientY, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey, int button, Object relatedTarget)
/* 123:    */   {
/* 124:253 */     jsxFunction_initUIEvent(type, bubbles, cancelable, view, detail);
/* 125:254 */     this.screenX_ = Integer.valueOf(screenX);
/* 126:255 */     this.screenY_ = Integer.valueOf(screenY);
/* 127:256 */     this.clientX_ = Integer.valueOf(clientX);
/* 128:257 */     this.clientY_ = Integer.valueOf(clientY);
/* 129:258 */     setCtrlKey(ctrlKey);
/* 130:259 */     setAltKey(altKey);
/* 131:260 */     setShiftKey(shiftKey);
/* 132:261 */     setMetaKey(metaKey);
/* 133:262 */     this.button_ = button;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static MouseEvent getCurrentMouseEvent()
/* 137:    */   {
/* 138:272 */     LinkedList<Event> events = (LinkedList)Context.getCurrentContext().getThreadLocal("Event#current");
/* 139:274 */     if ((events != null) && (!events.isEmpty()) && ((events.getLast() instanceof MouseEvent))) {
/* 140:275 */       return (MouseEvent)events.getLast();
/* 141:    */     }
/* 142:277 */     return null;
/* 143:    */   }
/* 144:    */   
/* 145:    */   static boolean isMouseEvent(String type)
/* 146:    */   {
/* 147:286 */     return ("click".equals(type)) || ("mouseover".equals(type)) || ("mousemove".equals(type)) || ("mouseout".equals(type)) || ("mousedown".equals(type)) || ("mouseup".equals(type)) || ("contextmenu".equals(type));
/* 148:    */   }
/* 149:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.MouseEvent
 * JD-Core Version:    0.7.0.1
 */