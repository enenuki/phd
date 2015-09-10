/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.Priority;
/*   4:    */ import org.apache.log4j.spi.LocationInfo;
/*   5:    */ import org.apache.log4j.spi.LoggingEvent;
/*   6:    */ 
/*   7:    */ class EventDetails
/*   8:    */ {
/*   9:    */   private final long mTimeStamp;
/*  10:    */   private final Priority mPriority;
/*  11:    */   private final String mCategoryName;
/*  12:    */   private final String mNDC;
/*  13:    */   private final String mThreadName;
/*  14:    */   private final String mMessage;
/*  15:    */   private final String[] mThrowableStrRep;
/*  16:    */   private final String mLocationDetails;
/*  17:    */   
/*  18:    */   EventDetails(long aTimeStamp, Priority aPriority, String aCategoryName, String aNDC, String aThreadName, String aMessage, String[] aThrowableStrRep, String aLocationDetails)
/*  19:    */   {
/*  20: 68 */     this.mTimeStamp = aTimeStamp;
/*  21: 69 */     this.mPriority = aPriority;
/*  22: 70 */     this.mCategoryName = aCategoryName;
/*  23: 71 */     this.mNDC = aNDC;
/*  24: 72 */     this.mThreadName = aThreadName;
/*  25: 73 */     this.mMessage = aMessage;
/*  26: 74 */     this.mThrowableStrRep = aThrowableStrRep;
/*  27: 75 */     this.mLocationDetails = aLocationDetails;
/*  28:    */   }
/*  29:    */   
/*  30:    */   EventDetails(LoggingEvent aEvent)
/*  31:    */   {
/*  32: 85 */     this(aEvent.timeStamp, aEvent.getLevel(), aEvent.getLoggerName(), aEvent.getNDC(), aEvent.getThreadName(), aEvent.getRenderedMessage(), aEvent.getThrowableStrRep(), aEvent.getLocationInformation() == null ? null : aEvent.getLocationInformation().fullInfo);
/*  33:    */   }
/*  34:    */   
/*  35:    */   long getTimeStamp()
/*  36:    */   {
/*  37: 98 */     return this.mTimeStamp;
/*  38:    */   }
/*  39:    */   
/*  40:    */   Priority getPriority()
/*  41:    */   {
/*  42:103 */     return this.mPriority;
/*  43:    */   }
/*  44:    */   
/*  45:    */   String getCategoryName()
/*  46:    */   {
/*  47:108 */     return this.mCategoryName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   String getNDC()
/*  51:    */   {
/*  52:113 */     return this.mNDC;
/*  53:    */   }
/*  54:    */   
/*  55:    */   String getThreadName()
/*  56:    */   {
/*  57:118 */     return this.mThreadName;
/*  58:    */   }
/*  59:    */   
/*  60:    */   String getMessage()
/*  61:    */   {
/*  62:123 */     return this.mMessage;
/*  63:    */   }
/*  64:    */   
/*  65:    */   String getLocationDetails()
/*  66:    */   {
/*  67:128 */     return this.mLocationDetails;
/*  68:    */   }
/*  69:    */   
/*  70:    */   String[] getThrowableStrRep()
/*  71:    */   {
/*  72:133 */     return this.mThrowableStrRep;
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.EventDetails
 * JD-Core Version:    0.7.0.1
 */