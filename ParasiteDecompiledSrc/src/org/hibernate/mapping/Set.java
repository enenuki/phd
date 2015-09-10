/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.cfg.Mappings;
/*   6:    */ import org.hibernate.engine.spi.Mapping;
/*   7:    */ import org.hibernate.type.CollectionType;
/*   8:    */ import org.hibernate.type.TypeFactory;
/*   9:    */ import org.hibernate.type.TypeResolver;
/*  10:    */ 
/*  11:    */ public class Set
/*  12:    */   extends Collection
/*  13:    */ {
/*  14:    */   public void validate(Mapping mapping)
/*  15:    */     throws MappingException
/*  16:    */   {
/*  17: 40 */     super.validate(mapping);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Set(Mappings mappings, PersistentClass owner)
/*  21:    */   {
/*  22: 53 */     super(mappings, owner);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean isSet()
/*  26:    */   {
/*  27: 57 */     return true;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CollectionType getDefaultCollectionType()
/*  31:    */   {
/*  32: 61 */     if (isSorted()) {
/*  33: 62 */       return getMappings().getTypeResolver().getTypeFactory().sortedSet(getRole(), getReferencedPropertyName(), isEmbedded(), getComparator());
/*  34:    */     }
/*  35: 66 */     if (hasOrder()) {
/*  36: 67 */       return getMappings().getTypeResolver().getTypeFactory().orderedSet(getRole(), getReferencedPropertyName(), isEmbedded());
/*  37:    */     }
/*  38: 72 */     return getMappings().getTypeResolver().getTypeFactory().set(getRole(), getReferencedPropertyName(), isEmbedded());
/*  39:    */   }
/*  40:    */   
/*  41:    */   void createPrimaryKey()
/*  42:    */   {
/*  43: 79 */     if (!isOneToMany())
/*  44:    */     {
/*  45: 80 */       PrimaryKey pk = new PrimaryKey();
/*  46: 81 */       pk.addColumns(getKey().getColumnIterator());
/*  47: 82 */       Iterator iter = getElement().getColumnIterator();
/*  48: 83 */       while (iter.hasNext())
/*  49:    */       {
/*  50: 84 */         Object selectable = iter.next();
/*  51: 85 */         if ((selectable instanceof Column))
/*  52:    */         {
/*  53: 86 */           Column col = (Column)selectable;
/*  54: 87 */           if (!col.isNullable()) {
/*  55: 88 */             pk.addColumn(col);
/*  56:    */           }
/*  57:    */         }
/*  58:    */       }
/*  59: 92 */       if (pk.getColumnSpan() != getKey().getColumnSpan()) {
/*  60: 98 */         getCollectionTable().setPrimaryKey(pk);
/*  61:    */       }
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object accept(ValueVisitor visitor)
/*  66:    */   {
/*  67:107 */     return visitor.accept(this);
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Set
 * JD-Core Version:    0.7.0.1
 */