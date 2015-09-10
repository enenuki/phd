/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.PrimitiveCharacterArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class CharArrayType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<char[]>
/*  8:   */ {
/*  9:35 */   public static final CharArrayType INSTANCE = new CharArrayType();
/* 10:   */   
/* 11:   */   public CharArrayType()
/* 12:   */   {
/* 13:38 */     super(VarcharTypeDescriptor.INSTANCE, PrimitiveCharacterArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:42 */     return "characters";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String[] getRegistrationKeys()
/* 22:   */   {
/* 23:47 */     return new String[] { getName(), "char[]", [C.class.getName() };
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CharArrayType
 * JD-Core Version:    0.7.0.1
 */