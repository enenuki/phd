/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.ObjectInputStream;
/*  5:   */ import java.io.ObjectOutputStream;
/*  6:   */ import java.io.Serializable;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.Set;
/*  9:   */ 
/* 10:   */ public abstract class AbstractSerializableSetDecorator
/* 11:   */   extends AbstractSetDecorator
/* 12:   */   implements Serializable
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 1229469966212206107L;
/* 15:   */   
/* 16:   */   protected AbstractSerializableSetDecorator(Set set)
/* 17:   */   {
/* 18:43 */     super(set);
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
 * Qualified Name:     org.apache.commons.collections.set.AbstractSerializableSetDecorator
 * JD-Core Version:    0.7.0.1
 */