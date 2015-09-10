/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ThrowStatement
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode expression;
/*   7:    */   
/*   8:    */   public ThrowStatement()
/*   9:    */   {
/*  10: 54 */     this.type = 50;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public ThrowStatement(int pos)
/*  14:    */   {
/*  15: 61 */     super(pos);this.type = 50;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ThrowStatement(int pos, int len)
/*  19:    */   {
/*  20: 65 */     super(pos, len);this.type = 50;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ThrowStatement(AstNode expr)
/*  24:    */   {
/*  25: 54 */     this.type = 50;
/*  26:    */     
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40: 69 */     setExpression(expr);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ThrowStatement(int pos, AstNode expr)
/*  44:    */   {
/*  45: 73 */     super(pos, expr.getLength());this.type = 50;
/*  46: 74 */     setExpression(expr);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ThrowStatement(int pos, int len, AstNode expr)
/*  50:    */   {
/*  51: 78 */     super(pos, len);this.type = 50;
/*  52: 79 */     setExpression(expr);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public AstNode getExpression()
/*  56:    */   {
/*  57: 86 */     return this.expression;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setExpression(AstNode expression)
/*  61:    */   {
/*  62: 95 */     assertNotNull(expression);
/*  63: 96 */     this.expression = expression;
/*  64: 97 */     expression.setParent(this);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toSource(int depth)
/*  68:    */   {
/*  69:102 */     StringBuilder sb = new StringBuilder();
/*  70:103 */     sb.append(makeIndent(depth));
/*  71:104 */     sb.append("throw");
/*  72:105 */     sb.append(" ");
/*  73:106 */     sb.append(this.expression.toSource(0));
/*  74:107 */     sb.append(";\n");
/*  75:108 */     return sb.toString();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void visit(NodeVisitor v)
/*  79:    */   {
/*  80:116 */     if (v.visit(this)) {
/*  81:117 */       this.expression.visit(v);
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ThrowStatement
 * JD-Core Version:    0.7.0.1
 */