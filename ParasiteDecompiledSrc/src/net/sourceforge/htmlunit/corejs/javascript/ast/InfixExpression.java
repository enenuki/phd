/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Token;
/*   4:    */ 
/*   5:    */ public class InfixExpression
/*   6:    */   extends AstNode
/*   7:    */ {
/*   8:    */   protected AstNode left;
/*   9:    */   protected AstNode right;
/*  10: 51 */   protected int operatorPosition = -1;
/*  11:    */   
/*  12:    */   public InfixExpression() {}
/*  13:    */   
/*  14:    */   public InfixExpression(int pos)
/*  15:    */   {
/*  16: 57 */     super(pos);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public InfixExpression(int pos, int len)
/*  20:    */   {
/*  21: 61 */     super(pos, len);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public InfixExpression(int pos, int len, AstNode left, AstNode right)
/*  25:    */   {
/*  26: 67 */     super(pos, len);
/*  27: 68 */     setLeft(left);
/*  28: 69 */     setRight(right);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public InfixExpression(AstNode left, AstNode right)
/*  32:    */   {
/*  33: 77 */     setLeftAndRight(left, right);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public InfixExpression(int operator, AstNode left, AstNode right, int operatorPos)
/*  37:    */   {
/*  38: 86 */     setType(operator);
/*  39: 87 */     setOperatorPosition(operatorPos - left.getPosition());
/*  40: 88 */     setLeftAndRight(left, right);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setLeftAndRight(AstNode left, AstNode right)
/*  44:    */   {
/*  45: 92 */     assertNotNull(left);
/*  46: 93 */     assertNotNull(right);
/*  47:    */     
/*  48: 95 */     int beg = left.getPosition();
/*  49: 96 */     int end = right.getPosition() + right.getLength();
/*  50: 97 */     setBounds(beg, end);
/*  51:    */     
/*  52: 99 */     setLeft(left);
/*  53:100 */     setRight(right);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getOperator()
/*  57:    */   {
/*  58:107 */     return getType();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setOperator(int operator)
/*  62:    */   {
/*  63:117 */     if (!Token.isValidToken(operator)) {
/*  64:118 */       throw new IllegalArgumentException("Invalid token: " + operator);
/*  65:    */     }
/*  66:119 */     setType(operator);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public AstNode getLeft()
/*  70:    */   {
/*  71:126 */     return this.left;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setLeft(AstNode left)
/*  75:    */   {
/*  76:136 */     assertNotNull(left);
/*  77:137 */     this.left = left;
/*  78:138 */     left.setParent(this);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public AstNode getRight()
/*  82:    */   {
/*  83:148 */     return this.right;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setRight(AstNode right)
/*  87:    */   {
/*  88:157 */     assertNotNull(right);
/*  89:158 */     this.right = right;
/*  90:159 */     right.setParent(this);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getOperatorPosition()
/*  94:    */   {
/*  95:166 */     return this.operatorPosition;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setOperatorPosition(int operatorPosition)
/*  99:    */   {
/* 100:174 */     this.operatorPosition = operatorPosition;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean hasSideEffects()
/* 104:    */   {
/* 105:180 */     switch (getType())
/* 106:    */     {
/* 107:    */     case 89: 
/* 108:182 */       return (this.right != null) && (this.right.hasSideEffects());
/* 109:    */     case 104: 
/* 110:    */     case 105: 
/* 111:185 */       return ((this.left != null) && (this.left.hasSideEffects())) || ((this.right != null) && (this.right.hasSideEffects()));
/* 112:    */     }
/* 113:188 */     return super.hasSideEffects();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String toSource(int depth)
/* 117:    */   {
/* 118:194 */     StringBuilder sb = new StringBuilder();
/* 119:195 */     sb.append(makeIndent(depth));
/* 120:196 */     sb.append(this.left.toSource());
/* 121:197 */     sb.append(" ");
/* 122:198 */     sb.append(operatorToString(getType()));
/* 123:199 */     sb.append(" ");
/* 124:200 */     sb.append(this.right.toSource());
/* 125:201 */     return sb.toString();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void visit(NodeVisitor v)
/* 129:    */   {
/* 130:209 */     if (v.visit(this))
/* 131:    */     {
/* 132:210 */       this.left.visit(v);
/* 133:211 */       this.right.visit(v);
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.InfixExpression
 * JD-Core Version:    0.7.0.1
 */