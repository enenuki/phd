/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ 
/*   5:    */ public class SerializerMessages_zh_TW
/*   6:    */   extends ListResourceBundle
/*   7:    */ {
/*   8:    */   public Object[][] getContents()
/*   9:    */   {
/*  10: 73 */     Object[][] contents = { { "BAD_MSGKEY", "訊息鍵 ''{0}'' 不在訊息類別 ''{1}'' 中" }, { "BAD_MSGFORMAT", "訊息類別 ''{1}'' 中的訊息 ''{0}'' 格式化失敗。" }, { "ER_SERIALIZER_NOT_CONTENTHANDLER", "Serializer 類別 ''{0}'' 不實作 org.xml.sax.ContentHandler。" }, { "ER_RESOURCE_COULD_NOT_FIND", "找不到資源 [ {0} ]。\n {1}" }, { "ER_RESOURCE_COULD_NOT_LOAD", "無法載入資源 [ {0} ]：{1} \n {2} \t {3}" }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "緩衝區大小 <=0" }, { "ER_INVALID_UTF16_SURROGATE", "偵測到無效的 UTF-16 代理：{0}?" }, { "ER_OIERROR", "IO 錯誤" }, { "ER_ILLEGAL_ATTRIBUTE_POSITION", "在產生子項節點之後，或在產生元素之前，不可新增屬性 {0}。屬性會被忽略。" }, { "ER_NAMESPACE_PREFIX", "字首 ''{0}'' 的名稱空間尚未宣告。" }, { "ER_STRAY_ATTRIBUTE", "屬性 ''{0}'' 超出元素外。" }, { "ER_STRAY_NAMESPACE", "名稱空間宣告 ''{0}''=''{1}'' 超出元素外。" }, { "ER_COULD_NOT_LOAD_RESOURCE", "無法載入 ''{0}''（檢查 CLASSPATH），目前只使用預設值" }, { "ER_ILLEGAL_CHARACTER", "試圖輸出不是以指定的輸出編碼 {1} 呈現的整數值 {0} 的字元。" }, { "ER_COULD_NOT_LOAD_METHOD_PROPERTY", "無法載入輸出方法 ''{1}''（檢查 CLASSPATH）的內容檔 ''{0}''" }, { "ER_INVALID_PORT", "無效的埠編號" }, { "ER_PORT_WHEN_HOST_NULL", "主機為空值時，無法設定埠" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "主機沒有完整的位址" }, { "ER_SCHEME_NOT_CONFORMANT", "綱要不是 conformant。" }, { "ER_SCHEME_FROM_NULL_STRING", "無法從空字串設定綱要" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "路徑包含無效的跳脫字元" }, { "ER_PATH_INVALID_CHAR", "路徑包含無效的字元：{0}" }, { "ER_FRAG_INVALID_CHAR", "片段包含無效的字元" }, { "ER_FRAG_WHEN_PATH_NULL", "路徑為空值時，無法設定片段" }, { "ER_FRAG_FOR_GENERIC_URI", "只能對通用的 URI 設定片段" }, { "ER_NO_SCHEME_IN_URI", "在 URI 找不到綱要" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "無法以空白參數起始設定 URI" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "片段無法同時在路徑和片段中指定" }, { "ER_NO_QUERY_STRING_IN_PATH", "在路徑及查詢字串中不可指定查詢字串" }, { "ER_NO_PORT_IF_NO_HOST", "如果沒有指定主機，不可指定埠" }, { "ER_NO_USERINFO_IF_NO_HOST", "如果沒有指定主機，不可指定 Userinfo" }, { "ER_XML_VERSION_NOT_SUPPORTED", "警告：輸出文件的版本要求是 ''{0}''。未支援這個版本的 XML。輸出文件的版本會是 ''1.0''。" }, { "ER_SCHEME_REQUIRED", "綱要是必需的！" }, { "ER_FACTORY_PROPERTY_MISSING", "傳遞到 SerializerFactory 的 Properties 物件沒有 ''{0}'' 內容。" }, { "ER_ENCODING_NOT_SUPPORTED", "警告：Java 執行時期不支援編碼 ''{0}''。" }, { "FEATURE_NOT_FOUND", "無法辨識參數 ''{0}''。" }, { "FEATURE_NOT_SUPPORTED", "可辨識 ''{0}'' 參數，但所要求的值無法設定。" }, { "DOMSTRING_SIZE_ERR", "結果字串過長，無法置入 DOMString: ''{0}'' 中。" }, { "TYPE_MISMATCH_ERR", "這個參數名稱的值類型與期望值類型不相容。" }, { "no-output-specified", "資料要寫入的輸出目的地為空值。" }, { "unsupported-encoding", "發現不支援的編碼。" }, { "ER_UNABLE_TO_SERIALIZE_NODE", "節點無法序列化。" }, { "cdata-sections-splitted", "CDATA 區段包含一或多個終止標記 ']]>'。" }, { "ER_WARNING_WF_NOT_CHECKED", "無法建立「形式完整」檢查程式的實例。Well-formed 參數雖設為 true，但無法執行形式完整檢查。" }, { "wf-invalid-character", "節點 ''{0}'' 包含無效的 XML 字元。" }, { "ER_WF_INVALID_CHARACTER_IN_COMMENT", "在註解中發現無效的 XML 字元 (Unicode: 0x{0})。" }, { "ER_WF_INVALID_CHARACTER_IN_PI", "在處理程序 instructiondata 中發現無效的 XML 字元 (Unicode: 0x{0})。" }, { "ER_WF_INVALID_CHARACTER_IN_CDATA", "在 CDATASection 的內容中發現無效的 XML 字元 (Unicode: 0x{0})。" }, { "ER_WF_INVALID_CHARACTER_IN_TEXT", "在節點的字元資料內容中發現無效的 XML 字元 (Unicode: 0x{0})。" }, { "wf-invalid-character-in-node-name", "在名為 ''{1}'' 的 ''{0}'' 中發現無效的 XML 字元。" }, { "ER_WF_DASH_IN_COMMENT", "註解中不允許使用字串 \"--\"。" }, { "ER_WF_LT_IN_ATTVAL", "與元素類型 \"{0}\" 相關聯的屬性 \"{1}\" 值不可包含 ''<'' 字元。" }, { "ER_WF_REF_TO_UNPARSED_ENT", "不允許使用未剖析的實體參照 \"&{0};\"。" }, { "ER_WF_REF_TO_EXTERNAL_ENT", "屬性值中不允許使用外部實體參照 \"&{0};\"。" }, { "ER_NS_PREFIX_CANNOT_BE_BOUND", "字首 \"{0}\" 無法連結到名稱空間 \"{1}\"。" }, { "ER_NULL_LOCAL_ELEMENT_NAME", "元素 \"{0}\" 的本端名稱是空值。" }, { "ER_NULL_LOCAL_ATTR_NAME", "屬性 \"{0}\" 的本端名稱是空值。" }, { "unbound-prefix-in-entity-reference", "實體節點 \"{0}\" 的取代文字包含附有已切斷連結字首 \"{2}\" 的元素節點 \"{1}\"。" }, { "unbound-prefix-in-entity-reference", "實體節點 \"{0}\" 的取代文字包含附有已切斷連結字首 \"{2}\" 的屬性節點 \"{1}\"。" } };
/*  11:    */     
/*  12:    */ 
/*  13:    */ 
/*  14:    */ 
/*  15:    */ 
/*  16:    */ 
/*  17:    */ 
/*  18:    */ 
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:291 */     return contents;
/* 229:    */   }
/* 230:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.SerializerMessages_zh_TW
 * JD-Core Version:    0.7.0.1
 */