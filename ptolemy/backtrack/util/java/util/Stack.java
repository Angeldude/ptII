/* This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package ptolemy.backtrack.util.java.util;

import java.util.EmptyStackException;
import ptolemy.backtrack.Rollbackable;
import ptolemy.backtrack.util.FieldRecord;

public class Stack extends Vector implements Rollbackable {

    private static final long serialVersionUID = 1224463164541339165L;

    public Stack() {
    }

    public Object push(Object item) {
        addElement(item);
        return item;
    }

    public synchronized Object pop() {
        if (getElementCount() == 0)
            throw new EmptyStackException();
        setModCount(getModCount() + 1);
        setElementCount(getElementCount() - 1);
        Object obj = getElementData()[getElementCount()];
        getElementData()[getElementCount()] = null;
        return obj;
    }

    public synchronized Object peek() {
        if (getElementCount() == 0)
            throw new EmptyStackException();
        return getElementData()[getElementCount() - 1];
    }

    public synchronized boolean empty() {
        return getElementCount() == 0;
    }

    public synchronized int search(Object o) {
        int i = getElementCount();
        while (--i >= 0) 
            if (equals(o, getElementData()[i]))
                return getElementCount() - i;
        return -1;
    }

    public void $COMMIT(long timestamp) {
        FieldRecord.commit($RECORDS, timestamp, $RECORD$$CHECKPOINT.getTopTimestamp());
        super.$COMMIT(timestamp);
    }

    public void $RESTORE(long timestamp, boolean trim) {
        super.$RESTORE(timestamp, trim);
    }

    private FieldRecord[] $RECORDS = new FieldRecord[] {
        };
}
