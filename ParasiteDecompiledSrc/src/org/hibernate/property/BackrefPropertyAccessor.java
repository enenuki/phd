/*   1:    */ package org.hibernate.property;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Member;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ 
/*  11:    */ public class BackrefPropertyAccessor
/*  12:    */   implements PropertyAccessor
/*  13:    */ {
/*  14:    */   private final String propertyName;
/*  15:    */   private final String entityName;
/*  16:    */   private final BackrefSetter setter;
/*  17:    */   private final BackrefGetter getter;
/*  18: 57 */   public static final Serializable UNKNOWN = new Serializable()
/*  19:    */   {
/*  20:    */     public String toString()
/*  21:    */     {
/*  22: 59 */       return "<unknown>";
/*  23:    */     }
/*  24:    */     
/*  25:    */     public Object readResolve()
/*  26:    */     {
/*  27: 63 */       return BackrefPropertyAccessor.UNKNOWN;
/*  28:    */     }
/*  29:    */   };
/*  30:    */   
/*  31:    */   public BackrefPropertyAccessor(String collectionRole, String entityName)
/*  32:    */   {
/*  33: 74 */     this.propertyName = collectionRole.substring(entityName.length() + 1);
/*  34: 75 */     this.entityName = entityName;
/*  35:    */     
/*  36: 77 */     this.setter = new BackrefSetter();
/*  37: 78 */     this.getter = new BackrefGetter();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Setter getSetter(Class theClass, String propertyName)
/*  41:    */   {
/*  42: 85 */     return this.setter;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Getter getGetter(Class theClass, String propertyName)
/*  46:    */   {
/*  47: 92 */     return this.getter;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static final class BackrefSetter
/*  51:    */     implements Setter
/*  52:    */   {
/*  53:    */     public Method getMethod()
/*  54:    */     {
/*  55:105 */       return null;
/*  56:    */     }
/*  57:    */     
/*  58:    */     public String getMethodName()
/*  59:    */     {
/*  60:112 */       return null;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void set(Object target, Object value, SessionFactoryImplementor factory) {}
/*  64:    */   }
/*  65:    */   
/*  66:    */   public class BackrefGetter
/*  67:    */     implements Getter
/*  68:    */   {
/*  69:    */     public BackrefGetter() {}
/*  70:    */     
/*  71:    */     public Object getForInsert(Object target, Map mergeMap, SessionImplementor session)
/*  72:    */     {
/*  73:134 */       if (session == null) {
/*  74:135 */         return BackrefPropertyAccessor.UNKNOWN;
/*  75:    */       }
/*  76:138 */       return session.getPersistenceContext().getOwnerId(BackrefPropertyAccessor.this.entityName, BackrefPropertyAccessor.this.propertyName, target, mergeMap);
/*  77:    */     }
/*  78:    */     
/*  79:    */     public Member getMember()
/*  80:    */     {
/*  81:146 */       return null;
/*  82:    */     }
/*  83:    */     
/*  84:    */     public Object get(Object target)
/*  85:    */     {
/*  86:153 */       return BackrefPropertyAccessor.UNKNOWN;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public Method getMethod()
/*  90:    */     {
/*  91:160 */       return null;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public String getMethodName()
/*  95:    */     {
/*  96:167 */       return null;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public Class getReturnType()
/* 100:    */     {
/* 101:174 */       return Object.class;
/* 102:    */     }
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.BackrefPropertyAccessor
 * JD-Core Version:    0.7.0.1
 */