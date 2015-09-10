/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class SwitchCase
/*   7:    */   extends AstNode
/*   8:    */ {
/*   9:    */   private AstNode expression;
/*  10:    */   private List<AstNode> statements;
/*  11:    */   
/*  12:    */   public SwitchCase()
/*  13:    */   {
/*  14: 68 */     this.type = 115;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public SwitchCase(int pos)
/*  18:    */   {
/*  19: 75 */     super(pos);this.type = 115;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public SwitchCase(int pos, int len)
/*  23:    */   {
/*  24: 79 */     super(pos, len);this.type = 115;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public AstNode getExpression()
/*  28:    */   {
/*  29: 86 */     return this.expression;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setExpression(AstNode expression)
/*  33:    */   {
/*  34: 97 */     this.expression = expression;
/*  35: 98 */     if (expression != null) {
/*  36: 99 */       expression.setParent(this);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isDefault()
/*  41:    */   {
/*  42:107 */     return this.expression == null;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<AstNode> getStatements()
/*  46:    */   {
/*  47:114 */     return this.statements;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setStatements(List<AstNode> statements)
/*  51:    */   {
/*  52:122 */     if (this.statements != null) {
/*  53:123 */       this.statements.clear();
/*  54:    */     }
/*  55:125 */     for (AstNode s : statements) {
/*  56:126 */       addStatement(s);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addStatement(AstNode statement)
/*  61:    */   {
/*  62:140 */     assertNotNull(statement);
/*  63:141 */     if (this.statements == null) {
/*  64:142 */       this.statements = new ArrayList();
/*  65:    */     }
/*  66:144 */     int end = statement.getPosition() + statement.getLength();
/*  67:145 */     setLength(end - getPosition());
/*  68:146 */     this.statements.add(statement);
/*  69:147 */     statement.setParent(this);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String toSource(int depth)
/*  73:    */   {
/*  74:152 */     StringBuilder sb = new StringBuilder();
/*  75:153 */     sb.append(makeIndent(depth));
/*  76:154 */     if (this.expression == null)
/*  77:    */     {
/*  78:155 */       sb.append("default:\n");
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:157 */       sb.append("case ");
/*  83:158 */       sb.append(this.expression.toSource(0));
/*  84:159 */       sb.append(":\n");
/*  85:    */     }
/*  86:161 */     if (this.statements != null) {
/*  87:162 */       for (AstNode s : this.statements) {
/*  88:163 */         sb.append(s.toSource(depth + 1));
/*  89:    */       }
/*  90:    */     }
/*  91:166 */     return sb.toString();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void visit(NodeVisitor v)
/*  95:    */   {
/*  96:175 */     if (v.visit(this))
/*  97:    */     {
/*  98:176 */       if (this.expression != null) {
/*  99:177 */         this.expression.visit(v);
/* 100:    */       }
/* 101:179 */       if (this.statements != null) {
/* 102:180 */         for (AstNode s : this.statements) {
/* 103:181 */           s.visit(v);
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase
 * JD-Core Version:    0.7.0.1
 */