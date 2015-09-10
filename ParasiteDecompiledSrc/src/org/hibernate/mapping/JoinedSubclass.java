/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class JoinedSubclass
/*  9:   */   extends Subclass
/* 10:   */   implements TableOwner
/* 11:   */ {
/* 12:   */   private Table table;
/* 13:   */   private KeyValue key;
/* 14:   */   
/* 15:   */   public JoinedSubclass(PersistentClass superclass)
/* 16:   */   {
/* 17:40 */     super(superclass);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Table getTable()
/* 21:   */   {
/* 22:44 */     return this.table;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setTable(Table table)
/* 26:   */   {
/* 27:48 */     this.table = table;
/* 28:49 */     getSuperclass().addSubclassTable(table);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public KeyValue getKey()
/* 32:   */   {
/* 33:53 */     return this.key;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setKey(KeyValue key)
/* 37:   */   {
/* 38:57 */     this.key = key;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void validate(Mapping mapping)
/* 42:   */     throws MappingException
/* 43:   */   {
/* 44:61 */     super.validate(mapping);
/* 45:62 */     if ((this.key != null) && (!this.key.isValid(mapping))) {
/* 46:63 */       throw new MappingException("subclass key mapping has wrong number of columns: " + getEntityName() + " type: " + this.key.getType().getName());
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Iterator getReferenceablePropertyIterator()
/* 51:   */   {
/* 52:73 */     return getPropertyIterator();
/* 53:   */   }
/* 54:   */   
/* 55:   */   public Object accept(PersistentClassVisitor mv)
/* 56:   */   {
/* 57:77 */     return mv.accept(this);
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.JoinedSubclass
 * JD-Core Version:    0.7.0.1
 */