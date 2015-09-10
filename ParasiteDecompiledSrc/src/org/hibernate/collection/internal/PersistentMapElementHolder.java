/*  1:   */ package org.hibernate.collection.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.List;
/*  5:   */ import org.dom4j.Element;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  9:   */ import org.hibernate.type.Type;
/* 10:   */ import org.hibernate.type.XmlRepresentableType;
/* 11:   */ 
/* 12:   */ public class PersistentMapElementHolder
/* 13:   */   extends PersistentIndexedElementHolder
/* 14:   */ {
/* 15:   */   public PersistentMapElementHolder(SessionImplementor session, Element element)
/* 16:   */   {
/* 17:43 */     super(session, element);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public PersistentMapElementHolder(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 21:   */     throws HibernateException
/* 22:   */   {
/* 23:48 */     super(session, persister, key);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 27:   */     throws HibernateException
/* 28:   */   {
/* 29:54 */     Type elementType = persister.getElementType();
/* 30:55 */     Type indexType = persister.getIndexType();
/* 31:56 */     String indexNodeName = getIndexAttributeName(persister);
/* 32:   */     
/* 33:58 */     Serializable[] cached = (Serializable[])disassembled;
/* 34:60 */     for (int i = 0; i < cached.length;)
/* 35:   */     {
/* 36:61 */       Object index = indexType.assemble(cached[(i++)], getSession(), owner);
/* 37:62 */       Object object = elementType.assemble(cached[(i++)], getSession(), owner);
/* 38:   */       
/* 39:64 */       Element subelement = this.element.addElement(persister.getElementNodeName());
/* 40:65 */       elementType.setToXMLNode(subelement, object, persister.getFactory());
/* 41:   */       
/* 42:67 */       String indexString = ((XmlRepresentableType)indexType).toXMLString(index, persister.getFactory());
/* 43:68 */       setIndex(subelement, indexNodeName, indexString);
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Serializable disassemble(CollectionPersister persister)
/* 48:   */     throws HibernateException
/* 49:   */   {
/* 50:75 */     Type elementType = persister.getElementType();
/* 51:76 */     Type indexType = persister.getIndexType();
/* 52:77 */     String indexNodeName = getIndexAttributeName(persister);
/* 53:   */     
/* 54:79 */     List elements = this.element.elements(persister.getElementNodeName());
/* 55:80 */     int length = elements.size();
/* 56:81 */     Serializable[] result = new Serializable[length * 2];
/* 57:82 */     for (int i = 0; i < length * 2;)
/* 58:   */     {
/* 59:83 */       Element elem = (Element)elements.get(i / 2);
/* 60:84 */       Object object = elementType.fromXMLNode(elem, persister.getFactory());
/* 61:85 */       String indexString = getIndex(elem, indexNodeName, i);
/* 62:86 */       Object index = ((XmlRepresentableType)indexType).fromXMLString(indexString, persister.getFactory());
/* 63:87 */       result[(i++)] = indexType.disassemble(index, getSession(), null);
/* 64:88 */       result[(i++)] = elementType.disassemble(object, getSession(), null);
/* 65:   */     }
/* 66:90 */     return result;
/* 67:   */   }
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentMapElementHolder
 * JD-Core Version:    0.7.0.1
 */