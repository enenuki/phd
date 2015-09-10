/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_zh
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "函数不受支持！" }, { "ER_CANNOT_OVERWRITE_CAUSE", "无法覆盖原因" }, { "ER_NO_DEFAULT_IMPL", "找不到缺省实现" }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "当前不支持 ChunkedIntArray({0})" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "偏移大于槽" }, { "ER_COROUTINE_NOT_AVAIL", "协同程序不可用，id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager 接收到 co_exit() 请求" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() 失败" }, { "ER_COROUTINE_PARAM", "协同程序参数错误（{0}）" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\n意外：解析器 doTerminate 应答 {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "解析时可能没有调用 parse" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "错误：没有实现为轴 {0} 输入的迭代器" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "错误：没有实现轴 {0} 的迭代器" }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "不支持迭代器克隆" }, { "ER_UNKNOWN_AXIS_TYPE", "未知的轴遍历类型：{0}" }, { "ER_AXIS_NOT_SUPPORTED", "不支持轴遍历程序：{0}" }, { "ER_NO_DTMIDS_AVAIL", "无更多的 DTM 标识可用" }, { "ER_NOT_SUPPORTED", "不支持：{0}" }, { "ER_NODE_NON_NULL", "对于 getDTMHandleFromNode，节点必须是非空的" }, { "ER_COULD_NOT_RESOLVE_NODE", "无法将节点解析到句柄" }, { "ER_STARTPARSE_WHILE_PARSING", "解析时可能没有调用 startParse" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse 需要非空的 SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "无法用以下工具初始化解析器" }, { "ER_EXCEPTION_CREATING_POOL", "为池创建新实例时发生异常" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "路径包含无效的转义序列" }, { "ER_SCHEME_REQUIRED", "模式是必需的！" }, { "ER_NO_SCHEME_IN_URI", "URI {0} 中找不到任何模式" }, { "ER_NO_SCHEME_INURI", "URI 中找不到任何模式" }, { "ER_PATH_INVALID_CHAR", "路径包含无效的字符：{0}" }, { "ER_SCHEME_FROM_NULL_STRING", "无法从空字符串设置模式" }, { "ER_SCHEME_NOT_CONFORMANT", "模式不一致。" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "主机不是格式正确的地址" }, { "ER_PORT_WHEN_HOST_NULL", "主机为空时，无法设置端口" }, { "ER_INVALID_PORT", "端口号无效" }, { "ER_FRAG_FOR_GENERIC_URI", "只能为类属 URI 设置片段" }, { "ER_FRAG_WHEN_PATH_NULL", "路径为空时，无法设置片段" }, { "ER_FRAG_INVALID_CHAR", "片段包含无效的字符" }, { "ER_PARSER_IN_USE", "解析器已在使用" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "解析时无法更改 {0} {1}" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "不允许自触发" }, { "ER_NO_USERINFO_IF_NO_HOST", "如果没有指定主机，则不可以指定用户信息" }, { "ER_NO_PORT_IF_NO_HOST", "如果没有指定主机，则不可以指定端口" }, { "ER_NO_QUERY_STRING_IN_PATH", "路径和查询字符串中不能指定查询字符串" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "路径和片段中都不能指定片段" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "不能以空参数初始化 URI" }, { "ER_METHOD_NOT_SUPPORTED", "尚不支持方法" }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "当前不可重新启动 IncrementalSAXSource_Filter" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader 不在 startParse 请求之前" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "不支持轴遍历程序：{0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "以空的 PrintWriter 创建了 ListingErrorHandler！" }, { "ER_SYSTEMID_UNKNOWN", "SystemId 未知" }, { "ER_LOCATION_UNKNOWN", "错误位置未知" }, { "ER_PREFIX_MUST_RESOLVE", "前缀必须解析为名称空间：{0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "XPathContext 中不支持 createDocument()！" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "子属性没有所有者文档！" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "子属性没有所有者文档元素！" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "警告：无法在记录元素前输出文本！忽略..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "DOM 上不能有多个根！" }, { "ER_ARG_LOCALNAME_NULL", "自变量“localName”为空" }, { "ER_ARG_LOCALNAME_INVALID", "QNAME 中的本地名应当是有效的 NCName" }, { "ER_ARG_PREFIX_INVALID", "QNAME 中的前缀应当是有效的 NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "名称不能以冒号开头" }, { "BAD_CODE", "createMessage 的参数超出范围" }, { "FORMAT_FAILED", "在 messageFormat 调用过程中抛出了异常" }, { "line", "行号" }, { "column", "列号" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("zh", "CN"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_zh
 * JD-Core Version:    0.7.0.1
 */