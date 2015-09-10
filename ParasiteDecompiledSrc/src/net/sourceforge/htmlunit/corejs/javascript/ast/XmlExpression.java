/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class XmlExpression
/*   4:    */   extends XmlFragment
/*   5:    */ {
/*   6:    */   private AstNode expression;
/*   7:    */   private boolean isXmlAttribute;
/*   8:    */   
/*   9:    */   public XmlExpression() {}
/*  10:    */   
/*  11:    */   public XmlExpression(int pos)
/*  12:    */   {
/*  13: 57 */     super(pos);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public XmlExpression(int pos, int len)
/*  17:    */   {
/*  18: 61 */     super(pos, len);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public XmlExpression(int pos, AstNode expr)
/*  22:    */   {
/*  23: 65 */     super(pos);
/*  24: 66 */     setExpression(expr);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public AstNode getExpression()
/*  28:    */   {
/*  29: 73 */     return this.expression;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setExpression(AstNode expression)
/*  33:    */   {
/*  34: 81 */     assertNotNull(expression);
/*  35: 82 */     this.expression = expression;
/*  36: 83 */     expression.setParent(this);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isXmlAttribute()
/*  40:    */   {
/*  41: 90 */     return this.isXmlAttribute;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setIsXmlAttribute(boolean isXmlAttribute)
/*  45:    */   {
/*  46: 97 */     this.isXmlAttribute = isXmlAttribute;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toSource(int depth)
/*  50:    */   {
/*  51:102 */     return makeIndent(depth) + "{" + this.expression.toSource(depth) + "}";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void visit(NodeVisitor v)
/*  55:    */   {
/*  56:110 */     if (v.visit(this)) {
/*  57:111 */       this.expression.visit(v);
/*  58:    */     }
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlExpression
 * JD-Core Version:    0.7.0.1
 */