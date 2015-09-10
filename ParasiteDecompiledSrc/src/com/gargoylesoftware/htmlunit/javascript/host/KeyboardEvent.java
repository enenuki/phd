/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ 
/*   7:    */ public class KeyboardEvent
/*   8:    */   extends UIEvent
/*   9:    */ {
/*  10:    */   public static final int DOM_VK_MULTIPLY = 106;
/*  11:    */   public static final int DOM_VK_ADD = 107;
/*  12:    */   public static final int DOM_VK_SEPARATOR = 108;
/*  13:    */   public static final int DOM_VK_SUBTRACT = 109;
/*  14:    */   public static final int DOM_VK_DECIMAL = 110;
/*  15:    */   public static final int DOM_VK_DIVIDE = 111;
/*  16:    */   public static final int DOM_VK_F1 = 112;
/*  17:    */   public static final int DOM_VK_F2 = 113;
/*  18:    */   public static final int DOM_VK_F3 = 114;
/*  19:    */   public static final int DOM_VK_F4 = 115;
/*  20:    */   public static final int DOM_VK_F5 = 116;
/*  21:    */   public static final int DOM_VK_F6 = 117;
/*  22:    */   public static final int DOM_VK_F7 = 118;
/*  23:    */   public static final int DOM_VK_F8 = 119;
/*  24:    */   public static final int DOM_VK_CLEAR = 12;
/*  25:    */   public static final int DOM_VK_F9 = 120;
/*  26:    */   public static final int DOM_VK_F10 = 121;
/*  27:    */   public static final int DOM_VK_F11 = 122;
/*  28:    */   public static final int DOM_VK_F12 = 123;
/*  29:    */   public static final int DOM_VK_F13 = 124;
/*  30:    */   public static final int DOM_VK_F14 = 125;
/*  31:    */   public static final int DOM_VK_F15 = 126;
/*  32:    */   public static final int DOM_VK_F16 = 127;
/*  33:    */   public static final int DOM_VK_F17 = 128;
/*  34:    */   public static final int DOM_VK_F18 = 129;
/*  35:    */   public static final int DOM_VK_RETURN = 13;
/*  36:    */   public static final int DOM_VK_F19 = 130;
/*  37:    */   public static final int DOM_VK_F20 = 131;
/*  38:    */   public static final int DOM_VK_F21 = 132;
/*  39:    */   public static final int DOM_VK_F22 = 133;
/*  40:    */   public static final int DOM_VK_F23 = 134;
/*  41:    */   public static final int DOM_VK_F24 = 135;
/*  42:    */   public static final int DOM_VK_ENTER = 14;
/*  43:    */   public static final int DOM_VK_NUM_LOCK = 144;
/*  44:    */   public static final int DOM_VK_SCROLL_LOCK = 145;
/*  45:    */   public static final int DOM_VK_SHIFT = 16;
/*  46:    */   public static final int DOM_VK_CONTROL = 17;
/*  47:    */   public static final int DOM_VK_ALT = 18;
/*  48:    */   public static final int DOM_VK_COMMA = 188;
/*  49:    */   public static final int DOM_VK_PAUSE = 19;
/*  50:    */   public static final int DOM_VK_PERIOD = 190;
/*  51:    */   public static final int DOM_VK_SLASH = 191;
/*  52:    */   public static final int DOM_VK_BACK_QUOTE = 192;
/*  53:    */   public static final int DOM_VK_CAPS_LOCK = 20;
/*  54:    */   public static final int DOM_VK_OPEN_BRACKET = 219;
/*  55:    */   public static final int DOM_VK_BACK_SLASH = 220;
/*  56:    */   public static final int DOM_VK_CLOSE_BRACKET = 221;
/*  57:    */   public static final int DOM_VK_QUOTE = 222;
/*  58:    */   public static final int DOM_VK_META = 224;
/*  59:    */   public static final int DOM_VK_ESCAPE = 27;
/*  60:    */   public static final int DOM_VK_CANCEL = 3;
/*  61:    */   public static final int DOM_VK_SPACE = 32;
/*  62:    */   public static final int DOM_VK_PAGE_UP = 33;
/*  63:    */   public static final int DOM_VK_PAGE_DOWN = 34;
/*  64:    */   public static final int DOM_VK_END = 35;
/*  65:    */   public static final int DOM_VK_HOME = 36;
/*  66:    */   public static final int DOM_VK_LEFT = 37;
/*  67:    */   public static final int DOM_VK_UP = 38;
/*  68:    */   public static final int DOM_VK_RIGHT = 39;
/*  69:    */   public static final int DOM_VK_DOWN = 40;
/*  70:    */   public static final int DOM_VK_PRINTSCREEN = 44;
/*  71:    */   public static final int DOM_VK_INSERT = 45;
/*  72:    */   public static final int DOM_VK_DELETE = 46;
/*  73:    */   public static final int DOM_VK_0 = 48;
/*  74:    */   public static final int DOM_VK_1 = 49;
/*  75:    */   public static final int DOM_VK_2 = 50;
/*  76:    */   public static final int DOM_VK_3 = 51;
/*  77:    */   public static final int DOM_VK_4 = 52;
/*  78:    */   public static final int DOM_VK_5 = 53;
/*  79:    */   public static final int DOM_VK_6 = 54;
/*  80:    */   public static final int DOM_VK_7 = 55;
/*  81:    */   public static final int DOM_VK_8 = 56;
/*  82:    */   public static final int DOM_VK_9 = 57;
/*  83:    */   public static final int DOM_VK_SEMICOLON = 59;
/*  84:    */   public static final int DOM_VK_HELP = 6;
/*  85:    */   public static final int DOM_VK_EQUALS = 61;
/*  86:    */   public static final int DOM_VK_A = 65;
/*  87:    */   public static final int DOM_VK_B = 66;
/*  88:    */   public static final int DOM_VK_C = 67;
/*  89:    */   public static final int DOM_VK_D = 68;
/*  90:    */   public static final int DOM_VK_E = 69;
/*  91:    */   public static final int DOM_VK_F = 70;
/*  92:    */   public static final int DOM_VK_G = 71;
/*  93:    */   public static final int DOM_VK_H = 72;
/*  94:    */   public static final int DOM_VK_I = 73;
/*  95:    */   public static final int DOM_VK_J = 74;
/*  96:    */   public static final int DOM_VK_K = 75;
/*  97:    */   public static final int DOM_VK_L = 76;
/*  98:    */   public static final int DOM_VK_M = 77;
/*  99:    */   public static final int DOM_VK_N = 78;
/* 100:    */   public static final int DOM_VK_O = 79;
/* 101:    */   public static final int DOM_VK_BACK_SPACE = 8;
/* 102:    */   public static final int DOM_VK_P = 80;
/* 103:    */   public static final int DOM_VK_Q = 81;
/* 104:    */   public static final int DOM_VK_R = 82;
/* 105:    */   public static final int DOM_VK_S = 83;
/* 106:    */   public static final int DOM_VK_T = 84;
/* 107:    */   public static final int DOM_VK_U = 85;
/* 108:    */   public static final int DOM_VK_V = 86;
/* 109:    */   public static final int DOM_VK_W = 87;
/* 110:    */   public static final int DOM_VK_X = 88;
/* 111:    */   public static final int DOM_VK_Y = 89;
/* 112:    */   public static final int DOM_VK_TAB = 9;
/* 113:    */   public static final int DOM_VK_Z = 90;
/* 114:    */   public static final int DOM_VK_CONTEXT_MENU = 93;
/* 115:    */   public static final int DOM_VK_NUMPAD0 = 96;
/* 116:    */   public static final int DOM_VK_NUMPAD1 = 97;
/* 117:    */   public static final int DOM_VK_NUMPAD2 = 98;
/* 118:    */   public static final int DOM_VK_NUMPAD3 = 99;
/* 119:    */   public static final int DOM_VK_NUMPAD4 = 100;
/* 120:    */   public static final int DOM_VK_NUMPAD5 = 101;
/* 121:    */   public static final int DOM_VK_NUMPAD6 = 102;
/* 122:    */   public static final int DOM_VK_NUMPAD7 = 103;
/* 123:    */   public static final int DOM_VK_NUMPAD8 = 104;
/* 124:    */   public static final int DOM_VK_NUMPAD9 = 105;
/* 125:    */   private int charCode_;
/* 126:    */   
/* 127:    */   public KeyboardEvent() {}
/* 128:    */   
/* 129:    */   public KeyboardEvent(DomNode domNode, String type, int character, boolean shiftKey, boolean ctrlKey, boolean altKey)
/* 130:    */   {
/* 131:397 */     super(domNode, type);
/* 132:398 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_113))
/* 133:    */     {
/* 134:399 */       if (jsxGet_type().equals("keypress")) {
/* 135:400 */         setKeyCode(Integer.valueOf(character));
/* 136:    */       } else {
/* 137:403 */         setKeyCode(Integer.valueOf(charToKeyCode(character)));
/* 138:    */       }
/* 139:    */     }
/* 140:407 */     else if ((jsxGet_type().equals("keypress")) && (character >= 33) && (character <= 126)) {
/* 141:408 */       this.charCode_ = character;
/* 142:    */     } else {
/* 143:411 */       setKeyCode(Integer.valueOf(charToKeyCode(character)));
/* 144:    */     }
/* 145:414 */     setShiftKey(shiftKey);
/* 146:415 */     setCtrlKey(ctrlKey);
/* 147:416 */     setAltKey(altKey);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void jsxFunction_initKeyEvent(String type, boolean bubbles, boolean cancelable, Object view, boolean ctrlKey, boolean altKey, boolean shiftKey, boolean metaKey, int keyCode, int charCode)
/* 151:    */   {
/* 152:445 */     jsxFunction_initUIEvent(type, bubbles, cancelable, view, 0);
/* 153:446 */     setCtrlKey(ctrlKey);
/* 154:447 */     setAltKey(altKey);
/* 155:448 */     setShiftKey(shiftKey);
/* 156:449 */     setKeyCode(Integer.valueOf(keyCode));
/* 157:450 */     setMetaKey(metaKey);
/* 158:451 */     this.charCode_ = 0;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int jsxGet_charCode()
/* 162:    */   {
/* 163:459 */     return this.charCode_;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Object jsxGet_which()
/* 167:    */   {
/* 168:467 */     return this.charCode_ != 0 ? Integer.valueOf(this.charCode_) : jsxGet_keyCode();
/* 169:    */   }
/* 170:    */   
/* 171:    */   private static int charToKeyCode(int c)
/* 172:    */   {
/* 173:477 */     if ((c >= 97) && (c <= 122)) {
/* 174:478 */       return 65 + c - 97;
/* 175:    */     }
/* 176:481 */     switch (c)
/* 177:    */     {
/* 178:    */     case 46: 
/* 179:483 */       return 190;
/* 180:    */     case 44: 
/* 181:486 */       return 188;
/* 182:    */     case 47: 
/* 183:489 */       return 191;
/* 184:    */     }
/* 185:492 */     return c;
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.KeyboardEvent
 * JD-Core Version:    0.7.0.1
 */