/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class BreakStatement
/*   4:    */   extends Jump
/*   5:    */ {
/*   6:    */   private Name breakLabel;
/*   7:    */   private AstNode target;
/*   8:    */   
/*   9:    */   public BreakStatement()
/*  10:    */   {
/*  11: 55 */     this.type = 120;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public BreakStatement(int pos)
/*  15:    */   {
/*  16: 55 */     this.type = 120;
/*  17:    */     
/*  18:    */ 
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24: 63 */     this.position = pos;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public BreakStatement(int pos, int len)
/*  28:    */   {
/*  29: 55 */     this.type = 120;
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41: 67 */     this.position = pos;
/*  42: 68 */     this.length = len;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Name getBreakLabel()
/*  46:    */   {
/*  47: 77 */     return this.breakLabel;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setBreakLabel(Name label)
/*  51:    */   {
/*  52: 87 */     this.breakLabel = label;
/*  53: 88 */     if (label != null) {
/*  54: 89 */       label.setParent(this);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public AstNode getBreakTarget()
/*  59:    */   {
/*  60: 98 */     return this.target;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setBreakTarget(Jump target)
/*  64:    */   {
/*  65:107 */     assertNotNull(target);
/*  66:108 */     this.target = target;
/*  67:109 */     setJumpStatement(target);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String toSource(int depth)
/*  71:    */   {
/*  72:114 */     StringBuilder sb = new StringBuilder();
/*  73:115 */     sb.append(makeIndent(depth));
/*  74:116 */     sb.append("break");
/*  75:117 */     if (this.breakLabel != null)
/*  76:    */     {
/*  77:118 */       sb.append(" ");
/*  78:119 */       sb.append(this.breakLabel.toSource(0));
/*  79:    */     }
/*  80:121 */     sb.append(";\n");
/*  81:122 */     return sb.toString();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void visit(NodeVisitor v)
/*  85:    */   {
/*  86:130 */     if ((v.visit(this)) && (this.breakLabel != null)) {
/*  87:131 */       this.breakLabel.visit(v);
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.BreakStatement
 * JD-Core Version:    0.7.0.1
 */