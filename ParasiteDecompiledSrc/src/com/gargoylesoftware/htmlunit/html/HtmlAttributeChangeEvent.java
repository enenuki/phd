/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import java.util.EventObject;
/*  4:   */ 
/*  5:   */ public class HtmlAttributeChangeEvent
/*  6:   */   extends EventObject
/*  7:   */ {
/*  8:   */   private final String name_;
/*  9:   */   private final String value_;
/* 10:   */   
/* 11:   */   public HtmlAttributeChangeEvent(HtmlElement element, String name, String value)
/* 12:   */   {
/* 13:40 */     super(element);
/* 14:41 */     this.name_ = name;
/* 15:42 */     this.value_ = value;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public HtmlElement getHtmlElement()
/* 19:   */   {
/* 20:50 */     return (HtmlElement)getSource();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:58 */     return this.name_;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getValue()
/* 29:   */   {
/* 30:70 */     return this.value_;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeEvent
 * JD-Core Version:    0.7.0.1
 */