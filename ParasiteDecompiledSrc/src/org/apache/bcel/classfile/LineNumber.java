/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public final class LineNumber
/*   8:    */   implements Cloneable, Node
/*   9:    */ {
/*  10:    */   private int start_pc;
/*  11:    */   private int line_number;
/*  12:    */   
/*  13:    */   public LineNumber(LineNumber c)
/*  14:    */   {
/*  15: 77 */     this(c.getStartPC(), c.getLineNumber());
/*  16:    */   }
/*  17:    */   
/*  18:    */   LineNumber(DataInputStream file)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 87 */     this(file.readUnsignedShort(), file.readUnsignedShort());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public LineNumber(int start_pc, int line_number)
/*  25:    */   {
/*  26: 96 */     this.start_pc = start_pc;
/*  27: 97 */     this.line_number = line_number;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void accept(Visitor v)
/*  31:    */   {
/*  32:108 */     v.visitLineNumber(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final void dump(DataOutputStream file)
/*  36:    */     throws IOException
/*  37:    */   {
/*  38:119 */     file.writeShort(this.start_pc);
/*  39:120 */     file.writeShort(this.line_number);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public final int getLineNumber()
/*  43:    */   {
/*  44:126 */     return this.line_number;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public final int getStartPC()
/*  48:    */   {
/*  49:131 */     return this.start_pc;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final void setLineNumber(int line_number)
/*  53:    */   {
/*  54:137 */     this.line_number = line_number;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final void setStartPC(int start_pc)
/*  58:    */   {
/*  59:144 */     this.start_pc = start_pc;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final String toString()
/*  63:    */   {
/*  64:151 */     return "LineNumber(" + this.start_pc + ", " + this.line_number + ")";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public LineNumber copy()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:159 */       return (LineNumber)clone();
/*  72:    */     }
/*  73:    */     catch (CloneNotSupportedException e) {}
/*  74:162 */     return null;
/*  75:    */   }
/*  76:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.LineNumber
 * JD-Core Version:    0.7.0.1
 */