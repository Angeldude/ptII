/* Interface for actors that generates discrete events.

 Copyright (c) 1998 The Regents of the University of California.
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

package ptolemy.domains.ct.kernel;

import ptolemy.actor.Actor;

//////////////////////////////////////////////////////////////////////////
//// CTEventInterpreter
/**
Interface for CT actors that generates continuous signals (waveforms) from 
discrete events. Typical implementations of event interpreters are 
zero-order holds and impulses.

@author Jie Liu
@version $Id$
*/
public interface CTEventInterpreter extends Actor{

    ////////////////////////////////////////////////////////////////////////
    ////                         public methods                         ////

    /** Consume the discrete event that happens at the current time. If there
     *  is nosuch events, do nothing.
     */
    public void consumeCurrentEvents();

    /** FIXME: Consider a method that tells if there's unconsumed event 
     *  at current time.
     */

}
