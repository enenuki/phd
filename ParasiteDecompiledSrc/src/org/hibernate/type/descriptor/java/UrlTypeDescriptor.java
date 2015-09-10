/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.net.MalformedURLException;
/*  4:   */ import java.net.URL;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  7:   */ 
/*  8:   */ public class UrlTypeDescriptor
/*  9:   */   extends AbstractTypeDescriptor<URL>
/* 10:   */ {
/* 11:38 */   public static final UrlTypeDescriptor INSTANCE = new UrlTypeDescriptor();
/* 12:   */   
/* 13:   */   public UrlTypeDescriptor()
/* 14:   */   {
/* 15:41 */     super(URL.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toString(URL value)
/* 19:   */   {
/* 20:45 */     return value.toExternalForm();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public URL fromString(String string)
/* 24:   */   {
/* 25:   */     try
/* 26:   */     {
/* 27:50 */       return new URL(string);
/* 28:   */     }
/* 29:   */     catch (MalformedURLException e)
/* 30:   */     {
/* 31:53 */       throw new HibernateException("Unable to convert string [" + string + "] to URL : " + e);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public <X> X unwrap(URL value, Class<X> type, WrapperOptions options)
/* 36:   */   {
/* 37:59 */     if (value == null) {
/* 38:60 */       return null;
/* 39:   */     }
/* 40:62 */     if (String.class.isAssignableFrom(type)) {
/* 41:63 */       return toString(value);
/* 42:   */     }
/* 43:65 */     throw unknownUnwrap(type);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public <X> URL wrap(X value, WrapperOptions options)
/* 47:   */   {
/* 48:69 */     if (value == null) {
/* 49:70 */       return null;
/* 50:   */     }
/* 51:72 */     if (String.class.isInstance(value)) {
/* 52:73 */       return fromString((String)value);
/* 53:   */     }
/* 54:75 */     throw unknownWrap(value.getClass());
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.UrlTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */