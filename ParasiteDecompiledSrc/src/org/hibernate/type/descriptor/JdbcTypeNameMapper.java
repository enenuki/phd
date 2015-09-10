/*  1:   */ package org.hibernate.type.descriptor;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Field;
/*  4:   */ import java.sql.Types;
/*  5:   */ import java.util.Collections;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.hibernate.HibernateException;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.jboss.logging.Logger;
/* 11:   */ 
/* 12:   */ public class JdbcTypeNameMapper
/* 13:   */ {
/* 14:44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JdbcTypeNameMapper.class.getName());
/* 15:45 */   private static Map<Integer, String> JDBC_TYPE_MAP = buildJdbcTypeMap();
/* 16:   */   
/* 17:   */   private static Map<Integer, String> buildJdbcTypeMap()
/* 18:   */   {
/* 19:48 */     HashMap<Integer, String> map = new HashMap();
/* 20:49 */     Field[] fields = Types.class.getFields();
/* 21:50 */     if (fields == null) {
/* 22:51 */       throw new HibernateException("Unexpected problem extracting JDBC type mapping codes from java.sql.Types");
/* 23:   */     }
/* 24:53 */     for (Field field : fields) {
/* 25:   */       try
/* 26:   */       {
/* 27:55 */         int code = field.getInt(null);
/* 28:56 */         String old = (String)map.put(Integer.valueOf(code), field.getName());
/* 29:57 */         if (old != null) {
/* 30:57 */           LOG.JavaSqlTypesMappedSameCodeMultipleTimes(code, old, field.getName());
/* 31:   */         }
/* 32:   */       }
/* 33:   */       catch (IllegalAccessException e)
/* 34:   */       {
/* 35:60 */         throw new HibernateException("Unable to access JDBC type mapping [" + field.getName() + "]", e);
/* 36:   */       }
/* 37:   */     }
/* 38:63 */     return Collections.unmodifiableMap(map);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static String getTypeName(Integer code)
/* 42:   */   {
/* 43:67 */     String name = (String)JDBC_TYPE_MAP.get(code);
/* 44:68 */     if (name == null) {
/* 45:69 */       return "UNKNOWN(" + code + ")";
/* 46:   */     }
/* 47:71 */     return name;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.JdbcTypeNameMapper
 * JD-Core Version:    0.7.0.1
 */