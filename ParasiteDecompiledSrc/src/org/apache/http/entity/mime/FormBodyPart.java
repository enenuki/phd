/*   1:    */ package org.apache.http.entity.mime;
/*   2:    */ 
/*   3:    */ import org.apache.http.entity.mime.content.ContentBody;
/*   4:    */ 
/*   5:    */ public class FormBodyPart
/*   6:    */ {
/*   7:    */   private final String name;
/*   8:    */   private final Header header;
/*   9:    */   private final ContentBody body;
/*  10:    */   
/*  11:    */   public FormBodyPart(String name, ContentBody body)
/*  12:    */   {
/*  13: 48 */     if (name == null) {
/*  14: 49 */       throw new IllegalArgumentException("Name may not be null");
/*  15:    */     }
/*  16: 51 */     if (body == null) {
/*  17: 52 */       throw new IllegalArgumentException("Body may not be null");
/*  18:    */     }
/*  19: 54 */     this.name = name;
/*  20: 55 */     this.body = body;
/*  21: 56 */     this.header = new Header();
/*  22:    */     
/*  23: 58 */     generateContentDisp(body);
/*  24: 59 */     generateContentType(body);
/*  25: 60 */     generateTransferEncoding(body);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName()
/*  29:    */   {
/*  30: 64 */     return this.name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ContentBody getBody()
/*  34:    */   {
/*  35: 68 */     return this.body;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Header getHeader()
/*  39:    */   {
/*  40: 72 */     return this.header;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void addField(String name, String value)
/*  44:    */   {
/*  45: 76 */     if (name == null) {
/*  46: 77 */       throw new IllegalArgumentException("Field name may not be null");
/*  47:    */     }
/*  48: 79 */     this.header.addField(new MinimalField(name, value));
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void generateContentDisp(ContentBody body)
/*  52:    */   {
/*  53: 83 */     StringBuilder buffer = new StringBuilder();
/*  54: 84 */     buffer.append("form-data; name=\"");
/*  55: 85 */     buffer.append(getName());
/*  56: 86 */     buffer.append("\"");
/*  57: 87 */     if (body.getFilename() != null)
/*  58:    */     {
/*  59: 88 */       buffer.append("; filename=\"");
/*  60: 89 */       buffer.append(body.getFilename());
/*  61: 90 */       buffer.append("\"");
/*  62:    */     }
/*  63: 92 */     addField("Content-Disposition", buffer.toString());
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void generateContentType(ContentBody body)
/*  67:    */   {
/*  68: 96 */     StringBuilder buffer = new StringBuilder();
/*  69: 97 */     buffer.append(body.getMimeType());
/*  70: 98 */     if (body.getCharset() != null)
/*  71:    */     {
/*  72: 99 */       buffer.append("; charset=");
/*  73:100 */       buffer.append(body.getCharset());
/*  74:    */     }
/*  75:102 */     addField("Content-Type", buffer.toString());
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void generateTransferEncoding(ContentBody body)
/*  79:    */   {
/*  80:106 */     addField("Content-Transfer-Encoding", body.getTransferEncoding());
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.FormBodyPart
 * JD-Core Version:    0.7.0.1
 */