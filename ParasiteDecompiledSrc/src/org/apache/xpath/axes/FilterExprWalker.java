/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.ExpressionOwner;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.XPathVisitor;
/*   9:    */ import org.apache.xpath.compiler.Compiler;
/*  10:    */ import org.apache.xpath.objects.XNodeSet;
/*  11:    */ import org.apache.xpath.operations.Variable;
/*  12:    */ 
/*  13:    */ public class FilterExprWalker
/*  14:    */   extends AxesWalker
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = 5457182471424488375L;
/*  17:    */   private Expression m_expr;
/*  18:    */   private transient XNodeSet m_exprObj;
/*  19:    */   
/*  20:    */   public FilterExprWalker(WalkingIterator locPathIterator)
/*  21:    */   {
/*  22: 50 */     super(locPathIterator, 20);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void init(Compiler compiler, int opPos, int stepType)
/*  26:    */     throws TransformerException
/*  27:    */   {
/*  28: 66 */     super.init(compiler, opPos, stepType);
/*  29: 69 */     switch (stepType)
/*  30:    */     {
/*  31:    */     case 24: 
/*  32:    */     case 25: 
/*  33: 73 */       this.m_mustHardReset = true;
/*  34:    */     case 22: 
/*  35:    */     case 23: 
/*  36: 76 */       this.m_expr = compiler.compile(opPos);
/*  37: 77 */       this.m_expr.exprSetParent(this);
/*  38: 79 */       if ((this.m_expr instanceof Variable)) {
/*  39: 82 */         this.m_canDetachNodeset = false;
/*  40:    */       }
/*  41:    */       break;
/*  42:    */     default: 
/*  43: 86 */       this.m_expr = compiler.compile(opPos + 2);
/*  44: 87 */       this.m_expr.exprSetParent(this);
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void detach()
/*  49:    */   {
/*  50:112 */     super.detach();
/*  51:113 */     if (this.m_canDetachNodeset) {
/*  52:115 */       this.m_exprObj.detach();
/*  53:    */     }
/*  54:117 */     this.m_exprObj = null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setRoot(int root)
/*  58:    */   {
/*  59:129 */     super.setRoot(root);
/*  60:    */     
/*  61:131 */     this.m_exprObj = FilterExprIteratorSimple.executeFilterExpr(root, this.m_lpi.getXPathContext(), this.m_lpi.getPrefixResolver(), this.m_lpi.getIsTopLevel(), this.m_lpi.m_stackFrame, this.m_expr);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object clone()
/*  65:    */     throws CloneNotSupportedException
/*  66:    */   {
/*  67:147 */     FilterExprWalker clone = (FilterExprWalker)super.clone();
/*  68:149 */     if (null != this.m_exprObj) {
/*  69:150 */       clone.m_exprObj = ((XNodeSet)this.m_exprObj.clone());
/*  70:    */     }
/*  71:152 */     return clone;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public short acceptNode(int n)
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:167 */       if (getPredicateCount() > 0)
/*  79:    */       {
/*  80:169 */         countProximityPosition(0);
/*  81:171 */         if (!executePredicates(n, this.m_lpi.getXPathContext())) {
/*  82:172 */           return 3;
/*  83:    */         }
/*  84:    */       }
/*  85:175 */       return 1;
/*  86:    */     }
/*  87:    */     catch (TransformerException se)
/*  88:    */     {
/*  89:179 */       throw new RuntimeException(se.getMessage());
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getNextNode()
/*  94:    */   {
/*  95:195 */     if (null != this.m_exprObj)
/*  96:    */     {
/*  97:197 */       int next = this.m_exprObj.nextNode();
/*  98:198 */       return next;
/*  99:    */     }
/* 100:201 */     return -1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getLastPos(XPathContext xctxt)
/* 104:    */   {
/* 105:214 */     return this.m_exprObj.getLength();
/* 106:    */   }
/* 107:    */   
/* 108:224 */   private boolean m_mustHardReset = false;
/* 109:225 */   private boolean m_canDetachNodeset = true;
/* 110:    */   
/* 111:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 112:    */   {
/* 113:239 */     super.fixupVariables(vars, globalsSize);
/* 114:240 */     this.m_expr.fixupVariables(vars, globalsSize);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Expression getInnerExpression()
/* 118:    */   {
/* 119:248 */     return this.m_expr;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setInnerExpression(Expression expr)
/* 123:    */   {
/* 124:256 */     expr.exprSetParent(this);
/* 125:257 */     this.m_expr = expr;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getAnalysisBits()
/* 129:    */   {
/* 130:267 */     if ((null != this.m_expr) && ((this.m_expr instanceof PathComponent))) {
/* 131:269 */       return ((PathComponent)this.m_expr).getAnalysisBits();
/* 132:    */     }
/* 133:271 */     return 67108864;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isDocOrdered()
/* 137:    */   {
/* 138:283 */     return this.m_exprObj.isDocOrdered();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getAxis()
/* 142:    */   {
/* 143:294 */     return this.m_exprObj.getAxis();
/* 144:    */   }
/* 145:    */   
/* 146:    */   class filterExprOwner
/* 147:    */     implements ExpressionOwner
/* 148:    */   {
/* 149:    */     filterExprOwner() {}
/* 150:    */     
/* 151:    */     public Expression getExpression()
/* 152:    */     {
/* 153:304 */       return FilterExprWalker.this.m_expr;
/* 154:    */     }
/* 155:    */     
/* 156:    */     public void setExpression(Expression exp)
/* 157:    */     {
/* 158:312 */       exp.exprSetParent(FilterExprWalker.this);
/* 159:313 */       FilterExprWalker.this.m_expr = exp;
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void callPredicateVisitors(XPathVisitor visitor)
/* 164:    */   {
/* 165:326 */     this.m_expr.callVisitors(new filterExprOwner(), visitor);
/* 166:    */     
/* 167:328 */     super.callPredicateVisitors(visitor);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean deepEquals(Expression expr)
/* 171:    */   {
/* 172:337 */     if (!super.deepEquals(expr)) {
/* 173:338 */       return false;
/* 174:    */     }
/* 175:340 */     FilterExprWalker walker = (FilterExprWalker)expr;
/* 176:341 */     if (!this.m_expr.deepEquals(walker.m_expr)) {
/* 177:342 */       return false;
/* 178:    */     }
/* 179:344 */     return true;
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.FilterExprWalker
 * JD-Core Version:    0.7.0.1
 */