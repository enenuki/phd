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
/*  11:    */ public class NoopAccessor
/*  12:    */   implements PropertyAccessor
/*  13:    */ {
/*  14:    */   public Getter getGetter(Class arg0, String arg1)
/*  15:    */     throws PropertyNotFoundException
/*  16:    */   {
/*  17: 44 */     return new NoopGetter(null);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Setter getSetter(Class arg0, String arg1)
/*  21:    */     throws PropertyNotFoundException
/*  22:    */   {
/*  23: 51 */     return new NoopSetter(null);
/*  24:    */   }
/*  25:    */   
/*  26:    */   private static class NoopGetter
/*  27:    */     implements Getter
/*  28:    */   {
/*  29:    */     public Object get(Object target)
/*  30:    */       throws HibernateException
/*  31:    */     {
/*  32: 64 */       return null;
/*  33:    */     }
/*  34:    */     
/*  35:    */     public Object getForInsert(Object target, Map map, SessionImplementor arg1)
/*  36:    */       throws HibernateException
/*  37:    */     {
/*  38: 72 */       return null;
/*  39:    */     }
/*  40:    */     
/*  41:    */     public Class getReturnType()
/*  42:    */     {
/*  43: 79 */       return Object.class;
/*  44:    */     }
/*  45:    */     
/*  46:    */     public Member getMember()
/*  47:    */     {
/*  48: 86 */       return null;
/*  49:    */     }
/*  50:    */     
/*  51:    */     public String getMethodName()
/*  52:    */     {
/*  53: 93 */       return null;
/*  54:    */     }
/*  55:    */     
/*  56:    */     public Method getMethod()
/*  57:    */     {
/*  58:100 */       return null;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   private static class NoopSetter
/*  63:    */     implements Setter
/*  64:    */   {
/*  65:    */     public void set(Object target, Object value, SessionFactoryImplementor arg2) {}
/*  66:    */     
/*  67:    */     public String getMethodName()
/*  68:    */     {
/*  69:119 */       return null;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public Method getMethod()
/*  73:    */     {
/*  74:126 */       return null;
/*  75:    */     }
/*  76:    */   }
/*  77:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.NoopAccessor
 * JD-Core Version:    0.7.0.1
 */