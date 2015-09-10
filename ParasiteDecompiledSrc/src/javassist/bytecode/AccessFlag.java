/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ public class AccessFlag
/*   4:    */ {
/*   5:    */   public static final int PUBLIC = 1;
/*   6:    */   public static final int PRIVATE = 2;
/*   7:    */   public static final int PROTECTED = 4;
/*   8:    */   public static final int STATIC = 8;
/*   9:    */   public static final int FINAL = 16;
/*  10:    */   public static final int SYNCHRONIZED = 32;
/*  11:    */   public static final int VOLATILE = 64;
/*  12:    */   public static final int BRIDGE = 64;
/*  13:    */   public static final int TRANSIENT = 128;
/*  14:    */   public static final int VARARGS = 128;
/*  15:    */   public static final int NATIVE = 256;
/*  16:    */   public static final int INTERFACE = 512;
/*  17:    */   public static final int ABSTRACT = 1024;
/*  18:    */   public static final int STRICT = 2048;
/*  19:    */   public static final int SYNTHETIC = 4096;
/*  20:    */   public static final int ANNOTATION = 8192;
/*  21:    */   public static final int ENUM = 16384;
/*  22:    */   public static final int SUPER = 32;
/*  23:    */   
/*  24:    */   public static int setPublic(int accflags)
/*  25:    */   {
/*  26: 51 */     return accflags & 0xFFFFFFF9 | 0x1;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static int setProtected(int accflags)
/*  30:    */   {
/*  31: 59 */     return accflags & 0xFFFFFFFC | 0x4;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static int setPrivate(int accflags)
/*  35:    */   {
/*  36: 67 */     return accflags & 0xFFFFFFFA | 0x2;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static int setPackage(int accflags)
/*  40:    */   {
/*  41: 74 */     return accflags & 0xFFFFFFF8;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean isPublic(int accflags)
/*  45:    */   {
/*  46: 81 */     return (accflags & 0x1) != 0;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static boolean isProtected(int accflags)
/*  50:    */   {
/*  51: 88 */     return (accflags & 0x4) != 0;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static boolean isPrivate(int accflags)
/*  55:    */   {
/*  56: 95 */     return (accflags & 0x2) != 0;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static boolean isPackage(int accflags)
/*  60:    */   {
/*  61:103 */     return (accflags & 0x7) == 0;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static int clear(int accflags, int clearBit)
/*  65:    */   {
/*  66:110 */     return accflags & (clearBit ^ 0xFFFFFFFF);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static int of(int modifier)
/*  70:    */   {
/*  71:120 */     return modifier;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static int toModifier(int accflags)
/*  75:    */   {
/*  76:130 */     return accflags;
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.AccessFlag
 * JD-Core Version:    0.7.0.1
 */