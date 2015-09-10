/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xpath.Expression;
/*   5:    */ import org.apache.xpath.ExpressionOwner;
/*   6:    */ import org.apache.xpath.XPathVisitor;
/*   7:    */ import org.apache.xpath.res.XPATHMessages;
/*   8:    */ 
/*   9:    */ public class FunctionMultiArgs
/*  10:    */   extends Function3Args
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = 7117257746138417181L;
/*  13:    */   Expression[] m_args;
/*  14:    */   
/*  15:    */   public Expression[] getArgs()
/*  16:    */   {
/*  17: 49 */     return this.m_args;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setArg(Expression arg, int argNum)
/*  21:    */     throws WrongNumberArgsException
/*  22:    */   {
/*  23: 66 */     if (argNum < 3)
/*  24:    */     {
/*  25: 67 */       super.setArg(arg, argNum);
/*  26:    */     }
/*  27:    */     else
/*  28:    */     {
/*  29: 70 */       if (null == this.m_args)
/*  30:    */       {
/*  31: 72 */         this.m_args = new Expression[1];
/*  32: 73 */         this.m_args[0] = arg;
/*  33:    */       }
/*  34:    */       else
/*  35:    */       {
/*  36: 79 */         Expression[] args = new Expression[this.m_args.length + 1];
/*  37:    */         
/*  38: 81 */         System.arraycopy(this.m_args, 0, args, 0, this.m_args.length);
/*  39:    */         
/*  40: 83 */         args[this.m_args.length] = arg;
/*  41: 84 */         this.m_args = args;
/*  42:    */       }
/*  43: 86 */       arg.exprSetParent(this);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  48:    */   {
/*  49:102 */     super.fixupVariables(vars, globalsSize);
/*  50:103 */     if (null != this.m_args) {
/*  51:105 */       for (int i = 0; i < this.m_args.length; i++) {
/*  52:107 */         this.m_args[i].fixupVariables(vars, globalsSize);
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void checkNumberArgs(int argNum)
/*  58:    */     throws WrongNumberArgsException
/*  59:    */   {}
/*  60:    */   
/*  61:    */   protected void reportWrongNumberArgs()
/*  62:    */     throws WrongNumberArgsException
/*  63:    */   {
/*  64:130 */     String fMsg = XPATHMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { "Programmer's assertion:  the method FunctionMultiArgs.reportWrongNumberArgs() should never be called." });
/*  65:    */     
/*  66:    */ 
/*  67:    */ 
/*  68:134 */     throw new RuntimeException(fMsg);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean canTraverseOutsideSubtree()
/*  72:    */   {
/*  73:146 */     if (super.canTraverseOutsideSubtree()) {
/*  74:147 */       return true;
/*  75:    */     }
/*  76:150 */     int n = this.m_args.length;
/*  77:152 */     for (int i = 0; i < n; i++) {
/*  78:154 */       if (this.m_args[i].canTraverseOutsideSubtree()) {
/*  79:155 */         return true;
/*  80:    */       }
/*  81:    */     }
/*  82:158 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   class ArgMultiOwner
/*  86:    */     implements ExpressionOwner
/*  87:    */   {
/*  88:    */     int m_argIndex;
/*  89:    */     
/*  90:    */     ArgMultiOwner(int index)
/*  91:    */     {
/*  92:168 */       this.m_argIndex = index;
/*  93:    */     }
/*  94:    */     
/*  95:    */     public Expression getExpression()
/*  96:    */     {
/*  97:176 */       return FunctionMultiArgs.this.m_args[this.m_argIndex];
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void setExpression(Expression exp)
/* 101:    */     {
/* 102:185 */       exp.exprSetParent(FunctionMultiArgs.this);
/* 103:186 */       FunctionMultiArgs.this.m_args[this.m_argIndex] = exp;
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void callArgVisitors(XPathVisitor visitor)
/* 108:    */   {
/* 109:196 */     super.callArgVisitors(visitor);
/* 110:197 */     if (null != this.m_args)
/* 111:    */     {
/* 112:199 */       int n = this.m_args.length;
/* 113:200 */       for (int i = 0; i < n; i++) {
/* 114:202 */         this.m_args[i].callVisitors(new ArgMultiOwner(i), visitor);
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean deepEquals(Expression expr)
/* 120:    */   {
/* 121:212 */     if (!super.deepEquals(expr)) {
/* 122:213 */       return false;
/* 123:    */     }
/* 124:215 */     FunctionMultiArgs fma = (FunctionMultiArgs)expr;
/* 125:216 */     if (null != this.m_args)
/* 126:    */     {
/* 127:218 */       int n = this.m_args.length;
/* 128:219 */       if ((null == fma) || (fma.m_args.length != n)) {
/* 129:220 */         return false;
/* 130:    */       }
/* 131:222 */       for (int i = 0; i < n; i++) {
/* 132:224 */         if (!this.m_args[i].deepEquals(fma.m_args[i])) {
/* 133:225 */           return false;
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:229 */     else if (null != fma.m_args)
/* 138:    */     {
/* 139:231 */       return false;
/* 140:    */     }
/* 141:234 */     return true;
/* 142:    */   }
/* 143:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FunctionMultiArgs
 * JD-Core Version:    0.7.0.1
 */