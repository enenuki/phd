/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ParenthesizedExpression
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode expression;
/*   7:    */   
/*   8:    */   public ParenthesizedExpression()
/*   9:    */   {
/*  10: 52 */     this.type = 87;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public ParenthesizedExpression(int pos)
/*  14:    */   {
/*  15: 59 */     super(pos);this.type = 87;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ParenthesizedExpression(int pos, int len)
/*  19:    */   {
/*  20: 63 */     super(pos, len);this.type = 87;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ParenthesizedExpression(AstNode expr)
/*  24:    */   {
/*  25: 67 */     this(expr != null ? expr.getPosition() : 0, expr != null ? expr.getLength() : 1, expr);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public ParenthesizedExpression(int pos, int len, AstNode expr)
/*  29:    */   {
/*  30: 73 */     super(pos, len);this.type = 87;
/*  31: 74 */     setExpression(expr);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public AstNode getExpression()
/*  35:    */   {
/*  36: 81 */     return this.expression;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setExpression(AstNode expression)
/*  40:    */   {
/*  41: 91 */     assertNotNull(expression);
/*  42: 92 */     this.expression = expression;
/*  43: 93 */     expression.setParent(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String toSource(int depth)
/*  47:    */   {
/*  48: 98 */     return makeIndent(depth) + "(" + this.expression.toSource(0) + ")";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void visit(NodeVisitor v)
/*  52:    */   {
/*  53:106 */     if (v.visit(this)) {
/*  54:107 */       this.expression.visit(v);
/*  55:    */     }
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ParenthesizedExpression
 * JD-Core Version:    0.7.0.1
 */