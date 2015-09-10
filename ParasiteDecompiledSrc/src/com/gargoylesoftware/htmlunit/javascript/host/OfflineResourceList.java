/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ 
/*   5:    */ public class OfflineResourceList
/*   6:    */   extends SimpleScriptable
/*   7:    */ {
/*   8:    */   public static final short STATUS_UNCACHED = 0;
/*   9:    */   public static final short STATUS_IDLE = 1;
/*  10:    */   public static final short STATUS_CHECKING = 2;
/*  11:    */   public static final short STATUS_DOWNLOADING = 3;
/*  12:    */   public static final short STATUS_UPDATEREADY = 4;
/*  13:    */   public static final short STATUS_OBSOLETE = 5;
/*  14: 49 */   private short status_ = 0;
/*  15:    */   private Object onchecking_;
/*  16:    */   private Object onerror_;
/*  17:    */   private Object onnoupdate_;
/*  18:    */   private Object ondownloading_;
/*  19:    */   private Object onprogress_;
/*  20:    */   private Object onupdateready_;
/*  21:    */   private Object oncached_;
/*  22:    */   
/*  23:    */   public Object jsxGet_onchecking()
/*  24:    */   {
/*  25: 63 */     return this.onchecking_;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void jsxSet_onchecking(Object o)
/*  29:    */   {
/*  30: 71 */     this.onchecking_ = o;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Object jsxGet_onerror()
/*  34:    */   {
/*  35: 79 */     return this.onerror_;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void jsxSet_onerror(Object o)
/*  39:    */   {
/*  40: 87 */     this.onerror_ = o;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object jsxGet_onnoupdate()
/*  44:    */   {
/*  45: 95 */     return this.onnoupdate_;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void jsxSet_onnoupdate(Object o)
/*  49:    */   {
/*  50:103 */     this.onnoupdate_ = o;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object jsxGet_ondownloading()
/*  54:    */   {
/*  55:111 */     return this.ondownloading_;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void jsxSet_ondownloading(Object o)
/*  59:    */   {
/*  60:119 */     this.ondownloading_ = o;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Object jsxGet_onprogress()
/*  64:    */   {
/*  65:127 */     return this.onprogress_;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void jsxSet_onprogress(Object o)
/*  69:    */   {
/*  70:135 */     this.onprogress_ = o;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object jsxGet_onupdateready()
/*  74:    */   {
/*  75:143 */     return this.onupdateready_;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void jsxSet_onupdateready(Object o)
/*  79:    */   {
/*  80:151 */     this.onupdateready_ = o;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Object jsxGet_oncached()
/*  84:    */   {
/*  85:159 */     return this.oncached_;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void jsxSet_oncached(Object o)
/*  89:    */   {
/*  90:167 */     this.oncached_ = o;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public short jsxGet_status()
/*  94:    */   {
/*  95:175 */     return this.status_;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int jsxGet_length()
/*  99:    */   {
/* 100:183 */     return 0;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void jsxFunction_add(String uri) {}
/* 104:    */   
/* 105:    */   public boolean jsxFunction_hasItem(String uri)
/* 106:    */   {
/* 107:200 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String jsxFunction_item(int index)
/* 111:    */   {
/* 112:209 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void jsxFunction_remove(String uri) {}
/* 116:    */   
/* 117:    */   public void jsxFunction_swapCache() {}
/* 118:    */   
/* 119:    */   public void jsxFunction_update() {}
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.OfflineResourceList
 * JD-Core Version:    0.7.0.1
 */