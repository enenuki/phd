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
/*  11:    */ public class MapAccessor
/*  12:    */   implements PropertyAccessor
/*  13:    */ {
/*  14:    */   public Getter getGetter(Class theClass, String propertyName)
/*  15:    */     throws PropertyNotFoundException
/*  16:    */   {
/*  17: 43 */     return new MapGetter(propertyName);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Setter getSetter(Class theClass, String propertyName)
/*  21:    */     throws PropertyNotFoundException
/*  22:    */   {
/*  23: 51 */     return new MapSetter(propertyName);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static final class MapSetter
/*  27:    */     implements Setter
/*  28:    */   {
/*  29:    */     private String name;
/*  30:    */     
/*  31:    */     MapSetter(String name)
/*  32:    */     {
/*  33: 58 */       this.name = name;
/*  34:    */     }
/*  35:    */     
/*  36:    */     public Method getMethod()
/*  37:    */     {
/*  38: 65 */       return null;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public String getMethodName()
/*  42:    */     {
/*  43: 72 */       return null;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public void set(Object target, Object value, SessionFactoryImplementor factory)
/*  47:    */       throws HibernateException
/*  48:    */     {
/*  49: 80 */       ((Map)target).put(this.name, value);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static final class MapGetter
/*  54:    */     implements Getter
/*  55:    */   {
/*  56:    */     private String name;
/*  57:    */     
/*  58:    */     MapGetter(String name)
/*  59:    */     {
/*  60: 89 */       this.name = name;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public Member getMember()
/*  64:    */     {
/*  65: 96 */       return null;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public Method getMethod()
/*  69:    */     {
/*  70:103 */       return null;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public String getMethodName()
/*  74:    */     {
/*  75:110 */       return null;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public Object get(Object target)
/*  79:    */       throws HibernateException
/*  80:    */     {
/*  81:117 */       return ((Map)target).get(this.name);
/*  82:    */     }
/*  83:    */     
/*  84:    */     public Object getForInsert(Object target, Map mergeMap, SessionImplementor session)
/*  85:    */     {
/*  86:124 */       return get(target);
/*  87:    */     }
/*  88:    */     
/*  89:    */     public Class getReturnType()
/*  90:    */     {
/*  91:131 */       return Object.class;
/*  92:    */     }
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.MapAccessor
 * JD-Core Version:    0.7.0.1
 */