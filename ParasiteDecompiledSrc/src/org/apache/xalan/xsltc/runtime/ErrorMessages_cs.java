/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_cs
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "Vnitřní běhová chyba ve třídě ''{0}''" }, { "RUN_TIME_COPY_ERR", "Vnitřní běhová chyba při provádění funkce <xsl:copy>." }, { "DATA_CONVERSION_ERR", "Neplatná konverze z typu ''{0}'' na typ ''{1}''. " }, { "EXTERNAL_FUNC_ERR", "Externí funkce ''{0}'' není produktem XSLTC podporována. " }, { "EQUALITY_EXPR_ERR", "Neznámý typ argumentu ve výrazu rovnosti." }, { "INVALID_ARGUMENT_ERR", "Neplatný argument typu ''{0}'' ve volání funkce ''{1}''" }, { "FORMAT_NUMBER_ERR", "Pokus o zformátování čísla ''{0}'' s použitím vzorku ''{1}''." }, { "ITERATOR_CLONE_ERR", "Nelze klonovat iterátor ''{0}''." }, { "AXIS_SUPPORT_ERR", "Iterátor pro osu ''{0}'' není podporován." }, { "TYPED_AXIS_SUPPORT_ERR", "Iterátor pro typizovanou osu ''{0}'' není podporován." }, { "STRAY_ATTRIBUTE_ERR", "Atribut ''{0}'' se nachází vně prvku." }, { "STRAY_NAMESPACE_ERR", "Deklarace oboru názvů ''{0}''=''{1}'' se nachází vně prvku." }, { "NAMESPACE_PREFIX_ERR", "Obor názvů pro předponu ''{0}'' nebyl deklarován." }, { "DOM_ADAPTER_INIT_ERR", "DOMAdapter byl vytvořen s použitím chybného typu zdroje DOM." }, { "PARSER_DTD_SUPPORT_ERR", "Použitý analyzátor SAX nemůže manipulovat s deklaračními událostmi DTD." }, { "NAMESPACES_SUPPORT_ERR", "Použitý analyzátor SAX nemůže podporovat obory názvů pro XML." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "Nelze přeložit odkaz na URI ''{0}''." }, { "UNSUPPORTED_XSL_ERR", "Nepodporovaný prvek XSL ''{0}''" }, { "UNSUPPORTED_EXT_ERR", "Nerozpoznaná přípona XSLTC ''{0}''" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "Určený translet ''{0}'' byl vytvořen pomocí verze prostředí XSLTC, která je novější než verze používaného běhového prostředí XSLTC. Předlohu se styly je třeba znovu zkompilovat nebo tento translet spustit v novější verzi prostředí XSLTC." }, { "INVALID_QNAME_ERR", "Atribut, jehož hodnotou musí být jméno QName, má hodnotu ''{0}''. " }, { "INVALID_NCNAME_ERR", "Atribut, jehož hodnotou musí být jméno NCName, má hodnotu ''{0}''. " }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "Je-li funkce zabezpečeného zpracování nastavena na hodnotu true, není povoleno použití rozšiřující funkce ''{0}''. " }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "Je-li funkce zabezpečeného zpracování nastavena na hodnotu true, není povoleno použití rozšiřujícího prvku ''{0}''. " } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_cs
 * JD-Core Version:    0.7.0.1
 */