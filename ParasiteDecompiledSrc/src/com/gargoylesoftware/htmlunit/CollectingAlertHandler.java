/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class CollectingAlertHandler
/*  8:   */   implements AlertHandler, Serializable
/*  9:   */ {
/* 10:   */   private final List<String> collectedAlerts_;
/* 11:   */   
/* 12:   */   public CollectingAlertHandler()
/* 13:   */   {
/* 14:35 */     this(new ArrayList());
/* 15:   */   }
/* 16:   */   
/* 17:   */   public CollectingAlertHandler(List<String> list)
/* 18:   */   {
/* 19:44 */     WebAssert.notNull("list", list);
/* 20:45 */     this.collectedAlerts_ = list;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void handleAlert(Page page, String message)
/* 24:   */   {
/* 25:56 */     this.collectedAlerts_.add(message);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public List<String> getCollectedAlerts()
/* 29:   */   {
/* 30:64 */     return this.collectedAlerts_;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.CollectingAlertHandler
 * JD-Core Version:    0.7.0.1
 */