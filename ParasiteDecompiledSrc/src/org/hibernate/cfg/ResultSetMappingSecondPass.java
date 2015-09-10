/*  1:   */ package org.hibernate.cfg;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.dom4j.Element;
/*  5:   */ import org.hibernate.MappingException;
/*  6:   */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  7:   */ 
/*  8:   */ public class ResultSetMappingSecondPass
/*  9:   */   extends ResultSetMappingBinder
/* 10:   */   implements QuerySecondPass
/* 11:   */ {
/* 12:   */   private Element element;
/* 13:   */   private String path;
/* 14:   */   private Mappings mappings;
/* 15:   */   
/* 16:   */   public ResultSetMappingSecondPass(Element element, String path, Mappings mappings)
/* 17:   */   {
/* 18:41 */     this.element = element;
/* 19:42 */     this.path = path;
/* 20:43 */     this.mappings = mappings;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void doSecondPass(Map persistentClasses)
/* 24:   */     throws MappingException
/* 25:   */   {
/* 26:47 */     ResultSetMappingDefinition definition = buildResultSetMappingDefinition(this.element, this.path, this.mappings);
/* 27:48 */     this.mappings.addResultSetMapping(definition);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.ResultSetMappingSecondPass
 * JD-Core Version:    0.7.0.1
 */