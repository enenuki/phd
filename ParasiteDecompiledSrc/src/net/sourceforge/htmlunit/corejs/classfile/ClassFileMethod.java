/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ final class ClassFileMethod
/*    4:     */ {
/*    5:     */   private String itsName;
/*    6:     */   private String itsType;
/*    7:     */   private short itsNameIndex;
/*    8:     */   private short itsTypeIndex;
/*    9:     */   private short itsFlags;
/*   10:     */   private byte[] itsCodeAttribute;
/*   11:     */   
/*   12:     */   ClassFileMethod(String name, short nameIndex, String type, short typeIndex, short flags)
/*   13:     */   {
/*   14:4347 */     this.itsName = name;
/*   15:4348 */     this.itsNameIndex = nameIndex;
/*   16:4349 */     this.itsType = type;
/*   17:4350 */     this.itsTypeIndex = typeIndex;
/*   18:4351 */     this.itsFlags = flags;
/*   19:     */   }
/*   20:     */   
/*   21:     */   void setCodeAttribute(byte[] codeAttribute)
/*   22:     */   {
/*   23:4356 */     this.itsCodeAttribute = codeAttribute;
/*   24:     */   }
/*   25:     */   
/*   26:     */   int write(byte[] data, int offset)
/*   27:     */   {
/*   28:4361 */     offset = ClassFileWriter.putInt16(this.itsFlags, data, offset);
/*   29:4362 */     offset = ClassFileWriter.putInt16(this.itsNameIndex, data, offset);
/*   30:4363 */     offset = ClassFileWriter.putInt16(this.itsTypeIndex, data, offset);
/*   31:     */     
/*   32:4365 */     offset = ClassFileWriter.putInt16(1, data, offset);
/*   33:4366 */     System.arraycopy(this.itsCodeAttribute, 0, data, offset, this.itsCodeAttribute.length);
/*   34:     */     
/*   35:4368 */     offset += this.itsCodeAttribute.length;
/*   36:4369 */     return offset;
/*   37:     */   }
/*   38:     */   
/*   39:     */   int getWriteSize()
/*   40:     */   {
/*   41:4374 */     return 8 + this.itsCodeAttribute.length;
/*   42:     */   }
/*   43:     */   
/*   44:     */   String getName()
/*   45:     */   {
/*   46:4379 */     return this.itsName;
/*   47:     */   }
/*   48:     */   
/*   49:     */   String getType()
/*   50:     */   {
/*   51:4384 */     return this.itsType;
/*   52:     */   }
/*   53:     */   
/*   54:     */   short getFlags()
/*   55:     */   {
/*   56:4389 */     return this.itsFlags;
/*   57:     */   }
/*   58:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.ClassFileMethod
 * JD-Core Version:    0.7.0.1
 */