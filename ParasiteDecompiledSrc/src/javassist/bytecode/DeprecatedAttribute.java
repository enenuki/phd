/*  1:   */ package javassist.bytecode;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class DeprecatedAttribute
/*  8:   */   extends AttributeInfo
/*  9:   */ {
/* 10:   */   public static final String tag = "Deprecated";
/* 11:   */   
/* 12:   */   DeprecatedAttribute(ConstPool cp, int n, DataInputStream in)
/* 13:   */     throws IOException
/* 14:   */   {
/* 15:34 */     super(cp, n, in);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public DeprecatedAttribute(ConstPool cp)
/* 19:   */   {
/* 20:43 */     super(cp, "Deprecated", new byte[0]);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/* 24:   */   {
/* 25:53 */     return new DeprecatedAttribute(newCp);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.DeprecatedAttribute
 * JD-Core Version:    0.7.0.1
 */