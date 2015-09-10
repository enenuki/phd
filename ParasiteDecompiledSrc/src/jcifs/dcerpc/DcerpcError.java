/*  1:   */ package jcifs.dcerpc;
/*  2:   */ 
/*  3:   */ public abstract interface DcerpcError
/*  4:   */ {
/*  5:   */   public static final int DCERPC_FAULT_OTHER = 1;
/*  6:   */   public static final int DCERPC_FAULT_ACCESS_DENIED = 5;
/*  7:   */   public static final int DCERPC_FAULT_CANT_PERFORM = 1752;
/*  8:   */   public static final int DCERPC_FAULT_NDR = 1783;
/*  9:   */   public static final int DCERPC_FAULT_INVALID_TAG = 469762054;
/* 10:   */   public static final int DCERPC_FAULT_CONTEXT_MISMATCH = 469762074;
/* 11:   */   public static final int DCERPC_FAULT_OP_RNG_ERROR = 469827586;
/* 12:   */   public static final int DCERPC_FAULT_UNK_IF = 469827587;
/* 13:   */   public static final int DCERPC_FAULT_PROTO_ERROR = 469827595;
/* 14:34 */   public static final int[] DCERPC_FAULT_CODES = { 1, 5, 1752, 1783, 469762054, 469762074, 469827586, 469827587, 469827595 };
/* 15:46 */   public static final String[] DCERPC_FAULT_MESSAGES = { "DCERPC_FAULT_OTHER", "DCERPC_FAULT_ACCESS_DENIED", "DCERPC_FAULT_CANT_PERFORM", "DCERPC_FAULT_NDR", "DCERPC_FAULT_INVALID_TAG", "DCERPC_FAULT_CONTEXT_MISMATCH", "DCERPC_FAULT_OP_RNG_ERROR", "DCERPC_FAULT_UNK_IF", "DCERPC_FAULT_PROTO_ERROR" };
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.DcerpcError
 * JD-Core Version:    0.7.0.1
 */