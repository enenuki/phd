/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ public class WebWindowNotFoundException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private final String name_;
/*  7:   */   
/*  8:   */   public WebWindowNotFoundException(String name)
/*  9:   */   {
/* 10:32 */     super("Searching for [" + name + "]");
/* 11:33 */     this.name_ = name;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:41 */     return this.name_;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebWindowNotFoundException
 * JD-Core Version:    0.7.0.1
 */