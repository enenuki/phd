/*   1:    */ package org.apache.commons.io.input;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ 
/*   5:    */ public class XmlStreamReaderException
/*   6:    */   extends IOException
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 1L;
/*   9:    */   private final String bomEncoding;
/*  10:    */   private final String xmlGuessEncoding;
/*  11:    */   private final String xmlEncoding;
/*  12:    */   private final String contentTypeMime;
/*  13:    */   private final String contentTypeEncoding;
/*  14:    */   
/*  15:    */   public XmlStreamReaderException(String msg, String bomEnc, String xmlGuessEnc, String xmlEnc)
/*  16:    */   {
/*  17: 62 */     this(msg, null, null, bomEnc, xmlGuessEnc, xmlEnc);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public XmlStreamReaderException(String msg, String ctMime, String ctEnc, String bomEnc, String xmlGuessEnc, String xmlEnc)
/*  21:    */   {
/*  22: 80 */     super(msg);
/*  23: 81 */     this.contentTypeMime = ctMime;
/*  24: 82 */     this.contentTypeEncoding = ctEnc;
/*  25: 83 */     this.bomEncoding = bomEnc;
/*  26: 84 */     this.xmlGuessEncoding = xmlGuessEnc;
/*  27: 85 */     this.xmlEncoding = xmlEnc;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getBomEncoding()
/*  31:    */   {
/*  32: 94 */     return this.bomEncoding;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getXmlGuessEncoding()
/*  36:    */   {
/*  37:103 */     return this.xmlGuessEncoding;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getXmlEncoding()
/*  41:    */   {
/*  42:112 */     return this.xmlEncoding;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getContentTypeMime()
/*  46:    */   {
/*  47:123 */     return this.contentTypeMime;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getContentTypeEncoding()
/*  51:    */   {
/*  52:135 */     return this.contentTypeEncoding;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.input.XmlStreamReaderException
 * JD-Core Version:    0.7.0.1
 */