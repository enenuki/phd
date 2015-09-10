/*  1:   */ package org.apache.log4j.or;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.Layout;
/*  4:   */ 
/*  5:   */ public class ThreadGroupRenderer
/*  6:   */   implements ObjectRenderer
/*  7:   */ {
/*  8:   */   public String doRender(Object o)
/*  9:   */   {
/* 10:51 */     if ((o instanceof ThreadGroup))
/* 11:   */     {
/* 12:52 */       StringBuffer sbuf = new StringBuffer();
/* 13:53 */       ThreadGroup tg = (ThreadGroup)o;
/* 14:54 */       sbuf.append("java.lang.ThreadGroup[name=");
/* 15:55 */       sbuf.append(tg.getName());
/* 16:56 */       sbuf.append(", maxpri=");
/* 17:57 */       sbuf.append(tg.getMaxPriority());
/* 18:58 */       sbuf.append("]");
/* 19:59 */       Thread[] t = new Thread[tg.activeCount()];
/* 20:60 */       tg.enumerate(t);
/* 21:61 */       for (int i = 0; i < t.length; i++)
/* 22:   */       {
/* 23:62 */         sbuf.append(Layout.LINE_SEP);
/* 24:63 */         sbuf.append("   Thread=[");
/* 25:64 */         sbuf.append(t[i].getName());
/* 26:65 */         sbuf.append(",");
/* 27:66 */         sbuf.append(t[i].getPriority());
/* 28:67 */         sbuf.append(",");
/* 29:68 */         sbuf.append(t[i].isDaemon());
/* 30:69 */         sbuf.append("]");
/* 31:   */       }
/* 32:71 */       return sbuf.toString();
/* 33:   */     }
/* 34:   */     try
/* 35:   */     {
/* 36:75 */       return o.toString();
/* 37:   */     }
/* 38:   */     catch (Exception ex)
/* 39:   */     {
/* 40:77 */       return ex.toString();
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.or.ThreadGroupRenderer
 * JD-Core Version:    0.7.0.1
 */