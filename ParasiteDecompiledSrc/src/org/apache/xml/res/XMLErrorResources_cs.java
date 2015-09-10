/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_cs
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Nepodporovaná funkce!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Příčinu nelze přepsat" }, { "ER_NO_DEFAULT_IMPL", "Nebyla nalezena výchozí implementace. " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "Funkce ChunkedIntArray({0}) není aktuálně podporována." }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Offset je větší než slot." }, { "ER_COROUTINE_NOT_AVAIL", "Společná rutina není k dispozici, id={0}" }, { "ER_COROUTINE_CO_EXIT", "Funkce CoroutineManager obdržela požadavek co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "Selhala funkce co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Chyba parametru společné rutiny ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nNeočekávané: odpovědi funkce analyzátoru doTerminate {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "během analýzy nelze volat analyzátor" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Chyba: zadaný iterátor osy {0} není implementován" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Chyba: zadaný iterátor osy {0} není implementován " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Nepodporovaný klon iterátoru." }, { "ER_UNKNOWN_AXIS_TYPE", "Neznámý typ osy průchodu: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Nepodporovaná osa průchodu: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Žádná další ID DTM nejsou k dispozici" }, { "ER_NOT_SUPPORTED", "Nepodporováno: {0}" }, { "ER_NODE_NON_NULL", "Uzel použitý ve funkci getDTMHandleFromNode musí mít hodnotu not-null" }, { "ER_COULD_NOT_RESOLVE_NODE", "Uzel nelze přeložit do manipulátoru" }, { "ER_STARTPARSE_WHILE_PARSING", "Během analýzy nelze volat funkci startParse." }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "Funkce startParse vyžaduje SAXParser s hodnotou not-null." }, { "ER_COULD_NOT_INIT_PARSER", "nelze inicializovat analyzátor s:" }, { "ER_EXCEPTION_CREATING_POOL", "výjimka při vytváření nové instance společné oblasti" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Cesta obsahuje neplatnou escape sekvenci" }, { "ER_SCHEME_REQUIRED", "Je vyžadováno schéma!" }, { "ER_NO_SCHEME_IN_URI", "V URI nebylo nalezeno žádné schéma: {0}" }, { "ER_NO_SCHEME_INURI", "V URI nebylo nalezeno žádné schéma" }, { "ER_PATH_INVALID_CHAR", "Cesta obsahuje neplatný znak: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Nelze nastavit schéma řetězce s hodnotou null." }, { "ER_SCHEME_NOT_CONFORMANT", "Schéma nevyhovuje." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Adresa hostitele má nesprávný formát." }, { "ER_PORT_WHEN_HOST_NULL", "Má-li hostitel hodnotu null, nelze nastavit port." }, { "ER_INVALID_PORT", "Neplatné číslo portu." }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment lze nastavit jen u generického URI." }, { "ER_FRAG_WHEN_PATH_NULL", "Má-li cesta hodnotu null, nelze nastavit fragment." }, { "ER_FRAG_INVALID_CHAR", "Fragment obsahuje neplatný znak." }, { "ER_PARSER_IN_USE", "Analyzátor se již používá." }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Během analýzy nelze měnit {0} {1}." }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Způsobení sama sebe (self-causation) není povoleno" }, { "ER_NO_USERINFO_IF_NO_HOST", "Není-li určen hostitel, nelze zadat údaje o uživateli." }, { "ER_NO_PORT_IF_NO_HOST", "Není-li určen hostitel, nelze zadat port." }, { "ER_NO_QUERY_STRING_IN_PATH", "V řetězci cesty a dotazu nelze zadat řetězec dotazu." }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment nelze určit zároveň v cestě i ve fragmentu." }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "URI nelze inicializovat s prázdnými parametry." }, { "ER_METHOD_NOT_SUPPORTED", "Prozatím nepodporovaná metoda. " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "Filtr IncrementalSAXSource_Filter nelze aktuálně znovu spustit." }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "Před požadavkem startParse není XMLReader." }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Nepodporovaná osa průchodu: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "Prvek ListingErrorHandler byl vytvořen s funkcí PrintWriter s hodnotou null!" }, { "ER_SYSTEMID_UNKNOWN", "Neznámý identifikátor SystemId" }, { "ER_LOCATION_UNKNOWN", "Chyba se vyskytla na neznámém místě" }, { "ER_PREFIX_MUST_RESOLVE", "Předponu musí být možno přeložit do oboru názvů: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "Funkce XPathContext nepodporuje funkci createDocument()!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Potomek atributu nemá dokument vlastníka!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Potomek atributu nemá prvek dokumentu vlastníka!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Varování: výstup textu nemůže předcházet prvku dokumentu!  Ignorováno..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "DOM nemůže mít několik kořenů!" }, { "ER_ARG_LOCALNAME_NULL", "Argument 'localName' má hodnotu null" }, { "ER_ARG_LOCALNAME_INVALID", "Hodnota Localname ve funkci QNAME by měla být platným prvkem NCName" }, { "ER_ARG_PREFIX_INVALID", "Předpona ve funkci QNAME by měla být platným prvkem NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "Název nesmí začínat dvojtečkou" }, { "BAD_CODE", "Parametr funkce createMessage je mimo limit" }, { "FORMAT_FAILED", "Při volání funkce messageFormat došlo k výjimce" }, { "line", "Řádek #" }, { "column", "Sloupec #" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("cs", "CZ"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_cs
 * JD-Core Version:    0.7.0.1
 */