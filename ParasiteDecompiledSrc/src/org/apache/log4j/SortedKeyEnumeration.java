/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Vector;
/*   6:    */ 
/*   7:    */ class SortedKeyEnumeration
/*   8:    */   implements Enumeration
/*   9:    */ {
/*  10:    */   private Enumeration e;
/*  11:    */   
/*  12:    */   public SortedKeyEnumeration(Hashtable ht)
/*  13:    */   {
/*  14:943 */     Enumeration f = ht.keys();
/*  15:944 */     Vector keys = new Vector(ht.size());
/*  16:945 */     for (int last = 0; f.hasMoreElements(); last++)
/*  17:    */     {
/*  18:946 */       String key = (String)f.nextElement();
/*  19:947 */       for (int i = 0; i < last; i++)
/*  20:    */       {
/*  21:948 */         String s = (String)keys.get(i);
/*  22:949 */         if (key.compareTo(s) <= 0) {
/*  23:    */           break;
/*  24:    */         }
/*  25:    */       }
/*  26:951 */       keys.add(i, key);
/*  27:    */     }
/*  28:953 */     this.e = keys.elements();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean hasMoreElements()
/*  32:    */   {
/*  33:957 */     return this.e.hasMoreElements();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object nextElement()
/*  37:    */   {
/*  38:961 */     return this.e.nextElement();
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.SortedKeyEnumeration
 * JD-Core Version:    0.7.0.1
 */