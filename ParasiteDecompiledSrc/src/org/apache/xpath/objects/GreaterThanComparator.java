/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import org.apache.xml.utils.XMLString;
/*   4:    */ 
/*   5:    */ class GreaterThanComparator
/*   6:    */   extends Comparator
/*   7:    */ {
/*   8:    */   boolean compareStrings(XMLString s1, XMLString s2)
/*   9:    */   {
/*  10:846 */     return s1.toDouble() > s2.toDouble();
/*  11:    */   }
/*  12:    */   
/*  13:    */   boolean compareNumbers(double n1, double n2)
/*  14:    */   {
/*  15:861 */     return n1 > n2;
/*  16:    */   }
/*  17:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.GreaterThanComparator
 * JD-Core Version:    0.7.0.1
 */