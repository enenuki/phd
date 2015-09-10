/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Set;
/*  5:   */ import org.hibernate.MappingException;
/*  6:   */ import org.hibernate.engine.spi.Mapping;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class UnionSubclass
/* 10:   */   extends Subclass
/* 11:   */   implements TableOwner
/* 12:   */ {
/* 13:   */   private Table table;
/* 14:   */   private KeyValue key;
/* 15:   */   
/* 16:   */   public UnionSubclass(PersistentClass superclass)
/* 17:   */   {
/* 18:40 */     super(superclass);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Table getTable()
/* 22:   */   {
/* 23:44 */     return this.table;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setTable(Table table)
/* 27:   */   {
/* 28:48 */     this.table = table;
/* 29:49 */     getSuperclass().addSubclassTable(table);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Set getSynchronizedTables()
/* 33:   */   {
/* 34:53 */     return this.synchronizedTables;
/* 35:   */   }
/* 36:   */   
/* 37:   */   protected Iterator getNonDuplicatedPropertyIterator()
/* 38:   */   {
/* 39:57 */     return getPropertyClosureIterator();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void validate(Mapping mapping)
/* 43:   */     throws MappingException
/* 44:   */   {
/* 45:61 */     super.validate(mapping);
/* 46:62 */     if ((this.key != null) && (!this.key.isValid(mapping))) {
/* 47:63 */       throw new MappingException("subclass key mapping has wrong number of columns: " + getEntityName() + " type: " + this.key.getType().getName());
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Table getIdentityTable()
/* 52:   */   {
/* 53:73 */     return getTable();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public Object accept(PersistentClassVisitor mv)
/* 57:   */   {
/* 58:77 */     return mv.accept(this);
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.UnionSubclass
 * JD-Core Version:    0.7.0.1
 */