/*  1:   */ package org.hibernate.id;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Properties;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.internal.CoreMessageLogger;
/*  8:   */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  9:   */ import org.hibernate.type.Type;
/* 10:   */ import org.jboss.logging.Logger;
/* 11:   */ 
/* 12:   */ public class UUIDHexGenerator
/* 13:   */   extends AbstractUUIDGenerator
/* 14:   */   implements Configurable
/* 15:   */ {
/* 16:50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, UUIDHexGenerator.class.getName());
/* 17:52 */   private static boolean warned = false;
/* 18:54 */   private String sep = "";
/* 19:   */   
/* 20:   */   public UUIDHexGenerator()
/* 21:   */   {
/* 22:57 */     if (!warned)
/* 23:   */     {
/* 24:58 */       warned = true;
/* 25:59 */       LOG.usingUuidHexGenerator(getClass().getName(), UUIDGenerator.class.getName());
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void configure(Type type, Properties params, Dialect d)
/* 30:   */   {
/* 31:67 */     this.sep = ConfigurationHelper.getString("separator", params, "");
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Serializable generate(SessionImplementor session, Object obj)
/* 35:   */   {
/* 36:74 */     return 36 + format(getIP()) + this.sep + format(getJVM()) + this.sep + format(getHiTime()) + this.sep + format(getLoTime()) + this.sep + format(getCount());
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected String format(int intValue)
/* 40:   */   {
/* 41:84 */     String formatted = Integer.toHexString(intValue);
/* 42:85 */     StringBuffer buf = new StringBuffer("00000000");
/* 43:86 */     buf.replace(8 - formatted.length(), 8, formatted);
/* 44:87 */     return buf.toString();
/* 45:   */   }
/* 46:   */   
/* 47:   */   protected String format(short shortValue)
/* 48:   */   {
/* 49:91 */     String formatted = Integer.toHexString(shortValue);
/* 50:92 */     StringBuffer buf = new StringBuffer("0000");
/* 51:93 */     buf.replace(4 - formatted.length(), 4, formatted);
/* 52:94 */     return buf.toString();
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.UUIDHexGenerator
 * JD-Core Version:    0.7.0.1
 */