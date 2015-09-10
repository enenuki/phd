/*  1:   */ package org.hibernate.persister.collection;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.engine.spi.Mapping;
/*  5:   */ import org.hibernate.persister.entity.AbstractPropertyMapping;
/*  6:   */ import org.hibernate.type.CompositeType;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class CompositeElementPropertyMapping
/* 10:   */   extends AbstractPropertyMapping
/* 11:   */ {
/* 12:   */   private final CompositeType compositeType;
/* 13:   */   
/* 14:   */   public CompositeElementPropertyMapping(String[] elementColumns, String[] elementColumnReaders, String[] elementColumnReaderTemplates, String[] elementFormulaTemplates, CompositeType compositeType, Mapping factory)
/* 15:   */     throws MappingException
/* 16:   */   {
/* 17:47 */     this.compositeType = compositeType;
/* 18:   */     
/* 19:49 */     initComponentPropertyPaths(null, compositeType, elementColumns, elementColumnReaders, elementColumnReaderTemplates, elementFormulaTemplates, factory);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Type getType()
/* 23:   */   {
/* 24:55 */     return this.compositeType;
/* 25:   */   }
/* 26:   */   
/* 27:   */   protected String getEntityName()
/* 28:   */   {
/* 29:59 */     return this.compositeType.getName();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.CompositeElementPropertyMapping
 * JD-Core Version:    0.7.0.1
 */