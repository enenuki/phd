/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_ru
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Функция не поддерживается!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Невозможно перезаписать причину" }, { "ER_NO_DEFAULT_IMPL", "Реализация по умолчанию не найдена " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) в настоящее время не поддерживается" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Смещение больше диапазона" }, { "ER_COROUTINE_NOT_AVAIL", "Coroutine недоступна, ИД={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager получил запрос co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "Ошибка co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Ошибка параметра Coroutine ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nНепредвиденная ошибка: Ответ анализатора doTerminate: {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "Нельзя вызывать анализатор во время анализа" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Ошибка: типизированный итератор для оси {0} не реализован" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Ошибка: не реализован счетчик для оси {0} " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Копия итератора не поддерживается" }, { "ER_UNKNOWN_AXIS_TYPE", "Неизвестный тип Traverser для оси: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Traverser для оси не поддерживается: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Нет доступных ИД DTM" }, { "ER_NOT_SUPPORTED", "Не поддерживается: {0}" }, { "ER_NODE_NON_NULL", "Для getDTMHandleFromNode узел должен быть непустым" }, { "ER_COULD_NOT_RESOLVE_NODE", "Не удалось преобразовать узел в дескриптор" }, { "ER_STARTPARSE_WHILE_PARSING", "Нельзя вызывать startParse во время анализа" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "Для startParse необходим непустой SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "Не удалось инициализировать анализатор с" }, { "ER_EXCEPTION_CREATING_POOL", "Исключительная ситуация при создании нового экземпляра пула" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "В имени пути встречается недопустимая Esc-последовательность" }, { "ER_SCHEME_REQUIRED", "Необходима схема!" }, { "ER_NO_SCHEME_IN_URI", "В URI не найдена схема: {0}" }, { "ER_NO_SCHEME_INURI", "В URI не найдена схема" }, { "ER_PATH_INVALID_CHAR", "В имени пути обнаружен недопустимый символ: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Невозможно задать схему для пустой строки" }, { "ER_SCHEME_NOT_CONFORMANT", "Схема не конформативна." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Неправильно сформирован адрес хоста" }, { "ER_PORT_WHEN_HOST_NULL", "Невозможно задать порт для пустого адреса хоста" }, { "ER_INVALID_PORT", "Недопустимый номер порта" }, { "ER_FRAG_FOR_GENERIC_URI", "Фрагмент можно задать только для шаблона URI" }, { "ER_FRAG_WHEN_PATH_NULL", "Невозможно задать фрагмент для пустого пути" }, { "ER_FRAG_INVALID_CHAR", "Фрагмент содержит недопустимый символ" }, { "ER_PARSER_IN_USE", "Анализатор уже используется" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Невозможно изменить {0} {1} во время анализа" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Самоприсвоение недопустимо" }, { "ER_NO_USERINFO_IF_NO_HOST", "Нельзя указывать информацию о пользователе, если не задан хост" }, { "ER_NO_PORT_IF_NO_HOST", "Нельзя указывать порт, если не задан хост" }, { "ER_NO_QUERY_STRING_IN_PATH", "Нельзя указывать строку запроса в строке пути и запроса" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Невозможно задать фрагмент одновременно для пути и фрагмента" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Невозможно инициализировать URI с пустыми параметрами" }, { "ER_METHOD_NOT_SUPPORTED", "Метод пока не поддерживается " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "Перезапуск IncrementalSAXSource_Filter в настоящее время невозможен" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "Нельзя применять XMLReader до startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Traverser для оси не поддерживается: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler создан с пустым PrintWriter!" }, { "ER_SYSTEMID_UNKNOWN", "Неизвестный ИД системы" }, { "ER_LOCATION_UNKNOWN", "Неизвестное расположение или ошибка" }, { "ER_PREFIX_MUST_RESOLVE", "Префикс должен обеспечивать преобразование в пространство имен: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() не поддерживается XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "У атрибута child нет документа-владельца!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "У атрибута child нет элемента документа-владельца!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Предупреждение: Невозможно вывести текст перед элементом документа!  Проигнорирован..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "В DOM может быть только один корневой элемент!" }, { "ER_ARG_LOCALNAME_NULL", "Пустой аргумент 'localName'" }, { "ER_ARG_LOCALNAME_INVALID", "Локальное имя в QNAME должно быть допустимым именем NCName" }, { "ER_ARG_PREFIX_INVALID", "Префикс в QNAME должен быть допустимым именем NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "Имя не может начинаться с двоеточия" }, { "BAD_CODE", "Параметр createMessage лежит вне допустимого диапазона" }, { "FORMAT_FAILED", "Исключительная ситуация при вызове messageFormat" }, { "line", "Номер строки " }, { "column", "Номер столбца " } };
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_ru
 * JD-Core Version:    0.7.0.1
 */