/*   1:    */ package org.apache.log4j.chainsaw;
/*   2:    */ 
/*   3:    */ import java.util.StringTokenizer;
/*   4:    */ import org.apache.log4j.Level;
/*   5:    */ import org.xml.sax.Attributes;
/*   6:    */ import org.xml.sax.SAXException;
/*   7:    */ import org.xml.sax.helpers.DefaultHandler;
/*   8:    */ 
/*   9:    */ class XMLFileHandler
/*  10:    */   extends DefaultHandler
/*  11:    */ {
/*  12:    */   private static final String TAG_EVENT = "log4j:event";
/*  13:    */   private static final String TAG_MESSAGE = "log4j:message";
/*  14:    */   private static final String TAG_NDC = "log4j:NDC";
/*  15:    */   private static final String TAG_THROWABLE = "log4j:throwable";
/*  16:    */   private static final String TAG_LOCATION_INFO = "log4j:locationInfo";
/*  17:    */   private final MyTableModel mModel;
/*  18:    */   private int mNumEvents;
/*  19:    */   private long mTimeStamp;
/*  20:    */   private Level mLevel;
/*  21:    */   private String mCategoryName;
/*  22:    */   private String mNDC;
/*  23:    */   private String mThreadName;
/*  24:    */   private String mMessage;
/*  25:    */   private String[] mThrowableStrRep;
/*  26:    */   private String mLocationDetails;
/*  27: 68 */   private final StringBuffer mBuf = new StringBuffer();
/*  28:    */   
/*  29:    */   XMLFileHandler(MyTableModel aModel)
/*  30:    */   {
/*  31: 76 */     this.mModel = aModel;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void startDocument()
/*  35:    */     throws SAXException
/*  36:    */   {
/*  37: 83 */     this.mNumEvents = 0;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void characters(char[] aChars, int aStart, int aLength)
/*  41:    */   {
/*  42: 88 */     this.mBuf.append(String.valueOf(aChars, aStart, aLength));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void endElement(String aNamespaceURI, String aLocalName, String aQName)
/*  46:    */   {
/*  47: 96 */     if ("log4j:event".equals(aQName))
/*  48:    */     {
/*  49: 97 */       addEvent();
/*  50: 98 */       resetData();
/*  51:    */     }
/*  52: 99 */     else if ("log4j:NDC".equals(aQName))
/*  53:    */     {
/*  54:100 */       this.mNDC = this.mBuf.toString();
/*  55:    */     }
/*  56:101 */     else if ("log4j:message".equals(aQName))
/*  57:    */     {
/*  58:102 */       this.mMessage = this.mBuf.toString();
/*  59:    */     }
/*  60:103 */     else if ("log4j:throwable".equals(aQName))
/*  61:    */     {
/*  62:104 */       StringTokenizer st = new StringTokenizer(this.mBuf.toString(), "\n\t");
/*  63:    */       
/*  64:106 */       this.mThrowableStrRep = new String[st.countTokens()];
/*  65:107 */       if (this.mThrowableStrRep.length > 0)
/*  66:    */       {
/*  67:108 */         this.mThrowableStrRep[0] = st.nextToken();
/*  68:109 */         for (int i = 1; i < this.mThrowableStrRep.length; i++) {
/*  69:110 */           this.mThrowableStrRep[i] = ("\t" + st.nextToken());
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void startElement(String aNamespaceURI, String aLocalName, String aQName, Attributes aAtts)
/*  76:    */   {
/*  77:122 */     this.mBuf.setLength(0);
/*  78:124 */     if ("log4j:event".equals(aQName))
/*  79:    */     {
/*  80:125 */       this.mThreadName = aAtts.getValue("thread");
/*  81:126 */       this.mTimeStamp = Long.parseLong(aAtts.getValue("timestamp"));
/*  82:127 */       this.mCategoryName = aAtts.getValue("logger");
/*  83:128 */       this.mLevel = Level.toLevel(aAtts.getValue("level"));
/*  84:    */     }
/*  85:129 */     else if ("log4j:locationInfo".equals(aQName))
/*  86:    */     {
/*  87:130 */       this.mLocationDetails = (aAtts.getValue("class") + "." + aAtts.getValue("method") + "(" + aAtts.getValue("file") + ":" + aAtts.getValue("line") + ")");
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   int getNumEvents()
/*  92:    */   {
/*  93:139 */     return this.mNumEvents;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void addEvent()
/*  97:    */   {
/*  98:148 */     this.mModel.addEvent(new EventDetails(this.mTimeStamp, this.mLevel, this.mCategoryName, this.mNDC, this.mThreadName, this.mMessage, this.mThrowableStrRep, this.mLocationDetails));
/*  99:    */     
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:156 */     this.mNumEvents += 1;
/* 107:    */   }
/* 108:    */   
/* 109:    */   private void resetData()
/* 110:    */   {
/* 111:161 */     this.mTimeStamp = 0L;
/* 112:162 */     this.mLevel = null;
/* 113:163 */     this.mCategoryName = null;
/* 114:164 */     this.mNDC = null;
/* 115:165 */     this.mThreadName = null;
/* 116:166 */     this.mMessage = null;
/* 117:167 */     this.mThrowableStrRep = null;
/* 118:168 */     this.mLocationDetails = null;
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.chainsaw.XMLFileHandler
 * JD-Core Version:    0.7.0.1
 */