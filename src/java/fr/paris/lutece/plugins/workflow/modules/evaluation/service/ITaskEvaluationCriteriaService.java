/*
 * Copyright (c) 2002-2015, Mairie de Paris
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

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.TaskEvaluationCriteria;
import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 *
 * ITaskEvaluationCriteriaService
 *
 */
public interface ITaskEvaluationCriteriaService
{
    /**
     * Find an evaluation criteria
     * @param nId the id
     * @param plugin the plugin
     * @return a {@link TaskEvaluationCriteria}
     */
    TaskEvaluationCriteria findByPrimaryKey( int nId, Plugin plugin );

    /**
     * Create a new {@link TaskEvaluationCriteria}
     * @param criteria a {@link TaskEvaluationCriteria}
     * @param plugin the plugin
     */
    void create( TaskEvaluationCriteria criteria, Plugin plugin );

    /**
     * Update a {@link TaskEvaluationCriteria}
     * @param criteria a {@link TaskEvaluationCriteria}
     * @param plugin the plugin
     */
    void update( TaskEvaluationCriteria criteria, Plugin plugin );

    /**
     * Select a list of {@link TaskEvaluationCriteria} from a given id task
     * @param nIdTask the id task
     * @param plugin the plugin
     * @return a list of {@link TaskEvaluationCriteria}
     */
    List<TaskEvaluationCriteria> selectByIdTask( int nIdTask, Plugin plugin );

    /**
     * Remove from a given id task
     * @param nIdTask the id task
     * @param plugin the plugin
     */
    void removeByIdTask( int nIdTask, Plugin plugin );

    /**
     * Remove a {@link TaskEvaluationCriteria}
     * @param nIdCriteria the id criteria
     * @param plugin the plugin
     */
    void remove( int nIdCriteria, Plugin plugin );
}
