/*   1:    */ package org.apache.commons.collections.bag;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.SortedMap;
/*  10:    */ import java.util.TreeMap;
/*  11:    */ import org.apache.commons.collections.SortedBag;
/*  12:    */ 
/*  13:    */ public class TreeBag
/*  14:    */   extends AbstractMapBag
/*  15:    */   implements SortedBag, Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -7740146511091606676L;
/*  18:    */   
/*  19:    */   public TreeBag()
/*  20:    */   {
/*  21: 59 */     super(new TreeMap());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public TreeBag(Comparator comparator)
/*  25:    */   {
/*  26: 69 */     super(new TreeMap(comparator));
/*  27:    */   }
/*  28:    */   
/*  29:    */   public TreeBag(Collection coll)
/*  30:    */   {
/*  31: 79 */     this();
/*  32: 80 */     addAll(coll);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object first()
/*  36:    */   {
/*  37: 85 */     return ((SortedMap)getMap()).firstKey();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object last()
/*  41:    */   {
/*  42: 89 */     return ((SortedMap)getMap()).lastKey();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Comparator comparator()
/*  46:    */   {
/*  47: 93 */     return ((SortedMap)getMap()).comparator();
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void writeObject(ObjectOutputStream out)
/*  51:    */     throws IOException
/*  52:    */   {
/*  53:101 */     out.defaultWriteObject();
/*  54:102 */     out.writeObject(comparator());
/*  55:103 */     super.doWriteObject(out);
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void readObject(ObjectInputStream in)
/*  59:    */     throws IOException, ClassNotFoundException
/*  60:    */   {
/*  61:110 */     in.defaultReadObject();
/*  62:111 */     Comparator comp = (Comparator)in.readObject();
/*  63:112 */     super.doReadObject(new TreeMap(comp), in);
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.TreeBag
 * JD-Core Version:    0.7.0.1
 */