/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ public class MsrpcSamrConnect2
/*  4:   */   extends samr.SamrConnect2
/*  5:   */ {
/*  6:   */   public MsrpcSamrConnect2(String server, int access, SamrPolicyHandle policyHandle)
/*  7:   */   {
/*  8:24 */     super(server, access, policyHandle);
/*  9:25 */     this.ptype = 0;
/* 10:26 */     this.flags = 3;
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcSamrConnect2
 * JD-Core Version:    0.7.0.1
 */