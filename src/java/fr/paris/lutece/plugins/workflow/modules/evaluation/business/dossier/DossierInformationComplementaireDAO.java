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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business.dossier;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for DossierInformationComplementaireDAO
 */
public final class DossierInformationComplementaireDAO implements IDossierInformationComplementaireDAO
{
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT id_dossier,rapporteur,note FROM information_complementaire" +
        " where id_dossier=? and type_ressource=?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO information_complementaire(id,id_dossier,rapporteur,note,type_ressource) VALUES (?,?,?,?,?)";
    private static final String SQL_QUERY_NEW_PK = "SELECT MAX(id) FROM information_complementaire";

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
    public synchronized void insert( DossierInformationComplementaire dossierInformationComplementaire, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );

        int nPos = 0;

        daoUtil.setInt( ++nPos, newPrimaryKey( plugin ) );
        daoUtil.setInt( ++nPos, dossierInformationComplementaire.getIdDossier(  ) );
        daoUtil.setString( ++nPos, dossierInformationComplementaire.getRapporteur(  ) );
        daoUtil.setString( ++nPos, dossierInformationComplementaire.getNote(  ) );
        daoUtil.setString( ++nPos, dossierInformationComplementaire.getTypeRessource(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DossierInformationComplementaire> load( int nId, String typeRessource, Plugin plugin )
    {
        List<DossierInformationComplementaire> listDossierInformationComplementaire = new ArrayList<DossierInformationComplementaire>(  );
        DossierInformationComplementaire dossierInformationComplementaire = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY );

        daoUtil.setInt( 1, nId );
        daoUtil.setString( 2, typeRessource );

        daoUtil.executeQuery(  );

        int nPos = 0;

        while ( daoUtil.next(  ) )
        {
            nPos = 0;
            dossierInformationComplementaire = new DossierInformationComplementaire(  );
            dossierInformationComplementaire.setIdDossier( daoUtil.getInt( ++nPos ) );
            dossierInformationComplementaire.setRapporteur( daoUtil.getString( ++nPos ) );
            dossierInformationComplementaire.setNote( daoUtil.getString( ++nPos ) );
            listDossierInformationComplementaire.add( dossierInformationComplementaire );
        }

        daoUtil.free(  );

        return listDossierInformationComplementaire;
    }
}
