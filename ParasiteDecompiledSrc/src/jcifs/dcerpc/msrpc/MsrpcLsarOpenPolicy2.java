/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ public class MsrpcLsarOpenPolicy2
/*  4:   */   extends lsarpc.LsarOpenPolicy2
/*  5:   */ {
/*  6:   */   public MsrpcLsarOpenPolicy2(String server, int access, LsaPolicyHandle policyHandle)
/*  7:   */   {
/*  8:25 */     super(server, new lsarpc.LsarObjectAttributes(), access, policyHandle);
/*  9:26 */     this.object_attributes.length = 24;
/* 10:27 */     lsarpc.LsarQosInfo qos = new lsarpc.LsarQosInfo();
/* 11:28 */     qos.length = 12;
/* 12:29 */     qos.impersonation_level = 2;
/* 13:30 */     qos.context_mode = 1;
/* 14:31 */     qos.effective_only = 0;
/* 15:32 */     this.object_attributes.security_quality_of_service = qos;
/* 16:33 */     this.ptype = 0;
/* 17:34 */     this.flags = 3;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcLsarOpenPolicy2
 * JD-Core Version:    0.7.0.1
 */