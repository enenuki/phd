/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*   6:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*   7:    */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*   8:    */ import org.hibernate.internal.CoreMessageLogger;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ import org.jboss.logging.Logger;
/*  11:    */ 
/*  12:    */ public class QueryNode
/*  13:    */   extends AbstractRestrictableStatement
/*  14:    */   implements SelectExpression
/*  15:    */ {
/*  16: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, QueryNode.class.getName());
/*  17:    */   private OrderByClause orderByClause;
/*  18: 47 */   private int scalarColumnIndex = -1;
/*  19:    */   private String alias;
/*  20:    */   
/*  21:    */   public int getStatementType()
/*  22:    */   {
/*  23: 53 */     return 86;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean needsExecutor()
/*  27:    */   {
/*  28: 60 */     return false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected int getWhereClauseParentTokenType()
/*  32:    */   {
/*  33: 65 */     return 22;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected CoreMessageLogger getLog()
/*  37:    */   {
/*  38: 70 */     return LOG;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public final SelectClause getSelectClause()
/*  42:    */   {
/*  43: 87 */     return (SelectClause)ASTUtil.findTypeInChildren(this, 137);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public final boolean hasOrderByClause()
/*  47:    */   {
/*  48: 91 */     OrderByClause orderByClause = locateOrderByClause();
/*  49: 92 */     return (orderByClause != null) && (orderByClause.getNumberOfChildren() > 0);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final OrderByClause getOrderByClause()
/*  53:    */   {
/*  54: 96 */     if (this.orderByClause == null)
/*  55:    */     {
/*  56: 97 */       this.orderByClause = locateOrderByClause();
/*  57:100 */       if (this.orderByClause == null)
/*  58:    */       {
/*  59:101 */         LOG.debug("getOrderByClause() : Creating a new ORDER BY clause");
/*  60:102 */         this.orderByClause = ((OrderByClause)ASTUtil.create(getWalker().getASTFactory(), 41, "ORDER"));
/*  61:    */         
/*  62:    */ 
/*  63:105 */         AST prevSibling = ASTUtil.findTypeInChildren(this, 53);
/*  64:106 */         if (prevSibling == null) {
/*  65:107 */           prevSibling = ASTUtil.findTypeInChildren(this, 22);
/*  66:    */         }
/*  67:111 */         this.orderByClause.setNextSibling(prevSibling.getNextSibling());
/*  68:112 */         prevSibling.setNextSibling(this.orderByClause);
/*  69:    */       }
/*  70:    */     }
/*  71:115 */     return this.orderByClause;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private OrderByClause locateOrderByClause()
/*  75:    */   {
/*  76:119 */     return (OrderByClause)ASTUtil.findTypeInChildren(this, 41);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getAlias()
/*  80:    */   {
/*  81:126 */     return this.alias;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public FromElement getFromElement()
/*  85:    */   {
/*  86:130 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isConstructor()
/*  90:    */   {
/*  91:134 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isReturnableEntity()
/*  95:    */     throws SemanticException
/*  96:    */   {
/*  97:138 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isScalar()
/* 101:    */     throws SemanticException
/* 102:    */   {
/* 103:142 */     return true;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setAlias(String alias)
/* 107:    */   {
/* 108:146 */     this.alias = alias;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setScalarColumn(int i)
/* 112:    */     throws SemanticException
/* 113:    */   {
/* 114:150 */     this.scalarColumnIndex = i;
/* 115:151 */     setScalarColumnText(i);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getScalarColumnIndex()
/* 119:    */   {
/* 120:155 */     return this.scalarColumnIndex;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setScalarColumnText(int i)
/* 124:    */     throws SemanticException
/* 125:    */   {
/* 126:159 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Type getDataType()
/* 130:    */   {
/* 131:164 */     return ((SelectExpression)getSelectClause().getFirstSelectExpression()).getDataType();
/* 132:    */   }
/* 133:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.QueryNode
 * JD-Core Version:    0.7.0.1
 */