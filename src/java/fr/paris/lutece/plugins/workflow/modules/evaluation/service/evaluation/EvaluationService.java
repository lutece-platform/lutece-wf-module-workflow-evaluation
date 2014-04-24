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
package fr.paris.lutece.plugins.workflow.modules.evaluation.service.evaluation;

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.evaluation.Evaluation;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.evaluation.EvaluationCriteriaValue;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.evaluation.IEvaluationDAO;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.SynthesisCriteriaValue;
import fr.paris.lutece.plugins.workflow.modules.evaluation.service.EvaluationPlugin;
import fr.paris.lutece.plugins.workflow.modules.evaluation.service.synthesis.ISynthesisCriteriaValueService;
import fr.paris.lutece.portal.service.plugin.Plugin;

import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


/**
 *
 * EvaluationService
 *
 */
public class EvaluationService implements IEvaluationService
{
    @Inject
    private IEvaluationDAO _evaluationDAO;
    @Inject
    private IEvaluationCriteriaValueService _evaluationCriteriaValueService;
    @Inject
    private ISynthesisCriteriaValueService _synthesisCriteriaValueService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( EvaluationPlugin.BEAN_TRANSACTION_MANAGER )
    public void create( Evaluation evaluation, Plugin plugin )
    {
        _evaluationDAO.insert( evaluation, plugin );

        if ( evaluation.getEvaluationCriteriaValues(  ) != null )
        {
            for ( EvaluationCriteriaValue criteriaValue : evaluation.getEvaluationCriteriaValues(  ) )
            {
                _evaluationCriteriaValueService.create( criteriaValue, plugin );
            }
        }

        if ( evaluation.getSynthesisValues(  ) != null )
        {
            for ( SynthesisCriteriaValue criteriaValue : evaluation.getSynthesisValues(  ) )
            {
                _synthesisCriteriaValueService.create( criteriaValue, plugin );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( EvaluationPlugin.BEAN_TRANSACTION_MANAGER )
    public void remove( int nIdHistory, int nIdTask, Plugin plugin )
    {
        _evaluationDAO.delete( nIdHistory, nIdTask, plugin );
        _synthesisCriteriaValueService.removeByEvaluation( nIdHistory, nIdTask, plugin );
        _evaluationCriteriaValueService.removeByEvaluation( nIdHistory, nIdTask, plugin );
    }

    // Finders

    /**
     * {@inheritDoc}
     */
    @Override
    public Evaluation findByPrimaryKey( int nIdHistory, int nIdTask, Plugin plugin )
    {
        return _evaluationDAO.load( nIdHistory, nIdTask, plugin );
    }
}
