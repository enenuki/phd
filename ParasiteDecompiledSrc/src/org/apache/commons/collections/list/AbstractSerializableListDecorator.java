/*  1:   */ package org.apache.commons.collections.list;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectInputStream;
/*  5:   */ import java.io.ObjectOutputStream;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.List;
/*  9:   */ 
/* 10:   */ public abstract class AbstractSerializableListDecorator
/* 11:   */   extends AbstractListDecorator
/* 12:   */   implements Serializable
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 2684959196747496299L;
/* 15:   */   
/* 16:   */   protected AbstractSerializableListDecorator(List list)
/* 17:   */   {
/* 18:43 */     super(list);
/* 19:   */   }
/* 20:   */   
/* 21:   */   private void writeObject(ObjectOutputStream out)
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:54 */     out.defaultWriteObject();
/* 25:55 */     out.writeObject(this.collection);
/* 26:   */   }
/* 27:   */   
/* 28:   */   private void readObject(ObjectInputStream in)
/* 29:   */     throws IOException, ClassNotFoundException
/* 30:   */   {
/* 31:66 */     in.defaultReadObject();
/* 32:67 */     this.collection = ((Collection)in.readObject());
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.list.AbstractSerializableListDecorator
 * JD-Core Version:    0.7.0.1
 */