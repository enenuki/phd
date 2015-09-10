package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;

public class CMBuilder
{
  private XSDeclarationPool fDeclPool = null;
  private static final XSEmptyCM fEmptyCM = new XSEmptyCM();
  private int fLeafCount;
  private int fParticleCount;
  private final CMNodeFactory fNodeFactory;
  
  public CMBuilder(CMNodeFactory paramCMNodeFactory)
  {
    this.fNodeFactory = paramCMNodeFactory;
  }
  
  public void setDeclPool(XSDeclarationPool paramXSDeclarationPool)
  {
    this.fDeclPool = paramXSDeclarationPool;
  }
  
  public XSCMValidator getContentModel(XSComplexTypeDecl paramXSComplexTypeDecl, boolean paramBoolean)
  {
    int i = paramXSComplexTypeDecl.getContentType();
    if ((i == 1) || (i == 0)) {
      return null;
    }
    XSParticleDecl localXSParticleDecl = (XSParticleDecl)paramXSComplexTypeDecl.getParticle();
    if (localXSParticleDecl == null) {
      return fEmptyCM;
    }
    Object localObject = null;
    if ((localXSParticleDecl.fType == 3) && (((XSModelGroupImpl)localXSParticleDecl.fValue).fCompositor == 103)) {
      localObject = createAllCM(localXSParticleDecl);
    } else {
      localObject = createDFACM(localXSParticleDecl, paramBoolean);
    }
    this.fNodeFactory.resetNodeCount();
    if (localObject == null) {
      localObject = fEmptyCM;
    }
    return localObject;
  }
  
  XSCMValidator createAllCM(XSParticleDecl paramXSParticleDecl)
  {
    if (paramXSParticleDecl.fMaxOccurs == 0) {
      return null;
    }
    XSModelGroupImpl localXSModelGroupImpl = (XSModelGroupImpl)paramXSParticleDecl.fValue;
    XSAllCM localXSAllCM = new XSAllCM(paramXSParticleDecl.fMinOccurs == 0, localXSModelGroupImpl.fParticleCount);
    for (int i = 0; i < localXSModelGroupImpl.fParticleCount; i++) {
      localXSAllCM.addElement((XSElementDecl)localXSModelGroupImpl.fParticles[i].fValue, localXSModelGroupImpl.fParticles[i].fMinOccurs == 0);
    }
    return localXSAllCM;
  }
  
  XSCMValidator createDFACM(XSParticleDecl paramXSParticleDecl, boolean paramBoolean)
  {
    this.fLeafCount = 0;
    this.fParticleCount = 0;
    CMNode localCMNode = useRepeatingLeafNodes(paramXSParticleDecl) ? buildCompactSyntaxTree(paramXSParticleDecl) : buildSyntaxTree(paramXSParticleDecl, paramBoolean);
    if (localCMNode == null) {
      return null;
    }
    return new XSDFACM(localCMNode, this.fLeafCount);
  }
  
  private CMNode buildSyntaxTree(XSParticleDecl paramXSParticleDecl, boolean paramBoolean)
  {
    int i = paramXSParticleDecl.fMaxOccurs;
    int j = paramXSParticleDecl.fMinOccurs;
    boolean bool = false;
    if (paramBoolean)
    {
      if (j > 1) {
        if ((i > j) || (paramXSParticleDecl.getMaxOccursUnbounded()))
        {
          j = 1;
          bool = true;
        }
        else
        {
          j = 2;
          bool = true;
        }
      }
      if (i > 1)
      {
        i = 2;
        bool = true;
      }
    }
    int k = paramXSParticleDecl.fType;
    Object localObject = null;
    if ((k == 2) || (k == 1))
    {
      localObject = this.fNodeFactory.getCMLeafNode(paramXSParticleDecl.fType, paramXSParticleDecl.fValue, this.fParticleCount++, this.fLeafCount++);
      localObject = expandContentModel((CMNode)localObject, j, i);
      if (localObject != null) {
        ((CMNode)localObject).setIsCompactUPAModel(bool);
      }
    }
    else if (k == 3)
    {
      XSModelGroupImpl localXSModelGroupImpl = (XSModelGroupImpl)paramXSParticleDecl.fValue;
      CMNode localCMNode = null;
      int m = 0;
      for (int n = 0; n < localXSModelGroupImpl.fParticleCount; n++)
      {
        localCMNode = buildSyntaxTree(localXSModelGroupImpl.fParticles[n], paramBoolean);
        if (localCMNode != null)
        {
          bool |= localCMNode.isCompactedForUPA();
          m++;
          if (localObject == null) {
            localObject = localCMNode;
          } else {
            localObject = this.fNodeFactory.getCMBinOpNode(localXSModelGroupImpl.fCompositor, (CMNode)localObject, localCMNode);
          }
        }
      }
      if (localObject != null)
      {
        if ((localXSModelGroupImpl.fCompositor == 101) && (m < localXSModelGroupImpl.fParticleCount)) {
          localObject = this.fNodeFactory.getCMUniOpNode(5, (CMNode)localObject);
        }
        localObject = expandContentModel((CMNode)localObject, j, i);
        ((CMNode)localObject).setIsCompactUPAModel(bool);
      }
    }
    return localObject;
  }
  
  private CMNode expandContentModel(CMNode paramCMNode, int paramInt1, int paramInt2)
  {
    CMNode localCMNode = null;
    if ((paramInt1 == 1) && (paramInt2 == 1))
    {
      localCMNode = paramCMNode;
    }
    else if ((paramInt1 == 0) && (paramInt2 == 1))
    {
      localCMNode = this.fNodeFactory.getCMUniOpNode(5, paramCMNode);
    }
    else if ((paramInt1 == 0) && (paramInt2 == -1))
    {
      localCMNode = this.fNodeFactory.getCMUniOpNode(4, paramCMNode);
    }
    else if ((paramInt1 == 1) && (paramInt2 == -1))
    {
      localCMNode = this.fNodeFactory.getCMUniOpNode(6, paramCMNode);
    }
    else if (paramInt2 == -1)
    {
      localCMNode = this.fNodeFactory.getCMUniOpNode(6, paramCMNode);
      localCMNode = this.fNodeFactory.getCMBinOpNode(102, multiNodes(paramCMNode, paramInt1 - 1, true), localCMNode);
    }
    else
    {
      if (paramInt1 > 0) {
        localCMNode = multiNodes(paramCMNode, paramInt1, false);
      }
      if (paramInt2 > paramInt1)
      {
        paramCMNode = this.fNodeFactory.getCMUniOpNode(5, paramCMNode);
        if (localCMNode == null) {
          localCMNode = multiNodes(paramCMNode, paramInt2 - paramInt1, false);
        } else {
          localCMNode = this.fNodeFactory.getCMBinOpNode(102, localCMNode, multiNodes(paramCMNode, paramInt2 - paramInt1, true));
        }
      }
    }
    return localCMNode;
  }
  
  private CMNode multiNodes(CMNode paramCMNode, int paramInt, boolean paramBoolean)
  {
    if (paramInt == 0) {
      return null;
    }
    if (paramInt == 1) {
      return paramBoolean ? copyNode(paramCMNode) : paramCMNode;
    }
    int i = paramInt / 2;
    return this.fNodeFactory.getCMBinOpNode(102, multiNodes(paramCMNode, i, paramBoolean), multiNodes(paramCMNode, paramInt - i, true));
  }
  
  private CMNode copyNode(CMNode paramCMNode)
  {
    int i = paramCMNode.type();
    Object localObject;
    if ((i == 101) || (i == 102))
    {
      localObject = (XSCMBinOp)paramCMNode;
      paramCMNode = this.fNodeFactory.getCMBinOpNode(i, copyNode(((XSCMBinOp)localObject).getLeft()), copyNode(((XSCMBinOp)localObject).getRight()));
    }
    else if ((i == 4) || (i == 6) || (i == 5))
    {
      localObject = (XSCMUniOp)paramCMNode;
      paramCMNode = this.fNodeFactory.getCMUniOpNode(i, copyNode(((XSCMUniOp)localObject).getChild()));
    }
    else if ((i == 1) || (i == 2))
    {
      localObject = (XSCMLeaf)paramCMNode;
      paramCMNode = this.fNodeFactory.getCMLeafNode(((CMNode)localObject).type(), ((XSCMLeaf)localObject).getLeaf(), ((XSCMLeaf)localObject).getParticleId(), this.fLeafCount++);
    }
    return paramCMNode;
  }
  
  private CMNode buildCompactSyntaxTree(XSParticleDecl paramXSParticleDecl)
  {
    int i = paramXSParticleDecl.fMaxOccurs;
    int j = paramXSParticleDecl.fMinOccurs;
    int k = paramXSParticleDecl.fType;
    Object localObject = null;
    if ((k == 2) || (k == 1)) {
      return buildCompactSyntaxTree2(paramXSParticleDecl, j, i);
    }
    if (k == 3)
    {
      XSModelGroupImpl localXSModelGroupImpl = (XSModelGroupImpl)paramXSParticleDecl.fValue;
      if ((localXSModelGroupImpl.fParticleCount == 1) && ((j != 1) || (i != 1))) {
        return buildCompactSyntaxTree2(localXSModelGroupImpl.fParticles[0], j, i);
      }
      CMNode localCMNode = null;
      int m = 0;
      for (int n = 0; n < localXSModelGroupImpl.fParticleCount; n++)
      {
        localCMNode = buildCompactSyntaxTree(localXSModelGroupImpl.fParticles[n]);
        if (localCMNode != null)
        {
          m++;
          if (localObject == null) {
            localObject = localCMNode;
          } else {
            localObject = this.fNodeFactory.getCMBinOpNode(localXSModelGroupImpl.fCompositor, (CMNode)localObject, localCMNode);
          }
        }
      }
      if ((localObject != null) && (localXSModelGroupImpl.fCompositor == 101) && (m < localXSModelGroupImpl.fParticleCount)) {
        localObject = this.fNodeFactory.getCMUniOpNode(5, (CMNode)localObject);
      }
    }
    return localObject;
  }
  
  private CMNode buildCompactSyntaxTree2(XSParticleDecl paramXSParticleDecl, int paramInt1, int paramInt2)
  {
    CMNode localCMNode = null;
    if ((paramInt1 == 1) && (paramInt2 == 1))
    {
      localCMNode = this.fNodeFactory.getCMLeafNode(paramXSParticleDecl.fType, paramXSParticleDecl.fValue, this.fParticleCount++, this.fLeafCount++);
    }
    else if ((paramInt1 == 0) && (paramInt2 == 1))
    {
      localCMNode = this.fNodeFactory.getCMLeafNode(paramXSParticleDecl.fType, paramXSParticleDecl.fValue, this.fParticleCount++, this.fLeafCount++);
      localCMNode = this.fNodeFactory.getCMUniOpNode(5, localCMNode);
    }
    else if ((paramInt1 == 0) && (paramInt2 == -1))
    {
      localCMNode = this.fNodeFactory.getCMLeafNode(paramXSParticleDecl.fType, paramXSParticleDecl.fValue, this.fParticleCount++, this.fLeafCount++);
      localCMNode = this.fNodeFactory.getCMUniOpNode(4, localCMNode);
    }
    else if ((paramInt1 == 1) && (paramInt2 == -1))
    {
      localCMNode = this.fNodeFactory.getCMLeafNode(paramXSParticleDecl.fType, paramXSParticleDecl.fValue, this.fParticleCount++, this.fLeafCount++);
      localCMNode = this.fNodeFactory.getCMUniOpNode(6, localCMNode);
    }
    else
    {
      localCMNode = this.fNodeFactory.getCMRepeatingLeafNode(paramXSParticleDecl.fType, paramXSParticleDecl.fValue, paramInt1, paramInt2, this.fParticleCount++, this.fLeafCount++);
      if (paramInt1 == 0) {
        localCMNode = this.fNodeFactory.getCMUniOpNode(4, localCMNode);
      } else {
        localCMNode = this.fNodeFactory.getCMUniOpNode(6, localCMNode);
      }
    }
    return localCMNode;
  }
  
  private boolean useRepeatingLeafNodes(XSParticleDecl paramXSParticleDecl)
  {
    int i = paramXSParticleDecl.fMaxOccurs;
    int j = paramXSParticleDecl.fMinOccurs;
    int k = paramXSParticleDecl.fType;
    if (k == 3)
    {
      XSModelGroupImpl localXSModelGroupImpl = (XSModelGroupImpl)paramXSParticleDecl.fValue;
      if ((j != 1) || (i != 1))
      {
        if (localXSModelGroupImpl.fParticleCount == 1)
        {
          XSParticleDecl localXSParticleDecl = localXSModelGroupImpl.fParticles[0];
          int n = localXSParticleDecl.fType;
          return ((n == 1) || (n == 2)) && (localXSParticleDecl.fMinOccurs == 1) && (localXSParticleDecl.fMaxOccurs == 1);
        }
        return localXSModelGroupImpl.fParticleCount == 0;
      }
      for (int m = 0; m < localXSModelGroupImpl.fParticleCount; m++) {
        if (!useRepeatingLeafNodes(localXSModelGroupImpl.fParticles[m])) {
          return false;
        }
      }
    }
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.models.CMBuilder
 * JD-Core Version:    0.7.0.1
 */