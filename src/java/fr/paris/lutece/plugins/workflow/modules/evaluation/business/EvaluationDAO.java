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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 *
 *class EvaluationDAO
 *
 */
public class EvaluationDAO implements IEvaluationDAO
{
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_history,id_task,summary,final_note  " +
        "FROM task_evaluation_evaluation WHERE id_history=? AND id_task=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO  task_evaluation_evaluation " +
        "(id_history,id_task,summary,final_note )VALUES(?,?,?,?)";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_evaluation_evaluation  WHERE id_history=? AND id_task=?";

    public synchronized void insert( Evaluation evaluationValue, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nPos = 0;

        daoUtil.setInt( ++nPos, evaluationValue.getIdResourceHistory(  ) );
        daoUtil.setInt( ++nPos, evaluationValue.getIdTask(  ) );
        daoUtil.setString( ++nPos, evaluationValue.getSummary(  ) );
        daoUtil.setString( ++nPos, evaluationValue.getFinalNote(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    public Evaluation load( int nIdHistory, int nIdTask, Plugin plugin )
    {
        Evaluation evaluationValue = null;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        int nPos = 0;
        daoUtil.setInt( ++nPos, nIdHistory );
        daoUtil.setInt( ++nPos, nIdTask );

        nPos = 0;

        daoUtil.executeQuery(  );

        if ( daoUtil.next(  ) )
        {
            evaluationValue = new Evaluation(  );
            evaluationValue.setIdResourceHistory( daoUtil.getInt( ++nPos ) );
            evaluationValue.setIdTask( daoUtil.getInt( ++nPos ) );
            evaluationValue.setSummary( daoUtil.getString( ++nPos ) );
            evaluationValue.setFinalNote( daoUtil.getString( ++nPos ) );
        }

        daoUtil.free(  );

        return evaluationValue;
    }

    public void delete( int nIdHistory, int nIdTask, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        int nPos = 0;
        daoUtil.setInt( ++nPos, nIdHistory );
        daoUtil.setInt( ++nPos, nIdTask );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
