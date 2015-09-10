/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ class NameValue
/*   4:    */ {
/*   5:    */   String key;
/*   6:    */   String value;
/*   7:    */   
/*   8:    */   public NameValue(String key, String value)
/*   9:    */   {
/*  10:930 */     this.key = key;
/*  11:931 */     this.value = value;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public String toString()
/*  15:    */   {
/*  16:934 */     return this.key + "=" + this.value;
/*  17:    */   }
/*  18:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.NameValue
 * JD-Core Version:    0.7.0.1
 */