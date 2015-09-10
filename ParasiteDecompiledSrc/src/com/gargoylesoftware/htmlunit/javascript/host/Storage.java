/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  13:    */ 
/*  14:    */ public class Storage
/*  15:    */   extends SimpleScriptable
/*  16:    */ {
/*  17:    */   static enum Type
/*  18:    */   {
/*  19: 36 */     GLOBAL_STORAGE,  LOCAL_STORAGE,  SESSION_STORAGE;
/*  20:    */     
/*  21:    */     private Type() {}
/*  22:    */   }
/*  23:    */   
/*  24: 38 */   private static List<String> RESERVED_NAMES_ = Arrays.asList(new String[] { "clear", "key", "getItem", "length", "removeItem", "setItem" });
/*  25:    */   private Type type_;
/*  26:    */   
/*  27:    */   void setType(Type type)
/*  28:    */   {
/*  29: 44 */     this.type_ = type;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void put(String name, Scriptable start, Object value)
/*  33:    */   {
/*  34: 52 */     if ((this.type_ == null) || (RESERVED_NAMES_.contains(name)))
/*  35:    */     {
/*  36: 53 */       super.put(name, start, value);
/*  37: 54 */       return;
/*  38:    */     }
/*  39: 56 */     jsxFunction_setItem(name, Context.toString(value));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object get(String name, Scriptable start)
/*  43:    */   {
/*  44: 64 */     if ((this.type_ == null) || (RESERVED_NAMES_.contains(name))) {
/*  45: 65 */       return super.get(name, start);
/*  46:    */     }
/*  47: 67 */     return jsxFunction_getItem(name);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int jsxGet_length()
/*  51:    */   {
/*  52: 75 */     return getMap().size();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void jsxFunction_removeItem(String key)
/*  56:    */   {
/*  57: 83 */     getMap().remove(key);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String jsxFunction_key(int index)
/*  61:    */   {
/*  62: 92 */     int counter = 0;
/*  63: 93 */     for (String key : getMap().keySet()) {
/*  64: 94 */       if (counter++ == index) {
/*  65: 95 */         return key;
/*  66:    */       }
/*  67:    */     }
/*  68: 98 */     return null;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private Map<String, String> getMap()
/*  72:    */   {
/*  73:102 */     return StorageImpl.getInstance().getMap(this.type_, (HtmlPage)getWindow().getWebWindow().getEnclosedPage());
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object jsxFunction_getItem(String key)
/*  77:    */   {
/*  78:111 */     return getMap().get(key);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void jsxFunction_setItem(String key, String data)
/*  82:    */   {
/*  83:120 */     getMap().put(key, data);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void jsxFunction_clear()
/*  87:    */   {
/*  88:127 */     StorageImpl.getInstance().clear(this.type_, (HtmlPage)getWindow().getWebWindow().getEnclosedPage());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getClassName()
/*  92:    */   {
/*  93:135 */     if ((getWindow().getWebWindow() != null) && 
/*  94:136 */       (getBrowserVersion().hasFeature(BrowserVersionFeatures.STORAGE_OBSOLETE))) {
/*  95:137 */       return "StorageObsolete";
/*  96:    */     }
/*  97:140 */     return super.getClassName();
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Storage
 * JD-Core Version:    0.7.0.1
 */