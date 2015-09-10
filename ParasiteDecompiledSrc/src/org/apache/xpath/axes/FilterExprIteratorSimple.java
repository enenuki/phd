/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.PrefixResolver;
/*   6:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.ExpressionOwner;
/*   9:    */ import org.apache.xpath.VariableStack;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.XPathVisitor;
/*  12:    */ import org.apache.xpath.objects.XNodeSet;
/*  13:    */ 
/*  14:    */ public class FilterExprIteratorSimple
/*  15:    */   extends LocPathIterator
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -6978977187025375579L;
/*  18:    */   private Expression m_expr;
/*  19:    */   private transient XNodeSet m_exprObj;
/*  20: 47 */   private boolean m_mustHardReset = false;
/*  21: 48 */   private boolean m_canDetachNodeset = true;
/*  22:    */   
/*  23:    */   public FilterExprIteratorSimple()
/*  24:    */   {
/*  25: 56 */     super(null);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public FilterExprIteratorSimple(Expression expr)
/*  29:    */   {
/*  30: 65 */     super(null);
/*  31: 66 */     this.m_expr = expr;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setRoot(int context, Object environment)
/*  35:    */   {
/*  36: 78 */     super.setRoot(context, environment);
/*  37: 79 */     this.m_exprObj = executeFilterExpr(context, this.m_execContext, getPrefixResolver(), getIsTopLevel(), this.m_stackFrame, this.m_expr);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static XNodeSet executeFilterExpr(int context, XPathContext xctxt, PrefixResolver prefixResolver, boolean isTopLevel, int stackFrame, Expression expr)
/*  41:    */     throws WrappedRuntimeException
/*  42:    */   {
/*  43: 94 */     PrefixResolver savedResolver = xctxt.getNamespaceContext();
/*  44: 95 */     XNodeSet result = null;
/*  45:    */     try
/*  46:    */     {
/*  47: 99 */       xctxt.pushCurrentNode(context);
/*  48:100 */       xctxt.setNamespaceContext(prefixResolver);
/*  49:107 */       if (isTopLevel)
/*  50:    */       {
/*  51:110 */         VariableStack vars = xctxt.getVarStack();
/*  52:    */         
/*  53:    */ 
/*  54:113 */         int savedStart = vars.getStackFrame();
/*  55:114 */         vars.setStackFrame(stackFrame);
/*  56:    */         
/*  57:116 */         result = (XNodeSet)expr.execute(xctxt);
/*  58:117 */         result.setShouldCacheNodes(true);
/*  59:    */         
/*  60:    */ 
/*  61:120 */         vars.setStackFrame(savedStart);
/*  62:    */       }
/*  63:    */       else
/*  64:    */       {
/*  65:123 */         result = (XNodeSet)expr.execute(xctxt);
/*  66:    */       }
/*  67:    */     }
/*  68:    */     catch (TransformerException se)
/*  69:    */     {
/*  70:130 */       throw new WrappedRuntimeException(se);
/*  71:    */     }
/*  72:    */     finally
/*  73:    */     {
/*  74:134 */       xctxt.popCurrentNode();
/*  75:135 */       xctxt.setNamespaceContext(savedResolver);
/*  76:    */     }
/*  77:137 */     return result;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int nextNode()
/*  81:    */   {
/*  82:150 */     if (this.m_foundLast) {
/*  83:151 */       return -1;
/*  84:    */     }
/*  85:    */     int next;
/*  86:155 */     if (null != this.m_exprObj) {
/*  87:157 */       this.m_lastFetched = (next = this.m_exprObj.nextNode());
/*  88:    */     } else {
/*  89:160 */       this.m_lastFetched = (next = -1);
/*  90:    */     }
/*  91:163 */     if (-1 != next)
/*  92:    */     {
/*  93:165 */       this.m_pos += 1;
/*  94:166 */       return next;
/*  95:    */     }
/*  96:170 */     this.m_foundLast = true;
/*  97:    */     
/*  98:172 */     return -1;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void detach()
/* 102:    */   {
/* 103:183 */     if (this.m_allowDetach)
/* 104:    */     {
/* 105:185 */       super.detach();
/* 106:186 */       this.m_exprObj.detach();
/* 107:187 */       this.m_exprObj = null;
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void fixupVariables(Vector vars, int globalsSize)
/* 112:    */   {
/* 113:203 */     super.fixupVariables(vars, globalsSize);
/* 114:204 */     this.m_expr.fixupVariables(vars, globalsSize);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Expression getInnerExpression()
/* 118:    */   {
/* 119:212 */     return this.m_expr;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setInnerExpression(Expression expr)
/* 123:    */   {
/* 124:220 */     expr.exprSetParent(this);
/* 125:221 */     this.m_expr = expr;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getAnalysisBits()
/* 129:    */   {
/* 130:230 */     if ((null != this.m_expr) && ((this.m_expr instanceof PathComponent))) {
/* 131:232 */       return ((PathComponent)this.m_expr).getAnalysisBits();
/* 132:    */     }
/* 133:234 */     return 67108864;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isDocOrdered()
/* 137:    */   {
/* 138:246 */     return this.m_exprObj.isDocOrdered();
/* 139:    */   }
/* 140:    */   
/* 141:    */   class filterExprOwner
/* 142:    */     implements ExpressionOwner
/* 143:    */   {
/* 144:    */     filterExprOwner() {}
/* 145:    */     
/* 146:    */     public Expression getExpression()
/* 147:    */     {
/* 148:256 */       return FilterExprIteratorSimple.this.m_expr;
/* 149:    */     }
/* 150:    */     
/* 151:    */     public void setExpression(Expression exp)
/* 152:    */     {
/* 153:264 */       exp.exprSetParent(FilterExprIteratorSimple.this);
/* 154:265 */       FilterExprIteratorSimple.this.m_expr = exp;
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void callPredicateVisitors(XPathVisitor visitor)
/* 159:    */   {
/* 160:279 */     this.m_expr.callVisitors(new filterExprOwner(), visitor);
/* 161:    */     
/* 162:281 */     super.callPredicateVisitors(visitor);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean deepEquals(Expression expr)
/* 166:    */   {
/* 167:289 */     if (!super.deepEquals(expr)) {
/* 168:290 */       return false;
/* 169:    */     }
/* 170:292 */     FilterExprIteratorSimple fet = (FilterExprIteratorSimple)expr;
/* 171:293 */     if (!this.m_expr.deepEquals(fet.m_expr)) {
/* 172:294 */       return false;
/* 173:    */     }
/* 174:296 */     return true;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getAxis()
/* 178:    */   {
/* 179:307 */     if (null != this.m_exprObj) {
/* 180:308 */       return this.m_exprObj.getAxis();
/* 181:    */     }
/* 182:310 */     return 20;
/* 183:    */   }
/* 184:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.FilterExprIteratorSimple
 * JD-Core Version:    0.7.0.1
 */