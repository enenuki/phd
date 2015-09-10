/*  1:   */ package jcifs.dcerpc.ndr;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class NdrException
/*  6:   */   extends IOException
/*  7:   */ {
/*  8:   */   public static final String NO_NULL_REF = "ref pointer cannot be null";
/*  9:   */   public static final String INVALID_CONFORMANCE = "invalid array conformance";
/* 10:   */   
/* 11:   */   public NdrException(String msg)
/* 12:   */   {
/* 13:30 */     super(msg);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.ndr.NdrException
 * JD-Core Version:    0.7.0.1
 */