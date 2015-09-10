/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import org.dom4j.DocumentFactory;
/*  4:   */ import org.dom4j.Namespace;
/*  5:   */ import org.dom4j.QName;
/*  6:   */ 
/*  7:   */ public class FlyweightAttribute
/*  8:   */   extends AbstractAttribute
/*  9:   */ {
/* 10:   */   private QName qname;
/* 11:   */   protected String value;
/* 12:   */   
/* 13:   */   public FlyweightAttribute(QName qname)
/* 14:   */   {
/* 15:35 */     this.qname = qname;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public FlyweightAttribute(QName qname, String value)
/* 19:   */   {
/* 20:39 */     this.qname = qname;
/* 21:40 */     this.value = value;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public FlyweightAttribute(String name, String value)
/* 25:   */   {
/* 26:53 */     this.qname = getDocumentFactory().createQName(name);
/* 27:54 */     this.value = value;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public FlyweightAttribute(String name, String value, Namespace namespace)
/* 31:   */   {
/* 32:69 */     this.qname = getDocumentFactory().createQName(name, namespace);
/* 33:70 */     this.value = value;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getValue()
/* 37:   */   {
/* 38:74 */     return this.value;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public QName getQName()
/* 42:   */   {
/* 43:78 */     return this.qname;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.FlyweightAttribute
 * JD-Core Version:    0.7.0.1
 */