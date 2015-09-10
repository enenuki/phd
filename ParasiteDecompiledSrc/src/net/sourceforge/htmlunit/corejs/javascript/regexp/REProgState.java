/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ class REProgState
/*    4:     */ {
/*    5:     */   REProgState previous;
/*    6:     */   int min;
/*    7:     */   int max;
/*    8:     */   int index;
/*    9:     */   int continuation_op;
/*   10:     */   int continuation_pc;
/*   11:     */   REBackTrackData backTrack;
/*   12:     */   
/*   13:     */   REProgState(REProgState previous, int min, int max, int index, REBackTrackData backTrack, int continuation_pc, int continuation_op)
/*   14:     */   {
/*   15:2691 */     this.previous = previous;
/*   16:2692 */     this.min = min;
/*   17:2693 */     this.max = max;
/*   18:2694 */     this.index = index;
/*   19:2695 */     this.continuation_op = continuation_op;
/*   20:2696 */     this.continuation_pc = continuation_pc;
/*   21:2697 */     this.backTrack = backTrack;
/*   22:     */   }
/*   23:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.REProgState
 * JD-Core Version:    0.7.0.1
 */