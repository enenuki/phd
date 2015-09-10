/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.ExceptionConstants;
/*   7:    */ import org.apache.bcel.classfile.Constant;
/*   8:    */ import org.apache.bcel.classfile.ConstantFloat;
/*   9:    */ import org.apache.bcel.classfile.ConstantInteger;
/*  10:    */ import org.apache.bcel.classfile.ConstantPool;
/*  11:    */ import org.apache.bcel.classfile.ConstantString;
/*  12:    */ import org.apache.bcel.classfile.ConstantUtf8;
/*  13:    */ import org.apache.bcel.util.ByteSequence;
/*  14:    */ 
/*  15:    */ public class LDC
/*  16:    */   extends CPInstruction
/*  17:    */   implements PushInstruction, ExceptionThrower, TypedInstruction
/*  18:    */ {
/*  19:    */   LDC() {}
/*  20:    */   
/*  21:    */   public LDC(int index)
/*  22:    */   {
/*  23: 76 */     super((short)19, index);
/*  24: 77 */     setSize();
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected final void setSize()
/*  28:    */   {
/*  29: 82 */     if (this.index <= 255)
/*  30:    */     {
/*  31: 83 */       this.opcode = 18;
/*  32: 84 */       this.length = 2;
/*  33:    */     }
/*  34:    */     else
/*  35:    */     {
/*  36: 86 */       this.opcode = 19;
/*  37: 87 */       this.length = 3;
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void dump(DataOutputStream out)
/*  42:    */     throws IOException
/*  43:    */   {
/*  44: 96 */     out.writeByte(this.opcode);
/*  45: 98 */     if (this.length == 2) {
/*  46: 99 */       out.writeByte(this.index);
/*  47:    */     } else {
/*  48:101 */       out.writeShort(this.index);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final void setIndex(int index)
/*  53:    */   {
/*  54:108 */     super.setIndex(index);
/*  55:109 */     setSize();
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61:118 */     this.length = 2;
/*  62:119 */     this.index = bytes.readUnsignedByte();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object getValue(ConstantPoolGen cpg)
/*  66:    */   {
/*  67:123 */     Constant c = cpg.getConstantPool().getConstant(this.index);
/*  68:125 */     switch (c.getTag())
/*  69:    */     {
/*  70:    */     case 8: 
/*  71:127 */       int i = ((ConstantString)c).getStringIndex();
/*  72:128 */       c = cpg.getConstantPool().getConstant(i);
/*  73:129 */       return ((ConstantUtf8)c).getBytes();
/*  74:    */     case 4: 
/*  75:132 */       return new Float(((ConstantFloat)c).getBytes());
/*  76:    */     case 3: 
/*  77:135 */       return new Integer(((ConstantInteger)c).getBytes());
/*  78:    */     }
/*  79:138 */     throw new RuntimeException("Unknown or invalid constant type at " + this.index);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Type getType(ConstantPoolGen cpg)
/*  83:    */   {
/*  84:143 */     switch (cpg.getConstantPool().getConstant(this.index).getTag())
/*  85:    */     {
/*  86:    */     case 8: 
/*  87:144 */       return Type.STRING;
/*  88:    */     case 4: 
/*  89:145 */       return Type.FLOAT;
/*  90:    */     case 3: 
/*  91:146 */       return Type.INT;
/*  92:    */     }
/*  93:148 */     throw new RuntimeException("Unknown or invalid constant type at " + this.index);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Class[] getExceptions()
/*  97:    */   {
/*  98:153 */     return ExceptionConstants.EXCS_STRING_RESOLUTION;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void accept(Visitor v)
/* 102:    */   {
/* 103:165 */     v.visitStackProducer(this);
/* 104:166 */     v.visitPushInstruction(this);
/* 105:167 */     v.visitExceptionThrower(this);
/* 106:168 */     v.visitTypedInstruction(this);
/* 107:169 */     v.visitCPInstruction(this);
/* 108:170 */     v.visitLDC(this);
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LDC
 * JD-Core Version:    0.7.0.1
 */