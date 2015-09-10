/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ 
/*    5:     */ final class RECharSet
/*    6:     */   implements Serializable
/*    7:     */ {
/*    8:     */   static final long serialVersionUID = 7931787979395898394L;
/*    9:     */   int length;
/*   10:     */   int startIndex;
/*   11:     */   int strlength;
/*   12:     */   volatile transient boolean converted;
/*   13:     */   volatile transient boolean sense;
/*   14:     */   volatile transient byte[] bits;
/*   15:     */   
/*   16:     */   RECharSet(int length, int startIndex, int strlength)
/*   17:     */   {
/*   18:2786 */     this.length = length;
/*   19:2787 */     this.startIndex = startIndex;
/*   20:2788 */     this.strlength = strlength;
/*   21:     */   }
/*   22:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.RECharSet
 * JD-Core Version:    0.7.0.1
 */