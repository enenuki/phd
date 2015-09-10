/*  1:   */ package org.dom4j.util;
/*  2:   */ 
/*  3:   */ import org.dom4j.Namespace;
/*  4:   */ import org.dom4j.QName;
/*  5:   */ import org.dom4j.tree.BaseElement;
/*  6:   */ 
/*  7:   */ public class NonLazyElement
/*  8:   */   extends BaseElement
/*  9:   */ {
/* 10:   */   public NonLazyElement(String name)
/* 11:   */   {
/* 12:25 */     super(name);
/* 13:26 */     this.attributes = createAttributeList();
/* 14:27 */     this.content = createContentList();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public NonLazyElement(QName qname)
/* 18:   */   {
/* 19:31 */     super(qname);
/* 20:32 */     this.attributes = createAttributeList();
/* 21:33 */     this.content = createContentList();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public NonLazyElement(String name, Namespace namespace)
/* 25:   */   {
/* 26:37 */     super(name, namespace);
/* 27:38 */     this.attributes = createAttributeList();
/* 28:39 */     this.content = createContentList();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public NonLazyElement(QName qname, int attributeCount)
/* 32:   */   {
/* 33:43 */     super(qname);
/* 34:44 */     this.attributes = createAttributeList(attributeCount);
/* 35:45 */     this.content = createContentList();
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.NonLazyElement
 * JD-Core Version:    0.7.0.1
 */