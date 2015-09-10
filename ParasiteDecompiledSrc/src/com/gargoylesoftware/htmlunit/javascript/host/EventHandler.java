/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  17:    */ 
/*  18:    */ public class EventHandler
/*  19:    */   extends BaseFunction
/*  20:    */ {
/*  21:    */   private final DomNode node_;
/*  22:    */   private final String eventName_;
/*  23:    */   private final String jsSnippet_;
/*  24:    */   private Function realFunction_;
/*  25:    */   
/*  26:    */   public EventHandler(DomNode node, String eventName, String jsSnippet)
/*  27:    */   {
/*  28: 46 */     this.node_ = node;
/*  29: 47 */     this.eventName_ = eventName;
/*  30:    */     String functionSignature;
/*  31:    */     String functionSignature;
/*  32: 50 */     if (node.getPage().getEnclosingWindow().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_39)) {
/*  33: 52 */       functionSignature = "function()";
/*  34:    */     } else {
/*  35: 55 */       functionSignature = "function(event)";
/*  36:    */     }
/*  37: 57 */     this.jsSnippet_ = (functionSignature + " {" + jsSnippet + "\n}");
/*  38:    */     
/*  39: 59 */     Window w = (Window)node.getPage().getEnclosingWindow().getScriptObject();
/*  40: 60 */     Scriptable function = (Scriptable)w.get("Function", w);
/*  41: 61 */     setPrototype(function.getPrototype());
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  45:    */     throws JavaScriptException
/*  46:    */   {
/*  47: 73 */     SimpleScriptable jsObj = (SimpleScriptable)this.node_.getScriptObject();
/*  48: 75 */     if (this.realFunction_ == null) {
/*  49: 76 */       this.realFunction_ = cx.compileFunction(jsObj, this.jsSnippet_, this.eventName_ + " event for " + this.node_ + " in " + this.node_.getPage().getWebResponse().getWebRequest().getUrl(), 0, null);
/*  50:    */     }
/*  51: 80 */     Object result = this.realFunction_.call(cx, scope, thisObj, args);
/*  52:    */     
/*  53: 82 */     return result;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object getDefaultValue(Class<?> typeHint)
/*  57:    */   {
/*  58: 92 */     return this.jsSnippet_;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object get(String name, Scriptable start)
/*  62:    */   {
/*  63:101 */     if ("toString".equals(name)) {
/*  64:102 */       new BaseFunction()
/*  65:    */       {
/*  66:    */         public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  67:    */         {
/*  68:106 */           return EventHandler.this.jsSnippet_;
/*  69:    */         }
/*  70:    */       };
/*  71:    */     }
/*  72:111 */     return super.get(name, start);
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.EventHandler
 * JD-Core Version:    0.7.0.1
 */