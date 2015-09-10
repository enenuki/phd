/*  1:   */ package com.gargoylesoftware.htmlunit.util;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ 
/*  6:   */ public final class MimeType
/*  7:   */ {
/*  8:26 */   private static final Map<String, String> type2extension = ;
/*  9:   */   
/* 10:   */   private static Map<String, String> buildMap()
/* 11:   */   {
/* 12:29 */     Map<String, String> map = new HashMap();
/* 13:30 */     map.put("application/pdf", "pdf");
/* 14:31 */     map.put("application/x-javascript", "js");
/* 15:32 */     map.put("image/gif", "gif");
/* 16:33 */     map.put("image/jpg", "jpeg");
/* 17:34 */     map.put("image/jpeg", "jpeg");
/* 18:35 */     map.put("image/png", "png");
/* 19:36 */     map.put("image/svg+xml", "svg");
/* 20:37 */     map.put("text/css", "css");
/* 21:38 */     map.put("text/html", "html");
/* 22:39 */     map.put("text/plain", "txt");
/* 23:40 */     map.put("image/x-icon", "ico");
/* 24:41 */     return map;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static String getFileExtension(String contentType)
/* 28:   */   {
/* 29:57 */     String value = (String)type2extension.get(contentType.toLowerCase());
/* 30:58 */     if (value == null) {
/* 31:59 */       return "unknown";
/* 32:   */     }
/* 33:62 */     return value;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.MimeType
 * JD-Core Version:    0.7.0.1
 */