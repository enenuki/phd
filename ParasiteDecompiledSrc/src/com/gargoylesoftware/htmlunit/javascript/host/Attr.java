/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   8:    */ 
/*   9:    */ public class Attr
/*  10:    */   extends Node
/*  11:    */ {
/*  12:    */   public void detachFromParent()
/*  13:    */   {
/*  14: 44 */     DomAttr domNode = getDomNodeOrDie();
/*  15: 45 */     DomElement parent = (DomElement)domNode.getParentNode();
/*  16: 46 */     if (parent != null) {
/*  17: 47 */       domNode.setValue(parent.getAttribute(jsxGet_name()));
/*  18:    */     }
/*  19: 49 */     domNode.remove();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean jsxGet_isId()
/*  23:    */   {
/*  24: 57 */     return getDomNodeOrDie().isId();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean jsxGet_expando()
/*  28:    */   {
/*  29: 65 */     return true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String jsxGet_name()
/*  33:    */   {
/*  34: 73 */     return getDomNodeOrDie().getName();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String jsxGet_nodeValue()
/*  38:    */   {
/*  39: 82 */     return jsxGet_value();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object jsxGet_ownerElement()
/*  43:    */   {
/*  44: 90 */     DomElement parent = getDomNodeOrDie().getOwnerElement();
/*  45: 91 */     if (parent != null) {
/*  46: 92 */       return parent.getScriptObject();
/*  47:    */     }
/*  48: 94 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Node jsxGet_parentNode()
/*  52:    */   {
/*  53:103 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean jsxGet_specified()
/*  57:    */   {
/*  58:111 */     return getDomNodeOrDie().getSpecified();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String jsxGet_value()
/*  62:    */   {
/*  63:119 */     return getDomNodeOrDie().getValue();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void jsxSet_value(String value)
/*  67:    */   {
/*  68:127 */     getDomNodeOrDie().setValue(value);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Node jsxGet_firstChild()
/*  72:    */   {
/*  73:135 */     return jsxGet_lastChild();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Node jsxGet_lastChild()
/*  77:    */   {
/*  78:143 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_151))
/*  79:    */     {
/*  80:144 */       DomText text = new DomText(getDomNodeOrDie().getPage(), jsxGet_nodeValue());
/*  81:145 */       return (Node)text.getScriptObject();
/*  82:    */     }
/*  83:147 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public DomAttr getDomNodeOrDie()
/*  87:    */     throws IllegalStateException
/*  88:    */   {
/*  89:156 */     return (DomAttr)super.getDomNodeOrDie();
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Attr
 * JD-Core Version:    0.7.0.1
 */