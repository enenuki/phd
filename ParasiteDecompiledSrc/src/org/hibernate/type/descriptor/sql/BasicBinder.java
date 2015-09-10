/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ import java.sql.PreparedStatement;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.internal.CoreMessageLogger;
/*  6:   */ import org.hibernate.type.descriptor.JdbcTypeNameMapper;
/*  7:   */ import org.hibernate.type.descriptor.ValueBinder;
/*  8:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  9:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/* 10:   */ import org.jboss.logging.Logger;
/* 11:   */ 
/* 12:   */ public abstract class BasicBinder<J>
/* 13:   */   implements ValueBinder<J>
/* 14:   */ {
/* 15:44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BasicBinder.class.getName());
/* 16:   */   private static final String BIND_MSG_TEMPLATE = "binding parameter [%s] as [%s] - %s";
/* 17:   */   private static final String NULL_BIND_MSG_TEMPLATE = "binding parameter [%s] as [%s] - <null>";
/* 18:   */   private final JavaTypeDescriptor<J> javaDescriptor;
/* 19:   */   private final SqlTypeDescriptor sqlDescriptor;
/* 20:   */   
/* 21:   */   public JavaTypeDescriptor<J> getJavaDescriptor()
/* 22:   */   {
/* 23:53 */     return this.javaDescriptor;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public SqlTypeDescriptor getSqlDescriptor()
/* 27:   */   {
/* 28:57 */     return this.sqlDescriptor;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public BasicBinder(JavaTypeDescriptor<J> javaDescriptor, SqlTypeDescriptor sqlDescriptor)
/* 32:   */   {
/* 33:61 */     this.javaDescriptor = javaDescriptor;
/* 34:62 */     this.sqlDescriptor = sqlDescriptor;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public final void bind(PreparedStatement st, J value, int index, WrapperOptions options)
/* 38:   */     throws SQLException
/* 39:   */   {
/* 40:69 */     if (value == null)
/* 41:   */     {
/* 42:70 */       if (LOG.isTraceEnabled()) {
/* 43:71 */         LOG.trace(String.format("binding parameter [%s] as [%s] - <null>", new Object[] { Integer.valueOf(index), JdbcTypeNameMapper.getTypeName(Integer.valueOf(this.sqlDescriptor.getSqlType())) }));
/* 44:   */       }
/* 45:79 */       st.setNull(index, this.sqlDescriptor.getSqlType());
/* 46:   */     }
/* 47:   */     else
/* 48:   */     {
/* 49:82 */       if (LOG.isTraceEnabled()) {
/* 50:83 */         LOG.trace(String.format("binding parameter [%s] as [%s] - %s", new Object[] { Integer.valueOf(index), JdbcTypeNameMapper.getTypeName(Integer.valueOf(this.sqlDescriptor.getSqlType())), getJavaDescriptor().extractLoggableRepresentation(value) }));
/* 51:   */       }
/* 52:92 */       doBind(st, value, index, options);
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected abstract void doBind(PreparedStatement paramPreparedStatement, J paramJ, int paramInt, WrapperOptions paramWrapperOptions)
/* 57:   */     throws SQLException;
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.BasicBinder
 * JD-Core Version:    0.7.0.1
 */