/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomProcessingInstruction;
/*  4:   */ 
/*  5:   */ public final class ProcessingInstruction
/*  6:   */   extends Node
/*  7:   */ {
/*  8:   */   public String jsxGet_target()
/*  9:   */   {
/* 10:38 */     return ((DomProcessingInstruction)getDomNodeOrDie()).getTarget();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public String jsxGet_data()
/* 14:   */   {
/* 15:46 */     return ((DomProcessingInstruction)getDomNodeOrDie()).getData();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void jsxSet_data(String data)
/* 19:   */   {
/* 20:54 */     ((DomProcessingInstruction)getDomNodeOrDie()).setData(data);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.ProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */