/* A schedule element.

 Copyright (c) 1998-2000 The Regents of the University of California.
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

@ProposedRating Red (vogel@eecs.berkeley.edu)
@AcceptedRating 
*/

package ptolemy.actor.sched;

import java.util.LinkedList;
import java.util.Iterator;

//////////////////////////////////////////////////////////////////////////
//// ScheduleElement
/**
This class is a schedule. This class is 
used together with Firing to represent a static schedule, which is 
used by domains that perform static scheduling. The schedule representation 
consists of an iteration count value and a sequence of schedule elements. In this
implementation, the sequence of schedule elements is represented by
a list.
<p>
<h1>Terminology</h1>
A schedule loop has the form (n,S<sub>1</sub>,S<sub>2</sub>...,S<sub>m</sub>)
where n is a positive integer called the iteration count, and S<sub>i</sub> 
is either another schedule loop or an actor. The schedule can be expressed as a sequence gS<sub>1</sub>S<sub>2</sub>...S<sub>m</sub> where 
S<sub>i</sub> is either an actor or a schedule loop.
<p>
<h1>Usage</h1>
The iteration count is set by the setIterationCount() method. If this
method is not invoked, a default value of one will be used.
The list methods should be used to add schedule elements. Only elements
of type Schedule may be added to the list. Otherwise an exception will
occur.
<p>
In this base class, the isFiring() method returns false, indicating that
this base class does not contain an actor. The subclass Firing contains
a reference to an actor, and should be used to represent an actor term
of a schedule loop.
<p>
As an example, suppose that we have an SDF graph containing actors
A, B, C, and D, with the schedule A(3BC)(2D).
The schedule can written as S = S<sub>1</sub>S<sub>2</sub>S<sub>3</sub>,
where S<sub>1</sub> = A, S<sub>2</sub> = (3BC), and S<sub>1</sub> = 2D.
To represent this schedule, S will be an instance of Schedule with
list elements S<sub>1</sub>, S<sub>2</sub>, S<sub>3</sub>, and
with an iteration count of 1. S<sub>1</sub> will be an instance of
Firing with a reference to actor A and an iteration count of 1.
S<sub>2</sub> will be an instance of Schedule with list elements
S<sub>2,1</sub>, S<sub>2,2</sub>, and an iteration count of 3.
S<sub>2,1</sub>, S<sub>2,2</sub> will each be an instance of Firing
with an iteration count of 1 and a reference to actors B and C,
respectively. S<sub>3</sub> will be an instance of Firing with
a reference to actor D and an iteration count of 2.

<h1>References</h1>
S. S. Bhattacharyya, P K. Murthy, and E. A. Lee,
Software Syntheses from Dataflow Graphs, Kluwer Academic Publishers, 1996.

@author Brian K. Vogel
@version $Id$
@see ptolemy.actor.sched.Firing
@see ptolemy.actor.sched.Schedule
*/

public abstract class ScheduleElement {

    /** Is this needed
     */
    //public ScheduleElement() {
    //super();
    //}

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** Return the actor invocation sequence of the schedule in the 
     *  form of a sequence of actors.
     *  Thus, next() will return an actor.
     *  
     * @return The iterator.
     */
    public abstract Iterator actorIterator();

    /** Return the actor invocation sequence of the schedule in the form
     *  of a sequence of firings.
     *  Thus, next() will return a firing.
     *  
     *  @return The iterator.
     */
    public abstract Iterator firingIterator();

    /** Return the iteration count for this schedule. This method
     *  returns the iteration count that was set by
     *  setIterationCount(). If setIterationCount() was never invoked,
     *  then a value of one is returned.
     *  @return The iteration count for this schedule.
     */
    public int getIterationCount() {
	return _iterationCount;
    }

    /** Set the iteration count for this schedule. The
     *  getIterationCount() method will return the value set
     *  by this method. If this method is not invoked, a default 
     *  value of one will be used.
     *  @param count The iteration count for this schedule.
     */
    public void setIterationCount(int count) {
	_iterationCount = count;
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////

    private int _iterationCount = 1;
}
