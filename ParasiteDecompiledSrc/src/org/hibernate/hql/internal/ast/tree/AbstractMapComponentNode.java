/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.hql.internal.antlr.HqlSqlTokenTypes;
/*   7:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   8:    */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  11:    */ import org.hibernate.type.CollectionType;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ 
/*  14:    */ public abstract class AbstractMapComponentNode
/*  15:    */   extends FromReferenceNode
/*  16:    */   implements HqlSqlTokenTypes
/*  17:    */ {
/*  18:    */   private String[] columns;
/*  19:    */   
/*  20:    */   public FromReferenceNode getMapReference()
/*  21:    */   {
/*  22: 45 */     return (FromReferenceNode)getFirstChild();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String[] getColumns()
/*  26:    */   {
/*  27: 49 */     return this.columns;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setScalarColumnText(int i)
/*  31:    */     throws SemanticException
/*  32:    */   {
/*  33: 53 */     ColumnHelper.generateScalarColumns(this, getColumns(), i);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void resolve(boolean generateJoin, boolean implicitJoin, String classAlias, AST parent)
/*  37:    */     throws SemanticException
/*  38:    */   {
/*  39: 61 */     if (parent != null) {
/*  40: 62 */       throw attemptedDereference();
/*  41:    */     }
/*  42: 65 */     FromReferenceNode mapReference = getMapReference();
/*  43: 66 */     mapReference.resolve(true, true);
/*  44: 67 */     if (mapReference.getDataType().isCollectionType())
/*  45:    */     {
/*  46: 68 */       CollectionType collectionType = (CollectionType)mapReference.getDataType();
/*  47: 69 */       if (Map.class.isAssignableFrom(collectionType.getReturnedClass()))
/*  48:    */       {
/*  49: 70 */         FromElement sourceFromElement = mapReference.getFromElement();
/*  50: 71 */         setFromElement(sourceFromElement);
/*  51: 72 */         setDataType(resolveType(sourceFromElement.getQueryableCollection()));
/*  52: 73 */         this.columns = resolveColumns(sourceFromElement.getQueryableCollection());
/*  53: 74 */         initText(this.columns);
/*  54: 75 */         setFirstChild(null);
/*  55: 76 */         return;
/*  56:    */       }
/*  57:    */     }
/*  58: 80 */     throw nonMap();
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void initText(String[] columns)
/*  62:    */   {
/*  63: 84 */     String text = StringHelper.join(", ", columns);
/*  64: 85 */     if ((columns.length > 1) && (getWalker().isComparativeExpressionClause())) {
/*  65: 86 */       text = "(" + text + ")";
/*  66:    */     }
/*  67: 88 */     setText(text);
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected abstract String expressionDescription();
/*  71:    */   
/*  72:    */   protected abstract String[] resolveColumns(QueryableCollection paramQueryableCollection);
/*  73:    */   
/*  74:    */   protected abstract Type resolveType(QueryableCollection paramQueryableCollection);
/*  75:    */   
/*  76:    */   protected SemanticException attemptedDereference()
/*  77:    */   {
/*  78: 96 */     return new SemanticException(expressionDescription() + " expression cannot be further de-referenced");
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected SemanticException nonMap()
/*  82:    */   {
/*  83:100 */     return new SemanticException(expressionDescription() + " expression did not reference map property");
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void resolveIndex(AST parent)
/*  87:    */     throws SemanticException
/*  88:    */   {
/*  89:104 */     throw new UnsupportedOperationException(expressionDescription() + " expression cannot be the source for an index operation");
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AbstractMapComponentNode
 * JD-Core Version:    0.7.0.1
 */