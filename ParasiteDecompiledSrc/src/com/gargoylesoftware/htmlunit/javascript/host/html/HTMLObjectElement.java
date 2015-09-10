/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   9:    */ import com.gargoylesoftware.htmlunit.javascript.host.ActiveXObjectImpl;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.host.FormChild;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  14:    */ 
/*  15:    */ public class HTMLObjectElement
/*  16:    */   extends FormChild
/*  17:    */ {
/*  18:    */   private SimpleScriptable wrappedActiveX_;
/*  19:    */   
/*  20:    */   public String jsxGet_alt()
/*  21:    */   {
/*  22: 47 */     String alt = getDomNodeOrDie().getAttribute("alt");
/*  23: 48 */     return alt;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void jsxSet_alt(String alt)
/*  27:    */   {
/*  28: 56 */     getDomNodeOrDie().setAttribute("alt", alt);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String jsxGet_border()
/*  32:    */   {
/*  33: 64 */     String border = getDomNodeOrDie().getAttribute("border");
/*  34: 65 */     return border;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void jsxSet_border(String border)
/*  38:    */   {
/*  39: 73 */     getDomNodeOrDie().setAttribute("border", border);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String jsxGet_classid()
/*  43:    */   {
/*  44: 81 */     String classid = getDomNodeOrDie().getAttribute("classid");
/*  45: 82 */     return classid;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void jsxSet_classid(String classid)
/*  49:    */   {
/*  50: 90 */     getDomNodeOrDie().setAttribute("classid", classid);
/*  51: 91 */     if ((classid.indexOf(':') != -1) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_86)) && (getWindow().getWebWindow().getWebClient().isActiveXNative()) && (System.getProperty("os.name").contains("Windows"))) {
/*  52:    */       try
/*  53:    */       {
/*  54: 95 */         this.wrappedActiveX_ = new ActiveXObjectImpl(classid);
/*  55: 96 */         this.wrappedActiveX_.setParentScope(getParentScope());
/*  56:    */       }
/*  57:    */       catch (Exception e)
/*  58:    */       {
/*  59: 99 */         Context.throwAsScriptRuntimeEx(e);
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object get(String name, Scriptable start)
/*  65:    */   {
/*  66:109 */     if (this.wrappedActiveX_ != null) {
/*  67:110 */       return this.wrappedActiveX_.get(name, start);
/*  68:    */     }
/*  69:112 */     return super.get(name, start);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void put(String name, Scriptable start, Object value)
/*  73:    */   {
/*  74:120 */     if (this.wrappedActiveX_ != null) {
/*  75:121 */       this.wrappedActiveX_.put(name, start, value);
/*  76:    */     } else {
/*  77:124 */       super.put(name, start, value);
/*  78:    */     }
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLObjectElement
 * JD-Core Version:    0.7.0.1
 */