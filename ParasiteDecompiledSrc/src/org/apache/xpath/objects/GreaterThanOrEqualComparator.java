/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import org.apache.xml.utils.XMLString;
/*   4:    */ 
/*   5:    */ class GreaterThanOrEqualComparator
/*   6:    */   extends Comparator
/*   7:    */ {
/*   8:    */   boolean compareStrings(XMLString s1, XMLString s2)
/*   9:    */   {
/*  10:882 */     return s1.toDouble() >= s2.toDouble();
/*  11:    */   }
/*  12:    */   
/*  13:    */   boolean compareNumbers(double n1, double n2)
/*  14:    */   {
/*  15:897 */     return n1 >= n2;
/*  16:    */   }
/*  17:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.GreaterThanOrEqualComparator
 * JD-Core Version:    0.7.0.1
 */