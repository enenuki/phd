/*   1:    */ package org.apache.james.mime4j.field.address;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.List;
/*   7:    */ import org.apache.james.mime4j.codec.EncoderUtil;
/*   8:    */ 
/*   9:    */ public class Group
/*  10:    */   extends Address
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 1L;
/*  13:    */   private final String name;
/*  14:    */   private final MailboxList mailboxList;
/*  15:    */   
/*  16:    */   public Group(String name, Mailbox... mailboxes)
/*  17:    */   {
/*  18: 46 */     this(name, new MailboxList(Arrays.asList(mailboxes), true));
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Group(String name, Collection<Mailbox> mailboxes)
/*  22:    */   {
/*  23: 56 */     this(name, new MailboxList(new ArrayList(mailboxes), true));
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Group(String name, MailboxList mailboxes)
/*  27:    */   {
/*  28: 66 */     if (name == null) {
/*  29: 67 */       throw new IllegalArgumentException();
/*  30:    */     }
/*  31: 68 */     if (mailboxes == null) {
/*  32: 69 */       throw new IllegalArgumentException();
/*  33:    */     }
/*  34: 71 */     this.name = name;
/*  35: 72 */     this.mailboxList = mailboxes;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static Group parse(String rawGroupString)
/*  39:    */   {
/*  40: 85 */     Address address = Address.parse(rawGroupString);
/*  41: 86 */     if (!(address instanceof Group)) {
/*  42: 87 */       throw new IllegalArgumentException("Not a group address");
/*  43:    */     }
/*  44: 89 */     return (Group)address;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getName()
/*  48:    */   {
/*  49: 96 */     return this.name;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public MailboxList getMailboxes()
/*  53:    */   {
/*  54:103 */     return this.mailboxList;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getDisplayString(boolean includeRoute)
/*  58:    */   {
/*  59:108 */     StringBuilder sb = new StringBuilder();
/*  60:    */     
/*  61:110 */     sb.append(this.name);
/*  62:111 */     sb.append(':');
/*  63:    */     
/*  64:113 */     boolean first = true;
/*  65:114 */     for (Mailbox mailbox : this.mailboxList)
/*  66:    */     {
/*  67:115 */       if (first) {
/*  68:116 */         first = false;
/*  69:    */       } else {
/*  70:118 */         sb.append(',');
/*  71:    */       }
/*  72:121 */       sb.append(' ');
/*  73:122 */       sb.append(mailbox.getDisplayString(includeRoute));
/*  74:    */     }
/*  75:125 */     sb.append(";");
/*  76:    */     
/*  77:127 */     return sb.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getEncodedString()
/*  81:    */   {
/*  82:132 */     StringBuilder sb = new StringBuilder();
/*  83:    */     
/*  84:134 */     sb.append(EncoderUtil.encodeAddressDisplayName(this.name));
/*  85:135 */     sb.append(':');
/*  86:    */     
/*  87:137 */     boolean first = true;
/*  88:138 */     for (Mailbox mailbox : this.mailboxList)
/*  89:    */     {
/*  90:139 */       if (first) {
/*  91:140 */         first = false;
/*  92:    */       } else {
/*  93:142 */         sb.append(',');
/*  94:    */       }
/*  95:145 */       sb.append(' ');
/*  96:146 */       sb.append(mailbox.getEncodedString());
/*  97:    */     }
/*  98:149 */     sb.append(';');
/*  99:    */     
/* 100:151 */     return sb.toString();
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void doAddMailboxesTo(List<Mailbox> results)
/* 104:    */   {
/* 105:156 */     for (Mailbox mailbox : this.mailboxList) {
/* 106:157 */       results.add(mailbox);
/* 107:    */     }
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.Group
 * JD-Core Version:    0.7.0.1
 */