/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis;

import java.util.List;


/**
 *
 * SynthesisCriteriaValue : synthesis criteria values wrapper
 *
 */
public class SynthesisCriteriaValue
{
    private int _nId;
    private int _nIdResourceHistory;
    private int _nIdTask;
    private int _nIdCriteria;
    private List<String> _values;

    /**
     *
     * @return the criteria value id
     */
    public int getId(  )
    {
        return _nId;
    }

    /**
     *
     * @param nIdCriteriaValue the criteria value id
     */
    public void setId( int nIdCriteriaValue )
    {
        _nId = nIdCriteriaValue;
    }

    /**
     * @return the criteria value
     */
    public List<String> getValues(  )
    {
        return _values;
    }

    /**
     * set the criteria value
     * @param values the criteria value to set
     */
    public void setValues( List<String> values )
    {
        _values = values;
    }

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
     * @param nIdCriteria the criteria id
     */
    public void setIdCriteria( int nIdCriteria )
    {
        _nIdCriteria = nIdCriteria;
    }

    /**
    *
    * @return the ResourceHistory id
    */
    public int getIdResourceHistory(  )
    {
        return _nIdResourceHistory;
    }

    /**
     * the ResourceHistory id
     * @param nIdResourceHistory the ResourceHistory id
     */
    public void setIdResourceHistory( int nIdResourceHistory )
    {
        _nIdResourceHistory = nIdResourceHistory;
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
     * the task id
     * @param nIdTask the task id
     */
    public void setIdTask( int nIdTask )
    {
        _nIdTask = nIdTask;
    }
}
