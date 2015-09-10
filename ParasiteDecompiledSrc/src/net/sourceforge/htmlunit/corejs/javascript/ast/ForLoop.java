/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ForLoop
/*   4:    */   extends Loop
/*   5:    */ {
/*   6:    */   private AstNode initializer;
/*   7:    */   private AstNode condition;
/*   8:    */   private AstNode increment;
/*   9:    */   
/*  10:    */   public ForLoop()
/*  11:    */   {
/*  12: 57 */     this.type = 119;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public ForLoop(int pos)
/*  16:    */   {
/*  17: 64 */     super(pos);this.type = 119;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public ForLoop(int pos, int len)
/*  21:    */   {
/*  22: 68 */     super(pos, len);this.type = 119;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public AstNode getInitializer()
/*  26:    */   {
/*  27: 78 */     return this.initializer;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setInitializer(AstNode initializer)
/*  31:    */   {
/*  32: 90 */     assertNotNull(initializer);
/*  33: 91 */     this.initializer = initializer;
/*  34: 92 */     initializer.setParent(this);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AstNode getCondition()
/*  38:    */   {
/*  39: 99 */     return this.condition;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setCondition(AstNode condition)
/*  43:    */   {
/*  44:109 */     assertNotNull(condition);
/*  45:110 */     this.condition = condition;
/*  46:111 */     condition.setParent(this);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public AstNode getIncrement()
/*  50:    */   {
/*  51:118 */     return this.increment;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setIncrement(AstNode increment)
/*  55:    */   {
/*  56:129 */     assertNotNull(increment);
/*  57:130 */     this.increment = increment;
/*  58:131 */     increment.setParent(this);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String toSource(int depth)
/*  62:    */   {
/*  63:136 */     StringBuilder sb = new StringBuilder();
/*  64:137 */     sb.append(makeIndent(depth));
/*  65:138 */     sb.append("for (");
/*  66:139 */     sb.append(this.initializer.toSource(0));
/*  67:140 */     sb.append("; ");
/*  68:141 */     sb.append(this.condition.toSource(0));
/*  69:142 */     sb.append("; ");
/*  70:143 */     sb.append(this.increment.toSource(0));
/*  71:144 */     sb.append(") ");
/*  72:145 */     if ((this.body instanceof Block)) {
/*  73:146 */       sb.append(this.body.toSource(depth).trim()).append("\n");
/*  74:    */     } else {
/*  75:148 */       sb.append("\n").append(this.body.toSource(depth + 1));
/*  76:    */     }
/*  77:150 */     return sb.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void visit(NodeVisitor v)
/*  81:    */   {
/*  82:159 */     if (v.visit(this))
/*  83:    */     {
/*  84:160 */       this.initializer.visit(v);
/*  85:161 */       this.condition.visit(v);
/*  86:162 */       this.increment.visit(v);
/*  87:163 */       this.body.visit(v);
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ForLoop
 * JD-Core Version:    0.7.0.1
 */