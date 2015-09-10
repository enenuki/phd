/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.internal.util.ReflectHelper;
/*  5:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  6:   */ 
/*  7:   */ public class ClassTypeDescriptor
/*  8:   */   extends AbstractTypeDescriptor<Class>
/*  9:   */ {
/* 10:35 */   public static final ClassTypeDescriptor INSTANCE = new ClassTypeDescriptor();
/* 11:   */   
/* 12:   */   public ClassTypeDescriptor()
/* 13:   */   {
/* 14:38 */     super(Class.class);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String toString(Class value)
/* 18:   */   {
/* 19:42 */     return value.getName();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Class fromString(String string)
/* 23:   */   {
/* 24:46 */     if (string == null) {
/* 25:47 */       return null;
/* 26:   */     }
/* 27:   */     try
/* 28:   */     {
/* 29:51 */       return ReflectHelper.classForName(string);
/* 30:   */     }
/* 31:   */     catch (ClassNotFoundException e)
/* 32:   */     {
/* 33:54 */       throw new HibernateException("Unable to locate named class " + string);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public <X> X unwrap(Class value, Class<X> type, WrapperOptions options)
/* 38:   */   {
/* 39:60 */     if (value == null) {
/* 40:61 */       return null;
/* 41:   */     }
/* 42:63 */     if (Class.class.isAssignableFrom(type)) {
/* 43:64 */       return value;
/* 44:   */     }
/* 45:66 */     if (String.class.isAssignableFrom(type)) {
/* 46:67 */       return toString(value);
/* 47:   */     }
/* 48:69 */     throw unknownUnwrap(type);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public <X> Class wrap(X value, WrapperOptions options)
/* 52:   */   {
/* 53:73 */     if (value == null) {
/* 54:74 */       return null;
/* 55:   */     }
/* 56:76 */     if (Class.class.isInstance(value)) {
/* 57:77 */       return (Class)value;
/* 58:   */     }
/* 59:79 */     if (String.class.isInstance(value)) {
/* 60:80 */       return fromString((String)value);
/* 61:   */     }
/* 62:82 */     throw unknownWrap(value.getClass());
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ClassTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */