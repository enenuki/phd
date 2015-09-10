/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.CharacterArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class CharacterArrayType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Character[]>
/*  8:   */ {
/*  9:35 */   public static final CharacterArrayType INSTANCE = new CharacterArrayType();
/* 10:   */   
/* 11:   */   public CharacterArrayType()
/* 12:   */   {
/* 13:38 */     super(VarcharTypeDescriptor.INSTANCE, CharacterArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:42 */     return "wrapper-characters";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String[] getRegistrationKeys()
/* 22:   */   {
/* 23:47 */     return new String[] { getName(), [Ljava.lang.Character.class.getName(), "Character[]" };
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CharacterArrayType
 * JD-Core Version:    0.7.0.1
 */