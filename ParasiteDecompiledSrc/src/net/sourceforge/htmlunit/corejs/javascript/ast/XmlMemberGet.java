/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.ast;
/*   2:    */ 
/*   3:    */ public class XmlMemberGet
/*   4:    */   extends InfixExpression
/*   5:    */ {
/*   6:    */   public XmlMemberGet()
/*   7:    */   {
/*   8: 54 */     this.type = 143;
/*   9:    */   }
/*  10:    */   
/*  11:    */   public XmlMemberGet(int pos)
/*  12:    */   {
/*  13: 61 */     super(pos);this.type = 143;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public XmlMemberGet(int pos, int len)
/*  17:    */   {
/*  18: 65 */     super(pos, len);this.type = 143;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public XmlMemberGet(int pos, int len, AstNode target, XmlRef ref)
/*  22:    */   {
/*  23: 69 */     super(pos, len, target, ref);this.type = 143;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public XmlMemberGet(AstNode target, XmlRef ref)
/*  27:    */   {
/*  28: 77 */     super(target, ref);this.type = 143;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public XmlMemberGet(AstNode target, XmlRef ref, int opPos)
/*  32:    */   {
/*  33: 81 */     super(143, target, ref, opPos);this.type = 143;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AstNode getTarget()
/*  37:    */   {
/*  38: 89 */     return getLeft();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setTarget(AstNode target)
/*  42:    */   {
/*  43: 97 */     setLeft(target);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public XmlRef getMemberRef()
/*  47:    */   {
/*  48:105 */     return (XmlRef)getRight();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setProperty(XmlRef ref)
/*  52:    */   {
/*  53:114 */     setRight(ref);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toSource(int depth)
/*  57:    */   {
/*  58:119 */     StringBuilder sb = new StringBuilder();
/*  59:120 */     sb.append(makeIndent(depth));
/*  60:121 */     sb.append(getLeft().toSource(0));
/*  61:122 */     sb.append(operatorToString(getType()));
/*  62:123 */     sb.append(getRight().toSource(0));
/*  63:124 */     return sb.toString();
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.ast.XmlMemberGet
 * JD-Core Version:    0.7.0.1
 */