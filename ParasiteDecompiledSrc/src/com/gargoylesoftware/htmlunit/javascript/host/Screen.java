/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ 
/*   5:    */ public class Screen
/*   6:    */   extends SimpleScriptable
/*   7:    */ {
/*   8:    */   private int left_;
/*   9:    */   private int top_;
/*  10:    */   private int width_;
/*  11:    */   private int height_;
/*  12:    */   private int colorDepth_;
/*  13:    */   private int bufferDepth_;
/*  14:    */   private int dpi_;
/*  15:    */   private boolean fontSmoothingEnabled_;
/*  16:    */   private int updateInterval_;
/*  17:    */   
/*  18:    */   public Screen()
/*  19:    */   {
/*  20: 48 */     this.left_ = 0;
/*  21: 49 */     this.top_ = 0;
/*  22: 50 */     this.width_ = 1024;
/*  23: 51 */     this.height_ = 768;
/*  24: 52 */     this.colorDepth_ = 24;
/*  25: 53 */     this.bufferDepth_ = 24;
/*  26: 54 */     this.dpi_ = 96;
/*  27: 55 */     this.fontSmoothingEnabled_ = true;
/*  28: 56 */     this.updateInterval_ = 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int jsxGet_availHeight()
/*  32:    */   {
/*  33: 64 */     return this.height_;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int jsxGet_availLeft()
/*  37:    */   {
/*  38: 72 */     return this.left_;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int jsxGet_availTop()
/*  42:    */   {
/*  43: 80 */     return this.top_;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int jsxGet_availWidth()
/*  47:    */   {
/*  48: 88 */     return this.width_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int jsxGet_bufferDepth()
/*  52:    */   {
/*  53: 96 */     return this.bufferDepth_;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void jsxSet_bufferDepth(int bufferDepth)
/*  57:    */   {
/*  58:104 */     this.bufferDepth_ = bufferDepth;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int jsxGet_colorDepth()
/*  62:    */   {
/*  63:112 */     return this.colorDepth_;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int jsxGet_deviceXDPI()
/*  67:    */   {
/*  68:120 */     return this.dpi_;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int jsxGet_deviceYDPI()
/*  72:    */   {
/*  73:128 */     return this.dpi_;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean jsxGet_fontSmoothingEnabled()
/*  77:    */   {
/*  78:136 */     return this.fontSmoothingEnabled_;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int jsxGet_height()
/*  82:    */   {
/*  83:144 */     return this.height_;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int jsxGet_left()
/*  87:    */   {
/*  88:152 */     return this.left_;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void jsxSet_left(int left)
/*  92:    */   {
/*  93:160 */     this.left_ = left;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int jsxGet_logicalXDPI()
/*  97:    */   {
/*  98:168 */     return this.dpi_;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int jsxGet_logicalYDPI()
/* 102:    */   {
/* 103:176 */     return this.dpi_;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int jsxGet_pixelDepth()
/* 107:    */   {
/* 108:184 */     return this.colorDepth_;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int jsxGet_top()
/* 112:    */   {
/* 113:192 */     return this.top_;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void jsxSet_top(int top)
/* 117:    */   {
/* 118:200 */     this.top_ = top;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int jsxGet_updateInterval()
/* 122:    */   {
/* 123:208 */     return this.updateInterval_;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void jsxSet_updateInterval(int updateInterval)
/* 127:    */   {
/* 128:216 */     this.updateInterval_ = updateInterval;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int jsxGet_width()
/* 132:    */   {
/* 133:224 */     return this.width_;
/* 134:    */   }
/* 135:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Screen
 * JD-Core Version:    0.7.0.1
 */