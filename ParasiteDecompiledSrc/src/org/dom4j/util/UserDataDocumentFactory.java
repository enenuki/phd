/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import org.dom4j.Attribute;
/*  4:   */ import org.dom4j.DocumentFactory;
/*  5:   */ import org.dom4j.Element;
/*  6:   */ import org.dom4j.QName;
/*  7:   */ 
/*  8:   */ public class UserDataDocumentFactory
/*  9:   */   extends DocumentFactory
/* 10:   */ {
/* 11:30 */   protected static transient UserDataDocumentFactory singleton = new UserDataDocumentFactory();
/* 12:   */   
/* 13:   */   public static DocumentFactory getInstance()
/* 14:   */   {
/* 15:41 */     return singleton;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Element createElement(QName qname)
/* 19:   */   {
/* 20:47 */     return new UserDataElement(qname);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Attribute createAttribute(Element owner, QName qname, String value)
/* 24:   */   {
/* 25:51 */     return new UserDataAttribute(qname, value);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.UserDataDocumentFactory
 * JD-Core Version:    0.7.0.1
 */