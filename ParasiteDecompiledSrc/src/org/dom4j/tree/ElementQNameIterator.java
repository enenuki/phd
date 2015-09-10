/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.dom4j.QName;
/*  6:   */ 
/*  7:   */ /**
/*  8:   */  * @deprecated
/*  9:   */  */
/* 10:   */ public class ElementQNameIterator
/* 11:   */   extends FilterIterator
/* 12:   */ {
/* 13:   */   private QName qName;
/* 14:   */   
/* 15:   */   public ElementQNameIterator(Iterator proxy, QName qName)
/* 16:   */   {
/* 17:31 */     super(proxy);
/* 18:32 */     this.qName = qName;
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected boolean matches(Object object)
/* 22:   */   {
/* 23:45 */     if ((object instanceof Element))
/* 24:   */     {
/* 25:46 */       Element element = (Element)object;
/* 26:   */       
/* 27:48 */       return this.qName.equals(element.getQName());
/* 28:   */     }
/* 29:51 */     return false;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.ElementQNameIterator
 * JD-Core Version:    0.7.0.1
 */