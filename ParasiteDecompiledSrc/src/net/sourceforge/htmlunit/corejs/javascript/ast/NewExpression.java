/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class NewExpression
/*   4:    */   extends FunctionCall
/*   5:    */ {
/*   6:    */   private ObjectLiteral initializer;
/*   7:    */   
/*   8:    */   public NewExpression()
/*   9:    */   {
/*  10: 59 */     this.type = 30;
/*  11:    */   }
/*  12:    */   
/*  13:    */   public NewExpression(int pos)
/*  14:    */   {
/*  15: 66 */     super(pos);this.type = 30;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public NewExpression(int pos, int len)
/*  19:    */   {
/*  20: 70 */     super(pos, len);this.type = 30;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public ObjectLiteral getInitializer()
/*  24:    */   {
/*  25: 79 */     return this.initializer;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setInitializer(ObjectLiteral initializer)
/*  29:    */   {
/*  30: 92 */     this.initializer = initializer;
/*  31: 93 */     if (initializer != null) {
/*  32: 94 */       initializer.setParent(this);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String toSource(int depth)
/*  37:    */   {
/*  38: 99 */     StringBuilder sb = new StringBuilder();
/*  39:100 */     sb.append(makeIndent(depth));
/*  40:101 */     sb.append("new ");
/*  41:102 */     sb.append(this.target.toSource(0));
/*  42:103 */     sb.append("(");
/*  43:104 */     if (this.arguments != null) {
/*  44:105 */       printList(this.arguments, sb);
/*  45:    */     }
/*  46:107 */     sb.append(")");
/*  47:108 */     if (this.initializer != null)
/*  48:    */     {
/*  49:109 */       sb.append(" ");
/*  50:110 */       sb.append(this.initializer.toSource(0));
/*  51:    */     }
/*  52:112 */     return sb.toString();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void visit(NodeVisitor v)
/*  56:    */   {
/*  57:121 */     if (v.visit(this))
/*  58:    */     {
/*  59:122 */       this.target.visit(v);
/*  60:123 */       for (AstNode arg : getArguments()) {
/*  61:124 */         arg.visit(v);
/*  62:    */       }
/*  63:126 */       if (this.initializer != null) {
/*  64:127 */         this.initializer.visit(v);
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.NewExpression
 * JD-Core Version:    0.7.0.1
 */