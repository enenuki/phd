/*  1:   */ package com.gargoylesoftware.htmlunit.attachment;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.Page;
/*  4:   */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  5:   */ 
/*  6:   */ public class Attachment
/*  7:   */ {
/*  8:   */   private final Page page_;
/*  9:   */   
/* 10:   */   public Attachment(Page page)
/* 11:   */   {
/* 12:39 */     this.page_ = page;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Page getPage()
/* 16:   */   {
/* 17:47 */     return this.page_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getSuggestedFilename()
/* 21:   */   {
/* 22:56 */     WebResponse response = this.page_.getWebResponse();
/* 23:57 */     String disp = response.getResponseHeaderValue("Content-Disposition");
/* 24:58 */     int start = disp.indexOf("filename=");
/* 25:59 */     if (start == -1) {
/* 26:60 */       return null;
/* 27:   */     }
/* 28:62 */     start += "filename=".length();
/* 29:63 */     int end = disp.indexOf(';', start);
/* 30:64 */     if (end == -1) {
/* 31:65 */       end = disp.length();
/* 32:   */     }
/* 33:67 */     if ((disp.charAt(start) == '"') && (disp.charAt(end - 1) == '"'))
/* 34:   */     {
/* 35:68 */       start++;
/* 36:69 */       end--;
/* 37:   */     }
/* 38:71 */     return disp.substring(start, end);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static boolean isAttachment(WebResponse response)
/* 42:   */   {
/* 43:81 */     String disp = response.getResponseHeaderValue("Content-Disposition");
/* 44:82 */     if (disp == null) {
/* 45:83 */       return false;
/* 46:   */     }
/* 47:85 */     return disp.startsWith("attachment");
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.attachment.Attachment
 * JD-Core Version:    0.7.0.1
 */