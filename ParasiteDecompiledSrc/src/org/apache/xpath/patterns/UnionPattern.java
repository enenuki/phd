/*   1:    */ package org.apache.xpath.patterns;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.ExpressionOwner;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.XPathVisitor;
/*   9:    */ import org.apache.xpath.objects.XObject;
/*  10:    */ 
/*  11:    */ public class UnionPattern
/*  12:    */   extends Expression
/*  13:    */ {
/*  14:    */   static final long serialVersionUID = -6670449967116905820L;
/*  15:    */   private StepPattern[] m_patterns;
/*  16:    */   
/*  17:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  18:    */   {
/*  19: 47 */     for (int i = 0; i < this.m_patterns.length; i++) {
/*  20: 49 */       this.m_patterns[i].fixupVariables(vars, globalsSize);
/*  21:    */     }
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean canTraverseOutsideSubtree()
/*  25:    */   {
/*  26: 62 */     if (null != this.m_patterns)
/*  27:    */     {
/*  28: 64 */       int n = this.m_patterns.length;
/*  29: 65 */       for (int i = 0; i < n; i++) {
/*  30: 67 */         if (this.m_patterns[i].canTraverseOutsideSubtree()) {
/*  31: 68 */           return true;
/*  32:    */         }
/*  33:    */       }
/*  34:    */     }
/*  35: 71 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setPatterns(StepPattern[] patterns)
/*  39:    */   {
/*  40: 82 */     this.m_patterns = patterns;
/*  41: 83 */     if (null != patterns) {
/*  42: 85 */       for (int i = 0; i < patterns.length; i++) {
/*  43: 87 */         patterns[i].exprSetParent(this);
/*  44:    */       }
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public StepPattern[] getPatterns()
/*  49:    */   {
/*  50:101 */     return this.m_patterns;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public XObject execute(XPathContext xctxt)
/*  54:    */     throws TransformerException
/*  55:    */   {
/*  56:120 */     XObject bestScore = null;
/*  57:121 */     int n = this.m_patterns.length;
/*  58:123 */     for (int i = 0; i < n; i++)
/*  59:    */     {
/*  60:125 */       XObject score = this.m_patterns[i].execute(xctxt);
/*  61:127 */       if (score != NodeTest.SCORE_NONE) {
/*  62:129 */         if (null == bestScore) {
/*  63:130 */           bestScore = score;
/*  64:131 */         } else if (score.num() > bestScore.num()) {
/*  65:132 */           bestScore = score;
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:136 */     if (null == bestScore) {
/*  70:138 */       bestScore = NodeTest.SCORE_NONE;
/*  71:    */     }
/*  72:141 */     return bestScore;
/*  73:    */   }
/*  74:    */   
/*  75:    */   class UnionPathPartOwner
/*  76:    */     implements ExpressionOwner
/*  77:    */   {
/*  78:    */     int m_index;
/*  79:    */     
/*  80:    */     UnionPathPartOwner(int index)
/*  81:    */     {
/*  82:150 */       this.m_index = index;
/*  83:    */     }
/*  84:    */     
/*  85:    */     public Expression getExpression()
/*  86:    */     {
/*  87:158 */       return UnionPattern.this.m_patterns[this.m_index];
/*  88:    */     }
/*  89:    */     
/*  90:    */     public void setExpression(Expression exp)
/*  91:    */     {
/*  92:167 */       exp.exprSetParent(UnionPattern.this);
/*  93:168 */       UnionPattern.this.m_patterns[this.m_index] = ((StepPattern)exp);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  98:    */   {
/*  99:177 */     visitor.visitUnionPattern(owner, this);
/* 100:178 */     if (null != this.m_patterns)
/* 101:    */     {
/* 102:180 */       int n = this.m_patterns.length;
/* 103:181 */       for (int i = 0; i < n; i++) {
/* 104:183 */         this.m_patterns[i].callVisitors(new UnionPathPartOwner(i), visitor);
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean deepEquals(Expression expr)
/* 110:    */   {
/* 111:193 */     if (!isSameClass(expr)) {
/* 112:194 */       return false;
/* 113:    */     }
/* 114:196 */     UnionPattern up = (UnionPattern)expr;
/* 115:198 */     if (null != this.m_patterns)
/* 116:    */     {
/* 117:200 */       int n = this.m_patterns.length;
/* 118:201 */       if ((null == up.m_patterns) || (up.m_patterns.length != n)) {
/* 119:202 */         return false;
/* 120:    */       }
/* 121:204 */       for (int i = 0; i < n; i++) {
/* 122:206 */         if (!this.m_patterns[i].deepEquals(up.m_patterns[i])) {
/* 123:207 */           return false;
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:210 */     else if (up.m_patterns != null)
/* 128:    */     {
/* 129:211 */       return false;
/* 130:    */     }
/* 131:213 */     return true;
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.patterns.UnionPattern
 * JD-Core Version:    0.7.0.1
 */