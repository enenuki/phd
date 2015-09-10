/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public class DerbyCaseFragment
/*  9:   */   extends CaseFragment
/* 10:   */ {
/* 11:   */   public String toFragmentString()
/* 12:   */   {
/* 13:42 */     StringBuffer buf = new StringBuffer(this.cases.size() * 15 + 10);
/* 14:43 */     buf.append("case");
/* 15:44 */     Iterator iter = this.cases.entrySet().iterator();
/* 16:45 */     while (iter.hasNext())
/* 17:   */     {
/* 18:46 */       Map.Entry me = (Map.Entry)iter.next();
/* 19:47 */       buf.append(" when ").append(me.getKey()).append(" is not null then ").append(me.getValue());
/* 20:   */     }
/* 21:53 */     buf.append(" else -1");
/* 22:54 */     buf.append(" end");
/* 23:55 */     if (this.returnColumnName != null) {
/* 24:56 */       buf.append(" as ").append(this.returnColumnName);
/* 25:   */     }
/* 26:59 */     return buf.toString();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.DerbyCaseFragment
 * JD-Core Version:    0.7.0.1
 */