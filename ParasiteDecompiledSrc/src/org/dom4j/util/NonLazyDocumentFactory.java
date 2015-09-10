/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import org.dom4j.DocumentFactory;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.dom4j.QName;
/*  6:   */ 
/*  7:   */ public class NonLazyDocumentFactory
/*  8:   */   extends DocumentFactory
/*  9:   */ {
/* 10:27 */   protected static transient NonLazyDocumentFactory singleton = new NonLazyDocumentFactory();
/* 11:   */   
/* 12:   */   public static DocumentFactory getInstance()
/* 13:   */   {
/* 14:38 */     return singleton;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Element createElement(QName qname)
/* 18:   */   {
/* 19:44 */     return new NonLazyElement(qname);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.NonLazyDocumentFactory
 * JD-Core Version:    0.7.0.1
 */