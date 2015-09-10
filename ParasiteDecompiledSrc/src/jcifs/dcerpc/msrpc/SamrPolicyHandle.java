/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import jcifs.dcerpc.DcerpcException;
/*  5:   */ import jcifs.dcerpc.DcerpcHandle;
/*  6:   */ import jcifs.dcerpc.rpc.policy_handle;
/*  7:   */ 
/*  8:   */ public class SamrPolicyHandle
/*  9:   */   extends rpc.policy_handle
/* 10:   */ {
/* 11:   */   public SamrPolicyHandle(DcerpcHandle handle, String server, int access)
/* 12:   */     throws IOException
/* 13:   */   {
/* 14:28 */     if (server == null) {
/* 15:29 */       server = "\\\\";
/* 16:   */     }
/* 17:30 */     MsrpcSamrConnect4 rpc = new MsrpcSamrConnect4(server, access, this);
/* 18:   */     try
/* 19:   */     {
/* 20:32 */       handle.sendrecv(rpc);
/* 21:   */     }
/* 22:   */     catch (DcerpcException de)
/* 23:   */     {
/* 24:34 */       if (de.getErrorCode() != 469827586) {
/* 25:35 */         throw de;
/* 26:   */       }
/* 27:37 */       MsrpcSamrConnect2 rpc2 = new MsrpcSamrConnect2(server, access, this);
/* 28:38 */       handle.sendrecv(rpc2);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void close()
/* 33:   */     throws IOException
/* 34:   */   {}
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.SamrPolicyHandle
 * JD-Core Version:    0.7.0.1
 */