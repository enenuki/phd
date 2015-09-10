/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class HtmlResetInput
/*  8:   */   extends HtmlInput
/*  9:   */ {
/* 10:   */   HtmlResetInput(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/* 11:   */   {
/* 12:44 */     super(namespaceURI, qualifiedName, page, attributes);
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected void doClickAction()
/* 16:   */     throws IOException
/* 17:   */   {
/* 18:58 */     HtmlForm form = getEnclosingForm();
/* 19:59 */     if (form != null)
/* 20:   */     {
/* 21:60 */       form.reset();
/* 22:61 */       return;
/* 23:   */     }
/* 24:63 */     super.doClickAction();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void reset() {}
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlResetInput
 * JD-Core Version:    0.7.0.1
 */