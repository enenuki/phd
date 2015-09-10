/*   1:    */ package org.hibernate.metamodel.source.annotations.global;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import org.hibernate.AnnotationException;
/*   5:    */ import org.hibernate.internal.CoreMessageLogger;
/*   6:    */ import org.hibernate.internal.util.StringHelper;
/*   7:    */ import org.hibernate.metamodel.relational.Column;
/*   8:    */ import org.hibernate.metamodel.relational.Database;
/*   9:    */ import org.hibernate.metamodel.relational.Identifier;
/*  10:    */ import org.hibernate.metamodel.relational.ObjectName;
/*  11:    */ import org.hibernate.metamodel.relational.Schema;
/*  12:    */ import org.hibernate.metamodel.relational.SimpleValue;
/*  13:    */ import org.hibernate.metamodel.relational.Table;
/*  14:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  15:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  16:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  17:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  18:    */ import org.jboss.jandex.AnnotationInstance;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class TableBinder
/*  22:    */ {
/*  23: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TableBinder.class.getName());
/*  24:    */   
/*  25:    */   public static void bind(AnnotationBindingContext bindingContext)
/*  26:    */   {
/*  27: 66 */     List<AnnotationInstance> annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.TABLE);
/*  28: 67 */     for (AnnotationInstance tableAnnotation : annotations) {
/*  29: 68 */       bind(bindingContext.getMetadataImplementor(), tableAnnotation);
/*  30:    */     }
/*  31: 71 */     annotations = bindingContext.getIndex().getAnnotations(HibernateDotNames.TABLES);
/*  32: 72 */     for (AnnotationInstance tables : annotations) {
/*  33: 73 */       for (AnnotationInstance table : (AnnotationInstance[])JandexHelper.getValue(tables, "value", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  34: 74 */         bind(bindingContext.getMetadataImplementor(), table);
/*  35:    */       }
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static void bind(MetadataImplementor metadata, AnnotationInstance tableAnnotation)
/*  40:    */   {
/*  41: 80 */     String tableName = (String)JandexHelper.getValue(tableAnnotation, "appliesTo", String.class);
/*  42: 81 */     ObjectName objectName = new ObjectName(tableName);
/*  43: 82 */     Schema schema = metadata.getDatabase().getSchema(objectName.getSchema(), objectName.getCatalog());
/*  44: 83 */     Table table = schema.locateTable(objectName.getName());
/*  45: 84 */     if (table != null) {
/*  46: 85 */       bindHibernateTableAnnotation(table, tableAnnotation);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   private static void bindHibernateTableAnnotation(Table table, AnnotationInstance tableAnnotation)
/*  51:    */   {
/*  52: 90 */     for (AnnotationInstance indexAnnotation : (AnnotationInstance[])JandexHelper.getValue(tableAnnotation, "indexes", [Lorg.jboss.jandex.AnnotationInstance.class)) {
/*  53: 95 */       bindIndexAnnotation(table, indexAnnotation);
/*  54:    */     }
/*  55: 97 */     String comment = (String)JandexHelper.getValue(tableAnnotation, "comment", String.class);
/*  56: 98 */     if (StringHelper.isNotEmpty(comment)) {
/*  57: 99 */       table.addComment(comment.trim());
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static void bindIndexAnnotation(Table table, AnnotationInstance indexAnnotation)
/*  62:    */   {
/*  63:104 */     String indexName = (String)JandexHelper.getValue(indexAnnotation, "appliesTo", String.class);
/*  64:105 */     String[] columnNames = (String[])JandexHelper.getValue(indexAnnotation, "columnNames", [Ljava.lang.String.class);
/*  65:106 */     if (columnNames == null)
/*  66:    */     {
/*  67:107 */       LOG.noColumnsSpecifiedForIndex(indexName, table.toLoggableString());
/*  68:108 */       return;
/*  69:    */     }
/*  70:110 */     org.hibernate.metamodel.relational.Index index = table.getOrCreateIndex(indexName);
/*  71:111 */     for (String columnName : columnNames)
/*  72:    */     {
/*  73:112 */       Column column = findColumn(table, columnName);
/*  74:113 */       if (column == null) {
/*  75:114 */         throw new AnnotationException("@Index references a unknown column: " + columnName);
/*  76:    */       }
/*  77:116 */       index.addColumn(column);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   private static Column findColumn(Table table, String columnName)
/*  82:    */   {
/*  83:121 */     Column column = null;
/*  84:122 */     for (SimpleValue value : table.values()) {
/*  85:123 */       if (((value instanceof Column)) && (((Column)value).getColumnName().getName().equals(columnName)))
/*  86:    */       {
/*  87:124 */         column = (Column)value;
/*  88:125 */         break;
/*  89:    */       }
/*  90:    */     }
/*  91:128 */     return column;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.global.TableBinder
 * JD-Core Version:    0.7.0.1
 */