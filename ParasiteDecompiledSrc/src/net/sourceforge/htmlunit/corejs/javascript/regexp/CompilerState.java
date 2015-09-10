/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*    4:     */ 
/*    5:     */ class CompilerState
/*    6:     */ {
/*    7:     */   Context cx;
/*    8:     */   char[] cpbegin;
/*    9:     */   int cpend;
/*   10:     */   int cp;
/*   11:     */   int flags;
/*   12:     */   int parenCount;
/*   13:     */   int parenNesting;
/*   14:     */   int classCount;
/*   15:     */   int progLength;
/*   16:     */   RENode result;
/*   17:     */   
/*   18:     */   CompilerState(Context cx, char[] source, int length, int flags)
/*   19:     */   {
/*   20:2663 */     this.cx = cx;
/*   21:2664 */     this.cpbegin = source;
/*   22:2665 */     this.cp = 0;
/*   23:2666 */     this.cpend = length;
/*   24:2667 */     this.flags = flags;
/*   25:2668 */     this.parenCount = 0;
/*   26:2669 */     this.classCount = 0;
/*   27:2670 */     this.progLength = 0;
/*   28:     */   }
/*   29:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.CompilerState
 * JD-Core Version:    0.7.0.1
 */