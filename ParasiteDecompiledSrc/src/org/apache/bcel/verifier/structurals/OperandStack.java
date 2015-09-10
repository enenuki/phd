/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ import java.util.AbstractList;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.apache.bcel.generic.ObjectType;
/*   6:    */ import org.apache.bcel.generic.ReferenceType;
/*   7:    */ import org.apache.bcel.generic.Type;
/*   8:    */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*   9:    */ import org.apache.bcel.verifier.exc.StructuralCodeConstraintException;
/*  10:    */ 
/*  11:    */ public class OperandStack
/*  12:    */ {
/*  13: 72 */   private ArrayList stack = new ArrayList();
/*  14:    */   private int maxStack;
/*  15:    */   
/*  16:    */   public OperandStack(int maxStack)
/*  17:    */   {
/*  18: 81 */     this.maxStack = maxStack;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public OperandStack(int maxStack, ObjectType obj)
/*  22:    */   {
/*  23: 89 */     this.maxStack = maxStack;
/*  24: 90 */     push(obj);
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected Object clone()
/*  28:    */   {
/*  29: 98 */     OperandStack newstack = new OperandStack(this.maxStack);
/*  30: 99 */     newstack.stack = ((ArrayList)this.stack.clone());
/*  31:100 */     return newstack;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void clear()
/*  35:    */   {
/*  36:107 */     this.stack = new ArrayList();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean equals(Object o)
/*  40:    */   {
/*  41:116 */     if (!(o instanceof OperandStack)) {
/*  42:116 */       return false;
/*  43:    */     }
/*  44:117 */     OperandStack s = (OperandStack)o;
/*  45:118 */     return this.stack.equals(s.stack);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public OperandStack getClone()
/*  49:    */   {
/*  50:127 */     return (OperandStack)clone();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isEmpty()
/*  54:    */   {
/*  55:134 */     return this.stack.isEmpty();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int maxStack()
/*  59:    */   {
/*  60:141 */     return this.maxStack;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Type peek()
/*  64:    */   {
/*  65:148 */     return peek(0);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Type peek(int i)
/*  69:    */   {
/*  70:156 */     return (Type)this.stack.get(size() - i - 1);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Type pop()
/*  74:    */   {
/*  75:163 */     Type e = (Type)this.stack.remove(size() - 1);
/*  76:164 */     return e;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Type pop(int i)
/*  80:    */   {
/*  81:171 */     for (int j = 0; j < i; j++) {
/*  82:172 */       pop();
/*  83:    */     }
/*  84:174 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void push(Type type)
/*  88:    */   {
/*  89:181 */     if (type == null) {
/*  90:181 */       throw new AssertionViolatedException("Cannot push NULL onto OperandStack.");
/*  91:    */     }
/*  92:182 */     if ((type == Type.BOOLEAN) || (type == Type.CHAR) || (type == Type.BYTE) || (type == Type.SHORT)) {
/*  93:183 */       throw new AssertionViolatedException("The OperandStack does not know about '" + type + "'; use Type.INT instead.");
/*  94:    */     }
/*  95:185 */     if (slotsUsed() >= this.maxStack) {
/*  96:186 */       throw new AssertionViolatedException("OperandStack too small, should have thrown proper Exception elsewhere. Stack: " + this);
/*  97:    */     }
/*  98:188 */     this.stack.add(type);
/*  99:    */   }
/* 100:    */   
/* 101:    */   int size()
/* 102:    */   {
/* 103:195 */     return this.stack.size();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int slotsUsed()
/* 107:    */   {
/* 108:207 */     int slots = 0;
/* 109:208 */     for (int i = 0; i < this.stack.size(); i++) {
/* 110:209 */       slots += peek(i).getSize();
/* 111:    */     }
/* 112:211 */     return slots;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String toString()
/* 116:    */   {
/* 117:218 */     String s = "Slots used: " + slotsUsed() + " MaxStack: " + this.maxStack + ".\n";
/* 118:219 */     for (int i = 0; i < size(); i++) {
/* 119:220 */       s = s + peek(i) + " (Size: " + peek(i).getSize() + ")\n";
/* 120:    */     }
/* 121:222 */     return s;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void merge(OperandStack s)
/* 125:    */   {
/* 126:231 */     if ((slotsUsed() != s.slotsUsed()) || (size() != s.size())) {
/* 127:232 */       throw new StructuralCodeConstraintException("Cannot merge stacks of different size:\nOperandStack A:\n" + this + "\nOperandStack B:\n" + s);
/* 128:    */     }
/* 129:234 */     for (int i = 0; i < size(); i++)
/* 130:    */     {
/* 131:237 */       if ((!(this.stack.get(i) instanceof UninitializedObjectType)) && ((s.stack.get(i) instanceof UninitializedObjectType))) {
/* 132:238 */         throw new StructuralCodeConstraintException("Backwards branch with an uninitialized object on the stack detected.");
/* 133:    */       }
/* 134:242 */       if ((!this.stack.get(i).equals(s.stack.get(i))) && ((this.stack.get(i) instanceof UninitializedObjectType)) && (!(s.stack.get(i) instanceof UninitializedObjectType))) {
/* 135:243 */         throw new StructuralCodeConstraintException("Backwards branch with an uninitialized object on the stack detected.");
/* 136:    */       }
/* 137:246 */       if (((this.stack.get(i) instanceof UninitializedObjectType)) && 
/* 138:247 */         (!(s.stack.get(i) instanceof UninitializedObjectType))) {
/* 139:248 */         this.stack.set(i, ((UninitializedObjectType)this.stack.get(i)).getInitialized());
/* 140:    */       }
/* 141:251 */       if (!this.stack.get(i).equals(s.stack.get(i))) {
/* 142:252 */         if (((this.stack.get(i) instanceof ReferenceType)) && ((s.stack.get(i) instanceof ReferenceType))) {
/* 143:254 */           this.stack.set(i, ((ReferenceType)this.stack.get(i)).firstCommonSuperclass((ReferenceType)s.stack.get(i)));
/* 144:    */         } else {
/* 145:257 */           throw new StructuralCodeConstraintException("Cannot merge stacks of different types:\nStack A:\n" + this + "\nStack B:\n" + s);
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void initializeObject(UninitializedObjectType u)
/* 152:    */   {
/* 153:268 */     for (int i = 0; i < this.stack.size(); i++) {
/* 154:269 */       if (this.stack.get(i) == u) {
/* 155:270 */         this.stack.set(i, u.getInitialized());
/* 156:    */       }
/* 157:    */     }
/* 158:    */   }
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.OperandStack
 * JD-Core Version:    0.7.0.1
 */