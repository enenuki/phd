/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlOption;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlSelect;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.IElementFactory;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.host.FormChild;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  15:    */ import org.xml.sax.helpers.AttributesImpl;
/*  16:    */ 
/*  17:    */ public class HTMLOptionElement
/*  18:    */   extends FormChild
/*  19:    */ {
/*  20:    */   public void jsConstructor(String newText, String newValue, boolean defaultSelected, boolean selected)
/*  21:    */   {
/*  22: 57 */     HtmlPage page = (HtmlPage)getWindow().getWebWindow().getEnclosedPage();
/*  23: 58 */     AttributesImpl attributes = null;
/*  24: 59 */     if (defaultSelected)
/*  25:    */     {
/*  26: 60 */       attributes = new AttributesImpl();
/*  27: 61 */       attributes.addAttribute(null, "selected", "selected", null, "selected");
/*  28:    */     }
/*  29: 64 */     HtmlOption htmlOption = (HtmlOption)HTMLParser.getFactory("option").createElement(page, "option", attributes);
/*  30:    */     
/*  31: 66 */     htmlOption.setSelected(selected);
/*  32: 67 */     setDomNode(htmlOption);
/*  33: 69 */     if (!"undefined".equals(newText)) {
/*  34: 70 */       htmlOption.appendChild(new DomText(page, newText));
/*  35:    */     }
/*  36: 72 */     if (!"undefined".equals(newValue)) {
/*  37: 73 */       htmlOption.setValueAttribute(newValue);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String jsxGet_value()
/*  42:    */   {
/*  43: 82 */     String value = getDomNodeOrNull().getAttribute("value");
/*  44: 83 */     if ((value == DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_170))) {
/*  45: 85 */       value = getDomNodeOrNull().getText();
/*  46:    */     }
/*  47: 87 */     return value;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void jsxSet_value(String newValue)
/*  51:    */   {
/*  52: 95 */     getDomNodeOrNull().setValueAttribute(newValue);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String jsxGet_text()
/*  56:    */   {
/*  57:104 */     return getDomNodeOrNull().getText();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public HtmlOption getDomNodeOrNull()
/*  61:    */   {
/*  62:112 */     return (HtmlOption)super.getDomNodeOrNull();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void jsxSet_text(String newText)
/*  66:    */   {
/*  67:120 */     getDomNodeOrNull().setText(newText);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean jsxGet_selected()
/*  71:    */   {
/*  72:128 */     return getDomNodeOrNull().isSelected();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void jsxSet_selected(boolean selected)
/*  76:    */   {
/*  77:136 */     HtmlOption optionNode = getDomNodeOrNull();
/*  78:137 */     HtmlSelect enclosingSelect = optionNode.getEnclosingSelect();
/*  79:138 */     if ((!selected) && (optionNode.isSelected())) {
/*  80:138 */       if (((enclosingSelect != null ? 1 : 0) & (!enclosingSelect.isMultipleSelectEnabled() ? 1 : 0)) != 0)
/*  81:    */       {
/*  82:142 */         if (!getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLOPTION_UNSELECT_SELECTS_FIRST)) {
/*  83:    */           return;
/*  84:    */         }
/*  85:143 */         enclosingSelect.getOption(0).setSelected(true); return;
/*  86:    */       }
/*  87:    */     }
/*  88:147 */     optionNode.setSelected(selected);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean jsxGet_defaultSelected()
/*  92:    */   {
/*  93:156 */     return getDomNodeOrNull().isDefaultSelected();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String jsxGet_label()
/*  97:    */   {
/*  98:164 */     return getDomNodeOrNull().getLabelAttribute();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void jsxSet_label(String label)
/* 102:    */   {
/* 103:172 */     getDomNodeOrNull().setLabelAttribute(label);
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLOptionElement
 * JD-Core Version:    0.7.0.1
 */