/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.CharacterTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.CharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class CharacterType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<Character>
/* 10:   */   implements PrimitiveType<Character>, DiscriminatorType<Character>
/* 11:   */ {
/* 12:42 */   public static final CharacterType INSTANCE = new CharacterType();
/* 13:   */   
/* 14:   */   public CharacterType()
/* 15:   */   {
/* 16:45 */     super(CharTypeDescriptor.INSTANCE, CharacterTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:49 */     return "character";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String[] getRegistrationKeys()
/* 25:   */   {
/* 26:54 */     return new String[] { getName(), Character.TYPE.getName(), Character.class.getName() };
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Serializable getDefaultValue()
/* 30:   */   {
/* 31:58 */     throw new UnsupportedOperationException("not a valid id type");
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Class getPrimitiveClass()
/* 35:   */   {
/* 36:62 */     return Character.TYPE;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String objectToSQLString(Character value, Dialect dialect)
/* 40:   */   {
/* 41:66 */     return '\'' + toString(value) + '\'';
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Character stringToObject(String xml)
/* 45:   */   {
/* 46:70 */     return (Character)fromString(xml);
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CharacterType
 * JD-Core Version:    0.7.0.1
 */