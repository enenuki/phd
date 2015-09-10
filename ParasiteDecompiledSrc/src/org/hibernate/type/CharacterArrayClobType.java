/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.CharacterArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class CharacterArrayClobType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Character[]>
/*  8:   */ {
/*  9:37 */   public static final CharacterArrayClobType INSTANCE = new CharacterArrayClobType();
/* 10:   */   
/* 11:   */   public CharacterArrayClobType()
/* 12:   */   {
/* 13:40 */     super(ClobTypeDescriptor.DEFAULT, CharacterArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:45 */     return null;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CharacterArrayClobType
 * JD-Core Version:    0.7.0.1
 */