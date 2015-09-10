/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xpath.Expression;
/*   5:    */ import org.apache.xpath.ExpressionOwner;
/*   6:    */ import org.apache.xpath.XPathVisitor;
/*   7:    */ import org.apache.xpath.res.XPATHMessages;
/*   8:    */ 
/*   9:    */ public class FunctionOneArg
/*  10:    */   extends Function
/*  11:    */   implements ExpressionOwner
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -5180174180765609758L;
/*  14:    */   Expression m_arg0;
/*  15:    */   
/*  16:    */   public Expression getArg0()
/*  17:    */   {
/*  18: 48 */     return this.m_arg0;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setArg(Expression arg, int argNum)
/*  22:    */     throws WrongNumberArgsException
/*  23:    */   {
/*  24: 64 */     if (0 == argNum)
/*  25:    */     {
/*  26: 66 */       this.m_arg0 = arg;
/*  27: 67 */       arg.exprSetParent(this);
/*  28:    */     }
/*  29:    */     else
/*  30:    */     {
/*  31: 70 */       reportWrongNumberArgs();
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void checkNumberArgs(int argNum)
/*  36:    */     throws WrongNumberArgsException
/*  37:    */   {
/*  38: 83 */     if (argNum != 1) {
/*  39: 84 */       reportWrongNumberArgs();
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void reportWrongNumberArgs()
/*  44:    */     throws WrongNumberArgsException
/*  45:    */   {
/*  46: 94 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("one", null));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean canTraverseOutsideSubtree()
/*  50:    */   {
/*  51:105 */     return this.m_arg0.canTraverseOutsideSubtree();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  55:    */   {
/*  56:120 */     if (null != this.m_arg0) {
/*  57:121 */       this.m_arg0.fixupVariables(vars, globalsSize);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void callArgVisitors(XPathVisitor visitor)
/*  62:    */   {
/*  63:129 */     if (null != this.m_arg0) {
/*  64:130 */       this.m_arg0.callVisitors(this, visitor);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Expression getExpression()
/*  69:    */   {
/*  70:139 */     return this.m_arg0;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setExpression(Expression exp)
/*  74:    */   {
/*  75:147 */     exp.exprSetParent(this);
/*  76:148 */     this.m_arg0 = exp;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean deepEquals(Expression expr)
/*  80:    */   {
/*  81:156 */     if (!super.deepEquals(expr)) {
/*  82:157 */       return false;
/*  83:    */     }
/*  84:159 */     if (null != this.m_arg0)
/*  85:    */     {
/*  86:161 */       if (null == ((FunctionOneArg)expr).m_arg0) {
/*  87:162 */         return false;
/*  88:    */       }
/*  89:164 */       if (!this.m_arg0.deepEquals(((FunctionOneArg)expr).m_arg0)) {
/*  90:165 */         return false;
/*  91:    */       }
/*  92:    */     }
/*  93:167 */     else if (null != ((FunctionOneArg)expr).m_arg0)
/*  94:    */     {
/*  95:168 */       return false;
/*  96:    */     }
/*  97:170 */     return true;
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FunctionOneArg
 * JD-Core Version:    0.7.0.1
 */