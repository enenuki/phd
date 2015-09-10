/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public abstract class BranchInstruction
/*   9:    */   extends Instruction
/*  10:    */   implements InstructionTargeter
/*  11:    */ {
/*  12:    */   protected int index;
/*  13:    */   protected InstructionHandle target;
/*  14:    */   protected int position;
/*  15:    */   
/*  16:    */   BranchInstruction() {}
/*  17:    */   
/*  18:    */   protected BranchInstruction(short opcode, InstructionHandle target)
/*  19:    */   {
/*  20: 85 */     super(opcode, (short)3);
/*  21: 86 */     setTarget(target);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void dump(DataOutputStream out)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 94 */     out.writeByte(this.opcode);
/*  28:    */     
/*  29: 96 */     this.index = getTargetOffset();
/*  30: 98 */     if (Math.abs(this.index) >= 32767) {
/*  31: 99 */       throw new ClassGenException("Branch target offset too large for short");
/*  32:    */     }
/*  33:101 */     out.writeShort(this.index);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int getTargetOffset(InstructionHandle target)
/*  37:    */   {
/*  38:109 */     if (target == null) {
/*  39:110 */       throw new ClassGenException("Target of " + super.toString(true) + " is invalid null handle");
/*  40:    */     }
/*  41:113 */     int t = target.getPosition();
/*  42:115 */     if (t < 0) {
/*  43:116 */       throw new ClassGenException("Invalid branch target position offset for " + super.toString(true) + ":" + t + ":" + target);
/*  44:    */     }
/*  45:119 */     return t - this.position;
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected int getTargetOffset()
/*  49:    */   {
/*  50:125 */     return getTargetOffset(this.target);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected int updatePosition(int offset, int max_offset)
/*  54:    */   {
/*  55:138 */     this.position += offset;
/*  56:139 */     return 0;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String toString(boolean verbose)
/*  60:    */   {
/*  61:154 */     String s = super.toString(verbose);
/*  62:155 */     String t = "null";
/*  63:157 */     if (verbose)
/*  64:    */     {
/*  65:158 */       if (this.target != null) {
/*  66:159 */         if (this.target.getInstruction() == this) {
/*  67:160 */           t = "<points to itself>";
/*  68:161 */         } else if (this.target.getInstruction() == null) {
/*  69:162 */           t = "<null instruction!!!?>";
/*  70:    */         } else {
/*  71:164 */           t = this.target.getInstruction().toString(false);
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:167 */     else if (this.target != null)
/*  76:    */     {
/*  77:168 */       this.index = getTargetOffset();
/*  78:169 */       t = "" + (this.index + this.position);
/*  79:    */     }
/*  80:173 */     return s + " -> " + t;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  84:    */     throws IOException
/*  85:    */   {
/*  86:186 */     this.length = 3;
/*  87:187 */     this.index = bytes.readShort();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final int getIndex()
/*  91:    */   {
/*  92:193 */     return this.index;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public InstructionHandle getTarget()
/*  96:    */   {
/*  97:198 */     return this.target;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setTarget(InstructionHandle target)
/* 101:    */   {
/* 102:205 */     notifyTarget(this.target, target, this);
/* 103:206 */     this.target = target;
/* 104:    */   }
/* 105:    */   
/* 106:    */   static final void notifyTarget(InstructionHandle old_ih, InstructionHandle new_ih, InstructionTargeter t)
/* 107:    */   {
/* 108:214 */     if (old_ih != null) {
/* 109:215 */       old_ih.removeTargeter(t);
/* 110:    */     }
/* 111:216 */     if (new_ih != null) {
/* 112:217 */       new_ih.addTargeter(t);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih)
/* 117:    */   {
/* 118:225 */     if (this.target == old_ih) {
/* 119:226 */       setTarget(new_ih);
/* 120:    */     } else {
/* 121:228 */       throw new ClassGenException("Not targeting " + old_ih + ", but " + this.target);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean containsTarget(InstructionHandle ih)
/* 126:    */   {
/* 127:235 */     return this.target == ih;
/* 128:    */   }
/* 129:    */   
/* 130:    */   void dispose()
/* 131:    */   {
/* 132:242 */     setTarget(null);
/* 133:243 */     this.index = -1;
/* 134:244 */     this.position = -1;
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.BranchInstruction
 * JD-Core Version:    0.7.0.1
 */