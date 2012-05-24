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
package fr.paris.lutece.plugins.workflow.modules.evaluation.business.note;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * NoteDAO
 */
public final class NoteDAO implements INoteDAO
{
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT  id_note,libelle,valeur  FROM note WHERE id_note=?";
    private static final String SQL_QUERY_SELECT = "SELECT id_note,libelle,valeur  FROM note ORDER BY id_note";

    /**
     * {@inheritDoc}
     */
    @Override
    public Note load( int nIdNote, Plugin plugin )
    {
        Note note = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nIdNote );
        daoUtil.executeQuery(  );

        int nPos = 0;

        if ( daoUtil.next(  ) )
        {
            note = new Note(  );
            note.setId( daoUtil.getInt( ++nPos ) );
            note.setLibelle( daoUtil.getString( ++nPos ) );
            note.setValeur( daoUtil.getString( ++nPos ) );
        }

        daoUtil.free(  );

        return note;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Note> selectNote( Plugin plugin )
    {
        List<Note> listNote = new ArrayList<Note>(  );
        Note note = null;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.executeQuery(  );

        int nPos = 0;

        while ( daoUtil.next(  ) )
        {
            nPos = 0;
            note = new Note(  );
            note.setId( daoUtil.getInt( ++nPos ) );
            note.setLibelle( daoUtil.getString( ++nPos ) );
            note.setValeur( daoUtil.getString( ++nPos ) );

            listNote.add( note );
        }

        daoUtil.free(  );

        return listNote;
    }
}
