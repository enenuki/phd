/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   6:    */ import org.apache.xpath.Expression;
/*   7:    */ import org.apache.xpath.compiler.Compiler;
/*   8:    */ import org.apache.xpath.compiler.OpMap;
/*   9:    */ import org.apache.xpath.patterns.NodeTest;
/*  10:    */ 
/*  11:    */ public class OneStepIteratorForward
/*  12:    */   extends ChildTestIterator
/*  13:    */ {
/*  14:    */   static final long serialVersionUID = -1576936606178190566L;
/*  15: 41 */   protected int m_axis = -1;
/*  16:    */   
/*  17:    */   OneStepIteratorForward(Compiler compiler, int opPos, int analysis)
/*  18:    */     throws TransformerException
/*  19:    */   {
/*  20: 55 */     super(compiler, opPos, analysis);
/*  21: 56 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  22:    */     
/*  23: 58 */     this.m_axis = WalkerFactory.getAxisFromStep(compiler, firstStepPos);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public OneStepIteratorForward(int axis)
/*  27:    */   {
/*  28: 71 */     super(null);
/*  29:    */     
/*  30: 73 */     this.m_axis = axis;
/*  31: 74 */     int whatToShow = -1;
/*  32: 75 */     initNodeTest(whatToShow);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setRoot(int context, Object environment)
/*  36:    */   {
/*  37: 90 */     super.setRoot(context, environment);
/*  38: 91 */     this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_axis);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected int getNextNode()
/*  42:    */   {
/*  43:140 */     this.m_lastFetched = (-1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched));
/*  44:    */     
/*  45:    */ 
/*  46:143 */     return this.m_lastFetched;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getAxis()
/*  50:    */   {
/*  51:154 */     return this.m_axis;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean deepEquals(Expression expr)
/*  55:    */   {
/*  56:162 */     if (!super.deepEquals(expr)) {
/*  57:163 */       return false;
/*  58:    */     }
/*  59:165 */     if (this.m_axis != ((OneStepIteratorForward)expr).m_axis) {
/*  60:166 */       return false;
/*  61:    */     }
/*  62:168 */     return true;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.OneStepIteratorForward
 * JD-Core Version:    0.7.0.1
 */