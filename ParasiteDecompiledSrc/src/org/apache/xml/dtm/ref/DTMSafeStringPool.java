/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class DTMSafeStringPool
/*   6:    */   extends DTMStringPool
/*   7:    */ {
/*   8:    */   public synchronized void removeAllElements()
/*   9:    */   {
/*  10: 37 */     super.removeAllElements();
/*  11:    */   }
/*  12:    */   
/*  13:    */   public synchronized String indexToString(int i)
/*  14:    */     throws ArrayIndexOutOfBoundsException
/*  15:    */   {
/*  16: 47 */     return super.indexToString(i);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public synchronized int stringToIndex(String s)
/*  20:    */   {
/*  21: 53 */     return super.stringToIndex(s);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static void main(String[] args)
/*  25:    */   {
/*  26: 62 */     String[] word = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five", "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty", "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five", "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine" };
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37: 73 */     DTMStringPool pool = new DTMSafeStringPool();
/*  38:    */     
/*  39: 75 */     System.out.println("If no complaints are printed below, we passed initial test.");
/*  40: 77 */     for (int pass = 0; pass <= 1; pass++)
/*  41:    */     {
/*  42: 81 */       for (int i = 0; i < word.length; i++)
/*  43:    */       {
/*  44: 83 */         int j = pool.stringToIndex(word[i]);
/*  45: 84 */         if (j != i) {
/*  46: 85 */           System.out.println("\tMismatch populating pool: assigned " + j + " for create " + i);
/*  47:    */         }
/*  48:    */       }
/*  49: 89 */       for (i = 0; i < word.length; i++)
/*  50:    */       {
/*  51: 91 */         int j = pool.stringToIndex(word[i]);
/*  52: 92 */         if (j != i) {
/*  53: 93 */           System.out.println("\tMismatch in stringToIndex: returned " + j + " for lookup " + i);
/*  54:    */         }
/*  55:    */       }
/*  56: 97 */       for (i = 0; i < word.length; i++)
/*  57:    */       {
/*  58: 99 */         String w = pool.indexToString(i);
/*  59:100 */         if (!word[i].equals(w)) {
/*  60:101 */           System.out.println("\tMismatch in indexToString: returned" + w + " for lookup " + i);
/*  61:    */         }
/*  62:    */       }
/*  63:105 */       pool.removeAllElements();
/*  64:    */       
/*  65:107 */       System.out.println("\nPass " + pass + " complete\n");
/*  66:    */     }
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMSafeStringPool
 * JD-Core Version:    0.7.0.1
 */