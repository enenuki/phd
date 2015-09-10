/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class SwitchStatement
/*   8:    */   extends Jump
/*   9:    */ {
/*  10: 66 */   private static final List<SwitchCase> NO_CASES = Collections.unmodifiableList(new ArrayList());
/*  11:    */   private AstNode expression;
/*  12:    */   private List<SwitchCase> cases;
/*  13: 71 */   private int lp = -1;
/*  14: 72 */   private int rp = -1;
/*  15:    */   
/*  16:    */   public SwitchStatement()
/*  17:    */   {
/*  18: 75 */     this.type = 114;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public SwitchStatement(int pos)
/*  22:    */   {
/*  23: 75 */     this.type = 114;
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31: 83 */     this.position = pos;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public SwitchStatement(int pos, int len)
/*  35:    */   {
/*  36: 75 */     this.type = 114;
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48: 87 */     this.position = pos;
/*  49: 88 */     this.length = len;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public AstNode getExpression()
/*  53:    */   {
/*  54: 95 */     return this.expression;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setExpression(AstNode expression)
/*  58:    */   {
/*  59:104 */     assertNotNull(expression);
/*  60:105 */     this.expression = expression;
/*  61:106 */     expression.setParent(this);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<SwitchCase> getCases()
/*  65:    */   {
/*  66:114 */     return this.cases != null ? this.cases : NO_CASES;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setCases(List<SwitchCase> cases)
/*  70:    */   {
/*  71:123 */     if (cases == null)
/*  72:    */     {
/*  73:124 */       this.cases = null;
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:126 */       if (this.cases != null) {
/*  78:127 */         this.cases.clear();
/*  79:    */       }
/*  80:128 */       for (SwitchCase sc : cases) {
/*  81:129 */         addCase(sc);
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void addCase(SwitchCase switchCase)
/*  87:    */   {
/*  88:138 */     assertNotNull(switchCase);
/*  89:139 */     if (this.cases == null) {
/*  90:140 */       this.cases = new ArrayList();
/*  91:    */     }
/*  92:142 */     this.cases.add(switchCase);
/*  93:143 */     switchCase.setParent(this);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getLp()
/*  97:    */   {
/*  98:150 */     return this.lp;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setLp(int lp)
/* 102:    */   {
/* 103:157 */     this.lp = lp;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getRp()
/* 107:    */   {
/* 108:164 */     return this.rp;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setRp(int rp)
/* 112:    */   {
/* 113:171 */     this.rp = rp;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setParens(int lp, int rp)
/* 117:    */   {
/* 118:178 */     this.lp = lp;
/* 119:179 */     this.rp = rp;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String toSource(int depth)
/* 123:    */   {
/* 124:184 */     String pad = makeIndent(depth);
/* 125:185 */     StringBuilder sb = new StringBuilder();
/* 126:186 */     sb.append(pad);
/* 127:187 */     sb.append("switch (");
/* 128:188 */     sb.append(this.expression.toSource(0));
/* 129:189 */     sb.append(") {\n");
/* 130:190 */     for (SwitchCase sc : this.cases) {
/* 131:191 */       sb.append(sc.toSource(depth + 1));
/* 132:    */     }
/* 133:193 */     sb.append(pad);
/* 134:194 */     sb.append("}\n");
/* 135:195 */     return sb.toString();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void visit(NodeVisitor v)
/* 139:    */   {
/* 140:204 */     if (v.visit(this))
/* 141:    */     {
/* 142:205 */       this.expression.visit(v);
/* 143:206 */       for (SwitchCase sc : getCases()) {
/* 144:207 */         sc.visit(v);
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.SwitchStatement
 * JD-Core Version:    0.7.0.1
 */