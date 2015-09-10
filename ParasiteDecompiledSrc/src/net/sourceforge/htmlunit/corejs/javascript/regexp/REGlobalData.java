/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ class REGlobalData
/*    4:     */ {
/*    5:     */   boolean multiline;
/*    6:     */   RECompiled regexp;
/*    7:     */   int lastParen;
/*    8:     */   int skipped;
/*    9:     */   int cp;
/*   10:     */   long[] parens;
/*   11:     */   REProgState stateStackTop;
/*   12:     */   REBackTrackData backTrackStackTop;
/*   13:     */   
/*   14:     */   int parens_index(int i)
/*   15:     */   {
/*   16:2754 */     return (int)this.parens[i];
/*   17:     */   }
/*   18:     */   
/*   19:     */   int parens_length(int i)
/*   20:     */   {
/*   21:2762 */     return (int)(this.parens[i] >>> 32);
/*   22:     */   }
/*   23:     */   
/*   24:     */   void set_parens(int i, int index, int length)
/*   25:     */   {
/*   26:2767 */     this.parens[i] = (index & 0xFFFFFFFF | length << 32);
/*   27:     */   }
/*   28:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.REGlobalData
 * JD-Core Version:    0.7.0.1
 */