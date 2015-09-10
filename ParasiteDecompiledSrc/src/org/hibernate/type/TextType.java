/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.StringTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.LongVarcharTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class TextType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<String>
/*  8:   */ {
/*  9:36 */   public static final TextType INSTANCE = new TextType();
/* 10:   */   
/* 11:   */   public TextType()
/* 12:   */   {
/* 13:39 */     super(LongVarcharTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:43 */     return "text";
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TextType
 * JD-Core Version:    0.7.0.1
 */