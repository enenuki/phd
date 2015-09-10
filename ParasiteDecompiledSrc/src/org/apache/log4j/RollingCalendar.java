/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.GregorianCalendar;
/*   6:    */ import java.util.Locale;
/*   7:    */ import java.util.TimeZone;
/*   8:    */ 
/*   9:    */ class RollingCalendar
/*  10:    */   extends GregorianCalendar
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = -3560331770601814177L;
/*  13:381 */   int type = -1;
/*  14:    */   
/*  15:    */   RollingCalendar() {}
/*  16:    */   
/*  17:    */   RollingCalendar(TimeZone tz, Locale locale)
/*  18:    */   {
/*  19:388 */     super(tz, locale);
/*  20:    */   }
/*  21:    */   
/*  22:    */   void setType(int type)
/*  23:    */   {
/*  24:392 */     this.type = type;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public long getNextCheckMillis(Date now)
/*  28:    */   {
/*  29:396 */     return getNextCheckDate(now).getTime();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Date getNextCheckDate(Date now)
/*  33:    */   {
/*  34:400 */     setTime(now);
/*  35:402 */     switch (this.type)
/*  36:    */     {
/*  37:    */     case 0: 
/*  38:404 */       set(13, 0);
/*  39:405 */       set(14, 0);
/*  40:406 */       add(12, 1);
/*  41:407 */       break;
/*  42:    */     case 1: 
/*  43:409 */       set(12, 0);
/*  44:410 */       set(13, 0);
/*  45:411 */       set(14, 0);
/*  46:412 */       add(11, 1);
/*  47:413 */       break;
/*  48:    */     case 2: 
/*  49:415 */       set(12, 0);
/*  50:416 */       set(13, 0);
/*  51:417 */       set(14, 0);
/*  52:418 */       int hour = get(11);
/*  53:419 */       if (hour < 12)
/*  54:    */       {
/*  55:420 */         set(11, 12);
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59:422 */         set(11, 0);
/*  60:423 */         add(5, 1);
/*  61:    */       }
/*  62:425 */       break;
/*  63:    */     case 3: 
/*  64:427 */       set(11, 0);
/*  65:428 */       set(12, 0);
/*  66:429 */       set(13, 0);
/*  67:430 */       set(14, 0);
/*  68:431 */       add(5, 1);
/*  69:432 */       break;
/*  70:    */     case 4: 
/*  71:434 */       set(7, getFirstDayOfWeek());
/*  72:435 */       set(11, 0);
/*  73:436 */       set(12, 0);
/*  74:437 */       set(13, 0);
/*  75:438 */       set(14, 0);
/*  76:439 */       add(3, 1);
/*  77:440 */       break;
/*  78:    */     case 5: 
/*  79:442 */       set(5, 1);
/*  80:443 */       set(11, 0);
/*  81:444 */       set(12, 0);
/*  82:445 */       set(13, 0);
/*  83:446 */       set(14, 0);
/*  84:447 */       add(2, 1);
/*  85:448 */       break;
/*  86:    */     default: 
/*  87:450 */       throw new IllegalStateException("Unknown periodicity type.");
/*  88:    */     }
/*  89:452 */     return getTime();
/*  90:    */   }
/*  91:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.RollingCalendar
 * JD-Core Version:    0.7.0.1
 */