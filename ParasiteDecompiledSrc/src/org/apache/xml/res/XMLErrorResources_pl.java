/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_pl
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Nieobsługiwana funkcja!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Nie można nadpisać przyczyny" }, { "ER_NO_DEFAULT_IMPL", "Nie znaleziono domyślnej implementacji" }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) nie jest obecnie obsługiwane" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Przesunięcie większe niż szczelina" }, { "ER_COROUTINE_NOT_AVAIL", "Koprocedura niedostępna, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager otrzymał żądanie co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() nie powiodło się" }, { "ER_COROUTINE_PARAM", "Błąd parametru koprocedury ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nNIEOCZEKIWANE: Analizator doTerminate odpowiada {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "Nie można wywołać parse podczas analizowania" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Błąd: Iterator określonego typu dla osi {0} nie jest zaimplementowany" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Błąd: Iterator dla osi {0} nie jest zaimplementowany" }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Kopia iteratora nie jest obsługiwana" }, { "ER_UNKNOWN_AXIS_TYPE", "Nieznany typ przejścia osi {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Nieobsługiwane przejście osi: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Nie ma więcej dostępnych identyfikatorów DTM" }, { "ER_NOT_SUPPORTED", "Nieobsługiwane: {0}" }, { "ER_NODE_NON_NULL", "Węzeł musi być niepusty dla getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "Nie można przetłumaczyć węzła na uchwyt" }, { "ER_STARTPARSE_WHILE_PARSING", "Nie można wywołać startParse podczas analizowania" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse potrzebuje niepustego SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "nie można zainicjować analizatora" }, { "ER_EXCEPTION_CREATING_POOL", "wyjątek podczas tworzenia nowej instancji dla puli" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Ścieżka zawiera nieznaną sekwencję o zmienionym znaczeniu" }, { "ER_SCHEME_REQUIRED", "Schemat jest wymagany!" }, { "ER_NO_SCHEME_IN_URI", "Nie znaleziono schematu w URI {0}" }, { "ER_NO_SCHEME_INURI", "Nie znaleziono schematu w URI" }, { "ER_PATH_INVALID_CHAR", "Ścieżka zawiera niepoprawny znak {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Nie można ustawić schematu z pustego ciągu znaków" }, { "ER_SCHEME_NOT_CONFORMANT", "Schemat nie jest zgodny." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Host nie jest poprawnie skonstruowanym adresem" }, { "ER_PORT_WHEN_HOST_NULL", "Nie można ustawić portu, kiedy host jest pusty" }, { "ER_INVALID_PORT", "Niepoprawny numer portu" }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment można ustawić tylko dla ogólnego URI" }, { "ER_FRAG_WHEN_PATH_NULL", "Nie można ustawić fragmentu, kiedy ścieżka jest pusta" }, { "ER_FRAG_INVALID_CHAR", "Fragment zawiera niepoprawny znak" }, { "ER_PARSER_IN_USE", "Analizator jest już używany" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Nie można zmienić {0} {1} podczas analizowania" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Bycie własną przyczyną jest niedozwolone" }, { "ER_NO_USERINFO_IF_NO_HOST", "Nie można podać informacji o użytkowniku, jeśli nie podano hosta" }, { "ER_NO_PORT_IF_NO_HOST", "Nie można podać portu, jeśli nie podano hosta" }, { "ER_NO_QUERY_STRING_IN_PATH", "Tekstu zapytania nie można podać w tekście ścieżki i zapytania" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Nie można podać fragmentu jednocześnie w ścieżce i fragmencie" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Nie można zainicjować URI z pustymi parametrami" }, { "ER_METHOD_NOT_SUPPORTED", "Metoda nie jest jeszcze obsługiwana" }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter nie jest obecnie możliwy do ponownego uruchomienia" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader nie może wystąpić przed żądaniem startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Nieobsługiwane przejście osi: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "Utworzono ListingErrorHandler z pustym PrintWriter!" }, { "ER_SYSTEMID_UNKNOWN", "Nieznany identyfikator systemu" }, { "ER_LOCATION_UNKNOWN", "Położenie błędu jest nieznane" }, { "ER_PREFIX_MUST_RESOLVE", "Przedrostek musi dać się przetłumaczyć na przestrzeń nazw: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "Funkcja createDocument() nie jest obsługiwana w XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Bezpośredni element potomny atrybutu nie ma dokumentu właściciela!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Bezpośredni element potomny atrybutu nie ma elementu dokumentu właściciela!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Ostrzeżenie: Nie można wyprowadzić tekstu przed elementem dokumentu!  Ignorowanie..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Nie można mieć więcej niż jeden element główny w DOM!" }, { "ER_ARG_LOCALNAME_NULL", "Argument 'localName' jest pusty" }, { "ER_ARG_LOCALNAME_INVALID", "Nazwa lokalna w QNAME powinna być poprawną nazwą NCName" }, { "ER_ARG_PREFIX_INVALID", "Przedrostek w QNAME powinien być poprawną nazwą NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "Nazwa nie może rozpoczynać się od dwukropka" }, { "BAD_CODE", "Parametr createMessage był spoza zakresu" }, { "FORMAT_FAILED", "Podczas wywołania messageFormat zgłoszony został wyjątek" }, { "line", "Nr wiersza: " }, { "column", "Nr kolumny: " } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("pl", "PL"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_pl
 * JD-Core Version:    0.7.0.1
 */