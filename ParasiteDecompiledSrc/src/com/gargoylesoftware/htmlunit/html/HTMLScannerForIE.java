/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import java.io.IOException;
/*   5:    */ import org.apache.commons.lang.StringUtils;
/*   6:    */ import org.apache.xerces.util.XMLStringBuffer;
/*   7:    */ import org.apache.xerces.xni.XMLDocumentHandler;
/*   8:    */ import org.cyberneko.html.HTMLScanner;
/*   9:    */ import org.cyberneko.html.HTMLScanner.ContentScanner;
/*  10:    */ 
/*  11:    */ class HTMLScannerForIE
/*  12:    */   extends HTMLScanner
/*  13:    */ {
/*  14:    */   HTMLScannerForIE(BrowserVersion browserVersion)
/*  15:    */   {
/*  16:837 */     this.fContentScanner = new ContentScannerForIE(browserVersion);
/*  17:    */   }
/*  18:    */   
/*  19:    */   class ContentScannerForIE
/*  20:    */     extends HTMLScanner.ContentScanner
/*  21:    */   {
/*  22:    */     private final BrowserVersion browserVersion_;
/*  23:    */     
/*  24:    */     ContentScannerForIE(BrowserVersion browserVersion)
/*  25:    */     {
/*  26:843 */       super();
/*  27:844 */       this.browserVersion_ = browserVersion;
/*  28:    */     }
/*  29:    */     
/*  30:    */     protected void scanComment()
/*  31:    */       throws IOException
/*  32:    */     {
/*  33:849 */       String s = nextContent(30);
/*  34:850 */       if ((s.startsWith("[if ")) && (s.contains("]>")))
/*  35:    */       {
/*  36:851 */         String condition = StringUtils.substringBefore(s.substring(4), "]>");
/*  37:    */         try
/*  38:    */         {
/*  39:853 */           if (IEConditionalCommentExpressionEvaluator.evaluate(condition, this.browserVersion_))
/*  40:    */           {
/*  41:855 */             for (int i = 0; i < condition.length() + 6; i++) {
/*  42:856 */               HTMLScannerForIE.this.read();
/*  43:    */             }
/*  44:858 */             return;
/*  45:    */           }
/*  46:    */         }
/*  47:    */         catch (Exception e)
/*  48:    */         {
/*  49:863 */           XMLStringBuffer buffer = new XMLStringBuffer("<!--");
/*  50:864 */           scanMarkupContent(buffer, '-');
/*  51:865 */           buffer.append("-->");
/*  52:866 */           HTMLScannerForIE.this.fDocumentHandler.characters(buffer, HTMLScannerForIE.this.locationAugs());
/*  53:867 */           return;
/*  54:    */         }
/*  55:    */       }
/*  56:871 */       super.scanComment();
/*  57:    */     }
/*  58:    */   }
/*  59:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HTMLScannerForIE
 * JD-Core Version:    0.7.0.1
 */