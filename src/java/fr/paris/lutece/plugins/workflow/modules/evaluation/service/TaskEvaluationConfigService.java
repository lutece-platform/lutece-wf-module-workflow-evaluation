/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.SynthesisCriteria;
import fr.paris.lutece.plugins.workflow.modules.evaluation.service.synthesis.ISynthetisCriteriaService;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfig;
import fr.paris.lutece.plugins.workflowcore.service.config.TaskConfigService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;


/**
 *
 * TaskEvaluationConfigService
 *
 */
public class TaskEvaluationConfigService extends TaskConfigService
{
    public static final String BEAN_SERVICE = "workflow-evaluation.taskEvaluationConfigService";
    @Inject
    private ISynthetisCriteriaService _synthesisCriteriaService;
    @Inject
    private ITaskEvaluationCriteriaService _taskEvaluationCriteriaService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( EvaluationPlugin.BEAN_TRANSACTION_MANAGER )
    public void create( ITaskConfig config )
    {
        super.create( config );

        // Create criteria criteria associated
        Plugin plugin = EvaluationPlugin.getPlugin(  );
        _taskEvaluationCriteriaService.removeByIdTask( config.getIdTask(  ), plugin );

        TaskEvaluationConfig evaluationConfig = getConfigBean( config );

        if ( evaluationConfig != null )
        {
            for ( TaskEvaluationCriteria criteria : evaluationConfig.getEvaluationsCriteria(  ) )
            {
                _taskEvaluationCriteriaService.create( criteria, plugin );
            }

            // create synthesis criteria associated
            _synthesisCriteriaService.removeByIdTask( config.getIdTask(  ), plugin );

            for ( SynthesisCriteria criteria : evaluationConfig.getListSynthesisCriteria(  ) )
            {
                _synthesisCriteriaService.create( criteria, plugin );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( EvaluationPlugin.BEAN_TRANSACTION_MANAGER )
    public void update( ITaskConfig config )
    {
        super.update( config );

        // Update criterias associated
        Plugin plugin = EvaluationPlugin.getPlugin(  );
        List<TaskEvaluationCriteria> taskEvaluationCriteriaOld = _taskEvaluationCriteriaService.selectByIdTask( config.getIdTask(  ),
                plugin );

        TaskEvaluationConfig evaluationConfig = getConfigBean( config );

        for ( TaskEvaluationCriteria criteriaOld : taskEvaluationCriteriaOld )
        {
            boolean isExist = false;
            Iterator<TaskEvaluationCriteria> itCriteria = evaluationConfig.getEvaluationsCriteria(  ).iterator(  );

            while ( itCriteria.hasNext(  ) && !isExist )
            {
                if ( criteriaOld.getIdCriteria(  ) == itCriteria.next(  ).getIdCriteria(  ) )
                {
                    isExist = true;
                }
            }

            if ( !isExist )
            {
                _taskEvaluationCriteriaService.remove( criteriaOld.getIdCriteria(  ), plugin );
            }
        }

        // update synthesis criterias associated
        List<SynthesisCriteria> synthesisCriteriaOld = _synthesisCriteriaService.selectByIdTask( config.getIdTask(  ),
                plugin );

        for ( SynthesisCriteria criteriaOld : synthesisCriteriaOld )
        {
            boolean bExists = false;
            Iterator<SynthesisCriteria> itCriteria = evaluationConfig.getListSynthesisCriteria(  ).iterator(  );

            while ( itCriteria.hasNext(  ) && !bExists )
            {
                if ( criteriaOld.getIdCriteria(  ) == itCriteria.next(  ).getIdCriteria(  ) )
                {
                    bExists = true;
                }
            }

            if ( !bExists )
            {
                if ( AppLogService.isDebugEnabled(  ) )
                {
                    AppLogService.debug( "Removing criteria " + criteriaOld.getIdCriteria(  ) );
                }

                _synthesisCriteriaService.remove( criteriaOld.getIdCriteria(  ), plugin );
            }
        }

        for ( TaskEvaluationCriteria criteria : evaluationConfig.getEvaluationsCriteria(  ) )
        {
            if ( criteria.getIdCriteria(  ) != -1 )
            {
                _taskEvaluationCriteriaService.update( criteria, plugin );
            }
            else
            {
                _taskEvaluationCriteriaService.create( criteria, plugin );
            }
        }

        for ( SynthesisCriteria criteria : evaluationConfig.getListSynthesisCriteria(  ) )
        {
            if ( criteria.getIdCriteria(  ) > 0 )
            {
                _synthesisCriteriaService.update( criteria, plugin );
            }
            else
            {
                _synthesisCriteriaService.create( criteria, plugin );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( EvaluationPlugin.BEAN_TRANSACTION_MANAGER )
    public void remove( int nIdTask )
    {
        // Delete all criterias associated
        Plugin plugin = EvaluationPlugin.getPlugin(  );
        _taskEvaluationCriteriaService.removeByIdTask( nIdTask, plugin );
        // Delete all synthesis criterias
        _synthesisCriteriaService.removeByIdTask( nIdTask, plugin );
        super.remove( nIdTask );
    }

    // Finders

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T findByPrimaryKey( int nIdTask )
    {
        TaskEvaluationConfig taskEvaluationConfig = super.findByPrimaryKey( nIdTask );

        if ( taskEvaluationConfig != null )
        {
            Plugin plugin = EvaluationPlugin.getPlugin(  );
            List<TaskEvaluationCriteria> listCriterias = _taskEvaluationCriteriaService.selectByIdTask( taskEvaluationConfig.getIdTask(  ),
                    plugin );
            taskEvaluationConfig.setEvaluationsCriteria( listCriterias );

            List<SynthesisCriteria> listSynthesisCriteria = _synthesisCriteriaService.selectByIdTask( taskEvaluationConfig.getIdTask(  ),
                    plugin );
            taskEvaluationConfig.setListSynthesisCriteria( listSynthesisCriteria );
        }

        return (T) taskEvaluationConfig;
    }
}
