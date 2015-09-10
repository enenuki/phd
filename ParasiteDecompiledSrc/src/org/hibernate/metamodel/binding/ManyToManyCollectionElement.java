/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.dom4j.Element;
/*  6:   */ 
/*  7:   */ public class ManyToManyCollectionElement
/*  8:   */   extends AbstractCollectionElement
/*  9:   */ {
/* 10:36 */   private final Map manyToManyFilters = new HashMap();
/* 11:   */   private String manyToManyWhere;
/* 12:   */   private String manyToManyOrderBy;
/* 13:   */   
/* 14:   */   ManyToManyCollectionElement(AbstractPluralAttributeBinding binding)
/* 15:   */   {
/* 16:42 */     super(binding);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public CollectionElementNature getCollectionElementNature()
/* 20:   */   {
/* 21:47 */     return CollectionElementNature.MANY_TO_MANY;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void fromHbmXml(Element node) {}
/* 25:   */   
/* 26:   */   public String getManyToManyWhere()
/* 27:   */   {
/* 28:72 */     return this.manyToManyWhere;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setManyToManyWhere(String manyToManyWhere)
/* 32:   */   {
/* 33:76 */     this.manyToManyWhere = manyToManyWhere;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String getManyToManyOrderBy()
/* 37:   */   {
/* 38:80 */     return this.manyToManyOrderBy;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void setManyToManyOrderBy(String manyToManyOrderBy)
/* 42:   */   {
/* 43:84 */     this.manyToManyOrderBy = manyToManyOrderBy;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.ManyToManyCollectionElement
 * JD-Core Version:    0.7.0.1
 */