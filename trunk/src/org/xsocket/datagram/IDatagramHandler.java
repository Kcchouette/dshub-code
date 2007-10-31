// $Id: IDatagramHandler.java 1049 2007-03-21 16:42:48Z grro $
/*
 *  Copyright (c) xsocket.org, 2006 - 2007. All rights reserved.
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Please refer to the LGPL license at: http://www.gnu.org/copyleft/lesser.txt
 * The latest copy of this software may be found on http://www.xsocket.org/
 */
package org.xsocket.datagram;

import java.io.IOException;




/**
 * Endpoint handler, which will be used by receiving a datagram in a asynchronous manner. E.g.
 * 
 * <pre>
 * class MyHandler implements IDatagramHandler {
 * 
 *     public boolean onDatagram(IEndpoint localEndpoint) throws IOException {
 *          UserDatagram datagram = localEndpoint.receive();  // get the datagram
 *          ...
 *          return true;   // true -> signal that this event has been handled
 *     }
 * }
 * </pre>	 
 * 
 * @author grro@xsocket.org
 */
public interface IDatagramHandler {
	
	
	/**
	 * signals a incoming datagram
	 * 
	 * @param localEndpoint   the local endpoint 
	 * @return true if the event has been handled 
	 * @throws IOException If some other I/O error occurs
	 */
	public boolean onDatagram(IEndpoint localEndpoint) throws IOException;
}