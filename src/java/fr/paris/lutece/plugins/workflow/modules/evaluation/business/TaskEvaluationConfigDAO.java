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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 *
 * TaskEvaluationConfigDAO
 *
 */
public class TaskEvaluationConfigDAO implements ITaskEvaluationConfigDAO
{
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_task,title_task,description_task,summary_title,is_mandatory_summary,title_final_note,is_mandatory_final_note,is_final_note_automatic,best_score_final_note " +
        "FROM task_evaluation_cf  WHERE id_task=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO  task_evaluation_cf( " +
        "id_task,title_task,description_task,summary_title,is_mandatory_summary,title_final_note,is_mandatory_final_note,is_final_note_automatic,best_score_final_note )" +
        "VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_evaluation_cf " +
        "SET id_task=?,title_task=?,description_task=?,summary_title=?,is_mandatory_summary=?,title_final_note=?,is_mandatory_final_note=?,is_final_note_automatic=?,best_score_final_note=?" +
        " WHERE id_task=?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_evaluation_cf  WHERE id_task=? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( TaskEvaluationConfig config, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nPos = 0;

        daoUtil.setInt( ++nPos, config.getIdTask(  ) );
        daoUtil.setString( ++nPos, config.getTaskTitle(  ) );
        daoUtil.setString( ++nPos, config.getTaskDescription(  ) );
        daoUtil.setString( ++nPos, config.getSummaryTitle(  ) );
        daoUtil.setBoolean( ++nPos, config.isMandatorySummary(  ) );
        daoUtil.setString( ++nPos, config.getFinalNoteTitle(  ) );
        daoUtil.setBoolean( ++nPos, config.isMandatoryFinalNote(  ) );
        daoUtil.setBoolean( ++nPos, config.isAutomaticFinalNote(  ) );
        daoUtil.setString( ++nPos, config.getBestScoreFinalNote(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( TaskEvaluationConfig config, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nPos = 0;

        daoUtil.setInt( ++nPos, config.getIdTask(  ) );
        daoUtil.setString( ++nPos, config.getTaskTitle(  ) );
        daoUtil.setString( ++nPos, config.getTaskDescription(  ) );
        daoUtil.setString( ++nPos, config.getSummaryTitle(  ) );
        daoUtil.setBoolean( ++nPos, config.isMandatorySummary(  ) );
        daoUtil.setString( ++nPos, config.getFinalNoteTitle(  ) );
        daoUtil.setBoolean( ++nPos, config.isMandatoryFinalNote(  ) );
        daoUtil.setBoolean( ++nPos, config.isAutomaticFinalNote(  ) );
        daoUtil.setString( ++nPos, config.getBestScoreFinalNote(  ) );
        daoUtil.setInt( ++nPos, config.getIdTask(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskEvaluationConfig load( int nIdTask, Plugin plugin )
    {
        TaskEvaluationConfig config = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );

        daoUtil.setInt( 1, nIdTask );

        daoUtil.executeQuery(  );

        int nPos = 0;

        if ( daoUtil.next(  ) )
        {
            config = new TaskEvaluationConfig(  );
            config.setIdTask( daoUtil.getInt( ++nPos ) );
            config.setTaskTitle( daoUtil.getString( ++nPos ) );
            config.setTaskDescription( daoUtil.getString( ++nPos ) );
            config.setSummaryTitle( daoUtil.getString( ++nPos ) );
            config.setMandatorySummary( daoUtil.getBoolean( ++nPos ) );
            config.setFinalNoteTitle( daoUtil.getString( ++nPos ) );
            config.setMandatoryFinalNote( daoUtil.getBoolean( ++nPos ) );
            config.setAutomaticFinalNote( daoUtil.getBoolean( ++nPos ) );
            config.setBestScoreFinalNote( daoUtil.getString( ++nPos ) );
        }

        daoUtil.free(  );

        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdState, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );

        daoUtil.setInt( 1, nIdState );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
