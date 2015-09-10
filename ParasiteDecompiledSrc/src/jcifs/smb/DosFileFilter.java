/*  1:   */ package jcifs.smb;
/*  2:   */ 
/*  3:   */ public class DosFileFilter
/*  4:   */   implements SmbFileFilter
/*  5:   */ {
/*  6:   */   protected String wildcard;
/*  7:   */   protected int attributes;
/*  8:   */   
/*  9:   */   public DosFileFilter(String wildcard, int attributes)
/* 10:   */   {
/* 11:32 */     this.wildcard = wildcard;
/* 12:33 */     this.attributes = attributes;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean accept(SmbFile file)
/* 16:   */     throws SmbException
/* 17:   */   {
/* 18:44 */     return (file.getAttributes() & this.attributes) != 0;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jcifs.smb.DosFileFilter
 * JD-Core Version:    0.7.0.1
 */