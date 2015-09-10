/*   1:    */ package org.apache.james.mime4j.field.address;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.InputStreamReader;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.io.StringReader;
/*   8:    */ import java.util.AbstractList;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.List;
/*  12:    */ import org.apache.james.mime4j.field.address.parser.AddressListParser;
/*  13:    */ import org.apache.james.mime4j.field.address.parser.ParseException;
/*  14:    */ 
/*  15:    */ public class AddressList
/*  16:    */   extends AbstractList<Address>
/*  17:    */   implements Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 1L;
/*  20:    */   private final List<? extends Address> addresses;
/*  21:    */   
/*  22:    */   public AddressList(List<? extends Address> addresses, boolean dontCopy)
/*  23:    */   {
/*  24: 49 */     if (addresses != null) {
/*  25: 50 */       this.addresses = (dontCopy ? addresses : new ArrayList(addresses));
/*  26:    */     } else {
/*  27: 53 */       this.addresses = Collections.emptyList();
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int size()
/*  32:    */   {
/*  33: 61 */     return this.addresses.size();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Address get(int index)
/*  37:    */   {
/*  38: 69 */     return (Address)this.addresses.get(index);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public MailboxList flatten()
/*  42:    */   {
/*  43: 78 */     boolean groupDetected = false;
/*  44: 79 */     for (Address addr : this.addresses) {
/*  45: 80 */       if (!(addr instanceof Mailbox))
/*  46:    */       {
/*  47: 81 */         groupDetected = true;
/*  48: 82 */         break;
/*  49:    */       }
/*  50:    */     }
/*  51: 86 */     if (!groupDetected)
/*  52:    */     {
/*  53: 88 */       List<Mailbox> mailboxes = this.addresses;
/*  54: 89 */       return new MailboxList(mailboxes, true);
/*  55:    */     }
/*  56: 92 */     List<Mailbox> results = new ArrayList();
/*  57: 93 */     for (Address addr : this.addresses) {
/*  58: 94 */       addr.addMailboxesTo(results);
/*  59:    */     }
/*  60: 99 */     return new MailboxList(results, false);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void print()
/*  64:    */   {
/*  65:107 */     for (Address addr : this.addresses) {
/*  66:108 */       System.out.println(addr.toString());
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static AddressList parse(String rawAddressList)
/*  71:    */     throws ParseException
/*  72:    */   {
/*  73:120 */     AddressListParser parser = new AddressListParser(new StringReader(rawAddressList));
/*  74:    */     
/*  75:122 */     return Builder.getInstance().buildAddressList(parser.parseAddressList());
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void main(String[] args)
/*  79:    */     throws Exception
/*  80:    */   {
/*  81:129 */     BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
/*  82:    */     try
/*  83:    */     {
/*  84:    */       for (;;)
/*  85:    */       {
/*  86:133 */         System.out.print("> ");
/*  87:134 */         String line = reader.readLine();
/*  88:135 */         if ((line.length() == 0) || (line.toLowerCase().equals("exit")) || (line.toLowerCase().equals("quit")))
/*  89:    */         {
/*  90:137 */           System.out.println("Goodbye.");
/*  91:138 */           return;
/*  92:    */         }
/*  93:140 */         AddressList list = parse(line);
/*  94:141 */         list.print();
/*  95:    */       }
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:143 */       e.printStackTrace();
/* 100:144 */       Thread.sleep(300L);
/* 101:    */     }
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.AddressList
 * JD-Core Version:    0.7.0.1
 */