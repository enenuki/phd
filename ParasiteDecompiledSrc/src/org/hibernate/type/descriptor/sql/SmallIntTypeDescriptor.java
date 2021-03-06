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
/* 11:   */ public class SmallIntTypeDescriptor
/* 12:   */   implements SqlTypeDescriptor
/* 13:   */ {
/* 14:42 */   public static final SmallIntTypeDescriptor INSTANCE = new SmallIntTypeDescriptor();
/* 15:   */   
/* 16:   */   public int getSqlType()
/* 17:   */   {
/* 18:45 */     return 5;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean canBeRemapped()
/* 22:   */   {
/* 23:50 */     return true;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 27:   */   {
/* 28:54 */     new BasicBinder(javaTypeDescriptor, this)
/* 29:   */     {
/* 30:   */       protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/* 31:   */         throws SQLException
/* 32:   */       {
/* 33:57 */         st.setShort(index, ((Short)javaTypeDescriptor.unwrap(value, Short.class, options)).shortValue());
/* 34:   */       }
/* 35:   */     };
/* 36:   */   }
/* 37:   */   
/* 38:   */   public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 39:   */   {
/* 40:63 */     new BasicExtractor(javaTypeDescriptor, this)
/* 41:   */     {
/* 42:   */       protected X doExtract(ResultSet rs, String name, WrapperOptions options)
/* 43:   */         throws SQLException
/* 44:   */       {
/* 45:66 */         return javaTypeDescriptor.wrap(Short.valueOf(rs.getShort(name)), options);
/* 46:   */       }
/* 47:   */     };
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */