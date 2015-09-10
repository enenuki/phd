/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import org.hibernate.type.descriptor.ValueBinder;
/*  7:   */ import org.hibernate.type.descriptor.ValueExtractor;
/*  8:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  9:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/* 10:   */ 
/* 11:   */ public class TinyIntTypeDescriptor
/* 12:   */   implements SqlTypeDescriptor
/* 13:   */ {
/* 14:45 */   public static final TinyIntTypeDescriptor INSTANCE = new TinyIntTypeDescriptor();
/* 15:   */   
/* 16:   */   public int getSqlType()
/* 17:   */   {
/* 18:48 */     return -6;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean canBeRemapped()
/* 22:   */   {
/* 23:53 */     return true;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 27:   */   {
/* 28:57 */     new BasicBinder(javaTypeDescriptor, this)
/* 29:   */     {
/* 30:   */       protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/* 31:   */         throws SQLException
/* 32:   */       {
/* 33:60 */         st.setByte(index, ((Byte)javaTypeDescriptor.unwrap(value, Byte.class, options)).byteValue());
/* 34:   */       }
/* 35:   */     };
/* 36:   */   }
/* 37:   */   
/* 38:   */   public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 39:   */   {
/* 40:66 */     new BasicExtractor(javaTypeDescriptor, this)
/* 41:   */     {
/* 42:   */       protected X doExtract(ResultSet rs, String name, WrapperOptions options)
/* 43:   */         throws SQLException
/* 44:   */       {
/* 45:69 */         return javaTypeDescriptor.wrap(Byte.valueOf(rs.getByte(name)), options);
/* 46:   */       }
/* 47:   */     };
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */