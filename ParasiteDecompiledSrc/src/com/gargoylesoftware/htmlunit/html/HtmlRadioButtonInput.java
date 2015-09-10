/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Page;
/*   6:    */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*   7:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public class HtmlRadioButtonInput
/*  13:    */   extends HtmlInput
/*  14:    */ {
/*  15:    */   private boolean defaultCheckedState_;
/*  16:    */   private boolean valueAtFocus_;
/*  17:    */   
/*  18:    */   HtmlRadioButtonInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  19:    */   {
/*  20: 58 */     super(namespaceURI, qualifiedName, page, attributes);
/*  21: 61 */     if (getAttribute("value") == ATTRIBUTE_NOT_DEFINED) {
/*  22: 62 */       setAttribute("value", "on");
/*  23:    */     }
/*  24: 65 */     this.defaultCheckedState_ = hasAttribute("checked");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void reset()
/*  28:    */   {
/*  29: 74 */     if (this.defaultCheckedState_) {
/*  30: 75 */       setAttribute("checked", "checked");
/*  31:    */     } else {
/*  32: 78 */       removeAttribute("checked");
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Page setChecked(boolean isChecked)
/*  37:    */   {
/*  38: 91 */     HtmlForm form = getEnclosingForm();
/*  39: 92 */     boolean changed = isChecked() != isChecked;
/*  40:    */     
/*  41: 94 */     Page page = getPage();
/*  42: 95 */     if (isChecked)
/*  43:    */     {
/*  44: 96 */       if (form != null) {
/*  45: 97 */         form.setCheckedRadioButton(this);
/*  46: 99 */       } else if ((page instanceof HtmlPage)) {
/*  47:100 */         ((HtmlPage)page).setCheckedRadioButton(this);
/*  48:    */       }
/*  49:    */     }
/*  50:    */     else {
/*  51:104 */       removeAttribute("checked");
/*  52:    */     }
/*  53:107 */     if ((changed) && (!getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_ONCHANGE_LOSING_FOCUS)))
/*  54:    */     {
/*  55:109 */       ScriptResult scriptResult = fireEvent("change");
/*  56:110 */       if (scriptResult != null) {
/*  57:111 */         page = scriptResult.getNewPage();
/*  58:    */       }
/*  59:    */     }
/*  60:114 */     return page;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String asText()
/*  64:    */   {
/*  65:125 */     return super.asText();
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected void doClickAction()
/*  69:    */     throws IOException
/*  70:    */   {
/*  71:136 */     setChecked(true);
/*  72:137 */     super.doClickAction();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setDefaultValue(String defaultValue)
/*  76:    */   {
/*  77:147 */     super.setDefaultValue(defaultValue);
/*  78:148 */     setValueAttribute(defaultValue);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setDefaultChecked(boolean defaultChecked)
/*  82:    */   {
/*  83:157 */     this.defaultCheckedState_ = defaultChecked;
/*  84:158 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLINPUT_DEFAULT_IS_CHECKED)) {
/*  85:160 */       setChecked(defaultChecked);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isDefaultChecked()
/*  90:    */   {
/*  91:170 */     return this.defaultCheckedState_;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected boolean isStateUpdateFirst()
/*  95:    */   {
/*  96:178 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected void onAddedToPage()
/* 100:    */   {
/* 101:186 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_4)) {
/* 102:187 */       setChecked(isDefaultChecked());
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void focus()
/* 107:    */   {
/* 108:196 */     super.focus();
/* 109:197 */     this.valueAtFocus_ = isChecked();
/* 110:    */   }
/* 111:    */   
/* 112:    */   void removeFocus()
/* 113:    */   {
/* 114:205 */     super.removeFocus();
/* 115:    */     
/* 116:207 */     boolean fireOnChange = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_ONCHANGE_LOSING_FOCUS);
/* 117:209 */     if ((fireOnChange) && (this.valueAtFocus_ != isChecked())) {
/* 118:210 */       executeOnChangeHandlerIfAppropriate(this);
/* 119:    */     }
/* 120:212 */     this.valueAtFocus_ = isChecked();
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput
 * JD-Core Version:    0.7.0.1
 */