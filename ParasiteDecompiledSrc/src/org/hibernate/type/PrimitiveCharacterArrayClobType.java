/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.PrimitiveCharacterArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class PrimitiveCharacterArrayClobType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<char[]>
/*  8:   */ {
/*  9:34 */   public static final CharacterArrayClobType INSTANCE = new CharacterArrayClobType();
/* 10:   */   
/* 11:   */   public PrimitiveCharacterArrayClobType()
/* 12:   */   {
/* 13:37 */     super(ClobTypeDescriptor.DEFAULT, PrimitiveCharacterArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:42 */     return null;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.PrimitiveCharacterArrayClobType
 * JD-Core Version:    0.7.0.1
 */