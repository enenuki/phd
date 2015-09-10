/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ 
/*  5:   */ public class DfsReferral
/*  6:   */   extends SmbException
/*  7:   */ {
/*  8:   */   public int pathConsumed;
/*  9:   */   public long ttl;
/* 10:   */   public String server;
/* 11:   */   public String share;
/* 12:   */   public String link;
/* 13:   */   public String path;
/* 14:   */   public boolean resolveHashes;
/* 15:   */   public long expiration;
/* 16:   */   DfsReferral next;
/* 17:   */   Map map;
/* 18:36 */   String key = null;
/* 19:   */   
/* 20:   */   public DfsReferral()
/* 21:   */   {
/* 22:40 */     this.next = this;
/* 23:   */   }
/* 24:   */   
/* 25:   */   void append(DfsReferral dr)
/* 26:   */   {
/* 27:45 */     dr.next = this.next;
/* 28:46 */     this.next = dr;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toString()
/* 32:   */   {
/* 33:50 */     return "DfsReferral[pathConsumed=" + this.pathConsumed + ",server=" + this.server + ",share=" + this.share + ",link=" + this.link + ",path=" + this.path + ",ttl=" + this.ttl + ",expiration=" + this.expiration + ",resolveHashes=" + this.resolveHashes + "]";
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.DfsReferral
 * JD-Core Version:    0.7.0.1
 */