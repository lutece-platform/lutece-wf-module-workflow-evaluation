/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.workflow.modules.evaluation.business.TaskEvaluationCriteria;

import java.util.List;


/**
 * Criteria like {@link Type#CHECKBOX}, {@link Type#RADIOBUTTON},
 * {@link Type#TEXT}, {@link Type#DROPDOWNLIST}.
 * @see Pair
 * @see Type
 * @see SynthesisCriteriaValue
 *
 */
public class SynthesisCriteria
{
    private int _nIdCriteria;
    private List<Pair> _availableValues;
    private String _strTitle;
    private boolean _bMandatory;
    private int _nPosition;
    private int _nIdTask;
    private Type _type;

    /**
     * IdCriteria
     * @return IdCriteria
     */
    public int getIdCriteria(  )
    {
        return _nIdCriteria;
    }

    /**
     * IdCriteria
     * @param nIdCriteria IdCriteria
     */
    public void setIdCriteria( int nIdCriteria )
    {
        _nIdCriteria = nIdCriteria;
    }

    /**
     * Avaiblable Values (for checkbox etc.)
     * @return values
     */
    public List<Pair> getAvailableValues(  )
    {
        return _availableValues;
    }

    /**
     * Sets the available values (for checkbox etc.)
     * @param availableValues values
     */
    public void setAvailableValues( List<Pair> availableValues )
    {
        _availableValues = availableValues;
    }

    /**
     * Title
     * @return title
     */
    public String getTitle(  )
    {
        return _strTitle;
    }

    /**
     * Sets the title
     * @param strTitle title
     */
    public void setTitle( String strTitle )
    {
        _strTitle = strTitle;
    }

    /**
     * <code>true</code> if criteria is mandatory, <code>false</code> otherwise.
     * @return <code>true</code> if criteria is mandatory, <code>false</code> otherwise.
     */
    public boolean isMandatory(  )
    {
        return _bMandatory;
    }

    /**
     * Sets if criteria is mandatory
     * @param bMandatory <code>true</code> if criteria is mandatory, <code>false</code> otherwise.
     */
    public void setMandatory( boolean bMandatory )
    {
        _bMandatory = bMandatory;
    }

    /**
     * The position
     * FIXME : never used (copied form {@link TaskEvaluationCriteria}
     * @return the position
     */
    public int getPosition(  )
    {
        return _nPosition;
    }

    /**
     * Sets the position
     * @param nPosition the position
     */
    public void setPosition( int nPosition )
    {
        _nPosition = nPosition;
    }

    /**
     * Task id
     * @return task id
     */
    public int getIdTask(  )
    {
        return _nIdTask;
    }

    /**
     * Sets task id
     * @param nIdTask task id
     */
    public void setIdTask( int nIdTask )
    {
        _nIdTask = nIdTask;
    }

    /**
     * Type
     * @return the type
     */
    public Type getType(  )
    {
        return _type;
    }

    /**
     * Sets the type
     * @param type the type
     */
    public void setType( Type type )
    {
        _type = type;
    }

    /**
     * Pair value / title
     *
     */
    public static final class Pair
    {
        private String _strValue;
        private String _strTitle;

        /**
             * Gets Programmatic value
             * @return Programmatic value
             */
        public String getValue(  )
        {
            return _strValue;
        }

        /**
             * Programmatic value
             * @param strValue the value
             */
        public void setValue( String strValue )
        {
            _strValue = strValue;
        }

        /**
             * Title : displayable value
             * @return strTitle title
             */
        public String getTitle(  )
        {
            return _strTitle;
        }

        /**
             * Title : displayable value
             * @param strTitle title
             */
        public void setTitle( String strTitle )
        {
            _strTitle = strTitle;
        }
    }

    /**
     * Type of the criteria.
     *
     */
    public static enum Type
    {
    	/**
         * CHECKBOX has multiple available values.
         */
        CHECKBOX,
        /**
         * Text has no available value
         */
        TEXT,
        /**
         * Radio button has multiple available values
         */
        RADIOBUTTON,
        /**
         * drop down list has multiple available values
         */
        DROPDOWNLIST;

    	private static final String MESSAGE_PREFIX = "module.workflow.evaluation.synthesis.";

        /**
         * Private constructor
         */
        private Type(  )
        {
        }

        /**
         * Gets i18n key for this type
         * @return i18n key
         */
        public String getI18nKey(  )
        {
            return MESSAGE_PREFIX + this.toString(  ).toLowerCase(  );
        }
    }
}
