/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ class REBackTrackData
/*    4:     */ {
/*    5:     */   REBackTrackData previous;
/*    6:     */   int continuation_op;
/*    7:     */   int continuation_pc;
/*    8:     */   int lastParen;
/*    9:     */   long[] parens;
/*   10:     */   int cp;
/*   11:     */   REProgState stateStackTop;
/*   12:     */   
/*   13:     */   REBackTrackData(REGlobalData gData, int op, int pc)
/*   14:     */   {
/*   15:2714 */     this.previous = gData.backTrackStackTop;
/*   16:2715 */     this.continuation_op = op;
/*   17:2716 */     this.continuation_pc = pc;
/*   18:2717 */     this.lastParen = gData.lastParen;
/*   19:2718 */     if (gData.parens != null) {
/*   20:2719 */       this.parens = ((long[])gData.parens.clone());
/*   21:     */     }
/*   22:2721 */     this.cp = gData.cp;
/*   23:2722 */     this.stateStackTop = gData.stateStackTop;
/*   24:     */   }
/*   25:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.REBackTrackData
 * JD-Core Version:    0.7.0.1
 */