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
package fr.paris.lutece.plugins.workflow.modules.evaluation.service.synthesis;

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.ISynthesisCriteriaDAO;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis.SynthesisCriteria;
import fr.paris.lutece.portal.service.plugin.Plugin;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;


/**
 *
 * SynthesisCriteriaService
 *
 */
public class SynthesisCriteriaService implements ISynthetisCriteriaService
{
    @Inject
    private ISynthesisCriteriaDAO _synthesisCriteriaDAO;

    /**
     * {@inheritDoc}
     */
    @Override
    public SynthesisCriteria findByPrimaryKey( int nId, Plugin plugin )
    {
        return _synthesisCriteriaDAO.load( nId, plugin );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "workflow-evaluation.transactionManager" )
    public void create( SynthesisCriteria criteria, Plugin plugin )
    {
        _synthesisCriteriaDAO.insert( criteria, plugin );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "workflow-evaluation.transactionManager" )
    public void update( SynthesisCriteria criteria, Plugin plugin )
    {
        _synthesisCriteriaDAO.store( criteria, plugin );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SynthesisCriteria> selectByIdTask( int nIdTask, Plugin plugin )
    {
        return _synthesisCriteriaDAO.selectByIdTask( nIdTask, plugin );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "workflow-evaluation.transactionManager" )
    public void removeByIdTask( int nIdTask, Plugin plugin )
    {
        _synthesisCriteriaDAO.deleteByIdTask( nIdTask, plugin );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "workflow-evaluation.transactionManager" )
    public void remove( int nIdCriteria, Plugin plugin )
    {
        _synthesisCriteriaDAO.delete( nIdCriteria, plugin );
    }
}
