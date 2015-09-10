/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.Reader;
/*   4:    */ import java.io.StringReader;
/*   5:    */ import java.sql.Clob;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  10:    */ import org.hibernate.type.descriptor.CharacterStream;
/*  11:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  12:    */ 
/*  13:    */ public class CharacterArrayTypeDescriptor
/*  14:    */   extends AbstractTypeDescriptor<Character[]>
/*  15:    */ {
/*  16: 42 */   public static final CharacterArrayTypeDescriptor INSTANCE = new CharacterArrayTypeDescriptor();
/*  17:    */   
/*  18:    */   public CharacterArrayTypeDescriptor()
/*  19:    */   {
/*  20: 46 */     super([Ljava.lang.Character.class, ArrayMutabilityPlan.INSTANCE);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String toString(Character[] value)
/*  24:    */   {
/*  25: 50 */     return new String(unwrapChars(value));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Character[] fromString(String string)
/*  29:    */   {
/*  30: 54 */     return wrapChars(string.toCharArray());
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean areEqual(Character[] one, Character[] another)
/*  34:    */   {
/*  35: 59 */     return (one == another) || ((one != null) && (another != null) && (Arrays.equals(one, another)));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int extractHashCode(Character[] chars)
/*  39:    */   {
/*  40: 65 */     int hashCode = 1;
/*  41: 66 */     for (Character aChar : chars) {
/*  42: 67 */       hashCode = 31 * hashCode + aChar.charValue();
/*  43:    */     }
/*  44: 69 */     return hashCode;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public <X> X unwrap(Character[] value, Class<X> type, WrapperOptions options)
/*  48:    */   {
/*  49: 74 */     if (value == null) {
/*  50: 75 */       return null;
/*  51:    */     }
/*  52: 77 */     if ([Ljava.lang.Character.class.isAssignableFrom(type)) {
/*  53: 78 */       return value;
/*  54:    */     }
/*  55: 80 */     if (String.class.isAssignableFrom(type)) {
/*  56: 81 */       return new String(unwrapChars(value));
/*  57:    */     }
/*  58: 83 */     if (Clob.class.isAssignableFrom(type)) {
/*  59: 84 */       return options.getLobCreator().createClob(new String(unwrapChars(value)));
/*  60:    */     }
/*  61: 86 */     if (Reader.class.isAssignableFrom(type)) {
/*  62: 87 */       return new StringReader(new String(unwrapChars(value)));
/*  63:    */     }
/*  64: 89 */     if (CharacterStream.class.isAssignableFrom(type)) {
/*  65: 90 */       return new CharacterStreamImpl(new String(unwrapChars(value)));
/*  66:    */     }
/*  67: 92 */     throw unknownUnwrap(type);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public <X> Character[] wrap(X value, WrapperOptions options)
/*  71:    */   {
/*  72: 96 */     if (value == null) {
/*  73: 97 */       return null;
/*  74:    */     }
/*  75: 99 */     if ([Ljava.lang.Character.class.isInstance(value)) {
/*  76:100 */       return (Character[])value;
/*  77:    */     }
/*  78:102 */     if (String.class.isInstance(value)) {
/*  79:103 */       return wrapChars(((String)value).toCharArray());
/*  80:    */     }
/*  81:105 */     if (Clob.class.isInstance(value)) {
/*  82:    */       try
/*  83:    */       {
/*  84:107 */         return wrapChars(DataHelper.extractString(((Clob)value).getCharacterStream()).toCharArray());
/*  85:    */       }
/*  86:    */       catch (SQLException e)
/*  87:    */       {
/*  88:110 */         throw new HibernateException("Unable to access lob stream", e);
/*  89:    */       }
/*  90:    */     }
/*  91:113 */     if (Reader.class.isInstance(value)) {
/*  92:114 */       return wrapChars(DataHelper.extractString((Reader)value).toCharArray());
/*  93:    */     }
/*  94:116 */     throw unknownWrap(value.getClass());
/*  95:    */   }
/*  96:    */   
/*  97:    */   private Character[] wrapChars(char[] chars)
/*  98:    */   {
/*  99:121 */     if (chars == null) {
/* 100:122 */       return null;
/* 101:    */     }
/* 102:124 */     Character[] result = new Character[chars.length];
/* 103:125 */     for (int i = 0; i < chars.length; i++) {
/* 104:126 */       result[i] = Character.valueOf(chars[i]);
/* 105:    */     }
/* 106:128 */     return result;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private char[] unwrapChars(Character[] chars)
/* 110:    */   {
/* 111:133 */     if (chars == null) {
/* 112:134 */       return null;
/* 113:    */     }
/* 114:136 */     char[] result = new char[chars.length];
/* 115:137 */     for (int i = 0; i < chars.length; i++) {
/* 116:138 */       result[i] = chars[i].charValue();
/* 117:    */     }
/* 118:140 */     return result;
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.CharacterArrayTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */