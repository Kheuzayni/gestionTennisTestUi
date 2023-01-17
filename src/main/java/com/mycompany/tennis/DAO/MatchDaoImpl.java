package com.mycompany.tennis.DAO;

import com.mycompany.tennis.DataSourceProvider;
import com.mycompany.tennis.entity.Match;

import javax.sql.DataSource;
import java.sql.*;

public class MatchDaoImpl {
    public void createMatchWithScore(Match match){

        Connection conn = null;
        try {
            DataSource dataSource = DataSourceProvider.getSingleDataSourceInstance();

            conn = dataSource.getConnection();
            //Test connexion
            System.out.println("\n success Test acces bdd");

            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `match_tennis` (`ID_EPREUVE`, `ID_VAINQUEUR`, `ID_FINALISTE`) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, match.getEpreuve().getId());
            preparedStatement.setLong(2, match.getVainqueur().getId());
            if (match.getVainqueur().getId() == null){
                preparedStatement.setNull(3, Types.TINYINT);
            }
            else{
                preparedStatement.setLong(3, match.getVainqueur().getId());
            }

            preparedStatement.executeQuery();

            //recuperer Toutes les valeurs auto-générées  après l'enregistrement
            ResultSet rs=preparedStatement.getGeneratedKeys();

            if (rs.next()){
                match.setId(rs.getLong(1));
            }

            System.out.println("Match créé avec succes");
            //fin ajout


            PreparedStatement preparedStatement2 = conn.prepareStatement("INSERT INTO `score_vainqueur` (`ID_MATCH`, `SET_1`, `SET_2`, `SET_3`, `SET_4`, `SET_5`) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);


            preparedStatement2.setLong(1, match.getScore().getId());
            preparedStatement2.setByte(2, match.getScore().getSet1());
            preparedStatement2.setByte(3, match.getScore().getSet2());
            if (match.getScore().getSet3() == null){
                preparedStatement2.setNull(4,Types.TINYINT);
            }
            else{
                preparedStatement2.setLong(4, match.getScore().getSet3());
            }

            if (match.getScore().getSet4() == null){
                preparedStatement2.setNull(5,Types.TINYINT);
            }
            else{
                preparedStatement2.setLong(5, match.getScore().getSet5());
            }
            if (match.getScore().getSet5() == null){
                preparedStatement2.setNull(6,Types.TINYINT);
            }
            else{
                preparedStatement2.setLong(6, match.getScore().getSet5());
            }

            preparedStatement2.executeUpdate();

            //recuperer Toutes les valeurs auto-générées  après l'enregistrement
            ResultSet rs2=preparedStatement2.getGeneratedKeys();

            if (rs2.next()){
                match.getScore().setId(rs.getLong(1));
            }

            System.out.println("Score enrégistré avec succes");
            //fin ajout

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn!=null) conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        finally {
            try {
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }



}
