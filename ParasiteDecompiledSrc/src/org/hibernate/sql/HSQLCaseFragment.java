/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public class HSQLCaseFragment
/*  9:   */   extends CaseFragment
/* 10:   */ {
/* 11:   */   public String toFragmentString()
/* 12:   */   {
/* 13:39 */     StringBuffer buf = new StringBuffer(this.cases.size() * 15 + 10);
/* 14:40 */     StringBuffer buf2 = new StringBuffer(this.cases.size());
/* 15:   */     
/* 16:42 */     Iterator iter = this.cases.entrySet().iterator();
/* 17:43 */     while (iter.hasNext())
/* 18:   */     {
/* 19:44 */       Map.Entry me = (Map.Entry)iter.next();
/* 20:45 */       buf.append(" casewhen(").append(me.getKey()).append(" is not null").append(", ").append(me.getValue()).append(", ");
/* 21:   */       
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:51 */       buf2.append(")");
/* 27:   */     }
/* 28:54 */     buf.append("-1");
/* 29:55 */     buf.append(buf2.toString());
/* 30:56 */     if (this.returnColumnName != null) {
/* 31:57 */       buf.append(" as ").append(this.returnColumnName);
/* 32:   */     }
/* 33:60 */     return buf.toString();
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.HSQLCaseFragment
 * JD-Core Version:    0.7.0.1
 */