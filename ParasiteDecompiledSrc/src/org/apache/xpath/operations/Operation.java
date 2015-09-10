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
/*  11:    */ public class Operation
/*  12:    */   extends Expression
/*  13:    */   implements ExpressionOwner
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -3037139537171050430L;
/*  16:    */   protected Expression m_left;
/*  17:    */   protected Expression m_right;
/*  18:    */   
/*  19:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  20:    */   {
/*  21: 56 */     this.m_left.fixupVariables(vars, globalsSize);
/*  22: 57 */     this.m_right.fixupVariables(vars, globalsSize);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean canTraverseOutsideSubtree()
/*  26:    */   {
/*  27: 70 */     if ((null != this.m_left) && (this.m_left.canTraverseOutsideSubtree())) {
/*  28: 71 */       return true;
/*  29:    */     }
/*  30: 73 */     if ((null != this.m_right) && (this.m_right.canTraverseOutsideSubtree())) {
/*  31: 74 */       return true;
/*  32:    */     }
/*  33: 76 */     return false;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setLeftRight(Expression l, Expression r)
/*  37:    */   {
/*  38: 88 */     this.m_left = l;
/*  39: 89 */     this.m_right = r;
/*  40: 90 */     l.exprSetParent(this);
/*  41: 91 */     r.exprSetParent(this);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public XObject execute(XPathContext xctxt)
/*  45:    */     throws TransformerException
/*  46:    */   {
/*  47:109 */     XObject left = this.m_left.execute(xctxt, true);
/*  48:110 */     XObject right = this.m_right.execute(xctxt, true);
/*  49:    */     
/*  50:112 */     XObject result = operate(left, right);
/*  51:113 */     left.detach();
/*  52:114 */     right.detach();
/*  53:115 */     return result;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public XObject operate(XObject left, XObject right)
/*  57:    */     throws TransformerException
/*  58:    */   {
/*  59:132 */     return null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Expression getLeftOperand()
/*  63:    */   {
/*  64:138 */     return this.m_left;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Expression getRightOperand()
/*  68:    */   {
/*  69:144 */     return this.m_right;
/*  70:    */   }
/*  71:    */   
/*  72:    */   class LeftExprOwner
/*  73:    */     implements ExpressionOwner
/*  74:    */   {
/*  75:    */     LeftExprOwner() {}
/*  76:    */     
/*  77:    */     public Expression getExpression()
/*  78:    */     {
/*  79:154 */       return Operation.this.m_left;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public void setExpression(Expression exp)
/*  83:    */     {
/*  84:162 */       exp.exprSetParent(Operation.this);
/*  85:163 */       Operation.this.m_left = exp;
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  90:    */   {
/*  91:172 */     if (visitor.visitBinaryOperation(owner, this))
/*  92:    */     {
/*  93:174 */       this.m_left.callVisitors(new LeftExprOwner(), visitor);
/*  94:175 */       this.m_right.callVisitors(this, visitor);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Expression getExpression()
/*  99:    */   {
/* 100:184 */     return this.m_right;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setExpression(Expression exp)
/* 104:    */   {
/* 105:192 */     exp.exprSetParent(this);
/* 106:193 */     this.m_right = exp;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean deepEquals(Expression expr)
/* 110:    */   {
/* 111:201 */     if (!isSameClass(expr)) {
/* 112:202 */       return false;
/* 113:    */     }
/* 114:204 */     if (!this.m_left.deepEquals(((Operation)expr).m_left)) {
/* 115:205 */       return false;
/* 116:    */     }
/* 117:207 */     if (!this.m_right.deepEquals(((Operation)expr).m_right)) {
/* 118:208 */       return false;
/* 119:    */     }
/* 120:210 */     return true;
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Operation
 * JD-Core Version:    0.7.0.1
 */