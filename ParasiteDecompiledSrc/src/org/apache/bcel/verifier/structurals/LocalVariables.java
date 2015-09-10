/*   1:    */ package org.apache.bcel.verifier.structurals;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.generic.ReferenceType;
/*   4:    */ import org.apache.bcel.generic.Type;
/*   5:    */ import org.apache.bcel.verifier.exc.AssertionViolatedException;
/*   6:    */ import org.apache.bcel.verifier.exc.StructuralCodeConstraintException;
/*   7:    */ 
/*   8:    */ public class LocalVariables
/*   9:    */ {
/*  10:    */   private Type[] locals;
/*  11:    */   
/*  12:    */   public LocalVariables(int maxLocals)
/*  13:    */   {
/*  14: 76 */     this.locals = new Type[maxLocals];
/*  15: 77 */     for (int i = 0; i < maxLocals; i++) {
/*  16: 78 */       this.locals[i] = Type.UNKNOWN;
/*  17:    */     }
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected Object clone()
/*  21:    */   {
/*  22: 88 */     LocalVariables lvs = new LocalVariables(this.locals.length);
/*  23: 89 */     for (int i = 0; i < this.locals.length; i++) {
/*  24: 90 */       lvs.locals[i] = this.locals[i];
/*  25:    */     }
/*  26: 92 */     return lvs;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public Type get(int i)
/*  30:    */   {
/*  31: 99 */     return this.locals[i];
/*  32:    */   }
/*  33:    */   
/*  34:    */   public LocalVariables getClone()
/*  35:    */   {
/*  36:107 */     return (LocalVariables)clone();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int maxLocals()
/*  40:    */   {
/*  41:115 */     return this.locals.length;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void set(int i, Type type)
/*  45:    */   {
/*  46:122 */     if ((type == Type.BYTE) || (type == Type.SHORT) || (type == Type.BOOLEAN) || (type == Type.CHAR)) {
/*  47:123 */       throw new AssertionViolatedException("LocalVariables do not know about '" + type + "'. Use Type.INT instead.");
/*  48:    */     }
/*  49:125 */     this.locals[i] = type;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean equals(Object o)
/*  53:    */   {
/*  54:132 */     if (!(o instanceof LocalVariables)) {
/*  55:132 */       return false;
/*  56:    */     }
/*  57:133 */     LocalVariables lv = (LocalVariables)o;
/*  58:134 */     if (this.locals.length != lv.locals.length) {
/*  59:134 */       return false;
/*  60:    */     }
/*  61:135 */     for (int i = 0; i < this.locals.length; i++) {
/*  62:136 */       if (!this.locals[i].equals(lv.locals[i])) {
/*  63:138 */         return false;
/*  64:    */       }
/*  65:    */     }
/*  66:141 */     return true;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void merge(LocalVariables lv)
/*  70:    */   {
/*  71:150 */     if (this.locals.length != lv.locals.length) {
/*  72:151 */       throw new AssertionViolatedException("Merging LocalVariables of different size?!? From different methods or what?!?");
/*  73:    */     }
/*  74:154 */     for (int i = 0; i < this.locals.length; i++) {
/*  75:155 */       merge(lv, i);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void merge(LocalVariables lv, int i)
/*  80:    */   {
/*  81:168 */     if ((!(this.locals[i] instanceof UninitializedObjectType)) && ((lv.locals[i] instanceof UninitializedObjectType))) {
/*  82:169 */       throw new StructuralCodeConstraintException("Backwards branch with an uninitialized object in the local variables detected.");
/*  83:    */     }
/*  84:172 */     if ((!this.locals[i].equals(lv.locals[i])) && ((this.locals[i] instanceof UninitializedObjectType)) && ((lv.locals[i] instanceof UninitializedObjectType))) {
/*  85:173 */       throw new StructuralCodeConstraintException("Backwards branch with an uninitialized object in the local variables detected.");
/*  86:    */     }
/*  87:176 */     if (((this.locals[i] instanceof UninitializedObjectType)) && 
/*  88:177 */       (!(lv.locals[i] instanceof UninitializedObjectType))) {
/*  89:178 */       this.locals[i] = ((UninitializedObjectType)this.locals[i]).getInitialized();
/*  90:    */     }
/*  91:181 */     if (((this.locals[i] instanceof ReferenceType)) && ((lv.locals[i] instanceof ReferenceType)))
/*  92:    */     {
/*  93:182 */       if (!this.locals[i].equals(lv.locals[i]))
/*  94:    */       {
/*  95:183 */         Type sup = ((ReferenceType)this.locals[i]).firstCommonSuperclass((ReferenceType)lv.locals[i]);
/*  96:185 */         if (sup != null) {
/*  97:186 */           this.locals[i] = sup;
/*  98:    */         } else {
/*  99:190 */           throw new AssertionViolatedException("Could not load all the super classes of '" + this.locals[i] + "' and '" + lv.locals[i] + "'.");
/* 100:    */         }
/* 101:    */       }
/* 102:    */     }
/* 103:195 */     else if (!this.locals[i].equals(lv.locals[i])) {
/* 104:202 */       this.locals[i] = Type.UNKNOWN;
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String toString()
/* 109:    */   {
/* 110:211 */     String s = new String();
/* 111:212 */     for (int i = 0; i < this.locals.length; i++) {
/* 112:213 */       s = s + Integer.toString(i) + ": " + this.locals[i] + "\n";
/* 113:    */     }
/* 114:215 */     return s;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void initializeObject(UninitializedObjectType u)
/* 118:    */   {
/* 119:223 */     for (int i = 0; i < this.locals.length; i++) {
/* 120:224 */       if (this.locals[i] == u) {
/* 121:225 */         this.locals[i] = u.getInitialized();
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.structurals.LocalVariables
 * JD-Core Version:    0.7.0.1
 */