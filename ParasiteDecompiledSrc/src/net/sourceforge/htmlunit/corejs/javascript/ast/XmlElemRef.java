/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class XmlElemRef
/*   4:    */   extends XmlRef
/*   5:    */ {
/*   6:    */   private AstNode indexExpr;
/*   7: 69 */   private int lb = -1;
/*   8: 70 */   private int rb = -1;
/*   9:    */   
/*  10:    */   public XmlElemRef()
/*  11:    */   {
/*  12: 73 */     this.type = 77;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public XmlElemRef(int pos)
/*  16:    */   {
/*  17: 80 */     super(pos);this.type = 77;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public XmlElemRef(int pos, int len)
/*  21:    */   {
/*  22: 84 */     super(pos, len);this.type = 77;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public AstNode getExpression()
/*  26:    */   {
/*  27: 92 */     return this.indexExpr;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setExpression(AstNode expr)
/*  31:    */   {
/*  32:100 */     assertNotNull(expr);
/*  33:101 */     this.indexExpr = expr;
/*  34:102 */     expr.setParent(this);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getLb()
/*  38:    */   {
/*  39:109 */     return this.lb;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setLb(int lb)
/*  43:    */   {
/*  44:116 */     this.lb = lb;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int getRb()
/*  48:    */   {
/*  49:123 */     return this.rb;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setRb(int rb)
/*  53:    */   {
/*  54:130 */     this.rb = rb;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setBrackets(int lb, int rb)
/*  58:    */   {
/*  59:137 */     this.lb = lb;
/*  60:138 */     this.rb = rb;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toSource(int depth)
/*  64:    */   {
/*  65:143 */     StringBuilder sb = new StringBuilder();
/*  66:144 */     sb.append(makeIndent(depth));
/*  67:145 */     if (isAttributeAccess()) {
/*  68:146 */       sb.append("@");
/*  69:    */     }
/*  70:148 */     if (this.namespace != null)
/*  71:    */     {
/*  72:149 */       sb.append(this.namespace.toSource(0));
/*  73:150 */       sb.append("::");
/*  74:    */     }
/*  75:152 */     sb.append("[");
/*  76:153 */     sb.append(this.indexExpr.toSource(0));
/*  77:154 */     sb.append("]");
/*  78:155 */     return sb.toString();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void visit(NodeVisitor v)
/*  82:    */   {
/*  83:164 */     if (v.visit(this))
/*  84:    */     {
/*  85:165 */       if (this.namespace != null) {
/*  86:166 */         this.namespace.visit(v);
/*  87:    */       }
/*  88:168 */       this.indexExpr.visit(v);
/*  89:    */     }
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlElemRef
 * JD-Core Version:    0.7.0.1
 */