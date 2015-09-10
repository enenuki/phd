/*   1:    */ package org.hibernate.engine.query.spi;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   8:    */ 
/*   9:    */ public class ParamLocationRecognizer
/*  10:    */   implements ParameterParser.Recognizer
/*  11:    */ {
/*  12:    */   public static class NamedParameterDescription
/*  13:    */   {
/*  14:    */     private final boolean jpaStyle;
/*  15: 44 */     private final List positions = new ArrayList();
/*  16:    */     
/*  17:    */     public NamedParameterDescription(boolean jpaStyle)
/*  18:    */     {
/*  19: 47 */       this.jpaStyle = jpaStyle;
/*  20:    */     }
/*  21:    */     
/*  22:    */     public boolean isJpaStyle()
/*  23:    */     {
/*  24: 51 */       return this.jpaStyle;
/*  25:    */     }
/*  26:    */     
/*  27:    */     private void add(int position)
/*  28:    */     {
/*  29: 55 */       this.positions.add(Integer.valueOf(position));
/*  30:    */     }
/*  31:    */     
/*  32:    */     public int[] buildPositionsArray()
/*  33:    */     {
/*  34: 59 */       return ArrayHelper.toIntArray(this.positions);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38: 63 */   private Map namedParameterDescriptions = new HashMap();
/*  39: 64 */   private List ordinalParameterLocationList = new ArrayList();
/*  40:    */   
/*  41:    */   public static ParamLocationRecognizer parseLocations(String query)
/*  42:    */   {
/*  43: 74 */     ParamLocationRecognizer recognizer = new ParamLocationRecognizer();
/*  44: 75 */     ParameterParser.parse(query, recognizer);
/*  45: 76 */     return recognizer;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Map getNamedParameterDescriptionMap()
/*  49:    */   {
/*  50: 86 */     return this.namedParameterDescriptions;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List getOrdinalParameterLocationList()
/*  54:    */   {
/*  55: 98 */     return this.ordinalParameterLocationList;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void ordinalParameter(int position)
/*  59:    */   {
/*  60:105 */     this.ordinalParameterLocationList.add(Integer.valueOf(position));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void namedParameter(String name, int position)
/*  64:    */   {
/*  65:109 */     getOrBuildNamedParameterDescription(name, false).add(position);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void jpaPositionalParameter(String name, int position)
/*  69:    */   {
/*  70:113 */     getOrBuildNamedParameterDescription(name, true).add(position);
/*  71:    */   }
/*  72:    */   
/*  73:    */   private NamedParameterDescription getOrBuildNamedParameterDescription(String name, boolean jpa)
/*  74:    */   {
/*  75:117 */     NamedParameterDescription desc = (NamedParameterDescription)this.namedParameterDescriptions.get(name);
/*  76:118 */     if (desc == null)
/*  77:    */     {
/*  78:119 */       desc = new NamedParameterDescription(jpa);
/*  79:120 */       this.namedParameterDescriptions.put(name, desc);
/*  80:    */     }
/*  81:122 */     return desc;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void other(char character) {}
/*  85:    */   
/*  86:    */   public void outParameter(int position) {}
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.ParamLocationRecognizer
 * JD-Core Version:    0.7.0.1
 */