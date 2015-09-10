/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*   5:    */ 
/*   6:    */ public class BoxObject
/*   7:    */   extends SimpleScriptable
/*   8:    */ {
/*   9:    */   private final HTMLElement element_;
/*  10:    */   
/*  11:    */   public BoxObject()
/*  12:    */   {
/*  13: 35 */     this(null);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public BoxObject(HTMLElement element)
/*  17:    */   {
/*  18: 43 */     this.element_ = element;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public HTMLElement jsxGet_element()
/*  22:    */   {
/*  23: 51 */     return this.element_;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object jsxGet_firstChild()
/*  27:    */   {
/*  28: 59 */     return this.element_.jsxGet_firstChild();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Object jsxGet_lastChild()
/*  32:    */   {
/*  33: 67 */     return this.element_.jsxGet_lastChild();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object jsxGet_nextSibling()
/*  37:    */   {
/*  38: 75 */     return this.element_.jsxGet_nextSibling();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Object jsxGet_previousSibling()
/*  42:    */   {
/*  43: 83 */     return this.element_.jsxGet_previousSibling();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int jsxGet_x()
/*  47:    */   {
/*  48: 91 */     return this.element_.getPosX();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int jsxGet_y()
/*  52:    */   {
/*  53: 99 */     return this.element_.getPosY();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int jsxGet_screenX()
/*  57:    */   {
/*  58:109 */     return jsxGet_x();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int jsxGet_screenY()
/*  62:    */   {
/*  63:120 */     return jsxGet_y() + 121;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int jsxGet_width()
/*  67:    */   {
/*  68:128 */     return this.element_.jsxGet_clientWidth();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int jsxGet_height()
/*  72:    */   {
/*  73:136 */     return this.element_.jsxGet_clientHeight();
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.BoxObject
 * JD-Core Version:    0.7.0.1
 */