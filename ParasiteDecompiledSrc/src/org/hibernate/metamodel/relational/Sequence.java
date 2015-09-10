/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ 
/*  6:   */ public class Sequence
/*  7:   */   implements Exportable
/*  8:   */ {
/*  9:   */   private final Schema schema;
/* 10:   */   private final String name;
/* 11:   */   private final String qualifiedName;
/* 12:38 */   private int initialValue = 1;
/* 13:39 */   private int incrementSize = 1;
/* 14:   */   
/* 15:   */   public Sequence(Schema schema, String name)
/* 16:   */   {
/* 17:42 */     this.schema = schema;
/* 18:43 */     this.name = name;
/* 19:44 */     this.qualifiedName = new ObjectName(schema, name).toText();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Sequence(Schema schema, String name, int initialValue, int incrementSize)
/* 23:   */   {
/* 24:48 */     this(schema, name);
/* 25:49 */     this.initialValue = initialValue;
/* 26:50 */     this.incrementSize = incrementSize;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Schema getSchema()
/* 30:   */   {
/* 31:54 */     return this.schema;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getName()
/* 35:   */   {
/* 36:58 */     return this.name;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getExportIdentifier()
/* 40:   */   {
/* 41:63 */     return this.qualifiedName;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getInitialValue()
/* 45:   */   {
/* 46:67 */     return this.initialValue;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public int getIncrementSize()
/* 50:   */   {
/* 51:71 */     return this.incrementSize;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String[] sqlCreateStrings(Dialect dialect)
/* 55:   */     throws MappingException
/* 56:   */   {
/* 57:76 */     return dialect.getCreateSequenceStrings(this.name, this.initialValue, this.incrementSize);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String[] sqlDropStrings(Dialect dialect)
/* 61:   */     throws MappingException
/* 62:   */   {
/* 63:81 */     return dialect.getDropSequenceStrings(this.name);
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Sequence
 * JD-Core Version:    0.7.0.1
 */