/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ final class ExceptionTableEntry
/*    4:     */ {
/*    5:     */   int itsStartLabel;
/*    6:     */   int itsEndLabel;
/*    7:     */   int itsHandlerLabel;
/*    8:     */   short itsCatchType;
/*    9:     */   
/*   10:     */   ExceptionTableEntry(int startLabel, int endLabel, int handlerLabel, short catchType)
/*   11:     */   {
/*   12:4272 */     this.itsStartLabel = startLabel;
/*   13:4273 */     this.itsEndLabel = endLabel;
/*   14:4274 */     this.itsHandlerLabel = handlerLabel;
/*   15:4275 */     this.itsCatchType = catchType;
/*   16:     */   }
/*   17:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.ExceptionTableEntry
 * JD-Core Version:    0.7.0.1
 */