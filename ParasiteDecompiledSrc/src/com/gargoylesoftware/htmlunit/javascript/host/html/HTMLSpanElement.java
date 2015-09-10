/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlAbbreviated;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlAcronym;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlAddress;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlBidirectionalOverride;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlBig;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.HtmlBlink;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.HtmlBold;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.HtmlCenter;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.HtmlCitation;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.HtmlCode;
/*  16:    */ import com.gargoylesoftware.htmlunit.html.HtmlDefinition;
/*  17:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  18:    */ import com.gargoylesoftware.htmlunit.html.HtmlEmphasis;
/*  19:    */ import com.gargoylesoftware.htmlunit.html.HtmlExample;
/*  20:    */ import com.gargoylesoftware.htmlunit.html.HtmlItalic;
/*  21:    */ import com.gargoylesoftware.htmlunit.html.HtmlKeyboard;
/*  22:    */ import com.gargoylesoftware.htmlunit.html.HtmlListing;
/*  23:    */ import com.gargoylesoftware.htmlunit.html.HtmlNoBreak;
/*  24:    */ import com.gargoylesoftware.htmlunit.html.HtmlPlainText;
/*  25:    */ import com.gargoylesoftware.htmlunit.html.HtmlS;
/*  26:    */ import com.gargoylesoftware.htmlunit.html.HtmlSample;
/*  27:    */ import com.gargoylesoftware.htmlunit.html.HtmlSmall;
/*  28:    */ import com.gargoylesoftware.htmlunit.html.HtmlStrike;
/*  29:    */ import com.gargoylesoftware.htmlunit.html.HtmlStrong;
/*  30:    */ import com.gargoylesoftware.htmlunit.html.HtmlSubscript;
/*  31:    */ import com.gargoylesoftware.htmlunit.html.HtmlSuperscript;
/*  32:    */ import com.gargoylesoftware.htmlunit.html.HtmlTeletype;
/*  33:    */ import com.gargoylesoftware.htmlunit.html.HtmlUnderlined;
/*  34:    */ import com.gargoylesoftware.htmlunit.html.HtmlVariable;
/*  35:    */ import com.gargoylesoftware.htmlunit.javascript.host.ActiveXObject;
/*  36:    */ 
/*  37:    */ public class HTMLSpanElement
/*  38:    */   extends HTMLElement
/*  39:    */ {
/*  40:    */   public void setDomNode(DomNode domNode)
/*  41:    */   {
/*  42: 73 */     super.setDomNode(domNode);
/*  43: 74 */     HtmlElement element = (HtmlElement)domNode;
/*  44: 75 */     BrowserVersion browser = getBrowserVersion();
/*  45: 76 */     if (browser.hasFeature(BrowserVersionFeatures.GENERATED_90))
/*  46:    */     {
/*  47: 77 */       if ((((element instanceof HtmlAbbreviated)) && (browser.hasFeature(BrowserVersionFeatures.HTMLABBREVIATED))) || ((element instanceof HtmlAcronym)) || ((element instanceof HtmlAddress)) || ((element instanceof HtmlBidirectionalOverride)) || ((element instanceof HtmlBig)) || ((element instanceof HtmlBold)) || ((element instanceof HtmlBlink)) || ((element instanceof HtmlCenter)) || ((element instanceof HtmlCitation)) || ((element instanceof HtmlCode)) || ((element instanceof HtmlDefinition)) || ((element instanceof HtmlExample)) || ((element instanceof HtmlEmphasis)) || ((element instanceof HtmlItalic)) || ((element instanceof HtmlKeyboard)) || ((element instanceof HtmlListing)) || ((element instanceof HtmlNoBreak)) || ((element instanceof HtmlPlainText)) || ((element instanceof HtmlS)) || ((element instanceof HtmlSample)) || ((element instanceof HtmlSmall)) || ((element instanceof HtmlStrike)) || ((element instanceof HtmlStrong)) || ((element instanceof HtmlSubscript)) || ((element instanceof HtmlSuperscript)) || ((element instanceof HtmlTeletype)) || ((element instanceof HtmlUnderlined)) || ((element instanceof HtmlVariable))) {
/*  48:105 */         ActiveXObject.addProperty(this, "cite", true, true);
/*  49:    */       }
/*  50:107 */       if ((((element instanceof HtmlAbbreviated)) && (browser.hasFeature(BrowserVersionFeatures.HTMLABBREVIATED))) || ((element instanceof HtmlAcronym)) || ((element instanceof HtmlBold)) || ((element instanceof HtmlBidirectionalOverride)) || ((element instanceof HtmlBig)) || ((element instanceof HtmlBlink)) || ((element instanceof HtmlCitation)) || ((element instanceof HtmlCode)) || ((element instanceof HtmlDefinition)) || ((element instanceof HtmlEmphasis)) || ((element instanceof HtmlItalic)) || ((element instanceof HtmlKeyboard)) || ((element instanceof HtmlNoBreak)) || ((element instanceof HtmlS)) || ((element instanceof HtmlSample)) || ((element instanceof HtmlSmall)) || ((element instanceof HtmlStrike)) || ((element instanceof HtmlStrong)) || ((element instanceof HtmlSubscript)) || ((element instanceof HtmlSuperscript)) || ((element instanceof HtmlTeletype)) || ((element instanceof HtmlUnderlined)) || ((element instanceof HtmlVariable))) {
/*  51:130 */         ActiveXObject.addProperty(this, "dateTime", true, true);
/*  52:    */       }
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String jsxGet_cite()
/*  57:    */   {
/*  58:140 */     String cite = getDomNodeOrDie().getAttribute("cite");
/*  59:141 */     return cite;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void jsxSet_cite(String cite)
/*  63:    */   {
/*  64:149 */     getDomNodeOrDie().setAttribute("cite", cite);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String jsxGet_dateTime()
/*  68:    */   {
/*  69:157 */     String dateTime = getDomNodeOrDie().getAttribute("datetime");
/*  70:158 */     return dateTime;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void jsxSet_dateTime(String dateTime)
/*  74:    */   {
/*  75:166 */     getDomNodeOrDie().setAttribute("datetime", dateTime);
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLSpanElement
 * JD-Core Version:    0.7.0.1
 */