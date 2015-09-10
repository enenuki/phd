/*  1:   */ package org.apache.xalan.lib;
/*  2:   */ 
/*  3:   */ import org.apache.xml.dtm.ref.DTMNodeProxy;
/*  4:   */ import org.w3c.dom.Node;
/*  5:   */ import org.w3c.dom.NodeList;
/*  6:   */ 
/*  7:   */ public abstract class ExsltBase
/*  8:   */ {
/*  9:   */   protected static String toString(Node n)
/* 10:   */   {
/* 11:42 */     if ((n instanceof DTMNodeProxy)) {
/* 12:43 */       return ((DTMNodeProxy)n).getStringValue();
/* 13:   */     }
/* 14:46 */     String value = n.getNodeValue();
/* 15:47 */     if (value == null)
/* 16:   */     {
/* 17:49 */       NodeList nodelist = n.getChildNodes();
/* 18:50 */       StringBuffer buf = new StringBuffer();
/* 19:51 */       for (int i = 0; i < nodelist.getLength(); i++)
/* 20:   */       {
/* 21:53 */         Node childNode = nodelist.item(i);
/* 22:54 */         buf.append(toString(childNode));
/* 23:   */       }
/* 24:56 */       return buf.toString();
/* 25:   */     }
/* 26:59 */     return value;
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected static double toNumber(Node n)
/* 30:   */   {
/* 31:72 */     double d = 0.0D;
/* 32:73 */     String str = toString(n);
/* 33:   */     try
/* 34:   */     {
/* 35:76 */       d = Double.valueOf(str).doubleValue();
/* 36:   */     }
/* 37:   */     catch (NumberFormatException e)
/* 38:   */     {
/* 39:80 */       d = (0.0D / 0.0D);
/* 40:   */     }
/* 41:82 */     return d;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltBase
 * JD-Core Version:    0.7.0.1
 */