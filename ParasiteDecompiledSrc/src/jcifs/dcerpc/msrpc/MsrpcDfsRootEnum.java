/*  1:   */ package jcifs.dcerpc.msrpc;
/*  2:   */ 
/*  3:   */ import jcifs.dcerpc.ndr.NdrLong;
/*  4:   */ import jcifs.smb.FileEntry;
/*  5:   */ import jcifs.smb.SmbShareInfo;
/*  6:   */ 
/*  7:   */ public class MsrpcDfsRootEnum
/*  8:   */   extends netdfs.NetrDfsEnumEx
/*  9:   */ {
/* 10:   */   public MsrpcDfsRootEnum(String server)
/* 11:   */   {
/* 12:27 */     super(server, 200, 65535, new netdfs.DfsEnumStruct(), new NdrLong(0));
/* 13:28 */     this.info.level = this.level;
/* 14:29 */     this.info.e = new netdfs.DfsEnumArray200();
/* 15:30 */     this.ptype = 0;
/* 16:31 */     this.flags = 3;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public FileEntry[] getEntries()
/* 20:   */   {
/* 21:35 */     netdfs.DfsEnumArray200 a200 = (netdfs.DfsEnumArray200)this.info.e;
/* 22:36 */     SmbShareInfo[] entries = new SmbShareInfo[a200.count];
/* 23:37 */     for (int i = 0; i < a200.count; i++) {
/* 24:38 */       entries[i] = new SmbShareInfo(a200.s[i].dfs_name, 0, null);
/* 25:   */     }
/* 26:40 */     return entries;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.dcerpc.msrpc.MsrpcDfsRootEnum
 * JD-Core Version:    0.7.0.1
 */