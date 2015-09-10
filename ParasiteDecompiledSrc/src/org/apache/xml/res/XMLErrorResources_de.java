/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_de
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funktion wird nicht unterstützt!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "cause kann nicht überschrieben werden." }, { "ER_NO_DEFAULT_IMPL", "Keine Standardimplementierung gefunden. " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) wird derzeit nicht unterstützt." }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Offset ist größer als der Bereich." }, { "ER_COROUTINE_NOT_AVAIL", "Koroutine nicht verfügbar, ID: {0}." }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager hat Anforderung co_exit() empfangen." }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() ist fehlgeschlagen." }, { "ER_COROUTINE_PARAM", "Parameterfehler der Koroutine ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nUNERWARTET: Parser doTerminate antwortet {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "parse darf während der Syntaxanalyse nicht aufgerufen werden." }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Fehler: Iterator mit Typangabe für Achse {0} ist nicht implementiert." }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Fehler: Iterator für Achse {0} ist nicht implementiert. " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Iterator 'clone' wird nicht unterstützt." }, { "ER_UNKNOWN_AXIS_TYPE", "Unbekannter Achsentraversiertyp: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Achsentraversierer wird nicht unterstützt: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Keine weiteren Dokumenttypmodell-IDs verfügbar" }, { "ER_NOT_SUPPORTED", "Nicht unterstützt: {0}" }, { "ER_NODE_NON_NULL", "Knoten muss ungleich Null sein für getDTMHandleFromNode." }, { "ER_COULD_NOT_RESOLVE_NODE", "Der Knoten konnte nicht in eine Kennung aufgelöst werden." }, { "ER_STARTPARSE_WHILE_PARSING", "startParse kann während der Syntaxanalyse nicht aufgerufen werden." }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse erfordert SAXParser ungleich Null." }, { "ER_COULD_NOT_INIT_PARSER", "Konnte Parser nicht initialisieren mit" }, { "ER_EXCEPTION_CREATING_POOL", "Ausnahmebedingung beim Erstellen einer neuen Instanz für Pool." }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Der Pfad enthält eine ungültige Escapezeichenfolge." }, { "ER_SCHEME_REQUIRED", "Schema ist erforderlich!" }, { "ER_NO_SCHEME_IN_URI", "Kein Schema gefunden in URI: {0}." }, { "ER_NO_SCHEME_INURI", "Kein Schema gefunden in URI" }, { "ER_PATH_INVALID_CHAR", "Pfad enthält ungültiges Zeichen: {0}." }, { "ER_SCHEME_FROM_NULL_STRING", "Schema kann nicht von Nullzeichenfolge festgelegt werden." }, { "ER_SCHEME_NOT_CONFORMANT", "Das Schema ist nicht angepasst." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Der Host ist keine syntaktisch korrekte Adresse." }, { "ER_PORT_WHEN_HOST_NULL", "Der Port kann nicht festgelegt werden, wenn der Host gleich Null ist." }, { "ER_INVALID_PORT", "Ungültige Portnummer" }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment kann nur für eine generische URI (Uniform Resource Identifier) festgelegt werden." }, { "ER_FRAG_WHEN_PATH_NULL", "Fragment kann nicht festgelegt werden, wenn der Pfad gleich Null ist." }, { "ER_FRAG_INVALID_CHAR", "Fragment enthält ein ungültiges Zeichen." }, { "ER_PARSER_IN_USE", "Der Parser wird bereits verwendet." }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "{0} {1} kann während der Syntaxanalyse nicht geändert werden." }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Selbstverursachung ist nicht zulässig." }, { "ER_NO_USERINFO_IF_NO_HOST", "Benutzerinformationen können nicht angegeben werden, wenn der Host nicht angegeben wurde." }, { "ER_NO_PORT_IF_NO_HOST", "Der Port kann nicht angegeben werden, wenn der Host nicht angegeben wurde." }, { "ER_NO_QUERY_STRING_IN_PATH", "Abfragezeichenfolge kann nicht im Pfad und in der Abfragezeichenfolge angegeben werden." }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment kann nicht im Pfad und im Fragment angegeben werden." }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "URI (Uniform Resource Identifier) kann nicht mit leeren Parametern initialisiert werden." }, { "ER_METHOD_NOT_SUPPORTED", "Die Methode wird noch nicht unterstützt. " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter ist momentan nicht wieder anlauffähig." }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader nicht vor Anforderung startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Achsentraversierer nicht unterstützt: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler erstellt ohne Druckausgabeprogramm!" }, { "ER_SYSTEMID_UNKNOWN", "System-ID unbekannt" }, { "ER_LOCATION_UNKNOWN", "Position des Fehlers unbekannt" }, { "ER_PREFIX_MUST_RESOLVE", "Das Präfix muss in einen Namensbereich aufgelöst werden: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() wird nicht in XPathContext unterstützt!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Das Attribut child weist kein Eignerdokument auf!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Das Attribut child weist kein Eignerdokumentelement auf!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Warnung: Vor dem Dokumentelement kann kein Text ausgegeben werden!  Wird ignoriert..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Mehr als ein Root für ein Dokumentobjektmodell ist nicht möglich!" }, { "ER_ARG_LOCALNAME_NULL", "Das Argument 'localName' ist Null." }, { "ER_ARG_LOCALNAME_INVALID", "Der lokale Name (Localname) in QNAME muss ein gültiger NCName sein." }, { "ER_ARG_PREFIX_INVALID", "Das Präfix in QNAME muss ein gültiger NCName sein." }, { "ER_NAME_CANT_START_WITH_COLON", "Name darf nicht mit einem Doppelpunkt beginnen." }, { "BAD_CODE", "Der Parameter für createMessage lag außerhalb des gültigen Bereichs" }, { "FORMAT_FAILED", "Während des Aufrufs von messageFormat wurde eine Ausnahmebedingung ausgelöst" }, { "line", "Zeilennummer" }, { "column", "Spaltennummer" } };
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_de
 * JD-Core Version:    0.7.0.1
 */