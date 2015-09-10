/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.collection.spi.PersistentCollection;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  11:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  12:    */ import org.hibernate.usertype.LoggableUserType;
/*  13:    */ import org.hibernate.usertype.UserCollectionType;
/*  14:    */ 
/*  15:    */ public class CustomCollectionType
/*  16:    */   extends CollectionType
/*  17:    */ {
/*  18:    */   private final UserCollectionType userType;
/*  19:    */   private final boolean customLogging;
/*  20:    */   
/*  21:    */   public CustomCollectionType(TypeFactory.TypeScope typeScope, Class userTypeClass, String role, String foreignKeyPropertyName, boolean isEmbeddedInXML)
/*  22:    */   {
/*  23: 57 */     super(typeScope, role, foreignKeyPropertyName, isEmbeddedInXML);
/*  24: 59 */     if (!UserCollectionType.class.isAssignableFrom(userTypeClass)) {
/*  25: 60 */       throw new MappingException("Custom type does not implement UserCollectionType: " + userTypeClass.getName());
/*  26:    */     }
/*  27:    */     try
/*  28:    */     {
/*  29: 64 */       this.userType = ((UserCollectionType)userTypeClass.newInstance());
/*  30:    */     }
/*  31:    */     catch (InstantiationException ie)
/*  32:    */     {
/*  33: 67 */       throw new MappingException("Cannot instantiate custom type: " + userTypeClass.getName());
/*  34:    */     }
/*  35:    */     catch (IllegalAccessException iae)
/*  36:    */     {
/*  37: 70 */       throw new MappingException("IllegalAccessException trying to instantiate custom type: " + userTypeClass.getName());
/*  38:    */     }
/*  39: 73 */     this.customLogging = LoggableUserType.class.isAssignableFrom(userTypeClass);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public PersistentCollection instantiate(SessionImplementor session, CollectionPersister persister, Serializable key)
/*  43:    */     throws HibernateException
/*  44:    */   {
/*  45: 78 */     return this.userType.instantiate(session, persister);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public PersistentCollection wrap(SessionImplementor session, Object collection)
/*  49:    */   {
/*  50: 82 */     return this.userType.wrap(session, collection);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Class getReturnedClass()
/*  54:    */   {
/*  55: 86 */     return this.userType.instantiate(-1).getClass();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Object instantiate(int anticipatedType)
/*  59:    */   {
/*  60: 90 */     return this.userType.instantiate(anticipatedType);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Iterator getElementsIterator(Object collection)
/*  64:    */   {
/*  65: 94 */     return this.userType.getElementsIterator(collection);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean contains(Object collection, Object entity, SessionImplementor session)
/*  69:    */   {
/*  70: 97 */     return this.userType.contains(collection, entity);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Object indexOf(Object collection, Object entity)
/*  74:    */   {
/*  75:100 */     return this.userType.indexOf(collection, entity);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object replaceElements(Object original, Object target, Object owner, Map copyCache, SessionImplementor session)
/*  79:    */     throws HibernateException
/*  80:    */   {
/*  81:105 */     CollectionPersister cp = session.getFactory().getCollectionPersister(getRole());
/*  82:106 */     return this.userType.replaceElements(original, target, cp, owner, copyCache, session);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected String renderLoggableString(Object value, SessionFactoryImplementor factory)
/*  86:    */     throws HibernateException
/*  87:    */   {
/*  88:110 */     if (this.customLogging) {
/*  89:111 */       return ((LoggableUserType)this.userType).toLoggableString(value, factory);
/*  90:    */     }
/*  91:114 */     return super.renderLoggableString(value, factory);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public UserCollectionType getUserType()
/*  95:    */   {
/*  96:119 */     return this.userType;
/*  97:    */   }
/*  98:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CustomCollectionType
 * JD-Core Version:    0.7.0.1
 */