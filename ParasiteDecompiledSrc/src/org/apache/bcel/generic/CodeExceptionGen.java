/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.bcel.classfile.CodeException;
/*   5:    */ 
/*   6:    */ public final class CodeExceptionGen
/*   7:    */   implements InstructionTargeter, Cloneable
/*   8:    */ {
/*   9:    */   private InstructionHandle start_pc;
/*  10:    */   private InstructionHandle end_pc;
/*  11:    */   private InstructionHandle handler_pc;
/*  12:    */   private ObjectType catch_type;
/*  13:    */   
/*  14:    */   public CodeExceptionGen(InstructionHandle start_pc, InstructionHandle end_pc, InstructionHandle handler_pc, ObjectType catch_type)
/*  15:    */   {
/*  16: 92 */     setStartPC(start_pc);
/*  17: 93 */     setEndPC(end_pc);
/*  18: 94 */     setHandlerPC(handler_pc);
/*  19: 95 */     this.catch_type = catch_type;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CodeException getCodeException(ConstantPoolGen cp)
/*  23:    */   {
/*  24:108 */     return new CodeException(this.start_pc.getPosition(), this.end_pc.getPosition() + this.end_pc.getInstruction().getLength(), this.handler_pc.getPosition(), this.catch_type == null ? 0 : cp.addClass(this.catch_type));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setStartPC(InstructionHandle start_pc)
/*  28:    */   {
/*  29:118 */     BranchInstruction.notifyTarget(this.start_pc, start_pc, this);
/*  30:119 */     this.start_pc = start_pc;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setEndPC(InstructionHandle end_pc)
/*  34:    */   {
/*  35:126 */     BranchInstruction.notifyTarget(this.end_pc, end_pc, this);
/*  36:127 */     this.end_pc = end_pc;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setHandlerPC(InstructionHandle handler_pc)
/*  40:    */   {
/*  41:134 */     BranchInstruction.notifyTarget(this.handler_pc, handler_pc, this);
/*  42:135 */     this.handler_pc = handler_pc;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih)
/*  46:    */   {
/*  47:143 */     boolean targeted = false;
/*  48:145 */     if (this.start_pc == old_ih)
/*  49:    */     {
/*  50:146 */       targeted = true;
/*  51:147 */       setStartPC(new_ih);
/*  52:    */     }
/*  53:150 */     if (this.end_pc == old_ih)
/*  54:    */     {
/*  55:151 */       targeted = true;
/*  56:152 */       setEndPC(new_ih);
/*  57:    */     }
/*  58:155 */     if (this.handler_pc == old_ih)
/*  59:    */     {
/*  60:156 */       targeted = true;
/*  61:157 */       setHandlerPC(new_ih);
/*  62:    */     }
/*  63:160 */     if (!targeted) {
/*  64:161 */       throw new ClassGenException("Not targeting " + old_ih + ", but {" + this.start_pc + ", " + this.end_pc + ", " + this.handler_pc + "}");
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean containsTarget(InstructionHandle ih)
/*  69:    */   {
/*  70:169 */     return (this.start_pc == ih) || (this.end_pc == ih) || (this.handler_pc == ih);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setCatchType(ObjectType catch_type)
/*  74:    */   {
/*  75:173 */     this.catch_type = catch_type;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ObjectType getCatchType()
/*  79:    */   {
/*  80:175 */     return this.catch_type;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public InstructionHandle getStartPC()
/*  84:    */   {
/*  85:179 */     return this.start_pc;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public InstructionHandle getEndPC()
/*  89:    */   {
/*  90:183 */     return this.end_pc;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public InstructionHandle getHandlerPC()
/*  94:    */   {
/*  95:187 */     return this.handler_pc;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String toString()
/*  99:    */   {
/* 100:190 */     return "CodeExceptionGen(" + this.start_pc + ", " + this.end_pc + ", " + this.handler_pc + ")";
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object clone()
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:195 */       return super.clone();
/* 108:    */     }
/* 109:    */     catch (CloneNotSupportedException e)
/* 110:    */     {
/* 111:197 */       System.err.println(e);
/* 112:    */     }
/* 113:198 */     return null;
/* 114:    */   }
/* 115:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.CodeExceptionGen
 * JD-Core Version:    0.7.0.1
 */