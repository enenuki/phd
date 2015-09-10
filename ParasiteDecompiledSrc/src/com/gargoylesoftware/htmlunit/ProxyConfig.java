/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.regex.Matcher;
/*   7:    */ import java.util.regex.Pattern;
/*   8:    */ 
/*   9:    */ public class ProxyConfig
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private String proxyHost_;
/*  13:    */   private int proxyPort_;
/*  14:    */   private boolean isSocksProxy_;
/*  15: 35 */   private final Map<String, Pattern> proxyBypassHosts_ = new HashMap();
/*  16:    */   private String proxyAutoConfigUrl_;
/*  17:    */   private String proxyAutoConfigContent_;
/*  18:    */   
/*  19:    */   public ProxyConfig()
/*  20:    */   {
/*  21: 43 */     this(null, 0);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ProxyConfig(String proxyHost, int proxyPort)
/*  25:    */   {
/*  26: 52 */     this(proxyHost, proxyPort, false);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ProxyConfig(String proxyHost, int proxyPort, boolean isSocks)
/*  30:    */   {
/*  31: 62 */     this.proxyHost_ = proxyHost;
/*  32: 63 */     this.proxyPort_ = proxyPort;
/*  33: 64 */     this.isSocksProxy_ = isSocks;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getProxyHost()
/*  37:    */   {
/*  38: 72 */     return this.proxyHost_;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setProxyHost(String proxyHost)
/*  42:    */   {
/*  43: 80 */     this.proxyHost_ = proxyHost;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int getProxyPort()
/*  47:    */   {
/*  48: 88 */     return this.proxyPort_;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setProxyPort(int proxyPort)
/*  52:    */   {
/*  53: 96 */     this.proxyPort_ = proxyPort;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isSocksProxy()
/*  57:    */   {
/*  58:104 */     return this.isSocksProxy_;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setSocksProxy(boolean isSocksProxy)
/*  62:    */   {
/*  63:112 */     this.isSocksProxy_ = isSocksProxy;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void addHostsToProxyBypass(String pattern)
/*  67:    */   {
/*  68:122 */     this.proxyBypassHosts_.put(pattern, Pattern.compile(pattern));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void removeHostsFromProxyBypass(String pattern)
/*  72:    */   {
/*  73:131 */     this.proxyBypassHosts_.remove(pattern);
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected boolean shouldBypassProxy(String hostname)
/*  77:    */   {
/*  78:142 */     boolean bypass = false;
/*  79:143 */     for (Pattern p : this.proxyBypassHosts_.values()) {
/*  80:144 */       if (p.matcher(hostname).find())
/*  81:    */       {
/*  82:145 */         bypass = true;
/*  83:146 */         break;
/*  84:    */       }
/*  85:    */     }
/*  86:149 */     return bypass;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getProxyAutoConfigUrl()
/*  90:    */   {
/*  91:157 */     return this.proxyAutoConfigUrl_;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setProxyAutoConfigUrl(String proxyAutoConfigUrl)
/*  95:    */   {
/*  96:165 */     this.proxyAutoConfigUrl_ = proxyAutoConfigUrl;
/*  97:166 */     setProxyAutoConfigContent(null);
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected String getProxyAutoConfigContent()
/* 101:    */   {
/* 102:174 */     return this.proxyAutoConfigContent_;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void setProxyAutoConfigContent(String proxyAutoConfigContent)
/* 106:    */   {
/* 107:182 */     this.proxyAutoConfigContent_ = proxyAutoConfigContent;
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ProxyConfig
 * JD-Core Version:    0.7.0.1
 */