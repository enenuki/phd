/*  1:   */ package org.apache.commons.collections.collection;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectInputStream;
/*  5:   */ import java.io.ObjectOutputStream;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import java.util.Collection;
/*  8:   */ 
/*  9:   */ public abstract class AbstractSerializableCollectionDecorator
/* 10:   */   extends AbstractCollectionDecorator
/* 11:   */   implements Serializable
/* 12:   */ {
/* 13:   */   private static final long serialVersionUID = 6249888059822088500L;
/* 14:   */   
/* 15:   */   protected AbstractSerializableCollectionDecorator(Collection coll)
/* 16:   */   {
/* 17:42 */     super(coll);
/* 18:   */   }
/* 19:   */   
/* 20:   */   private void writeObject(ObjectOutputStream out)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:53 */     out.defaultWriteObject();
/* 24:54 */     out.writeObject(this.collection);
/* 25:   */   }
/* 26:   */   
/* 27:   */   private void readObject(ObjectInputStream in)
/* 28:   */     throws IOException, ClassNotFoundException
/* 29:   */   {
/* 30:65 */     in.defaultReadObject();
/* 31:66 */     this.collection = ((Collection)in.readObject());
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.AbstractSerializableCollectionDecorator
 * JD-Core Version:    0.7.0.1
 */