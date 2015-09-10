/*  1:   */ package org.hibernate.sql;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.Map;
/*  5:   */ import java.util.Map.Entry;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public class DecodeCaseFragment
/*  9:   */   extends CaseFragment
/* 10:   */ {
/* 11:   */   public String toFragmentString()
/* 12:   */   {
/* 13:40 */     StringBuffer buf = new StringBuffer(this.cases.size() * 15 + 10).append("decode(");
/* 14:   */     
/* 15:   */ 
/* 16:43 */     Iterator iter = this.cases.entrySet().iterator();
/* 17:44 */     while (iter.hasNext())
/* 18:   */     {
/* 19:45 */       Map.Entry me = (Map.Entry)iter.next();
/* 20:47 */       if (iter.hasNext()) {
/* 21:48 */         buf.append(", ").append(me.getKey()).append(", ").append(me.getValue());
/* 22:   */       } else {
/* 23:54 */         buf.insert(7, me.getKey()).append(", ").append(me.getValue());
/* 24:   */       }
/* 25:   */     }
/* 26:60 */     buf.append(')');
/* 27:62 */     if (this.returnColumnName != null) {
/* 28:63 */       buf.append(" as ").append(this.returnColumnName);
/* 29:   */     }
/* 30:67 */     return buf.toString();
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.DecodeCaseFragment
 * JD-Core Version:    0.7.0.1
 */