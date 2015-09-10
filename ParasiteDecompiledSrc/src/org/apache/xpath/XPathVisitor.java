/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import org.apache.xpath.axes.LocPathIterator;
/*   4:    */ import org.apache.xpath.axes.UnionPathIterator;
/*   5:    */ import org.apache.xpath.functions.Function;
/*   6:    */ import org.apache.xpath.objects.XNumber;
/*   7:    */ import org.apache.xpath.objects.XString;
/*   8:    */ import org.apache.xpath.operations.Operation;
/*   9:    */ import org.apache.xpath.operations.UnaryOperation;
/*  10:    */ import org.apache.xpath.operations.Variable;
/*  11:    */ import org.apache.xpath.patterns.NodeTest;
/*  12:    */ import org.apache.xpath.patterns.StepPattern;
/*  13:    */ import org.apache.xpath.patterns.UnionPattern;
/*  14:    */ 
/*  15:    */ public class XPathVisitor
/*  16:    */ {
/*  17:    */   public boolean visitLocationPath(ExpressionOwner owner, LocPathIterator path)
/*  18:    */   {
/*  19: 62 */     return true;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean visitUnionPath(ExpressionOwner owner, UnionPathIterator path)
/*  23:    */   {
/*  24: 74 */     return true;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean visitStep(ExpressionOwner owner, NodeTest step)
/*  28:    */   {
/*  29: 86 */     return true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean visitPredicate(ExpressionOwner owner, Expression pred)
/*  33:    */   {
/*  34:101 */     return true;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean visitBinaryOperation(ExpressionOwner owner, Operation op)
/*  38:    */   {
/*  39:113 */     return true;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean visitUnaryOperation(ExpressionOwner owner, UnaryOperation op)
/*  43:    */   {
/*  44:125 */     return true;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean visitVariableRef(ExpressionOwner owner, Variable var)
/*  48:    */   {
/*  49:137 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean visitFunction(ExpressionOwner owner, Function func)
/*  53:    */   {
/*  54:149 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean visitMatchPattern(ExpressionOwner owner, StepPattern pattern)
/*  58:    */   {
/*  59:161 */     return true;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean visitUnionPattern(ExpressionOwner owner, UnionPattern pattern)
/*  63:    */   {
/*  64:173 */     return true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean visitStringLiteral(ExpressionOwner owner, XString str)
/*  68:    */   {
/*  69:185 */     return true;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean visitNumberLiteral(ExpressionOwner owner, XNumber num)
/*  73:    */   {
/*  74:198 */     return true;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.XPathVisitor
 * JD-Core Version:    0.7.0.1
 */