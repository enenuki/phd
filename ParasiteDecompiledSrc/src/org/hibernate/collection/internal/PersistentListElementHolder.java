/*  1:   */ package org.hibernate.collection.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.List;
/*  5:   */ import org.dom4j.Element;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.persister.collection.CollectionPersister;
/*  9:   */ import org.hibernate.type.IntegerType;
/* 10:   */ import org.hibernate.type.Type;
/* 11:   */ 
/* 12:   */ public class PersistentListElementHolder
/* 13:   */   extends PersistentIndexedElementHolder
/* 14:   */ {
/* 15:   */   public PersistentListElementHolder(SessionImplementor session, Element element)
/* 16:   */   {
/* 17:43 */     super(session, element);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public PersistentListElementHolder(SessionImplementor session, CollectionPersister persister, Serializable key)
/* 21:   */     throws HibernateException
/* 22:   */   {
/* 23:48 */     super(session, persister, key);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 27:   */     throws HibernateException
/* 28:   */   {
/* 29:54 */     Type elementType = persister.getElementType();
/* 30:55 */     String indexNodeName = getIndexAttributeName(persister);
/* 31:56 */     Serializable[] cached = (Serializable[])disassembled;
/* 32:57 */     for (int i = 0; i < cached.length; i++)
/* 33:   */     {
/* 34:58 */       Object object = elementType.assemble(cached[i], getSession(), owner);
/* 35:59 */       Element subelement = this.element.addElement(persister.getElementNodeName());
/* 36:60 */       elementType.setToXMLNode(subelement, object, persister.getFactory());
/* 37:61 */       setIndex(subelement, indexNodeName, Integer.toString(i));
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public Serializable disassemble(CollectionPersister persister)
/* 42:   */     throws HibernateException
/* 43:   */   {
/* 44:68 */     Type elementType = persister.getElementType();
/* 45:69 */     String indexNodeName = getIndexAttributeName(persister);
/* 46:70 */     List elements = this.element.elements(persister.getElementNodeName());
/* 47:71 */     int length = elements.size();
/* 48:72 */     Serializable[] result = new Serializable[length];
/* 49:73 */     for (int i = 0; i < length; i++)
/* 50:   */     {
/* 51:74 */       Element elem = (Element)elements.get(i);
/* 52:75 */       Object object = elementType.fromXMLNode(elem, persister.getFactory());
/* 53:76 */       Integer index = (Integer)IntegerType.INSTANCE.fromString(getIndex(elem, indexNodeName, i));
/* 54:77 */       result[index.intValue()] = elementType.disassemble(object, getSession(), null);
/* 55:   */     }
/* 56:79 */     return result;
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentListElementHolder
 * JD-Core Version:    0.7.0.1
 */