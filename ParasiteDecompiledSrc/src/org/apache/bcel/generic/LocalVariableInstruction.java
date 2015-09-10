/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public abstract class LocalVariableInstruction
/*   9:    */   extends Instruction
/*  10:    */   implements TypedInstruction, IndexedInstruction
/*  11:    */ {
/*  12: 69 */   protected int n = -1;
/*  13: 70 */   private short c_tag = -1;
/*  14: 71 */   private short canon_tag = -1;
/*  15:    */   
/*  16:    */   private final boolean wide()
/*  17:    */   {
/*  18: 73 */     return this.n > 255;
/*  19:    */   }
/*  20:    */   
/*  21:    */   LocalVariableInstruction(short canon_tag, short c_tag)
/*  22:    */   {
/*  23: 82 */     this.canon_tag = canon_tag;
/*  24: 83 */     this.c_tag = c_tag;
/*  25:    */   }
/*  26:    */   
/*  27:    */   LocalVariableInstruction() {}
/*  28:    */   
/*  29:    */   protected LocalVariableInstruction(short opcode, short c_tag, int n)
/*  30:    */   {
/*  31: 99 */     super(opcode, (short)2);
/*  32:    */     
/*  33:101 */     this.c_tag = c_tag;
/*  34:102 */     this.canon_tag = opcode;
/*  35:    */     
/*  36:104 */     setIndex(n);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void dump(DataOutputStream out)
/*  40:    */     throws IOException
/*  41:    */   {
/*  42:112 */     if (wide()) {
/*  43:113 */       out.writeByte(196);
/*  44:    */     }
/*  45:115 */     out.writeByte(this.opcode);
/*  46:117 */     if (this.length > 1) {
/*  47:118 */       if (wide()) {
/*  48:119 */         out.writeShort(this.n);
/*  49:    */       } else {
/*  50:121 */         out.writeByte(this.n);
/*  51:    */       }
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString(boolean verbose)
/*  56:    */   {
/*  57:135 */     if (((this.opcode >= 26) && (this.opcode <= 45)) || ((this.opcode >= 59) && (this.opcode <= 78))) {
/*  58:139 */       return super.toString(verbose);
/*  59:    */     }
/*  60:141 */     return super.toString(verbose) + " " + this.n;
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  64:    */     throws IOException
/*  65:    */   {
/*  66:151 */     if (wide)
/*  67:    */     {
/*  68:152 */       this.n = bytes.readUnsignedShort();
/*  69:153 */       this.length = 4;
/*  70:    */     }
/*  71:154 */     else if (((this.opcode >= 21) && (this.opcode <= 25)) || ((this.opcode >= 54) && (this.opcode <= 58)))
/*  72:    */     {
/*  73:158 */       this.n = bytes.readUnsignedByte();
/*  74:159 */       this.length = 2;
/*  75:    */     }
/*  76:160 */     else if (this.opcode <= 45)
/*  77:    */     {
/*  78:161 */       this.n = ((this.opcode - 26) % 4);
/*  79:162 */       this.length = 1;
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:164 */       this.n = ((this.opcode - 59) % 4);
/*  84:165 */       this.length = 1;
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public final int getIndex()
/*  89:    */   {
/*  90:172 */     return this.n;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIndex(int n)
/*  94:    */   {
/*  95:178 */     if ((n < 0) || (n > 65535)) {
/*  96:179 */       throw new ClassGenException("Illegal value: " + n);
/*  97:    */     }
/*  98:181 */     this.n = n;
/*  99:183 */     if ((n >= 0) && (n <= 3))
/* 100:    */     {
/* 101:184 */       this.opcode = ((short)(this.c_tag + n));
/* 102:185 */       this.length = 1;
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:187 */       this.opcode = this.canon_tag;
/* 107:189 */       if (wide()) {
/* 108:190 */         this.length = 4;
/* 109:    */       } else {
/* 110:192 */         this.length = 2;
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public short getCanonicalTag()
/* 116:    */   {
/* 117:199 */     return this.canon_tag;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Type getType(ConstantPoolGen cp)
/* 121:    */   {
/* 122:211 */     switch (this.canon_tag)
/* 123:    */     {
/* 124:    */     case 21: 
/* 125:    */     case 54: 
/* 126:213 */       return Type.INT;
/* 127:    */     case 22: 
/* 128:    */     case 55: 
/* 129:215 */       return Type.LONG;
/* 130:    */     case 24: 
/* 131:    */     case 57: 
/* 132:217 */       return Type.DOUBLE;
/* 133:    */     case 23: 
/* 134:    */     case 56: 
/* 135:219 */       return Type.FLOAT;
/* 136:    */     case 25: 
/* 137:    */     case 58: 
/* 138:221 */       return Type.OBJECT;
/* 139:    */     }
/* 140:223 */     throw new ClassGenException("Oops: unknown case in switch" + this.canon_tag);
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LocalVariableInstruction
 * JD-Core Version:    0.7.0.1
 */