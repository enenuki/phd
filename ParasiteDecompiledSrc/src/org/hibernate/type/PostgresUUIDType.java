/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import java.util.UUID;
/*  7:   */ import org.hibernate.type.descriptor.ValueBinder;
/*  8:   */ import org.hibernate.type.descriptor.ValueExtractor;
/*  9:   */ import org.hibernate.type.descriptor.WrapperOptions;
/* 10:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/* 11:   */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
/* 12:   */ import org.hibernate.type.descriptor.sql.BasicBinder;
/* 13:   */ import org.hibernate.type.descriptor.sql.BasicExtractor;
/* 14:   */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/* 15:   */ 
/* 16:   */ public class PostgresUUIDType
/* 17:   */   extends AbstractSingleColumnStandardBasicType<UUID>
/* 18:   */ {
/* 19:49 */   public static final PostgresUUIDType INSTANCE = new PostgresUUIDType();
/* 20:   */   
/* 21:   */   public PostgresUUIDType()
/* 22:   */   {
/* 23:52 */     super(PostgresUUIDSqlTypeDescriptor.INSTANCE, UUIDTypeDescriptor.INSTANCE);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getName()
/* 27:   */   {
/* 28:56 */     return "pg-uuid";
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static class PostgresUUIDSqlTypeDescriptor
/* 32:   */     implements SqlTypeDescriptor
/* 33:   */   {
/* 34:60 */     public static final PostgresUUIDSqlTypeDescriptor INSTANCE = new PostgresUUIDSqlTypeDescriptor();
/* 35:   */     
/* 36:   */     public int getSqlType()
/* 37:   */     {
/* 38:64 */       return 1111;
/* 39:   */     }
/* 40:   */     
/* 41:   */     public boolean canBeRemapped()
/* 42:   */     {
/* 43:69 */       return true;
/* 44:   */     }
/* 45:   */     
/* 46:   */     public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 47:   */     {
/* 48:73 */       new BasicBinder(javaTypeDescriptor, this)
/* 49:   */       {
/* 50:   */         protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/* 51:   */           throws SQLException
/* 52:   */         {
/* 53:76 */           st.setObject(index, javaTypeDescriptor.unwrap(value, UUID.class, options), PostgresUUIDType.PostgresUUIDSqlTypeDescriptor.this.getSqlType());
/* 54:   */         }
/* 55:   */       };
/* 56:   */     }
/* 57:   */     
/* 58:   */     public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 59:   */     {
/* 60:82 */       new BasicExtractor(javaTypeDescriptor, this)
/* 61:   */       {
/* 62:   */         protected X doExtract(ResultSet rs, String name, WrapperOptions options)
/* 63:   */           throws SQLException
/* 64:   */         {
/* 65:85 */           return javaTypeDescriptor.wrap(rs.getObject(name), options);
/* 66:   */         }
/* 67:   */       };
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.PostgresUUIDType
 * JD-Core Version:    0.7.0.1
 */