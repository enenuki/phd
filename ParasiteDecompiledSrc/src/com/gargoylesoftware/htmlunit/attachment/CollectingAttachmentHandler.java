/*  1:   */ package com.gargoylesoftware.htmlunit.attachment;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.Page;
/*  4:   */ import com.gargoylesoftware.htmlunit.WebAssert;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class CollectingAttachmentHandler
/*  9:   */   implements AttachmentHandler
/* 10:   */ {
/* 11:   */   private final List<Attachment> collectedAttachments_;
/* 12:   */   
/* 13:   */   public CollectingAttachmentHandler()
/* 14:   */   {
/* 15:40 */     this(new ArrayList());
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CollectingAttachmentHandler(List<Attachment> list)
/* 19:   */   {
/* 20:48 */     WebAssert.notNull("list", list);
/* 21:49 */     this.collectedAttachments_ = list;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void handleAttachment(Page page)
/* 25:   */   {
/* 26:56 */     this.collectedAttachments_.add(new Attachment(page));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public List<Attachment> getCollectedAttachments()
/* 30:   */   {
/* 31:65 */     return this.collectedAttachments_;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.attachment.CollectingAttachmentHandler
 * JD-Core Version:    0.7.0.1
 */