/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Array;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.collection.internal.PersistentArrayHolder;
/*  12:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  16:    */ 
/*  17:    */ public class ArrayType
/*  18:    */   extends CollectionType
/*  19:    */ {
/*  20:    */   private final Class elementClass;
/*  21:    */   private final Class arrayClass;
/*  22:    */   
/*  23:    */   public ArrayType(TypeFactory.TypeScope typeScope, String role, String propertyRef, Class elementClass, boolean isEmbeddedInXML)
/*  24:    */   {
/*  25: 51 */     super(typeScope, role, propertyRef, isEmbeddedInXML);
/*  26: 52 */     this.elementClass = elementClass;
/*  27: 53 */     this.arrayClass = Array.newInstance(elementClass, 0).getClass();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Class getReturnedClass()
/*  31:    */   {
/*  32: 57 */     return this.arrayClass;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/*  36:    */     throws HibernateException
/*  37:    */   {
/*  38: 62 */     return new PersistentArrayHolder(session, persister);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Iterator getElementsIterator(Object collection)
/*  42:    */   {
/*  43: 69 */     return Arrays.asList((Object[])collection).iterator();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public PersistentCollection wrap(SessionImplementor session, Object array)
/*  47:    */   {
/*  48: 73 */     return new PersistentArrayHolder(session, array);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isArrayType()
/*  52:    */   {
/*  53: 77 */     return true;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/*  57:    */     throws HibernateException
/*  58:    */   {
/*  59: 81 */     if (value == null) {
/*  60: 82 */       return "null";
/*  61:    */     }
/*  62: 84 */     int length = Array.getLength(value);
/*  63: 85 */     List list = new ArrayList(length);
/*  64: 86 */     Type elemType = getElementType(factory);
/*  65: 87 */     for (int i = 0; i < length; i++) {
/*  66: 88 */       list.add(elemType.toLoggableString(Array.get(value, i), factory));
/*  67:    */     }
/*  68: 90 */     return list.toString();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object instantiateResult(Object original)
/*  72:    */   {
/*  73: 94 */     return Array.newInstance(this.elementClass, Array.getLength(original));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object replaceElements(Object original, Object target, Object owner, Map copyCache, SessionImplementor session)
/*  77:    */     throws HibernateException
/*  78:    */   {
/*  79:105 */     int length = Array.getLength(original);
/*  80:106 */     if (length != Array.getLength(target)) {
/*  81:108 */       target = instantiateResult(original);
/*  82:    */     }
/*  83:111 */     Type elemType = getElementType(session.getFactory());
/*  84:112 */     for (int i = 0; i < length; i++) {
/*  85:113 */       Array.set(target, i, elemType.replace(Array.get(original, i), null, session, owner, copyCache));
/*  86:    */     }
/*  87:116 */     return target;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object instantiate(int anticipatedSize)
/*  91:    */   {
/*  92:121 */     throw new UnsupportedOperationException();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Object indexOf(Object array, Object element)
/*  96:    */   {
/*  97:125 */     int length = Array.getLength(array);
/*  98:126 */     for (int i = 0; i < length; i++) {
/*  99:128 */       if (Array.get(array, i) == element) {
/* 100:128 */         return Integer.valueOf(i);
/* 101:    */       }
/* 102:    */     }
/* 103:130 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   protected boolean initializeImmediately()
/* 107:    */   {
/* 108:135 */     return true;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean hasHolder()
/* 112:    */   {
/* 113:140 */     return true;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ArrayType
 * JD-Core Version:    0.7.0.1
 */