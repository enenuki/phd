package org.apache.xerces.impl.xs;

import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class SubstitutionGroupHandler
{
  private static final XSElementDecl[] EMPTY_GROUP = new XSElementDecl[0];
  XSGrammarBucket fGrammarBucket;
  Hashtable fSubGroupsB = new Hashtable();
  private static final OneSubGroup[] EMPTY_VECTOR = new OneSubGroup[0];
  Hashtable fSubGroups = new Hashtable();
  
  public SubstitutionGroupHandler(XSGrammarBucket paramXSGrammarBucket)
  {
    this.fGrammarBucket = paramXSGrammarBucket;
  }
  
  public XSElementDecl getMatchingElemDecl(QName paramQName, XSElementDecl paramXSElementDecl)
  {
    if ((paramQName.localpart == paramXSElementDecl.fName) && (paramQName.uri == paramXSElementDecl.fTargetNamespace)) {
      return paramXSElementDecl;
    }
    if (paramXSElementDecl.fScope != 1) {
      return null;
    }
    if ((paramXSElementDecl.fBlock & 0x4) != 0) {
      return null;
    }
    SchemaGrammar localSchemaGrammar = this.fGrammarBucket.getGrammar(paramQName.uri);
    if (localSchemaGrammar == null) {
      return null;
    }
    XSElementDecl localXSElementDecl = localSchemaGrammar.getGlobalElementDecl(paramQName.localpart);
    if (localXSElementDecl == null) {
      return null;
    }
    if (substitutionGroupOK(localXSElementDecl, paramXSElementDecl, paramXSElementDecl.fBlock)) {
      return localXSElementDecl;
    }
    return null;
  }
  
  protected boolean substitutionGroupOK(XSElementDecl paramXSElementDecl1, XSElementDecl paramXSElementDecl2, short paramShort)
  {
    if (paramXSElementDecl1 == paramXSElementDecl2) {
      return true;
    }
    if ((paramShort & 0x4) != 0) {
      return false;
    }
    for (XSElementDecl localXSElementDecl = paramXSElementDecl1.fSubGroup; (localXSElementDecl != null) && (localXSElementDecl != paramXSElementDecl2); localXSElementDecl = localXSElementDecl.fSubGroup) {}
    if (localXSElementDecl == null) {
      return false;
    }
    return typeDerivationOK(paramXSElementDecl1.fType, paramXSElementDecl2.fType, paramShort);
  }
  
  private boolean typeDerivationOK(XSTypeDefinition paramXSTypeDefinition1, XSTypeDefinition paramXSTypeDefinition2, short paramShort)
  {
    int i = 0;
    int j = paramShort;
    Object localObject = paramXSTypeDefinition1;
    while ((localObject != paramXSTypeDefinition2) && (localObject != SchemaGrammar.fAnyType))
    {
      if (((XSTypeDefinition)localObject).getTypeCategory() == 15) {
        i = (short)(i | ((XSComplexTypeDecl)localObject).fDerivedBy);
      } else {
        i = (short)(i | 0x2);
      }
      localObject = ((XSTypeDefinition)localObject).getBaseType();
      if (localObject == null) {
        localObject = SchemaGrammar.fAnyType;
      }
      if (((XSTypeDefinition)localObject).getTypeCategory() == 15) {
        j = (short)(j | ((XSComplexTypeDecl)localObject).fBlock);
      }
    }
    if (localObject != paramXSTypeDefinition2)
    {
      if (paramXSTypeDefinition2.getTypeCategory() == 16)
      {
        XSSimpleTypeDefinition localXSSimpleTypeDefinition = (XSSimpleTypeDefinition)paramXSTypeDefinition2;
        if (localXSSimpleTypeDefinition.getVariety() == 3)
        {
          XSObjectList localXSObjectList = localXSSimpleTypeDefinition.getMemberTypes();
          int k = localXSObjectList.getLength();
          for (int m = 0; m < k; m++) {
            if (typeDerivationOK(paramXSTypeDefinition1, (XSTypeDefinition)localXSObjectList.item(m), paramShort)) {
              return true;
            }
          }
        }
      }
      return false;
    }
    return (i & j) == 0;
  }
  
  public boolean inSubstitutionGroup(XSElementDecl paramXSElementDecl1, XSElementDecl paramXSElementDecl2)
  {
    return substitutionGroupOK(paramXSElementDecl1, paramXSElementDecl2, paramXSElementDecl2.fBlock);
  }
  
  public void reset()
  {
    this.fSubGroupsB.clear();
    this.fSubGroups.clear();
  }
  
  public void addSubstitutionGroup(XSElementDecl[] paramArrayOfXSElementDecl)
  {
    for (int i = paramArrayOfXSElementDecl.length - 1; i >= 0; i--)
    {
      XSElementDecl localXSElementDecl2 = paramArrayOfXSElementDecl[i];
      XSElementDecl localXSElementDecl1 = localXSElementDecl2.fSubGroup;
      Vector localVector = (Vector)this.fSubGroupsB.get(localXSElementDecl1);
      if (localVector == null)
      {
        localVector = new Vector();
        this.fSubGroupsB.put(localXSElementDecl1, localVector);
      }
      localVector.addElement(localXSElementDecl2);
    }
  }
  
  public XSElementDecl[] getSubstitutionGroup(XSElementDecl paramXSElementDecl)
  {
    Object localObject1 = this.fSubGroups.get(paramXSElementDecl);
    if (localObject1 != null) {
      return (XSElementDecl[])localObject1;
    }
    if ((paramXSElementDecl.fBlock & 0x4) != 0)
    {
      this.fSubGroups.put(paramXSElementDecl, EMPTY_GROUP);
      return EMPTY_GROUP;
    }
    OneSubGroup[] arrayOfOneSubGroup = getSubGroupB(paramXSElementDecl, new OneSubGroup());
    int i = arrayOfOneSubGroup.length;
    int j = 0;
    Object localObject2 = new XSElementDecl[i];
    for (int k = 0; k < i; k++) {
      if ((paramXSElementDecl.fBlock & arrayOfOneSubGroup[k].dMethod) == 0) {
        localObject2[(j++)] = arrayOfOneSubGroup[k].sub;
      }
    }
    if (j < i)
    {
      XSElementDecl[] arrayOfXSElementDecl = new XSElementDecl[j];
      System.arraycopy(localObject2, 0, arrayOfXSElementDecl, 0, j);
      localObject2 = arrayOfXSElementDecl;
    }
    this.fSubGroups.put(paramXSElementDecl, localObject2);
    return localObject2;
  }
  
  private OneSubGroup[] getSubGroupB(XSElementDecl paramXSElementDecl, OneSubGroup paramOneSubGroup)
  {
    Object localObject1 = this.fSubGroupsB.get(paramXSElementDecl);
    if (localObject1 == null)
    {
      this.fSubGroupsB.put(paramXSElementDecl, EMPTY_VECTOR);
      return EMPTY_VECTOR;
    }
    if ((localObject1 instanceof OneSubGroup[])) {
      return (OneSubGroup[])localObject1;
    }
    Vector localVector1 = (Vector)localObject1;
    Vector localVector2 = new Vector();
    for (int k = localVector1.size() - 1; k >= 0; k--)
    {
      localObject2 = (XSElementDecl)localVector1.elementAt(k);
      if (getDBMethods(((XSElementDecl)localObject2).fType, paramXSElementDecl.fType, paramOneSubGroup))
      {
        int i = paramOneSubGroup.dMethod;
        int j = paramOneSubGroup.bMethod;
        localVector2.addElement(new OneSubGroup((XSElementDecl)localObject2, paramOneSubGroup.dMethod, paramOneSubGroup.bMethod));
        OneSubGroup[] arrayOfOneSubGroup = getSubGroupB((XSElementDecl)localObject2, paramOneSubGroup);
        for (int m = arrayOfOneSubGroup.length - 1; m >= 0; m--)
        {
          short s1 = (short)(i | arrayOfOneSubGroup[m].dMethod);
          short s2 = (short)(j | arrayOfOneSubGroup[m].bMethod);
          if ((s1 & s2) == 0) {
            localVector2.addElement(new OneSubGroup(arrayOfOneSubGroup[m].sub, s1, s2));
          }
        }
      }
    }
    Object localObject2 = new OneSubGroup[localVector2.size()];
    for (int n = localVector2.size() - 1; n >= 0; n--) {
      localObject2[n] = ((OneSubGroup)localVector2.elementAt(n));
    }
    this.fSubGroupsB.put(paramXSElementDecl, localObject2);
    return localObject2;
  }
  
  private boolean getDBMethods(XSTypeDefinition paramXSTypeDefinition1, XSTypeDefinition paramXSTypeDefinition2, OneSubGroup paramOneSubGroup)
  {
    short s1 = 0;
    short s2 = 0;
    while ((paramXSTypeDefinition1 != paramXSTypeDefinition2) && (paramXSTypeDefinition1 != SchemaGrammar.fAnyType))
    {
      if (paramXSTypeDefinition1.getTypeCategory() == 15) {
        s1 = (short)(s1 | ((XSComplexTypeDecl)paramXSTypeDefinition1).fDerivedBy);
      } else {
        s1 = (short)(s1 | 0x2);
      }
      paramXSTypeDefinition1 = paramXSTypeDefinition1.getBaseType();
      if (paramXSTypeDefinition1 == null) {
        paramXSTypeDefinition1 = SchemaGrammar.fAnyType;
      }
      if (paramXSTypeDefinition1.getTypeCategory() == 15) {
        s2 = (short)(s2 | ((XSComplexTypeDecl)paramXSTypeDefinition1).fBlock);
      }
    }
    if ((paramXSTypeDefinition1 != paramXSTypeDefinition2) || ((s1 & s2) != 0)) {
      return false;
    }
    paramOneSubGroup.dMethod = s1;
    paramOneSubGroup.bMethod = s2;
    return true;
  }
  
  private static final class OneSubGroup
  {
    XSElementDecl sub;
    short dMethod;
    short bMethod;
    
    OneSubGroup() {}
    
    OneSubGroup(XSElementDecl paramXSElementDecl, short paramShort1, short paramShort2)
    {
      this.sub = paramXSElementDecl;
      this.dMethod = paramShort1;
      this.bMethod = paramShort2;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.SubstitutionGroupHandler
 * JD-Core Version:    0.7.0.1
 */