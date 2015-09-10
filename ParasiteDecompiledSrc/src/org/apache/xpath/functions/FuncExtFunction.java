/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.ExpressionNode;
/*   7:    */ import org.apache.xpath.ExpressionOwner;
/*   8:    */ import org.apache.xpath.ExtensionsProvider;
/*   9:    */ import org.apache.xpath.XPathContext;
/*  10:    */ import org.apache.xpath.XPathVisitor;
/*  11:    */ import org.apache.xpath.objects.XNull;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ import org.apache.xpath.res.XPATHMessages;
/*  14:    */ 
/*  15:    */ public class FuncExtFunction
/*  16:    */   extends Function
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = 5196115554693708718L;
/*  19:    */   String m_namespace;
/*  20:    */   String m_extensionName;
/*  21:    */   Object m_methodKey;
/*  22: 72 */   Vector m_argVec = new Vector();
/*  23:    */   
/*  24:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  25:    */   {
/*  26: 88 */     if (null != this.m_argVec)
/*  27:    */     {
/*  28: 90 */       int nArgs = this.m_argVec.size();
/*  29: 92 */       for (int i = 0; i < nArgs; i++)
/*  30:    */       {
/*  31: 94 */         Expression arg = (Expression)this.m_argVec.elementAt(i);
/*  32:    */         
/*  33: 96 */         arg.fixupVariables(vars, globalsSize);
/*  34:    */       }
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getNamespace()
/*  39:    */   {
/*  40:108 */     return this.m_namespace;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getFunctionName()
/*  44:    */   {
/*  45:118 */     return this.m_extensionName;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Object getMethodKey()
/*  49:    */   {
/*  50:128 */     return this.m_methodKey;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Expression getArg(int n)
/*  54:    */   {
/*  55:138 */     if ((n >= 0) && (n < this.m_argVec.size())) {
/*  56:139 */       return (Expression)this.m_argVec.elementAt(n);
/*  57:    */     }
/*  58:141 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getArgCount()
/*  62:    */   {
/*  63:151 */     return this.m_argVec.size();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public FuncExtFunction(String namespace, String extensionName, Object methodKey)
/*  67:    */   {
/*  68:169 */     this.m_namespace = namespace;
/*  69:170 */     this.m_extensionName = extensionName;
/*  70:171 */     this.m_methodKey = methodKey;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public XObject execute(XPathContext xctxt)
/*  74:    */     throws TransformerException
/*  75:    */   {
/*  76:185 */     if (xctxt.isSecureProcessing()) {
/*  77:186 */       throw new TransformerException(XPATHMessages.createXPATHMessage("ER_EXTENSION_FUNCTION_CANNOT_BE_INVOKED", new Object[] { toString() }));
/*  78:    */     }
/*  79:192 */     Vector argVec = new Vector();
/*  80:193 */     int nArgs = this.m_argVec.size();
/*  81:195 */     for (int i = 0; i < nArgs; i++)
/*  82:    */     {
/*  83:197 */       Expression arg = (Expression)this.m_argVec.elementAt(i);
/*  84:    */       
/*  85:199 */       XObject xobj = arg.execute(xctxt);
/*  86:    */       
/*  87:    */ 
/*  88:    */ 
/*  89:203 */       xobj.allowDetachToRelease(false);
/*  90:204 */       argVec.addElement(xobj);
/*  91:    */     }
/*  92:207 */     ExtensionsProvider extProvider = (ExtensionsProvider)xctxt.getOwnerObject();
/*  93:208 */     Object val = extProvider.extFunction(this, argVec);
/*  94:    */     XObject result;
/*  95:210 */     if (null != val) {
/*  96:212 */       result = XObject.create(val, xctxt);
/*  97:    */     } else {
/*  98:216 */       result = new XNull();
/*  99:    */     }
/* 100:219 */     return result;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setArg(Expression arg, int argNum)
/* 104:    */     throws WrongNumberArgsException
/* 105:    */   {
/* 106:235 */     this.m_argVec.addElement(arg);
/* 107:236 */     arg.exprSetParent(this);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void checkNumberArgs(int argNum)
/* 111:    */     throws WrongNumberArgsException
/* 112:    */   {}
/* 113:    */   
/* 114:    */   class ArgExtOwner
/* 115:    */     implements ExpressionOwner
/* 116:    */   {
/* 117:    */     Expression m_exp;
/* 118:    */     
/* 119:    */     ArgExtOwner(Expression exp)
/* 120:    */     {
/* 121:257 */       this.m_exp = exp;
/* 122:    */     }
/* 123:    */     
/* 124:    */     public Expression getExpression()
/* 125:    */     {
/* 126:265 */       return this.m_exp;
/* 127:    */     }
/* 128:    */     
/* 129:    */     public void setExpression(Expression exp)
/* 130:    */     {
/* 131:274 */       exp.exprSetParent(FuncExtFunction.this);
/* 132:275 */       this.m_exp = exp;
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void callArgVisitors(XPathVisitor visitor)
/* 137:    */   {
/* 138:285 */     for (int i = 0; i < this.m_argVec.size(); i++)
/* 139:    */     {
/* 140:287 */       Expression exp = (Expression)this.m_argVec.elementAt(i);
/* 141:288 */       exp.callVisitors(new ArgExtOwner(exp), visitor);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void exprSetParent(ExpressionNode n)
/* 146:    */   {
/* 147:303 */     super.exprSetParent(n);
/* 148:    */     
/* 149:305 */     int nArgs = this.m_argVec.size();
/* 150:307 */     for (int i = 0; i < nArgs; i++)
/* 151:    */     {
/* 152:309 */       Expression arg = (Expression)this.m_argVec.elementAt(i);
/* 153:    */       
/* 154:311 */       arg.exprSetParent(n);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected void reportWrongNumberArgs()
/* 159:    */     throws WrongNumberArgsException
/* 160:    */   {
/* 161:323 */     String fMsg = XPATHMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { "Programmer's assertion:  the method FunctionMultiArgs.reportWrongNumberArgs() should never be called." });
/* 162:    */     
/* 163:    */ 
/* 164:    */ 
/* 165:327 */     throw new RuntimeException(fMsg);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String toString()
/* 169:    */   {
/* 170:335 */     if ((this.m_namespace != null) && (this.m_namespace.length() > 0)) {
/* 171:336 */       return "{" + this.m_namespace + "}" + this.m_extensionName;
/* 172:    */     }
/* 173:338 */     return this.m_extensionName;
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncExtFunction
 * JD-Core Version:    0.7.0.1
 */