/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Token;
/*   4:    */ 
/*   5:    */ public class UnaryExpression
/*   6:    */   extends AstNode
/*   7:    */ {
/*   8:    */   private AstNode operand;
/*   9:    */   private boolean isPostfix;
/*  10:    */   
/*  11:    */   public UnaryExpression() {}
/*  12:    */   
/*  13:    */   public UnaryExpression(int pos)
/*  14:    */   {
/*  15: 64 */     super(pos);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public UnaryExpression(int pos, int len)
/*  19:    */   {
/*  20: 71 */     super(pos, len);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public UnaryExpression(int operator, int operatorPosition, AstNode operand)
/*  24:    */   {
/*  25: 79 */     this(operator, operatorPosition, operand, false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public UnaryExpression(int operator, int operatorPosition, AstNode operand, boolean postFix)
/*  29:    */   {
/*  30: 94 */     assertNotNull(operand);
/*  31: 95 */     int beg = postFix ? operand.getPosition() : operatorPosition;
/*  32:    */     
/*  33: 97 */     int end = postFix ? operatorPosition + 2 : operand.getPosition() + operand.getLength();
/*  34:    */     
/*  35:    */ 
/*  36:100 */     setBounds(beg, end);
/*  37:101 */     setOperator(operator);
/*  38:102 */     setOperand(operand);
/*  39:103 */     this.isPostfix = postFix;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getOperator()
/*  43:    */   {
/*  44:110 */     return this.type;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setOperator(int operator)
/*  48:    */   {
/*  49:120 */     if (!Token.isValidToken(operator)) {
/*  50:121 */       throw new IllegalArgumentException("Invalid token: " + operator);
/*  51:    */     }
/*  52:122 */     setType(operator);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public AstNode getOperand()
/*  56:    */   {
/*  57:126 */     return this.operand;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setOperand(AstNode operand)
/*  61:    */   {
/*  62:134 */     assertNotNull(operand);
/*  63:135 */     this.operand = operand;
/*  64:136 */     operand.setParent(this);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean isPostfix()
/*  68:    */   {
/*  69:143 */     return this.isPostfix;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isPrefix()
/*  73:    */   {
/*  74:150 */     return !this.isPostfix;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIsPostfix(boolean isPostfix)
/*  78:    */   {
/*  79:157 */     this.isPostfix = isPostfix;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String toSource(int depth)
/*  83:    */   {
/*  84:162 */     StringBuilder sb = new StringBuilder();
/*  85:163 */     sb.append(makeIndent(depth));
/*  86:164 */     if (!this.isPostfix)
/*  87:    */     {
/*  88:165 */       sb.append(operatorToString(getType()));
/*  89:166 */       if ((getType() == 32) || (getType() == 31)) {
/*  90:168 */         sb.append(" ");
/*  91:    */       }
/*  92:    */     }
/*  93:171 */     sb.append(this.operand.toSource());
/*  94:172 */     if (this.isPostfix) {
/*  95:173 */       sb.append(operatorToString(getType()));
/*  96:    */     }
/*  97:175 */     return sb.toString();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void visit(NodeVisitor v)
/* 101:    */   {
/* 102:183 */     if (v.visit(this)) {
/* 103:184 */       this.operand.visit(v);
/* 104:    */     }
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.UnaryExpression
 * JD-Core Version:    0.7.0.1
 */