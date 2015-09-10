/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.hibernate.internal.util.xml.XmlDocument;
/*  5:   */ 
/*  6:   */ public class ExtendsQueueEntry
/*  7:   */ {
/*  8:   */   private final String explicitName;
/*  9:   */   private final String mappingPackage;
/* 10:   */   private final XmlDocument metadataXml;
/* 11:   */   private final Set<String> entityNames;
/* 12:   */   
/* 13:   */   public ExtendsQueueEntry(String explicitName, String mappingPackage, XmlDocument metadataXml, Set<String> entityNames)
/* 14:   */   {
/* 15:43 */     this.explicitName = explicitName;
/* 16:44 */     this.mappingPackage = mappingPackage;
/* 17:45 */     this.metadataXml = metadataXml;
/* 18:46 */     this.entityNames = entityNames;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getExplicitName()
/* 22:   */   {
/* 23:50 */     return this.explicitName;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getMappingPackage()
/* 27:   */   {
/* 28:54 */     return this.mappingPackage;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public XmlDocument getMetadataXml()
/* 32:   */   {
/* 33:58 */     return this.metadataXml;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Set<String> getEntityNames()
/* 37:   */   {
/* 38:62 */     return this.entityNames;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ExtendsQueueEntry
 * JD-Core Version:    0.7.0.1
 */