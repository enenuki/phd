/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  5:   */ 
/*  6:   */ public class CharacterTypeDescriptor
/*  7:   */   extends AbstractTypeDescriptor<Character>
/*  8:   */ {
/*  9:34 */   public static final CharacterTypeDescriptor INSTANCE = new CharacterTypeDescriptor();
/* 10:   */   
/* 11:   */   public CharacterTypeDescriptor()
/* 12:   */   {
/* 13:37 */     super(Character.class);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString(Character value)
/* 17:   */   {
/* 18:41 */     return value.toString();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Character fromString(String string)
/* 22:   */   {
/* 23:46 */     if (string.length() != 1) {
/* 24:47 */       throw new HibernateException("multiple or zero characters found parsing string");
/* 25:   */     }
/* 26:49 */     return Character.valueOf(string.charAt(0));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public <X> X unwrap(Character value, Class<X> type, WrapperOptions options)
/* 30:   */   {
/* 31:54 */     if (value == null) {
/* 32:55 */       return null;
/* 33:   */     }
/* 34:57 */     if (Character.class.isAssignableFrom(type)) {
/* 35:58 */       return value;
/* 36:   */     }
/* 37:60 */     if (String.class.isAssignableFrom(type)) {
/* 38:61 */       return value.toString();
/* 39:   */     }
/* 40:63 */     if (Number.class.isAssignableFrom(type)) {
/* 41:64 */       return Short.valueOf((short)value.charValue());
/* 42:   */     }
/* 43:66 */     throw unknownUnwrap(type);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public <X> Character wrap(X value, WrapperOptions options)
/* 47:   */   {
/* 48:71 */     if (value == null) {
/* 49:72 */       return null;
/* 50:   */     }
/* 51:74 */     if (Character.class.isInstance(value)) {
/* 52:75 */       return (Character)value;
/* 53:   */     }
/* 54:77 */     if (String.class.isInstance(value))
/* 55:   */     {
/* 56:78 */       String str = (String)value;
/* 57:79 */       return Character.valueOf(str.charAt(0));
/* 58:   */     }
/* 59:81 */     if (Number.class.isInstance(value))
/* 60:   */     {
/* 61:82 */       Number nbr = (Number)value;
/* 62:83 */       return Character.valueOf((char)nbr.shortValue());
/* 63:   */     }
/* 64:85 */     throw unknownWrap(value.getClass());
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.CharacterTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */