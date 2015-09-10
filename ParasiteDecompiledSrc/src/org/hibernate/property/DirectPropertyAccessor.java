/*   1:    */ package org.hibernate.property;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.lang.reflect.Member;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.PropertyAccessException;
/*   9:    */ import org.hibernate.PropertyNotFoundException;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.internal.util.ReflectHelper;
/*  13:    */ 
/*  14:    */ public class DirectPropertyAccessor
/*  15:    */   implements PropertyAccessor
/*  16:    */ {
/*  17:    */   public static final class DirectGetter
/*  18:    */     implements Getter
/*  19:    */   {
/*  20:    */     private final transient Field field;
/*  21:    */     private final Class clazz;
/*  22:    */     private final String name;
/*  23:    */     
/*  24:    */     DirectGetter(Field field, Class clazz, String name)
/*  25:    */     {
/*  26: 48 */       this.field = field;
/*  27: 49 */       this.clazz = clazz;
/*  28: 50 */       this.name = name;
/*  29:    */     }
/*  30:    */     
/*  31:    */     public Object get(Object target)
/*  32:    */       throws HibernateException
/*  33:    */     {
/*  34:    */       try
/*  35:    */       {
/*  36: 58 */         return this.field.get(target);
/*  37:    */       }
/*  38:    */       catch (Exception e)
/*  39:    */       {
/*  40: 61 */         throw new PropertyAccessException(e, "could not get a field value by reflection", false, this.clazz, this.name);
/*  41:    */       }
/*  42:    */     }
/*  43:    */     
/*  44:    */     public Object getForInsert(Object target, Map mergeMap, SessionImplementor session)
/*  45:    */     {
/*  46: 69 */       return get(target);
/*  47:    */     }
/*  48:    */     
/*  49:    */     public Member getMember()
/*  50:    */     {
/*  51: 76 */       return this.field;
/*  52:    */     }
/*  53:    */     
/*  54:    */     public Method getMethod()
/*  55:    */     {
/*  56: 83 */       return null;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public String getMethodName()
/*  60:    */     {
/*  61: 90 */       return null;
/*  62:    */     }
/*  63:    */     
/*  64:    */     public Class getReturnType()
/*  65:    */     {
/*  66: 97 */       return this.field.getType();
/*  67:    */     }
/*  68:    */     
/*  69:    */     Object readResolve()
/*  70:    */     {
/*  71:101 */       return new DirectGetter(DirectPropertyAccessor.getField(this.clazz, this.name), this.clazz, this.name);
/*  72:    */     }
/*  73:    */     
/*  74:    */     public String toString()
/*  75:    */     {
/*  76:105 */       return "DirectGetter(" + this.clazz.getName() + '.' + this.name + ')';
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static final class DirectSetter
/*  81:    */     implements Setter
/*  82:    */   {
/*  83:    */     private final transient Field field;
/*  84:    */     private final Class clazz;
/*  85:    */     private final String name;
/*  86:    */     
/*  87:    */     DirectSetter(Field field, Class clazz, String name)
/*  88:    */     {
/*  89:114 */       this.field = field;
/*  90:115 */       this.clazz = clazz;
/*  91:116 */       this.name = name;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public Method getMethod()
/*  95:    */     {
/*  96:123 */       return null;
/*  97:    */     }
/*  98:    */     
/*  99:    */     public String getMethodName()
/* 100:    */     {
/* 101:130 */       return null;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public void set(Object target, Object value, SessionFactoryImplementor factory)
/* 105:    */       throws HibernateException
/* 106:    */     {
/* 107:    */       try
/* 108:    */       {
/* 109:138 */         this.field.set(target, value);
/* 110:    */       }
/* 111:    */       catch (Exception e)
/* 112:    */       {
/* 113:141 */         if ((value == null) && (this.field.getType().isPrimitive())) {
/* 114:142 */           throw new PropertyAccessException(e, "Null value was assigned to a property of primitive type", true, this.clazz, this.name);
/* 115:    */         }
/* 116:150 */         throw new PropertyAccessException(e, "could not set a field value by reflection", true, this.clazz, this.name);
/* 117:    */       }
/* 118:    */     }
/* 119:    */     
/* 120:    */     public String toString()
/* 121:    */     {
/* 122:156 */       return "DirectSetter(" + this.clazz.getName() + '.' + this.name + ')';
/* 123:    */     }
/* 124:    */     
/* 125:    */     Object readResolve()
/* 126:    */     {
/* 127:160 */       return new DirectSetter(DirectPropertyAccessor.getField(this.clazz, this.name), this.clazz, this.name);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   private static Field getField(Class clazz, String name)
/* 132:    */     throws PropertyNotFoundException
/* 133:    */   {
/* 134:165 */     if ((clazz == null) || (clazz == Object.class)) {
/* 135:166 */       throw new PropertyNotFoundException("field not found: " + name);
/* 136:    */     }
/* 137:    */     Field field;
/* 138:    */     try
/* 139:    */     {
/* 140:170 */       field = clazz.getDeclaredField(name);
/* 141:    */     }
/* 142:    */     catch (NoSuchFieldException nsfe)
/* 143:    */     {
/* 144:173 */       field = getField(clazz, clazz.getSuperclass(), name);
/* 145:    */     }
/* 146:175 */     if (!ReflectHelper.isPublic(clazz, field)) {
/* 147:175 */       field.setAccessible(true);
/* 148:    */     }
/* 149:176 */     return field;
/* 150:    */   }
/* 151:    */   
/* 152:    */   private static Field getField(Class root, Class clazz, String name)
/* 153:    */     throws PropertyNotFoundException
/* 154:    */   {
/* 155:180 */     if ((clazz == null) || (clazz == Object.class)) {
/* 156:181 */       throw new PropertyNotFoundException("field [" + name + "] not found on " + root.getName());
/* 157:    */     }
/* 158:    */     Field field;
/* 159:    */     try
/* 160:    */     {
/* 161:185 */       field = clazz.getDeclaredField(name);
/* 162:    */     }
/* 163:    */     catch (NoSuchFieldException nsfe)
/* 164:    */     {
/* 165:188 */       field = getField(root, clazz.getSuperclass(), name);
/* 166:    */     }
/* 167:190 */     if (!ReflectHelper.isPublic(clazz, field)) {
/* 168:190 */       field.setAccessible(true);
/* 169:    */     }
/* 170:191 */     return field;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Getter getGetter(Class theClass, String propertyName)
/* 174:    */     throws PropertyNotFoundException
/* 175:    */   {
/* 176:196 */     return new DirectGetter(getField(theClass, propertyName), theClass, propertyName);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Setter getSetter(Class theClass, String propertyName)
/* 180:    */     throws PropertyNotFoundException
/* 181:    */   {
/* 182:201 */     return new DirectSetter(getField(theClass, propertyName), theClass, propertyName);
/* 183:    */   }
/* 184:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.DirectPropertyAccessor
 * JD-Core Version:    0.7.0.1
 */