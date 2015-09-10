/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public class ElementNameIterator
/* 10:   */   extends FilterIterator
/* 11:   */ {
/* 12:   */   private String name;
/* 13:   */   
/* 14:   */   public ElementNameIterator(Iterator proxy, String name)
/* 15:   */   {
/* 16:30 */     super(proxy);
/* 17:31 */     this.name = name;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected boolean matches(Object object)
/* 21:   */   {
/* 22:44 */     if ((object instanceof Element))
/* 23:   */     {
/* 24:45 */       Element element = (Element)object;
/* 25:   */       
/* 26:47 */       return this.name.equals(element.getName());
/* 27:   */     }
/* 28:50 */     return false;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.ElementNameIterator
 * JD-Core Version:    0.7.0.1
 */