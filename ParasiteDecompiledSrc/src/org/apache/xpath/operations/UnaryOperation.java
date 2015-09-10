/*   1:    */ package org.apache.xpath.operations;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.ExpressionOwner;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.XPathVisitor;
/*   9:    */ import org.apache.xpath.objects.XObject;
/*  10:    */ 
/*  11:    */ public abstract class UnaryOperation
/*  12:    */   extends Expression
/*  13:    */   implements ExpressionOwner
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 6536083808424286166L;
/*  16:    */   protected Expression m_right;
/*  17:    */   
/*  18:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  19:    */   {
/*  20: 52 */     this.m_right.fixupVariables(vars, globalsSize);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean canTraverseOutsideSubtree()
/*  24:    */   {
/*  25: 64 */     if ((null != this.m_right) && (this.m_right.canTraverseOutsideSubtree())) {
/*  26: 65 */       return true;
/*  27:    */     }
/*  28: 67 */     return false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setRight(Expression r)
/*  32:    */   {
/*  33: 79 */     this.m_right = r;
/*  34: 80 */     r.exprSetParent(this);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public XObject execute(XPathContext xctxt)
/*  38:    */     throws TransformerException
/*  39:    */   {
/*  40: 97 */     return operate(this.m_right.execute(xctxt));
/*  41:    */   }
/*  42:    */   
/*  43:    */   public abstract XObject operate(XObject paramXObject)
/*  44:    */     throws TransformerException;
/*  45:    */   
/*  46:    */   public Expression getOperand()
/*  47:    */   {
/*  48:116 */     return this.m_right;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  52:    */   {
/*  53:124 */     if (visitor.visitUnaryOperation(owner, this)) {
/*  54:126 */       this.m_right.callVisitors(this, visitor);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Expression getExpression()
/*  59:    */   {
/*  60:136 */     return this.m_right;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setExpression(Expression exp)
/*  64:    */   {
/*  65:144 */     exp.exprSetParent(this);
/*  66:145 */     this.m_right = exp;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean deepEquals(Expression expr)
/*  70:    */   {
/*  71:153 */     if (!isSameClass(expr)) {
/*  72:154 */       return false;
/*  73:    */     }
/*  74:156 */     if (!this.m_right.deepEquals(((UnaryOperation)expr).m_right)) {
/*  75:157 */       return false;
/*  76:    */     }
/*  77:159 */     return true;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.UnaryOperation
 * JD-Core Version:    0.7.0.1
 */