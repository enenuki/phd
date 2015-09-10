/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import jcifs.dcerpc.ndr.NdrObject;
/*  4:   */ 
/*  5:   */ public class MsrpcQueryInformationPolicy
/*  6:   */   extends lsarpc.LsarQueryInformationPolicy
/*  7:   */ {
/*  8:   */   public MsrpcQueryInformationPolicy(LsaPolicyHandle policyHandle, short level, NdrObject info)
/*  9:   */   {
/* 10:30 */     super(policyHandle, level, info);
/* 11:31 */     this.ptype = 0;
/* 12:32 */     this.flags = 3;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcQueryInformationPolicy
 * JD-Core Version:    0.7.0.1
 */