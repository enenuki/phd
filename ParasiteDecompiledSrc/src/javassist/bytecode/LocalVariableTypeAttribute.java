/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class LocalVariableTypeAttribute
/*  8:   */   extends LocalVariableAttribute
/*  9:   */ {
/* 10:   */   public static final String tag = "LocalVariableTypeTable";
/* 11:   */   
/* 12:   */   public LocalVariableTypeAttribute(ConstPool cp)
/* 13:   */   {
/* 14:37 */     super(cp, "LocalVariableTypeTable", new byte[2]);
/* 15:38 */     ByteArray.write16bit(0, this.info, 0);
/* 16:   */   }
/* 17:   */   
/* 18:   */   LocalVariableTypeAttribute(ConstPool cp, int n, DataInputStream in)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:44 */     super(cp, n, in);
/* 22:   */   }
/* 23:   */   
/* 24:   */   private LocalVariableTypeAttribute(ConstPool cp, byte[] dest)
/* 25:   */   {
/* 26:48 */     super(cp, "LocalVariableTypeTable", dest);
/* 27:   */   }
/* 28:   */   
/* 29:   */   String renameEntry(String desc, String oldname, String newname)
/* 30:   */   {
/* 31:52 */     return SignatureAttribute.renameClass(desc, oldname, newname);
/* 32:   */   }
/* 33:   */   
/* 34:   */   String renameEntry(String desc, Map classnames)
/* 35:   */   {
/* 36:56 */     return SignatureAttribute.renameClass(desc, classnames);
/* 37:   */   }
/* 38:   */   
/* 39:   */   LocalVariableAttribute makeThisAttr(ConstPool cp, byte[] dest)
/* 40:   */   {
/* 41:60 */     return new LocalVariableTypeAttribute(cp, dest);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.LocalVariableTypeAttribute
 * JD-Core Version:    0.7.0.1
 */