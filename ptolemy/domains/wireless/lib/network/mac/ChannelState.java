/* An actor that maintains the channel state based on both the result of carrier sense
 * and the reservation (NAV).

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

@ProposedRating Yellow (eal@eecs.berkeley.edu)
@AcceptedRating Red (pjb2e@eecs.berkeley.edu)
*/

package ptolemy.domains.wireless.lib.network.mac;

import java.util.LinkedList;
import java.util.List;

import ptolemy.actor.Director;
import ptolemy.actor.TypedIOPort;
import ptolemy.data.IntToken;
import ptolemy.data.RecordToken;
import ptolemy.data.Token;
import ptolemy.data.type.BaseType;
import ptolemy.data.type.RecordType;
import ptolemy.data.type.Type;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.StringAttribute;
import ptolemy.kernel.util.Workspace;

//////////////////////////////////////////////////////////////////////////
//// ChannelState

/** 
This actor updates the channel state based on the information from PHY
and NAV (Natwork Allocation Vector). To speed up simulation, slot events
in 802.11 are not generated here.


@author Yang Zhao
@version $ $
*/
public class ChannelState extends MACActorBase {

    /** Construct an actor with the specified name and container.
     *  The container argument must not be null, or a
     *  NullPointerException will be thrown.
     *  If the name argument is null, then the name is set to the empty string.
     *  This constructor write-synchronizes on the workspace.
     *  @param container The container.
     *  @param name The name of the actor.
     *  @exception IllegalActionException If the container is incompatible
     *   with this actor.
     *  @exception NameDuplicationException If the name coincides with
     *   an actor already in the container.
     */
    public ChannelState(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);

        // Create and configure the ports.       
        channelStatus = new TypedIOPort(this, "channelStatus", true, false);
        //set the port to be a south port if it hasn't been configured.
        StringAttribute cardinal = (StringAttribute)
                channelStatus.getAttribute("_cardinal");
        if (cardinal == null) {
            StringAttribute thisCardinal =  
                    new StringAttribute(channelStatus, "_cardinal");
            thisCardinal.setExpression("SOUTH");
        }
        
        updateNav = new TypedIOPort(this, "updateNav", true, false);
        
        IfsControl = new TypedIOPort(this, "IfsControl", true, false);
        
        fromValidateMpdu = new TypedIOPort(this, "fromValidateMpdu", true, false);
        
        toTransmission = new TypedIOPort(this, "toTransmission", false, true);

        channelStatus.setTypeEquals(BaseType.GENERAL);
        updateNav.setTypeEquals(BaseType.GENERAL);
        fromValidateMpdu.setTypeEquals(BaseType.GENERAL);
        toTransmission.setTypeEquals(BaseType.GENERAL);     
    }
    
    ///////////////////////////////////////////////////////////////////
    ////                         parameters                        ////

    /** The input port for channel status message from the 
     *  physical layer.  This has undeclared type.
     */
    public TypedIOPort channelStatus;
    
    /** The input port for update NAV message. 
     */
    public TypedIOPort updateNav;
    
    /** The input port for setting up inter frame time..
     */
    public TypedIOPort IfsControl;
    
    /** The input port for NAV change message from the
     *  protocol control block.
     */
    public TypedIOPort fromValidateMpdu;
    
    /** The output port that produces messages that
     *  indicate the channel status.
     */
    public TypedIOPort toTransmission;
    
    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////
    /** Clone the object into the specified workspace. The new object is
     *  <i>not</i> added to the directory of that workspace (you must do this
     *  yourself if you want it there).
     *  The result is an object with no container.
     *  @param workspace The workspace for the cloned object.
     *  @exception CloneNotSupportedException Not thrown in this base class
     *  @return The new Attribute.
     */
    public Object clone(Workspace workspace)
            throws CloneNotSupportedException {
        ChannelState newObject = (ChannelState)super.clone(workspace);
        /*
        String[] labels = {"type", "content"};
        Type[] types = {BaseType.INT, BaseType.GENERAL};
        RecordType recordType = new RecordType(labels, types);
        
        newObject.channelStatus.setTypeAtMost(recordType);
        newObject.updateNav.setTypeAtMost(recordType);
        newObject.fromValidateMpdu.setTypeAtMost(recordType);
        //no need to set type constraint for toTransmission since
        //it has an absolute constraint.
         *
         */
        return newObject;
    }

    /** FIXME: add doc here.
     *  @exception IllegalActionException If an error occurs reading
     *   or writing inputs or outputs.
     */
    public void fire() throws IllegalActionException {
        super.fire();
        Director director = getDirector();
        double currentTime = director.getCurrentTime();
	int kind=whoTimeout();
        
        //THE SDL specification has don-deterministic
        //transition.However the order does not matter,
	// so We will pick an particular order.
        if (IfsControl.hasToken(0)) {
            _inputMessage = (RecordToken)IfsControl.get(0);
            _messageType = ((IntToken)_inputMessage.
                    get("kind")).intValue();

            if (_messageType == (UseDifs)) {
                _dIfs = _dDIfs - _aRxTxTurnaroundTime;
            } else if (_messageType == UseEifs){
                _dIfs = _dEIfs - _aRxTxTurnaroundTime;
            }
            int tRxEnd = ((IntToken) _inputMessage.get("tRxEnd"))
                    .intValue();

	    _IfsTimer=setTimer(IfsTimeOut, currentTime + tRxEnd + _dIfs*1e-6);

        } else if (channelStatus.hasToken(0)) {
            _inputMessage = (RecordToken) channelStatus.get(0);
        } else if (updateNav.hasToken(0)){
            _inputMessage = (RecordToken) updateNav.get(0);
        } else if (fromValidateMpdu.hasToken(0)){
            _inputMessage = (RecordToken) fromValidateMpdu.get(0);
        } 
        if(_inputMessage != null) {
            _messageType = ((IntToken)
                    _inputMessage.get("kind")).intValue();

        } 
        
        switch (_state) {
            case Cs_noNav:
                switch(_messageType) {
                    case Idle:
		    _IfsTimer=setTimer(IfsTimeOut,currentTime + _dIfs*1e-6);
                    _state = Wait_Ifs;
                    break;
                    
                    case SetNav:
                        if(_setNav()) {
                            _state = Cs_Nav;
                        }
                    break;     
                }
            break;
            
            case Wait_Ifs:
                if (kind == IfsTimeOut) {
  		    _changeStatus(Idle);
                    _state = noCs_noNav;                   
                } else { 
                    switch(_messageType) {
                        case Busy:
                        _state = Cs_noNav;
                        break;

                        case SetNav:
                            if (_setNav()) {
                                //From OMNET: original standard
                                //FSM_Goto(fsm,Cs_Nav);
                                // modify standard here
                                _state = noCs_Nav;
                            }
                        break;
                   }
                }
            break;    
                
            case noCs_noNav:

                    switch(_messageType){
                        case Busy:
			    _changeStatus(Busy);
                            _state = Cs_noNav;
                        break;

                        case SetNav:
                           if (_setNav()){
			       _changeStatus(Busy);
                               _state = noCs_Nav;
                           }
                        break;
                    }

            break;

            case Cs_Nav:
                if (kind == NavTimeOut) {
                    _state = Cs_noNav;
                } else {
                    if (_messageType == Idle) 
                        _state = noCs_Nav;
                    else
                        _updateNav();
                }
            break;

            case noCs_Nav:
                  if (kind == NavTimeOut) {
                      _IfsTimer = setTimer(IfsTimeOut, currentTime + _dIfs*1e-6);
                      _state = Wait_Ifs;
                  } else {
                      if (_messageType == Busy)
                          _state = Cs_Nav;
                      else
                          _updateNav();
                      }
            break;
        }
        _inputMessage = null;
        _messageType = UNKNOWN;
        //_message = null;         
    }
    
    /** Initialize the private variables.
     *  @exception IllegalActionException If thrown by the base class.
     */
    public void initialize() throws IllegalActionException {
        super.initialize();
        _dDIfs = _aSifsTime+2*_aSlotTime;
        _dEIfs= _aSifsTime+_sAckCtsLng/_mBrate+
                _aPreambleLength+_aPlcpHeaderLength+_dDIfs;
        _dIfs=_dEIfs;
        _state = 0;
        _curSrc = nosrc;
        _inputMessage = null;
        //_message = null;
        _messageType = UNKNOWN;


        
        //FIXME:Why I need to output a "Busy" state at the beginning?
        //Seems it won't change anything in the down stream components...
        Token[] value = {new IntToken(Busy)};
        RecordToken t = new RecordToken(CSMsgFields, value);
        toTransmission.send(0, t);
    }
    
    private void _updateNav() throws IllegalActionException {
        double tNew;
        
        switch(_messageType) {
            case SetNav:
            
                tNew = ((IntToken)_inputMessage.get("tRef")).intValue()
                       +((IntToken)_inputMessage.get("dNav")).intValue()*1e-6;
                if (tNew > _NavTimer.expirationTime) {
                    _NavTimer.expirationTime = tNew;
                    //curSrc=((SetNavMsg *)msg)->src;
                    _curSrc=((IntToken)_inputMessage.get("src")).intValue();
                }
             break;

             case RtsTimeout:
                 if (_curSrc!=Rts)
             break;

             case ClearNav:
                Director director = getDirector();
                double currentTime = director.getCurrentTime();
		// force the state transition to noNav
                _NavTimer.expirationTime = currentTime;
                _curSrc=nosrc;
             break;
      
         }
    }

    private boolean _setNav() throws IllegalActionException {
        Director director = getDirector();
        double currentTime = director.getCurrentTime();
        double expirationTime =  ((IntToken)_inputMessage.get("tRef")).intValue()
                   +((IntToken)_inputMessage.get("dNav")).intValue()*1e-6;
        if(expirationTime > currentTime) {
	    _NavTimer=setTimer(NavTimeOut,expirationTime);
            return true;
        } else {
            return false;
        }
    }

    private void _changeStatus(int kind) throws IllegalActionException {
        // send idle/busy event to the Transmission block
        Token[] value = {new IntToken(kind)};
        RecordToken t = new RecordToken(CSMsgFields, value);
        toTransmission.send(0, t);
    }

    ///////////////////////////////////////////////////////////////////
    ////                         private variables                 ////
    //Define the states of the inside FSM.
    private static final int Cs_noNav   = 0;
    private static final int Cs_Nav     = 1;
    private static final int Wait_Ifs   = 2;
    private static final int noCs_Nav   = 3;
    private static final int noCs_noNav = 4;
    
    private int _state=0;
    
    //the distributed inter frame space.
    private int _dDIfs;
    //the extended inter frame space.
    private int _dEIfs;
    //the applying inter frame space.
    private int _dIfs;
    
    private int _curSrc;
    
    private Timer _IfsTimer;
    private Timer _NavTimer;

    // timer types
    private static final int IfsTimeOut=0;
    private static final int NavTimeOut=1;
    private static final int SlotTimeOut=2;

    private RecordToken _inputMessage;
    private int _messageType;

}
