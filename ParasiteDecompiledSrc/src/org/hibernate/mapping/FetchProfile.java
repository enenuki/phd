/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.LinkedHashSet;
/*   4:    */ 
/*   5:    */ public class FetchProfile
/*   6:    */ {
/*   7:    */   private final String name;
/*   8:    */   private final MetadataSource source;
/*   9: 40 */   private LinkedHashSet<Fetch> fetches = new LinkedHashSet();
/*  10:    */   
/*  11:    */   public FetchProfile(String name, MetadataSource source)
/*  12:    */   {
/*  13: 49 */     this.name = name;
/*  14: 50 */     this.source = source;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String getName()
/*  18:    */   {
/*  19: 59 */     return this.name;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public MetadataSource getSource()
/*  23:    */   {
/*  24: 68 */     return this.source;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public LinkedHashSet<Fetch> getFetches()
/*  28:    */   {
/*  29: 77 */     return this.fetches;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void addFetch(String entity, String association, String style)
/*  33:    */   {
/*  34: 88 */     this.fetches.add(new Fetch(entity, association, style));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean equals(Object o)
/*  38:    */   {
/*  39: 95 */     if (this == o) {
/*  40: 96 */       return true;
/*  41:    */     }
/*  42: 98 */     if ((o == null) || (getClass() != o.getClass())) {
/*  43: 99 */       return false;
/*  44:    */     }
/*  45:102 */     FetchProfile that = (FetchProfile)o;
/*  46:    */     
/*  47:104 */     return this.name.equals(that.name);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int hashCode()
/*  51:    */   {
/*  52:111 */     return this.name.hashCode();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static class Fetch
/*  56:    */   {
/*  57:    */     private final String entity;
/*  58:    */     private final String association;
/*  59:    */     private final String style;
/*  60:    */     
/*  61:    */     public Fetch(String entity, String association, String style)
/*  62:    */     {
/*  63:124 */       this.entity = entity;
/*  64:125 */       this.association = association;
/*  65:126 */       this.style = style;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public String getEntity()
/*  69:    */     {
/*  70:130 */       return this.entity;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public String getAssociation()
/*  74:    */     {
/*  75:134 */       return this.association;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public String getStyle()
/*  79:    */     {
/*  80:138 */       return this.style;
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.FetchProfile
 * JD-Core Version:    0.7.0.1
 */