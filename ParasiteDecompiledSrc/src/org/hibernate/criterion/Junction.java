/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.Criteria;
/*  7:   */ import org.hibernate.HibernateException;
/*  8:   */ import org.hibernate.engine.spi.TypedValue;
/*  9:   */ import org.hibernate.internal.util.StringHelper;
/* 10:   */ 
/* 11:   */ public class Junction
/* 12:   */   implements Criterion
/* 13:   */ {
/* 14:43 */   private final List criteria = new ArrayList();
/* 15:   */   private final String op;
/* 16:   */   
/* 17:   */   protected Junction(String op)
/* 18:   */   {
/* 19:47 */     this.op = op;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Junction add(Criterion criterion)
/* 23:   */   {
/* 24:51 */     this.criteria.add(criterion);
/* 25:52 */     return this;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getOp()
/* 29:   */   {
/* 30:56 */     return this.op;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TypedValue[] getTypedValues(Criteria crit, CriteriaQuery criteriaQuery)
/* 34:   */     throws HibernateException
/* 35:   */   {
/* 36:61 */     ArrayList typedValues = new ArrayList();
/* 37:62 */     Iterator iter = this.criteria.iterator();
/* 38:63 */     while (iter.hasNext())
/* 39:   */     {
/* 40:64 */       TypedValue[] subvalues = ((Criterion)iter.next()).getTypedValues(crit, criteriaQuery);
/* 41:65 */       for (int i = 0; i < subvalues.length; i++) {
/* 42:66 */         typedValues.add(subvalues[i]);
/* 43:   */       }
/* 44:   */     }
/* 45:69 */     return (TypedValue[])typedValues.toArray(new TypedValue[typedValues.size()]);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String toSqlString(Criteria crit, CriteriaQuery criteriaQuery)
/* 49:   */     throws HibernateException
/* 50:   */   {
/* 51:75 */     if (this.criteria.size() == 0) {
/* 52:75 */       return "1=1";
/* 53:   */     }
/* 54:77 */     StringBuffer buffer = new StringBuffer().append('(');
/* 55:   */     
/* 56:79 */     Iterator iter = this.criteria.iterator();
/* 57:80 */     while (iter.hasNext())
/* 58:   */     {
/* 59:81 */       buffer.append(((Criterion)iter.next()).toSqlString(crit, criteriaQuery));
/* 60:82 */       if (iter.hasNext()) {
/* 61:82 */         buffer.append(' ').append(this.op).append(' ');
/* 62:   */       }
/* 63:   */     }
/* 64:84 */     return ')';
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String toString()
/* 68:   */   {
/* 69:91 */     return '(' + StringHelper.join(new StringBuilder().append(' ').append(this.op).append(' ').toString(), this.criteria.iterator()) + ')';
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Junction
 * JD-Core Version:    0.7.0.1
 */