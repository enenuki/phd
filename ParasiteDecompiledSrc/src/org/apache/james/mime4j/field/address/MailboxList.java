/*  1:   */ package org.apache.james.mime4j.field.address;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.AbstractList;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Collections;
/*  8:   */ import java.util.List;
/*  9:   */ 
/* 10:   */ public class MailboxList
/* 11:   */   extends AbstractList<Mailbox>
/* 12:   */   implements Serializable
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 1L;
/* 15:   */   private final List<Mailbox> mailboxes;
/* 16:   */   
/* 17:   */   public MailboxList(List<Mailbox> mailboxes, boolean dontCopy)
/* 18:   */   {
/* 19:45 */     if (mailboxes != null) {
/* 20:46 */       this.mailboxes = (dontCopy ? mailboxes : new ArrayList(mailboxes));
/* 21:   */     } else {
/* 22:49 */       this.mailboxes = Collections.emptyList();
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int size()
/* 27:   */   {
/* 28:57 */     return this.mailboxes.size();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Mailbox get(int index)
/* 32:   */   {
/* 33:65 */     return (Mailbox)this.mailboxes.get(index);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void print()
/* 37:   */   {
/* 38:73 */     for (int i = 0; i < size(); i++)
/* 39:   */     {
/* 40:74 */       Mailbox mailbox = get(i);
/* 41:75 */       System.out.println(mailbox.toString());
/* 42:   */     }
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.MailboxList
 * JD-Core Version:    0.7.0.1
 */