/*   1:    */ package javassist.bytecode.stackmap;
/*   2:    */ 
/*   3:    */ import javassist.bytecode.BadBytecode;
/*   4:    */ import javassist.bytecode.CodeAttribute;
/*   5:    */ import javassist.bytecode.ConstPool;
/*   6:    */ import javassist.bytecode.MethodInfo;
/*   7:    */ 
/*   8:    */ public class TypedBlock
/*   9:    */   extends BasicBlock
/*  10:    */ {
/*  11:    */   public int stackTop;
/*  12:    */   public int numLocals;
/*  13:    */   public TypeData[] stackTypes;
/*  14:    */   public TypeData[] localsTypes;
/*  15:    */   public boolean[] inputs;
/*  16:    */   public boolean updating;
/*  17:    */   public int status;
/*  18:    */   public byte[] localsUsage;
/*  19:    */   
/*  20:    */   public static TypedBlock[] makeBlocks(MethodInfo minfo, CodeAttribute ca, boolean optimize)
/*  21:    */     throws BadBytecode
/*  22:    */   {
/*  23: 44 */     TypedBlock[] blocks = (TypedBlock[])new Maker().make(minfo);
/*  24: 45 */     if ((optimize) && (blocks.length < 2) && (
/*  25: 46 */       (blocks.length == 0) || (blocks[0].incoming == 0))) {
/*  26: 47 */       return null;
/*  27:    */     }
/*  28: 49 */     ConstPool pool = minfo.getConstPool();
/*  29: 50 */     boolean isStatic = (minfo.getAccessFlags() & 0x8) != 0;
/*  30: 51 */     blocks[0].initFirstBlock(ca.getMaxStack(), ca.getMaxLocals(), pool.getClassName(), minfo.getDescriptor(), isStatic, minfo.isConstructor());
/*  31:    */     
/*  32:    */ 
/*  33: 54 */     new Liveness().compute(ca.iterator(), blocks, ca.getMaxLocals(), blocks[0].localsTypes);
/*  34:    */     
/*  35: 56 */     return blocks;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected TypedBlock(int pos)
/*  39:    */   {
/*  40: 60 */     super(pos);
/*  41: 61 */     this.localsTypes = null;
/*  42: 62 */     this.inputs = null;
/*  43: 63 */     this.updating = false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected void toString2(StringBuffer sbuf)
/*  47:    */   {
/*  48: 67 */     super.toString2(sbuf);
/*  49: 68 */     sbuf.append(",\n stack={");
/*  50: 69 */     printTypes(sbuf, this.stackTop, this.stackTypes);
/*  51: 70 */     sbuf.append("}, locals={");
/*  52: 71 */     printTypes(sbuf, this.numLocals, this.localsTypes);
/*  53: 72 */     sbuf.append("}, inputs={");
/*  54: 73 */     if (this.inputs != null) {
/*  55: 74 */       for (int i = 0; i < this.inputs.length; i++) {
/*  56: 75 */         sbuf.append(this.inputs[i] != 0 ? "1, " : "0, ");
/*  57:    */       }
/*  58:    */     }
/*  59: 77 */     sbuf.append('}');
/*  60:    */   }
/*  61:    */   
/*  62:    */   private void printTypes(StringBuffer sbuf, int size, TypeData[] types)
/*  63:    */   {
/*  64: 82 */     if (types == null) {
/*  65: 83 */       return;
/*  66:    */     }
/*  67: 85 */     for (int i = 0; i < size; i++)
/*  68:    */     {
/*  69: 86 */       if (i > 0) {
/*  70: 87 */         sbuf.append(", ");
/*  71:    */       }
/*  72: 89 */       TypeData td = types[i];
/*  73: 90 */       sbuf.append(td == null ? "<>" : td.toString());
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean alreadySet()
/*  78:    */   {
/*  79: 95 */     return this.localsTypes != null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setStackMap(int st, TypeData[] stack, int nl, TypeData[] locals)
/*  83:    */     throws BadBytecode
/*  84:    */   {
/*  85:101 */     this.stackTop = st;
/*  86:102 */     this.stackTypes = stack;
/*  87:103 */     this.numLocals = nl;
/*  88:104 */     this.localsTypes = locals;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void resetNumLocals()
/*  92:    */   {
/*  93:111 */     if (this.localsTypes != null)
/*  94:    */     {
/*  95:112 */       int nl = this.localsTypes.length;
/*  96:113 */       while ((nl > 0) && (this.localsTypes[(nl - 1)] == TypeTag.TOP))
/*  97:    */       {
/*  98:114 */         if (nl > 1)
/*  99:    */         {
/* 100:115 */           TypeData td = this.localsTypes[(nl - 2)];
/* 101:116 */           if ((td == TypeTag.LONG) || (td == TypeTag.DOUBLE)) {
/* 102:    */             break;
/* 103:    */           }
/* 104:    */         }
/* 105:120 */         nl--;
/* 106:    */       }
/* 107:123 */       this.numLocals = nl;
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static class Maker
/* 112:    */     extends BasicBlock.Maker
/* 113:    */   {
/* 114:    */     protected BasicBlock makeBlock(int pos)
/* 115:    */     {
/* 116:129 */       return new TypedBlock(pos);
/* 117:    */     }
/* 118:    */     
/* 119:    */     protected BasicBlock[] makeArray(int size)
/* 120:    */     {
/* 121:133 */       return new TypedBlock[size];
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   void initFirstBlock(int maxStack, int maxLocals, String className, String methodDesc, boolean isStatic, boolean isConstructor)
/* 126:    */     throws BadBytecode
/* 127:    */   {
/* 128:151 */     if (methodDesc.charAt(0) != '(') {
/* 129:152 */       throw new BadBytecode("no method descriptor: " + methodDesc);
/* 130:    */     }
/* 131:154 */     this.stackTop = 0;
/* 132:155 */     this.stackTypes = new TypeData[maxStack];
/* 133:156 */     TypeData[] locals = new TypeData[maxLocals];
/* 134:157 */     if (isConstructor) {
/* 135:158 */       locals[0] = new TypeData.UninitThis(className);
/* 136:159 */     } else if (!isStatic) {
/* 137:160 */       locals[0] = new TypeData.ClassName(className);
/* 138:    */     }
/* 139:162 */     int n = isStatic ? -1 : 0;
/* 140:163 */     int i = 1;
/* 141:    */     try
/* 142:    */     {
/* 143:165 */       while ((i = descToTag(methodDesc, i, ++n, locals)) > 0) {
/* 144:166 */         if (locals[n].is2WordType()) {
/* 145:167 */           locals[(++n)] = TypeTag.TOP;
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */     catch (StringIndexOutOfBoundsException e)
/* 150:    */     {
/* 151:170 */       throw new BadBytecode("bad method descriptor: " + methodDesc);
/* 152:    */     }
/* 153:174 */     this.numLocals = n;
/* 154:175 */     this.localsTypes = locals;
/* 155:    */   }
/* 156:    */   
/* 157:    */   private static int descToTag(String desc, int i, int n, TypeData[] types)
/* 158:    */     throws BadBytecode
/* 159:    */   {
/* 160:182 */     int i0 = i;
/* 161:183 */     int arrayDim = 0;
/* 162:184 */     char c = desc.charAt(i);
/* 163:185 */     if (c == ')') {
/* 164:186 */       return 0;
/* 165:    */     }
/* 166:188 */     while (c == '[')
/* 167:    */     {
/* 168:189 */       arrayDim++;
/* 169:190 */       c = desc.charAt(++i);
/* 170:    */     }
/* 171:193 */     if (c == 'L')
/* 172:    */     {
/* 173:194 */       int i2 = desc.indexOf(';', ++i);
/* 174:195 */       if (arrayDim > 0) {
/* 175:196 */         types[n] = new TypeData.ClassName(desc.substring(i0, ++i2));
/* 176:    */       } else {
/* 177:198 */         types[n] = new TypeData.ClassName(desc.substring(i0 + 1, ++i2 - 1).replace('/', '.'));
/* 178:    */       }
/* 179:200 */       return i2;
/* 180:    */     }
/* 181:202 */     if (arrayDim > 0)
/* 182:    */     {
/* 183:203 */       types[n] = new TypeData.ClassName(desc.substring(i0, ++i));
/* 184:204 */       return i;
/* 185:    */     }
/* 186:207 */     TypeData t = toPrimitiveTag(c);
/* 187:208 */     if (t == null) {
/* 188:209 */       throw new BadBytecode("bad method descriptor: " + desc);
/* 189:    */     }
/* 190:211 */     types[n] = t;
/* 191:212 */     return i + 1;
/* 192:    */   }
/* 193:    */   
/* 194:    */   private static TypeData toPrimitiveTag(char c)
/* 195:    */   {
/* 196:217 */     switch (c)
/* 197:    */     {
/* 198:    */     case 'B': 
/* 199:    */     case 'C': 
/* 200:    */     case 'I': 
/* 201:    */     case 'S': 
/* 202:    */     case 'Z': 
/* 203:223 */       return TypeTag.INTEGER;
/* 204:    */     case 'J': 
/* 205:225 */       return TypeTag.LONG;
/* 206:    */     case 'F': 
/* 207:227 */       return TypeTag.FLOAT;
/* 208:    */     case 'D': 
/* 209:229 */       return TypeTag.DOUBLE;
/* 210:    */     }
/* 211:232 */     return null;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static String getRetType(String desc)
/* 215:    */   {
/* 216:237 */     int i = desc.indexOf(')');
/* 217:238 */     if (i < 0) {
/* 218:239 */       return "java.lang.Object";
/* 219:    */     }
/* 220:241 */     char c = desc.charAt(i + 1);
/* 221:242 */     if (c == '[') {
/* 222:243 */       return desc.substring(i + 1);
/* 223:    */     }
/* 224:244 */     if (c == 'L') {
/* 225:245 */       return desc.substring(i + 2, desc.length() - 1).replace('/', '.');
/* 226:    */     }
/* 227:247 */     return "java.lang.Object";
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.stackmap.TypedBlock
 * JD-Core Version:    0.7.0.1
 */