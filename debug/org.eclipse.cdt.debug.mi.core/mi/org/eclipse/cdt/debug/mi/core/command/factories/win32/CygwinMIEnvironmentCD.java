/*******************************************************************************
 * Copyright (c) 2002, 2009 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * QNX Software Systems - Initial API and implementation
 *******************************************************************************/

package org.eclipse.cdt.debug.mi.core.command.factories.win32;

import org.eclipse.cdt.internal.core.Cygwin;

/**
 * CygwinMIEnvironmentCD
 */
public class CygwinMIEnvironmentCD extends WinMIEnvironmentCD {

	CygwinMIEnvironmentCD(String miVersion, String path) {
		super(miVersion, path);

		String cygwinPath = path;

		try {
			cygwinPath = Cygwin.windowsToCygwinPath(path);
		} catch (UnsupportedOperationException e) {
		}

		setParameters(new String[] { cygwinPath });
	}
}
