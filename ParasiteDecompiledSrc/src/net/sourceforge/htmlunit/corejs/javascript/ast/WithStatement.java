/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class WithStatement
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode expression;
/*   7:    */   private AstNode statement;
/*   8: 53 */   private int lp = -1;
/*   9: 54 */   private int rp = -1;
/*  10:    */   
/*  11:    */   public WithStatement()
/*  12:    */   {
/*  13: 57 */     this.type = 123;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public WithStatement(int pos)
/*  17:    */   {
/*  18: 64 */     super(pos);this.type = 123;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public WithStatement(int pos, int len)
/*  22:    */   {
/*  23: 68 */     super(pos, len);this.type = 123;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public AstNode getExpression()
/*  27:    */   {
/*  28: 75 */     return this.expression;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setExpression(AstNode expression)
/*  32:    */   {
/*  33: 83 */     assertNotNull(expression);
/*  34: 84 */     this.expression = expression;
/*  35: 85 */     expression.setParent(this);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AstNode getStatement()
/*  39:    */   {
/*  40: 92 */     return this.statement;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setStatement(AstNode statement)
/*  44:    */   {
/*  45:100 */     assertNotNull(statement);
/*  46:101 */     this.statement = statement;
/*  47:102 */     statement.setParent(this);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getLp()
/*  51:    */   {
/*  52:109 */     return this.lp;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setLp(int lp)
/*  56:    */   {
/*  57:116 */     this.lp = lp;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getRp()
/*  61:    */   {
/*  62:123 */     return this.rp;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setRp(int rp)
/*  66:    */   {
/*  67:130 */     this.rp = rp;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setParens(int lp, int rp)
/*  71:    */   {
/*  72:137 */     this.lp = lp;
/*  73:138 */     this.rp = rp;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toSource(int depth)
/*  77:    */   {
/*  78:143 */     StringBuilder sb = new StringBuilder();
/*  79:144 */     sb.append(makeIndent(depth));
/*  80:145 */     sb.append("with (");
/*  81:146 */     sb.append(this.expression.toSource(0));
/*  82:147 */     sb.append(") ");
/*  83:148 */     sb.append(this.statement.toSource(depth + 1));
/*  84:149 */     if (!(this.statement instanceof Block)) {
/*  85:150 */       sb.append(";\n");
/*  86:    */     }
/*  87:152 */     return sb.toString();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void visit(NodeVisitor v)
/*  91:    */   {
/*  92:160 */     if (v.visit(this))
/*  93:    */     {
/*  94:161 */       this.expression.visit(v);
/*  95:162 */       this.statement.visit(v);
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.WithStatement
 * JD-Core Version:    0.7.0.1
 */