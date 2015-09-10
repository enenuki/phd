/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_de
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "Interner Fehler bei der Ausführung in ''{0}''" }, { "RUN_TIME_COPY_ERR", "Fehler bei der Ausführung von <xsl:copy>." }, { "DATA_CONVERSION_ERR", "Ungültige Konvertierung von ''{0}'' in ''{1}''." }, { "EXTERNAL_FUNC_ERR", "Die externe Funktion ''{0}'' wird nicht von XSLTC unterstützt." }, { "EQUALITY_EXPR_ERR", "Unbekannter Argumenttyp in Gleichheitsausdruck." }, { "INVALID_ARGUMENT_ERR", "Ungültiger Argumenttyp ''{0}'' in Aufruf von ''{1}''" }, { "FORMAT_NUMBER_ERR", "Es wird versucht, Nummer ''{0}'' mit Muster ''{1}'' zu formatieren." }, { "ITERATOR_CLONE_ERR", "Iterator ''{0}'' kann nicht geklont werden." }, { "AXIS_SUPPORT_ERR", "Iterator für Achse ''{0}'' wird nicht unterstützt." }, { "TYPED_AXIS_SUPPORT_ERR", "Iterator für Achse ''{0}'' mit Typangabe wird nicht unterstützt." }, { "STRAY_ATTRIBUTE_ERR", "Attribut ''{0}'' befindet sich nicht in einem Element." }, { "STRAY_NAMESPACE_ERR", "Namensbereichdeklaration ''{0}''=''{1}'' befindet sich nicht in einem Element." }, { "NAMESPACE_PREFIX_ERR", "Der Namensbereich für Präfix ''{0}'' wurde nicht deklariert." }, { "DOM_ADAPTER_INIT_ERR", "DOMAdapter wurde mit dem falschen Typ für das Dokumentobjektmodell der Quelle erstellt." }, { "PARSER_DTD_SUPPORT_ERR", "Der von Ihnen verwendete SAX-Parser bearbeitet keine DTD-Deklarationsereignisse." }, { "NAMESPACES_SUPPORT_ERR", "Der von Ihnen verwendete SAX-Parser unterstützt keine XML-Namensbereiche." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "Der URI-Verweis ''{0}'' konnte nicht aufgelöst werden." }, { "UNSUPPORTED_XSL_ERR", "Nicht unterstütztes XSL-Element ''{0}''" }, { "UNSUPPORTED_EXT_ERR", "Nicht erkannte XSLTC-Erweiterung ''{0}''" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "Das angegebene Translet ''{0}'' wurde mit einer neueren XSLTC-Version erstellt als die verwendete Version der XSLTC-Laufzeitsoftware. Sie müssen die Formatvorlage erneut kompilieren oder eine neuere XSLTC-Version zum Ausführen dieses Translets verwenden." }, { "INVALID_QNAME_ERR", "Ein Attribut, dessen Wert ein QName sein muss, hatte den Wert ''{0}''." }, { "INVALID_NCNAME_ERR", "Ein Attribut, dessen Wert ein NCName sein muss, hatte den Wert ''{0}''." }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "Die Verwendung der Erweiterungsfunktion ''{0}'' ist nicht zulässig, wenn für die Funktion zur sicheren Verarbeitung der Wert ''true'' festgelegt wurde." }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "Die Verwendung des Erweiterungselements ''{0}'' ist nicht zulässig, wenn für die Funktion zur sicheren Verarbeitung der Wert ''true'' festgelegt wurde." } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_de
 * JD-Core Version:    0.7.0.1
 */