/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import org.apache.xpath.XPathContext;
/*   4:    */ 
/*   5:    */ public class XNull
/*   6:    */   extends XNodeSet
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = -6841683711458983005L;
/*   9:    */   
/*  10:    */   public int getType()
/*  11:    */   {
/*  12: 50 */     return -1;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public String getTypeString()
/*  16:    */   {
/*  17: 61 */     return "#CLASS_NULL";
/*  18:    */   }
/*  19:    */   
/*  20:    */   public double num()
/*  21:    */   {
/*  22: 72 */     return 0.0D;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean bool()
/*  26:    */   {
/*  27: 82 */     return false;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String str()
/*  31:    */   {
/*  32: 92 */     return "";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int rtf(XPathContext support)
/*  36:    */   {
/*  37:106 */     return -1;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean equals(XObject obj2)
/*  41:    */   {
/*  42:128 */     return obj2.getType() == -1;
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XNull
 * JD-Core Version:    0.7.0.1
 */