/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_zh_TW
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "函數不受支援！" }, { "ER_CANNOT_OVERWRITE_CAUSE", "無法改寫原因" }, { "ER_NO_DEFAULT_IMPL", "找不到預設實作" }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) 目前不受支援" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "偏移比槽大" }, { "ER_COROUTINE_NOT_AVAIL", "沒有 Coroutine 可用，id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager 收到 co_exit() 要求" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() 失效" }, { "ER_COROUTINE_PARAM", "Coroutine 參數錯誤 ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\n非預期的結果：剖析器 doTerminate 回答 {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "在剖析時未呼叫 parse" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "錯誤：針對軸 {0} 輸入的重複項目沒有實作" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "錯誤：軸 {0} 的重複項目沒有實作" }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "重複項目複製不受支援" }, { "ER_UNKNOWN_AXIS_TYPE", "不明的軸遍歷類型：{0}" }, { "ER_AXIS_NOT_SUPPORTED", "不支援軸遍歷：{0}" }, { "ER_NO_DTMIDS_AVAIL", "沒有可用的 DTM ID" }, { "ER_NOT_SUPPORTED", "不支援：{0}" }, { "ER_NODE_NON_NULL", "對 getDTMHandleFromNode 而言，節點必須為非空值" }, { "ER_COULD_NOT_RESOLVE_NODE", "無法解析節點為控點" }, { "ER_STARTPARSE_WHILE_PARSING", "在剖析時未呼叫 startParse" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse 需要非空值的 SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "無法使用以下項目起始設定剖析器" }, { "ER_EXCEPTION_CREATING_POOL", "建立儲存池的新實例時發生異常" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "路徑包含無效的跳脫字元" }, { "ER_SCHEME_REQUIRED", "綱要是必需的！" }, { "ER_NO_SCHEME_IN_URI", "在 URI：{0} 找不到綱要" }, { "ER_NO_SCHEME_INURI", "在 URI 找不到綱要" }, { "ER_PATH_INVALID_CHAR", "路徑包含無效的字元：{0}" }, { "ER_SCHEME_FROM_NULL_STRING", "無法從空字串設定綱要" }, { "ER_SCHEME_NOT_CONFORMANT", "綱要不是 conformant。" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "主機沒有完整的位址" }, { "ER_PORT_WHEN_HOST_NULL", "主機為空值時，無法設定埠" }, { "ER_INVALID_PORT", "無效的埠編號" }, { "ER_FRAG_FOR_GENERIC_URI", "只能對通用的 URI 設定片段" }, { "ER_FRAG_WHEN_PATH_NULL", "路徑為空值時，無法設定片段" }, { "ER_FRAG_INVALID_CHAR", "片段包含無效的字元" }, { "ER_PARSER_IN_USE", "剖析器已在使用中" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "剖析時無法變更 {0} {1}" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "不允許本身的因果關係" }, { "ER_NO_USERINFO_IF_NO_HOST", "如果沒有指定主機，不可指定 Userinfo" }, { "ER_NO_PORT_IF_NO_HOST", "如果沒有指定主機，不可指定埠" }, { "ER_NO_QUERY_STRING_IN_PATH", "在路徑及查詢字串中不可指定查詢字串" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "片段無法同時在路徑和片段中指定" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "無法以空白參數起始設定 URI" }, { "ER_METHOD_NOT_SUPPORTED", "方法不受支援" }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter 目前無法重新啟動" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader 沒有在 startParse 要求之前" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "不支援軸遍歷：{0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "以空值 PrintWriter 建立的 ListingErrorHandler！" }, { "ER_SYSTEMID_UNKNOWN", "不明的 SystemId" }, { "ER_LOCATION_UNKNOWN", "錯誤位置不明" }, { "ER_PREFIX_MUST_RESOLVE", "字首必須解析為名稱空間：{0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "在 XPathContext 中不支援 createDocument()" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "屬性子項元件沒有擁有者文件！" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "屬性子項元件沒有擁有者文件元素！" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "警告：不能輸出文件元素之前的文字！忽略..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "一個 DOM 只能有一個根目錄！" }, { "ER_ARG_LOCALNAME_NULL", "引數 'localName' 為空值" }, { "ER_ARG_LOCALNAME_INVALID", "QNAME 中的本端名稱應該是有效的 NCName" }, { "ER_ARG_PREFIX_INVALID", "QNAME 中的字首應該是有效的 NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "名稱的開頭不可為冒號" }, { "BAD_CODE", "createMessage 的參數超出界限" }, { "FORMAT_FAILED", "在 messageFormat 呼叫期間擲出異常" }, { "line", "行號" }, { "column", "欄號" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("zh", "TW"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_zh_TW
 * JD-Core Version:    0.7.0.1
 */