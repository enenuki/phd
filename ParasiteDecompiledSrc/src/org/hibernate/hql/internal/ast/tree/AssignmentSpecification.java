/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.QueryException;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.hql.internal.ast.SqlGenerator;
/*  12:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  13:    */ import org.hibernate.hql.internal.ast.util.ASTUtil.IncludePredicate;
/*  14:    */ import org.hibernate.param.ParameterSpecification;
/*  15:    */ import org.hibernate.persister.entity.Queryable;
/*  16:    */ import org.hibernate.persister.entity.UnionSubclassEntityPersister;
/*  17:    */ import org.hibernate.type.Type;
/*  18:    */ 
/*  19:    */ public class AssignmentSpecification
/*  20:    */ {
/*  21:    */   private final Set tableNames;
/*  22:    */   private final ParameterSpecification[] hqlParameters;
/*  23:    */   private final AST eq;
/*  24:    */   private final SessionFactoryImplementor factory;
/*  25:    */   private String sqlAssignmentString;
/*  26:    */   
/*  27:    */   public AssignmentSpecification(AST eq, Queryable persister)
/*  28:    */   {
/*  29: 60 */     if (eq.getType() != 102) {
/*  30: 61 */       throw new QueryException("assignment in set-clause not associated with equals");
/*  31:    */     }
/*  32: 64 */     this.eq = eq;
/*  33: 65 */     this.factory = persister.getFactory();
/*  34:    */     
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39: 71 */     DotNode lhs = (DotNode)eq.getFirstChild();
/*  40: 72 */     SqlNode rhs = (SqlNode)lhs.getNextSibling();
/*  41:    */     
/*  42: 74 */     validateLhs(lhs);
/*  43:    */     
/*  44: 76 */     String propertyPath = lhs.getPropertyPath();
/*  45: 77 */     Set temp = new HashSet();
/*  46: 79 */     if ((persister instanceof UnionSubclassEntityPersister))
/*  47:    */     {
/*  48: 80 */       UnionSubclassEntityPersister usep = (UnionSubclassEntityPersister)persister;
/*  49: 81 */       String[] tables = persister.getConstraintOrderedTableNameClosure();
/*  50: 82 */       int size = tables.length;
/*  51: 83 */       for (int i = 0; i < size; i++) {
/*  52: 84 */         temp.add(tables[i]);
/*  53:    */       }
/*  54:    */     }
/*  55:    */     else
/*  56:    */     {
/*  57: 88 */       temp.add(persister.getSubclassTableName(persister.getSubclassPropertyTableNumber(propertyPath)));
/*  58:    */     }
/*  59: 92 */     this.tableNames = Collections.unmodifiableSet(temp);
/*  60: 94 */     if (rhs == null)
/*  61:    */     {
/*  62: 95 */       this.hqlParameters = new ParameterSpecification[0];
/*  63:    */     }
/*  64: 97 */     else if (isParam(rhs))
/*  65:    */     {
/*  66: 98 */       this.hqlParameters = new ParameterSpecification[] { ((ParameterNode)rhs).getHqlParameterSpecification() };
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70:101 */       List parameterList = ASTUtil.collectChildren(rhs, new ASTUtil.IncludePredicate()
/*  71:    */       {
/*  72:    */         public boolean include(AST node)
/*  73:    */         {
/*  74:105 */           return AssignmentSpecification.isParam(node);
/*  75:    */         }
/*  76:108 */       });
/*  77:109 */       this.hqlParameters = new ParameterSpecification[parameterList.size()];
/*  78:110 */       Iterator itr = parameterList.iterator();
/*  79:111 */       int i = 0;
/*  80:112 */       while (itr.hasNext()) {
/*  81:113 */         this.hqlParameters[(i++)] = ((ParameterNode)itr.next()).getHqlParameterSpecification();
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean affectsTable(String tableName)
/*  87:    */   {
/*  88:119 */     return this.tableNames.contains(tableName);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public ParameterSpecification[] getParameters()
/*  92:    */   {
/*  93:123 */     return this.hqlParameters;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getSqlAssignmentFragment()
/*  97:    */   {
/*  98:127 */     if (this.sqlAssignmentString == null) {
/*  99:    */       try
/* 100:    */       {
/* 101:129 */         SqlGenerator sqlGenerator = new SqlGenerator(this.factory);
/* 102:130 */         sqlGenerator.comparisonExpr(this.eq, false);
/* 103:131 */         this.sqlAssignmentString = sqlGenerator.getSQL();
/* 104:    */       }
/* 105:    */       catch (Throwable t)
/* 106:    */       {
/* 107:134 */         throw new QueryException("cannot interpret set-clause assignment");
/* 108:    */       }
/* 109:    */     }
/* 110:137 */     return this.sqlAssignmentString;
/* 111:    */   }
/* 112:    */   
/* 113:    */   private static boolean isParam(AST node)
/* 114:    */   {
/* 115:141 */     return (node.getType() == 123) || (node.getType() == 148);
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void validateLhs(FromReferenceNode lhs)
/* 119:    */   {
/* 120:146 */     if (!lhs.isResolved()) {
/* 121:147 */       throw new UnsupportedOperationException("cannot validate assignablity of unresolved node");
/* 122:    */     }
/* 123:150 */     if (lhs.getDataType().isCollectionType()) {
/* 124:151 */       throw new QueryException("collections not assignable in update statements");
/* 125:    */     }
/* 126:153 */     if (lhs.getDataType().isComponentType()) {
/* 127:154 */       throw new QueryException("Components currently not assignable in update statements");
/* 128:    */     }
/* 129:156 */     if ((!lhs.getDataType().isEntityType()) || (
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:161 */       (lhs.getImpliedJoin() != null) || (lhs.getFromElement().isImplied()))) {
/* 135:162 */       throw new QueryException("Implied join paths are not assignable in update statements");
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AssignmentSpecification
 * JD-Core Version:    0.7.0.1
 */