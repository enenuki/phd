/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import jcifs.dcerpc.rpc.sid_t;
/*  4:   */ 
/*  5:   */ public class MsrpcSamrOpenDomain
/*  6:   */   extends samr.SamrOpenDomain
/*  7:   */ {
/*  8:   */   public MsrpcSamrOpenDomain(SamrPolicyHandle handle, int access, rpc.sid_t sid, SamrDomainHandle domainHandle)
/*  9:   */   {
/* 10:29 */     super(handle, access, sid, domainHandle);
/* 11:30 */     this.ptype = 0;
/* 12:31 */     this.flags = 3;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcSamrOpenDomain
 * JD-Core Version:    0.7.0.1
 */