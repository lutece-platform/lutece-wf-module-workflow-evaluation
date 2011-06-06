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

import fr.paris.lutece.plugins.workflow.modules.evaluation.service.EvaluationPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * This class provides instances management methods (create, find, ...) for DocumentWorkflowHome objects
 */
public final class DossierInformationComplementaireHome
{
    // Static variable pointed at the DAO instance
    private static IDossierInformationComplementaire _dao = (IDossierInformationComplementaire) SpringContextService.getPluginBean( EvaluationPlugin.PLUGIN_NAME,
            "dossierInformationComplementaireDAO" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private DossierInformationComplementaireHome(  )
    {
    }

    /**
         * Insere des informations compl�mentaires sur le dossier
         * @param dossierInformationComplementaire information complementaire
         * @param plugin le pugin
         */
    public static void create( DossierInformationComplementaire dossierInformationComplementaire, Plugin plugin )
    {
        _dao.insert( dossierInformationComplementaire, plugin );
    }

    /**
         * Charge les informations compl�mentaires sur le dossier
         * @param nId l'identifiant du dossier
         * @param plugin le plugin
         * @return les informations complementaires du dossier
         */
    public static List<DossierInformationComplementaire> selectByIdDossierAndTypeRessource( int nIdDossier,
        String typeRessource, Plugin plugin )
    {
        return _dao.load( nIdDossier, typeRessource, plugin );
    }
}
