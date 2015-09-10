/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ConditionalExpression
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode testExpression;
/*   7:    */   private AstNode trueExpression;
/*   8:    */   private AstNode falseExpression;
/*   9: 62 */   private int questionMarkPosition = -1;
/*  10: 63 */   private int colonPosition = -1;
/*  11:    */   
/*  12:    */   public ConditionalExpression()
/*  13:    */   {
/*  14: 66 */     this.type = 102;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ConditionalExpression(int pos)
/*  18:    */   {
/*  19: 73 */     super(pos);this.type = 102;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ConditionalExpression(int pos, int len)
/*  23:    */   {
/*  24: 77 */     super(pos, len);this.type = 102;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public AstNode getTestExpression()
/*  28:    */   {
/*  29: 84 */     return this.testExpression;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setTestExpression(AstNode testExpression)
/*  33:    */   {
/*  34: 93 */     assertNotNull(testExpression);
/*  35: 94 */     this.testExpression = testExpression;
/*  36: 95 */     testExpression.setParent(this);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public AstNode getTrueExpression()
/*  40:    */   {
/*  41:102 */     return this.trueExpression;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setTrueExpression(AstNode trueExpression)
/*  45:    */   {
/*  46:112 */     assertNotNull(trueExpression);
/*  47:113 */     this.trueExpression = trueExpression;
/*  48:114 */     trueExpression.setParent(this);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public AstNode getFalseExpression()
/*  52:    */   {
/*  53:121 */     return this.falseExpression;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setFalseExpression(AstNode falseExpression)
/*  57:    */   {
/*  58:132 */     assertNotNull(falseExpression);
/*  59:133 */     this.falseExpression = falseExpression;
/*  60:134 */     falseExpression.setParent(this);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getQuestionMarkPosition()
/*  64:    */   {
/*  65:141 */     return this.questionMarkPosition;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setQuestionMarkPosition(int questionMarkPosition)
/*  69:    */   {
/*  70:149 */     this.questionMarkPosition = questionMarkPosition;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getColonPosition()
/*  74:    */   {
/*  75:156 */     return this.colonPosition;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setColonPosition(int colonPosition)
/*  79:    */   {
/*  80:164 */     this.colonPosition = colonPosition;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean hasSideEffects()
/*  84:    */   {
/*  85:169 */     if ((this.testExpression == null) || (this.trueExpression == null) || (this.falseExpression == null)) {
/*  86:171 */       codeBug();
/*  87:    */     }
/*  88:172 */     return (this.trueExpression.hasSideEffects()) && (this.falseExpression.hasSideEffects());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String toSource(int depth)
/*  92:    */   {
/*  93:178 */     StringBuilder sb = new StringBuilder();
/*  94:179 */     sb.append(makeIndent(depth));
/*  95:180 */     sb.append(this.testExpression.toSource(depth));
/*  96:181 */     sb.append(" ? ");
/*  97:182 */     sb.append(this.trueExpression.toSource(0));
/*  98:183 */     sb.append(" : ");
/*  99:184 */     sb.append(this.falseExpression.toSource(0));
/* 100:185 */     return sb.toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void visit(NodeVisitor v)
/* 104:    */   {
/* 105:194 */     if (v.visit(this))
/* 106:    */     {
/* 107:195 */       this.testExpression.visit(v);
/* 108:196 */       this.trueExpression.visit(v);
/* 109:197 */       this.falseExpression.visit(v);
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ConditionalExpression
 * JD-Core Version:    0.7.0.1
 */