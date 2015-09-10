/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class IfStatement
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode condition;
/*   7:    */   private AstNode thenPart;
/*   8: 54 */   private int elsePosition = -1;
/*   9:    */   private AstNode elsePart;
/*  10: 56 */   private int lp = -1;
/*  11: 57 */   private int rp = -1;
/*  12:    */   
/*  13:    */   public IfStatement()
/*  14:    */   {
/*  15: 60 */     this.type = 112;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public IfStatement(int pos)
/*  19:    */   {
/*  20: 67 */     super(pos);this.type = 112;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public IfStatement(int pos, int len)
/*  24:    */   {
/*  25: 71 */     super(pos, len);this.type = 112;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public AstNode getCondition()
/*  29:    */   {
/*  30: 78 */     return this.condition;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setCondition(AstNode condition)
/*  34:    */   {
/*  35: 86 */     assertNotNull(condition);
/*  36: 87 */     this.condition = condition;
/*  37: 88 */     condition.setParent(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public AstNode getThenPart()
/*  41:    */   {
/*  42: 95 */     return this.thenPart;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setThenPart(AstNode thenPart)
/*  46:    */   {
/*  47:103 */     assertNotNull(thenPart);
/*  48:104 */     this.thenPart = thenPart;
/*  49:105 */     thenPart.setParent(this);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public AstNode getElsePart()
/*  53:    */   {
/*  54:112 */     return this.elsePart;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setElsePart(AstNode elsePart)
/*  58:    */   {
/*  59:121 */     this.elsePart = elsePart;
/*  60:122 */     if (elsePart != null) {
/*  61:123 */       elsePart.setParent(this);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getElsePosition()
/*  66:    */   {
/*  67:130 */     return this.elsePosition;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setElsePosition(int elsePosition)
/*  71:    */   {
/*  72:137 */     this.elsePosition = elsePosition;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getLp()
/*  76:    */   {
/*  77:144 */     return this.lp;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setLp(int lp)
/*  81:    */   {
/*  82:151 */     this.lp = lp;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getRp()
/*  86:    */   {
/*  87:158 */     return this.rp;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setRp(int rp)
/*  91:    */   {
/*  92:165 */     this.rp = rp;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setParens(int lp, int rp)
/*  96:    */   {
/*  97:172 */     this.lp = lp;
/*  98:173 */     this.rp = rp;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toSource(int depth)
/* 102:    */   {
/* 103:178 */     String pad = makeIndent(depth);
/* 104:179 */     StringBuilder sb = new StringBuilder(32);
/* 105:180 */     sb.append(pad);
/* 106:181 */     sb.append("if (");
/* 107:182 */     sb.append(this.condition.toSource(0));
/* 108:183 */     sb.append(") ");
/* 109:184 */     if (!(this.thenPart instanceof Block)) {
/* 110:185 */       sb.append("\n").append(makeIndent(depth));
/* 111:    */     }
/* 112:187 */     sb.append(this.thenPart.toSource(depth).trim());
/* 113:188 */     if ((this.elsePart instanceof IfStatement))
/* 114:    */     {
/* 115:189 */       sb.append(" else ");
/* 116:190 */       sb.append(this.elsePart.toSource(depth).trim());
/* 117:    */     }
/* 118:191 */     else if (this.elsePart != null)
/* 119:    */     {
/* 120:192 */       sb.append(" else ");
/* 121:193 */       sb.append(this.elsePart.toSource(depth).trim());
/* 122:    */     }
/* 123:195 */     sb.append("\n");
/* 124:196 */     return sb.toString();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void visit(NodeVisitor v)
/* 128:    */   {
/* 129:205 */     if (v.visit(this))
/* 130:    */     {
/* 131:206 */       this.condition.visit(v);
/* 132:207 */       this.thenPart.visit(v);
/* 133:208 */       if (this.elsePart != null) {
/* 134:209 */         this.elsePart.visit(v);
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.IfStatement
 * JD-Core Version:    0.7.0.1
 */