/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ public abstract class CtMember
/*   4:    */ {
/*   5:    */   CtMember next;
/*   6:    */   protected CtClass declaringClass;
/*   7:    */   
/*   8:    */   static class Cache
/*   9:    */     extends CtMember
/*  10:    */   {
/*  11:    */     private CtMember methodTail;
/*  12:    */     private CtMember consTail;
/*  13:    */     private CtMember fieldTail;
/*  14:    */     
/*  15:    */     protected void extendToString(StringBuffer buffer) {}
/*  16:    */     
/*  17:    */     public boolean hasAnnotation(Class clz)
/*  18:    */     {
/*  19: 32 */       return false;
/*  20:    */     }
/*  21:    */     
/*  22:    */     public Object getAnnotation(Class clz)
/*  23:    */       throws ClassNotFoundException
/*  24:    */     {
/*  25: 34 */       return null;
/*  26:    */     }
/*  27:    */     
/*  28:    */     public Object[] getAnnotations()
/*  29:    */       throws ClassNotFoundException
/*  30:    */     {
/*  31: 36 */       return null;
/*  32:    */     }
/*  33:    */     
/*  34:    */     public byte[] getAttribute(String name)
/*  35:    */     {
/*  36: 37 */       return null;
/*  37:    */     }
/*  38:    */     
/*  39:    */     public Object[] getAvailableAnnotations()
/*  40:    */     {
/*  41: 38 */       return null;
/*  42:    */     }
/*  43:    */     
/*  44:    */     public int getModifiers()
/*  45:    */     {
/*  46: 39 */       return 0;
/*  47:    */     }
/*  48:    */     
/*  49:    */     public String getName()
/*  50:    */     {
/*  51: 40 */       return null;
/*  52:    */     }
/*  53:    */     
/*  54:    */     public String getSignature()
/*  55:    */     {
/*  56: 41 */       return null;
/*  57:    */     }
/*  58:    */     
/*  59:    */     public void setAttribute(String name, byte[] data) {}
/*  60:    */     
/*  61:    */     public void setModifiers(int mod) {}
/*  62:    */     
/*  63:    */     Cache(CtClassType decl)
/*  64:    */     {
/*  65: 50 */       super();
/*  66: 51 */       this.methodTail = this;
/*  67: 52 */       this.consTail = this;
/*  68: 53 */       this.fieldTail = this;
/*  69: 54 */       this.fieldTail.next = this;
/*  70:    */     }
/*  71:    */     
/*  72:    */     CtMember methodHead()
/*  73:    */     {
/*  74: 57 */       return this;
/*  75:    */     }
/*  76:    */     
/*  77:    */     CtMember lastMethod()
/*  78:    */     {
/*  79: 58 */       return this.methodTail;
/*  80:    */     }
/*  81:    */     
/*  82:    */     CtMember consHead()
/*  83:    */     {
/*  84: 59 */       return this.methodTail;
/*  85:    */     }
/*  86:    */     
/*  87:    */     CtMember lastCons()
/*  88:    */     {
/*  89: 60 */       return this.consTail;
/*  90:    */     }
/*  91:    */     
/*  92:    */     CtMember fieldHead()
/*  93:    */     {
/*  94: 61 */       return this.consTail;
/*  95:    */     }
/*  96:    */     
/*  97:    */     CtMember lastField()
/*  98:    */     {
/*  99: 62 */       return this.fieldTail;
/* 100:    */     }
/* 101:    */     
/* 102:    */     void addMethod(CtMember method)
/* 103:    */     {
/* 104: 65 */       method.next = this.methodTail.next;
/* 105: 66 */       this.methodTail.next = method;
/* 106: 67 */       if (this.methodTail == this.consTail)
/* 107:    */       {
/* 108: 68 */         this.consTail = method;
/* 109: 69 */         if (this.methodTail == this.fieldTail) {
/* 110: 70 */           this.fieldTail = method;
/* 111:    */         }
/* 112:    */       }
/* 113: 73 */       this.methodTail = method;
/* 114:    */     }
/* 115:    */     
/* 116:    */     void addConstructor(CtMember cons)
/* 117:    */     {
/* 118: 79 */       cons.next = this.consTail.next;
/* 119: 80 */       this.consTail.next = cons;
/* 120: 81 */       if (this.consTail == this.fieldTail) {
/* 121: 82 */         this.fieldTail = cons;
/* 122:    */       }
/* 123: 84 */       this.consTail = cons;
/* 124:    */     }
/* 125:    */     
/* 126:    */     void addField(CtMember field)
/* 127:    */     {
/* 128: 88 */       field.next = this;
/* 129: 89 */       this.fieldTail.next = field;
/* 130: 90 */       this.fieldTail = field;
/* 131:    */     }
/* 132:    */     
/* 133:    */     static int count(CtMember head, CtMember tail)
/* 134:    */     {
/* 135: 94 */       int n = 0;
/* 136: 95 */       while (head != tail)
/* 137:    */       {
/* 138: 96 */         n++;
/* 139: 97 */         head = head.next;
/* 140:    */       }
/* 141:100 */       return n;
/* 142:    */     }
/* 143:    */     
/* 144:    */     void remove(CtMember mem)
/* 145:    */     {
/* 146:104 */       CtMember m = this;
/* 147:    */       CtMember node;
/* 148:106 */       while ((node = m.next) != this)
/* 149:    */       {
/* 150:107 */         if (node == mem)
/* 151:    */         {
/* 152:108 */           m.next = node.next;
/* 153:109 */           if (node == this.methodTail) {
/* 154:110 */             this.methodTail = m;
/* 155:    */           }
/* 156:112 */           if (node == this.consTail) {
/* 157:113 */             this.consTail = m;
/* 158:    */           }
/* 159:115 */           if (node != this.fieldTail) {
/* 160:    */             break;
/* 161:    */           }
/* 162:116 */           this.fieldTail = m; break;
/* 163:    */         }
/* 164:121 */         m = m.next;
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected CtMember(CtClass clazz)
/* 170:    */   {
/* 171:127 */     this.declaringClass = clazz;
/* 172:128 */     this.next = null;
/* 173:    */   }
/* 174:    */   
/* 175:    */   final CtMember next()
/* 176:    */   {
/* 177:131 */     return this.next;
/* 178:    */   }
/* 179:    */   
/* 180:    */   void nameReplaced() {}
/* 181:    */   
/* 182:    */   public String toString()
/* 183:    */   {
/* 184:142 */     StringBuffer buffer = new StringBuffer(getClass().getName());
/* 185:143 */     buffer.append("@");
/* 186:144 */     buffer.append(Integer.toHexString(hashCode()));
/* 187:145 */     buffer.append("[");
/* 188:146 */     buffer.append(Modifier.toString(getModifiers()));
/* 189:147 */     extendToString(buffer);
/* 190:148 */     buffer.append("]");
/* 191:149 */     return buffer.toString();
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected abstract void extendToString(StringBuffer paramStringBuffer);
/* 195:    */   
/* 196:    */   public CtClass getDeclaringClass()
/* 197:    */   {
/* 198:164 */     return this.declaringClass;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean visibleFrom(CtClass clazz)
/* 202:    */   {
/* 203:170 */     int mod = getModifiers();
/* 204:171 */     if (Modifier.isPublic(mod)) {
/* 205:172 */       return true;
/* 206:    */     }
/* 207:173 */     if (Modifier.isPrivate(mod)) {
/* 208:174 */       return clazz == this.declaringClass;
/* 209:    */     }
/* 210:176 */     String declName = this.declaringClass.getPackageName();
/* 211:177 */     String fromName = clazz.getPackageName();
/* 212:    */     boolean visible;
/* 213:    */     boolean visible;
/* 214:179 */     if (declName == null) {
/* 215:180 */       visible = fromName == null;
/* 216:    */     } else {
/* 217:182 */       visible = declName.equals(fromName);
/* 218:    */     }
/* 219:184 */     if ((!visible) && (Modifier.isProtected(mod))) {
/* 220:185 */       return clazz.subclassOf(this.declaringClass);
/* 221:    */     }
/* 222:187 */     return visible;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public abstract int getModifiers();
/* 226:    */   
/* 227:    */   public abstract void setModifiers(int paramInt);
/* 228:    */   
/* 229:    */   public abstract boolean hasAnnotation(Class paramClass);
/* 230:    */   
/* 231:    */   public abstract Object getAnnotation(Class paramClass)
/* 232:    */     throws ClassNotFoundException;
/* 233:    */   
/* 234:    */   public abstract Object[] getAnnotations()
/* 235:    */     throws ClassNotFoundException;
/* 236:    */   
/* 237:    */   public abstract Object[] getAvailableAnnotations();
/* 238:    */   
/* 239:    */   public abstract String getName();
/* 240:    */   
/* 241:    */   public abstract String getSignature();
/* 242:    */   
/* 243:    */   public abstract byte[] getAttribute(String paramString);
/* 244:    */   
/* 245:    */   public abstract void setAttribute(String paramString, byte[] paramArrayOfByte);
/* 246:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.CtMember
 * JD-Core Version:    0.7.0.1
 */