/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.Page;
/*  4:   */ import java.lang.ref.WeakReference;
/*  5:   */ 
/*  6:   */ public abstract class PostponedAction
/*  7:   */ {
/*  8:   */   private final WeakReference<Page> owningPageRef_;
/*  9:   */   
/* 10:   */   public PostponedAction(Page owningPage)
/* 11:   */   {
/* 12:37 */     this.owningPageRef_ = new WeakReference(owningPage);
/* 13:   */   }
/* 14:   */   
/* 15:   */   Page getOwningPage()
/* 16:   */   {
/* 17:45 */     return (Page)this.owningPageRef_.get();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public abstract void execute()
/* 21:   */     throws Exception;
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.PostponedAction
 * JD-Core Version:    0.7.0.1
 */