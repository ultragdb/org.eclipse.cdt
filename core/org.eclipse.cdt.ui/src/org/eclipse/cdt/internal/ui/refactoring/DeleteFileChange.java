/*******************************************************************************
 * Copyright (c) 2008 Institute for Software, HSR Hochschule fuer Technik  
 * Rapperswil, University of applied sciences and others
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html  
 *  
 * Contributors: 
 * Institute for Software - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.internal.ui.refactoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * The counterpart to the CreateFileChange, a change to delete a file. 
 * 
 * @author Emanuel Graf
 *
 */
public class DeleteFileChange extends Change {
	
	private final IPath path;
	private String source;

	public DeleteFileChange(IPath path) {
		this.path = path;
	}

	@Override
	public Object getModifiedElement() {
		return path;
	}


	@Override
	public String getName() {
		return Messages.DeleteFileChange_0 + path.toPortableString(); 
	}


	@Override
	public void initializeValidationData(IProgressMonitor pm) {
		// Nothing to do
	}

	@Override
	public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		if(!file.exists()) {
			status.addFatalError(Messages.DeleteFileChange_1 + path.toString()); 
		}
		return status;
	}
	
	private String getSource(IFile file) throws CoreException {
		String encoding= null;
		try {
			encoding= file.getCharset();
		} catch (CoreException ex) {
			// fall through. Take default encoding.
		}
		StringBuffer sb= new StringBuffer();
		BufferedReader br= null;
		InputStream in= null;
		try {
			in= file.getContents();
		    if (encoding != null)
		        br= new BufferedReader(new InputStreamReader(in, encoding));	
		    else
		        br= new BufferedReader(new InputStreamReader(in));	
			int read= 0;
			while ((read= br.read()) != -1) {
				sb.append((char) read);
			}
			br.close();
		} catch (IOException e){
			
		}
		return sb.toString();
	}

	@Override
	public Change perform(IProgressMonitor pm) throws CoreException {
		IFile file= ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		source = getSource(file);
		Change undo = new CreateFileChange(file.getFullPath(), source, file.getCharset());
		file.delete(true,true, pm);
		return undo;
	}

}
