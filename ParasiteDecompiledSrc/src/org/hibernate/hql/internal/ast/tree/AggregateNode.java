/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.SemanticException;
/*  4:   */ import antlr.collections.AST;
/*  5:   */ import org.hibernate.dialect.function.SQLFunction;
/*  6:   */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  7:   */ import org.hibernate.hql.internal.ast.util.ColumnHelper;
/*  8:   */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.hibernate.type.Type;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public class AggregateNode
/* 14:   */   extends AbstractSelectExpression
/* 15:   */   implements SelectExpression, FunctionNode
/* 16:   */ {
/* 17:42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AggregateNode.class.getName());
/* 18:   */   private SQLFunction sqlFunction;
/* 19:   */   
/* 20:   */   public SQLFunction getSQLFunction()
/* 21:   */   {
/* 22:47 */     return this.sqlFunction;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void resolve()
/* 26:   */   {
/* 27:51 */     resolveFunction();
/* 28:   */   }
/* 29:   */   
/* 30:   */   private SQLFunction resolveFunction()
/* 31:   */   {
/* 32:55 */     if (this.sqlFunction == null)
/* 33:   */     {
/* 34:56 */       String name = getText();
/* 35:57 */       this.sqlFunction = getSessionFactoryHelper().findSQLFunction(getText());
/* 36:58 */       if (this.sqlFunction == null)
/* 37:   */       {
/* 38:59 */         LOG.unableToResolveAggregateFunction(name);
/* 39:60 */         this.sqlFunction = new StandardSQLFunction(name);
/* 40:   */       }
/* 41:   */     }
/* 42:63 */     return this.sqlFunction;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Type getFirstArgumentType()
/* 46:   */   {
/* 47:67 */     AST argument = getFirstChild();
/* 48:68 */     while (argument != null) {
/* 49:69 */       if ((argument instanceof SqlNode))
/* 50:   */       {
/* 51:70 */         Type type = ((SqlNode)argument).getDataType();
/* 52:71 */         if (type != null) {
/* 53:72 */           return type;
/* 54:   */         }
/* 55:74 */         argument = argument.getNextSibling();
/* 56:   */       }
/* 57:   */     }
/* 58:77 */     return null;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public Type getDataType()
/* 62:   */   {
/* 63:83 */     return getSessionFactoryHelper().findFunctionReturnType(getText(), resolveFunction(), getFirstChild());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public void setScalarColumnText(int i)
/* 67:   */     throws SemanticException
/* 68:   */   {
/* 69:87 */     ColumnHelper.generateSingleScalarColumn(this, i);
/* 70:   */   }
/* 71:   */   
/* 72:   */   public boolean isScalar()
/* 73:   */     throws SemanticException
/* 74:   */   {
/* 75:93 */     return true;
/* 76:   */   }
/* 77:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.AggregateNode
 * JD-Core Version:    0.7.0.1
 */