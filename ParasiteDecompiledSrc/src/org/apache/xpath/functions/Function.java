/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.ExpressionOwner;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.XPathVisitor;
/*   9:    */ import org.apache.xpath.compiler.Compiler;
/*  10:    */ import org.apache.xpath.objects.XObject;
/*  11:    */ import org.apache.xpath.res.XPATHMessages;
/*  12:    */ 
/*  13:    */ public abstract class Function
/*  14:    */   extends Expression
/*  15:    */ {
/*  16:    */   static final long serialVersionUID = 6927661240854599768L;
/*  17:    */   
/*  18:    */   public void setArg(Expression arg, int argNum)
/*  19:    */     throws WrongNumberArgsException
/*  20:    */   {
/*  21: 59 */     reportWrongNumberArgs();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void checkNumberArgs(int argNum)
/*  25:    */     throws WrongNumberArgsException
/*  26:    */   {
/*  27: 74 */     if (argNum != 0) {
/*  28: 75 */       reportWrongNumberArgs();
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected void reportWrongNumberArgs()
/*  33:    */     throws WrongNumberArgsException
/*  34:    */   {
/*  35: 86 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("zero", null));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public XObject execute(XPathContext xctxt)
/*  39:    */     throws TransformerException
/*  40:    */   {
/*  41:101 */     System.out.println("Error! Function.execute should not be called!");
/*  42:    */     
/*  43:103 */     return null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void callArgVisitors(XPathVisitor visitor) {}
/*  47:    */   
/*  48:    */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  49:    */   {
/*  50:119 */     if (visitor.visitFunction(owner, this)) {
/*  51:121 */       callArgVisitors(visitor);
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean deepEquals(Expression expr)
/*  56:    */   {
/*  57:130 */     if (!isSameClass(expr)) {
/*  58:131 */       return false;
/*  59:    */     }
/*  60:133 */     return true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void postCompileStep(Compiler compiler) {}
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.Function
 * JD-Core Version:    0.7.0.1
 */