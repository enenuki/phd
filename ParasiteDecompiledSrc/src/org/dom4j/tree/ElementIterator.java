/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public class ElementIterator
/* 10:   */   extends FilterIterator
/* 11:   */ {
/* 12:   */   public ElementIterator(Iterator proxy)
/* 13:   */   {
/* 14:27 */     super(proxy);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected boolean matches(Object element)
/* 18:   */   {
/* 19:40 */     return element instanceof Element;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.ElementIterator
 * JD-Core Version:    0.7.0.1
 */