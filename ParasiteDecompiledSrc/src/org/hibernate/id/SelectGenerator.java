/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Properties;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.dialect.Dialect;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.engine.spi.ValueInclusion;
/*  13:    */ import org.hibernate.id.insert.AbstractSelectingDelegate;
/*  14:    */ import org.hibernate.id.insert.IdentifierGeneratingInsert;
/*  15:    */ import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ 
/*  18:    */ public class SelectGenerator
/*  19:    */   extends AbstractPostInsertGenerator
/*  20:    */   implements Configurable
/*  21:    */ {
/*  22:    */   private String uniqueKeyPropertyName;
/*  23:    */   
/*  24:    */   public void configure(Type type, Properties params, Dialect d)
/*  25:    */     throws MappingException
/*  26:    */   {
/*  27: 56 */     this.uniqueKeyPropertyName = params.getProperty("key");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister persister, Dialect dialect, boolean isGetGeneratedKeysEnabled)
/*  31:    */     throws HibernateException
/*  32:    */   {
/*  33: 63 */     return new SelectGeneratorDelegate(persister, dialect, this.uniqueKeyPropertyName, null);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private static String determineNameOfPropertyToUse(PostInsertIdentityPersister persister, String supplied)
/*  37:    */   {
/*  38: 67 */     if (supplied != null) {
/*  39: 68 */       return supplied;
/*  40:    */     }
/*  41: 70 */     int[] naturalIdPropertyIndices = persister.getNaturalIdentifierProperties();
/*  42: 71 */     if (naturalIdPropertyIndices == null) {
/*  43: 72 */       throw new IdentifierGenerationException("no natural-id property defined; need to specify [key] in generator parameters");
/*  44:    */     }
/*  45: 77 */     if (naturalIdPropertyIndices.length > 1) {
/*  46: 78 */       throw new IdentifierGenerationException("select generator does not currently support composite natural-id properties; need to specify [key] in generator parameters");
/*  47:    */     }
/*  48: 83 */     ValueInclusion inclusion = persister.getPropertyInsertGenerationInclusions()[naturalIdPropertyIndices[0]];
/*  49: 84 */     if (inclusion != ValueInclusion.NONE) {
/*  50: 85 */       throw new IdentifierGenerationException("natural-id also defined as insert-generated; need to specify [key] in generator parameters");
/*  51:    */     }
/*  52: 90 */     return persister.getPropertyNames()[naturalIdPropertyIndices[0]];
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static class SelectGeneratorDelegate
/*  56:    */     extends AbstractSelectingDelegate
/*  57:    */     implements InsertGeneratedIdentifierDelegate
/*  58:    */   {
/*  59:    */     private final PostInsertIdentityPersister persister;
/*  60:    */     private final Dialect dialect;
/*  61:    */     private final String uniqueKeyPropertyName;
/*  62:    */     private final Type uniqueKeyType;
/*  63:    */     private final Type idType;
/*  64:    */     private final String idSelectString;
/*  65:    */     
/*  66:    */     private SelectGeneratorDelegate(PostInsertIdentityPersister persister, Dialect dialect, String suppliedUniqueKeyPropertyName)
/*  67:    */     {
/*  68:113 */       super();
/*  69:114 */       this.persister = persister;
/*  70:115 */       this.dialect = dialect;
/*  71:116 */       this.uniqueKeyPropertyName = SelectGenerator.determineNameOfPropertyToUse(persister, suppliedUniqueKeyPropertyName);
/*  72:    */       
/*  73:118 */       this.idSelectString = persister.getSelectByUniqueKeyString(this.uniqueKeyPropertyName);
/*  74:119 */       this.uniqueKeyType = persister.getPropertyType(this.uniqueKeyPropertyName);
/*  75:120 */       this.idType = persister.getIdentifierType();
/*  76:    */     }
/*  77:    */     
/*  78:    */     public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert()
/*  79:    */     {
/*  80:124 */       return new IdentifierGeneratingInsert(this.dialect);
/*  81:    */     }
/*  82:    */     
/*  83:    */     protected String getSelectSQL()
/*  84:    */     {
/*  85:131 */       return this.idSelectString;
/*  86:    */     }
/*  87:    */     
/*  88:    */     protected void bindParameters(SessionImplementor session, PreparedStatement ps, Object entity)
/*  89:    */       throws SQLException
/*  90:    */     {
/*  91:138 */       Object uniqueKeyValue = this.persister.getPropertyValue(entity, this.uniqueKeyPropertyName);
/*  92:139 */       this.uniqueKeyType.nullSafeSet(ps, uniqueKeyValue, 1, session);
/*  93:    */     }
/*  94:    */     
/*  95:    */     protected Serializable getResult(SessionImplementor session, ResultSet rs, Object entity)
/*  96:    */       throws SQLException
/*  97:    */     {
/*  98:146 */       if (!rs.next()) {
/*  99:147 */         throw new IdentifierGenerationException("the inserted row could not be located by the unique key: " + this.uniqueKeyPropertyName);
/* 100:    */       }
/* 101:152 */       return (Serializable)this.idType.nullSafeGet(rs, this.persister.getRootTableKeyColumnNames(), session, entity);
/* 102:    */     }
/* 103:    */   }
/* 104:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.SelectGenerator
 * JD-Core Version:    0.7.0.1
 */