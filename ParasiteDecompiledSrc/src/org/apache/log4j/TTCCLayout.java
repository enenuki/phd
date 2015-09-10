/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.DateLayout;
/*   4:    */ import org.apache.log4j.spi.LoggingEvent;
/*   5:    */ 
/*   6:    */ public class TTCCLayout
/*   7:    */   extends DateLayout
/*   8:    */ {
/*   9: 77 */   private boolean threadPrinting = true;
/*  10: 78 */   private boolean categoryPrefixing = true;
/*  11: 79 */   private boolean contextPrinting = true;
/*  12: 82 */   protected final StringBuffer buf = new StringBuffer(256);
/*  13:    */   
/*  14:    */   public TTCCLayout()
/*  15:    */   {
/*  16: 92 */     setDateFormat("RELATIVE", null);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public TTCCLayout(String dateFormatType)
/*  20:    */   {
/*  21:105 */     setDateFormat(dateFormatType);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setThreadPrinting(boolean threadPrinting)
/*  25:    */   {
/*  26:115 */     this.threadPrinting = threadPrinting;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean getThreadPrinting()
/*  30:    */   {
/*  31:123 */     return this.threadPrinting;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setCategoryPrefixing(boolean categoryPrefixing)
/*  35:    */   {
/*  36:132 */     this.categoryPrefixing = categoryPrefixing;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean getCategoryPrefixing()
/*  40:    */   {
/*  41:140 */     return this.categoryPrefixing;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setContextPrinting(boolean contextPrinting)
/*  45:    */   {
/*  46:150 */     this.contextPrinting = contextPrinting;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean getContextPrinting()
/*  50:    */   {
/*  51:158 */     return this.contextPrinting;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String format(LoggingEvent event)
/*  55:    */   {
/*  56:176 */     this.buf.setLength(0);
/*  57:    */     
/*  58:178 */     dateFormat(this.buf, event);
/*  59:180 */     if (this.threadPrinting)
/*  60:    */     {
/*  61:181 */       this.buf.append('[');
/*  62:182 */       this.buf.append(event.getThreadName());
/*  63:183 */       this.buf.append("] ");
/*  64:    */     }
/*  65:185 */     this.buf.append(event.getLevel().toString());
/*  66:186 */     this.buf.append(' ');
/*  67:188 */     if (this.categoryPrefixing)
/*  68:    */     {
/*  69:189 */       this.buf.append(event.getLoggerName());
/*  70:190 */       this.buf.append(' ');
/*  71:    */     }
/*  72:193 */     if (this.contextPrinting)
/*  73:    */     {
/*  74:194 */       String ndc = event.getNDC();
/*  75:196 */       if (ndc != null)
/*  76:    */       {
/*  77:197 */         this.buf.append(ndc);
/*  78:198 */         this.buf.append(' ');
/*  79:    */       }
/*  80:    */     }
/*  81:201 */     this.buf.append("- ");
/*  82:202 */     this.buf.append(event.getRenderedMessage());
/*  83:203 */     this.buf.append(Layout.LINE_SEP);
/*  84:204 */     return this.buf.toString();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean ignoresThrowable()
/*  88:    */   {
/*  89:215 */     return true;
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.TTCCLayout
 * JD-Core Version:    0.7.0.1
 */