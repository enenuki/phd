/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class CatchClause
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private Name varName;
/*   7:    */   private AstNode catchCondition;
/*   8:    */   private Block body;
/*   9: 55 */   private int ifPosition = -1;
/*  10: 56 */   private int lp = -1;
/*  11: 57 */   private int rp = -1;
/*  12:    */   
/*  13:    */   public CatchClause()
/*  14:    */   {
/*  15: 60 */     this.type = 124;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public CatchClause(int pos)
/*  19:    */   {
/*  20: 67 */     super(pos);this.type = 124;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public CatchClause(int pos, int len)
/*  24:    */   {
/*  25: 71 */     super(pos, len);this.type = 124;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Name getVarName()
/*  29:    */   {
/*  30: 79 */     return this.varName;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setVarName(Name varName)
/*  34:    */   {
/*  35: 88 */     assertNotNull(varName);
/*  36: 89 */     this.varName = varName;
/*  37: 90 */     varName.setParent(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public AstNode getCatchCondition()
/*  41:    */   {
/*  42: 98 */     return this.catchCondition;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setCatchCondition(AstNode catchCondition)
/*  46:    */   {
/*  47:106 */     this.catchCondition = catchCondition;
/*  48:107 */     if (catchCondition != null) {
/*  49:108 */       catchCondition.setParent(this);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Block getBody()
/*  54:    */   {
/*  55:115 */     return this.body;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setBody(Block body)
/*  59:    */   {
/*  60:123 */     assertNotNull(body);
/*  61:124 */     this.body = body;
/*  62:125 */     body.setParent(this);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getLp()
/*  66:    */   {
/*  67:132 */     return this.lp;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setLp(int lp)
/*  71:    */   {
/*  72:139 */     this.lp = lp;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getRp()
/*  76:    */   {
/*  77:146 */     return this.rp;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setRp(int rp)
/*  81:    */   {
/*  82:153 */     this.rp = rp;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setParens(int lp, int rp)
/*  86:    */   {
/*  87:160 */     this.lp = lp;
/*  88:161 */     this.rp = rp;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getIfPosition()
/*  92:    */   {
/*  93:169 */     return this.ifPosition;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setIfPosition(int ifPosition)
/*  97:    */   {
/*  98:177 */     this.ifPosition = ifPosition;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toSource(int depth)
/* 102:    */   {
/* 103:182 */     StringBuilder sb = new StringBuilder();
/* 104:183 */     sb.append(makeIndent(depth));
/* 105:184 */     sb.append("catch (");
/* 106:185 */     sb.append(this.varName.toSource(0));
/* 107:186 */     if (this.catchCondition != null)
/* 108:    */     {
/* 109:187 */       sb.append(" if ");
/* 110:188 */       sb.append(this.catchCondition.toSource(0));
/* 111:    */     }
/* 112:190 */     sb.append(") ");
/* 113:191 */     sb.append(this.body.toSource(0));
/* 114:192 */     return sb.toString();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void visit(NodeVisitor v)
/* 118:    */   {
/* 119:201 */     if (v.visit(this))
/* 120:    */     {
/* 121:202 */       this.varName.visit(v);
/* 122:203 */       if (this.catchCondition != null) {
/* 123:204 */         this.catchCondition.visit(v);
/* 124:    */       }
/* 125:206 */       this.body.visit(v);
/* 126:    */     }
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.CatchClause
 * JD-Core Version:    0.7.0.1
 */