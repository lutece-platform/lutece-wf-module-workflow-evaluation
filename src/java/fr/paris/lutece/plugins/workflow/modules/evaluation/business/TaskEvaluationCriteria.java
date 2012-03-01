/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.evaluation.business;

import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;


public class TaskEvaluationCriteria
{
    private int _nIdCriteria = WorkflowUtils.CONSTANT_ID_NULL;
    private int _nIdTask;
    private String _strTitle;
    private String _strBestScore;
    private boolean _bMandatory;
    private int _nPosition;

    /**
    *
    * @return the criteria id
    */
    public int getIdCriteria(  )
    {
        return _nIdCriteria;
    }

    /**
     * set the criteria id
     * @param the criteria id
     */
    public void setIdCriteria( int idCriteria )
    {
        _nIdCriteria = idCriteria;
    }

    /**
    *
    * @return the task id
    */
    public int getIdTask(  )
    {
        return _nIdTask;
    }

    /**
     * set the task id
     * @param the task id
     */
    public void setIdTask( int idTask )
    {
        _nIdTask = idTask;
    }

    /**
    *
    * @return  true if the criteria is mandatory
    */
    public boolean isMandatory(  )
    {
        return _bMandatory;
    }

    /**
     * set true if the criteria is mandatory
     * @param mandatory boolean
     */
    public void setMandatory( boolean mandatory )
    {
        _bMandatory = mandatory;
    }

    /**
     *
     * @return the criteria title
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * set the criteria title
     * @param title the title
     */
    public void setTitle( String title )
    {
        _strTitle = title;
    }

    /**
    *
    * @return the best score of the criteria
    */
    public String getBestScore(  )
    {
        return _strBestScore;
    }

    /**
     * set the best score of the criteria
     * @param strBestScore the best score
     */
    public void setBestScore( String strBestScore )
    {
        _strBestScore = strBestScore;
    }

    /**
    *
    * @return the criteria position
    */
    public int getPosition(  )
    {
        return _nPosition;
    }

    /**
     * set the criteria position
     * @param position the criteria position
     */
    public void setPosition( int position )
    {
        _nPosition = position;
    }
}
