/*   1:    */ package org.hibernate.property;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Member;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ 
/*  11:    */ public class IndexPropertyAccessor
/*  12:    */   implements PropertyAccessor
/*  13:    */ {
/*  14:    */   private final String propertyName;
/*  15:    */   private final String entityName;
/*  16:    */   
/*  17:    */   public IndexPropertyAccessor(String collectionRole, String entityName)
/*  18:    */   {
/*  19: 49 */     this.propertyName = collectionRole.substring(entityName.length() + 1);
/*  20: 50 */     this.entityName = entityName;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Setter getSetter(Class theClass, String propertyName)
/*  24:    */   {
/*  25: 54 */     return new IndexSetter();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Getter getGetter(Class theClass, String propertyName)
/*  29:    */   {
/*  30: 58 */     return new IndexGetter();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static final class IndexSetter
/*  34:    */     implements Setter
/*  35:    */   {
/*  36:    */     public Method getMethod()
/*  37:    */     {
/*  38: 70 */       return null;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public String getMethodName()
/*  42:    */     {
/*  43: 77 */       return null;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public void set(Object target, Object value, SessionFactoryImplementor factory) {}
/*  47:    */   }
/*  48:    */   
/*  49:    */   public class IndexGetter
/*  50:    */     implements Getter
/*  51:    */   {
/*  52:    */     public IndexGetter() {}
/*  53:    */     
/*  54:    */     public Object getForInsert(Object target, Map mergeMap, SessionImplementor session)
/*  55:    */       throws HibernateException
/*  56:    */     {
/*  57: 95 */       if (session == null) {
/*  58: 96 */         return BackrefPropertyAccessor.UNKNOWN;
/*  59:    */       }
/*  60: 99 */       return session.getPersistenceContext().getIndexInOwner(IndexPropertyAccessor.this.entityName, IndexPropertyAccessor.this.propertyName, target, mergeMap);
/*  61:    */     }
/*  62:    */     
/*  63:    */     public Object get(Object target)
/*  64:    */     {
/*  65:108 */       return BackrefPropertyAccessor.UNKNOWN;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public Member getMember()
/*  69:    */     {
/*  70:115 */       return null;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public Method getMethod()
/*  74:    */     {
/*  75:122 */       return null;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public String getMethodName()
/*  79:    */     {
/*  80:129 */       return null;
/*  81:    */     }
/*  82:    */     
/*  83:    */     public Class getReturnType()
/*  84:    */     {
/*  85:136 */       return Object.class;
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.IndexPropertyAccessor
 * JD-Core Version:    0.7.0.1
 */