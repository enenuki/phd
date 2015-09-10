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
/*  13:    */ public class PrimitiveCharacterArrayTypeDescriptor
/*  14:    */   extends AbstractTypeDescriptor<char[]>
/*  15:    */ {
/*  16: 42 */   public static final PrimitiveCharacterArrayTypeDescriptor INSTANCE = new PrimitiveCharacterArrayTypeDescriptor();
/*  17:    */   
/*  18:    */   protected PrimitiveCharacterArrayTypeDescriptor()
/*  19:    */   {
/*  20: 46 */     super([C.class, ArrayMutabilityPlan.INSTANCE);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String toString(char[] value)
/*  24:    */   {
/*  25: 50 */     return new String(value);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public char[] fromString(String string)
/*  29:    */   {
/*  30: 54 */     return string.toCharArray();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean areEqual(char[] one, char[] another)
/*  34:    */   {
/*  35: 59 */     return (one == another) || ((one != null) && (another != null) && (Arrays.equals(one, another)));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int extractHashCode(char[] chars)
/*  39:    */   {
/*  40: 65 */     int hashCode = 1;
/*  41: 66 */     for (char aChar : chars) {
/*  42: 67 */       hashCode = 31 * hashCode + aChar;
/*  43:    */     }
/*  44: 69 */     return hashCode;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public <X> X unwrap(char[] value, Class<X> type, WrapperOptions options)
/*  48:    */   {
/*  49: 74 */     if (value == null) {
/*  50: 75 */       return null;
/*  51:    */     }
/*  52: 77 */     if ([C.class.isAssignableFrom(type)) {
/*  53: 78 */       return value;
/*  54:    */     }
/*  55: 80 */     if (String.class.isAssignableFrom(type)) {
/*  56: 81 */       return new String(value);
/*  57:    */     }
/*  58: 83 */     if (Clob.class.isAssignableFrom(type)) {
/*  59: 84 */       return options.getLobCreator().createClob(new String(value));
/*  60:    */     }
/*  61: 86 */     if (Reader.class.isAssignableFrom(type)) {
/*  62: 87 */       return new StringReader(new String(value));
/*  63:    */     }
/*  64: 89 */     if (CharacterStream.class.isAssignableFrom(type)) {
/*  65: 90 */       return new CharacterStreamImpl(new String(value));
/*  66:    */     }
/*  67: 92 */     throw unknownUnwrap(type);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public <X> char[] wrap(X value, WrapperOptions options)
/*  71:    */   {
/*  72: 96 */     if (value == null) {
/*  73: 97 */       return null;
/*  74:    */     }
/*  75: 99 */     if ([C.class.isInstance(value)) {
/*  76:100 */       return (char[])value;
/*  77:    */     }
/*  78:102 */     if (String.class.isInstance(value)) {
/*  79:103 */       return ((String)value).toCharArray();
/*  80:    */     }
/*  81:105 */     if (Clob.class.isInstance(value)) {
/*  82:    */       try
/*  83:    */       {
/*  84:107 */         return DataHelper.extractString(((Clob)value).getCharacterStream()).toCharArray();
/*  85:    */       }
/*  86:    */       catch (SQLException e)
/*  87:    */       {
/*  88:110 */         throw new HibernateException("Unable to access lob stream", e);
/*  89:    */       }
/*  90:    */     }
/*  91:113 */     if (Reader.class.isInstance(value)) {
/*  92:114 */       return DataHelper.extractString((Reader)value).toCharArray();
/*  93:    */     }
/*  94:116 */     throw unknownWrap(value.getClass());
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.PrimitiveCharacterArrayTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */