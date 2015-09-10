/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.apache.commons.collections.Factory;
/*   9:    */ import org.apache.commons.collections.Transformer;
/*  10:    */ import org.apache.commons.collections.functors.FactoryTransformer;
/*  11:    */ 
/*  12:    */ public class LazyMap
/*  13:    */   extends AbstractMapDecorator
/*  14:    */   implements Map, Serializable
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 7990956402564206740L;
/*  17:    */   protected final Transformer factory;
/*  18:    */   
/*  19:    */   public static Map decorate(Map map, Factory factory)
/*  20:    */   {
/*  21: 83 */     return new LazyMap(map, factory);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static Map decorate(Map map, Transformer factory)
/*  25:    */   {
/*  26: 94 */     return new LazyMap(map, factory);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected LazyMap(Map map, Factory factory)
/*  30:    */   {
/*  31:106 */     super(map);
/*  32:107 */     if (factory == null) {
/*  33:108 */       throw new IllegalArgumentException("Factory must not be null");
/*  34:    */     }
/*  35:110 */     this.factory = FactoryTransformer.getInstance(factory);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected LazyMap(Map map, Transformer factory)
/*  39:    */   {
/*  40:121 */     super(map);
/*  41:122 */     if (factory == null) {
/*  42:123 */       throw new IllegalArgumentException("Factory must not be null");
/*  43:    */     }
/*  44:125 */     this.factory = factory;
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void writeObject(ObjectOutputStream out)
/*  48:    */     throws IOException
/*  49:    */   {
/*  50:137 */     out.defaultWriteObject();
/*  51:138 */     out.writeObject(this.map);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void readObject(ObjectInputStream in)
/*  55:    */     throws IOException, ClassNotFoundException
/*  56:    */   {
/*  57:150 */     in.defaultReadObject();
/*  58:151 */     this.map = ((Map)in.readObject());
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object get(Object key)
/*  62:    */   {
/*  63:157 */     if (!this.map.containsKey(key))
/*  64:    */     {
/*  65:158 */       Object value = this.factory.transform(key);
/*  66:159 */       this.map.put(key, value);
/*  67:160 */       return value;
/*  68:    */     }
/*  69:162 */     return this.map.get(key);
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.LazyMap
 * JD-Core Version:    0.7.0.1
 */