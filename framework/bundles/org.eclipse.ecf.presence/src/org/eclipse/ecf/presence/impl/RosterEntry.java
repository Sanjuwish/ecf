/****************************************************************************
* Copyright (c) 2004 Composent, Inc. and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Composent, Inc. - initial API and implementation
*****************************************************************************/

package org.eclipse.ecf.presence.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.presence.IPresence;
import org.eclipse.ecf.presence.IRosterEntry;
import org.eclipse.ecf.presence.IRosterGroup;

public class RosterEntry implements IRosterEntry {
    
    protected ID userID;
    protected String name;
    protected IPresence presenceState;
    protected InterestType interestType;
    protected List groups;
    protected ID serviceID;
    
    public RosterEntry(ID svcID, ID userID, String name, IPresence presenceState, InterestType interestType, Collection grps) {
        this.userID = userID;
        this.name = name;
        this.presenceState = presenceState;
        this.interestType = interestType;
        this.groups = new ArrayList();
        this.serviceID = svcID;
        if (grps != null) this.groups.addAll(groups);
    }
    
    public RosterEntry(ID svcID, ID userID, String name) {
        this(svcID,userID,name,null,InterestType.BOTH,null);
    }

    public RosterEntry(ID svcID, ID userID, String name, IPresence presenceState) {
        this(svcID,userID,name,presenceState,InterestType.BOTH,null);
    }
    public RosterEntry(ID svcID, ID userID, String name, InterestType interestType) {
        this(svcID,userID,name,null,interestType,null);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#getGroups()
     */
    public Iterator getGroups() {
        return groups.iterator();
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#getUserID()
     */
    public ID getUserID() {
        return userID;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#getInterestType()
     */
    public InterestType getInterestType() {
        return interestType;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#getPresenceState()
     */
    public IPresence getPresenceState() {
        return presenceState;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#setPresenceState(org.eclipse.ecf.ui.presence.IPresence)
     */
    public void setPresenceState(IPresence presence) {
        this.presenceState = presence;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#addGroup(org.eclipse.ecf.ui.presence.IRosterGroup)
     */
    public void add(IRosterGroup group) {
        groups.add(group);
    }
    
    public void addAll(Collection grps) {
        groups.addAll(grps);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ecf.ui.presence.IRosterEntry#remvoe(org.eclipse.ecf.ui.presence.IRosterGroup)
     */
    public void remove(IRosterGroup group) {
        groups.remove(group);
    }
    
    public ID getServiceID() {
    	return serviceID;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer("RosterEntry[");
        sb.append("userID=").append(userID).append(";");
        sb.append("name=").append(name).append(";");
        sb.append("presence=").append(presenceState).append(";");
        sb.append("interest=").append(interestType).append(";");
        sb.append("groups=").append(groups).append(";");
        sb.append("]");
        return sb.toString();
    }

}
