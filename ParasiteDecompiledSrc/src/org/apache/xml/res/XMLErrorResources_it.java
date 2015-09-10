/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_it
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funzione non supportata." }, { "ER_CANNOT_OVERWRITE_CAUSE", "Impossibile sovrascrivere causa" }, { "ER_NO_DEFAULT_IMPL", "Non è stata trovata alcuna implementazione predefinita " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) correntemente non supportato" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Offset più grande dello slot" }, { "ER_COROUTINE_NOT_AVAIL", "Coroutine non disponibile, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager ha ricevuto la richiesta co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() con esito negativo" }, { "ER_COROUTINE_PARAM", "Errore parametro Coroutine {0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nNON PREVISTO: Risposte doTerminate del parser {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "impossibile richiamare l'analisi durante l'analisi" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Errore: iteratore immesso per l''''asse {0} non implementato" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Errore: iteratore per l''''asse {0} non implementato " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Clone iteratore non supportato" }, { "ER_UNKNOWN_AXIS_TYPE", "Tipo trasversale di asse sconosciuto: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Trasversale dell''''asse non supportato: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Non vi sono ulteriori ID DTM disponibili" }, { "ER_NOT_SUPPORTED", "Non supportato: {0}" }, { "ER_NODE_NON_NULL", "Il nodo deve essere non nullo per getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "Impossibile risolvere il nodo in un handle" }, { "ER_STARTPARSE_WHILE_PARSING", "Impossibile richiamare startParse durante l'analisi" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse richiede SAXParser non nullo" }, { "ER_COULD_NOT_INIT_PARSER", "impossibile inizializzare il parser con" }, { "ER_EXCEPTION_CREATING_POOL", "si è verificata un'eccezione durante la creazione della nuova istanza per il pool" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Il percorso contiene sequenza di escape non valida" }, { "ER_SCHEME_REQUIRED", "Lo schema è obbligatorio." }, { "ER_NO_SCHEME_IN_URI", "Nessuno schema trovato nell''''URI: {0}" }, { "ER_NO_SCHEME_INURI", "Non è stato trovato alcuno schema nell'URI" }, { "ER_PATH_INVALID_CHAR", "Il percorso contiene un carattere non valido: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Impossibile impostare lo schema da una stringa nulla" }, { "ER_SCHEME_NOT_CONFORMANT", "Lo schema non è conforme." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Host non è un'indirizzo corretto" }, { "ER_PORT_WHEN_HOST_NULL", "La porta non può essere impostata se l'host è nullo" }, { "ER_INVALID_PORT", "Numero di porta non valido" }, { "ER_FRAG_FOR_GENERIC_URI", "Il frammento può essere impostato solo per un URI generico" }, { "ER_FRAG_WHEN_PATH_NULL", "Il frammento non può essere impostato se il percorso è nullo" }, { "ER_FRAG_INVALID_CHAR", "Il frammento contiene un carattere non valido" }, { "ER_PARSER_IN_USE", "Parser già in utilizzo" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Impossibile modificare {0} {1} durante l''''analisi" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Self-causation non consentito" }, { "ER_NO_USERINFO_IF_NO_HOST", "Userinfo non può essere specificato se l'host non è specificato" }, { "ER_NO_PORT_IF_NO_HOST", "La porta non può essere specificata se l'host non è specificato" }, { "ER_NO_QUERY_STRING_IN_PATH", "La stringa di interrogazione non può essere specificata nella stringa di interrogazione e percorso." }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Il frammento non può essere specificato sia nel percorso che nel frammento" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Impossibile inizializzare l'URI con i parametri vuoti" }, { "ER_METHOD_NOT_SUPPORTED", "Metodo non ancora supportato " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter correntemente non riavviabile" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader non si trova prima della richiesta startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Trasversale dell''''asse non supportato: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler creato con PrintWriter nullo." }, { "ER_SYSTEMID_UNKNOWN", "SystemId sconosciuto" }, { "ER_LOCATION_UNKNOWN", "Posizione di errore sconosciuta" }, { "ER_PREFIX_MUST_RESOLVE", "Il prefisso deve risolvere in uno namespace: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() non supportato in XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Il child dell'attributo non ha un documento proprietario." }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Il child dell'attributo non ha un elemento del documento proprietario." }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Attenzione: impossibile emettere testo prima dell'elemento del documento.  Operazione ignorata..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Impossibile avere più di una root in un DOM!" }, { "ER_ARG_LOCALNAME_NULL", "Argomento 'localName' nullo" }, { "ER_ARG_LOCALNAME_INVALID", "Localname in QNAME deve essere un NCName valido" }, { "ER_ARG_PREFIX_INVALID", "Prefix in QNAME deve essere un NCName valido" }, { "ER_NAME_CANT_START_WITH_COLON", "Il nome non può iniziare con un carattere di due punti" }, { "BAD_CODE", "Il parametro per createMessage fuori limite" }, { "FORMAT_FAILED", "Rilevata eccezione durante la chiamata messageFormat" }, { "line", "Riga #" }, { "column", "Colonna #" } };
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
/*  92:396 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("it", "IT"));
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_it
 * JD-Core Version:    0.7.0.1
 */