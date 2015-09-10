/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.ObjectInputStream;
/*  10:    */ import java.io.ObjectOutputStream;
/*  11:    */ import java.io.Serializable;
/*  12:    */ import java.net.URL;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.LinkedHashMap;
/*  15:    */ import java.util.Map;
/*  16:    */ import org.apache.commons.logging.Log;
/*  17:    */ import org.apache.commons.logging.LogFactory;
/*  18:    */ 
/*  19:    */ final class StorageImpl
/*  20:    */   implements Serializable
/*  21:    */ {
/*  22: 44 */   private static final Log LOG = LogFactory.getLog(Storage.class);
/*  23: 46 */   private static StorageImpl SINGLETON_ = new StorageImpl();
/*  24:    */   
/*  25:    */   static
/*  26:    */   {
/*  27: 49 */     load();
/*  28:    */   }
/*  29:    */   
/*  30: 52 */   private Map<String, Map<String, String>> globalStorage_ = new HashMap();
/*  31: 55 */   private Map<String, Map<String, String>> localStorage_ = new HashMap();
/*  32: 58 */   private transient Map<String, Map<String, String>> sessionStorage_ = new HashMap();
/*  33:    */   
/*  34:    */   static StorageImpl getInstance()
/*  35:    */   {
/*  36: 64 */     return SINGLETON_;
/*  37:    */   }
/*  38:    */   
/*  39:    */   void set(Storage.Type type, HtmlPage page, String key, String data)
/*  40:    */   {
/*  41: 68 */     set(getStorage(type), getKey(type, page), key, data);
/*  42:    */   }
/*  43:    */   
/*  44:    */   Map<String, String> getMap(Storage.Type type, HtmlPage page)
/*  45:    */   {
/*  46: 72 */     Map<String, Map<String, String>> storage = getStorage(type);
/*  47: 73 */     String key = getKey(type, page);
/*  48: 74 */     Map<String, String> map = (Map)storage.get(key);
/*  49: 75 */     if (map == null)
/*  50:    */     {
/*  51: 76 */       map = new LinkedHashMap();
/*  52: 77 */       storage.put(key, map);
/*  53:    */     }
/*  54: 79 */     return map;
/*  55:    */   }
/*  56:    */   
/*  57:    */   void clear(Storage.Type type, HtmlPage page)
/*  58:    */   {
/*  59: 83 */     getStorage(type).remove(getKey(type, page));
/*  60:    */   }
/*  61:    */   
/*  62:    */   private String getKey(Storage.Type type, HtmlPage page)
/*  63:    */   {
/*  64: 87 */     switch (1.$SwitchMap$com$gargoylesoftware$htmlunit$javascript$host$Storage$Type[type.ordinal()])
/*  65:    */     {
/*  66:    */     case 1: 
/*  67: 89 */       return page.getUrl().getHost();
/*  68:    */     case 2: 
/*  69: 92 */       URL url = page.getUrl();
/*  70: 93 */       return url.getProtocol() + "://" + url.getHost() + ':' + url.getProtocol();
/*  71:    */     case 3: 
/*  72: 96 */       WebWindow topWindow = page.getEnclosingWindow().getTopWindow();
/*  73: 97 */       return Integer.toHexString(topWindow.hashCode());
/*  74:    */     }
/*  75:100 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   Map<String, Map<String, String>> getStorage(Storage.Type type)
/*  79:    */   {
/*  80:105 */     switch (1.$SwitchMap$com$gargoylesoftware$htmlunit$javascript$host$Storage$Type[type.ordinal()])
/*  81:    */     {
/*  82:    */     case 1: 
/*  83:107 */       return this.globalStorage_;
/*  84:    */     case 2: 
/*  85:110 */       return this.localStorage_;
/*  86:    */     case 3: 
/*  87:113 */       return this.sessionStorage_;
/*  88:    */     }
/*  89:116 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static void set(Map<String, Map<String, String>> storage, String url, String key, String data)
/*  93:    */   {
/*  94:122 */     Map<String, String> map = (Map)storage.get(url);
/*  95:123 */     if (map == null)
/*  96:    */     {
/*  97:124 */       map = new LinkedHashMap();
/*  98:125 */       storage.put(url, map);
/*  99:    */     }
/* 100:127 */     map.put(key, data);
/* 101:128 */     save();
/* 102:    */   }
/* 103:    */   
/* 104:    */   String get(Storage.Type type, HtmlPage page, String key)
/* 105:    */   {
/* 106:132 */     return get(getStorage(type), getKey(type, page), key);
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static String get(Map<String, Map<String, String>> storage, String url, String key)
/* 110:    */   {
/* 111:137 */     Map<String, String> map = (Map)storage.get(url);
/* 112:138 */     if (map != null) {
/* 113:139 */       return (String)map.get(key);
/* 114:    */     }
/* 115:141 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private static void save()
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:146 */       File file = new File(System.getProperty("user.home"), "htmlunit.storage");
/* 123:147 */       ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
/* 124:148 */       out.writeObject(SINGLETON_);
/* 125:149 */       out.close();
/* 126:    */     }
/* 127:    */     catch (IOException e)
/* 128:    */     {
/* 129:152 */       LOG.info("Could not save storage", e);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private static void load()
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:158 */       File file = new File(System.getProperty("user.home"), "htmlunit.storage");
/* 138:159 */       if (file.exists())
/* 139:    */       {
/* 140:160 */         ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
/* 141:161 */         SINGLETON_ = (StorageImpl)in.readObject();
/* 142:162 */         SINGLETON_.sessionStorage_ = new HashMap();
/* 143:163 */         in.close();
/* 144:    */       }
/* 145:    */     }
/* 146:    */     catch (Exception e)
/* 147:    */     {
/* 148:167 */       LOG.info("Could not load storage", e);
/* 149:    */     }
/* 150:    */   }
/* 151:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.StorageImpl
 * JD-Core Version:    0.7.0.1
 */