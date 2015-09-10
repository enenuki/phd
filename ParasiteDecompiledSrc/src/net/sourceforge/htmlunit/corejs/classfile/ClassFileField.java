/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ final class ClassFileField
/*    4:     */ {
/*    5:     */   private short itsNameIndex;
/*    6:     */   private short itsTypeIndex;
/*    7:     */   private short itsFlags;
/*    8:     */   private boolean itsHasAttributes;
/*    9:     */   private short itsAttr1;
/*   10:     */   private short itsAttr2;
/*   11:     */   private short itsAttr3;
/*   12:     */   private int itsIndex;
/*   13:     */   
/*   14:     */   ClassFileField(short nameIndex, short typeIndex, short flags)
/*   15:     */   {
/*   16:4289 */     this.itsNameIndex = nameIndex;
/*   17:4290 */     this.itsTypeIndex = typeIndex;
/*   18:4291 */     this.itsFlags = flags;
/*   19:4292 */     this.itsHasAttributes = false;
/*   20:     */   }
/*   21:     */   
/*   22:     */   void setAttributes(short attr1, short attr2, short attr3, int index)
/*   23:     */   {
/*   24:4297 */     this.itsHasAttributes = true;
/*   25:4298 */     this.itsAttr1 = attr1;
/*   26:4299 */     this.itsAttr2 = attr2;
/*   27:4300 */     this.itsAttr3 = attr3;
/*   28:4301 */     this.itsIndex = index;
/*   29:     */   }
/*   30:     */   
/*   31:     */   int write(byte[] data, int offset)
/*   32:     */   {
/*   33:4306 */     offset = ClassFileWriter.putInt16(this.itsFlags, data, offset);
/*   34:4307 */     offset = ClassFileWriter.putInt16(this.itsNameIndex, data, offset);
/*   35:4308 */     offset = ClassFileWriter.putInt16(this.itsTypeIndex, data, offset);
/*   36:4309 */     if (!this.itsHasAttributes)
/*   37:     */     {
/*   38:4311 */       offset = ClassFileWriter.putInt16(0, data, offset);
/*   39:     */     }
/*   40:     */     else
/*   41:     */     {
/*   42:4313 */       offset = ClassFileWriter.putInt16(1, data, offset);
/*   43:4314 */       offset = ClassFileWriter.putInt16(this.itsAttr1, data, offset);
/*   44:4315 */       offset = ClassFileWriter.putInt16(this.itsAttr2, data, offset);
/*   45:4316 */       offset = ClassFileWriter.putInt16(this.itsAttr3, data, offset);
/*   46:4317 */       offset = ClassFileWriter.putInt16(this.itsIndex, data, offset);
/*   47:     */     }
/*   48:4319 */     return offset;
/*   49:     */   }
/*   50:     */   
/*   51:     */   int getWriteSize()
/*   52:     */   {
/*   53:4324 */     int size = 6;
/*   54:4325 */     if (!this.itsHasAttributes) {
/*   55:4326 */       size += 2;
/*   56:     */     } else {
/*   57:4328 */       size += 10;
/*   58:     */     }
/*   59:4330 */     return size;
/*   60:     */   }
/*   61:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.ClassFileField
 * JD-Core Version:    0.7.0.1
 */