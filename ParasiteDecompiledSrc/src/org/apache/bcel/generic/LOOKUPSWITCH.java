/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class LOOKUPSWITCH
/*   9:    */   extends Select
/*  10:    */ {
/*  11:    */   LOOKUPSWITCH() {}
/*  12:    */   
/*  13:    */   public LOOKUPSWITCH(int[] match, InstructionHandle[] targets, InstructionHandle target)
/*  14:    */   {
/*  15: 75 */     super((short)171, match, targets, target);
/*  16:    */     
/*  17: 77 */     this.length = ((short)(9 + this.match_length * 8));
/*  18:    */     
/*  19: 79 */     this.fixed_length = this.length;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void dump(DataOutputStream out)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 87 */     super.dump(out);
/*  26: 88 */     out.writeInt(this.match_length);
/*  27: 90 */     for (int i = 0; i < this.match_length; i++)
/*  28:    */     {
/*  29: 91 */       out.writeInt(this.match[i]);
/*  30: 92 */       out.writeInt(this.indices[i] = getTargetOffset(this.targets[i]));
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  35:    */     throws IOException
/*  36:    */   {
/*  37:101 */     super.initFromFile(bytes, wide);
/*  38:    */     
/*  39:103 */     this.match_length = bytes.readInt();
/*  40:104 */     this.fixed_length = ((short)(9 + this.match_length * 8));
/*  41:105 */     this.length = ((short)(this.fixed_length + this.padding));
/*  42:    */     
/*  43:107 */     this.match = new int[this.match_length];
/*  44:108 */     this.indices = new int[this.match_length];
/*  45:109 */     this.targets = new InstructionHandle[this.match_length];
/*  46:111 */     for (int i = 0; i < this.match_length; i++)
/*  47:    */     {
/*  48:112 */       this.match[i] = bytes.readInt();
/*  49:113 */       this.indices[i] = bytes.readInt();
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void accept(Visitor v)
/*  54:    */   {
/*  55:127 */     v.visitVariableLengthInstruction(this);
/*  56:128 */     v.visitStackProducer(this);
/*  57:129 */     v.visitBranchInstruction(this);
/*  58:130 */     v.visitSelect(this);
/*  59:131 */     v.visitLOOKUPSWITCH(this);
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LOOKUPSWITCH
 * JD-Core Version:    0.7.0.1
 */