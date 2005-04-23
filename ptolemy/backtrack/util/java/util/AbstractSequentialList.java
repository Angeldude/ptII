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

import java.util.Iterator;
import java.util.ListIterator;
import ptolemy.backtrack.Rollbackable;
import ptolemy.backtrack.util.FieldRecord;

public abstract class AbstractSequentialList extends AbstractList implements Rollbackable {

    protected AbstractSequentialList() {
    }

    public abstract ListIterator listIterator(int index);

    public void add(int index, Object o) {
        listIterator(index).add(o);
    }

    public boolean addAll(int index, Collection c) {
        Iterator ci = c.iterator();
        int size = c.size();
        ListIterator i = listIterator(index);
        for (int pos = size; pos > 0; pos--) 
            i.add(ci.next());
        return size > 0;
    }

    public Object get(int index) {
        if (index == size())
            throw new IndexOutOfBoundsException("Index: " + index+", Size:"+size());
        return listIterator(index).next();
    }

    public Iterator iterator() {
        return listIterator();
    }

    public Object remove(int index) {
        if (index == size())
            throw new IndexOutOfBoundsException("Index: " + index+", Size:"+size());
        ListIterator i = listIterator(index);
        Object removed = i.next();
        i.remove();
        return removed;
    }

    public Object set(int index, Object o) {
        if (index == size())
            throw new IndexOutOfBoundsException("Index: " + index+", Size:"+size());
        ListIterator i = listIterator(index);
        Object old = i.next();
        i.set(o);
        return old;
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
