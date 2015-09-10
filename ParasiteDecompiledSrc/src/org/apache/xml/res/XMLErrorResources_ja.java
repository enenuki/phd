/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_ja
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "機能はサポートされていません。" }, { "ER_CANNOT_OVERWRITE_CAUSE", "cause を上書きできません" }, { "ER_NO_DEFAULT_IMPL", "デフォルト実装が見つかりません " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "現在 ChunkedIntArray({0}) はサポートされていません" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "オフセットがスロットより大です" }, { "ER_COROUTINE_NOT_AVAIL", "コルーチンが使用可能でありません。id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager が co_exit() 要求を受け取りました" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() が失敗しました" }, { "ER_COROUTINE_PARAM", "コルーチン・パラメーター・エラー ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\n予想外: パーサー doTerminate が {0} を応答しています" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "parse は構文解析中に呼び出してはいけません" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "エラー: 軸 {0} の型付きイテレーターは実装されていません" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "エラー: 軸 {0} のイテレーターは実装されていません " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "イテレーターの複製はサポートされていません" }, { "ER_UNKNOWN_AXIS_TYPE", "不明の軸トラバース・タイプ: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "軸トラバーサーはサポートされていません: {0}" }, { "ER_NO_DTMIDS_AVAIL", "使用可能な DTM ID はこれ以上ありません" }, { "ER_NOT_SUPPORTED", "サポートされていません: {0}" }, { "ER_NODE_NON_NULL", "getDTMHandleFromNode のノードは非ヌルでなければなりません" }, { "ER_COULD_NOT_RESOLVE_NODE", "ノードをハンドルに解決できませんでした" }, { "ER_STARTPARSE_WHILE_PARSING", "startParse は構文解析中に呼び出してはいけません" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse にはヌル以外の SAXParser が必要です" }, { "ER_COULD_NOT_INIT_PARSER", "パーサーを次で初期化できませんでした:" }, { "ER_EXCEPTION_CREATING_POOL", "プールの新規インスタンスを作成中に例外" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "パスに無効なエスケープ・シーケンスが含まれています" }, { "ER_SCHEME_REQUIRED", "スキームが必要です。" }, { "ER_NO_SCHEME_IN_URI", "スキームは URI {0} で見つかりません" }, { "ER_NO_SCHEME_INURI", "スキームは URI で見つかりません" }, { "ER_PATH_INVALID_CHAR", "パスに無効文字: {0} が含まれています" }, { "ER_SCHEME_FROM_NULL_STRING", "ヌル・ストリングからはスキームを設定できません" }, { "ER_SCHEME_NOT_CONFORMANT", "スキームは一致していません。" }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "ホストはうまく構成されたアドレスでありません" }, { "ER_PORT_WHEN_HOST_NULL", "ホストがヌルであるとポートを設定できません" }, { "ER_INVALID_PORT", "無効なポート番号" }, { "ER_FRAG_FOR_GENERIC_URI", "総称 URI のフラグメントしか設定できません" }, { "ER_FRAG_WHEN_PATH_NULL", "パスがヌルであるとフラグメントを設定できません" }, { "ER_FRAG_INVALID_CHAR", "フラグメントに無効文字が含まれています" }, { "ER_PARSER_IN_USE", "パーサーはすでに使用中です" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "構文解析中に {0} {1} を変更できません" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "自分自身を原因とすることはできません" }, { "ER_NO_USERINFO_IF_NO_HOST", "ホストが指定されていない場合は Userinfo を指定してはいけません" }, { "ER_NO_PORT_IF_NO_HOST", "ホストが指定されていない場合はポートを指定してはいけません" }, { "ER_NO_QUERY_STRING_IN_PATH", "照会ストリングはパスおよび照会ストリング内に指定できません" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "フラグメントはパスとフラグメントの両方に指定できません" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "URI は空のパラメーターを使用して初期化できません" }, { "ER_METHOD_NOT_SUPPORTED", "メソッドはまだサポートされていません " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "現在 IncrementalSAXSource_Filter は再始動可能でありません" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader が startParse 要求の前でありません" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "軸トラバーサーはサポートされていません: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler がヌル PrintWriter で作成されました。" }, { "ER_SYSTEMID_UNKNOWN", "SystemId は不明" }, { "ER_LOCATION_UNKNOWN", "エラーの位置は不明" }, { "ER_PREFIX_MUST_RESOLVE", "接頭部は名前空間に解決されなければなりません: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() は XPathContext 内でサポートされません。" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "属性の子に所有者文書がありません。" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "属性の子に所有者文書要素がありません。" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "警告: 文書要素の前にテキストを出力できません。  無視しています..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "DOM では複数のルートを持つことはできません。" }, { "ER_ARG_LOCALNAME_NULL", "引数 'localName' がヌルです。" }, { "ER_ARG_LOCALNAME_INVALID", "QNAME 内のローカル名は有効な NCName であるはずです" }, { "ER_ARG_PREFIX_INVALID", "QNAME 内の接頭部は有効な NCName であるはずです" }, { "ER_NAME_CANT_START_WITH_COLON", "名前はコロンで始めることができません" }, { "BAD_CODE", "createMessage へのパラメーターが範囲外でした。" }, { "FORMAT_FAILED", "messageFormat 呼び出し中に例外がスローされました。" }, { "line", "行 #" }, { "column", "桁 #" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("en", "US"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_ja
 * JD-Core Version:    0.7.0.1
 */