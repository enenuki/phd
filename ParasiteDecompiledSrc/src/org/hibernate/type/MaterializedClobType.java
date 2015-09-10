/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.StringTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class MaterializedClobType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<String>
/*  8:   */ {
/*  9:36 */   public static final MaterializedClobType INSTANCE = new MaterializedClobType();
/* 10:   */   
/* 11:   */   public MaterializedClobType()
/* 12:   */   {
/* 13:39 */     super(ClobTypeDescriptor.DEFAULT, StringTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:43 */     return "materialized_clob";
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.MaterializedClobType
 * JD-Core Version:    0.7.0.1
 */