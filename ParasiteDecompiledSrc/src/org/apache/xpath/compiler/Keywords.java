/*   1:    */ package org.apache.xpath.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ 
/*   5:    */ public class Keywords
/*   6:    */ {
/*   7: 33 */   private static Hashtable m_keywords = new Hashtable();
/*   8: 36 */   private static Hashtable m_axisnames = new Hashtable();
/*   9: 39 */   private static Hashtable m_nodetests = new Hashtable();
/*  10: 42 */   private static Hashtable m_nodetypes = new Hashtable();
/*  11:    */   private static final String FROM_ANCESTORS_STRING = "ancestor";
/*  12:    */   private static final String FROM_ANCESTORS_OR_SELF_STRING = "ancestor-or-self";
/*  13:    */   private static final String FROM_ATTRIBUTES_STRING = "attribute";
/*  14:    */   private static final String FROM_CHILDREN_STRING = "child";
/*  15:    */   private static final String FROM_DESCENDANTS_STRING = "descendant";
/*  16:    */   private static final String FROM_DESCENDANTS_OR_SELF_STRING = "descendant-or-self";
/*  17:    */   private static final String FROM_FOLLOWING_STRING = "following";
/*  18:    */   private static final String FROM_FOLLOWING_SIBLINGS_STRING = "following-sibling";
/*  19:    */   private static final String FROM_PARENT_STRING = "parent";
/*  20:    */   private static final String FROM_PRECEDING_STRING = "preceding";
/*  21:    */   private static final String FROM_PRECEDING_SIBLINGS_STRING = "preceding-sibling";
/*  22:    */   private static final String FROM_SELF_STRING = "self";
/*  23:    */   private static final String FROM_NAMESPACE_STRING = "namespace";
/*  24:    */   private static final String FROM_SELF_ABBREVIATED_STRING = ".";
/*  25:    */   private static final String NODETYPE_COMMENT_STRING = "comment";
/*  26:    */   private static final String NODETYPE_TEXT_STRING = "text";
/*  27:    */   private static final String NODETYPE_PI_STRING = "processing-instruction";
/*  28:    */   private static final String NODETYPE_NODE_STRING = "node";
/*  29:    */   private static final String NODETYPE_ANYELEMENT_STRING = "*";
/*  30:    */   public static final String FUNC_CURRENT_STRING = "current";
/*  31:    */   public static final String FUNC_LAST_STRING = "last";
/*  32:    */   public static final String FUNC_POSITION_STRING = "position";
/*  33:    */   public static final String FUNC_COUNT_STRING = "count";
/*  34:    */   static final String FUNC_ID_STRING = "id";
/*  35:    */   public static final String FUNC_KEY_STRING = "key";
/*  36:    */   public static final String FUNC_LOCAL_PART_STRING = "local-name";
/*  37:    */   public static final String FUNC_NAMESPACE_STRING = "namespace-uri";
/*  38:    */   public static final String FUNC_NAME_STRING = "name";
/*  39:    */   public static final String FUNC_GENERATE_ID_STRING = "generate-id";
/*  40:    */   public static final String FUNC_NOT_STRING = "not";
/*  41:    */   public static final String FUNC_TRUE_STRING = "true";
/*  42:    */   public static final String FUNC_FALSE_STRING = "false";
/*  43:    */   public static final String FUNC_BOOLEAN_STRING = "boolean";
/*  44:    */   public static final String FUNC_LANG_STRING = "lang";
/*  45:    */   public static final String FUNC_NUMBER_STRING = "number";
/*  46:    */   public static final String FUNC_FLOOR_STRING = "floor";
/*  47:    */   public static final String FUNC_CEILING_STRING = "ceiling";
/*  48:    */   public static final String FUNC_ROUND_STRING = "round";
/*  49:    */   public static final String FUNC_SUM_STRING = "sum";
/*  50:    */   public static final String FUNC_STRING_STRING = "string";
/*  51:    */   public static final String FUNC_STARTS_WITH_STRING = "starts-with";
/*  52:    */   public static final String FUNC_CONTAINS_STRING = "contains";
/*  53:    */   public static final String FUNC_SUBSTRING_BEFORE_STRING = "substring-before";
/*  54:    */   public static final String FUNC_SUBSTRING_AFTER_STRING = "substring-after";
/*  55:    */   public static final String FUNC_NORMALIZE_SPACE_STRING = "normalize-space";
/*  56:    */   public static final String FUNC_TRANSLATE_STRING = "translate";
/*  57:    */   public static final String FUNC_CONCAT_STRING = "concat";
/*  58:    */   public static final String FUNC_SYSTEM_PROPERTY_STRING = "system-property";
/*  59:    */   public static final String FUNC_EXT_FUNCTION_AVAILABLE_STRING = "function-available";
/*  60:    */   public static final String FUNC_EXT_ELEM_AVAILABLE_STRING = "element-available";
/*  61:    */   public static final String FUNC_SUBSTRING_STRING = "substring";
/*  62:    */   public static final String FUNC_STRING_LENGTH_STRING = "string-length";
/*  63:    */   public static final String FUNC_UNPARSED_ENTITY_URI_STRING = "unparsed-entity-uri";
/*  64:    */   public static final String FUNC_DOCLOCATION_STRING = "document-location";
/*  65:    */   
/*  66:    */   static
/*  67:    */   {
/*  68:218 */     m_axisnames.put("ancestor", new Integer(37));
/*  69:    */     
/*  70:220 */     m_axisnames.put("ancestor-or-self", new Integer(38));
/*  71:    */     
/*  72:222 */     m_axisnames.put("attribute", new Integer(39));
/*  73:    */     
/*  74:224 */     m_axisnames.put("child", new Integer(40));
/*  75:    */     
/*  76:226 */     m_axisnames.put("descendant", new Integer(41));
/*  77:    */     
/*  78:228 */     m_axisnames.put("descendant-or-self", new Integer(42));
/*  79:    */     
/*  80:230 */     m_axisnames.put("following", new Integer(43));
/*  81:    */     
/*  82:232 */     m_axisnames.put("following-sibling", new Integer(44));
/*  83:    */     
/*  84:234 */     m_axisnames.put("parent", new Integer(45));
/*  85:    */     
/*  86:236 */     m_axisnames.put("preceding", new Integer(46));
/*  87:    */     
/*  88:238 */     m_axisnames.put("preceding-sibling", new Integer(47));
/*  89:    */     
/*  90:240 */     m_axisnames.put("self", new Integer(48));
/*  91:    */     
/*  92:242 */     m_axisnames.put("namespace", new Integer(49));
/*  93:    */     
/*  94:244 */     m_nodetypes.put("comment", new Integer(1030));
/*  95:    */     
/*  96:246 */     m_nodetypes.put("text", new Integer(1031));
/*  97:    */     
/*  98:248 */     m_nodetypes.put("processing-instruction", new Integer(1032));
/*  99:    */     
/* 100:250 */     m_nodetypes.put("node", new Integer(1033));
/* 101:    */     
/* 102:252 */     m_nodetypes.put("*", new Integer(36));
/* 103:    */     
/* 104:254 */     m_keywords.put(".", new Integer(48));
/* 105:    */     
/* 106:256 */     m_keywords.put("id", new Integer(4));
/* 107:    */     
/* 108:258 */     m_keywords.put("key", new Integer(5));
/* 109:    */     
/* 110:    */ 
/* 111:261 */     m_nodetests.put("comment", new Integer(1030));
/* 112:    */     
/* 113:263 */     m_nodetests.put("text", new Integer(1031));
/* 114:    */     
/* 115:265 */     m_nodetests.put("processing-instruction", new Integer(1032));
/* 116:    */     
/* 117:267 */     m_nodetests.put("node", new Integer(1033));
/* 118:    */   }
/* 119:    */   
/* 120:    */   static Object getAxisName(String key)
/* 121:    */   {
/* 122:272 */     return m_axisnames.get(key);
/* 123:    */   }
/* 124:    */   
/* 125:    */   static Object lookupNodeTest(String key)
/* 126:    */   {
/* 127:276 */     return m_nodetests.get(key);
/* 128:    */   }
/* 129:    */   
/* 130:    */   static Object getKeyWord(String key)
/* 131:    */   {
/* 132:280 */     return m_keywords.get(key);
/* 133:    */   }
/* 134:    */   
/* 135:    */   static Object getNodeType(String key)
/* 136:    */   {
/* 137:284 */     return m_nodetypes.get(key);
/* 138:    */   }
/* 139:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.Keywords
 * JD-Core Version:    0.7.0.1
 */