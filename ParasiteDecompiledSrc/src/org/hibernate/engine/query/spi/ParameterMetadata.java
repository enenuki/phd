/*   1:    */ package org.hibernate.engine.query.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.QueryParameterException;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public class ParameterMetadata
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14: 41 */   private static final OrdinalParameterDescriptor[] EMPTY_ORDINALS = new OrdinalParameterDescriptor[0];
/*  15:    */   private final OrdinalParameterDescriptor[] ordinalDescriptors;
/*  16:    */   private final Map namedDescriptorMap;
/*  17:    */   
/*  18:    */   public ParameterMetadata(OrdinalParameterDescriptor[] ordinalDescriptors, Map namedDescriptorMap)
/*  19:    */   {
/*  20: 53 */     if (ordinalDescriptors == null)
/*  21:    */     {
/*  22: 54 */       this.ordinalDescriptors = EMPTY_ORDINALS;
/*  23:    */     }
/*  24:    */     else
/*  25:    */     {
/*  26: 57 */       OrdinalParameterDescriptor[] copy = new OrdinalParameterDescriptor[ordinalDescriptors.length];
/*  27: 58 */       System.arraycopy(ordinalDescriptors, 0, copy, 0, ordinalDescriptors.length);
/*  28: 59 */       this.ordinalDescriptors = copy;
/*  29:    */     }
/*  30: 61 */     if (namedDescriptorMap == null)
/*  31:    */     {
/*  32: 62 */       this.namedDescriptorMap = Collections.EMPTY_MAP;
/*  33:    */     }
/*  34:    */     else
/*  35:    */     {
/*  36: 65 */       int size = (int)(namedDescriptorMap.size() / 0.75D + 1.0D);
/*  37: 66 */       Map copy = new HashMap(size);
/*  38: 67 */       copy.putAll(namedDescriptorMap);
/*  39: 68 */       this.namedDescriptorMap = Collections.unmodifiableMap(copy);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getOrdinalParameterCount()
/*  44:    */   {
/*  45: 73 */     return this.ordinalDescriptors.length;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public OrdinalParameterDescriptor getOrdinalParameterDescriptor(int position)
/*  49:    */   {
/*  50: 77 */     if ((position < 1) || (position > this.ordinalDescriptors.length))
/*  51:    */     {
/*  52: 78 */       String error = "Position beyond number of declared ordinal parameters. Remember that ordinal parameters are 1-based! Position: " + position;
/*  53:    */       
/*  54: 80 */       throw new QueryParameterException(error);
/*  55:    */     }
/*  56: 82 */     return this.ordinalDescriptors[(position - 1)];
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Type getOrdinalParameterExpectedType(int position)
/*  60:    */   {
/*  61: 86 */     return getOrdinalParameterDescriptor(position).getExpectedType();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getOrdinalParameterSourceLocation(int position)
/*  65:    */   {
/*  66: 90 */     return getOrdinalParameterDescriptor(position).getSourceLocation();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Set getNamedParameterNames()
/*  70:    */   {
/*  71: 94 */     return this.namedDescriptorMap.keySet();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public NamedParameterDescriptor getNamedParameterDescriptor(String name)
/*  75:    */   {
/*  76: 98 */     NamedParameterDescriptor meta = (NamedParameterDescriptor)this.namedDescriptorMap.get(name);
/*  77: 99 */     if (meta == null) {
/*  78:100 */       throw new QueryParameterException("could not locate named parameter [" + name + "]");
/*  79:    */     }
/*  80:102 */     return meta;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Type getNamedParameterExpectedType(String name)
/*  84:    */   {
/*  85:106 */     return getNamedParameterDescriptor(name).getExpectedType();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int[] getNamedParameterSourceLocations(String name)
/*  89:    */   {
/*  90:110 */     return getNamedParameterDescriptor(name).getSourceLocations();
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.ParameterMetadata
 * JD-Core Version:    0.7.0.1
 */