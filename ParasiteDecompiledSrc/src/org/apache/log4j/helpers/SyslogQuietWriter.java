/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.io.Writer;
/*  4:   */ import org.apache.log4j.spi.ErrorHandler;
/*  5:   */ 
/*  6:   */ public class SyslogQuietWriter
/*  7:   */   extends QuietWriter
/*  8:   */ {
/*  9:   */   int syslogFacility;
/* 10:   */   int level;
/* 11:   */   
/* 12:   */   public SyslogQuietWriter(Writer writer, int syslogFacility, ErrorHandler eh)
/* 13:   */   {
/* 14:38 */     super(writer, eh);
/* 15:39 */     this.syslogFacility = syslogFacility;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setLevel(int level)
/* 19:   */   {
/* 20:44 */     this.level = level;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void setSyslogFacility(int syslogFacility)
/* 24:   */   {
/* 25:49 */     this.syslogFacility = syslogFacility;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void write(String string)
/* 29:   */   {
/* 30:54 */     super.write("<" + (this.syslogFacility | this.level) + ">" + string);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.SyslogQuietWriter
 * JD-Core Version:    0.7.0.1
 */