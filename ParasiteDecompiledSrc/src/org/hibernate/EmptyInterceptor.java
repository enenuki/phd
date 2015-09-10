/*   1:    */ package org.hibernate;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.hibernate.type.Type;
/*   6:    */ 
/*   7:    */ public class EmptyInterceptor
/*   8:    */   implements Interceptor, Serializable
/*   9:    */ {
/*  10: 39 */   public static final Interceptor INSTANCE = new EmptyInterceptor();
/*  11:    */   
/*  12:    */   public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {}
/*  13:    */   
/*  14:    */   public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
/*  15:    */   {
/*  16: 57 */     return false;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
/*  20:    */   {
/*  21: 66 */     return false;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
/*  25:    */   {
/*  26: 75 */     return false;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void postFlush(Iterator entities) {}
/*  30:    */   
/*  31:    */   public void preFlush(Iterator entities) {}
/*  32:    */   
/*  33:    */   public Boolean isTransient(Object entity)
/*  34:    */   {
/*  35: 82 */     return null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object instantiate(String entityName, EntityMode entityMode, Serializable id)
/*  39:    */   {
/*  40: 86 */     return null;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types)
/*  44:    */   {
/*  45: 95 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getEntityName(Object object)
/*  49:    */   {
/*  50: 99 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object getEntity(String entityName, Serializable id)
/*  54:    */   {
/*  55:103 */     return null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void afterTransactionBegin(Transaction tx) {}
/*  59:    */   
/*  60:    */   public void afterTransactionCompletion(Transaction tx) {}
/*  61:    */   
/*  62:    */   public void beforeTransactionCompletion(Transaction tx) {}
/*  63:    */   
/*  64:    */   public String onPrepareStatement(String sql)
/*  65:    */   {
/*  66:111 */     return sql;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void onCollectionRemove(Object collection, Serializable key)
/*  70:    */     throws CallbackException
/*  71:    */   {}
/*  72:    */   
/*  73:    */   public void onCollectionRecreate(Object collection, Serializable key)
/*  74:    */     throws CallbackException
/*  75:    */   {}
/*  76:    */   
/*  77:    */   public void onCollectionUpdate(Object collection, Serializable key)
/*  78:    */     throws CallbackException
/*  79:    */   {}
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.EmptyInterceptor
 * JD-Core Version:    0.7.0.1
 */