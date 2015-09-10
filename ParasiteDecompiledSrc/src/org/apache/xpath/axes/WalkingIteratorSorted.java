/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.utils.PrefixResolver;
/*   6:    */ import org.apache.xpath.compiler.Compiler;
/*   7:    */ 
/*   8:    */ public class WalkingIteratorSorted
/*   9:    */   extends WalkingIterator
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = -4512512007542368213L;
/*  12: 40 */   protected boolean m_inNaturalOrderStatic = false;
/*  13:    */   
/*  14:    */   public WalkingIteratorSorted(PrefixResolver nscontext)
/*  15:    */   {
/*  16: 50 */     super(nscontext);
/*  17:    */   }
/*  18:    */   
/*  19:    */   WalkingIteratorSorted(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
/*  20:    */     throws TransformerException
/*  21:    */   {
/*  22: 72 */     super(compiler, opPos, analysis, shouldLoadWalkers);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean isDocOrdered()
/*  26:    */   {
/*  27: 83 */     return this.m_inNaturalOrderStatic;
/*  28:    */   }
/*  29:    */   
/*  30:    */   boolean canBeWalkedInNaturalDocOrderStatic()
/*  31:    */   {
/*  32: 96 */     if (null != this.m_firstWalker)
/*  33:    */     {
/*  34: 98 */       AxesWalker walker = this.m_firstWalker;
/*  35: 99 */       int prevAxis = -1;
/*  36:100 */       boolean prevIsSimpleDownAxis = true;
/*  37:102 */       for (int i = 0; null != walker; i++)
/*  38:    */       {
/*  39:104 */         int axis = walker.getAxis();
/*  40:106 */         if (walker.isDocOrdered())
/*  41:    */         {
/*  42:108 */           boolean isSimpleDownAxis = (axis == 3) || (axis == 13) || (axis == 19);
/*  43:113 */           if ((isSimpleDownAxis) || (axis == -1))
/*  44:    */           {
/*  45:114 */             walker = walker.getNextWalker();
/*  46:    */           }
/*  47:    */           else
/*  48:    */           {
/*  49:117 */             boolean isLastWalker = null == walker.getNextWalker();
/*  50:118 */             if (isLastWalker) {
/*  51:120 */               if (((walker.isDocOrdered()) && ((axis == 4) || (axis == 5) || (axis == 17) || (axis == 18))) || (axis == 2)) {
/*  52:123 */                 return true;
/*  53:    */               }
/*  54:    */             }
/*  55:125 */             return false;
/*  56:    */           }
/*  57:    */         }
/*  58:    */         else
/*  59:    */         {
/*  60:129 */           return false;
/*  61:    */         }
/*  62:    */       }
/*  63:131 */       return true;
/*  64:    */     }
/*  65:133 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void fixupVariables(Vector vars, int globalsSize)
/*  69:    */   {
/*  70:198 */     super.fixupVariables(vars, globalsSize);
/*  71:    */     
/*  72:200 */     int analysis = getAnalysisBits();
/*  73:201 */     if (WalkerFactory.isNaturalDocOrder(analysis)) {
/*  74:203 */       this.m_inNaturalOrderStatic = true;
/*  75:    */     } else {
/*  76:207 */       this.m_inNaturalOrderStatic = false;
/*  77:    */     }
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.WalkingIteratorSorted
 * JD-Core Version:    0.7.0.1
 */