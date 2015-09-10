package org.xml.sax.helpers;

import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class NamespaceSupport
{
  public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
  public static final String NSDECL = "http://www.w3.org/xmlns/2000/";
  private static final Enumeration EMPTY_ENUMERATION = new Vector().elements();
  private Context[] contexts;
  private Context currentContext;
  private int contextPos;
  private boolean namespaceDeclUris;
  
  public NamespaceSupport()
  {
    reset();
  }
  
  public void reset()
  {
    this.contexts = new Context[32];
    this.namespaceDeclUris = false;
    this.contextPos = 0;
    void tmp36_33 = new Context();
    this.currentContext = tmp36_33;
    this.contexts[this.contextPos] = tmp36_33;
    this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
  }
  
  public void pushContext()
  {
    int i = this.contexts.length;
    this.contextPos += 1;
    if (this.contextPos >= i)
    {
      Context[] arrayOfContext = new Context[i * 2];
      System.arraycopy(this.contexts, 0, arrayOfContext, 0, i);
      i *= 2;
      this.contexts = arrayOfContext;
    }
    this.currentContext = this.contexts[this.contextPos];
    if (this.currentContext == null)
    {
      void tmp88_85 = new Context();
      this.currentContext = tmp88_85;
      this.contexts[this.contextPos] = tmp88_85;
    }
    if (this.contextPos > 0) {
      this.currentContext.setParent(this.contexts[(this.contextPos - 1)]);
    }
  }
  
  public void popContext()
  {
    this.contexts[this.contextPos].clear();
    this.contextPos -= 1;
    if (this.contextPos < 0) {
      throw new EmptyStackException();
    }
    this.currentContext = this.contexts[this.contextPos];
  }
  
  public boolean declarePrefix(String paramString1, String paramString2)
  {
    if ((paramString1.equals("xml")) || (paramString1.equals("xmlns"))) {
      return false;
    }
    this.currentContext.declarePrefix(paramString1, paramString2);
    return true;
  }
  
  public String[] processName(String paramString, String[] paramArrayOfString, boolean paramBoolean)
  {
    String[] arrayOfString = this.currentContext.processName(paramString, paramBoolean);
    if (arrayOfString == null) {
      return null;
    }
    paramArrayOfString[0] = arrayOfString[0];
    paramArrayOfString[1] = arrayOfString[1];
    paramArrayOfString[2] = arrayOfString[2];
    return paramArrayOfString;
  }
  
  public String getURI(String paramString)
  {
    return this.currentContext.getURI(paramString);
  }
  
  public Enumeration getPrefixes()
  {
    return this.currentContext.getPrefixes();
  }
  
  public String getPrefix(String paramString)
  {
    return this.currentContext.getPrefix(paramString);
  }
  
  public Enumeration getPrefixes(String paramString)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = getPrefixes();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (paramString.equals(getURI(str))) {
        localVector.addElement(str);
      }
    }
    return localVector.elements();
  }
  
  public Enumeration getDeclaredPrefixes()
  {
    return this.currentContext.getDeclaredPrefixes();
  }
  
  public void setNamespaceDeclUris(boolean paramBoolean)
  {
    if (this.contextPos != 0) {
      throw new IllegalStateException();
    }
    if (paramBoolean == this.namespaceDeclUris) {
      return;
    }
    this.namespaceDeclUris = paramBoolean;
    if (paramBoolean)
    {
      this.currentContext.declarePrefix("xmlns", "http://www.w3.org/xmlns/2000/");
    }
    else
    {
      void tmp64_61 = new Context();
      this.currentContext = tmp64_61;
      this.contexts[this.contextPos] = tmp64_61;
      this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
    }
  }
  
  public boolean isNamespaceDeclUris()
  {
    return this.namespaceDeclUris;
  }
  
  final class Context
  {
    Hashtable prefixTable;
    Hashtable uriTable;
    Hashtable elementNameTable;
    Hashtable attributeNameTable;
    String defaultNS = null;
    private Vector declarations = null;
    private boolean declSeen = false;
    private Context parent = null;
    
    Context()
    {
      copyTables();
    }
    
    void setParent(Context paramContext)
    {
      this.parent = paramContext;
      this.declarations = null;
      this.prefixTable = paramContext.prefixTable;
      this.uriTable = paramContext.uriTable;
      this.elementNameTable = paramContext.elementNameTable;
      this.attributeNameTable = paramContext.attributeNameTable;
      this.defaultNS = paramContext.defaultNS;
      this.declSeen = false;
    }
    
    void clear()
    {
      this.parent = null;
      this.prefixTable = null;
      this.uriTable = null;
      this.elementNameTable = null;
      this.attributeNameTable = null;
      this.defaultNS = null;
    }
    
    void declarePrefix(String paramString1, String paramString2)
    {
      if (!this.declSeen) {
        copyTables();
      }
      if (this.declarations == null) {
        this.declarations = new Vector();
      }
      paramString1 = paramString1.intern();
      paramString2 = paramString2.intern();
      if ("".equals(paramString1))
      {
        if ("".equals(paramString2)) {
          this.defaultNS = null;
        } else {
          this.defaultNS = paramString2;
        }
      }
      else
      {
        this.prefixTable.put(paramString1, paramString2);
        this.uriTable.put(paramString2, paramString1);
      }
      this.declarations.addElement(paramString1);
    }
    
    String[] processName(String paramString, boolean paramBoolean)
    {
      Hashtable localHashtable;
      if (paramBoolean) {
        localHashtable = this.attributeNameTable;
      } else {
        localHashtable = this.elementNameTable;
      }
      String[] arrayOfString = (String[])localHashtable.get(paramString);
      if (arrayOfString != null) {
        return arrayOfString;
      }
      arrayOfString = new String[3];
      arrayOfString[2] = paramString.intern();
      int i = paramString.indexOf(':');
      if (i == -1)
      {
        if (paramBoolean)
        {
          if ((paramString == "xmlns") && (NamespaceSupport.this.namespaceDeclUris)) {
            arrayOfString[0] = "http://www.w3.org/xmlns/2000/";
          } else {
            arrayOfString[0] = "";
          }
        }
        else if (this.defaultNS == null) {
          arrayOfString[0] = "";
        } else {
          arrayOfString[0] = this.defaultNS;
        }
        arrayOfString[1] = arrayOfString[2];
      }
      else
      {
        String str1 = paramString.substring(0, i);
        String str2 = paramString.substring(i + 1);
        String str3;
        if ("".equals(str1)) {
          str3 = this.defaultNS;
        } else {
          str3 = (String)this.prefixTable.get(str1);
        }
        if ((str3 == null) || ((!paramBoolean) && ("xmlns".equals(str1)))) {
          return null;
        }
        arrayOfString[0] = str3;
        arrayOfString[1] = str2.intern();
      }
      localHashtable.put(arrayOfString[2], arrayOfString);
      return arrayOfString;
    }
    
    String getURI(String paramString)
    {
      if ("".equals(paramString)) {
        return this.defaultNS;
      }
      if (this.prefixTable == null) {
        return null;
      }
      return (String)this.prefixTable.get(paramString);
    }
    
    String getPrefix(String paramString)
    {
      if (this.uriTable == null) {
        return null;
      }
      return (String)this.uriTable.get(paramString);
    }
    
    Enumeration getDeclaredPrefixes()
    {
      if (this.declarations == null) {
        return NamespaceSupport.EMPTY_ENUMERATION;
      }
      return this.declarations.elements();
    }
    
    Enumeration getPrefixes()
    {
      if (this.prefixTable == null) {
        return NamespaceSupport.EMPTY_ENUMERATION;
      }
      return this.prefixTable.keys();
    }
    
    private void copyTables()
    {
      if (this.prefixTable != null) {
        this.prefixTable = ((Hashtable)this.prefixTable.clone());
      } else {
        this.prefixTable = new Hashtable();
      }
      if (this.uriTable != null) {
        this.uriTable = ((Hashtable)this.uriTable.clone());
      } else {
        this.uriTable = new Hashtable();
      }
      this.elementNameTable = new Hashtable();
      this.attributeNameTable = new Hashtable();
      this.declSeen = true;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.helpers.NamespaceSupport
 * JD-Core Version:    0.7.0.1
 */