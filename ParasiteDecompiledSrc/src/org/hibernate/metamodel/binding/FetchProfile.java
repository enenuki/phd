/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.Set;
/*   5:    */ 
/*   6:    */ public class FetchProfile
/*   7:    */ {
/*   8:    */   private final String name;
/*   9:    */   private final Set<Fetch> fetches;
/*  10:    */   
/*  11:    */   public FetchProfile(String name, Set<Fetch> fetches)
/*  12:    */   {
/*  13: 51 */     this.name = name;
/*  14: 52 */     this.fetches = fetches;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String getName()
/*  18:    */   {
/*  19: 61 */     return this.name;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Set<Fetch> getFetches()
/*  23:    */   {
/*  24: 70 */     return Collections.unmodifiableSet(this.fetches);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addFetch(String entity, String association, String style)
/*  28:    */   {
/*  29: 83 */     this.fetches.add(new Fetch(entity, association, style));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static class Fetch
/*  33:    */   {
/*  34:    */     private final String entity;
/*  35:    */     private final String association;
/*  36:    */     private final String style;
/*  37:    */     
/*  38:    */     public Fetch(String entity, String association, String style)
/*  39:    */     {
/*  40: 97 */       this.entity = entity;
/*  41: 98 */       this.association = association;
/*  42: 99 */       this.style = style;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public String getEntity()
/*  46:    */     {
/*  47:103 */       return this.entity;
/*  48:    */     }
/*  49:    */     
/*  50:    */     public String getAssociation()
/*  51:    */     {
/*  52:107 */       return this.association;
/*  53:    */     }
/*  54:    */     
/*  55:    */     public String getStyle()
/*  56:    */     {
/*  57:111 */       return this.style;
/*  58:    */     }
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.FetchProfile
 * JD-Core Version:    0.7.0.1
 */