/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class LetNode
/*   4:    */   extends Scope
/*   5:    */ {
/*   6:    */   private VariableDeclaration variables;
/*   7:    */   private AstNode body;
/*   8: 61 */   private int lp = -1;
/*   9: 62 */   private int rp = -1;
/*  10:    */   
/*  11:    */   public LetNode()
/*  12:    */   {
/*  13: 65 */     this.type = 158;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public LetNode(int pos)
/*  17:    */   {
/*  18: 72 */     super(pos);this.type = 158;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public LetNode(int pos, int len)
/*  22:    */   {
/*  23: 76 */     super(pos, len);this.type = 158;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public VariableDeclaration getVariables()
/*  27:    */   {
/*  28: 83 */     return this.variables;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setVariables(VariableDeclaration variables)
/*  32:    */   {
/*  33: 91 */     assertNotNull(variables);
/*  34: 92 */     this.variables = variables;
/*  35: 93 */     variables.setParent(this);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AstNode getBody()
/*  39:    */   {
/*  40:105 */     return this.body;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setBody(AstNode body)
/*  44:    */   {
/*  45:115 */     this.body = body;
/*  46:116 */     if (body != null) {
/*  47:117 */       body.setParent(this);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getLp()
/*  52:    */   {
/*  53:124 */     return this.lp;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setLp(int lp)
/*  57:    */   {
/*  58:131 */     this.lp = lp;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getRp()
/*  62:    */   {
/*  63:138 */     return this.rp;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setRp(int rp)
/*  67:    */   {
/*  68:145 */     this.rp = rp;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setParens(int lp, int rp)
/*  72:    */   {
/*  73:152 */     this.lp = lp;
/*  74:153 */     this.rp = rp;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toSource(int depth)
/*  78:    */   {
/*  79:158 */     String pad = makeIndent(depth);
/*  80:159 */     StringBuilder sb = new StringBuilder();
/*  81:160 */     sb.append(pad);
/*  82:161 */     sb.append("let (");
/*  83:162 */     printList(this.variables.getVariables(), sb);
/*  84:163 */     sb.append(") ");
/*  85:164 */     if (this.body != null) {
/*  86:165 */       sb.append(this.body.toSource(depth));
/*  87:    */     }
/*  88:167 */     return sb.toString();
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void visit(NodeVisitor v)
/*  92:    */   {
/*  93:176 */     if (v.visit(this))
/*  94:    */     {
/*  95:177 */       this.variables.visit(v);
/*  96:178 */       if (this.body != null) {
/*  97:179 */         this.body.visit(v);
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.LetNode
 * JD-Core Version:    0.7.0.1
 */