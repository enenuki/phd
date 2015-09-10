/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.Constants;
/*   4:    */ import org.apache.bcel.Repository;
/*   5:    */ import org.apache.bcel.classfile.JavaClass;
/*   6:    */ 
/*   7:    */ public class ReferenceType
/*   8:    */   extends Type
/*   9:    */ {
/*  10:    */   protected ReferenceType(byte t, String s)
/*  11:    */   {
/*  12: 68 */     super(t, s);
/*  13:    */   }
/*  14:    */   
/*  15:    */   ReferenceType()
/*  16:    */   {
/*  17: 74 */     super((byte)14, "<null object>");
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean isCastableTo(Type t)
/*  21:    */   {
/*  22: 86 */     if (equals(Type.NULL)) {
/*  23: 87 */       return true;
/*  24:    */     }
/*  25: 89 */     return isAssignmentCompatibleWith(t);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean isAssignmentCompatibleWith(Type t)
/*  29:    */   {
/*  30:100 */     if (!(t instanceof ReferenceType)) {
/*  31:101 */       return false;
/*  32:    */     }
/*  33:103 */     ReferenceType T = (ReferenceType)t;
/*  34:105 */     if (equals(Type.NULL)) {
/*  35:106 */       return true;
/*  36:    */     }
/*  37:110 */     if (((this instanceof ObjectType)) && (((ObjectType)this).referencesClass()))
/*  38:    */     {
/*  39:114 */       if (((T instanceof ObjectType)) && (((ObjectType)T).referencesClass()))
/*  40:    */       {
/*  41:115 */         if (equals(T)) {
/*  42:116 */           return true;
/*  43:    */         }
/*  44:118 */         if (Repository.instanceOf(((ObjectType)this).getClassName(), ((ObjectType)T).getClassName())) {
/*  45:120 */           return true;
/*  46:    */         }
/*  47:    */       }
/*  48:125 */       if (((T instanceof ObjectType)) && (((ObjectType)T).referencesInterface()) && 
/*  49:126 */         (Repository.implementationOf(((ObjectType)this).getClassName(), ((ObjectType)T).getClassName()))) {
/*  50:128 */         return true;
/*  51:    */       }
/*  52:    */     }
/*  53:134 */     if (((this instanceof ObjectType)) && (((ObjectType)this).referencesInterface()))
/*  54:    */     {
/*  55:137 */       if (((T instanceof ObjectType)) && (((ObjectType)T).referencesClass()) && 
/*  56:138 */         (T.equals(Type.OBJECT))) {
/*  57:138 */         return true;
/*  58:    */       }
/*  59:144 */       if (((T instanceof ObjectType)) && (((ObjectType)T).referencesInterface()))
/*  60:    */       {
/*  61:145 */         if (equals(T)) {
/*  62:145 */           return true;
/*  63:    */         }
/*  64:146 */         if (Repository.implementationOf(((ObjectType)this).getClassName(), ((ObjectType)T).getClassName())) {
/*  65:147 */           return true;
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:154 */     if ((this instanceof ArrayType))
/*  70:    */     {
/*  71:157 */       if (((T instanceof ObjectType)) && (((ObjectType)T).referencesClass()) && 
/*  72:158 */         (T.equals(Type.OBJECT))) {
/*  73:158 */         return true;
/*  74:    */       }
/*  75:164 */       if ((T instanceof ArrayType))
/*  76:    */       {
/*  77:167 */         Type sc = ((ArrayType)this).getElementType();
/*  78:168 */         Type tc = ((ArrayType)this).getElementType();
/*  79:170 */         if (((sc instanceof BasicType)) && ((tc instanceof BasicType)) && (sc.equals(tc))) {
/*  80:171 */           return true;
/*  81:    */         }
/*  82:175 */         if (((tc instanceof ReferenceType)) && ((sc instanceof ReferenceType)) && (((ReferenceType)sc).isAssignmentCompatibleWith((ReferenceType)tc))) {
/*  83:176 */           return true;
/*  84:    */         }
/*  85:    */       }
/*  86:185 */       if (((T instanceof ObjectType)) && (((ObjectType)T).referencesInterface())) {
/*  87:186 */         for (int ii = 0; ii < Constants.INTERFACES_IMPLEMENTED_BY_ARRAYS.length; ii++) {
/*  88:187 */           if (T.equals(new ObjectType(Constants.INTERFACES_IMPLEMENTED_BY_ARRAYS[ii]))) {
/*  89:187 */             return true;
/*  90:    */           }
/*  91:    */         }
/*  92:    */       }
/*  93:    */     }
/*  94:191 */     return false;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public ReferenceType firstCommonSuperclass(ReferenceType t)
/*  98:    */   {
/*  99:207 */     if (equals(Type.NULL)) {
/* 100:207 */       return t;
/* 101:    */     }
/* 102:209 */     if (t.equals(Type.NULL)) {
/* 103:209 */       return this;
/* 104:    */     }
/* 105:211 */     if (equals(t)) {
/* 106:211 */       return this;
/* 107:    */     }
/* 108:219 */     if (((this instanceof ArrayType)) || ((t instanceof ArrayType))) {
/* 109:220 */       return Type.OBJECT;
/* 110:    */     }
/* 111:223 */     if ((((this instanceof ObjectType)) && (((ObjectType)this).referencesInterface())) || (((t instanceof ObjectType)) && (((ObjectType)t).referencesInterface()))) {
/* 112:225 */       return Type.OBJECT;
/* 113:    */     }
/* 114:232 */     ObjectType thiz = (ObjectType)this;
/* 115:233 */     ObjectType other = (ObjectType)t;
/* 116:234 */     JavaClass[] thiz_sups = Repository.getSuperClasses(thiz.getClassName());
/* 117:235 */     JavaClass[] other_sups = Repository.getSuperClasses(other.getClassName());
/* 118:237 */     if ((thiz_sups == null) || (other_sups == null)) {
/* 119:238 */       return null;
/* 120:    */     }
/* 121:242 */     JavaClass[] this_sups = new JavaClass[thiz_sups.length + 1];
/* 122:243 */     JavaClass[] t_sups = new JavaClass[other_sups.length + 1];
/* 123:244 */     System.arraycopy(thiz_sups, 0, this_sups, 1, thiz_sups.length);
/* 124:245 */     System.arraycopy(other_sups, 0, t_sups, 1, other_sups.length);
/* 125:246 */     this_sups[0] = Repository.lookupClass(thiz.getClassName());
/* 126:247 */     t_sups[0] = Repository.lookupClass(other.getClassName());
/* 127:249 */     for (int i = 0; i < t_sups.length; i++) {
/* 128:250 */       for (int j = 0; j < this_sups.length; j++) {
/* 129:251 */         if (this_sups[j].equals(t_sups[i])) {
/* 130:251 */           return new ObjectType(this_sups[j].getClassName());
/* 131:    */         }
/* 132:    */       }
/* 133:    */     }
/* 134:256 */     return null;
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.ReferenceType
 * JD-Core Version:    0.7.0.1
 */