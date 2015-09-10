/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public abstract class Select
/*   9:    */   extends BranchInstruction
/*  10:    */   implements VariableLengthInstruction, StackProducer
/*  11:    */ {
/*  12:    */   protected int[] match;
/*  13:    */   protected int[] indices;
/*  14:    */   protected InstructionHandle[] targets;
/*  15:    */   protected int fixed_length;
/*  16:    */   protected int match_length;
/*  17: 76 */   protected int padding = 0;
/*  18:    */   
/*  19:    */   Select() {}
/*  20:    */   
/*  21:    */   Select(short opcode, int[] match, InstructionHandle[] targets, InstructionHandle target)
/*  22:    */   {
/*  23: 94 */     super(opcode, target);
/*  24:    */     
/*  25: 96 */     this.targets = targets;
/*  26: 97 */     for (int i = 0; i < targets.length; i++) {
/*  27: 98 */       BranchInstruction.notifyTarget(null, targets[i], this);
/*  28:    */     }
/*  29:100 */     this.match = match;
/*  30:102 */     if ((this.match_length = match.length) != targets.length) {
/*  31:103 */       throw new ClassGenException("Match and target array have not the same length");
/*  32:    */     }
/*  33:105 */     this.indices = new int[this.match_length];
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int updatePosition(int offset, int max_offset)
/*  37:    */   {
/*  38:122 */     this.position += offset;
/*  39:    */     
/*  40:124 */     short old_length = this.length;
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:128 */     this.padding = ((4 - (this.position + 1) % 4) % 4);
/*  45:129 */     this.length = ((short)(this.fixed_length + this.padding));
/*  46:    */     
/*  47:131 */     return this.length - old_length;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void dump(DataOutputStream out)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:139 */     out.writeByte(this.opcode);
/*  54:141 */     for (int i = 0; i < this.padding; i++) {
/*  55:142 */       out.writeByte(0);
/*  56:    */     }
/*  57:144 */     this.index = getTargetOffset();
/*  58:145 */     out.writeInt(this.index);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:153 */     this.padding = ((4 - bytes.getIndex() % 4) % 4);
/*  65:155 */     for (int i = 0; i < this.padding; i++)
/*  66:    */     {
/*  67:    */       byte b;
/*  68:157 */       if ((b = bytes.readByte()) != 0) {
/*  69:158 */         throw new ClassGenException("Padding byte != 0: " + b);
/*  70:    */       }
/*  71:    */     }
/*  72:162 */     this.index = bytes.readInt();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String toString(boolean verbose)
/*  76:    */   {
/*  77:169 */     StringBuffer buf = new StringBuffer(super.toString(verbose));
/*  78:171 */     if (verbose) {
/*  79:172 */       for (int i = 0; i < this.match_length; i++)
/*  80:    */       {
/*  81:173 */         String s = "null";
/*  82:175 */         if (this.targets[i] != null) {
/*  83:176 */           s = this.targets[i].getInstruction().toString();
/*  84:    */         }
/*  85:178 */         buf.append("(" + this.match[i] + ", " + s + " = {" + this.indices[i] + "})");
/*  86:    */       }
/*  87:    */     } else {
/*  88:182 */       buf.append(" ...");
/*  89:    */     }
/*  90:184 */     return buf.toString();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setTarget(int i, InstructionHandle target)
/*  94:    */   {
/*  95:191 */     BranchInstruction.notifyTarget(this.targets[i], target, this);
/*  96:192 */     this.targets[i] = target;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih)
/* 100:    */   {
/* 101:200 */     boolean targeted = false;
/* 102:202 */     if (this.target == old_ih)
/* 103:    */     {
/* 104:203 */       targeted = true;
/* 105:204 */       setTarget(new_ih);
/* 106:    */     }
/* 107:207 */     for (int i = 0; i < this.targets.length; i++) {
/* 108:208 */       if (this.targets[i] == old_ih)
/* 109:    */       {
/* 110:209 */         targeted = true;
/* 111:210 */         setTarget(i, new_ih);
/* 112:    */       }
/* 113:    */     }
/* 114:214 */     if (!targeted) {
/* 115:215 */       throw new ClassGenException("Not targeting " + old_ih);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean containsTarget(InstructionHandle ih)
/* 120:    */   {
/* 121:222 */     if (this.target == ih) {
/* 122:223 */       return true;
/* 123:    */     }
/* 124:225 */     for (int i = 0; i < this.targets.length; i++) {
/* 125:226 */       if (this.targets[i] == ih) {
/* 126:227 */         return true;
/* 127:    */       }
/* 128:    */     }
/* 129:229 */     return false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   void dispose()
/* 133:    */   {
/* 134:236 */     super.dispose();
/* 135:238 */     for (int i = 0; i < this.targets.length; i++) {
/* 136:239 */       this.targets[i].removeTargeter(this);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int[] getMatchs()
/* 141:    */   {
/* 142:245 */     return this.match;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int[] getIndices()
/* 146:    */   {
/* 147:250 */     return this.indices;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public InstructionHandle[] getTargets()
/* 151:    */   {
/* 152:255 */     return this.targets;
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.Select
 * JD-Core Version:    0.7.0.1
 */