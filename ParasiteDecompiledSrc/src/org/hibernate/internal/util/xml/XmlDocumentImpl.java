/*  1:   */ package org.hibernate.internal.util.xml;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.dom4j.Document;
/*  5:   */ 
/*  6:   */ public class XmlDocumentImpl
/*  7:   */   implements XmlDocument, Serializable
/*  8:   */ {
/*  9:   */   private final Document documentTree;
/* 10:   */   private final Origin origin;
/* 11:   */   
/* 12:   */   public XmlDocumentImpl(Document documentTree, String originType, String originName)
/* 13:   */   {
/* 14:40 */     this(documentTree, new OriginImpl(originType, originName));
/* 15:   */   }
/* 16:   */   
/* 17:   */   public XmlDocumentImpl(Document documentTree, Origin origin)
/* 18:   */   {
/* 19:44 */     this.documentTree = documentTree;
/* 20:45 */     this.origin = origin;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Document getDocumentTree()
/* 24:   */   {
/* 25:52 */     return this.documentTree;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Origin getOrigin()
/* 29:   */   {
/* 30:59 */     return this.origin;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.xml.XmlDocumentImpl
 * JD-Core Version:    0.7.0.1
 */