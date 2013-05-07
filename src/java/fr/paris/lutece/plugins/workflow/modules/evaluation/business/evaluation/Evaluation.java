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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business.evaluation;

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.SynthesisCriteriaValue;

import java.util.List;


/**
 *
 * EvaluationValue
 *
 */
public class Evaluation
{
    private int _nIdResourceHistory;
    private int _nIdTask;
    private List<EvaluationCriteriaValue> _listCriteriaValue;
    private List<SynthesisCriteriaValue> _listSynthesisValues;
    private String _strSummary;
    private String _strFinalNote;

    /**
     * gets SynthesisValues
     * @return SynthesisValues
     */
    public List<SynthesisCriteriaValue> getSynthesisValues(  )
    {
        return _listSynthesisValues;
    }

    /**
     * sets SynthesisValues
     * @param listSynthesisValues SynthesisValues
     */
    public void setSynthesisValues( List<SynthesisCriteriaValue> listSynthesisValues )
    {
        _listSynthesisValues = listSynthesisValues;
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
     * @param id the ResourceHistory id
     */
    public void setIdResourceHistory( int id )
    {
        _nIdResourceHistory = id;
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
     * @param idTask the task id
     */
    public void setIdTask( int idTask )
    {
        _nIdTask = idTask;
    }

    /**
     * return the summary
     * @return the summary
     */
    public String getSummary(  )
    {
        return _strSummary;
    }

    /**
     * set the summary
     * @param value the summary
     */
    public void setSummary( String value )
    {
        _strSummary = value;
    }

    /**
     * return la note finale
     * @return la note finale
     */
    public List<EvaluationCriteriaValue> getEvaluationCriteriaValues(  )
    {
        return _listCriteriaValue;
    }

    /**
     * set criteria values
     * @param values the criteria value
     */
    public void setEvaluationCriteriaValues( List<EvaluationCriteriaValue> values )
    {
        _listCriteriaValue = values;
    }

    /**
     * return the final note
     * @return the final note
     */
    public String getFinalNote(  )
    {
        return _strFinalNote;
    }

    /**
     * set the final note
     * @param value the final note
     */
    public void setFinalNote( String value )
    {
        _strFinalNote = value;
    }
}
