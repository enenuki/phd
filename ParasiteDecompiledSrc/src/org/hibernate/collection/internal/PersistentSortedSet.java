/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Set;
/*   7:    */ import java.util.SortedSet;
/*   8:    */ import java.util.TreeMap;
/*   9:    */ import org.hibernate.EntityMode;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.persister.collection.BasicCollectionPersister;
/*  13:    */ import org.hibernate.type.Type;
/*  14:    */ 
/*  15:    */ public class PersistentSortedSet
/*  16:    */   extends PersistentSet
/*  17:    */   implements SortedSet
/*  18:    */ {
/*  19:    */   protected Comparator comparator;
/*  20:    */   
/*  21:    */   protected Serializable snapshot(BasicCollectionPersister persister, EntityMode entityMode)
/*  22:    */     throws HibernateException
/*  23:    */   {
/*  24: 51 */     TreeMap clonedSet = new TreeMap(this.comparator);
/*  25: 52 */     Iterator iter = this.set.iterator();
/*  26: 53 */     while (iter.hasNext())
/*  27:    */     {
/*  28: 54 */       Object copy = persister.getElementType().deepCopy(iter.next(), persister.getFactory());
/*  29: 55 */       clonedSet.put(copy, copy);
/*  30:    */     }
/*  31: 57 */     return clonedSet;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setComparator(Comparator comparator)
/*  35:    */   {
/*  36: 61 */     this.comparator = comparator;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public PersistentSortedSet(SessionImplementor session)
/*  40:    */   {
/*  41: 65 */     super(session);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public PersistentSortedSet(SessionImplementor session, SortedSet set)
/*  45:    */   {
/*  46: 69 */     super(session, set);
/*  47: 70 */     this.comparator = set.comparator();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PersistentSortedSet() {}
/*  51:    */   
/*  52:    */   public Comparator comparator()
/*  53:    */   {
/*  54: 79 */     return this.comparator;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public SortedSet subSet(Object fromElement, Object toElement)
/*  58:    */   {
/*  59: 86 */     read();
/*  60:    */     
/*  61: 88 */     SortedSet s = ((SortedSet)this.set).subSet(fromElement, toElement);
/*  62: 89 */     return new SubSetProxy(s);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public SortedSet headSet(Object toElement)
/*  66:    */   {
/*  67: 96 */     read();
/*  68: 97 */     SortedSet s = ((SortedSet)this.set).headSet(toElement);
/*  69: 98 */     return new SubSetProxy(s);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public SortedSet tailSet(Object fromElement)
/*  73:    */   {
/*  74:105 */     read();
/*  75:106 */     SortedSet s = ((SortedSet)this.set).tailSet(fromElement);
/*  76:107 */     return new SubSetProxy(s);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Object first()
/*  80:    */   {
/*  81:114 */     read();
/*  82:115 */     return ((SortedSet)this.set).first();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Object last()
/*  86:    */   {
/*  87:122 */     read();
/*  88:123 */     return ((SortedSet)this.set).last();
/*  89:    */   }
/*  90:    */   
/*  91:    */   class SubSetProxy
/*  92:    */     extends AbstractPersistentCollection.SetProxy
/*  93:    */     implements SortedSet
/*  94:    */   {
/*  95:    */     SubSetProxy(SortedSet s)
/*  96:    */     {
/*  97:130 */       super(s);
/*  98:    */     }
/*  99:    */     
/* 100:    */     public Comparator comparator()
/* 101:    */     {
/* 102:134 */       return ((SortedSet)this.set).comparator();
/* 103:    */     }
/* 104:    */     
/* 105:    */     public Object first()
/* 106:    */     {
/* 107:138 */       return ((SortedSet)this.set).first();
/* 108:    */     }
/* 109:    */     
/* 110:    */     public SortedSet headSet(Object toValue)
/* 111:    */     {
/* 112:142 */       return new SubSetProxy(PersistentSortedSet.this, ((SortedSet)this.set).headSet(toValue));
/* 113:    */     }
/* 114:    */     
/* 115:    */     public Object last()
/* 116:    */     {
/* 117:146 */       return ((SortedSet)this.set).last();
/* 118:    */     }
/* 119:    */     
/* 120:    */     public SortedSet subSet(Object fromValue, Object toValue)
/* 121:    */     {
/* 122:150 */       return new SubSetProxy(PersistentSortedSet.this, ((SortedSet)this.set).subSet(fromValue, toValue));
/* 123:    */     }
/* 124:    */     
/* 125:    */     public SortedSet tailSet(Object fromValue)
/* 126:    */     {
/* 127:154 */       return new SubSetProxy(PersistentSortedSet.this, ((SortedSet)this.set).tailSet(fromValue));
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentSortedSet
 * JD-Core Version:    0.7.0.1
 */