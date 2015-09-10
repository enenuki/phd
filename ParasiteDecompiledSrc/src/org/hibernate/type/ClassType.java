/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.ClassTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class ClassType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Class>
/*  8:   */ {
/*  9:35 */   public static final ClassType INSTANCE = new ClassType();
/* 10:   */   
/* 11:   */   public ClassType()
/* 12:   */   {
/* 13:38 */     super(VarcharTypeDescriptor.INSTANCE, ClassTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:42 */     return "class";
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected boolean registerUnderJavaType()
/* 22:   */   {
/* 23:47 */     return true;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ClassType
 * JD-Core Version:    0.7.0.1
 */