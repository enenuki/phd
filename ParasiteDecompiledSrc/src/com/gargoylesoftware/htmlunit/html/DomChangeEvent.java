/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import java.util.EventObject;
/*  4:   */ 
/*  5:   */ public class DomChangeEvent
/*  6:   */   extends EventObject
/*  7:   */ {
/*  8:   */   private final DomNode changedNode_;
/*  9:   */   
/* 10:   */   public DomChangeEvent(DomNode parentNode, DomNode changedNode)
/* 11:   */   {
/* 12:37 */     super(parentNode);
/* 13:38 */     this.changedNode_ = changedNode;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public DomNode getParentNode()
/* 17:   */   {
/* 18:46 */     return (DomNode)getSource();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public DomNode getChangedNode()
/* 22:   */   {
/* 23:54 */     return this.changedNode_;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomChangeEvent
 * JD-Core Version:    0.7.0.1
 */