/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.sql.DatabaseMetaData;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.LinkedHashSet;
/*   7:    */ import org.hibernate.internal.CoreMessageLogger;
/*   8:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   9:    */ import org.jboss.logging.Logger;
/*  10:    */ 
/*  11:    */ public class TypeInfoExtracter
/*  12:    */ {
/*  13: 43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TypeInfoExtracter.class.getName());
/*  14:    */   
/*  15:    */   public static LinkedHashSet<TypeInfo> extractTypeInfo(DatabaseMetaData metaData)
/*  16:    */   {
/*  17: 56 */     typeInfoSet = new LinkedHashSet();
/*  18:    */     try
/*  19:    */     {
/*  20: 58 */       ResultSet resultSet = metaData.getTypeInfo();
/*  21:    */       try
/*  22:    */       {
/*  23: 60 */         while (resultSet.next()) {
/*  24: 61 */           typeInfoSet.add(new TypeInfo(resultSet.getString("TYPE_NAME"), resultSet.getInt("DATA_TYPE"), interpretCreateParams(resultSet.getString("CREATE_PARAMS")), resultSet.getBoolean("UNSIGNED_ATTRIBUTE"), resultSet.getInt("PRECISION"), resultSet.getShort("MINIMUM_SCALE"), resultSet.getShort("MAXIMUM_SCALE"), resultSet.getBoolean("FIXED_PREC_SCALE"), resultSet.getString("LITERAL_PREFIX"), resultSet.getString("LITERAL_SUFFIX"), resultSet.getBoolean("CASE_SENSITIVE"), TypeSearchability.interpret(resultSet.getShort("SEARCHABLE")), TypeNullability.interpret(resultSet.getShort("NULLABLE"))));
/*  25:    */         }
/*  26:    */       }
/*  27:    */       catch (SQLException e)
/*  28:    */       {
/*  29: 81 */         LOG.unableToAccessTypeInfoResultSet(e.toString());
/*  30:    */       }
/*  31:    */       finally
/*  32:    */       {
/*  33:    */         try
/*  34:    */         {
/*  35: 85 */           resultSet.close();
/*  36:    */         }
/*  37:    */         catch (SQLException e)
/*  38:    */         {
/*  39: 88 */           LOG.unableToReleaseTypeInfoResultSet();
/*  40:    */         }
/*  41:    */       }
/*  42: 96 */       return typeInfoSet;
/*  43:    */     }
/*  44:    */     catch (SQLException e)
/*  45:    */     {
/*  46: 93 */       LOG.unableToRetrieveTypeInfoResultSet(e.toString());
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static String[] interpretCreateParams(String value)
/*  51:    */   {
/*  52:100 */     if ((value == null) || (value.length() == 0)) {
/*  53:101 */       return ArrayHelper.EMPTY_STRING_ARRAY;
/*  54:    */     }
/*  55:103 */     return value.split(",");
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.TypeInfoExtracter
 * JD-Core Version:    0.7.0.1
 */