/*   1:    */ package org.apache.james.mime4j.field.address;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.io.StringReader;
/*   5:    */ import java.util.List;
/*   6:    */ import org.apache.james.mime4j.field.address.parser.AddressListParser;
/*   7:    */ import org.apache.james.mime4j.field.address.parser.ParseException;
/*   8:    */ 
/*   9:    */ public abstract class Address
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 634090661990433426L;
/*  13:    */   
/*  14:    */   final void addMailboxesTo(List<Mailbox> results)
/*  15:    */   {
/*  16: 43 */     doAddMailboxesTo(results);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected abstract void doAddMailboxesTo(List<Mailbox> paramList);
/*  20:    */   
/*  21:    */   public final String getDisplayString()
/*  22:    */   {
/*  23: 61 */     return getDisplayString(false);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public abstract String getDisplayString(boolean paramBoolean);
/*  27:    */   
/*  28:    */   public abstract String getEncodedString();
/*  29:    */   
/*  30:    */   public static Address parse(String rawAddressString)
/*  31:    */   {
/*  32:108 */     AddressListParser parser = new AddressListParser(new StringReader(rawAddressString));
/*  33:    */     try
/*  34:    */     {
/*  35:111 */       return Builder.getInstance().buildAddress(parser.parseAddress());
/*  36:    */     }
/*  37:    */     catch (ParseException e)
/*  38:    */     {
/*  39:113 */       throw new IllegalArgumentException(e);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String toString()
/*  44:    */   {
/*  45:119 */     return getDisplayString(false);
/*  46:    */   }
/*  47:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.Address
 * JD-Core Version:    0.7.0.1
 */