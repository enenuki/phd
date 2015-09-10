/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.LinkedHashMap;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.concurrent.atomic.AtomicInteger;
/*  7:   */ 
/*  8:   */ public abstract class AbstractTableSpecification
/*  9:   */   implements TableSpecification
/* 10:   */ {
/* 11:38 */   private static final AtomicInteger tableCounter = new AtomicInteger(0);
/* 12:   */   private final int tableNumber;
/* 13:41 */   private final LinkedHashMap<String, SimpleValue> values = new LinkedHashMap();
/* 14:43 */   private final PrimaryKey primaryKey = new PrimaryKey(this);
/* 15:44 */   private final List<ForeignKey> foreignKeys = new ArrayList();
/* 16:   */   
/* 17:   */   public AbstractTableSpecification()
/* 18:   */   {
/* 19:47 */     this.tableNumber = tableCounter.getAndIncrement();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getTableNumber()
/* 23:   */   {
/* 24:52 */     return this.tableNumber;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Iterable<SimpleValue> values()
/* 28:   */   {
/* 29:57 */     return this.values.values();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Column locateOrCreateColumn(String name)
/* 33:   */   {
/* 34:62 */     if (this.values.containsKey(name)) {
/* 35:63 */       return (Column)this.values.get(name);
/* 36:   */     }
/* 37:65 */     Column column = new Column(this, this.values.size(), name);
/* 38:66 */     this.values.put(name, column);
/* 39:67 */     return column;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public DerivedValue locateOrCreateDerivedValue(String fragment)
/* 43:   */   {
/* 44:72 */     if (this.values.containsKey(fragment)) {
/* 45:73 */       return (DerivedValue)this.values.get(fragment);
/* 46:   */     }
/* 47:75 */     DerivedValue value = new DerivedValue(this, this.values.size(), fragment);
/* 48:76 */     this.values.put(fragment, value);
/* 49:77 */     return value;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Tuple createTuple(String name)
/* 53:   */   {
/* 54:82 */     return new Tuple(this, name);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Iterable<ForeignKey> getForeignKeys()
/* 58:   */   {
/* 59:87 */     return this.foreignKeys;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public ForeignKey createForeignKey(TableSpecification targetTable, String name)
/* 63:   */   {
/* 64:92 */     ForeignKey fk = new ForeignKey(this, targetTable, name);
/* 65:93 */     this.foreignKeys.add(fk);
/* 66:94 */     return fk;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public PrimaryKey getPrimaryKey()
/* 70:   */   {
/* 71:99 */     return this.primaryKey;
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.AbstractTableSpecification
 * JD-Core Version:    0.7.0.1
 */