/*   1:    */ package org.hibernate.property;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Member;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.PropertyNotFoundException;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ 
/*  11:    */ public class EmbeddedPropertyAccessor
/*  12:    */   implements PropertyAccessor
/*  13:    */ {
/*  14:    */   public static final class EmbeddedGetter
/*  15:    */     implements Getter
/*  16:    */   {
/*  17:    */     private final Class clazz;
/*  18:    */     
/*  19:    */     EmbeddedGetter(Class clazz)
/*  20:    */     {
/*  21: 43 */       this.clazz = clazz;
/*  22:    */     }
/*  23:    */     
/*  24:    */     public Object get(Object target)
/*  25:    */       throws HibernateException
/*  26:    */     {
/*  27: 50 */       return target;
/*  28:    */     }
/*  29:    */     
/*  30:    */     public Object getForInsert(Object target, Map mergeMap, SessionImplementor session)
/*  31:    */     {
/*  32: 57 */       return get(target);
/*  33:    */     }
/*  34:    */     
/*  35:    */     public Member getMember()
/*  36:    */     {
/*  37: 64 */       return null;
/*  38:    */     }
/*  39:    */     
/*  40:    */     public Method getMethod()
/*  41:    */     {
/*  42: 71 */       return null;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public String getMethodName()
/*  46:    */     {
/*  47: 78 */       return null;
/*  48:    */     }
/*  49:    */     
/*  50:    */     public Class getReturnType()
/*  51:    */     {
/*  52: 85 */       return this.clazz;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public String toString()
/*  56:    */     {
/*  57: 89 */       return "EmbeddedGetter(" + this.clazz.getName() + ')';
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static final class EmbeddedSetter
/*  62:    */     implements Setter
/*  63:    */   {
/*  64:    */     private final Class clazz;
/*  65:    */     
/*  66:    */     EmbeddedSetter(Class clazz)
/*  67:    */     {
/*  68: 97 */       this.clazz = clazz;
/*  69:    */     }
/*  70:    */     
/*  71:    */     public Method getMethod()
/*  72:    */     {
/*  73:104 */       return null;
/*  74:    */     }
/*  75:    */     
/*  76:    */     public String getMethodName()
/*  77:    */     {
/*  78:111 */       return null;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public void set(Object target, Object value, SessionFactoryImplementor factory) {}
/*  82:    */     
/*  83:    */     public String toString()
/*  84:    */     {
/*  85:124 */       return "EmbeddedSetter(" + this.clazz.getName() + ')';
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Getter getGetter(Class theClass, String propertyName)
/*  90:    */     throws PropertyNotFoundException
/*  91:    */   {
/*  92:130 */     return new EmbeddedGetter(theClass);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Setter getSetter(Class theClass, String propertyName)
/*  96:    */     throws PropertyNotFoundException
/*  97:    */   {
/*  98:135 */     return new EmbeddedSetter(theClass);
/*  99:    */   }
/* 100:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.EmbeddedPropertyAccessor
 * JD-Core Version:    0.7.0.1
 */