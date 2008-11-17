package ptolemy.domains.de.lib.apes.demo;

import ptolemy.actor.NoRoomException;
import ptolemy.domains.de.lib.apes.CTask;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;
import ptolemy.kernel.util.Workspace;

public class CTaskA extends CTask {

    public CTaskA() { 
    }

    public CTaskA(Workspace workspace) {
        super(workspace); 
    }

    public CTaskA(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name); 
    }
    
    private native void CMethod();
    
    
    @Override
    protected void _callCMethod() {
        CMethod();
    }
    
    @Override
    public void accessPointCallback() throws NoRoomException,
            IllegalActionException {
        // TODO Auto-generated method stub
        super.accessPointCallback();
    }
}
