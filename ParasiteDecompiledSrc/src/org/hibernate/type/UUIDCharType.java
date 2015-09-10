/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.UUID;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class UUIDCharType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<UUID>
/* 10:   */   implements LiteralType<UUID>
/* 11:   */ {
/* 12:38 */   public static final UUIDCharType INSTANCE = new UUIDCharType();
/* 13:   */   
/* 14:   */   public UUIDCharType()
/* 15:   */   {
/* 16:41 */     super(VarcharTypeDescriptor.INSTANCE, UUIDTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:45 */     return "uuid-char";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String objectToSQLString(UUID value, Dialect dialect)
/* 25:   */     throws Exception
/* 26:   */   {
/* 27:49 */     return StringType.INSTANCE.objectToSQLString(value.toString(), dialect);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.UUIDCharType
 * JD-Core Version:    0.7.0.1
 */