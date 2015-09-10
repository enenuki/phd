/*   1:    */ package org.apache.xalan.lib;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.extensions.ExpressionContext;
/*   4:    */ import org.apache.xml.dtm.DTMIterator;
/*   5:    */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*   6:    */ import org.apache.xpath.NodeSet;
/*   7:    */ import org.apache.xpath.axes.RTFIterator;
/*   8:    */ 
/*   9:    */ public class ExsltCommon
/*  10:    */ {
/*  11:    */   public static String objectType(Object obj)
/*  12:    */   {
/*  13: 63 */     if ((obj instanceof String)) {
/*  14: 64 */       return "string";
/*  15:    */     }
/*  16: 65 */     if ((obj instanceof Boolean)) {
/*  17: 66 */       return "boolean";
/*  18:    */     }
/*  19: 67 */     if ((obj instanceof Number)) {
/*  20: 68 */       return "number";
/*  21:    */     }
/*  22: 69 */     if ((obj instanceof DTMNodeIterator))
/*  23:    */     {
/*  24: 71 */       DTMIterator dtmI = ((DTMNodeIterator)obj).getDTMIterator();
/*  25: 72 */       if ((dtmI instanceof RTFIterator)) {
/*  26: 73 */         return "RTF";
/*  27:    */       }
/*  28: 75 */       return "node-set";
/*  29:    */     }
/*  30: 78 */     return "unknown";
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static NodeSet nodeSet(ExpressionContext myProcessor, Object rtf)
/*  34:    */   {
/*  35:103 */     return Extensions.nodeset(myProcessor, rtf);
/*  36:    */   }
/*  37:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.ExsltCommon
 * JD-Core Version:    0.7.0.1
 */