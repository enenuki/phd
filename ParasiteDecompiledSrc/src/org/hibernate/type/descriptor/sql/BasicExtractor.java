/*  1:   */ package org.hibernate.type.descriptor.sql;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import java.sql.SQLException;
/*  5:   */ import org.hibernate.internal.CoreMessageLogger;
/*  6:   */ import org.hibernate.type.descriptor.ValueExtractor;
/*  7:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  8:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ public abstract class BasicExtractor<J>
/* 12:   */   implements ValueExtractor<J>
/* 13:   */ {
/* 14:43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BasicExtractor.class.getName());
/* 15:   */   private final JavaTypeDescriptor<J> javaDescriptor;
/* 16:   */   private final SqlTypeDescriptor sqlDescriptor;
/* 17:   */   
/* 18:   */   public BasicExtractor(JavaTypeDescriptor<J> javaDescriptor, SqlTypeDescriptor sqlDescriptor)
/* 19:   */   {
/* 20:49 */     this.javaDescriptor = javaDescriptor;
/* 21:50 */     this.sqlDescriptor = sqlDescriptor;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public JavaTypeDescriptor<J> getJavaDescriptor()
/* 25:   */   {
/* 26:54 */     return this.javaDescriptor;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public SqlTypeDescriptor getSqlDescriptor()
/* 30:   */   {
/* 31:58 */     return this.sqlDescriptor;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public J extract(ResultSet rs, String name, WrapperOptions options)
/* 35:   */     throws SQLException
/* 36:   */   {
/* 37:65 */     J value = doExtract(rs, name, options);
/* 38:66 */     if ((value == null) || (rs.wasNull()))
/* 39:   */     {
/* 40:67 */       LOG.tracev("Found [null] as column [{0}]", name);
/* 41:68 */       return null;
/* 42:   */     }
/* 43:71 */     if (LOG.isTraceEnabled()) {
/* 44:72 */       LOG.tracev("Found [{0}] as column [{1}]", getJavaDescriptor().extractLoggableRepresentation(value), name);
/* 45:   */     }
/* 46:74 */     return value;
/* 47:   */   }
/* 48:   */   
/* 49:   */   protected abstract J doExtract(ResultSet paramResultSet, String paramString, WrapperOptions paramWrapperOptions)
/* 50:   */     throws SQLException;
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.BasicExtractor
 * JD-Core Version:    0.7.0.1
 */