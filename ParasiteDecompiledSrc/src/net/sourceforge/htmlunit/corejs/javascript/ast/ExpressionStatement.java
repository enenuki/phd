/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ExpressionStatement
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode expr;
/*   7:    */   
/*   8:    */   public ExpressionStatement()
/*   9:    */   {
/*  10: 53 */     this.type = 133;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public void setHasResult()
/*  14:    */   {
/*  15: 61 */     this.type = 134;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ExpressionStatement(AstNode expr, boolean hasResult)
/*  19:    */   {
/*  20: 78 */     this(expr);
/*  21: 79 */     if (hasResult) {
/*  22: 79 */       setHasResult();
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ExpressionStatement(AstNode expr)
/*  27:    */   {
/*  28: 91 */     this(expr.getPosition(), expr.getLength(), expr);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public ExpressionStatement(int pos, int len)
/*  32:    */   {
/*  33: 95 */     super(pos, len);this.type = 133;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public ExpressionStatement(int pos, int len, AstNode expr)
/*  37:    */   {
/*  38:106 */     super(pos, len);this.type = 133;
/*  39:107 */     setExpression(expr);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public AstNode getExpression()
/*  43:    */   {
/*  44:114 */     return this.expr;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setExpression(AstNode expression)
/*  48:    */   {
/*  49:122 */     assertNotNull(expression);
/*  50:123 */     this.expr = expression;
/*  51:124 */     expression.setParent(this);
/*  52:125 */     setLineno(expression.getLineno());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean hasSideEffects()
/*  56:    */   {
/*  57:135 */     return (this.type == 134) || (this.expr.hasSideEffects());
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String toSource(int depth)
/*  61:    */   {
/*  62:140 */     StringBuilder sb = new StringBuilder();
/*  63:141 */     sb.append(this.expr.toSource(depth));
/*  64:142 */     sb.append(";\n");
/*  65:143 */     return sb.toString();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void visit(NodeVisitor v)
/*  69:    */   {
/*  70:151 */     if (v.visit(this)) {
/*  71:152 */       this.expr.visit(v);
/*  72:    */     }
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ExpressionStatement
 * JD-Core Version:    0.7.0.1
 */