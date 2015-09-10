/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.util.ByteSequence;
/*   7:    */ 
/*   8:    */ public class IINC
/*   9:    */   extends LocalVariableInstruction
/*  10:    */ {
/*  11:    */   private boolean wide;
/*  12:    */   private int c;
/*  13:    */   
/*  14:    */   IINC() {}
/*  15:    */   
/*  16:    */   public IINC(int n, int c)
/*  17:    */   {
/*  18: 78 */     this.opcode = 132;
/*  19: 79 */     this.length = 3;
/*  20:    */     
/*  21: 81 */     setIndex(n);
/*  22: 82 */     setIncrement(c);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void dump(DataOutputStream out)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 90 */     if (this.wide) {
/*  29: 91 */       out.writeByte(196);
/*  30:    */     }
/*  31: 93 */     out.writeByte(this.opcode);
/*  32: 95 */     if (this.wide)
/*  33:    */     {
/*  34: 96 */       out.writeShort(this.n);
/*  35: 97 */       out.writeShort(this.c);
/*  36:    */     }
/*  37:    */     else
/*  38:    */     {
/*  39: 99 */       out.writeByte(this.n);
/*  40:100 */       out.writeByte(this.c);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   private final void setWide()
/*  45:    */   {
/*  46:105 */     if ((this.wide = (this.n > 65535) || (Math.abs(this.c) > 127) ? 1 : 0) != 0) {
/*  47:107 */       this.length = 6;
/*  48:    */     } else {
/*  49:109 */       this.length = 3;
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void initFromFile(ByteSequence bytes, boolean wide)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:117 */     this.wide = wide;
/*  57:119 */     if (wide)
/*  58:    */     {
/*  59:120 */       this.length = 6;
/*  60:121 */       this.n = bytes.readUnsignedShort();
/*  61:122 */       this.c = bytes.readShort();
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65:124 */       this.length = 3;
/*  66:125 */       this.n = bytes.readUnsignedByte();
/*  67:126 */       this.c = bytes.readByte();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String toString(boolean verbose)
/*  72:    */   {
/*  73:134 */     return super.toString(verbose) + " " + this.c;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public final void setIndex(int n)
/*  77:    */   {
/*  78:141 */     if (n < 0) {
/*  79:142 */       throw new ClassGenException("Negative index value: " + n);
/*  80:    */     }
/*  81:144 */     this.n = n;
/*  82:145 */     setWide();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public final int getIncrement()
/*  86:    */   {
/*  87:151 */     return this.c;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public final void setIncrement(int c)
/*  91:    */   {
/*  92:157 */     this.c = c;
/*  93:158 */     setWide();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Type getType(ConstantPoolGen cp)
/*  97:    */   {
/*  98:164 */     return Type.INT;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void accept(Visitor v)
/* 102:    */   {
/* 103:176 */     v.visitLocalVariableInstruction(this);
/* 104:177 */     v.visitIINC(this);
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.IINC
 * JD-Core Version:    0.7.0.1
 */