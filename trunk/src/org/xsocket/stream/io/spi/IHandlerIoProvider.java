// $Id: IMutlithreadedServerListener.java 1280 2007-05-28 17:59:08Z grro $
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
package org.xsocket.stream.io.spi;


import java.io.IOException;

import org.xsocket.stream.INonBlockingConnection;


/**
 *  <br><br>
 * 
 * <b>This class is experimental and is subject to change</b>
 *  
 * @author grro@xsocket.org
 */
public interface IHandlerIoProvider {
	
	public static final int UNLIMITED = INonBlockingConnection.UNLIMITED;
	
	
	/**
	 * update the write transfer rate for the given handler
	 * 
	 * @param ioHandler       the io handler  
	 * @param bytesPerSecond  the transfer rate (bytes/sec)
	 * @return the upated handler 
	 * @throws IOException if an exception occurs
	 */
	public IIoHandler setWriteTransferRate(IIoHandler ioHandler, int bytesPerSecond) throws IOException;
}