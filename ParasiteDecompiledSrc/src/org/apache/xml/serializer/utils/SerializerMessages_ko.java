/*   1:    */ package org.apache.xml.serializer.utils;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ 
/*   5:    */ public class SerializerMessages_ko
/*   6:    */   extends ListResourceBundle
/*   7:    */ {
/*   8:    */   public Object[][] getContents()
/*   9:    */   {
/*  10: 73 */     Object[][] contents = { { "BAD_MSGKEY", "''{0}'' 메시지 키가 ''{1}'' 메시지 클래스에 없습니다." }, { "BAD_MSGFORMAT", "''{1}'' 메시지 클래스에 있는 ''{0}'' 메시지의 형식이 잘못 되었습니다." }, { "ER_SERIALIZER_NOT_CONTENTHANDLER", "''{0}'' 직렬 프로그램 클래스가 org.xml.sax.ContentHandler를 구현하지 않습니다." }, { "ER_RESOURCE_COULD_NOT_FIND", "[ {0} ] 자원을 찾을 수 없습니다.\n{1}" }, { "ER_RESOURCE_COULD_NOT_LOAD", "[ {0} ] 자원이 {1} \n {2} \t {3}을(를) 로드할 수 없습니다." }, { "ER_BUFFER_SIZE_LESSTHAN_ZERO", "버퍼 크기 <=0" }, { "ER_INVALID_UTF16_SURROGATE", "잘못된 UTF-16 대리자(surrogate)가 발견되었습니다: {0} ?" }, { "ER_OIERROR", "IO 오류" }, { "ER_ILLEGAL_ATTRIBUTE_POSITION", "하위 노드가 생성된 이후 또는 요소가 작성되기 이전에 {0} 속성을 추가할 수 없습니다. 속성이 무시됩니다." }, { "ER_NAMESPACE_PREFIX", "''{0}'' 접두부에 대한 이름 공간이 선언되지 않았습니다." }, { "ER_STRAY_ATTRIBUTE", "''{0}'' 속성이 요소의 외부에 있습니다." }, { "ER_STRAY_NAMESPACE", "''{0}''=''{1}'' 이름 공간 선언이 요소의 외부에 있습니다." }, { "ER_COULD_NOT_LOAD_RESOURCE", "''{0}''(CLASSPATH 확인)을(를) 로드할 수 없으므로, 현재 기본값만을 사용하는 중입니다." }, { "ER_ILLEGAL_CHARACTER", "{1}의 지정된 출력 인코딩에 표시되지 않은 무결성 값 {0}의 문자를 출력하십시오. " }, { "ER_COULD_NOT_LOAD_METHOD_PROPERTY", "''{1}'' 출력 메소드(CLASSPATH 확인)에 대한 ''{0}'' 특성 파일을 로드할 수 없습니다." }, { "ER_INVALID_PORT", "잘못된 포트 번호" }, { "ER_PORT_WHEN_HOST_NULL", "호스트가 널(null)이면 포트를 설정할 수 없습니다." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "호스트가 완전한 주소가 아닙니다." }, { "ER_SCHEME_NOT_CONFORMANT", "스키마가 일치하지 않습니다." }, { "ER_SCHEME_FROM_NULL_STRING", "널(null) 문자열에서 스키마를 설정할 수 없습니다." }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "경로에 잘못된 이스케이프 순서가 있습니다." }, { "ER_PATH_INVALID_CHAR", "경로에 잘못된 문자가 있습니다: {0}" }, { "ER_FRAG_INVALID_CHAR", "단편에 잘못된 문자가 있습니다." }, { "ER_FRAG_WHEN_PATH_NULL", "경로가 널(null)이면 단편을 설정할 수 없습니다." }, { "ER_FRAG_FOR_GENERIC_URI", "일반 URI에 대해서만 단편을 설정할 수 있습니다." }, { "ER_NO_SCHEME_IN_URI", "URI에 스키마가 없습니다." }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "빈 매개변수로 URI를 초기화할 수 없습니다." }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "경로 및 단편 둘 다에 단편을 지정할 수 없습니다." }, { "ER_NO_QUERY_STRING_IN_PATH", "경로 및 조회 문자열에 조회 문자열을 지정할 수 없습니다." }, { "ER_NO_PORT_IF_NO_HOST", "호스트를 지정하지 않은 경우에는 포트를 지정할 수 없습니다." }, { "ER_NO_USERINFO_IF_NO_HOST", "호스트를 지정하지 않은 경우에는 Userinfo를 지정할 수 없습니다." }, { "ER_XML_VERSION_NOT_SUPPORTED", "경고:  요청된 출력 문서의 버전은 ''{0}''입니다. 하지만 이 XML 버전은 지원되지 않습니다. 출력 문서의 버전은 ''1.0''이 됩니다." }, { "ER_SCHEME_REQUIRED", "스키마가 필요합니다." }, { "ER_FACTORY_PROPERTY_MISSING", "SerializerFactory에 전달된 특성 오브젝트에 ''{0}'' 특성이 없습니다." }, { "ER_ENCODING_NOT_SUPPORTED", "경고: ''{0}'' 인코딩은 Java Runtime을 지원하지 않습니다." }, { "FEATURE_NOT_FOUND", "''{0}'' 매개변수를 인식할 수 없습니다." }, { "FEATURE_NOT_SUPPORTED", "''{0}'' 매개변수는 인식할 수 있으나 요청된 값을 설정할 수 없습니다." }, { "DOMSTRING_SIZE_ERR", "결과 문자열이 너무 길어 DOMString에 맞지 않습니다: ''{0}'' " }, { "TYPE_MISMATCH_ERR", "이 매개변수 이름에 대한 값 유형이 예상 값 유형과 호환되지 않습니다." }, { "no-output-specified", "데이터를 기록할 출력 대상이 널(null)입니다." }, { "unsupported-encoding", "지원되지 않는 인코딩이 발견되었습니다." }, { "ER_UNABLE_TO_SERIALIZE_NODE", "노드를 직렬화할 수 없습니다." }, { "cdata-sections-splitted", "CDATA 섹션에 종료 표시 문자인 ']]>'가 하나 이상 포함되어 있습니다." }, { "ER_WARNING_WF_NOT_CHECKED", "Well-Formedness 검사기의 인스턴스를 작성할 수 없습니다. Well-Formed 매개변수가 true로 설정되었지만 Well-Formedness 검사를 수행할 수 없습니다." }, { "wf-invalid-character", "''{0}'' 노드에 유효하지 않은 XML 문자가 있습니다." }, { "ER_WF_INVALID_CHARACTER_IN_COMMENT", "설명에 유효하지 않은 XML 문자(Unicode: 0x{0})가 있습니다. " }, { "ER_WF_INVALID_CHARACTER_IN_PI", "처리 명령어 데이터에 유효하지 않은 XML 문자(Unicode: 0x{0})가 있습니다 " }, { "ER_WF_INVALID_CHARACTER_IN_CDATA", "CDATASection의 내용에 유효하지 않은 XML 문자(Unicode: 0x{0})가 있습니다. " }, { "ER_WF_INVALID_CHARACTER_IN_TEXT", "노드의 문자 데이터 내용에 유효하지 않은 XML 문자(Unicode: 0x{0})가 있습니다. " }, { "wf-invalid-character-in-node-name", "이름이 ''{1}''인 {0} 노드에 유효하지 않은 XML 문자가 있습니다. " }, { "ER_WF_DASH_IN_COMMENT", "설명 내에서는 \"--\" 문자열이 허용되지 않습니다." }, { "ER_WF_LT_IN_ATTVAL", "\"{0}\" 요소 유형과 연관된 \"{1}\" 속성값에 ''<'' 문자가 포함되면 안됩니다." }, { "ER_WF_REF_TO_UNPARSED_ENT", "\"&{0};\"의 구분 분석되지 않은 엔티티 참조는 허용되지 않습니다. " }, { "ER_WF_REF_TO_EXTERNAL_ENT", "속성값에는 \"&{0};\" 외부 엔티티 참조가 허용되지 않습니다. " }, { "ER_NS_PREFIX_CANNOT_BE_BOUND", "\"{0}\" 접두부를 \"{1}\" 이름 공간에 바인드할 수 없습니다." }, { "ER_NULL_LOCAL_ELEMENT_NAME", "\"{0}\" 요소의 로컬 이름이 널(null)입니다." }, { "ER_NULL_LOCAL_ATTR_NAME", "\"{0}\" 속성의 로컬 이름이 널(null)입니다." }, { "unbound-prefix-in-entity-reference", "\"{0}\" 엔티티 노드의 대체 텍스트에 바인드되지 않은 접두부 \"{2}\"을(를) 갖는 \"{1}\" 요소 노드가 있습니다." }, { "unbound-prefix-in-entity-reference", "\"{0}\" 엔티티 노드의 대체 텍스트에 바인드되지 않은 접두부 \"{2}\"을(를) 갖는 \"{1}\" 속성 노드가 있습니다." } };
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
 * Qualified Name:     org.apache.xml.serializer.utils.SerializerMessages_ko
 * JD-Core Version:    0.7.0.1
 */