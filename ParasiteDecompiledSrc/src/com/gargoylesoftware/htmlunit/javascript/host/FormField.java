/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlForm;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   7:    */ 
/*   8:    */ public class FormField
/*   9:    */   extends FormChild
/*  10:    */ {
/*  11:    */   public void setDomNode(DomNode domNode)
/*  12:    */   {
/*  13: 42 */     super.setDomNode(domNode);
/*  14:    */     
/*  15: 44 */     HtmlForm form = ((HtmlElement)domNode).getEnclosingForm();
/*  16: 45 */     if (form != null) {
/*  17: 46 */       setParentScope(getScriptableFor(form));
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public String jsxGet_value()
/*  22:    */   {
/*  23: 56 */     return getDomNodeOrDie().getAttribute("value");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void jsxSet_value(String newValue)
/*  27:    */   {
/*  28: 65 */     getDomNodeOrDie().setAttribute("value", newValue);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String jsxGet_name()
/*  32:    */   {
/*  33: 74 */     return getDomNodeOrDie().getAttribute("name");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void jsxSet_name(String newName)
/*  37:    */   {
/*  38: 83 */     getDomNodeOrDie().setAttribute("name", newName);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String jsxGet_type()
/*  42:    */   {
/*  43: 92 */     return getDomNodeOrDie().getAttribute("type");
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void jsxSet_onchange(Object onchange)
/*  47:    */   {
/*  48:100 */     setEventHandlerProp("onchange", onchange);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Function jsxGet_onchange()
/*  52:    */   {
/*  53:108 */     return getEventHandler("onchange");
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.FormField
 * JD-Core Version:    0.7.0.1
 */