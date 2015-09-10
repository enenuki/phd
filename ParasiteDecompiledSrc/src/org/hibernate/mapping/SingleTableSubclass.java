/*  1:   */ package org.hibernate.mapping;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.hibernate.MappingException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.internal.util.collections.JoinedIterator;
/*  7:   */ 
/*  8:   */ public class SingleTableSubclass
/*  9:   */   extends Subclass
/* 10:   */ {
/* 11:   */   public SingleTableSubclass(PersistentClass superclass)
/* 12:   */   {
/* 13:37 */     super(superclass);
/* 14:   */   }
/* 15:   */   
/* 16:   */   protected Iterator getNonDuplicatedPropertyIterator()
/* 17:   */   {
/* 18:41 */     return new JoinedIterator(getSuperclass().getUnjoinedPropertyIterator(), getUnjoinedPropertyIterator());
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected Iterator getDiscriminatorColumnIterator()
/* 22:   */   {
/* 23:48 */     if ((isDiscriminatorInsertable()) && (!getDiscriminator().hasFormula())) {
/* 24:49 */       return getDiscriminator().getColumnIterator();
/* 25:   */     }
/* 26:52 */     return super.getDiscriminatorColumnIterator();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Object accept(PersistentClassVisitor mv)
/* 30:   */   {
/* 31:57 */     return mv.accept(this);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void validate(Mapping mapping)
/* 35:   */     throws MappingException
/* 36:   */   {
/* 37:61 */     if (getDiscriminator() == null) {
/* 38:62 */       throw new MappingException("No discriminator found for " + getEntityName() + ". Discriminator is needed when 'single-table-per-hierarchy' is used and a class has subclasses");
/* 39:   */     }
/* 40:64 */     super.validate(mapping);
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.SingleTableSubclass
 * JD-Core Version:    0.7.0.1
 */