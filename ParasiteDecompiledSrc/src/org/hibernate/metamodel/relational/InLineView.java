/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import org.hibernate.dialect.Dialect;
/*   5:    */ 
/*   6:    */ public class InLineView
/*   7:    */   extends AbstractTableSpecification
/*   8:    */ {
/*   9:    */   private final Schema schema;
/*  10:    */   private final String logicalName;
/*  11:    */   private final String select;
/*  12:    */   
/*  13:    */   public InLineView(Schema schema, String logicalName, String select)
/*  14:    */   {
/*  15: 43 */     this.schema = schema;
/*  16: 44 */     this.logicalName = logicalName;
/*  17: 45 */     this.select = select;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Schema getSchema()
/*  21:    */   {
/*  22: 49 */     return this.schema;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String getSelect()
/*  26:    */   {
/*  27: 53 */     return this.select;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getLoggableValueQualifier()
/*  31:    */   {
/*  32: 58 */     return this.logicalName;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Iterable<Index> getIndexes()
/*  36:    */   {
/*  37: 63 */     return Collections.emptyList();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Index getOrCreateIndex(String name)
/*  41:    */   {
/*  42: 68 */     throw new UnsupportedOperationException("Cannot create index on inline view");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Iterable<UniqueKey> getUniqueKeys()
/*  46:    */   {
/*  47: 73 */     return Collections.emptyList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public UniqueKey getOrCreateUniqueKey(String name)
/*  51:    */   {
/*  52: 78 */     throw new UnsupportedOperationException("Cannot create unique-key on inline view");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Iterable<CheckConstraint> getCheckConstraints()
/*  56:    */   {
/*  57: 83 */     return Collections.emptyList();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addCheckConstraint(String checkCondition)
/*  61:    */   {
/*  62: 88 */     throw new UnsupportedOperationException("Cannot create check constraint on inline view");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Iterable<String> getComments()
/*  66:    */   {
/*  67: 93 */     return Collections.emptyList();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void addComment(String comment)
/*  71:    */   {
/*  72: 98 */     throw new UnsupportedOperationException("Cannot comment on inline view");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getQualifiedName(Dialect dialect)
/*  76:    */   {
/*  77:103 */     return this.select.length() + 4 + "( " + this.select + " )";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String toLoggableString()
/*  81:    */   {
/*  82:112 */     return "{inline-view}";
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.InLineView
 * JD-Core Version:    0.7.0.1
 */