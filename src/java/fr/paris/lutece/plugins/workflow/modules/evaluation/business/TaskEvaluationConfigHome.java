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

import fr.paris.lutece.plugins.workflow.modules.evaluation.service.EvaluationPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.Iterator;
import java.util.List;


/**
 * TaskEvaluationExpertConfigHome
 */
public final class TaskEvaluationConfigHome
{
    // Static variable pointed at the DAO instance
    private static ITaskEvaluationConfigDAO _dao = (ITaskEvaluationConfigDAO) SpringContextService.getPluginBean( EvaluationPlugin.PLUGIN_NAME,
            "taskEvaluationConfigDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private TaskEvaluationConfigHome(  )
    {
    }

    /**
     * Insere une nouvelle configuration de type evaluation
     *
     * @param config l objet configuration
     * @param plugin the plugin
     */
    public static void create( TaskEvaluationConfig config, Plugin plugin )
    {
        _dao.insert( config, plugin );
        //create criteria criteria associated
        TaskEvaluationCriteriaHome.removeByIdTask( config.getIdTask(  ), plugin );

        for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
        {
            TaskEvaluationCriteriaHome.create( criteria, plugin );
        }
        
        // create synthesis criteria associated
        SynthesisCriteriaHome.removeByIdTask( config.getIdTask(  ), plugin );
        
        for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
        {
        	SynthesisCriteriaHome.create( criteria, plugin );
        }
    }

    /**
     * met à jour une configuration de type evaluation
     *
     * @param  config instance of config object to update
     * @param plugin the plugin
     */
    public static void update( TaskEvaluationConfig config, Plugin plugin )
    {
        _dao.store( config, plugin );

        //update criterias associated
        List<TaskEvaluationCriteria> taskEvaluationCriteriaOld = TaskEvaluationCriteriaHome.selectByIdTask( config.getIdTask(  ),
                plugin );

        for ( TaskEvaluationCriteria criteriaOld : taskEvaluationCriteriaOld )
        {
            boolean isExist = false;
            Iterator<TaskEvaluationCriteria> itCriteria = config.getEvaluationsCriteria(  ).iterator(  );

            while ( itCriteria.hasNext(  ) && !isExist )
            {
                if ( criteriaOld.getIdCriteria(  ) ==  itCriteria.next(  ).getIdCriteria(  ) )
                {
                    isExist = true;
                }
            }

            if ( !isExist )
            {
                TaskEvaluationCriteriaHome.remove( criteriaOld.getIdCriteria(  ), plugin );
            }
        }
        
        // update synthesis criterias associated
        List<SynthesisCriteria> synthesisCriteriaOld = SynthesisCriteriaHome.selectByIdTask( config.getIdTask(  ),
        		plugin);
        for ( SynthesisCriteria criteriaOld : synthesisCriteriaOld )
        {
        	boolean bExists = false;
        	Iterator<SynthesisCriteria> itCriteria = config.getListSynthesisCriteria(  ).iterator(  );
        	
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
        		SynthesisCriteriaHome.remove( criteriaOld.getIdCriteria(  ), plugin );
        	}
        }

        for ( TaskEvaluationCriteria criteria : config.getEvaluationsCriteria(  ) )
        {
            if ( criteria.getIdCriteria(  ) != -1 )
            {
                TaskEvaluationCriteriaHome.update( criteria, plugin );
            }
            else
            {
                TaskEvaluationCriteriaHome.create( criteria, plugin );
            }
        }
        
        for ( SynthesisCriteria criteria : config.getListSynthesisCriteria(  ) )
        {
        	if ( criteria.getIdCriteria() > 0 )
        	{
        		SynthesisCriteriaHome.update( criteria, plugin );
        	}
        	else
        	{
        		SynthesisCriteriaHome.create( criteria, plugin );
        	}
        }
    }

    /**
     * Supprime une configuration de type evaluation
     * @param nIdConfig l identifiant de la configuration
     * @param plugin le plugin
     */
    public static void remove( int nIdTask, Plugin plugin )
    {
    	//delete all criterias associated
        TaskEvaluationCriteriaHome.removeByIdTask( nIdTask, plugin );
        // delete all synthesis criterias
        SynthesisCriteriaHome.removeByIdTask( nIdTask, plugin);
        _dao.delete( nIdTask, plugin );
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
     * charge une configuration de type evaluation
     * @param nIdConfig the config id
     * @param plugin the plugin
     * @return une configuration
     *
     */
    public static TaskEvaluationConfig findByPrimaryKey( int nIdTask, Plugin plugin )
    {
        TaskEvaluationConfig taskEvaluationConfig = _dao.load( nIdTask, plugin );

        if ( taskEvaluationConfig != null )
        {
            List<TaskEvaluationCriteria> listCriterias = TaskEvaluationCriteriaHome.selectByIdTask( taskEvaluationConfig.getIdTask(  ),
                    plugin );
            taskEvaluationConfig.setEvaluationsCriteria( listCriterias );
            List<SynthesisCriteria> listSynthesisCriteria = SynthesisCriteriaHome.selectByIdTask( taskEvaluationConfig.getIdTask(  ),
                    plugin );
            taskEvaluationConfig.setListSynthesisCriteria( listSynthesisCriteria );
        }

        return taskEvaluationConfig;
    }
}
