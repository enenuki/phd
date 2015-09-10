/*   1:    */ package org.apache.xml.res;
/*   2:    */ 
/*   3:    */ import java.util.ListResourceBundle;
/*   4:    */ import java.util.Locale;
/*   5:    */ import java.util.MissingResourceException;
/*   6:    */ import java.util.ResourceBundle;
/*   7:    */ 
/*   8:    */ public class XMLErrorResources_fr
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
/*  76:161 */     return new Object[][] { { "ER0000", "{0}" }, { "ER_FUNCTION_NOT_SUPPORTED", "Fonction non prise en charge !" }, { "ER_CANNOT_OVERWRITE_CAUSE", "Impossible de remplacer la cause" }, { "ER_NO_DEFAULT_IMPL", "Impossible de trouver une implémentation par défaut " }, { "ER_CHUNKEDINTARRAY_NOT_SUPPORTED", "ChunkedIntArray({0}) n''est pas pris en charge" }, { "ER_OFFSET_BIGGER_THAN_SLOT", "Décalage plus important que l'emplacement" }, { "ER_COROUTINE_NOT_AVAIL", "Coroutine non disponible, id={0}" }, { "ER_COROUTINE_CO_EXIT", "CoroutineManager a reçu une demande de co_exit()" }, { "ER_COJOINROUTINESET_FAILED", "Echec de co_joinCoroutineSet()" }, { "ER_COROUTINE_PARAM", "Erreur de paramètre de Coroutine ({0})" }, { "ER_PARSER_DOTERMINATE_ANSWERS", "\nRESULTAT INATTENDU : L''analyseur doTerminate répond {0}" }, { "ER_NO_PARSE_CALL_WHILE_PARSING", "parse ne peut être appelé lors de l'analyse" }, { "ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", "Erreur : itérateur typé de l''axe {0} non implémenté" }, { "ER_ITERATOR_AXIS_NOT_IMPLEMENTED", "Erreur : itérateur de l''axe {0} non implémenté " }, { "ER_ITERATOR_CLONE_NOT_SUPPORTED", "Clone de l'itérateur non pris en charge" }, { "ER_UNKNOWN_AXIS_TYPE", "Type transversal d''axe inconnu : {0}" }, { "ER_AXIS_NOT_SUPPORTED", "Traverseur d''axe non pris en charge : {0}" }, { "ER_NO_DTMIDS_AVAIL", "Aucun autre ID de DTM disponible" }, { "ER_NOT_SUPPORTED", "Non pris en charge : {0}" }, { "ER_NODE_NON_NULL", "Le noeud ne doit pas être vide pour getDTMHandleFromNode" }, { "ER_COULD_NOT_RESOLVE_NODE", "Impossible de convertir le noeud en pointeur" }, { "ER_STARTPARSE_WHILE_PARSING", "startParse ne peut être appelé pendant l'analyse" }, { "ER_STARTPARSE_NEEDS_SAXPARSER", "startParse requiert un SAXParser non vide" }, { "ER_COULD_NOT_INIT_PARSER", "impossible d'initialiser l'analyseur" }, { "ER_EXCEPTION_CREATING_POOL", "exception durant la création d'une instance du pool" }, { "ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE", "Le chemin d'accès contient une séquence d'échappement non valide" }, { "ER_SCHEME_REQUIRED", "Processus requis !" }, { "ER_NO_SCHEME_IN_URI", "Processus introuvable dans l''URI : {0}" }, { "ER_NO_SCHEME_INURI", "Processus introuvable dans l'URI" }, { "ER_PATH_INVALID_CHAR", "Le chemin contient un caractère non valide : {0}" }, { "ER_SCHEME_FROM_NULL_STRING", "Impossible de définir le processus à partir de la chaîne vide" }, { "ER_SCHEME_NOT_CONFORMANT", "Le processus n'est pas conforme." }, { "ER_HOST_ADDRESS_NOT_WELLFORMED", "L'hôte n'est pas une adresse bien formée" }, { "ER_PORT_WHEN_HOST_NULL", "Le port ne peut être défini quand l'hôte est vide" }, { "ER_INVALID_PORT", "Numéro de port non valide" }, { "ER_FRAG_FOR_GENERIC_URI", "Le fragment ne peut être défini que pour un URI générique" }, { "ER_FRAG_WHEN_PATH_NULL", "Le fragment ne peut être défini quand le chemin d'accès est vide" }, { "ER_FRAG_INVALID_CHAR", "Le fragment contient un caractère non valide" }, { "ER_PARSER_IN_USE", "L'analyseur est déjà utilisé" }, { "ER_CANNOT_CHANGE_WHILE_PARSING", "Impossible de modifier {0} {1} durant l''analyse" }, { "ER_SELF_CAUSATION_NOT_PERMITTED", "Auto-causalité interdite" }, { "ER_NO_USERINFO_IF_NO_HOST", "Userinfo ne peut être spécifié si l'hôte ne l'est pas" }, { "ER_NO_PORT_IF_NO_HOST", "Le port peut ne pas être spécifié si l'hôte n'est pas spécifié" }, { "ER_NO_QUERY_STRING_IN_PATH", "La chaîne de requête ne doit pas figurer dans un chemin et une chaîne de requête" }, { "ER_NO_FRAGMENT_STRING_IN_PATH", "Le fragment ne doit pas être indiqué à la fois dans le chemin et dans le fragment" }, { "ER_CANNOT_INIT_URI_EMPTY_PARMS", "Impossible d'initialiser l'URI avec des paramètres vides" }, { "ER_METHOD_NOT_SUPPORTED", "Cette méthode n'est pas encore prise en charge " }, { "ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", "IncrementalSAXSource_Filter ne peut redémarrer" }, { "ER_XMLRDR_NOT_BEFORE_STARTPARSE", "XMLReader ne figure pas avant la demande startParse" }, { "ER_AXIS_TRAVERSER_NOT_SUPPORTED", "Traverseur d''axe non pris en charge : {0}" }, { "ER_ERRORHANDLER_CREATED_WITH_NULL_PRINTWRITER", "ListingErrorHandler créé avec PrintWriter vide !" }, { "ER_SYSTEMID_UNKNOWN", "ID système inconnu" }, { "ER_LOCATION_UNKNOWN", "Emplacement inconnu de l'erreur" }, { "ER_PREFIX_MUST_RESOLVE", "Le préfixe doit se convertir en espace de noms : {0}" }, { "ER_CREATEDOCUMENT_NOT_SUPPORTED", "createDocument() non pris en charge dans XPathContext !" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT", "L'enfant de l'attribut ne possède pas de document propriétaire !" }, { "ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT", "Le contexte ne possède pas d'élément de document propriétaire !" }, { "ER_CANT_OUTPUT_TEXT_BEFORE_DOC", "Avertissement : impossible d'afficher du texte avant l'élément de document !  Traitement ignoré..." }, { "ER_CANT_HAVE_MORE_THAN_ONE_ROOT", "Un DOM ne peut posséder plusieurs racines !" }, { "ER_ARG_LOCALNAME_NULL", "L'argument 'localName' est vide" }, { "ER_ARG_LOCALNAME_INVALID", "Dans QNAME, le nom local doit être un nom NCName valide" }, { "ER_ARG_PREFIX_INVALID", "Dans QNAME, le préfixe doit être un nom NCName valide" }, { "ER_NAME_CANT_START_WITH_COLON", "Un nom ne peut commencer par le signe deux-points" }, { "BAD_CODE", "Le paramètre de createMessage se trouve hors limites" }, { "FORMAT_FAILED", "Exception soulevée lors de l'appel de messageFormat" }, { "line", "Ligne #" }, { "column", "Colonne #" } };
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
 * Qualified Name:     org.apache.xml.res.XMLErrorResources_fr
 * JD-Core Version:    0.7.0.1
 */