/*    1:     */ package net.sourceforge.htmlunit.corejs.classfile;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ 
/*    5:     */ final class TypeInfo
/*    6:     */ {
/*    7:     */   static final int TOP = 0;
/*    8:     */   static final int INTEGER = 1;
/*    9:     */   static final int FLOAT = 2;
/*   10:     */   static final int DOUBLE = 3;
/*   11:     */   static final int LONG = 4;
/*   12:     */   static final int NULL = 5;
/*   13:     */   static final int UNINITIALIZED_THIS = 6;
/*   14:     */   static final int OBJECT_TAG = 7;
/*   15:     */   static final int UNINITIALIZED_VAR_TAG = 8;
/*   16:     */   
/*   17:     */   static final int OBJECT(int constantPoolIndex)
/*   18:     */   {
/*   19:4964 */     return (constantPoolIndex & 0xFFFF) << 8 | 0x7;
/*   20:     */   }
/*   21:     */   
/*   22:     */   static final int OBJECT(String type, ConstantPool pool)
/*   23:     */   {
/*   24:4968 */     return OBJECT(pool.addClass(type));
/*   25:     */   }
/*   26:     */   
/*   27:     */   static final int UNINITIALIZED_VARIABLE(int bytecodeOffset)
/*   28:     */   {
/*   29:4972 */     return (bytecodeOffset & 0xFFFF) << 8 | 0x8;
/*   30:     */   }
/*   31:     */   
/*   32:     */   static final int getTag(int typeInfo)
/*   33:     */   {
/*   34:4976 */     return typeInfo & 0xFF;
/*   35:     */   }
/*   36:     */   
/*   37:     */   static final int getPayload(int typeInfo)
/*   38:     */   {
/*   39:4980 */     return typeInfo >>> 8;
/*   40:     */   }
/*   41:     */   
/*   42:     */   static final String getPayloadAsType(int typeInfo, ConstantPool pool)
/*   43:     */   {
/*   44:4990 */     if (getTag(typeInfo) == 7) {
/*   45:4991 */       return (String)pool.getConstantData(getPayload(typeInfo));
/*   46:     */     }
/*   47:4993 */     throw new IllegalArgumentException("expecting object type");
/*   48:     */   }
/*   49:     */   
/*   50:     */   static final int fromType(String type, ConstantPool pool)
/*   51:     */   {
/*   52:5000 */     if (type.length() == 1)
/*   53:     */     {
/*   54:5001 */       switch (type.charAt(0))
/*   55:     */       {
/*   56:     */       case 'B': 
/*   57:     */       case 'C': 
/*   58:     */       case 'I': 
/*   59:     */       case 'S': 
/*   60:     */       case 'Z': 
/*   61:5007 */         return 1;
/*   62:     */       case 'D': 
/*   63:5009 */         return 3;
/*   64:     */       case 'F': 
/*   65:5011 */         return 2;
/*   66:     */       case 'J': 
/*   67:5013 */         return 4;
/*   68:     */       }
/*   69:5015 */       throw new IllegalArgumentException("bad type");
/*   70:     */     }
/*   71:5018 */     return OBJECT(type, pool);
/*   72:     */   }
/*   73:     */   
/*   74:     */   static boolean isTwoWords(int type)
/*   75:     */   {
/*   76:5022 */     return (type == 3) || (type == 4);
/*   77:     */   }
/*   78:     */   
/*   79:     */   static int merge(int current, int incoming, ConstantPool pool)
/*   80:     */   {
/*   81:5044 */     int currentTag = getTag(current);
/*   82:5045 */     int incomingTag = getTag(incoming);
/*   83:5046 */     boolean currentIsObject = currentTag == 7;
/*   84:5047 */     boolean incomingIsObject = incomingTag == 7;
/*   85:5049 */     if ((current == incoming) || ((currentIsObject) && (incoming == 5))) {
/*   86:5050 */       return current;
/*   87:     */     }
/*   88:5051 */     if ((currentTag == 0) || (incomingTag == 0)) {
/*   89:5053 */       return 0;
/*   90:     */     }
/*   91:5054 */     if ((current == 5) && (incomingIsObject)) {
/*   92:5055 */       return incoming;
/*   93:     */     }
/*   94:5056 */     if ((currentIsObject) && (incomingIsObject))
/*   95:     */     {
/*   96:5057 */       String currentName = getPayloadAsType(current, pool);
/*   97:5058 */       String incomingName = getPayloadAsType(incoming, pool);
/*   98:     */       
/*   99:     */ 
/*  100:     */ 
/*  101:5062 */       String currentlyGeneratedName = (String)pool.getConstantData(2);
/*  102:5063 */       String currentlyGeneratedSuperName = (String)pool.getConstantData(4);
/*  103:5070 */       if (currentName.equals(currentlyGeneratedName)) {
/*  104:5071 */         currentName = currentlyGeneratedSuperName;
/*  105:     */       }
/*  106:5073 */       if (incomingName.equals(currentlyGeneratedName)) {
/*  107:5074 */         incomingName = currentlyGeneratedSuperName;
/*  108:     */       }
/*  109:5077 */       Class<?> currentClass = getClassFromInternalName(currentName);
/*  110:5078 */       Class<?> incomingClass = getClassFromInternalName(incomingName);
/*  111:5080 */       if (currentClass.isAssignableFrom(incomingClass)) {
/*  112:5081 */         return current;
/*  113:     */       }
/*  114:5082 */       if (incomingClass.isAssignableFrom(currentClass)) {
/*  115:5083 */         return incoming;
/*  116:     */       }
/*  117:5084 */       if ((incomingClass.isInterface()) || (currentClass.isInterface())) {
/*  118:5090 */         return OBJECT("java/lang/Object", pool);
/*  119:     */       }
/*  120:5092 */       Class<?> commonClass = incomingClass.getSuperclass();
/*  121:5093 */       while (commonClass != null)
/*  122:     */       {
/*  123:5094 */         if (commonClass.isAssignableFrom(currentClass))
/*  124:     */         {
/*  125:5095 */           String name = commonClass.getName();
/*  126:5096 */           name = ClassFileWriter.getSlashedForm(name);
/*  127:5097 */           return OBJECT(name, pool);
/*  128:     */         }
/*  129:5099 */         commonClass = commonClass.getSuperclass();
/*  130:     */       }
/*  131:     */     }
/*  132:5103 */     throw new IllegalArgumentException("bad merge attempt between " + toString(current, pool) + " and " + toString(incoming, pool));
/*  133:     */   }
/*  134:     */   
/*  135:     */   static String toString(int type, ConstantPool pool)
/*  136:     */   {
/*  137:5109 */     int tag = getTag(type);
/*  138:5110 */     switch (tag)
/*  139:     */     {
/*  140:     */     case 0: 
/*  141:5112 */       return "top";
/*  142:     */     case 1: 
/*  143:5114 */       return "int";
/*  144:     */     case 2: 
/*  145:5116 */       return "float";
/*  146:     */     case 3: 
/*  147:5118 */       return "double";
/*  148:     */     case 4: 
/*  149:5120 */       return "long";
/*  150:     */     case 5: 
/*  151:5122 */       return "null";
/*  152:     */     case 6: 
/*  153:5124 */       return "uninitialized_this";
/*  154:     */     }
/*  155:5126 */     if (tag == 7) {
/*  156:5127 */       return getPayloadAsType(type, pool);
/*  157:     */     }
/*  158:5128 */     if (tag == 8) {
/*  159:5129 */       return "uninitialized";
/*  160:     */     }
/*  161:5131 */     throw new IllegalArgumentException("bad type");
/*  162:     */   }
/*  163:     */   
/*  164:     */   static Class getClassFromInternalName(String internalName)
/*  165:     */   {
/*  166:     */     try
/*  167:     */     {
/*  168:5145 */       return Class.forName(internalName.replace('/', '.'));
/*  169:     */     }
/*  170:     */     catch (ClassNotFoundException e)
/*  171:     */     {
/*  172:5147 */       throw new RuntimeException(e);
/*  173:     */     }
/*  174:     */   }
/*  175:     */   
/*  176:     */   static String toString(int[] types, ConstantPool pool)
/*  177:     */   {
/*  178:5152 */     return toString(types, types.length, pool);
/*  179:     */   }
/*  180:     */   
/*  181:     */   static String toString(int[] types, int typesTop, ConstantPool pool)
/*  182:     */   {
/*  183:5156 */     StringBuilder sb = new StringBuilder();
/*  184:5157 */     sb.append("[");
/*  185:5158 */     for (int i = 0; i < typesTop; i++)
/*  186:     */     {
/*  187:5159 */       if (i > 0) {
/*  188:5160 */         sb.append(", ");
/*  189:     */       }
/*  190:5162 */       sb.append(toString(types[i], pool));
/*  191:     */     }
/*  192:5164 */     sb.append("]");
/*  193:5165 */     return sb.toString();
/*  194:     */   }
/*  195:     */   
/*  196:     */   static void print(int[] locals, int[] stack, ConstantPool pool)
/*  197:     */   {
/*  198:5169 */     print(locals, locals.length, stack, stack.length, pool);
/*  199:     */   }
/*  200:     */   
/*  201:     */   static void print(int[] locals, int localsTop, int[] stack, int stackTop, ConstantPool pool)
/*  202:     */   {
/*  203:5174 */     System.out.print("locals: ");
/*  204:5175 */     System.out.println(toString(locals, localsTop, pool));
/*  205:5176 */     System.out.print("stack: ");
/*  206:5177 */     System.out.println(toString(stack, stackTop, pool));
/*  207:5178 */     System.out.println();
/*  208:     */   }
/*  209:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.classfile.TypeInfo
 * JD-Core Version:    0.7.0.1
 */