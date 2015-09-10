/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.XPathContext;
/*   7:    */ import org.apache.xpath.objects.XObject;
/*   8:    */ import org.apache.xpath.patterns.NodeTest;
/*   9:    */ 
/*  10:    */ public class UnionChildIterator
/*  11:    */   extends ChildTestIterator
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = 3500298482193003495L;
/*  14: 42 */   private PredicatedNodeTest[] m_nodeTests = null;
/*  15:    */   
/*  16:    */   public UnionChildIterator()
/*  17:    */   {
/*  18: 49 */     super(null);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void addNodeTest(PredicatedNodeTest test)
/*  22:    */   {
/*  23: 65 */     if (null == this.m_nodeTests)
/*  24:    */     {
/*  25: 67 */       this.m_nodeTests = new PredicatedNodeTest[1];
/*  26: 68 */       this.m_nodeTests[0] = test;
/*  27:    */     }
/*  28:    */     else
/*  29:    */     {
/*  30: 72 */       PredicatedNodeTest[] tests = this.m_nodeTests;
/*  31: 73 */       int len = this.m_nodeTests.length;
/*  32:    */       
/*  33: 75 */       this.m_nodeTests = new PredicatedNodeTest[len + 1];
/*  34:    */       
/*  35: 77 */       System.arraycopy(tests, 0, this.m_nodeTests, 0, len);
/*  36:    */       
/*  37: 79 */       this.m_nodeTests[len] = test;
/*  38:    */     }
/*  39: 81 */     test.exprSetParent(this);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  43:    */   {
/*  44: 96 */     super.fixupVariables(vars, globalsSize);
/*  45: 97 */     if (this.m_nodeTests != null) {
/*  46: 98 */       for (int i = 0; i < this.m_nodeTests.length; i++) {
/*  47: 99 */         this.m_nodeTests[i].fixupVariables(vars, globalsSize);
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public short acceptNode(int n)
/*  53:    */   {
/*  54:115 */     XPathContext xctxt = getXPathContext();
/*  55:    */     try
/*  56:    */     {
/*  57:118 */       xctxt.pushCurrentNode(n);
/*  58:119 */       for (int i = 0; i < this.m_nodeTests.length; i++)
/*  59:    */       {
/*  60:121 */         PredicatedNodeTest pnt = this.m_nodeTests[i];
/*  61:122 */         XObject score = pnt.execute(xctxt, n);
/*  62:123 */         if (score != NodeTest.SCORE_NONE)
/*  63:    */         {
/*  64:    */           short s;
/*  65:126 */           if (pnt.getPredicateCount() > 0)
/*  66:    */           {
/*  67:128 */             if (pnt.executePredicates(n, xctxt)) {
/*  68:129 */               return 1;
/*  69:    */             }
/*  70:    */           }
/*  71:    */           else {
/*  72:132 */             return 1;
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (TransformerException se)
/*  78:    */     {
/*  79:141 */       throw new RuntimeException(se.getMessage());
/*  80:    */     }
/*  81:    */     finally
/*  82:    */     {
/*  83:145 */       xctxt.popCurrentNode();
/*  84:    */     }
/*  85:147 */     return 3;
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.UnionChildIterator
 * JD-Core Version:    0.7.0.1
 */