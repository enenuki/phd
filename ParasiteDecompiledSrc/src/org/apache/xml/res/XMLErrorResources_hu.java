/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_hu
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "A függvény nem támogatott." }, { "ER_CANNOT_OVERWRITE_CAUSE", "Nem lehet felülírni az okot" }, { "ER_NO_DEFAULT_IMPL", "Nem található alapértelmezett megvalósítás " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "A ChunkedIntArray({0}) jelenleg nem támogatott" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Az eltolás nagyobb mint a nyílás" }, { "ER_COROUTINE_NOT_AVAIL", "Társ szubrutin nem érhető el, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager érkezett a co_exit() kérésre" }, { "ER_COJOINROUTINESET_FAILED", "A co_joinCoroutineSet() nem sikerült " }, { "ER_COROUTINE_PARAM", "Társ szubrutin paraméterhiba ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nVÁRATLAN: Értelmező doTerminate válaszok {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "értelmezés nem hívható meg értelmezés közben " }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Hiba: A tipizált iterátor a(z) {0} tengelyhez nincs megvalósítva" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Hiba: Az iterátor a(z) {0} tengelyhez nincs megvalósítva " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Az iterátor klónozása nem támogatott" }, { "ER_UNKNOWN_AXIS_TYPE", "Ismeretlen tengely bejárási út típus: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "A tengely bejárási út nem támogatott: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Nincs több DTM azonosító" }, { "ER_NOT_SUPPORTED", "Nem támogatott: {0}" }, { "ER_NODE_NON_NULL", "A csomópont nem lehet null a getDTMHandleFromNode függvényhez" }, { "ER_COULD_NOT_RESOLVE_NODE", "A csomópontot nem lehet azonosítóra feloldani" }, { "ER_STARTPARSE_WHILE_PARSING", "A startParse függvényt nem hívhatja meg értelmezés közben" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "A startParse függvényhez nemnull SAXParser szükséges" }, { "ER_COULD_NOT_INIT_PARSER", "Nem lehet inicializálni az értelmezőt ezzel" }, { "ER_EXCEPTION_CREATING_POOL", "kivétel egy új tárolópéldány létrehozásakor" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Az elérési út érvénytelen vezérlő jelsorozatot tartalmaz" }, { "ER_SCHEME_REQUIRED", "Séma szükséges." }, { "ER_NO_SCHEME_IN_URI", "Nem található séma az URI-ban: {0}" }, { "ER_NO_SCHEME_INURI", "Nem található séma az URI-ban" }, { "ER_PATH_INVALID_CHAR", "Az elérési út érvénytelen karaktert tartalmaz: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Nem lehet beállítani a sémát null karaktersorozatból" }, { "ER_SCHEME_NOT_CONFORMANT", "A séma nem megfelelő." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "A hoszt nem jól formázott cím" }, { "ER_PORT_WHEN_HOST_NULL", "A portot nem állíthatja be, ha a hoszt null" }, { "ER_INVALID_PORT", "Érvénytelen portszám" }, { "ER_FRAG_FOR_GENERIC_URI", "Csak általános URI-hoz állíthat be töredéket " }, { "ER_FRAG_WHEN_PATH_NULL", "A töredéket nem állíthatja be, ha az elérési út null" }, { "ER_FRAG_INVALID_CHAR", "A töredék érvénytelen karaktert tartalmaz" }, { "ER_PARSER_IN_USE", "Az értelmező már használatban van" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Nem változtatható meg a(z) {0} {1} értelmezés közben" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Az ön-megokolás nem megengedett" }, { "ER_NO_USERINFO_IF_NO_HOST", "Nem adhatja meg a felhasználói információkat, ha nincs megadva hoszt" }, { "ER_NO_PORT_IF_NO_HOST", "Nem adhatja meg a portot, ha nincs megadva hoszt" }, { "ER_NO_QUERY_STRING_IN_PATH", "Nem adhat meg lekérdezési karaktersorozatot az elérési útban és a lekérdezési karaktersorozatban" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Nem adhat meg töredéket az elérési útban és a töredékben is" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Az URI nem inicializálható üres paraméterekkel" }, { "ER_METHOD_NOT_SUPPORTED", "A metódus még nem támogatott " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "Az IncrementalSAXSource_Filter jelenleg nem índítható újra" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "Az XMLReader nem a startParse kérés előtt van " }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "A tengely bejárási út nem támogatott: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "A ListingErrorHandler null PrintWriter értékkel jött létre." }, { "ER_SYSTEMID_UNKNOWN", "Ismeretlen SystemId" }, { "ER_LOCATION_UNKNOWN", "A hiba helye ismeretlen" }, { "ER_PREFIX_MUST_RESOLVE", "Az előtagnak egy névtérre kell feloldódnia: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "A createDocument() nem támogatott az XPathContext-ben." }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Az attribútum utódnak nincs tulajdonos dokumentuma." }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Az attribútum utódnak nincs tulajdonos dokumentumeleme." }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Figyelmeztetés: nem lehet szöveget kiírni dokumentum elem előtt. Figyelmen kívül marad..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Nem lehet egynél több gyökér a DOM-ban" }, { "ER_ARG_LOCALNAME_NULL", "A 'localName' argumentum null" }, { "ER_ARG_LOCALNAME_INVALID", "A QNAME-beli helyi névnek egy érvényes NCName-nek kell lennie" }, { "ER_ARG_PREFIX_INVALID", "A QNAME-beli előtagnak érvényes NCName-nek kell lennie" }, { "ER_NAME_CANT_START_WITH_COLON", "A név nem kezdődhet kettősponttal" }, { "BAD_CODE", "A createMessage egyik paramétere nincs a megfelelő tartományban" }, { "FORMAT_FAILED", "Kivétel történt a messageFormat hívása közben" }, { "line", "Sor #" }, { "column", "Oszlop #" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("hu", "HU"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_hu
 * JD-Core Version:    0.7.0.1
 */