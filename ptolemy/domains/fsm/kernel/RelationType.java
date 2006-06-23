/* Static types for relation node.

 Copyright (c) 1998-2005 The Regents of the University of California.
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

 */
package ptolemy.domains.fsm.kernel;

//////////////////////////////////////////////////////////////////////////
////RelationType

/**
 A static class contains a list of types for relation node. There are six
 different types:
 <pre>
 INVALID
 TRUE
 FALSE
 EQUAL_INEQUAL
 LESS_THAN
 GREATER_THAN
 </pre>
 <p>
 For a leaf node evaluated as a boolean token, its relationType is
 decided by the boolean value of the result boolean token: TRUE for
 true and FALSE for false. 
 <p>
 For a relation node, (scalarLeft
 relationOperator scalarRight), the relationType depends on the
 relationOperator. If the relationOperator is '==' or '!=', the
 relationType can be EQUAL_INEQUAL indicating the two scalars equal or 
 not equal. The relation type is LESS_THAN to indicate that the left scalar 
 is less than the right one, and GREATER_THAN to
 indicate left scalar is bigger than the right one. For the other kinds of
 relation operators, the relationType is decided by the boolean value of 
 the evaluation result, i.e., TRUE for true and FALSE for false. 
 <p>
 The INVALID type is for a relation not evaluated yet.

 @author  Haiyang Zheng
 @version $Id$
 @since Ptolemy II 6.0
 @Pt.ProposedRating Yellow (hyzheng)
 @Pt.AcceptedRating Red (hyzheng)
*/
public final class RelationType {

    ///////////////////////////////////////////////////////////////////
    ////                         public variables                  ////

    /** Index for actors in the continuous part of the system, not sorted.
     */
    public final static int INVALID = 0;

    /** Index for actors in the continuous part of the system, not sorted.
     */
    public final static int TRUE = 1;

    /** Index for actors in the discrete part of the system,
     *  topologically ordered.
     */
    public final static int FALSE = 2;

    /** Index for dynamic actor schedule, in a topologically reverse order.
     */
    public final static int EQUAL_INEQUAL = 3;

    /** Index for the schedule of actors that implement
     *  the CTEventGenerator interface, topologically ordered.
     */
    public final static int LESS_THAN = 4;

    /** Index for output schedule, topologically ordered.
     */
    public final static int GREATER_THAN = 5;
}
