/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.bcel.classfile.LocalVariable;
/*   5:    */ 
/*   6:    */ public class LocalVariableGen
/*   7:    */   implements InstructionTargeter, NamedAndTyped, Cloneable
/*   8:    */ {
/*   9:    */   private int index;
/*  10:    */   private String name;
/*  11:    */   private Type type;
/*  12:    */   private InstructionHandle start;
/*  13:    */   private InstructionHandle end;
/*  14:    */   
/*  15:    */   public LocalVariableGen(int index, String name, Type type, InstructionHandle start, InstructionHandle end)
/*  16:    */   {
/*  17: 89 */     if ((index < 0) || (index > 65535)) {
/*  18: 90 */       throw new ClassGenException("Invalid index index: " + index);
/*  19:    */     }
/*  20: 92 */     this.name = name;
/*  21: 93 */     this.type = type;
/*  22: 94 */     this.index = index;
/*  23: 95 */     setStart(start);
/*  24: 96 */     setEnd(end);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public LocalVariable getLocalVariable(ConstantPoolGen cp)
/*  28:    */   {
/*  29:115 */     int start_pc = this.start.getPosition();
/*  30:116 */     int length = this.end.getPosition() - start_pc;
/*  31:118 */     if (length > 0) {
/*  32:119 */       length += this.end.getInstruction().getLength();
/*  33:    */     }
/*  34:121 */     int name_index = cp.addUtf8(this.name);
/*  35:122 */     int signature_index = cp.addUtf8(this.type.getSignature());
/*  36:    */     
/*  37:124 */     return new LocalVariable(start_pc, length, name_index, signature_index, this.index, cp.getConstantPool());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setIndex(int index)
/*  41:    */   {
/*  42:128 */     this.index = index;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int getIndex()
/*  46:    */   {
/*  47:129 */     return this.index;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setName(String name)
/*  51:    */   {
/*  52:130 */     this.name = name;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getName()
/*  56:    */   {
/*  57:131 */     return this.name;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setType(Type type)
/*  61:    */   {
/*  62:132 */     this.type = type;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Type getType()
/*  66:    */   {
/*  67:133 */     return this.type;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public InstructionHandle getStart()
/*  71:    */   {
/*  72:135 */     return this.start;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public InstructionHandle getEnd()
/*  76:    */   {
/*  77:136 */     return this.end;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setStart(InstructionHandle start)
/*  81:    */   {
/*  82:139 */     BranchInstruction.notifyTarget(this.start, start, this);
/*  83:140 */     this.start = start;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setEnd(InstructionHandle end)
/*  87:    */   {
/*  88:144 */     BranchInstruction.notifyTarget(this.end, end, this);
/*  89:145 */     this.end = end;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih)
/*  93:    */   {
/*  94:153 */     boolean targeted = false;
/*  95:155 */     if (this.start == old_ih)
/*  96:    */     {
/*  97:156 */       targeted = true;
/*  98:157 */       setStart(new_ih);
/*  99:    */     }
/* 100:160 */     if (this.end == old_ih)
/* 101:    */     {
/* 102:161 */       targeted = true;
/* 103:162 */       setEnd(new_ih);
/* 104:    */     }
/* 105:165 */     if (!targeted) {
/* 106:166 */       throw new ClassGenException("Not targeting " + old_ih + ", but {" + this.start + ", " + this.end + "}");
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean containsTarget(InstructionHandle ih)
/* 111:    */   {
/* 112:174 */     return (this.start == ih) || (this.end == ih);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean equals(Object o)
/* 116:    */   {
/* 117:182 */     if (!(o instanceof LocalVariableGen)) {
/* 118:183 */       return false;
/* 119:    */     }
/* 120:185 */     LocalVariableGen l = (LocalVariableGen)o;
/* 121:186 */     return (l.index == this.index) && (l.start == this.start) && (l.end == this.end);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String toString()
/* 125:    */   {
/* 126:190 */     return "LocalVariableGen(" + this.name + ", " + this.type + ", " + this.start + ", " + this.end + ")";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Object clone()
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:195 */       return super.clone();
/* 134:    */     }
/* 135:    */     catch (CloneNotSupportedException e)
/* 136:    */     {
/* 137:197 */       System.err.println(e);
/* 138:    */     }
/* 139:198 */     return null;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.LocalVariableGen
 * JD-Core Version:    0.7.0.1
 */