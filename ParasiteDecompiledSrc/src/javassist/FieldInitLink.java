/*    1:     */ package javassist;
/*    2:     */ 
/*    3:     */ class FieldInitLink
/*    4:     */ {
/*    5:     */   FieldInitLink next;
/*    6:     */   CtField field;
/*    7:     */   CtField.Initializer init;
/*    8:     */   
/*    9:     */   FieldInitLink(CtField f, CtField.Initializer i)
/*   10:     */   {
/*   11:1681 */     this.next = null;
/*   12:1682 */     this.field = f;
/*   13:1683 */     this.init = i;
/*   14:     */   }
/*   15:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.FieldInitLink
 * JD-Core Version:    0.7.0.1
 */