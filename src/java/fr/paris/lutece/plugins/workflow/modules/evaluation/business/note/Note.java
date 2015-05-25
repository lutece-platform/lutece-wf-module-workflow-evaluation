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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business.note;

import fr.paris.lutece.plugins.workflowcore.business.IReferenceItem;


/**
 *
 * Note
 *
 */
public class Note implements IReferenceItem
{
    private int _nIdNote;
    private String _strLibelle;
    private String _strValeur;

    /**
     *
     * @return l identifiant de la note
     */
    public int getId(  )
    {
        return _nIdNote;
    }

    /**
     * renseigne l identifiant de la note
     * @param idNote l identifiant de la note
     */
    public void setId( int idNote )
    {
        _nIdNote = idNote;
    }

    /**
     *
     * @return le libelle de la note
     */
    public String getLibelle(  )
    {
        return _strLibelle;
    }

    /**
     * renseigne le libelle de la note
     * @param libelle le libelle de la note
     */
    public void setLibelle( String libelle )
    {
        _strLibelle = libelle;
    }

    /**
     *
     * @return la valeur de la note
     */
    public String getValeur(  )
    {
        return _strValeur;
    }

    /**
     * renseigne la valeur de la note
     * @param valeur la valeur de la note
     */
    public void setValeur( String valeur )
    {
        _strValeur = valeur;
    }

    /**
     * @return le libelle de la note
     */
    public String getName(  )
    {
        return getLibelle(  );
    }
}
