/*   1:    */ package org.apache.xpath.patterns;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   6:    */ import org.apache.xpath.XPathContext;
/*   7:    */ import org.apache.xpath.axes.WalkerFactory;
/*   8:    */ import org.apache.xpath.objects.XObject;
/*   9:    */ 
/*  10:    */ public class ContextMatchStepPattern
/*  11:    */   extends StepPattern
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -1888092779313211942L;
/*  14:    */   
/*  15:    */   public ContextMatchStepPattern(int axis, int paxis)
/*  16:    */   {
/*  17: 43 */     super(-1, axis, paxis);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public XObject execute(XPathContext xctxt)
/*  21:    */     throws TransformerException
/*  22:    */   {
/*  23: 64 */     if (xctxt.getIteratorRoot() == xctxt.getCurrentNode()) {
/*  24: 65 */       return getStaticScore();
/*  25:    */     }
/*  26: 67 */     return NodeTest.SCORE_NONE;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public XObject executeRelativePathPattern(XPathContext xctxt, StepPattern prevStep)
/*  30:    */     throws TransformerException
/*  31:    */   {
/*  32: 90 */     XObject score = NodeTest.SCORE_NONE;
/*  33: 91 */     int context = xctxt.getCurrentNode();
/*  34: 92 */     DTM dtm = xctxt.getDTM(context);
/*  35: 94 */     if (null != dtm)
/*  36:    */     {
/*  37: 96 */       int predContext = xctxt.getCurrentNode();
/*  38:    */       
/*  39:    */ 
/*  40: 99 */       int axis = this.m_axis;
/*  41:    */       
/*  42:101 */       boolean needToTraverseAttrs = WalkerFactory.isDownwardAxisOfMany(axis);
/*  43:102 */       boolean iterRootIsAttr = dtm.getNodeType(xctxt.getIteratorRoot()) == 2;
/*  44:105 */       if ((11 == axis) && (iterRootIsAttr)) {
/*  45:107 */         axis = 15;
/*  46:    */       }
/*  47:110 */       DTMAxisTraverser traverser = dtm.getAxisTraverser(axis);
/*  48:112 */       for (int relative = traverser.first(context); -1 != relative; relative = traverser.next(context, relative)) {
/*  49:    */         try
/*  50:    */         {
/*  51:117 */           xctxt.pushCurrentNode(relative);
/*  52:    */           
/*  53:119 */           score = execute(xctxt);
/*  54:121 */           if (score != NodeTest.SCORE_NONE)
/*  55:    */           {
/*  56:125 */             if (executePredicates(xctxt, dtm, context)) {
/*  57:126 */               return score;
/*  58:    */             }
/*  59:128 */             score = NodeTest.SCORE_NONE;
/*  60:    */           }
/*  61:131 */           if ((needToTraverseAttrs) && (iterRootIsAttr) && (1 == dtm.getNodeType(relative)))
/*  62:    */           {
/*  63:134 */             int xaxis = 2;
/*  64:135 */             for (int i = 0; i < 2; i++)
/*  65:    */             {
/*  66:137 */               DTMAxisTraverser atraverser = dtm.getAxisTraverser(xaxis);
/*  67:139 */               for (int arelative = atraverser.first(relative); -1 != arelative; arelative = atraverser.next(relative, arelative)) {
/*  68:    */                 try
/*  69:    */                 {
/*  70:145 */                   xctxt.pushCurrentNode(arelative);
/*  71:    */                   
/*  72:147 */                   score = execute(xctxt);
/*  73:149 */                   if (score != NodeTest.SCORE_NONE) {
/*  74:154 */                     if (score != NodeTest.SCORE_NONE) {
/*  75:155 */                       return score;
/*  76:    */                     }
/*  77:    */                   }
/*  78:    */                 }
/*  79:    */                 finally
/*  80:    */                 {
/*  81:160 */                   xctxt.popCurrentNode();
/*  82:    */                 }
/*  83:    */               }
/*  84:163 */               xaxis = 9;
/*  85:    */             }
/*  86:    */           }
/*  87:    */         }
/*  88:    */         finally
/*  89:    */         {
/*  90:170 */           xctxt.popCurrentNode();
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:176 */     return score;
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.patterns.ContextMatchStepPattern
 * JD-Core Version:    0.7.0.1
 */