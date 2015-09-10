/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class PropertyGet
/*   4:    */   extends InfixExpression
/*   5:    */ {
/*   6:    */   public PropertyGet()
/*   7:    */   {
/*   8: 49 */     this.type = 33;
/*   9:    */   }
/*  10:    */   
/*  11:    */   public PropertyGet(int pos)
/*  12:    */   {
/*  13: 56 */     super(pos);this.type = 33;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public PropertyGet(int pos, int len)
/*  17:    */   {
/*  18: 60 */     super(pos, len);this.type = 33;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public PropertyGet(int pos, int len, AstNode target, Name property)
/*  22:    */   {
/*  23: 64 */     super(pos, len, target, property);this.type = 33;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public PropertyGet(AstNode target, Name property)
/*  27:    */   {
/*  28: 72 */     super(target, property);this.type = 33;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PropertyGet(AstNode target, Name property, int dotPosition)
/*  32:    */   {
/*  33: 76 */     super(33, target, property, dotPosition);this.type = 33;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AstNode getTarget()
/*  37:    */   {
/*  38: 84 */     return getLeft();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setTarget(AstNode target)
/*  42:    */   {
/*  43: 94 */     setLeft(target);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Name getProperty()
/*  47:    */   {
/*  48:101 */     return (Name)getRight();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setProperty(Name property)
/*  52:    */   {
/*  53:109 */     setRight(property);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toSource(int depth)
/*  57:    */   {
/*  58:114 */     StringBuilder sb = new StringBuilder();
/*  59:115 */     sb.append(makeIndent(depth));
/*  60:116 */     sb.append(getLeft().toSource(0));
/*  61:117 */     sb.append(".");
/*  62:118 */     sb.append(getRight().toSource(0));
/*  63:119 */     return sb.toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void visit(NodeVisitor v)
/*  67:    */   {
/*  68:127 */     if (v.visit(this))
/*  69:    */     {
/*  70:128 */       getTarget().visit(v);
/*  71:129 */       getProperty().visit(v);
/*  72:    */     }
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.PropertyGet
 * JD-Core Version:    0.7.0.1
 */