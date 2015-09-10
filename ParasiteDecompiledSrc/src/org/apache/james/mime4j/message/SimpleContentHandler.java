/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import org.apache.james.mime4j.MimeException;
/*   6:    */ import org.apache.james.mime4j.codec.Base64InputStream;
/*   7:    */ import org.apache.james.mime4j.codec.QuotedPrintableInputStream;
/*   8:    */ import org.apache.james.mime4j.descriptor.BodyDescriptor;
/*   9:    */ import org.apache.james.mime4j.field.AbstractField;
/*  10:    */ import org.apache.james.mime4j.parser.AbstractContentHandler;
/*  11:    */ import org.apache.james.mime4j.parser.Field;
/*  12:    */ import org.apache.james.mime4j.util.MimeUtil;
/*  13:    */ 
/*  14:    */ public abstract class SimpleContentHandler
/*  15:    */   extends AbstractContentHandler
/*  16:    */ {
/*  17:    */   private Header currHeader;
/*  18:    */   
/*  19:    */   public abstract void headers(Header paramHeader);
/*  20:    */   
/*  21:    */   public abstract void bodyDecoded(BodyDescriptor paramBodyDescriptor, InputStream paramInputStream)
/*  22:    */     throws IOException;
/*  23:    */   
/*  24:    */   public final void startHeader()
/*  25:    */   {
/*  26: 72 */     this.currHeader = new Header();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public final void field(Field field)
/*  30:    */     throws MimeException
/*  31:    */   {
/*  32: 80 */     Field parsedField = AbstractField.parse(field.getRaw());
/*  33: 81 */     this.currHeader.addField(parsedField);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final void endHeader()
/*  37:    */   {
/*  38: 89 */     Header tmp = this.currHeader;
/*  39: 90 */     this.currHeader = null;
/*  40: 91 */     headers(tmp);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final void body(BodyDescriptor bd, InputStream is)
/*  44:    */     throws IOException
/*  45:    */   {
/*  46: 99 */     if (MimeUtil.isBase64Encoding(bd.getTransferEncoding())) {
/*  47:100 */       bodyDecoded(bd, new Base64InputStream(is));
/*  48:102 */     } else if (MimeUtil.isQuotedPrintableEncoded(bd.getTransferEncoding())) {
/*  49:103 */       bodyDecoded(bd, new QuotedPrintableInputStream(is));
/*  50:    */     } else {
/*  51:106 */       bodyDecoded(bd, is);
/*  52:    */     }
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.SimpleContentHandler
 * JD-Core Version:    0.7.0.1
 */