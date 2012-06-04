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
package fr.paris.lutece.plugins.workflow.modules.evaluation.service;

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.TaskEvaluationConfig;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.TaskEvaluationCriteria;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.dossier.DossierInformationComplementaire;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.evaluation.Evaluation;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.evaluation.EvaluationCriteriaValue;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.SynthesisCriteria;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.SynthesisCriteriaValue;
import fr.paris.lutece.plugins.workflow.modules.evaluation.service.dossier.IDossierInformationComplementaireService;
import fr.paris.lutece.plugins.workflow.modules.evaluation.service.evaluation.IEvaluationService;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.Task;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * TaskEvaluationExpert
 */
public class TaskEvaluation extends Task
{
    // PARAMETERS
    private static final String PARAMETER_SUMMARY = "summary";
    private static final String PARAMETER_FINAL_NOTE = "final_note";
    private static final String PARAMETER_ID_CRITERIA = "id_criteria";
    private static final String PARAMETER_ID_SYNTHESIS_CRITERIA = "id_synthesis_criteria";

    // SERVICES
    @Inject
    @Named( TaskEvaluationConfigService.BEAN_SERVICE )
    private ITaskConfigService _taskEvalutionConfigService;
    @Inject
    private IEvaluationService _evaluationService;
    @Inject
    private IResourceHistoryService _resourceHistoryService;
    @Inject
    private IDossierInformationComplementaireService _dossierInformationComplementaireService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(  )
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        String strSummary = request.getParameter( PARAMETER_SUMMARY + "_" + this.getId(  ) );
        String strFinalNote = request.getParameter( PARAMETER_FINAL_NOTE + "_" + this.getId(  ) );
        Plugin plugin = PluginService.getPlugin( EvaluationPlugin.PLUGIN_NAME );
        TaskEvaluationConfig config = _taskEvalutionConfigService.findByPrimaryKey( this.getId(  ) );

        int nFinalNoteAuto = 0;
        EvaluationCriteriaValue criteriaValue;
        List<EvaluationCriteriaValue> listCriteriaValues = new ArrayList<EvaluationCriteriaValue>(  );
        List<SynthesisCriteriaValue> listSynthesisValues = new ArrayList<SynthesisCriteriaValue>(  );

        if ( config != null )
        {
            Evaluation evaluation = new Evaluation(  );
            evaluation.setIdResourceHistory( nIdResourceHistory );
            evaluation.setIdTask( this.getId(  ) );
            evaluation.setSummary( strSummary );

            for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
            {
                String strCriteriaValue = request.getParameter( PARAMETER_ID_CRITERIA + "_" + this.getId(  ) + "_" +
                        criteria.getIdCriteria(  ) );

                if ( config.isAutomaticFinalNote(  ) )
                {
                    nFinalNoteAuto += WorkflowUtils.convertStringToInt( strCriteriaValue );
                }

                criteriaValue = new EvaluationCriteriaValue(  );
                criteriaValue.setIdCriteria( criteria.getIdCriteria(  ) );
                criteriaValue.setIdResourceHistory( nIdResourceHistory );
                criteriaValue.setIdTask( this.getId(  ) );
                criteriaValue.setValue( strCriteriaValue );
                listCriteriaValues.add( criteriaValue );
            }

            for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
            {
                String[] values = request.getParameterValues( PARAMETER_ID_SYNTHESIS_CRITERIA + "_" + this.getId(  ) +
                        "_" + criteria.getIdCriteria(  ) );
                SynthesisCriteriaValue synthesisValue = new SynthesisCriteriaValue(  );
                synthesisValue.setIdCriteria( criteria.getIdCriteria(  ) );
                synthesisValue.setIdResourceHistory( nIdResourceHistory );
                synthesisValue.setIdTask( this.getId(  ) );
                synthesisValue.setValues( ( values == null ) ? new ArrayList<String>(  ) : Arrays.asList( values ) );

                listSynthesisValues.add( synthesisValue );
            }

            if ( config.isAutomaticFinalNote(  ) )
            {
                evaluation.setFinalNote( Integer.toString( nFinalNoteAuto ) );
            }
            else
            {
                evaluation.setFinalNote( strFinalNote );
            }

            evaluation.setEvaluationCriteriaValues( listCriteriaValues );
            evaluation.setSynthesisValues( listSynthesisValues );

            _evaluationService.create( evaluation, plugin );

            //Add Note Finale
            ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
            AdminUser user = AdminUserService.getAdminUser( request );

            if ( resourceHistory != null )
            {
                DossierInformationComplementaire dossierInformationComplementaire = new DossierInformationComplementaire(  );
                dossierInformationComplementaire.setIdDossier( resourceHistory.getIdResource(  ) );

                if ( config.isAutomaticFinalNote(  ) )
                {
                    dossierInformationComplementaire.setNote( Integer.toString( nFinalNoteAuto ) );
                }
                else
                {
                    dossierInformationComplementaire.setNote( strFinalNote );
                }

                dossierInformationComplementaire.setTypeRessource( resourceHistory.getResourceType(  ) );
                dossierInformationComplementaire.setRapporteur( user.getAccessCode(  ) );

                _dossierInformationComplementaireService.create( dossierInformationComplementaire, plugin );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveConfig(  )
    {
        _taskEvalutionConfigService.remove( this.getId(  ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveTaskInformation( int nIdHistory )
    {
        Plugin plugin = PluginService.getPlugin( EvaluationPlugin.PLUGIN_NAME );
        _evaluationService.remove( nIdHistory, this.getId(  ), plugin );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( Locale locale )
    {
        TaskEvaluationConfig config = _taskEvalutionConfigService.findByPrimaryKey( this.getId(  ) );

        if ( config != null )
        {
            return config.getTaskTitle(  );
        }

        return StringUtils.EMPTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> getTaskFormEntries( Locale locale )
    {
        TaskEvaluationConfig config = _taskEvalutionConfigService.findByPrimaryKey( this.getId(  ) );
        Map<String, String> mapListEntriesForm = null;

        if ( config != null )
        {
            mapListEntriesForm = new HashMap<String, String>(  );
            mapListEntriesForm.put( PARAMETER_SUMMARY + "_" + this.getId(  ), config.getSummaryTitle(  ) );
            mapListEntriesForm.put( PARAMETER_FINAL_NOTE + "_" + this.getId(  ), config.getFinalNoteTitle(  ) );

            for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
            {
                mapListEntriesForm.put( PARAMETER_ID_CRITERIA + "_" + this.getId(  ) + "_" +
                    criteria.getIdCriteria(  ), criteria.getTitle(  ) );
            }

            for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
            {
                mapListEntriesForm.put( PARAMETER_ID_SYNTHESIS_CRITERIA + "_" + this.getId(  ) + "_" +
                    criteria.getIdCriteria(  ), criteria.getTitle(  ) );
            }
        }

        return mapListEntriesForm;
    }
}
