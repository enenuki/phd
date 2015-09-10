/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.CookieManager;
/*   5:    */ import com.gargoylesoftware.htmlunit.PluginConfiguration;
/*   6:    */ import com.gargoylesoftware.htmlunit.PluginConfiguration.MimeType;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  10:    */ 
/*  11:    */ public final class Navigator
/*  12:    */   extends SimpleScriptable
/*  13:    */ {
/*  14:    */   private PluginArray plugins_;
/*  15:    */   private MimeTypeArray mimeTypes_;
/*  16:    */   
/*  17:    */   public String jsxGet_appCodeName()
/*  18:    */   {
/*  19: 47 */     return getBrowserVersion().getApplicationCodeName();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String jsxGet_appMinorVersion()
/*  23:    */   {
/*  24: 55 */     return getBrowserVersion().getApplicationMinorVersion();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String jsxGet_appName()
/*  28:    */   {
/*  29: 63 */     return getBrowserVersion().getApplicationName();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String jsxGet_appVersion()
/*  33:    */   {
/*  34: 71 */     return getBrowserVersion().getApplicationVersion();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String jsxGet_browserLanguage()
/*  38:    */   {
/*  39: 79 */     return getBrowserVersion().getBrowserLanguage();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String jsxGet_language()
/*  43:    */   {
/*  44: 87 */     return getBrowserVersion().getBrowserLanguage();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean jsxGet_cookieEnabled()
/*  48:    */   {
/*  49: 95 */     return getWindow().getWebWindow().getWebClient().getCookieManager().isCookiesEnabled();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String jsxGet_cpuClass()
/*  53:    */   {
/*  54:103 */     return getBrowserVersion().getCpuClass();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean jsxGet_onLine()
/*  58:    */   {
/*  59:111 */     return getBrowserVersion().isOnLine();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String jsxGet_platform()
/*  63:    */   {
/*  64:119 */     return getBrowserVersion().getPlatform();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String jsxGet_product()
/*  68:    */   {
/*  69:127 */     return "Gecko";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String jsxGet_productSub()
/*  73:    */   {
/*  74:136 */     return "20100215";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String jsxGet_systemLanguage()
/*  78:    */   {
/*  79:144 */     return getBrowserVersion().getSystemLanguage();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String jsxGet_userAgent()
/*  83:    */   {
/*  84:152 */     return getBrowserVersion().getUserAgent();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String jsxGet_userLanguage()
/*  88:    */   {
/*  89:160 */     return getBrowserVersion().getUserLanguage();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Object jsxGet_plugins()
/*  93:    */   {
/*  94:168 */     initPlugins();
/*  95:169 */     return this.plugins_;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void initPlugins()
/*  99:    */   {
/* 100:173 */     if (this.plugins_ != null) {
/* 101:174 */       return;
/* 102:    */     }
/* 103:176 */     this.plugins_ = new PluginArray();
/* 104:177 */     this.plugins_.setParentScope(this);
/* 105:178 */     this.plugins_.setPrototype(getPrototype(PluginArray.class));
/* 106:    */     
/* 107:180 */     this.mimeTypes_ = new MimeTypeArray();
/* 108:181 */     this.mimeTypes_.setParentScope(this);
/* 109:182 */     this.mimeTypes_.setPrototype(getPrototype(MimeTypeArray.class));
/* 110:184 */     for (PluginConfiguration pluginConfig : getBrowserVersion().getPlugins())
/* 111:    */     {
/* 112:185 */       plugin = new Plugin(pluginConfig.getName(), pluginConfig.getDescription(), pluginConfig.getFilename());
/* 113:    */       
/* 114:187 */       plugin.setParentScope(this);
/* 115:188 */       plugin.setPrototype(getPrototype(Plugin.class));
/* 116:189 */       this.plugins_.add(plugin);
/* 117:191 */       for (PluginConfiguration.MimeType mimeTypeConfig : pluginConfig.getMimeTypes())
/* 118:    */       {
/* 119:192 */         MimeType mimeType = new MimeType(mimeTypeConfig.getType(), mimeTypeConfig.getDescription(), mimeTypeConfig.getSuffixes(), plugin);
/* 120:    */         
/* 121:194 */         mimeType.setParentScope(this);
/* 122:195 */         mimeType.setPrototype(getPrototype(MimeType.class));
/* 123:196 */         this.mimeTypes_.add(mimeType);
/* 124:197 */         plugin.add(mimeType);
/* 125:    */       }
/* 126:    */     }
/* 127:    */     Plugin plugin;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Object jsxGet_mimeTypes()
/* 131:    */   {
/* 132:207 */     initPlugins();
/* 133:208 */     return this.mimeTypes_;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean jsxFunction_javaEnabled()
/* 137:    */   {
/* 138:216 */     return getWindow().getWebWindow().getWebClient().isAppletEnabled();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean jsxFunction_taintEnabled()
/* 142:    */   {
/* 143:224 */     return false;
/* 144:    */   }
/* 145:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Navigator
 * JD-Core Version:    0.7.0.1
 */