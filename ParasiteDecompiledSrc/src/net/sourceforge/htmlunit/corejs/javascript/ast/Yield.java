/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class Yield
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode value;
/*   7:    */   
/*   8:    */   public Yield()
/*   9:    */   {
/*  10: 55 */     this.type = 72;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public Yield(int pos)
/*  14:    */   {
/*  15: 62 */     super(pos);this.type = 72;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Yield(int pos, int len)
/*  19:    */   {
/*  20: 66 */     super(pos, len);this.type = 72;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Yield(int pos, int len, AstNode value)
/*  24:    */   {
/*  25: 70 */     super(pos, len);this.type = 72;
/*  26: 71 */     setValue(value);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public AstNode getValue()
/*  30:    */   {
/*  31: 78 */     return this.value;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setValue(AstNode expr)
/*  35:    */   {
/*  36: 86 */     this.value = expr;
/*  37: 87 */     if (expr != null) {
/*  38: 88 */       expr.setParent(this);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String toSource(int depth)
/*  43:    */   {
/*  44: 93 */     return "yield " + this.value.toSource(0);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void visit(NodeVisitor v)
/*  48:    */   {
/*  49:103 */     if ((v.visit(this)) && (this.value != null)) {
/*  50:104 */       this.value.visit(v);
/*  51:    */     }
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.Yield
 * JD-Core Version:    0.7.0.1
 */