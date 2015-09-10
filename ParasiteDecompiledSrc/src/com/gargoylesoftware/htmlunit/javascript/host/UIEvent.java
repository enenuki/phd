/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   4:    */ 
/*   5:    */ public class UIEvent
/*   6:    */   extends Event
/*   7:    */ {
/*   8:    */   private long detail_;
/*   9:    */   private boolean metaKey_;
/*  10:    */   
/*  11:    */   public UIEvent() {}
/*  12:    */   
/*  13:    */   public UIEvent(DomNode domNode, String type)
/*  14:    */   {
/*  15: 49 */     super(domNode, type);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public long jsxGet_detail()
/*  19:    */   {
/*  20: 60 */     return this.detail_;
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected void setDetail(long detail)
/*  24:    */   {
/*  25: 69 */     this.detail_ = detail;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Object jsxGet_view()
/*  29:    */   {
/*  30: 78 */     return getWindow();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void jsxFunction_initUIEvent(String type, boolean bubbles, boolean cancelable, Object view, int detail)
/*  34:    */   {
/*  35: 96 */     jsxFunction_initEvent(type, bubbles, cancelable);
/*  36:    */     
/*  37: 98 */     setDetail(detail);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean jsxGet_metaKey()
/*  41:    */   {
/*  42:106 */     return this.metaKey_;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected void setMetaKey(boolean metaKey)
/*  46:    */   {
/*  47:113 */     this.metaKey_ = metaKey;
/*  48:    */   }
/*  49:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.UIEvent
 * JD-Core Version:    0.7.0.1
 */