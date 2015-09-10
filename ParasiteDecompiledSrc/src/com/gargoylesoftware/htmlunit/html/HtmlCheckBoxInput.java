/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Page;
/*   6:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.Map;
/*  10:    */ 
/*  11:    */ public class HtmlCheckBoxInput
/*  12:    */   extends HtmlInput
/*  13:    */ {
/*  14:    */   private boolean defaultCheckedState_;
/*  15:    */   private boolean valueAtFocus_;
/*  16:    */   
/*  17:    */   HtmlCheckBoxInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  18:    */   {
/*  19: 55 */     super(namespaceURI, qualifiedName, page, attributes);
/*  20:    */     
/*  21:    */ 
/*  22: 58 */     this.defaultCheckedState_ = hasAttribute("checked");
/*  23: 61 */     if (getAttribute("value") == ATTRIBUTE_NOT_DEFINED) {
/*  24: 62 */       setAttribute("value", "on");
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void reset()
/*  29:    */   {
/*  30: 73 */     setChecked(this.defaultCheckedState_);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Page setChecked(boolean isChecked)
/*  34:    */   {
/*  35: 81 */     if (isChecked) {
/*  36: 82 */       setAttribute("checked", "checked");
/*  37:    */     } else {
/*  38: 85 */       removeAttribute("checked");
/*  39:    */     }
/*  40: 88 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_ONCHANGE_LOSING_FOCUS)) {
/*  41: 90 */       return getPage();
/*  42:    */     }
/*  43: 92 */     return executeOnChangeHandlerIfAppropriate(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String asText()
/*  47:    */   {
/*  48:103 */     return super.asText();
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void doClickAction()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54:114 */     setChecked(!isChecked());
/*  55:115 */     super.doClickAction();
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected boolean isStateUpdateFirst()
/*  59:    */   {
/*  60:125 */     return true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void preventDefault()
/*  64:    */   {
/*  65:133 */     setChecked(!isChecked());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setDefaultValue(String defaultValue)
/*  69:    */   {
/*  70:142 */     super.setDefaultValue(defaultValue);
/*  71:143 */     setValueAttribute(defaultValue);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDefaultChecked(boolean defaultChecked)
/*  75:    */   {
/*  76:152 */     this.defaultCheckedState_ = defaultChecked;
/*  77:153 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLINPUT_DEFAULT_IS_CHECKED)) {
/*  78:155 */       setChecked(defaultChecked);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isDefaultChecked()
/*  83:    */   {
/*  84:165 */     return this.defaultCheckedState_;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void focus()
/*  88:    */   {
/*  89:173 */     super.focus();
/*  90:174 */     this.valueAtFocus_ = isChecked();
/*  91:    */   }
/*  92:    */   
/*  93:    */   void removeFocus()
/*  94:    */   {
/*  95:182 */     super.removeFocus();
/*  96:    */     
/*  97:184 */     boolean fireOnChange = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_ONCHANGE_LOSING_FOCUS);
/*  98:186 */     if ((fireOnChange) && (this.valueAtFocus_ != isChecked())) {
/*  99:187 */       executeOnChangeHandlerIfAppropriate(this);
/* 100:    */     }
/* 101:189 */     this.valueAtFocus_ = isChecked();
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput
 * JD-Core Version:    0.7.0.1
 */