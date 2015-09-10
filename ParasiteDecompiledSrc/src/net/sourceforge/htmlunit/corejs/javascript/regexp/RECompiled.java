/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ 
/*    5:     */ class RECompiled
/*    6:     */   implements Serializable
/*    7:     */ {
/*    8:     */   static final long serialVersionUID = -6144956577595844213L;
/*    9:     */   char[] source;
/*   10:     */   int parenCount;
/*   11:     */   int flags;
/*   12:     */   byte[] program;
/*   13:     */   int classCount;
/*   14:     */   RECharSet[] classList;
/*   15:2623 */   int anchorCh = -1;
/*   16:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.RECompiled
 * JD-Core Version:    0.7.0.1
 */