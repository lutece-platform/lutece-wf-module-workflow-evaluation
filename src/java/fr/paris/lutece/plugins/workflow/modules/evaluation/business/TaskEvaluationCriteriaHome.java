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

import java.util.List;


/**
 * NoteHome
 */
public final class TaskEvaluationCriteriaHome
{
    // Static variable pointed at the DAO instance
    private static ITaskEvaluationCriteriaDAO _dao = (ITaskEvaluationCriteriaDAO) SpringContextService.getPluginBean( EvaluationPlugin.PLUGIN_NAME,
            "taskEvaluationCriteriaDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private TaskEvaluationCriteriaHome(  )
    {
    }

    ///////////////////////////////////////////////////////////////////////////
    // Finders

    /**
       * Charge un critere d'evaluation a partir de sa clef primaire
       * @param nId  l'identifiant
       * @param plugin le plugin
       * @return un critere d'evaluation
       */
    public static TaskEvaluationCriteria findByPrimaryKey( int nId, Plugin plugin )
    {
        return _dao.load( nId, plugin );
    }

    /**
     * Insere une nouvelle configuration de type crit�re d'evaluation
     * @param config l objet configuration
     * @param plugin the plugin
     */
    public static void create( TaskEvaluationCriteria criteria, Plugin plugin )
    {
        _dao.insert( criteria, plugin );
    }

    /**
     * met à jour une configuration de type crit�re d'evaluation
     *
     * @param  config instance of config object to update
     * @param plugin the plugin
     */
    public static void update( TaskEvaluationCriteria criteria, Plugin plugin )
    {
        _dao.store( criteria, plugin );
    }

    /**
     * Permet d'obtenir une liste de crit�re d'�valuation pour une tache donn�e
     * @param nIdTask l identifiant de la tache
     * @param plugin le plugin
     * @return une liste de critere d'evaluation
     */
    public static List<TaskEvaluationCriteria> selectByIdTask( int nIdTask, Plugin plugin )
    {
        return _dao.selectByIdTask( nIdTask, plugin );
    }

    /**
     * Supprime une configuration de type crit�re d'evaluation
     * @param nIdTask l identifiant de la tache
     * @param plugin le plugin
     */
    public static void removeByIdTask( int nIdState, Plugin plugin )
    {
        _dao.deleteByIdTask( nIdState, plugin );
    }

    /**
     * Supprime un crit�re d'evaluation
     * @param nIdCriteria l identifiant du critere
     * @param plugin le plugin
     */
    public static void remove( int nIdCriteria, Plugin plugin )
    {
        _dao.delete( nIdCriteria, plugin );
    }
}
