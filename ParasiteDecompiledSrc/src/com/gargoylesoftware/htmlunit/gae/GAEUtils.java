/*  1:   */ package com.gargoylesoftware.htmlunit.gae;
/*  2:   */ 
/*  3:   */ public final class GAEUtils
/*  4:   */ {
/*  5:   */   public static boolean isGaeMode()
/*  6:   */   {
/*  7:35 */     return System.getProperty("com.google.appengine.runtime.environment") != null;
/*  8:   */   }
/*  9:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.gae.GAEUtils
 * JD-Core Version:    0.7.0.1
 */