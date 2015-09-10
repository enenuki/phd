/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_ko
/*   9:    */   extends ListResourceBundle
/*  10:    */ {
/*  11:    */   public static final String ER_FUNCTION_NOT_SUPPORTED = "ER_FUNCTION_NOT_SUPPORTED";
/*  12:    */   public static final String ER_CANNOT_OVERWRITE_CAUSE = "ER_CANNOT_OVERWRITE_CAUSE";
/*  13:    */   public static final String ER_NO_DEFAULT_IMPL = "ER_NO_DEFAULT_IMPL";
/*  14:    */   public static final String ER_CHUNKEDINTARRAY_NOT_SUPPORTED = "ER_CHUNKEDINTARRAY_NOT_SUPPORTED";
/*  15:    */   public static final String ER_OFFSET_BIGGER_THAN_SLOT = "ER_OFFSET_BIGGER_THAN_SLOT";
/*  16:    */   public static final String ER_COROUTINE_NOT_AVAIL = "ER_COROUTINE_NOT_AVAIL";
/*  17:    */   public static final String ER_COROUTINE_CO_EXIT = "ER_COROUTINE_CO_EXIT";
/*  18:    */   public static final String ER_COJOINROUTINESET_FAILED = "ER_COJOINROUTINESET_FAILED";
/*  19:    */   public static final String ER_COROUTINE_PARAM = "ER_COROUTINE_PARAM";
/*  20:    */   public static final String ER_PARSER_DOTERMINATE_ANSWERS = "ER_PARSER_DOTERMINATE_ANSWERS";
/*  21:    */   public static final String ER_NO_PARSE_CALL_WHILE_PARSING = "ER_NO_PARSE_CALL_WHILE_PARSING";
/*  22:    */   public static final String ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED = "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED";
/*  23:    */   public static final String ER_ITERATOR_AXIS_NOT_IMPLEMENTED = "ER_ITERATOR_AXIS_NOT_IMPLEMENTED";
/*  24:    */   public static final String ER_ITERATOR_CLONE_NOT_SUPPORTED = "ER_ITERATOR_CLONE_NOT_SUPPORTED";
/*  25:    */   public static final String ER_UNKNOWN_AXIS_TYPE = "ER_UNKNOWN_AXIS_TYPE";
/*  26:    */   public static final String ER_AXIS_NOT_SUPPORTED = "ER_AXIS_NOT_SUPPORTED";
/*  27:    */   public static final String ER_NO_DTMIDS_AVAIL = "ER_NO_DTMIDS_AVAIL";
/*  28:    */   public static final String ER_NOT_SUPPORTED = "ER_NOT_SUPPORTED";
/*  29:    */   public static final String ER_NODE_NON_NULL = "ER_NODE_NON_NULL";
/*  30:    */   public static final String ER_COULD_NOT_RESOLVE_NODE = "ER_COULD_NOT_RESOLVE_NODE";
/*  31:    */   public static final String ER_STARTPARSE_WHILE_PARSING = "ER_STARTPARSE_WHILE_PARSING";
/*  32:    */   public static final String ER_STARTPARSE_NEEDS_SAXPARSER = "ER_STARTPARSE_NEEDS_SAXPARSER";
/*  33:    */   public static final String ER_COULD_NOT_INIT_PARSER = "ER_COULD_NOT_INIT_PARSER";
/*  34:    */   public static final String ER_EXCEPTION_CREATING_POOL = "ER_EXCEPTION_CREATING_POOL";
/*  35:    */   public static final String ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE = "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE";
/*  36:    */   public static final String ER_SCHEME_REQUIRED = "ER_SCHEME_REQUIRED";
/*  37:    */   public static final String ER_NO_SCHEME_IN_URI = "ER_NO_SCHEME_IN_URI";
/*  38:    */   public static final String ER_NO_SCHEME_INURI = "ER_NO_SCHEME_INURI";
/*  39:    */   public static final String ER_PATH_INVALID_CHAR = "ER_PATH_INVALID_CHAR";
/*  40:    */   public static final String ER_SCHEME_FROM_NULL_STRING = "ER_SCHEME_FROM_NULL_STRING";
/*  41:    */   public static final String ER_SCHEME_NOT_CONFORMANT = "ER_SCHEME_NOT_CONFORMANT";
/*  42:    */   public static final String ER_HOST_ADDRESS_NOT_WELLFORMED = "ER_HOST_ADDRESS_NOT_WELLFORMED";
/*  43:    */   public static final String ER_PORT_WHEN_HOST_NULL = "ER_PORT_WHEN_HOST_NULL";
/*  44:    */   public static final String ER_INVALID_PORT = "ER_INVALID_PORT";
/*  45:    */   public static final String ER_FRAG_FOR_GENERIC_URI = "ER_FRAG_FOR_GENERIC_URI";
/*  46:    */   public static final String ER_FRAG_WHEN_PATH_NULL = "ER_FRAG_WHEN_PATH_NULL";
/*  47:    */   public static final String ER_FRAG_INVALID_CHAR = "ER_FRAG_INVALID_CHAR";
/*  48:    */   public static final String ER_PARSER_IN_USE = "ER_PARSER_IN_USE";
/*  49:    */   public static final String ER_CANNOT_CHANGE_WHILE_PARSING = "ER_CANNOT_CHANGE_WHILE_PARSING";
/*  50:    */   public static final String ER_SELF_CAUSATION_NOT_PERMITTED = "ER_SELF_CAUSATION_NOT_PERMITTED";
/*  51:    */   public static final String ER_NO_USERINFO_IF_NO_HOST = "ER_NO_USERINFO_IF_NO_HOST";
/*  52:    */   public static final String ER_NO_PORT_IF_NO_HOST = "ER_NO_PORT_IF_NO_HOST";
/*  53:    */   public static final String ER_NO_QUERY_STRING_IN_PATH = "ER_NO_QUERY_STRING_IN_PATH";
/*  54:    */   public static final String ER_NO_FRAGMENT_STRING_IN_PATH = "ER_NO_FRAGMENT_STRING_IN_PATH";
/*  55:    */   public static final String ER_CANNOT_INIT_URI_EMPTY_PARMS = "ER_CANNOT_INIT_URI_EMPTY_PARMS";
/*  56:    */   public static final String ER_METHOD_NOT_SUPPORTED = "ER_METHOD_NOT_SUPPORTED";
/*  57:    */   public static final String ER_INCRSAXSRCFILTER_NOT_RESTARTABLE = "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE";
/*  58:    */   public static final String ER_XMLRDR_NOT_BEFORE_STARTPARSE = "ER_XMLRDR_NOT_BEFORE_STARTPARSE";
/*  59:    */   public static final String ER_AXIS_TRAVERSER_NOT_SUPPORTED = "ER_AXIS_TRAVERSER_NOT_SUPPORTED";
/*  60:    */   public static final String ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER = "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER";
/*  61:    */   public static final String ER_SYSTEMID_UNKNOWN = "ER_SYSTEMID_UNKNOWN";
/*  62:    */   public static final String ER_LOCATION_UNKNOWN = "ER_LOCATION_UNKNOWN";
/*  63:    */   public static final String ER_PREFIX_MUST_RESOLVE = "ER_PREFIX_MUST_RESOLVE";
/*  64:    */   public static final String ER_CREATEDOCUMENT_NOT_SUPPORTED = "ER_CREATEDOCUMENT_NOT_SUPPORTED";
/*  65:    */   public static final String ER_CHILD_HAS_NO_OWNER_DOCUMENT = "ER_CHILD_HAS_NO_OWNER_DOCUMENT";
/*  66:    */   public static final String ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT = "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT";
/*  67:    */   public static final String ER_CANT_OUTPUT_TEXT_BEFORE_DOC = "ER_CANT_OUTPUT_TEXT_BEFORE_DOC";
/*  68:    */   public static final String ER_CANT_HAVE_MORE_THAN_ONE_ROOT = "ER_CANT_HAVE_MORE_THAN_ONE_ROOT";
/*  69:    */   public static final String ER_ARG_LOCALNAME_NULL = "ER_ARG_LOCALNAME_NULL";
/*  70:    */   public static final String ER_ARG_LOCALNAME_INVALID = "ER_ARG_LOCALNAME_INVALID";
/*  71:    */   public static final String ER_ARG_PREFIX_INVALID = "ER_ARG_PREFIX_INVALID";
/*  72:    */   public static final String ER_NAME_CANT_START_WITH_COLON = "ER_NAME_CANT_START_WITH_COLON";
/*  73:    */   
/*  74:    */   public Object[][] getContents()
/*  75:    */   {
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "함수가 지원되지 않습니다." }, { "ER_CANNOT_OVERWRITE_CAUSE", "원인을 겹쳐쓸 수 없습니다." }, { "ER_NO_DEFAULT_IMPL", "기본 구현이 없습니다. " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0})가 현재 지원되지 않습니다." }, { "ER_OFFSET_BIGGER_THAN_SLOT", "오프셋이 슬롯보다 큽니다." }, { "ER_COROUTINE_NOT_AVAIL", "Coroutine은 사용할 수 없습니다. id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager가 co_exit() 요청을 받았습니다." }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet()가 실패했습니다." }, { "ER_COROUTINE_PARAM", "Coroutine 매개변수 오류({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nUNEXPECTED: 구분 분석기 doTerminate가 {0}에 응답합니다." }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "구문 분석 중에는 parse를 호출할 수 없습니다." }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "오류: {0} 축에 대해 유형화된 반복기를 구현할 수 없습니다." }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "오류: {0} 축에 대한 반복기를 구현할 수 없습니다. " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "반복기 복제가 지원되지 않습니다." }, { "ER_UNKNOWN_AXIS_TYPE", "알 수 없는 axis traversal 유형: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Axis traverser가 지원되지 않습니다: {0}" }, { "ER_NO_DTMIDS_AVAIL", "사용 가능한 추가 DTM ID가 없습니다." }, { "ER_NOT_SUPPORTED", "지원되지 않습니다: {0}" }, { "ER_NODE_NON_NULL", "getDTMHandleFromNode의 노드는 널(null) 이외의 값이어야 합니다." }, { "ER_COULD_NOT_RESOLVE_NODE", "노드를 핸들로 분석할 수 없습니다." }, { "ER_STARTPARSE_WHILE_PARSING", "구문 분석 중에는 startParse를 호출할 수 없습니다." }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse는 널(null)이 아닌 SAXParser를 필요로 합니다." }, { "ER_COULD_NOT_INIT_PARSER", "구문 분석기를 초기화할 수 없습니다." }, { "ER_EXCEPTION_CREATING_POOL", "풀의 새 인스턴스 작성 중 예외가 발생했습니다." }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "경로에 잘못된 이스케이프 순서가 있습니다." }, { "ER_SCHEME_REQUIRED", "스키마가 필요합니다." }, { "ER_NO_SCHEME_IN_URI", "URI에 스키마가 없습니다: {0}" }, { "ER_NO_SCHEME_INURI", "URI에 스키마가 없습니다." }, { "ER_PATH_INVALID_CHAR", "경로에 잘못된 문자가 있습니다: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "널(null) 문자열에서 스키마를 설정할 수 없습니다." }, { "ER_SCHEME_NOT_CONFORMANT", "스키마가 일치하지 않습니다." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "호스트가 완전한 주소가 아닙니다." }, { "ER_PORT_WHEN_HOST_NULL", "호스트가 널(null)이면 포트를 설정할 수 없습니다." }, { "ER_INVALID_PORT", "잘못된 포트 번호" }, { "ER_FRAG_FOR_GENERIC_URI", "일반 URI에 대해서만 단편을 설정할 수 있습니다." }, { "ER_FRAG_WHEN_PATH_NULL", "경로가 널(null)이면 단편을 설정할 수 없습니다." }, { "ER_FRAG_INVALID_CHAR", "단편에 잘못된 문자가 있습니다." }, { "ER_PARSER_IN_USE", "구문 분석기가 이미 사용 중입니다." }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "구문 분석 중에는 {0} {1}을(를) 변경할 수 없습니다." }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Self-causation이 허용되지 않습니다." }, { "ER_NO_USERINFO_IF_NO_HOST", "호스트를 지정하지 않은 경우에는 Userinfo를 지정할 수 없습니다." }, { "ER_NO_PORT_IF_NO_HOST", "호스트를 지정하지 않은 경우에는 포트를 지정할 수 없습니다." }, { "ER_NO_QUERY_STRING_IN_PATH", "경로 및 조회 문자열에 조회 문자열을 지정할 수 없습니다." }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "경로 및 단편 둘 다에 단편을 지정할 수 없습니다." }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "빈 매개변수로 URI를 초기화할 수 없습니다." }, { "ER_METHOD_NOT_SUPPORTED", "아직 메소드가 지원되지 않았습니다. " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "현재 IncrementalSAXSource_Filter를 다시 시작할 수 없습니다." }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "startParse 요청 전에 XMLReader를 시작했습니다." }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Axis traverser가 지원되지 않습니다: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "널(null) PrintWriter로 ListingErrorHandler를 작성했습니다." }, { "ER_SYSTEMID_UNKNOWN", "SystemId를 알 수 없습니다." }, { "ER_LOCATION_UNKNOWN", "오류 위치 알 수 없음" }, { "ER_PREFIX_MUST_RESOLVE", "접두부는 이름 공간으로 분석되어야 합니다: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "XPathContext에서 createDocument()가 지원되지 않습니다." }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "하위 속성에 소유자 문서가 없습니다." }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "하위 속성에 소유자 문서 요소가 없습니다." }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "경고: 문서 요소 앞에 텍스트를 출력할 수 없습니다. 무시 중..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "DOM에 둘 이상의 루트가 있을 수 없습니다." }, { "ER_ARG_LOCALNAME_NULL", "'localName' 인수가 널(null)입니다." }, { "ER_ARG_LOCALNAME_INVALID", "QNAME의 로컬 이름은 유효한 NCName이어야 합니다." }, { "ER_ARG_PREFIX_INVALID", "QNAME의 접두부는 유효한 NCName이어야 합니다." }, { "ER_NAME_CANT_START_WITH_COLON", "이름은 콜론으로 시작할 수 없습니다. " }, { "BAD_CODE", "createMessage에 대한 매개변수가 범위를 벗어났습니다." }, { "FORMAT_FAILED", "messageFormat 호출 중 예외가 발생했습니다." }, { "line", "행 #" }, { "column", "열 #" } };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static final XMLErrorResources loadResourceBundle(String className)
/*  80:    */     throws MissingResourceException
/*  81:    */   {
/*  82:379 */     Locale locale = Locale.getDefault();
/*  83:380 */     String suffix = getResourceSuffix(locale);
/*  84:    */     try
/*  85:    */     {
/*  86:386 */       return (XMLErrorResources)ResourceBundle.getBundle(className + suffix, locale);
/*  87:    */     }
/*  88:    */     catch (MissingResourceException e)
/*  89:    */     {
/*  90:    */       try
/*  91:    */       {
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("ko", "KR"));
/*  93:    */       }
/*  94:    */       catch (MissingResourceException e2)
/*  95:    */       {
/*  96:404 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static final String getResourceSuffix(Locale locale)
/* 102:    */   {
/* 103:421 */     String suffix = "_" + locale.getLanguage();
/* 104:422 */     String country = locale.getCountry();
/* 105:424 */     if (country.equals("TW")) {
/* 106:425 */       suffix = suffix + "_" + country;
/* 107:    */     }
/* 108:427 */     return suffix;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_ko
 * JD-Core Version:    0.7.0.1
 */