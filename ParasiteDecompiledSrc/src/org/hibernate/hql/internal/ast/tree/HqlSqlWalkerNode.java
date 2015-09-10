/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import antlr.ASTFactory;
/*  4:   */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  5:   */ import org.hibernate.hql.internal.ast.util.AliasGenerator;
/*  6:   */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  7:   */ 
/*  8:   */ public class HqlSqlWalkerNode
/*  9:   */   extends SqlNode
/* 10:   */   implements InitializeableNode
/* 11:   */ {
/* 12:   */   private HqlSqlWalker walker;
/* 13:   */   
/* 14:   */   public void initialize(Object param)
/* 15:   */   {
/* 16:44 */     this.walker = ((HqlSqlWalker)param);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public HqlSqlWalker getWalker()
/* 20:   */   {
/* 21:48 */     return this.walker;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public SessionFactoryHelper getSessionFactoryHelper()
/* 25:   */   {
/* 26:52 */     return this.walker.getSessionFactoryHelper();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ASTFactory getASTFactory()
/* 30:   */   {
/* 31:56 */     return this.walker.getASTFactory();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public AliasGenerator getAliasGenerator()
/* 35:   */   {
/* 36:60 */     return this.walker.getAliasGenerator();
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.HqlSqlWalkerNode
 * JD-Core Version:    0.7.0.1
 */