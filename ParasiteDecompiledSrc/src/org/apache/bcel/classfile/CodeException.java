/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import org.apache.bcel.Constants;
/*   7:    */ 
/*   8:    */ public final class CodeException
/*   9:    */   implements Cloneable, Constants, Node
/*  10:    */ {
/*  11:    */   private int start_pc;
/*  12:    */   private int end_pc;
/*  13:    */   private int handler_pc;
/*  14:    */   private int catch_type;
/*  15:    */   
/*  16:    */   public CodeException(CodeException c)
/*  17:    */   {
/*  18: 83 */     this(c.getStartPC(), c.getEndPC(), c.getHandlerPC(), c.getCatchType());
/*  19:    */   }
/*  20:    */   
/*  21:    */   CodeException(DataInputStream file)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 93 */     this(file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort(), file.readUnsignedShort());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public CodeException(int start_pc, int end_pc, int handler_pc, int catch_type)
/*  28:    */   {
/*  29:110 */     this.start_pc = start_pc;
/*  30:111 */     this.end_pc = end_pc;
/*  31:112 */     this.handler_pc = handler_pc;
/*  32:113 */     this.catch_type = catch_type;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void accept(Visitor v)
/*  36:    */   {
/*  37:124 */     v.visitCodeException(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final void dump(DataOutputStream file)
/*  41:    */     throws IOException
/*  42:    */   {
/*  43:134 */     file.writeShort(this.start_pc);
/*  44:135 */     file.writeShort(this.end_pc);
/*  45:136 */     file.writeShort(this.handler_pc);
/*  46:137 */     file.writeShort(this.catch_type);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public final int getCatchType()
/*  50:    */   {
/*  51:144 */     return this.catch_type;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public final int getEndPC()
/*  55:    */   {
/*  56:149 */     return this.end_pc;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public final int getHandlerPC()
/*  60:    */   {
/*  61:154 */     return this.handler_pc;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final int getStartPC()
/*  65:    */   {
/*  66:159 */     return this.start_pc;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public final void setCatchType(int catch_type)
/*  70:    */   {
/*  71:165 */     this.catch_type = catch_type;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public final void setEndPC(int end_pc)
/*  75:    */   {
/*  76:172 */     this.end_pc = end_pc;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final void setHandlerPC(int handler_pc)
/*  80:    */   {
/*  81:179 */     this.handler_pc = handler_pc;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public final void setStartPC(int start_pc)
/*  85:    */   {
/*  86:186 */     this.start_pc = start_pc;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public final String toString()
/*  90:    */   {
/*  91:193 */     return "CodeException(start_pc = " + this.start_pc + ", end_pc = " + this.end_pc + ", handler_pc = " + this.handler_pc + ", catch_type = " + this.catch_type + ")";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final String toString(ConstantPool cp, boolean verbose)
/*  95:    */   {
/*  96:    */     String str;
/*  97:204 */     if (this.catch_type == 0) {
/*  98:205 */       str = "<Any exception>(0)";
/*  99:    */     } else {
/* 100:207 */       str = Utility.compactClassName(cp.getConstantString(this.catch_type, (byte)7), false) + (verbose ? "(" + this.catch_type + ")" : "");
/* 101:    */     }
/* 102:210 */     return this.start_pc + "\t" + this.end_pc + "\t" + this.handler_pc + "\t" + str;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public final String toString(ConstantPool cp)
/* 106:    */   {
/* 107:214 */     return toString(cp, true);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public CodeException copy()
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:222 */       return (CodeException)clone();
/* 115:    */     }
/* 116:    */     catch (CloneNotSupportedException e) {}
/* 117:225 */     return null;
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.CodeException
 * JD-Core Version:    0.7.0.1
 */