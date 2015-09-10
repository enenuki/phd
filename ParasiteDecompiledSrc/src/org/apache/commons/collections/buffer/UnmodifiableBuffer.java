/*   1:    */ package org.apache.commons.collections.buffer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import org.apache.commons.collections.Buffer;
/*  10:    */ import org.apache.commons.collections.Unmodifiable;
/*  11:    */ import org.apache.commons.collections.iterators.UnmodifiableIterator;
/*  12:    */ 
/*  13:    */ public final class UnmodifiableBuffer
/*  14:    */   extends AbstractBufferDecorator
/*  15:    */   implements Unmodifiable, Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1832948656215393357L;
/*  18:    */   
/*  19:    */   public static Buffer decorate(Buffer buffer)
/*  20:    */   {
/*  21: 57 */     if ((buffer instanceof Unmodifiable)) {
/*  22: 58 */       return buffer;
/*  23:    */     }
/*  24: 60 */     return new UnmodifiableBuffer(buffer);
/*  25:    */   }
/*  26:    */   
/*  27:    */   private UnmodifiableBuffer(Buffer buffer)
/*  28:    */   {
/*  29: 71 */     super(buffer);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private void writeObject(ObjectOutputStream out)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 82 */     out.defaultWriteObject();
/*  36: 83 */     out.writeObject(this.collection);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void readObject(ObjectInputStream in)
/*  40:    */     throws IOException, ClassNotFoundException
/*  41:    */   {
/*  42: 94 */     in.defaultReadObject();
/*  43: 95 */     this.collection = ((Collection)in.readObject());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Iterator iterator()
/*  47:    */   {
/*  48:100 */     return UnmodifiableIterator.decorate(getCollection().iterator());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean add(Object object)
/*  52:    */   {
/*  53:104 */     throw new UnsupportedOperationException();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean addAll(Collection coll)
/*  57:    */   {
/*  58:108 */     throw new UnsupportedOperationException();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void clear()
/*  62:    */   {
/*  63:112 */     throw new UnsupportedOperationException();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean remove(Object object)
/*  67:    */   {
/*  68:116 */     throw new UnsupportedOperationException();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean removeAll(Collection coll)
/*  72:    */   {
/*  73:120 */     throw new UnsupportedOperationException();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean retainAll(Collection coll)
/*  77:    */   {
/*  78:124 */     throw new UnsupportedOperationException();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Object remove()
/*  82:    */   {
/*  83:129 */     throw new UnsupportedOperationException();
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.buffer.UnmodifiableBuffer
 * JD-Core Version:    0.7.0.1
 */