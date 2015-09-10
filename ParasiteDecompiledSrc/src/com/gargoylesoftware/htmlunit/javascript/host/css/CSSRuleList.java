/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   9:    */ 
/*  10:    */ public class CSSRuleList
/*  11:    */   extends SimpleScriptable
/*  12:    */ {
/*  13:    */   private final CSSStyleSheet stylesheet_;
/*  14:    */   private final org.w3c.dom.css.CSSRuleList rules_;
/*  15:    */   
/*  16:    */   @Deprecated
/*  17:    */   public CSSRuleList()
/*  18:    */   {
/*  19: 40 */     this.stylesheet_ = null;
/*  20: 41 */     this.rules_ = null;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public CSSRuleList(CSSStyleSheet stylesheet)
/*  24:    */   {
/*  25: 49 */     this.stylesheet_ = stylesheet;
/*  26: 50 */     this.rules_ = stylesheet.getWrappedSheet().getCssRules();
/*  27: 51 */     setParentScope(stylesheet.getParentScope());
/*  28: 52 */     setPrototype(getPrototype(getClass()));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int jsxGet_length()
/*  32:    */   {
/*  33: 60 */     if (this.rules_ != null) {
/*  34: 61 */       return this.rules_.getLength();
/*  35:    */     }
/*  36: 63 */     return 0;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object jsxFunction_item(int index)
/*  40:    */   {
/*  41: 72 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Object[] getIds()
/*  45:    */   {
/*  46: 80 */     List<String> idList = new ArrayList();
/*  47:    */     
/*  48: 82 */     int length = jsxGet_length();
/*  49: 83 */     if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_21))
/*  50:    */     {
/*  51: 84 */       for (int i = 0; i < length; i++) {
/*  52: 85 */         idList.add(Integer.toString(i));
/*  53:    */       }
/*  54: 88 */       idList.add("length");
/*  55: 89 */       idList.add("item");
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 92 */       idList.add("length");
/*  60: 94 */       for (int i = 0; i < length; i++) {
/*  61: 95 */         idList.add(Integer.toString(i));
/*  62:    */       }
/*  63:    */     }
/*  64: 98 */     return idList.toArray();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean has(String name, Scriptable start)
/*  68:    */   {
/*  69:106 */     if (("length".equals(name)) || ("item".equals(name))) {
/*  70:107 */       return true;
/*  71:    */     }
/*  72:    */     try
/*  73:    */     {
/*  74:110 */       int index = Integer.parseInt(name);
/*  75:111 */       int length = jsxGet_length();
/*  76:112 */       if ((index >= 0) && (index < length)) {
/*  77:113 */         return true;
/*  78:    */       }
/*  79:    */     }
/*  80:    */     catch (Exception e) {}
/*  81:119 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Object get(int index, Scriptable start)
/*  85:    */   {
/*  86:127 */     if ((index < 0) || (jsxGet_length() <= index)) {
/*  87:128 */       return NOT_FOUND;
/*  88:    */     }
/*  89:130 */     return CSSRule.create(this.stylesheet_, this.rules_.item(index));
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSRuleList
 * JD-Core Version:    0.7.0.1
 */