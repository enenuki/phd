/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import jcifs.smb.FileEntry;
/*  4:   */ import jcifs.smb.SmbShareInfo;
/*  5:   */ 
/*  6:   */ public class MsrpcShareEnum
/*  7:   */   extends srvsvc.ShareEnumAll
/*  8:   */ {
/*  9:   */   class MsrpcShareInfo1
/* 10:   */     extends SmbShareInfo
/* 11:   */   {
/* 12:   */     MsrpcShareInfo1(srvsvc.ShareInfo1 info1)
/* 13:   */     {
/* 14:30 */       this.netName = info1.netname;
/* 15:31 */       this.type = info1.type;
/* 16:32 */       this.remark = info1.remark;
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public MsrpcShareEnum(String server)
/* 21:   */   {
/* 22:37 */     super("\\\\" + server, 1, new srvsvc.ShareInfoCtr1(), -1, 0, 0);
/* 23:38 */     this.ptype = 0;
/* 24:39 */     this.flags = 3;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public FileEntry[] getEntries()
/* 28:   */   {
/* 29:47 */     srvsvc.ShareInfoCtr1 ctr = (srvsvc.ShareInfoCtr1)this.info;
/* 30:48 */     MsrpcShareInfo1[] entries = new MsrpcShareInfo1[ctr.count];
/* 31:49 */     for (int i = 0; i < ctr.count; i++) {
/* 32:50 */       entries[i] = new MsrpcShareInfo1(ctr.array[i]);
/* 33:   */     }
/* 34:52 */     return entries;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcShareEnum
 * JD-Core Version:    0.7.0.1
 */