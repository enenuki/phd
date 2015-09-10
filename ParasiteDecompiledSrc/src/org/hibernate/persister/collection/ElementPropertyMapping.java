/*  1:   */ package org.hibernate.persister.collection;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.persister.entity.PropertyMapping;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class ElementPropertyMapping
/* 10:   */   implements PropertyMapping
/* 11:   */ {
/* 12:   */   private final String[] elementColumns;
/* 13:   */   private final Type type;
/* 14:   */   
/* 15:   */   public ElementPropertyMapping(String[] elementColumns, Type type)
/* 16:   */     throws MappingException
/* 17:   */   {
/* 18:42 */     this.elementColumns = elementColumns;
/* 19:43 */     this.type = type;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Type toType(String propertyName)
/* 23:   */     throws QueryException
/* 24:   */   {
/* 25:47 */     if ((propertyName == null) || ("id".equals(propertyName))) {
/* 26:48 */       return this.type;
/* 27:   */     }
/* 28:51 */     throw new QueryException("cannot dereference scalar collection element: " + propertyName);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String[] toColumns(String alias, String propertyName)
/* 32:   */     throws QueryException
/* 33:   */   {
/* 34:56 */     if ((propertyName == null) || ("id".equals(propertyName))) {
/* 35:57 */       return StringHelper.qualify(alias, this.elementColumns);
/* 36:   */     }
/* 37:60 */     throw new QueryException("cannot dereference scalar collection element: " + propertyName);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String[] toColumns(String propertyName)
/* 41:   */     throws QueryException, UnsupportedOperationException
/* 42:   */   {
/* 43:68 */     throw new UnsupportedOperationException("References to collections must be define a SQL alias");
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Type getType()
/* 47:   */   {
/* 48:72 */     return this.type;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.ElementPropertyMapping
 * JD-Core Version:    0.7.0.1
 */