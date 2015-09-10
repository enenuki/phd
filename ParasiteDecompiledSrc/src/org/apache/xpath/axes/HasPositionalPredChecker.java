/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import org.apache.xpath.Expression;
/*   4:    */ import org.apache.xpath.ExpressionOwner;
/*   5:    */ import org.apache.xpath.XPathVisitor;
/*   6:    */ import org.apache.xpath.functions.FuncLast;
/*   7:    */ import org.apache.xpath.functions.FuncPosition;
/*   8:    */ import org.apache.xpath.functions.Function;
/*   9:    */ import org.apache.xpath.objects.XNumber;
/*  10:    */ import org.apache.xpath.operations.Div;
/*  11:    */ import org.apache.xpath.operations.Minus;
/*  12:    */ import org.apache.xpath.operations.Mod;
/*  13:    */ import org.apache.xpath.operations.Mult;
/*  14:    */ import org.apache.xpath.operations.Number;
/*  15:    */ import org.apache.xpath.operations.Plus;
/*  16:    */ import org.apache.xpath.operations.Quo;
/*  17:    */ import org.apache.xpath.operations.Variable;
/*  18:    */ 
/*  19:    */ public class HasPositionalPredChecker
/*  20:    */   extends XPathVisitor
/*  21:    */ {
/*  22: 40 */   private boolean m_hasPositionalPred = false;
/*  23: 41 */   private int m_predDepth = 0;
/*  24:    */   
/*  25:    */   public static boolean check(LocPathIterator path)
/*  26:    */   {
/*  27: 52 */     HasPositionalPredChecker hppc = new HasPositionalPredChecker();
/*  28: 53 */     path.callVisitors(null, hppc);
/*  29: 54 */     return hppc.m_hasPositionalPred;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean visitFunction(ExpressionOwner owner, Function func)
/*  33:    */   {
/*  34: 66 */     if (((func instanceof FuncPosition)) || ((func instanceof FuncLast))) {
/*  35: 68 */       this.m_hasPositionalPred = true;
/*  36:    */     }
/*  37: 69 */     return true;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean visitPredicate(ExpressionOwner owner, Expression pred)
/*  41:    */   {
/*  42: 97 */     this.m_predDepth += 1;
/*  43: 99 */     if (this.m_predDepth == 1) {
/*  44:101 */       if (((pred instanceof Variable)) || ((pred instanceof XNumber)) || ((pred instanceof Div)) || ((pred instanceof Plus)) || ((pred instanceof Minus)) || ((pred instanceof Mod)) || ((pred instanceof Quo)) || ((pred instanceof Mult)) || ((pred instanceof Number)) || ((pred instanceof Function))) {
/*  45:111 */         this.m_hasPositionalPred = true;
/*  46:    */       } else {
/*  47:113 */         pred.callVisitors(owner, this);
/*  48:    */       }
/*  49:    */     }
/*  50:116 */     this.m_predDepth -= 1;
/*  51:    */     
/*  52:    */ 
/*  53:119 */     return false;
/*  54:    */   }
/*  55:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.HasPositionalPredChecker
 * JD-Core Version:    0.7.0.1
 */