/*   1:    */ package javassist.runtime;
/*   2:    */ 
/*   3:    */ public class Desc
/*   4:    */ {
/*   5: 34 */   public static boolean useContextClassLoader = false;
/*   6:    */   
/*   7:    */   private static Class getClassObject(String name)
/*   8:    */     throws ClassNotFoundException
/*   9:    */   {
/*  10: 39 */     if (useContextClassLoader) {
/*  11: 40 */       return Thread.currentThread().getContextClassLoader().loadClass(name);
/*  12:    */     }
/*  13: 43 */     return Class.forName(name);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static Class getClazz(String name)
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 52 */       return getClassObject(name);
/*  21:    */     }
/*  22:    */     catch (ClassNotFoundException e)
/*  23:    */     {
/*  24: 55 */       throw new RuntimeException("$class: internal error, could not find class '" + name + "' (Desc.useContextClassLoader: " + Boolean.toString(useContextClassLoader) + ")", e);
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static Class[] getParams(String desc)
/*  29:    */   {
/*  30: 67 */     if (desc.charAt(0) != '(') {
/*  31: 68 */       throw new RuntimeException("$sig: internal error");
/*  32:    */     }
/*  33: 70 */     return getType(desc, desc.length(), 1, 0);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Class getType(String desc)
/*  37:    */   {
/*  38: 78 */     Class[] result = getType(desc, desc.length(), 0, 0);
/*  39: 79 */     if ((result == null) || (result.length != 1)) {
/*  40: 80 */       throw new RuntimeException("$type: internal error");
/*  41:    */     }
/*  42: 82 */     return result[0];
/*  43:    */   }
/*  44:    */   
/*  45:    */   private static Class[] getType(String desc, int descLen, int start, int num)
/*  46:    */   {
/*  47: 88 */     if (start >= descLen) {
/*  48: 89 */       return new Class[num];
/*  49:    */     }
/*  50: 91 */     char c = desc.charAt(start);
/*  51:    */     Class clazz;
/*  52: 92 */     switch (c)
/*  53:    */     {
/*  54:    */     case 'Z': 
/*  55: 94 */       clazz = Boolean.TYPE;
/*  56: 95 */       break;
/*  57:    */     case 'C': 
/*  58: 97 */       clazz = Character.TYPE;
/*  59: 98 */       break;
/*  60:    */     case 'B': 
/*  61:100 */       clazz = Byte.TYPE;
/*  62:101 */       break;
/*  63:    */     case 'S': 
/*  64:103 */       clazz = Short.TYPE;
/*  65:104 */       break;
/*  66:    */     case 'I': 
/*  67:106 */       clazz = Integer.TYPE;
/*  68:107 */       break;
/*  69:    */     case 'J': 
/*  70:109 */       clazz = Long.TYPE;
/*  71:110 */       break;
/*  72:    */     case 'F': 
/*  73:112 */       clazz = Float.TYPE;
/*  74:113 */       break;
/*  75:    */     case 'D': 
/*  76:115 */       clazz = Double.TYPE;
/*  77:116 */       break;
/*  78:    */     case 'V': 
/*  79:118 */       clazz = Void.TYPE;
/*  80:119 */       break;
/*  81:    */     case 'L': 
/*  82:    */     case '[': 
/*  83:122 */       return getClassType(desc, descLen, start, num);
/*  84:    */     case 'E': 
/*  85:    */     case 'G': 
/*  86:    */     case 'H': 
/*  87:    */     case 'K': 
/*  88:    */     case 'M': 
/*  89:    */     case 'N': 
/*  90:    */     case 'O': 
/*  91:    */     case 'P': 
/*  92:    */     case 'Q': 
/*  93:    */     case 'R': 
/*  94:    */     case 'T': 
/*  95:    */     case 'U': 
/*  96:    */     case 'W': 
/*  97:    */     case 'X': 
/*  98:    */     case 'Y': 
/*  99:    */     default: 
/* 100:124 */       return new Class[num];
/* 101:    */     }
/* 102:127 */     Class[] result = getType(desc, descLen, start + 1, num + 1);
/* 103:128 */     result[num] = clazz;
/* 104:129 */     return result;
/* 105:    */   }
/* 106:    */   
/* 107:    */   private static Class[] getClassType(String desc, int descLen, int start, int num)
/* 108:    */   {
/* 109:134 */     int end = start;
/* 110:135 */     while (desc.charAt(end) == '[') {
/* 111:136 */       end++;
/* 112:    */     }
/* 113:138 */     if (desc.charAt(end) == 'L')
/* 114:    */     {
/* 115:139 */       end = desc.indexOf(';', end);
/* 116:140 */       if (end < 0) {
/* 117:141 */         throw new IndexOutOfBoundsException("bad descriptor");
/* 118:    */       }
/* 119:    */     }
/* 120:    */     String cname;
/* 121:    */     String cname;
/* 122:145 */     if (desc.charAt(start) == 'L') {
/* 123:146 */       cname = desc.substring(start + 1, end);
/* 124:    */     } else {
/* 125:148 */       cname = desc.substring(start, end + 1);
/* 126:    */     }
/* 127:150 */     Class[] result = getType(desc, descLen, end + 1, num + 1);
/* 128:    */     try
/* 129:    */     {
/* 130:152 */       result[num] = getClassObject(cname.replace('/', '.'));
/* 131:    */     }
/* 132:    */     catch (ClassNotFoundException e)
/* 133:    */     {
/* 134:156 */       throw new RuntimeException(e.getMessage());
/* 135:    */     }
/* 136:159 */     return result;
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.runtime.Desc
 * JD-Core Version:    0.7.0.1
 */