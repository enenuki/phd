/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ public class BinaryTypeDescriptor
/*  4:   */   extends VarbinaryTypeDescriptor
/*  5:   */ {
/*  6:33 */   public static final BinaryTypeDescriptor INSTANCE = new BinaryTypeDescriptor();
/*  7:   */   
/*  8:   */   public int getSqlType()
/*  9:   */   {
/* 10:37 */     return -2;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.BinaryTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */