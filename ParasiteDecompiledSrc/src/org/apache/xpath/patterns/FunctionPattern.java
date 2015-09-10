/*   1:    */ package org.apache.xpath.patterns;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xpath.Expression;
/*   8:    */ import org.apache.xpath.ExpressionOwner;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.XPathVisitor;
/*  11:    */ import org.apache.xpath.objects.XNumber;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ 
/*  14:    */ public class FunctionPattern
/*  15:    */   extends StepPattern
/*  16:    */ {
/*  17:    */   static final long serialVersionUID = -5426793413091209944L;
/*  18:    */   Expression m_functionExpr;
/*  19:    */   
/*  20:    */   public FunctionPattern(Expression expr, int axis, int predaxis)
/*  21:    */   {
/*  22: 49 */     super(0, null, null, axis, predaxis);
/*  23:    */     
/*  24: 51 */     this.m_functionExpr = expr;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public final void calcScore()
/*  28:    */   {
/*  29: 60 */     this.m_score = NodeTest.SCORE_OTHER;
/*  30: 62 */     if (null == this.m_targetString) {
/*  31: 63 */       calcTargetString();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  36:    */   {
/*  37: 84 */     super.fixupVariables(vars, globalsSize);
/*  38: 85 */     this.m_functionExpr.fixupVariables(vars, globalsSize);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public XObject execute(XPathContext xctxt, int context)
/*  42:    */     throws TransformerException
/*  43:    */   {
/*  44:106 */     DTMIterator nl = this.m_functionExpr.asIterator(xctxt, context);
/*  45:107 */     XNumber score = NodeTest.SCORE_NONE;
/*  46:109 */     if (null != nl)
/*  47:    */     {
/*  48:    */       int n;
/*  49:113 */       while (-1 != (n = nl.nextNode()))
/*  50:    */       {
/*  51:    */         int i;
/*  52:115 */         score = i == context ? NodeTest.SCORE_OTHER : NodeTest.SCORE_NONE;
/*  53:117 */         if (score == NodeTest.SCORE_OTHER)
/*  54:    */         {
/*  55:119 */           context = i;
/*  56:    */           
/*  57:121 */           break;
/*  58:    */         }
/*  59:    */       }
/*  60:    */     }
/*  61:127 */     nl.detach();
/*  62:    */     
/*  63:129 */     return score;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public XObject execute(XPathContext xctxt, int context, DTM dtm, int expType)
/*  67:    */     throws TransformerException
/*  68:    */   {
/*  69:150 */     DTMIterator nl = this.m_functionExpr.asIterator(xctxt, context);
/*  70:151 */     XNumber score = NodeTest.SCORE_NONE;
/*  71:153 */     if (null != nl)
/*  72:    */     {
/*  73:    */       int n;
/*  74:157 */       while (-1 != (n = nl.nextNode()))
/*  75:    */       {
/*  76:    */         int i;
/*  77:159 */         score = i == context ? NodeTest.SCORE_OTHER : NodeTest.SCORE_NONE;
/*  78:161 */         if (score == NodeTest.SCORE_OTHER)
/*  79:    */         {
/*  80:163 */           context = i;
/*  81:    */           
/*  82:165 */           break;
/*  83:    */         }
/*  84:    */       }
/*  85:169 */       nl.detach();
/*  86:    */     }
/*  87:172 */     return score;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public XObject execute(XPathContext xctxt)
/*  91:    */     throws TransformerException
/*  92:    */   {
/*  93:192 */     int context = xctxt.getCurrentNode();
/*  94:193 */     DTMIterator nl = this.m_functionExpr.asIterator(xctxt, context);
/*  95:194 */     XNumber score = NodeTest.SCORE_NONE;
/*  96:196 */     if (null != nl)
/*  97:    */     {
/*  98:    */       int n;
/*  99:200 */       while (-1 != (n = nl.nextNode()))
/* 100:    */       {
/* 101:    */         int i;
/* 102:202 */         score = i == context ? NodeTest.SCORE_OTHER : NodeTest.SCORE_NONE;
/* 103:204 */         if (score == NodeTest.SCORE_OTHER)
/* 104:    */         {
/* 105:206 */           context = i;
/* 106:    */           
/* 107:208 */           break;
/* 108:    */         }
/* 109:    */       }
/* 110:212 */       nl.detach();
/* 111:    */     }
/* 112:215 */     return score;
/* 113:    */   }
/* 114:    */   
/* 115:    */   class FunctionOwner
/* 116:    */     implements ExpressionOwner
/* 117:    */   {
/* 118:    */     FunctionOwner() {}
/* 119:    */     
/* 120:    */     public Expression getExpression()
/* 121:    */     {
/* 122:225 */       return FunctionPattern.this.m_functionExpr;
/* 123:    */     }
/* 124:    */     
/* 125:    */     public void setExpression(Expression exp)
/* 126:    */     {
/* 127:234 */       exp.exprSetParent(FunctionPattern.this);
/* 128:235 */       FunctionPattern.this.m_functionExpr = exp;
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void callSubtreeVisitors(XPathVisitor visitor)
/* 133:    */   {
/* 134:244 */     this.m_functionExpr.callVisitors(new FunctionOwner(), visitor);
/* 135:245 */     super.callSubtreeVisitors(visitor);
/* 136:    */   }
/* 137:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.patterns.FunctionPattern
 * JD-Core Version:    0.7.0.1
 */