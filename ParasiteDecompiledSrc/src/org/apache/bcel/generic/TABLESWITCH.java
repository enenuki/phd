/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class TABLESWITCH
/*   9:    */   extends Select
/*  10:    */ {
/*  11:    */   TABLESWITCH() {}
/*  12:    */   
/*  13:    */   public TABLESWITCH(int[] match, InstructionHandle[] targets, InstructionHandle target)
/*  14:    */   {
/*  15: 81 */     super((short)170, match, targets, target);
/*  16:    */     
/*  17: 83 */     this.length = ((short)(13 + this.match_length * 4));
/*  18:    */     
/*  19: 85 */     this.fixed_length = this.length;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void dump(DataOutputStream out)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25: 93 */     super.dump(out);
/*  26:    */     
/*  27: 95 */     int low = this.match_length > 0 ? this.match[0] : 0;
/*  28: 96 */     out.writeInt(low);
/*  29:    */     
/*  30: 98 */     int high = this.match_length > 0 ? this.match[(this.match_length - 1)] : 0;
/*  31: 99 */     out.writeInt(high);
/*  32:101 */     for (int i = 0; i < this.match_length; i++) {
/*  33:102 */       out.writeInt(this.indices[i] = getTargetOffset(this.targets[i]));
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40:110 */     super.initFromFile(bytes, wide);
/*  41:    */     
/*  42:112 */     int low = bytes.readInt();
/*  43:113 */     int high = bytes.readInt();
/*  44:    */     
/*  45:115 */     this.match_length = (high - low + 1);
/*  46:116 */     this.fixed_length = ((short)(13 + this.match_length * 4));
/*  47:117 */     this.length = ((short)(this.fixed_length + this.padding));
/*  48:    */     
/*  49:119 */     this.match = new int[this.match_length];
/*  50:120 */     this.indices = new int[this.match_length];
/*  51:121 */     this.targets = new InstructionHandle[this.match_length];
/*  52:123 */     for (int i = low; i <= high; i++) {
/*  53:124 */       this.match[(i - low)] = i;
/*  54:    */     }
/*  55:126 */     for (int i = 0; i < this.match_length; i++) {
/*  56:127 */       this.indices[i] = bytes.readInt();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void accept(Visitor v)
/*  61:    */   {
/*  62:141 */     v.visitVariableLengthInstruction(this);
/*  63:142 */     v.visitStackProducer(this);
/*  64:143 */     v.visitBranchInstruction(this);
/*  65:144 */     v.visitSelect(this);
/*  66:145 */     v.visitTABLESWITCH(this);
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.TABLESWITCH
 * JD-Core Version:    0.7.0.1
 */