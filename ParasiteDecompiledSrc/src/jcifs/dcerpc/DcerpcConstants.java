/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ public abstract interface DcerpcConstants
/*  4:   */ {
/*  5:24 */   public static final UUID DCERPC_UUID_SYNTAX_NDR = new UUID("8a885d04-1ceb-11c9-9fe8-08002b104860");
/*  6:   */   public static final int DCERPC_FIRST_FRAG = 1;
/*  7:   */   public static final int DCERPC_LAST_FRAG = 2;
/*  8:   */   public static final int DCERPC_PENDING_CANCEL = 4;
/*  9:   */   public static final int DCERPC_RESERVED_1 = 8;
/* 10:   */   public static final int DCERPC_CONC_MPX = 16;
/* 11:   */   public static final int DCERPC_DID_NOT_EXECUTE = 32;
/* 12:   */   public static final int DCERPC_MAYBE = 64;
/* 13:   */   public static final int DCERPC_OBJECT_UUID = 128;
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcConstants
 * JD-Core Version:    0.7.0.1
 */