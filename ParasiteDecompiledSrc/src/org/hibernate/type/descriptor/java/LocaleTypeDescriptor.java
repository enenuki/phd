/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import java.util.Locale;
/*  5:   */ import java.util.StringTokenizer;
/*  6:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  7:   */ 
/*  8:   */ public class LocaleTypeDescriptor
/*  9:   */   extends AbstractTypeDescriptor<Locale>
/* 10:   */ {
/* 11:38 */   public static final LocaleTypeDescriptor INSTANCE = new LocaleTypeDescriptor();
/* 12:   */   
/* 13:   */   public static class LocaleComparator
/* 14:   */     implements Comparator<Locale>
/* 15:   */   {
/* 16:41 */     public static final LocaleComparator INSTANCE = new LocaleComparator();
/* 17:   */     
/* 18:   */     public int compare(Locale o1, Locale o2)
/* 19:   */     {
/* 20:44 */       return o1.toString().compareTo(o2.toString());
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public LocaleTypeDescriptor()
/* 25:   */   {
/* 26:49 */     super(Locale.class);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Comparator<Locale> getComparator()
/* 30:   */   {
/* 31:54 */     return LocaleComparator.INSTANCE;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toString(Locale value)
/* 35:   */   {
/* 36:58 */     return value.toString();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Locale fromString(String string)
/* 40:   */   {
/* 41:62 */     StringTokenizer tokens = new StringTokenizer(string, "_");
/* 42:63 */     String language = tokens.hasMoreTokens() ? tokens.nextToken() : "";
/* 43:64 */     String country = tokens.hasMoreTokens() ? tokens.nextToken() : "";
/* 44:   */     
/* 45:66 */     String variant = "";
/* 46:67 */     String sep = "";
/* 47:68 */     while (tokens.hasMoreTokens())
/* 48:   */     {
/* 49:69 */       variant = variant + sep + tokens.nextToken();
/* 50:70 */       sep = "_";
/* 51:   */     }
/* 52:72 */     return new Locale(language, country, variant);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public <X> X unwrap(Locale value, Class<X> type, WrapperOptions options)
/* 56:   */   {
/* 57:77 */     if (value == null) {
/* 58:78 */       return null;
/* 59:   */     }
/* 60:80 */     if (String.class.isAssignableFrom(type)) {
/* 61:81 */       return value.toString();
/* 62:   */     }
/* 63:83 */     throw unknownUnwrap(type);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public <X> Locale wrap(X value, WrapperOptions options)
/* 67:   */   {
/* 68:87 */     if (value == null) {
/* 69:88 */       return null;
/* 70:   */     }
/* 71:90 */     if (String.class.isInstance(value)) {
/* 72:91 */       return fromString((String)value);
/* 73:   */     }
/* 74:93 */     throw unknownWrap(value.getClass());
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.LocaleTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */