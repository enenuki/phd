/*   1:    */ package javassist;
/*   2:    */ 
/*   3:    */ public class Modifier
/*   4:    */ {
/*   5:    */   public static final int PUBLIC = 1;
/*   6:    */   public static final int PRIVATE = 2;
/*   7:    */   public static final int PROTECTED = 4;
/*   8:    */   public static final int STATIC = 8;
/*   9:    */   public static final int FINAL = 16;
/*  10:    */   public static final int SYNCHRONIZED = 32;
/*  11:    */   public static final int VOLATILE = 64;
/*  12:    */   public static final int TRANSIENT = 128;
/*  13:    */   public static final int NATIVE = 256;
/*  14:    */   public static final int INTERFACE = 512;
/*  15:    */   public static final int ABSTRACT = 1024;
/*  16:    */   public static final int STRICT = 2048;
/*  17:    */   public static final int ANNOTATION = 8192;
/*  18:    */   public static final int ENUM = 16384;
/*  19:    */   
/*  20:    */   public static boolean isPublic(int mod)
/*  21:    */   {
/*  22: 51 */     return (mod & 0x1) != 0;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static boolean isPrivate(int mod)
/*  26:    */   {
/*  27: 59 */     return (mod & 0x2) != 0;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static boolean isProtected(int mod)
/*  31:    */   {
/*  32: 67 */     return (mod & 0x4) != 0;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static boolean isPackage(int mod)
/*  36:    */   {
/*  37: 75 */     return (mod & 0x7) == 0;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static boolean isStatic(int mod)
/*  41:    */   {
/*  42: 83 */     return (mod & 0x8) != 0;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static boolean isFinal(int mod)
/*  46:    */   {
/*  47: 91 */     return (mod & 0x10) != 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static boolean isSynchronized(int mod)
/*  51:    */   {
/*  52: 99 */     return (mod & 0x20) != 0;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static boolean isVolatile(int mod)
/*  56:    */   {
/*  57:107 */     return (mod & 0x40) != 0;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static boolean isTransient(int mod)
/*  61:    */   {
/*  62:115 */     return (mod & 0x80) != 0;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static boolean isNative(int mod)
/*  66:    */   {
/*  67:123 */     return (mod & 0x100) != 0;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static boolean isInterface(int mod)
/*  71:    */   {
/*  72:131 */     return (mod & 0x200) != 0;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static boolean isAnnotation(int mod)
/*  76:    */   {
/*  77:141 */     return (mod & 0x2000) != 0;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static boolean isEnum(int mod)
/*  81:    */   {
/*  82:151 */     return (mod & 0x4000) != 0;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static boolean isAbstract(int mod)
/*  86:    */   {
/*  87:159 */     return (mod & 0x400) != 0;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static boolean isStrict(int mod)
/*  91:    */   {
/*  92:167 */     return (mod & 0x800) != 0;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static int setPublic(int mod)
/*  96:    */   {
/*  97:175 */     return mod & 0xFFFFFFF9 | 0x1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static int setProtected(int mod)
/* 101:    */   {
/* 102:183 */     return mod & 0xFFFFFFFC | 0x4;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static int setPrivate(int mod)
/* 106:    */   {
/* 107:191 */     return mod & 0xFFFFFFFA | 0x2;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static int setPackage(int mod)
/* 111:    */   {
/* 112:198 */     return mod & 0xFFFFFFF8;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static int clear(int mod, int clearBit)
/* 116:    */   {
/* 117:205 */     return mod & (clearBit ^ 0xFFFFFFFF);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static String toString(int mod)
/* 121:    */   {
/* 122:215 */     return java.lang.reflect.Modifier.toString(mod);
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.Modifier
 * JD-Core Version:    0.7.0.1
 */