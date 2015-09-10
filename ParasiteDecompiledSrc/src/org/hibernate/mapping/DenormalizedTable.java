/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  10:    */ 
/*  11:    */ public class DenormalizedTable
/*  12:    */   extends Table
/*  13:    */ {
/*  14:    */   private final Table includedTable;
/*  15:    */   
/*  16:    */   public DenormalizedTable(Table includedTable)
/*  17:    */   {
/*  18: 41 */     this.includedTable = includedTable;
/*  19: 42 */     includedTable.setHasDenormalizedTables();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void createForeignKeys()
/*  23:    */   {
/*  24: 47 */     this.includedTable.createForeignKeys();
/*  25: 48 */     Iterator iter = this.includedTable.getForeignKeyIterator();
/*  26: 49 */     while (iter.hasNext())
/*  27:    */     {
/*  28: 50 */       ForeignKey fk = (ForeignKey)iter.next();
/*  29: 51 */       createForeignKey(fk.getName() + Integer.toHexString(getName().hashCode()), fk.getColumns(), fk.getReferencedEntityName());
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Column getColumn(Column column)
/*  34:    */   {
/*  35: 61 */     Column superColumn = super.getColumn(column);
/*  36: 62 */     if (superColumn != null) {
/*  37: 63 */       return superColumn;
/*  38:    */     }
/*  39: 66 */     return this.includedTable.getColumn(column);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Iterator getColumnIterator()
/*  43:    */   {
/*  44: 72 */     return new JoinedIterator(this.includedTable.getColumnIterator(), super.getColumnIterator());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean containsColumn(Column column)
/*  48:    */   {
/*  49: 80 */     return (super.containsColumn(column)) || (this.includedTable.containsColumn(column));
/*  50:    */   }
/*  51:    */   
/*  52:    */   public PrimaryKey getPrimaryKey()
/*  53:    */   {
/*  54: 85 */     return this.includedTable.getPrimaryKey();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Iterator getUniqueKeyIterator()
/*  58:    */   {
/*  59: 94 */     Map uks = new HashMap();
/*  60: 95 */     uks.putAll(getUniqueKeys());
/*  61: 96 */     uks.putAll(this.includedTable.getUniqueKeys());
/*  62: 97 */     return uks.values().iterator();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Iterator getIndexIterator()
/*  66:    */   {
/*  67:102 */     List indexes = new ArrayList();
/*  68:103 */     Iterator iter = this.includedTable.getIndexIterator();
/*  69:104 */     while (iter.hasNext())
/*  70:    */     {
/*  71:105 */       Index parentIndex = (Index)iter.next();
/*  72:106 */       Index index = new Index();
/*  73:107 */       index.setName(getName() + parentIndex.getName());
/*  74:108 */       index.setTable(this);
/*  75:109 */       index.addColumns(parentIndex.getColumnIterator());
/*  76:110 */       indexes.add(index);
/*  77:    */     }
/*  78:112 */     return new JoinedIterator(indexes.iterator(), super.getIndexIterator());
/*  79:    */   }
/*  80:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.DenormalizedTable
 * JD-Core Version:    0.7.0.1
 */