/* ASTPtBitwiseNode represent bitwise operator(&, |, ^) nodes in the parse tree

 Copyright (c) 1998-2003 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS.

                                        PT_COPYRIGHT_VERSION_2
                                        COPYRIGHTENDKEY

@ProposedRating Yellow (nsmyth@eecs.berkeley.edu)
@AcceptedRating Red (cxh@eecs.berkeley.edu)

Created : May 1998

*/

package ptolemy.data.expr;

import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.InternalErrorException;
import ptolemy.data.*;

//////////////////////////////////////////////////////////////////////////
//// ASTPtBitwiseNode
/**
The parse tree created from the expression string consists of a
hierarchy of node objects. This class represents bitwise operator(&, |, ^)
nodes in the parse tree.

@author Neil Smyth
@version $Id$
@since Ptolemy II 0.2
@see ptolemy.data.expr.ASTPtRootNode
@see ptolemy.data.expr.PtParser
@see ptolemy.data.Token
*/
public class ASTPtBitwiseNode extends ASTPtRootNode {


    public ASTPtBitwiseNode(int id) {
        super(id);
    }

    public ASTPtBitwiseNode(PtParser p, int id) {
        super(p, id);
    }

    /** Traverse this node with the given visitor.
     */
    public void visit(ParseTreeVisitor visitor)
            throws IllegalActionException {
        visitor.visitBitwiseNode(this);
    }

    /** Return true if this operation represents a boolean AND operation.
     */
    public boolean isBitwiseAnd() {
        return (_lexicalToken.kind == PtParserConstants.AND);
    }

    /** Return true if this operation represents a boolean OR operation.
     */
    public boolean isBitwiseOr() {
        return (_lexicalToken.kind == PtParserConstants.OR);
    }

    /** Return true if this operation represents a boolean XOR operation.
     */
    public boolean isBitwiseXor() {
        return (_lexicalToken.kind == PtParserConstants.XOR);
    }

    /** Return the token that represents the operation of this node.
     */
    public Token getOperator() {
        return _lexicalToken;
    }

    protected Token _lexicalToken = null;
}
