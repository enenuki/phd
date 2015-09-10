/*   1:    */ package org.hibernate.persister.collection;
/*   2:    */ 
/*   3:    */ import org.hibernate.QueryException;
/*   4:    */ import org.hibernate.persister.entity.PropertyMapping;
/*   5:    */ import org.hibernate.type.StandardBasicTypes;
/*   6:    */ import org.hibernate.type.Type;
/*   7:    */ 
/*   8:    */ public class CollectionPropertyMapping
/*   9:    */   implements PropertyMapping
/*  10:    */ {
/*  11:    */   private final QueryableCollection memberPersister;
/*  12:    */   
/*  13:    */   public CollectionPropertyMapping(QueryableCollection memberPersister)
/*  14:    */   {
/*  15: 39 */     this.memberPersister = memberPersister;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Type toType(String propertyName)
/*  19:    */     throws QueryException
/*  20:    */   {
/*  21: 43 */     if (propertyName.equals("elements")) {
/*  22: 44 */       return this.memberPersister.getElementType();
/*  23:    */     }
/*  24: 46 */     if (propertyName.equals("indices"))
/*  25:    */     {
/*  26: 47 */       if (!this.memberPersister.hasIndex()) {
/*  27: 47 */         throw new QueryException("unindexed collection before indices()");
/*  28:    */       }
/*  29: 48 */       return this.memberPersister.getIndexType();
/*  30:    */     }
/*  31: 50 */     if (propertyName.equals("size")) {
/*  32: 51 */       return StandardBasicTypes.INTEGER;
/*  33:    */     }
/*  34: 53 */     if (propertyName.equals("maxIndex")) {
/*  35: 54 */       return this.memberPersister.getIndexType();
/*  36:    */     }
/*  37: 56 */     if (propertyName.equals("minIndex")) {
/*  38: 57 */       return this.memberPersister.getIndexType();
/*  39:    */     }
/*  40: 59 */     if (propertyName.equals("maxElement")) {
/*  41: 60 */       return this.memberPersister.getElementType();
/*  42:    */     }
/*  43: 62 */     if (propertyName.equals("minElement")) {
/*  44: 63 */       return this.memberPersister.getElementType();
/*  45:    */     }
/*  46: 67 */     throw new QueryException("illegal syntax near collection: " + propertyName);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String[] toColumns(String alias, String propertyName)
/*  50:    */     throws QueryException
/*  51:    */   {
/*  52: 72 */     if (propertyName.equals("elements")) {
/*  53: 73 */       return this.memberPersister.getElementColumnNames(alias);
/*  54:    */     }
/*  55: 75 */     if (propertyName.equals("indices"))
/*  56:    */     {
/*  57: 76 */       if (!this.memberPersister.hasIndex()) {
/*  58: 76 */         throw new QueryException("unindexed collection in indices()");
/*  59:    */       }
/*  60: 77 */       return this.memberPersister.getIndexColumnNames(alias);
/*  61:    */     }
/*  62: 79 */     if (propertyName.equals("size"))
/*  63:    */     {
/*  64: 80 */       String[] cols = this.memberPersister.getKeyColumnNames();
/*  65: 81 */       return new String[] { "count(" + alias + '.' + cols[0] + ')' };
/*  66:    */     }
/*  67: 83 */     if (propertyName.equals("maxIndex"))
/*  68:    */     {
/*  69: 84 */       if (!this.memberPersister.hasIndex()) {
/*  70: 84 */         throw new QueryException("unindexed collection in maxIndex()");
/*  71:    */       }
/*  72: 85 */       String[] cols = this.memberPersister.getIndexColumnNames(alias);
/*  73: 86 */       if (cols.length != 1) {
/*  74: 86 */         throw new QueryException("composite collection index in maxIndex()");
/*  75:    */       }
/*  76: 87 */       return new String[] { "max(" + cols[0] + ')' };
/*  77:    */     }
/*  78: 89 */     if (propertyName.equals("minIndex"))
/*  79:    */     {
/*  80: 90 */       if (!this.memberPersister.hasIndex()) {
/*  81: 90 */         throw new QueryException("unindexed collection in minIndex()");
/*  82:    */       }
/*  83: 91 */       String[] cols = this.memberPersister.getIndexColumnNames(alias);
/*  84: 92 */       if (cols.length != 1) {
/*  85: 92 */         throw new QueryException("composite collection index in minIndex()");
/*  86:    */       }
/*  87: 93 */       return new String[] { "min(" + cols[0] + ')' };
/*  88:    */     }
/*  89: 95 */     if (propertyName.equals("maxElement"))
/*  90:    */     {
/*  91: 96 */       String[] cols = this.memberPersister.getElementColumnNames(alias);
/*  92: 97 */       if (cols.length != 1) {
/*  93: 97 */         throw new QueryException("composite collection element in maxElement()");
/*  94:    */       }
/*  95: 98 */       return new String[] { "max(" + cols[0] + ')' };
/*  96:    */     }
/*  97:100 */     if (propertyName.equals("minElement"))
/*  98:    */     {
/*  99:101 */       String[] cols = this.memberPersister.getElementColumnNames(alias);
/* 100:102 */       if (cols.length != 1) {
/* 101:102 */         throw new QueryException("composite collection element in minElement()");
/* 102:    */       }
/* 103:103 */       return new String[] { "min(" + cols[0] + ')' };
/* 104:    */     }
/* 105:107 */     throw new QueryException("illegal syntax near collection: " + propertyName);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String[] toColumns(String propertyName)
/* 109:    */     throws QueryException, UnsupportedOperationException
/* 110:    */   {
/* 111:115 */     throw new UnsupportedOperationException("References to collections must be define a SQL alias");
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Type getType()
/* 115:    */   {
/* 116:120 */     return this.memberPersister.getCollectionType();
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.CollectionPropertyMapping
 * JD-Core Version:    0.7.0.1
 */