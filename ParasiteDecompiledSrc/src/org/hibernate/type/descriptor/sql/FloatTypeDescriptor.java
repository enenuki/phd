/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ public class FloatTypeDescriptor
/*  4:   */   extends RealTypeDescriptor
/*  5:   */ {
/*  6:33 */   public static final FloatTypeDescriptor INSTANCE = new FloatTypeDescriptor();
/*  7:   */   
/*  8:   */   public int getSqlType()
/*  9:   */   {
/* 10:36 */     return 6;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.FloatTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */