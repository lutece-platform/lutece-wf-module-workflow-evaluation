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

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.SynthesisCriteria.Pair;
import fr.paris.lutece.plugins.workflow.modules.evaluation.business.SynthesisCriteria.Type;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 *
 *   SynthesisCriteriaDAO
 *
 */
public class SynthesisCriteriaDAO implements ISynthesisCriteriaDAO
{
	// task_evaluation_synthesis_value
	// task_evaluation_synthesis
    private static final String SQL_QUERY_NEW_PK = "SELECT MAX(id_criteria) FROM task_evaluation_synthesis";
    private static final String SQL_QUERY_NEW_PK_VALUE = "SELECT MAX(id_value) FROM task_evaluation_synthesis_value";
    
    private static final String SQL_QUERY_NEW_POSITION = "SELECT MAX(criteria_position) FROM task_evaluation_synthesis where id_task=? ";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = " SELECT id_criteria,id_task,title,is_mandatory,criteria_position,criteria_type " +
        "FROM task_evaluation_synthesis  WHERE id_criteria=?";
    private static final String SQL_QUERY_FIND_VALUES = "SELECT id_value, criteria_value FROM task_evaluation_synthesis_value " +
    		"WHERE id_criteria = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO  task_evaluation_synthesis( " +
        "id_criteria,id_task,title,is_mandatory,criteria_position,criteria_type )" + "VALUES (?,?,?,?,?,?)";
    private static final String SQL_QUERY_INSERT_VALUE = "INSERT INTO  task_evaluation_synthesis_value( " +
    "id_value, id_criteria, criteria_value )" + "VALUES (?,?,?)";
    private static final String SQL_QUERY_UPDATE_VALUE = "UPDATE task_evaluation_synthesis_value " +
    "SET id_criteria = ?, criteria_value = ? WHERE id_value = ?";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_evaluation_synthesis " +
        "SET id_task=?,title=?,is_mandatory=?,criteria_position=?,criteria_type=?" + " WHERE id_criteria=?";
    private static final String SQL_QUERY_DELETE_VALUE_BY_ID_TASK = "DELETE FROM task_evaluation_synthesis_value " +
    		"WHERE id_criteria IN ( SELECT c.id_criteria FROM task_evaluation_synthesis c WHERE c.id_task=? )";
    private static final String SQL_QUERY_DELETE_BY_ID_TASK = "DELETE FROM task_evaluation_synthesis  WHERE id_task=? ";
    private static final String SQL_QUERY_SELECT_BY_ID_TASK = " SELECT id_criteria,id_task,title,is_mandatory,criteria_position,criteria_type " +
        "FROM task_evaluation_synthesis  WHERE id_task=?";
    private static final String SQL_ORDER_BY_POSITION = " ORDER BY criteria_position ASC";
    private static final String SQL_QUERY_DELETE_VALUE_BY_ID_CRITERIA = "DELETE FROM task_evaluation_synthesis_value " +
    		"WHERE id_criteria = ?";
    private static final String SQL_QUERY_DELETE_VALUE_BY_ID_VALUE = "DELETE FROM task_evaluation_synthesis_value " +
	"WHERE id_value = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_evaluation_synthesis  WHERE id_criteria=? ";

    /**
     * New primary key for values
     * @param plugin the plugin
     * @return new pk
     */
    private int newPrimaryKeyValue( Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK_VALUE, plugin );
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
     * Generates a new primary key
     *
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
     *
     * @param plugin the plugin
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
    public synchronized void insert( SynthesisCriteria criteria, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        int nPos = 0;

        criteria.setIdCriteria( newPrimaryKey( plugin ) );

        daoUtil.setInt( ++nPos, criteria.getIdCriteria(  ) );
        daoUtil.setInt( ++nPos, criteria.getIdTask(  ) );
        daoUtil.setString( ++nPos, criteria.getTitle(  ) );
        daoUtil.setBoolean( ++nPos, criteria.isMandatory(  ) );
        daoUtil.setInt( ++nPos, newPosition( plugin, criteria.getIdTask(  ) ) );
        daoUtil.setString( ++nPos, criteria.getType(  ).toString(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
        
        // insert values
        for ( Pair pair : criteria.getAvailableValues(  ) )
        {
        	insertValue( pair, criteria.getIdCriteria(  ), plugin );
        }
    }
    
    /**
     * Inserts a pair
     * @param pair pair
     * @param nIdCriteria criteria id
     * @param plugin the plugin
     */
    private void insertValue( Pair pair, int nIdCriteria, Plugin plugin )
    {
    	int nNewPkValue = newPrimaryKeyValue( plugin );
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_VALUE, plugin );
    	
    	int nPos = 0;
    	
    	daoUtil.setInt( ++nPos, nNewPkValue );
    	daoUtil.setInt( ++nPos, nIdCriteria );
    	daoUtil.setString( ++nPos, pair.getTitle(  ) );
    	
    	daoUtil.executeUpdate(  );
    	
    	daoUtil.free(  );
    }
    
    /**
     * Updates the value
     * @param pair the pair
     * @param nIdCriteria the criteria
     * @param plugin the plugin
     */
    private void updateValue( Pair pair, int nIdCriteria, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_VALUE, plugin );
    	
    	int nPos = 0;
    	
    	daoUtil.setInt( ++nPos, nIdCriteria );
    	daoUtil.setString( ++nPos, pair.getTitle(  ) );
    	daoUtil.setInt( ++nPos, Integer.parseInt( pair.getValue(  ) ) );
    	
    	daoUtil.executeUpdate(  );
    	
    	daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void store( SynthesisCriteria criteria, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nPos = 0;

        daoUtil.setInt( ++nPos, criteria.getIdTask(  ) );
        daoUtil.setString( ++nPos, criteria.getTitle(  ) );
        daoUtil.setBoolean( ++nPos, criteria.isMandatory(  ) );
        daoUtil.setInt( ++nPos, criteria.getPosition(  ) );
        daoUtil.setString( ++nPos, criteria.getType(  ).toString(  ) );
        daoUtil.setInt( ++nPos, criteria.getIdCriteria(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
        
        List<Pair> oldValues = loadValuesForCriteria( criteria.getIdCriteria(  ), plugin );
        
        for ( Pair pair : oldValues )
        {
        	boolean bExists = false;
        	Iterator<Pair> itPair = criteria.getAvailableValues(  ).iterator(  );
        	
        	while ( itPair.hasNext(  ) && !bExists )
        	{
        		if ( pair.getValue(  ).equals( itPair.next(  ).getValue(  ) ) )
        		{
        			bExists = true;
        		}
        	}
        	
        	if ( !bExists )
        	{
        		if ( AppLogService.isDebugEnabled(  ) )
        		{
        			AppLogService.debug( "Removing criteria " + pair.getValue(  ) + " : " + pair.getTitle(  ) );
        		}
        		nPos = 0;
        		daoUtil = new DAOUtil( SQL_QUERY_DELETE_VALUE_BY_ID_VALUE, plugin );
        		
        		daoUtil.setInt( ++nPos, Integer.parseInt( pair.getValue(  ) ) );
        		
        		daoUtil.executeUpdate(  );
        		
        		daoUtil.free(  );
        	}
        }
        
        for ( Pair pair :  criteria.getAvailableValues(  ) )
        {
        	if ( StringUtils.isBlank( pair.getValue(  ) ) )
        	{
        		// insert new value
        		insertValue( pair, criteria.getIdCriteria(  ), plugin );
        	}
        	else
        	{
        		// update existing value
        		updateValue( pair, criteria.getIdCriteria(  ), plugin );
        	}
        }
        
    }

    /**
     * {@inheritDoc}
     */
    public SynthesisCriteria load( int nIdCriteria, Plugin plugin )
    {
    	SynthesisCriteria criteria = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );

        daoUtil.setInt( 1, nIdCriteria );

        daoUtil.executeQuery(  );

        int nPos = 0;

        if ( daoUtil.next(  ) )
        {
            criteria = new SynthesisCriteria(  );
            criteria.setIdCriteria( daoUtil.getInt( ++nPos ) );
            criteria.setIdTask( daoUtil.getInt( ++nPos ) );
            criteria.setTitle( daoUtil.getString( ++nPos ) );
            criteria.setMandatory( daoUtil.getBoolean( ++nPos ) );
            criteria.setPosition( daoUtil.getInt( ++nPos ) );
            criteria.setType( Type.valueOf( daoUtil.getString( ++nPos ) ) );
        }

        daoUtil.free(  );

        if ( criteria != null )
        {
        	criteria.setAvailableValues( loadValuesForCriteria( criteria.getIdCriteria(  ), plugin ) );
        }
        
        daoUtil.free(  );
        
        return criteria;
    }
    
    /**
     * Loads final values
     * @param nIdCriteria the criteria
     * @param plugin the plugin
     * @return the list found
     */
    private List<Pair> loadValuesForCriteria( int nIdCriteria, Plugin plugin )
    {
    	List<Pair> listValues = new ArrayList<Pair>(  );
    	DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_VALUES, plugin );
    	daoUtil.setInt( 1, nIdCriteria );
    	
    	daoUtil.executeQuery(  );
    	
    	while ( daoUtil.next(  ) )
    	{
    		Pair pair = new Pair(  );
    		
    		pair.setValue( Integer.toString( daoUtil.getInt( 1 ) ) );
    		pair.setTitle( daoUtil.getString( 2 ) );
    		
    		listValues.add( pair );
    	}
    	
    	daoUtil.free(  );
    	
    	return listValues;
    }

    /**
     * {@inheritDoc}
     */
    public List<SynthesisCriteria> selectByIdTask( int nIdTask, Plugin plugin )
    {
    	SynthesisCriteria criteria = null;
        List<SynthesisCriteria> listCriteria = new ArrayList<SynthesisCriteria>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_TASK + SQL_ORDER_BY_POSITION, plugin );

        daoUtil.setInt( 1, nIdTask );

        daoUtil.executeQuery(  );

        int nPos;

        while ( daoUtil.next(  ) )
        {
            nPos = 0;
            criteria = new SynthesisCriteria(  );
            criteria.setIdCriteria( daoUtil.getInt( ++nPos ) );
            criteria.setIdTask( daoUtil.getInt( ++nPos ) );
            criteria.setTitle( daoUtil.getString( ++nPos ) );
            criteria.setMandatory( daoUtil.getBoolean( ++nPos ) );
            criteria.setPosition( daoUtil.getInt( ++nPos ) );
            criteria.setType( Type.valueOf( daoUtil.getString( ++nPos ) ) );
            criteria.setAvailableValues( loadValuesForCriteria( criteria.getIdCriteria(  ), plugin ) );
            
            listCriteria.add( criteria );
        }

        daoUtil.free(  );
        
        return listCriteria;
    }

    /**
     * {@inheritDoc}
     */
    public void deleteByIdTask( int nIdTask, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil ( SQL_QUERY_DELETE_VALUE_BY_ID_TASK, plugin );
    	daoUtil.setInt( 1, nIdTask );
    	
    	daoUtil.executeUpdate(  );
        daoUtil.free(  );
    	
        daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_ID_TASK, plugin );

        daoUtil.setInt( 1, nIdTask );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    public void delete( int nIdCriteria, Plugin plugin )
    {
    	DAOUtil daoUtil = new DAOUtil ( SQL_QUERY_DELETE_VALUE_BY_ID_CRITERIA, plugin );
    	daoUtil.setInt( 1, nIdCriteria );
    	
    	daoUtil.executeUpdate(  );
        daoUtil.free(  );

        daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );

        daoUtil.setInt( 1, nIdCriteria );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }
}
