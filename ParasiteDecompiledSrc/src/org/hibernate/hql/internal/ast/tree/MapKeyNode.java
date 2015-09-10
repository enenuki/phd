/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import org.hibernate.persister.collection.QueryableCollection;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public class MapKeyNode
/*  7:   */   extends AbstractMapComponentNode
/*  8:   */ {
/*  9:   */   protected String expressionDescription()
/* 10:   */   {
/* 11:33 */     return "key(*)";
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected String[] resolveColumns(QueryableCollection collectionPersister)
/* 15:   */   {
/* 16:37 */     return collectionPersister.getIndexColumnNames();
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected Type resolveType(QueryableCollection collectionPersister)
/* 20:   */   {
/* 21:41 */     return collectionPersister.getIndexType();
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.MapKeyNode
 * JD-Core Version:    0.7.0.1
 */