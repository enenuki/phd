/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ 
/*  7:   */ public class BasicAuxiliaryDatabaseObjectImpl
/*  8:   */   extends AbstractAuxiliaryDatabaseObject
/*  9:   */ {
/* 10:   */   private static final String CATALOG_NAME_PLACEHOLDER = "${catalog}";
/* 11:   */   private static final String SCHEMA_NAME_PLACEHOLDER = "${schema}";
/* 12:   */   private final Schema defaultSchema;
/* 13:   */   private final String createString;
/* 14:   */   private final String dropString;
/* 15:   */   
/* 16:   */   public BasicAuxiliaryDatabaseObjectImpl(Schema defaultSchema, String createString, String dropString, Set<String> dialectScopes)
/* 17:   */   {
/* 18:47 */     super(dialectScopes);
/* 19:   */     
/* 20:   */ 
/* 21:50 */     this.defaultSchema = defaultSchema;
/* 22:51 */     this.createString = createString;
/* 23:52 */     this.dropString = dropString;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String[] sqlCreateStrings(Dialect dialect)
/* 27:   */   {
/* 28:57 */     return new String[] { injectCatalogAndSchema(this.createString, this.defaultSchema) };
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String[] sqlDropStrings(Dialect dialect)
/* 32:   */   {
/* 33:62 */     return new String[] { injectCatalogAndSchema(this.dropString, this.defaultSchema) };
/* 34:   */   }
/* 35:   */   
/* 36:   */   private static String injectCatalogAndSchema(String ddlString, Schema schema)
/* 37:   */   {
/* 38:66 */     String rtn = StringHelper.replace(ddlString, "${catalog}", schema.getName().getCatalog().getName());
/* 39:67 */     rtn = StringHelper.replace(rtn, "${schema}", schema.getName().getSchema().getName());
/* 40:68 */     return rtn;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.BasicAuxiliaryDatabaseObjectImpl
 * JD-Core Version:    0.7.0.1
 */