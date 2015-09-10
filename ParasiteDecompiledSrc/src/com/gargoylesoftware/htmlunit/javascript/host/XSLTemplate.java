/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ 
/*  5:   */ public class XSLTemplate
/*  6:   */   extends SimpleScriptable
/*  7:   */ {
/*  8:   */   private Node stylesheet_;
/*  9:   */   
/* 10:   */   public void jsxSet_stylesheet(Node node)
/* 11:   */   {
/* 12:35 */     this.stylesheet_ = node;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Node jsxGet_stylesheet()
/* 16:   */   {
/* 17:43 */     return this.stylesheet_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public XSLTProcessor jsxFunction_createProcessor()
/* 21:   */   {
/* 22:51 */     XSLTProcessor processor = new XSLTProcessor();
/* 23:52 */     processor.setPrototype(getPrototype(processor.getClass()));
/* 24:53 */     processor.setParentScope(getParentScope());
/* 25:54 */     processor.jsxFunction_importStylesheet(this.stylesheet_);
/* 26:55 */     return processor;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.XSLTemplate
 * JD-Core Version:    0.7.0.1
 */