/*
 * Copyright (c) 2002-2011, Mairie de Paris
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

import fr.paris.lutece.plugins.workflow.business.ResourceHistory;
import fr.paris.lutece.plugins.workflow.business.ResourceHistoryHome;
import fr.paris.lutece.plugins.workflow.business.task.Task;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.SynthesisCriteria.Pair;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.SynthesisCriteria.Type;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 *
 * TaskEvaluationExpert
 */
public class TaskEvaluation extends Task
{
    //templates
    private static final String TEMPLATE_TASK_EVALUATION_CONFIG = "admin/plugins/workflow/modules/evaluation/task_evaluation_config.html";
    private static final String TEMPLATE_TASK_EVALUATION_EXPERT_FORM = "admin/plugins/workflow/modules/evaluation/task_evaluation_form.html";
    private static final String TEMPLATE_TASK_EVALUATION_INFORMATION = "admin/plugins/workflow/modules/evaluation/task_evaluation_information.html";

    //	Markers
    private static final String MARK_CONFIG = "config";
    private static final String MARK_AVAILABLE_TYPES = "types_list";
    private static final String MARK_CREATE_VALUES = "create_values";
    private static final String MARK_EVALUATION = "evaluation";
    private static final String MARK_LIST_CRITERIA = "criteria_list";
    private static final String MARK_CRITERIA_TITLE = "criteria_title";
    private static final String MARK_CRITERIA_VALUE = "criteria_value";
    private static final String MARK_AUTOMATIC_BEST_SCORE = "automatic_bestScoreFinalNote";

    //Parameters
    private static final String PARAMETER_TASK_TITLE = "task_title";
    private static final String PARAMETER_TASK_DESCRIPTION = "task_description";
    private static final String PARAMETER_CRITERIA_TITLE = "criteria_title";
    private static final String PARAMETER_MANDATORY_CRITERIA = "mandatory_criteria";
    private static final String PARAMETER_CRITERIA_BEST_SCORE = "criteria_best_score";
    private static final String PARAMETER_MANDATORY_SUMMARY = "mandatory_summary";
    private static final String PARAMETER_SUMMARY_TITLE = "summary_title";
    private static final String PARAMETER_FINAL_NOTE_TITLE = "final_note_title";
    private static final String PARAMETER_FINAL_NOTE_MANDATORY = "final_note_mandatory";
    private static final String PARAMETER_AUTOMATIC_FINAL_NOTE = "automatic_final_note";
    private static final String PARAMETER_BEST_SCORE_FINAL_NOTE = "best_score_final_note";
    private static final String PARAMETER_USE_NOTATION = "use_notation";
    private static final String PARAMETER_CRITERIAS_CHECKED = "criterias_checked";
    private static final String PARAMETER_SYNTHESIS_CRITERIAS = "synthesis_criterias";
    private static final String PARAMETER_FROM_ID_SYNTHESIS_CRITERIAS = "synthesis_criterias_form_id";
    private static final String PARAMETER_SYNTHESIS_MANDATORY_CRITERIA  = "synthesis_mandatory_criteria";
    private static final String PARAMETER_SYNTHESIS_CRITERIA_TITLE = "synthesis_criterias_title";
    private static final String PARAMETER_SYNTHESIS_CRITERIA_VALUE = "synthesis_criterias_value";
    private static final String PARAMETER_SUMMARY = "summary";
    private static final String PARAMETER_TYPE = "criteria_type";
    private static final String PARAMETER_FINAL_NOTE = "final_note";
    private static final String PARAMETER_ID_CRITERIA = "id_criteria";
    private static final String PARAMETER_ID_SYNTHESIS_CRITERIA = "id_synthesis_criteria";

    //Properties
    private static final String FIELD_TASK_TITLE = "module.workflow.evaluation.task_evaluation_config.label_task_title";
    private static final String FIELD_TASK_DESCRIPTION = "module.workflow.evaluation.task_evaluation_config.label_task_description";
    private static final String FIELD_SUMMARY_TITLE = "module.workflow.evaluation.task_evaluation_config.label_summary_title";
    private static final String FIELD_FINAL_NOTE_TITLE = "module.workflow.evaluation.task_evaluation_config.label_final_note_title";
    private static final String FIELD_BEST_SCORE_FINAL_NOTE = "module.workflow.evaluation.task_evaluation_config.label_best_score_final_note";
    private static final String PROPERTY_CRITERIA_NUMBER = "workflow-evaluation.task_evaluation_config.criteria_number";
    private static final String PROPERTY_CRITERIA_CREATENUMBER = "workflow-evaluation.task_evaluation_config.create_values_number";
    private static final String PROPERTY_VALUES_SEPARATOR = ",";

    //Messages
    private static final String MESSAGE_MANDATORY_FIELD = "module.workflow.evaluation.message.mandatory.field";
    private static final String MESSAGE_MANDATORY_CRITERIA_TITLE = "module.workflow.evaluation.message.mandatory.criteria_title.error";
    private static final String MESSAGE_MANDATORY_CRITERIA_BEST_SCORE = "module.workflow.evaluation.message.mandatory.criteria_best_score.error";
    private static final String MESSAGE_NUMERIC_CRITERIA_BEST_SCORE = "module.workflow.evaluation.message.numeric.criteria_best_score.error";
    private static final String MESSAGE_SCORE_ERROR = "module.workflow.evaluation.message.score.error";
    private static final String MESSAGE_NUMERIC_FIELD_ERROR = "module.workflow.evaluation.message.numeric.field.error";
    private static final String MESSAGE_NO_CONFIGURATION_FOR_TASK_EVALUATION_EXPERT = "module.workflow.evaluation.message.no_configuration_for_task_evaluation";
    private static final String MESSAGE_MANDATORY_CRITERIA_TYPE = "module.workflow.evaluation.message.mandatory.criteria_type.error";

    // Xml Tags
    private static final String TAG_EVALUATION = "evaluation";
    private static final String TAG_SUMMARY = "summary";
    private static final String TAG_CRITERIA = "criteria";
    private static final String TAG_LIST_CRITERIA = "list_criteria";
    private static final String TAG_LIST_SYNTHESIS_CRITERIA = "list_synthesis_criteria";
    private static final String TAG_TITLE = "title";
    private static final String TAG_VALUE = "value";
    private static final String TAG_FINAL_NOTE = "final_note";
    private static final String ATTRIBUTE_NUMBER = "number";
    private int _nNbCritere = AppPropertiesService.getPropertyInt( PROPERTY_CRITERIA_NUMBER, 20 );
    private int _nCreateValues = AppPropertiesService.getPropertyInt( PROPERTY_CRITERIA_CREATENUMBER, 5 );

    /**
     * {@inheritDoc}
     */
    public void init(  )
    {
    }

    /**
     * {@inheritDoc}
     */
    public String doSaveConfig( HttpServletRequest request, Locale locale, Plugin plugin )
    {
        String strError = WorkflowUtils.EMPTY_STRING;
        String strTaskTitle = request.getParameter( PARAMETER_TASK_TITLE );
        String strDescriptionTask = request.getParameter( PARAMETER_TASK_DESCRIPTION );
        String strSummaryTitle = request.getParameter( PARAMETER_SUMMARY_TITLE );
        String strMandatorySummary = request.getParameter( PARAMETER_MANDATORY_SUMMARY );
        String strFinalNoteTitle = request.getParameter( PARAMETER_FINAL_NOTE_TITLE );
        String strMandatoryFinalNotre = request.getParameter( PARAMETER_FINAL_NOTE_MANDATORY );
        String strAutomaticFinalNote = request.getParameter( PARAMETER_AUTOMATIC_FINAL_NOTE );

        String strBestScoreFinalNote = request.getParameter( PARAMETER_BEST_SCORE_FINAL_NOTE );
        
        String strNotation = request.getParameter( PARAMETER_USE_NOTATION );
        
        boolean bNotation = strNotation != null;

        //String strFinalNoteType = request.getParameter( PARAMETER_FINAL_NOTE_TYPE );
        int nBestScoreFinalNote = WorkflowUtils.convertStringToInt( strBestScoreFinalNote );

        if ( ( strTaskTitle == null ) || strTaskTitle.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) )
        {
            strError = FIELD_TASK_TITLE;
        }
        else if ( ( strDescriptionTask == null ) || strDescriptionTask.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) )
        {
            strError = FIELD_TASK_DESCRIPTION;
        }
        else if ( bNotation && ( ( strFinalNoteTitle == null ) || strFinalNoteTitle.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) )
        {
            strError = FIELD_FINAL_NOTE_TITLE;
        }
        else if ( bNotation && ( ( strAutomaticFinalNote == null ) &&
                ( ( strBestScoreFinalNote == null ) ||
                strBestScoreFinalNote.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) ) )
        {
            strError = FIELD_BEST_SCORE_FINAL_NOTE;
        }

        if ( !strError.equals( WorkflowUtils.EMPTY_STRING ) )
        {
            Object[] tabRequiredFields = { I18nService.getLocalizedString( strError, locale ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        if ( bNotation && ( ( strAutomaticFinalNote == null ) && ( nBestScoreFinalNote < 0 ) ) )
        {
            Object[] tabNumericFields = { I18nService.getLocalizedString( FIELD_BEST_SCORE_FINAL_NOTE, locale ) };

            return AdminMessageService.getMessageUrl( request, MESSAGE_NUMERIC_FIELD_ERROR, tabNumericFields,
                AdminMessage.TYPE_STOP );
        }

        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );
        Boolean bCreate = false;

        if ( config == null )
        {
            config = new TaskEvaluationConfig(  );
            config.setIdTask( this.getId(  ) );
            bCreate = true;
        }

        //test if criterias error exist
        if ( bNotation )
        {
        	strError = getCriteriasData( config, request, locale, plugin );
        }
        else
        {
        	config.setEvaluationsCriteria( new ArrayList<TaskEvaluationCriteria>(  ) );
        }

        if ( StringUtils.isNotBlank( strError ) )
        {
            return strError;
        }
        
        //test if synthesis criterias error exist
        strError = getSynthesisCriteriaData( config, request, locale, plugin );
        if ( StringUtils.isNotBlank( strError ) )
        {
            return strError;
        }

        config.setTaskTitle( strTaskTitle );
        config.setTaskDescription( strDescriptionTask );
        config.setMandatorySummary( strMandatorySummary != null );
        config.setSummaryTitle( strSummaryTitle );
        if ( bNotation )
        {
	        config.setFinalNoteTitle( strFinalNoteTitle );
	
	        config.setAutomaticFinalNote( strAutomaticFinalNote != null );
	        config.setMandatoryFinalNote( strMandatoryFinalNotre != null );
	
	        if ( strAutomaticFinalNote == null )
	        {
	            config.setBestScoreFinalNote( strBestScoreFinalNote );
	        }
        }
        else
        {
        	config.setFinalNoteTitle( WorkflowUtils.EMPTY_STRING );
        	config.setAutomaticFinalNote( false );
        	config.setMandatoryFinalNote( false );
        	config.setBestScoreFinalNote( WorkflowUtils.EMPTY_STRING );
        }

        if ( bCreate )
        {
            TaskEvaluationConfigHome.create( config, plugin );
        }
        else
        {
            TaskEvaluationConfigHome.update( config, plugin );
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String doValidateTask( int nIdResource, String strResourceType, HttpServletRequest request, Locale locale,
        Plugin plugin )
    {
        String strMandatoryError = WorkflowUtils.EMPTY_STRING;
        String strEvaluationError = WorkflowUtils.EMPTY_STRING;
        String strCriteriaValue;
        String strSummary = request.getParameter( PARAMETER_SUMMARY + "_" + this.getId(  ) );
        String strFinalNote = request.getParameter( PARAMETER_FINAL_NOTE + "_" + this.getId(  ) );
        int nFinalNote = WorkflowUtils.convertStringToInt( strFinalNote );
        int nCriteriaValue;
        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );

        if ( config == null )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_NO_CONFIGURATION_FOR_TASK_EVALUATION_EXPERT,
                AdminMessage.TYPE_STOP );
        }
        
        boolean bNotation = StringUtils.isNotBlank( config.getFinalNoteTitle(  ) );

        if ( config.isMandatorySummary(  ) &&
                ( ( strSummary == null ) || strSummary.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) )
        {
            strMandatoryError = config.getSummaryTitle(  );
        }
        else if ( bNotation && ( !config.isAutomaticFinalNote(  ) && config.isMandatoryFinalNote(  ) &&
                ( ( strFinalNote == null ) || strFinalNote.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) ) )
        {
            strMandatoryError = config.getFinalNoteTitle(  );
        }
        else if ( bNotation && (   !config.isAutomaticFinalNote(  ) &&
                ( ( strFinalNote != null ) && !strFinalNote.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) &&
                ( ( nFinalNote < 0 ) ||
                ( nFinalNote > WorkflowUtils.convertStringToInt( config.getBestScoreFinalNote(  ) ) ) ) ) )
        {
            strEvaluationError = config.getFinalNoteTitle(  );
        }

        else
        {
            for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
            {
                strCriteriaValue = request.getParameter( PARAMETER_ID_CRITERIA + "_" + this.getId(  ) + "_" +
                        criteria.getIdCriteria(  ) );
                nCriteriaValue = WorkflowUtils.convertStringToInt( strCriteriaValue );

                if ( criteria.isMandatory(  ) &&
                        ( ( strCriteriaValue == null ) ||
                        strCriteriaValue.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) )
                {
                    strMandatoryError = criteria.getTitle(  );

                    break;
                }
                else if ( ( ( strCriteriaValue != null ) &&
                        !strCriteriaValue.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) &&
                        ( ( nCriteriaValue < 0 ) ||
                        ( nCriteriaValue > WorkflowUtils.convertStringToInt( criteria.getBestScore(  ) ) ) ) )
                {
                    strEvaluationError = criteria.getTitle(  );

                    break;
                }
            }
            
            if ( StringUtils.isBlank( strEvaluationError ) )
            {
            	for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
            	{
            		strCriteriaValue = request.getParameter( PARAMETER_ID_SYNTHESIS_CRITERIA + "_" + this.getId(  ) + "_" +
            				criteria.getIdCriteria(  ) );
            		if ( criteria.isMandatory(  ) &&
                            ( StringUtils.isBlank( strCriteriaValue ) ) )
                    {
                        strMandatoryError = criteria.getTitle(  );

                        break;
                    }
            	}
            }
        }

        if ( !strMandatoryError.equals( WorkflowUtils.EMPTY_STRING ) )
        {
            Object[] tabRequiredFields = { strMandatoryError };

            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELD, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        else if ( !strEvaluationError.equals( WorkflowUtils.EMPTY_STRING ) )
        {
            Object[] tabRequiredFields = { strEvaluationError };

            return AdminMessageService.getMessageUrl( request, MESSAGE_SCORE_ERROR, tabRequiredFields,
                AdminMessage.TYPE_STOP );
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getDisplayConfigForm( HttpServletRequest request, Plugin plugin, Locale locale )
    {
    	Map<String, Object> model = new HashMap<String, Object>(  );

        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );

        if ( config == null )
        {
            config = new TaskEvaluationConfig(  );
            config.setEvaluationsCriteria( new ArrayList<TaskEvaluationCriteria>(  ) );
            config.setListSynthesisCriteria( new ArrayList<SynthesisCriteria>(  ) );
        }

        while ( config.getEvaluationsCriteria(  ).size(  ) < _nNbCritere )
        {
            config.getEvaluationsCriteria(  ).add( new TaskEvaluationCriteria(  ) );
        }
        
        while ( config.getListSynthesisCriteria(  ).size(  ) < _nNbCritere )
        {
        	config.getListSynthesisCriteria(  ).add( new SynthesisCriteria(  ) );
        }

        model.put( MARK_CONFIG, config );
        
        ReferenceList typesRefList = new ReferenceList();
        typesRefList.addItem( WorkflowUtils.EMPTY_STRING, WorkflowUtils.EMPTY_STRING );
        
        for ( Type type : Type.values(  ) )
        {
        	typesRefList.addItem( type.toString(  ), I18nService.getLocalizedString( type.getI18nKey(  ), locale ) );
        }
        
        model.put( MARK_AVAILABLE_TYPES, typesRefList );
        
        List<Pair> listCreateValues = new ArrayList<Pair>(  );
        for ( int nCpt = 0; nCpt < _nCreateValues ; nCpt++ )
        {
        	listCreateValues.add( new Pair(  ) );
        }
        model.put( MARK_CREATE_VALUES, listCreateValues );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_EVALUATION_CONFIG, locale, model );

        return template.getHtml(  );
    }

    /**
     * {@inheritDoc}
     */
    public String getDisplayTaskForm( int nIdResource, String strResourceType, HttpServletRequest request,
        Plugin plugin, Locale locale )
    {
    	Map<String, Object> model = new HashMap<String, Object>(  );

        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );
        model.put( MARK_CONFIG, config );

        int automaticBestScore = 0;

        for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
        {
            automaticBestScore += Integer.parseInt( criteria.getBestScore(  ) );
        }

        model.put( MARK_AUTOMATIC_BEST_SCORE, automaticBestScore );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_EVALUATION_EXPERT_FORM, locale, model );

        return template.getHtml(  );
    }

    /**
     * {@inheritDoc}
     */
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Plugin plugin, Locale locale )
    {
        Evaluation evaluationValue = EvaluationHome.findByPrimaryKey( nIdHistory, this.getId(  ), plugin );

        EvaluationCriteriaValue evaluationCriteriaValue;
        Map<String, Object> model = new HashMap<String, Object>(  );

        List<Map<String, String>> listHashCriterias = new ArrayList<Map<String, String>>(  );
        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );

        if ( config != null )
        {
            for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
            {
            	Map<String, String> hashCriteria = new HashMap<String, String>(  );
                hashCriteria.put( MARK_CRITERIA_TITLE, criteria.getTitle(  ) );
                evaluationCriteriaValue = EvaluationCriteriaValueHome.findByPrimaryKey( nIdHistory, this.getId(  ),
                        criteria.getIdCriteria(  ), plugin );

                if ( evaluationCriteriaValue != null )
                {
                    hashCriteria.put( MARK_CRITERIA_VALUE, evaluationCriteriaValue.getValue(  ) );
                }

                listHashCriterias.add( hashCriteria );
            }
            
            for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
            {
            	Map<String, String> hashCriteria = new HashMap<String, String>(  );
                hashCriteria.put( MARK_CRITERIA_TITLE, criteria.getTitle(  ) );
                SynthesisCriteriaValue synthesisCriteriaValue = SynthesisCriteriaValueHome.findByPrimaryKey( nIdHistory, this.getId(  ),
                        criteria.getIdCriteria(  ), plugin );

                if ( synthesisCriteriaValue != null )
                {
                    hashCriteria.put( MARK_CRITERIA_VALUE, getSynthesisValue( synthesisCriteriaValue.getValues(  ) ) );
                }

                listHashCriterias.add( hashCriteria );
            }
        }

        model.put( MARK_CONFIG, config );
        model.put( MARK_EVALUATION, evaluationValue );
        model.put( MARK_LIST_CRITERIA, listHashCriterias );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_EVALUATION_INFORMATION, locale, model );

        return template.getHtml(  );
    }
    
    private String getSynthesisValue( List<String> strValues )
    {
    	StringBuilder sbValue = new StringBuilder();
    	for ( int nIndex = 0; nIndex < strValues.size(  ); nIndex++ )
    	{
    		sbValue.append( strValues.get( nIndex ) );
    		if ( nIndex <strValues.size(  ) - 1 )
    		{
    			sbValue.append( PROPERTY_VALUES_SEPARATOR );
    		}
    	}
    	
    	return sbValue.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Plugin plugin, Locale locale )
    {
        String strSummary = request.getParameter( PARAMETER_SUMMARY + "_" + this.getId(  ) );
        String strFinalNote = request.getParameter( PARAMETER_FINAL_NOTE + "_" + this.getId(  ) );

        String strCriteriaValue;
        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );

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
                strCriteriaValue = request.getParameter( PARAMETER_ID_CRITERIA + "_" + this.getId(  ) + "_" +
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
            	String[] values = request.getParameterValues( PARAMETER_ID_SYNTHESIS_CRITERIA + "_" + this.getId(  ) + "_" +
                        criteria.getIdCriteria(  ) );
            	SynthesisCriteriaValue synthesisValue = new SynthesisCriteriaValue();
            	synthesisValue.setIdCriteria( criteria.getIdCriteria(  ) );
            	synthesisValue.setIdResourceHistory( nIdResourceHistory );
            	synthesisValue.setIdTask( this.getId(  ) );
            	synthesisValue.setValues( values == null ?  new ArrayList<String>(  ) : Arrays.asList(values) );
            	
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
            
            
            EvaluationHome.create( evaluation, plugin );

            //Add Note Finale
            ResourceHistory resourceHistory = ResourceHistoryHome.findByPrimaryKey( nIdResourceHistory, plugin );
            AdminUser user = AdminUserService.getAdminUser( request );

            if ( ( resourceHistory != null ) )
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

                DossierInformationComplementaireHome.create( dossierInformationComplementaire, plugin );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void doRemoveConfig( Plugin plugin )
    {
        TaskEvaluationConfigHome.remove( this.getId(  ), plugin );
    }

    /**
     * {@inheritDoc}
     */
    public boolean isConfigRequire(  )
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isFormTaskRequire(  )
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void doRemoveTaskInformation( int nIdHistory, Plugin plugin )
    {
        EvaluationHome.remove( nIdHistory, this.getId(  ), plugin );
    }

    /**
     * {@inheritDoc}
     */
    public String getTaskInformationXml( int nIdHistory, HttpServletRequest request, Plugin plugin, Locale locale )
    {
        StringBuffer strXml = new StringBuffer(  );
        Evaluation evaluationValue = EvaluationHome.findByPrimaryKey( nIdHistory, this.getId(  ), plugin );
        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );
        EvaluationCriteriaValue evaluationCriteriaValue;

        if ( evaluationValue != null )
        {
            XmlUtil.beginElement( strXml, TAG_EVALUATION );

            if ( evaluationValue.getSummary(  ) != null )
            {
                XmlUtil.addElementHtml( strXml, TAG_SUMMARY, evaluationValue.getSummary(  ) );
            }
            else
            {
                XmlUtil.addEmptyElement( strXml, TAG_SUMMARY, null );
            }

            if ( evaluationValue.getFinalNote(  ) != null )
            {
                XmlUtil.addElementHtml( strXml, TAG_FINAL_NOTE, evaluationValue.getFinalNote(  ) );
            }
            else
            {
                XmlUtil.addEmptyElement( strXml, TAG_FINAL_NOTE, null );
            }

            if ( config != null )
            {
                XmlUtil.beginElement( strXml, TAG_LIST_CRITERIA );

                int nCpt = 0;

                for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
                {
                    Map<String, String> attributes = new HashMap<String, String>(  );
                    attributes.put( ATTRIBUTE_NUMBER, String.valueOf( ++nCpt ) );
                    XmlUtil.beginElement( strXml, TAG_CRITERIA, attributes );

                    if ( criteria.getTitle(  ) != null )
                    {
                        XmlUtil.addElementHtml( strXml, TAG_TITLE, criteria.getTitle(  ) );
                    }
                    else
                    {
                        XmlUtil.addEmptyElement( strXml, TAG_TITLE, null );
                    }

                    evaluationCriteriaValue = EvaluationCriteriaValueHome.findByPrimaryKey( nIdHistory, this.getId(  ),
                            criteria.getIdCriteria(  ), plugin );

                    if ( evaluationCriteriaValue != null )
                    {
                        XmlUtil.addElementHtml( strXml, TAG_VALUE, evaluationCriteriaValue.getValue(  ) );
                    }
                    else
                    {
                        XmlUtil.addEmptyElement( strXml, TAG_VALUE, null );
                    }

                    XmlUtil.endElement( strXml, TAG_CRITERIA );
                }
                

                XmlUtil.endElement( strXml, TAG_LIST_CRITERIA );
                
                XmlUtil.beginElement( strXml, TAG_LIST_SYNTHESIS_CRITERIA );
                
                nCpt = 0;

                for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
                {
                	Map<String, String> attributes = new HashMap<String, String>(  );
                    attributes.put( ATTRIBUTE_NUMBER, String.valueOf( ++nCpt ) );
                    XmlUtil.beginElement( strXml, TAG_CRITERIA, attributes );

                    if ( criteria.getTitle(  ) != null )
                    {
                        XmlUtil.addElementHtml( strXml, TAG_TITLE, criteria.getTitle(  ) );
                    }
                    else
                    {
                        XmlUtil.addEmptyElement( strXml, TAG_TITLE, null );
                    }

                    SynthesisCriteriaValue synthesisCriteriaValue = SynthesisCriteriaValueHome.findByPrimaryKey( nIdHistory, this.getId(  ),
                            criteria.getIdCriteria(  ), plugin );

                    if ( synthesisCriteriaValue != null )
                    {
                        XmlUtil.addElementHtml( strXml, TAG_VALUE, getSynthesisValue( synthesisCriteriaValue.getValues(  ) ) );
                    }
                    else
                    {
                        XmlUtil.addEmptyElement( strXml, TAG_VALUE, null );
                    }

                    XmlUtil.endElement( strXml, TAG_CRITERIA );
                }
                
                XmlUtil.endElement( strXml, TAG_LIST_SYNTHESIS_CRITERIA );
            }

            XmlUtil.endElement( strXml, TAG_EVALUATION );
        }

        return strXml.toString(  );
    }
    
    /**
     * Synthesis criteria data
     * @param config the config
     * @param request request
     * @param locale locale
     * @param plugin plugin
     * @return error url
     */
    private String getSynthesisCriteriaData( TaskEvaluationConfig config, HttpServletRequest request, Locale locale,
            Plugin plugin )
    {
    	List<SynthesisCriteria> listSynthesisCriteria = new ArrayList<SynthesisCriteria>(  );
    	String[] tabCriterias = request.getParameterValues( PARAMETER_SYNTHESIS_CRITERIAS );
    	
		for ( int nIndex = 0; nIndex < _nNbCritere; nIndex++ )
		{
            String strIdCriteria = request.getParameter( PARAMETER_ID_SYNTHESIS_CRITERIA + "_" + tabCriterias[nIndex] );
            
            if ( StringUtils.isBlank( strIdCriteria ) )
            {
            	throw new AppException( "Criteria id not found" );
            }
            
            int nIdCriteria;
            
            try
            {
            	nIdCriteria = Integer.parseInt(strIdCriteria );
            }
            catch ( NumberFormatException nfe )
            {
            	throw new AppException( "Criteria id not found : " +  nfe.getMessage(  ), nfe );
            }
            String strCriteriaTitle = request.getParameter( PARAMETER_SYNTHESIS_CRITERIA_TITLE + "_" +
            		tabCriterias[nIndex] );
            if ( StringUtils.isBlank( strCriteriaTitle ) )
            {
            	continue;
            }
            String strMandatoryCriteria = request.getParameter( PARAMETER_SYNTHESIS_MANDATORY_CRITERIA + "_" +
            		tabCriterias[nIndex] );
            
            String strType = request.getParameter( PARAMETER_TYPE+ "_" +
            		tabCriterias[nIndex] );

            Object[] tabCriteriaNum = { nIndex + 1 };

            if ( ( ( strCriteriaTitle == null ) || strCriteriaTitle.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) )
            {
                return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_CRITERIA_TITLE,
                    tabCriteriaNum, AdminMessage.TYPE_STOP );
            }
            if ( StringUtils.isBlank( strType) )
            {
            	return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_CRITERIA_TYPE,
                        tabCriteriaNum, AdminMessage.TYPE_STOP ); 
            }
            
            Type type;
            try
            {
            	type = Type.valueOf( strType );
            }
            catch ( Exception e )
            {
            	throw new AppException( "Can't convert to enum for type " + strType +
            			" " + e.getMessage(  ), e );
            }
            
            List<Pair> listValues = new ArrayList<Pair>();
            String[] tabValues = request.getParameterValues( tabCriterias[nIndex] + "_" + PARAMETER_FROM_ID_SYNTHESIS_CRITERIAS );
            for ( String strFormValueId : tabValues )
            {
            	String strTitle = request.getParameter( tabCriterias[nIndex] + "_" + PARAMETER_SYNTHESIS_CRITERIA_TITLE + "_" + strFormValueId );
            	
            	if ( StringUtils.isBlank( strTitle ) )
            	{
            		continue;
            	}
            	String strValue = request.getParameter( tabCriterias[nIndex] + "_" + PARAMETER_SYNTHESIS_CRITERIA_VALUE + "_" + strFormValueId );
            	
            	Pair pair = new Pair();
            	
            	pair.setTitle( strTitle );
            	pair.setValue( strValue );
            	
            	listValues.add( pair );
            }

            SynthesisCriteria criteria = new SynthesisCriteria(  );
            criteria.setIdCriteria( nIdCriteria );
            criteria.setIdTask( this.getId(  ) );
            criteria.setMandatory( strMandatoryCriteria != null );
            criteria.setTitle( strCriteriaTitle );
            criteria.setAvailableValues( listValues );
            criteria.setType( type );
            listSynthesisCriteria.add( criteria );
		}
    	
    	config.setListSynthesisCriteria( listSynthesisCriteria );
    	return null;
    }

    /**
     * 
     * @param config the config
     * @param request request
     * @param locale locale
     * @param plugin plugin
     * @return error url
     */
    private String getCriteriasData( TaskEvaluationConfig config, HttpServletRequest request, Locale locale,
        Plugin plugin )
    {
        List<TaskEvaluationCriteria> criterias = new ArrayList<TaskEvaluationCriteria>(  );
        String[] tabCriteriasChecked = request.getParameterValues( PARAMETER_CRITERIAS_CHECKED );

        if ( tabCriteriasChecked != null )
        {
            //save criteria
            for ( int cpt = 0; cpt < tabCriteriasChecked.length; cpt++ )
            {
                String strIdCriteria = request.getParameter( PARAMETER_ID_CRITERIA + "_" + tabCriteriasChecked[cpt] );
                String strMandatoryCriteria = request.getParameter( PARAMETER_MANDATORY_CRITERIA + "_" +
                        tabCriteriasChecked[cpt] );
                String strCriteriaTitle = request.getParameter( PARAMETER_CRITERIA_TITLE + "_" +
                        tabCriteriasChecked[cpt] );
                String strCriteriaBestScore = request.getParameter( PARAMETER_CRITERIA_BEST_SCORE + "_" +
                        tabCriteriasChecked[cpt] );
                int nCriteriaBestScore = WorkflowUtils.convertStringToInt( strCriteriaBestScore );
                Object[] tabCriteriaNum = { cpt + 1 };

                if ( ( ( strCriteriaTitle == null ) || strCriteriaTitle.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) )
                {
                    return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_CRITERIA_TITLE,
                        tabCriteriaNum, AdminMessage.TYPE_STOP );
                }
                else if ( ( ( strCriteriaBestScore == null ) ||
                        strCriteriaBestScore.trim(  ).equals( WorkflowUtils.EMPTY_STRING ) ) )
                {
                    return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_CRITERIA_BEST_SCORE,
                        tabCriteriaNum, AdminMessage.TYPE_STOP );
                }
                else if ( nCriteriaBestScore < 0 )
                {
                    return AdminMessageService.getMessageUrl( request, MESSAGE_NUMERIC_CRITERIA_BEST_SCORE,
                        tabCriteriaNum, AdminMessage.TYPE_STOP );
                }

                TaskEvaluationCriteria criteria = new TaskEvaluationCriteria(  );
                criteria.setIdCriteria( Integer.parseInt( strIdCriteria ) );
                criteria.setIdTask( this.getId(  ) );
                criteria.setMandatory( strMandatoryCriteria != null );
                criteria.setTitle( strCriteriaTitle );
                criteria.setBestScore( strCriteriaBestScore );
                criterias.add( criteria );
            }
        }

        config.setEvaluationsCriteria( criterias );

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getTitle( Plugin plugin, Locale locale )
    {
        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );

        if ( config != null )
        {
            return config.getTaskTitle(  );
        }

        return WorkflowUtils.EMPTY_STRING;
    }

    /**
     * {@inheritDoc}
     */
    public ReferenceList getTaskFormEntries( Plugin plugin, Locale locale )
    {
        TaskEvaluationConfig config = TaskEvaluationConfigHome.findByPrimaryKey( this.getId(  ), plugin );
        ReferenceList refListEntriesForm = null;

        if ( config != null )
        {
            refListEntriesForm = new ReferenceList(  );
            refListEntriesForm.addItem( PARAMETER_SUMMARY + "_" + this.getId(  ), config.getSummaryTitle(  ) );
            refListEntriesForm.addItem( PARAMETER_FINAL_NOTE + "_" + this.getId(  ), config.getFinalNoteTitle(  ) );

            for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
            {
                refListEntriesForm.addItem( PARAMETER_ID_CRITERIA + "_" + this.getId(  ) + "_" +
                    criteria.getIdCriteria(  ), criteria.getTitle(  ) );
            }
            
            for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
            {
            	refListEntriesForm.addItem( PARAMETER_ID_SYNTHESIS_CRITERIA + "_" + this.getId(  ) + "_" +
                        criteria.getIdCriteria(  ), criteria.getTitle(  ) );
            }
        }

        return refListEntriesForm;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTaskForActionAutomatic(  )
    {
        return false;
    }
}
