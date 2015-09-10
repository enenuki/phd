/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ public final class BranchHandle
/*   4:    */   extends InstructionHandle
/*   5:    */ {
/*   6:    */   private BranchInstruction bi;
/*   7:    */   
/*   8:    */   private BranchHandle(BranchInstruction i)
/*   9:    */   {
/*  10: 73 */     super(i);
/*  11: 74 */     this.bi = i;
/*  12:    */   }
/*  13:    */   
/*  14: 79 */   private static BranchHandle bh_list = null;
/*  15:    */   
/*  16:    */   static final BranchHandle getBranchHandle(BranchInstruction i)
/*  17:    */   {
/*  18: 82 */     if (bh_list == null) {
/*  19: 83 */       return new BranchHandle(i);
/*  20:    */     }
/*  21: 85 */     BranchHandle bh = bh_list;
/*  22: 86 */     bh_list = (BranchHandle)bh.next;
/*  23:    */     
/*  24: 88 */     bh.setInstruction(i);
/*  25:    */     
/*  26: 90 */     return bh;
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void addHandle()
/*  30:    */   {
/*  31: 97 */     this.next = bh_list;
/*  32: 98 */     bh_list = this;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int getPosition()
/*  36:    */   {
/*  37:105 */     return this.bi.position;
/*  38:    */   }
/*  39:    */   
/*  40:    */   void setPosition(int pos)
/*  41:    */   {
/*  42:108 */     this.i_position = (this.bi.position = pos);
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected int updatePosition(int offset, int max_offset)
/*  46:    */   {
/*  47:112 */     int x = this.bi.updatePosition(offset, max_offset);
/*  48:113 */     this.i_position = this.bi.position;
/*  49:114 */     return x;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setTarget(InstructionHandle ih)
/*  53:    */   {
/*  54:121 */     this.bi.setTarget(ih);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih)
/*  58:    */   {
/*  59:128 */     this.bi.updateTarget(old_ih, new_ih);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public InstructionHandle getTarget()
/*  63:    */   {
/*  64:135 */     return this.bi.getTarget();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setInstruction(Instruction i)
/*  68:    */   {
/*  69:142 */     super.setInstruction(i);
/*  70:144 */     if (!(i instanceof BranchInstruction)) {
/*  71:145 */       throw new ClassGenException("Assigning " + i + " to branch handle which is not a branch instruction");
/*  72:    */     }
/*  73:148 */     this.bi = ((BranchInstruction)i);
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.BranchHandle
 * JD-Core Version:    0.7.0.1
 */