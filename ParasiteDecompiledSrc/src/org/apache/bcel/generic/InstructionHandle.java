/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.AbstractCollection;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import org.apache.bcel.classfile.Utility;
/*   9:    */ 
/*  10:    */ public class InstructionHandle
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   InstructionHandle next;
/*  14:    */   InstructionHandle prev;
/*  15:    */   Instruction instruction;
/*  16: 83 */   protected int i_position = -1;
/*  17:    */   private HashSet targeters;
/*  18:    */   private HashMap attributes;
/*  19:    */   
/*  20:    */   public final InstructionHandle getNext()
/*  21:    */   {
/*  22: 87 */     return this.next;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public final InstructionHandle getPrev()
/*  26:    */   {
/*  27: 88 */     return this.prev;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public final Instruction getInstruction()
/*  31:    */   {
/*  32: 89 */     return this.instruction;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setInstruction(Instruction i)
/*  36:    */   {
/*  37: 96 */     if (i == null) {
/*  38: 97 */       throw new ClassGenException("Assigning null to handle");
/*  39:    */     }
/*  40: 99 */     if ((getClass() != BranchHandle.class) && ((i instanceof BranchInstruction))) {
/*  41:100 */       throw new ClassGenException("Assigning branch instruction " + i + " to plain handle");
/*  42:    */     }
/*  43:102 */     if (this.instruction != null) {
/*  44:103 */       this.instruction.dispose();
/*  45:    */     }
/*  46:105 */     this.instruction = i;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Instruction swapInstruction(Instruction i)
/*  50:    */   {
/*  51:114 */     Instruction oldInstruction = this.instruction;
/*  52:115 */     this.instruction = i;
/*  53:116 */     return oldInstruction;
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected InstructionHandle(Instruction i)
/*  57:    */   {
/*  58:120 */     setInstruction(i);
/*  59:    */   }
/*  60:    */   
/*  61:123 */   private static InstructionHandle ih_list = null;
/*  62:    */   
/*  63:    */   static final InstructionHandle getInstructionHandle(Instruction i)
/*  64:    */   {
/*  65:128 */     if (ih_list == null) {
/*  66:129 */       return new InstructionHandle(i);
/*  67:    */     }
/*  68:131 */     InstructionHandle ih = ih_list;
/*  69:132 */     ih_list = ih.next;
/*  70:    */     
/*  71:134 */     ih.setInstruction(i);
/*  72:    */     
/*  73:136 */     return ih;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected int updatePosition(int offset, int max_offset)
/*  77:    */   {
/*  78:151 */     this.i_position += offset;
/*  79:152 */     return 0;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getPosition()
/*  83:    */   {
/*  84:159 */     return this.i_position;
/*  85:    */   }
/*  86:    */   
/*  87:    */   void setPosition(int pos)
/*  88:    */   {
/*  89:164 */     this.i_position = pos;
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void addHandle()
/*  93:    */   {
/*  94:169 */     this.next = ih_list;
/*  95:170 */     ih_list = this;
/*  96:    */   }
/*  97:    */   
/*  98:    */   void dispose()
/*  99:    */   {
/* 100:177 */     this.next = (this.prev = null);
/* 101:178 */     this.instruction.dispose();
/* 102:179 */     this.instruction = null;
/* 103:180 */     this.i_position = -1;
/* 104:181 */     this.attributes = null;
/* 105:182 */     removeAllTargeters();
/* 106:183 */     addHandle();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void removeAllTargeters()
/* 110:    */   {
/* 111:189 */     if (this.targeters != null) {
/* 112:190 */       this.targeters.clear();
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void removeTargeter(InstructionTargeter t)
/* 117:    */   {
/* 118:197 */     this.targeters.remove(t);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void addTargeter(InstructionTargeter t)
/* 122:    */   {
/* 123:204 */     if (this.targeters == null) {
/* 124:205 */       this.targeters = new HashSet();
/* 125:    */     }
/* 126:208 */     this.targeters.add(t);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean hasTargeters()
/* 130:    */   {
/* 131:212 */     return (this.targeters != null) && (this.targeters.size() > 0);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public InstructionTargeter[] getTargeters()
/* 135:    */   {
/* 136:219 */     if (!hasTargeters()) {
/* 137:220 */       return null;
/* 138:    */     }
/* 139:222 */     InstructionTargeter[] t = new InstructionTargeter[this.targeters.size()];
/* 140:223 */     this.targeters.toArray(t);
/* 141:224 */     return t;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String toString(boolean verbose)
/* 145:    */   {
/* 146:230 */     return Utility.format(this.i_position, 4, false, ' ') + ": " + this.instruction.toString(verbose);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:236 */     return toString(true);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void addAttribute(Object key, Object attr)
/* 155:    */   {
/* 156:245 */     if (this.attributes == null) {
/* 157:246 */       this.attributes = new HashMap(3);
/* 158:    */     }
/* 159:248 */     this.attributes.put(key, attr);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void removeAttribute(Object key)
/* 163:    */   {
/* 164:256 */     if (this.attributes != null) {
/* 165:257 */       this.attributes.remove(key);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Object getAttribute(Object key)
/* 170:    */   {
/* 171:265 */     if (this.attributes != null) {
/* 172:266 */       return this.attributes.get(key);
/* 173:    */     }
/* 174:268 */     return null;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Collection getAttributes()
/* 178:    */   {
/* 179:274 */     return this.attributes.values();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void accept(Visitor v)
/* 183:    */   {
/* 184:282 */     this.instruction.accept(v);
/* 185:    */   }
/* 186:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.InstructionHandle
 * JD-Core Version:    0.7.0.1
 */