/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_sk
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funkcia nie je podporovaná!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Nie je možné prepísať príčinu" }, { "ER_NO_DEFAULT_IMPL", "Nebola nájdená žiadna predvolená implementácia " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) nie je momentálne podporovaný" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Offset väčší, než zásuvka" }, { "ER_COROUTINE_NOT_AVAIL", "Ko-rutina nie je dostupná, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager obdržal požiadavku co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "zlyhal co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Chyba parametra korutiny ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nNEOČAKÁVANÉ: Analyzátor doTerminate odpovedá {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "syntaktický analyzátor nemôže byť volaný počas vykonávania analýzy" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Chyba: napísaný iterátor pre os {0} nie je implementovaný" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Chyba: iterátor pre os {0} nie je implementovaný " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Klon iterátora nie je podporovaný" }, { "ER_UNKNOWN_AXIS_TYPE", "Neznámy typ pretínania osí: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Pretínanie osí nie je podporované: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Žiadne ďalšie DTM ID nie sú dostupné" }, { "ER_NOT_SUPPORTED", "Nie je podporované: {0}" }, { "ER_NODE_NON_NULL", "Pre getDTMHandleFromNode musí byť uzol nenulový" }, { "ER_COULD_NOT_RESOLVE_NODE", "Nebolo možné určiť uzol na spracovanie" }, { "ER_STARTPARSE_WHILE_PARSING", "startParse nemôže byť volaný počas vykonávania analýzy" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse potrebuje nenulový SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "Nebolo možné inicializovať syntaktický analyzátor pomocou" }, { "ER_EXCEPTION_CREATING_POOL", "výnimka vytvárania novej inštancie oblasti" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Cesta obsahuje neplatnú únikovú sekvenciu" }, { "ER_SCHEME_REQUIRED", "Je požadovaná schéma!" }, { "ER_NO_SCHEME_IN_URI", "V URI sa nenašla žiadna schéma: {0}" }, { "ER_NO_SCHEME_INURI", "V URI nebola nájdená žiadna schéma" }, { "ER_PATH_INVALID_CHAR", "Cesta obsahuje neplatný znak: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Nie je možné stanoviť schému z nulového reťazca" }, { "ER_SCHEME_NOT_CONFORMANT", "Nezhodná schéma." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Hostiteľ nie je správne formátovaná adresa" }, { "ER_PORT_WHEN_HOST_NULL", "Nemôže byť stanovený port, ak je hostiteľ nulový" }, { "ER_INVALID_PORT", "Neplatné číslo portu" }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment môže byť stanovený len pre všeobecné URI" }, { "ER_FRAG_WHEN_PATH_NULL", "Ak je cesta nulová, nemôže byť stanovený fragment" }, { "ER_FRAG_INVALID_CHAR", "Fragment obsahuje neplatný znak" }, { "ER_PARSER_IN_USE", "Syntaktický analyzátor je už používaný" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Nie je možné zmeniť {0} {1} počas vykonávania analýzy" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Samozapríčinenie nie je povolené" }, { "ER_NO_USERINFO_IF_NO_HOST", "Ak nebol zadaný hostiteľ, možno nebolo zadané userinfo" }, { "ER_NO_PORT_IF_NO_HOST", "Ak nebol zadaný hostiteľ, možno nebol zadaný port" }, { "ER_NO_QUERY_STRING_IN_PATH", "Reťazec dotazu nemôže byť zadaný v ceste a reťazci dotazu" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment nemôže byť zadaný v ceste, ani vo fragmente" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Nie je možné inicializovať URI s prázdnymi parametrami" }, { "ER_METHOD_NOT_SUPPORTED", "Metóda ešte nie je podporovaná " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter nie je momentálne reštartovateľný" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader nepredchádza požiadavke na startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Pretínanie osí nie je podporované: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler vytvorený s nulovým PrintWriter!" }, { "ER_SYSTEMID_UNKNOWN", "Neznáme SystemId" }, { "ER_LOCATION_UNKNOWN", "Neznáme miesto výskytu chyby" }, { "ER_PREFIX_MUST_RESOLVE", "Predpona sa musí rozlíšiť do názvového priestoru: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() nie je podporované XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Potomok atribútu nemá dokument vlastníka!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Potomok atribútu nemá súčasť dokumentu vlastníka!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Upozornenie: nemožno vypustiť text pred elementom dokumentu!  Ignorovanie..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Nie je možné mať viac, než jeden koreň DOM!" }, { "ER_ARG_LOCALNAME_NULL", "Argument 'localName' je nulový" }, { "ER_ARG_LOCALNAME_INVALID", "Lokálny názov v QNAME by mal byť platným NCName" }, { "ER_ARG_PREFIX_INVALID", "Predpona v QNAME by mala byť platným NCName" }, { "ER_NAME_CANT_START_WITH_COLON", "Názov sa nemôže začínať dvojbodkou." }, { "BAD_CODE", "Parameter na createMessage bol mimo ohraničenia" }, { "FORMAT_FAILED", "Výnimka počas volania messageFormat" }, { "line", "Riadok #" }, { "column", "Stĺpec #" } };
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_sk
 * JD-Core Version:    0.7.0.1
 */