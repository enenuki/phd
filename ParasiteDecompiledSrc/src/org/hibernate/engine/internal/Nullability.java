/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.EntityMode;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.PropertyValueException;
/*   7:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   8:    */ import org.hibernate.cfg.Settings;
/*   9:    */ import org.hibernate.engine.spi.CascadingAction;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.persister.entity.EntityPersister;
/*  13:    */ import org.hibernate.type.CollectionType;
/*  14:    */ import org.hibernate.type.CompositeType;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ 
/*  17:    */ public final class Nullability
/*  18:    */ {
/*  19:    */   private final SessionImplementor session;
/*  20:    */   private final boolean checkNullability;
/*  21:    */   
/*  22:    */   public Nullability(SessionImplementor session)
/*  23:    */   {
/*  24: 50 */     this.session = session;
/*  25: 51 */     this.checkNullability = session.getFactory().getSettings().isCheckNullability();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void checkNullability(Object[] values, EntityPersister persister, boolean isUpdate)
/*  29:    */     throws PropertyValueException, HibernateException
/*  30:    */   {
/*  31: 71 */     if (this.checkNullability)
/*  32:    */     {
/*  33: 90 */       boolean[] nullability = persister.getPropertyNullability();
/*  34: 91 */       boolean[] checkability = isUpdate ? persister.getPropertyUpdateability() : persister.getPropertyInsertability();
/*  35:    */       
/*  36:    */ 
/*  37: 94 */       Type[] propertyTypes = persister.getPropertyTypes();
/*  38: 96 */       for (int i = 0; i < values.length; i++) {
/*  39: 98 */         if ((checkability[i] != 0) && (values[i] != LazyPropertyInitializer.UNFETCHED_PROPERTY))
/*  40:    */         {
/*  41: 99 */           Object value = values[i];
/*  42:100 */           if ((nullability[i] == 0) && (value == null)) {
/*  43:103 */             throw new PropertyValueException("not-null property references a null or transient value", persister.getEntityName(), persister.getPropertyNames()[i]);
/*  44:    */           }
/*  45:110 */           if (value != null)
/*  46:    */           {
/*  47:113 */             String breakProperties = checkSubElementsNullability(propertyTypes[i], value);
/*  48:114 */             if (breakProperties != null) {
/*  49:115 */               throw new PropertyValueException("not-null property references a null or transient value", persister.getEntityName(), buildPropertyPath(persister.getPropertyNames()[i], breakProperties));
/*  50:    */             }
/*  51:    */           }
/*  52:    */         }
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   private String checkSubElementsNullability(Type propertyType, Object value)
/*  58:    */     throws HibernateException
/*  59:    */   {
/*  60:142 */     if (propertyType.isComponentType()) {
/*  61:143 */       return checkComponentNullability(value, (CompositeType)propertyType);
/*  62:    */     }
/*  63:145 */     if (propertyType.isCollectionType())
/*  64:    */     {
/*  65:148 */       CollectionType collectionType = (CollectionType)propertyType;
/*  66:149 */       Type collectionElementType = collectionType.getElementType(this.session.getFactory());
/*  67:150 */       if (collectionElementType.isComponentType())
/*  68:    */       {
/*  69:153 */         CompositeType componentType = (CompositeType)collectionElementType;
/*  70:154 */         Iterator iter = CascadingAction.getLoadedElementsIterator(this.session, collectionType, value);
/*  71:155 */         while (iter.hasNext())
/*  72:    */         {
/*  73:156 */           Object compValue = iter.next();
/*  74:157 */           if (compValue != null) {
/*  75:158 */             return checkComponentNullability(compValue, componentType);
/*  76:    */           }
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:163 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private String checkComponentNullability(Object value, CompositeType compType)
/*  84:    */     throws HibernateException
/*  85:    */   {
/*  86:181 */     boolean[] nullability = compType.getPropertyNullability();
/*  87:182 */     if (nullability != null)
/*  88:    */     {
/*  89:184 */       Object[] values = compType.getPropertyValues(value, EntityMode.POJO);
/*  90:185 */       Type[] propertyTypes = compType.getSubtypes();
/*  91:186 */       for (int i = 0; i < values.length; i++)
/*  92:    */       {
/*  93:187 */         Object subvalue = values[i];
/*  94:188 */         if ((nullability[i] == 0) && (subvalue == null)) {
/*  95:189 */           return compType.getPropertyNames()[i];
/*  96:    */         }
/*  97:191 */         if (subvalue != null)
/*  98:    */         {
/*  99:192 */           String breakProperties = checkSubElementsNullability(propertyTypes[i], subvalue);
/* 100:193 */           if (breakProperties != null) {
/* 101:194 */             return buildPropertyPath(compType.getPropertyNames()[i], breakProperties);
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:199 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private static String buildPropertyPath(String parent, String child)
/* 110:    */   {
/* 111:211 */     return parent.length() + child.length() + 1 + parent + '.' + child;
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.Nullability
 * JD-Core Version:    0.7.0.1
 */