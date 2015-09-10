/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class SourceFileAttribute
/*  8:   */   extends AttributeInfo
/*  9:   */ {
/* 10:   */   public static final String tag = "SourceFile";
/* 11:   */   
/* 12:   */   SourceFileAttribute(ConstPool cp, int n, DataInputStream in)
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:34 */     super(cp, n, in);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public SourceFileAttribute(ConstPool cp, String filename)
/* 19:   */   {
/* 20:44 */     super(cp, "SourceFile");
/* 21:45 */     int index = cp.addUtf8Info(filename);
/* 22:46 */     byte[] bvalue = new byte[2];
/* 23:47 */     bvalue[0] = ((byte)(index >>> 8));
/* 24:48 */     bvalue[1] = ((byte)index);
/* 25:49 */     set(bvalue);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getFileName()
/* 29:   */   {
/* 30:56 */     return getConstPool().getUtf8Info(ByteArray.readU16bit(get(), 0));
/* 31:   */   }
/* 32:   */   
/* 33:   */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/* 34:   */   {
/* 35:68 */     return new SourceFileAttribute(newCp, getFileName());
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.SourceFileAttribute
 * JD-Core Version:    0.7.0.1
 */