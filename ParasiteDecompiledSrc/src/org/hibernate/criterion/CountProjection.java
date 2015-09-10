/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Criteria;
/*  7:   */ 
/*  8:   */ public class CountProjection
/*  9:   */   extends AggregateProjection
/* 10:   */ {
/* 11:   */   private boolean distinct;
/* 12:   */   
/* 13:   */   protected CountProjection(String prop)
/* 14:   */   {
/* 15:39 */     super("count", prop);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String toString()
/* 19:   */   {
/* 20:43 */     if (this.distinct) {
/* 21:44 */       return "distinct " + super.toString();
/* 22:   */     }
/* 23:47 */     return super.toString();
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected List buildFunctionParameterList(Criteria criteria, CriteriaQuery criteriaQuery)
/* 27:   */   {
/* 28:52 */     String[] cols = criteriaQuery.getColumns(this.propertyName, criteria);
/* 29:53 */     return this.distinct ? buildCountDistinctParameterList(cols) : Arrays.asList(cols);
/* 30:   */   }
/* 31:   */   
/* 32:   */   private List buildCountDistinctParameterList(String[] cols)
/* 33:   */   {
/* 34:57 */     List params = new ArrayList(cols.length + 1);
/* 35:58 */     params.add("distinct");
/* 36:59 */     params.addAll(Arrays.asList(cols));
/* 37:60 */     return params;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public CountProjection setDistinct()
/* 41:   */   {
/* 42:64 */     this.distinct = true;
/* 43:65 */     return this;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.CountProjection
 * JD-Core Version:    0.7.0.1
 */