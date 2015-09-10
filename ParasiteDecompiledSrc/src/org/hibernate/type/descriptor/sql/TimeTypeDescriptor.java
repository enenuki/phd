/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.ResultSet;
/*  5:   */ import java.sql.SQLException;
/*  6:   */ import java.sql.Time;
/*  7:   */ import org.hibernate.type.descriptor.ValueBinder;
/*  8:   */ import org.hibernate.type.descriptor.ValueExtractor;
/*  9:   */ import org.hibernate.type.descriptor.WrapperOptions;
/* 10:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/* 11:   */ 
/* 12:   */ public class TimeTypeDescriptor
/* 13:   */   implements SqlTypeDescriptor
/* 14:   */ {
/* 15:43 */   public static final TimeTypeDescriptor INSTANCE = new TimeTypeDescriptor();
/* 16:   */   
/* 17:   */   public int getSqlType()
/* 18:   */   {
/* 19:46 */     return 92;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean canBeRemapped()
/* 23:   */   {
/* 24:51 */     return true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 28:   */   {
/* 29:55 */     new BasicBinder(javaTypeDescriptor, this)
/* 30:   */     {
/* 31:   */       protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/* 32:   */         throws SQLException
/* 33:   */       {
/* 34:58 */         st.setTime(index, (Time)javaTypeDescriptor.unwrap(value, Time.class, options));
/* 35:   */       }
/* 36:   */     };
/* 37:   */   }
/* 38:   */   
/* 39:   */   public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor)
/* 40:   */   {
/* 41:64 */     new BasicExtractor(javaTypeDescriptor, this)
/* 42:   */     {
/* 43:   */       protected X doExtract(ResultSet rs, String name, WrapperOptions options)
/* 44:   */         throws SQLException
/* 45:   */       {
/* 46:67 */         return javaTypeDescriptor.wrap(rs.getTime(name), options);
/* 47:   */       }
/* 48:   */     };
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.TimeTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */