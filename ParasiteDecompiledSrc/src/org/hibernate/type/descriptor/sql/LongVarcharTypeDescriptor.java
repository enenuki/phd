/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ public class LongVarcharTypeDescriptor
/*  4:   */   extends VarcharTypeDescriptor
/*  5:   */ {
/*  6:33 */   public static final LongVarcharTypeDescriptor INSTANCE = new LongVarcharTypeDescriptor();
/*  7:   */   
/*  8:   */   public int getSqlType()
/*  9:   */   {
/* 10:37 */     return -1;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.LongVarcharTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */