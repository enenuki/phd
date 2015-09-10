/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public abstract class Loop
/*   4:    */   extends Scope
/*   5:    */ {
/*   6:    */   protected AstNode body;
/*   7: 47 */   protected int lp = -1;
/*   8: 48 */   protected int rp = -1;
/*   9:    */   
/*  10:    */   public Loop() {}
/*  11:    */   
/*  12:    */   public Loop(int pos)
/*  13:    */   {
/*  14: 54 */     super(pos);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Loop(int pos, int len)
/*  18:    */   {
/*  19: 58 */     super(pos, len);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public AstNode getBody()
/*  23:    */   {
/*  24: 65 */     return this.body;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setBody(AstNode body)
/*  28:    */   {
/*  29: 74 */     this.body = body;
/*  30: 75 */     int end = body.getPosition() + body.getLength();
/*  31: 76 */     setLength(end - getPosition());
/*  32: 77 */     body.setParent(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getLp()
/*  36:    */   {
/*  37: 84 */     return this.lp;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setLp(int lp)
/*  41:    */   {
/*  42: 91 */     this.lp = lp;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getRp()
/*  46:    */   {
/*  47: 98 */     return this.rp;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setRp(int rp)
/*  51:    */   {
/*  52:105 */     this.rp = rp;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setParens(int lp, int rp)
/*  56:    */   {
/*  57:112 */     this.lp = lp;
/*  58:113 */     this.rp = rp;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Loop
 * JD-Core Version:    0.7.0.1
 */