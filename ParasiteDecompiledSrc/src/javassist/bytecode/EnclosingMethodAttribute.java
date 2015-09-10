/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class EnclosingMethodAttribute
/*   8:    */   extends AttributeInfo
/*   9:    */ {
/*  10:    */   public static final String tag = "EnclosingMethod";
/*  11:    */   
/*  12:    */   EnclosingMethodAttribute(ConstPool cp, int n, DataInputStream in)
/*  13:    */     throws IOException
/*  14:    */   {
/*  15: 34 */     super(cp, n, in);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public EnclosingMethodAttribute(ConstPool cp, String className, String methodName, String methodDesc)
/*  19:    */   {
/*  20: 47 */     super(cp, "EnclosingMethod");
/*  21: 48 */     int ci = cp.addClassInfo(className);
/*  22: 49 */     int ni = cp.addNameAndTypeInfo(methodName, methodDesc);
/*  23: 50 */     byte[] bvalue = new byte[4];
/*  24: 51 */     bvalue[0] = ((byte)(ci >>> 8));
/*  25: 52 */     bvalue[1] = ((byte)ci);
/*  26: 53 */     bvalue[2] = ((byte)(ni >>> 8));
/*  27: 54 */     bvalue[3] = ((byte)ni);
/*  28: 55 */     set(bvalue);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public EnclosingMethodAttribute(ConstPool cp, String className)
/*  32:    */   {
/*  33: 66 */     super(cp, "EnclosingMethod");
/*  34: 67 */     int ci = cp.addClassInfo(className);
/*  35: 68 */     int ni = 0;
/*  36: 69 */     byte[] bvalue = new byte[4];
/*  37: 70 */     bvalue[0] = ((byte)(ci >>> 8));
/*  38: 71 */     bvalue[1] = ((byte)ci);
/*  39: 72 */     bvalue[2] = ((byte)(ni >>> 8));
/*  40: 73 */     bvalue[3] = ((byte)ni);
/*  41: 74 */     set(bvalue);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int classIndex()
/*  45:    */   {
/*  46: 81 */     return ByteArray.readU16bit(get(), 0);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int methodIndex()
/*  50:    */   {
/*  51: 88 */     return ByteArray.readU16bit(get(), 2);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String className()
/*  55:    */   {
/*  56: 95 */     return getConstPool().getClassInfo(classIndex());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String methodName()
/*  60:    */   {
/*  61:102 */     ConstPool cp = getConstPool();
/*  62:103 */     int mi = methodIndex();
/*  63:104 */     int ni = cp.getNameAndTypeName(mi);
/*  64:105 */     return cp.getUtf8Info(ni);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String methodDescriptor()
/*  68:    */   {
/*  69:112 */     ConstPool cp = getConstPool();
/*  70:113 */     int mi = methodIndex();
/*  71:114 */     int ti = cp.getNameAndTypeDescriptor(mi);
/*  72:115 */     return cp.getUtf8Info(ti);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  76:    */   {
/*  77:127 */     if (methodIndex() == 0) {
/*  78:128 */       return new EnclosingMethodAttribute(newCp, className());
/*  79:    */     }
/*  80:130 */     return new EnclosingMethodAttribute(newCp, className(), methodName(), methodDescriptor());
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.EnclosingMethodAttribute
 * JD-Core Version:    0.7.0.1
 */