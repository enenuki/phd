/*   1:    */ package org.apache.xml.serializer.dom3;
/*   2:    */ 
/*   3:    */ import java.io.OutputStream;
/*   4:    */ import java.io.Writer;
/*   5:    */ import org.w3c.dom.ls.LSOutput;
/*   6:    */ 
/*   7:    */ final class DOMOutputImpl
/*   8:    */   implements LSOutput
/*   9:    */ {
/*  10: 63 */   private Writer fCharStream = null;
/*  11: 64 */   private OutputStream fByteStream = null;
/*  12: 65 */   private String fSystemId = null;
/*  13: 66 */   private String fEncoding = null;
/*  14:    */   
/*  15:    */   public Writer getCharacterStream()
/*  16:    */   {
/*  17: 82 */     return this.fCharStream;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setCharacterStream(Writer characterStream)
/*  21:    */   {
/*  22: 94 */     this.fCharStream = characterStream;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public OutputStream getByteStream()
/*  26:    */   {
/*  27:106 */     return this.fByteStream;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setByteStream(OutputStream byteStream)
/*  31:    */   {
/*  32:118 */     this.fByteStream = byteStream;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getSystemId()
/*  36:    */   {
/*  37:131 */     return this.fSystemId;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setSystemId(String systemId)
/*  41:    */   {
/*  42:144 */     this.fSystemId = systemId;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getEncoding()
/*  46:    */   {
/*  47:159 */     return this.fEncoding;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setEncoding(String encoding)
/*  51:    */   {
/*  52:174 */     this.fEncoding = encoding;
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.dom3.DOMOutputImpl
 * JD-Core Version:    0.7.0.1
 */