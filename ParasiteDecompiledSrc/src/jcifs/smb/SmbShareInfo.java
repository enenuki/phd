/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ import jcifs.util.Hexdump;
/*  4:   */ 
/*  5:   */ public class SmbShareInfo
/*  6:   */   implements FileEntry
/*  7:   */ {
/*  8:   */   protected String netName;
/*  9:   */   protected int type;
/* 10:   */   protected String remark;
/* 11:   */   
/* 12:   */   public SmbShareInfo() {}
/* 13:   */   
/* 14:   */   public SmbShareInfo(String netName, int type, String remark)
/* 15:   */   {
/* 16:33 */     this.netName = netName;
/* 17:34 */     this.type = type;
/* 18:35 */     this.remark = remark;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getName()
/* 22:   */   {
/* 23:38 */     return this.netName;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getType()
/* 27:   */   {
/* 28:43 */     switch (this.type & 0xFFFF)
/* 29:   */     {
/* 30:   */     case 1: 
/* 31:45 */       return 32;
/* 32:   */     case 3: 
/* 33:47 */       return 16;
/* 34:   */     }
/* 35:49 */     return 8;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getAttributes()
/* 39:   */   {
/* 40:52 */     return 17;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public long createTime()
/* 44:   */   {
/* 45:55 */     return 0L;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public long lastModified()
/* 49:   */   {
/* 50:58 */     return 0L;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public long length()
/* 54:   */   {
/* 55:61 */     return 0L;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean equals(Object obj)
/* 59:   */   {
/* 60:65 */     if ((obj instanceof SmbShareInfo))
/* 61:   */     {
/* 62:66 */       SmbShareInfo si = (SmbShareInfo)obj;
/* 63:67 */       return this.netName.equals(si.netName);
/* 64:   */     }
/* 65:69 */     return false;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public int hashCode()
/* 69:   */   {
/* 70:72 */     int hashCode = this.netName.hashCode();
/* 71:73 */     return hashCode;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public String toString()
/* 75:   */   {
/* 76:77 */     return new String("SmbShareInfo[netName=" + this.netName + ",type=0x" + Hexdump.toHexString(this.type, 8) + ",remark=" + this.remark + "]");
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.SmbShareInfo
 * JD-Core Version:    0.7.0.1
 */