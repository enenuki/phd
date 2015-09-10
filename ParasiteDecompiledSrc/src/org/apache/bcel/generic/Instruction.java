/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import org.apache.bcel.classfile.ConstantPool;
/*   9:    */ import org.apache.bcel.util.ByteSequence;
/*  10:    */ 
/*  11:    */ public abstract class Instruction
/*  12:    */   implements Cloneable, Serializable
/*  13:    */ {
/*  14: 70 */   protected short length = 1;
/*  15: 71 */   protected short opcode = -1;
/*  16:    */   
/*  17:    */   Instruction() {}
/*  18:    */   
/*  19:    */   public Instruction(short opcode, short length)
/*  20:    */   {
/*  21: 80 */     this.length = length;
/*  22: 81 */     this.opcode = opcode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void dump(DataOutputStream out)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 89 */     out.writeByte(this.opcode);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String toString(boolean verbose)
/*  32:    */   {
/*  33:102 */     if (verbose) {
/*  34:103 */       return org.apache.bcel.Constants.OPCODE_NAMES[this.opcode] + "[" + this.opcode + "](" + this.length + ")";
/*  35:    */     }
/*  36:105 */     return org.apache.bcel.Constants.OPCODE_NAMES[this.opcode];
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String toString()
/*  40:    */   {
/*  41:112 */     return toString(true);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String toString(ConstantPool cp)
/*  45:    */   {
/*  46:119 */     return toString(false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Instruction copy()
/*  50:    */   {
/*  51:131 */     Instruction i = null;
/*  52:134 */     if (InstructionConstants.INSTRUCTIONS[getOpcode()] != null) {
/*  53:135 */       i = this;
/*  54:    */     } else {
/*  55:    */       try
/*  56:    */       {
/*  57:138 */         i = (Instruction)clone();
/*  58:    */       }
/*  59:    */       catch (CloneNotSupportedException e)
/*  60:    */       {
/*  61:140 */         System.err.println(e);
/*  62:    */       }
/*  63:    */     }
/*  64:144 */     return i;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  68:    */     throws IOException
/*  69:    */   {}
/*  70:    */   
/*  71:    */   public static final Instruction readInstruction(ByteSequence bytes)
/*  72:    */     throws IOException
/*  73:    */   {
/*  74:167 */     boolean wide = false;
/*  75:168 */     short opcode = (short)bytes.readUnsignedByte();
/*  76:169 */     Instruction obj = null;
/*  77:171 */     if (opcode == 196)
/*  78:    */     {
/*  79:172 */       wide = true;
/*  80:173 */       opcode = (short)bytes.readUnsignedByte();
/*  81:    */     }
/*  82:176 */     if (InstructionConstants.INSTRUCTIONS[opcode] != null) {
/*  83:177 */       return InstructionConstants.INSTRUCTIONS[opcode];
/*  84:    */     }
/*  85:    */     Class clazz;
/*  86:    */     try
/*  87:    */     {
/*  88:184 */       clazz = Class.forName(className(opcode));
/*  89:    */     }
/*  90:    */     catch (ClassNotFoundException cnfe)
/*  91:    */     {
/*  92:189 */       throw new ClassGenException("Illegal opcode detected.");
/*  93:    */     }
/*  94:    */     try
/*  95:    */     {
/*  96:192 */       obj = (Instruction)clazz.newInstance();
/*  97:194 */       if ((wide) && (!(obj instanceof LocalVariableInstruction)) && (!(obj instanceof IINC)) && (!(obj instanceof RET))) {
/*  98:196 */         throw new Exception("Illegal opcode after wide: " + opcode);
/*  99:    */       }
/* 100:198 */       obj.setOpcode(opcode);
/* 101:199 */       obj.initFromFile(bytes, wide);
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:201 */       throw new ClassGenException(e.toString());
/* 106:    */     }
/* 107:203 */     return obj;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private static final String className(short opcode)
/* 111:    */   {
/* 112:207 */     String name = org.apache.bcel.Constants.OPCODE_NAMES[opcode].toUpperCase();
/* 113:    */     try
/* 114:    */     {
/* 115:213 */       int len = name.length();
/* 116:214 */       char ch1 = name.charAt(len - 2);char ch2 = name.charAt(len - 1);
/* 117:216 */       if ((ch1 == '_') && (ch2 >= '0') && (ch2 <= '5')) {
/* 118:217 */         name = name.substring(0, len - 2);
/* 119:    */       }
/* 120:219 */       if (name.equals("ICONST_M1")) {
/* 121:220 */         name = "ICONST";
/* 122:    */       }
/* 123:    */     }
/* 124:    */     catch (StringIndexOutOfBoundsException e)
/* 125:    */     {
/* 126:221 */       System.err.println(e);
/* 127:    */     }
/* 128:223 */     return "org.apache.bcel.generic." + name;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int consumeStack(ConstantPoolGen cpg)
/* 132:    */   {
/* 133:234 */     return org.apache.bcel.Constants.CONSUME_STACK[this.opcode];
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int produceStack(ConstantPoolGen cpg)
/* 137:    */   {
/* 138:245 */     return org.apache.bcel.Constants.PRODUCE_STACK[this.opcode];
/* 139:    */   }
/* 140:    */   
/* 141:    */   public short getOpcode()
/* 142:    */   {
/* 143:251 */     return this.opcode;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int getLength()
/* 147:    */   {
/* 148:256 */     return this.length;
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void setOpcode(short opcode)
/* 152:    */   {
/* 153:261 */     this.opcode = opcode;
/* 154:    */   }
/* 155:    */   
/* 156:    */   void dispose() {}
/* 157:    */   
/* 158:    */   public abstract void accept(Visitor paramVisitor);
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.Instruction
 * JD-Core Version:    0.7.0.1
 */