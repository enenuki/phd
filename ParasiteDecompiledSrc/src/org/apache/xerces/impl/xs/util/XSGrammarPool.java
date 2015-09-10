package org.apache.xerces.impl.xs.util;

import java.util.ArrayList;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.XSModelImpl;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.util.XMLGrammarPoolImpl.Entry;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xs.XSModel;

public class XSGrammarPool
  extends XMLGrammarPoolImpl
{
  public XSModel toXSModel()
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < this.fGrammars.length; i++) {
      for (XMLGrammarPoolImpl.Entry localEntry = this.fGrammars[i]; localEntry != null; localEntry = localEntry.next) {
        if (localEntry.desc.getGrammarType().equals("http://www.w3.org/2001/XMLSchema")) {
          localArrayList.add(localEntry.grammar);
        }
      }
    }
    int j = localArrayList.size();
    if (j == 0) {
      return null;
    }
    SchemaGrammar[] arrayOfSchemaGrammar = (SchemaGrammar[])localArrayList.toArray(new SchemaGrammar[j]);
    return new XSModelImpl(arrayOfSchemaGrammar);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.XSGrammarPool
 * JD-Core Version:    0.7.0.1
 */