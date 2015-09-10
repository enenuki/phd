/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class VariableInitializer
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode target;
/*   7:    */   private AstNode initializer;
/*   8:    */   
/*   9:    */   public VariableInitializer()
/*  10:    */   {
/*  11: 57 */     this.type = 122;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public void setNodeType(int nodeType)
/*  15:    */   {
/*  16: 66 */     if ((nodeType != 122) && (nodeType != 154) && (nodeType != 153)) {
/*  17: 69 */       throw new IllegalArgumentException("invalid node type");
/*  18:    */     }
/*  19: 70 */     setType(nodeType);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public VariableInitializer(int pos)
/*  23:    */   {
/*  24: 77 */     super(pos);this.type = 122;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public VariableInitializer(int pos, int len)
/*  28:    */   {
/*  29: 81 */     super(pos, len);this.type = 122;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean isDestructuring()
/*  33:    */   {
/*  34: 92 */     return !(this.target instanceof Name);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AstNode getTarget()
/*  38:    */   {
/*  39: 99 */     return this.target;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setTarget(AstNode target)
/*  43:    */   {
/*  44:110 */     if (target == null) {
/*  45:111 */       throw new IllegalArgumentException("invalid target arg");
/*  46:    */     }
/*  47:112 */     this.target = target;
/*  48:113 */     target.setParent(this);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public AstNode getInitializer()
/*  52:    */   {
/*  53:120 */     return this.initializer;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setInitializer(AstNode initializer)
/*  57:    */   {
/*  58:128 */     this.initializer = initializer;
/*  59:129 */     if (initializer != null) {
/*  60:130 */       initializer.setParent(this);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String toSource(int depth)
/*  65:    */   {
/*  66:135 */     StringBuilder sb = new StringBuilder();
/*  67:136 */     sb.append(makeIndent(depth));
/*  68:137 */     sb.append(this.target.toSource(0));
/*  69:138 */     if (this.initializer != null)
/*  70:    */     {
/*  71:139 */       sb.append(" = ");
/*  72:140 */       sb.append(this.initializer.toSource(0));
/*  73:    */     }
/*  74:142 */     return sb.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void visit(NodeVisitor v)
/*  78:    */   {
/*  79:151 */     if (v.visit(this))
/*  80:    */     {
/*  81:152 */       this.target.visit(v);
/*  82:153 */       if (this.initializer != null) {
/*  83:154 */         this.initializer.visit(v);
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.VariableInitializer
 * JD-Core Version:    0.7.0.1
 */