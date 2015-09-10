/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xpath.Expression;
/*   5:    */ import org.apache.xpath.ExpressionOwner;
/*   6:    */ import org.apache.xpath.XPathVisitor;
/*   7:    */ import org.apache.xpath.res.XPATHMessages;
/*   8:    */ 
/*   9:    */ public class Function3Args
/*  10:    */   extends Function2Args
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = 7915240747161506646L;
/*  13:    */   Expression m_arg2;
/*  14:    */   
/*  15:    */   public Expression getArg2()
/*  16:    */   {
/*  17: 48 */     return this.m_arg2;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  21:    */   {
/*  22: 63 */     super.fixupVariables(vars, globalsSize);
/*  23: 64 */     if (null != this.m_arg2) {
/*  24: 65 */       this.m_arg2.fixupVariables(vars, globalsSize);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setArg(Expression arg, int argNum)
/*  29:    */     throws WrongNumberArgsException
/*  30:    */   {
/*  31: 81 */     if (argNum < 2)
/*  32:    */     {
/*  33: 82 */       super.setArg(arg, argNum);
/*  34:    */     }
/*  35: 83 */     else if (2 == argNum)
/*  36:    */     {
/*  37: 85 */       this.m_arg2 = arg;
/*  38: 86 */       arg.exprSetParent(this);
/*  39:    */     }
/*  40:    */     else
/*  41:    */     {
/*  42: 89 */       reportWrongNumberArgs();
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void checkNumberArgs(int argNum)
/*  47:    */     throws WrongNumberArgsException
/*  48:    */   {
/*  49:102 */     if (argNum != 3) {
/*  50:103 */       reportWrongNumberArgs();
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void reportWrongNumberArgs()
/*  55:    */     throws WrongNumberArgsException
/*  56:    */   {
/*  57:113 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("three", null));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean canTraverseOutsideSubtree()
/*  61:    */   {
/*  62:124 */     return super.canTraverseOutsideSubtree() ? true : this.m_arg2.canTraverseOutsideSubtree();
/*  63:    */   }
/*  64:    */   
/*  65:    */   class Arg2Owner
/*  66:    */     implements ExpressionOwner
/*  67:    */   {
/*  68:    */     Arg2Owner() {}
/*  69:    */     
/*  70:    */     public Expression getExpression()
/*  71:    */     {
/*  72:135 */       return Function3Args.this.m_arg2;
/*  73:    */     }
/*  74:    */     
/*  75:    */     public void setExpression(Expression exp)
/*  76:    */     {
/*  77:144 */       exp.exprSetParent(Function3Args.this);
/*  78:145 */       Function3Args.this.m_arg2 = exp;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void callArgVisitors(XPathVisitor visitor)
/*  83:    */   {
/*  84:155 */     super.callArgVisitors(visitor);
/*  85:156 */     if (null != this.m_arg2) {
/*  86:157 */       this.m_arg2.callVisitors(new Arg2Owner(), visitor);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean deepEquals(Expression expr)
/*  91:    */   {
/*  92:165 */     if (!super.deepEquals(expr)) {
/*  93:166 */       return false;
/*  94:    */     }
/*  95:168 */     if (null != this.m_arg2)
/*  96:    */     {
/*  97:170 */       if (null == ((Function3Args)expr).m_arg2) {
/*  98:171 */         return false;
/*  99:    */       }
/* 100:173 */       if (!this.m_arg2.deepEquals(((Function3Args)expr).m_arg2)) {
/* 101:174 */         return false;
/* 102:    */       }
/* 103:    */     }
/* 104:176 */     else if (null != ((Function3Args)expr).m_arg2)
/* 105:    */     {
/* 106:177 */       return false;
/* 107:    */     }
/* 108:179 */     return true;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.Function3Args
 * JD-Core Version:    0.7.0.1
 */