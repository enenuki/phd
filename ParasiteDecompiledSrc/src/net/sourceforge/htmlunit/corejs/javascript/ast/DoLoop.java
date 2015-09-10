/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class DoLoop
/*   4:    */   extends Loop
/*   5:    */ {
/*   6:    */   private AstNode condition;
/*   7: 52 */   private int whilePosition = -1;
/*   8:    */   
/*   9:    */   public DoLoop()
/*  10:    */   {
/*  11: 55 */     this.type = 118;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public DoLoop(int pos)
/*  15:    */   {
/*  16: 62 */     super(pos);this.type = 118;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public DoLoop(int pos, int len)
/*  20:    */   {
/*  21: 66 */     super(pos, len);this.type = 118;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public AstNode getCondition()
/*  25:    */   {
/*  26: 73 */     return this.condition;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setCondition(AstNode condition)
/*  30:    */   {
/*  31: 81 */     assertNotNull(condition);
/*  32: 82 */     this.condition = condition;
/*  33: 83 */     condition.setParent(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getWhilePosition()
/*  37:    */   {
/*  38: 90 */     return this.whilePosition;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setWhilePosition(int whilePosition)
/*  42:    */   {
/*  43: 97 */     this.whilePosition = whilePosition;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String toSource(int depth)
/*  47:    */   {
/*  48:102 */     StringBuilder sb = new StringBuilder();
/*  49:103 */     sb.append("do ");
/*  50:104 */     sb.append(this.body.toSource(depth).trim());
/*  51:105 */     sb.append(" while (");
/*  52:106 */     sb.append(this.condition.toSource(0));
/*  53:107 */     sb.append(");\n");
/*  54:108 */     return sb.toString();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void visit(NodeVisitor v)
/*  58:    */   {
/*  59:116 */     if (v.visit(this))
/*  60:    */     {
/*  61:117 */       this.body.visit(v);
/*  62:118 */       this.condition.visit(v);
/*  63:    */     }
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.DoLoop
 * JD-Core Version:    0.7.0.1
 */