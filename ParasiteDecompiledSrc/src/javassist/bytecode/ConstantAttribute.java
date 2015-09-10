/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class ConstantAttribute
/*  8:   */   extends AttributeInfo
/*  9:   */ {
/* 10:   */   public static final String tag = "ConstantValue";
/* 11:   */   
/* 12:   */   ConstantAttribute(ConstPool cp, int n, DataInputStream in)
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:34 */     super(cp, n, in);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ConstantAttribute(ConstPool cp, int index)
/* 19:   */   {
/* 20:45 */     super(cp, "ConstantValue");
/* 21:46 */     byte[] bvalue = new byte[2];
/* 22:47 */     bvalue[0] = ((byte)(index >>> 8));
/* 23:48 */     bvalue[1] = ((byte)index);
/* 24:49 */     set(bvalue);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getConstantValue()
/* 28:   */   {
/* 29:56 */     return ByteArray.readU16bit(get(), 0);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/* 33:   */   {
/* 34:68 */     int index = getConstPool().copy(getConstantValue(), newCp, classnames);
/* 35:   */     
/* 36:70 */     return new ConstantAttribute(newCp, index);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ConstantAttribute
 * JD-Core Version:    0.7.0.1
 */