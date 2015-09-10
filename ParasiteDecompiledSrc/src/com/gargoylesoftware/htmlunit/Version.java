/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ 
/*  6:   */ public final class Version
/*  7:   */ {
/*  8:   */   public static void main(String[] args)
/*  9:   */     throws Exception
/* 10:   */   {
/* 11:40 */     if ((args.length == 1) && ("-SanityCheck".equals(args[0])))
/* 12:   */     {
/* 13:41 */       runSanityCheck();
/* 14:42 */       return;
/* 15:   */     }
/* 16:44 */     System.out.println(getProductName());
/* 17:45 */     System.out.println(getCopyright());
/* 18:46 */     System.out.println("Version: " + getProductVersion());
/* 19:   */   }
/* 20:   */   
/* 21:   */   private static void runSanityCheck()
/* 22:   */     throws Exception
/* 23:   */   {
/* 24:54 */     WebClient webClient = new WebClient();
/* 25:55 */     HtmlPage page = (HtmlPage)webClient.getPage("http://htmlunit.sourceforge.net/index.html");
/* 26:56 */     page.executeJavaScript("document.location");
/* 27:57 */     System.out.println("Sanity check complete.");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static String getProductName()
/* 31:   */   {
/* 32:65 */     return "HtmlUnit";
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static String getProductVersion()
/* 36:   */   {
/* 37:73 */     return Version.class.getPackage().getImplementationVersion();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static String getCopyright()
/* 41:   */   {
/* 42:81 */     return "Copyright (c) 2002-2011 Gargoyle Software Inc. All rights reserved.";
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.Version
 * JD-Core Version:    0.7.0.1
 */