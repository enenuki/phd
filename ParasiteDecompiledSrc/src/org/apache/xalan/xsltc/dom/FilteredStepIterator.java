/*  1:   */ package org.apache.xalan.xsltc.dom;
/*  2:   */ 
/*  3:   */ import org.apache.xml.dtm.DTMAxisIterator;
/*  4:   */ import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
/*  5:   */ 
/*  6:   */ public final class FilteredStepIterator
/*  7:   */   extends StepIterator
/*  8:   */ {
/*  9:   */   private Filter _filter;
/* 10:   */   
/* 11:   */   public FilteredStepIterator(DTMAxisIterator source, DTMAxisIterator iterator, Filter filter)
/* 12:   */   {
/* 13:40 */     super(source, iterator);
/* 14:41 */     this._filter = filter;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int next()
/* 18:   */   {
/* 19:   */     int node;
/* 20:46 */     while ((node = super.next()) != -1)
/* 21:   */     {
/* 22:   */       int i;
/* 23:47 */       if (this._filter.test(i)) {
/* 24:48 */         return returnNode(i);
/* 25:   */       }
/* 26:   */     }
/* 27:51 */     return node;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.FilteredStepIterator
 * JD-Core Version:    0.7.0.1
 */