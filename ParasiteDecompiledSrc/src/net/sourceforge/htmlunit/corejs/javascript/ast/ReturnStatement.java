/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class ReturnStatement
/*   4:    */   extends AstNode
/*   5:    */ {
/*   6:    */   private AstNode returnValue;
/*   7:    */   
/*   8:    */   public ReturnStatement()
/*   9:    */   {
/*  10: 54 */     this.type = 4;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public ReturnStatement(int pos)
/*  14:    */   {
/*  15: 61 */     super(pos);this.type = 4;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public ReturnStatement(int pos, int len)
/*  19:    */   {
/*  20: 65 */     super(pos, len);this.type = 4;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ReturnStatement(int pos, int len, AstNode returnValue)
/*  24:    */   {
/*  25: 69 */     super(pos, len);this.type = 4;
/*  26: 70 */     setReturnValue(returnValue);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public AstNode getReturnValue()
/*  30:    */   {
/*  31: 77 */     return this.returnValue;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setReturnValue(AstNode returnValue)
/*  35:    */   {
/*  36: 85 */     this.returnValue = returnValue;
/*  37: 86 */     if (returnValue != null) {
/*  38: 87 */       returnValue.setParent(this);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String toSource(int depth)
/*  43:    */   {
/*  44: 92 */     StringBuilder sb = new StringBuilder();
/*  45: 93 */     sb.append(makeIndent(depth));
/*  46: 94 */     sb.append("return");
/*  47: 95 */     if (this.returnValue != null)
/*  48:    */     {
/*  49: 96 */       sb.append(" ");
/*  50: 97 */       sb.append(this.returnValue.toSource(0));
/*  51:    */     }
/*  52: 99 */     sb.append(";\n");
/*  53:100 */     return sb.toString();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void visit(NodeVisitor v)
/*  57:    */   {
/*  58:108 */     if ((v.visit(this)) && (this.returnValue != null)) {
/*  59:109 */       this.returnValue.visit(v);
/*  60:    */     }
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.ReturnStatement
 * JD-Core Version:    0.7.0.1
 */