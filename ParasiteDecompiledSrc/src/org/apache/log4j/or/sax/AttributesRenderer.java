/*  1:   */ package org.apache.log4j.or.sax;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.or.ObjectRenderer;
/*  4:   */ import org.xml.sax.Attributes;
/*  5:   */ 
/*  6:   */ public class AttributesRenderer
/*  7:   */   implements ObjectRenderer
/*  8:   */ {
/*  9:   */   public String doRender(Object o)
/* 10:   */   {
/* 11:41 */     if ((o instanceof Attributes))
/* 12:   */     {
/* 13:42 */       StringBuffer sbuf = new StringBuffer();
/* 14:43 */       Attributes a = (Attributes)o;
/* 15:44 */       int len = a.getLength();
/* 16:45 */       boolean first = true;
/* 17:46 */       for (int i = 0; i < len; i++)
/* 18:   */       {
/* 19:47 */         if (first) {
/* 20:48 */           first = false;
/* 21:   */         } else {
/* 22:50 */           sbuf.append(", ");
/* 23:   */         }
/* 24:52 */         sbuf.append(a.getQName(i));
/* 25:53 */         sbuf.append('=');
/* 26:54 */         sbuf.append(a.getValue(i));
/* 27:   */       }
/* 28:56 */       return sbuf.toString();
/* 29:   */     }
/* 30:   */     try
/* 31:   */     {
/* 32:59 */       return o.toString();
/* 33:   */     }
/* 34:   */     catch (Exception ex)
/* 35:   */     {
/* 36:61 */       return ex.toString();
/* 37:   */     }
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.or.sax.AttributesRenderer
 * JD-Core Version:    0.7.0.1
 */