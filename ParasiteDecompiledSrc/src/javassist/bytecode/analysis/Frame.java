/*   1:    */ package javassist.bytecode.analysis;
/*   2:    */ 
/*   3:    */ public class Frame
/*   4:    */ {
/*   5:    */   private Type[] locals;
/*   6:    */   private Type[] stack;
/*   7:    */   private int top;
/*   8:    */   private boolean jsrMerged;
/*   9:    */   private boolean retMerged;
/*  10:    */   
/*  11:    */   public Frame(int locals, int stack)
/*  12:    */   {
/*  13: 37 */     this.locals = new Type[locals];
/*  14: 38 */     this.stack = new Type[stack];
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Type getLocal(int index)
/*  18:    */   {
/*  19: 48 */     return this.locals[index];
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setLocal(int index, Type type)
/*  23:    */   {
/*  24: 58 */     this.locals[index] = type;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Type getStack(int index)
/*  28:    */   {
/*  29: 69 */     return this.stack[index];
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setStack(int index, Type type)
/*  33:    */   {
/*  34: 79 */     this.stack[index] = type;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void clearStack()
/*  38:    */   {
/*  39: 86 */     this.top = 0;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getTopIndex()
/*  43:    */   {
/*  44: 98 */     return this.top - 1;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public int localsLength()
/*  48:    */   {
/*  49:108 */     return this.locals.length;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Type peek()
/*  53:    */   {
/*  54:117 */     if (this.top < 1) {
/*  55:118 */       throw new IndexOutOfBoundsException("Stack is empty");
/*  56:    */     }
/*  57:120 */     return this.stack[(this.top - 1)];
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Type pop()
/*  61:    */   {
/*  62:129 */     if (this.top < 1) {
/*  63:130 */       throw new IndexOutOfBoundsException("Stack is empty");
/*  64:    */     }
/*  65:131 */     return this.stack[(--this.top)];
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void push(Type type)
/*  69:    */   {
/*  70:140 */     this.stack[(this.top++)] = type;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Frame copy()
/*  74:    */   {
/*  75:151 */     Frame frame = new Frame(this.locals.length, this.stack.length);
/*  76:152 */     System.arraycopy(this.locals, 0, frame.locals, 0, this.locals.length);
/*  77:153 */     System.arraycopy(this.stack, 0, frame.stack, 0, this.stack.length);
/*  78:154 */     frame.top = this.top;
/*  79:155 */     return frame;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Frame copyStack()
/*  83:    */   {
/*  84:165 */     Frame frame = new Frame(this.locals.length, this.stack.length);
/*  85:166 */     System.arraycopy(this.stack, 0, frame.stack, 0, this.stack.length);
/*  86:167 */     frame.top = this.top;
/*  87:168 */     return frame;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean mergeStack(Frame frame)
/*  91:    */   {
/*  92:179 */     boolean changed = false;
/*  93:180 */     if (this.top != frame.top) {
/*  94:181 */       throw new RuntimeException("Operand stacks could not be merged, they are different sizes!");
/*  95:    */     }
/*  96:183 */     for (int i = 0; i < this.top; i++) {
/*  97:184 */       if (this.stack[i] != null)
/*  98:    */       {
/*  99:185 */         Type prev = this.stack[i];
/* 100:186 */         Type merged = prev.merge(frame.stack[i]);
/* 101:187 */         if (merged == Type.BOGUS) {
/* 102:188 */           throw new RuntimeException("Operand stacks could not be merged due to differing primitive types: pos = " + i);
/* 103:    */         }
/* 104:190 */         this.stack[i] = merged;
/* 105:192 */         if ((!merged.equals(prev)) || (merged.popChanged())) {
/* 106:193 */           changed = true;
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:198 */     return changed;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean merge(Frame frame)
/* 114:    */   {
/* 115:209 */     boolean changed = false;
/* 116:212 */     for (int i = 0; i < this.locals.length; i++) {
/* 117:213 */       if (this.locals[i] != null)
/* 118:    */       {
/* 119:214 */         Type prev = this.locals[i];
/* 120:215 */         Type merged = prev.merge(frame.locals[i]);
/* 121:    */         
/* 122:217 */         this.locals[i] = merged;
/* 123:218 */         if ((!merged.equals(prev)) || (merged.popChanged())) {
/* 124:219 */           changed = true;
/* 125:    */         }
/* 126:    */       }
/* 127:221 */       else if (frame.locals[i] != null)
/* 128:    */       {
/* 129:222 */         this.locals[i] = frame.locals[i];
/* 130:223 */         changed = true;
/* 131:    */       }
/* 132:    */     }
/* 133:227 */     changed |= mergeStack(frame);
/* 134:228 */     return changed;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String toString()
/* 138:    */   {
/* 139:232 */     StringBuffer buffer = new StringBuffer();
/* 140:    */     
/* 141:234 */     buffer.append("locals = [");
/* 142:235 */     for (int i = 0; i < this.locals.length; i++)
/* 143:    */     {
/* 144:236 */       buffer.append(this.locals[i] == null ? "empty" : this.locals[i].toString());
/* 145:237 */       if (i < this.locals.length - 1) {
/* 146:238 */         buffer.append(", ");
/* 147:    */       }
/* 148:    */     }
/* 149:240 */     buffer.append("] stack = [");
/* 150:241 */     for (int i = 0; i < this.top; i++)
/* 151:    */     {
/* 152:242 */       buffer.append(this.stack[i]);
/* 153:243 */       if (i < this.top - 1) {
/* 154:244 */         buffer.append(", ");
/* 155:    */       }
/* 156:    */     }
/* 157:246 */     buffer.append("]");
/* 158:    */     
/* 159:248 */     return buffer.toString();
/* 160:    */   }
/* 161:    */   
/* 162:    */   boolean isJsrMerged()
/* 163:    */   {
/* 164:257 */     return this.jsrMerged;
/* 165:    */   }
/* 166:    */   
/* 167:    */   void setJsrMerged(boolean jsrMerged)
/* 168:    */   {
/* 169:266 */     this.jsrMerged = jsrMerged;
/* 170:    */   }
/* 171:    */   
/* 172:    */   boolean isRetMerged()
/* 173:    */   {
/* 174:276 */     return this.retMerged;
/* 175:    */   }
/* 176:    */   
/* 177:    */   void setRetMerged(boolean retMerged)
/* 178:    */   {
/* 179:286 */     this.retMerged = retMerged;
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.analysis.Frame
 * JD-Core Version:    0.7.0.1
 */