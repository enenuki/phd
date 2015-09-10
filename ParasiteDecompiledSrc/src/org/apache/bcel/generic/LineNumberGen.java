/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.bcel.classfile.LineNumber;
/*   5:    */ 
/*   6:    */ public class LineNumberGen
/*   7:    */   implements InstructionTargeter, Cloneable
/*   8:    */ {
/*   9:    */   private InstructionHandle ih;
/*  10:    */   private int src_line;
/*  11:    */   
/*  12:    */   public LineNumberGen(InstructionHandle ih, int src_line)
/*  13:    */   {
/*  14: 80 */     setInstruction(ih);
/*  15: 81 */     setSourceLine(src_line);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public boolean containsTarget(InstructionHandle ih)
/*  19:    */   {
/*  20: 88 */     return this.ih == ih;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih)
/*  24:    */   {
/*  25: 96 */     if (old_ih != this.ih) {
/*  26: 97 */       throw new ClassGenException("Not targeting " + old_ih + ", but " + this.ih + "}");
/*  27:    */     }
/*  28: 99 */     setInstruction(new_ih);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public LineNumber getLineNumber()
/*  32:    */   {
/*  33:109 */     return new LineNumber(this.ih.getPosition(), this.src_line);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setInstruction(InstructionHandle ih)
/*  37:    */   {
/*  38:113 */     BranchInstruction.notifyTarget(this.ih, ih, this);
/*  39:    */     
/*  40:115 */     this.ih = ih;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Object clone()
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47:120 */       return super.clone();
/*  48:    */     }
/*  49:    */     catch (CloneNotSupportedException e)
/*  50:    */     {
/*  51:122 */       System.err.println(e);
/*  52:    */     }
/*  53:123 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public InstructionHandle getInstruction()
/*  57:    */   {
/*  58:127 */     return this.ih;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setSourceLine(int src_line)
/*  62:    */   {
/*  63:128 */     this.src_line = src_line;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getSourceLine()
/*  67:    */   {
/*  68:129 */     return this.src_line;
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LineNumberGen
 * JD-Core Version:    0.7.0.1
 */