/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ public class LongVarbinaryTypeDescriptor
/*  4:   */   extends VarbinaryTypeDescriptor
/*  5:   */ {
/*  6:33 */   public static final LongVarbinaryTypeDescriptor INSTANCE = new LongVarbinaryTypeDescriptor();
/*  7:   */   
/*  8:   */   public int getSqlType()
/*  9:   */   {
/* 10:37 */     return -4;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.LongVarbinaryTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */