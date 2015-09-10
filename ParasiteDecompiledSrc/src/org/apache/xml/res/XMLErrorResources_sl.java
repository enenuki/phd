/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_sl
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
/*  76:162 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Funkcija ni podprta!" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Vzroka ni mogoče prepisati" }, { "ER_NO_DEFAULT_IMPL", "Privzete implementacije ni mogoče najti " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) trenutno ni podprt" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Odmik večji od reže" }, { "ER_COROUTINE_NOT_AVAIL", "Sorutina ni na voljo, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager je prejel zahtevo co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "co_joinCoroutineSet() je spodletela" }, { "ER_COROUTINE_PARAM", "Napaka parametra sorutine ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nNEPRIČAKOVANO: Odgovor razčlenjevalnika doTerminate je {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "med razčlenjevanjem klic razčlenitve ni možen" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Napaka: določen iterator za os {0} ni implementiran" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Napaka: iterator za os {0} ni implementiran " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Klon iteratorja ni podprt" }, { "ER_UNKNOWN_AXIS_TYPE", "Neznan prečni tip osi: {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Prečnik osi ni podprt: {0}" }, { "ER_NO_DTMIDS_AVAIL", "Na voljo ni več DTM ID-jev" }, { "ER_NOT_SUPPORTED", "Ni podprto: {0}" }, { "ER_NODE_NON_NULL", "Vozlišče ne sme biti NULL za getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "Ne morem razrešiti vozlišča v obravnavo" }, { "ER_STARTPARSE_WHILE_PARSING", "Med razčlenjevanjem klic startParse ni mogoč" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse potrebuje ne-NULL SAXParser" }, { "ER_COULD_NOT_INIT_PARSER", "parserja ni mogoče inicializirati z" }, { "ER_EXCEPTION_CREATING_POOL", "izjema pri ustvarjanju novega primerka za zalogo" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Pot vsebuje neveljavno zaporedje za izhod" }, { "ER_SCHEME_REQUIRED", "Zahtevana je shema!" }, { "ER_NO_SCHEME_IN_URI", "Ne najdem sheme v URI: {0}" }, { "ER_NO_SCHEME_INURI", "Ne najdem sheme v URI" }, { "ER_PATH_INVALID_CHAR", "Pot vsebuje neveljaven znak: {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Ne morem nastaviti sheme iz niza NULL" }, { "ER_SCHEME_NOT_CONFORMANT", "Shema ni skladna." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "Naslov gostitelja ni pravilno oblikovan" }, { "ER_PORT_WHEN_HOST_NULL", "Ko je gostitelj NULL, nastavitev vrat ni mogoča" }, { "ER_INVALID_PORT", "Neveljavna številka vrat" }, { "ER_FRAG_FOR_GENERIC_URI", "Fragment je lahko nastavljen samo za splošni URI" }, { "ER_FRAG_WHEN_PATH_NULL", "Ko je pot NULL, nastavitev fragmenta ni mogoča" }, { "ER_FRAG_INVALID_CHAR", "Fragment vsebuje neveljaven znak" }, { "ER_PARSER_IN_USE", "Razčlenjevalnik je že v uporabi" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Med razčlenjevanjem ni mogoče spremeniti {0} {1}" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Samopovzročitev ni dovoljena" }, { "ER_NO_USERINFO_IF_NO_HOST", "Informacije o uporabniku ne morejo biti navedene, če ni naveden gostitelj" }, { "ER_NO_PORT_IF_NO_HOST", "Vrata ne morejo biti navedena, če ni naveden gostitelj" }, { "ER_NO_QUERY_STRING_IN_PATH", "Poizvedbeni niz ne more biti naveden v nizu poti in poizvedbenem nizu" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Fragment ne more biti hkrati naveden v poti in v fragmentu" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Ne morem inicializirat URI-ja s praznimi parametri" }, { "ER_METHOD_NOT_SUPPORTED", "Metoda ni več podprta " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter v tem trenutku ni mogoče ponovno zagnati" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader ne pred zahtevo za startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Prečnik osi ni podprt: {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler ustvarjen s PrintWriter NULL!" }, { "ER_SYSTEMID_UNKNOWN", "Neznan sistemski ID" }, { "ER_LOCATION_UNKNOWN", "Mesto napake neznano" }, { "ER_PREFIX_MUST_RESOLVE", "Predpona se mora razrešiti v imenski prostor: {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() ni podprt v XPathContext!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "Podrejeni predmet atributa nima lastniškega dokumenta!" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Podrejeni predmet atributa nima elementa lastniškega dokumenta!" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Opozorilo: besedila ne morem prikazati pred elementom dokumenta!  Ignoriram..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Na DOM-u ne more biti več kot en koren!" }, { "ER_ARG_LOCALNAME_NULL", "Argument 'lokalno ime' je NULL" }, { "ER_ARG_LOCALNAME_INVALID", "Lokalno ime v QNAME bi moralo biti veljavno NCIme" }, { "ER_ARG_PREFIX_INVALID", "Predpona v QNAME bi morala biti valjavno NCIme" }, { "ER_NAME_CANT_START_WITH_COLON", "Ime se ne more začeti z dvopičjem" }, { "BAD_CODE", "Parameter za ustvariSporočilo presega meje" }, { "FORMAT_FAILED", "Med klicem messageFormat naletel na izjemo" }, { "line", "Vrstica #" }, { "column", "Stolpec #" } };
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static final XMLErrorResources loadResourceBundle(String className)
/*  80:    */     throws MissingResourceException
/*  81:    */   {
/*  82:380 */     Locale locale = Locale.getDefault();
/*  83:381 */     String suffix = getResourceSuffix(locale);
/*  84:    */     try
/*  85:    */     {
/*  86:387 */       return (XMLErrorResources)ResourceBundle.getBundle(className + suffix, locale);
/*  87:    */     }
/*  88:    */     catch (MissingResourceException e)
/*  89:    */     {
/*  90:    */       try
/*  91:    */       {
/*  92:397 */         return (XMLErrorResources)ResourceBundle.getBundle(className, new Locale("sl", "SL"));
/*  93:    */       }
/*  94:    */       catch (MissingResourceException e2)
/*  95:    */       {
/*  96:405 */         throw new MissingResourceException("Could not load any resource bundles.", className, "");
/*  97:    */       }
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static final String getResourceSuffix(Locale locale)
/* 102:    */   {
/* 103:422 */     String suffix = "_" + locale.getLanguage();
/* 104:423 */     String country = locale.getCountry();
/* 105:425 */     if (country.equals("TW")) {
/* 106:426 */       suffix = suffix + "_" + country;
/* 107:    */     }
/* 108:428 */     return suffix;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_sl
 * JD-Core Version:    0.7.0.1
 */