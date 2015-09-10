/*  1:   */ package org.hibernate.hql.internal.ast.tree;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import org.hibernate.param.ParameterSpecification;
/*  6:   */ import org.hibernate.sql.JoinFragment;
/*  7:   */ 
/*  8:   */ public class SqlFragment
/*  9:   */   extends Node
/* 10:   */   implements ParameterContainer
/* 11:   */ {
/* 12:   */   private JoinFragment joinFragment;
/* 13:   */   private FromElement fromElement;
/* 14:   */   private List embeddedParameters;
/* 15:   */   
/* 16:   */   public void setJoinFragment(JoinFragment joinFragment)
/* 17:   */   {
/* 18:42 */     this.joinFragment = joinFragment;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean hasFilterCondition()
/* 22:   */   {
/* 23:46 */     return this.joinFragment.hasFilterCondition();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setFromElement(FromElement fromElement)
/* 27:   */   {
/* 28:50 */     this.fromElement = fromElement;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public FromElement getFromElement()
/* 32:   */   {
/* 33:54 */     return this.fromElement;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void addEmbeddedParameter(ParameterSpecification specification)
/* 37:   */   {
/* 38:62 */     if (this.embeddedParameters == null) {
/* 39:63 */       this.embeddedParameters = new ArrayList();
/* 40:   */     }
/* 41:65 */     this.embeddedParameters.add(specification);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean hasEmbeddedParameters()
/* 45:   */   {
/* 46:69 */     return (this.embeddedParameters != null) && (!this.embeddedParameters.isEmpty());
/* 47:   */   }
/* 48:   */   
/* 49:   */   public ParameterSpecification[] getEmbeddedParameters()
/* 50:   */   {
/* 51:73 */     return (ParameterSpecification[])this.embeddedParameters.toArray(new ParameterSpecification[this.embeddedParameters.size()]);
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.SqlFragment
 * JD-Core Version:    0.7.0.1
 */