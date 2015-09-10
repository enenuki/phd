/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class WhileLoop
/*   4:    */   extends Loop
/*   5:    */ {
/*   6:    */   private AstNode condition;
/*   7:    */   
/*   8:    */   public WhileLoop()
/*   9:    */   {
/*  10: 54 */     this.type = 117;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public WhileLoop(int pos)
/*  14:    */   {
/*  15: 61 */     super(pos);this.type = 117;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public WhileLoop(int pos, int len)
/*  19:    */   {
/*  20: 65 */     super(pos, len);this.type = 117;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public AstNode getCondition()
/*  24:    */   {
/*  25: 72 */     return this.condition;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setCondition(AstNode condition)
/*  29:    */   {
/*  30: 80 */     assertNotNull(condition);
/*  31: 81 */     this.condition = condition;
/*  32: 82 */     condition.setParent(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String toSource(int depth)
/*  36:    */   {
/*  37: 87 */     StringBuilder sb = new StringBuilder();
/*  38: 88 */     sb.append(makeIndent(depth));
/*  39: 89 */     sb.append("while (");
/*  40: 90 */     sb.append(this.condition.toSource(0));
/*  41: 91 */     sb.append(") ");
/*  42: 92 */     if ((this.body instanceof Block))
/*  43:    */     {
/*  44: 93 */       sb.append(this.body.toSource(depth).trim());
/*  45: 94 */       sb.append("\n");
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49: 96 */       sb.append("\n").append(this.body.toSource(depth + 1));
/*  50:    */     }
/*  51: 98 */     return sb.toString();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void visit(NodeVisitor v)
/*  55:    */   {
/*  56:106 */     if (v.visit(this))
/*  57:    */     {
/*  58:107 */       this.condition.visit(v);
/*  59:108 */       this.body.visit(v);
/*  60:    */     }
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.WhileLoop
 * JD-Core Version:    0.7.0.1
 */