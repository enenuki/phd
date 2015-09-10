/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet;
/*  5:   */ 
/*  6:   */ public class MediaList
/*  7:   */   extends SimpleScriptable
/*  8:   */ {
/*  9:   */   private final org.w3c.dom.stylesheets.MediaList wrappedList_;
/* 10:   */   
/* 11:   */   @Deprecated
/* 12:   */   public MediaList()
/* 13:   */   {
/* 14:35 */     this.wrappedList_ = null;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public MediaList(CSSStyleSheet parent, org.w3c.dom.stylesheets.MediaList wrappedList)
/* 18:   */   {
/* 19:44 */     this.wrappedList_ = wrappedList;
/* 20:45 */     setParentScope(parent);
/* 21:46 */     setPrototype(getPrototype(getClass()));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String jsxFunction_item(int index)
/* 25:   */   {
/* 26:55 */     if ((index < 0) || (index >= jsxGet_length())) {
/* 27:56 */       return null;
/* 28:   */     }
/* 29:58 */     return this.wrappedList_.item(index);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int jsxGet_length()
/* 33:   */   {
/* 34:66 */     return this.wrappedList_.getLength();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String jsxGet_mediaText()
/* 38:   */   {
/* 39:75 */     return this.wrappedList_.getMediaText();
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.MediaList
 * JD-Core Version:    0.7.0.1
 */