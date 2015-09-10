/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ 
/*   5:    */ public class TextRectangle
/*   6:    */   extends SimpleScriptable
/*   7:    */ {
/*   8:    */   private int bottom_;
/*   9:    */   private int left_;
/*  10:    */   private int right_;
/*  11:    */   private int top_;
/*  12:    */   
/*  13:    */   public TextRectangle() {}
/*  14:    */   
/*  15:    */   public TextRectangle(int bottom, int left, int right, int top)
/*  16:    */   {
/*  17: 49 */     this.bottom_ = bottom;
/*  18: 50 */     this.left_ = left;
/*  19: 51 */     this.right_ = right;
/*  20: 52 */     this.top_ = top;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void jsxSet_bottom(int bottom)
/*  24:    */   {
/*  25: 60 */     this.bottom_ = bottom;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int jsxGet_bottom()
/*  29:    */   {
/*  30: 68 */     return this.bottom_;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void jsxSet_left(int left)
/*  34:    */   {
/*  35: 76 */     this.left_ = left;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int jsxGet_left()
/*  39:    */   {
/*  40: 84 */     return this.left_;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void jsxSet_right(int right)
/*  44:    */   {
/*  45: 92 */     this.right_ = right;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int jsxGet_right()
/*  49:    */   {
/*  50:100 */     return this.right_;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void jsxSet_top(int top)
/*  54:    */   {
/*  55:108 */     this.top_ = top;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int jsxGet_top()
/*  59:    */   {
/*  60:116 */     return this.top_;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.TextRectangle
 * JD-Core Version:    0.7.0.1
 */