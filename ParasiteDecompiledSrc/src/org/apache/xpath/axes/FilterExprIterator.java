/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xpath.Expression;
/*   5:    */ import org.apache.xpath.ExpressionOwner;
/*   6:    */ import org.apache.xpath.XPathVisitor;
/*   7:    */ import org.apache.xpath.objects.XNodeSet;
/*   8:    */ 
/*   9:    */ public class FilterExprIterator
/*  10:    */   extends BasicTestIterator
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = 2552176105165737614L;
/*  13:    */   private Expression m_expr;
/*  14:    */   private transient XNodeSet m_exprObj;
/*  15: 39 */   private boolean m_mustHardReset = false;
/*  16: 40 */   private boolean m_canDetachNodeset = true;
/*  17:    */   
/*  18:    */   public FilterExprIterator()
/*  19:    */   {
/*  20: 48 */     super(null);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public FilterExprIterator(Expression expr)
/*  24:    */   {
/*  25: 57 */     super(null);
/*  26: 58 */     this.m_expr = expr;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setRoot(int context, Object environment)
/*  30:    */   {
/*  31: 70 */     super.setRoot(context, environment);
/*  32:    */     
/*  33: 72 */     this.m_exprObj = FilterExprIteratorSimple.executeFilterExpr(context, this.m_execContext, getPrefixResolver(), getIsTopLevel(), this.m_stackFrame, this.m_expr);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int getNextNode()
/*  37:    */   {
/*  38: 84 */     if (null != this.m_exprObj) {
/*  39: 86 */       this.m_lastFetched = this.m_exprObj.nextNode();
/*  40:    */     } else {
/*  41: 89 */       this.m_lastFetched = -1;
/*  42:    */     }
/*  43: 91 */     return this.m_lastFetched;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void detach()
/*  47:    */   {
/*  48:101 */     super.detach();
/*  49:102 */     this.m_exprObj.detach();
/*  50:103 */     this.m_exprObj = null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  54:    */   {
/*  55:118 */     super.fixupVariables(vars, globalsSize);
/*  56:119 */     this.m_expr.fixupVariables(vars, globalsSize);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Expression getInnerExpression()
/*  60:    */   {
/*  61:127 */     return this.m_expr;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setInnerExpression(Expression expr)
/*  65:    */   {
/*  66:135 */     expr.exprSetParent(this);
/*  67:136 */     this.m_expr = expr;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getAnalysisBits()
/*  71:    */   {
/*  72:145 */     if ((null != this.m_expr) && ((this.m_expr instanceof PathComponent))) {
/*  73:147 */       return ((PathComponent)this.m_expr).getAnalysisBits();
/*  74:    */     }
/*  75:149 */     return 67108864;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isDocOrdered()
/*  79:    */   {
/*  80:161 */     return this.m_exprObj.isDocOrdered();
/*  81:    */   }
/*  82:    */   
/*  83:    */   class filterExprOwner
/*  84:    */     implements ExpressionOwner
/*  85:    */   {
/*  86:    */     filterExprOwner() {}
/*  87:    */     
/*  88:    */     public Expression getExpression()
/*  89:    */     {
/*  90:171 */       return FilterExprIterator.this.m_expr;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void setExpression(Expression exp)
/*  94:    */     {
/*  95:179 */       exp.exprSetParent(FilterExprIterator.this);
/*  96:180 */       FilterExprIterator.this.m_expr = exp;
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void callPredicateVisitors(XPathVisitor visitor)
/* 101:    */   {
/* 102:194 */     this.m_expr.callVisitors(new filterExprOwner(), visitor);
/* 103:    */     
/* 104:196 */     super.callPredicateVisitors(visitor);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean deepEquals(Expression expr)
/* 108:    */   {
/* 109:204 */     if (!super.deepEquals(expr)) {
/* 110:205 */       return false;
/* 111:    */     }
/* 112:207 */     FilterExprIterator fet = (FilterExprIterator)expr;
/* 113:208 */     if (!this.m_expr.deepEquals(fet.m_expr)) {
/* 114:209 */       return false;
/* 115:    */     }
/* 116:211 */     return true;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.FilterExprIterator
 * JD-Core Version:    0.7.0.1
 */