/*   1:    */ package org.hibernate.engine.profile;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.internal.CoreMessageLogger;
/*   6:    */ import org.hibernate.persister.entity.EntityPersister;
/*   7:    */ import org.hibernate.type.BagType;
/*   8:    */ import org.hibernate.type.Type;
/*   9:    */ import org.jboss.logging.Logger;
/*  10:    */ 
/*  11:    */ public class FetchProfile
/*  12:    */ {
/*  13: 45 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FetchProfile.class.getName());
/*  14:    */   private final String name;
/*  15: 48 */   private Map<String, Fetch> fetches = new HashMap();
/*  16: 50 */   private boolean containsJoinFetchedCollection = false;
/*  17: 51 */   private boolean containsJoinFetchedBag = false;
/*  18:    */   private Fetch bagJoinFetch;
/*  19:    */   
/*  20:    */   public FetchProfile(String name)
/*  21:    */   {
/*  22: 63 */     this.name = name;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void addFetch(Association association, String fetchStyleName)
/*  26:    */   {
/*  27: 74 */     addFetch(association, Fetch.Style.parse(fetchStyleName));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void addFetch(Association association, Fetch.Style style)
/*  31:    */   {
/*  32: 84 */     addFetch(new Fetch(association, style));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void addFetch(Fetch fetch)
/*  36:    */   {
/*  37: 93 */     String fetchAssociactionRole = fetch.getAssociation().getRole();
/*  38: 94 */     Type associationType = fetch.getAssociation().getOwner().getPropertyType(fetch.getAssociation().getAssociationPath());
/*  39: 95 */     if (associationType.isCollectionType())
/*  40:    */     {
/*  41: 96 */       LOG.tracev("Handling request to add collection fetch [{0}]", fetchAssociactionRole);
/*  42:100 */       if (Fetch.Style.JOIN == fetch.getStyle())
/*  43:    */       {
/*  44:103 */         if ((BagType.class.isInstance(associationType)) && 
/*  45:104 */           (this.containsJoinFetchedCollection))
/*  46:    */         {
/*  47:105 */           LOG.containsJoinFetchedCollection(fetchAssociactionRole);
/*  48:106 */           return;
/*  49:    */         }
/*  50:113 */         if (this.containsJoinFetchedBag)
/*  51:    */         {
/*  52:115 */           if (this.fetches.remove(this.bagJoinFetch.getAssociation().getRole()) != this.bagJoinFetch) {
/*  53:116 */             LOG.unableToRemoveBagJoinFetch();
/*  54:    */           }
/*  55:118 */           this.bagJoinFetch = null;
/*  56:119 */           this.containsJoinFetchedBag = false;
/*  57:    */         }
/*  58:122 */         this.containsJoinFetchedCollection = true;
/*  59:    */       }
/*  60:    */     }
/*  61:125 */     this.fetches.put(fetchAssociactionRole, fetch);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getName()
/*  65:    */   {
/*  66:134 */     return this.name;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Map<String, Fetch> getFetches()
/*  70:    */   {
/*  71:144 */     return this.fetches;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Fetch getFetchByRole(String role)
/*  75:    */   {
/*  76:148 */     return (Fetch)this.fetches.get(role);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isContainsJoinFetchedCollection()
/*  80:    */   {
/*  81:159 */     return this.containsJoinFetchedCollection;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean isContainsJoinFetchedBag()
/*  85:    */   {
/*  86:170 */     return this.containsJoinFetchedBag;
/*  87:    */   }
/*  88:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.profile.FetchProfile
 * JD-Core Version:    0.7.0.1
 */