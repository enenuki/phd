/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ class RENode
/*    4:     */ {
/*    5:     */   byte op;
/*    6:     */   RENode next;
/*    7:     */   RENode kid;
/*    8:     */   RENode kid2;
/*    9:     */   int parenIndex;
/*   10:     */   int min;
/*   11:     */   int max;
/*   12:     */   int parenCount;
/*   13:     */   boolean greedy;
/*   14:     */   int startIndex;
/*   15:     */   int kidlen;
/*   16:     */   int bmsize;
/*   17:     */   int index;
/*   18:     */   char chr;
/*   19:     */   int length;
/*   20:     */   int flatIndex;
/*   21:     */   
/*   22:     */   RENode(byte op)
/*   23:     */   {
/*   24:2630 */     this.op = op;
/*   25:     */   }
/*   26:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.RENode
 * JD-Core Version:    0.7.0.1
 */