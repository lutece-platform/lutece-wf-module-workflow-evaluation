/*
 * Copyright (c) 2002-2009, Mairie de Paris
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

import java.util.List;


/**
 *
 *TaskEvaluationExpertConfig
 *
 */
public class TaskEvaluationConfig
{
    private int _nIdTask;
    private String _strTaskTitle;
    private String _strTaskDescription;
    private String _strSummaryTitle;
    private boolean _bMandatorySummary;
    private List<TaskEvaluationCriteria> _listEvaluationsCriteria;
    private List<SynthesisCriteria> _listSynthesisCriteria;
    private String strFinalNoteTitle;
    private boolean _bMandatoryFinalNote;
    private boolean _bAutomaticFinalNote;
    private String strBestScoreFinalNote;

    /**
    *
    * @return l'identifiant de la tache
    */
    public int getIdTask(  )
    {
        return _nIdTask;
    }

    /**
     * set l'identifiant  de la tache
     * @param idTask l'identifianty de la tache
     */
    public void setIdTask( int idTask )
    {
        _nIdTask = idTask;
    }

    /**
     *
     * @return true si la synthese est Obligatoire
     */
    public boolean isMandatorySummary(  )
    {
        return _bMandatorySummary;
    }

    /**
     * set true si la synthese est Obligatoire
     * @param syntheseObligatoire true si la synthese est Obligatoire
     */
    public void setMandatorySummary( boolean bMandatorySummary )
    {
        _bMandatorySummary = bMandatorySummary;
    }

    /**
     *
     * @return the summary title
     */
    public String getSummaryTitle(  )
    {
        return _strSummaryTitle;
    }

    /**
     * set  the summary title
     * @param summaryTitle  the summary title
     */
    public void setSummaryTitle( String summaryTitle )
    {
        _strSummaryTitle = summaryTitle;
    }

    /**
     *
     * @return a list of criteria
     */
    public List<TaskEvaluationCriteria> getEvaluationsCriteria(  )
    {
        return _listEvaluationsCriteria;
    }

    /**
     * set a list of criteria
     * @param listCriterias list of criteria
     */
    public void setEvaluationsCriteria( List<TaskEvaluationCriteria> listCriterias )
    {
        _listEvaluationsCriteria = listCriterias;
    }

    /**
     *
     * @return a title of task
     */
    public String getTaskTitle(  )
    {
        return _strTaskTitle;
    }

    /**
    * set a title of task
    * @param listCriterias list of criteria
    */
    public void setTaskTitle( String titleTask )
    {
        _strTaskTitle = titleTask;
    }

    /**
     *
     * @return a description of task
     */
    public String getTaskDescription(  )
    {
        return _strTaskDescription;
    }

    /**
    * set a description of task
    * @param _strTaskDescription list of criteria
    */
    public void setTaskDescription( String descriptionTask )
    {
        _strTaskDescription = descriptionTask;
    }

    /**
     *
     * @return a final note of task
     */
    public String getFinalNoteTitle(  )
    {
        return strFinalNoteTitle;
    }

    /**
    * set a title final note of task
    * @param strTitleFinalNote title of note final
    */
    public void setFinalNoteTitle( String strTitleFinalNote )
    {
        this.strFinalNoteTitle = strTitleFinalNote;
    }

    /**
     *
     * @return true si la note final est obligatoire
     */
    public boolean isMandatoryFinalNote(  )
    {
        return _bMandatoryFinalNote;
    }

    /**
    * set true si la note final est Obligatoire
    * @param mandatoryFinalNote true si la note final est Obligatoire
    */
    public void setMandatoryFinalNote( boolean mandatoryFinalNote )
    {
        _bMandatoryFinalNote = mandatoryFinalNote;
    }

    /**
     *
     * @return the type note final of task
     */
    public boolean isAutomaticFinalNote(  )
    {
        return _bAutomaticFinalNote;
    }

    /**
    * set the type note final of task
    * @param strTypeFinalNote type note final
    */
    public void setAutomaticFinalNote( boolean bAutomaticFinalNote )
    {
        _bAutomaticFinalNote = bAutomaticFinalNote;
    }

    /**
     *
     * @return the best note final of task
     */
    public String getBestScoreFinalNote(  )
    {
        return strBestScoreFinalNote;
    }

    /**
    * set the best note final of task
    * @param strBestScoreFinalNote best note final
    */
    public void setBestScoreFinalNote( String strBestScoreFinalNote )
    {
        this.strBestScoreFinalNote = strBestScoreFinalNote;
    }

	public void setListSynthesisCriteria(List<SynthesisCriteria> listSynthesisCriteria) {
		this._listSynthesisCriteria = listSynthesisCriteria;
	}

	public List<SynthesisCriteria> getListSynthesisCriteria() {
		return _listSynthesisCriteria;
	}
}
