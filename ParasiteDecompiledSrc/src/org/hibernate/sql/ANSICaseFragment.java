/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public class ANSICaseFragment
/*  9:   */   extends CaseFragment
/* 10:   */ {
/* 11:   */   public String toFragmentString()
/* 12:   */   {
/* 13:40 */     StringBuffer buf = new StringBuffer(this.cases.size() * 15 + 10).append("case");
/* 14:   */     
/* 15:   */ 
/* 16:43 */     Iterator iter = this.cases.entrySet().iterator();
/* 17:44 */     while (iter.hasNext())
/* 18:   */     {
/* 19:45 */       Map.Entry me = (Map.Entry)iter.next();
/* 20:46 */       buf.append(" when ").append(me.getKey()).append(" is not null then ").append(me.getValue());
/* 21:   */     }
/* 22:52 */     buf.append(" end");
/* 23:54 */     if (this.returnColumnName != null) {
/* 24:55 */       buf.append(" as ").append(this.returnColumnName);
/* 25:   */     }
/* 26:59 */     return buf.toString();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ANSICaseFragment
 * JD-Core Version:    0.7.0.1
 */