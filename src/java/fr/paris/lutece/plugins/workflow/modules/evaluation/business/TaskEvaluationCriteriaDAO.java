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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  TaskEvaluationCriteriaDAO
 *
 */
public class TaskEvaluationCriteriaDAO implements ITaskEvaluationCriteriaDAO
{
    private static final String SQL_QUERY_NEW_PK = "SELECT MAX(id_criteria) FROM task_evaluation_criteria";
    private static final String SQL_QUERY_NEW_POSITION = "SELECT MAX(criteria_position) FROM task_evaluation_criteria where id_task=? ";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = " SELECT id_criteria,id_task,title,is_mandatory,best_score,criteria_position " +
        "FROM task_evaluation_criteria  WHERE id_criteria=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO  task_evaluation_criteria( " +
        "id_criteria,id_task,title,is_mandatory,best_score,criteria_position )" + "VALUES (?,?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_evaluation_criteria " +
        "SET id_task=?,title=?,is_mandatory=?,best_score=?,criteria_position=?" + " WHERE id_criteria=?";
    private static final String SQL_QUERY_DELETE_BY_ID_TASK = "DELETE FROM task_evaluation_criteria  WHERE id_task=? ";
    private static final String SQL_QUERY_SELECT_BY_ID_TASK = " SELECT id_criteria,id_task,title,is_mandatory,best_score,criteria_position " +
        "FROM task_evaluation_criteria  WHERE id_task=?";
    private static final String SQL_ORDER_BY_POSITION = " ORDER BY criteria_position ASC";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_evaluation_criteria  WHERE id_criteria=? ";

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
     * Generates a new position
     * @param plugin the plugin
     * @param nIdTask the id task
     * @return The new position
     */
    private int newPosition( Plugin plugin, int nIdTask )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_POSITION, plugin );
        daoUtil.setInt( 1, nIdTask );
        daoUtil.executeQuery(  );

        int nPosition;

        if ( !daoUtil.next(  ) )
        {
            // if the table is empty
            nPosition = 1;
        }
        else
        {
            nPosition = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( TaskEvaluationCriteria criteria, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nPos = 0;

        criteria.setIdCriteria( newPrimaryKey( plugin ) );

        daoUtil.setInt( ++nPos, criteria.getIdCriteria(  ) );
        daoUtil.setInt( ++nPos, criteria.getIdTask(  ) );
        daoUtil.setString( ++nPos, criteria.getTitle(  ) );
        daoUtil.setBoolean( ++nPos, criteria.isMandatory(  ) );
        daoUtil.setString( ++nPos, criteria.getBestScore(  ) );
        daoUtil.setInt( ++nPos, newPosition( plugin, criteria.getIdTask(  ) ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( TaskEvaluationCriteria criteria, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nPos = 0;

        daoUtil.setInt( ++nPos, criteria.getIdTask(  ) );
        daoUtil.setString( ++nPos, criteria.getTitle(  ) );
        daoUtil.setBoolean( ++nPos, criteria.isMandatory(  ) );
        daoUtil.setString( ++nPos, criteria.getBestScore(  ) );
        daoUtil.setInt( ++nPos, criteria.getPosition(  ) );
        daoUtil.setInt( ++nPos, criteria.getIdCriteria(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskEvaluationCriteria load( int nIdCriteria, Plugin plugin )
    {
        TaskEvaluationCriteria criteria = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );

        daoUtil.setInt( 1, nIdCriteria );

        daoUtil.executeQuery(  );

        int nPos = 0;

        if ( daoUtil.next(  ) )
        {
            criteria = new TaskEvaluationCriteria(  );
            criteria.setIdCriteria( daoUtil.getInt( ++nPos ) );
            criteria.setIdTask( daoUtil.getInt( ++nPos ) );
            criteria.setTitle( daoUtil.getString( ++nPos ) );
            criteria.setMandatory( daoUtil.getBoolean( ++nPos ) );
            criteria.setBestScore( daoUtil.getString( ++nPos ) );
            criteria.setPosition( daoUtil.getInt( ++nPos ) );
        }

        daoUtil.free(  );

        return criteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskEvaluationCriteria> selectByIdTask( int nIdTask, Plugin plugin )
    {
        TaskEvaluationCriteria criteria = null;
        List<TaskEvaluationCriteria> listCriteria = new ArrayList<TaskEvaluationCriteria>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_TASK + SQL_ORDER_BY_POSITION, plugin );

        daoUtil.setInt( 1, nIdTask );

        daoUtil.executeQuery(  );

        int nPos;

        while ( daoUtil.next(  ) )
        {
            nPos = 0;
            criteria = new TaskEvaluationCriteria(  );
            criteria.setIdCriteria( daoUtil.getInt( ++nPos ) );
            criteria.setIdTask( daoUtil.getInt( ++nPos ) );
            criteria.setTitle( daoUtil.getString( ++nPos ) );
            criteria.setMandatory( daoUtil.getBoolean( ++nPos ) );
            criteria.setBestScore( daoUtil.getString( ++nPos ) );
            criteria.setPosition( daoUtil.getInt( ++nPos ) );
            listCriteria.add( criteria );
        }

        daoUtil.free(  );

        return listCriteria;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByIdTask( int nIdTask, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_TASK, plugin );

        daoUtil.setInt( 1, nIdTask );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdCriteria, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );

        daoUtil.setInt( 1, nIdCriteria );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
