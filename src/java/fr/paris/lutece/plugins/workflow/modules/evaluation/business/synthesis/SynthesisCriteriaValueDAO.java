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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business.synthesis;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * SynthesisCriteriaValueDAO
 *
 */
public class SynthesisCriteriaValueDAO implements ISynthesisCriteriaValueDAO
{
    private static final String SQL_QUERY_NEW_PK = "SELECT MAX(id_synthesis) FROM evaluation_synthesis_criteria ";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_synthesis, id_history,id_task,id_criteria  " +
        "FROM evaluation_synthesis_criteria WHERE id_history=? AND id_task=? AND id_criteria=?";
    private static final String SQL_QUERY_FIND_VALUES_BY_CRITERIA_ID = "SELECT criteria_value FROM evaluation_synthesis_criteria_value " +
        "WHERE id_synthesis = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO  evaluation_synthesis_criteria " +
        "(id_synthesis, id_history,id_task,id_criteria )VALUES(?,?,?,?)";
    private static final String SQL_QUERY_INSERT_VALUE = "INSERT INTO  evaluation_synthesis_criteria_value (" +
        "id_synthesis, criteria_value) VALUES (?,?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM evaluation_synthesis_criteria  WHERE id_history=? AND id_task=?";
    private static final String SQL_QUERY_DELETE_VALUES = "DELETE FROM evaluation_synthesis_criteria_value " +
        "WHERE id_synthesis IN " +
        "(SELECT e.id_synthesis FROM evaluation_synthesis_criteria e WHERE e.id_history=? AND e.id_task=?)";

    /**
     * Generates a new primary key
     * @param plugin the plugin
     * @return The new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nKey = 1;
        }

        nKey = daoUtil.getInt( 1 ) + 1;
        daoUtil.free(  );

        return nKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( SynthesisCriteriaValue synthesisCriteriaValue, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nPos = 0;

        synthesisCriteriaValue.setId( newPrimaryKey( plugin ) );

        daoUtil.setInt( ++nPos, synthesisCriteriaValue.getId(  ) );
        daoUtil.setInt( ++nPos, synthesisCriteriaValue.getIdResourceHistory(  ) );
        daoUtil.setInt( ++nPos, synthesisCriteriaValue.getIdTask(  ) );
        daoUtil.setInt( ++nPos, synthesisCriteriaValue.getIdCriteria(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );

        for ( String strValue : synthesisCriteriaValue.getValues(  ) )
        {
            daoUtil = new DAOUtil( SQL_QUERY_INSERT_VALUE, plugin );
            daoUtil.setInt( 1, synthesisCriteriaValue.getId(  ) );
            daoUtil.setString( 2, strValue );
            daoUtil.executeUpdate(  );

            daoUtil.free(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SynthesisCriteriaValue load( int nIdHistory, int nIdTask, int nIdCriteria, Plugin plugin )
    {
        SynthesisCriteriaValue criteriaValue = null;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        int nPos = 0;
        daoUtil.setInt( ++nPos, nIdHistory );
        daoUtil.setInt( ++nPos, nIdTask );
        daoUtil.setInt( ++nPos, nIdCriteria );

        nPos = 0;

        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            criteriaValue = new SynthesisCriteriaValue(  );
            criteriaValue.setId( daoUtil.getInt( ++nPos ) );
            criteriaValue.setIdResourceHistory( daoUtil.getInt( ++nPos ) );
            criteriaValue.setIdTask( daoUtil.getInt( ++nPos ) );
            criteriaValue.setIdCriteria( daoUtil.getInt( ++nPos ) );
        }

        daoUtil.free(  );

        if ( criteriaValue != null )
        {
            daoUtil = new DAOUtil( SQL_QUERY_FIND_VALUES_BY_CRITERIA_ID, plugin );

            daoUtil.setInt( 1, criteriaValue.getId(  ) );

            daoUtil.executeQuery(  );

            List<String> values = new ArrayList<String>(  );

            while ( daoUtil.next(  ) )
            {
                values.add( daoUtil.getString( 1 ) );
            }

            daoUtil.free(  );

            criteriaValue.setValues( values );
        }

        return criteriaValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByEvaluation( int nIdHistory, int nIdTask, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_VALUES, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, nIdHistory );
        daoUtil.setInt( nIndex++, nIdTask );

        daoUtil.executeUpdate(  );

        daoUtil.free(  );

        daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        nIndex = 1;

        daoUtil.setInt( nIndex++, nIdHistory );
        daoUtil.setInt( nIndex++, nIdTask );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
