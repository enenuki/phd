/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xpath.Expression;
/*   5:    */ import org.apache.xpath.ExpressionOwner;
/*   6:    */ import org.apache.xpath.XPathVisitor;
/*   7:    */ import org.apache.xpath.res.XPATHMessages;
/*   8:    */ 
/*   9:    */ public class Function2Args
/*  10:    */   extends FunctionOneArg
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = 5574294996842710641L;
/*  13:    */   Expression m_arg1;
/*  14:    */   
/*  15:    */   public Expression getArg1()
/*  16:    */   {
/*  17: 48 */     return this.m_arg1;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  21:    */   {
/*  22: 63 */     super.fixupVariables(vars, globalsSize);
/*  23: 64 */     if (null != this.m_arg1) {
/*  24: 65 */       this.m_arg1.fixupVariables(vars, globalsSize);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setArg(Expression arg, int argNum)
/*  29:    */     throws WrongNumberArgsException
/*  30:    */   {
/*  31: 83 */     if (argNum == 0)
/*  32:    */     {
/*  33: 84 */       super.setArg(arg, argNum);
/*  34:    */     }
/*  35: 85 */     else if (1 == argNum)
/*  36:    */     {
/*  37: 87 */       this.m_arg1 = arg;
/*  38: 88 */       arg.exprSetParent(this);
/*  39:    */     }
/*  40:    */     else
/*  41:    */     {
/*  42: 91 */       reportWrongNumberArgs();
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void checkNumberArgs(int argNum)
/*  47:    */     throws WrongNumberArgsException
/*  48:    */   {
/*  49:104 */     if (argNum != 2) {
/*  50:105 */       reportWrongNumberArgs();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void reportWrongNumberArgs()
/*  55:    */     throws WrongNumberArgsException
/*  56:    */   {
/*  57:115 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("two", null));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean canTraverseOutsideSubtree()
/*  61:    */   {
/*  62:126 */     return super.canTraverseOutsideSubtree() ? true : this.m_arg1.canTraverseOutsideSubtree();
/*  63:    */   }
/*  64:    */   
/*  65:    */   class Arg1Owner
/*  66:    */     implements ExpressionOwner
/*  67:    */   {
/*  68:    */     Arg1Owner() {}
/*  69:    */     
/*  70:    */     public Expression getExpression()
/*  71:    */     {
/*  72:137 */       return Function2Args.this.m_arg1;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public void setExpression(Expression exp)
/*  76:    */     {
/*  77:146 */       exp.exprSetParent(Function2Args.this);
/*  78:147 */       Function2Args.this.m_arg1 = exp;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void callArgVisitors(XPathVisitor visitor)
/*  83:    */   {
/*  84:157 */     super.callArgVisitors(visitor);
/*  85:158 */     if (null != this.m_arg1) {
/*  86:159 */       this.m_arg1.callVisitors(new Arg1Owner(), visitor);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean deepEquals(Expression expr)
/*  91:    */   {
/*  92:167 */     if (!super.deepEquals(expr)) {
/*  93:168 */       return false;
/*  94:    */     }
/*  95:170 */     if (null != this.m_arg1)
/*  96:    */     {
/*  97:172 */       if (null == ((Function2Args)expr).m_arg1) {
/*  98:173 */         return false;
/*  99:    */       }
/* 100:175 */       if (!this.m_arg1.deepEquals(((Function2Args)expr).m_arg1)) {
/* 101:176 */         return false;
/* 102:    */       }
/* 103:    */     }
/* 104:178 */     else if (null != ((Function2Args)expr).m_arg1)
/* 105:    */     {
/* 106:179 */       return false;
/* 107:    */     }
/* 108:181 */     return true;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.Function2Args
 * JD-Core Version:    0.7.0.1
 */