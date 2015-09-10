/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ import java.util.ListResourceBundle;
/*  4:   */ 
/*  5:   */ public class ErrorMessages_sk
/*  6:   */   extends ListResourceBundle
/*  7:   */ {
/*  8:   */   public Object[][] getContents()
/*  9:   */   {
/* 10:88 */     return new Object[][] { { "RUN_TIME_INTERNAL_ERR", "V ''{0}'' sa vyskytla interná runtime chyba" }, { "RUN_TIME_COPY_ERR", "Chyba času spustenia pri spúšťaní <xsl:copy>." }, { "DATA_CONVERSION_ERR", "Konverzia z ''{0}'' na ''{1}'' je neplatná." }, { "EXTERNAL_FUNC_ERR", "XLTC nepodporuje externú funkciu ''{0}''." }, { "EQUALITY_EXPR_ERR", "Neznámy typ argumentu je výrazom rovnosti." }, { "INVALID_ARGUMENT_ERR", "Vo volaní do ''{1}'' je neplatný typ argumentu ''{0}'' " }, { "FORMAT_NUMBER_ERR", "Prebieha pokus o formátovanie čísla ''{0}'' pomocou vzoru ''{1}''." }, { "ITERATOR_CLONE_ERR", "Iterátor ''{0}'' sa nedá klonovať." }, { "AXIS_SUPPORT_ERR", "Iterátor pre os ''{0}'' nie je podporovaný." }, { "TYPED_AXIS_SUPPORT_ERR", "Iterátor pre zadanú os ''{0}'' nie je podporovaný." }, { "STRAY_ATTRIBUTE_ERR", "Atribút ''{0}'' je mimo prvku." }, { "STRAY_NAMESPACE_ERR", "Deklarácia názvového priestoru ''{0}''=''{1}'' je mimo prvku." }, { "NAMESPACE_PREFIX_ERR", "Názvový priestor pre predponu ''{0}'' nebol deklarovaný." }, { "DOM_ADAPTER_INIT_ERR", "DOMAdapter bol vytvorený pomocou nesprávneho typu zdrojového DOM." }, { "PARSER_DTD_SUPPORT_ERR", "Analyzátor SAX, ktorý používate, nespracúva udalosti deklarácie DTD." }, { "NAMESPACES_SUPPORT_ERR", "Analyzátor SAX, ktorý používate, nemá podporu pre názvové priestory XML." }, { "CANT_RESOLVE_RELATIVE_URI_ERR", "Nebolo možné rozlíšiť odkaz na URI ''{0}''." }, { "UNSUPPORTED_XSL_ERR", "XSL prvok ''{0}'' nie je podporovaný" }, { "UNSUPPORTED_EXT_ERR", "XSLTC prípona ''{0}'' nebola rozpoznaná" }, { "UNKNOWN_TRANSLET_VERSION_ERR", "Špecifikovaný translet ''{0}'' bol vytvorený pomocou verzie XSLTC, ktorá je novšia ako verzia XSLTC runtime, ktorý sa používa.  Musíte prekompilovať definície štýlov (objekt stylesheet) alebo použiť na spustenie tohto transletu novšiu verziu nástroja XSLTC." }, { "INVALID_QNAME_ERR", "Atribút, ktorý musí mať hodnotu QName, mal hodnotu ''{0}''" }, { "INVALID_NCNAME_ERR", "Atribút, ktorý musí mať hodnotu NCName, mal hodnotu ''{0}''" }, { "UNALLOWED_EXTENSION_FUNCTION_ERR", "Používanie funkcie rozšírenia ''{0}'' nie je povolené, keď je funkcia bezpečného spracovania nastavená na hodnotu true." }, { "UNALLOWED_EXTENSION_ELEMENT_ERR", "Používanie prvku rozšírenia ''{0}'' nie je povolené, keď je funkcia bezpečného spracovania nastavená na hodnotu true." } };
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ErrorMessages_sk
 * JD-Core Version:    0.7.0.1
 */