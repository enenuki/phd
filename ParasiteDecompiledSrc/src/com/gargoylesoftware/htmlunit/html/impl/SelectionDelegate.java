/*   1:    */ package com.gargoylesoftware.htmlunit.html.impl;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.Page;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import org.w3c.dom.ranges.Range;
/*  10:    */ 
/*  11:    */ public class SelectionDelegate
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14:    */   private final SelectableTextInput element_;
/*  15:    */   private final Range selection_;
/*  16:    */   
/*  17:    */   public SelectionDelegate(SelectableTextInput element)
/*  18:    */   {
/*  19: 49 */     this.element_ = element;
/*  20: 50 */     this.selection_ = new SimpleRange(element, element.getText().length());
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void select()
/*  24:    */   {
/*  25: 57 */     this.element_.focus();
/*  26: 58 */     setSelectionStart(0);
/*  27: 59 */     setSelectionEnd(this.element_.getText().length());
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getSelectedText()
/*  31:    */   {
/*  32: 67 */     return this.selection_.toString();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getSelectionStart()
/*  36:    */   {
/*  37: 75 */     return this.selection_.getStartOffset();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setSelectionStart(int selectionStart)
/*  41:    */   {
/*  42: 83 */     int length = this.element_.getText().length();
/*  43: 84 */     selectionStart = Math.max(0, Math.min(selectionStart, length));
/*  44: 85 */     this.selection_.setStart(this.element_, selectionStart);
/*  45: 86 */     if (this.selection_.getEndOffset() < selectionStart) {
/*  46: 87 */       this.selection_.setEnd(this.element_, selectionStart);
/*  47:    */     }
/*  48: 89 */     makeThisTheOnlySelectionIfEmulatingIE();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getSelectionEnd()
/*  52:    */   {
/*  53: 97 */     return this.selection_.getEndOffset();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setSelectionEnd(int selectionEnd)
/*  57:    */   {
/*  58:105 */     int length = this.element_.getText().length();
/*  59:106 */     selectionEnd = Math.min(length, Math.max(selectionEnd, 0));
/*  60:107 */     this.selection_.setEnd(this.element_, selectionEnd);
/*  61:108 */     if (this.selection_.getStartOffset() > selectionEnd) {
/*  62:109 */       this.selection_.setStart(this.element_, selectionEnd);
/*  63:    */     }
/*  64:111 */     makeThisTheOnlySelectionIfEmulatingIE();
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void makeThisTheOnlySelectionIfEmulatingIE()
/*  68:    */   {
/*  69:115 */     Page page = this.element_.getPage();
/*  70:116 */     if ((page instanceof HtmlPage))
/*  71:    */     {
/*  72:117 */       HtmlPage htmlPage = (HtmlPage)page;
/*  73:118 */       if (htmlPage.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_13)) {
/*  74:119 */         htmlPage.setSelectionRange(this.selection_);
/*  75:    */       }
/*  76:    */     }
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.impl.SelectionDelegate
 * JD-Core Version:    0.7.0.1
 */